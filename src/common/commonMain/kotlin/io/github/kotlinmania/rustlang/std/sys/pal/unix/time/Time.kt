// port-lint: source sys/pal/unix/time.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.time

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.castUnsigned
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedAddUnsigned
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.checkedSubUnsigned
import io.github.kotlinmania.rustlang.core.num.nichetypes.Nanoseconds
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.clockGettime
import io.github.kotlinmania.rustlang.libc.timespec.default
import io.github.kotlinmania.rustlang.libc.wasi.ClockidT
import io.github.kotlinmania.rustlang.libc.wasi.TimeT
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

public val NSEC_PER_SEC: ULong = 1000000000uL

// This additional constant is only used when calling
// `libc.pthreadCondTimedwait`.
public val TIMESPEC_MAX_CAPPED: io.github.kotlinmania.rustlang.libc.unix.Timespec = io.github.kotlinmania.rustlang.libc.unix.Timespec(tvSec = (ULong.MAX_VALUE / NSEC_PER_SEC).toLong(), tvNsec = (ULong.MAX_VALUE % NSEC_PER_SEC).toLong())

// Used for pthread condvar timeouts
public val TIMESPEC_MAX: io.github.kotlinmania.rustlang.libc.unix.Timespec =
    run {
        val ts = null
        ts.tvSec = TimeT.MAX
        ts.tvNsec = 1000000000 - 1
        ts
    }

public open class Timespec(
    public var tvSec: Long,
    public var tvNsec: Nanoseconds,
) {
    public open fun clone(): Timespec = Timespec(tvSec, tvNsec)

    public open fun subTimespec(other: Timespec): Result<Duration, Duration> {
        // When a >= b, the difference fits in u64.
        fun subGeToUnsigned(a: Long, b: Long): ULong {
            check(a >= b)
            return (a - (b.toLong())).castUnsigned()
        }
        return if (this >= other) {
            val (secs, nsec) =
                if (this.tvNsec.asInner() >= other.tvNsec.asInner()) {
                    Pair(subGeToUnsigned(this.tvSec, other.tvSec), this.tvNsec.asInner() - other.tvNsec.asInner())
                } else {
                    // Following sequence of assertions explain why `self.tvSec - 1` does not underflow.
                    check(this.tvNsec < other.tvNsec)
                    check(this.tvSec > other.tvSec)
                    check(this.tvSec > Long.MIN_VALUE)
                    Pair(subGeToUnsigned(this.tvSec - 1L, other.tvSec), this.tvNsec.asInner() + (NSEC_PER_SEC.toUInt()) - other.tvNsec.asInner())
                }
            Result.Ok<Duration, Duration>(Duration.new(secs, nsec))
        } else {
            run {
                val matchVal = other.subTimespec(this)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val d = matchVal.okValue()
                    Result.Err<Duration, Duration>(d)
                }; is Result.Err<*, *> -> {
                    val d1 = matchVal.errValue()
                    Result.Ok<Duration, Duration>(d1)
                } }
            }
        }
    }

    public open fun checkedAddDuration(other: Duration): Timespec? {
        var secs = (this.tvSec.checkedAddUnsigned(other.asSecs()) ?: return null)
        // Nano calculations can't overflow because nanos are <1B which fit
        // in a u32.
        var nsec = other.subsecNanos() + this.tvNsec.asInner()
        if (nsec >= NSEC_PER_SEC.toUInt()) {
            nsec -= NSEC_PER_SEC.toUInt()
            secs = (secs.checkedAdd(1) ?: return null)
        }
        return Timespec.newUnchecked(secs, nsec.into())
    }

    public open fun checkedSubDuration(other: Duration): Timespec? {
        var secs = (this.tvSec.checkedSubUnsigned(other.asSecs()) ?: return null)
        // Similar to above, nanos can't overflow.
        var nsec = this.tvNsec.asInner().toInt() - other.subsecNanos().toInt()
        if (nsec < 0) {
            nsec += NSEC_PER_SEC.toInt()
            secs = (secs.checkedSub(1) ?: return null)
        }
        return Timespec.newUnchecked(secs, nsec.into())
    }

    public open fun toTimespec(): io.github.kotlinmania.rustlang.libc.unix.Timespec? {
        return run {
            val ts = default()
            ts.tvSec = (this.tvSec.tryInto().ok() ?: return null)
            ts.tvNsec = (this.tvNsec.asInner().tryInto().ok() ?: return null)
            ts
        }
    }

    // On QNX, the maximum timespec for e.g. pthreadCondTimedwait
    // is 2^64 nanoseconds
    public open fun toTimespecCapped(): io.github.kotlinmania.rustlang.libc.unix.Timespec? {
        // Check if timeout in nanoseconds would fit into an u64
        if ((this.tvNsec.asInner().toULong()).checkedAdd((((this.tvSec.toULong()).checkedMul(NSEC_PER_SEC) ?: return null)).toULong()) == null) {
            return null
        }
        return this.toTimespec()
    }

    public companion object {
        public val MAX: Timespec = Timespec.newUnchecked(Long.MAX_VALUE, 1000000000L - 1L)

        // As described below, on Apple OS, dates before epoch are represented differently.
        // This is not an issue here however, because we are using tvSec = i64.Min,
        // which will cause the compatibility wrapper to not be executed at all.
        public val MIN: Timespec = Timespec.newUnchecked(Long.MIN_VALUE, 0L)

        public fun newUnchecked(tvSec: Long, tvNsec: Long): Timespec = Timespec(tvSec = tvSec, tvNsec = Nanoseconds.newUnchecked(tvNsec.toUInt()))

        public fun zero(): Timespec = Timespec.newUnchecked(0L, 0L)

        public fun new(tvSec: Long, tvNsec: Long): Result<Timespec, Error> {
            // On Apple OS, dates before epoch are represented differently than on other
            // Unix platforms: e.g. 1/10th of a second before epoch is represented as `seconds = - 1`
            // and `nanoseconds = 100_000_000` on other platforms, but is `seconds = 0` and
            // `nanoseconds = - 900_000_000` on Apple OS.
            //
            // To compensate, we first detect this special case by checking if both
            // seconds and nanoseconds are in range, and then correct the value for seconds
            // and nanoseconds to match the common unix representation.
            //
            // Please note that Apple OS nonetheless accepts the standard unix format when
            // setting file times, which makes this compensation round-trippable and generally
            // transparent.
            val (tvSec1, tvNsec1) =
                if ((tvSec <= 0L && tvSec > Long.MIN_VALUE) && (tvNsec < 0L && tvNsec > -1000000000L)) {
                    Pair(tvSec - 1L, tvNsec + 1000000000L)
                } else {
                    Pair(tvSec, tvNsec)
                }
            return if (tvNsec1 >= 0 && tvNsec1 < NSEC_PER_SEC.toLong()) {
                Result.Ok<Timespec, Error>(Timespec.newUnchecked(tvSec1, tvNsec1))
            } else {
                Result.Err<Timespec, Error>(constError(ErrorKind.InvalidData, "invalid timestamp"))
            }
        }

        public fun now(clock: ClockidT): Timespec {
            val t = MaybeUninit.uninit(PrimitiveLayouts.U8)
            cvt(clockGettime(clock, t.asMutPtr())).unwrap()
            val t1 = t.assumeInit()
            return Timespec.new(t1.tvSec.toLong(), t1.tvNsec.toLong()).unwrap()
        }
    }
}
