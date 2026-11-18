// port-lint: source math/generic/sqrt.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libm.math.generic.sqrt

import io.github.kotlinmania.rustlang.libm.math.support.coldPath
import io.github.kotlinmania.rustlang.libm.math.support.env.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.HInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

// Generic square root algorithm.
//
// This routine operates around `mU2`, a U.2 (fixed point with two integral bits) mantissa
// within the range [1, 4). A table lookup provides an initial estimate, then goldschmidt
// iterations at various widths are used to approach the real values.
//
// For the iterations, `r` is a U0 number that approaches `1 / sqrt(mU2)`, and `s` is a U2 number
// that approaches `sqrt(mU2)`. Recall that mU2 ∈ [1, 4).
//
// With Newton-Raphson iterations, this would be:
//
// - `w = r * r w ~ 1 / m`
// - `u = 3 - m * w u ~ 3 - m * w = 3 - m / m = 2`
// - `r = r * u / 2 r ~ r`
//
// (Note that the righthand column does not show anything analytically meaningful (i.e. r ~ r),
// since the value of performing one iteration is in reducing the error representable by `~`).
//
// Instead of Newton-Raphson iterations, Goldschmidt iterations are used to calculate
// `s = m * r`:
//
// - `s = m * r s ~ m / sqrt(m)`
// - `u = 3 - s * r u ~ 3 - (m / sqrt(m)) * (1 / sqrt(m)) = 3 - m / m = 2`
// - `r = r * u / 2 r ~ r`
// - `s = s * u / 2 s ~ s`
//
// The above is precise because it uses the original value `m`. There is also a faster version
// that performs fewer steps but does not use `m`:
//
// - `u = 3 - s * r u ~ 3 - 1`
// - `r = r * u / 2 r ~ r`
// - `s = s * u / 2 s ~ s`
//
// Rounding errors accumulate faster with the second version, so it is only used for subsequent
// iterations within the same width integer. The first version is always used for the first
// iteration at a new width in order to avoid this accumulation.
//
// Goldschmidt has the advantage over Newton-Raphson that `sqrt(x)` and `1 / sqrt(x)` are
// computed at the same time, i.e. there is no need to calculate `1 / sqrt(x)` and invert it.

public fun <Int, SignedInt, ISet1, ISet2, D, OtherSign, Unsigned, F> sqrtRound(x: F, round: Round, fromPartsFFn: (Boolean, UInt, Int) -> F, fromBitsFFn: (Int) -> F): FpResult<F> where F : Float<F, Int, SignedInt>, F : SqrtHelper<F, ISet1, ISet2, Int, SignedInt> {
    val zero = IntTy.ZERO
    val one = IntTy.ONE
    var ix = x.toBits()
    // Top is the exponent and sign, which may or may not be shifted. If the float fits into a
    // `UInt`, we can get by without paying shifting costs.
    val noshift = x.BITS <= 32u
    var (top1, specialCase2) =
        if (noshift) {
            val expLsb = one shl x.SIG_BITS.toInt()
            val specialCase = (ix - (expLsb.toULong())) >= x.EXP_MASK - expLsb
            Pair(Exp.NoShift(Unit), specialCase)
        } else {
            val top = UInt.castFrom(ix shr x.SIG_BITS.toInt())
            val specialCase1 = top.wrappingSub(1) >= x.EXP_SAT - 1
            Pair(Exp.Shifted(top), specialCase1)
        }
    // Handle NaN, zero, and out of domain (<= 0)
    if (specialCase2) {
        coldPath()
        // +/-0
        if (ix shl 1 == zero) {
            return FpResult.ok(x)
        }
        // Positive infinity
        if (ix == x.EXP_MASK) {
            return FpResult.ok(x)
        }
        // NaN or negative
        if (ix > x.EXP_MASK) {
            return FpResult.new(x.NAN, Status.INVALID)
        }
        // Normalize subnormals by multiplying by 1.0 << SIG_BITS (e.g. 0x1p52 for doubles).
        val scaled = x * fromPartsFFn(false, x.SIG_BITS + x.EXP_BIAS, zero)
        ix = scaled.toBits()
        when (top1) {
            is Exp.Shifted<*> -> {
                val (v) = top1
                v = scaled.ex()
                v = ((v) - (x.SIG_BITS.toUInt()))
            }
            is Exp.NoShift<*> -> {
                ix = (ix - (x.SIG_BITS shl x.SIG_BITS.toInt()).cast().toULong())
            }
        }
    }
    // Reduce arguments such that `x = 4 ^ e * m`:
    //
    // - mU2 ∈ [1, 4), a fixed point U2.BITS number
    // - 2^e is the exponent part of the result
    val (mU22, exp) =
        when (top1) {
            is Exp.Shifted<*> -> {
                val (top2) = top1
                // We now know `x` is positive, so `top` is just its (biased) exponent
                var e = top2
                // Construct a fixed point representation of the mantissa.
                var mU2 = (ix or x.IMPLICIT_BIT) shl x.EXP_BITS.toInt()
                val even = (e and 1u) != 0u
                if (even) {
                    mU2 = mU2 shr 1
                }
                e = ((e + ((x.EXP_SAT shr 1).toUInt()))) shr 1
                Pair(mU2, Exp.Shifted(e))
            }
            is Exp.NoShift<*> -> {
                val even1 = ix and (one shl x.SIG_BITS.toInt()) != zero
                // Exponent part of the return value
                var eNoshift = ix shr 1
                // ey &= (F.ExpMask << 2) >> 2; // clear the top exponent bit (result = 1.0)
                eNoshift += (x.EXP_MASK xor (x.SIGN_MASK shr 1)) shr 1
                eNoshift = eNoshift and x.EXP_MASK
                val m1 = (ix shl x.EXP_BITS.toInt()) or x.SIGN_MASK
                val m0 = (ix shl (x.EXP_BITS - 1).toInt()) and x.SIGN_MASK.inv()
                val mU21 =
                    if (even1) {
                        m0
                    } else {
                        m1
                    }
                Pair(mU21, Exp.NoShift(eNoshift))
            }
        }
    // Extract the top 6 bits of the significand with the lowest bit of the exponent.
    val i = castFrom(ix shr (x.SIG_BITS - 6).toInt()) and 0x7F
    // Start with an initial guess for `r = 1 / sqrt(m)` from the table, and shift `m` as an
    // initial value for `s = sqrt(m)`. See the module documentation for details.
    val r1U0: ISet1 = F.ISet1.castFrom(RSQRT_TAB[i]) shl (F.ISet1.BITS - 16).toInt()
    val s1U2: ISet1 = ((mU22) shr (x.BITS - F.ISet1.BITS).toInt()).cast()
    // Perform iterations, if any, at quarter width (used for `f128`).
    val (r1U01, s1U2) = goldschmidt<ISet1>(r1U0, s1U2, x.SET1_ROUNDS, false)
    // Widen values and perform iterations at half width (used for `Double` and `f128`).
    val r2U0: ISet2 = F.ISet2.from(r1U01) shl (F.ISet2.BITS - F.ISet1.BITS).toInt()
    val s2U2: ISet2 = ((mU22) shr (x.BITS - F.ISet2.BITS).toInt()).cast()
    val (r2U01, s2U2) = goldschmidt<ISet2>(r2U0, s2U2, x.SET2_ROUNDS, false)
    // Perform final iterations at full width (used for all float types).
    val rU0: Int = F.Int.from(r2U01) shl (x.BITS - F.ISet2.BITS).toInt()
    val sU2: Int = mU22
    val (rU0, sU21) = goldschmidt<Int>(rU0, sU2, x.FINAL_ROUNDS, true)
    // Shift back to mantissa position.
    var m = sU21 shr (x.EXP_BITS - 2).toInt()
    // The musl source includes the following comment (with literals replaced):
    //
    // > s < sqrt(m) < s + 0x1.09p-SIG_BITS
    // > compute nearest rounded result: the nearest result to SIG_BITS bits is either s or
    // > s+0x1p-SIG_BITS, we can decide by comparing (2^SIG_BITS s + 0.5)^2 to 2^(2*SIG_BITS) m.
    //
    // Expanding this with , with `SIG_BITS = p` and adjusting based on the operations done to
    // `d0` and `d1`:
    //
    // - `2^(2p)m ≟ ((2^p)m + 0.5)^2`
    // - `2^(2p)m ≟ 2^(2p)m^2 + (2^p)m + 0.25`
    // - `2^(2p)m - m^2 ≟ (2^(2p) - 1)m^2 + (2^p)m + 0.25`
    // - `(1 - 2^(2p))m + m^2 ≟ (1 - 2^(2p))m^2 + (1 - 2^p)m + 0.25` (?)
    //
    // I do not follow how the rounding bit is extracted from this comparison with the below
    // operations. In any case, the algorithm is well tested.
    // The value needed to shift `mU2` by to create `m * 2 ^(2p)`. `2p = 2 * F.SIG_BITS`,
    // `F.BITS - 2` accounts for the offset that `mU2` already has.
    val shift = 2 * x.SIG_BITS - (x.BITS - 2)
    // `2 ^(2p) m - m ^ 2`
    val d0 = (mU22 shl shift.toInt()).wrappingSub(m.wrappingMul(m))
    // `m - 2 ^(2p) m + m ^ 2`
    val d1 = m.wrappingSub(d0)
    m += d1 shr (x.BITS - 1).toInt()
    m = m and x.SIG_MASK
    when (exp) {
        is Exp.Shifted<*> -> {
            val (e1) = exp
            m = m or (IntTy.castFrom(e1) shl x.SIG_BITS.toInt())
        }
        is Exp.NoShift<*> -> {
            val (e2) = exp
            m = m or e2
        }
    }
    var y = fromBitsFFn(m)
    // Note(f16): the fenv math does not work for `f16`
    if (x.BITS > 16) {
        // Handle rounding and inexact. `(m + 1) ^ 2 == 2 ^ shift m` is exact; for all other cases, add
        // a tiny value to cause fenv effects.
        val d2 = d1.wrappingAdd(m).wrappingAdd(one)
        var tiny =
            if (d2 == zero) {
                coldPath()
                zero
            } else {
                x.IMPLICIT_BIT
            }
        tiny = tiny or ((d1 xor d2) and x.SIGN_MASK)
        val t = fromBitsFFn(tiny)
        y = y + t
    }
    return FpResult.ok(y)
}

/**
 * Multiply at the wider integer size, returning the high half.
 */
public fun <D, OtherSign, Unsigned, I : HInt<I, D, OtherSign, Unsigned>> wmulh(a: I, b: I): I = a.widenMul(b).hi()

/**
 * Perform `count` goldschmidt iterations, returning `(rU0, sU)`.
 *
 * - `rU0` is the reciprocal `r ~ 1 / sqrt(m)`, as U0.
 * - `sU2` is the square root, `s ~ sqrt(m)`, as U2.
 * - `count` is the number of iterations to perform.
 * - `finalSet` should be true if this is the last round (same-sized integer). If so, the
 *   returned `s` will be U3, for later shifting. Otherwise, the returned `s` is U2.
 *
 * Note that performance relies on the optimizer being able to unroll these loops (reasonably
 * trivial, `count` is a constant when called).
 */
public fun <D, OtherSign, Unsigned, I> goldschmidt(rU0: I, sU2: I, count: UInt, finalSet: Boolean, fromIFn: (UByte) -> I): Pair<I, I> where I : HInt<I, D, OtherSign, Unsigned> {
    var rU01 = rU0
    var sU21 = sU2
    val threeU2 = fromIFn(0x3u.toUByte()) shl (rU0.BITS - 2).toInt()
    var uU0 = rU01
    for (i in 0 until count) {
        // First iteration: `s = m * r` (`uU0 = rU0` set above)
        // Subsequent iterations: `s = s * u / 2`
        sU21 = wmulh(sU21, uU0)
        // Perform `s /= 2` if:
        //
        // 1. This is not the first iteration (the first iteration is `s = m * r`)...
        // 2. ... and this is not the last set of iterations
        // 3. ... or, if this is the last set, it is not the last iteration
        //
        // This step is not performed for the final iteration because the shift is combined with
        // a later shift (moving `s` into the mantissa).
        if (i > 0 && (!finalSet || i + 1u < count)) {
            sU21 = sU21 shl 1
        }
        // u = 3 - s*r
        val dU2 = wmulh(sU21, rU01)
        uU0 = threeU2.wrappingSub(dU2)
        // r = r*u/2
        rU01 = wmulh(rU01, uU0) shl 1
    }
    return Pair(rU01, sU21)
}

/**
 * Representation of whether we shift the exponent into a `UInt`, or modify it in place to save
 * the shift operations.
 */
public sealed class Exp<T> {
    /**
     * The exponent has been shifted to a `UInt` and is LSB-aligned.
     */
    public data class Shifted<T>(public var value: UInt) : Exp<T>()
    /**
     * The exponent is in its natural position in integer repr.
     */
    public data class NoShift<T>(public var value: T) : Exp<T>()
}

/**
 * Size-specific constants related to the square root routine.
 */
public interface SqrtHelper<T, ISet1, ISet2, Int, SignedInt> : Float<T, Int, SignedInt> {
    /**
     * Number of rounds at `ISet1`.
     */
    public val SET1_ROUNDS: UInt
        get() = 0u

    /**
     * Number of rounds at `ISet2`.
     */
    public val SET2_ROUNDS: UInt
        get() = 0u

    /**
     * Number of rounds at `Self.Int`.
     */
    public val FINAL_ROUNDS: UInt
}

// unused
public val FINAL_ROUNDS: UInt = 2u

// unused
public val FINAL_ROUNDS1: UInt = 3u

public val SET2_ROUNDS: UInt = 2u

public val FINAL_ROUNDS2: UInt = 2u

public val SET1_ROUNDS: UInt = 1u

public val SET2_ROUNDS1: UInt = 2u

public val FINAL_ROUNDS3: UInt = 2u

/**
 * A U0.16 representation of `1 / sqrt(x)`.
 *
 * The index is a 7-bit number consisting of a single exponent bit and 6 bits of significand.
 */
public var RSQRT_TAB: UShortArray = ushortArrayOf(0xb451u.toUShort(), 0xb2f0u.toUShort(), 0xb196u.toUShort(), 0xb044u.toUShort(), 0xaef9u.toUShort(), 0xadb6u.toUShort(), 0xac79u.toUShort(), 0xab43u.toUShort(), 0xaa14u.toUShort(), 0xa8ebu.toUShort(), 0xa7c8u.toUShort(), 0xa6aau.toUShort(), 0xa592u.toUShort(), 0xa480u.toUShort(), 0xa373u.toUShort(), 0xa26bu.toUShort(), 0xa168u.toUShort(), 0xa06au.toUShort(), 0x9f70u.toUShort(), 0x9e7bu.toUShort(), 0x9d8au.toUShort(), 0x9c9du.toUShort(), 0x9bb5u.toUShort(), 0x9ad1u.toUShort(), 0x99f0u.toUShort(), 0x9913u.toUShort(), 0x983au.toUShort(), 0x9765u.toUShort(), 0x9693u.toUShort(), 0x95c4u.toUShort(), 0x94f8u.toUShort(), 0x9430u.toUShort(), 0x936bu.toUShort(), 0x92a9u.toUShort(), 0x91eau.toUShort(), 0x912eu.toUShort(), 0x9075u.toUShort(), 0x8fbeu.toUShort(), 0x8f0au.toUShort(), 0x8e59u.toUShort(), 0x8daau.toUShort(), 0x8cfeu.toUShort(), 0x8c54u.toUShort(), 0x8bacu.toUShort(), 0x8b07u.toUShort(), 0x8a64u.toUShort(), 0x89c4u.toUShort(), 0x8925u.toUShort(), 0x8889u.toUShort(), 0x87eeu.toUShort(), 0x8756u.toUShort(), 0x86c0u.toUShort(), 0x862bu.toUShort(), 0x8599u.toUShort(), 0x8508u.toUShort(), 0x8479u.toUShort(), 0x83ecu.toUShort(), 0x8361u.toUShort(), 0x82d8u.toUShort(), 0x8250u.toUShort(), 0x81c9u.toUShort(), 0x8145u.toUShort(), 0x80c2u.toUShort(), 0x8040u.toUShort(), 0xff02u.toUShort(), 0xfd0eu.toUShort(), 0xfb25u.toUShort(), 0xf947u.toUShort(), 0xf773u.toUShort(), 0xf5aau.toUShort(), 0xf3eau.toUShort(), 0xf234u.toUShort(), 0xf087u.toUShort(), 0xeee3u.toUShort(), 0xed47u.toUShort(), 0xebb3u.toUShort(), 0xea27u.toUShort(), 0xe8a3u.toUShort(), 0xe727u.toUShort(), 0xe5b2u.toUShort(), 0xe443u.toUShort(), 0xe2dcu.toUShort(), 0xe17au.toUShort(), 0xe020u.toUShort(), 0xdecbu.toUShort(), 0xdd7du.toUShort(), 0xdc34u.toUShort(), 0xdaf1u.toUShort(), 0xd9b3u.toUShort(), 0xd87bu.toUShort(), 0xd748u.toUShort(), 0xd61au.toUShort(), 0xd4f1u.toUShort(), 0xd3cdu.toUShort(), 0xd2adu.toUShort(), 0xd192u.toUShort(), 0xd07bu.toUShort(), 0xcf69u.toUShort(), 0xce5bu.toUShort(), 0xcd51u.toUShort(), 0xcc4au.toUShort(), 0xcb48u.toUShort(), 0xca4au.toUShort(), 0xc94fu.toUShort(), 0xc858u.toUShort(), 0xc764u.toUShort(), 0xc674u.toUShort(), 0xc587u.toUShort(), 0xc49du.toUShort(), 0xc3b7u.toUShort(), 0xc2d4u.toUShort(), 0xc1f4u.toUShort(), 0xc116u.toUShort(), 0xc03cu.toUShort(), 0xbf65u.toUShort(), 0xbe90u.toUShort(), 0xbdbeu.toUShort(), 0xbcefu.toUShort(), 0xbc23u.toUShort(), 0xbb59u.toUShort(), 0xba91u.toUShort(), 0xb9ccu.toUShort(), 0xb90au.toUShort(), 0xb84au.toUShort(), 0xb78cu.toUShort(), 0xb6d0u.toUShort(), 0xb617u.toUShort(), 0xb560u.toUShort())
