// port-lint: source float/add.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.add

import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.compilerbuiltins.support.MinInt
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.leadingZeros

/**
 * Returns `a + b`
 */
public fun <F : Float> add(a: F, b: F, fromBitsFFn: (ULong) -> F, normalizeFFn: (ULong) -> Any?): F {
    val one = F.Int.ONE
    val zero = F.Int.ZERO
    val bits: Int = F.BITS.cast()
    val significandBits = F.SIG_BITS
    val maxExponent = F.EXP_SAT
    val implicitBit = F.IMPLICIT_BIT
    val significandMask = F.SIG_MASK
    val signBit = F.SIGN_MASK.toInt()
    val absMask = signBit - one
    val exponentMask = F.EXP_MASK
    val infRep = exponentMask
    val quietBit = implicitBit shr 1
    val qnanRep = exponentMask or quietBit
    var aRep = a.toBits()
    var bRep = b.toBits()
    val aAbs = aRep and absMask.toULong()
    val bAbs = bRep and absMask.toULong()
    // Detect if a or b is zero, infinity, or NaN.
    if ((aAbs - (one.toULong())) >= infRep - one || (bAbs - (one.toULong())) >= infRep - one) {
        // NaN + anything = qNaN
        if (aAbs > infRep) {
            return fromBitsFFn(aRep or quietBit)
        }
        // anything + NaN = qNaN
        if (bAbs > infRep) {
            return fromBitsFFn(bRep or quietBit)
        }
        if (aAbs == infRep) {
            // +/-infinity + -/+infinity = qNaN
            if ((a.toBits() xor b.toBits()) == signBit.toULong()) {
                return fromBitsFFn(qnanRep)
            } else {
                // +/-infinity + anything remaining = +/- infinity
                return a
            }
        }
        // anything remaining + +/-infinity = +/-infinity
        if (bAbs == infRep) {
            return b
        }
        // zero + anything = anything
        if (aAbs == MinInt.ZERO) {
            // but we need to get the sign right for zero + zero
            if (bAbs == MinInt.ZERO) {
                return fromBitsFFn(a.toBits() and b.toBits())
            } else {
                return b
            }
        }
        // anything + zero = anything
        if (bAbs == MinInt.ZERO) {
            return a
        }
    }
    // Swap a and b if necessary so that a has the larger absolute value.
    if (bAbs > aAbs) {
        // Don't use mem.swap because it may generate references to memcpy in unoptimized code.
        val tmp = aRep
        aRep = bRep
        bRep = tmp
    }
    // Extract the exponent and significand from the (possibly swapped) a and b.
    var aExponent: Int = ((aRep and exponentMask) shr significandBits.toInt()).cast()
    var bExponent: Int = ((bRep and exponentMask) shr significandBits.toInt()).cast()
    var aSignificand = aRep and significandMask
    var bSignificand = bRep and significandMask
    // normalize any denormals, and adjust the exponent accordingly.
    if (aExponent == 0) {
        val (exponent, significand) = normalizeFFn(aSignificand)
        aExponent = exponent
        aSignificand = significand
    }
    if (bExponent == 0) {
        val (exponent1, significand1) = normalizeFFn(bSignificand)
        bExponent = exponent1
        bSignificand = significand1
    }
    // The sign of the result is the sign of the larger operand, a.  If they
    // have opposite signs, we are performing a subtraction; otherwise addition.
    val resultSign = aRep and signBit.toULong()
    val subtraction = ((aRep xor bRep) and signBit.toULong()) != zero
    // Shift the significands to give us round, guard and sticky, and or in the
    // implicit significand bit.  (If we fell through from the denormal path it
    // was already set by normalize(), but setting it twice won't hurt
    // anything.)
    aSignificand = (aSignificand or implicitBit) shl 3
    bSignificand = (bSignificand or implicitBit) shl 3
    // Shift the significand of b by the difference in exponents, with a sticky
    // bottom bit to get rounding correct.
    val align = (aExponent - (bExponent.toInt())).cast()
    if (align != MinInt.ZERO) {
        if (align < bits) {
            val sticky = F.Int.fromBool(bSignificand shl UInt.castFrom((bits - (align.toInt()))).toInt() != MinInt.ZERO)
            bSignificand = (bSignificand shr UInt.castFrom(align).toInt()) or sticky
        } else {
            bSignificand = one
            // sticky; b is known to be non-zero.
        }
    }
    if (subtraction) {
        aSignificand = (aSignificand - (bSignificand.toULong()))
        // If a == -b, return +zero.
        if (aSignificand == MinInt.ZERO) {
            return fromBitsFFn(MinInt.ZERO)
        }
        // If partial cancellation occurred, we need to left-shift the result
        // and adjust the exponent:
        if (aSignificand < implicitBit shl 3) {
            val shift = aSignificand.leadingZeros().toInt() - (implicitBit shl 3u.toInt()).leadingZeros().toInt()
            aSignificand = aSignificand shl shift
            aExponent -= shift
        }
    } else {
        // addition
        aSignificand += bSignificand
        // If the addition carried up, we need to right-shift the result and
        // adjust the exponent:
        if (aSignificand and (implicitBit shl 4) != MinInt.ZERO) {
            val sticky1 = F.Int.fromBool(aSignificand and one != MinInt.ZERO)
            aSignificand = (aSignificand shr 1) or sticky1
            aExponent += 1
        }
    }
    // If we have overflowed the type, return +/- infinity:
    if (aExponent >= maxExponent.toInt()) {
        return fromBitsFFn(infRep or resultSign)
    }
    if (aExponent <= 0) {
        // Result is denormal before rounding; the exponent is zero and we
        // need to shift the significand.
        val shift1 = (1 - aExponent).cast()
        val sticky2 = F.Int.fromBool((aSignificand shl UInt.castFrom((bits - (shift1.toInt()))).toInt()) != MinInt.ZERO)
        aSignificand = (aSignificand shr UInt.castFrom(shift1).toInt()) or sticky2
        aExponent = 0
    }
    // Low three bits are round, guard, and sticky.
    val aSignificandI32: Int = aSignificand.castLossy()
    val roundGuardSticky: Int = aSignificandI32 and 0x7
    // Shift the significand into place, and mask off the implicit bit.
    var result = (aSignificand shr 3) and significandMask
    // Insert the exponent and sign.
    result = result or ((aExponent.cast() shl significandBits.toInt()).toULong())
    result = result or resultSign
    // Final rounding.  The result may overflow to infinity, but that is the
    // correct result in that case.
    if (roundGuardSticky > 0x4) {
        result += one
    }
    if (roundGuardSticky == 0x4) {
        result += result and one
    }
    return fromBitsFFn(result)
}

public fun addhf3(a: F16, b: F16): F16 = add(a, b)

public fun addsf3(a: Float, b: Float): Float = add(a, b)

public fun adddf3(a: Double, b: Double): Double = add(a, b)

public fun addtf3(a: F128, b: F128): F128 = add(a, b)

public fun addkf3(a: F128, b: F128): F128 = add(a, b)
