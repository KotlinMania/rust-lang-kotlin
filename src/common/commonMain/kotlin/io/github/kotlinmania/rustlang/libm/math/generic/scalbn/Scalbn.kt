// port-lint: source math/generic/scalbn.rs
package io.github.kotlinmania.rustlang.libm.math.generic.scalbn

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy

/**
 * Scale the exponent.
 *
 * From N3220:
 *
 * > The scalbn and scalbln functions compute `x * b ^ n`, where `b = FLT_RADIX` if the return type
 * > of the function is a standard floating type, or `b = 10` if the return type of the function
 * > is a decimal floating type. A range error occurs for some finite x, depending on n.
 * >
 * > [...]
 * >
 * > * `scalbn(±0, n)` returns `±0`.
 * > * `scalbn(x, 0)` returns `x`.
 * > * `scalbn(±∞, n)` returns `±∞`.
 * >
 * > If the calculation does not overflow or underflow, the returned value is exact and
 * > independent of the current rounding direction mode.
 */
public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> scalbn(x: F, n: kotlin.Int, fromPartsFFn: (Boolean, UInt, Int) -> F): F {
    var x1 = x
    var n1 = n
    val zero = IntTy.ZERO
    // Bits including the implicit bit
    val sigTotalBits = x.SIG_BITS + 1
    // Maximum and minimum values when biased
    val expMax = x.EXP_MAX
    val expMin = x.EXP_MIN
    // 2 ^ Emax, maximum positive with null significand (0x1p1023 for f64)
    val fExpMax = fromPartsFFn(false, x.EXP_BIAS shl 1, zero)
    // 2 ^ Emin, minimum positive normal with null significand (0x1p-1022 for f64)
    val fExpMin = fromPartsFFn(false, 1, zero)
    // 2 ^ sigTotalBits, moltiplier to normalize subnormals (0x1p53 for f64)
    val fPowSubnorm = fromPartsFFn(false, sigTotalBits + x.EXP_BIAS, zero)
    //
    // The goal is to multiply `x` by a scale factor that applies `n`. However, there are cases
    // where `2 ^ n` is not representable by `F` but the result should be, e.g. `x = 2 ^ Emin` with
    // `n = - EMin + 2` (one out of range of 2^Emax). To get around this, reduce the magnitude of
    // the final scale operation by prescaling by the max/min power representable by `F`.
    //
    if (n1 > expMax) {
        // Worse case positive `n`: `x`  is the minimum subnormal value, the result is `F.MAX`.
        // This can be reached by three scaling multiplications (two here and one final).
        check(-expMin + ((x.SIG_BITS as Any?) as kotlin.Int) + expMax <= expMax * 3)
        x1 *= fExpMax
        n1 -= expMax
        if (n1 > expMax) {
            x1 *= fExpMax
            n1 -= expMax
            if (n1 > expMax) {
                n1 = expMax
            }
        }
    } else if (n1 < expMin) {
        // When scaling toward 0, the prescaling is limited to a value that does not allow `x` to
        // go subnormal. This avoids double rounding.
        if (x.BITS > 16) {
            // `mul` s.t. `!(x * mul).isSubnormal() ∀ x`
            val mul = fExpMin * fPowSubnorm
            val add = -expMin - ((sigTotalBits as Any?) as kotlin.Int)
            // Worse case negative `n`: `x`  is the maximum positive value, the result is `F.MIN`.
            // This must be reachable by three scaling multiplications (two here and one final).
            check(-expMin + ((x.SIG_BITS as Any?) as kotlin.Int) + expMax <= add * 2 + -expMin)
            x1 *= mul
            n1 += add
            if (n1 < expMin) {
                x1 *= mul
                n1 += add
                if (n1 < expMin) {
                    n1 = expMin
                }
            }
        } else {
            // `f16` is unique compared to other float types in that the difference between the
            // minimum exponent and the significand bits (`add = - expMin - sigTotalBits`) is
            // small, only three. The above method depend on decrementing `n` by `add` two times;
            // for other float types this works out because `add` is a substantial fraction of
            // the exponent range. For `f16`, however, 3 is relatively small compared to the
            // exponent range (which is 39), so that requires ~10 prescale rounds rather than two.
            //
            // Work aroudn this by using a different algorithm that calculates the prescale
            // dynamically based on the maximum possible value. This adds more operations per round
            // since it needs to construct the scale, but works better in the general case.
            val add1 = -(n1 + ((sigTotalBits as Any?) as kotlin.Int)).max(expMin)
            val mul1 = fromPartsFFn(false, (((x.EXP_BIAS as Any?) as kotlin.Int) - add1).toUInt(), zero)
            x1 *= mul1
            n1 += add1
            if (n1 < expMin) {
                val add2 = -(n1 + ((sigTotalBits as Any?) as kotlin.Int)).max(expMin)
                val mul2 = fromPartsFFn(false, (((x.EXP_BIAS as Any?) as kotlin.Int) - add2).toUInt(), zero)
                x1 *= mul2
                n1 += add2
                if (n1 < expMin) {
                    n1 = expMin
                }
            }
        }
    }
    val scale = fromPartsFFn(false, (((x.EXP_BIAS as Any?) as kotlin.Int) + n1).toUInt(), zero)
    return x1 * scale
}
