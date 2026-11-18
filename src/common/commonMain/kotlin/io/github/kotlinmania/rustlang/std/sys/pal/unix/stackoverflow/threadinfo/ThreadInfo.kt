// port-lint: source sys/pal/unix/stack_overflow/thread_info.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo

import io.github.kotlinmania.rustlang.core.hint.spinLoop
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.compareExchange
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.libc.pause
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.errnoLocation
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.current.currentOsId
import io.github.kotlinmania.rustlang.std.thread.current.withCurrentName

// TLS, but async-signal-safe.
//
// Unfortunately, because thread local storage isn't async-signal-safe, we
// cannot soundly use it in our stack overflow handler. While this works
// without problems on most platforms, it can lead to undefined behaviour
// on others (such as GNU/Linux). Luckily, the POSIX specification documents
// two thread-specific values that can be accessed in asynchronous signal
// handlers: the value of `pthreadSelf()` and the address of `errno`. As
// `pthreadT` is an opaque platform-specific type, we use the address of
// `errno` here. As it is thread-specific and does not change over the
// scope of a thread, we can use `errno` as a key for a `BTreeMap`
// that stores thread-specific data.
//
// Concurrent access to this map is synchronized by two locks – an outer
// [Mutex] and an inner spin lock that also remembers the identity of
// the lock owner:
// * The spin lock is the primary means of synchronization: since it only
//   uses native atomics, it can be soundly used inside the signal handle
//   as opposed to [Mutex], which might not be async-signal-safe.
// * The [Mutex] prevents busy-waiting in the setup logic, as all accesses
//   there are performed with the [Mutex] held, which makes the spin-lock
//   redundant in the common case.
// * Finally, by using the `errno` address as the locked value of the spin
//   lock, we can detect cases where a SIGSEGV occurred while the thread
//   info is being modified.

public open class ThreadInfo(
    public var tid: ULong,
    public var name: String?,
    public var guardPageRange: IntRange,
)

public var LOCK: Mutex<Unit> = Mutex.new(Unit)

public var SPIN_LOCK: AtomicUsize = AtomicUsize.new(0)

// This uses a `BTreeMap` instead of a hashmap since it supports constant
// initialization and automatically reduces the amount of memory used when
// items are removed.
public var THREAD_INFO: MutableMap<Int, ThreadInfo> = mutableMapOf()

public open class UnlockOnDrop {
    public open fun drop() {
        SPIN_LOCK.store(0, Ordering.Release)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UnlockOnDrop> =
            object : TypeLayout<UnlockOnDrop> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): UnlockOnDrop =
                    UnlockOnDrop()
                override fun writeNative(addr: Long, value: UnlockOnDrop) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnlockOnDrop =
                    UnlockOnDrop()
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnlockOnDrop) {
                }
            }
    }
}

/**
 * Get the current thread's information, if available.
 *
 * Calling this function might freeze other threads if they attempt to modify
 * their thread information. Thus, the caller should ensure that the process
 * is aborted shortly after this function is called.
 *
 * This function is guaranteed to be async-signal-safe if `f` is too.
 */
public fun <R> withCurrentInfo(f: (ThreadInfo?) -> R): R {
    val thisRef = (errnoLocation()).rawAddress.toInt()
    var attempt = 0
    val guard =
        while (true) {
            // If we are just spinning endlessly, it's very likely that the thread
            // modifying the thread info map has a lower priority than us and will
            // not continue until we stop running. Just give up in that case.
            if (attempt == 10000000) {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        out.writeFmt("deadlock in SIGSEGV handler")
                    }
                }
                run {
                    "deadlock in SIGSEGV handler"
                }
                return f(null)
            }
            run {
                val matchVal1 = SPIN_LOCK.compareExchange(0, thisRef, Ordering.Acquire, Ordering.Relaxed)
                when {
                    matchVal1 is Result.Ok<*, *> -> { break }
                    matchVal1 is Result.Err<*, *> && matchVal1.errValue() == thisRef -> {
                        val owner = matchVal1.errValue()
                        run {
                            when (val matchVal2 = panicOutput()) {
                                null -> {}
                                else -> {
                                    val out1 = matchVal2
                                    out1.writeFmt("fatal runtime error: " + ("a thread received SIGSEGV while modifying its stack overflow information").toString() + ", aborting\n")
                                }
                            }
                            run {
                                "fatal runtime error: " + ("a thread received SIGSEGV while modifying its stack overflow information").toString() + ", aborting\n"
                            }
                            abort()
                        }
                    }
                    // Spin until the lock can be acquired – there is nothing better to
                    // do. This is unfortunately a priority hole, but a stack overflow
                    // is a fatal error anyway.
                    else -> {
                        spinLoop()
                        attempt += 1
                    }
                }
            }
        }
    // SAFETY: we own the spin lock, so `THREAD_INFO` cannot not be aliased.
    val threadInfo = (UnresolvedTypeLayout<MutableMap<Int, ThreadInfo>>()).readNative(((addrOf(THREAD_INFO))).rawAddress)
    return f(threadInfo.get(thisRef))
}

public fun spinLockInSetup(thisRef: Int): UnlockOnDrop {
    while (true) {
        run {
            val matchVal = SPIN_LOCK.compareExchange(0, thisRef, Ordering.Acquire, Ordering.Relaxed)
            when {
                matchVal is Result.Ok<*, *> -> { return UnlockOnDrop() }
                matchVal is Result.Err<*, *> && matchVal.errValue() == thisRef -> {
                    val owner = matchVal.errValue()
                    error("the thread info setup logic isn't recursive")
                }
                // This function is always called with the outer lock held,
                // meaning the only time locking can fail is if another thread has
                // encountered a stack overflow. Since that will abort the process,
                // we just stop the current thread until that time. We use `pause`
                // instead of spinning to avoid priority inversion.
                // SAFETY: this doesn't have any safety preconditions.
                else -> (pause()).let { }
            }
        }
    }
}

public fun setCurrentInfo(guardPageRange: IntRange) {
    val tid = currentOsId()
    val name = withCurrentName({ name -> name.map(Box::from) })
    val thisRef = (errnoLocation()).rawAddress.toInt()
    // SAFETY: we own the spin lock, so `THREAD_INFO` cannot be aliased.
    val threadInfo = RustMutableReference(MutPtr(((addrOfMut(THREAD_INFO))).rawAddress), UnresolvedTypeLayout<MutableMap<Int, ThreadInfo>>())
    threadInfo.put(thisRef, ThreadInfo(tid = tid, name = name, guardPageRange = guardPageRange))
}

public fun deleteCurrentInfo() {
    val thisRef = (errnoLocation()).rawAddress.toInt()
    // SAFETY: we own the spin lock, so `THREAD_INFO` cannot not be aliased.
    val threadInfo = RustMutableReference(MutPtr(((addrOfMut(THREAD_INFO))).rawAddress), UnresolvedTypeLayout<MutableMap<Int, ThreadInfo>>())
    threadInfo.remove(thisRef)
}
