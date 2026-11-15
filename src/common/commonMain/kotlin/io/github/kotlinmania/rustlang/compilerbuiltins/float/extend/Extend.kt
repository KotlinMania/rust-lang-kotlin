// port-lint: source float/extend.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.extend

import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.wrappingShl

/**
 * Generic conversion from a narrower to a wider IEEE-754 floating-point type
 */
public fun <F : Float, R : Float> extend(a: F, fromBitsRFn: (Int) -> R): R {
    val srcZero = F.Int.ZERO
    val srcOne = F.Int.ONE
    val srcBits = F.BITS
    val srcSigBits = F.SIG_BITS
    val srcExpBias = F.EXP_BIAS
    val srcMinNormal = F.IMPLICIT_BIT
    val srcInfinity = F.EXP_MASK
    val srcSignMask = F.SIGN_MASK
    val srcAbsMask = srcSignMask - srcOne
    val srcQnan = F.SIG_MASK
    val srcNanCode = srcQnan - srcOne
    val dstBits = R.BITS
    val dstSigBits = R.SIG_BITS
    val dstInfExp = R.EXP_SAT
    val dstExpBias = R.EXP_BIAS
    val dstMinNormal = R.IMPLICIT_BIT
    val sigBitsDelta = dstSigBits - srcSigBits
    val expBiasDelta = dstExpBias - srcExpBias
    val aAbs = a.toBits() and srcAbsMask
    var absResult = R.Int.ZERO
    if ((aAbs - (srcMinNormal.toULong())) < srcInfinity.wrappingSub(srcMinNormal)) {
        // a is a normal number.
        // Extend to the destination type by shifting the significand and
        // exponent into the proper position and rebiasing the exponent.
        val absDst: Int = aAbs.cast().toInt()
        val biasDst: Int = expBiasDelta.cast()
        absResult = absDst.wrappingShl(sigBitsDelta.toUInt())
        absResult += biasDst.wrappingShl(dstSigBits.toUInt())
    } else if (aAbs >= srcInfinity) {
        // a is NaN or infinity.
        // Conjure the result by beginning with infinity, then setting the qNaN
        // bit (if needed) and right-aligning the rest of the trailing NaN
        // payload field.
        val qnanDst: Int = (aAbs and srcQnan).cast()
        val nanCodeDst: Int = (aAbs and srcNanCode).cast()
        val infExpDst: Int = dstInfExp.cast()
        absResult = infExpDst.wrappingShl(dstSigBits.toUInt())
        absResult = absResult or qnanDst.wrappingShl(sigBitsDelta.toUInt())
        absResult = absResult or nanCodeDst.wrappingShl(sigBitsDelta.toUInt())
    } else if (aAbs != srcZero) {
        // a is denormal.
        // Renormalize the significand and clear the leading bit, then insert
        // the correct adjusted exponent in the destination type.
        val scale = aAbs.leadingZeros() - srcMinNormal.leadingZeros()
        val absDst1: Int = aAbs.cast().toInt()
        val biasDst1: Int = (expBiasDelta - scale + 1u).cast()
        absResult = absDst1.wrappingShl(sigBitsDelta + scale)
        absResult = (absResult xor dstMinNormal) or (biasDst1.wrappingShl(dstSigBits.toUInt()))
    }
    val signResult: Int = (a.toBits() and srcSignMask).cast()
    return fromBitsRFn(absResult or (signResult.wrappingShl((dstBits - srcBits).toUInt())))
}

public fun extendsfdf2(a: Float): Double = extend(a)

public fun extendhfsf2(a: F16): Float = extend(a)

public fun gnuH2fIeee(a: UShort): UInt = (extend<F16, Float>(F16.fromBits(a)).toBits()).toUInt()

public fun extendhfdf2(a: F16): Double = extend(a)

public fun extendhftf2(a: F16): F128 = extend(a)

public fun extendhfkf2(a: F16): F128 = extend(a)

public fun extendsftf2(a: Float): F128 = extend(a)

public fun extendsfkf2(a: Float): F128 = extend(a)

public fun extenddftf2(a: Double): F128 = extend(a)

public fun extenddfkf2(a: Double): F128 = extend(a)
