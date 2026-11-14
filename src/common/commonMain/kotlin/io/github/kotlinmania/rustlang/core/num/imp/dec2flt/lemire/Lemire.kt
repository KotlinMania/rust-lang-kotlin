// port-lint: source num/imp/dec2flt/lemire.rs
package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.lemire

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common.BiasedFp
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.table.LARGEST_POWER_OF_FIVE
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.table.POWER_OF_FIVE_128
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.table.SMALLEST_POWER_OF_FIVE
import io.github.kotlinmania.rustlang.core.num.imp.traits.Float
import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros

// Implementation of the Eisel-Lemire algorithm.

/**
 * Compute w * 10^q using an extended-precision float representation.
 *
 * Fast conversion of a the significant digits and decimal exponent
 * a float to an extended representation with a binary float. This
 * algorithm will accurately parse the vast majority of cases,
 * and uses a 128-bit representation (with a fallback 192-bit
 * representation).
 *
 * This algorithm scales the exponent by the decimal exponent
 * using pre-computed powers-of-5, and calculates if the
 * representation can be unambiguously rounded to the nearest
 * machine float. Near-halfway cases are not handled here,
 * and are represented by a negative, biased binary exponent.
 *
 * The algorithm is described in detail in "Daniel Lemire, Number Parsing
 * at a Gigabyte per Second" in section 5, "Fast Algorithm", and
 * section 6, "Exact Numbers And Ties", available online:
 * <https://arxiv.org/abs/2101.11408.pdf>.
 */
public fun <Int, F : Float<F, Any?>> computeFloat(q: Long, w: ULong): BiasedFp {
    var w1 = w
    val fpZero = BiasedFp.zeroPow2(0)
    val fpInf = BiasedFp.zeroPow2(F.INFINITE_POWER)
    val fpError = BiasedFp.zeroPow2(-1)
    // Short-circuit if the value can only be a literal 0 or infinity.
    if (w1 == 0uL || q < F.SMALLEST_POWER_OF_TEN.toLong()) {
        return fpZero
    } else if (q > F.LARGEST_POWER_OF_TEN.toLong()) {
        return fpInf
    }
    // Normalize our significant digits, so the most-significant bit is set.
    val lz = rustLeadingZeros(w1)
    w1 = w1 shl lz.toInt()
    val (lo, hi) = computeProductApprox(q, w1, F.SIG_BITS.toInt() + 3)
    if (lo == 0xFFFFFFFFFFFFFFFFuL) {
        // If we have failed to approximate w x 5^-q with our 128-bit value.
        // Since the addition of 1 could lead to an overflow which could then
        // round up over the half-way point, this can lead to improper rounding
        // of a float.
        //
        // However, this can only occur if q ∈ [-27, 55]. The upper bound of q
        // is 55 because 5^55 < 2^128, however, this can only happen if 5^q > 2^64,
        // since otherwise the product can be represented in 64-bits, producing
        // an exact result. For negative exponents, rounding-to-even can
        // only occur if 5^-q < 2^64.
        //
        // For detailed explanations of rounding for negative exponents, see
        // <https://arxiv.org/pdf/2101.11408.pdf#section.9.1>. For detailed
        // explanations of rounding for positive exponents, see
        // <https://arxiv.org/pdf/2101.11408.pdf#section.8>.
        val insideSafeExponent = (q >= -27L) && (q <= 55L)
        if (!insideSafeExponent) {
            return fpError
        }
    }
    val upperbit = (hi shr 63).toInt()
    var mantissa = hi shr (upperbit + 64 - F.SIG_BITS.toInt() - 3)
    var power2 = power(q.toInt()) + upperbit - lz.toInt() - F.EXP_MIN + 1
    if (power2 <= 0) {
        if (-power2 + 1 >= 64) {
            // Have more than 64 bits below the minimum exponent, must be 0.
            return fpZero
        }
        // Have a subnormal value.
        mantissa = mantissa shr (-power2 + 1)
        mantissa += mantissa and 1
        mantissa = mantissa shr 1
        power2 = (if (mantissa >= (1uL shl F.SIG_BITS.toInt())) 1 else 0)
        return BiasedFp(m = mantissa, pBiased = power2)
    }
    // Need to handle rounding ties. Normally, we need to round up,
    // but if we fall right in between and we have an even basis, we
    // need to round down.
    //
    // This will only occur if:
    // 1. The lower 64 bits of the 128-bit representation is 0.
    // IE, 5^q fits in single 64-bit word.
    // 2. The least-significant bit prior to truncated mantissa is odd.
    // 3. All the bits truncated when shifting to mantissa bits + 1 are 0.
    //
    // Or, we may fall between two floats: we are exactly halfway.
    if (lo <= 1 && q >= F.MIN_EXPONENT_ROUND_TO_EVEN.toLong() && q <= F.MAX_EXPONENT_ROUND_TO_EVEN.toLong() && mantissa and 0x3 == 0x1 && (mantissa shl (upperbit + 64 - F.SIG_BITS.toInt() - 3)) == hi) {
        // Zero the lowest bit, so we don't round up.
        mantissa = mantissa and 1uL.inv()
    }
    // Round-to-even, then shift the significant digits into place.
    mantissa += mantissa and 1
    mantissa = mantissa shr 1
    if (mantissa >= (2uL shl F.SIG_BITS.toInt())) {
        // Rounding up overflowed, so the carry bit is set. Set the
        // mantissa to 1 (only the implicit, hidden bit is set) and
        // increase the exponent.
        mantissa = 1uL shl F.SIG_BITS.toInt()
        power2 += 1
    }
    // Zero out the hidden bit.
    mantissa = mantissa and (1uL shl F.SIG_BITS.toInt()).inv()
    if (power2 >= F.INFINITE_POWER) {
        // Exponent is above largest normal value, must be infinite.
        return fpInf
    }
    return BiasedFp(m = mantissa, pBiased = power2)
}

/**
 * Calculate a base 2 exponent from a decimal exponent.
 * This uses a pre-computed integer approximation for
 * log2(10), where 217706 / 2^16 is accurate for the
 * entire range of non-finite decimal exponents.
 */
public fun power(q: Int): Int = ((q * ((152170 + 65536).toInt())) shr 16) + 63

public fun fullMultiplication(a: ULong, b: ULong): Pair<ULong, ULong> {
    val r = (U128.fromULong(a)) * (U128.fromULong(b))
    return Pair(r.toULong(), (r shr 64).toULong())
}

// This will compute or rather approximate w * 5**q and return a pair of 64-bit words
// approximating the result, with the "high" part corresponding to the most significant
// bits and the low part corresponding to the least significant bits.
public fun computeProductApprox(q: Long, w: ULong, precision: Int): Pair<ULong, ULong> {
    if (false) {
        check(q >= SMALLEST_POWER_OF_FIVE.toLong())
    }
    if (false) {
        check(q <= LARGEST_POWER_OF_FIVE.toLong())
    }
    if (false) {
        check(precision <= 64)
    }
    val mask =
        if (precision < 64) {
            0xFFFFFFFFFFFFFFFFuL shr precision
        } else {
            0xFFFFFFFFFFFFFFFFuL
        }
    // 5^q < 2^64, then the multiplication always provides an exact value.
    // That means whenever we need to round ties to even, we always have
    // an exact value.
    val index = (q - SMALLEST_POWER_OF_FIVE.toLong()).toInt()
    val (lo5, hi5) = POWER_OF_FIVE_128[index]
    // Only need one multiplication as long as there is 1 zero but
    // in the explicit mantissa bits, +1 for the hidden bit, +1 to
    // determine the rounding direction, +1 for if the computed
    // product has a leading zero.
    var (firstLo, firstHi) = fullMultiplication(w, lo5)
    if (firstHi and mask == mask) {
        // Need to do a second multiplication to get better precision
        // for the lower product. This will always be exact
        // where q is < 55, since 5^55 < 2^128. If this wraps,
        // then we need to round up the hi product.
        val (_, secondHi) = fullMultiplication(w, hi5)
        firstLo = firstLo.wrappingAdd(secondHi)
        if (secondHi > firstLo) {
            firstHi += 1
        }
    }
    return Pair(firstLo, firstHi)
}
