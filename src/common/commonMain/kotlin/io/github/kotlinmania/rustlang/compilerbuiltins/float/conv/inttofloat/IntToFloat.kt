// port-lint: source float/conv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.wrappingShl
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

// Conversions from integers to floats.
//
// The algorithm is explained here: <https://blog.m-ou.se/floats/>. It roughly does the following:
// - Calculate a base mantissa by shifting the integer into mantissa position. This gives us a
//   mantissa _with the implicit bit set
// - Figure out if rounding needs to occur by classifying the bits that are to be truncated. Some
//   patterns are used to simplify this. Adjust the mantissa with the result if needed.
// - Calculate the exponent based on the base-2 logarithm of `i` (leading zeros). Subtract one.
// - Shift the exponent and add the mantissa to create the final representation. Subtracting one
//   from the exponent (above) accounts for the explicit bit being set in the mantissa.
//
// # Terminology
//
// - `i`: the original integer
// - `iM`: the integer, shifted fully left (no leading zeros)
// - `n`: number of leading zeroes
// - `e`: the resulting exponent. Usually 1 is subtracted to offset the mantissa implicit bit.
// - `mBase`: the mantissa before adjusting for truncated bits. Implicit bit is usually set.
// - `adj`: the bits that will be truncated, possibly compressed in some way.
// - `m`: the resulting mantissa. Implicit bit is usually set.

/**
 * Calculate the exponent from the number of leading zeros.
 *
 * Usually 1 is subtracted from this function's result, so that a mantissa with the implicit
 * bit set can be added back later.
 */
public fun <I : Int, F : Float> exp(n: UInt): Int = F.Int.castFrom(F.EXP_BIAS - 1u + I.BITS - n)

/**
 * Adjust a mantissa with dropped bits to perform correct rounding.
 *
 * The dropped bits should be exactly the bits that get truncated (left-aligned), but they
 * can be combined or compressed in some way that simplifies operations.
 */
public fun <F : Float> mAdj(mBase: Int, droppedBits: Int): Int {
    // Branchlessly extract a `1` if rounding up should happen, 0 otherwise
    // This accounts for rounding to even.
    val adj = (droppedBits - ((droppedBits shr (F.BITS - 1).toInt()) and mBase.inv())) shr (F.BITS - 1).toInt()
    // Add one when we need to round up. Break ties to even.
    return mBase + adj
}

/**
 * Shift the exponent to its position and add the mantissa.
 *
 * If the mantissa has the implicit bit set, the exponent should be one less than its actual
 * value to cancel it out.
 */
public fun <F : Float> repr(e: Int, m: Int): Int {
    // + rather than | so the mantissa can overflow into the exponent
    return (e shl F.SIG_BITS.toInt()) + m
}

/**
 * Shift distance from a left-aligned integer to a smaller float.
 */
public fun <I : Int, F : Float> shiftFLtI(): UInt = (I.BITS - F.BITS) + F.EXP_BITS

/**
 * Shift distance from an integer with `n` leading zeros to a smaller float.
 */
public fun <I : Int, F : Float> shiftFGtI(n: UInt): UInt = F.SIG_BITS - I.BITS + 1u + n

/**
 * Perform a signed operation as unsigned, then add the sign back.
 */
public fun <I, F> signed(i: I, conv: (I.Unsigned) -> F.Int, fromBitsFFn: (Any?) -> F): F where F : Float, I : Int {
    val signBit = F.Int.castFromLossy(i shr (i.BITS - 1).toInt()) shl (F.BITS - 1).toInt()
    return fromBitsFFn(conv(i.unsignedAbs()) or signBit)
}

public fun u32ToF32Bits(i: UInt): UInt {
    if (i == 0u) {
        return 0u
    }
    val n = i.leadingZeros()
    // Mantissa with implicit bit set (significant bits)
    val mBase = (i shl n.toInt()) shr Float.EXP_BITS.toInt()
    // Bits that will be dropped (insignificant bits)
    val adj = (i shl n.toInt()) shl (Float.SIG_BITS + 1.0f).toInt()
    val m = mAdj<Float>(mBase.toInt(), adj.toInt())
    val e = exp<UInt, Float>(n) - 1
    return repr<Float>(e, m).toUInt()
}

public fun u32ToF64Bits(i: UInt): ULong {
    if (i == 0u) {
        return 0uL
    }
    val n = i.leadingZeros()
    // Mantissa with implicit bit set
    val m = (i.toULong()) shl (shiftFGtI<UInt, Double>(n)).toInt()
    val e = exp<UInt, Double>(n) - 1
    return repr<Double>(e, m.toInt()).toULong()
}

public fun u32ToF128Bits(i: UInt): U128 {
    if (i == 0u) {
        return U128(0x0uL, 0x0uL)
    }
    val n = i.leadingZeros()
    // Shift into mantissa position that is correct for the type, but shifted into the lower
    // 64 bits over so can can avoid 128-bit math.
    val m = (i.toULong()) shl (shiftFGtI<UInt, F128>(n) - 64u).toInt()
    val e = (exp<UInt, F128>(n)).toULong() - 1uL
    // High 64 bits of f128 representation.
    val h = (e shl (F128.SIG_BITS - F128.fromDouble(64.0)).toInt()) + m
    // Shift back to the high bits, the rest of the mantissa will always be 0.
    return (U128.fromULong(h)) shl 64
}

public fun u64ToF32Bits(i: ULong): UInt {
    val n = i.leadingZeros()
    val iM = i.wrappingShl(n)
    // Mantissa with implicit bit set
    val mBase: UInt = (iM shr (shiftFLtI<ULong, Float>()).toInt()).toUInt()
    // The entire lower half of `i` will be truncated (masked portion), plus the
    // next `EXP_BITS` bits.
    val adj = ((iM shr Float.EXP_BITS.toInt()) or iM and 0xFFFFuL).toUInt()
    val m = mAdj<Float>(mBase.toInt(), adj.toInt())
    val e =
        if (i == 0uL) {
            0
        } else {
            exp<ULong, Float>(n) - 1
        }
    return repr<Float>(e, m).toUInt()
}

public fun u64ToF64Bits(i: ULong): ULong {
    if (i == 0uL) {
        return 0uL
    }
    val n = i.leadingZeros()
    // Mantissa with implicit bit set
    val mBase = (i shl n.toInt()) shr Double.EXP_BITS.toInt()
    val adj = (i shl n.toInt()) shl (Double.SIG_BITS + 1.0).toInt()
    val m = mAdj<Double>(mBase.toInt(), adj.toInt())
    val e = exp<ULong, Double>(n) - 1
    return repr<Double>(e, m).toULong()
}

public fun u64ToF128Bits(i: ULong): U128 {
    if (i == 0uL) {
        return U128(0x0uL, 0x0uL)
    }
    val n = i.leadingZeros()
    // Mantissa with implicit bit set
    val m = (U128.fromULong(i)) shl (shiftFGtI<ULong, F128>(n)).toInt()
    val e = exp<ULong, F128>(n) - 1
    return repr<F128>(e, m.toInt())
}

public fun u128ToF32Bits(i: U128): UInt {
    val n = i.leadingZeros()
    val iM = i.wrappingShl(n)
    // Mantissa, shifted so the first bit is nonzero
    val mBase: UInt = (iM shr (shiftFLtI<U128, Float>()).toInt()).toUInt()
    // Within the upper `F.BITS`, everything except for the signifcand
    // gets truncated
    val d1: UInt = (iM shr (128u - Float.BITS - Float.SIG_BITS.toUInt() - 1u).toInt()).castLossy()
    // The entire rest of `iM` gets truncated. Zero the upper `F.BITS` then just
    // check if it is nonzero.
    val d2: UInt = (iM shl Float.BITS.toInt() shr Float.BITS.toInt() != U128(0x0uL, 0x0uL)).into()
    val adj = d1 or d2
    // Mantissa with implicit bit set
    val m = mAdj<Float>(mBase.toInt(), adj.toInt())
    val e =
        if (i == U128(0x0uL, 0x0uL)) {
            0
        } else {
            exp<U128, Float>(n) - 1
        }
    return repr<Float>(e, m).toUInt()
}

public fun u128ToF64Bits(i: U128): ULong {
    val n = i.leadingZeros()
    val iM = i.wrappingShl(n)
    // Mantissa with implicit bit set
    val mBase: ULong = (iM shr (shiftFLtI<U128, Double>()).toInt()).toULong()
    // The entire lower half of `i` will be truncated (masked portion), plus the
    // next `EXP_BITS` bits.
    val adj = ((iM shr Double.EXP_BITS.toInt()) or iM and U128(0x0uL, 0xffffffffuL)).toULong()
    val m = mAdj<Double>(mBase.toInt(), adj.toInt())
    val e =
        if (i == U128(0x0uL, 0x0uL)) {
            0
        } else {
            exp<U128, Double>(n) - 1
        }
    return repr<Double>(e, m).toULong()
}

public fun u128ToF128Bits(i: U128): U128 {
    if (i == U128(0x0uL, 0x0uL)) {
        return U128(0x0uL, 0x0uL)
    }
    val n = i.leadingZeros()
    // Mantissa with implicit bit set
    val mBase = (i shl n.toInt()) shr F128.EXP_BITS.toInt()
    val adj = (i shl n.toInt()) shl (F128.SIG_BITS + F128.fromDouble(1.0)).toInt()
    val m = mAdj<F128>(mBase.toInt(), adj.toInt())
    val e = exp<U128, F128>(n) - 1
    return repr<F128>(e, m)
}
