// port-lint: source num/imp/int_sqrt.rs
package io.github.kotlinmania.rustlang.core.num.imp.intsqrt

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.overflowingMul
import io.github.kotlinmania.rustlang.core.num.overflowingSub
import io.github.kotlinmania.rustlang.core.num.pow

// These functions use the [Karatsuba square root algorithm][1] to compute the
// [integer square root](https://en.wikipedia.org/wiki/IntegerSquareRoot)
// for the primitive integer types.
//
// The signed integer functions can only handle **nonnegative** inputs, so
// that must be checked before calling those.
//
// [1]: <https://web.archive.org/web/20230511212802/https://inria.hal.science/inria-00072854v1/file/RR-3805.pdf>
// "Paul Zimmermann. Karatsuba Square Root. \[Research Report\] RR-3805,
// INRIA. 1999, pp.8. (inria-00072854)"
//
// Generates a `u *` function that returns the [integer square root](
// https://en.wikipedia.org/wiki/IntegerSquareRoot) of any input of
// a specific unsigned integer type.
//
// Generates the first stage of the computation after normalization.
//
// # Safety
//
// `$ n` must be nonzero.
//
// Generates a middle stage of the computation.
//
// # Safety
//
// `$ s` must be nonzero.
//
// Generates the last stage of the computation before denormalization.
//
// # Safety
//
// `$ s` must be nonzero.

/**
 * This array stores the [integer square roots](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) and remainders of each
 * [`u8`](prim@u8) value. For example, `U8_ISQRT_WITH_REMAINDER[17]` will be
 * `(4, 1)` because the integer square root of 17 is 4 and because 17 is 1
 * higher than 4 squared.
 */
public val U8_ISQRT_WITH_REMAINDER: List<Pair<UByte, UByte>> =
    run {
        val result = MutableList(256) { Pair(0, 0) }
        var n: Int = 0
        var isqrtN: Int = 0
        while (n < result.size) {
            result[n] = Pair(isqrtN.toUByte(), (n - isqrtN.pow(2u)).toUByte())
            n += 1
            if (n == (isqrtN + 1).pow(2u)) {
                isqrtN += 1
            }
        }
        result
    }

/**
 * Returns the [integer square root](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) of any [`u8`](prim@u8)
 * input.
 */
public fun u8(n: UByte): UByte = U8_ISQRT_WITH_REMAINDER[n.toInt()].first

/**
 * Takes the normalized [`u16`](prim@u16) input and gets its normalized
 * [integer square root](https://en.wikipedia.org/wiki/IntegerSquareRoot).
 *
 * # Safety
 *
 * `n` must be nonzero.
 */
public fun u16Stages(n: UShort): UShort {
    val (s1, r1) = run {
        val N_SHIFT: UInt = 16u - 8u
        if (false) {
            check(n != 0u.toUShort()) { "`\$n` is  zero in `first_stage!`." }
        }
        val n1 = (n.toUInt() shr (N_SHIFT.toInt())).toUShort()
        val (s, r) = U8_ISQRT_WITH_REMAINDER[n1.toInt()]
        assertUnchecked(s != 0u.toUByte())
        Pair(s, r)
    }
    return run {
        val HALF_BITS: UInt = 16u shr 1
        val QUARTER_BITS: UInt = 16u shr 2
        val LOWER_HALF_1_BITS: UShort = (((1u.toUInt() shl (HALF_BITS.toInt())).toUShort()) - 1u).toUShort()
        if (false) {
            check(s1 != 0) { "`\$s` is  zero in `last_stage!`." }
        }
        val lo = n and LOWER_HALF_1_BITS
        val numerator = (((r1.toUShort()).toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or ((lo.toUInt() shr (QUARTER_BITS.toInt())).toUShort())
        val denominator = ((s1.toUShort()).toUInt() shl 1).toUShort()
        val q = numerator / denominator
        var s2 = (s1 shl QUARTER_BITS.toInt()).toUShort() + q
        val (sSquared, overflow) = s2.overflowingMul(s2)
        if (overflow || sSquared > n) {
            s2 -= 1u
        }
        s2
    }
}

/**
 * Takes the normalized [`u32`](prim@u32) input and gets its normalized
 * [integer square root](https://en.wikipedia.org/wiki/IntegerSquareRoot).
 *
 * # Safety
 *
 * `n` must be nonzero.
 */
public fun u32Stages(n: UInt): UInt {
    val (s1, r1) = run {
        val N_SHIFT: UInt = 32u - 8u
        if (false) {
            check(n != 0u) { "`\$n` is  zero in `first_stage!`." }
        }
        val n1 = n shr N_SHIFT.toInt()
        val (s, r) = U8_ISQRT_WITH_REMAINDER[n1.toInt()]
        assertUnchecked(s != 0u.toUByte())
        Pair(s, r)
    }
    val (s3, r3) = run {
        val N_SHIFT: UInt = 32u - 16u
        val HALF_BITS: UInt = 16u shr 1
        val QUARTER_BITS: UInt = 16u shr 2
        val LOWER_HALF_1_BITS: UShort = (((1u.toUInt() shl (HALF_BITS.toInt())).toUShort()) - 1u).toUShort()
        val LOWEST_QUARTER_1_BITS: UShort = (((1u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) - 1u).toUShort()
        if (false) {
            check(s1 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n2 = (n shr N_SHIFT.toInt()).toUShort()
        val lo = n2 and LOWER_HALF_1_BITS
        val numerator = (((r1.toUShort()).toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or ((lo.toUInt() shr (QUARTER_BITS.toInt())).toUShort())
        val denominator = ((s1.toUShort()).toUInt() shl 1).toUShort()
        val q = numerator / denominator
        val u = numerator % denominator
        var s2 = (s1 shl QUARTER_BITS.toInt()).toUShort() + q
        var (r2, overflow) = (((u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or (lo and LOWEST_QUARTER_1_BITS)).overflowingSub(q * q)
        if (overflow) {
            r2 = (r2 + ((2u * s2 - 1u).toUShort())).toUShort()
            s2 -= 1u
        }
        assertUnchecked(s2 != 0u.toUShort())
        Pair(s2, r2)
    }
    return run {
        val HALF_BITS: UInt = 32u shr 1
        val QUARTER_BITS: UInt = 32u shr 2
        val LOWER_HALF_1_BITS: UInt = (1u shl HALF_BITS.toInt()) - 1u
        if (false) {
            check(s3 != 0) { "`\$s` is  zero in `last_stage!`." }
        }
        val lo1 = n and LOWER_HALF_1_BITS
        val numerator1 = ((r3.toUInt()) shl QUARTER_BITS.toInt()) or (lo1 shr QUARTER_BITS.toInt())
        val denominator1 = (s3.toUInt()) shl 1
        val q1 = numerator1 / denominator1
        var s4 = (s3 shl QUARTER_BITS.toInt()).toUInt() + q1
        val (sSquared, overflow1) = s4.overflowingMul(s4)
        if (overflow1 || sSquared > n) {
            s4 -= 1u
        }
        s4
    }
}

/**
 * Takes the normalized [`u64`](prim@u64) input and gets its normalized
 * [integer square root](https://en.wikipedia.org/wiki/IntegerSquareRoot).
 *
 * # Safety
 *
 * `n` must be nonzero.
 */
public fun u64Stages(n: ULong): ULong {
    val (s1, r1) = run {
        val N_SHIFT: UInt = 64u - 8u
        if (false) {
            check(n != 0uL) { "`\$n` is  zero in `first_stage!`." }
        }
        val n1 = n shr N_SHIFT.toInt()
        val (s, r) = U8_ISQRT_WITH_REMAINDER[n1.toInt()]
        assertUnchecked(s != 0u.toUByte())
        Pair(s, r)
    }
    val (s3, r3) = run {
        val N_SHIFT: UInt = 64u - 16u
        val HALF_BITS: UInt = 16u shr 1
        val QUARTER_BITS: UInt = 16u shr 2
        val LOWER_HALF_1_BITS: UShort = (((1u.toUInt() shl (HALF_BITS.toInt())).toUShort()) - 1u).toUShort()
        val LOWEST_QUARTER_1_BITS: UShort = (((1u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) - 1u).toUShort()
        if (false) {
            check(s1 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n2 = (n shr N_SHIFT.toInt()).toUShort()
        val lo = n2 and LOWER_HALF_1_BITS
        val numerator = (((r1.toUShort()).toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or ((lo.toUInt() shr (QUARTER_BITS.toInt())).toUShort())
        val denominator = ((s1.toUShort()).toUInt() shl 1).toUShort()
        val q = numerator / denominator
        val u = numerator % denominator
        var s2 = (s1 shl QUARTER_BITS.toInt()).toUShort() + q
        var (r2, overflow) = (((u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or (lo and LOWEST_QUARTER_1_BITS)).overflowingSub(q * q)
        if (overflow) {
            r2 = (r2 + ((2u * s2 - 1u).toUShort())).toUShort()
            s2 -= 1u
        }
        assertUnchecked(s2 != 0u.toUShort())
        Pair(s2, r2)
    }
    val (s5, r5) = run {
        val N_SHIFT: UInt = 64u - 32u
        val HALF_BITS: UInt = 32u shr 1
        val QUARTER_BITS: UInt = 32u shr 2
        val LOWER_HALF_1_BITS: UInt = (1u shl HALF_BITS.toInt()) - 1u
        val LOWEST_QUARTER_1_BITS: UInt = (1u shl QUARTER_BITS.toInt()) - 1u
        if (false) {
            check(s3 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n3 = (n shr N_SHIFT.toInt()).toUInt()
        val lo1 = n3 and LOWER_HALF_1_BITS
        val numerator1 = ((r3.toUInt()) shl QUARTER_BITS.toInt()) or (lo1 shr QUARTER_BITS.toInt())
        val denominator1 = (s3.toUInt()) shl 1
        val q1 = numerator1 / denominator1
        val u1 = numerator1 % denominator1
        var s4 = (s3 shl QUARTER_BITS.toInt()).toUInt() + q1
        var (r4, overflow1) = ((u1 shl QUARTER_BITS.toInt()) or (lo1 and LOWEST_QUARTER_1_BITS)).overflowingSub(q1 * q1)
        if (overflow1) {
            r4 = (r4 + (2u * s4 - 1u))
            s4 -= 1u
        }
        assertUnchecked(s4 != 0u)
        Pair(s4, r4)
    }
    return run {
        val HALF_BITS: UInt = 64u shr 1
        val QUARTER_BITS: UInt = 64u shr 2
        val LOWER_HALF_1_BITS: ULong = (1uL shl HALF_BITS.toInt()) - 1uL
        if (false) {
            check(s5 != 0) { "`\$s` is  zero in `last_stage!`." }
        }
        val lo2 = n and LOWER_HALF_1_BITS
        val numerator2 = ((r5.toULong()) shl QUARTER_BITS.toInt()) or (lo2 shr QUARTER_BITS.toInt())
        val denominator2 = (s5.toULong()) shl 1
        val q2 = numerator2 / denominator2
        var s6 = (s5 shl QUARTER_BITS.toInt()).toULong() + q2
        val (sSquared, overflow2) = s6.overflowingMul(s6)
        if (overflow2 || sSquared > n) {
            s6 -= 1uL
        }
        s6
    }
}

/**
 * Takes the normalized [`u128`](prim@u128) input and gets its normalized
 * [integer square root](https://en.wikipedia.org/wiki/IntegerSquareRoot).
 *
 * # Safety
 *
 * `n` must be nonzero.
 */
public fun u128Stages(n: U128): U128 {
    val (s1, r1) = run {
        val N_SHIFT: UInt = 128u - 8u
        if (false) {
            check(n != U128(0x0uL, 0x0uL)) { "`\$n` is  zero in `first_stage!`." }
        }
        val n1 = n shr N_SHIFT.toInt()
        val (s, r) = U8_ISQRT_WITH_REMAINDER[n1.toInt()]
        assertUnchecked(s != 0u.toUByte())
        Pair(s, r)
    }
    val (s3, r3) = run {
        val N_SHIFT: UInt = 128u - 16u
        val HALF_BITS: UInt = 16u shr 1
        val QUARTER_BITS: UInt = 16u shr 2
        val LOWER_HALF_1_BITS: UShort = (((1u.toUInt() shl (HALF_BITS.toInt())).toUShort()) - 1u).toUShort()
        val LOWEST_QUARTER_1_BITS: UShort = (((1u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) - 1u).toUShort()
        if (false) {
            check(s1 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n2 = (n shr N_SHIFT.toInt()).toUShort()
        val lo = n2 and LOWER_HALF_1_BITS
        val numerator = (((r1.toUShort()).toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or ((lo.toUInt() shr (QUARTER_BITS.toInt())).toUShort())
        val denominator = ((s1.toUShort()).toUInt() shl 1).toUShort()
        val q = numerator / denominator
        val u = numerator % denominator
        var s2 = (s1 shl QUARTER_BITS.toInt()).toUShort() + q
        var (r2, overflow) = (((u.toUInt() shl (QUARTER_BITS.toInt())).toUShort()) or (lo and LOWEST_QUARTER_1_BITS)).overflowingSub(q * q)
        if (overflow) {
            r2 = (r2 + ((2u * s2 - 1u).toUShort())).toUShort()
            s2 -= 1u
        }
        assertUnchecked(s2 != 0u.toUShort())
        Pair(s2, r2)
    }
    val (s5, r5) = run {
        val N_SHIFT: UInt = 128u - 32u
        val HALF_BITS: UInt = 32u shr 1
        val QUARTER_BITS: UInt = 32u shr 2
        val LOWER_HALF_1_BITS: UInt = (1u shl HALF_BITS.toInt()) - 1u
        val LOWEST_QUARTER_1_BITS: UInt = (1u shl QUARTER_BITS.toInt()) - 1u
        if (false) {
            check(s3 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n3 = (n shr N_SHIFT.toInt()).toUInt()
        val lo1 = n3 and LOWER_HALF_1_BITS
        val numerator1 = ((r3.toUInt()) shl QUARTER_BITS.toInt()) or (lo1 shr QUARTER_BITS.toInt())
        val denominator1 = (s3.toUInt()) shl 1
        val q1 = numerator1 / denominator1
        val u1 = numerator1 % denominator1
        var s4 = (s3 shl QUARTER_BITS.toInt()).toUInt() + q1
        var (r4, overflow1) = ((u1 shl QUARTER_BITS.toInt()) or (lo1 and LOWEST_QUARTER_1_BITS)).overflowingSub(q1 * q1)
        if (overflow1) {
            r4 = (r4 + (2u * s4 - 1u))
            s4 -= 1u
        }
        assertUnchecked(s4 != 0u)
        Pair(s4, r4)
    }
    val (s7, r7) = run {
        val N_SHIFT: UInt = 128u - 64u
        val HALF_BITS: UInt = 64u shr 1
        val QUARTER_BITS: UInt = 64u shr 2
        val LOWER_HALF_1_BITS: ULong = (1uL shl HALF_BITS.toInt()) - 1uL
        val LOWEST_QUARTER_1_BITS: ULong = (1uL shl QUARTER_BITS.toInt()) - 1uL
        if (false) {
            check(s5 != 0) { "`\$s` is  zero in `middle_stage!`." }
        }
        val n4 = (n shr N_SHIFT.toInt()).toULong()
        val lo2 = n4 and LOWER_HALF_1_BITS
        val numerator2 = ((r5.toULong()) shl QUARTER_BITS.toInt()) or (lo2 shr QUARTER_BITS.toInt())
        val denominator2 = (s5.toULong()) shl 1
        val q2 = numerator2 / denominator2
        val u2 = numerator2 % denominator2
        var s6 = (s5 shl QUARTER_BITS.toInt()).toULong() + q2
        var (r6, overflow2) = ((u2 shl QUARTER_BITS.toInt()) or (lo2 and LOWEST_QUARTER_1_BITS)).overflowingSub(q2 * q2)
        if (overflow2) {
            r6 = (r6 + (2uL * s6 - 1uL))
            s6 -= 1uL
        }
        assertUnchecked(s6 != 0uL)
        Pair(s6, r6)
    }
    return run {
        val HALF_BITS: UInt = 128u shr 1
        val QUARTER_BITS: UInt = 128u shr 2
        val LOWER_HALF_1_BITS: U128 = (U128(0x0uL, 0x1uL) shl HALF_BITS.toInt()) - U128(0x0uL, 0x1uL)
        if (false) {
            check(s7 != 0) { "`\$s` is  zero in `last_stage!`." }
        }
        val lo3 = n and LOWER_HALF_1_BITS
        val numerator3 = ((U128.fromLong(r7.toLong())) shl QUARTER_BITS.toInt()) or (lo3 shr QUARTER_BITS.toInt())
        val denominator3 = (U128.fromLong(s7.toLong())) shl 1
        val q3 = numerator3 / denominator3
        var s8 = U128.fromLong((s7 shl QUARTER_BITS.toInt()).toLong()) + q3
        val (sSquared, overflow3) = s8.overflowingMul(s8)
        if (overflow3 || sSquared > n) {
            s8 -= U128(0x0uL, 0x1uL)
        }
        s8
    }
}

/**
 * Returns the [integer square root](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) of any
 * input.
 */
public fun u16(n: UShort): UShort {
    var n1 = n
    return if (n1 <= UByte.MAX_VALUE.toUShort()) {
        u8(n1.toUByte()).toUShort()
    } else {
        val EVEN_MAKING_BITMASK: UInt = 1u.inv()
        val normalizationShift = n1.leadingZeros() and EVEN_MAKING_BITMASK
        n1 = ((n1).toUInt() shl normalizationShift.toInt()).toUShort()
        val s = u16Stages(n1)
        val denormalizationShift = normalizationShift shr 1
        (s.toUInt() shr (denormalizationShift.toInt())).toUShort()
    }
}

/**
 * Returns the [integer square root](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) of any
 * input.
 */
public fun u32(n: UInt): UInt {
    var n1 = n
    return if (n1 <= UShort.MAX_VALUE.toUInt()) {
        u16(n1.toUShort()).toUInt()
    } else {
        val EVEN_MAKING_BITMASK: UInt = 1u.inv()
        val normalizationShift = n1.leadingZeros() and EVEN_MAKING_BITMASK
        n1 = n1 shl normalizationShift.toInt()
        val s = u32Stages(n1)
        val denormalizationShift = normalizationShift shr 1
        s shr denormalizationShift.toInt()
    }
}

/**
 * Returns the [integer square root](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) of any
 * input.
 */
public fun u64(n: ULong): ULong {
    var n1 = n
    return if (n1 <= UInt.MAX_VALUE.toULong()) {
        u32(n1.toUInt()).toULong()
    } else {
        val EVEN_MAKING_BITMASK: UInt = 1u.inv()
        val normalizationShift = n1.leadingZeros() and EVEN_MAKING_BITMASK
        n1 = n1 shl normalizationShift.toInt()
        val s = u64Stages(n1)
        val denormalizationShift = normalizationShift shr 1
        s shr denormalizationShift.toInt()
    }
}

/**
 * Returns the [integer square root](
 * https://en.wikipedia.org/wiki/IntegerSquareRoot) of any
 * input.
 */
public fun u128(n: U128): U128 {
    var n1 = n
    return if (n1 <= U128.fromULong(ULong.MAX_VALUE)) {
        U128.fromULong(u64(n1.toULong()))
    } else {
        val EVEN_MAKING_BITMASK: UInt = 1u.inv()
        val normalizationShift = n1.leadingZeros() and EVEN_MAKING_BITMASK
        n1 = n1 shl normalizationShift.toInt()
        val s = u128Stages(n1)
        val denormalizationShift = normalizationShift shr 1
        s shr denormalizationShift.toInt()
    }
}

/**
 * Instantiate this panic logic once, rather than for all the isqrt methods
 * on every single primitive type.
 */
public fun panicForNegativeArgument(): Nothing {
    error("argument of integer square root cannot be negative")
}
