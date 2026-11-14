// port-lint: source sys/pal/windows/time.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.time

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI64
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.pal.c
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

public val NANOS_PER_SEC: ULong = 1000000000uL

public val INTERVALS_PER_SEC: ULong = NANOS_PER_SEC / 100uL

public fun checkedDur2intervals(dur: Duration): Long? {
    return ((dur.asSecs().checkedMul(INTERVALS_PER_SEC) ?: return null).checkedAdd(dur.subsecNanos().toULong() / 100uL) ?: return null).tryInto().ok()
}

public fun intervals2dur(intervals: ULong): Duration = Duration.new(intervals / INTERVALS_PER_SEC, ((intervals % INTERVALS_PER_SEC) * 100uL).toUInt())

public fun now(): Long {
    val localStorageL28C8 = AddressableLocal(0L, PrimitiveLayouts.I64)
    val qpcValue by localStorageL28C8
    cvt(c.QueryPerformanceCounter(RustMutableReference(localStorageL28C8.asMutPtr(), localStorageL28C8.layout))).unwrap()
    return qpcValue
}

public fun frequency(): Long {
    // Either the cached result of `QueryPerformanceFrequency` or `0` for
    // uninitialized. Storing this as a single `AtomicI64` allows us to use
    // `Relaxed` operations, as we are only interested in the effects on a
    // single memory location.
    val FREQUENCY: AtomicI64  = AtomicI64.new(0)
    val cached = FREQUENCY.load(Ordering.Relaxed)
    // If a previous thread has filled in this global state, use that.
    if (cached != 0) {
        return cached
    }
    // ... otherwise learn for ourselves ...
    return frequencyInit(FREQUENCY)
}

public fun frequencyInit(cache: AtomicI64): Long {
    val localStorageL51C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val frequency by localStorageL51C8
    run {
        cvt(c.QueryPerformanceFrequency(RustMutableReference(localStorageL51C8.asMutPtr(), localStorageL51C8.layout))).unwrap()
    }
    // SAFETY: According to the MSDN entry for `QueryPerformanceFrequency`,
    // a value of 0 will never be returned starting from Windows XP.
    assertUnchecked(frequency != 0)
    cache.store(frequency, Ordering.Relaxed)
    return frequency.toLong()
}

// Per microsoft docs, the margin of error for cross-thread time comparisons
// using QueryPerformanceCounter is 1 "tick" -- defined as 1/frequency().
// Reference: https://docs.microsoft.com/en-us/windows/desktop/SysInfo
// /acquiring-high-resolution-time-stamps
public fun epsilon(): Duration {
    val epsilon = NANOS_PER_SEC / (frequency().toULong())
    return Duration.fromNanos(epsilon)
}

/**
 * A timer you can wait on.
 */
public open class WaitableTimer internal constructor(
    handleCtorArg: c.Handle,
) {
    public var handle: c.Handle = handleCtorArg
        internal set

    public open fun set(duration: Duration): Result<Unit, Unit> {
        // Convert the Duration to a format similar to FILETIME.
        // Negative values are relative times whereas positive values are absolute.
        // Therefore we negate the relative duration.
        val time = when (val tryResult = checkedDur2intervals(duration).okOr()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.neg()
        val result = c.SetWaitableTimer(this.handle, time, 0, null, ConstPtr(0L), c.FALSE)
        return if (result != 0) {
            Result.Ok(Unit)
        } else {
            Result.Err(Unit)
        }
    }

    public open fun wait(): Result<Unit, Unit> {
        val result = c.WaitForSingleObject(this.handle, c.INFINITE)
        return if (result != c.WAIT_FAILED) {
            Result.Ok(Unit)
        } else {
            Result.Err(Unit)
        }
    }

    public open fun drop() {
        c.CloseHandle(this.handle)
    }

    public companion object {
        public operator fun invoke(
            handle: c.Handle,
        ): WaitableTimer = WaitableTimer(
            handleCtorArg = handle,
        )

        /**
         * Creates a high-resolution timer. Will fail before Windows 10, version 1803.
         */
        public fun highResolution(): Result<WaitableTimer, Unit> {
            val handle = c.CreateWaitableTimerExW(ConstPtr(0L), ConstPtr(0L), c.CREATE_WAITABLE_TIMER_HIGH_RESOLUTION, c.TIMER_ALL_ACCESS)
            return if (!handle.isNull()) {
                Result.Ok(WaitableTimer(handle = handle))
            } else {
                Result.Err(Unit)
            }
        }
    }
}
