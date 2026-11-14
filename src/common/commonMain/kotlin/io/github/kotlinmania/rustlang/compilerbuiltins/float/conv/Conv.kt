// port-lint: source float/conv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.conv

import io.github.kotlinmania.f128.fromBits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.signed
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u128ToF128Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u128ToF32Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u128ToF64Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u32ToF128Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u32ToF32Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u32ToF64Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u64ToF128Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u64ToF32Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.u64ToF64Bits
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ops.arith.Neg

// Conversions from unsigned integers to floats.
// Conversions from signed integers to floats.
// Conversions from floats to unsigned integers.
// Conversions from floats to signed integers.
public fun floatunsisf(i: UInt): Float = Float.fromBits(u32ToF32Bits(i).toInt())

public fun floatunsidf(i: UInt): Double = Double.fromBits(u32ToF64Bits(i).toLong())

public fun floatunsitf(i: UInt): F128 = fromBits(u32ToF128Bits(i))

public fun floatunsikf(i: UInt): F128 = fromBits(u32ToF128Bits(i))

public fun floatundisf(i: ULong): Float = Float.fromBits(u64ToF32Bits(i).toInt())

public fun floatundidf(i: ULong): Double = Double.fromBits(u64ToF64Bits(i).toLong())

public fun floatunditf(i: ULong): F128 = fromBits(u64ToF128Bits(i))

public fun floatundikf(i: ULong): F128 = fromBits(u64ToF128Bits(i))

public fun floatuntisf(i: U128): Float = Float.fromBits(u128ToF32Bits(i).toInt())

public fun floatuntisf(lo: ULong, hi: ULong): Float = Float.fromBits(u128ToF32Bits((U128.from(hi) shl 64) or U128.from(lo)).toInt())

public fun floatuntidf(i: U128): Double = Double.fromBits(u128ToF64Bits(i).toLong())

public fun floatuntidf(lo: ULong, hi: ULong): Double = Double.fromBits(u128ToF64Bits((U128.from(hi) shl 64) or U128.from(lo)).toLong())

public fun floatuntitf(i: U128): F128 = fromBits(u128ToF128Bits(i))

public fun floatuntikf(i: U128): F128 = fromBits(u128ToF128Bits(i))

public fun floatsisf(i: Int): Float = signed(i, u32ToF32Bits)

public fun floatsidf(i: Int): Double = signed(i, u32ToF64Bits)

public fun floatsitf(i: Int): F128 = signed(i, u32ToF128Bits)

public fun floatsikf(i: Int): F128 = signed(i, u32ToF128Bits)

public fun floatdisf(i: Long): Float = signed(i, u64ToF32Bits)

public fun floatdidf(i: Long): Double = signed(i, u64ToF64Bits)

public fun floatditf(i: Long): F128 = signed(i, u64ToF128Bits)

public fun floatdikf(i: Long): F128 = signed(i, u64ToF128Bits)

public fun floattisf(i: I128): Float = signed(i, u128ToF32Bits)

public fun floattisf(lo: ULong, hi: ULong): Float = signed((I128.from(hi) shl 64) or I128.from(lo), u128ToF32Bits)

public fun floattidf(i: I128): Double = signed(i, u128ToF64Bits)

public fun floattidf(lo: ULong, hi: ULong): Double = signed((I128.from(hi) shl 64) or I128.from(lo), u128ToF64Bits)

public fun floattitf(i: I128): F128 = signed(i, u128ToF128Bits)

public fun floattikf(i: I128): F128 = signed(i, u128ToF128Bits)

/**
 * Generic float to unsigned int conversions.
 */
public fun <F, U> floatToUnsignedInt(f: F): U where F : Float, U : Int<U> = floatToIntInner<F, U>(f.toBits().toInt(), { i: U -> i }, { U.MAX })

/**
 * Generic float to signed int conversions.
 */
public fun <F, I> floatToSignedInt(f: F): I where F : Float, I : Int, I : Neg<I> =
    floatToIntInner<F, I>(f.toBits() and F.SIGN_MASK.inv(), { i: I -> if (f.isSignNegative()) {
        -i
    } else {
        i
    } }, { if (f.isSignNegative()) {
        I.MIN
    } else {
        I.MAX
    } })

/**
 * Float to int conversions, generic for both signed and unsigned.
 *
 * Parameters:
 * - `fbits`: `abg(f)` bitcasted to an integer.
 * - `mapInbounds`: apply this transformation to integers that are within range (add the sign back).
 * - `outOfBounds`: return value when out of range for `I`.
 */
public fun <F, I> floatToIntInner(fbits: Int, mapInbounds: (I) -> I, outOfBounds: () -> I, fromUnsignedIFn: (Any?) -> Any?): I where F : Float, I : Int {
    val intMaxExp = F.EXP_BIAS + I.MAX.ilog2() + 1
    val foobar = F.EXP_BIAS + I.Unsigned.BITS - 1
    return if (fbits < F.ONE.toBits().toInt()) {
        // < 0 gets rounded to 0
        I.ZERO
    } else if (fbits < F.Int.castFrom(intMaxExp) shl F.SIG_BITS.toInt()) {
        // >= 1, < integer max
        val mBase =
            if (I.Unsigned.BITS >= F.Int.BITS) {
                I.Unsigned.castFrom(fbits) shl (I.BITS - F.SIG_BITS - 1).toInt()
            } else {
                I.Unsigned.castFromLossy(fbits shr (F.SIG_BITS - I.BITS + 1).toInt())
            }
        // Set the implicit 1-bit.
        val m: Unsigned = (I.Unsigned.ONE shl (I.BITS - 1).toInt()) or mBase
        // Shift based on the exponent and bias.
        val s: UInt = (foobar) - UInt.castFrom(fbits shr F.SIG_BITS.toInt())
        val unsigned = m shr s.toInt()
        mapInbounds(fromUnsignedIFn(unsigned))
    } else if (fbits <= F.EXP_MASK) {
        // >= max (incl. inf)
        outOfBounds()
    } else {
        I.ZERO
    }
}

public fun fixunssfsi(f: Float): UInt = floatToUnsignedInt(f)

public fun fixunssfdi(f: Float): ULong = floatToUnsignedInt(f)

public fun fixunssfti(f: Float): U128 = floatToUnsignedInt(f)

public fun fixunsdfsi(f: Double): UInt = floatToUnsignedInt(f)

public fun fixunsdfdi(f: Double): ULong = floatToUnsignedInt(f)

public fun fixunsdfti(f: Double): U128 = floatToUnsignedInt(f)

public fun fixunstfsi(f: F128): UInt = floatToUnsignedInt(f)

public fun fixunskfsi(f: F128): UInt = floatToUnsignedInt(f)

public fun fixunstfdi(f: F128): ULong = floatToUnsignedInt(f)

public fun fixunskfdi(f: F128): ULong = floatToUnsignedInt(f)

public fun fixunstfti(f: F128): U128 = floatToUnsignedInt(f)

public fun fixunskfti(f: F128): U128 = floatToUnsignedInt(f)

public fun fixsfsi(f: Float): Int = floatToSignedInt(f)

public fun fixsfdi(f: Float): Long = floatToSignedInt(f)

public fun fixsfti(f: Float): I128 = floatToSignedInt(f)

public fun fixdfsi(f: Double): Int = floatToSignedInt(f)

public fun fixdfdi(f: Double): Long = floatToSignedInt(f)

public fun fixdfti(f: Double): I128 = floatToSignedInt(f)

public fun fixtfsi(f: F128): Int = floatToSignedInt(f)

public fun fixkfsi(f: F128): Int = floatToSignedInt(f)

public fun fixtfdi(f: F128): Long = floatToSignedInt(f)

public fun fixkfdi(f: F128): Long = floatToSignedInt(f)

public fun fixtfti(f: F128): I128 = floatToSignedInt(f)

public fun fixkfti(f: F128): I128 = floatToSignedInt(f)
