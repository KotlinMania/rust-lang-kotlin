// port-lint: source math/fmaf16.rs
package io.github.kotlinmania.rustlang.libm.math.fmaf16

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf32
import io.github.kotlinmania.rustlang.core.num.f16.isSignNegative
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.libm.math.support.macros.cfgSelectNofmt
import io.github.kotlinmania.rustlang.libm.math.support.unboundedShrU64

/**
 * We use a a U21.43 fixed-point representation when needed.
 */
public val FIXED_FRAC_BITS: UInt = 43u

/**
 * Floating multiply add (f16)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fmaf16(x: F16, y: F16, z: F16): F16 {
    val ix = x.toBits() and F16.SIGN_MASK.inv()
    val iy = y.toBits() and F16.SIGN_MASK.inv()
    val iz = z.toBits() and F16.SIGN_MASK.inv()
    val xneg = x.isSignNegative()
    val yneg = y.isSignNegative()
    val zneg = z.isSignNegative()
    val mneg = xneg xor yneg
    if (ix == 0u.toUShort() || ix >= F16.EXP_MASK.toUShort() || iy == 0u.toUShort() || iy >= F16.EXP_MASK.toUShort()) {
        // Value will overflow, defer to non-fused operations.
        return x * y + z
    }
    if (iz == 0u.toUShort()) {
        // Empty add component means we only need to multiply.
        return x * y
    }
    if (iz >= F16.EXP_MASK.toUShort()) {
        // `z` is NaN or infinity, which sets the result.
        return z
    }
    var xexp = x.ex()
    var yexp = y.ex()
    var zexp = z.ex()
    var xsig = ix and F16.SIG_MASK.toUShort()
    var ysig = iy and F16.SIG_MASK.toUShort()
    var zsig = iz and F16.SIG_MASK.toUShort()
    // If not subnormal, set the implicit bit
    if (xexp != 0) {
        xsig = xsig or F16.IMPLICIT_BIT.toUShort()
    }
    if (yexp != 0) {
        ysig = ysig or F16.IMPLICIT_BIT.toUShort()
    }
    if (zexp != 0) {
        zsig = zsig or F16.IMPLICIT_BIT.toUShort()
    }
    // A biased exponent of 1 (min normal) and 0 (subnormal) have the same real exponent, so
    // adjust for this. Bias is now 14 rather than 15.
    xexp = xexp.saturatingSub(1)
    yexp = yexp.saturatingSub(1)
    zexp = zexp.saturatingSub(1)
    val adjbias = F16.EXP_BIAS - F16.fromFloat(1.0f)
    // Exponent after multiplication. Bias doubles to 28.
    val mexp = xexp + yexp
    val mbias = adjbias * F16.fromFloat(2.0f)
    // Exit now if we know the result will overflow. We need to keep one beyond the infinite
    // exponent in case the addition rounds down to a finite number.
    //
    // Note that `EXP_MAX` (i.e. max finite) represents infinity here because our values are
    // acting with a bias of 14.
    val infExp = mbias + F16.EXP_MAX.unsigned()
    if (mexp > infExp + F16.fromFloat(1.0f)) {
        if (mneg) {
            return F16.NEG_INFINITY
        } else {
            return F16.INFINITY
        }
    }
    // Multiplication moves the explicit 1 from the 11th bit to the 22nd bit.
    val m = UInt.from(xsig) * UInt.from(ysig)
    var m64 = ULong.from(m)
    // The entire dynamic range of an `f16` fits into a `ULong`. Shift based on the exponent to
    // create a U21.43 fixed-point value. At the maximum exponent, there are five zeros before
    // the explicit leading 1 (intentional so this truncates to the final repr).
    when (val matchVal = mexp.checkedSub(5)) {
        null -> {
            // The lower few bits here would be on the order of 2^-43, which is too small to show up
            // in a result significand. Just squash them to a sticky bit.
            val sticky = m64 and 0x1F != 0
            m64 = m64 shr ((5 - mexp).toInt())
            m64 = m64 or ULong.from(sticky)
        }
        else -> {
            val mshift = matchVal
            cfgSelectNofmt(feature, "compiler-builtins", debugAssert, unboundedShrU64, m64, 64, mshift, 0, "data shifted out", debugAssertEq, unboundedShrU64, m64, 64, mshift, 0, "data shifted out {m} {mshift}")
            m64 = m64 shl mshift.toInt()
        }
    }
    // Shift z to U21.43 as well.
    val zshift = zexp + FIXED_FRAC_BITS - F16.SIG_BITS.toUInt() - adjbias.toUInt()
    val z64 = ULong.from(zsig) shl zshift.toInt()
    val sub = mneg xor zneg
    val rneg: Any?
    val r64 =
        if (sub) {
            if (m64 > z64) {
                rneg = mneg
                m64.wrappingSub(z64)
            } else if (m64 == z64) {
                rneg = false
                m64.wrappingSub(z64)
            } else {
                rneg = zneg
                z64.wrappingSub(m64)
            }
        } else {
            rneg = mneg
            m64 + z64
        }
    val sign =
        if (rneg) {
            -1.0
        } else {
            1.0
        }
    return F16.fromFloat(kmCopysignf32(f16FromU2143(r64).toFloat(), (F16.fromDouble(sign.toDouble())).toFloat()))
}

/**
 * Turn a U21.43 value into an f16 with positive sign.
 */
public fun f16FromU2143(r64: ULong): F16 {
    var r641 = r64
    val extraBits = 64 - 16
    val maxFiniteLz = F16.fromFloat(64.0f) - F16.SIG_BITS - extraBits - F16.fromFloat(1.0f)
    // 5
    // Check for overflow to infinity after addition, return before checking lz.
    if (r641 and (ULong.MAX_VALUE shl (F16.fromFloat(64.0f) - maxFiniteLz).toInt()) != 0uL) {
        return F16.INFINITY
    }
    // Shift the fixed point to floating point. There are 5 leading zeros before the largest
    // finite value's explicit one.
    //
    // We want `rexp` as one less than the actual value to be stored because it gets added to
    // a value with the leading one set. This value and the shift are clamped so subnormals
    // don't become normalized.
    val expMaxBiasedM1 = F16.EXP_MAX.unsigned() + F16.EXP_BIAS - F16.fromFloat(1.0f)
    // 29
    val lz = r641.leadingZeros()
    val rexp = (expMaxBiasedM1 + maxFiniteLz).saturatingSub(lz)
    val shift = expMaxBiasedM1 - rexp
    r641 = r641 shl shift.toInt()
    // Round up if the round bit (one past significand end) is set and any trailing bit is set,
    // or if the preceding bit is set.
    val roundBit = 1uL shl (extraBits - 1).toInt()
    val upMask = ((1uL shl (extraBits + 1).toInt()) - 1uL) and roundBit.inv()
    val roundUp = r641 and roundBit != 0uL && r641 and upMask != 0uL
    val roundUp1 = UShort.from(roundUp)
    // Truncate then round. Automatically accounts for subnormals with the unset explicit decimal
    // bit, since `rexp` is one less than the actual biased value.
    var r = (r641 shr extraBits.toInt()).toUShort()
    r += UShort.castFrom(rexp) shl F16.SIG_BITS.toInt()
    r += roundUp1
    return F16.fromBits(r)
}
