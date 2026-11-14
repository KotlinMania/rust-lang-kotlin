// port-lint: source float/trunc.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.trunc

import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.rustWrappingShr

public fun <F : Float, R : Float> trunc(a: F, fromBitsRFn: (Int) -> R): R {
    val srcZero = F.Int.ZERO
    val srcOne = F.Int.ONE
    val srcBits = F.BITS
    val srcExpBias = F.EXP_BIAS
    val srcMinNormal = F.IMPLICIT_BIT
    val srcSigMask = F.SIG_MASK
    val srcInfinity = F.EXP_MASK
    val srcSignMask = F.SIGN_MASK
    val srcAbsMask = srcSignMask - srcOne
    val roundMask = (srcOne shl (F.SIG_BITS - R.SIG_BITS).toInt()) - srcOne
    val halfway = srcOne shl (F.SIG_BITS - R.SIG_BITS - 1).toInt()
    val srcQnan = srcOne shl (F.SIG_BITS - 1).toInt()
    val srcNanCode = srcQnan - srcOne
    val dstZero = R.Int.ZERO
    val dstOne = R.Int.ONE
    val dstBits = R.BITS
    val dstInfExp = R.EXP_SAT
    val dstExpBias = R.EXP_BIAS
    val underflowExponent: Int = (srcExpBias + 1 - dstExpBias).cast()
    val overflowExponent: Int = (srcExpBias + dstInfExp - dstExpBias).cast()
    val underflow: Int = underflowExponent shl F.SIG_BITS.toInt()
    val overflow: Int = overflowExponent shl F.SIG_BITS.toInt()
    val dstQnan = R.Int.ONE shl (R.SIG_BITS - 1).toInt()
    val dstNanCode = dstQnan - dstOne
    val sigBitsDelta = F.SIG_BITS - R.SIG_BITS
    // Break a into a sign and representation of the absolute value.
    val aAbs = a.toBits() and srcAbsMask
    val sign = a.toBits() and srcSignMask
    var absResult: Int
    if ((aAbs - (underflow.toULong())) < (aAbs - (overflow.toULong()))) {
        // The exponent of a is within the range of normal numbers in the
        // destination format.  We can convert by simply right-shifting with
        // rounding and adjusting the exponent.
        absResult = (aAbs shr sigBitsDelta.toInt()).castLossy()
        // Cast before shifting to prevent overflow.
        val biasDiff: Int = srcExpBias.wrappingSub(dstExpBias).cast()
        val tmp = biasDiff shl R.SIG_BITS.toInt()
        absResult = (absResult - (tmp.toInt()))
        val roundBits = aAbs and roundMask
        if (roundBits > halfway) {
            // Round to nearest.
            absResult += dstOne
        } else if (roundBits == halfway) {
            // Tie to even.
            absResult += absResult and dstOne
        }
    } else if (aAbs > srcInfinity) {
        // a is NaN.
        // Conjure the result by beginning with infinity, setting the qNaN
        // bit and inserting the (truncated) trailing NaN field.
        // Cast before shifting to prevent overflow.
        val dstInfExp1: Int = dstInfExp.cast()
        absResult = dstInfExp1 shl R.SIG_BITS.toInt()
        absResult = absResult or dstQnan
        absResult = absResult or (dstNanCode and ((aAbs and srcNanCode) shr (F.SIG_BITS - R.SIG_BITS).toInt()).cast())
    } else if (aAbs >= overflow.toULong()) {
        // a overflows to infinity.
        // Cast before shifting to prevent overflow.
        val dstInfExp2: Int = dstInfExp.cast()
        absResult = dstInfExp2 shl R.SIG_BITS.toInt()
    } else {
        // a underflows on conversion to the destination type or is an exact
        // zero.  The result may be a denormal or zero.  Extract the exponent
        // to get the shift amount for the denormalization.
        val aExp: UInt = (aAbs shr F.SIG_BITS.toInt()).cast()
        val shift = srcExpBias - dstExpBias - aExp + 1u
        val significand = (a.toBits() and srcSigMask) or srcMinNormal
        // Right shift by the denormalization amount with sticky.
        if (shift > F.SIG_BITS) {
            absResult = dstZero
        } else {
            val sticky =
                if ((significand shl (srcBits - shift).toInt()) != srcZero) {
                    srcOne
                } else {
                    srcZero
                }
            val denormalizedSignificand: Int = (significand shr shift.toInt()) or sticky
            absResult = (denormalizedSignificand shr (F.SIG_BITS - R.SIG_BITS).toInt()).cast()
            val roundBits1 = denormalizedSignificand and roundMask
            // Round to nearest
            if (roundBits1 > halfway) {
                absResult += dstOne
            } else if (roundBits1 == halfway) {
                absResult += absResult and dstOne
            }
        }
    }
    // Apply the signbit to the absolute value.
    return fromBitsRFn(absResult or rustWrappingShr(sign, (srcBits - dstBits).toUInt()).cast().toInt())
}

public fun truncdfsf2(a: Double): Float = trunc(a)

public fun truncsfhf2(a: Float): F16 = trunc(a)

public fun gnuF2hIeee(a: UInt): UShort = (trunc<Float, F16>(Float.fromBits(a.toInt())).toBits()).toUShort()

public fun truncdfhf2(a: Double): F16 = trunc(a)

public fun trunctfhf2(a: F128): F16 = trunc(a)

public fun trunckfhf2(a: F128): F16 = trunc(a)

public fun trunctfsf2(a: F128): Float = trunc(a)

public fun trunckfsf2(a: F128): Float = trunc(a)

public fun trunctfdf2(a: F128): Double = trunc(a)

public fun trunckfdf2(a: F128): Double = trunc(a)
