// port-lint: source sys/thread/windows.rs
package io.github.kotlinmania.rustlang.std.sys.thread.windows

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.os.windows.io.HandleOrNull
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.STACK_SIZE_PARAM_IS_A_RESERVATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setThreadDescription
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.INFINITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SystemInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WAIT_FAILED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCurrentThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCurrentThreadId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getSystemInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.switchToThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.waitForSingleObject
import io.github.kotlinmania.rustlang.std.sys.pal.windows.dur2timeout
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.stackoverflow.reserveStack
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.WaitableTimer
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s
import io.github.kotlinmania.rustlang.std.thread.lifecycle.ThreadInit
import io.github.kotlinmania.rustlang.std.time.Instant

public val DEFAULT_MIN_STACK_SIZE: Int = 2 * 1024 * 1024

public open class Thread internal constructor(
    handleCtorArg: Handle,
) {
    public var handle: Handle = handleCtorArg
        internal set

    public open fun join() {
        val rc = waitForSingleObject(this.handle.asRawHandle(), INFINITE)
        if (rc == WAIT_FAILED) {
            error("failed to join on thread: ${Error.lastOsError()}")
        }
    }

    public open fun handle(): Handle = this.handle

    public open fun intoHandle(): Handle = this.handle

    public companion object {
        public operator fun invoke(
            handle: Handle,
        ): Thread = Thread(
            handleCtorArg = handle,
        )

        // unchecked: see thread.Builder.spawnUnchecked for safety requirements
        // even without panics, this helps for Miri backtraces
        public fun new(stack: Int, init: ThreadInit): Result<Thread, Error> {
            fun threadStart(data: MutPtr): UInt {
                // SAFETY: we are simply recreating the box that was leaked earlier.
                val init1 = Box.fromRaw(MutPtr(data.rawAddress, data.rawMeta))
                val rustStart = init1.init()
                // Reserve some stack space for if we otherwise run out of stack.
                reserveStack()
                rustStart()
                return 0u
            }
            val data = Box.intoRaw(init)
            // CreateThread rounds up values for the stack size to the nearest page size (at least 4kb).
            // If a value of zero is given then the default stack size is used instead.
            // SAFETY: `threadStart` has the right ABI for a thread's entry point.
            // `data` is simply passed through to the new thread without being touched.
            val ret1 =
                run {
                    val ret = createThread(MutPtr(0L), stack, ::threadStart, mutPtrFromRustCast(data), STACK_SIZE_PARAM_IS_A_RESERVATION, MutPtr(0L))
                    HandleOrNull.fromRawHandle(ret)
                }
            return when (val matchVal = ret1.tryInto()) {
                is Result.Ok<*, *> -> {
                    val handle = matchVal.okValue()
                    Result.Ok<Thread, Error>(Thread(handle = Handle.fromInner(handle)))
                }
                else -> {
                    // The thread failed to start and as a result data was not consumed. Therefore, it is
                    // safe to reconstruct the box so that it gets deallocated.
                    (Box.fromRaw(data)).let { }
                    Result.Err<Thread, Error>(Error.lastOsError())
                }
            }
        }
    }
}

public fun availableParallelism(): Result<NonZero<Int, NonZeroUsizeInner>, Error> {
    val res =
        run {
            val sysinfo: SystemInfo  = SystemInfo()
            getSystemInfo(sysinfo)
            sysinfo.dwNumberOfProcessors.toInt()
        }
    return when (res) {
        0 -> Result.Err<NonZero<Int, NonZeroUsizeInner>, Error>(Error.UNKNOWN_THREAD_COUNT)
        else -> {
            val cpus = res
            Result.Ok<NonZero<Int, NonZeroUsizeInner>, Error>(NonZero.newUnchecked<NonZeroUsizeInner, Int>(cpus))
        }
    }
}

public fun currentOsId(): ULong? {
    // SAFETY: FFI call with no preconditions.
    val id: UInt = getCurrentThreadId()
    // A return value of 0 indicates failed lookup.
    return if (id == 0u) {
        null
    } else {
        id.into()
    }
}

public fun setName(name: CStr) {
    when (val matchVal = name.toStr()) {
        is Result.Ok<*, *> -> {
            val utf8 = matchVal.okValue()
            when (val matchVal1 = toU16s(utf8)) {
                is Result.Ok<*, *> -> {
                    val utf16 = matchVal1.okValue()
                    setNameWide(utf16)
                }
                else -> {}
            }
        }
        else -> {}
    }
}

/**
 * # Safety
 *
 * `name` must end with a zero value
 */
public fun setNameWide(name: List<UShort>) {
    setThreadDescription(getCurrentThread(), name.asPtr())
}

public fun sleep(dur: Duration) {
    fun highPrecisionSleep(dur1: Duration): Result<Unit, Unit> {
        val timer = when (val tryResult = WaitableTimer.highResolution()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        when (val tryResult = timer.set(dur1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return timer.wait()
    }
    // Directly forward to `Sleep` for its zero duration behavior when indeed
    // zero in order to skip the `Instant.now` calls, useless in this case.
    if (dur.isZero()) {
        io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.sleep(0u)
        // Attempt to use high-precision sleep (Windows 10, version 1803+).
        // On error, fallback to the standard `Sleep` function.
    } else if (highPrecisionSleep(dur).isErr()) {
        val start = Instant.now()
        io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.sleep(dur2timeout(dur))
        // See #149935: `Sleep` under Windows 7 and probably 8 as well seems a
        // bit buggy for us as it can last less than the requested time while
        // our API is meant to guarantee that. This is fixed by measuring the
        // effective time difference and if needed, sleeping a bit more in
        // order to ensure the duration is always exceeded. A fixed single
        // millisecond works because `Sleep` operates based on a system-wide
        // (until Windows 10 2004 that makes it process-local) interrupt timer
        // that counts in "tick" units of ~15ms by default: a 1ms timeout
        // therefore passes the next tick boundary.
        if (start.elapsed() < dur) {
            io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.sleep(1u)
        }
    }
}

public fun yieldNow() {
    // This function will return 0 if there are no other threads to execute,
    // but this also means that the yield was useless so this isn't really a
    // case that needs to be worried about.
    run {
        switchToThread()
    }
}
