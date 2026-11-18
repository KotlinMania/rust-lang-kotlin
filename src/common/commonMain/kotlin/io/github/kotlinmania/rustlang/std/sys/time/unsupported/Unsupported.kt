// port-lint: source sys/time/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.time.unsupported

import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration

public open class Instant(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Instant = Instant(value)

    public open fun checkedSubInstant(other: Instant): Duration? = this.value.checkedSub(other.value)

    public open fun checkedAddDuration(other: Duration): Instant? {
        return Instant((this.value.checkedAdd(other) ?: return null))
    }

    public open fun checkedSubDuration(other: Duration): Instant? {
        return Instant((this.value.checkedSub(other) ?: return null))
    }

    public companion object {
        public fun now(): Instant {
            error("time not implemented on this platform")
        }
    }
}

public open class SystemTime(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): SystemTime = SystemTime(value)

    public open fun subTime(other: SystemTime): Result<Duration, Duration> = (this.value.checkedSub(other.value)?.let { Result.Ok<Duration, Duration>(it) } ?: Result.Err<Duration, Duration>(other.value - this.value))

    public open fun checkedAddDuration(other: Duration): SystemTime? {
        return SystemTime((this.value.checkedAdd(other) ?: return null))
    }

    public open fun checkedSubDuration(other: Duration): SystemTime? {
        return SystemTime((this.value.checkedSub(other) ?: return null))
    }

    public companion object {
        public val MAX: SystemTime = SystemTime(Duration.MAX)

        public val MIN: SystemTime = SystemTime(Duration.ZERO)

        public fun now(): SystemTime {
            error("time not implemented on this platform")
        }
    }
}

public val UNIX_EPOCH: SystemTime = SystemTime(Duration.fromSecs(0))
