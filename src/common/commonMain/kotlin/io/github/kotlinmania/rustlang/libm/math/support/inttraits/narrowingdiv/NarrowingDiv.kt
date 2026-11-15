// port-lint: source math/support/int_traits/narrowing_div.rs
package io.github.kotlinmania.rustlang.libm.math.support.inttraits.narrowingdiv

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.libm.math.support.big.U256
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.DInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.HInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.MinInt

/**
 * Trait for unsigned division of a double-wide integer
 * when the quotient doesn't overflow.
 *
 * This is the inverse of widening multiplication:
 *  - for any `x` and nonzero `y`: `x.widenMul(y).checkedNarrowingDivRem(y) == Some((x, 0))`,
 *  - and for any `r in 0 .. y`: `x.carryingMul(y, r).checkedNarrowingDivRem(y) == Some((x, r))`,
 */
public interface NarrowingDiv<T> : DInt<T, Any?>, MinInt<T, Any?, T> {
    /**
     * Computes `(self / n, self % n))`
     *
     * # Safety
     * The caller must ensure that `self.hi()<n`, or equivalently,
     * that the quotient does not overflow.
     */
    public fun uncheckedNarrowingDivRem(n: H): Pair<H, H>

    /**
     * Returns `Some((self / n, self % n))` when `self.hi()<n`.
     */
    public fun checkedNarrowingDivRem(n: H): Pair<H, H>? =
        if (this.hi() < n) {
            this.uncheckedNarrowingDivRem(n)
        } else {
            null
        }
}

public fun UShort.uncheckedNarrowingDivRem(n: H): Pair<H, H> {
    if (this.hi() >= n) {
        unreachableUnchecked()
    }
    return Pair((this / n.widen()).cast(), (this % n.widen()).cast())
}

public fun UInt.uncheckedNarrowingDivRem(n: H): Pair<H, H> {
    if (this.hi() >= n) {
        unreachableUnchecked()
    }
    return Pair((this / n.widen()).cast(), (this % n.widen()).cast())
}

public fun ULong.uncheckedNarrowingDivRem(n: H): Pair<H, H> {
    if (this.hi() >= n) {
        unreachableUnchecked()
    }
    return Pair((this / n.widen()).cast(), (this % n.widen()).cast())
}

public fun U128.uncheckedNarrowingDivRem(n: H): Pair<H, H> {
    if (this.hi() >= n) {
        unreachableUnchecked()
    }
    return Pair((this / n.widen()).cast(), (this % n.widen()).cast())
}

public fun U256.uncheckedNarrowingDivRem(n: H): Pair<H, H> {
    if (this.hi() >= n) {
        unreachableUnchecked()
    }
    val lz = n.leadingZeros()
    val a = this shl lz.toInt()
    val b = n shl lz.toInt()
    val ah = a.hi()
    val (a0, a1) = a.lo().loHi()
    val (q1, r) = divThreeDigitsByTwo(a1, ah, b)
    val (q0, r1) = divThreeDigitsByTwo(a0, r, b)
    return Pair(U256.H.fromLoHi(q0, q1), r1 shr lz.toInt())
}

/**
 * Implement `u3N / u2N`-division on top of `u2N / uN`-division.
 *
 * Returns the quotient and remainder of `(a * R + a0) / n`,
 * where `R = (1<<U.BITS)` is the digit size.
 *
 * # Safety
 * Requires that `n.leadingZeros() == 0` and `a<n`.
 */
public fun <D, U> divThreeDigitsByTwo(a0: U, a: D, n: D): Pair<U, D> where U : HInt<U, Any?> {
    if (n.leadingZeros() > 0u || a >= n) {
        unreachableUnchecked()
    }
    // n = n1R + n0
    val (n0, n1) = n.loHi()
    // a = a2R + a1
    val (a1, a2) = a.loHi()
    var q: Any?
    var r: Any?
    var wrap: Any?
    // `a<n` is guaranteed by the caller, but `a2 == n1 && a1<n0` is possible
    when (val matchVal = a.checkedNarrowingDivRem(n1)) {
        null -> {
            check(a2 == n1 && a1 < n0)
            // Otherwise, `a2 == n1`, and the estimated quotient would be
            // `R + (a1 % n1)`, but the correct quotient can't overflow.
            // We'll start from `q = R = (1<<U.BITS)`,
            // so `r = aR + a0 - qn = (a - n) R + a0`
            r = U.D.fromLoHi(a0, a1.wrappingSub(n0))
            // Since `a<n`, the first decrement is always needed:
            q = U.MAX
            // R - 1
        }
        else -> {
            val q0 = matchVal.first
            val r1 = matchVal.second
            q = q0
            // a = qn1 + r1, where 0 <= r1 < n1
            // Include the remainder with the low bits:
            // r = a0 + r1R
            r = U.D.fromLoHi(a0, r1)
            // Subtract the contribution of the divisor low bits with the estimated quotient
            val d = q.widenMul(n0)
            Pair(r, wrap) = r.overflowingSub(d)
            // Since `q` is the quotient of dividing with a slightly smaller divisor,
            // it may be an overapproximation, but is never too small, and similarly,
            // `r` is now either the correct remainder ...
            if (!wrap) {
                return Pair(q, r)
            }
            // ... or the remainder went "negative" (by as much as `d = qn0<RR`)
            // and we have to adjust.
            q -= U.ONE
        }
    }
    Pair(r, wrap) = r.overflowingAdd(n)
    if (wrap) {
        return Pair(q, r)
    }
    // If the remainder still didn't wrap, we need another step.
    q -= U.ONE
    Pair(r, wrap) = r.overflowingAdd(n)
    // Since `n >= RR / 2`, at least one of the two `r += n` must have wrapped.
    check(wrap) { "estimated quotient should be off by at most two" }
    return Pair(q, r)
}
