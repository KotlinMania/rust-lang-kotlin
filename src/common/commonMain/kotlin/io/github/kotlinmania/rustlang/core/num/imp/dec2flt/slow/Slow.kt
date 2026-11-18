// port-lint: source num/imp/dec2flt/slow.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.slow

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common.BiasedFp
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.decimalseq.DecimalSeq
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.decimalseq.parseDecimalSeq
import io.github.kotlinmania.rustlang.core.num.imp.traits.Float

// Slow, fallback algorithm for cases the Eisel-Lemire algorithm cannot round.

/**
 * Parse the significant digits and biased, binary exponent of a float.
 *
 * This is a fallback algorithm that uses a big-integer representation
 * of the float, and therefore is considerably slower than faster
 * approximations. However, it will always determine how to round
 * the significant digits to the nearest machine float, allowing
 * use to handle near half-way cases.
 *
 * Near half-way cases are halfway between two consecutive machine floats.
 * For example, the float `16777217.0` has a bitwise representation of
 * `100000000000000000000000 1`. Rounding to a single-precision float,
 * the trailing `1` is truncated. Using round-nearest, tie-even, any
 * value above `16777217.0` must be rounded up to `16777218.0`, while
 * any value before or equal to `16777217.0` must be rounded down
 * to `16777216.0`. These near-halfway conversions therefore may require
 * a large number of digits to unambiguously determine how to round.
 *
 * The algorithms described here are based on "Processing Long Numbers Quickly",
 * available here: <https://arxiv.org/pdf/2101.11408.pdf#section.11>.
 */
public fun <Int, F : Float<F, Int>> parseLongMantissa(s: List<UByte>): BiasedFp {
    val MAX_SHIFT: kotlin.Int = 60
    val NUM_POWERS: kotlin.Int = 19
    val POWERS: UByteArray = ubyteArrayOf(0u.toUByte(), 3u.toUByte(), 6u.toUByte(), 9u.toUByte(), 13u.toUByte(), 16u.toUByte(), 19u.toUByte(), 23u.toUByte(), 26u.toUByte(), 29u.toUByte(), 33u.toUByte(), 36u.toUByte(), 39u.toUByte(), 43u.toUByte(), 46u.toUByte(), 49u.toUByte(), 53u.toUByte(), 56u.toUByte(), 59u.toUByte())
    val getShift =
        { n ->
            if (n < NUM_POWERS) {
                ((POWERS[n] as Any?) as kotlin.Int)
            } else {
                MAX_SHIFT
            }
        }
    val fpZero = BiasedFp.zeroPow2(0)
    val fpInf = BiasedFp.zeroPow2(F.INFINITE_POWER)
    val d = parseDecimalSeq(s)
    // Short-circuit if the value can only be a literal 0 or infinity.
    if (d.numDigits == 0 || d.decimalPoint < -324) {
        return fpZero
    } else if (d.decimalPoint >= 310) {
        return fpInf
    }
    var exp2 = 0
    // Shift right toward (1/2 ... 1].
    while (d.decimalPoint > 0) {
        val n = ((d.decimalPoint as Any?) as kotlin.Int)
        val shift = getShift(n)
        d.rightShift(shift)
        if (d.decimalPoint < -DecimalSeq.DECIMAL_POINT_RANGE) {
            return fpZero
        }
        exp2 += ((shift as Any?) as kotlin.Int)
    }
    // Shift left toward (1/2 ... 1].
    while (d.decimalPoint <= 0) {
        val shift1 =
            if (d.decimalPoint == 0) {
                run {
                    val matchVal = d.digits[0]
                    when {
                        matchVal >= 5 -> {
                            val digit = matchVal
                            break
                        }
                        matchVal == 0u.toUByte() || matchVal == 1u.toUByte() -> 2
                        else -> 1
                    }
                }
            } else {
                getShift((-d.decimalPoint))
            }
        d.leftShift(shift1)
        if (d.decimalPoint > DecimalSeq.DECIMAL_POINT_RANGE) {
            return fpInf
        }
        exp2 -= ((shift1 as Any?) as kotlin.Int)
    }
    // We are now in the range [1/2 ... 1] but the binary format uses [1 ... 2].
    exp2 -= 1
    while (F.EXP_MIN > exp2) {
        var n1 = (((F.EXP_MIN - exp2) as Any?) as kotlin.Int)
        if ((kmCompareValues(n1, MAX_SHIFT) > 0)) {
            n1 = MAX_SHIFT
        }
        d.rightShift(n1)
        exp2 += ((n1 as Any?) as kotlin.Int)
    }
    if ((exp2 - F.EXP_MIN + 1) >= F.INFINITE_POWER) {
        return fpInf
    }
    // Shift the decimal to the hidden bit, and then round the value
    // to get the high mantissa+1 bits.
    d.leftShift(((F.SIG_BITS as Any?) as kotlin.Int) + 1)
    var mantissa = d.round()
    if (mantissa >= (1uL shl (F.SIG_BITS + 1).toInt())) {
        // Rounding up overflowed to the carry bit, need to
        // shift back to the hidden bit.
        d.rightShift(1)
        exp2 += 1
        mantissa = d.round()
        if ((exp2 - F.EXP_MIN + 1) >= F.INFINITE_POWER) {
            return fpInf
        }
    }
    var power2 = exp2 - F.EXP_MIN + 1
    if (mantissa < (1uL shl F.SIG_BITS.toInt())) {
        power2 -= 1
    }
    // Zero out all the bits above the explicit mantissa bits.
    mantissa = mantissa and ((1uL shl F.SIG_BITS.toInt()) - 1uL)
    return BiasedFp(m = mantissa, pBiased = power2)
}
