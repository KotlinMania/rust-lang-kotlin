// port-lint: source math/generic/fma.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fma

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libm.math.generic.scalbn.scalbn
import io.github.kotlinmania.rustlang.libm.math.support.env.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.CastFrom
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

/**
 * Fused multiply-add that works when there is not a larger float size available. Computes
 * `(x * y) + z`.
 */
public fun <Int, SignedInt, D, OtherSign, Unsigned, F> fmaRound(x: F, y: F, z: F, round: Round, castFromLossyFFn: (T) -> F, fromPartsFFn: (Boolean, UInt, Int) -> F, castFromFFn: (T) -> F): FpResult<F> where F : Float<F, Int, SignedInt>, F : CastFrom<SignedInt, F>, F : CastFrom<Byte, F> {
    val one = IntTy.ONE
    val zero = IntTy.ZERO
    // Normalize such that the top of the mantissa is zero and we have a guard bit.
    val nx = Norm.fromFloat(x)
    val ny = Norm.fromFloat(y)
    val nz = Norm.fromFloat(z)
    if (nx.isZeroNanInf() || ny.isZeroNanInf()) {
        // Value will overflow, defer to non-fused operations.
        return FpResult.ok(x * y + z)
    }
    if (nz.isZeroNanInf()) {
        if (nz.isZero()) {
            // Empty add component means we only need to multiply.
            return FpResult.ok(x * y)
        }
        // `z` is NaN or infinity, which sets the result.
        return FpResult.ok(z)
    }
    // multiply: r = x * y
    val zhi: Int
    val zlo: Int
    var (rlo, rhi) = nx.m.widenMul(ny.m).loHi()
    // Exponent result of multiplication
    var e: Int<OtherSign, Unsigned> = nx.e + ny.e
    // Needed shift to align `z` to the multiplication result
    var d: Int<OtherSign, Unsigned> = nz.e - e
    val sbits = ((F.BITS as Any?) as Int<OtherSign, Unsigned>)
    // Scale `z`. Shift `z< <= kz`, `r> >= kr`, so `kz + kr == d`, set `e = e + kr` (== ez-kz)
    if ((kmCompareValues(d, 0) > 0)) {
        // The magnitude of `z` is larger than `x * y`
        if ((kmCompareValues(d, sbits) < 0)) {
            // Maximum shift of one `F.BITS` means shifted `z` will fit into `2 * F.BITS`. Shift
            // it into `(zhi, zlo)`. No exponent adjustment necessary.
            zlo = nz.m shl d.toInt()
            zhi = nz.m shr (sbits - d).toInt()
        } else {
            // Shift larger than `sbits`, `z` only needs the top half `zhi`. Place it there (acts
            // as a shift by `sbits`).
            zlo = zero
            zhi = nz.m
            d -= sbits
            // `z`s exponent is large enough that it now needs to be taken into account.
            e = nz.e - sbits
            if (d == 0) {
                // Exactly `sbits`, nothing to do
            } else if ((kmCompareValues(d, sbits) < 0)) {
                // Remaining shift fits within `sbits`. Leave `z` in place, shift `x * y`
                rlo = (rhi shl (sbits - d).toInt()) or (rlo shr d.toInt())
                // Set the sticky bit
                rlo = rlo or IntTy.from((rlo shl (sbits - d).toInt()) != zero)
                rhi = rhi shr d.toInt()
            } else {
                // `z`s magnitude is enough that `x * y` is irrelevant. It was nonzero, so set
                // the sticky bit.
                rlo = one
                rhi = zero
            }
        }
    } else {
        // `z`s magnitude once shifted fits entirely within `zlo`
        zhi = zero
        d = -d
        if (d == 0) {
            // No shift needed
            zlo = nz.m
        } else if ((kmCompareValues(d, sbits) < 0)) {
            // Shift s.t. `nz.m` fits into `zlo`
            val sticky = IntTy.from((nz.m shl (sbits - d).toInt()) != zero)
            zlo = (nz.m shr d.toInt()) or sticky
        } else {
            // Would be entirely shifted out, only set the sticky bit
            zlo = one
        }
    }
    // addition
    var neg = nx.neg xor ny.neg
    val samesign: Boolean = neg.inv() xor nz.neg
    var rhiNonzero = true
    if (samesign) {
        // r += z
        rlo = rlo.wrappingAdd(zlo)
        rhi += zhi + IntTy.from(rlo < zlo)
    } else {
        // r -= z
        val (res, borrow) = rlo.overflowingSub(zlo)
        rlo = res
        rhi = rhi.wrappingSub((zhi + IntTy.from(borrow).toInt()))
        if ((rhi shr (F.BITS - 1).toInt()) != zero) {
            rlo = rlo.signed().wrappingNeg().unsigned()
            rhi = rhi.signed().wrappingNeg().unsigned() - IntTy.from(rlo != zero)
            neg = !neg
        }
        rhiNonzero = rhi != zero
    }
    // Construct result
    // Shift result into `rhi`, left-aligned. Last bit is sticky
    if (rhiNonzero) {
        // `d` > 0, need to shift both `rhi` and `rlo` into result
        e += sbits
        d = ((rhi.leadingZeros() as Any?) as Int<OtherSign, Unsigned>) - 1
        rhi = (rhi shl d.toInt()) or (rlo shr (sbits - d).toInt())
        // Update sticky
        rhi = rhi or IntTy.from((rlo shl d.toInt()) != zero)
    } else if (rlo != zero) {
        // `rhi` is zero, `rlo` is the entire result and needs to be shifted
        d = ((rlo.leadingZeros() as Any?) as Int<OtherSign, Unsigned>) - 1
        if ((kmCompareValues(d, 0) < 0)) {
            // Shift and set sticky
            rhi = (rlo shr 1) or (rlo and one)
        } else {
            rhi = rlo shl d.toInt()
        }
    } else {
        // exact +/- 0.0
        return FpResult.ok(x * y + z)
    }
    e -= d
    // Use int->float conversion to populate the significand.
    // i is in [1 << (BITS - 2), (1 << (BITS - 1)) - 1]
    var i: SignedInt = rhi.signed()
    if (neg) {
        i = -i
    }
    // `| r |` is in `[0x1p62, 0x1p63]` for `Double`
    var r: F = castFromLossyFFn(i)
    // Account for subnormal and rounding
    // Unbiased exponent for the maximum value of `r`
    val maxPow = F.BITS - 1 + F.EXP_BIAS
    val status = Status.OK
    if ((kmCompareValues(e, -(((maxPow as Any?) as Int<OtherSign, Unsigned>) - 2)) < 0)) {
        // Result is subnormal before rounding
        if (e == -(((maxPow as Any?) as Int<OtherSign, Unsigned>) - 1)) {
            var c = fromPartsFFn(false, maxPow, zero)
            if (neg) {
                c = -c
            }
            if (r == c) {
                // Min normal after rounding,
                status.setUnderflow(true)
                r = F.MIN_POSITIVE_NORMAL.copysign(r)
                return FpResult.new(r, status)
            }
            if ((rhi shl (F.SIG_BITS + 1).toInt()) != zero) {
                // Account for truncated bits. One bit will be lost in the `scalbn` call, add
                // another top bit to avoid double rounding if inexact.
                val iu: Int = (rhi shr 1) or (rhi and one) or (one shl (F.BITS - 2).toInt())
                i = iu.signed()
                if (neg) {
                    i = -i
                }
                r = castFromLossyFFn(i)
                // Remove the top bit
                r = castFromFFn(2) * r - c
                status.setUnderflow(true)
            }
        } else {
            // Only round once when scaled
            d = ((F.EXP_BITS as Any?) as Int<OtherSign, Unsigned>) - 1
            val sticky1 = IntTy.from(rhi shl (((F.BITS as Any?) as Int<OtherSign, Unsigned>) - d).toInt() != zero)
            i = (((rhi shr d.toInt()) or sticky1) shl d.toInt()).signed()
            if (neg) {
                i = -i
            }
            r = castFromLossyFFn(i)
        }
    }
    // Use our exponent to scale the final value.
    return FpResult.new(scalbn(r, e), status)
}

/**
 * Representation of `F` that has handled subnormals.
 */
public open class Norm<F : Float<F, Int, SignedInt>, Int, SignedInt>(
    /**
     * Normalized significand with one guard bit, unsigned.
     */
    public var m: Int,
    /**
     * Exponent of the mantissa such that `m * 2 ^ e = x`. Accounts for the shift in the mantissa
     * and the guard bit; that is, 1.0 will normalize as `m = 1<<53` and `e = - 53`.
     */
    public var e: Int,
    public var neg: Boolean,
) {
    public open fun clone(): Norm<F, Int, SignedInt> = Norm(m, e, neg)

    /**
     * True if the value was zero, infinity, or NaN.
     */
    public open fun isZeroNanInf(): Boolean = this.e >= Norm.ZERO_INF_NAN.toInt()

    /**
     * The only value we have
     */
    public open fun isZero(): Boolean {
        // The only exponent that strictly exceeds this value is our sentinel value for zero.
        return this.e > Norm.ZERO_INF_NAN.toInt()
    }

    public companion object {
        public fun <F : Float<F, Int, SignedInt>, Int, SignedInt> typeLayout(): TypeLayout<Norm<F, Int, SignedInt>> =
            object : TypeLayout<Norm<F, Int, SignedInt>> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Norm<F, Int, SignedInt> =
                    Norm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Norm<F, Int, SignedInt>) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.m)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.e)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 8L, value.neg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Norm<F, Int, SignedInt> =
                    Norm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Norm<F, Int, SignedInt>) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.m)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.e)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 8, value.neg)
                }
            }

        /**
         * Unbias the exponent and account for the mantissa's precision, including the guard bit.
         */
        public val EXP_UNBIAS: UInt = F.EXP_BIAS + F.SIG_BITS + 1u

        /**
         * Values greater than this had a saturated exponent (infinity or NaN), OR were zero and we
         * adjusted the exponent such that it exceeds this threashold.
         */
        public val ZERO_INF_NAN: UInt = F.EXP_SAT - Norm.EXP_UNBIAS

        public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fromFloat(x: F, fromPartsFFn: (Boolean, UInt, Int) -> F): Norm<F, Int, SignedInt> {
            var ix = x.toBits()
            var e = x.ex().toInt()
            val neg = x.isSignNegative()
            if (e == 0) {
                // Normalize subnormals by multiplication
                val scaleI = F.BITS - 1
                val scaleF = fromPartsFFn(false, scaleI + F.EXP_BIAS, F.Int.ZERO)
                val scaled = x * scaleF
                ix = scaled.toBits()
                e = scaled.ex().toInt()
                e = if (e == 0) {
                    // If the exponent is still zero, the input was zero. Artifically set this value
                    // such that the final `e` will exceed `ZERO_INF_NAN`.
                    1 shl F.EXP_BITS.toInt()
                } else {
                    // Otherwise, account for the scaling we just did.
                    e - scaleI.toInt()
                }
            }
            e -= Norm.EXP_UNBIAS.toInt()
            // Absolute  value, set the implicit bit, and shift to create a guard bit
            ix = ix and F.SIG_MASK
            ix = ix or F.IMPLICIT_BIT
            ix = ix shl 1
            return Norm(m = ix.toInt(), e = e, neg = neg)
        }
    }
}
