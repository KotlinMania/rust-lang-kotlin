// port-lint: source sys/time/unix.rs
package io.github.kotlinmania.rustlang.std.sys.time.unix

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.CLOCK_UPTIME_RAW
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.KERN_SUCCESS
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.KernReturnT
import io.github.kotlinmania.rustlang.libc.wasi.CLOCK_MONOTONIC
import io.github.kotlinmania.rustlang.libc.wasi.CLOCK_REALTIME
import io.github.kotlinmania.rustlang.libc.wasi.ClockidT
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.pal.unix.time.Timespec

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

public val UNIX_EPOCH: SystemTime = SystemTime(t = Timespec.zero())

public open class SystemTime(
    public var t: Timespec,
) {
    public open fun clone(): SystemTime = SystemTime(t)

    public open fun subTime(other: SystemTime): Result<Duration, Duration> = this.t.subTimespec(other.t)

    public open fun checkedAddDuration(other: Duration): SystemTime? {
        return SystemTime(t = (this.t.checkedAddDuration(other) ?: return null))
    }

    public open fun checkedSubDuration(other: Duration): SystemTime? {
        return SystemTime(t = (this.t.checkedSubDuration(other) ?: return null))
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "SystemTime").field("tv_sec", this.t.tvSec).field("tv_nsec", this.t.tvNsec).finish()

    public companion object {
        public val MAX: SystemTime = SystemTime(t = Timespec.MAX)

        public val MIN: SystemTime = SystemTime(t = Timespec.MIN)

        public fun new(tvSec: Long, tvNsec: Long): Result<SystemTime, Error> {
            return Result.Ok<SystemTime, Error>(SystemTime(t = when (val tryResult = Timespec.new(tvSec, tvNsec)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<SystemTime, Error>(tryResult.error) }))
        }

        public fun now(): SystemTime = SystemTime(t = Timespec.now(CLOCK_REALTIME))
    }
}

public open class Instant(
    public var t: Timespec,
) : AsInner<Timespec> {
    public open fun clone(): Instant = Instant(t)

    public open fun checkedSubInstant(other: Instant): Duration? = this.t.subTimespec(other.t).ok()

    public open fun checkedAddDuration(other: Duration): Instant? {
        return Instant(t = (this.t.checkedAddDuration(other) ?: return null))
    }

    public open fun checkedSubDuration(other: Duration): Instant? {
        return Instant(t = (this.t.checkedSubDuration(other) ?: return null))
    }

    public open fun intoTimespec(): Timespec = this.t

    /**
     * Returns `self` converted into units of `machAbsoluteTime`, or `None`
     * if `self` is before the system boot time. If the conversion cannot be
     * performed precisely, this ceils the result up to the nearest
     * representable value.
     */
    public open fun intoMachAbsoluteTimeCeil(): U128? {
        open class MachTimebaseInfo(
            public var numer: UInt,
            public var denom: UInt,
        )
        fun machTimebaseInfo(info: MutPtr): KernReturnT = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<KernReturnT>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<KernReturnT>()).alignBytes)); val res = (UnresolvedTypeLayout<KernReturnT>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<KernReturnT>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<KernReturnT>()).alignBytes)); res }
        val secs = (ULong.tryFrom(this.t.tvSec).ok() ?: return null)
        val timebase = MachTimebaseInfo(numer = 0u, denom = 0u)
        check(machTimebaseInfo(timebase) == KERN_SUCCESS)
        // Since `tvSec` is 64-bit and `tvNsec` is smaller than 1 billion,
        // this cannot overflow. The resulting number needs at most 94 bits.
        val nanos = 1000000000 * U128.from(secs) + U128.from(this.t.tvNsec.asInner())
        // This multiplication cannot overflow since multiplying a 94-bit
        // number by a 32-bit number yields a number that needs at most
        // 126 bits.
        return (nanos * U128.from(timebase.denom)).divCeil(U128.from(timebase.numer))
    }

    public override fun asInner(): Timespec = this.t

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "Instant").field("tv_sec", this.t.tvSec).field("tv_nsec", this.t.tvNsec).finish()

    public companion object {
        // CLOCK_UPTIME_RAW   clock that increments monotonically, in the same man-
        // ner as CLOCK_MONOTONIC_RAW, but that does not incre-
        // ment while the system is asleep.  The returned value
        // is identical to the result of machAbsoluteTime()
        // after the appropriate machTimebase conversion is
        // applied.
        //
        // We use `CLOCK_UPTIME_RAW` instead of `CLOCK_MONOTONIC` since
        // `CLOCK_UPTIME_RAW` is based on `machAbsoluteTime`, which is the
        // clock that all timeouts and deadlines are measured against inside
        // the kernel.
        public val CLOCK_ID: ClockidT = CLOCK_UPTIME_RAW

        public val CLOCK_ID1: ClockidT = CLOCK_MONOTONIC

        public fun now(): Instant {
            // https://pubs.opengroup.org/onlinepubs/9799919799/functions/clockGetres.html
            return Instant(t = Timespec.now(Instant.CLOCK_ID))
        }
    }
}
