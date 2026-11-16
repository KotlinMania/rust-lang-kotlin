// port-lint: source num/imp/bignum.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.bignum

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.clone.UseCloned
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.trailingZeros
import io.github.kotlinmania.rustlang.core.option.Iter
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.result.Result

// Custom arbitrary-precision number (bignum) implementation.
//
// This is designed to avoid the heap allocation at expense of stack memory.
// The most used bignum type, `Big32x40` and  `Big64x20` you can choose, is limited by 32 × 40 = 1,280 bits
// and will take at most 160 bytes of stack memory. This is more than enough
// for round-tripping all possible finite `Double` values.
//
// In principle it is possible to have multiple bignum types for different
// inputs, but we don't do so to avoid the code bloat. Each bignum is still
// tracked for the actual usages, so it normally doesn't matter.

/**
 * Arithmetic operations required by bignums.
 */
public interface FullOps<T> {
    /**
     * Returns `(carry', v')` such that `carry' * 2^W + v' = self * other + other2 + carry`,
     * where `W` is the number of bits in `Self`.
     */
    public fun fullMulAdd(other: T, other2: T, carry: T): Pair<T, T>

    /**
     * Returns `(quo, rem)` such that `borrow * 2 ^ W + self = quo * other + rem`
     * and `0 <= rem<other`, where `W` is the number of bits in `Self`.
     */
    public fun fullDivRem(other: T, borrow: T): Pair<T, T>
}

public fun UByte.fullMulAdd(other: UByte, other2: UByte, carry: UByte): Pair<UByte, UByte> {
    val (lo, hi) = this.carryingMulAdd(other, other2, carry)
    return Pair(hi, lo)
}

public fun UByte.fullDivRem(other: UByte, borrow: UByte): Pair<UByte, UByte> {
    if (false) {
        check(borrow < other)
    }
    val lhs = (((borrow.toUShort()).toUInt() shl (8u.toInt())).toUShort()) or (this.toUShort())
    val rhs = other.toUShort()
    return Pair((lhs / rhs).toUByte(), (lhs % rhs).toUByte())
}

public fun UShort.fullMulAdd(other: UShort, other2: UShort, carry: UShort): Pair<UShort, UShort> {
    val (lo, hi) = this.carryingMulAdd(other, other2, carry)
    return Pair(hi, lo)
}

public fun UShort.fullDivRem(other: UShort, borrow: UShort): Pair<UShort, UShort> {
    if (false) {
        check(borrow < other)
    }
    val lhs = ((borrow.toUInt()) shl 16u.toInt()) or (this.toUInt())
    val rhs = other.toUInt()
    return Pair((lhs / rhs).toUShort(), (lhs % rhs).toUShort())
}

public fun UInt.fullMulAdd(other: UInt, other2: UInt, carry: UInt): Pair<UInt, UInt> {
    val (lo, hi) = this.carryingMulAdd(other, other2, carry)
    return Pair(hi, lo)
}

public fun UInt.fullDivRem(other: UInt, borrow: UInt): Pair<UInt, UInt> {
    if (false) {
        check(borrow < other)
    }
    val lhs = ((borrow.toULong()) shl 32u.toInt()) or (this.toULong())
    val rhs = other.toULong()
    return Pair((lhs / rhs).toUInt(), (lhs % rhs).toUInt())
}

public fun ULong.fullMulAdd(other: ULong, other2: ULong, carry: ULong): Pair<ULong, ULong> {
    val (lo, hi) = this.carryingMulAdd(other, other2, carry)
    return Pair(hi, lo)
}

public fun ULong.fullDivRem(other: ULong, borrow: ULong): Pair<ULong, ULong> {
    if (false) {
        check(borrow < other)
    }
    val lhs = ((U128.fromULong(borrow)) shl 64u.toInt()) or (U128.fromULong(this))
    val rhs = U128.fromULong(other)
    return Pair((lhs / rhs).toULong(), (lhs % rhs).toULong())
}

/**
 * Table of powers of 5 representable in digits. Specifically, the largest {u8, u16, u32, u64} value
 * that's a power of five, plus the corresponding exponent. Used in `mulPow5`.
 */
public val SMALL_POW5: List<Pair<ULong, Int>> = listOf(Pair(125, 3), Pair(15625, 6), Pair(1220703125, 13), Pair(7450580596923828125, 27))

/**
 * the digit type for `Big32x40`
 */
public typealias Digit32 = UInt

/**
 * The digit type for `Big64x20`.
 */
public typealias Digit64 = ULong

/**
 * Stack-allocated arbitrary-precision (up to certain limit) integer.
 *
 * This is backed by a fixed-size array of given type ("digit").
 * While the array is not very large (normally some hundred bytes),
 * copying it recklessly may result in the performance hit.
 * Thus this is intentionally not `Copy`.
 *
 * All operations available to bignums panic in the case of overflows.
 * The caller is responsible to use large enough bignum types.
 */
public open class Big64x20(
    /**
     * One plus the offset to the maximum "digit" in use.
     * This does not decrease, so be aware of the computation order.
     * `base[size ..]` should be zero.
     */
    public var size: Int,
    /**
     * Digits. `[a, b, c, ...]` represents `a + b * 2 ^ W + c * 2 ^(2W) + ...`
     * where `W` is the number of bits in the digit type.
     */
    public var base: ULongArray,
) : UseCloned<Big64x20> {
    /**
     * Returns the internal digits as a slice `[a, b, c, ...]` such that the numeric
     * value is `a + b * 2 ^ W + c * 2 ^(2W) + ...` where `W` is the number of bits in
     * the digit type.
     */
    public open fun digits(): List<Digit64> = ((this.base).sliceArray(0 until this.size))

    /**
     * Returns the `i`-th bit where bit 0 is the least significant one.
     * In other words, the bit with weight `2 ^ i`.
     */
    public open fun getBit(i: Int): UByte {
        val digitbits = Digit64.BITS.toInt()
        val d = i / digitbits
        val b = i % digitbits
        return ((this.base[d] shr b) and 1uL).toUByte()
    }

    /**
     * Returns `true` if the bignum is zero.
     */
    public open fun isZero(): Boolean = this.digits().all({ v -> v == 0 })

    /**
     * Returns the number of bits necessary to represent this value. Note that zero
     * is considered to need 0 bits.
     */
    public open fun bitLength(): Int {
        val digitbits = Digit64.BITS.toInt()
        val digits = this.digits()
        val msd = digits.iter().indexOfLast({ x -> x != 0 }).takeIf { it >= 0 }
        return when {
            msd != null -> {
                val msd1 = msd
                msd1 * digitbits + digits[msd1].ilog2().toInt() + 1
            }
            else -> 0
        }
    }

    /**
     * Adds `other` to itself and returns its own mutable reference.
     */
    public open fun add(other: Big64x20): Big64x20 {
        var sz = cmp.max(this.size, other.size)
        var carry = false
        for ((a, b) in run { val subSlice0 = this.base.sliceArray(0 until sz); val subCall0 = Iter.zip(subSlice0, ((other.base).sliceArray(0 until sz))); subSlice0.copyInto(this.base, destinationOffset = 0); subCall0 }) {
            val (v, c) = a.carryingAdd(b, carry)
            a = v
            carry = c
        }
        if (carry) {
            this.base[sz] = 1uL
            sz += 1
        }
        this.size = sz
        return this
    }

    public open fun addSmall(other: Digit64): Big64x20 {
        var (v, carry) = this.base[0].carryingAdd(other, false)
        this.base[0] = v
        var i = 1
        while (carry) {
            val (v1, c) = this.base[i].carryingAdd(0, carry)
            this.base[i] = v1
            carry = c
            i += 1
        }
        if (i > this.size) {
            this.size = i
        }
        return this
    }

    /**
     * Subtracts `other` from itself and returns its own mutable reference.
     */
    public open fun sub(other: Big64x20): Big64x20 {
        val sz = cmp.max(this.size, other.size)
        var noborrow = true
        for ((a, b) in run { val subSlice0 = this.base.sliceArray(0 until sz); val subCall0 = Iter.zip(subSlice0, ((other.base).sliceArray(0 until sz))); subSlice0.copyInto(this.base, destinationOffset = 0); subCall0 }) {
            val (v, c) = a.carryingAdd(!b, noborrow)
            a = v
            noborrow = c
        }
        check(noborrow)
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by a digit-sized `other` and returns its own
     * mutable reference.
     */
    public open fun mulSmall(other: Digit64): Big64x20 {
        var sz = this.size
        var carry = 0
        for (idx in ((this.base).sliceArray(0 until sz)).indices) {
            val (v, c) = ((this.base).sliceArray(0 until sz))[idx].carryingMul(other, carry)
            ((this.base).sliceArray(0 until sz))[idx] = v
            carry = c
        }
        if (carry > 0) {
            this.base[sz] = carry
            sz += 1
        }
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by `2 ^ bits` and returns its own mutable reference.
     */
    public open fun mulPow2(bits: Int): Big64x20 {
        val digitbits = Digit64.BITS.toInt()
        val digits = bits / digitbits
        val bits1 = bits % digitbits
        check(digits < 20)
        if (false) {
            check(((this.base).sliceArray(20 - digits until this.base.size)).all({ v -> v == 0 }))
        }
        if (false) {
            check(bits1 == 0 || (this.base[20 - digits - 1] shr (digitbits - bits1)) == 0uL)
        }
        for (i in (0 until this.size).rev()) {
            this.base[i + digits] = this.base[i]
        }
        for (i1 in 0 until digits) {
            this.base[i1] = 0uL
        }
        var sz = this.size + digits
        if (bits1 > 0) {
            val last = sz
            val overflow = this.base[last - 1] shr (digitbits - bits1)
            if (overflow > 0uL) {
                this.base[last] = overflow
                sz += 1
            }
            for (i2 in (digits + 1 until last).rev()) {
                this.base[i2] = (this.base[i2] shl bits1) or (this.base[i2 - 1] shr (digitbits - bits1))
            }
            this.base[digits] = this.base[digits] shl bits1
        }
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by `5 ^ e` and returns its own mutable reference.
     */
    public open fun mulPow5(e: Int): Big64x20 {
        var e1 = e
        val tableIndex = sizeOf<Digit64>(typeLayoutT = PrimitiveLayouts.U64).trailingZeros().toInt()
        val (smallPower, smallE) = SMALL_POW5[tableIndex]
        val smallPower1 = smallPower.toULong()
        while (e1 >= smallE) {
            this.mulSmall(smallPower1)
            e1 -= smallE
        }
        var restPower = 1
        for (unused in 0 until e1) {
            restPower *= 5
        }
        this.mulSmall(restPower)
        return this
    }

    /**
     * Multiplies itself by a number described by `other[0] + other[1] * 2^W +
     * other[2] * 2^(2W) + ...`(where`W` is the number of bits in the digit type)
     * and returns its own mutable reference.
     */
    public open fun mulDigits(other: List<Digit64>): Big64x20 {
        fun mulInner(ret: ULongArray, aa: List<Digit64>, bb: List<Digit64>): Int {
            var retsz = 0
            for ((i, a) in aa.mapIndexed { index, item -> Pair(index, item) }) {
                if (a == 0) {
                    continue
                }
                var sz = bb.size
                var carry = 0
                for ((j, b) in bb.mapIndexed { index, item -> Pair(index, item) }) {
                    val (c, v) = a.fullMulAdd(b, ret[i + j], carry)
                    ret[i + j] = v
                    carry = c
                }
                if (carry > 0) {
                    ret[i + sz] = carry
                    sz += 1
                }
                if (retsz < i + sz) {
                    retsz = i + sz
                }
            }
            return retsz
        }
        val ret = MutableList(20) { 0 }
        val retsz =
            if (this.size < other.size) {
                mulInner(ret, this.digits(), other)
            } else {
                mulInner(ret, other, this.digits())
            }
        this.base = ret
        this.size = retsz
        return this
    }

    /**
     * Divides itself by a digit-sized `other` and returns its own
     * mutable reference *and* the remainder.
     */
    public open fun divRemSmall(other: Digit64): Pair<Big64x20, Digit64> {
        check(other > 0uL)
        val sz = this.size
        var borrow = 0
        for (a in ((this.base).sliceArray(0 until sz)).iterMut().rev()) {
            val (q, r) = a.fullDivRem(other, borrow)
            a = q
            borrow = r
        }
        return Pair(this, borrow)
    }

    public open fun eq(other: Big64x20): Boolean = this.base.contentEquals(other.base)

    public open fun partialCmp(other: Big64x20): Option<Ordering> = Option.Some(this.cmp(other))

    public open fun cmp(other: Big64x20): Ordering {
        val sz = max(this.size, other.size)
        val lhs = ((this.base).sliceArray(0 until sz)).iter().cloned().rev()
        val rhs = ((other.base).sliceArray(0 until sz)).iter().cloned().rev()
        return lhs.cmp(rhs)
    }

    public override fun clone(): Big64x20 = Big64x20(size = this.size, base = this.base)

    public open fun fmt(f: Formatter): Result {
        val sz =
            if (this.size < 1) {
                1
            } else {
                this.size
            }
        when (val tryResult = f.writeFmt("${this.base[sz - 1]}")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        for (v in ((this.base).sliceArray(0 until sz - 1)).iter().rev()) {
            when (val tryResult = f.writeFmt("_$v")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        }
        return io.github.kotlinmania.rustlang.core.result.Result.Ok(Unit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Big64x20> =
            object : TypeLayout<Big64x20> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 168L
                override fun readNative(addr: Long): Big64x20 =
                    Big64x20(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(20)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Big64x20) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.size)
                    (PrimitiveArrayLayouts.uLongArray(20)).writeNative(addr + 8L, value.base)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Big64x20 =
                    Big64x20(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(20)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Big64x20) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.size)
                    (PrimitiveArrayLayouts.uLongArray(20)).writeBytes(buf, offset + 8, value.base)
                }
            }

        /**
         * Makes a bignum from one digit.
         */
        public fun fromSmall(v: Digit64): Big64x20 {
            var base = MutableList(20) { 0 }
            base[0] = v
            return Big64x20(size = 1, base = base)
        }

        /**
         * Makes a bignum from `ULong` value.
         */
        public fun fromU64(v: ULong): Big64x20 {
            var v1 = v
            var base = MutableList(20) { 0 }
            var sz = 0
            while (v1 > 0uL) {
                base[sz] = v1
                v1 = v1.unboundedShr(Digit64.BITS)
                sz += 1
            }
            return Big64x20(size = sz, base = base)
        }
    }
}

public typealias Big = Big64x20

public typealias Digit = Digit64

/**
 * Stack-allocated arbitrary-precision (up to certain limit) integer.
 *
 * This is backed by a fixed-size array of given type ("digit").
 * While the array is not very large (normally some hundred bytes),
 * copying it recklessly may result in the performance hit.
 * Thus this is intentionally not `Copy`.
 *
 * All operations available to bignums panic in the case of overflows.
 * The caller is responsible to use large enough bignum types.
 */
public open class Big8x3(
    /**
     * One plus the offset to the maximum "digit" in use.
     * This does not decrease, so be aware of the computation order.
     * `base[size ..]` should be zero.
     */
    public var size: Int,
    /**
     * Digits. `[a, b, c, ...]` represents `a + b * 2 ^ W + c * 2 ^(2W) + ...`
     * where `W` is the number of bits in the digit type.
     */
    public var base: UByteArray,
) : UseCloned<Big8x3> {
    /**
     * Returns the internal digits as a slice `[a, b, c, ...]` such that the numeric
     * value is `a + b * 2 ^ W + c * 2 ^(2W) + ...` where `W` is the number of bits in
     * the digit type.
     */
    public open fun digits(): List<UByte> = ((this.base).sliceArray(0 until this.size))

    /**
     * Returns the `i`-th bit where bit 0 is the least significant one.
     * In other words, the bit with weight `2 ^ i`.
     */
    public open fun getBit(i: Int): UByte {
        val digitbits = 8u.toInt()
        val d = i / digitbits
        val b = i % digitbits
        return (((this.base[d].toUInt() shr b).toUByte()) and 1u.toUByte())
    }

    /**
     * Returns `true` if the bignum is zero.
     */
    public open fun isZero(): Boolean = this.digits().all({ v -> v == 0 })

    /**
     * Returns the number of bits necessary to represent this value. Note that zero
     * is considered to need 0 bits.
     */
    public open fun bitLength(): Int {
        val digitbits = 8u.toInt()
        val digits = this.digits()
        val msd = digits.iter().indexOfLast({ x -> x != 0 }).takeIf { it >= 0 }
        return when {
            msd != null -> {
                val msd1 = msd
                msd1 * digitbits + digits[msd1].ilog2().toInt() + 1
            }
            else -> 0
        }
    }

    /**
     * Adds `other` to itself and returns its own mutable reference.
     */
    public open fun add(other: Big8x3): Big8x3 {
        var sz = cmp.max(this.size, other.size)
        var carry = false
        for ((a, b) in run { val subSlice0 = this.base.sliceArray(0 until sz); val subCall0 = Iter.zip(subSlice0, ((other.base).sliceArray(0 until sz))); subSlice0.copyInto(this.base, destinationOffset = 0); subCall0 }) {
            val (v, c) = a.carryingAdd(b, carry)
            a = v
            carry = c
        }
        if (carry) {
            this.base[sz] = 1u
            sz += 1
        }
        this.size = sz
        return this
    }

    public open fun addSmall(other: UByte): Big8x3 {
        var (v, carry) = this.base[0].carryingAdd(other, false)
        this.base[0] = v
        var i = 1
        while (carry) {
            val (v1, c) = this.base[i].carryingAdd(0, carry)
            this.base[i] = v1
            carry = c
            i += 1
        }
        if (i > this.size) {
            this.size = i
        }
        return this
    }

    /**
     * Subtracts `other` from itself and returns its own mutable reference.
     */
    public open fun sub(other: Big8x3): Big8x3 {
        val sz = cmp.max(this.size, other.size)
        var noborrow = true
        for ((a, b) in run { val subSlice0 = this.base.sliceArray(0 until sz); val subCall0 = Iter.zip(subSlice0, ((other.base).sliceArray(0 until sz))); subSlice0.copyInto(this.base, destinationOffset = 0); subCall0 }) {
            val (v, c) = a.carryingAdd(!b, noborrow)
            a = v
            noborrow = c
        }
        check(noborrow)
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by a digit-sized `other` and returns its own
     * mutable reference.
     */
    public open fun mulSmall(other: UByte): Big8x3 {
        var sz = this.size
        var carry = 0
        for (idx in ((this.base).sliceArray(0 until sz)).indices) {
            val (v, c) = ((this.base).sliceArray(0 until sz))[idx].carryingMul(other, carry)
            ((this.base).sliceArray(0 until sz))[idx] = v
            carry = c
        }
        if (carry > 0) {
            this.base[sz] = carry
            sz += 1
        }
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by `2 ^ bits` and returns its own mutable reference.
     */
    public open fun mulPow2(bits: Int): Big8x3 {
        val digitbits = 8u.toInt()
        val digits = bits / digitbits
        val bits1 = bits % digitbits
        check(digits < 3)
        if (false) {
            check(((this.base).sliceArray(3 - digits until this.base.size)).all({ v -> v == 0 }))
        }
        if (false) {
            check(bits1 == 0 || ((this.base[3 - digits - 1].toUInt() shr (digitbits - bits1)).toUByte()) == 0u.toUByte())
        }
        for (i in (0 until this.size).rev()) {
            this.base[i + digits] = this.base[i]
        }
        for (i1 in 0 until digits) {
            this.base[i1] = 0u
        }
        var sz = this.size + digits
        if (bits1 > 0) {
            val last = sz
            val overflow = (this.base[last - 1].toUInt() shr (digitbits - bits1)).toUByte()
            if (overflow > 0u.toUByte()) {
                this.base[last] = overflow
                sz += 1
            }
            for (i2 in (digits + 1 until last).rev()) {
                this.base[i2] = ((this.base[i2].toUInt() shl bits1).toUByte()) or ((this.base[i2 - 1].toUInt() shr (digitbits - bits1)).toUByte())
            }
            this.base[digits] = this.base[digits] shl bits1
        }
        this.size = sz
        return this
    }

    /**
     * Multiplies itself by `5 ^ e` and returns its own mutable reference.
     */
    public open fun mulPow5(e: Int): Big8x3 {
        var e1 = e
        val tableIndex = sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8).trailingZeros().toInt()
        val (smallPower, smallE) = SMALL_POW5[tableIndex]
        val smallPower1 = smallPower.toUByte()
        while (e1 >= smallE) {
            this.mulSmall(smallPower1)
            e1 -= smallE
        }
        var restPower = 1
        for (unused in 0 until e1) {
            restPower *= 5
        }
        this.mulSmall(restPower)
        return this
    }

    /**
     * Multiplies itself by a number described by `other[0] + other[1] * 2^W +
     * other[2] * 2^(2W) + ...`(where`W` is the number of bits in the digit type)
     * and returns its own mutable reference.
     */
    public open fun mulDigits(other: List<UByte>): Big8x3 {
        fun mulInner(ret: UByteArray, aa: List<UByte>, bb: List<UByte>): Int {
            var retsz = 0
            for ((i, a) in aa.mapIndexed { index, item -> Pair(index, item) }) {
                if (a == 0) {
                    continue
                }
                var sz = bb.size
                var carry = 0
                for ((j, b) in bb.mapIndexed { index, item -> Pair(index, item) }) {
                    val (c, v) = a.fullMulAdd(b, ret[i + j], carry)
                    ret[i + j] = v
                    carry = c
                }
                if (carry > 0) {
                    ret[i + sz] = carry
                    sz += 1
                }
                if (retsz < i + sz) {
                    retsz = i + sz
                }
            }
            return retsz
        }
        val ret = MutableList(3) { 0 }
        val retsz =
            if (this.size < other.size) {
                mulInner(ret, this.digits(), other)
            } else {
                mulInner(ret, other, this.digits())
            }
        this.base = ret
        this.size = retsz
        return this
    }

    /**
     * Divides itself by a digit-sized `other` and returns its own
     * mutable reference *and* the remainder.
     */
    public open fun divRemSmall(other: UByte): Pair<Big8x3, UByte> {
        check(other > 0u.toUByte())
        val sz = this.size
        var borrow = 0
        for (a in ((this.base).sliceArray(0 until sz)).iterMut().rev()) {
            val (q, r) = a.fullDivRem(other, borrow)
            a = q
            borrow = r
        }
        return Pair(this, borrow)
    }

    public open fun eq(other: Big8x3): Boolean = this.base.contentEquals(other.base)

    public open fun partialCmp(other: Big8x3): Option<Ordering> = Option.Some(this.cmp(other))

    public open fun cmp(other: Big8x3): Ordering {
        val sz = max(this.size, other.size)
        val lhs = ((this.base).sliceArray(0 until sz)).iter().cloned().rev()
        val rhs = ((other.base).sliceArray(0 until sz)).iter().cloned().rev()
        return lhs.cmp(rhs)
    }

    public override fun clone(): Big8x3 = Big8x3(size = this.size, base = this.base)

    public open fun fmt(f: Formatter): Result {
        val sz =
            if (this.size < 1) {
                1
            } else {
                this.size
            }
        when (val tryResult = f.writeFmt("${this.base[sz - 1]}")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        for (v in ((this.base).sliceArray(0 until sz - 1)).iter().rev()) {
            when (val tryResult = f.writeFmt("_$v")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        }
        return io.github.kotlinmania.rustlang.core.result.Result.Ok(Unit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Big8x3> =
            object : TypeLayout<Big8x3> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Big8x3 =
                    Big8x3(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(3)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Big8x3) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.size)
                    (PrimitiveArrayLayouts.uByteArray(3)).writeNative(addr + 8L, value.base)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Big8x3 =
                    Big8x3(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(3)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Big8x3) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.size)
                    (PrimitiveArrayLayouts.uByteArray(3)).writeBytes(buf, offset + 8, value.base)
                }
            }

        /**
         * Makes a bignum from one digit.
         */
        public fun fromSmall(v: UByte): Big8x3 {
            var base = MutableList(3) { 0 }
            base[0] = v
            return Big8x3(size = 1, base = base)
        }

        /**
         * Makes a bignum from `ULong` value.
         */
        public fun fromU64(v: ULong): Big8x3 {
            var v1 = v
            var base = MutableList(3) { 0 }
            var sz = 0
            while (v1 > 0uL) {
                base[sz] = v1.toUByte()
                v1 = v1.unboundedShr(8u)
                sz += 1
            }
            return Big8x3(size = sz, base = base)
        }
    }
}
