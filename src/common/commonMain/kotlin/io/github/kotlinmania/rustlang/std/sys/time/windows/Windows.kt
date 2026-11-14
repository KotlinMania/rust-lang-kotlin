// port-lint: source sys/time/windows.rs
package io.github.kotlinmania.rustlang.std.sys.time.windows

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.helpers.mulDivU64
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Filetime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getSystemTimePreciseAsFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.INTERVALS_PER_SEC
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.checkedDur2intervals
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.intervals2dur
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.perfcounter.epsilon
import io.github.kotlinmania.rustlang.std.sys.pal.windows.time.perfcounter.frequency

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public val NANOS_PER_SEC: ULong = 1000000000uL

public open class Instant(
    // This duration is relative to an arbitrary microsecond epoch
    // from the winapi QueryPerformanceCounter function.
    public var t: Duration,
) {
    public open fun clone(): Instant = Instant(t)

    public open fun checkedSubInstant(other: Instant): Duration? {
        // On windows there's a threshold below which we consider two timestamps
        // equivalent due to measurement error. For more details + doc link,
        // check the docs on epsilon.
        val epsilon = epsilon()
        return if (other.t > this.t && other.t - this.t <= epsilon) {
            Duration.new(0, 0)
        } else {
            this.t.checkedSub(other.t)
        }
    }

    public open fun checkedAddDuration(other: Duration): Instant? {
        return Instant(t = (this.t.checkedAdd(other) ?: return null))
    }

    public open fun checkedSubDuration(other: Duration): Instant? {
        return Instant(t = (this.t.checkedSub(other) ?: return null))
    }

    public companion object {
        public fun now(): Instant {
            // High precision timing on windows operates in "Performance Counter"
            // units, as returned by the WINAPI QueryPerformanceCounter function.
            // These relate to seconds by a factor of QueryPerformanceFrequency.
            // In order to keep unit conversions out of normal interval math, we
            // measure in QPC units and immediately convert to nanoseconds.
            val freq = frequency().toULong()
            val now = io.github.kotlinmania.rustlang.std.sys.pal.windows.time.perfcounter.now()
            // We convert now to `ULong` to be able to use `Duration`.
            val instantNsec = mulDivU64(now.toULong(), NANOS_PER_SEC, freq)
            // We can add an arbitrary offset to shift the epoch of this clock. We do that to avoid
            // being too close to 0 which would lead to underflow when computing times in the past. Also
            // see <https://github.com/rust-lang/rust/issues/156142>.
            val instantNsec1 = instantNsec + (ULong.MAX_VALUE / 4uL)
            return Instant(t = Duration.fromNanos(instantNsec1))
        }
    }
}

public open class SystemTime(
    public var t: Filetime,
) : IntoInner<Filetime> {
    public open fun clone(): SystemTime = SystemTime(t)

    public open fun intervals(): Long = (this.t.dwLowDateTime.toLong()) or ((this.t.dwHighDateTime.toLong()) shl 32)

    public open fun subTime(other: SystemTime): Result<Duration, Duration> {
        val me = this.intervals()
        val other1 = other.intervals()
        return if (me >= other1) {
            Result.Ok(intervals2dur((me - other1).toULong()))
        } else {
            Result.Err(intervals2dur((other1 - me).toULong()))
        }
    }

    public open fun checkedAddDuration(other: Duration): SystemTime? {
        val intervals = (rustCheckedAdd(this.intervals(), ((checkedDur2intervals(other) ?: return null)).toLong()) ?: return null)
        return SystemTime.fromIntervals(intervals)
    }

    public open fun checkedSubDuration(other: Duration): SystemTime? {
        // Windows does not support times before 1601, hence why we don't
        // support negatives. In order to tackle this, we try to convert the
        // resulting value into an u64, which should obviously fail in the case
        // that the value is below zero.
        val intervals: ULong = ((rustCheckedSub(this.intervals(), ((checkedDur2intervals(other) ?: return null)).toLong()) ?: return null).tryInto().ok() ?: return null)
        return SystemTime.fromIntervals(intervals.toLong())
    }

    public open fun eq(other: SystemTime): Boolean = (this.intervals() == other.intervals()).toUByte()

    public open fun partialCmp(other: SystemTime): Ordering? = this.cmp(other)

    public open fun cmp(other: SystemTime): Ordering = this.intervals().cmp(other.intervals())

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "SystemTime").field("intervals", this.intervals()).finish()
    }

    public override fun intoInner(): Filetime = this.t

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.intervals()).hashCode()
        return state
    }

    public companion object {
        public val MAX: SystemTime = SystemTime.fromIntervals(Long.MAX_VALUE)

        public val MIN: SystemTime = SystemTime.fromIntervals(0L)

        public fun now(): SystemTime =
            run {
                val t: SystemTime?  = null
                getSystemTimePreciseAsFileTime(t!!.t)
                t
            }

        public fun fromIntervals(intervals: Long): SystemTime = SystemTime(t = Filetime(dwLowDateTime = intervals.toUInt(), dwHighDateTime = (intervals shr 32).toUInt()))

        public fun from(t: Filetime): SystemTime = SystemTime(t = t)
    }
}

public val UNIX_EPOCH: SystemTime = SystemTime.fromIntervals(11644473600L * INTERVALS_PER_SEC.toLong())
