// port-lint: source sys/thread_local/guard/windows.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.windows

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.fence
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FLS_OUT_OF_INDEXES
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.destructors.run

// Support for Windows TLS destructors.
//
// Windows has an API to provide a destructor for a FLS (fiber local storage) variable,
// which behaves similarly to a TLS variable for our purpose [1].
//
// All TLS destructors are tracked by *us*, not the Windows runtime.
// This means that we have a global list of destructors for
// each TLS key or variable that we know about.
//
// [1]: https://devblogs.microsoft.com/oldnewthing/20191011-00/?p=102989

public typealias Key = UInt

public fun create(dtor: c.PflsCallbackFunction): Key {
    val keyResult = c.FlsAlloc(dtor)
    if (keyResult == c.FLS_OUT_OF_INDEXES) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("out of FLS keys").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("out of FLS keys").toString() + ", aborting\n"
            }
            abort()
        }
    }
    return keyResult
}

public fun set(key: Key, ptr: ConstPtr) {
    val result = c.FlsSetValue(key, ptr)
    if (result == c.FALSE) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("failed to set FLS value").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("failed to set FLS value").toString() + ", aborting\n"
            }
            abort()
        }
    }
}

public fun isThreadAFiber(): Boolean {
    val res = c.IsThreadAFiber()
    return (res == c.TRUE).toUByte()
}

public var KEY: AtomicU32 = AtomicU32.new(FLS_OUT_OF_INDEXES)

/**
 * Used to track whether we are currently in the critical section of `enable`.
 * For miri, these atomic operations cause synchronization that can mask user bugs,
 * and they are not needed as `atexit` is anyway not supported, so we can skip them.
 */
public open class EnableGuard {
    public open fun drop() {
        ACTIVE_ENABLE_CALLS.fetchSub(1, Ordering.Relaxed)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<EnableGuard> =
            object : TypeLayout<EnableGuard> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): EnableGuard =
                    EnableGuard()
                override fun writeNative(addr: Long, value: EnableGuard) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): EnableGuard =
                    EnableGuard()
                override fun writeBytes(buf: ByteArray, offset: Int, value: EnableGuard) {
                }
            }

        // Mark the start of an `enable` call, returning whether the `atexit` hook has already been called or not.
        public fun new(): Pair<EnableGuard, Boolean> {
            if (false) {
                return Pair(EnableGuard, false)
            }
            ACTIVE_ENABLE_CALLS.fetchAdd(1, Ordering.Relaxed)
            // Both `new` and `startExit` publish state to one atomic and inspect the other.
            // `AcqRel` is insufficient because neither read is required to observe the other's publication,
            // so we could create the guard but `startExit` would not see any active enable calls.
            // `SeqCst` ensures that there's a single global order between the publish and check,
            // so at least one side must observe the other and bail.
            fence(Ordering.SeqCst)
            val atExitCalled = AT_EXIT_HOOK_CALLED.load(Ordering.Relaxed)
            return Pair(EnableGuard, atExitCalled)
        }

        /**
         * Mark the start of process exit, returning whether we should free the FLS key or not.
         */
        public fun startExit(): Boolean {
            // After this hook starts, new destructor registration will be skipped,
            // causing TLS destructors initialized after this point to leak.
            if (AT_EXIT_HOOK_CALLED.swap(true, Ordering.Relaxed)) {
                // Cleanup already started, there is nothing else to do.
                return false
            }
            fence(Ordering.SeqCst)
            val anyActiveEnabledCalled = ACTIVE_ENABLE_CALLS.load(Ordering.Relaxed) != 0
            if (anyActiveEnabledCalled) {
                // If another thread is currently in `enable`, it may already have loaded this key and may be about to call `FlsSetValue`.
                // So we must *not* call free the FLS key.
                //
                // During real process exit this is harmless because the `cleanup` hook is always available,
                // and the FLS callback will be triggered normally by the OS.
                //
                // During DLL unload, the unloader cannot safely have threads running code from the DLL except for the destructors,
                // so there must not be any `enable` calls active anyway.
                return false
            }
            return true
        }
    }
}

public var AT_EXIT_HOOK_CALLED: AtomicBool = AtomicBool.new(false)

public var ACTIVE_ENABLE_CALLS: AtomicU32 = AtomicU32.new(0)

/**
 * Set up the current thread to invoke `cleanup` when it finishes.
 */
public fun enable() {
    val registered =
        if (false) {
            val REGISTERED: Cell<Boolean>  = Cell.new(false)
            REGISTERED.replace(true)
        } else {
            // `@threadLocal` is unavailable on windows-gnu (`targetThreadLocal` is off),
            // but setting the FLS key's value is about as expensive as `TlsGet`, so we don't bother tracking registration separately.
            false
        }
    if (!registered) {
        // We are in a critical section where we are trying to register a destructor for the current thread.
        // We need to avoid racing with the `atexit` hook that frees the FLS slot, which would cause us to call `FlsSetValue` on a freed key,
        // or calling `atexit` during process shutdown, which would cause a deadlock.
        val (guard, atExitCalled) = EnableGuard.new()
        if (atExitCalled) {
            // We are exiting and don't want to race with the `atexit` hook, so we won't be able to run the destructors for this thread.
            return
        }
        val currentKey = KEY.load(Ordering.Acquire)
        // If we already allocated a key, we only need to set it to a non-null value so that the destructors hook is run for this thread.
        val key =
            if (currentKey != FLS_OUT_OF_INDEXES) {
                currentKey
            } else {
                // Otherwise, we try to allocate a key.
                val newKey = create(::cleanup)
                // Now we need to set this key to be used by everyone else.
                // If we won the race, our key is the right one and we can set it to non-null value.
                // If we lost, we'll use the winning key and free our losing key.
                run {
                    val matchVal = KEY.compareExchange(currentKey, newKey, Ordering.Release, Ordering.Acquire)
                    when (matchVal) { is Result.Ok -> {
                        // If the current DLL is unloaded, the registered `cleanup` hook will not be available later during thread exit,
                        // triggering a `STATUS_ACCESS_VIOLATION`. To avoid this, we use the `atexit` hook, which is called during DLL unload
                        // to manually free the FLS slot, triggering the destructors.
                        //
                        // However, calling `atexit` during process exit can cause a deadlock.
                        // In a Rust binary, `enable` is called during the main thread startup and before any user code,
                        // and we checked using `atExitCalled` that we aren't in process shutdown.
                        //
                        // In a Rust DLL, dynamic unloading can only happen safely when no other threads are
                        // concurrently executing Rust code, so if we are here we cannot be unloading yet.
                        //
                        // If a main non-Rust binary is exiting, it must not be trigger the `enable` guard
                        // for the first time during process shutdown.
                        //
                        // Miri has no DLL unloading so we can skip this step here.
                        if (!false) {
                            if (cleanupIsUnloadable()) {
                                val res = c.atexit(::freeFlsKeyAtExit)
                                if (res != 0) {
                                    run {
                                        when (val matchVal1 = panicOutput()) {
                                            null -> {}
                                            else -> {
                                                val out = matchVal1
                                                Write.writeFmt(out, "fatal runtime error: " + ("failed to register fls atexit hook").toString() + ", aborting\n")
                                            }
                                        }
                                        run {
                                            "fatal runtime error: " + ("failed to register fls atexit hook").toString() + ", aborting\n"
                                        }
                                        abort()
                                    }
                                }
                            }
                        }
                        newKey
                    }; is Result.Err -> {
                        val otherKey = matchVal.error
                        c.FlsFree(newKey)
                        otherKey
                    } }
                }
            }
        // We must not set the key if we are in a fiber, since deleting that fiber from a thread
        // will cause the destructors to run before thread exit.
        if (isThreadAFiber()) {
            return
        }
        // Setting the key's value to non-zero will cause the dtor callback to be called when the thread exits.
        set(key, ptr.withoutProvenance(1))
    }
}

/**
 * Checks if `cleanup` is in a different module from the main executable,
 * using `GetModuleHandleExW(FLAG_FROM_ADDRESS, cleanup) != GetModuleHandleW(ptr.null())`.
 *
 * If `cleanup` lives in the main executable, its code cannot be unmapped
 * before process exit, so no unload hook is needed.
 *
 * If it lives in a DLL, the DLL may be unloaded while the process keeps
 * running, so the FLS callback must be unregistered before that image is
 * unmapped.
 *
 * On failure, return true, which assumes it can be unloaded.
 */
public fun cleanupIsUnloadable(): Boolean {
    // Get a handle to the module of `cleanup`.
    val cleanupModule = run {
        val handle: c.Hmodule  = MutPtr(0L)
        val res = c.GetModuleHandleExW(c.GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS or c.GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT, ConstPtr((constPtrFromRustCast(::cleanup)).rawAddress), handle)
        if (res == c.FALSE || handle.isNull()) {
            return true
        }
        handle
    }
    // Get a handle to the file used to create the calling process (.exe file).
    val mainExeModule = c.GetModuleHandleW(ConstPtr(0L))
    if (mainExeModule.isNull()) {
        return true
    }
    return (cleanupModule != mainExeModule).toUByte()
}

public fun freeFlsKeyAtExit() {
    // The main purpose of this hook is to free the FLS slot during DLL unload.
    // However, this hook will also be called during normal process exit, while other Rust threads are still running,
    // so we must be careful to avoid races with `enable`.
    val shouldFreeKey = EnableGuard.startExit()
    if (!shouldFreeKey) {
        return
    }
    val currentKey = KEY.swap(c.FLS_OUT_OF_INDEXES, Ordering.AcqRel)
    if (currentKey != c.FLS_OUT_OF_INDEXES) {
        // Calling `FlsFree` will cause the OS to call the `cleanup` hook, in the current thread, *for each thread* (or fiber) with a value in this FLS slot.
        // `cleanup` is safe to run repeatedly: it only drains the current thread's TLS destructor list, and we check that we are not running in a fiber before doing so.
        // We only call this when no `enable` call is active, so it cannot race with `FlsSetValue` using this key.
        // Destructors of thread locals in other threads will not run and therefore leak, which is allowed since we are exiting or unloading.
        c.FlsFree(currentKey)
    }
}

public fun cleanup(ptr: ConstPtr) {
    // Avoid running the hook if we are in a fiber.
    // This will cause destructors of thread locals to not run, leaking them.
    // Thread-local runtime state will not be cleaned.
    //
    // We need to verify that we won't run the destructors *before* the thread exits,
    // but if the fiber that registered the callback is deleted, the thread might still be running other fibers.
    //
    // By checking that we are not running in a fiber here, we are guaranteed that the hook is only running during the thread's exit.
    // See also the `fiberDoesNotTriggerDtor` test.
    if (isThreadAFiber()) {
        return
    }
    run {
        run()
    }
    threadCleanup()
}
