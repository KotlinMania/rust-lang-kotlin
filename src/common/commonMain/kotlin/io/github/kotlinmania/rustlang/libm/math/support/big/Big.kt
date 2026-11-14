// port-lint: source math/support/big.rs
package io.github.kotlinmania.rustlang.libm.math.support.big

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustOverflowingAdd
import io.github.kotlinmania.rustlang.core.num.rustOverflowingMul
import io.github.kotlinmania.rustlang.core.num.rustOverflowingSub
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.Not
import io.github.kotlinmania.rustlang.core.ops.bit.Shl
import io.github.kotlinmania.rustlang.core.ops.bit.Shr
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.DInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.MinInt
import io.github.kotlinmania.rustlang.libm.math.support.macros.cfgSelectNofmt

// Integers used for wide operations, larger than `ULong`.

public val U128_LO_MASK: U128 = U128.fromULong(ULong.MAX_VALUE)

/**
 * A 256-bit unsigned integer represented as two 128-bit native-endian limbs.
 */
public open class U256(
    public var hi: U128,
    public var lo: U128,
) : MinInt<U256, Any?, Any?, I256, U256>, BitOr<Any?, U256>, Not<Any?, U256>, Add<U256, U256>, Sub<U256, U256>, Shl<UInt, U256>, Shr<UInt, U256>, DInt<U256, Any?, U128> {
    public open fun clone(): U256 = U256(hi, lo)

    /**
     * Reinterpret as a signed integer
     */
    public open fun signed(): I256 = I256(lo = this.lo, hi = this.hi.toI128())

    public open fun bitor(rhs: U256): U256 {
        this.lo = this.lo or rhs.lo
        this.hi = this.hi or rhs.hi
        return this
    }

    public open fun not(): U256 {
        this.lo = !this.lo
        this.hi = !this.hi
        return this
    }

    public open fun add(rhs: U256): U256 {
        val (lo, carry) = rustOverflowingAdd(this.lo, rhs.lo)
        val (hi, of) = this.hi.carryingAdd(rhs.hi, carry)
        check(!of) { "attempt to add with overflow" }
        return U256(lo = lo, hi = hi)
    }

    public open fun sub(rhs: U256): U256 {
        val (lo, borrow) = rustOverflowingSub(this.lo, rhs.lo)
        val (hi, of) = this.hi.borrowingSub(rhs.hi, borrow)
        check(!of) { "attempt to subtract with overflow" }
        return U256(lo = lo, hi = hi)
    }

    public open fun shl(rhs: UInt): U256 {
        check(rhs < U256.BITS) { "attempt to shift left with overflow" }
        val halfBits = U256.BITS / 2
        val lowMask = halfBits - 1
        val s = rhs and lowMask
        val lo = this.lo
        val hi = this.hi
        this.lo = lo shl s.toInt()
        if (rhs and halfBits == 0u) {
            this.hi = (lo shr (lowMask xor s).toInt() shr 1)
            this.hi = this.hi or (hi shl s.toInt())
        } else {
            this.hi = this.lo
            this.lo = U128(0x0uL, 0x0uL)
        }
        return this
    }

    public open fun shr(rhs: UInt): U256 {
        check(rhs < U256.BITS) { "attempt to shift right with overflow" }
        val halfBits = U256.BITS / 2
        val lowMask = halfBits - 1
        val s = rhs and lowMask
        val lo = this.lo
        val hi = this.hi
        this.hi = hi shr s.toInt()
        if (rhs and halfBits == 0u) {
            this.lo = (hi shl (lowMask xor s).toInt() shl 1)
            this.lo = this.lo or (lo shr s.toInt())
        } else {
            this.lo = this.hi
            this.hi = if (hi < U128(0x0uL, 0x0uL)) {
                (U128(0x0uL, 0x0uL)).inv()
            } else {
                U128(0x0uL, 0x0uL)
            }
        }
        return this
    }

    public override fun lo(): U128 = this.lo

    public override fun hi(): U128 = this.hi

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtLowerHex(f: StringBuilder) {
        cfgSelectNofmt(feature, "compiler-builtins", f, unsupportedBranch, pfx, f, alternate, "0x", "", write, f, "{pfx}{:032x}{:032x}", this, hi, this, lo)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<U256> =
            object : TypeLayout<U256> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): U256 =
                    U256(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr),
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: U256) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr, value.hi)
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr + 16L, value.lo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): U256 =
                    U256(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset),
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: U256) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset, value.hi)
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset + 16, value.lo)
                }
            }

        public val MAX: U256 = U256(lo = U128.MAX_VALUE, hi = U128.MAX_VALUE)

        public val MIN: U256 = U256(lo = U128(0x0uL, 0x0uL), hi = U128(0x0uL, 0x0uL))

        public val SIGNED: Boolean = false

        public val BITS: UInt = 256u

        public val ZERO: U256 = U256(lo = U128(0x0uL, 0x0uL), hi = U128(0x0uL, 0x0uL))

        public val ONE: U256 = U256(lo = U128(0x0uL, 0x1uL), hi = U128(0x0uL, 0x0uL))

        public val MIN1: U256 = U256.MIN

        public val MAX1: U256 = U256.MAX
    }
}

/**
 * A 256-bit signed integer represented as two 128-bit native-endian limbs.
 */
public open class I256(
    public var hi: I128,
    public var lo: U128,
) : MinInt<I256, Any?, Any?, U256, U256>, BitOr<Any?, I256>, Not<Any?, I256>, Add<I256, I256>, Sub<I256, I256>, Shl<UInt, I256>, Shr<UInt, I256>, DInt<I256, Any?, I128> {
    public open fun clone(): I256 = I256(hi, lo)

    /**
     * Reinterpret as an unsigned integer
     */
    public open fun unsigned(): U256 = U256(lo = this.lo, hi = this.hi.toU128())

    public open fun bitor(rhs: I256): I256 {
        this.lo = this.lo or rhs.lo
        this.hi = this.hi or rhs.hi
        return this
    }

    public open fun not(): I256 {
        this.lo = !this.lo
        this.hi = !this.hi
        return this
    }

    public open fun add(rhs: I256): I256 {
        val (lo, carry) = rustOverflowingAdd(this.lo, rhs.lo)
        val (hi, of) = this.hi.carryingAdd(rhs.hi, carry)
        check(!of) { "attempt to add with overflow" }
        return I256(lo = lo, hi = hi)
    }

    public open fun sub(rhs: I256): I256 {
        val (lo, borrow) = rustOverflowingSub(this.lo, rhs.lo)
        val (hi, of) = this.hi.borrowingSub(rhs.hi, borrow)
        check(!of) { "attempt to subtract with overflow" }
        return I256(lo = lo, hi = hi)
    }

    public open fun shl(rhs: UInt): I256 {
        check(rhs < I256.BITS) { "attempt to shift left with overflow" }
        val halfBits = I256.BITS / 2
        val lowMask = halfBits - 1
        val s = rhs and lowMask
        val lo = this.lo
        val hi = this.hi
        this.lo = lo shl s.toInt()
        if (rhs and halfBits == 0u) {
            this.hi = (lo shr (lowMask xor s).toInt() shr 1).toI128()
            this.hi = this.hi or (hi shl s.toInt())
        } else {
            this.hi = this.lo.toI128()
            this.lo = U128(0x0uL, 0x0uL)
        }
        return this
    }

    public open fun shr(rhs: UInt): I256 {
        check(rhs < I256.BITS) { "attempt to shift right with overflow" }
        val halfBits = I256.BITS / 2
        val lowMask = halfBits - 1
        val s = rhs and lowMask
        val lo = this.lo
        val hi = this.hi
        this.hi = hi shr s.toInt()
        if (rhs and halfBits == 0u) {
            this.lo = (hi shl (lowMask xor s).toInt() shl 1).toU128()
            this.lo = this.lo or (lo shr s.toInt())
        } else {
            this.lo = this.hi.toU128()
            this.hi = if (hi < I128(0x0L, 0x0uL)) {
                (I128(0x0L, 0x0uL)).inv()
            } else {
                I128(0x0L, 0x0uL)
            }
        }
        return this
    }

    public override fun lo(): I128 = this.lo.toI128()

    public override fun hi(): I128 = this.hi

    public open fun fmt(f: StringBuilder) {
        f.append((this.unsigned()).toString())
    }

    public open fun fmtLowerHex(f: StringBuilder) {
        f.append((this.unsigned()).toString())
    }

    public companion object {
        public fun typeLayout(): TypeLayout<I256> =
            object : TypeLayout<I256> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): I256 =
                    I256(
                        (PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })).readNative(addr),
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: I256) {
                    (PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })).writeNative(addr, value.hi)
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr + 16L, value.lo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): I256 =
                    I256(
                        (PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })).readBytes(buf, offset),
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: I256) {
                    (PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })).writeBytes(buf, offset, value.hi)
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset + 16, value.lo)
                }
            }

        public val MAX: I256 = I256(lo = U128.MAX_VALUE, hi = I128.MAX_VALUE)

        public val MIN: I256 = I256(lo = U128.MIN_VALUE, hi = I128.MIN_VALUE)

        public val SIGNED: Boolean = true

        public val BITS: UInt = 256u

        public val ZERO: I256 = I256(lo = U128(0x0uL, 0x0uL), hi = I128(0x0L, 0x0uL))

        public val ONE: I256 = I256(lo = U128(0x0uL, 0x1uL), hi = I128(0x0L, 0x0uL))

        public val MIN1: I256 = I256.MIN

        public val MAX1: I256 = I256.MAX
    }
}

public fun U128.widen(): U256 = U256(lo = this, hi = U128(0x0uL, 0x0uL))

public fun U128.zeroWiden(): U256 = this.widen()

public fun U128.zeroWidenMul(rhs: U128): U256 {
    val l0 = this and U128_LO_MASK
    val l1 = rhs and U128_LO_MASK
    val h0 = this shr 64
    val h1 = rhs shr 64
    val pLl: U128 = rustOverflowingMul(l0, l1).first
    val pLh: U128 = rustOverflowingMul(l0, h1).first
    val pHl: U128 = rustOverflowingMul(h0, l1).first
    val pHh: U128 = rustOverflowingMul(h0, h1).first
    val s0 = pHl + (pLl shr 64)
    val s1 = (pLl and U128_LO_MASK) + (s0 shl 64)
    val s2 = pLh + (s1 shr 64)
    val lo = (pLl and U128_LO_MASK) + (s2 shl 64)
    val hi = pHh + (s0 shr 64) + (s2 shr 64)
    return U256(lo = lo, hi = hi)
}

public fun U128.widenMul(rhs: U128): U256 = this.zeroWidenMul(rhs)

public fun U128.widenHi(): U256 = U256(lo = U128(0x0uL, 0x0uL), hi = this)

public fun I128.widen(): I256 =
    I256(lo = this.toU128(), hi = if (this < I128(0x0L, 0x0uL)) {
        -I128(0x0L, 0x1uL)
    } else {
        I128(0x0L, 0x0uL)
    })

public fun I128.zeroWiden(): I256 = this.unsigned().zeroWiden().signed()

public fun I128.zeroWidenMul(rhs: I128): I256 = this.unsigned().zeroWidenMul(rhs.unsigned()).signed()

public fun I128.widenMul(rhs: I128): I256 {
    error("signed i128 widening multiply is not used")
}

public fun I128.widenHi(): I256 = I256(lo = U128(0x0uL, 0x0uL), hi = this)
