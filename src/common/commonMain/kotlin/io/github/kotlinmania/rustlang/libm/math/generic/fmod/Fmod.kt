// port-lint: source math/generic/fmod.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fmod

import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int
import io.github.kotlinmania.rustlang.libm.math.support.modular.linearMulReduction

public fun <Int, SignedInt, D, F : Float<F, Any?, Any?>> fmod(x: F, y: F, fromBitsFFn: (Int) -> F): F {
    val v1 = F.Int.ONE
    val sx = x.toBits() and F.SIGN_MASK
    val ux = x.toBits() and F.SIGN_MASK.inv()
    val uy = y.toBits() and F.SIGN_MASK.inv()
    // Cases that return NaN:
    // NaN % _
    // Inf % _
    // _ % NaN
    // _ % 0
    val xNanOrInf = ux and F.EXP_MASK == F.EXP_MASK
    val yNanOrZero = (uy - (v1.toULong())) and F.EXP_MASK == F.EXP_MASK
    if (xNanOrInf or yNanOrZero) {
        return (x * y) / (x * y)
    }
    if (ux < uy) {
        // |x| < |y|
        return x
    }
    val (num, ex) = intoSigExp<F>(ux.toInt())
    val (div, ey) = intoSigExp<F>(uy.toInt())
    // To compute `(num<<ex) %(div<<ey)`, first
    // evaluate `rem = (num<<(ex - ey)) % div` ...
    val rem = reduction<F>(num, ex - ey, div)
    // ... so the result will be `rem<<ey`
    if (rem.isZero()) {
        // Return zero with the sign of `x`
        return fromBitsFFn(sx)
    }
    // We would shift `rem` up by `ey`, but have to stop at `F.SIG_BITS`
    val shift = ey.min(F.SIG_BITS - rem.ilog2())
    // Anything past that is added to the exponent field
    val bits = (rem shl shift.toInt()) + (F.Int.castFrom(ey - shift) shl F.SIG_BITS.toInt())
    return fromBitsFFn(sx + bits.toULong())
}

/**
 * Given the bits of a finite float, return a tuple of
 *  - the mantissa with the implicit bit (0 if subnormal, 1 otherwise)
 *  - the additional exponent past 1, (0 for subnormal, 0 or more otherwise)
 */
public fun <Int, SignedInt, F : Float<F, Any?, Any?>> intoSigExp(bits: Int): Pair<Int, UInt> {
    var bits1 = bits
    bits1 = bits1 and F.SIGN_MASK.inv()
    // Subtract 1 from the exponent, clamping at 0
    val sat = (rustCheckedSub(bits1, F.IMPLICIT_BIT.toInt()) ?: F.Int.ZERO)
    return Pair(bits1 - (sat and F.EXP_MASK), UInt.castFrom(sat shr F.SIG_BITS.toInt()))
}

/**
 * Compute the remainder `(x * 2.pow(e)) % y` without overflow.
 */
public fun <Int, SignedInt, D, F> reduction(x: Int, e: UInt, y: Int): Int where F : Float<F, Any?, Any?> {
    var x1 = x
    // `f16` only has 5 exponent bits, so even `f16.MAX = 65504.0` is only
    // a 40-bit integer multiple of the smallest subnormal.
    if (F.BITS == 16) {
        check(F.EXP_MAX - F.EXP_MIN == 29)
        check(e <= 29u)
        val u: UShort = x1.cast().toUShort()
        val v: UShort = y.cast().toUShort()
        val u1 = (u.toULong()) shl e.toInt()
        val v1 = v.toULong()
        return F.Int.castFrom((u1 % v1).toUShort())
    }
    // Ensure `x<2y` for later steps
    if (x1 >= (y shl 1)) {
        // This case is only reached with subnormal divisors,
        // but it might be better to just normalize all significands
        // to make this unnecessary. The further calls could potentially
        // benefit from assuming a specific fixed leading bit position.
        x1 %= y
    }
    // The simple implementation seems to be fastest for a short reduction
    // at this size. The limit here was chosen empirically on an Intel Nehalem.
    // Less old CPUs that have faster `ULong * ULong -> ULong` might not benefit,
    // and 32-bit systems or architectures without hardware multipliers might
    // want to do this in more cases.
    if (F.BITS == 64 && e < 32u) {
        // Assumes `x<2y`
        for (unused in 0 until e) {
            x1 = (rustCheckedSub(x1, y) ?: x1)
            x1 = x1 shl 1
        }
        return (rustCheckedSub(x1, y) ?: x1)
    }
    // Fast path for short reductions
    if (e < F.BITS) {
        val w = x1.widen() shl e.toInt()
        when (val matchVal = w.checkedNarrowingDivRem(y)) {
            null -> {}
            else -> {
                val r = matchVal.second
                return r
            }
        }
    }
    // Assumes `x<2y`
    return linearMulReduction(x1, e, y)
}
