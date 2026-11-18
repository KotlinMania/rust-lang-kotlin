// port-lint: source float/mul.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.mul

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

public fun <F : Float> mul(a: F, b: F, fromBitsFFn: (ULong) -> F, normalizeFFn: (ULong) -> Any?): F {
    val one = F.Int.ONE
    val zero = F.Int.ZERO
    val bits = a.BITS
    val significandBits = a.SIG_BITS
    val maxExponent = a.EXP_SAT
    val exponentBias = a.EXP_BIAS
    val implicitBit = a.IMPLICIT_BIT
    val significandMask = a.SIG_MASK
    val signBit = a.SIGN_MASK
    val absMask = signBit - one
    val exponentMask = a.EXP_MASK
    val infRep = exponentMask
    val quietBit = implicitBit shr 1
    val qnanRep = exponentMask or quietBit
    val exponentBits = a.EXP_BITS
    val aRep = a.toBits()
    val bRep = b.toBits()
    val aExponent = (aRep shr significandBits.toInt()) and maxExponent.cast()
    val bExponent = (bRep shr significandBits.toInt()) and maxExponent.cast()
    val productSign = (aRep xor bRep) and signBit
    var aSignificand = aRep and significandMask
    var bSignificand = bRep and significandMask
    var scale = 0
    // Detect if a or b is zero, denormal, infinity, or NaN.
    if ((aExponent - (one.toULong())) >= (maxExponent - 1uL).cast() || (bExponent - (one.toULong())) >= (maxExponent - 1uL).cast()) {
        val aAbs = aRep and absMask
        val bAbs = bRep and absMask
        // NaN + anything = qNaN
        if (aAbs > infRep) {
            return fromBitsFFn(aRep or quietBit)
        }
        // anything + NaN = qNaN
        if (bAbs > infRep) {
            return fromBitsFFn(bRep or quietBit)
        }
        if (aAbs == infRep) {
            if (bAbs != zero) {
                // infinity * non-zero = +/- infinity
                return fromBitsFFn(aAbs or productSign)
            } else {
                // infinity * zero = NaN
                return fromBitsFFn(qnanRep)
            }
        }
        if (bAbs == infRep) {
            if (aAbs != zero) {
                // infinity * non-zero = +/- infinity
                return fromBitsFFn(bAbs or productSign)
            } else {
                // infinity * zero = NaN
                return fromBitsFFn(qnanRep)
            }
        }
        // zero * anything = +/- zero
        if (aAbs == zero) {
            return fromBitsFFn(productSign)
        }
        // anything * zero = +/- zero
        if (bAbs == zero) {
            return fromBitsFFn(productSign)
        }
        // one or both of a or b is denormal, the other (if applicable) is a
        // normal number.  Renormalize one or both of a and b, and set scale to
        // include the necessary exponent adjustment.
        if (aAbs < implicitBit) {
            val (exponent, significand) = normalizeFFn(aSignificand)
            scale += exponent
            aSignificand = significand
        }
        if (bAbs < implicitBit) {
            val (exponent1, significand1) = normalizeFFn(bSignificand)
            scale += exponent1
            bSignificand = significand1
        }
    }
    // Or in the implicit significand bit.  (If we fell through from the
    // denormal path it was already set by normalize( ), but setting it twice
    // won't hurt anything.)
    aSignificand = aSignificand or implicitBit
    bSignificand = bSignificand or implicitBit
    // Get the significand of a*b.  Before multiplying the significands, shift
    // one of them left to left-align it in the field.  Thus, the product will
    // have (exponentBits + 2) integral digits, all but two of which must be
    // zero.  Normalizing this result is just a conditional left-shift by one
    // and bumping the exponent accordingly.
    var (productLow, productHigh) = aSignificand.widenMul(bSignificand shl exponentBits.toInt()).loHi()
    val aExponentI32: Int = aExponent.cast().toInt()
    val bExponentI32: Int = bExponent.cast().toInt()
    var productExponent: Int = (((aExponentI32 + (bExponentI32.toInt())) + (scale.toInt())) - (exponentBias.toInt()))
    // Normalize the significand, adjust exponent if needed.
    if ((productHigh and implicitBit) != zero) {
        productExponent = (productExponent + 1)
    } else {
        productHigh = (productHigh shl 1) or (productLow shr (bits - 1).toInt())
        productLow = productLow shl 1
    }
    // If we have overflowed the type, return +/- infinity.
    if (productExponent >= maxExponent.toInt()) {
        return fromBitsFFn(infRep or productSign)
    }
    if (productExponent <= 0) {
        // Result is denormal before rounding
        //
        // If the result is so small that it just underflows to zero, return
        // a zero of the appropriate sign.  Mathematically there is no need to
        // handle this case separately, but we make it a special case to
        // simplify the shift logic.
        val shift: UInt = one.wrappingSub(productExponent.castLossy()).cast()
        if (shift >= bits) {
            return fromBitsFFn(productSign)
        }
        // Otherwise, shift the significand of the result so that the round
        // bit is the high bit of `productLow`.
        // Ensure one of the non-highest bits in `productLow` is set if the shifted out bit are
        // not all zero so that the result is correctly rounded below.
        val sticky = productLow shl (bits - shift).toInt() != zero
        productLow = (productHigh shl (bits - shift).toInt()) or (productLow shr shift.toInt()) or (sticky.toUInt()).cast()
        productHigh = productHigh shr shift.toInt()
    } else {
        // Result is normal before rounding; insert the exponent.
        productHigh = productHigh and significandMask
        productHigh = productHigh or (productExponent.cast() shl significandBits.toInt())
    }
    // Insert the sign of the result:
    productHigh = productHigh or productSign
    // Final rounding.  The final result may overflow to infinity, or underflow
    // to zero, but those are the correct results in those cases.  We use the
    // default IEEE-754 round-to-nearest, ties-to-even rounding mode.
    if (productLow > signBit) {
        productHigh += one
    }
    if (productLow == signBit) {
        productHigh += productHigh and one
    }
    return fromBitsFFn(productHigh)
}

public fun mulhf3(a: F16, b: F16): F16 = mul(a, b)

public fun mulsf3(a: Float, b: Float): Float = mul(a, b)

public fun muldf3(a: Double, b: Double): Double = mul(a, b)

public fun multf3(a: F128, b: F128): F128 = mul(a, b)

public fun mulkf3(a: F128, b: F128): F128 = mul(a, b)
