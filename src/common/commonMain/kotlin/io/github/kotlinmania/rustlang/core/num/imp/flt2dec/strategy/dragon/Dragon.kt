// port-lint: source num/imp/flt2dec/strategy/dragon.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.flt2dec.strategy.dragon

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.imp.bignum.Big
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.MAX_SIG_DIGITS
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.Decoded
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.estimator.estimateScalingFactor
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.roundUp
import kotlin.experimental.and

// Almost direct (but slightly optimized) Rust translation of Figure 3 of "Printing
// Floating-Point Numbers Quickly and Accurately"[^1].
//
// [^1]: Burger, R. G. and Dybvig, R. K. 1996. Printing floating-point numbers
//   quickly and accurately. SIGPLAN Not. 31, 5 (May. 1996), 108-116.

public var POW10: ULongArray = ulongArrayOf(1uL, 10uL, 100uL, 1000uL, 10000uL, 100000uL, 1000000uL, 10000000uL, 100000000uL, 1000000000uL)

public var POW5TO16: ULongArray = ulongArrayOf(0x2386f26fc1uL)

public var POW5TO32: ULongArray = ulongArrayOf(0x2d6d415b85acef81uL, 0x4eeuL)

public var POW5TO64: ULongArray = ulongArrayOf(0x6e38ed64bf6a1f01uL, 0xe93ff9f4daa797eduL, 0x184f03uL)

public var POW5TO128: ULongArray = ulongArrayOf(0x3df99092e953e01uL, 0x2374e42f0f1538fduL, 0xc404dc08d3cff5ecuL, 0xa6337f19bccdb0dauL, 0x24ee91f2603uL)

public var POW5TO256: ULongArray = ulongArrayOf(0xbed3875b982e7c01uL, 0x12152f87d8d99f72uL, 0xcf4a6e706bde50c6uL, 0x26b2716ed595d80fuL, 0x1d153624adc666b0uL, 0x63ff540e3c42d35auL, 0x65f9ef17cc5573c0uL, 0x80dcc7f755bc28f2uL, 0x5fdcefcef46eeddcuL, 0x553f7uL)

public fun mulPow10(x: Big, n: Int): Big {
    if (false) {
        check(n < 512)
    }
    // Save ourself the left shift for the smallest cases.
    if (n < 8) {
        return x.mulSmall(POW10[n and 7])
    }
    // Multiply by the powers of 5 and shift the 2s in at the end.
    // This keeps the intermediate products smaller and faster.
    if (n and 7 != 0) {
        x.mulSmall(POW10[n and 7] shr (n and 7))
    }
    if (n and 8 != 0) {
        x.mulSmall(POW10[8] shr 8)
    }
    if (n and 16 != 0) {
        x.mulDigits(POW5TO16)
    }
    if (n and 32 != 0) {
        x.mulDigits(POW5TO32)
    }
    if (n and 64 != 0) {
        x.mulDigits(POW5TO64)
    }
    if (n and 128 != 0) {
        x.mulDigits(POW5TO128)
    }
    if (n and 256 != 0) {
        x.mulDigits(POW5TO256)
    }
    return x.mulPow2(n)
}

public fun div2pow10(x: Big, n: Int): Big {
    var n1 = n
    val largest = POW10.size - 1
    while (n1 > largest) {
        x.divRemSmall(POW10[largest])
        n1 -= largest
    }
    x.divRemSmall(POW10[n1] shl 1)
    return x
}

// only usable when `x<16 * scale`; `scaleN` should be `scale.mulSmall(N)`
public fun divRemUpto16(x: Big, scale: Big, scale2: Big, scale4: Big, scale8: Big): Pair<UByte, Big> {
    var d = 0
    if (x >= scale8) {
        x.sub(scale8)
        d += 8
    }
    if (x >= scale4) {
        x.sub(scale4)
        d += 4
    }
    if (x >= scale2) {
        x.sub(scale2)
        d += 2
    }
    if (x >= scale) {
        x.sub(scale)
        d += 1
    }
    if (false) {
        check(x < scale)
    }
    return Pair(d, x)
}

/**
 * The shortest mode implementation for Dragon.
 */
public fun formatShortest(d: Decoded, buf: MutableList<MaybeUninit<UByte>>): Pair<List<UByte>, Short> {
    // the number `v` to format is known to be:
    // - equal to `mant * 2 ^ exp`;
    // - preceded by `(mant - 2 * minus) * 2 ^ exp` in the original type; and
    // - followed by `(mant + 2 * plus) * 2 ^ exp` in the original type.
    //
    // obviously, `minus` and `plus` cannot be zero. (for infinities, we use out-of-range values.)
    // also we assume that at least one digit is generated, i.e., `mant` cannot be zero too.
    //
    // this also means that any number between `low = (mant - minus) * 2 ^ exp` and
    // `high = (mant + plus) * 2 ^ exp` will map to this exact floating point number,
    // with bounds included when the original mantissa was even (i.e., `!mantWasOdd`).
    check(d.mant > 0uL)
    check(d.minus > 0uL)
    check(d.plus > 0uL)
    check(d.mant.checkedAdd(d.plus) != null)
    check(d.mant.checkedSub(d.minus) != null)
    check(buf.size >= MAX_SIG_DIGITS)
    // `a.cmp(b)<rounding` is `if d.inclusive ( a <= b ) else ( a<b )`
    val rounding =
        if (d.inclusive) {
            Ordering.Greater
        } else {
            Ordering.Equal
        }
    // estimate `k0` from original inputs satisfying `10 ^(k0 - 1)<high <= 10 ^(k0 + 1)`.
    // the tight bound `k` satisfying `10 ^(k - 1)<high <= 10 ^ k` is calculated later.
    var k = estimateScalingFactor(d.mant + d.plus, d.exp)
    // convert `( mant, plus, minus ) * 2 ^ exp` into the fractional form so that:
    // - `v = mant / scale`
    // - `low = (mant - minus) / scale`
    // - `high = (mant + plus) / scale`
    val mant = Big.fromU64(d.mant)
    val minus = Big.fromU64(d.minus)
    val plus = Big.fromU64(d.plus)
    val scale = Big.fromSmall(1)
    if (d.exp < 0.toShort()) {
        scale.mulPow2((-d.exp).toInt())
    } else {
        mant.mulPow2(d.exp.toInt())
        minus.mulPow2(d.exp.toInt())
        plus.mulPow2(d.exp.toInt())
    }
    // divide `mant` by `10 ^ k`. now `scale / 10<mant + plus <= scale * 10`.
    if (k >= 0.toShort()) {
        mulPow10(scale, k.toInt())
    } else {
        mulPow10(mant, (-k).toInt())
        mulPow10(minus, (-k).toInt())
        mulPow10(plus, (-k).toInt())
    }
    // fixup when `mant + plus> scale` (or `>=`).
    // we are not actually modifying `scale`, since we can skip the initial multiplication instead.
    // now `scale<mant + plus <= scale * 10` and we are ready to generate digits.
    //
    // note that `d[0]` *can* be zero, when `scale - plus<mant<scale`.
    // in this case rounding-up condition (`up` below) will be triggered immediately.
    if (scale.cmp(mant.clone().add(plus)) < rounding) {
        // equivalent to scaling `scale` by 10
        k += 1
    } else {
        mant.mulSmall(10)
        minus.mulSmall(10)
        plus.mulSmall(10)
    }
    // cache `(2, 4, 8) * scale` for digit generation.
    val scale2 = scale.clone()
    scale2.mulPow2(1)
    val scale4 = scale.clone()
    scale4.mulPow2(2)
    val scale8 = scale.clone()
    scale8.mulPow2(3)
    var down: Any?
    var up: Any?
    var i = 0
    while (true) {
        // invariants, where `d[0 .. n - 1]` are digits generated so far:
        // - `v = mant / scale * 10 ^(k - n - 1) + d[0 .. n - 1] * 10 ^(k - n)`
        // - `v - low = minus / scale * 10 ^(k - n - 1)`
        // - `high - v = plus / scale * 10 ^(k - n - 1)`
        // - `(mant + plus) / scale <= 10` (thus `mant / scale<10`)
        // where `d[i .. j]` is a shorthand for `d[i] * 10 ^(j - i) + ... + d[j - 1] * 10 + d[j]`.
        // generate one digit: `d[n] = floor(mant / scale)<10`.
        val (d1, _) = divRemUpto16(mant, scale, scale2, scale4, scale8)
        if (false) {
            check(d1 < 10)
        }
        buf[i] = MaybeUninit.new(48u.toUByte() + d1, PrimitiveLayouts.U8)
        i += 1
        // this is a simplified description of the modified Dragon algorithm.
        // many intermediate derivations and completeness arguments are omitted for convenience.
        //
        // start with modified invariants, as we've updated `n`:
        // - `v = mant / scale * 10 ^(k - n) + d[0 .. n - 1] * 10 ^(k - n)`
        // - `v - low = minus / scale * 10 ^(k - n)`
        // - `high - v = plus / scale * 10 ^(k - n)`
        //
        // assume that `d[0 .. n - 1]` is the shortest representation between `low` and `high`,
        // i.e., `d[0 .. n - 1]` satisfies both of the following but `d[0 .. n - 2]` doesn't:
        // - `low<d[0 .. n - 1] * 10 ^(k - n)<high` (bijectivity: digits round to `v`); and
        // - `abs(v / 10 ^(k - n) - d[0 .. n - 1]) <= 1 / 2` (the last digit is correct).
        //
        // the second condition simplifies to `2 * mant <= scale`.
        // solving invariants in terms of `mant`, `low` and `high` yields
        // a simpler version of the first condition: `- plus<mant<minus`.
        // since `- plus<0 <= mant`, we have the correct shortest representation
        // when `mant<minus` and `2 * mant <= scale`.
        // (the former becomes `mant <= minus` when the original mantissa is even.)
        //
        // when the second doesn't hold (`2 * mant> scale`), we need to increase the last digit.
        // this is enough for restoring that condition: we already know that
        // the digit generation guarantees `0 <= v / 10 ^(k - n) - d[0 .. n - 1]<1`.
        // in this case, the first condition becomes `- plus<mant - scale<minus`.
        // since `mant<scale` after the generation, we have `scale<mant + plus`.
        // (again, this becomes `scale <= mant + plus` when the original mantissa is even.)
        //
        // in short:
        // - stop and round `down` (keep digits as is) when `mant<minus` (or `<=`).
        // - stop and round `up` (increase the last digit) when `scale<mant + plus` (or `<=`).
        // - keep generating otherwise.
        down = mant.cmp(minus) < rounding
        up = scale.cmp(mant.clone().add(plus)) < rounding
        if (down || up) {
            break
        }
        // we have the shortest representation, proceed to the rounding
        // restore the invariants.
        // this makes the algorithm always terminating: `minus` and `plus` always increases,
        // but `mant` is clipped modulo `scale` and `scale` is fixed.
        mant.mulSmall(10)
        minus.mulSmall(10)
        plus.mulSmall(10)
    }
    // rounding up happens when
    // i) only the rounding-up condition was triggered, or
    // ii) both conditions were triggered and tie breaking prefers rounding up.
    if (up && (!down || mant.mulPow2(1) >= scale)) {
        // if rounding up changes the length, the exponent should also change.
        // it seems that this condition is very hard to satisfy (possibly impossible),
        // but we are just being safe and consistent here.
        // SAFETY: we initialized that memory above.
        when (val matchVal = roundUp(((buf).subList(0, i)).assumeInitMut())) {
            null -> {}
            else -> {
                val c = matchVal
                buf[i] = MaybeUninit.new(c, UnresolvedTypeLayout<Option>())
                i += 1
                k += 1
            }
        }
    }
    // SAFETY: we initialized that memory above.
    return Pair(((buf).subList(0, i)).assumeInitRef(), k)
}

/**
 * The exact and fixed mode implementation for Dragon.
 */
public fun formatExact(d: Decoded, buf: MutableList<MaybeUninit<UByte>>, limit: Short): Pair<List<UByte>, Short> {
    val d1 = d
    check(d1.mant > 0uL)
    check(d1.minus > 0uL)
    check(d1.plus > 0uL)
    check(d1.mant.checkedAdd(d1.plus) != null)
    check(d1.mant.checkedSub(d1.minus) != null)
    // estimate `k0` from original inputs satisfying `10 ^(k0 - 1)<v <= 10 ^(k0 + 1)`.
    var k = estimateScalingFactor(d1.mant, d1.exp)
    // `v = mant / scale`.
    val mant = Big.fromU64(d1.mant)
    var scale = Big.fromSmall(1)
    if (d1.exp < 0.toShort()) {
        scale.mulPow2((-d1.exp).toInt())
    } else {
        mant.mulPow2(d1.exp.toInt())
    }
    // divide `mant` by `10 ^ k`. now `scale / 10<mant <= scale * 10`.
    if (k >= 0.toShort()) {
        mulPow10(scale, k.toInt())
    } else {
        mulPow10(mant, (-k).toInt())
    }
    // fixup when `mant + plus >= scale`, where `plus / scale = 10 ^ - buf.len() / 2`.
    // in order to keep the fixed-size bignum, we actually use `mant + floor(plus) >= scale`.
    // we are not actually modifying `scale`, since we can skip the initial multiplication instead.
    // again with the shortest algorithm, `d[0]` can be zero but will be eventually rounded up.
    if (div2pow10(scale.clone(), buf.size).add(mant) >= scale) {
        // equivalent to scaling `scale` by 10
        k += 1
    } else {
        mant.mulSmall(10)
    }
    // if we are working with the last-digit limitation, we need to shorten the buffer
    // before the actual rendering in order to avoid double rounding.
    // note that we have to enlarge the buffer again when rounding up happens!
    var len =
        if (k < limit) {
            // oops, we cannot even produce *one* digit.
            // this is possible when, say, we've got something like 9.5 and it's being rounded to 10.
            // we return an empty buffer, with an exception of the later rounding-up case
            // which occurs when `k == limit` and has to produce exactly one digit.
            0
        } else if (((k.toInt() - limit.toInt())) < buf.size) {
            (k - limit).toInt()
        } else {
            buf.size
        }
    if (len > 0) {
        // cache `(2, 4, 8) * scale` for digit generation.
        // (this can be expensive, so do not calculate them when the buffer is empty.)
        val scale2 = scale.clone()
        scale2.mulPow2(1)
        val scale4 = scale.clone()
        scale4.mulPow2(2)
        val scale8 = scale.clone()
        scale8.mulPow2(3)
        for (i in 0 until len) {
            if (mant.isZero()) {
                // following digits are all zeroes, we stop here
                // do *not* try to perform rounding! rather, fill remaining digits.
                for (idx in ((buf).subList(i, len)).indices) {
                    ((buf).subList(i, len))[idx] = MaybeUninit.new(48u.toUByte(), UnresolvedTypeLayout<Pair<List<UByte>, Short>>())
                }
                // SAFETY: we initialized that memory above.
                return Pair(((buf).subList(0, len)).assumeInitRef(), k)
            }
            var d2 = 0
            if (mant >= scale8) {
                mant.sub(scale8)
                d2 += 8
            }
            if (mant >= scale4) {
                mant.sub(scale4)
                d2 += 4
            }
            if (mant >= scale2) {
                mant.sub(scale2)
                d2 += 2
            }
            if (mant >= scale) {
                mant.sub(scale)
                d2 += 1
            }
            if (false) {
                check(mant < scale)
            }
            if (false) {
                check(d2 < 10)
            }
            buf[i] = MaybeUninit.new(48u.toUByte() + d2, PrimitiveLayouts.U8)
            mant.mulSmall(10)
        }
    }
    // rounding up if we stop in the middle of digits
    // if the following digits are exactly 5000..., check the prior digit and try to
    // round to even (i.e., avoid rounding up when the prior digit is even).
    val order = mant.cmp(scale.mulSmall(5))
    if (order == Ordering.Greater || (order == Ordering.Equal && len > 0 && buf[len - 1].assumeInit() and 1 == 1)) {
        // if rounding up changes the length, the exponent should also change.
        // but we've been requested a fixed number of digits, so do not alter the buffer...
        // SAFETY: we initialized that memory above.
        when (val matchVal = roundUp(((buf).subList(0, len)).assumeInitMut())) {
            null -> {}
            else -> {
                val c = matchVal
                // ...unless we've been requested the fixed precision instead.
                // we also need to check that, if the original buffer was empty,
                // the additional digit can only be added when `k == limit` (edge case).
                k += 1
                if (k > limit && len < buf.size) {
                    buf[len] = MaybeUninit.new(c, UnresolvedTypeLayout<Option>())
                    len += 1
                }
            }
        }
    }
    // SAFETY: we initialized that memory above.
    return Pair(((buf).subList(0, len)).assumeInitRef(), k)
}
