// port-lint: source math/support/env.rs
package io.github.kotlinmania.rustlang.libm.math.support.env

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

// Support for rounding directions and status flags as specified by IEEE 754.
//
// Rust does not support the floating point environment so rounding mode is passed as an argument
// and status flags are returned as part of the result. There is currently not much support for
// this; most existing ports from musl use a form of `forceEval` to raise exceptions, but this
// has no side effects in Rust. Further, correct behavior relies on elementary operations making
// use of the correct rounding and raising relevant exceptions, which is not the case for Rust.
//
// This module exists so no functionality is lost when porting algorithms that respect floating
// point environment, and so that some functionality may be tested (that which does not rely on
// side effects from elementary operations). Full support would require wrappers around basic
// operations, but there is no plan to add this at the current time.

/**
 * A value combined with a floating point status.
 */
public open class FpResult<T>(
    public var value: T,
    public var status: Status,
) {
    public companion object {
        public fun <T> new(value: T, status: Status): FpResult<T> = FpResult(value = value, status = status)

        /**
         * Return `val` with `Status.OK`.
         */
        public fun <T> ok(value: T): FpResult<T> = FpResult(value = value, status = Status.OK)
    }
}

/**
 * IEEE 754 rounding mode, excluding the optional `roundTiesToAway` version of nearest.
 *
 * Integer representation comes from what CORE-MATH uses for indexing.
 */
public sealed class Round(
    public val value: Int,
) {
    /**
     * IEEE 754 nearest, `roundTiesToEven`.
     */
    public data object Nearest : Round(0)
    /**
     * IEEE 754 `roundTowardNegative`.
     */
    public data object Negative : Round(1)
    /**
     * IEEE 754 `roundTowardPositive`.
     */
    public data object Positive : Round(2)
    /**
     * IEEE 754 `roundTowardZero`.
     */
    public data object Zero : Round(3)

    public fun rustEquals(other: Round): Boolean = this == other

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value

    public fun toUInt(): UInt = value.toUInt()

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()
}

/**
 * IEEE 754 exception status flags.
 */
public open class Status(
    public var value: UByte,
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Status = Status(value)

    /**
     * True if `UNDERFLOW` is set.
     */
    public open fun underflow(): Boolean = this.value and Status.UNDERFLOW.first != 0u.toUByte()

    /**
     * True if `OVERFLOW` is set.
     */
    public open fun overflow(): Boolean = this.value and Status.OVERFLOW.first != 0u.toUByte()

    public open fun setUnderflow(value: Boolean) {
        this.setFlag(value, Status.UNDERFLOW)
    }

    /**
     * True if `INEXACT` is set.
     */
    public open fun inexact(): Boolean = this.value and Status.INEXACT.first != 0u.toUByte()

    public open fun setInexact(value: Boolean) {
        this.setFlag(value, Status.INEXACT)
    }

    public open fun setFlag(value: Boolean, mask: Status) {
        if (value) {
            this.value = this.value or mask.value
        } else {
            this.value = this.value and mask.value.inv()
        }
    }

    public open fun with(rhs: Status): Status = Status(this.value or rhs.value)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        // shift -> flag map
        val names = listOf("INVALID", "DIVIDE_BY_ZERO", "OVERFLOW", "UNDERFLOW", "INEXACT")
        f.append("Status(")
        var any = false
        for (shift in 0 until 8u) {
            if (this.value and (((1u.toUByte()).toUInt() shl (shift.toInt())).toUByte()) != 0u.toUByte()) {
                if (any) {
                    f.append(" | ")
                }
                when (val matchVal = names.get(shift.toInt())) {
                    null -> f.append("UNKNOWN(1 << $shift)")
                    else -> {
                        val name = matchVal
                        f.append("$name")
                    }
                }
                any = true
            }
        }
        if (!any) {
            f.append("OK")
        }
        f.append(")")
        return Result.Ok<Unit, Error>(Unit)
    }

    public companion object {
        // Note that if we ever store/load this to/from floating point control status registers, it
        // may be worth making these values platform-dependent to line up with register layout
        // to avoid bit swapping. For the time being, this isn't a concern.

        /**
         * Default status indicating no errors.
         */
        public val OK: Status = Status(0)

        /**
         * No definable result.
         *
         * Includes:
         * - Any ops on sNaN, with a few exceptions.
         * - `0 * inf`, `inf * 0`.
         * - `fma(0, inf, c)` or `fma(inf, 0, c)`, possibly excluding `c = qNaN`.
         * - `+ inf + - inf` and similar (includes subtraction and fma).
         * - `0.0 / 0.0`, `inf / inf`
         * - `remainder(x, y)` if `y == 0.0` or `x == inf`, and neither is NaN.
         * - `sqrt(x)` with `x<0.0`.
         */
        public val INVALID: Status = Status(1)

        /**
         * Division by zero.
         *
         * The default result for division is +/-inf based on operand sign. For `logB`, the default
         * result is -inf.
         * `x / y` when `x != 0.0` and `y == 0.0`,
         */
        public val DIVIDE_BY_ZERO: Status = Status(1 shl 1)

        /**
         * The result exceeds the maximum finite value.
         *
         * The default result depends on rounding mode. `Nearest *` rounds to +/- infinity, sign based
         * on the intermediate result. `Zero` rounds to the signed maximum finite. `Positive` and
         * `Negative` round to signed maximum finite in one direction, signed infinity in the other.
         */
        public val OVERFLOW: Status = Status(1 shl 2)

        /**
         * The result is subnormal and lost precision.
         */
        public val UNDERFLOW: Status = Status(1 shl 3)

        /**
         * The finite-precision result does not match that of infinite precision, and the reason
         * is not represented by one of the other flags.
         */
        public val INEXACT: Status = Status(1 shl 4)
    }
}
