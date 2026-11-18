// port-lint: source math/support/modular.rs
package io.github.kotlinmania.rustlang.libm.math.support.modular

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.HInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

// This module provides accelerated modular multiplication by large powers
// of two, which is needed for computing floating point remainders in `fmod`
// and similar functions.
//
// To keep the equations somewhat concise, the following conventions are used:
//  - all integer operations are in the mathematical sense, without overflow
//  - concatenation means multiplication: `2xq = 2 * x * q`
//  - `R = (1<<U.BITS)` is the modulus of wrapping arithmetic in `U`

/**
 * Compute the remainder `(x<<e) % y` with unbounded integers.
 * Requires `x<2y` and `y.leadingZeros() >= 2`
 */
public fun <D, OtherSign, Unsigned, H, U> linearMulReduction(x: U, e: UInt, y: U): U where U : HInt<U, D, OtherSign, Unsigned>, U : Int<U, OtherSign, U> {
    var e1 = e
    var y1 = y
    check((kmCompareValues(y1, U.MAX shr 2) <= 0))
    check((kmCompareValues(x, (y1 shl 1)) < 0))
    val v0 = U.ZERO
    val v1 = U.ONE
    // power of two divisors
    if ((y1 and (y1 - v1)).isZero()) {
        if (e1 < U.BITS) {
            // shift and only keep low bits
            return (x shl e1.toInt()) and (y1 - v1)
        } else {
            // would shift out all the bits
            return v0
        }
    }
    // Use the identity `(x<<e) % y == ((x<<(e + s)) %(y<<s))>> s`
    // to shift the divisor so it has exactly two leading zeros to satisfy
    // the precondition of `Reducer.new`
    val s = y1.leadingZeros() - 2
    e1 += s
    y1 = y1 shl s.toInt()
    // `m: Reducer` keeps track of the remainder `x` in a form that makes it
    // very efficient to do `x< <= k` modulo `y` for integers `k<U.BITS`
    val m = Reducer.new(x, y1)
    // Use the faster special case with constant `k == U.BITS - 1` while we can
    while (e1 >= U.BITS - 1u) {
        m.wordReduce()
        e1 -= U.BITS - 1u
    }
    // Finish with the variable shift operation
    m.shiftReduce(e1)
    // The partial remainder is in `[0, 2y)` ...
    val r = m.partialRemainder()
    // ... so check and correct, and compensate for the earlier shift.
    return (r.checkedSub(y1) ?: r) shr s.toInt()
}

/**
 * Helper type for computing the reductions. The implementation has a number
 * of seemingly weird choices, but everything is aimed at streamlining
 * `Reducer.wordReduce` into its current form.
 *
 * Implicitly contains:
 *  n in (R/8, R/4)
 *  x in [0, 2n)
 * The value of `n` is fixed for a given `Reducer`,
 * but the value of `x` is modified by the methods.
 */
public open class Reducer<U : HInt<U, D, OtherSign, Unsigned>, D, OtherSign, Unsigned>(
    // m = 2n
    public var m: U,
    // q = (RR/2) / m
    // r = (RR/2) % m
    // Then RR/2 = qm + r, where `0 <= r<m`
    // The value `q` is only needed during construction, so isn't saved.
    public var r: U,
    // The value `x` is implicitly stored as `2 * q * x`:
    public var v2xq: D,
) {
    public open fun clone(): Reducer<U, D, OtherSign, Unsigned> = Reducer(m, r, v2xq)

    /**
     * Extract the current remainder `x` in the range `[0, 2n)`
     */
    public open fun partialRemainder(): U {
        // `RR / 2 = qm + r`, where `0 <= r<m`
        // `2xq = uR + v`,  where `0 <= v<R`
        // The goal is to extract the current value of `x` from the value `2xq`
        // that we actually have. A bit simplified, we could multiply it by `m`
        // to obtain `2xqm == 2x(RR / 2 - r) == xRR - 2xr`, where `2xr<RR`.
        // We could just round that up to the next multiple of `RR` to get `x`,
        // but we can avoid having to multiply the full double-wide `2xq` by
        // making a couple of adjustments:
        // First, let's only use the high half `u` for the product, and
        // include an additional error term due to the truncation:
        // `mu = xR - (2xr + mv) / R`
        // Next, show bounds for the error term
        // `0 <= mv<mR` follows from `0 <= v<R`
        // `0 <= 2xr<mR` follows from `0 <= x<m<R / 2` and `0 <= r<m`
        // Adding those together, we have:
        // `0 <= (mv + 2xr) / R<2m`
        // Which also implies:
        // `0<2m - (mv + 2xr) / R <= 2m<R`
        // For that reason, we can use `u + 2` as the factor to obtain
        // `m(u + 2) = xR + (2m - (mv + 2xr) / R)`
        // By the previous inequality, the second term fits neatly in the lower
        // half, so we get exactly `x` as the high half.
        val u = this.v2xq.hi()
        val v2 = U.ONE + U.ONE
        return this.m.widenMul(u + v2).hi()
        // Additionally, we should ensure that `u + 2` cannot overflow:
        // Since `x<m` and `2qm <= RR`,
        // `2xq <= 2q(m - 1) <= RR - 2q`
        // As we also have `q> R`,
        // `2xq<RR - 2R`
        // which is sufficient.
    }

    /**
     * Replace the remainder `x` with `(x<<k) - un`,
     * for a suitable quotient `u`, which is returned.
     *
     * Requires that `k<U.BITS`.
     */
    public open fun shiftReduce(k: UInt): U {
        check(k < U.BITS)
        // First, split the shifted value:
        // `2xq<<k = aRR / 2 + b`, where `0 <= b<RR / 2`
        val a = this.v2xq.hi() shr (U.BITS - 1u - k).toInt()
        val (low, high) = (this.v2xq shl k.toInt()).loHi()
        val b = U.D.fromLoHi(low, high and (U.MAX shr 1))
        // Then, subtract `2anq = aqm`:
        // `
        // (2xq << k) - aqm
        // = aRR/2 + b - aqm
        // = a(RR/2 - qm) + b
        // = ar + b
        // `
        this.v2xq = a.widenMul(this.r) + b
        return a
        // Since `a` is at most the high half of `2xq`, we have
        // `a + 2<R` (shown above, in `partialRemainder`)
        // Using that together with `b<RR / 2` and `r<m<R / 2`,
        // we get `(a + 2) r + b<RR`, so
        // `ar + b<RR - 2r = 2mq`
        // which shows that the new remainder still satisfies `x<m`.
    }

    // NB: `wordReduce()` is just the special case `shiftReduce(U.BITS - 1)`
    // that optimizes especially well. The correspondence is that `a == u` and
    // `b == (v>> 1).widenHi()`
    //

    /**
     * Replace the remainder `x` with `x(R / 2) - un`,
     * for a suitable quotient `u`, which is returned.
     */
    public open fun wordReduce(widenHiUFn: (U) -> D): U {
        // To do so, we replace `2xq = uR + v` with
        // `
        // 2 * (x(R/2) - un) * q
        // = xqR - 2unq
        // = xqR - uqm
        // = uRR/2 + vR/2 - uRR/2 + ur
        // = ur + (v/2)R
        // `
        val (v, u) = this.v2xq.loHi()
        this.v2xq = u.widenMul(this.r) + widenHiUFn(v shr 1)
        return u
        // Additional notes:
        // 1. As `v` is the low bits of `2xq`, it is even and can be halved.
        // 2. The new remainder is `(xr + mv / 2) / R` (see below)
        // and since `v<R`, `r<m`, `x<m<R / 2`,
        // that is also strictly less than `m`.
        // `
        // (x(R/2) - un)R
        // = xRR/2 - (m/2)uR
        // = x(qm + r) - (m/2)(2xq - v)
        // = xqm + xr - xqm + mv/2
        // = xr + mv/2
        // `
    }

    public companion object {
        /**
         * Construct a reducer for `(x<<_) mod n`.
         *
         * Requires `R / 8<n<R / 4` and `x<2n`.
         */
        public fun <D, OtherSign, Unsigned, H, U : HInt<U, D, OtherSign, Unsigned>> new(x: U, n: U): Reducer<U, D, OtherSign, Unsigned> where U : HInt<U, D, OtherSign, Unsigned>, U : Int<U, OtherSign, U> {
            val v1 = U.ONE
            check(n > (v1 shl (U.BITS - 3).toInt()))
            check(n < (v1 shl (U.BITS - 2).toInt()))
            val m = n shl 1
            check(x < m)
            // We need to compute the parameters
            // `q = (RR / 2) / m`
            // `r = (RR / 2) % m`
            // Since `m` is in `(R / 4, R / 2)`, the quotient `q` is in `[R, 2R)`, and
            // it would overflow in `U` if computed directly. Instead, we compute
            // `f = q - R`, which is in `[0, R)`. To do so, we simply subtract `Rm`
            // from the dividend, which doesn't change the remainder:
            // `f = R(R / 2 - m) / m`
            // `r = R(R / 2 - m) % m`
            val dividend = ((v1 shl (U.BITS - 1).toInt()) - m).widenHi()
            val (f, r) = dividend.checkedNarrowingDivRem(m).unwrap()
            // As `x<m`, `xq<qm <= RR / 2`
            // Thus `2xq = 2xR + 2xf` does not overflow in `U.D`.
            val v2x = x + x
            val v2xq = v2x.widenHi() + v2x.widenMul(f)
            return Reducer(m = m, r = r, v2xq = v2xq)
        }
    }
}
