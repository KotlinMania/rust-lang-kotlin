@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num

import io.github.kotlinmania.rustlang.core.ascii.EscapeDefault
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphanumeric
import io.github.kotlinmania.rustlang.core.char.isAsciiControl
import io.github.kotlinmania.rustlang.core.char.isAsciiDigit
import io.github.kotlinmania.rustlang.core.char.isAsciiGraphic
import io.github.kotlinmania.rustlang.core.char.isAsciiHexdigit
import io.github.kotlinmania.rustlang.core.char.isAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.isAsciiPunctuation
import io.github.kotlinmania.rustlang.core.char.isAsciiUppercase
import io.github.kotlinmania.rustlang.core.char.isAsciiWhitespace
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.convert.BoundedCastFromInt
import io.github.kotlinmania.rustlang.core.convert.CheckedCastFromInt
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.CarryingMulAdd
import io.github.kotlinmania.rustlang.core.intrinsics.CarrylessMul
import io.github.kotlinmania.rustlang.core.intrinsics.addWithOverflow
import io.github.kotlinmania.rustlang.core.intrinsics.disjointBitor
import io.github.kotlinmania.rustlang.core.intrinsics.exactDiv
import io.github.kotlinmania.rustlang.core.intrinsics.kmBitreverseU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmBswapU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU64
import io.github.kotlinmania.rustlang.core.intrinsics.mulWithOverflow
import io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.intrinsics.subWithOverflow
import io.github.kotlinmania.rustlang.core.intrinsics.threeWayCompare
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedDiv
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedRem
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.core.io.util.Empty
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.error.ParseIntError
import io.github.kotlinmania.rustlang.core.num.imp.intbits.U128
import io.github.kotlinmania.rustlang.core.num.imp.intlog10.I128
import io.github.kotlinmania.rustlang.core.num.imp.intlog10.panicForNonpositiveArgument
import io.github.kotlinmania.rustlang.core.num.imp.intsqrt.panicForNegativeArgument
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.add
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.castInteger
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.mul
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.neg
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.pow
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.rem
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.shl
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.shr
import io.github.kotlinmania.rustlang.core.num.imp.overflowpanic.sub
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.panic.constPanic
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.rustToDigit
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition
import io.github.kotlinmania.rustlang.core.ubchecks.checkLanguageUb
import io.github.kotlinmania.rustlang.core.num.ParseIntError as Pie

/**
 * Determines if a string of text of that length of that radix could be guaranteed to be
 * stored in the given type T.
 * Note that if the radix is known to the compiler, it is just the check of digits.len that
 * is done at runtime.
 */
public fun <T> canNotOverflow(radix: UInt, isSignedTy: Boolean, digits: List<UByte>, typeLayoutT: TypeLayout<T>): Boolean {
    // Assume that `digits` represents a whole number N in base `radix`.
    // Then in infinite precision arithmetic (on whole numbers), we have:
    //
    // |N| <= pow(radix, digits.len()) - 1
    // <= pow(16, 2 * sizeOf::<T>() - isSigned) - 1
    // == pow(2, 8 * sizeOf::<T>() - 4 * isSigned) - 1
    // <= pow(2, 8 * sizeOf::<T>() - isSigned) - 1
    // == T.Max
    //
    // Therefore this condition is sufficient for having no overflow.
    return radix <= 16u && digits.size <= sizeOf<T>(typeLayoutT = typeLayoutT) * 2 - isSignedTy.toInt()
}

public fun fromAsciiBytesRadixPanic(radix: UInt): Nothing = constPanic("from_ascii_bytes_radix: radix must lie in the range `[2, 36]`", "from_ascii_bytes_radix: radix must lie in the range `[2, 36]` - found {radix}", radix, radix)

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN: Byte = (Byte.MAX_VALUE.inv()).toByte()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX: Byte = ((UByte.MAX_VALUE.toUInt() shr 1).toUByte()).toByte()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS: UInt = 8u

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 * }
 * ```
 */
public fun Byte.bit(index: UInt): Boolean = this.castUnsigned().bit(index)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     check(n == 0x2A)
 * }
 * ```
 */
public fun Byte.setBit(index: UInt, value: Boolean): Byte = this.castUnsigned().setBit(index, value).castSigned()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 1)
 * ```
 */
public fun Byte.countOnes(): UInt = rustCountOnes((this.toUByte()))

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun Byte.leadingZeros(): UInt = rustLeadingZeros((this.toUByte()))

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 2)
 * ```
 */
public fun Byte.trailingZeros(): UInt = rustTrailingZeros((this.toUByte()))

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.leadingOnes(): UInt = rustLeadingOnes((this.toUByte()))

/**
 * Returns the number of trailing ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 2)
 * ```
 */
public fun Byte.trailingOnes(): UInt = rustTrailingOnes((this.toUByte()))

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun Byte.isolateHighestOne(): Byte = (this.toInt() and (rustWrappingShr(((1.toByte()) shl (8u - 1u).toInt()), rustLeadingZeros(this))).toInt()).toByte()

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun Byte.isolateLowestOne(): Byte = (this.toInt() and rustWrappingNeg(this).toInt()).toByte()

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.highestOne(): UInt? = (this.toUByte()).highestOne()

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.lowestOne(): UInt? = (this.toUByte()).lowestOne()

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.castUnsigned(): UByte = this.toUByte()

/**
 * Saturating conversion of `self` to an unsigned integer of the same size.
 *
 * Negative values are clamped to `0`.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.saturatingCastUnsigned(): UByte =
    if (this >= 0.toByte()) {
        this.castUnsigned()
    } else {
        0u
    }

/**
 * Checked conversion of `self` to an unsigned integer of the same size,
 * returning `None` if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`saturatingCastUnsigned`](Self.saturatingCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.checkedCastUnsigned(): UByte? =
    if (this >= 0.toByte()) {
        this.castUnsigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to an unsigned integer of the same size,
 * which panics if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`saturatingCastUnsigned`](Self.saturatingCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.strictCastUnsigned(): UByte =
    when (val matchVal = this.checkedCastUnsigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.rotateLeft(n: UInt): Byte = rustRotateLeft((this.toUByte()), n).toByte()

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.rotateRight(n: UInt): Byte = rustRotateRight((this.toUByte()), n).toByte()

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun Byte.swapBytes(): Byte = (this.toUByte()).toByte()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun Byte.reverseBits(): Byte = rustReverseBits((this.toUByte())).toByte()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are swapped.
 *
 * See also [fromBeBytes()](Self.fromBeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Byte.Companion.fromBe(x: Byte): Byte = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are swapped.
 *
 * See also [fromLeBytes()](Self.fromLeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Byte.Companion.fromLe(x: Byte): Byte = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Swaps bytes of `self` on little endian targets.
 *
 * On big endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toBeBytes`](Self.toBeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun Byte.toBe(): Byte = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Swaps bytes of `self` on big endian targets.
 *
 * On little endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toLeBytes`](Self.toLeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun Byte.toLe(): Byte = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedAdd(rhs: Byte): Byte? {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictAdd(rhs: Byte): Byte {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Byte.uncheckedAdd(rhs: Byte): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedAddUnsigned(rhs: UByte): Byte? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictAddUnsigned(rhs: UByte): Byte {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedSub(rhs: Byte): Byte? {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictSub(rhs: Byte): Byte {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Byte.uncheckedSub(rhs: Byte): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedSubUnsigned(rhs: UByte): Byte? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictSubUnsigned(rhs: UByte): Byte {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedMul(rhs: Byte): Byte? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictMul(rhs: Byte): Byte {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Byte.uncheckedMul(rhs: Byte): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None` if `rhs == 0`
 * or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedDiv(rhs: Byte): Byte? =
    if (rhs == 0.toByte() || ((this == Byte.MIN_VALUE) && (rhs == (-1).toByte()))) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to normal division: `MIN / - 1` will also panic both in
 * debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictDiv(rhs: Byte): Byte = (this / rhs).toByte()

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`,
 * returning `None` if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedDivEuclid(rhs: Byte): Byte? =
    if (rhs == 0.toByte() || ((this == Byte.MIN_VALUE) and (rhs == (-1).toByte()))) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to `divEuclid`: `MIN.divEuclid(- 1)` will also panic both
 * in debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictDivEuclid(rhs: Byte): Byte = rustDivEuclid(this, rhs)

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0`, the division results in overflow,
 * or `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.checkedDivExact(rhs: Byte): Byte? =
    if (rhs == 0.toByte() || ((this == Byte.MIN_VALUE) && (rhs == (-1).toByte()))) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.divExact(rhs: Byte): Byte? =
    if (this % rhs != 0.toByte()) {
        null
    } else {
        (this / rhs).toByte()
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0`, `self % rhs != 0`, or
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun Byte.uncheckedDivExact(rhs: Byte): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_div_exact cannot overflow, divide by zero, or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0, lhs, MIN, rhs, 1)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None` if
 * `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedRem(rhs: Byte): Byte? =
    if (rhs == 0.toByte() || ((this == Byte.MIN_VALUE) && (rhs == (-1).toByte()))) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictRem(rhs: Byte): Byte {
    val (a, b) = this.overflowingRem(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Checked Euclidean remainder. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedRemEuclid(rhs: Byte): Byte? =
    if (rhs == 0.toByte() || ((this == Byte.MIN_VALUE) and (rhs == (-1).toByte()))) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean remainder. Computes `self.remEuclid(rhs)`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictRemEuclid(rhs: Byte): Byte {
    val (a, b) = this.overflowingRemEuclid(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(-1)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 *
 * Notably, this prevents both values from being negative.
 */
public fun Byte.uncheckedDisjointBitor(rhs: Byte): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Checked negation. Computes `- self`, returning `None` if `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedNeg(): Byte? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Unchecked negation. Computes `- self`, assuming overflow cannot occur.
 *
 * # Safety
 *
 * This results in undefined behavior when
 * i.e. when [checkedNeg] would return `None`.
 */
public fun Byte.uncheckedNeg(): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_neg cannot overflow", lhs, this, lhs, overflowingNeg, 1)
    return uncheckedSub(0, this)
}

/**
 * Strict negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictNeg(): Byte {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None` if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedShl(rhs: UInt): Byte? =
    if (rhs < 8u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictShl(rhs: UInt): Byte {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun Byte.uncheckedShl(rhs: UInt): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.unboundedShl(rhs: UInt): Byte =
    if (rhs < 8u) {
        this.uncheckedShl(rhs)
    } else {
        0
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any bits that would be shifted out differ from the resulting sign bit
 * or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.shlExact(rhs: UInt): Byte? =
    if (rhs < rustLeadingZeros(this) || rhs < rustLeadingOnes(this)) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs >= self.leadingZeros() && rhs >=
 * self.leadingOnes()` i.e. when
 * would return `None`.
 */
public fun Byte.uncheckedShlExact(rhs: UInt): Byte {
    assertUnsafePrecondition(checkLibraryUb, "i8::unchecked_shl_exact cannot shift out bits that would change the value of the first bit", zeros, this, leadingZeros, ones, this, leadingOnes, rhs, rhs, rhs, zeros, rhs, ones)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None` if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedShr(rhs: UInt): Byte? =
    if (rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictShr(rhs: UInt): Byte {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun Byte.uncheckedShr(rhs: UInt): Byte {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, which yields `0` for a positive number,
 * and `- 1` for a negative number.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.unboundedShr(rhs: UInt): Byte =
    if (rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        this.uncheckedShr(8u - 1)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.shrExact(rhs: UInt): Byte? =
    if (rhs <= rustTrailingZeros(this) && rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun Byte.uncheckedShrExact(rhs: UInt): Byte {
    assertUnsafePrecondition(checkLibraryUb, "i8::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked absolute value. Computes `self.abs()`, returning `None` if
 * `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedAbs(): Byte? =
    if (this.isNegative()) {
        rustCheckedNeg(this)
    } else {
        this
    }

/**
 * Strict absolute value. Computes `self.abs()`, panicking if
 * `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictAbs(): Byte =
    if (this.isNegative()) {
        this.strictNeg()
    } else {
        this
    }

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedPow(exp: UInt): Byte? {
    var exp1 = exp
    var base = this
    var acc: Byte = 1
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return if (base < 0.toByte() && (exp1 % 2u) == 1u) {
            ((-1).toByte()).shlExact(shift)
        } else {
            (1.toByte()).shlExact(shift)
        }
    }
    if (exp1 == 0u) {
        return 1
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Byte.strictPow(exp: UInt): Byte =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * Returns `None` if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedIsqrt(): Byte? =
    if (this < 0.toByte()) {
        null
    } else {
        val result = this.castUnsigned().isqrt().castSigned()
        run {
            val MAX_RESULT: Byte = Byte.MAX_VALUE.castUnsigned().isqrt().castSigned()
            assertUnchecked(result <= MAX_RESULT)
        }
        result
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingAdd(rhs: Byte): Byte = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingAddUnsigned(rhs: UByte): Byte =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> Byte.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingSub(rhs: Byte): Byte = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingSubUnsigned(rhs: UByte): Byte =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> Byte.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer negation. Computes `- self`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingNeg(): Byte = saturatingSub(0, this)

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self ==
 * MIN` instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingAbs(): Byte =
    if (this.isNegative()) {
        rustSaturatingNeg(this)
    } else {
        this
    }

/**
 * Saturating integer multiplication. Computes `self * rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingMul(rhs: Byte): Byte =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> if ((this < 0.toByte()) == (rhs < 0.toByte())) {
            Byte.MAX_VALUE
        } else {
            Byte.MIN_VALUE
        }
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingDiv(rhs: Byte): Byte =
    run {
        val matchVal = this.overflowingDiv(rhs)
        when {
            matchVal.second == false -> {
                val result = matchVal.first
                result
            }
            else -> Byte.MAX_VALUE
        }
    }

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.saturatingPow(exp: UInt): Byte =
    run {
        val matchVal = rustCheckedPow(this, exp)
        when {
            matchVal != null -> {
                val x = matchVal
                x
            }
            matchVal == null && this < 0.toByte() && exp % 2u == 1u -> Byte.MIN_VALUE
            else -> Byte.MAX_VALUE
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingAdd(rhs: Byte): Byte = (this + rhs)

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingAddUnsigned(rhs: UByte): Byte = (this + (rhs.toByte())).toByte()

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingSub(rhs: Byte): Byte = (this - rhs)

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingSubUnsigned(rhs: UByte): Byte = (this - (rhs.toByte())).toByte()

/**
 * Wrapping (modular) multiplication. Computes `self * rhs`, wrapping around at
 * the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check((11 * (12.toByte())).toByte() == -124)
 * ```
 */
public fun Byte.wrappingMul(rhs: Byte): Byte = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`, wrapping around at the
 * boundary of the type.
 *
 * The only case where such wrapping can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); this is equivalent to `- MIN`, a positive value
 * that is too large to represent in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDiv(-1) == -128)
 * ```
 */
public fun Byte.wrappingDiv(rhs: Byte): Byte = this.overflowingDiv(rhs).first

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`,
 * wrapping around at the boundary of the type.
 *
 * Wrapping will only occur in `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value
 * for the type). This is equivalent to `- MIN`, a positive value that is too large to represent in the
 * type. In this case, this method returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDivEuclid(-1) == -128)
 * ```
 */
public fun Byte.wrappingDivEuclid(rhs: Byte): Byte = this.overflowingDivEuclid(rhs).first

/**
 * Wrapping (modular) remainder. Computes `self % rhs`, wrapping around at the
 * boundary of the type.
 *
 * Such wrap-around never actually occurs mathematically; implementation artifacts make `x % y`
 * invalid for `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value). In such a case,
 * this function returns `0`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRem(-1) == 0)
 * ```
 */
public fun Byte.wrappingRem(rhs: Byte): Byte = this.overflowingRem(rhs).first

/**
 * Wrapping Euclidean remainder. Computes `self.remEuclid(rhs)`, wrapping around
 * at the boundary of the type.
 *
 * Wrapping will only occur in `MIN % - 1` on a signed type (where [`Min`](Self.Min) is
 * the negative minimal value for the type). In this case, this method returns 0.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRemEuclid(-1) == 0)
 * ```
 */
public fun Byte.wrappingRemEuclid(rhs: Byte): Byte = this.overflowingRemEuclid(rhs).first

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * The only case where such wrapping can occur is when one negates [`Min`](Self.Min) on a signed type (where [`Min`](Self.Min)
 * is the negative minimal value for the type); this is a positive value that is too large to represent
 * in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingNeg(): Byte = ((0.toByte()) - (this.toByte())).toByte()

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`, where `mask` removes
 * any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the RHS of a wrapping shift-left is restricted to
 * the range of the type, rather than the bits shifted out of the LHS being returned to the other end.
 * The primitive integer types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.wrappingShl(rhs: UInt): Byte = this.uncheckedShl(rhs and (8u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`, where `mask`
 * removes any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the RHS of a wrapping shift-right is restricted
 * to the range of the type, rather than the bits shifted out of the LHS being returned to the other
 * end. The primitive integer types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128).wrappingShr(64u) == -128)
 * ```
 */
public fun Byte.wrappingShr(rhs: UInt): Byte = this.uncheckedShr(rhs and (8u - 1u))

/**
 * Wrapping (modular) absolute value. Computes `self.abs()`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type; this is a positive value that is too large to represent in the type. In
 * such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingAbs().toUByte() == 128u.toUByte())
 * ```
 */
public fun Byte.wrappingAbs(): Byte =
    if (this.isNegative()) {
        rustWrappingNeg(this)
    } else {
        this
    }

/**
 * Computes the absolute value of `self` without any wrapping
 * or panicking.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).unsignedAbs() == 128u.toUByte())
 * ```
 */
public fun Byte.unsignedAbs(): UByte = rustWrappingAbs(this).toUByte()

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.wrappingPow(5) == -13)
 * check(3.wrappingPow(6) == -39)
 * ```
 */
public fun Byte.wrappingPow(exp: UInt): Byte {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would have
 * occurred then the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingAdd(rhs: Byte): Pair<Byte, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toByte(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and checks for overflow.
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns a tuple of the sum along with a boolean indicating
 * whether an arithmetic overflow would occur. On overflow, the wrapped
 * value is returned.
 *
 * This allows chaining together multiple additions to create a wider
 * addition, and can be useful for bignum addition. This method should
 * only be used for the most significant word; for the less significant
 * words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a carry flag,
 * and should *not* be added to a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val carry0 = false
 *     val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 *     check(carry1 == true)
 *     val (sum1, overflow) = a1.carryingAdd(b1, carry1)
 *     check(overflow == false)
 *     check(Pair(sum1, sum0) == Pair(6, 8))
 * }
 * ```
 */
public fun Byte.carryingAdd(rhs: Byte, carry: Boolean): Pair<Byte, Boolean> {
    val (a, b) = rustOverflowingAdd(this, rhs)
    val (c, d) = a.overflowingAdd(carry.toByte())
    return Pair(c, b != d)
}

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingAddUnsigned(rhs: UByte): Pair<Byte, Boolean> {
    val rhs1 = rhs.toByte()
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0.toByte()))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned
 * (negative if overflowed above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingSub(rhs: Byte): Pair<Byte, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toByte(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and checks for
 * overflow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns a tuple of the
 * difference along with a boolean indicating whether an arithmetic
 * overflow would occur. On overflow, the wrapped value is returned.
 *
 * This allows chaining together multiple subtractions to create a
 * wider subtraction, and can be useful for bignum subtraction. This
 * method should only be used for the most significant word; for the
 * less significant words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a borrow flag,
 * and should *not* be subtracted from a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input borrow is false, this method is equivalent to
 * [`overflowingSub`](Self.overflowingSub).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val borrow0 = false
 *     val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 *     check(borrow1 == true)
 *     val (diff1, overflow) = a1.borrowingSub(b1, borrow1)
 *     check(overflow == false)
 * }
 * ```
 */
public fun Byte.borrowingSub(rhs: Byte, borrow: Boolean): Pair<Byte, Boolean> {
    val (a, b) = rustOverflowingSub(this, rhs)
    val (c, d) = a.overflowingSub(borrow.toByte())
    return Pair(c, b != d)
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingSubUnsigned(rhs: UByte): Pair<Byte, Boolean> {
    val rhs1 = rhs.toByte()
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0.toByte()))
}

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * check(1000000000.overflowingMul(10) == Pair(1410065408, true))
 * ```
 */
public fun Byte.overflowingMul(rhs: Byte): Pair<Byte, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toByte(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why [i32] is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMul(-2, 0) == Pair(4294967286, -1))
 *     check(5.carryingMul(-2, 10) == Pair(0, 0))
 *     check(1000000000.carryingMul(-10, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMul(-10, 10) == Pair(2884901898, -3))
 * }
 * ```
 */
public fun Byte.carryingMul(rhs: Byte, carry: Byte): Pair<UByte, Byte> = Byte.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you only need one `carry`, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Int` is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMulAdd(-2, 0, 0) == Pair(4294967286, -1))
 *     check(5.carryingMulAdd(-2, 10, 10) == Pair(10, 0))
 *     check(1000000000.carryingMulAdd(-10, 0, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMulAdd(-10, 10, 10) == Pair(2884901908, -3))
 * }
 * ```
 */
public fun Byte.carryingMulAdd(rhs: Byte, carry: Byte, add: Byte): Pair<UByte, Byte> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then self is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingDiv(rhs: Byte): Pair<Byte, Boolean> =
    if ((this == Byte.MIN_VALUE) and (rhs == (-1).toByte())) {
        Pair(this, true)
    } else {
        Pair(this / rhs, false)
    }

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then `self` is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingDivEuclid(rhs: Byte): Pair<Byte, Boolean> =
    if ((this == Byte.MIN_VALUE) and (rhs == (-1).toByte())) {
        Pair(this, true)
    } else {
        Pair(rustDivEuclid(this, rhs), false)
    }

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingRem(rhs: Byte): Pair<Byte, Boolean> =
    if (rhs == (-1).toByte()) {
        Pair(0, this == Byte.MIN_VALUE)
    } else {
        Pair(this % rhs, false)
    }

/**
 * Overflowing Euclidean remainder. Calculates `self.remEuclid(rhs)`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingRemEuclid(rhs: Byte): Pair<Byte, Boolean> =
    if (rhs == (-1).toByte()) {
        Pair(0, this == Byte.MIN_VALUE)
    } else {
        Pair(rustRemEuclid(this, rhs), false)
    }

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * Returns a tuple of the negated version of self along with a boolean indicating whether an overflow
 * happened. If `self` is the minimum value (e.g., [i32.Min] for values of type [i32]), then the
 * minimum value will be returned again and `true` will be returned for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingNeg(): Pair<Byte, Boolean> =
    if (this == Byte.MIN_VALUE) {
        Pair(Byte.MIN_VALUE, true)
    } else {
        Pair(-this, false)
    }

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x1.overflowingShl(36u) == Pair(0x10, true))
 * ```
 */
public fun Byte.overflowingShl(rhs: UInt): Pair<Byte, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 8u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x10.overflowingShr(36u) == Pair(0x1, true))
 * ```
 */
public fun Byte.overflowingShr(rhs: UInt): Pair<Byte, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 8u)

/**
 * Computes the absolute value of `self`.
 *
 * Returns a tuple of the absolute version of self along with a boolean indicating whether an overflow
 * happened. If self is the minimum value
 * then the minimum value will be returned again and true will be returned
 * for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.overflowingAbs(): Pair<Byte, Boolean> = Pair(rustWrappingAbs(this), this == Byte.MIN_VALUE)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.overflowingPow(5) == Pair(-13, true))
 * ```
 */
public fun Byte.overflowingPow(exp: UInt): Pair<Byte, Boolean> {
    var exp1 = exp
    val base = this
    val acc: Byte  = 1
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        val base1: Byte =
            if (base < 0.toByte() && (exp1 % 2u) != 0u) {
                -1
            } else {
                1
            }
        return Pair(base1.unboundedShl(shift), base1.shlExact(shift) == null)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * check(x.pow(5) == 32)
 * ```
 */
public fun Byte.pow(exp: UInt): Byte =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * # Panics
 *
 * This function will panic if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.isqrt(): Byte =
    when (val matchVal = this.checkedIsqrt()) {
        null -> panicForNegativeArgument()
        else -> {
            val sqrt = matchVal
            sqrt
        }
    }

/**
 * Calculates the quotient of Euclidean division of `self` by `rhs`.
 *
 * This computes the integer `q` such that `self = q * rhs + r`, with
 * `r = self.remEuclid(rhs)` and `0 <= r<abs(rhs)`.
 *
 * In other words, the result is `self / rhs` rounded to the integer `q`
 * such that `self >= q * rhs`.
 * If `self> 0`, this is equal to rounding towards zero (the default in Rust);
 * if `self<0`, this is equal to rounding away from zero (towards +/- infinity).
 * If `rhs> 0`, this is equal to rounding towards -infinity;
 * if `rhs<0`, this is equal to rounding towards +infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.divEuclid(b) == 1)
 * // 7 >= 4 * 1
 * check(a.divEuclid(-b) == -1)
 * // 7 >= -4 * -1
 * check((-a).divEuclid(b) == -2)
 * // -7 >= 4 * -2
 * check((-a).divEuclid(-b) == 2)
 * // -7 >= -4 * 2
 * ```
 */
public fun Byte.divEuclid(rhs: Byte): Byte {
    val q = this / rhs
    if (this % rhs < 0.toByte()) {
        return if (rhs > 0.toByte()) {
            q - 1
        } else {
            q + 1
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
 *
 * This is done as if by the Euclidean division algorithm -- given
 * `r = self.remEuclid(rhs)`, the result satisfies
 * `self = rhs * self.divEuclid(rhs) + r` and `0 <= r<abs(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min] and
 * `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.remEuclid(b) == 3)
 * check((-a).remEuclid(b) == 1)
 * check(a.remEuclid(-b) == 3)
 * check((-a).remEuclid(-b) == 1)
 * ```
 *
 * This will panic:
 * ```kotlin
 * ```
 */
public fun Byte.remEuclid(rhs: Byte): Byte {
    val r = this % rhs
    return if (r < 0.toByte()) {
        (r + rustWrappingAbs(rhs).toByte()).toByte()
    } else {
        r
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divFloor(b) == 2)
 *     check(a.divFloor(-b) == -3)
 *     check((-a).divFloor(b) == -3)
 *     check((-a).divFloor(-b) == 2)
 * }
 * ```
 */
public fun Byte.divFloor(rhs: Byte): Byte {
    val d = this / rhs
    val r = this % rhs
    val correction = ((this.toInt() xor rhs.toInt()).toByte()) shr (8u - 1).toInt()
    return if (r != 0.toByte()) {
        d + correction
    } else {
        d
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divCeil(b) == 3)
 *     check(a.divCeil(-b) == -2)
 *     check((-a).divCeil(b) == -2)
 *     check((-a).divCeil(-b) == 3)
 * }
 * ```
 */
public fun Byte.divCeil(rhs: Byte): Byte {
    val d = this / rhs
    val r = this % rhs
    val correction = 1 + (((this.toInt() xor rhs.toInt()).toByte()) shr (8u - 1).toInt())
    return if (r != 0.toByte()) {
        d + correction
    } else {
        d
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.nextMultipleOf(rhs: Byte): Byte {
    if (rhs == (-1).toByte()) {
        return this
    }
    val r = this % rhs
    val m =
        if ((r > 0.toByte() && rhs < 0.toByte()) || (r < 0.toByte() && rhs > 0.toByte())) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        this + (rhs - m)
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`. Returns `None` if `rhs` is zero or the operation
 * would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.checkedNextMultipleOf(rhs: Byte): Byte? {
    if (rhs == (-1).toByte()) {
        return this
    }
    val r =
        when (val matchVal = rustCheckedRem(this, rhs)) {
            null -> { return null }
            else -> {
                val x = matchVal
                x
            }
        }
    val m =
        if ((r > 0 && rhs < 0.toByte()) || (r < 0 && rhs > 0.toByte())) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        rustCheckedAdd(this, rhs - m)
    }
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`][Self.ilog2] can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero,
 * or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.ilog(base: Byte): UInt {
    check(base >= 2.toByte()) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Byte.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is negative or zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedIlog(base: Byte): UInt? =
    if (this <= 0.toByte() || base <= 1.toByte()) {
        null
    } else {
        (this.toUByte()).checkedIlog(base.toUByte())
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedIlog2(): UInt? =
    if (this <= 0.toByte()) {
        null
    } else {
        val log = (8u - 1u) - kmCtlzU64((this).toULong())
        log
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Byte.checkedIlog10(): UInt? = io.github.kotlinmania.rustlang.core.num.imp.intlog10.Byte(this)

/**
 * Computes the absolute value of `self`.
 *
 * # Overflow behavior
 *
 * The absolute value of
 * cannot be represented as an
 * and attempting to calculate it will cause an overflow. This means
 * that code in debug mode will trigger a panic on this case and
 * optimized code will return
 * without a panic. If you do not want this behavior, consider
 * using [`unsignedAbs`](Self.unsignedAbs) instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.abs(): Byte =
    if (this.isNegative()) {
        -this
    } else {
        this
    }

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * This function always returns the correct answer without overflow or
 * panics by returning an unsigned integer.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.absDiff(other: Byte): UByte =
    if (this < other) {
        ((other.toUByte()) - (this.toUByte())).toUByte()
    } else {
        ((this.toUByte()) - (other.toUByte())).toUByte()
    }

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.signum(): Byte = threeWayCompare(this, 0).toByte()

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.isPositive(): Boolean = this > 0.toByte()

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.isNegative(): Boolean = this < 0.toByte()

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.toBeBytes(): UByteArray = ubyteArrayOf(this.toUByte())

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.toLeBytes(): UByteArray = ubyteArrayOf(this.toUByte())

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun Byte.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates an integer value from its representation as a byte array in
 * big endian.
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Byte.Companion.fromBeBytes(bytes: UByteArray): Byte = Byte.fromNeBytes(bytes)

/**
 * Creates an integer value from its representation as a byte array in
 * little endian.
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Byte.Companion.fromLeBytes(bytes: UByteArray): Byte = Byte.fromNeBytes(bytes)

/**
 * Creates an integer value from its memory representation as a byte
 * array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * **Note**: This function is meaningless on `Byte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types. You can cast from and to `UByte` using
 * [`castSigned`](u8.castSigned) and [`castUnsigned`](Self.castUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Byte.Companion.fromNeBytes(bytes: UByteArray): Byte = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<Byte>(typeLayoutT = PrimitiveLayouts.I8)).toInt()), PrimitiveLayouts.I8)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun Byte.Companion.minValue(): Byte = Byte.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun Byte.Companion.maxValue(): Byte = Byte.MAX_VALUE

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.clampMagnitude(limit: UByte): Byte =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120 == 376.truncate())
 * }
 * ```
 */
public inline fun <reified Target> Byte.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(127 == 376.saturatingTruncate())
 *     check(-128.toByte() == (-1000).saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> Byte.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376.checkedTruncate<Byte>())
 *     check(null == (-1000).checkedTruncate<Byte>())
 * }
 * ```
 */
public inline fun <reified Target> Byte.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> Byte.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Byte, T>> Byte.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Byte, T>> Byte.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Byte, T>> Byte.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Byte, T>> Byte.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<Byte, T>> Byte.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "i8::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Byte.midpoint(rhs: Byte): Byte = ((this.toShort() + rhs.toShort()) / 2).toByte()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun Byte.wideningMul(rhs: Byte): Short = this.toShort() * rhs.toShort()

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN1: Short = (Short.MAX_VALUE.inv()).toShort()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX1: Short = ((UShort.MAX_VALUE.toUInt() shr 1).toUShort()).toShort()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS1: UInt = 16u

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 * }
 * ```
 */
public fun Short.bit(index: UInt): Boolean = this.castUnsigned().bit(index)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     check(n == 0x2A)
 * }
 * ```
 */
public fun Short.setBit(index: UInt, value: Boolean): Short = this.castUnsigned().setBit(index, value).castSigned()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 1)
 * ```
 */
public fun Short.countOnes(): UInt = rustCountOnes((this.toUShort()))

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun Short.leadingZeros(): UInt = rustLeadingZeros((this.toUShort()))

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 2)
 * ```
 */
public fun Short.trailingZeros(): UInt = rustTrailingZeros((this.toUShort()))

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.leadingOnes(): UInt = rustLeadingOnes((this.toUShort()))

/**
 * Returns the number of trailing ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 2)
 * ```
 */
public fun Short.trailingOnes(): UInt = rustTrailingOnes((this.toUShort()))

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun Short.isolateHighestOne(): Short = (this.toInt() and (rustWrappingShr(((1.toShort()) shl (16u - 1u).toInt()), rustLeadingZeros(this))).toInt()).toShort()

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun Short.isolateLowestOne(): Short = (this.toInt() and rustWrappingNeg(this).toInt()).toShort()

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.highestOne(): UInt? = (this.toUShort()).highestOne()

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.lowestOne(): UInt? = (this.toUShort()).lowestOne()

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.castUnsigned(): UShort = this.toUShort()

/**
 * Saturating conversion of `self` to an unsigned integer of the same size.
 *
 * Negative values are clamped to `0`.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.saturatingCastUnsigned(): UShort =
    if (this >= 0.toShort()) {
        this.castUnsigned()
    } else {
        0u
    }

/**
 * Checked conversion of `self` to an unsigned integer of the same size,
 * returning `None` if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`saturatingCastUnsigned`](Self.saturatingCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.checkedCastUnsigned(): UShort? =
    if (this >= 0.toShort()) {
        this.castUnsigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to an unsigned integer of the same size,
 * which panics if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`saturatingCastUnsigned`](Self.saturatingCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.strictCastUnsigned(): UShort =
    when (val matchVal = this.checkedCastUnsigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.rotateLeft(n: UInt): Short = rustRotateLeft((this.toUShort()), n).toShort()

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.rotateRight(n: UInt): Short = rustRotateRight((this.toUShort()), n).toShort()

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun Short.swapBytes(): Short = (run { val swapVal0 = ((this.toUShort())); (((((swapVal0).toUInt() and 0xFFu) shl 8) or (((swapVal0).toUInt() shr 8) and 0xFFu)).toUShort()) }).toShort()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun Short.reverseBits(): Short = rustReverseBits((this.toUShort())).toShort()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are swapped.
 *
 * See also [fromBeBytes()](Self.fromBeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Short.Companion.fromBe(x: Short): Short = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are swapped.
 *
 * See also [fromLeBytes()](Self.fromLeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Short.Companion.fromLe(x: Short): Short = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Swaps bytes of `self` on little endian targets.
 *
 * On big endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toBeBytes`](Self.toBeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun Short.toBe(): Short = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Swaps bytes of `self` on big endian targets.
 *
 * On little endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toLeBytes`](Self.toLeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun Short.toLe(): Short = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedAdd(rhs: Short): Short? {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictAdd(rhs: Short): Short {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Short.uncheckedAdd(rhs: Short): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedAddUnsigned(rhs: UShort): Short? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictAddUnsigned(rhs: UShort): Short {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedSub(rhs: Short): Short? {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictSub(rhs: Short): Short {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Short.uncheckedSub(rhs: Short): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedSubUnsigned(rhs: UShort): Short? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictSubUnsigned(rhs: UShort): Short {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedMul(rhs: Short): Short? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictMul(rhs: Short): Short {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Short.uncheckedMul(rhs: Short): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None` if `rhs == 0`
 * or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedDiv(rhs: Short): Short? =
    if (rhs == 0.toShort() || ((this == Short.MIN_VALUE) && (rhs == (-1).toShort()))) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to normal division: `MIN / - 1` will also panic both in
 * debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictDiv(rhs: Short): Short = (this / rhs).toShort()

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`,
 * returning `None` if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedDivEuclid(rhs: Short): Short? =
    if (rhs == 0.toShort() || ((this == Short.MIN_VALUE) and (rhs == (-1).toShort()))) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to `divEuclid`: `MIN.divEuclid(- 1)` will also panic both
 * in debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictDivEuclid(rhs: Short): Short = rustDivEuclid(this, rhs)

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0`, the division results in overflow,
 * or `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.checkedDivExact(rhs: Short): Short? =
    if (rhs == 0.toShort() || ((this == Short.MIN_VALUE) && (rhs == (-1).toShort()))) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.divExact(rhs: Short): Short? =
    if (this % rhs != 0.toShort()) {
        null
    } else {
        (this / rhs).toShort()
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0`, `self % rhs != 0`, or
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun Short.uncheckedDivExact(rhs: Short): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_div_exact cannot overflow, divide by zero, or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0, lhs, MIN, rhs, 1)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None` if
 * `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedRem(rhs: Short): Short? =
    if (rhs == 0.toShort() || ((this == Short.MIN_VALUE) && (rhs == (-1).toShort()))) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictRem(rhs: Short): Short {
    val (a, b) = this.overflowingRem(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Checked Euclidean remainder. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedRemEuclid(rhs: Short): Short? =
    if (rhs == 0.toShort() || ((this == Short.MIN_VALUE) and (rhs == (-1).toShort()))) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean remainder. Computes `self.remEuclid(rhs)`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictRemEuclid(rhs: Short): Short {
    val (a, b) = this.overflowingRemEuclid(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(-1)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 *
 * Notably, this prevents both values from being negative.
 */
public fun Short.uncheckedDisjointBitor(rhs: Short): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Checked negation. Computes `- self`, returning `None` if `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedNeg(): Short? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Unchecked negation. Computes `- self`, assuming overflow cannot occur.
 *
 * # Safety
 *
 * This results in undefined behavior when
 * i.e. when [checkedNeg] would return `None`.
 */
public fun Short.uncheckedNeg(): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_neg cannot overflow", lhs, this, lhs, overflowingNeg, 1)
    return uncheckedSub(0, this)
}

/**
 * Strict negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictNeg(): Short {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None` if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedShl(rhs: UInt): Short? =
    if (rhs < 16u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictShl(rhs: UInt): Short {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun Short.uncheckedShl(rhs: UInt): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.unboundedShl(rhs: UInt): Short =
    if (rhs < 16u) {
        this.uncheckedShl(rhs)
    } else {
        0
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any bits that would be shifted out differ from the resulting sign bit
 * or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.shlExact(rhs: UInt): Short? =
    if (rhs < rustLeadingZeros(this) || rhs < rustLeadingOnes(this)) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs >= self.leadingZeros() && rhs >=
 * self.leadingOnes()` i.e. when
 * would return `None`.
 */
public fun Short.uncheckedShlExact(rhs: UInt): Short {
    assertUnsafePrecondition(checkLibraryUb, "i16::unchecked_shl_exact cannot shift out bits that would change the value of the first bit", zeros, this, leadingZeros, ones, this, leadingOnes, rhs, rhs, rhs, zeros, rhs, ones)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None` if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedShr(rhs: UInt): Short? =
    if (rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictShr(rhs: UInt): Short {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun Short.uncheckedShr(rhs: UInt): Short {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, which yields `0` for a positive number,
 * and `- 1` for a negative number.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.unboundedShr(rhs: UInt): Short =
    if (rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        this.uncheckedShr(16u - 1)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.shrExact(rhs: UInt): Short? =
    if (rhs <= rustTrailingZeros(this) && rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun Short.uncheckedShrExact(rhs: UInt): Short {
    assertUnsafePrecondition(checkLibraryUb, "i16::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked absolute value. Computes `self.abs()`, returning `None` if
 * `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedAbs(): Short? =
    if (this.isNegative()) {
        rustCheckedNeg(this)
    } else {
        this
    }

/**
 * Strict absolute value. Computes `self.abs()`, panicking if
 * `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictAbs(): Short =
    if (this.isNegative()) {
        this.strictNeg()
    } else {
        this
    }

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedPow(exp: UInt): Short? {
    var exp1 = exp
    var base = this
    var acc: Short = 1
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return if (base < 0.toShort() && (exp1 % 2u) == 1u) {
            ((-1).toShort()).shlExact(shift)
        } else {
            (1.toShort()).shlExact(shift)
        }
    }
    if (exp1 == 0u) {
        return 1
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Short.strictPow(exp: UInt): Short =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * Returns `None` if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedIsqrt(): Short? =
    if (this < 0.toShort()) {
        null
    } else {
        val result = this.castUnsigned().isqrt().castSigned()
        run {
            val MAX_RESULT: Short = Short.MAX_VALUE.castUnsigned().isqrt().castSigned()
            assertUnchecked(result <= MAX_RESULT)
        }
        result
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingAdd(rhs: Short): Short = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingAddUnsigned(rhs: UShort): Short =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> Short.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingSub(rhs: Short): Short = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingSubUnsigned(rhs: UShort): Short =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> Short.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer negation. Computes `- self`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingNeg(): Short = saturatingSub(0, this)

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self ==
 * MIN` instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingAbs(): Short =
    if (this.isNegative()) {
        rustSaturatingNeg(this)
    } else {
        this
    }

/**
 * Saturating integer multiplication. Computes `self * rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingMul(rhs: Short): Short =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> if ((this < 0.toShort()) == (rhs < 0.toShort())) {
            Short.MAX_VALUE
        } else {
            Short.MIN_VALUE
        }
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingDiv(rhs: Short): Short =
    run {
        val matchVal = this.overflowingDiv(rhs)
        when {
            matchVal.second == false -> {
                val result = matchVal.first
                result
            }
            else -> Short.MAX_VALUE
        }
    }

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.saturatingPow(exp: UInt): Short =
    run {
        val matchVal = rustCheckedPow(this, exp)
        when {
            matchVal != null -> {
                val x = matchVal
                x
            }
            matchVal == null && this < 0.toShort() && exp % 2u == 1u -> Short.MIN_VALUE
            else -> Short.MAX_VALUE
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingAdd(rhs: Short): Short = (this + rhs)

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingAddUnsigned(rhs: UShort): Short = (this + (rhs.toShort())).toShort()

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingSub(rhs: Short): Short = (this - rhs)

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingSubUnsigned(rhs: UShort): Short = (this - (rhs.toShort())).toShort()

/**
 * Wrapping (modular) multiplication. Computes `self * rhs`, wrapping around at
 * the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check((11 * (12.toByte())).toByte() == -124)
 * ```
 */
public fun Short.wrappingMul(rhs: Short): Short = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`, wrapping around at the
 * boundary of the type.
 *
 * The only case where such wrapping can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); this is equivalent to `- MIN`, a positive value
 * that is too large to represent in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDiv(-1) == -128)
 * ```
 */
public fun Short.wrappingDiv(rhs: Short): Short = this.overflowingDiv(rhs).first

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`,
 * wrapping around at the boundary of the type.
 *
 * Wrapping will only occur in `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value
 * for the type). This is equivalent to `- MIN`, a positive value that is too large to represent in the
 * type. In this case, this method returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDivEuclid(-1) == -128)
 * ```
 */
public fun Short.wrappingDivEuclid(rhs: Short): Short = this.overflowingDivEuclid(rhs).first

/**
 * Wrapping (modular) remainder. Computes `self % rhs`, wrapping around at the
 * boundary of the type.
 *
 * Such wrap-around never actually occurs mathematically; implementation artifacts make `x % y`
 * invalid for `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value). In such a case,
 * this function returns `0`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRem(-1) == 0)
 * ```
 */
public fun Short.wrappingRem(rhs: Short): Short = this.overflowingRem(rhs).first

/**
 * Wrapping Euclidean remainder. Computes `self.remEuclid(rhs)`, wrapping around
 * at the boundary of the type.
 *
 * Wrapping will only occur in `MIN % - 1` on a signed type (where [`Min`](Self.Min) is
 * the negative minimal value for the type). In this case, this method returns 0.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRemEuclid(-1) == 0)
 * ```
 */
public fun Short.wrappingRemEuclid(rhs: Short): Short = this.overflowingRemEuclid(rhs).first

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * The only case where such wrapping can occur is when one negates [`Min`](Self.Min) on a signed type (where [`Min`](Self.Min)
 * is the negative minimal value for the type); this is a positive value that is too large to represent
 * in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingNeg(): Short = ((0.toShort()) - (this.toShort())).toShort()

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`, where `mask` removes
 * any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the RHS of a wrapping shift-left is restricted to
 * the range of the type, rather than the bits shifted out of the LHS being returned to the other end.
 * The primitive integer types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.wrappingShl(rhs: UInt): Short = this.uncheckedShl(rhs and (16u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`, where `mask`
 * removes any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the RHS of a wrapping shift-right is restricted
 * to the range of the type, rather than the bits shifted out of the LHS being returned to the other
 * end. The primitive integer types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128).wrappingShr(64u) == -128)
 * ```
 */
public fun Short.wrappingShr(rhs: UInt): Short = this.uncheckedShr(rhs and (16u - 1u))

/**
 * Wrapping (modular) absolute value. Computes `self.abs()`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type; this is a positive value that is too large to represent in the type. In
 * such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingAbs().toUByte() == 128u.toUByte())
 * ```
 */
public fun Short.wrappingAbs(): Short =
    if (this.isNegative()) {
        rustWrappingNeg(this)
    } else {
        this
    }

/**
 * Computes the absolute value of `self` without any wrapping
 * or panicking.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).unsignedAbs() == 128u.toUByte())
 * ```
 */
public fun Short.unsignedAbs(): UShort = rustWrappingAbs(this).toUShort()

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.wrappingPow(5) == -13)
 * check(3.wrappingPow(6) == -39)
 * ```
 */
public fun Short.wrappingPow(exp: UInt): Short {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would have
 * occurred then the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingAdd(rhs: Short): Pair<Short, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toShort(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and checks for overflow.
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns a tuple of the sum along with a boolean indicating
 * whether an arithmetic overflow would occur. On overflow, the wrapped
 * value is returned.
 *
 * This allows chaining together multiple additions to create a wider
 * addition, and can be useful for bignum addition. This method should
 * only be used for the most significant word; for the less significant
 * words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a carry flag,
 * and should *not* be added to a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val carry0 = false
 *     val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 *     check(carry1 == true)
 *     val (sum1, overflow) = a1.carryingAdd(b1, carry1)
 *     check(overflow == false)
 *     check(Pair(sum1, sum0) == Pair(6, 8))
 * }
 * ```
 */
public fun Short.carryingAdd(rhs: Short, carry: Boolean): Pair<Short, Boolean> {
    val (a, b) = rustOverflowingAdd(this, rhs)
    val (c, d) = a.overflowingAdd(carry.toShort())
    return Pair(c, b != d)
}

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingAddUnsigned(rhs: UShort): Pair<Short, Boolean> {
    val rhs1 = rhs.toShort()
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0.toShort()))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned
 * (negative if overflowed above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingSub(rhs: Short): Pair<Short, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toShort(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and checks for
 * overflow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns a tuple of the
 * difference along with a boolean indicating whether an arithmetic
 * overflow would occur. On overflow, the wrapped value is returned.
 *
 * This allows chaining together multiple subtractions to create a
 * wider subtraction, and can be useful for bignum subtraction. This
 * method should only be used for the most significant word; for the
 * less significant words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a borrow flag,
 * and should *not* be subtracted from a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input borrow is false, this method is equivalent to
 * [`overflowingSub`](Self.overflowingSub).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val borrow0 = false
 *     val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 *     check(borrow1 == true)
 *     val (diff1, overflow) = a1.borrowingSub(b1, borrow1)
 *     check(overflow == false)
 * }
 * ```
 */
public fun Short.borrowingSub(rhs: Short, borrow: Boolean): Pair<Short, Boolean> {
    val (a, b) = rustOverflowingSub(this, rhs)
    val (c, d) = a.overflowingSub(borrow.toShort())
    return Pair(c, b != d)
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingSubUnsigned(rhs: UShort): Pair<Short, Boolean> {
    val rhs1 = rhs.toShort()
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0.toShort()))
}

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * check(1000000000.overflowingMul(10) == Pair(1410065408, true))
 * ```
 */
public fun Short.overflowingMul(rhs: Short): Pair<Short, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toShort(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why [i32] is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMul(-2, 0) == Pair(4294967286, -1))
 *     check(5.carryingMul(-2, 10) == Pair(0, 0))
 *     check(1000000000.carryingMul(-10, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMul(-10, 10) == Pair(2884901898, -3))
 * }
 * ```
 */
public fun Short.carryingMul(rhs: Short, carry: Short): Pair<UShort, Short> = Short.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you only need one `carry`, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Int` is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMulAdd(-2, 0, 0) == Pair(4294967286, -1))
 *     check(5.carryingMulAdd(-2, 10, 10) == Pair(10, 0))
 *     check(1000000000.carryingMulAdd(-10, 0, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMulAdd(-10, 10, 10) == Pair(2884901908, -3))
 * }
 * ```
 */
public fun Short.carryingMulAdd(rhs: Short, carry: Short, add: Short): Pair<UShort, Short> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then self is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingDiv(rhs: Short): Pair<Short, Boolean> =
    if ((this == Short.MIN_VALUE) and (rhs == (-1).toShort())) {
        Pair(this, true)
    } else {
        Pair(this / rhs, false)
    }

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then `self` is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingDivEuclid(rhs: Short): Pair<Short, Boolean> =
    if ((this == Short.MIN_VALUE) and (rhs == (-1).toShort())) {
        Pair(this, true)
    } else {
        Pair(rustDivEuclid(this, rhs), false)
    }

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingRem(rhs: Short): Pair<Short, Boolean> =
    if (rhs == (-1).toShort()) {
        Pair(0, this == Short.MIN_VALUE)
    } else {
        Pair(this % rhs, false)
    }

/**
 * Overflowing Euclidean remainder. Calculates `self.remEuclid(rhs)`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingRemEuclid(rhs: Short): Pair<Short, Boolean> =
    if (rhs == (-1).toShort()) {
        Pair(0, this == Short.MIN_VALUE)
    } else {
        Pair(rustRemEuclid(this, rhs), false)
    }

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * Returns a tuple of the negated version of self along with a boolean indicating whether an overflow
 * happened. If `self` is the minimum value (e.g., [i32.Min] for values of type [i32]), then the
 * minimum value will be returned again and `true` will be returned for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingNeg(): Pair<Short, Boolean> =
    if (this == Short.MIN_VALUE) {
        Pair(Short.MIN_VALUE, true)
    } else {
        Pair(-this, false)
    }

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x1.overflowingShl(36u) == Pair(0x10, true))
 * ```
 */
public fun Short.overflowingShl(rhs: UInt): Pair<Short, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 16u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x10.overflowingShr(36u) == Pair(0x1, true))
 * ```
 */
public fun Short.overflowingShr(rhs: UInt): Pair<Short, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 16u)

/**
 * Computes the absolute value of `self`.
 *
 * Returns a tuple of the absolute version of self along with a boolean indicating whether an overflow
 * happened. If self is the minimum value
 * then the minimum value will be returned again and true will be returned
 * for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.overflowingAbs(): Pair<Short, Boolean> = Pair(rustWrappingAbs(this), this == Short.MIN_VALUE)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.overflowingPow(5) == Pair(-13, true))
 * ```
 */
public fun Short.overflowingPow(exp: UInt): Pair<Short, Boolean> {
    var exp1 = exp
    val base = this
    val acc: Short  = 1
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        val base1: Short =
            if (base < 0.toShort() && (exp1 % 2u) != 0u) {
                -1
            } else {
                1
            }
        return Pair(base1.unboundedShl(shift), base1.shlExact(shift) == null)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * check(x.pow(5) == 32)
 * ```
 */
public fun Short.pow(exp: UInt): Short =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * # Panics
 *
 * This function will panic if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.isqrt(): Short =
    when (val matchVal = this.checkedIsqrt()) {
        null -> panicForNegativeArgument()
        else -> {
            val sqrt = matchVal
            sqrt
        }
    }

/**
 * Calculates the quotient of Euclidean division of `self` by `rhs`.
 *
 * This computes the integer `q` such that `self = q * rhs + r`, with
 * `r = self.remEuclid(rhs)` and `0 <= r<abs(rhs)`.
 *
 * In other words, the result is `self / rhs` rounded to the integer `q`
 * such that `self >= q * rhs`.
 * If `self> 0`, this is equal to rounding towards zero (the default in Rust);
 * if `self<0`, this is equal to rounding away from zero (towards +/- infinity).
 * If `rhs> 0`, this is equal to rounding towards -infinity;
 * if `rhs<0`, this is equal to rounding towards +infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.divEuclid(b) == 1)
 * // 7 >= 4 * 1
 * check(a.divEuclid(-b) == -1)
 * // 7 >= -4 * -1
 * check((-a).divEuclid(b) == -2)
 * // -7 >= 4 * -2
 * check((-a).divEuclid(-b) == 2)
 * // -7 >= -4 * 2
 * ```
 */
public fun Short.divEuclid(rhs: Short): Short {
    val q = this / rhs
    if (this % rhs < 0.toShort()) {
        return if (rhs > 0.toShort()) {
            q - 1
        } else {
            q + 1
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
 *
 * This is done as if by the Euclidean division algorithm -- given
 * `r = self.remEuclid(rhs)`, the result satisfies
 * `self = rhs * self.divEuclid(rhs) + r` and `0 <= r<abs(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min] and
 * `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.remEuclid(b) == 3)
 * check((-a).remEuclid(b) == 1)
 * check(a.remEuclid(-b) == 3)
 * check((-a).remEuclid(-b) == 1)
 * ```
 *
 * This will panic:
 * ```kotlin
 * ```
 */
public fun Short.remEuclid(rhs: Short): Short {
    val r = this % rhs
    return if (r < 0.toShort()) {
        (r + rustWrappingAbs(rhs).toShort()).toShort()
    } else {
        r
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divFloor(b) == 2)
 *     check(a.divFloor(-b) == -3)
 *     check((-a).divFloor(b) == -3)
 *     check((-a).divFloor(-b) == 2)
 * }
 * ```
 */
public fun Short.divFloor(rhs: Short): Short {
    val d = this / rhs
    val r = this % rhs
    val correction = ((this.toInt() xor rhs.toInt()).toShort()) shr (16u - 1).toInt()
    return if (r != 0.toShort()) {
        d + correction
    } else {
        d
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divCeil(b) == 3)
 *     check(a.divCeil(-b) == -2)
 *     check((-a).divCeil(b) == -2)
 *     check((-a).divCeil(-b) == 3)
 * }
 * ```
 */
public fun Short.divCeil(rhs: Short): Short {
    val d = this / rhs
    val r = this % rhs
    val correction = 1 + (((this.toInt() xor rhs.toInt()).toShort()) shr (16u - 1).toInt())
    return if (r != 0.toShort()) {
        d + correction
    } else {
        d
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.nextMultipleOf(rhs: Short): Short {
    if (rhs == (-1).toShort()) {
        return this
    }
    val r = this % rhs
    val m =
        if ((r > 0.toShort() && rhs < 0.toShort()) || (r < 0.toShort() && rhs > 0.toShort())) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        this + (rhs - m)
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`. Returns `None` if `rhs` is zero or the operation
 * would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.checkedNextMultipleOf(rhs: Short): Short? {
    if (rhs == (-1).toShort()) {
        return this
    }
    val r =
        when (val matchVal = rustCheckedRem(this, rhs)) {
            null -> { return null }
            else -> {
                val x = matchVal
                x
            }
        }
    val m =
        if ((r > 0 && rhs < 0.toShort()) || (r < 0 && rhs > 0.toShort())) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        rustCheckedAdd(this, rhs - m)
    }
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`][Self.ilog2] can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero,
 * or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.ilog(base: Short): UInt {
    check(base >= 2.toShort()) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Short.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is negative or zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedIlog(base: Short): UInt? =
    if (this <= 0.toShort() || base <= 1.toShort()) {
        null
    } else {
        (this.toUShort()).checkedIlog(base.toUShort())
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedIlog2(): UInt? =
    if (this <= 0.toShort()) {
        null
    } else {
        val log = (16u - 1u) - kmCtlzU64((this).toULong())
        log
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Short.checkedIlog10(): UInt? = io.github.kotlinmania.rustlang.core.num.imp.intlog10.Short(this)

/**
 * Computes the absolute value of `self`.
 *
 * # Overflow behavior
 *
 * The absolute value of
 * cannot be represented as an
 * and attempting to calculate it will cause an overflow. This means
 * that code in debug mode will trigger a panic on this case and
 * optimized code will return
 * without a panic. If you do not want this behavior, consider
 * using [`unsignedAbs`](Self.unsignedAbs) instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.abs(): Short =
    if (this.isNegative()) {
        -this
    } else {
        this
    }

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * This function always returns the correct answer without overflow or
 * panics by returning an unsigned integer.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.absDiff(other: Short): UShort =
    if (this < other) {
        ((other.toUShort()) - (this.toUShort())).toUShort()
    } else {
        ((this.toUShort()) - (other.toUShort())).toUShort()
    }

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.signum(): Short = threeWayCompare(this, 0).toShort()

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.isPositive(): Boolean = this > 0.toShort()

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.isNegative(): Boolean = this < 0.toShort()

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.toBeBytes(): UByteArray = run { val rawBits = (((((this).toInt() and 0xFF) shl 8) or (((this).toInt() ushr 8) and 0xFF)).toShort()).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.toLeBytes(): UByteArray = run { val rawBits = this.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun Short.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates an integer value from its representation as a byte array in
 * big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Short.Companion.fromBeBytes(bytes: UByteArray): Short = run { val swapVal0 = (Short.fromNeBytes(bytes)); (((((swapVal0).toInt() and 0xFF) shl 8) or (((swapVal0).toInt() ushr 8) and 0xFF)).toShort()) }

/**
 * Creates an integer value from its representation as a byte array in
 * little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Short.Companion.fromLeBytes(bytes: UByteArray): Short = Short.fromNeBytes(bytes)

/**
 * Creates an integer value from its memory representation as a byte
 * array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Short.Companion.fromNeBytes(bytes: UByteArray): Short = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()), PrimitiveLayouts.I16)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun Short.Companion.minValue(): Short = Short.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun Short.Companion.maxValue(): Short = Short.MAX_VALUE

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.clampMagnitude(limit: UShort): Short =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120 == 376.truncate())
 * }
 * ```
 */
public inline fun <reified Target> Short.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(127 == 376.saturatingTruncate())
 *     check(-128.toByte() == (-1000).saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> Short.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376.checkedTruncate<Byte>())
 *     check(null == (-1000).checkedTruncate<Byte>())
 * }
 * ```
 */
public inline fun <reified Target> Short.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> Short.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Short, T>> Short.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Short, T>> Short.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Short, T>> Short.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Short, T>> Short.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<Short, T>> Short.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "i16::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Short.midpoint(rhs: Short): Short = ((this.toInt() + rhs.toInt()) / 2).toShort()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun Short.wideningMul(rhs: Short): Int = this.toInt() * rhs.toInt()

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN2: Int = Int.MAX_VALUE.inv()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX2: Int = (UInt.MAX_VALUE shr 1).toInt()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS2: UInt = 32u

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 * }
 * ```
 */
public fun Int.bit(index: UInt): Boolean = this.castUnsigned().bit(index)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     check(n == 0x2A)
 * }
 * ```
 */
public fun Int.setBit(index: UInt, value: Boolean): Int = this.castUnsigned().setBit(index, value).castSigned()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 1)
 * ```
 */
public fun Int.countOnes(): UInt = rustCountOnes((this.toUInt()))

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun Int.leadingZeros(): UInt = rustLeadingZeros((this.toUInt()))

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 2)
 * ```
 */
public fun Int.trailingZeros(): UInt = rustTrailingZeros((this.toUInt()))

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.leadingOnes(): UInt = rustLeadingOnes((this.toUInt()))

/**
 * Returns the number of trailing ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 2)
 * ```
 */
public fun Int.trailingOnes(): UInt = rustTrailingOnes((this.toUInt()))

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun Int.isolateHighestOne(): Int = this and (rustWrappingShr(((1) shl (32u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun Int.isolateLowestOne(): Int = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.highestOne(): UInt? = (this.toUInt()).highestOne()

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.lowestOne(): UInt? = (this.toUInt()).lowestOne()

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.castUnsigned(): UInt = this.toUInt()

/**
 * Saturating conversion of `self` to an unsigned integer of the same size.
 *
 * Negative values are clamped to `0`.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.saturatingCastUnsigned(): UInt =
    if (this >= 0) {
        this.castUnsigned()
    } else {
        0u
    }

/**
 * Checked conversion of `self` to an unsigned integer of the same size,
 * returning `None` if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`saturatingCastUnsigned`](Self.saturatingCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.checkedCastUnsigned(): UInt? =
    if (this >= 0) {
        this.castUnsigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to an unsigned integer of the same size,
 * which panics if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`saturatingCastUnsigned`](Self.saturatingCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.strictCastUnsigned(): UInt =
    when (val matchVal = this.checkedCastUnsigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.rotateLeft(n: UInt): Int = rustRotateLeft((this.toUInt()), n).toInt()

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.rotateRight(n: UInt): Int = rustRotateRight((this.toUInt()), n).toInt()

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun Int.swapBytes(): Int = (run { val swapVal0 = ((this.toUInt())); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00u) shl 8) or (((swapVal0) shr 8) and 0x0000FF00u) or ((swapVal0) shr 24)) }).toInt()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun Int.reverseBits(): Int = rustReverseBits((this.toUInt())).toInt()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are swapped.
 *
 * See also [fromBeBytes()](Self.fromBeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Int.Companion.fromBe(x: Int): Int = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are swapped.
 *
 * See also [fromLeBytes()](Self.fromLeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Int.Companion.fromLe(x: Int): Int = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Swaps bytes of `self` on little endian targets.
 *
 * On big endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toBeBytes`](Self.toBeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun Int.toBe(): Int = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Swaps bytes of `self` on big endian targets.
 *
 * On little endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toLeBytes`](Self.toLeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun Int.toLe(): Int = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedAdd(rhs: Int): Int? {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictAdd(rhs: Int): Int {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Int.uncheckedAdd(rhs: Int): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedAddUnsigned(rhs: UInt): Int? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictAddUnsigned(rhs: UInt): Int {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedSub(rhs: Int): Int? {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictSub(rhs: Int): Int {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Int.uncheckedSub(rhs: Int): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedSubUnsigned(rhs: UInt): Int? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictSubUnsigned(rhs: UInt): Int {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedMul(rhs: Int): Int? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictMul(rhs: Int): Int {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Int.uncheckedMul(rhs: Int): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None` if `rhs == 0`
 * or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedDiv(rhs: Int): Int? =
    if (rhs == 0 || ((this == Int.MIN_VALUE) && (rhs == -1))) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to normal division: `MIN / - 1` will also panic both in
 * debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictDiv(rhs: Int): Int = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`,
 * returning `None` if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedDivEuclid(rhs: Int): Int? =
    if (rhs == 0 || ((this == Int.MIN_VALUE) and (rhs == -1))) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to `divEuclid`: `MIN.divEuclid(- 1)` will also panic both
 * in debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictDivEuclid(rhs: Int): Int = rustDivEuclid(this, rhs)

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0`, the division results in overflow,
 * or `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.checkedDivExact(rhs: Int): Int? =
    if (rhs == 0 || ((this == Int.MIN_VALUE) && (rhs == -1))) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.divExact(rhs: Int): Int? =
    if (this % rhs != 0) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0`, `self % rhs != 0`, or
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun Int.uncheckedDivExact(rhs: Int): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_div_exact cannot overflow, divide by zero, or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0, lhs, MIN, rhs, 1)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None` if
 * `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedRem(rhs: Int): Int? =
    if (rhs == 0 || ((this == Int.MIN_VALUE) && (rhs == -1))) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictRem(rhs: Int): Int {
    val (a, b) = this.overflowingRem(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Checked Euclidean remainder. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedRemEuclid(rhs: Int): Int? =
    if (rhs == 0 || ((this == Int.MIN_VALUE) and (rhs == -1))) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean remainder. Computes `self.remEuclid(rhs)`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictRemEuclid(rhs: Int): Int {
    val (a, b) = this.overflowingRemEuclid(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(-1)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 *
 * Notably, this prevents both values from being negative.
 */
public fun Int.uncheckedDisjointBitor(rhs: Int): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Checked negation. Computes `- self`, returning `None` if `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedNeg(): Int? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Unchecked negation. Computes `- self`, assuming overflow cannot occur.
 *
 * # Safety
 *
 * This results in undefined behavior when
 * i.e. when [checkedNeg] would return `None`.
 */
public fun Int.uncheckedNeg(): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_neg cannot overflow", lhs, this, lhs, overflowingNeg, 1)
    return uncheckedSub(0, this)
}

/**
 * Strict negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictNeg(): Int {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None` if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedShl(rhs: UInt): Int? =
    if (rhs < 32u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictShl(rhs: UInt): Int {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun Int.uncheckedShl(rhs: UInt): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.unboundedShl(rhs: UInt): Int =
    if (rhs < 32u) {
        this.uncheckedShl(rhs)
    } else {
        0
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any bits that would be shifted out differ from the resulting sign bit
 * or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.shlExact(rhs: UInt): Int? =
    if (rhs < rustLeadingZeros(this) || rhs < rustLeadingOnes(this)) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs >= self.leadingZeros() && rhs >=
 * self.leadingOnes()` i.e. when
 * would return `None`.
 */
public fun Int.uncheckedShlExact(rhs: UInt): Int {
    assertUnsafePrecondition(checkLibraryUb, "i32::unchecked_shl_exact cannot shift out bits that would change the value of the first bit", zeros, this, leadingZeros, ones, this, leadingOnes, rhs, rhs, rhs, zeros, rhs, ones)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None` if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedShr(rhs: UInt): Int? =
    if (rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictShr(rhs: UInt): Int {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun Int.uncheckedShr(rhs: UInt): Int {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, which yields `0` for a positive number,
 * and `- 1` for a negative number.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.unboundedShr(rhs: UInt): Int =
    if (rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        this.uncheckedShr(32u - 1)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.shrExact(rhs: UInt): Int? =
    if (rhs <= rustTrailingZeros(this) && rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun Int.uncheckedShrExact(rhs: UInt): Int {
    assertUnsafePrecondition(checkLibraryUb, "i32::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked absolute value. Computes `self.abs()`, returning `None` if
 * `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedAbs(): Int? =
    if (this.isNegative()) {
        rustCheckedNeg(this)
    } else {
        this
    }

/**
 * Strict absolute value. Computes `self.abs()`, panicking if
 * `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictAbs(): Int =
    if (this.isNegative()) {
        this.strictNeg()
    } else {
        this
    }

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedPow(exp: UInt): Int? {
    var exp1 = exp
    var base = this
    var acc: Int = 1
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return if (base < 0 && (exp1 % 2u) == 1u) {
            (-1).shlExact(shift)
        } else {
            (1).shlExact(shift)
        }
    }
    if (exp1 == 0u) {
        return 1
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictPow(exp: UInt): Int =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * Returns `None` if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedIsqrt(): Int? =
    if (this < 0) {
        null
    } else {
        val result = this.castUnsigned().isqrt().castSigned()
        run {
            val MAX_RESULT: Int = Int.MAX_VALUE.castUnsigned().isqrt().castSigned()
            assertUnchecked(result <= MAX_RESULT)
        }
        result
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingAdd(rhs: Int): Int = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingAddUnsigned(rhs: UInt): Int =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> Int.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingSub(rhs: Int): Int = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingSubUnsigned(rhs: UInt): Int =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> Int.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer negation. Computes `- self`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingNeg(): Int = saturatingSub(0, this)

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self ==
 * MIN` instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingAbs(): Int =
    if (this.isNegative()) {
        rustSaturatingNeg(this)
    } else {
        this
    }

/**
 * Saturating integer multiplication. Computes `self * rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingMul(rhs: Int): Int =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> if ((this < 0) == (rhs < 0)) {
            Int.MAX_VALUE
        } else {
            Int.MIN_VALUE
        }
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingDiv(rhs: Int): Int =
    run {
        val matchVal = this.overflowingDiv(rhs)
        when {
            matchVal.second == false -> {
                val result = matchVal.first
                result
            }
            else -> Int.MAX_VALUE
        }
    }

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingPow(exp: UInt): Int =
    run {
        val matchVal = rustCheckedPow(this, exp)
        when {
            matchVal != null -> {
                val x = matchVal
                x
            }
            matchVal == null && this < 0 && exp % 2u == 1u -> Int.MIN_VALUE
            else -> Int.MAX_VALUE
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingAdd(rhs: Int): Int = (this + rhs)

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingAddUnsigned(rhs: UInt): Int = (this + (rhs.toInt()))

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingSub(rhs: Int): Int = (this - rhs)

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingSubUnsigned(rhs: UInt): Int = (this - (rhs.toInt()))

/**
 * Wrapping (modular) multiplication. Computes `self * rhs`, wrapping around at
 * the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check((11 * (12.toByte())).toByte() == -124)
 * ```
 */
public fun Int.wrappingMul(rhs: Int): Int = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`, wrapping around at the
 * boundary of the type.
 *
 * The only case where such wrapping can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); this is equivalent to `- MIN`, a positive value
 * that is too large to represent in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDiv(-1) == -128)
 * ```
 */
public fun Int.wrappingDiv(rhs: Int): Int = this.overflowingDiv(rhs).first

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`,
 * wrapping around at the boundary of the type.
 *
 * Wrapping will only occur in `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value
 * for the type). This is equivalent to `- MIN`, a positive value that is too large to represent in the
 * type. In this case, this method returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDivEuclid(-1) == -128)
 * ```
 */
public fun Int.wrappingDivEuclid(rhs: Int): Int = this.overflowingDivEuclid(rhs).first

/**
 * Wrapping (modular) remainder. Computes `self % rhs`, wrapping around at the
 * boundary of the type.
 *
 * Such wrap-around never actually occurs mathematically; implementation artifacts make `x % y`
 * invalid for `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value). In such a case,
 * this function returns `0`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRem(-1) == 0)
 * ```
 */
public fun Int.wrappingRem(rhs: Int): Int = this.overflowingRem(rhs).first

/**
 * Wrapping Euclidean remainder. Computes `self.remEuclid(rhs)`, wrapping around
 * at the boundary of the type.
 *
 * Wrapping will only occur in `MIN % - 1` on a signed type (where [`Min`](Self.Min) is
 * the negative minimal value for the type). In this case, this method returns 0.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRemEuclid(-1) == 0)
 * ```
 */
public fun Int.wrappingRemEuclid(rhs: Int): Int = this.overflowingRemEuclid(rhs).first

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * The only case where such wrapping can occur is when one negates [`Min`](Self.Min) on a signed type (where [`Min`](Self.Min)
 * is the negative minimal value for the type); this is a positive value that is too large to represent
 * in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingNeg(): Int = ((0) - (this.toInt()))

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`, where `mask` removes
 * any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the RHS of a wrapping shift-left is restricted to
 * the range of the type, rather than the bits shifted out of the LHS being returned to the other end.
 * The primitive integer types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingShl(rhs: UInt): Int = this.uncheckedShl(rhs and (32u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`, where `mask`
 * removes any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the RHS of a wrapping shift-right is restricted
 * to the range of the type, rather than the bits shifted out of the LHS being returned to the other
 * end. The primitive integer types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128).wrappingShr(64u) == -128)
 * ```
 */
public fun Int.wrappingShr(rhs: UInt): Int = this.uncheckedShr(rhs and (32u - 1u))

/**
 * Wrapping (modular) absolute value. Computes `self.abs()`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type; this is a positive value that is too large to represent in the type. In
 * such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingAbs().toUByte() == 128u.toUByte())
 * ```
 */
public fun Int.wrappingAbs(): Int =
    if (this.isNegative()) {
        rustWrappingNeg(this)
    } else {
        this
    }

/**
 * Computes the absolute value of `self` without any wrapping
 * or panicking.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).unsignedAbs() == 128u.toUByte())
 * ```
 */
public fun Int.unsignedAbs(): UInt = rustWrappingAbs(this).toUInt()

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.wrappingPow(5) == -13)
 * check(3.wrappingPow(6) == -39)
 * ```
 */
public fun Int.wrappingPow(exp: UInt): Int {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would have
 * occurred then the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingAdd(rhs: Int): Pair<Int, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toInt(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and checks for overflow.
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns a tuple of the sum along with a boolean indicating
 * whether an arithmetic overflow would occur. On overflow, the wrapped
 * value is returned.
 *
 * This allows chaining together multiple additions to create a wider
 * addition, and can be useful for bignum addition. This method should
 * only be used for the most significant word; for the less significant
 * words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a carry flag,
 * and should *not* be added to a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val carry0 = false
 *     val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 *     check(carry1 == true)
 *     val (sum1, overflow) = a1.carryingAdd(b1, carry1)
 *     check(overflow == false)
 *     check(Pair(sum1, sum0) == Pair(6, 8))
 * }
 * ```
 */
public fun Int.carryingAdd(rhs: Int, carry: Boolean): Pair<Int, Boolean> {
    val (a, b) = rustOverflowingAdd(this, rhs)
    val (c, d) = a.overflowingAdd(carry.toInt())
    return Pair(c, b != d)
}

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingAddUnsigned(rhs: UInt): Pair<Int, Boolean> {
    val rhs1 = rhs.toInt()
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned
 * (negative if overflowed above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingSub(rhs: Int): Pair<Int, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toInt(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and checks for
 * overflow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns a tuple of the
 * difference along with a boolean indicating whether an arithmetic
 * overflow would occur. On overflow, the wrapped value is returned.
 *
 * This allows chaining together multiple subtractions to create a
 * wider subtraction, and can be useful for bignum subtraction. This
 * method should only be used for the most significant word; for the
 * less significant words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a borrow flag,
 * and should *not* be subtracted from a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input borrow is false, this method is equivalent to
 * [`overflowingSub`](Self.overflowingSub).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val borrow0 = false
 *     val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 *     check(borrow1 == true)
 *     val (diff1, overflow) = a1.borrowingSub(b1, borrow1)
 *     check(overflow == false)
 * }
 * ```
 */
public fun Int.borrowingSub(rhs: Int, borrow: Boolean): Pair<Int, Boolean> {
    val (a, b) = rustOverflowingSub(this, rhs)
    val (c, d) = a.overflowingSub(borrow.toInt())
    return Pair(c, b != d)
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingSubUnsigned(rhs: UInt): Pair<Int, Boolean> {
    val rhs1 = rhs.toInt()
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0))
}

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * check(1000000000.overflowingMul(10) == Pair(1410065408, true))
 * ```
 */
public fun Int.overflowingMul(rhs: Int): Pair<Int, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toInt(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why [i32] is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMul(-2, 0) == Pair(4294967286, -1))
 *     check(5.carryingMul(-2, 10) == Pair(0, 0))
 *     check(1000000000.carryingMul(-10, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMul(-10, 10) == Pair(2884901898, -3))
 * }
 * ```
 */
public fun Int.carryingMul(rhs: Int, carry: Int): Pair<UInt, Int> = Int.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you only need one `carry`, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Int` is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMulAdd(-2, 0, 0) == Pair(4294967286, -1))
 *     check(5.carryingMulAdd(-2, 10, 10) == Pair(10, 0))
 *     check(1000000000.carryingMulAdd(-10, 0, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMulAdd(-10, 10, 10) == Pair(2884901908, -3))
 * }
 * ```
 */
public fun Int.carryingMulAdd(rhs: Int, carry: Int, add: Int): Pair<UInt, Int> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then self is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingDiv(rhs: Int): Pair<Int, Boolean> =
    if ((this == Int.MIN_VALUE) and (rhs == -1)) {
        Pair(this, true)
    } else {
        Pair(this / rhs, false)
    }

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then `self` is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingDivEuclid(rhs: Int): Pair<Int, Boolean> =
    if ((this == Int.MIN_VALUE) and (rhs == -1)) {
        Pair(this, true)
    } else {
        Pair(rustDivEuclid(this, rhs), false)
    }

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingRem(rhs: Int): Pair<Int, Boolean> =
    if (rhs == -1) {
        Pair(0, this == Int.MIN_VALUE)
    } else {
        Pair(this % rhs, false)
    }

/**
 * Overflowing Euclidean remainder. Calculates `self.remEuclid(rhs)`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingRemEuclid(rhs: Int): Pair<Int, Boolean> =
    if (rhs == -1) {
        Pair(0, this == Int.MIN_VALUE)
    } else {
        Pair(rustRemEuclid(this, rhs), false)
    }

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * Returns a tuple of the negated version of self along with a boolean indicating whether an overflow
 * happened. If `self` is the minimum value (e.g., [i32.Min] for values of type [i32]), then the
 * minimum value will be returned again and `true` will be returned for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingNeg(): Pair<Int, Boolean> =
    if (this == Int.MIN_VALUE) {
        Pair(Int.MIN_VALUE, true)
    } else {
        Pair(-this, false)
    }

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x1.overflowingShl(36u) == Pair(0x10, true))
 * ```
 */
public fun Int.overflowingShl(rhs: UInt): Pair<Int, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 32u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x10.overflowingShr(36u) == Pair(0x1, true))
 * ```
 */
public fun Int.overflowingShr(rhs: UInt): Pair<Int, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 32u)

/**
 * Computes the absolute value of `self`.
 *
 * Returns a tuple of the absolute version of self along with a boolean indicating whether an overflow
 * happened. If self is the minimum value
 * then the minimum value will be returned again and true will be returned
 * for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingAbs(): Pair<Int, Boolean> = Pair(rustWrappingAbs(this), this == Int.MIN_VALUE)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.overflowingPow(5) == Pair(-13, true))
 * ```
 */
public fun Int.overflowingPow(exp: UInt): Pair<Int, Boolean> {
    var exp1 = exp
    val base = this
    val acc: Int  = 1
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        val base1: Int =
            if (base < 0 && (exp1 % 2u) != 0u) {
                -1
            } else {
                1
            }
        return Pair(base1.unboundedShl(shift), base1.shlExact(shift) == null)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * check(x.pow(5) == 32)
 * ```
 */
public fun Int.pow(exp: UInt): Int =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * # Panics
 *
 * This function will panic if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.isqrt(): Int =
    when (val matchVal = this.checkedIsqrt()) {
        null -> panicForNegativeArgument()
        else -> {
            val sqrt = matchVal
            sqrt
        }
    }

/**
 * Calculates the quotient of Euclidean division of `self` by `rhs`.
 *
 * This computes the integer `q` such that `self = q * rhs + r`, with
 * `r = self.remEuclid(rhs)` and `0 <= r<abs(rhs)`.
 *
 * In other words, the result is `self / rhs` rounded to the integer `q`
 * such that `self >= q * rhs`.
 * If `self> 0`, this is equal to rounding towards zero (the default in Rust);
 * if `self<0`, this is equal to rounding away from zero (towards +/- infinity).
 * If `rhs> 0`, this is equal to rounding towards -infinity;
 * if `rhs<0`, this is equal to rounding towards +infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.divEuclid(b) == 1)
 * // 7 >= 4 * 1
 * check(a.divEuclid(-b) == -1)
 * // 7 >= -4 * -1
 * check((-a).divEuclid(b) == -2)
 * // -7 >= 4 * -2
 * check((-a).divEuclid(-b) == 2)
 * // -7 >= -4 * 2
 * ```
 */
public fun Int.divEuclid(rhs: Int): Int {
    val q = this / rhs
    if (this % rhs < 0) {
        return if (rhs > 0) {
            q - 1
        } else {
            q + 1
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
 *
 * This is done as if by the Euclidean division algorithm -- given
 * `r = self.remEuclid(rhs)`, the result satisfies
 * `self = rhs * self.divEuclid(rhs) + r` and `0 <= r<abs(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min] and
 * `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.remEuclid(b) == 3)
 * check((-a).remEuclid(b) == 1)
 * check(a.remEuclid(-b) == 3)
 * check((-a).remEuclid(-b) == 1)
 * ```
 *
 * This will panic:
 * ```kotlin
 * ```
 */
public fun Int.remEuclid(rhs: Int): Int {
    val r = this % rhs
    return if (r < 0) {
        (r + rustWrappingAbs(rhs).toInt())
    } else {
        r
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divFloor(b) == 2)
 *     check(a.divFloor(-b) == -3)
 *     check((-a).divFloor(b) == -3)
 *     check((-a).divFloor(-b) == 2)
 * }
 * ```
 */
public fun Int.divFloor(rhs: Int): Int {
    val d = this / rhs
    val r = this % rhs
    val correction = (this xor rhs) shr (32u - 1).toInt()
    return if (r != 0) {
        d + correction
    } else {
        d
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divCeil(b) == 3)
 *     check(a.divCeil(-b) == -2)
 *     check((-a).divCeil(b) == -2)
 *     check((-a).divCeil(-b) == 3)
 * }
 * ```
 */
public fun Int.divCeil(rhs: Int): Int {
    val d = this / rhs
    val r = this % rhs
    val correction = 1 + ((this xor rhs) shr (32u - 1).toInt())
    return if (r != 0) {
        d + correction
    } else {
        d
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.nextMultipleOf(rhs: Int): Int {
    if (rhs == -1) {
        return this
    }
    val r = this % rhs
    val m =
        if ((r > 0 && rhs < 0) || (r < 0 && rhs > 0)) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        this + (rhs - m)
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`. Returns `None` if `rhs` is zero or the operation
 * would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.checkedNextMultipleOf(rhs: Int): Int? {
    if (rhs == -1) {
        return this
    }
    val r =
        when (val matchVal = rustCheckedRem(this, rhs)) {
            null -> { return null }
            else -> {
                val x = matchVal
                x
            }
        }
    val m =
        if ((r > 0 && rhs < 0) || (r < 0 && rhs > 0)) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        rustCheckedAdd(this, rhs - m)
    }
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`][Self.ilog2] can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero,
 * or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.ilog(base: Int): UInt {
    check(base >= 2) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Int.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is negative or zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedIlog(base: Int): UInt? =
    if (this <= 0 || base <= 1) {
        null
    } else {
        (this.toUInt()).checkedIlog(base.toUInt())
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedIlog2(): UInt? =
    if (this <= 0) {
        null
    } else {
        val log = (32u - 1u) - kmCtlzU64((this).toULong())
        log
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedIlog10(): UInt? = io.github.kotlinmania.rustlang.core.num.imp.intlog10.Int(this)

/**
 * Computes the absolute value of `self`.
 *
 * # Overflow behavior
 *
 * The absolute value of
 * cannot be represented as an
 * and attempting to calculate it will cause an overflow. This means
 * that code in debug mode will trigger a panic on this case and
 * optimized code will return
 * without a panic. If you do not want this behavior, consider
 * using [`unsignedAbs`](Self.unsignedAbs) instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.abs(): Int =
    if (this.isNegative()) {
        -this
    } else {
        this
    }

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * This function always returns the correct answer without overflow or
 * panics by returning an unsigned integer.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.absDiff(other: Int): UInt =
    if (this < other) {
        ((other.toUInt()) - (this.toUInt()))
    } else {
        ((this.toUInt()) - (other.toUInt()))
    }

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.signum(): Int = threeWayCompare(this, 0).toInt()

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.isPositive(): Boolean = this > 0

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.isNegative(): Boolean = this < 0

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.toBeBytes(): UByteArray = run { val rawBits = ((((this) shl 24)) or (((this) and 0x0000FF00) shl 8) or (((this) ushr 8) and 0x0000FF00) or ((this) ushr 24)).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.toLeBytes(): UByteArray = run { val rawBits = this.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun Int.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates an integer value from its representation as a byte array in
 * big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Int.Companion.fromBeBytes(bytes: UByteArray): Int = run { val swapVal0 = (Int.fromNeBytes(bytes)); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00) shl 8) or (((swapVal0) ushr 8) and 0x0000FF00) or ((swapVal0) ushr 24)) }

/**
 * Creates an integer value from its representation as a byte array in
 * little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Int.Companion.fromLeBytes(bytes: UByteArray): Int = Int.fromNeBytes(bytes)

/**
 * Creates an integer value from its memory representation as a byte
 * array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Int.Companion.fromNeBytes(bytes: UByteArray): Int = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()), PrimitiveLayouts.I32)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun Int.Companion.minValue(): Int = Int.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun Int.Companion.maxValue(): Int = Int.MAX_VALUE

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.clampMagnitude(limit: UInt): Int =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120 == 376.truncate())
 * }
 * ```
 */
public inline fun <reified Target> Int.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(127 == 376.saturatingTruncate())
 *     check(-128.toByte() == (-1000).saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> Int.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376.checkedTruncate<Byte>())
 *     check(null == (-1000).checkedTruncate<Byte>())
 * }
 * ```
 */
public inline fun <reified Target> Int.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> Int.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Int, T>> Int.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Int, T>> Int.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Int, T>> Int.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Int, T>> Int.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<Int, T>> Int.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "i32::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.midpoint(rhs: Int): Int = ((this.toLong() + rhs.toLong()) / 2L).toInt()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.wideningMul(rhs: Int): Long = this.toLong() * rhs.toLong()

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN3: Long = Long.MAX_VALUE.inv()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX3: Long = (ULong.MAX_VALUE shr 1).toLong()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS3: UInt = 64u

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 * }
 * ```
 */
public fun Long.bit(index: UInt): Boolean = this.castUnsigned().bit(index)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     check(n == 0x2A)
 * }
 * ```
 */
public fun Long.setBit(index: UInt, value: Boolean): Long = this.castUnsigned().setBit(index, value).castSigned()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 1)
 * ```
 */
public fun Long.countOnes(): UInt = rustCountOnes((this.toULong()))

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun Long.leadingZeros(): UInt = rustLeadingZeros((this.toULong()))

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 2)
 * ```
 */
public fun Long.trailingZeros(): UInt = rustTrailingZeros((this.toULong()))

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.leadingOnes(): UInt = rustLeadingOnes((this.toULong()))

/**
 * Returns the number of trailing ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 2)
 * ```
 */
public fun Long.trailingOnes(): UInt = rustTrailingOnes((this.toULong()))

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun Long.isolateHighestOne(): Long = this and (rustWrappingShr(((1.toLong()) shl (64u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun Long.isolateLowestOne(): Long = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.highestOne(): UInt? = (this.toULong()).highestOne()

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.lowestOne(): UInt? = (this.toULong()).lowestOne()

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.castUnsigned(): ULong = this.toULong()

/**
 * Saturating conversion of `self` to an unsigned integer of the same size.
 *
 * Negative values are clamped to `0`.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.saturatingCastUnsigned(): ULong =
    if (this >= 0L) {
        this.castUnsigned()
    } else {
        0uL
    }

/**
 * Checked conversion of `self` to an unsigned integer of the same size,
 * returning `None` if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`saturatingCastUnsigned`](Self.saturatingCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.checkedCastUnsigned(): ULong? =
    if (this >= 0L) {
        this.castUnsigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to an unsigned integer of the same size,
 * which panics if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`saturatingCastUnsigned`](Self.saturatingCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.strictCastUnsigned(): ULong =
    when (val matchVal = this.checkedCastUnsigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.rotateLeft(n: UInt): Long = rustRotateLeft((this.toULong()), n).toLong()

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.rotateRight(n: UInt): Long = rustRotateRight((this.toULong()), n).toLong()

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun Long.swapBytes(): Long = (run { val swapVal0 = ((this.toULong())); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) }).toLong()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun Long.reverseBits(): Long = rustReverseBits((this.toULong())).toLong()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are swapped.
 *
 * See also [fromBeBytes()](Self.fromBeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Long.Companion.fromBe(x: Long): Long = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are swapped.
 *
 * See also [fromLeBytes()](Self.fromLeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Long.Companion.fromLe(x: Long): Long = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Swaps bytes of `self` on little endian targets.
 *
 * On big endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toBeBytes`](Self.toBeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun Long.toBe(): Long = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Swaps bytes of `self` on big endian targets.
 *
 * On little endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toLeBytes`](Self.toLeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun Long.toLe(): Long = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedAdd(rhs: Long): Long? {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictAdd(rhs: Long): Long {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Long.uncheckedAdd(rhs: Long): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedAddUnsigned(rhs: ULong): Long? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictAddUnsigned(rhs: ULong): Long {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedSub(rhs: Long): Long? {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictSub(rhs: Long): Long {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Long.uncheckedSub(rhs: Long): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedSubUnsigned(rhs: ULong): Long? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictSubUnsigned(rhs: ULong): Long {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedMul(rhs: Long): Long? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictMul(rhs: Long): Long {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun Long.uncheckedMul(rhs: Long): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None` if `rhs == 0`
 * or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedDiv(rhs: Long): Long? =
    if (rhs == 0L || ((this == Long.MIN_VALUE) && (rhs == -1L))) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to normal division: `MIN / - 1` will also panic both in
 * debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictDiv(rhs: Long): Long = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`,
 * returning `None` if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedDivEuclid(rhs: Long): Long? =
    if (rhs == 0L || ((this == Long.MIN_VALUE) and (rhs == -1L))) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to `divEuclid`: `MIN.divEuclid(- 1)` will also panic both
 * in debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictDivEuclid(rhs: Long): Long = rustDivEuclid(this, rhs)

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0`, the division results in overflow,
 * or `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.checkedDivExact(rhs: Long): Long? =
    if (rhs == 0L || ((this == Long.MIN_VALUE) && (rhs == -1L))) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.divExact(rhs: Long): Long? =
    if (this % rhs != 0L) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0`, `self % rhs != 0`, or
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun Long.uncheckedDivExact(rhs: Long): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_div_exact cannot overflow, divide by zero, or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0, lhs, MIN, rhs, 1)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None` if
 * `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedRem(rhs: Long): Long? =
    if (rhs == 0L || ((this == Long.MIN_VALUE) && (rhs == -1L))) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictRem(rhs: Long): Long {
    val (a, b) = this.overflowingRem(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Checked Euclidean remainder. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedRemEuclid(rhs: Long): Long? =
    if (rhs == 0L || ((this == Long.MIN_VALUE) and (rhs == -1L))) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean remainder. Computes `self.remEuclid(rhs)`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictRemEuclid(rhs: Long): Long {
    val (a, b) = this.overflowingRemEuclid(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(-1)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 *
 * Notably, this prevents both values from being negative.
 */
public fun Long.uncheckedDisjointBitor(rhs: Long): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Checked negation. Computes `- self`, returning `None` if `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedNeg(): Long? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Unchecked negation. Computes `- self`, assuming overflow cannot occur.
 *
 * # Safety
 *
 * This results in undefined behavior when
 * i.e. when [checkedNeg] would return `None`.
 */
public fun Long.uncheckedNeg(): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_neg cannot overflow", lhs, this, lhs, overflowingNeg, 1)
    return uncheckedSub(0, this)
}

/**
 * Strict negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictNeg(): Long {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None` if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedShl(rhs: UInt): Long? =
    if (rhs < 64u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictShl(rhs: UInt): Long {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun Long.uncheckedShl(rhs: UInt): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.unboundedShl(rhs: UInt): Long =
    if (rhs < 64u) {
        this.uncheckedShl(rhs)
    } else {
        0L
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any bits that would be shifted out differ from the resulting sign bit
 * or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.shlExact(rhs: UInt): Long? =
    if (rhs < rustLeadingZeros(this) || rhs < rustLeadingOnes(this)) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs >= self.leadingZeros() && rhs >=
 * self.leadingOnes()` i.e. when
 * would return `None`.
 */
public fun Long.uncheckedShlExact(rhs: UInt): Long {
    assertUnsafePrecondition(checkLibraryUb, "i64::unchecked_shl_exact cannot shift out bits that would change the value of the first bit", zeros, this, leadingZeros, ones, this, leadingOnes, rhs, rhs, rhs, zeros, rhs, ones)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None` if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedShr(rhs: UInt): Long? =
    if (rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictShr(rhs: UInt): Long {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun Long.uncheckedShr(rhs: UInt): Long {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, which yields `0` for a positive number,
 * and `- 1` for a negative number.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.unboundedShr(rhs: UInt): Long =
    if (rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        this.uncheckedShr(64u - 1)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.shrExact(rhs: UInt): Long? =
    if (rhs <= rustTrailingZeros(this) && rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun Long.uncheckedShrExact(rhs: UInt): Long {
    assertUnsafePrecondition(checkLibraryUb, "i64::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked absolute value. Computes `self.abs()`, returning `None` if
 * `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedAbs(): Long? =
    if (this.isNegative()) {
        rustCheckedNeg(this)
    } else {
        this
    }

/**
 * Strict absolute value. Computes `self.abs()`, panicking if
 * `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictAbs(): Long =
    if (this.isNegative()) {
        this.strictNeg()
    } else {
        this
    }

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedPow(exp: UInt): Long? {
    var exp1 = exp
    var base = this
    var acc: Long = 1L
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return if (base < 0L && (exp1 % 2u) == 1u) {
            ((-1).toLong()).shlExact(shift)
        } else {
            (1.toLong()).shlExact(shift)
        }
    }
    if (exp1 == 0u) {
        return 1L
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Long.strictPow(exp: UInt): Long =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * Returns `None` if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedIsqrt(): Long? =
    if (this < 0L) {
        null
    } else {
        val result = this.castUnsigned().isqrt().castSigned()
        run {
            val MAX_RESULT: Long = Long.MAX_VALUE.castUnsigned().isqrt().castSigned()
            assertUnchecked(result <= MAX_RESULT)
        }
        result
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingAdd(rhs: Long): Long = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingAddUnsigned(rhs: ULong): Long =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> Long.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingSub(rhs: Long): Long = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingSubUnsigned(rhs: ULong): Long =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> Long.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer negation. Computes `- self`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingNeg(): Long = saturatingSub(0, this)

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self ==
 * MIN` instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingAbs(): Long =
    if (this.isNegative()) {
        rustSaturatingNeg(this)
    } else {
        this
    }

/**
 * Saturating integer multiplication. Computes `self * rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingMul(rhs: Long): Long =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> if ((this < 0L) == (rhs < 0L)) {
            Long.MAX_VALUE
        } else {
            Long.MIN_VALUE
        }
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingDiv(rhs: Long): Long =
    run {
        val matchVal = this.overflowingDiv(rhs)
        when {
            matchVal.second == false -> {
                val result = matchVal.first
                result
            }
            else -> Long.MAX_VALUE
        }
    }

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.saturatingPow(exp: UInt): Long =
    run {
        val matchVal = rustCheckedPow(this, exp)
        when {
            matchVal != null -> {
                val x = matchVal
                x
            }
            matchVal == null && this < 0L && exp % 2u == 1u -> Long.MIN_VALUE
            else -> Long.MAX_VALUE
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingAdd(rhs: Long): Long = (this + rhs)

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingAddUnsigned(rhs: ULong): Long = (this + (rhs.toLong()))

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingSub(rhs: Long): Long = (this - rhs)

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingSubUnsigned(rhs: ULong): Long = (this - (rhs.toLong()))

/**
 * Wrapping (modular) multiplication. Computes `self * rhs`, wrapping around at
 * the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check((11 * (12.toByte())).toByte() == -124)
 * ```
 */
public fun Long.wrappingMul(rhs: Long): Long = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`, wrapping around at the
 * boundary of the type.
 *
 * The only case where such wrapping can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); this is equivalent to `- MIN`, a positive value
 * that is too large to represent in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDiv(-1) == -128)
 * ```
 */
public fun Long.wrappingDiv(rhs: Long): Long = this.overflowingDiv(rhs).first

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`,
 * wrapping around at the boundary of the type.
 *
 * Wrapping will only occur in `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value
 * for the type). This is equivalent to `- MIN`, a positive value that is too large to represent in the
 * type. In this case, this method returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDivEuclid(-1) == -128)
 * ```
 */
public fun Long.wrappingDivEuclid(rhs: Long): Long = this.overflowingDivEuclid(rhs).first

/**
 * Wrapping (modular) remainder. Computes `self % rhs`, wrapping around at the
 * boundary of the type.
 *
 * Such wrap-around never actually occurs mathematically; implementation artifacts make `x % y`
 * invalid for `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value). In such a case,
 * this function returns `0`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRem(-1) == 0)
 * ```
 */
public fun Long.wrappingRem(rhs: Long): Long = this.overflowingRem(rhs).first

/**
 * Wrapping Euclidean remainder. Computes `self.remEuclid(rhs)`, wrapping around
 * at the boundary of the type.
 *
 * Wrapping will only occur in `MIN % - 1` on a signed type (where [`Min`](Self.Min) is
 * the negative minimal value for the type). In this case, this method returns 0.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRemEuclid(-1) == 0)
 * ```
 */
public fun Long.wrappingRemEuclid(rhs: Long): Long = this.overflowingRemEuclid(rhs).first

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * The only case where such wrapping can occur is when one negates [`Min`](Self.Min) on a signed type (where [`Min`](Self.Min)
 * is the negative minimal value for the type); this is a positive value that is too large to represent
 * in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingNeg(): Long = ((0.toLong()) - (this.toLong()))

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`, where `mask` removes
 * any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the RHS of a wrapping shift-left is restricted to
 * the range of the type, rather than the bits shifted out of the LHS being returned to the other end.
 * The primitive integer types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.wrappingShl(rhs: UInt): Long = this.uncheckedShl(rhs and (64u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`, where `mask`
 * removes any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the RHS of a wrapping shift-right is restricted
 * to the range of the type, rather than the bits shifted out of the LHS being returned to the other
 * end. The primitive integer types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128).wrappingShr(64u) == -128)
 * ```
 */
public fun Long.wrappingShr(rhs: UInt): Long = this.uncheckedShr(rhs and (64u - 1u))

/**
 * Wrapping (modular) absolute value. Computes `self.abs()`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type; this is a positive value that is too large to represent in the type. In
 * such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingAbs().toUByte() == 128u.toUByte())
 * ```
 */
public fun Long.wrappingAbs(): Long =
    if (this.isNegative()) {
        rustWrappingNeg(this)
    } else {
        this
    }

/**
 * Computes the absolute value of `self` without any wrapping
 * or panicking.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).unsignedAbs() == 128u.toUByte())
 * ```
 */
public fun Long.unsignedAbs(): ULong = rustWrappingAbs(this).toULong()

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.wrappingPow(5) == -13)
 * check(3.wrappingPow(6) == -39)
 * ```
 */
public fun Long.wrappingPow(exp: UInt): Long {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would have
 * occurred then the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingAdd(rhs: Long): Pair<Long, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toLong(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and checks for overflow.
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns a tuple of the sum along with a boolean indicating
 * whether an arithmetic overflow would occur. On overflow, the wrapped
 * value is returned.
 *
 * This allows chaining together multiple additions to create a wider
 * addition, and can be useful for bignum addition. This method should
 * only be used for the most significant word; for the less significant
 * words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a carry flag,
 * and should *not* be added to a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val carry0 = false
 *     val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 *     check(carry1 == true)
 *     val (sum1, overflow) = a1.carryingAdd(b1, carry1)
 *     check(overflow == false)
 *     check(Pair(sum1, sum0) == Pair(6, 8))
 * }
 * ```
 */
public fun Long.carryingAdd(rhs: Long, carry: Boolean): Pair<Long, Boolean> {
    val (a, b) = rustOverflowingAdd(this, rhs)
    val (c, d) = a.overflowingAdd(carry.toLong())
    return Pair(c, b != d)
}

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingAddUnsigned(rhs: ULong): Pair<Long, Boolean> {
    val rhs1 = rhs.toLong()
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0L))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned
 * (negative if overflowed above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingSub(rhs: Long): Pair<Long, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toLong(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and checks for
 * overflow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns a tuple of the
 * difference along with a boolean indicating whether an arithmetic
 * overflow would occur. On overflow, the wrapped value is returned.
 *
 * This allows chaining together multiple subtractions to create a
 * wider subtraction, and can be useful for bignum subtraction. This
 * method should only be used for the most significant word; for the
 * less significant words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a borrow flag,
 * and should *not* be subtracted from a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input borrow is false, this method is equivalent to
 * [`overflowingSub`](Self.overflowingSub).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val borrow0 = false
 *     val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 *     check(borrow1 == true)
 *     val (diff1, overflow) = a1.borrowingSub(b1, borrow1)
 *     check(overflow == false)
 * }
 * ```
 */
public fun Long.borrowingSub(rhs: Long, borrow: Boolean): Pair<Long, Boolean> {
    val (a, b) = rustOverflowingSub(this, rhs)
    val (c, d) = a.overflowingSub(borrow.toLong())
    return Pair(c, b != d)
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingSubUnsigned(rhs: ULong): Pair<Long, Boolean> {
    val rhs1 = rhs.toLong()
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0L))
}

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * check(1000000000.overflowingMul(10) == Pair(1410065408, true))
 * ```
 */
public fun Long.overflowingMul(rhs: Long): Pair<Long, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toLong(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why [i32] is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMul(-2, 0) == Pair(4294967286, -1))
 *     check(5.carryingMul(-2, 10) == Pair(0, 0))
 *     check(1000000000.carryingMul(-10, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMul(-10, 10) == Pair(2884901898, -3))
 * }
 * ```
 */
public fun Long.carryingMul(rhs: Long, carry: Long): Pair<ULong, Long> = Long.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you only need one `carry`, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Int` is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMulAdd(-2, 0, 0) == Pair(4294967286, -1))
 *     check(5.carryingMulAdd(-2, 10, 10) == Pair(10, 0))
 *     check(1000000000.carryingMulAdd(-10, 0, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMulAdd(-10, 10, 10) == Pair(2884901908, -3))
 * }
 * ```
 */
public fun Long.carryingMulAdd(rhs: Long, carry: Long, add: Long): Pair<ULong, Long> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then self is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingDiv(rhs: Long): Pair<Long, Boolean> =
    if ((this == Long.MIN_VALUE) and (rhs == -1L)) {
        Pair(this, true)
    } else {
        Pair(this / rhs, false)
    }

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then `self` is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingDivEuclid(rhs: Long): Pair<Long, Boolean> =
    if ((this == Long.MIN_VALUE) and (rhs == -1L)) {
        Pair(this, true)
    } else {
        Pair(rustDivEuclid(this, rhs), false)
    }

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingRem(rhs: Long): Pair<Long, Boolean> =
    if (rhs == -1L) {
        Pair(0, this == Long.MIN_VALUE)
    } else {
        Pair(this % rhs, false)
    }

/**
 * Overflowing Euclidean remainder. Calculates `self.remEuclid(rhs)`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingRemEuclid(rhs: Long): Pair<Long, Boolean> =
    if (rhs == -1L) {
        Pair(0, this == Long.MIN_VALUE)
    } else {
        Pair(rustRemEuclid(this, rhs), false)
    }

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * Returns a tuple of the negated version of self along with a boolean indicating whether an overflow
 * happened. If `self` is the minimum value (e.g., [i32.Min] for values of type [i32]), then the
 * minimum value will be returned again and `true` will be returned for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingNeg(): Pair<Long, Boolean> =
    if (this == Long.MIN_VALUE) {
        Pair(Long.MIN_VALUE, true)
    } else {
        Pair(-this, false)
    }

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x1.overflowingShl(36u) == Pair(0x10, true))
 * ```
 */
public fun Long.overflowingShl(rhs: UInt): Pair<Long, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 64u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x10.overflowingShr(36u) == Pair(0x1, true))
 * ```
 */
public fun Long.overflowingShr(rhs: UInt): Pair<Long, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 64u)

/**
 * Computes the absolute value of `self`.
 *
 * Returns a tuple of the absolute version of self along with a boolean indicating whether an overflow
 * happened. If self is the minimum value
 * then the minimum value will be returned again and true will be returned
 * for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.overflowingAbs(): Pair<Long, Boolean> = Pair(rustWrappingAbs(this), this == Long.MIN_VALUE)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.overflowingPow(5) == Pair(-13, true))
 * ```
 */
public fun Long.overflowingPow(exp: UInt): Pair<Long, Boolean> {
    var exp1 = exp
    val base = this
    val acc: Long  = 1L
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        val base1: Long =
            if (base < 0L && (exp1 % 2u) != 0u) {
                -1L
            } else {
                1L
            }
        return Pair(base1.unboundedShl(shift), base1.shlExact(shift) == null)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * check(x.pow(5) == 32)
 * ```
 */
public fun Long.pow(exp: UInt): Long =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * # Panics
 *
 * This function will panic if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.isqrt(): Long =
    when (val matchVal = this.checkedIsqrt()) {
        null -> panicForNegativeArgument()
        else -> {
            val sqrt = matchVal
            sqrt
        }
    }

/**
 * Calculates the quotient of Euclidean division of `self` by `rhs`.
 *
 * This computes the integer `q` such that `self = q * rhs + r`, with
 * `r = self.remEuclid(rhs)` and `0 <= r<abs(rhs)`.
 *
 * In other words, the result is `self / rhs` rounded to the integer `q`
 * such that `self >= q * rhs`.
 * If `self> 0`, this is equal to rounding towards zero (the default in Rust);
 * if `self<0`, this is equal to rounding away from zero (towards +/- infinity).
 * If `rhs> 0`, this is equal to rounding towards -infinity;
 * if `rhs<0`, this is equal to rounding towards +infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.divEuclid(b) == 1)
 * // 7 >= 4 * 1
 * check(a.divEuclid(-b) == -1)
 * // 7 >= -4 * -1
 * check((-a).divEuclid(b) == -2)
 * // -7 >= 4 * -2
 * check((-a).divEuclid(-b) == 2)
 * // -7 >= -4 * 2
 * ```
 */
public fun Long.divEuclid(rhs: Long): Long {
    val q = this / rhs
    if (this % rhs < 0L) {
        return if (rhs > 0L) {
            q - 1L
        } else {
            q + 1L
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
 *
 * This is done as if by the Euclidean division algorithm -- given
 * `r = self.remEuclid(rhs)`, the result satisfies
 * `self = rhs * self.divEuclid(rhs) + r` and `0 <= r<abs(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min] and
 * `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.remEuclid(b) == 3)
 * check((-a).remEuclid(b) == 1)
 * check(a.remEuclid(-b) == 3)
 * check((-a).remEuclid(-b) == 1)
 * ```
 *
 * This will panic:
 * ```kotlin
 * ```
 */
public fun Long.remEuclid(rhs: Long): Long {
    val r = this % rhs
    return if (r < 0L) {
        (r + rustWrappingAbs(rhs).toLong())
    } else {
        r
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divFloor(b) == 2)
 *     check(a.divFloor(-b) == -3)
 *     check((-a).divFloor(b) == -3)
 *     check((-a).divFloor(-b) == 2)
 * }
 * ```
 */
public fun Long.divFloor(rhs: Long): Long {
    val d = this / rhs
    val r = this % rhs
    val correction = (this xor rhs) shr (64u - 1).toInt()
    return if (r != 0L) {
        d + correction
    } else {
        d
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divCeil(b) == 3)
 *     check(a.divCeil(-b) == -2)
 *     check((-a).divCeil(b) == -2)
 *     check((-a).divCeil(-b) == 3)
 * }
 * ```
 */
public fun Long.divCeil(rhs: Long): Long {
    val d = this / rhs
    val r = this % rhs
    val correction = 1L + ((this xor rhs) shr (64u - 1).toInt())
    return if (r != 0L) {
        d + correction
    } else {
        d
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.nextMultipleOf(rhs: Long): Long {
    if (rhs == -1L) {
        return this
    }
    val r = this % rhs
    val m =
        if ((r > 0L && rhs < 0L) || (r < 0L && rhs > 0L)) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        this + (rhs - m)
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`. Returns `None` if `rhs` is zero or the operation
 * would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.checkedNextMultipleOf(rhs: Long): Long? {
    if (rhs == -1L) {
        return this
    }
    val r =
        when (val matchVal = rustCheckedRem(this, rhs)) {
            null -> { return null }
            else -> {
                val x = matchVal
                x
            }
        }
    val m =
        if ((r > 0 && rhs < 0L) || (r < 0 && rhs > 0L)) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        rustCheckedAdd(this, rhs - m)
    }
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`][Self.ilog2] can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero,
 * or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.ilog(base: Long): UInt {
    check(base >= 2L) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Long.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is negative or zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedIlog(base: Long): UInt? =
    if (this <= 0L || base <= 1L) {
        null
    } else {
        (this.toULong()).checkedIlog(base.toULong())
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedIlog2(): UInt? =
    if (this <= 0L) {
        null
    } else {
        val log = (64u - 1u) - kmCtlzU64((this).toULong())
        log
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun Long.checkedIlog10(): UInt? = io.github.kotlinmania.rustlang.core.num.imp.intlog10.Long(this)

/**
 * Computes the absolute value of `self`.
 *
 * # Overflow behavior
 *
 * The absolute value of
 * cannot be represented as an
 * and attempting to calculate it will cause an overflow. This means
 * that code in debug mode will trigger a panic on this case and
 * optimized code will return
 * without a panic. If you do not want this behavior, consider
 * using [`unsignedAbs`](Self.unsignedAbs) instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.abs(): Long =
    if (this.isNegative()) {
        -this
    } else {
        this
    }

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * This function always returns the correct answer without overflow or
 * panics by returning an unsigned integer.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.absDiff(other: Long): ULong =
    if (this < other) {
        ((other.toULong()) - (this.toULong()))
    } else {
        ((this.toULong()) - (other.toULong()))
    }

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.signum(): Long = threeWayCompare(this, 0).toLong()

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.isPositive(): Boolean = this > 0L

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.isNegative(): Boolean = this < 0L

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.toBeBytes(): UByteArray = run { val rawBits = (((((this).toULong() shl 56)) or (((this).toULong() and 0x000000000000FF00uL) shl 40) or (((this).toULong() and 0x0000000000FF0000uL) shl 24) or (((this).toULong() and 0x00000000FF000000uL) shl 8) or ((((this).toULong() shr 8) and 0x00000000FF000000uL)) or ((((this).toULong() shr 24) and 0x0000000000FF0000uL)) or ((((this).toULong() shr 40) and 0x000000000000FF00uL)) or ((this).toULong() shr 56)).toLong()).toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.toLeBytes(): UByteArray = run { val rawBits = this.toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun Long.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates an integer value from its representation as a byte array in
 * big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Long.Companion.fromBeBytes(bytes: UByteArray): Long = run { val swapVal0 = (Long.fromNeBytes(bytes)); (((((swapVal0).toULong() shl 56)) or (((swapVal0).toULong() and 0x000000000000FF00uL) shl 40) or (((swapVal0).toULong() and 0x0000000000FF0000uL) shl 24) or (((swapVal0).toULong() and 0x00000000FF000000uL) shl 8) or ((((swapVal0).toULong() shr 8) and 0x00000000FF000000uL)) or ((((swapVal0).toULong() shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0).toULong() shr 40) and 0x000000000000FF00uL)) or ((swapVal0).toULong() shr 56)).toLong()) }

/**
 * Creates an integer value from its representation as a byte array in
 * little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Long.Companion.fromLeBytes(bytes: UByteArray): Long = Long.fromNeBytes(bytes)

/**
 * Creates an integer value from its memory representation as a byte
 * array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun Long.Companion.fromNeBytes(bytes: UByteArray): Long = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()), PrimitiveLayouts.I64)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun Long.Companion.minValue(): Long = Long.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun Long.Companion.maxValue(): Long = Long.MAX_VALUE

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.clampMagnitude(limit: ULong): Long =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120 == 376.truncate())
 * }
 * ```
 */
public inline fun <reified Target> Long.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(127 == 376.saturatingTruncate())
 *     check(-128.toByte() == (-1000).saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> Long.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376.checkedTruncate<Byte>())
 *     check(null == (-1000).checkedTruncate<Byte>())
 * }
 * ```
 */
public inline fun <reified Target> Long.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> Long.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Long, T>> Long.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<Long, T>> Long.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Long, T>> Long.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<Long, T>> Long.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<Long, T>> Long.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "i64::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Long.midpoint(rhs: Long): Long {
    val t = ((this xor rhs) shr 1) + (this and rhs)
    return t +
        (if (t < 0L) {
            1L
        } else {
            0L
        } and (this xor rhs))
}

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun Long.wideningMul(rhs: Long): I128 = I128.fromLong(this) * I128.fromLong(rhs)

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN4: I128 = I128.MAX_VALUE.inv()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX4: I128 = (U128.MAX_VALUE shr 1).toI128()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS4: UInt = 128u

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 * }
 * ```
 */
public fun I128.bit(index: UInt): Boolean = this.castUnsigned().bit(index)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     check(n == 0x2A)
 * }
 * ```
 */
public fun I128.setBit(index: UInt, value: Boolean): I128 = this.castUnsigned().setBit(index, value).castSigned()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 1)
 * ```
 */
public fun I128.countOnes(): UInt = rustCountOnes((this.toU128()))

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun I128.leadingZeros(): UInt = rustLeadingZeros((this.toU128()))

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 2)
 * ```
 */
public fun I128.trailingZeros(): UInt = rustTrailingZeros((this.toU128()))

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.leadingOnes(): UInt = rustLeadingOnes((this.toU128()))

/**
 * Returns the number of trailing ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 2)
 * ```
 */
public fun I128.trailingOnes(): UInt = rustTrailingOnes((this.toU128()))

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun I128.isolateHighestOne(): I128 = this and (rustWrappingShr(((I128.fromLong(1.toLong())) shl (128u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun I128.isolateLowestOne(): I128 = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.highestOne(): UInt? = (this.toU128()).highestOne()

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.lowestOne(): UInt? = (this.toU128()).lowestOne()

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.castUnsigned(): U128 = this.toU128()

/**
 * Saturating conversion of `self` to an unsigned integer of the same size.
 *
 * Negative values are clamped to `0`.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.saturatingCastUnsigned(): U128 =
    if (this >= I128(0x0L, 0x0uL)) {
        this.castUnsigned()
    } else {
        U128(0x0uL, 0x0uL)
    }

/**
 * Checked conversion of `self` to an unsigned integer of the same size,
 * returning `None` if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`saturatingCastUnsigned`](Self.saturatingCastUnsigned),
 * or [`strictCastUnsigned`](Self.strictCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.checkedCastUnsigned(): U128? =
    if (this >= I128(0x0L, 0x0uL)) {
        this.castUnsigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to an unsigned integer of the same size,
 * which panics if `self` is negative.
 *
 * For other kinds of unsigned integer casts, see
 * [`castUnsigned`](Self.castUnsigned),
 * [`checkedCastUnsigned`](Self.checkedCastUnsigned),
 * or [`saturatingCastUnsigned`](Self.saturatingCastUnsigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.strictCastUnsigned(): U128 =
    when (val matchVal = this.checkedCastUnsigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.rotateLeft(n: UInt): I128 = rustRotateLeft((this.toU128()), n).toI128()

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.rotateRight(n: UInt): I128 = rustRotateRight((this.toU128()), n).toI128()

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun I128.swapBytes(): I128 = rustSwapBytes((this.toU128())).toI128()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun I128.reverseBits(): I128 = rustReverseBits((this.toU128())).toI128()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are swapped.
 *
 * See also [fromBeBytes()](Self.fromBeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun I128.Companion.fromBe(x: I128): I128 = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are swapped.
 *
 * See also [fromLeBytes()](Self.fromLeBytes).
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun I128.Companion.fromLe(x: I128): I128 = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Swaps bytes of `self` on little endian targets.
 *
 * On big endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toBeBytes`](Self.toBeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun I128.toBe(): I128 = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Swaps bytes of `self` on big endian targets.
 *
 * On little endian this is a no-op.
 *
 * The returned value has the same type as `self`, and will be interpreted
 * as (a potentially different) value of a native-endian
 *
 * See [`toLeBytes`](Self.toLeBytes) for a type-safe alternative.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun I128.toLe(): I128 = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedAdd(rhs: I128): I128? {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictAdd(rhs: I128): I128 {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun I128.uncheckedAdd(rhs: I128): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedAddUnsigned(rhs: U128): I128? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictAddUnsigned(rhs: U128): I128 {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedSub(rhs: I128): I128? {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictSub(rhs: I128): I128 {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun I128.uncheckedSub(rhs: I128): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedSubUnsigned(rhs: U128): I128? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictSubUnsigned(rhs: U128): I128 {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedMul(rhs: I128): I128? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictMul(rhs: I128): I128 {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun I128.uncheckedMul(rhs: I128): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None` if `rhs == 0`
 * or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedDiv(rhs: I128): I128? =
    if (rhs == I128(0x0L, 0x0uL) || ((this == I128.MIN_VALUE) && (rhs == -I128(0x0L, 0x1uL)))) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to normal division: `MIN / - 1` will also panic both in
 * debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictDiv(rhs: I128): I128 = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`,
 * returning `None` if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedDivEuclid(rhs: I128): I128? =
    if (rhs == I128(0x0L, 0x0uL) || ((this == I128.MIN_VALUE) and (rhs == -I128(0x0L, 0x1uL)))) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); the result of this is `- MIN`, a positive value
 * that is too large to represent in the type.
 *
 * Note that this is equivalent to `divEuclid`: `MIN.divEuclid(- 1)` will also panic both
 * in debug and release builds.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictDivEuclid(rhs: I128): I128 = rustDivEuclid(this, rhs)

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0`, the division results in overflow,
 * or `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.checkedDivExact(rhs: I128): I128? =
    if (rhs == I128(0x0L, 0x0uL) || ((this == I128.MIN_VALUE) && (rhs == -I128(0x0L, 0x1uL)))) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.divExact(rhs: I128): I128? =
    if (this % rhs != I128(0x0L, 0x0uL)) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0`, `self % rhs != 0`, or
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun I128.uncheckedDivExact(rhs: I128): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_div_exact cannot overflow, divide by zero, or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0, lhs, MIN, rhs, 1)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None` if
 * `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedRem(rhs: I128): I128? =
    if (rhs == I128(0x0L, 0x0uL) || ((this == I128.MIN_VALUE) && (rhs == -I128(0x0L, 0x1uL)))) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictRem(rhs: I128): I128 {
    val (a, b) = this.overflowingRem(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Checked Euclidean remainder. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0` or the division results in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedRemEuclid(rhs: I128): I128? =
    if (rhs == I128(0x0L, 0x0uL) || ((this == I128.MIN_VALUE) and (rhs == -I128(0x0L, 0x1uL)))) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean remainder. Computes `self.remEuclid(rhs)`, panicking if
 * the division results in overflow.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * The only case where such an overflow can occur is `x % y` for `MIN / - 1` on a
 * signed type (where [`Min`](Self.Min) is the negative minimal value), which is invalid due to implementation artifacts.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictRemEuclid(rhs: I128): I128 {
    val (a, b) = this.overflowingRemEuclid(rhs)
    return if (b) {
        rem()
    } else {
        a
    }
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(-1)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 *
 * Notably, this prevents both values from being negative.
 */
public fun I128.uncheckedDisjointBitor(rhs: I128): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Checked negation. Computes `- self`, returning `None` if `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedNeg(): I128? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Unchecked negation. Computes `- self`, assuming overflow cannot occur.
 *
 * # Safety
 *
 * This results in undefined behavior when
 * i.e. when [checkedNeg] would return `None`.
 */
public fun I128.uncheckedNeg(): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_neg cannot overflow", lhs, this, lhs, overflowingNeg, 1)
    return uncheckedSub(0, this)
}

/**
 * Strict negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictNeg(): I128 {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None` if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedShl(rhs: UInt): I128? =
    if (rhs < 128u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictShl(rhs: UInt): I128 {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun I128.uncheckedShl(rhs: UInt): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.unboundedShl(rhs: UInt): I128 =
    if (rhs < 128u) {
        this.uncheckedShl(rhs)
    } else {
        I128(0x0L, 0x0uL)
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any bits that would be shifted out differ from the resulting sign bit
 * or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.shlExact(rhs: UInt): I128? =
    if (rhs < rustLeadingZeros(this) || rhs < rustLeadingOnes(this)) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs >= self.leadingZeros() && rhs >=
 * self.leadingOnes()` i.e. when
 * would return `None`.
 */
public fun I128.uncheckedShlExact(rhs: UInt): I128 {
    assertUnsafePrecondition(checkLibraryUb, "i128::unchecked_shl_exact cannot shift out bits that would change the value of the first bit", zeros, this, leadingZeros, ones, this, leadingOnes, rhs, rhs, rhs, zeros, rhs, ones)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None` if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedShr(rhs: UInt): I128? =
    if (rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictShr(rhs: UInt): I128 {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun I128.uncheckedShr(rhs: UInt): I128 {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, which yields `0` for a positive number,
 * and `- 1` for a negative number.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.unboundedShr(rhs: UInt): I128 =
    if (rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        this.uncheckedShr(128u - 1)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.shrExact(rhs: UInt): I128? =
    if (rhs <= rustTrailingZeros(this) && rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun I128.uncheckedShrExact(rhs: UInt): I128 {
    assertUnsafePrecondition(checkLibraryUb, "i128::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked absolute value. Computes `self.abs()`, returning `None` if
 * `self == MIN`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedAbs(): I128? =
    if (this.isNegative()) {
        rustCheckedNeg(this)
    } else {
        this
    }

/**
 * Strict absolute value. Computes `self.abs()`, panicking if
 * `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictAbs(): I128 =
    if (this.isNegative()) {
        this.strictNeg()
    } else {
        this
    }

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedPow(exp: UInt): I128? {
    var exp1 = exp
    var base = this
    var acc: I128 = I128(0x0L, 0x1uL)
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return if (base < I128(0x0L, 0x0uL) && (exp1 % 2u) == 1u) {
            (I128.fromLong((-1).toLong())).shlExact(shift)
        } else {
            (I128.fromLong(1.toLong())).shlExact(shift)
        }
    }
    if (exp1 == 0u) {
        return I128(0x0L, 0x1uL)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun I128.strictPow(exp: UInt): I128 =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * Returns `None` if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedIsqrt(): I128? =
    if (this < I128(0x0L, 0x0uL)) {
        null
    } else {
        val result = this.castUnsigned().isqrt().castSigned()
        run {
            val MAX_RESULT: I128 = I128.MAX_VALUE.castUnsigned().isqrt().castSigned()
            assertUnchecked(result <= MAX_RESULT)
        }
        result
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingAdd(rhs: I128): I128 = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingAddUnsigned(rhs: U128): I128 =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> I128.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingSub(rhs: I128): I128 = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingSubUnsigned(rhs: U128): I128 =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> I128.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer negation. Computes `- self`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingNeg(): I128 = saturatingSub(0, this)

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self ==
 * MIN` instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingAbs(): I128 =
    if (this.isNegative()) {
        rustSaturatingNeg(this)
    } else {
        this
    }

/**
 * Saturating integer multiplication. Computes `self * rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingMul(rhs: I128): I128 =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> if ((this < I128(0x0L, 0x0uL)) == (rhs < I128(0x0L, 0x0uL))) {
            I128.MAX_VALUE
        } else {
            I128.MIN_VALUE
        }
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingDiv(rhs: I128): I128 =
    run {
        val matchVal = this.overflowingDiv(rhs)
        when {
            matchVal.second == false -> {
                val result = matchVal.first
                result
            }
            else -> I128.MAX_VALUE
        }
    }

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.saturatingPow(exp: UInt): I128 =
    run {
        val matchVal = rustCheckedPow(this, exp)
        when {
            matchVal != null -> {
                val x = matchVal
                x
            }
            matchVal == null && this < I128(0x0L, 0x0uL) && exp % 2u == 1u -> I128.MIN_VALUE
            else -> I128.MAX_VALUE
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingAdd(rhs: I128): I128 = (this + rhs)

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingAddUnsigned(rhs: U128): I128 = rustWrappingAdd(this, rhs.toI128())

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`, wrapping around at the
 * boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingSub(rhs: I128): I128 = (this - rhs)

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingSubUnsigned(rhs: U128): I128 = rustWrappingSub(this, rhs.toI128())

/**
 * Wrapping (modular) multiplication. Computes `self * rhs`, wrapping around at
 * the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check((11 * (12.toByte())).toByte() == -124)
 * ```
 */
public fun I128.wrappingMul(rhs: I128): I128 = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`, wrapping around at the
 * boundary of the type.
 *
 * The only case where such wrapping can occur is when one divides `MIN / - 1` on a signed type (where
 * [`Min`](Self.Min) is the negative minimal value for the type); this is equivalent to `- MIN`, a positive value
 * that is too large to represent in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDiv(-1) == -128)
 * ```
 */
public fun I128.wrappingDiv(rhs: I128): I128 = this.overflowingDiv(rhs).first

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`,
 * wrapping around at the boundary of the type.
 *
 * Wrapping will only occur in `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value
 * for the type). This is equivalent to `- MIN`, a positive value that is too large to represent in the
 * type. In this case, this method returns [`Min`](Self.Min) itself.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingDivEuclid(-1) == -128)
 * ```
 */
public fun I128.wrappingDivEuclid(rhs: I128): I128 = this.overflowingDivEuclid(rhs).first

/**
 * Wrapping (modular) remainder. Computes `self % rhs`, wrapping around at the
 * boundary of the type.
 *
 * Such wrap-around never actually occurs mathematically; implementation artifacts make `x % y`
 * invalid for `MIN / - 1` on a signed type (where [`Min`](Self.Min) is the negative minimal value). In such a case,
 * this function returns `0`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRem(-1) == 0)
 * ```
 */
public fun I128.wrappingRem(rhs: I128): I128 = this.overflowingRem(rhs).first

/**
 * Wrapping Euclidean remainder. Computes `self.remEuclid(rhs)`, wrapping around
 * at the boundary of the type.
 *
 * Wrapping will only occur in `MIN % - 1` on a signed type (where [`Min`](Self.Min) is
 * the negative minimal value for the type). In this case, this method returns 0.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingRemEuclid(-1) == 0)
 * ```
 */
public fun I128.wrappingRemEuclid(rhs: I128): I128 = this.overflowingRemEuclid(rhs).first

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * The only case where such wrapping can occur is when one negates [`Min`](Self.Min) on a signed type (where [`Min`](Self.Min)
 * is the negative minimal value for the type); this is a positive value that is too large to represent
 * in the type. In such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingNeg(): I128 = rustWrappingSub((I128.fromLong(0.toLong())), this)

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`, where `mask` removes
 * any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the RHS of a wrapping shift-left is restricted to
 * the range of the type, rather than the bits shifted out of the LHS being returned to the other end.
 * The primitive integer types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.wrappingShl(rhs: UInt): I128 = this.uncheckedShl(rhs and (128u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`, where `mask`
 * removes any high-order bits of `rhs` that would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the RHS of a wrapping shift-right is restricted
 * to the range of the type, rather than the bits shifted out of the LHS being returned to the other
 * end. The primitive integer types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128).wrappingShr(64u) == -128)
 * ```
 */
public fun I128.wrappingShr(rhs: UInt): I128 = this.uncheckedShr(rhs and (128u - 1u))

/**
 * Wrapping (modular) absolute value. Computes `self.abs()`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type; this is a positive value that is too large to represent in the type. In
 * such a case, this function returns [`Min`](Self.Min) itself.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).wrappingAbs().toUByte() == 128u.toUByte())
 * ```
 */
public fun I128.wrappingAbs(): I128 =
    if (this.isNegative()) {
        rustWrappingNeg(this)
    } else {
        this
    }

/**
 * Computes the absolute value of `self` without any wrapping
 * or panicking.
 *
 * # Examples
 *
 * ```kotlin
 * check((-128.toByte()).unsignedAbs() == 128u.toUByte())
 * ```
 */
public fun I128.unsignedAbs(): U128 = rustWrappingAbs(this).toU128()

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.wrappingPow(5) == -13)
 * check(3.wrappingPow(6) == -39)
 * ```
 */
public fun I128.wrappingPow(exp: UInt): I128 {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would have
 * occurred then the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingAdd(rhs: I128): Pair<I128, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(I128.fromLong(a.toLong()), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and checks for overflow.
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns a tuple of the sum along with a boolean indicating
 * whether an arithmetic overflow would occur. On overflow, the wrapped
 * value is returned.
 *
 * This allows chaining together multiple additions to create a wider
 * addition, and can be useful for bignum addition. This method should
 * only be used for the most significant word; for the less significant
 * words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a carry flag,
 * and should *not* be added to a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val carry0 = false
 *     val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 *     check(carry1 == true)
 *     val (sum1, overflow) = a1.carryingAdd(b1, carry1)
 *     check(overflow == false)
 *     check(Pair(sum1, sum0) == Pair(6, 8))
 * }
 * ```
 */
public fun I128.carryingAdd(rhs: I128, carry: Boolean): Pair<I128, Boolean> {
    val (a, b) = rustOverflowingAdd(this, rhs)
    val (c, d) = a.overflowingAdd(I128.fromLong(carry.toLong()))
    return Pair(c, b != d)
}

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingAddUnsigned(rhs: U128): Pair<I128, Boolean> {
    val rhs1 = rhs.toI128()
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < I128(0x0L, 0x0uL)))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned
 * (negative if overflowed above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingSub(rhs: I128): Pair<I128, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(I128.fromLong(a.toLong()), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and checks for
 * overflow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns a tuple of the
 * difference along with a boolean indicating whether an arithmetic
 * overflow would occur. On overflow, the wrapped value is returned.
 *
 * This allows chaining together multiple subtractions to create a
 * wider subtraction, and can be useful for bignum subtraction. This
 * method should only be used for the most significant word; for the
 * less significant words the unsigned method
 * should be used.
 *
 * The output boolean returned by this method is *not* a borrow flag,
 * and should *not* be subtracted from a more significant word.
 *
 * If overflow occurred, the wrapped value is returned (negative if overflowed
 * above [`Max`](Self.Max), non-negative if below [`Min`](Self.Min)).
 *
 * If the input borrow is false, this method is equivalent to
 * [`overflowingSub`](Self.overflowingSub).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     // Only the most significant word is signed.
 *     //
 *     // ---------
 *     val borrow0 = false
 *     val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 *     check(borrow1 == true)
 *     val (diff1, overflow) = a1.borrowingSub(b1, borrow1)
 *     check(overflow == false)
 * }
 * ```
 */
public fun I128.borrowingSub(rhs: I128, borrow: Boolean): Pair<I128, Boolean> {
    val (a, b) = rustOverflowingSub(this, rhs)
    val (c, d) = a.overflowingSub(I128.fromLong(borrow.toLong()))
    return Pair(c, b != d)
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingSubUnsigned(rhs: U128): Pair<I128, Boolean> {
    val rhs1 = rhs.toI128()
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < I128(0x0L, 0x0uL)))
}

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean indicating whether an arithmetic overflow
 * would occur. If an overflow would have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * check(1000000000.overflowingMul(10) == Pair(1410065408, true))
 * ```
 */
public fun I128.overflowingMul(rhs: I128): Pair<I128, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(I128.fromLong(a.toLong()), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why [i32] is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMul(-2, 0) == Pair(4294967286, -1))
 *     check(5.carryingMul(-2, 10) == Pair(0, 0))
 *     check(1000000000.carryingMul(-10, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMul(-10, 10) == Pair(2884901898, -3))
 * }
 * ```
 */
public fun I128.carryingMul(rhs: I128, carry: I128): Pair<U128, I128> = I128.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you only need one `carry`, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Int` is used.
 *
 * ```kotlin
 * @feature(signedBigintHelpers)
 * public fun doctestEntry() {
 *     check(5.carryingMulAdd(-2, 0, 0) == Pair(4294967286, -1))
 *     check(5.carryingMulAdd(-2, 10, 10) == Pair(10, 0))
 *     check(1000000000.carryingMulAdd(-10, 0, 0) == Pair(2884901888, -3))
 *     check(1000000000.carryingMulAdd(-10, 10, 10) == Pair(2884901908, -3))
 * }
 * ```
 */
public fun I128.carryingMulAdd(rhs: I128, carry: I128, add: I128): Pair<U128, I128> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then self is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingDiv(rhs: I128): Pair<I128, Boolean> =
    if ((this == I128.MIN_VALUE) and (rhs == -I128(0x0L, 0x1uL))) {
        Pair(this, true)
    } else {
        Pair(this / rhs, false)
    }

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating whether an arithmetic overflow would
 * occur. If an overflow would occur then `self` is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingDivEuclid(rhs: I128): Pair<I128, Boolean> =
    if ((this == I128.MIN_VALUE) and (rhs == -I128(0x0L, 0x1uL))) {
        Pair(this, true)
    } else {
        Pair(rustDivEuclid(this, rhs), false)
    }

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingRem(rhs: I128): Pair<I128, Boolean> =
    if (rhs == -I128(0x0L, 0x1uL)) {
        Pair(0, this == I128.MIN_VALUE)
    } else {
        Pair(this % rhs, false)
    }

/**
 * Overflowing Euclidean remainder. Calculates `self.remEuclid(rhs)`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean indicating whether an
 * arithmetic overflow would occur. If an overflow would occur then 0 is returned.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingRemEuclid(rhs: I128): Pair<I128, Boolean> =
    if (rhs == -I128(0x0L, 0x1uL)) {
        Pair(0, this == I128.MIN_VALUE)
    } else {
        Pair(rustRemEuclid(this, rhs), false)
    }

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * Returns a tuple of the negated version of self along with a boolean indicating whether an overflow
 * happened. If `self` is the minimum value (e.g., [i32.Min] for values of type [i32]), then the
 * minimum value will be returned again and `true` will be returned for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingNeg(): Pair<I128, Boolean> =
    if (this == I128.MIN_VALUE) {
        Pair(I128.MIN_VALUE, true)
    } else {
        Pair(-this, false)
    }

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x1.overflowingShl(36u) == Pair(0x10, true))
 * ```
 */
public fun I128.overflowingShl(rhs: UInt): Pair<I128, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 128u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean indicating whether the shift
 * value was larger than or equal to the number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * check(0x10.overflowingShr(36u) == Pair(0x1, true))
 * ```
 */
public fun I128.overflowingShr(rhs: UInt): Pair<I128, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 128u)

/**
 * Computes the absolute value of `self`.
 *
 * Returns a tuple of the absolute version of self along with a boolean indicating whether an overflow
 * happened. If self is the minimum value
 * then the minimum value will be returned again and true will be returned
 * for an overflow happening.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.overflowingAbs(): Pair<I128, Boolean> = Pair(rustWrappingAbs(this), this == I128.MIN_VALUE)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3.overflowingPow(5) == Pair(-13, true))
 * ```
 */
public fun I128.overflowingPow(exp: UInt): Pair<I128, Boolean> {
    var exp1 = exp
    val base = this
    val acc: I128  = I128(0x0L, 0x1uL)
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.unsignedAbs().isPowerOfTwo()) {
        val k = base.unsignedAbs().ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        val base1: I128 =
            if (base < I128(0x0L, 0x0uL) && (exp1 % 2u) != 0u) {
                -I128(0x0L, 0x1uL)
            } else {
                I128(0x0L, 0x1uL)
            }
        return Pair(base1.unboundedShl(shift), base1.shlExact(shift) == null)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * check(x.pow(5) == 32)
 * ```
 */
public fun I128.pow(exp: UInt): I128 =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the integer square root of the number, rounded down.
 *
 * This function returns the **principal (non-negative) square root**.
 * For a given number `n`, although both `x` and `- x` satisfy x<sup>2</sup> = n,
 * this function always returns the non-negative value.
 *
 * # Panics
 *
 * This function will panic if `self` is negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.isqrt(): I128 =
    when (val matchVal = this.checkedIsqrt()) {
        null -> panicForNegativeArgument()
        else -> {
            val sqrt = matchVal
            sqrt
        }
    }

/**
 * Calculates the quotient of Euclidean division of `self` by `rhs`.
 *
 * This computes the integer `q` such that `self = q * rhs + r`, with
 * `r = self.remEuclid(rhs)` and `0 <= r<abs(rhs)`.
 *
 * In other words, the result is `self / rhs` rounded to the integer `q`
 * such that `self >= q * rhs`.
 * If `self> 0`, this is equal to rounding towards zero (the default in Rust);
 * if `self<0`, this is equal to rounding away from zero (towards +/- infinity).
 * If `rhs> 0`, this is equal to rounding towards -infinity;
 * if `rhs<0`, this is equal to rounding towards +infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.divEuclid(b) == 1)
 * // 7 >= 4 * 1
 * check(a.divEuclid(-b) == -1)
 * // 7 >= -4 * -1
 * check((-a).divEuclid(b) == -2)
 * // -7 >= 4 * -2
 * check((-a).divEuclid(-b) == 2)
 * // -7 >= -4 * 2
 * ```
 */
public fun I128.divEuclid(rhs: I128): I128 {
    val q = this / rhs
    if (this % rhs < I128(0x0L, 0x0uL)) {
        return if (rhs > I128(0x0L, 0x0uL)) {
            q - I128(0x0L, 0x1uL)
        } else {
            q + I128(0x0L, 0x1uL)
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
 *
 * This is done as if by the Euclidean division algorithm -- given
 * `r = self.remEuclid(rhs)`, the result satisfies
 * `self = rhs * self.divEuclid(rhs) + r` and `0 <= r<abs(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min] and
 * `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * val b = 4
 * check(a.remEuclid(b) == 3)
 * check((-a).remEuclid(b) == 1)
 * check(a.remEuclid(-b) == 3)
 * check((-a).remEuclid(-b) == 1)
 * ```
 *
 * This will panic:
 * ```kotlin
 * ```
 */
public fun I128.remEuclid(rhs: I128): I128 {
    val r = this % rhs
    return if (r < I128(0x0L, 0x0uL)) {
        rustWrappingAdd(r, rustWrappingAbs(rhs))
    } else {
        r
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divFloor(b) == 2)
 *     check(a.divFloor(-b) == -3)
 *     check((-a).divFloor(b) == -3)
 *     check((-a).divFloor(-b) == 2)
 * }
 * ```
 */
public fun I128.divFloor(rhs: I128): I128 {
    val d = this / rhs
    val r = this % rhs
    val correction = (this xor rhs) shr (128u - 1).toInt()
    return if (r != I128(0x0L, 0x0uL)) {
        d + correction
    } else {
        d
    }
}

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero or if `self` is [Self.Min]
 * and `rhs` is -1. This behavior is not affected by the `overflow - checks` flag.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {
 *     val b = 3
 *     check(a.divCeil(b) == 3)
 *     check(a.divCeil(-b) == -2)
 *     check((-a).divCeil(b) == -2)
 *     check((-a).divCeil(-b) == 3)
 * }
 * ```
 */
public fun I128.divCeil(rhs: I128): I128 {
    val d = this / rhs
    val r = this % rhs
    val correction = I128(0x0L, 0x1uL) + ((this xor rhs) shr (128u - 1).toInt())
    return if (r != I128(0x0L, 0x0uL)) {
        d + correction
    } else {
        d
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.nextMultipleOf(rhs: I128): I128 {
    if (rhs == -I128(0x0L, 0x1uL)) {
        return this
    }
    val r = this % rhs
    val m =
        if ((r > I128(0x0L, 0x0uL) && rhs < I128(0x0L, 0x0uL)) || (r < I128(0x0L, 0x0uL) && rhs > I128(0x0L, 0x0uL))) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        this + (rhs - m)
    }
}

/**
 * If `rhs` is positive, calculates the smallest value greater than or
 * equal to `self` that is a multiple of `rhs`. If `rhs` is negative,
 * calculates the largest value less than or equal to `self` that is a
 * multiple of `rhs`. Returns `None` if `rhs` is zero or the operation
 * would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.checkedNextMultipleOf(rhs: I128): I128? {
    if (rhs == -I128(0x0L, 0x1uL)) {
        return this
    }
    val r =
        when (val matchVal = rustCheckedRem(this, rhs)) {
            null -> { return null }
            else -> {
                val x = matchVal
                x
            }
        }
    val m =
        if ((r > 0 && rhs < I128(0x0L, 0x0uL)) || (r < 0 && rhs > I128(0x0L, 0x0uL))) {
            r + rhs
        } else {
            r
        }
    return if (m == 0) {
        this
    } else {
        rustCheckedAdd(this, rhs - m)
    }
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`][Self.ilog2] can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero,
 * or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.ilog(base: I128): UInt {
    check(base >= I128(0x0L, 0x2uL)) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is less than or equal to zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun I128.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is negative or zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedIlog(base: I128): UInt? =
    if (this <= I128(0x0L, 0x0uL) || base <= I128(0x0L, 0x1uL)) {
        null
    } else {
        (this.toU128()).checkedIlog(base.toU128())
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * Note that for non-negative numbers, this is equivalent to
 * [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedIlog2(): UInt? =
    if (this <= I128(0x0L, 0x0uL)) {
        null
    } else {
        val log = (128u - 1u) - kmCtlzU64((this).toULong())
        log
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is negative or zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun I128.checkedIlog10(): UInt? = I128(this)

/**
 * Computes the absolute value of `self`.
 *
 * # Overflow behavior
 *
 * The absolute value of
 * cannot be represented as an
 * and attempting to calculate it will cause an overflow. This means
 * that code in debug mode will trigger a panic on this case and
 * optimized code will return
 * without a panic. If you do not want this behavior, consider
 * using [`unsignedAbs`](Self.unsignedAbs) instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.abs(): I128 =
    if (this.isNegative()) {
        -this
    } else {
        this
    }

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * This function always returns the correct answer without overflow or
 * panics by returning an unsigned integer.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.absDiff(other: I128): U128 =
    if (this < other) {
        rustWrappingSub((other.toU128()), this.toU128())
    } else {
        rustWrappingSub((this.toU128()), other.toU128())
    }

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.signum(): I128 = I128.fromLong(threeWayCompare(this, 0).toLong())

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.isPositive(): Boolean = this > I128(0x0L, 0x0uL)

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.isNegative(): Boolean = this < I128(0x0L, 0x0uL)

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.toBeBytes(): UByteArray = rustToBe(this).toLeBytes()

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.toLeBytes(): UByteArray = rustToLe(this).toLeBytes()

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun I128.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates an integer value from its representation as a byte array in
 * big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun I128.Companion.fromBeBytes(bytes: UByteArray): I128 = I128.fromBe(I128.fromNeBytes(bytes))

/**
 * Creates an integer value from its representation as a byte array in
 * little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun I128.Companion.fromLeBytes(bytes: UByteArray): I128 = I128.fromLe(I128.fromNeBytes(bytes))

/**
 * Creates an integer value from its memory representation as a byte
 * array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun I128.Companion.fromNeBytes(bytes: UByteArray): I128 = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<I128>(typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))).toInt()), PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun I128.Companion.minValue(): I128 = I128.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun I128.Companion.maxValue(): I128 = I128.MAX_VALUE

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun I128.clampMagnitude(limit: U128): I128 =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120 == 376.truncate())
 * }
 * ```
 */
public inline fun <reified Target> I128.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(127 == 376.saturatingTruncate())
 *     check(-128.toByte() == (-1000).saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> I128.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376.checkedTruncate<Byte>())
 *     check(null == (-1000).checkedTruncate<Byte>())
 * }
 * ```
 */
public inline fun <reified Target> I128.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> I128.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<I128, T>> I128.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<I128, T>> I128.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<I128, T>> I128.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<I128, T>> I128.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<I128, T>> I128.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "i128::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun I128.midpoint(rhs: I128): I128 {
    val t = ((this xor rhs) shr 1) + (this and rhs)
    return t +
        (if (t < I128(0x0L, 0x0uL)) {
            I128(0x0L, 0x1uL)
        } else {
            I128(0x0L, 0x0uL)
        } and (this xor rhs))
}

/**
 * The smallest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN5: Int = Int.MAX_VALUE.inv()

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX5: Int = (Int.MAX_VALUE shr 1)

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS5: UInt = 32u

/**
 * Checked addition with an unsigned integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedAddUnsigned(rhs: Int): Int? {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with an unsigned integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictAddUnsigned(rhs: Int): Int {
    val (a, b) = this.overflowingAddUnsigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked subtraction with an unsigned integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedSubUnsigned(rhs: Int): Int? {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict subtraction with an unsigned integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun Int.strictSubUnsigned(rhs: Int): Int {
    val (a, b) = this.overflowingSubUnsigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Saturating addition with an unsigned integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingAddUnsigned(rhs: Int): Int =
    when (val matchVal = this.checkedAddUnsigned(rhs)) {
        null -> Int.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating subtraction with an unsigned integer. Computes `self - rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingSubUnsigned(rhs: Int): Int =
    when (val matchVal = this.checkedSubUnsigned(rhs)) {
        null -> Int.MIN_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition with an unsigned integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingAddUnsigned(rhs: Int): Int = (this + ((rhs).toInt()))

/**
 * Wrapping (modular) subtraction with an unsigned integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingSubUnsigned(rhs: Int): Int = (this - ((rhs).toInt()))

/**
 * Calculates `self` + `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingAddUnsigned(rhs: Int): Pair<Int, Boolean> {
    val rhs1 = rhs
    val (res, overflowed) = rustOverflowingAdd(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0))
}

/**
 * Calculates `self` - `rhs` with an unsigned `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingSubUnsigned(rhs: Int): Pair<Int, Boolean> {
    val rhs1 = rhs
    val (res, overflowed) = rustOverflowingSub(this, rhs1)
    return Pair(res, overflowed xor (rhs1 < 0))
}

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.clampMagnitude(limit: Int): Int =
    when (val matchVal = limit.tryInto()) {
        is Ok -> {
            val limit1 = matchVal.value
            this.clamp(-limit1, limit1)
        }
        else -> {
            this
        }
    }

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN6: UByte = 0u

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX6: UByte = (0u.inv()).toUByte()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS6: UInt = UByte.MAX_VALUE.countOnes()

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 *     check(!n.bit(7))
 * }
 * ```
 */
public fun UByte.bit(index: UInt): Boolean = (this and (1u shl index.toInt())) != 0u.toUByte()

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     n = n.setBit(7, true)
 *     check(n == 0xAA)
 * }
 * ```
 */
public fun UByte.setBit(index: UInt, value: Boolean): UByte {
    this = this and (1u shl index.toInt()).inv()
    this = this or (((value.toUByte()).toUInt() shl (index.toInt())).toUByte())
    return this
}

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 3)
 * check(zero.countOnes() == 0)
 * ```
 */
public fun UByte.countOnes(): UInt = kmCtpopU64((this).toULong())

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(max.countZeros() == 0)
 * ```
 *
 * This is heavily dependent on the width of the type, and thus
 * might give surprising results depending on type inference:
 * ```kotlin
 * val lucky = 7
 * foo(lucky)
 * check(lucky.countZeros() == 5)
 * check(lucky.countOnes() == 3)
 * val lucky1 = 7
 * bar(lucky1)
 * check(lucky1.countZeros() == 13)
 * check(lucky1.countOnes() == 3)
 * ```
 * You might want to use [Self.countOnes] instead, or emphasize
 * the type you're using in the call rather than method syntax:
 * ```kotlin
 * val small = 1
 * ```
 */
public fun UByte.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 2)
 * check(max.leadingZeros() == 0)
 * ```
 */
public fun UByte.leadingZeros(): UInt = kmCtlzU64((this).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun UByte.trailingZeros(): UInt = kmCttzU64((this).toULong())

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingOnes() == 2)
 * check(zero.leadingOnes() == 0)
 * ```
 */
public fun UByte.leadingOnes(): UInt = (!this).leadingZeros()

/**
 * Returns the number of trailing ones in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 3)
 * check(zero.trailingOnes() == 0)
 * ```
 */
public fun UByte.trailingOnes(): UInt = (!this).trailingZeros()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.bitWidth(): UInt = 8u - rustLeadingZeros(this)

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun UByte.isolateHighestOne(): UByte = this and (rustWrappingShr((((1.toUByte()).toUInt() shl ((8u - 1u).toInt())).toUByte()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun UByte.isolateLowestOne(): UByte = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that this is equivalent to [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.highestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.highestOne()
        }
    }

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.lowestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.lowestOne()
        }
    }

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.castSigned(): Byte = this.toByte()

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.saturatingCastSigned(): Byte =
    if (this <= Byte.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        Byte.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.checkedCastSigned(): Byte? =
    if (this <= Byte.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.strictCastSigned(): Byte =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.rotateLeft(n: UInt): UByte = io.github.kotlinmania.rustlang.core.intrinsics.rotateLeft(this, n)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.rotateRight(n: UInt): UByte = io.github.kotlinmania.rustlang.core.intrinsics.rotateRight(this, n)

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.funnelShl(right: UByte, n: UInt): UByte {
    if (false) {
        check(n < 8u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (8u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.funnelShr(right: UByte, n: UInt): UByte {
    if (false) {
        check(n < 8u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (8u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun UByte.uncheckedFunnelShl(right: UByte, n: UInt): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun UByte.uncheckedFunnelShr(right: UByte, n: UInt): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.carrylessMul(rhs: UByte): UByte = CarrylessMul(this, rhs)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun UByte.swapBytes(): UByte = kmBswapU64((this).toULong()).toUByte()

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun UByte.extractBits(mask: UByte): UByte = io.github.kotlinmania.rustlang.core.num.imp.intbits.UByte.extractImpl(this, mask).toUByte()

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun UByte.depositBits(mask: UByte): UByte = io.github.kotlinmania.rustlang.core.num.imp.intbits.UByte.depositImpl(this, mask).toUByte()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun UByte.reverseBits(): UByte = kmBitreverseU64((this).toULong()).toUByte()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun UByte.Companion.fromBe(x: UByte): UByte = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun UByte.Companion.fromLe(x: UByte): UByte = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun UByte.toBe(): UByte = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun UByte.toLe(): UByte = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedAdd(rhs: UByte): UByte? =
    if (addWithOverflow(this, rhs).second) {
        null
    } else {
        uncheckedAdd(this, rhs)
    }

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictAdd(rhs: UByte): UByte {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UByte.uncheckedAdd(rhs: UByte): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedAddSigned(rhs: Byte): UByte? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictAddSigned(rhs: Byte): UByte {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedSub(rhs: UByte): UByte? =
    if (this < rhs) {
        null
    } else {
        uncheckedSub(this, rhs)
    }

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictSub(rhs: UByte): UByte {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * If you find yourself writing code like this:
 *
 * ```kotlin
 * if (foo >= bar) {
 *     // SAFETY: just checked it will not overflow
 *     val diff = foo.uncheckedSub(bar)
 *     // ... use diff ...
 * }
 * ```
 *
 * Consider changing it to
 *
 * ```kotlin
 * when (val matchVal = foo.checkedSub(bar)) {
 *     null -> {}
 *     else -> {
 *         val diff = matchVal
 *         // ... use diff ...
 *     }
 * }
 * ```
 *
 * As that does exactly the same thing -- including telling the optimizer
 * that the subtraction cannot overflow -- but avoids needing `unchecked`.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UByte.uncheckedSub(rhs: UByte): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedSubSigned(rhs: Byte): UByte? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictSubSigned(rhs: Byte): UByte {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedSignedDiff(rhs: UByte): Byte? {
    val res = (this - (rhs.toUByte())).toUByte().toByte()
    val overflow = (this >= rhs) == (res < 0.toByte())
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedMul(rhs: UByte): UByte? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictMul(rhs: UByte): UByte {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UByte.uncheckedMul(rhs: UByte): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedDiv(rhs: UByte): UByte? =
    if (rhs == 0u.toUByte()) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictDiv(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedDivEuclid(rhs: UByte): UByte? =
    if (rhs == 0u.toUByte()) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations. Since, for the
 * positive integers, all common definitions of division are equal, this
 * is exactly equal to `self.strictDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictDivEuclid(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0` or if `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.checkedDivExact(rhs: UByte): UByte? =
    if (rhs == 0u.toUByte()) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.divExact(rhs: UByte): UByte? =
    if (this % rhs != 0u.toUByte()) {
        null
    } else {
        (this / rhs).toUByte()
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0` or `self % rhs != 0`,
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun UByte.uncheckedDivExact(rhs: UByte): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_div_exact divide by zero or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedRem(rhs: UByte): UByte? =
    if (rhs == 0u.toUByte()) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`.
 *
 * Strict remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictRem(rhs: UByte): UByte = (this % rhs).toUByte()

/**
 * Checked Euclidean modulo. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedRemEuclid(rhs: UByte): UByte? =
    if (rhs == 0u.toUByte()) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Strict modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.strictRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictRemEuclid(rhs: UByte): UByte = (this % rhs).toUByte()

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(5)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 */
public fun UByte.uncheckedDisjointBitor(rhs: UByte): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`](Self.ilog2) can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is zero, or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.ilog(base: UByte): UInt {
    check(base >= 2u.toUByte()) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun UByte.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedIlog(base: UByte): UInt? {
    if (false) {
        if (base.isPowerOfTwo() && base > 1u.toUByte()) {
            val k = base.ilog2()
            return when (val matchVal = this.checkedIlog2()) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            } / k
        }
        if (base == 10u.toUByte()) {
            return this.checkedIlog10()
        }
    }
    return if (this <= 0u.toUByte() || base <= 1u.toUByte()) {
        null
    } else if (this < base) {
        0u
    } else {
        var n = 1
        var r = base
        if (8u == 128) {
            n = this.ilog2() / (base.ilog2() + 1u)
            r = rustPow(base, n.toUInt())
        }
        while (r <= this / base) {
            n += 1
            r *= base
        }
        n
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedIlog2(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog2()
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedIlog10(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog10()
        }
    }

/**
 * Checked negation. Computes `- self`, returning `None` unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedNeg(): UByte? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict negation. Computes `- self`, panicking unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictNeg(): UByte {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedShl(rhs: UInt): UByte? =
    if (rhs < 8u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictShl(rhs: UInt): UByte {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun UByte.uncheckedShl(rhs: UInt): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift left by i is the same as `<<1` i times
 *     check(running == start.unboundedShl(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shl 1
 * }
 * ```
 */
public fun UByte.unboundedShl(rhs: UInt): UByte =
    if (rhs < 8u) {
        this.uncheckedShl(rhs)
    } else {
        0u
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.shlExact(rhs: UInt): UByte? =
    if (rhs <= rustLeadingZeros(this) && rhs < 8u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.leadingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UByte.uncheckedShlExact(rhs: UInt): UByte {
    assertUnsafePrecondition(checkLibraryUb, "u8::unchecked_shl_exact cannot shift out non-zero bits", zeros, this, leadingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedShr(rhs: UInt): UByte? =
    if (rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictShr(rhs: UInt): UByte {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun UByte.uncheckedShr(rhs: UInt): UByte {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift right by i is the same as `>> 1` i times
 *     check(running == start.unboundedShr(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shr 1
 * }
 * ```
 */
public fun UByte.unboundedShr(rhs: UInt): UByte =
    if (rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        0u
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.shrExact(rhs: UInt): UByte? =
    if (rhs <= rustTrailingZeros(this) && rhs < 8u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UByte.uncheckedShrExact(rhs: UInt): UByte {
    assertUnsafePrecondition(checkLibraryUb, "u8::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedPow(exp: UInt): UByte? {
    var exp1 = exp
    var base = this
    var acc: UByte = 1u
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return rustCheckedShl((1.toUByte()), shift.toUInt())
    }
    if (exp1 == 0u) {
        return 1u
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UByte.strictPow(exp: UInt): UByte =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val a = matchVal
            a
        }
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingAdd(rhs: UByte): UByte = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingAddSigned(rhs: Byte): UByte {
    val (res, overflow) = rustOverflowingAdd(this, rhs.toUByte())
    return if (overflow == (rhs < 0.toByte())) {
        res
    } else if (overflow) {
        UByte.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating
 * at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingSub(rhs: UByte): UByte = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingSubSigned(rhs: Byte): UByte {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < 0.toByte()) {
        UByte.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer multiplication. Computes `self * rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingMul(rhs: UByte): UByte =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> UByte.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingDiv(rhs: UByte): UByte = rustWrappingDiv(this, rhs)

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.saturatingPow(exp: UInt): UByte =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> UByte.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingAdd(rhs: UByte): UByte = (this + rhs)

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingAddSigned(rhs: Byte): UByte = (this + (rhs.toUByte())).toUByte()

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingSub(rhs: UByte): UByte = (this - rhs)

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingSubSigned(rhs: Byte): UByte = (this - (rhs.toUByte())).toUByte()

/**
 * Wrapping (modular) multiplication. Computes `self *
 * rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * check(((10u.toUByte()) * (12u.toUByte())).toUByte() == 120)
 * check(((25u.toUByte()) * (12u.toUByte())).toUByte() == 44)
 * ```
 */
public fun UByte.wrappingMul(rhs: UByte): UByte = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingDiv(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations. Since, for
 * the positive integers, all common definitions of division are equal,
 * this is exactly equal to `self.wrappingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingDivEuclid(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Wrapping (modular) remainder. Computes `self % rhs`.
 *
 * Wrapped remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingRem(rhs: UByte): UByte = (this % rhs).toUByte()

/**
 * Wrapping Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Wrapped modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.wrappingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingRemEuclid(rhs: UByte): UByte = (this % rhs).toUByte()

/**
 * Wrapping (modular) negation. Computes `- self`,
 * wrapping around at the boundary of the type.
 *
 * Since unsigned types do not have negative equivalents
 * all applications of this function will wrap (except for `- 0`).
 * For values smaller than the corresponding signed type's maximum
 * the result is the same as casting the corresponding signed value.
 * Any larger values are equivalent to `MAX + 1 - (val - MAX - 1)` where
 * `MAX` is the corresponding signed type's maximum.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingNeg(): UByte = ((0.toUByte()) - (this.toUByte())).toUByte()

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the
 * RHS of a wrapping shift-left is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingShl(rhs: UInt): UByte = this.uncheckedShl(rhs and (8u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the
 * RHS of a wrapping shift-right is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.wrappingShr(rhs: UInt): UByte = this.uncheckedShr(rhs and (8u - 1u))

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().wrappingPow(6) == 217)
 * ```
 */
public fun UByte.wrappingPow(exp: UInt): UByte {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingAdd(rhs: UByte): Pair<UByte, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toUByte(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and returns a tuple containing
 * the sum and the output carry (in that order).
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns an output integer and a carry-out bit. This allows
 * chaining together multiple additions to create a wider addition, and
 * can be useful for bignum addition.
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd), and the output carry is
 * equal to the overflow flag. Note that although carry and overflow
 * flags are similar for unsigned integers, they are different for
 * signed integers.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val carry0 = false
 * val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 * check(carry1 == true)
 * val (sum1, carry2) = a1.carryingAdd(b1, carry1)
 * check(carry2 == false)
 * check(Pair(sum1, sum0) == Pair(9, 6))
 * ```
 */
public fun UByte.carryingAdd(rhs: UByte, carry: Boolean): Pair<UByte, Boolean> {
    val (a, c1) = rustOverflowingAdd(this, rhs)
    val (b, c2) = a.overflowingAdd(carry.toUByte())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingAddSigned(rhs: Byte): Pair<UByte, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs.toUByte())
    return Pair(res, overflowed xor (rhs < 0.toByte()))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingSub(rhs: UByte): Pair<UByte, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toUByte(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and returns a tuple
 * containing the difference and the output borrow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns an output
 * integer and a borrow-out bit. This allows chaining together multiple
 * subtractions to create a wider subtraction, and can be useful for
 * bignum subtraction.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val borrow0 = false
 * val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 * check(borrow1 == true)
 * val (diff1, borrow2) = a1.borrowingSub(b1, borrow1)
 * check(borrow2 == false)
 * ```
 */
public fun UByte.borrowingSub(rhs: UByte, borrow: Boolean): Pair<UByte, Boolean> {
    val (a, c1) = rustOverflowingSub(this, rhs)
    val (b, c2) = a.overflowingSub(borrow.toUByte())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingSubSigned(rhs: Byte): Pair<UByte, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs.toUByte())
    return Pair(res, overflow xor (rhs < 0.toByte()))
}

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.absDiff(other: UByte): UByte =
    if (sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8) == 1) {
        ((this.toInt()) - (other.toInt())).unsignedAbs().toUByte()
    } else {
        if (this < other) {
            other - this
        } else {
            this - other
        }
    }

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean
 * indicating whether an arithmetic overflow would occur. If an
 * overflow would have occurred then the wrapped value is returned.
 *
 * If you want the *value* of the overflow, rather than just *whether*
 * an overflow occurred, see [Self.carryingMul].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.overflowingMul(2u) == Pair(10, false))
 * check(1000000000u.overflowingMul(10u) == Pair(1410065408, true))
 * ```
 */
public fun UByte.overflowingMul(rhs: UByte): Pair<UByte, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toUByte(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you also need to add a value, then use [Self.carryingMulAdd].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.carryingMul(2, 0) == Pair(10, 0))
 * check(5u.carryingMul(2, 10) == Pair(20, 0))
 * check(1000000000u.carryingMul(10, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMul(10, 10) == Pair(1410065418, 2))
 * ```
 *
 * This is the core operation needed for scalar multiplication when
 * implementing it for wider-than-native types.
 *
 * ```kotlin
 * fun scalarMulEq(littleEndianDigits: List<UShort>, multiplicand: UShort) {
 *     var carry = 0
 *     for (d in littleEndianDigits) {
 *         Pair(d, carry) = d.carryingMul(multiplicand, carry)
 *     }
 *     if (carry != 0) {
 *         littleEndianDigits.push(carry)
 *     }
 * }
 * var v = mutableListOf(10, 20)
 * scalarMulEq(v, 3u)
 * check(v == listOf(30, 60))
 * check(0x87654321uL * 0xFEEDuL == 0x86D3D159E38DuL)
 * var v1 = mutableListOf(0x4321, 0x8765)
 * scalarMulEq(v1, 0xFEEDu)
 * check(v1 == listOf(0xE38D, 0xD159, 0x86D3))
 * ```
 *
 * If `carry` is zero, this is similar to [`overflowingMul`](Self.overflowingMul),
 * except that it gives the value of the overflow instead of just whether one happened:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     val r = UByte.carryingMul(7, 13, 0)
 *     check(Pair(r.first, r.second != 0) == UByte.overflowingMul(7, 13))
 *     val r1 = UByte.carryingMul(13, 42, 0)
 *     check(Pair(r1.first, r1.second != 0) == UByte.overflowingMul(13, 42))
 * }
 * ```
 *
 * The value of the first field in the returned tuple matches what you'd get
 * by combining the [`wrappingMul`](Self.wrappingMul) and
 * [`wrappingAdd`](Self.wrappingAdd) methods:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     check(789u.toUShort().carryingMul(456, 123).first == ((789u.toUShort()) * (456u.toUShort())).toUShort().wrappingAdd(123))
 * }
 * ```
 */
public fun UByte.carryingMul(rhs: UByte, carry: UByte): Pair<UByte, UByte> = UByte.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * This cannot overflow, as the double-width result has exactly enough
 * space for the largest possible result. This is equivalent to how, in
 * decimal, 9 × 9 + 9 + 9 = 81 + 18 = 99 = 9×10⁰ + 9×10¹ = 10² - 1.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you don't need the `add` part, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared between integer types,
 * which explains why `UInt` is used here.
 *
 * ```kotlin
 * check(5u.carryingMulAdd(2, 0, 0) == Pair(10, 0))
 * check(5u.carryingMulAdd(2, 10, 10) == Pair(30, 0))
 * check(1000000000u.carryingMulAdd(10, 0, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMulAdd(10, 10, 10) == Pair(1410065428, 2))
 * ```
 *
 * This is the core per-digit operation for "grade school" O(n²) multiplication.
 *
 * Please note that this example is shared between integer types,
 * using `UByte` for simplicity of the demonstration.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun quadraticMul(a: UByteArray, b: UByteArray, n: Int): UByteArray {
 *         var out = MutableList(n) { 0 }
 *         for (j in 0 until n) {
 *             var carry = 0
 *             for (i in 0 until (n - j)) {
 *                 Pair(out[j + i], carry) = UByte.carryingMulAdd(a[i], b[j], out[j + i], carry)
 *             }
 *         }
 *         return out
 *     }
 *     // -1 * -1 == 1
 *     check(quadraticMul(List(3) { 0xFF }, List(3) { 0xFF }) == listOf(1, 0, 0))
 *     check(UInt.wrappingMul(0x9e3779b9, 0x7f4a7c15) == 0xcffc982d)
 *     check(quadraticMul(UInt.toLeBytes(0x9e3779b9), UInt.toLeBytes(0x7f4a7c15)) == UInt.toLeBytes(0xcffc982d))
 * }
 * ```
 */
public fun UByte.carryingMulAdd(rhs: UByte, carry: UByte, add: UByte): Pair<UByte, UByte> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingDiv(rhs: UByte): Pair<UByte, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self.overflowingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingDivEuclid(rhs: UByte): Pair<UByte, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingRem(rhs: UByte): Pair<UByte, Boolean> = Pair(this % rhs, false)

/**
 * Calculates the remainder `self.remEuclid(rhs)` as if by Euclidean division.
 *
 * Returns a tuple of the modulo after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this operation
 * is exactly equal to `self.overflowingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingRemEuclid(rhs: UByte): Pair<UByte, Boolean> = Pair(this % rhs, false)

/**
 * Negates self in an overflowing fashion.
 *
 * Returns `!self + 1` using wrapping operations to return the value
 * that represents the negation of this unsigned value. Note that for
 * positive unsigned values overflow always occurs, but negating 0 does
 * not overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingNeg(): Pair<UByte, Boolean> = Pair((!this).wrappingAdd(1), this != 0u.toUByte())

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingShl(rhs: UInt): Pair<UByte, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 8u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.overflowingShr(rhs: UInt): Pair<UByte, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 8u)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().overflowingPow(6) == Pair(217, true))
 * ```
 */
public fun UByte.overflowingPow(exp: UInt): Pair<UByte, Boolean> {
    var exp1 = exp
    val base = this
    val acc: UByte  = 1u
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        return Pair((1.toUByte()).unboundedShl(shift), shift >= 8u)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.pow(exp: UInt): UByte =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.isqrt(): UByte {
    val result = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UByte(this)
    run {
        val MAX_RESULT: UByte = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UByte(UByte.MAX_VALUE)
        assertUnchecked(result <= MAX_RESULT)
    }
    if (this >= 1u.toUByte()) {
        assertUnchecked(result >= 1u.toUByte())
    }
    run {
        assertUnchecked(result.uncheckedMul(result) <= this)
        assertUnchecked(result <= this)
    }
    return result
}

/**
 * Performs Euclidean division.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self / rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.divEuclid(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Calculates the least remainder of `self` when divided by
 * `rhs`.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self % rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.remEuclid(rhs: UByte): UByte = (this % rhs).toUByte()

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * This is the same as performing `self / rhs` for all unsigned integers.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.divFloor(rhs: UByte): UByte = (this / rhs).toUByte()

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.divCeil(rhs: UByte): UByte {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0u.toUByte()) {
        d + 1u
    } else {
        d
    }
}

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.nextMultipleOf(rhs: UByte): UByte =
    when (val matchVal = this % rhs) {
        0 -> this
        else -> {
            val r = matchVal
            this + (rhs - r)
        }
    }

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`. Returns `None` if `rhs` is zero or the
 * operation would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedNextMultipleOf(rhs: UByte): UByte? {
    return when (val matchVal1 = when (val matchVal = rustCheckedRem(this, rhs)) {
        null -> { return null }
        else -> {
            val x = matchVal
            x
        }
    }) {
        0 -> this
        else -> {
            val r = matchVal1
            rustCheckedAdd(this, rhs - r)
        }
    }
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.isMultipleOf(rhs: UByte): Boolean =
    when (rhs) {
        0 -> this == 0u.toUByte()
        else -> this % rhs == 0u.toUByte()
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.nextPowerOfTwo(): UByte =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            UByte.MAX_VALUE + 1u
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.checkedNextPowerOfTwo(): UByte? {
    val m1 = rustSaturatingSub(this, 1u.toUByte())
    return if (m1.castSigned() >= 0.toByte()) {
        val exp = m1.bitWidth()
        (1u shl exp.toInt()).toUByte()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.wrappingNextPowerOfTwo(): UByte = (this.checkedNextPowerOfTwo() ?: 0u)

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.toBeBytes(): UByteArray = ubyteArrayOf(this)

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.toLeBytes(): UByteArray = ubyteArrayOf(this)

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun UByte.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in big endian.
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UByte.Companion.fromBeBytes(bytes: UByteArray): UByte = UByte.fromNeBytes(bytes)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in little endian.
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UByte.Companion.fromLeBytes(bytes: UByteArray): UByte = UByte.fromNeBytes(bytes)

/**
 * Creates a native endian integer value from its memory representation
 * as a byte array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * **Note**: This function is meaningless on `UByte`. Byte order does not exist as a
 * concept for byte-sized integers. This function is only provided in symmetry
 * with larger integer types.
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UByte.Companion.fromNeBytes(bytes: UByteArray): UByte = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8)).toInt()), PrimitiveLayouts.U8)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun UByte.Companion.minValue(): UByte = UByte.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun UByte.Companion.maxValue(): UByte = UByte.MAX_VALUE

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120u.toUByte() == 376u.truncate())
 * }
 * ```
 */
public inline fun <reified Target> UByte.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(255u.toUByte() == 376u.saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> UByte.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376u.checkedTruncate<UByte>())
 * }
 * ```
 */
public inline fun <reified Target> UByte.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> UByte.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UByte, T>> UByte.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UByte, T>> UByte.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UByte, T>> UByte.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UByte, T>> UByte.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<UByte, T>> UByte.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "u8::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large unsigned integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UByte.midpoint(rhs: UByte): UByte = ((this.toUShort() + rhs.toUShort()) / 2u).toUByte()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.wideningMul(rhs: UByte): UShort = this.toUShort() * rhs.toUShort()

/**
 * Performs a widening carry-less multiplication.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UByte.wideningCarrylessMul(rhs: UByte): UShort = (this.toUShort()).carrylessMul(rhs.toUShort())

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun UByte.carryingCarrylessMul(rhs: UByte, carry: UByte): Pair<UByte, UByte> {
    val p = (this.toUShort()).carrylessMul(rhs.toUShort())
    val lo = (p.toUByte())
    val hi = ((p.toUInt() shr (8u.toInt())).toUShort()).toUByte()
    return Pair(lo xor carry, hi)
}

/**
 * Checks if the value is within the ASCII range.
 *
 * # Examples
 *
 * ```kotlin
 * import ascii
 *
 * val ascii = 97u.toUByte()
 * val nonAscii = 150u.toUByte()
 * check(ascii.isAscii())
 * check(!nonAscii.isAscii())
 * ```
 */
public fun UByte.isAscii(): Boolean = this <= 127u.toUByte()

/**
 * If the value of this byte is within the ASCII range, returns it as an
 * [ASCII character](ascii.Char).  Otherwise, returns `None`.
 */
public fun UByte.asAscii(): io.github.kotlinmania.rustlang.core.ascii.Char? = Char.fromU8(this)

/**
 * Converts this byte to an [ASCII character](ascii.Char), without
 * checking whether or not it's valid.
 *
 * # Safety
 *
 * This byte must be valid ASCII, or else this is UB.
 */
public fun UByte.asAsciiUnchecked(): io.github.kotlinmania.rustlang.core.ascii.Char {
    assertUnsafePrecondition(checkLibraryUb, "as_ascii_unchecked requires that the byte is valid ASCII", it, this, it, isAscii)
    // SAFETY: the caller promised that this byte is ASCII.
    return Char.fromU8Unchecked(this)
}

/**
 * Makes a copy of the value in its ASCII upper case equivalent.
 *
 * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
 * but non-ASCII letters are unchanged.
 *
 * To uppercase the value in-place, use [makeAsciiUppercase].
 *
 * # Examples
 *
 * ```kotlin
 * val lowercaseA = 97u.toUByte()
 * check(65u.toUByte() == lowercaseA.toAsciiUppercase())
 * ```
 *
 * [makeAsciiUppercase]: Self.makeAsciiUppercase
 */
public fun UByte.toAsciiUppercase(): UByte {
    // Toggle the 6th bit if this is a lowercase letter
    return this xor ((this.isAsciiLowercase().toUByte()) * ASCII_CASE_MASK)
}

/**
 * Makes a copy of the value in its ASCII lower case equivalent.
 *
 * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
 * but non-ASCII letters are unchanged.
 *
 * To lowercase the value in-place, use [makeAsciiLowercase].
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u.toUByte()
 * check(97u.toUByte() == uppercaseA.toAsciiLowercase())
 * ```
 *
 * [makeAsciiLowercase]: Self.makeAsciiLowercase
 */
public fun UByte.toAsciiLowercase(): UByte {
    // Set the 6th bit if this is an uppercase letter
    return this or (this.isAsciiUppercase().toUByte() * ASCII_CASE_MASK)
}

/**
 * Assumes self is ascii
 */
internal fun UByte.asciiChangeCaseUnchecked(): UByte = this xor ASCII_CASE_MASK

/**
 * Checks that two values are an ASCII case-insensitive match.
 *
 * This is equivalent to `toAsciiLowercase(a) == toAsciiLowercase(b)`.
 *
 * # Examples
 *
 * ```kotlin
 * val lowercaseA = 97u.toUByte()
 * val uppercaseA = 65u.toUByte()
 * check(lowercaseA.eqIgnoreAsciiCase(uppercaseA))
 * ```
 */
public fun UByte.eqIgnoreAsciiCase(other: UByte): Boolean = this.toAsciiLowercase() == other.toAsciiLowercase()

/**
 * Converts this value to its ASCII upper case equivalent in-place.
 *
 * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
 * but non-ASCII letters are unchanged.
 *
 * To return a new uppercased value without modifying the existing one, use
 * [toAsciiUppercase].
 *
 * # Examples
 *
 * ```kotlin
 * var byte = 97u
 * byte.makeAsciiUppercase()
 * check(65u == byte)
 * ```
 *
 * [toAsciiUppercase]: Self.toAsciiUppercase
 */
public fun UByte.makeAsciiUppercase() {
    this = this.toAsciiUppercase()
}

/**
 * Converts this value to its ASCII lower case equivalent in-place.
 *
 * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
 * but non-ASCII letters are unchanged.
 *
 * To return a new lowercased value without modifying the existing one, use
 * [toAsciiLowercase].
 *
 * # Examples
 *
 * ```kotlin
 * var byte = 65u
 * byte.makeAsciiLowercase()
 * check(97u == byte)
 * ```
 *
 * [toAsciiLowercase]: Self.toAsciiLowercase
 */
public fun UByte.makeAsciiLowercase() {
    this = this.toAsciiLowercase()
}

/**
 * Checks if the value is an ASCII alphabetic character:
 *
 * - U+0041 'A' ..= U+005A 'Z', or
 * - U+0061 'a' ..= U+007A 'z'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(uppercaseA.isAsciiAlphabetic())
 * check(uppercaseG.isAsciiAlphabetic())
 * check(a.isAsciiAlphabetic())
 * check(g.isAsciiAlphabetic())
 * check(!zero.isAsciiAlphabetic())
 * check(!percent.isAsciiAlphabetic())
 * check(!space.isAsciiAlphabetic())
 * check(!lf.isAsciiAlphabetic())
 * check(!esc.isAsciiAlphabetic())
 * ```
 */
public fun UByte.isAsciiAlphabetic(): Boolean =
    when (this) {
        in 65u..90u, in 97u..122u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII uppercase character:
 * U+0041 'A' ..= U+005A 'Z'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(uppercaseA.isAsciiUppercase())
 * check(uppercaseG.isAsciiUppercase())
 * check(!a.isAsciiUppercase())
 * check(!g.isAsciiUppercase())
 * check(!zero.isAsciiUppercase())
 * check(!percent.isAsciiUppercase())
 * check(!space.isAsciiUppercase())
 * check(!lf.isAsciiUppercase())
 * check(!esc.isAsciiUppercase())
 * ```
 */
public fun UByte.isAsciiUppercase(): Boolean =
    when (this) {
        in 65u..90u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII lowercase character:
 * U+0061 'a' ..= U+007A 'z'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(!uppercaseA.isAsciiLowercase())
 * check(!uppercaseG.isAsciiLowercase())
 * check(a.isAsciiLowercase())
 * check(g.isAsciiLowercase())
 * check(!zero.isAsciiLowercase())
 * check(!percent.isAsciiLowercase())
 * check(!space.isAsciiLowercase())
 * check(!lf.isAsciiLowercase())
 * check(!esc.isAsciiLowercase())
 * ```
 */
public fun UByte.isAsciiLowercase(): Boolean =
    when (this) {
        in 97u..122u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII alphanumeric character:
 *
 * - U+0041 'A' ..= U+005A 'Z', or
 * - U+0061 'a' ..= U+007A 'z', or
 * - U+0030 '0' ..= U+0039 '9'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(uppercaseA.isAsciiAlphanumeric())
 * check(uppercaseG.isAsciiAlphanumeric())
 * check(a.isAsciiAlphanumeric())
 * check(g.isAsciiAlphanumeric())
 * check(zero.isAsciiAlphanumeric())
 * check(!percent.isAsciiAlphanumeric())
 * check(!space.isAsciiAlphanumeric())
 * check(!lf.isAsciiAlphanumeric())
 * check(!esc.isAsciiAlphanumeric())
 * ```
 */
public fun UByte.isAsciiAlphanumeric(): Boolean =
    when (this) {
        in 48u..57u -> true
        else -> false
    } or
        when (this) {
            in 65u..90u -> true
            else -> false
        } or
        when (this) {
            in 97u..122u -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII decimal digit:
 * U+0030 '0' ..= U+0039 '9'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(!uppercaseA.isAsciiDigit())
 * check(!uppercaseG.isAsciiDigit())
 * check(!a.isAsciiDigit())
 * check(!g.isAsciiDigit())
 * check(zero.isAsciiDigit())
 * check(!percent.isAsciiDigit())
 * check(!space.isAsciiDigit())
 * check(!lf.isAsciiDigit())
 * check(!esc.isAsciiDigit())
 * ```
 */
public fun UByte.isAsciiDigit(): Boolean =
    when (this) {
        in 48u..57u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII octal digit:
 * U+0030 '0' ..= U+0037 '7'.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(isAsciiOctdigit)
 * public fun doctestEntry() {
 *     val uppercaseA = 65u
 *     val a = 97u
 *     val zero = 48u
 *     val seven = 55u
 *     val nine = 57u
 *     val percent = 37u
 *     val lf = 10u
 *     check(!uppercaseA.isAsciiOctdigit())
 *     check(!a.isAsciiOctdigit())
 *     check(zero.isAsciiOctdigit())
 *     check(seven.isAsciiOctdigit())
 *     check(!nine.isAsciiOctdigit())
 *     check(!percent.isAsciiOctdigit())
 *     check(!lf.isAsciiOctdigit())
 * }
 * ```
 */
public fun UByte.isAsciiOctdigit(): Boolean =
    when (this) {
        in 48u..55u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII hexadecimal digit:
 *
 * - U+0030 '0' ..= U+0039 '9', or
 * - U+0041 'A' ..= U+0046 'F', or
 * - U+0061 'a' ..= U+0066 'f'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(uppercaseA.isAsciiHexdigit())
 * check(!uppercaseG.isAsciiHexdigit())
 * check(a.isAsciiHexdigit())
 * check(!g.isAsciiHexdigit())
 * check(zero.isAsciiHexdigit())
 * check(!percent.isAsciiHexdigit())
 * check(!space.isAsciiHexdigit())
 * check(!lf.isAsciiHexdigit())
 * check(!esc.isAsciiHexdigit())
 * ```
 */
public fun UByte.isAsciiHexdigit(): Boolean =
    when (this) {
        in 48u..57u -> true
        else -> false
    } or
        when (this) {
            in 65u..70u -> true
            else -> false
        } or
        when (this) {
            in 97u..102u -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII punctuation or symbol character
 * (i.e. not alphanumeric, whitespace, or control):
 *
 * - U+0021 ..= U+002F `! " # $ % & ' ( ) * + , - . /`, or
 * - U+003A ..= U+0040 `:; < = > ? @`, or
 * - U+005B ..= U+0060  [ \ ] ^ _ `, or
 * - U+007B ..= U+007E `{ | } ~`
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(!uppercaseA.isAsciiPunctuation())
 * check(!uppercaseG.isAsciiPunctuation())
 * check(!a.isAsciiPunctuation())
 * check(!g.isAsciiPunctuation())
 * check(!zero.isAsciiPunctuation())
 * check(percent.isAsciiPunctuation())
 * check(!space.isAsciiPunctuation())
 * check(!lf.isAsciiPunctuation())
 * check(!esc.isAsciiPunctuation())
 * ```
 */
public fun UByte.isAsciiPunctuation(): Boolean =
    when (this) {
        in 33u..47u -> true
        else -> false
    } or
        when (this) {
            in 58u..64u -> true
            else -> false
        } or
        when (this) {
            in 91u..96u -> true
            else -> false
        } or
        when (this) {
            in 123u..126u -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII graphic character
 * (i.e. not whitespace or control):
 * U+0021 '!' ..= U+007E '~'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(uppercaseA.isAsciiGraphic())
 * check(uppercaseG.isAsciiGraphic())
 * check(a.isAsciiGraphic())
 * check(g.isAsciiGraphic())
 * check(zero.isAsciiGraphic())
 * check(percent.isAsciiGraphic())
 * check(!space.isAsciiGraphic())
 * check(!lf.isAsciiGraphic())
 * check(!esc.isAsciiGraphic())
 * ```
 */
public fun UByte.isAsciiGraphic(): Boolean =
    when (this) {
        in 33u..126u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII whitespace character:
 * U+0020 SPACE, U+0009 HORIZONTAL TAB, U+000A LINE FEED,
 * U+000C FORM FEED, or U+000D CARRIAGE RETURN.
 *
 * **Warning:** Because the list above excludes U+000B VERTICAL TAB,
 * `b.isAsciiWhitespace()` is **not** equivalent to `char.from(b).isWhitespace()`.
 *
 * Rust uses the WhatWG Infra Standard's [definition of ASCII
 * whitespace][infra-aw]. There are several other definitions in
 * wide use. For instance, [the POSIX locale][pct] includes
 * U+000B VERTICAL TAB as well as all the above characters,
 * but—from the very same specification—[the default rule for
 * "field splitting" in the Bourne shell][bfs] considers *only*
 * SPACE, HORIZONTAL TAB, and LINE FEED as whitespace.
 *
 * If you are writing a program that will process an existing
 * file format, check what that format's definition of whitespace is
 * before using this function.
 *
 * [infra-aw]: https://infra.spec.whatwg.org/#ascii-whitespace
 * [pct]: https://pubs.opengroup.org/onlinepubs/9799919799/basedefs/V1Chap07.html#tag070301
 * [bfs]: https://pubs.opengroup.org/onlinepubs/9799919799/utilities/V3Chap02.html#tag190605
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(!uppercaseA.isAsciiWhitespace())
 * check(!uppercaseG.isAsciiWhitespace())
 * check(!a.isAsciiWhitespace())
 * check(!g.isAsciiWhitespace())
 * check(!zero.isAsciiWhitespace())
 * check(!percent.isAsciiWhitespace())
 * check(space.isAsciiWhitespace())
 * check(lf.isAsciiWhitespace())
 * check(!esc.isAsciiWhitespace())
 * ```
 */
public fun UByte.isAsciiWhitespace(): Boolean =
    when (this) {
        9u, 10u, 12u, 13u, 32u -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII control character:
 * U+0000 NUL ..= U+001F UNIT SEPARATOR, or U+007F DELETE.
 * Note that most ASCII whitespace characters are control
 * characters, but SPACE is not.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 65u
 * val uppercaseG = 71u
 * val a = 97u
 * val g = 103u
 * val zero = 48u
 * val percent = 37u
 * val space = 32u
 * val lf = 10u
 * val esc = 27u
 * check(!uppercaseA.isAsciiControl())
 * check(!uppercaseG.isAsciiControl())
 * check(!a.isAsciiControl())
 * check(!g.isAsciiControl())
 * check(!zero.isAsciiControl())
 * check(!percent.isAsciiControl())
 * check(!space.isAsciiControl())
 * check(lf.isAsciiControl())
 * check(esc.isAsciiControl())
 * ```
 */
public fun UByte.isAsciiControl(): Boolean =
    when (this) {
        in 0u..31u, 127u -> true
        else -> false
    }

/**
 * Returns an iterator that produces an escaped version of a `UByte`,
 * treating it as an ASCII character.
 *
 * The behavior is identical to [ascii.escapeDefault].
 *
 * # Examples
 *
 * ```kotlin
 * assertEq("0", b'0'.escapeAscii().toString()); assertEq("\\t", b'\t'.escapeAscii().toString()); assertEq("\\r", b'\r'.escapeAscii().toString()); assertEq("\\n", b'\n'.escapeAscii().toString()); assertEq("\\'", b'\''.escapeAscii().toString()); assertEq("\\\"", b'"'.escapeAscii().toString()); assertEq("\\\\", b'\\'.escapeAscii().toString()); assertEq("\\x9d", b'\x9d'.escapeAscii().toString());
 * ```
 */
public fun UByte.escapeAscii(): EscapeDefault = escapeDefault(this)

internal fun UByte.isUtf8CharBoundary(): Boolean {
    // This is bit magic equivalent to: b < 128 || b >= 192
    return (this.toByte()) >= (-0x40).toByte()
}

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN7: UShort = 0u

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX7: UShort = (0u.inv()).toUShort()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS7: UInt = UShort.MAX_VALUE.countOnes()

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 *     check(!n.bit(7))
 * }
 * ```
 */
public fun UShort.bit(index: UInt): Boolean = (this and (1u shl index.toInt())) != 0u.toUShort()

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     n = n.setBit(7, true)
 *     check(n == 0xAA)
 * }
 * ```
 */
public fun UShort.setBit(index: UInt, value: Boolean): UShort {
    this = this and (1u shl index.toInt()).inv()
    this = this or (((value.toUShort()).toUInt() shl (index.toInt())).toUShort())
    return this
}

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 3)
 * check(zero.countOnes() == 0)
 * ```
 */
public fun UShort.countOnes(): UInt = kmCtpopU64((this).toULong())

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(max.countZeros() == 0)
 * ```
 *
 * This is heavily dependent on the width of the type, and thus
 * might give surprising results depending on type inference:
 * ```kotlin
 * val lucky = 7
 * foo(lucky)
 * check(lucky.countZeros() == 5)
 * check(lucky.countOnes() == 3)
 * val lucky1 = 7
 * bar(lucky1)
 * check(lucky1.countZeros() == 13)
 * check(lucky1.countOnes() == 3)
 * ```
 * You might want to use [Self.countOnes] instead, or emphasize
 * the type you're using in the call rather than method syntax:
 * ```kotlin
 * val small = 1
 * ```
 */
public fun UShort.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 2)
 * check(max.leadingZeros() == 0)
 * ```
 */
public fun UShort.leadingZeros(): UInt = kmCtlzU64((this).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun UShort.trailingZeros(): UInt = kmCttzU64((this).toULong())

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingOnes() == 2)
 * check(zero.leadingOnes() == 0)
 * ```
 */
public fun UShort.leadingOnes(): UInt = (!this).leadingZeros()

/**
 * Returns the number of trailing ones in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 3)
 * check(zero.trailingOnes() == 0)
 * ```
 */
public fun UShort.trailingOnes(): UInt = (!this).trailingZeros()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.bitWidth(): UInt = 16u - rustLeadingZeros(this)

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun UShort.isolateHighestOne(): UShort = this and (rustWrappingShr((((1.toUShort()).toUInt() shl ((16u - 1u).toInt())).toUShort()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun UShort.isolateLowestOne(): UShort = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that this is equivalent to [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.highestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.highestOne()
        }
    }

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.lowestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.lowestOne()
        }
    }

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.castSigned(): Short = this.toShort()

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.saturatingCastSigned(): Short =
    if (this <= Short.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        Short.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.checkedCastSigned(): Short? =
    if (this <= Short.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.strictCastSigned(): Short =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.rotateLeft(n: UInt): UShort = io.github.kotlinmania.rustlang.core.intrinsics.rotateLeft(this, n)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.rotateRight(n: UInt): UShort = io.github.kotlinmania.rustlang.core.intrinsics.rotateRight(this, n)

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.funnelShl(right: UShort, n: UInt): UShort {
    if (false) {
        check(n < 16u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (16u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.funnelShr(right: UShort, n: UInt): UShort {
    if (false) {
        check(n < 16u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (16u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun UShort.uncheckedFunnelShl(right: UShort, n: UInt): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun UShort.uncheckedFunnelShr(right: UShort, n: UInt): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.carrylessMul(rhs: UShort): UShort = CarrylessMul(this, rhs)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun UShort.swapBytes(): UShort = kmBswapU64((this).toULong()).toUShort()

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun UShort.extractBits(mask: UShort): UShort = io.github.kotlinmania.rustlang.core.num.imp.intbits.UShort.extractImpl(this, mask).toUShort()

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun UShort.depositBits(mask: UShort): UShort = io.github.kotlinmania.rustlang.core.num.imp.intbits.UShort.depositImpl(this, mask).toUShort()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun UShort.reverseBits(): UShort = kmBitreverseU64((this).toULong()).toUShort()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun UShort.Companion.fromBe(x: UShort): UShort = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun UShort.Companion.fromLe(x: UShort): UShort = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun UShort.toBe(): UShort = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun UShort.toLe(): UShort = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedAdd(rhs: UShort): UShort? =
    if (addWithOverflow(this, rhs).second) {
        null
    } else {
        uncheckedAdd(this, rhs)
    }

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictAdd(rhs: UShort): UShort {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UShort.uncheckedAdd(rhs: UShort): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedAddSigned(rhs: Short): UShort? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictAddSigned(rhs: Short): UShort {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedSub(rhs: UShort): UShort? =
    if (this < rhs) {
        null
    } else {
        uncheckedSub(this, rhs)
    }

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictSub(rhs: UShort): UShort {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * If you find yourself writing code like this:
 *
 * ```kotlin
 * if (foo >= bar) {
 *     // SAFETY: just checked it will not overflow
 *     val diff = foo.uncheckedSub(bar)
 *     // ... use diff ...
 * }
 * ```
 *
 * Consider changing it to
 *
 * ```kotlin
 * when (val matchVal = foo.checkedSub(bar)) {
 *     null -> {}
 *     else -> {
 *         val diff = matchVal
 *         // ... use diff ...
 *     }
 * }
 * ```
 *
 * As that does exactly the same thing -- including telling the optimizer
 * that the subtraction cannot overflow -- but avoids needing `unchecked`.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UShort.uncheckedSub(rhs: UShort): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedSubSigned(rhs: Short): UShort? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictSubSigned(rhs: Short): UShort {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedSignedDiff(rhs: UShort): Short? {
    val res = (this - (rhs.toUShort())).toUShort().toShort()
    val overflow = (this >= rhs) == (res < 0.toShort())
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedMul(rhs: UShort): UShort? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictMul(rhs: UShort): UShort {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UShort.uncheckedMul(rhs: UShort): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedDiv(rhs: UShort): UShort? =
    if (rhs == 0u.toUShort()) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictDiv(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedDivEuclid(rhs: UShort): UShort? =
    if (rhs == 0u.toUShort()) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations. Since, for the
 * positive integers, all common definitions of division are equal, this
 * is exactly equal to `self.strictDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictDivEuclid(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0` or if `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.checkedDivExact(rhs: UShort): UShort? =
    if (rhs == 0u.toUShort()) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.divExact(rhs: UShort): UShort? =
    if (this % rhs != 0u.toUShort()) {
        null
    } else {
        (this / rhs).toUShort()
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0` or `self % rhs != 0`,
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun UShort.uncheckedDivExact(rhs: UShort): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_div_exact divide by zero or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedRem(rhs: UShort): UShort? =
    if (rhs == 0u.toUShort()) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`.
 *
 * Strict remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictRem(rhs: UShort): UShort = (this % rhs).toUShort()

/**
 * Checked Euclidean modulo. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedRemEuclid(rhs: UShort): UShort? =
    if (rhs == 0u.toUShort()) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Strict modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.strictRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictRemEuclid(rhs: UShort): UShort = (this % rhs).toUShort()

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(5)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 */
public fun UShort.uncheckedDisjointBitor(rhs: UShort): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`](Self.ilog2) can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is zero, or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.ilog(base: UShort): UInt {
    check(base >= 2u.toUShort()) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun UShort.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedIlog(base: UShort): UInt? {
    if (false) {
        if (base.isPowerOfTwo() && base > 1u.toUShort()) {
            val k = base.ilog2()
            return when (val matchVal = this.checkedIlog2()) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            } / k
        }
        if (base == 10u.toUShort()) {
            return this.checkedIlog10()
        }
    }
    return if (this <= 0u.toUShort() || base <= 1u.toUShort()) {
        null
    } else if (this < base) {
        0u
    } else {
        var n = 1
        var r = base
        if (16u == 128) {
            n = this.ilog2() / (base.ilog2() + 1u)
            r = rustPow(base, n.toUInt())
        }
        while (r <= this / base) {
            n += 1
            r *= base
        }
        n
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedIlog2(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog2()
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedIlog10(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog10()
        }
    }

/**
 * Checked negation. Computes `- self`, returning `None` unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedNeg(): UShort? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict negation. Computes `- self`, panicking unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictNeg(): UShort {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedShl(rhs: UInt): UShort? =
    if (rhs < 16u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictShl(rhs: UInt): UShort {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun UShort.uncheckedShl(rhs: UInt): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift left by i is the same as `<<1` i times
 *     check(running == start.unboundedShl(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shl 1
 * }
 * ```
 */
public fun UShort.unboundedShl(rhs: UInt): UShort =
    if (rhs < 16u) {
        this.uncheckedShl(rhs)
    } else {
        0u
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.shlExact(rhs: UInt): UShort? =
    if (rhs <= rustLeadingZeros(this) && rhs < 16u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.leadingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UShort.uncheckedShlExact(rhs: UInt): UShort {
    assertUnsafePrecondition(checkLibraryUb, "u16::unchecked_shl_exact cannot shift out non-zero bits", zeros, this, leadingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedShr(rhs: UInt): UShort? =
    if (rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictShr(rhs: UInt): UShort {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun UShort.uncheckedShr(rhs: UInt): UShort {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift right by i is the same as `>> 1` i times
 *     check(running == start.unboundedShr(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shr 1
 * }
 * ```
 */
public fun UShort.unboundedShr(rhs: UInt): UShort =
    if (rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        0u
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.shrExact(rhs: UInt): UShort? =
    if (rhs <= rustTrailingZeros(this) && rhs < 16u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UShort.uncheckedShrExact(rhs: UInt): UShort {
    assertUnsafePrecondition(checkLibraryUb, "u16::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedPow(exp: UInt): UShort? {
    var exp1 = exp
    var base = this
    var acc: UShort = 1u
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return rustCheckedShl((1.toUShort()), shift.toUInt())
    }
    if (exp1 == 0u) {
        return 1u
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UShort.strictPow(exp: UInt): UShort =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val a = matchVal
            a
        }
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingAdd(rhs: UShort): UShort = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingAddSigned(rhs: Short): UShort {
    val (res, overflow) = rustOverflowingAdd(this, rhs.toUShort())
    return if (overflow == (rhs < 0.toShort())) {
        res
    } else if (overflow) {
        UShort.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating
 * at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingSub(rhs: UShort): UShort = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingSubSigned(rhs: Short): UShort {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < 0.toShort()) {
        UShort.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer multiplication. Computes `self * rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingMul(rhs: UShort): UShort =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> UShort.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingDiv(rhs: UShort): UShort = rustWrappingDiv(this, rhs)

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.saturatingPow(exp: UInt): UShort =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> UShort.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingAdd(rhs: UShort): UShort = (this + rhs)

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingAddSigned(rhs: Short): UShort = (this + (rhs.toUShort())).toUShort()

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingSub(rhs: UShort): UShort = (this - rhs)

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingSubSigned(rhs: Short): UShort = (this - (rhs.toUShort())).toUShort()

/**
 * Wrapping (modular) multiplication. Computes `self *
 * rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * check(((10u.toUByte()) * (12u.toUByte())).toUByte() == 120)
 * check(((25u.toUByte()) * (12u.toUByte())).toUByte() == 44)
 * ```
 */
public fun UShort.wrappingMul(rhs: UShort): UShort = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingDiv(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations. Since, for
 * the positive integers, all common definitions of division are equal,
 * this is exactly equal to `self.wrappingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingDivEuclid(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Wrapping (modular) remainder. Computes `self % rhs`.
 *
 * Wrapped remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingRem(rhs: UShort): UShort = (this % rhs).toUShort()

/**
 * Wrapping Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Wrapped modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.wrappingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingRemEuclid(rhs: UShort): UShort = (this % rhs).toUShort()

/**
 * Wrapping (modular) negation. Computes `- self`,
 * wrapping around at the boundary of the type.
 *
 * Since unsigned types do not have negative equivalents
 * all applications of this function will wrap (except for `- 0`).
 * For values smaller than the corresponding signed type's maximum
 * the result is the same as casting the corresponding signed value.
 * Any larger values are equivalent to `MAX + 1 - (val - MAX - 1)` where
 * `MAX` is the corresponding signed type's maximum.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingNeg(): UShort = ((0.toUShort()) - (this.toUShort())).toUShort()

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the
 * RHS of a wrapping shift-left is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingShl(rhs: UInt): UShort = this.uncheckedShl(rhs and (16u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the
 * RHS of a wrapping shift-right is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.wrappingShr(rhs: UInt): UShort = this.uncheckedShr(rhs and (16u - 1u))

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().wrappingPow(6) == 217)
 * ```
 */
public fun UShort.wrappingPow(exp: UInt): UShort {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingAdd(rhs: UShort): Pair<UShort, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toUShort(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and returns a tuple containing
 * the sum and the output carry (in that order).
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns an output integer and a carry-out bit. This allows
 * chaining together multiple additions to create a wider addition, and
 * can be useful for bignum addition.
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd), and the output carry is
 * equal to the overflow flag. Note that although carry and overflow
 * flags are similar for unsigned integers, they are different for
 * signed integers.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val carry0 = false
 * val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 * check(carry1 == true)
 * val (sum1, carry2) = a1.carryingAdd(b1, carry1)
 * check(carry2 == false)
 * check(Pair(sum1, sum0) == Pair(9, 6))
 * ```
 */
public fun UShort.carryingAdd(rhs: UShort, carry: Boolean): Pair<UShort, Boolean> {
    val (a, c1) = rustOverflowingAdd(this, rhs)
    val (b, c2) = a.overflowingAdd(carry.toUShort())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingAddSigned(rhs: Short): Pair<UShort, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs.toUShort())
    return Pair(res, overflowed xor (rhs < 0.toShort()))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingSub(rhs: UShort): Pair<UShort, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toUShort(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and returns a tuple
 * containing the difference and the output borrow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns an output
 * integer and a borrow-out bit. This allows chaining together multiple
 * subtractions to create a wider subtraction, and can be useful for
 * bignum subtraction.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val borrow0 = false
 * val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 * check(borrow1 == true)
 * val (diff1, borrow2) = a1.borrowingSub(b1, borrow1)
 * check(borrow2 == false)
 * ```
 */
public fun UShort.borrowingSub(rhs: UShort, borrow: Boolean): Pair<UShort, Boolean> {
    val (a, c1) = rustOverflowingSub(this, rhs)
    val (b, c2) = a.overflowingSub(borrow.toUShort())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingSubSigned(rhs: Short): Pair<UShort, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs.toUShort())
    return Pair(res, overflow xor (rhs < 0.toShort()))
}

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.absDiff(other: UShort): UShort =
    if (sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) == 1) {
        ((this.toInt()) - (other.toInt())).unsignedAbs().toUShort()
    } else {
        if (this < other) {
            other - this
        } else {
            this - other
        }
    }

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean
 * indicating whether an arithmetic overflow would occur. If an
 * overflow would have occurred then the wrapped value is returned.
 *
 * If you want the *value* of the overflow, rather than just *whether*
 * an overflow occurred, see [Self.carryingMul].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.overflowingMul(2u) == Pair(10, false))
 * check(1000000000u.overflowingMul(10u) == Pair(1410065408, true))
 * ```
 */
public fun UShort.overflowingMul(rhs: UShort): Pair<UShort, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toUShort(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you also need to add a value, then use [Self.carryingMulAdd].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.carryingMul(2, 0) == Pair(10, 0))
 * check(5u.carryingMul(2, 10) == Pair(20, 0))
 * check(1000000000u.carryingMul(10, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMul(10, 10) == Pair(1410065418, 2))
 * ```
 *
 * This is the core operation needed for scalar multiplication when
 * implementing it for wider-than-native types.
 *
 * ```kotlin
 * fun scalarMulEq(littleEndianDigits: List<UShort>, multiplicand: UShort) {
 *     var carry = 0
 *     for (d in littleEndianDigits) {
 *         Pair(d, carry) = d.carryingMul(multiplicand, carry)
 *     }
 *     if (carry != 0) {
 *         littleEndianDigits.push(carry)
 *     }
 * }
 * var v = mutableListOf(10, 20)
 * scalarMulEq(v, 3u)
 * check(v == listOf(30, 60))
 * check(0x87654321uL * 0xFEEDuL == 0x86D3D159E38DuL)
 * var v1 = mutableListOf(0x4321, 0x8765)
 * scalarMulEq(v1, 0xFEEDu)
 * check(v1 == listOf(0xE38D, 0xD159, 0x86D3))
 * ```
 *
 * If `carry` is zero, this is similar to [`overflowingMul`](Self.overflowingMul),
 * except that it gives the value of the overflow instead of just whether one happened:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     val r = UByte.carryingMul(7, 13, 0)
 *     check(Pair(r.first, r.second != 0) == UByte.overflowingMul(7, 13))
 *     val r1 = UByte.carryingMul(13, 42, 0)
 *     check(Pair(r1.first, r1.second != 0) == UByte.overflowingMul(13, 42))
 * }
 * ```
 *
 * The value of the first field in the returned tuple matches what you'd get
 * by combining the [`wrappingMul`](Self.wrappingMul) and
 * [`wrappingAdd`](Self.wrappingAdd) methods:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     check(789u.toUShort().carryingMul(456, 123).first == ((789u.toUShort()) * (456u.toUShort())).toUShort().wrappingAdd(123))
 * }
 * ```
 */
public fun UShort.carryingMul(rhs: UShort, carry: UShort): Pair<UShort, UShort> = UShort.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * This cannot overflow, as the double-width result has exactly enough
 * space for the largest possible result. This is equivalent to how, in
 * decimal, 9 × 9 + 9 + 9 = 81 + 18 = 99 = 9×10⁰ + 9×10¹ = 10² - 1.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you don't need the `add` part, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared between integer types,
 * which explains why `UInt` is used here.
 *
 * ```kotlin
 * check(5u.carryingMulAdd(2, 0, 0) == Pair(10, 0))
 * check(5u.carryingMulAdd(2, 10, 10) == Pair(30, 0))
 * check(1000000000u.carryingMulAdd(10, 0, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMulAdd(10, 10, 10) == Pair(1410065428, 2))
 * ```
 *
 * This is the core per-digit operation for "grade school" O(n²) multiplication.
 *
 * Please note that this example is shared between integer types,
 * using `UByte` for simplicity of the demonstration.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun quadraticMul(a: UByteArray, b: UByteArray, n: Int): UByteArray {
 *         var out = MutableList(n) { 0 }
 *         for (j in 0 until n) {
 *             var carry = 0
 *             for (i in 0 until (n - j)) {
 *                 Pair(out[j + i], carry) = UByte.carryingMulAdd(a[i], b[j], out[j + i], carry)
 *             }
 *         }
 *         return out
 *     }
 *     // -1 * -1 == 1
 *     check(quadraticMul(List(3) { 0xFF }, List(3) { 0xFF }) == listOf(1, 0, 0))
 *     check(UInt.wrappingMul(0x9e3779b9, 0x7f4a7c15) == 0xcffc982d)
 *     check(quadraticMul(UInt.toLeBytes(0x9e3779b9), UInt.toLeBytes(0x7f4a7c15)) == UInt.toLeBytes(0xcffc982d))
 * }
 * ```
 */
public fun UShort.carryingMulAdd(rhs: UShort, carry: UShort, add: UShort): Pair<UShort, UShort> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingDiv(rhs: UShort): Pair<UShort, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self.overflowingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingDivEuclid(rhs: UShort): Pair<UShort, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingRem(rhs: UShort): Pair<UShort, Boolean> = Pair(this % rhs, false)

/**
 * Calculates the remainder `self.remEuclid(rhs)` as if by Euclidean division.
 *
 * Returns a tuple of the modulo after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this operation
 * is exactly equal to `self.overflowingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingRemEuclid(rhs: UShort): Pair<UShort, Boolean> = Pair(this % rhs, false)

/**
 * Negates self in an overflowing fashion.
 *
 * Returns `!self + 1` using wrapping operations to return the value
 * that represents the negation of this unsigned value. Note that for
 * positive unsigned values overflow always occurs, but negating 0 does
 * not overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingNeg(): Pair<UShort, Boolean> = Pair((!this).wrappingAdd(1), this != 0u.toUShort())

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingShl(rhs: UInt): Pair<UShort, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 16u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.overflowingShr(rhs: UInt): Pair<UShort, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 16u)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().overflowingPow(6) == Pair(217, true))
 * ```
 */
public fun UShort.overflowingPow(exp: UInt): Pair<UShort, Boolean> {
    var exp1 = exp
    val base = this
    val acc: UShort  = 1u
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        return Pair((1.toUShort()).unboundedShl(shift), shift >= 16u)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.pow(exp: UInt): UShort =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.isqrt(): UShort {
    val result = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UShort(this).toUShort()
    run {
        val MAX_RESULT: UShort = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UShort(UShort.MAX_VALUE).toUShort()
        assertUnchecked(result <= MAX_RESULT)
    }
    if (this >= 1u.toUShort()) {
        assertUnchecked(result >= 1u.toUShort())
    }
    run {
        assertUnchecked(result.uncheckedMul(result) <= this)
        assertUnchecked(result <= this)
    }
    return result
}

/**
 * Performs Euclidean division.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self / rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.divEuclid(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Calculates the least remainder of `self` when divided by
 * `rhs`.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self % rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.remEuclid(rhs: UShort): UShort = (this % rhs).toUShort()

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * This is the same as performing `self / rhs` for all unsigned integers.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.divFloor(rhs: UShort): UShort = (this / rhs).toUShort()

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.divCeil(rhs: UShort): UShort {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0u.toUShort()) {
        d + 1u
    } else {
        d
    }
}

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.nextMultipleOf(rhs: UShort): UShort =
    when (val matchVal = this % rhs) {
        0 -> this
        else -> {
            val r = matchVal
            this + (rhs - r)
        }
    }

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`. Returns `None` if `rhs` is zero or the
 * operation would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedNextMultipleOf(rhs: UShort): UShort? {
    return when (val matchVal1 = when (val matchVal = rustCheckedRem(this, rhs)) {
        null -> { return null }
        else -> {
            val x = matchVal
            x
        }
    }) {
        0 -> this
        else -> {
            val r = matchVal1
            rustCheckedAdd(this, rhs - r)
        }
    }
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.isMultipleOf(rhs: UShort): Boolean =
    when (rhs) {
        0 -> this == 0u.toUShort()
        else -> this % rhs == 0u.toUShort()
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.nextPowerOfTwo(): UShort =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            UShort.MAX_VALUE + 1u
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.checkedNextPowerOfTwo(): UShort? {
    val m1 = rustSaturatingSub(this, 1u.toUShort())
    return if (m1.castSigned() >= 0.toShort()) {
        val exp = m1.bitWidth()
        (1u shl exp.toInt()).toUShort()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.wrappingNextPowerOfTwo(): UShort = (this.checkedNextPowerOfTwo() ?: 0u)

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.toBeBytes(): UByteArray = run { val rawBits = (((((this).toUInt() and 0xFFu) shl 8) or (((this).toUInt() shr 8) and 0xFFu)).toUShort()).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.toLeBytes(): UByteArray = run { val rawBits = this.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun UShort.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UShort.Companion.fromBeBytes(bytes: UByteArray): UShort = run { val swapVal0 = (UShort.fromNeBytes(bytes)); (((((swapVal0).toUInt() and 0xFFu) shl 8) or (((swapVal0).toUInt() shr 8) and 0xFFu)).toUShort()) }

/**
 * Creates a native endian integer value from its representation
 * as a byte array in little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UShort.Companion.fromLeBytes(bytes: UByteArray): UShort = UShort.fromNeBytes(bytes)

/**
 * Creates a native endian integer value from its memory representation
 * as a byte array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UShort.Companion.fromNeBytes(bytes: UByteArray): UShort = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()), PrimitiveLayouts.U16)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun UShort.Companion.minValue(): UShort = UShort.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun UShort.Companion.maxValue(): UShort = UShort.MAX_VALUE

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120u.toUByte() == 376u.truncate())
 * }
 * ```
 */
public inline fun <reified Target> UShort.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(255u.toUByte() == 376u.saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> UShort.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376u.checkedTruncate<UByte>())
 * }
 * ```
 */
public inline fun <reified Target> UShort.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> UShort.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UShort, T>> UShort.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UShort, T>> UShort.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UShort, T>> UShort.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UShort, T>> UShort.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<UShort, T>> UShort.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "u16::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large unsigned integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UShort.midpoint(rhs: UShort): UShort = ((this.toUInt() + rhs.toUInt()) / 2u).toUShort()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.wideningMul(rhs: UShort): UInt = this.toUInt() * rhs.toUInt()

/**
 * Performs a widening carry-less multiplication.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UShort.wideningCarrylessMul(rhs: UShort): UInt = (this.toUInt()).carrylessMul(rhs.toUInt())

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun UShort.carryingCarrylessMul(rhs: UShort, carry: UShort): Pair<UShort, UShort> {
    val p = (this.toUInt()).carrylessMul(rhs.toUInt())
    val lo = (p.toUShort())
    val hi = (p shr 16u.toInt()).toUShort()
    return Pair(lo xor carry, hi)
}

/**
 * Checks if the value is a Unicode surrogate code point, which are disallowed values for [char].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(utf16Extra)
 * public fun doctestEntry() {
 *     val lowNonSurrogate = 0xA000u.toUShort()
 *     val lowSurrogate = 0xD800u.toUShort()
 *     val highSurrogate = 0xDC00u.toUShort()
 *     val highNonSurrogate = 0xE000u.toUShort()
 *     check(!lowNonSurrogate.isUtf16Surrogate())
 *     check(lowSurrogate.isUtf16Surrogate())
 *     check(highSurrogate.isUtf16Surrogate())
 *     check(!highNonSurrogate.isUtf16Surrogate())
 * }
 * ```
 */
public fun UShort.isUtf16Surrogate(): Boolean =
    when (this) {
        in 0xD800..0xDFFF -> true
        else -> false
    }

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN8: UInt = 0u

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX8: UInt = 0u.inv()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS8: UInt = UInt.MAX_VALUE.countOnes()

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 *     check(!n.bit(7))
 * }
 * ```
 */
public fun UInt.bit(index: UInt): Boolean = (this and (1u shl index.toInt())) != 0u

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     n = n.setBit(7, true)
 *     check(n == 0xAA)
 * }
 * ```
 */
public fun UInt.setBit(index: UInt, value: Boolean): UInt {
    this = this and (1u shl index.toInt()).inv()
    this = this or ((value.toUInt()) shl index.toInt())
    return this
}

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 3)
 * check(zero.countOnes() == 0)
 * ```
 */
public fun UInt.countOnes(): UInt = kmCtpopU64((this).toULong())

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(max.countZeros() == 0)
 * ```
 *
 * This is heavily dependent on the width of the type, and thus
 * might give surprising results depending on type inference:
 * ```kotlin
 * val lucky = 7
 * foo(lucky)
 * check(lucky.countZeros() == 5)
 * check(lucky.countOnes() == 3)
 * val lucky1 = 7
 * bar(lucky1)
 * check(lucky1.countZeros() == 13)
 * check(lucky1.countOnes() == 3)
 * ```
 * You might want to use [Self.countOnes] instead, or emphasize
 * the type you're using in the call rather than method syntax:
 * ```kotlin
 * val small = 1
 * ```
 */
public fun UInt.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 2)
 * check(max.leadingZeros() == 0)
 * ```
 */
public fun UInt.leadingZeros(): UInt = kmCtlzU64((this).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun UInt.trailingZeros(): UInt = kmCttzU64((this).toULong())

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingOnes() == 2)
 * check(zero.leadingOnes() == 0)
 * ```
 */
public fun UInt.leadingOnes(): UInt = (!this).leadingZeros()

/**
 * Returns the number of trailing ones in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 3)
 * check(zero.trailingOnes() == 0)
 * ```
 */
public fun UInt.trailingOnes(): UInt = (!this).trailingZeros()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.bitWidth(): UInt = 32u - rustLeadingZeros(this)

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun UInt.isolateHighestOne(): UInt = this and (rustWrappingShr(((1.toUInt()) shl (32u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun UInt.isolateLowestOne(): UInt = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that this is equivalent to [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.highestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.highestOne()
        }
    }

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.lowestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.lowestOne()
        }
    }

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.castSigned(): Int = this.toInt()

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.saturatingCastSigned(): Int =
    if (this <= Int.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        Int.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.checkedCastSigned(): Int? =
    if (this <= Int.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.strictCastSigned(): Int =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.rotateLeft(n: UInt): UInt = io.github.kotlinmania.rustlang.core.intrinsics.rotateLeft(this, n)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.rotateRight(n: UInt): UInt = io.github.kotlinmania.rustlang.core.intrinsics.rotateRight(this, n)

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.funnelShl(right: UInt, n: UInt): UInt {
    if (false) {
        check(n < 32u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (32u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.funnelShr(right: UInt, n: UInt): UInt {
    if (false) {
        check(n < 32u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (32u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun UInt.uncheckedFunnelShl(right: UInt, n: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun UInt.uncheckedFunnelShr(right: UInt, n: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.carrylessMul(rhs: UInt): UInt = CarrylessMul(this, rhs)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun UInt.swapBytes(): UInt = kmBswapU64((this).toULong()).toUInt()

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun UInt.extractBits(mask: UInt): UInt = io.github.kotlinmania.rustlang.core.num.imp.intbits.UInt.extractImpl(this, mask).toUInt()

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun UInt.depositBits(mask: UInt): UInt = io.github.kotlinmania.rustlang.core.num.imp.intbits.UInt.depositImpl(this, mask).toUInt()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun UInt.reverseBits(): UInt = kmBitreverseU64((this).toULong()).toUInt()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun UInt.Companion.fromBe(x: UInt): UInt = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun UInt.Companion.fromLe(x: UInt): UInt = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun UInt.toBe(): UInt = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun UInt.toLe(): UInt = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedAdd(rhs: UInt): UInt? =
    if (addWithOverflow(this, rhs).second) {
        null
    } else {
        uncheckedAdd(this, rhs)
    }

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictAdd(rhs: UInt): UInt {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UInt.uncheckedAdd(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedAddSigned(rhs: Int): UInt? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictAddSigned(rhs: Int): UInt {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedSub(rhs: UInt): UInt? =
    if (this < rhs) {
        null
    } else {
        uncheckedSub(this, rhs)
    }

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictSub(rhs: UInt): UInt {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * If you find yourself writing code like this:
 *
 * ```kotlin
 * if (foo >= bar) {
 *     // SAFETY: just checked it will not overflow
 *     val diff = foo.uncheckedSub(bar)
 *     // ... use diff ...
 * }
 * ```
 *
 * Consider changing it to
 *
 * ```kotlin
 * when (val matchVal = foo.checkedSub(bar)) {
 *     null -> {}
 *     else -> {
 *         val diff = matchVal
 *         // ... use diff ...
 *     }
 * }
 * ```
 *
 * As that does exactly the same thing -- including telling the optimizer
 * that the subtraction cannot overflow -- but avoids needing `unchecked`.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UInt.uncheckedSub(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedSubSigned(rhs: Int): UInt? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictSubSigned(rhs: Int): UInt {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedSignedDiff(rhs: UInt): Int? {
    val res = (this - (rhs.toUInt())).toInt()
    val overflow = (this >= rhs) == (res < 0)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedMul(rhs: UInt): UInt? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictMul(rhs: UInt): UInt {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun UInt.uncheckedMul(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedDiv(rhs: UInt): UInt? =
    if (rhs == 0u) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictDiv(rhs: UInt): UInt = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedDivEuclid(rhs: UInt): UInt? =
    if (rhs == 0u) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations. Since, for the
 * positive integers, all common definitions of division are equal, this
 * is exactly equal to `self.strictDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictDivEuclid(rhs: UInt): UInt = this / rhs

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0` or if `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.checkedDivExact(rhs: UInt): UInt? =
    if (rhs == 0u) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.divExact(rhs: UInt): UInt? =
    if (this % rhs != 0u) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0` or `self % rhs != 0`,
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun UInt.uncheckedDivExact(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_div_exact divide by zero or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedRem(rhs: UInt): UInt? =
    if (rhs == 0u) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`.
 *
 * Strict remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictRem(rhs: UInt): UInt = this % rhs

/**
 * Checked Euclidean modulo. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedRemEuclid(rhs: UInt): UInt? =
    if (rhs == 0u) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Strict modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.strictRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictRemEuclid(rhs: UInt): UInt = this % rhs

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(5)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 */
public fun UInt.uncheckedDisjointBitor(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`](Self.ilog2) can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is zero, or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.ilog(base: UInt): UInt {
    check(base >= 2u) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun UInt.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedIlog(base: UInt): UInt? {
    if (false) {
        if (base.isPowerOfTwo() && base > 1u) {
            val k = base.ilog2()
            return when (val matchVal = this.checkedIlog2()) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            } / k
        }
        if (base == 10u) {
            return this.checkedIlog10()
        }
    }
    return if (this <= 0u || base <= 1u) {
        null
    } else if (this < base) {
        0u
    } else {
        var n = 1
        var r = base
        if (32u == 128) {
            n = this.ilog2() / (base.ilog2() + 1u)
            r = rustPow(base, n.toUInt())
        }
        while (r <= this / base) {
            n += 1
            r *= base
        }
        n
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedIlog2(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog2()
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedIlog10(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog10()
        }
    }

/**
 * Checked negation. Computes `- self`, returning `None` unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedNeg(): UInt? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict negation. Computes `- self`, panicking unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictNeg(): UInt {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedShl(rhs: UInt): UInt? =
    if (rhs < 32u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictShl(rhs: UInt): UInt {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun UInt.uncheckedShl(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift left by i is the same as `<<1` i times
 *     check(running == start.unboundedShl(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shl 1
 * }
 * ```
 */
public fun UInt.unboundedShl(rhs: UInt): UInt =
    if (rhs < 32u) {
        this.uncheckedShl(rhs)
    } else {
        0u
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.shlExact(rhs: UInt): UInt? =
    if (rhs <= rustLeadingZeros(this) && rhs < 32u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.leadingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UInt.uncheckedShlExact(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLibraryUb, "u32::unchecked_shl_exact cannot shift out non-zero bits", zeros, this, leadingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedShr(rhs: UInt): UInt? =
    if (rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictShr(rhs: UInt): UInt {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun UInt.uncheckedShr(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift right by i is the same as `>> 1` i times
 *     check(running == start.unboundedShr(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shr 1
 * }
 * ```
 */
public fun UInt.unboundedShr(rhs: UInt): UInt =
    if (rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        0u
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.shrExact(rhs: UInt): UInt? =
    if (rhs <= rustTrailingZeros(this) && rhs < 32u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun UInt.uncheckedShrExact(rhs: UInt): UInt {
    assertUnsafePrecondition(checkLibraryUb, "u32::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedPow(exp: UInt): UInt? {
    var exp1 = exp
    var base = this
    var acc: UInt = 1u
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return rustCheckedShl((1.toUInt()), shift.toUInt())
    }
    if (exp1 == 0u) {
        return 1u
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun UInt.strictPow(exp: UInt): UInt =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val a = matchVal
            a
        }
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingAdd(rhs: UInt): UInt = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingAddSigned(rhs: Int): UInt {
    val (res, overflow) = rustOverflowingAdd(this, rhs.toUInt())
    return if (overflow == (rhs < 0)) {
        res
    } else if (overflow) {
        UInt.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating
 * at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingSub(rhs: UInt): UInt = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingSubSigned(rhs: Int): UInt {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < 0) {
        UInt.MAX_VALUE
    } else {
        0u
    }
}

/**
 * Saturating integer multiplication. Computes `self * rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingMul(rhs: UInt): UInt =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> UInt.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingDiv(rhs: UInt): UInt = rustWrappingDiv(this, rhs)

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.saturatingPow(exp: UInt): UInt =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> UInt.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingAdd(rhs: UInt): UInt = (this + rhs)

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingAddSigned(rhs: Int): UInt = (this + (rhs.toUInt()))

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingSub(rhs: UInt): UInt = (this - rhs)

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingSubSigned(rhs: Int): UInt = (this - (rhs.toUInt()))

/**
 * Wrapping (modular) multiplication. Computes `self *
 * rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * check(((10u.toUByte()) * (12u.toUByte())).toUByte() == 120)
 * check(((25u.toUByte()) * (12u.toUByte())).toUByte() == 44)
 * ```
 */
public fun UInt.wrappingMul(rhs: UInt): UInt = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingDiv(rhs: UInt): UInt = this / rhs

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations. Since, for
 * the positive integers, all common definitions of division are equal,
 * this is exactly equal to `self.wrappingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingDivEuclid(rhs: UInt): UInt = this / rhs

/**
 * Wrapping (modular) remainder. Computes `self % rhs`.
 *
 * Wrapped remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingRem(rhs: UInt): UInt = this % rhs

/**
 * Wrapping Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Wrapped modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.wrappingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingRemEuclid(rhs: UInt): UInt = this % rhs

/**
 * Wrapping (modular) negation. Computes `- self`,
 * wrapping around at the boundary of the type.
 *
 * Since unsigned types do not have negative equivalents
 * all applications of this function will wrap (except for `- 0`).
 * For values smaller than the corresponding signed type's maximum
 * the result is the same as casting the corresponding signed value.
 * Any larger values are equivalent to `MAX + 1 - (val - MAX - 1)` where
 * `MAX` is the corresponding signed type's maximum.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingNeg(): UInt = ((0.toUInt()) - (this.toUInt()))

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the
 * RHS of a wrapping shift-left is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingShl(rhs: UInt): UInt = this.uncheckedShl(rhs and (32u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the
 * RHS of a wrapping shift-right is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.wrappingShr(rhs: UInt): UInt = this.uncheckedShr(rhs and (32u - 1u))

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().wrappingPow(6) == 217)
 * ```
 */
public fun UInt.wrappingPow(exp: UInt): UInt {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingAdd(rhs: UInt): Pair<UInt, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toUInt(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and returns a tuple containing
 * the sum and the output carry (in that order).
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns an output integer and a carry-out bit. This allows
 * chaining together multiple additions to create a wider addition, and
 * can be useful for bignum addition.
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd), and the output carry is
 * equal to the overflow flag. Note that although carry and overflow
 * flags are similar for unsigned integers, they are different for
 * signed integers.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val carry0 = false
 * val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 * check(carry1 == true)
 * val (sum1, carry2) = a1.carryingAdd(b1, carry1)
 * check(carry2 == false)
 * check(Pair(sum1, sum0) == Pair(9, 6))
 * ```
 */
public fun UInt.carryingAdd(rhs: UInt, carry: Boolean): Pair<UInt, Boolean> {
    val (a, c1) = rustOverflowingAdd(this, rhs)
    val (b, c2) = a.overflowingAdd(carry.toUInt())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingAddSigned(rhs: Int): Pair<UInt, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs.toUInt())
    return Pair(res, overflowed xor (rhs < 0))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingSub(rhs: UInt): Pair<UInt, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toUInt(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and returns a tuple
 * containing the difference and the output borrow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns an output
 * integer and a borrow-out bit. This allows chaining together multiple
 * subtractions to create a wider subtraction, and can be useful for
 * bignum subtraction.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val borrow0 = false
 * val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 * check(borrow1 == true)
 * val (diff1, borrow2) = a1.borrowingSub(b1, borrow1)
 * check(borrow2 == false)
 * ```
 */
public fun UInt.borrowingSub(rhs: UInt, borrow: Boolean): Pair<UInt, Boolean> {
    val (a, c1) = rustOverflowingSub(this, rhs)
    val (b, c2) = a.overflowingSub(borrow.toUInt())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingSubSigned(rhs: Int): Pair<UInt, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs.toUInt())
    return Pair(res, overflow xor (rhs < 0))
}

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.absDiff(other: UInt): UInt =
    if (sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) == 1) {
        ((this.toInt()) - (other.toInt())).unsignedAbs()
    } else {
        if (this < other) {
            other - this
        } else {
            this - other
        }
    }

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean
 * indicating whether an arithmetic overflow would occur. If an
 * overflow would have occurred then the wrapped value is returned.
 *
 * If you want the *value* of the overflow, rather than just *whether*
 * an overflow occurred, see [Self.carryingMul].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.overflowingMul(2u) == Pair(10, false))
 * check(1000000000u.overflowingMul(10u) == Pair(1410065408, true))
 * ```
 */
public fun UInt.overflowingMul(rhs: UInt): Pair<UInt, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toUInt(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you also need to add a value, then use [Self.carryingMulAdd].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.carryingMul(2, 0) == Pair(10, 0))
 * check(5u.carryingMul(2, 10) == Pair(20, 0))
 * check(1000000000u.carryingMul(10, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMul(10, 10) == Pair(1410065418, 2))
 * ```
 *
 * This is the core operation needed for scalar multiplication when
 * implementing it for wider-than-native types.
 *
 * ```kotlin
 * fun scalarMulEq(littleEndianDigits: List<UShort>, multiplicand: UShort) {
 *     var carry = 0
 *     for (d in littleEndianDigits) {
 *         Pair(d, carry) = d.carryingMul(multiplicand, carry)
 *     }
 *     if (carry != 0) {
 *         littleEndianDigits.push(carry)
 *     }
 * }
 * var v = mutableListOf(10, 20)
 * scalarMulEq(v, 3u)
 * check(v == listOf(30, 60))
 * check(0x87654321uL * 0xFEEDuL == 0x86D3D159E38DuL)
 * var v1 = mutableListOf(0x4321, 0x8765)
 * scalarMulEq(v1, 0xFEEDu)
 * check(v1 == listOf(0xE38D, 0xD159, 0x86D3))
 * ```
 *
 * If `carry` is zero, this is similar to [`overflowingMul`](Self.overflowingMul),
 * except that it gives the value of the overflow instead of just whether one happened:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     val r = UByte.carryingMul(7, 13, 0)
 *     check(Pair(r.first, r.second != 0) == UByte.overflowingMul(7, 13))
 *     val r1 = UByte.carryingMul(13, 42, 0)
 *     check(Pair(r1.first, r1.second != 0) == UByte.overflowingMul(13, 42))
 * }
 * ```
 *
 * The value of the first field in the returned tuple matches what you'd get
 * by combining the [`wrappingMul`](Self.wrappingMul) and
 * [`wrappingAdd`](Self.wrappingAdd) methods:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     check(789u.toUShort().carryingMul(456, 123).first == ((789u.toUShort()) * (456u.toUShort())).toUShort().wrappingAdd(123))
 * }
 * ```
 */
public fun UInt.carryingMul(rhs: UInt, carry: UInt): Pair<UInt, UInt> = UInt.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * This cannot overflow, as the double-width result has exactly enough
 * space for the largest possible result. This is equivalent to how, in
 * decimal, 9 × 9 + 9 + 9 = 81 + 18 = 99 = 9×10⁰ + 9×10¹ = 10² - 1.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you don't need the `add` part, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared between integer types,
 * which explains why `UInt` is used here.
 *
 * ```kotlin
 * check(5u.carryingMulAdd(2, 0, 0) == Pair(10, 0))
 * check(5u.carryingMulAdd(2, 10, 10) == Pair(30, 0))
 * check(1000000000u.carryingMulAdd(10, 0, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMulAdd(10, 10, 10) == Pair(1410065428, 2))
 * ```
 *
 * This is the core per-digit operation for "grade school" O(n²) multiplication.
 *
 * Please note that this example is shared between integer types,
 * using `UByte` for simplicity of the demonstration.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun quadraticMul(a: UByteArray, b: UByteArray, n: Int): UByteArray {
 *         var out = MutableList(n) { 0 }
 *         for (j in 0 until n) {
 *             var carry = 0
 *             for (i in 0 until (n - j)) {
 *                 Pair(out[j + i], carry) = UByte.carryingMulAdd(a[i], b[j], out[j + i], carry)
 *             }
 *         }
 *         return out
 *     }
 *     // -1 * -1 == 1
 *     check(quadraticMul(List(3) { 0xFF }, List(3) { 0xFF }) == listOf(1, 0, 0))
 *     check(UInt.wrappingMul(0x9e3779b9, 0x7f4a7c15) == 0xcffc982d)
 *     check(quadraticMul(UInt.toLeBytes(0x9e3779b9), UInt.toLeBytes(0x7f4a7c15)) == UInt.toLeBytes(0xcffc982d))
 * }
 * ```
 */
public fun UInt.carryingMulAdd(rhs: UInt, carry: UInt, add: UInt): Pair<UInt, UInt> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingDiv(rhs: UInt): Pair<UInt, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self.overflowingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingDivEuclid(rhs: UInt): Pair<UInt, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingRem(rhs: UInt): Pair<UInt, Boolean> = Pair(this % rhs, false)

/**
 * Calculates the remainder `self.remEuclid(rhs)` as if by Euclidean division.
 *
 * Returns a tuple of the modulo after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this operation
 * is exactly equal to `self.overflowingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingRemEuclid(rhs: UInt): Pair<UInt, Boolean> = Pair(this % rhs, false)

/**
 * Negates self in an overflowing fashion.
 *
 * Returns `!self + 1` using wrapping operations to return the value
 * that represents the negation of this unsigned value. Note that for
 * positive unsigned values overflow always occurs, but negating 0 does
 * not overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingNeg(): Pair<UInt, Boolean> = Pair((!this).wrappingAdd(1), this != 0u)

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingShl(rhs: UInt): Pair<UInt, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 32u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.overflowingShr(rhs: UInt): Pair<UInt, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 32u)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().overflowingPow(6) == Pair(217, true))
 * ```
 */
public fun UInt.overflowingPow(exp: UInt): Pair<UInt, Boolean> {
    var exp1 = exp
    val base = this
    val acc: UInt  = 1u
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        return Pair((1.toUInt()).unboundedShl(shift), shift >= 32u)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.pow(exp: UInt): UInt =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.isqrt(): UInt {
    val result = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UInt(this).toUInt()
    run {
        val MAX_RESULT: UInt = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.UInt(UInt.MAX_VALUE).toUInt()
        assertUnchecked(result <= MAX_RESULT)
    }
    if (this >= 1u) {
        assertUnchecked(result >= 1u)
    }
    run {
        assertUnchecked(result.uncheckedMul(result) <= this)
        assertUnchecked(result <= this)
    }
    return result
}

/**
 * Performs Euclidean division.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self / rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.divEuclid(rhs: UInt): UInt = this / rhs

/**
 * Calculates the least remainder of `self` when divided by
 * `rhs`.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self % rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.remEuclid(rhs: UInt): UInt = this % rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * This is the same as performing `self / rhs` for all unsigned integers.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.divFloor(rhs: UInt): UInt = this / rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.divCeil(rhs: UInt): UInt {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0u) {
        d + 1u
    } else {
        d
    }
}

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.nextMultipleOf(rhs: UInt): UInt =
    when (val matchVal = this % rhs) {
        0 -> this
        else -> {
            val r = matchVal
            this + (rhs - r)
        }
    }

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`. Returns `None` if `rhs` is zero or the
 * operation would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedNextMultipleOf(rhs: UInt): UInt? {
    return when (val matchVal1 = when (val matchVal = rustCheckedRem(this, rhs)) {
        null -> { return null }
        else -> {
            val x = matchVal
            x
        }
    }) {
        0 -> this
        else -> {
            val r = matchVal1
            rustCheckedAdd(this, rhs - r)
        }
    }
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.isMultipleOf(rhs: UInt): Boolean =
    when (rhs) {
        0 -> this == 0u
        else -> this % rhs == 0u
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.nextPowerOfTwo(): UInt =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            UInt.MAX_VALUE + 1u
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.checkedNextPowerOfTwo(): UInt? {
    val m1 = rustSaturatingSub(this, 1u)
    return if (m1.castSigned() >= 0) {
        val exp = m1.bitWidth()
        1u shl exp.toInt()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.wrappingNextPowerOfTwo(): UInt = (this.checkedNextPowerOfTwo() ?: 0u)

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.toBeBytes(): UByteArray = run { val rawBits = ((((this) shl 24)) or (((this) and 0x0000FF00u) shl 8) or (((this) shr 8) and 0x0000FF00u) or ((this) shr 24)); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.toLeBytes(): UByteArray = run { val rawBits = this; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun UInt.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UInt.Companion.fromBeBytes(bytes: UByteArray): UInt = run { val swapVal0 = (UInt.fromNeBytes(bytes)); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00u) shl 8) or (((swapVal0) shr 8) and 0x0000FF00u) or ((swapVal0) shr 24)) }

/**
 * Creates a native endian integer value from its representation
 * as a byte array in little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UInt.Companion.fromLeBytes(bytes: UByteArray): UInt = UInt.fromNeBytes(bytes)

/**
 * Creates a native endian integer value from its memory representation
 * as a byte array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun UInt.Companion.fromNeBytes(bytes: UByteArray): UInt = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()), PrimitiveLayouts.U32)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun UInt.Companion.minValue(): UInt = UInt.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun UInt.Companion.maxValue(): UInt = UInt.MAX_VALUE

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120u.toUByte() == 376u.truncate())
 * }
 * ```
 */
public inline fun <reified Target> UInt.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(255u.toUByte() == 376u.saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> UInt.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376u.checkedTruncate<UByte>())
 * }
 * ```
 */
public inline fun <reified Target> UInt.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> UInt.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UInt, T>> UInt.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<UInt, T>> UInt.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UInt, T>> UInt.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<UInt, T>> UInt.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<UInt, T>> UInt.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "u32::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large unsigned integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun UInt.midpoint(rhs: UInt): UInt = ((this.toULong() + rhs.toULong()) / 2uL).toUInt()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.wideningMul(rhs: UInt): ULong = this.toULong() * rhs.toULong()

/**
 * Performs a widening carry-less multiplication.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun UInt.wideningCarrylessMul(rhs: UInt): ULong = (this.toULong()).carrylessMul(rhs.toULong())

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun UInt.carryingCarrylessMul(rhs: UInt, carry: UInt): Pair<UInt, UInt> {
    val p = (this.toULong()).carrylessMul(rhs.toULong())
    val lo = (p.toUInt())
    val hi = (p shr 32u.toInt()).toUInt()
    return Pair(lo xor carry, hi)
}

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN9: ULong = 0uL

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX9: ULong = 0uL.inv()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS9: UInt = ULong.MAX_VALUE.countOnes()

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 *     check(!n.bit(7))
 * }
 * ```
 */
public fun ULong.bit(index: UInt): Boolean = (this and (1uL shl index.toInt())) != 0uL

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     n = n.setBit(7, true)
 *     check(n == 0xAA)
 * }
 * ```
 */
public fun ULong.setBit(index: UInt, value: Boolean): ULong {
    this = this and (1uL shl index.toInt()).inv()
    this = this or ((value.toULong()) shl index.toInt())
    return this
}

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 3)
 * check(zero.countOnes() == 0)
 * ```
 */
public fun ULong.countOnes(): UInt = kmCtpopU64((this).toULong())

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(max.countZeros() == 0)
 * ```
 *
 * This is heavily dependent on the width of the type, and thus
 * might give surprising results depending on type inference:
 * ```kotlin
 * val lucky = 7
 * foo(lucky)
 * check(lucky.countZeros() == 5)
 * check(lucky.countOnes() == 3)
 * val lucky1 = 7
 * bar(lucky1)
 * check(lucky1.countZeros() == 13)
 * check(lucky1.countOnes() == 3)
 * ```
 * You might want to use [Self.countOnes] instead, or emphasize
 * the type you're using in the call rather than method syntax:
 * ```kotlin
 * val small = 1
 * ```
 */
public fun ULong.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 2)
 * check(max.leadingZeros() == 0)
 * ```
 */
public fun ULong.leadingZeros(): UInt = kmCtlzU64((this).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun ULong.trailingZeros(): UInt = kmCttzU64((this).toULong())

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingOnes() == 2)
 * check(zero.leadingOnes() == 0)
 * ```
 */
public fun ULong.leadingOnes(): UInt = (!this).leadingZeros()

/**
 * Returns the number of trailing ones in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 3)
 * check(zero.trailingOnes() == 0)
 * ```
 */
public fun ULong.trailingOnes(): UInt = (!this).trailingZeros()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.bitWidth(): UInt = 64u - rustLeadingZeros(this)

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun ULong.isolateHighestOne(): ULong = this and (rustWrappingShr(((1.toULong()) shl (64u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun ULong.isolateLowestOne(): ULong = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that this is equivalent to [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.highestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.highestOne()
        }
    }

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.lowestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.lowestOne()
        }
    }

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.castSigned(): Long = this.toLong()

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.saturatingCastSigned(): Long =
    if (this <= Long.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        Long.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.checkedCastSigned(): Long? =
    if (this <= Long.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.strictCastSigned(): Long =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.rotateLeft(n: UInt): ULong = io.github.kotlinmania.rustlang.core.intrinsics.rotateLeft(this, n)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.rotateRight(n: UInt): ULong = io.github.kotlinmania.rustlang.core.intrinsics.rotateRight(this, n)

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.funnelShl(right: ULong, n: UInt): ULong {
    if (false) {
        check(n < 64u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (64u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.funnelShr(right: ULong, n: UInt): ULong {
    if (false) {
        check(n < 64u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (64u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun ULong.uncheckedFunnelShl(right: ULong, n: UInt): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun ULong.uncheckedFunnelShr(right: ULong, n: UInt): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.carrylessMul(rhs: ULong): ULong = CarrylessMul(this, rhs)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun ULong.swapBytes(): ULong = kmBswapU64((this).toULong()).toULong()

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun ULong.extractBits(mask: ULong): ULong = io.github.kotlinmania.rustlang.core.num.imp.intbits.ULong.extractImpl(this, mask).toULong()

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun ULong.depositBits(mask: ULong): ULong = io.github.kotlinmania.rustlang.core.num.imp.intbits.ULong.depositImpl(this, mask).toULong()

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun ULong.reverseBits(): ULong = kmBitreverseU64((this).toULong()).toULong()

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun ULong.Companion.fromBe(x: ULong): ULong = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun ULong.Companion.fromLe(x: ULong): ULong = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun ULong.toBe(): ULong = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun ULong.toLe(): ULong = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedAdd(rhs: ULong): ULong? =
    if (addWithOverflow(this, rhs).second) {
        null
    } else {
        uncheckedAdd(this, rhs)
    }

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictAdd(rhs: ULong): ULong {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun ULong.uncheckedAdd(rhs: ULong): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedAddSigned(rhs: Long): ULong? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictAddSigned(rhs: Long): ULong {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedSub(rhs: ULong): ULong? =
    if (this < rhs) {
        null
    } else {
        uncheckedSub(this, rhs)
    }

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictSub(rhs: ULong): ULong {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * If you find yourself writing code like this:
 *
 * ```kotlin
 * if (foo >= bar) {
 *     // SAFETY: just checked it will not overflow
 *     val diff = foo.uncheckedSub(bar)
 *     // ... use diff ...
 * }
 * ```
 *
 * Consider changing it to
 *
 * ```kotlin
 * when (val matchVal = foo.checkedSub(bar)) {
 *     null -> {}
 *     else -> {
 *         val diff = matchVal
 *         // ... use diff ...
 *     }
 * }
 * ```
 *
 * As that does exactly the same thing -- including telling the optimizer
 * that the subtraction cannot overflow -- but avoids needing `unchecked`.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun ULong.uncheckedSub(rhs: ULong): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedSubSigned(rhs: Long): ULong? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictSubSigned(rhs: Long): ULong {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedSignedDiff(rhs: ULong): Long? {
    val res = (this - (rhs.toULong())).toLong()
    val overflow = (this >= rhs) == (res < 0L)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedMul(rhs: ULong): ULong? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictMul(rhs: ULong): ULong {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun ULong.uncheckedMul(rhs: ULong): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedDiv(rhs: ULong): ULong? =
    if (rhs == 0uL) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictDiv(rhs: ULong): ULong = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedDivEuclid(rhs: ULong): ULong? =
    if (rhs == 0uL) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations. Since, for the
 * positive integers, all common definitions of division are equal, this
 * is exactly equal to `self.strictDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictDivEuclid(rhs: ULong): ULong = this / rhs

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0` or if `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.checkedDivExact(rhs: ULong): ULong? =
    if (rhs == 0uL) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.divExact(rhs: ULong): ULong? =
    if (this % rhs != 0uL) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0` or `self % rhs != 0`,
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun ULong.uncheckedDivExact(rhs: ULong): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_div_exact divide by zero or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedRem(rhs: ULong): ULong? =
    if (rhs == 0uL) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`.
 *
 * Strict remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictRem(rhs: ULong): ULong = this % rhs

/**
 * Checked Euclidean modulo. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedRemEuclid(rhs: ULong): ULong? =
    if (rhs == 0uL) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Strict modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.strictRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictRemEuclid(rhs: ULong): ULong = this % rhs

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(5)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 */
public fun ULong.uncheckedDisjointBitor(rhs: ULong): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`](Self.ilog2) can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is zero, or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.ilog(base: ULong): UInt {
    check(base >= 2uL) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun ULong.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedIlog(base: ULong): UInt? {
    if (false) {
        if (base.isPowerOfTwo() && base > 1uL) {
            val k = base.ilog2()
            return when (val matchVal = this.checkedIlog2()) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            } / k
        }
        if (base == 10uL) {
            return this.checkedIlog10()
        }
    }
    return if (this <= 0uL || base <= 1uL) {
        null
    } else if (this < base) {
        0u
    } else {
        var n = 1
        var r = base
        if (64u == 128) {
            n = this.ilog2() / (base.ilog2() + 1u)
            r = rustPow(base, n.toUInt())
        }
        while (r <= this / base) {
            n += 1
            r *= base
        }
        n
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedIlog2(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog2()
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedIlog10(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog10()
        }
    }

/**
 * Checked negation. Computes `- self`, returning `None` unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedNeg(): ULong? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict negation. Computes `- self`, panicking unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictNeg(): ULong {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedShl(rhs: UInt): ULong? =
    if (rhs < 64u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictShl(rhs: UInt): ULong {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun ULong.uncheckedShl(rhs: UInt): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift left by i is the same as `<<1` i times
 *     check(running == start.unboundedShl(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shl 1
 * }
 * ```
 */
public fun ULong.unboundedShl(rhs: UInt): ULong =
    if (rhs < 64u) {
        this.uncheckedShl(rhs)
    } else {
        0uL
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.shlExact(rhs: UInt): ULong? =
    if (rhs <= rustLeadingZeros(this) && rhs < 64u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.leadingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun ULong.uncheckedShlExact(rhs: UInt): ULong {
    assertUnsafePrecondition(checkLibraryUb, "u64::unchecked_shl_exact cannot shift out non-zero bits", zeros, this, leadingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedShr(rhs: UInt): ULong? =
    if (rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictShr(rhs: UInt): ULong {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun ULong.uncheckedShr(rhs: UInt): ULong {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift right by i is the same as `>> 1` i times
 *     check(running == start.unboundedShr(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shr 1
 * }
 * ```
 */
public fun ULong.unboundedShr(rhs: UInt): ULong =
    if (rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        0uL
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.shrExact(rhs: UInt): ULong? =
    if (rhs <= rustTrailingZeros(this) && rhs < 64u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun ULong.uncheckedShrExact(rhs: UInt): ULong {
    assertUnsafePrecondition(checkLibraryUb, "u64::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedPow(exp: UInt): ULong? {
    var exp1 = exp
    var base = this
    var acc: ULong = 1uL
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return rustCheckedShl((1.toULong()), shift.toUInt())
    }
    if (exp1 == 0u) {
        return 1uL
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun ULong.strictPow(exp: UInt): ULong =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val a = matchVal
            a
        }
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingAdd(rhs: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingAddSigned(rhs: Long): ULong {
    val (res, overflow) = rustOverflowingAdd(this, rhs.toULong())
    return if (overflow == (rhs < 0L)) {
        res
    } else if (overflow) {
        ULong.MAX_VALUE
    } else {
        0uL
    }
}

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating
 * at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingSub(rhs: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingSubSigned(rhs: Long): ULong {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < 0L) {
        ULong.MAX_VALUE
    } else {
        0uL
    }
}

/**
 * Saturating integer multiplication. Computes `self * rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingMul(rhs: ULong): ULong =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> ULong.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingDiv(rhs: ULong): ULong = rustWrappingDiv(this, rhs)

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.saturatingPow(exp: UInt): ULong =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> ULong.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingAdd(rhs: ULong): ULong = (this + rhs)

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingAddSigned(rhs: Long): ULong = (this + (rhs.toULong()))

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingSub(rhs: ULong): ULong = (this - rhs)

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingSubSigned(rhs: Long): ULong = (this - (rhs.toULong()))

/**
 * Wrapping (modular) multiplication. Computes `self *
 * rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * check(((10u.toUByte()) * (12u.toUByte())).toUByte() == 120)
 * check(((25u.toUByte()) * (12u.toUByte())).toUByte() == 44)
 * ```
 */
public fun ULong.wrappingMul(rhs: ULong): ULong = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingDiv(rhs: ULong): ULong = this / rhs

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations. Since, for
 * the positive integers, all common definitions of division are equal,
 * this is exactly equal to `self.wrappingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingDivEuclid(rhs: ULong): ULong = this / rhs

/**
 * Wrapping (modular) remainder. Computes `self % rhs`.
 *
 * Wrapped remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingRem(rhs: ULong): ULong = this % rhs

/**
 * Wrapping Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Wrapped modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.wrappingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingRemEuclid(rhs: ULong): ULong = this % rhs

/**
 * Wrapping (modular) negation. Computes `- self`,
 * wrapping around at the boundary of the type.
 *
 * Since unsigned types do not have negative equivalents
 * all applications of this function will wrap (except for `- 0`).
 * For values smaller than the corresponding signed type's maximum
 * the result is the same as casting the corresponding signed value.
 * Any larger values are equivalent to `MAX + 1 - (val - MAX - 1)` where
 * `MAX` is the corresponding signed type's maximum.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingNeg(): ULong = ((0.toULong()) - (this.toULong()))

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the
 * RHS of a wrapping shift-left is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingShl(rhs: UInt): ULong = this.uncheckedShl(rhs and (64u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the
 * RHS of a wrapping shift-right is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.wrappingShr(rhs: UInt): ULong = this.uncheckedShr(rhs and (64u - 1u))

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().wrappingPow(6) == 217)
 * ```
 */
public fun ULong.wrappingPow(exp: UInt): ULong {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingAdd(rhs: ULong): Pair<ULong, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(a.toULong(), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and returns a tuple containing
 * the sum and the output carry (in that order).
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns an output integer and a carry-out bit. This allows
 * chaining together multiple additions to create a wider addition, and
 * can be useful for bignum addition.
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd), and the output carry is
 * equal to the overflow flag. Note that although carry and overflow
 * flags are similar for unsigned integers, they are different for
 * signed integers.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val carry0 = false
 * val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 * check(carry1 == true)
 * val (sum1, carry2) = a1.carryingAdd(b1, carry1)
 * check(carry2 == false)
 * check(Pair(sum1, sum0) == Pair(9, 6))
 * ```
 */
public fun ULong.carryingAdd(rhs: ULong, carry: Boolean): Pair<ULong, Boolean> {
    val (a, c1) = rustOverflowingAdd(this, rhs)
    val (b, c2) = a.overflowingAdd(carry.toULong())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingAddSigned(rhs: Long): Pair<ULong, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs.toULong())
    return Pair(res, overflowed xor (rhs < 0L))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingSub(rhs: ULong): Pair<ULong, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(a.toULong(), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and returns a tuple
 * containing the difference and the output borrow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns an output
 * integer and a borrow-out bit. This allows chaining together multiple
 * subtractions to create a wider subtraction, and can be useful for
 * bignum subtraction.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val borrow0 = false
 * val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 * check(borrow1 == true)
 * val (diff1, borrow2) = a1.borrowingSub(b1, borrow1)
 * check(borrow2 == false)
 * ```
 */
public fun ULong.borrowingSub(rhs: ULong, borrow: Boolean): Pair<ULong, Boolean> {
    val (a, c1) = rustOverflowingSub(this, rhs)
    val (b, c2) = a.overflowingSub(borrow.toULong())
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingSubSigned(rhs: Long): Pair<ULong, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs.toULong())
    return Pair(res, overflow xor (rhs < 0L))
}

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.absDiff(other: ULong): ULong =
    if (sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64) == 1) {
        ((this.toInt()) - (other.toInt())).unsignedAbs().toULong()
    } else {
        if (this < other) {
            other - this
        } else {
            this - other
        }
    }

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean
 * indicating whether an arithmetic overflow would occur. If an
 * overflow would have occurred then the wrapped value is returned.
 *
 * If you want the *value* of the overflow, rather than just *whether*
 * an overflow occurred, see [Self.carryingMul].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.overflowingMul(2u) == Pair(10, false))
 * check(1000000000u.overflowingMul(10u) == Pair(1410065408, true))
 * ```
 */
public fun ULong.overflowingMul(rhs: ULong): Pair<ULong, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(a.toULong(), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you also need to add a value, then use [Self.carryingMulAdd].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.carryingMul(2, 0) == Pair(10, 0))
 * check(5u.carryingMul(2, 10) == Pair(20, 0))
 * check(1000000000u.carryingMul(10, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMul(10, 10) == Pair(1410065418, 2))
 * ```
 *
 * This is the core operation needed for scalar multiplication when
 * implementing it for wider-than-native types.
 *
 * ```kotlin
 * fun scalarMulEq(littleEndianDigits: List<UShort>, multiplicand: UShort) {
 *     var carry = 0
 *     for (d in littleEndianDigits) {
 *         Pair(d, carry) = d.carryingMul(multiplicand, carry)
 *     }
 *     if (carry != 0) {
 *         littleEndianDigits.push(carry)
 *     }
 * }
 * var v = mutableListOf(10, 20)
 * scalarMulEq(v, 3u)
 * check(v == listOf(30, 60))
 * check(0x87654321uL * 0xFEEDuL == 0x86D3D159E38DuL)
 * var v1 = mutableListOf(0x4321, 0x8765)
 * scalarMulEq(v1, 0xFEEDu)
 * check(v1 == listOf(0xE38D, 0xD159, 0x86D3))
 * ```
 *
 * If `carry` is zero, this is similar to [`overflowingMul`](Self.overflowingMul),
 * except that it gives the value of the overflow instead of just whether one happened:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     val r = UByte.carryingMul(7, 13, 0)
 *     check(Pair(r.first, r.second != 0) == UByte.overflowingMul(7, 13))
 *     val r1 = UByte.carryingMul(13, 42, 0)
 *     check(Pair(r1.first, r1.second != 0) == UByte.overflowingMul(13, 42))
 * }
 * ```
 *
 * The value of the first field in the returned tuple matches what you'd get
 * by combining the [`wrappingMul`](Self.wrappingMul) and
 * [`wrappingAdd`](Self.wrappingAdd) methods:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     check(789u.toUShort().carryingMul(456, 123).first == ((789u.toUShort()) * (456u.toUShort())).toUShort().wrappingAdd(123))
 * }
 * ```
 */
public fun ULong.carryingMul(rhs: ULong, carry: ULong): Pair<ULong, ULong> = ULong.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * This cannot overflow, as the double-width result has exactly enough
 * space for the largest possible result. This is equivalent to how, in
 * decimal, 9 × 9 + 9 + 9 = 81 + 18 = 99 = 9×10⁰ + 9×10¹ = 10² - 1.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you don't need the `add` part, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared between integer types,
 * which explains why `UInt` is used here.
 *
 * ```kotlin
 * check(5u.carryingMulAdd(2, 0, 0) == Pair(10, 0))
 * check(5u.carryingMulAdd(2, 10, 10) == Pair(30, 0))
 * check(1000000000u.carryingMulAdd(10, 0, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMulAdd(10, 10, 10) == Pair(1410065428, 2))
 * ```
 *
 * This is the core per-digit operation for "grade school" O(n²) multiplication.
 *
 * Please note that this example is shared between integer types,
 * using `UByte` for simplicity of the demonstration.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun quadraticMul(a: UByteArray, b: UByteArray, n: Int): UByteArray {
 *         var out = MutableList(n) { 0 }
 *         for (j in 0 until n) {
 *             var carry = 0
 *             for (i in 0 until (n - j)) {
 *                 Pair(out[j + i], carry) = UByte.carryingMulAdd(a[i], b[j], out[j + i], carry)
 *             }
 *         }
 *         return out
 *     }
 *     // -1 * -1 == 1
 *     check(quadraticMul(List(3) { 0xFF }, List(3) { 0xFF }) == listOf(1, 0, 0))
 *     check(UInt.wrappingMul(0x9e3779b9, 0x7f4a7c15) == 0xcffc982d)
 *     check(quadraticMul(UInt.toLeBytes(0x9e3779b9), UInt.toLeBytes(0x7f4a7c15)) == UInt.toLeBytes(0xcffc982d))
 * }
 * ```
 */
public fun ULong.carryingMulAdd(rhs: ULong, carry: ULong, add: ULong): Pair<ULong, ULong> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingDiv(rhs: ULong): Pair<ULong, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self.overflowingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingDivEuclid(rhs: ULong): Pair<ULong, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingRem(rhs: ULong): Pair<ULong, Boolean> = Pair(this % rhs, false)

/**
 * Calculates the remainder `self.remEuclid(rhs)` as if by Euclidean division.
 *
 * Returns a tuple of the modulo after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this operation
 * is exactly equal to `self.overflowingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingRemEuclid(rhs: ULong): Pair<ULong, Boolean> = Pair(this % rhs, false)

/**
 * Negates self in an overflowing fashion.
 *
 * Returns `!self + 1` using wrapping operations to return the value
 * that represents the negation of this unsigned value. Note that for
 * positive unsigned values overflow always occurs, but negating 0 does
 * not overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingNeg(): Pair<ULong, Boolean> = Pair((!this).wrappingAdd(1), this != 0uL)

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingShl(rhs: UInt): Pair<ULong, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 64u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.overflowingShr(rhs: UInt): Pair<ULong, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 64u)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().overflowingPow(6) == Pair(217, true))
 * ```
 */
public fun ULong.overflowingPow(exp: UInt): Pair<ULong, Boolean> {
    var exp1 = exp
    val base = this
    val acc: ULong  = 1uL
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        return Pair((1.toULong()).unboundedShl(shift), shift >= 64u)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.pow(exp: UInt): ULong =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.isqrt(): ULong {
    val result = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.ULong(this).toULong()
    run {
        val MAX_RESULT: ULong = io.github.kotlinmania.rustlang.core.num.imp.intsqrt.ULong(ULong.MAX_VALUE).toULong()
        assertUnchecked(result <= MAX_RESULT)
    }
    if (this >= 1uL) {
        assertUnchecked(result >= 1uL)
    }
    run {
        assertUnchecked(result.uncheckedMul(result) <= this)
        assertUnchecked(result <= this)
    }
    return result
}

/**
 * Performs Euclidean division.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self / rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.divEuclid(rhs: ULong): ULong = this / rhs

/**
 * Calculates the least remainder of `self` when divided by
 * `rhs`.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self % rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.remEuclid(rhs: ULong): ULong = this % rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * This is the same as performing `self / rhs` for all unsigned integers.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.divFloor(rhs: ULong): ULong = this / rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.divCeil(rhs: ULong): ULong {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0uL) {
        d + 1uL
    } else {
        d
    }
}

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.nextMultipleOf(rhs: ULong): ULong =
    when (val matchVal = this % rhs) {
        0 -> this
        else -> {
            val r = matchVal
            this + (rhs - r)
        }
    }

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`. Returns `None` if `rhs` is zero or the
 * operation would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedNextMultipleOf(rhs: ULong): ULong? {
    return when (val matchVal1 = when (val matchVal = rustCheckedRem(this, rhs)) {
        null -> { return null }
        else -> {
            val x = matchVal
            x
        }
    }) {
        0 -> this
        else -> {
            val r = matchVal1
            rustCheckedAdd(this, rhs - r)
        }
    }
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.isMultipleOf(rhs: ULong): Boolean =
    when (rhs) {
        0 -> this == 0uL
        else -> this % rhs == 0uL
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.nextPowerOfTwo(): ULong =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            ULong.MAX_VALUE + 1uL
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.checkedNextPowerOfTwo(): ULong? {
    val m1 = rustSaturatingSub(this, 1uL)
    return if (m1.castSigned() >= 0L) {
        val exp = m1.bitWidth()
        1uL shl exp.toInt()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.wrappingNextPowerOfTwo(): ULong = (this.checkedNextPowerOfTwo() ?: 0uL)

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.toBeBytes(): UByteArray = run { val rawBits = ((((this) shl 56)) or (((this) and 0x000000000000FF00uL) shl 40) or (((this) and 0x0000000000FF0000uL) shl 24) or (((this) and 0x00000000FF000000uL) shl 8) or ((((this) shr 8) and 0x00000000FF000000uL)) or ((((this) shr 24) and 0x0000000000FF0000uL)) or ((((this) shr 40) and 0x000000000000FF00uL)) or ((this) shr 56)); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.toLeBytes(): UByteArray = run { val rawBits = this; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun ULong.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun ULong.Companion.fromBeBytes(bytes: UByteArray): ULong = run { val swapVal0 = (ULong.fromNeBytes(bytes)); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) }

/**
 * Creates a native endian integer value from its representation
 * as a byte array in little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun ULong.Companion.fromLeBytes(bytes: UByteArray): ULong = ULong.fromNeBytes(bytes)

/**
 * Creates a native endian integer value from its memory representation
 * as a byte array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun ULong.Companion.fromNeBytes(bytes: UByteArray): ULong = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()), PrimitiveLayouts.U64)

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun ULong.Companion.minValue(): ULong = ULong.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun ULong.Companion.maxValue(): ULong = ULong.MAX_VALUE

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120u.toUByte() == 376u.truncate())
 * }
 * ```
 */
public inline fun <reified Target> ULong.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(255u.toUByte() == 376u.saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> ULong.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376u.checkedTruncate<UByte>())
 * }
 * ```
 */
public inline fun <reified Target> ULong.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> ULong.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<ULong, T>> ULong.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<ULong, T>> ULong.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<ULong, T>> ULong.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<ULong, T>> ULong.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<ULong, T>> ULong.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "u64::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large unsigned integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun ULong.midpoint(rhs: ULong): ULong = ((U128.fromULong(this) + U128.fromULong(rhs)) / U128(0x0uL, 0x2uL)).toULong()

/**
 * Widening multiplication. Computes `self * rhs`, widening to a larger integer.
 *
 * The returned value is always exact and can never overflow.
 *
 * Note that this method is semantically equivalent to [carryingMul] with a
 * carry of zero, with the latter instead returning a tuple denoting the low and
 * high parts of the result. Consider using it instead if you need
 * interoperability with other big int helper functions, or if this method isn't
 * available for a given type.
 *
 * [carryingMul]: Self.carryingMul
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wideningMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.wideningMul(rhs: ULong): U128 = U128.fromULong(this) * U128.fromULong(rhs)

/**
 * Performs a widening carry-less multiplication.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun ULong.wideningCarrylessMul(rhs: ULong): U128 = (U128.fromULong(this)).carrylessMul(U128.fromULong(rhs))

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun ULong.carryingCarrylessMul(rhs: ULong, carry: ULong): Pair<ULong, ULong> {
    val p = (U128.fromULong(this)).carrylessMul(U128.fromULong(rhs))
    val lo = (p.toULong())
    val hi = (p shr 64u.toInt()).toULong()
    return Pair(lo xor carry, hi)
}

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN10: U128 = U128(0x0uL, 0x0uL)

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX10: U128 = (U128(0x0uL, 0x0uL)).inv()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS10: UInt = U128.MAX_VALUE.countOnes()

/**
 * Tests the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal to [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     check(n.bit(0))
 *     check(!n.bit(1))
 *     check(!n.bit(2))
 *     check(n.bit(3))
 *     check(n.bit(4))
 *     check(n.bit(5))
 *     check(n.bit(6))
 *     check(!n.bit(7))
 * }
 * ```
 */
public fun U128.bit(index: UInt): Boolean = (this and (U128(0x0uL, 0x1uL) shl index.toInt())) != U128(0x0uL, 0x0uL)

/**
 * Sets the value of a specific bit.
 *
 * # Panics
 *
 * If overflow checks are enabled, this method will panic if the provided index is
 * greater than or equal [Bits].
 *
 * [Bits]: Self.Bits
 *
 * # Examples
 *
 * ```kotlin
 * @feature(getSetBit)
 * public fun doctestEntry() {
 *     n = n.setBit(0, false)
 *     n = n.setBit(1, true)
 *     n = n.setBit(2, false)
 *     n = n.setBit(3, true)
 *     n = n.setBit(4, false)
 *     n = n.setBit(5, true)
 *     n = n.setBit(6, false)
 *     n = n.setBit(7, true)
 *     check(n == 0xAA)
 * }
 * ```
 */
public fun U128.setBit(index: UInt, value: Boolean): U128 {
    this = this and (U128(0x0uL, 0x1uL) shl index.toInt()).inv()
    this = this or ((U128.fromLong(value.toLong())) shl index.toInt())
    return this
}

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.countOnes() == 3)
 * check(zero.countOnes() == 0)
 * ```
 */
public fun U128.countOnes(): UInt = kmCtpopU64((this).toULong())

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(max.countZeros() == 0)
 * ```
 *
 * This is heavily dependent on the width of the type, and thus
 * might give surprising results depending on type inference:
 * ```kotlin
 * val lucky = 7
 * foo(lucky)
 * check(lucky.countZeros() == 5)
 * check(lucky.countOnes() == 3)
 * val lucky1 = 7
 * bar(lucky1)
 * check(lucky1.countZeros() == 13)
 * check(lucky1.countOnes() == 3)
 * ```
 * You might want to use [Self.countOnes] instead, or emphasize
 * the type you're using in the call rather than method syntax:
 * ```kotlin
 * val small = 1
 * ```
 */
public fun U128.countZeros(): UInt = (!this).countOnes()

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * Depending on what you're doing with the value, you might also be interested in the
 * [ilog2] function which returns a consistent number, even if the type widens.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 2)
 * check(max.leadingZeros() == 0)
 * ```
 */
public fun U128.leadingZeros(): UInt = kmCtlzU64((this).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun U128.trailingZeros(): UInt = kmCttzU64((this).toULong())

/**
 * Returns the number of leading ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingOnes() == 2)
 * check(zero.leadingOnes() == 0)
 * ```
 */
public fun U128.leadingOnes(): UInt = (!this).leadingZeros()

/**
 * Returns the number of trailing ones in the binary representation
 * of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingOnes() == 3)
 * check(zero.trailingOnes() == 0)
 * ```
 */
public fun U128.trailingOnes(): UInt = (!this).trailingZeros()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.bitWidth(): UInt = 128u - rustLeadingZeros(this)

/**
 * Returns `self` with only the most significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateHighestOne() == 0x40)
 * ```
 */
public fun U128.isolateHighestOne(): U128 = this and (rustWrappingShr(((U128.fromLong(1.toLong())) shl (128u - 1u).toInt()), rustLeadingZeros(this)))

/**
 * Returns `self` with only the least significant bit set, or `0` if
 * the input is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.isolateLowestOne() == 0x4)
 * ```
 */
public fun U128.isolateLowestOne(): U128 = this and rustWrappingNeg(this)

/**
 * Returns the index of the highest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * Note that this is equivalent to [`checkedIlog2`](Self.checkedIlog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.highestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.highestOne()
        }
    }

/**
 * Returns the index of the lowest bit set to one in `self`, or `None`
 * if `self` is `0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.lowestOne(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val v = matchVal
            v.lowestOne()
        }
    }

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.castSigned(): I128 = this.toI128()

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.saturatingCastSigned(): I128 =
    if (this <= I128.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        I128.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.checkedCastSigned(): I128? =
    if (this <= I128.MAX_VALUE.castUnsigned()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.strictCastSigned(): I128 =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * `rotateLeft(n)` is equivalent to applying `rotateLeft(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.rotateLeft(n: UInt): U128 = io.github.kotlinmania.rustlang.core.intrinsics.rotateLeft(this, n)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * `rotateRight(n)` is equivalent to applying `rotateRight(1)` a total of `n` times. In
 * particular, a rotation by the number of bits in `self` returns the input value
 * unchanged.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.rotateRight(n: UInt): U128 = io.github.kotlinmania.rustlang.core.intrinsics.rotateRight(this, n)

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.funnelShl(right: U128, n: UInt): U128 {
    if (false) {
        check(n < 128u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (128u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.funnelShr(right: U128, n: UInt): U128 {
    if (false) {
        check(n < 128u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (128u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun U128.uncheckedFunnelShl(right: U128, n: UInt): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun U128.uncheckedFunnelShr(right: U128, n: UInt): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.carrylessMul(rhs: U128): U128 = CarrylessMul(this, rhs)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.swapBytes()
 * ```
 */
public fun U128.swapBytes(): U128 = U128.fromLong(kmBswapU64((this).toULong()).toLong())

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun U128.extractBits(mask: U128): U128 = U128.fromLong(U128.extractImpl(this, mask).toLong())

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun U128.depositBits(mask: U128): U128 = U128.fromLong(U128.depositImpl(this, mask).toLong())

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * val m = n.reverseBits()
 * ```
 */
public fun U128.reverseBits(): U128 = U128.fromLong(kmBitreverseU64((this).toULong()).toLong())

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun U128.Companion.fromBe(x: U128): U128 = cfgSelect(targetEndian, "big", x, x, swapBytes)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun U128.Companion.fromLe(x: U128): U128 = cfgSelect(targetEndian, "little", x, x, swapBytes)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun U128.toBe(): U128 = cfgSelect(targetEndian, "big", this, this, swapBytes)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun U128.toLe(): U128 = cfgSelect(targetEndian, "little", this, this, swapBytes)

/**
 * Checked integer addition. Computes `self + rhs`, returning `None`
 * if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedAdd(rhs: U128): U128? =
    if (addWithOverflow(this, rhs).second) {
        null
    } else {
        uncheckedAdd(this, rhs)
    }

/**
 * Strict integer addition. Computes `self + rhs`, panicking
 * if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictAdd(rhs: U128): U128 {
    val (a, b) = rustOverflowingAdd(this, rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Unchecked integer addition. Computes `self + rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedAdd(y)` is semantically equivalent to calling
 * `x.`[checkedAdd]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingAdd].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun U128.uncheckedAdd(rhs: U128): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_add cannot overflow", lhs, this, rhs, rhs, lhs, overflowingAdd, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd(this, rhs)
}

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedAddSigned(rhs: I128): U128? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun U128.strictAddSigned(rhs: I128): U128 {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked integer subtraction. Computes `self - rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedSub(rhs: U128): U128? =
    if (this < rhs) {
        null
    } else {
        uncheckedSub(this, rhs)
    }

/**
 * Strict integer subtraction. Computes `self - rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictSub(rhs: U128): U128 {
    val (a, b) = rustOverflowingSub(this, rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * Unchecked integer subtraction. Computes `self - rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedSub(y)` is semantically equivalent to calling
 * `x.`[checkedSub]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingSub].
 *
 * If you find yourself writing code like this:
 *
 * ```kotlin
 * if (foo >= bar) {
 *     // SAFETY: just checked it will not overflow
 *     val diff = foo.uncheckedSub(bar)
 *     // ... use diff ...
 * }
 * ```
 *
 * Consider changing it to
 *
 * ```kotlin
 * when (val matchVal = foo.checkedSub(bar)) {
 *     null -> {}
 *     else -> {
 *         val diff = matchVal
 *         // ... use diff ...
 *     }
 * }
 * ```
 *
 * As that does exactly the same thing -- including telling the optimizer
 * that the subtraction cannot overflow -- but avoids needing `unchecked`.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedSub] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun U128.uncheckedSub(rhs: U128): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_sub cannot overflow", lhs, this, rhs, rhs, lhs, overflowingSub, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub(this, rhs)
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedSubSigned(rhs: I128): U128? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun U128.strictSubSigned(rhs: I128): U128 {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedSignedDiff(rhs: U128): I128? {
    val res = rustWrappingSub(this, rhs).toI128()
    val overflow = (this >= rhs) == (res < I128(0x0L, 0x0uL))
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Checked integer multiplication. Computes `self * rhs`, returning
 * `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedMul(rhs: U128): U128? {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict integer multiplication. Computes `self * rhs`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictMul(rhs: U128): U128 {
    val (a, b) = rustOverflowingMul(this, rhs)
    return if (b) {
        mul()
    } else {
        a
    }
}

/**
 * Unchecked integer multiplication. Computes `self * rhs`, assuming overflow
 * cannot occur.
 *
 * Calling `x.uncheckedMul(y)` is semantically equivalent to calling
 * `x.`[checkedMul]`(y).`[unwrapUnchecked]`()`.
 *
 * If you're just trying to avoid the panic in debug mode, then **do not**
 * use this.  Instead, you're looking for [wrappingMul].
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedMul] would return `None`.
 *
 * [unwrapUnchecked]: option/enum.Option.html#method.unwrapUnchecked
 */
public fun U128.uncheckedMul(rhs: U128): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_mul cannot overflow", lhs, this, rhs, rhs, lhs, overflowingMul, rhs, 1)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul(this, rhs)
}

/**
 * Checked integer division. Computes `self / rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedDiv(rhs: U128): U128? =
    if (rhs == U128(0x0uL, 0x0uL)) {
        null
    } else {
        uncheckedDiv(this, rhs)
    }

/**
 * Strict integer division. Computes `self / rhs`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictDiv(rhs: U128): U128 = this / rhs

/**
 * Checked Euclidean division. Computes `self.divEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedDivEuclid(rhs: U128): U128? =
    if (rhs == U128(0x0uL, 0x0uL)) {
        null
    } else {
        rustDivEuclid(this, rhs)
    }

/**
 * Strict Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Strict division on unsigned types is just normal division. There's no
 * way overflow could ever happen. This function exists so that all
 * operations are accounted for in the strict operations. Since, for the
 * positive integers, all common definitions of division are equal, this
 * is exactly equal to `self.strictDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictDivEuclid(rhs: U128): U128 = this / rhs

/**
 * Checked integer division without remainder. Computes `self / rhs`,
 * returning `None` if `rhs == 0` or if `self % rhs != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.checkedDivExact(rhs: U128): U128? =
    if (rhs == U128(0x0uL, 0x0uL)) {
        null
    } else {
        if (uncheckedRem(this, rhs) != 0) {
            null
        } else {
            exactDiv(this, rhs)
        }
    }

/**
 * Integer division without remainder. Computes `self / rhs`, returning `None` if `self % rhs != 0`.
 *
 * # Panics
 *
 * This function will panic  if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactDiv)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.divExact(rhs: U128): U128? =
    if (this % rhs != U128(0x0uL, 0x0uL)) {
        null
    } else {
        this / rhs
    }

/**
 * Unchecked integer division without remainder. Computes `self / rhs`.
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs == 0` or `self % rhs != 0`,
 * i.e. when [`checkedDivExact`](Self.checkedDivExact) would return `None`.
 */
public fun U128.uncheckedDivExact(rhs: U128): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_div_exact divide by zero or leave a remainder", lhs, this, rhs, rhs, rhs, 0, lhs, rhs, 0)
    return exactDiv(this, rhs)
}

/**
 * Checked integer remainder. Computes `self % rhs`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedRem(rhs: U128): U128? =
    if (rhs == U128(0x0uL, 0x0uL)) {
        null
    } else {
        uncheckedRem(this, rhs)
    }

/**
 * Strict integer remainder. Computes `self % rhs`.
 *
 * Strict remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictRem(rhs: U128): U128 = this % rhs

/**
 * Checked Euclidean modulo. Computes `self.remEuclid(rhs)`, returning `None`
 * if `rhs == 0`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedRemEuclid(rhs: U128): U128? =
    if (rhs == U128(0x0uL, 0x0uL)) {
        null
    } else {
        rustRemEuclid(this, rhs)
    }

/**
 * Strict Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Strict modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way overflow could ever happen.
 * This function exists so that all operations are accounted for in the
 * strict operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.strictRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of division by zero:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictRemEuclid(rhs: U128): U128 = this % rhs

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming no overlapping one bits.
 *
 * Practically, this requires that `self | rhs`, `self ^ rhs`, and `self + rhs` all
 * yield the same result, allowing for any of the three to be emitted in codegen --
 * depending on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(5)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if `self` and `rhs` have any overlapping one
 * bits, i.e. `lhs & rhs` must be zero.
 */
public fun U128.uncheckedDisjointBitor(rhs: U128): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_disjoint_bitor cannot bitor overlapping ones", lhs, this, rhs, rhs, lhs, rhs, 0)
    return disjointBitor(this, rhs)
}

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * This method might not be optimized owing to implementation details;
 * [`ilog2`](Self.ilog2) can produce results more efficiently for base 2,
 * and [`ilog10`](Self.ilog10) can produce results more efficiently for base 10.
 *
 * # Panics
 *
 * This function will panic if `self` is zero, or if `base` is less than 2.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.ilog(base: U128): UInt {
    check(base >= U128(0x0uL, 0x2uL)) { "base of integer logarithm must be at least 2" }
    return when (val matchVal = this.checkedIlog(base)) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.ilog2(): UInt =
    when (val matchVal = this.checkedIlog2()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * # Panics
 *
 * This function will panic if `self` is zero.
 *
 * # Example
 *
 * ```kotlin
 * ```
 */
public fun U128.ilog10(): UInt =
    when (val matchVal = this.checkedIlog10()) {
        null -> {
            panicForNonpositiveArgument()
        }
        else -> {
            val log = matchVal
            log
        }
    }

/**
 * Returns the logarithm of the number with respect to an arbitrary base,
 * rounded down.
 *
 * Returns `None` if the number is zero, or if the base is not at least 2.
 *
 * This method might not be optimized owing to implementation details;
 * `checkedIlog2` can produce results more efficiently for base 2, and
 * `checkedIlog10` can produce results more efficiently for base 10.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedIlog(base: U128): UInt? {
    if (false) {
        if (base.isPowerOfTwo() && base > U128(0x0uL, 0x1uL)) {
            val k = base.ilog2()
            return when (val matchVal = this.checkedIlog2()) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            } / k
        }
        if (base == U128(0x0uL, 0xauL)) {
            return this.checkedIlog10()
        }
    }
    return if (this <= U128(0x0uL, 0x0uL) || base <= U128(0x0uL, 0x1uL)) {
        null
    } else if (this < base) {
        0u
    } else {
        var n = 1
        var r = base
        if (128u == 128) {
            n = this.ilog2() / (base.ilog2() + 1u)
            r = rustPow(base, n.toUInt())
        }
        while (r <= this / base) {
            n += 1
            r *= base
        }
        n
    }
}

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedIlog2(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog2()
        }
    }

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * Returns `None` if the number is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedIlog10(): UInt? =
    when (val matchVal = NonZero.new(this)) {
        null -> null
        else -> {
            val x = matchVal
            x.ilog10()
        }
    }

/**
 * Checked negation. Computes `- self`, returning `None` unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedNeg(): U128? {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict negation. Computes `- self`, panicking unless `self ==
 * 0`.
 *
 * Note that negating any positive integer will overflow.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictNeg(): U128 {
    val (a, b) = rustOverflowingNeg(this)
    return if (b) {
        neg()
    } else {
        a
    }
}

/**
 * Checked shift left. Computes `self<<rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedShl(rhs: UInt): U128? =
    if (rhs < 128u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Strict shift left. Computes `self<<rhs`, panicking if `rhs` is larger
 * than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictShl(rhs: UInt): U128 {
    val (a, b) = rustOverflowingShl(this, rhs)
    return if (b) {
        shl()
    } else {
        a
    }
}

/**
 * Unchecked shift left. Computes `self<<rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShl] would return `None`.
 */
public fun U128.uncheckedShl(rhs: UInt): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_shl cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl(this, rhs)
}

/**
 * Unbounded shift left. Computes `self<<rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift left by i is the same as `<<1` i times
 *     check(running == start.unboundedShl(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shl 1
 * }
 * ```
 */
public fun U128.unboundedShl(rhs: UInt): U128 =
    if (rhs < 128u) {
        this.uncheckedShl(rhs)
    } else {
        U128(0x0uL, 0x0uL)
    }

/**
 * Exact shift left. Computes `self<<rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self<<rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.shlExact(rhs: UInt): U128? =
    if (rhs <= rustLeadingZeros(this) && rhs < 128u) {
        this.uncheckedShl(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift left. Computes `self<<rhs`, assuming the operation can be
 * losslessly reversed `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.leadingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun U128.uncheckedShlExact(rhs: UInt): U128 {
    assertUnsafePrecondition(checkLibraryUb, "u128::unchecked_shl_exact cannot shift out non-zero bits", zeros, this, leadingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShl(rhs)
}

/**
 * Checked shift right. Computes `self>> rhs`, returning `None`
 * if `rhs` is larger than or equal to the number of bits in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedShr(rhs: UInt): U128? =
    if (rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Strict shift right. Computes `self>> rhs`, panicking if `rhs` is
 * larger than or equal to the number of bits in `self`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictShr(rhs: UInt): U128 {
    val (a, b) = rustOverflowingShr(this, rhs)
    return if (b) {
        shr()
    } else {
        a
    }
}

/**
 * Unchecked shift right. Computes `self>> rhs`, assuming that
 * `rhs` is less than the number of bits in `self`.
 *
 * # Safety
 *
 * This results in undefined behavior if `rhs` is larger than
 * or equal to the number of bits in `self`,
 * i.e. when [checkedShr] would return `None`.
 */
public fun U128.uncheckedShr(rhs: UInt): U128 {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_shr cannot overflow", rhs, rhs, rhs, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr(this, rhs)
}

/**
 * Unbounded shift right. Computes `self>> rhs`, without bounding the value of `rhs`.
 *
 * If `rhs` is larger or equal to the number of bits in `self`,
 * the entire value is shifted out, and `0` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * var running = start
 * for (i in 0 until 160) {
 *     // The unbounded shift right by i is the same as `>> 1` i times
 *     check(running == start.unboundedShr(i))
 *     // Which is not always the case for a wrapping shift
 *     running = running shr 1
 * }
 * ```
 */
public fun U128.unboundedShr(rhs: UInt): U128 =
    if (rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        U128(0x0uL, 0x0uL)
    }

/**
 * Exact shift right. Computes `self>> rhs` as long as it can be reversed losslessly.
 *
 * Returns `None` if any non-zero bits would be shifted out or if `rhs` >=
 * Otherwise, returns `Some(self>> rhs)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(exactBitshifts)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.shrExact(rhs: UInt): U128? =
    if (rhs <= rustTrailingZeros(this) && rhs < 128u) {
        this.uncheckedShr(rhs)
    } else {
        null
    }

/**
 * Unchecked exact shift right. Computes `self>> rhs`, assuming the operation can be
 * losslessly reversed and `rhs` cannot be larger than
 *
 * # Safety
 *
 * This results in undefined behavior when `rhs > self.trailingZeros() || rhs >=
 * i.e. when
 * would return `None`.
 */
public fun U128.uncheckedShrExact(rhs: UInt): U128 {
    assertUnsafePrecondition(checkLibraryUb, "u128::unchecked_shr_exact cannot shift out non-zero bits", zeros, this, trailingZeros, bits, BITS, rhs, rhs, rhs, zeros, rhs, bits)
    return this.uncheckedShr(rhs)
}

/**
 * Checked exponentiation. Computes `self.pow(exp)`, returning `None` if
 * overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedPow(exp: UInt): U128? {
    var exp1 = exp
    var base = this
    var acc: U128 = U128(0x0uL, 0x1uL)
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift =
            when (val matchVal = rustCheckedMul(k, exp1)) {
                null -> { return null }
                else -> {
                    val x = matchVal
                    x
                }
            }
        return rustCheckedShl((U128.fromLong(1.toLong())), shift.toUInt())
    }
    if (exp1 == 0u) {
        return U128(0x0uL, 0x1uL)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                acc = when (val matchVal1 = rustCheckedMul(acc, base)) {
                    null -> { return null }
                    else -> {
                        val x1 = matchVal1
                        x1
                    }
                }
            }
            exp1 /= 2u
            base = when (val matchVal2 = rustCheckedMul(base, base)) {
                null -> { return null }
                else -> {
                    val x2 = matchVal2
                    x2
                }
            }
        }
        return rustCheckedMul(acc, base)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            acc = when (val matchVal3 = rustCheckedMul(acc, base)) {
                null -> { return null }
                else -> {
                    val x3 = matchVal3
                    x3
                }
            }
            if (exp1 == 1u) {
                return acc
            }
        }
        exp1 /= 2u
        base = when (val matchVal4 = rustCheckedMul(base, base)) {
            null -> { return null }
            else -> {
                val x4 = matchVal4
                x4
            }
        }
    }
}

/**
 * Strict exponentiation. Computes `self.pow(exp)`, panicking if
 * overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * ```
 */
public fun U128.strictPow(exp: UInt): U128 =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> pow()
        else -> {
            val a = matchVal
            a
        }
    }

/**
 * Saturating integer addition. Computes `self + rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingAdd(rhs: U128): U128 = io.github.kotlinmania.rustlang.core.intrinsics.saturatingAdd(this, rhs)

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingAddSigned(rhs: I128): U128 {
    val (res, overflow) = rustOverflowingAdd(this, rhs.toU128())
    return if (overflow == (rhs < I128(0x0L, 0x0uL))) {
        res
    } else if (overflow) {
        U128.MAX_VALUE
    } else {
        U128(0x0uL, 0x0uL)
    }
}

/**
 * Saturating integer subtraction. Computes `self - rhs`, saturating
 * at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingSub(rhs: U128): U128 = io.github.kotlinmania.rustlang.core.intrinsics.saturatingSub(this, rhs)

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingSubSigned(rhs: I128): U128 {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < I128(0x0L, 0x0uL)) {
        U128.MAX_VALUE
    } else {
        U128(0x0uL, 0x0uL)
    }
}

/**
 * Saturating integer multiplication. Computes `self * rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingMul(rhs: U128): U128 =
    when (val matchVal = rustCheckedMul(this, rhs)) {
        null -> U128.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Saturating integer division. Computes `self / rhs`, saturating at the
 * numeric bounds instead of overflowing.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingDiv(rhs: U128): U128 = rustWrappingDiv(this, rhs)

/**
 * Saturating integer exponentiation. Computes `self.pow(exp)`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.saturatingPow(exp: UInt): U128 =
    when (val matchVal = rustCheckedPow(this, exp)) {
        null -> U128.MAX_VALUE
        else -> {
            val x = matchVal
            x
        }
    }

/**
 * Wrapping (modular) addition. Computes `self + rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingAdd(rhs: U128): U128 = (this + rhs)

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingAddSigned(rhs: I128): U128 = rustWrappingAdd(this, rhs.toU128())

/**
 * Wrapping (modular) subtraction. Computes `self - rhs`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingSub(rhs: U128): U128 = (this - rhs)

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingSubSigned(rhs: I128): U128 = rustWrappingSub(this, rhs.toU128())

/**
 * Wrapping (modular) multiplication. Computes `self *
 * rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * check(((10u.toUByte()) * (12u.toUByte())).toUByte() == 120)
 * check(((25u.toUByte()) * (12u.toUByte())).toUByte() == 44)
 * ```
 */
public fun U128.wrappingMul(rhs: U128): U128 = (this * rhs)

/**
 * Wrapping (modular) division. Computes `self / rhs`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingDiv(rhs: U128): U128 = this / rhs

/**
 * Wrapping Euclidean division. Computes `self.divEuclid(rhs)`.
 *
 * Wrapped division on unsigned types is just normal division. There's
 * no way wrapping could ever happen. This function exists so that all
 * operations are accounted for in the wrapping operations. Since, for
 * the positive integers, all common definitions of division are equal,
 * this is exactly equal to `self.wrappingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingDivEuclid(rhs: U128): U128 = this / rhs

/**
 * Wrapping (modular) remainder. Computes `self % rhs`.
 *
 * Wrapped remainder calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingRem(rhs: U128): U128 = this % rhs

/**
 * Wrapping Euclidean modulo. Computes `self.remEuclid(rhs)`.
 *
 * Wrapped modulo calculation on unsigned types is just the regular
 * remainder calculation. There's no way wrapping could ever happen.
 * This function exists so that all operations are accounted for in the
 * wrapping operations. Since, for the positive integers, all common
 * definitions of division are equal, this is exactly equal to
 * `self.wrappingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingRemEuclid(rhs: U128): U128 = this % rhs

/**
 * Wrapping (modular) negation. Computes `- self`,
 * wrapping around at the boundary of the type.
 *
 * Since unsigned types do not have negative equivalents
 * all applications of this function will wrap (except for `- 0`).
 * For values smaller than the corresponding signed type's maximum
 * the result is the same as casting the corresponding signed value.
 * Any larger values are equivalent to `MAX + 1 - (val - MAX - 1)` where
 * `MAX` is the corresponding signed type's maximum.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingNeg(): U128 = rustWrappingSub((U128.fromLong(0.toLong())), this)

/**
 * Panic-free bitwise shift-left; yields `self<<mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `<<` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShl] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-left; the
 * RHS of a wrapping shift-left is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateLeft`](Self.rotateLeft) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingShl(rhs: UInt): U128 = this.uncheckedShl(rhs and (128u - 1u))

/**
 * Panic-free bitwise shift-right; yields `self>> mask(rhs)`,
 * where `mask` removes any high-order bits of `rhs` that
 * would cause the shift to exceed the bitwidth of the type.
 *
 * Beware that, unlike most other `wrapping_ *` methods on integers, this
 * does *not* give the same result as doing the shift in infinite precision
 * then truncating as needed. Instead, the behaviour of this method matches what shift instructions
 * do on many processors, and is what the `>>` operator does when overflow
 * checks are disabled, but numerically it's weird.  Consider, instead,
 * using [Self.unboundedShr] which has nicer behaviour.
 *
 * Note that this is *not* the same as a rotate-right; the
 * RHS of a wrapping shift-right is restricted to the range
 * of the type, rather than the bits shifted out of the LHS
 * being returned to the other end. The primitive integer
 * types all implement a [`rotateRight`](Self.rotateRight) function,
 * which may be what you want instead.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.wrappingShr(rhs: UInt): U128 = this.uncheckedShr(rhs and (128u - 1u))

/**
 * Wrapping (modular) exponentiation. Computes `self.pow(exp)`,
 * wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().wrappingPow(6) == 217)
 * ```
 */
public fun U128.wrappingPow(exp: UInt): U128 {
    val (a, _) = this.overflowingPow(exp)
    return a
}

/**
 * Calculates `self` + `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingAdd(rhs: U128): Pair<U128, Boolean> {
    val (a, b) = addWithOverflow(this, rhs)
    return Pair(U128.fromLong(a.toLong()), b)
}

/**
 * Calculates `self` + `rhs` + `carry` and returns a tuple containing
 * the sum and the output carry (in that order).
 *
 * Performs "ternary addition" of two integer operands and a carry-in
 * bit, and returns an output integer and a carry-out bit. This allows
 * chaining together multiple additions to create a wider addition, and
 * can be useful for bignum addition.
 *
 * If the input carry is false, this method is equivalent to
 * [`overflowingAdd`](Self.overflowingAdd), and the output carry is
 * equal to the overflow flag. Note that although carry and overflow
 * flags are similar for unsigned integers, they are different for
 * signed integers.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val carry0 = false
 * val (sum0, carry1) = a0.carryingAdd(b0, carry0)
 * check(carry1 == true)
 * val (sum1, carry2) = a1.carryingAdd(b1, carry1)
 * check(carry2 == false)
 * check(Pair(sum1, sum0) == Pair(9, 6))
 * ```
 */
public fun U128.carryingAdd(rhs: U128, carry: Boolean): Pair<U128, Boolean> {
    val (a, c1) = rustOverflowingAdd(this, rhs)
    val (b, c2) = a.overflowingAdd(U128.fromLong(carry.toLong()))
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingAddSigned(rhs: I128): Pair<U128, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs.toU128())
    return Pair(res, overflowed xor (rhs < I128(0x0L, 0x0uL)))
}

/**
 * Calculates `self` - `rhs`.
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingSub(rhs: U128): Pair<U128, Boolean> {
    val (a, b) = subWithOverflow(this, rhs)
    return Pair(U128.fromLong(a.toLong()), b)
}

/**
 * Calculates `self` &minus; `rhs` &minus; `borrow` and returns a tuple
 * containing the difference and the output borrow.
 *
 * Performs "ternary subtraction" by subtracting both an integer
 * operand and a borrow-in bit from `self`, and returns an output
 * integer and a borrow-out bit. This allows chaining together multiple
 * subtractions to create a wider subtraction, and can be useful for
 * bignum subtraction.
 *
 * # Examples
 *
 * ```kotlin
 * // ---------
 * val borrow0 = false
 * val (diff0, borrow1) = a0.borrowingSub(b0, borrow0)
 * check(borrow1 == true)
 * val (diff1, borrow2) = a1.borrowingSub(b1, borrow1)
 * check(borrow2 == false)
 * ```
 */
public fun U128.borrowingSub(rhs: U128, borrow: Boolean): Pair<U128, Boolean> {
    val (a, c1) = rustOverflowingSub(this, rhs)
    val (b, c2) = a.overflowingSub(U128.fromLong(borrow.toLong()))
    return Pair(b, disjointBitor(c1, c2))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingSubSigned(rhs: I128): Pair<U128, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs.toU128())
    return Pair(res, overflow xor (rhs < I128(0x0L, 0x0uL)))
}

/**
 * Computes the absolute difference between `self` and `other`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.absDiff(other: U128): U128 =
    if (sizeOf<U128>(typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })) == 1) {
        U128.fromUInt(((this.toInt()) - (other.toInt())).unsignedAbs())
    } else {
        if (this < other) {
            other - this
        } else {
            this - other
        }
    }

/**
 * Calculates the multiplication of `self` and `rhs`.
 *
 * Returns a tuple of the multiplication along with a boolean
 * indicating whether an arithmetic overflow would occur. If an
 * overflow would have occurred then the wrapped value is returned.
 *
 * If you want the *value* of the overflow, rather than just *whether*
 * an overflow occurred, see [Self.carryingMul].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.overflowingMul(2u) == Pair(10, false))
 * check(1000000000u.overflowingMul(10u) == Pair(1410065408, true))
 * ```
 */
public fun U128.overflowingMul(rhs: U128): Pair<U128, Boolean> {
    val (a, b) = mulWithOverflow(this, rhs)
    return Pair(U128.fromLong(a.toLong()), b)
}

/**
 * Calculates the "full multiplication" `self * rhs + carry`
 * without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you also need to add a value, then use [Self.carryingMulAdd].
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UInt` is used.
 *
 * ```kotlin
 * check(5u.carryingMul(2, 0) == Pair(10, 0))
 * check(5u.carryingMul(2, 10) == Pair(20, 0))
 * check(1000000000u.carryingMul(10, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMul(10, 10) == Pair(1410065418, 2))
 * ```
 *
 * This is the core operation needed for scalar multiplication when
 * implementing it for wider-than-native types.
 *
 * ```kotlin
 * fun scalarMulEq(littleEndianDigits: List<UShort>, multiplicand: UShort) {
 *     var carry = 0
 *     for (d in littleEndianDigits) {
 *         Pair(d, carry) = d.carryingMul(multiplicand, carry)
 *     }
 *     if (carry != 0) {
 *         littleEndianDigits.push(carry)
 *     }
 * }
 * var v = mutableListOf(10, 20)
 * scalarMulEq(v, 3u)
 * check(v == listOf(30, 60))
 * check(0x87654321uL * 0xFEEDuL == 0x86D3D159E38DuL)
 * var v1 = mutableListOf(0x4321, 0x8765)
 * scalarMulEq(v1, 0xFEEDu)
 * check(v1 == listOf(0xE38D, 0xD159, 0x86D3))
 * ```
 *
 * If `carry` is zero, this is similar to [`overflowingMul`](Self.overflowingMul),
 * except that it gives the value of the overflow instead of just whether one happened:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     val r = UByte.carryingMul(7, 13, 0)
 *     check(Pair(r.first, r.second != 0) == UByte.overflowingMul(7, 13))
 *     val r1 = UByte.carryingMul(13, 42, 0)
 *     check(Pair(r1.first, r1.second != 0) == UByte.overflowingMul(13, 42))
 * }
 * ```
 *
 * The value of the first field in the returned tuple matches what you'd get
 * by combining the [`wrappingMul`](Self.wrappingMul) and
 * [`wrappingAdd`](Self.wrappingAdd) methods:
 *
 * ```kotlin
 * @feature(constUnsignedBigintHelpers)
 * public fun doctestEntry() {
 *     check(789u.toUShort().carryingMul(456, 123).first == ((789u.toUShort()) * (456u.toUShort())).toUShort().wrappingAdd(123))
 * }
 * ```
 */
public fun U128.carryingMul(rhs: U128, carry: U128): Pair<U128, U128> = U128.carryingMulAdd(this, rhs, carry, 0)

/**
 * Calculates the "full multiplication" `self * rhs + carry + add`.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * This cannot overflow, as the double-width result has exactly enough
 * space for the largest possible result. This is equivalent to how, in
 * decimal, 9 × 9 + 9 + 9 = 81 + 18 = 99 = 9×10⁰ + 9×10¹ = 10² - 1.
 *
 * Performs "long multiplication" which takes in an extra amount to add, and may return an
 * additional amount of overflow. This allows for chaining together multiple
 * multiplications to create "big integers" which represent larger values.
 *
 * If you don't need the `add` part, then you can use [Self.carryingMul] instead.
 *
 * # Examples
 *
 * Please note that this example is shared between integer types,
 * which explains why `UInt` is used here.
 *
 * ```kotlin
 * check(5u.carryingMulAdd(2, 0, 0) == Pair(10, 0))
 * check(5u.carryingMulAdd(2, 10, 10) == Pair(30, 0))
 * check(1000000000u.carryingMulAdd(10, 0, 0) == Pair(1410065408, 2))
 * check(1000000000u.carryingMulAdd(10, 10, 10) == Pair(1410065428, 2))
 * ```
 *
 * This is the core per-digit operation for "grade school" O(n²) multiplication.
 *
 * Please note that this example is shared between integer types,
 * using `UByte` for simplicity of the demonstration.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun quadraticMul(a: UByteArray, b: UByteArray, n: Int): UByteArray {
 *         var out = MutableList(n) { 0 }
 *         for (j in 0 until n) {
 *             var carry = 0
 *             for (i in 0 until (n - j)) {
 *                 Pair(out[j + i], carry) = UByte.carryingMulAdd(a[i], b[j], out[j + i], carry)
 *             }
 *         }
 *         return out
 *     }
 *     // -1 * -1 == 1
 *     check(quadraticMul(List(3) { 0xFF }, List(3) { 0xFF }) == listOf(1, 0, 0))
 *     check(UInt.wrappingMul(0x9e3779b9, 0x7f4a7c15) == 0xcffc982d)
 *     check(quadraticMul(UInt.toLeBytes(0x9e3779b9), UInt.toLeBytes(0x7f4a7c15)) == UInt.toLeBytes(0xcffc982d))
 * }
 * ```
 */
public fun U128.carryingMulAdd(rhs: U128, carry: U128, add: U128): Pair<U128, U128> = CarryingMulAdd(this, rhs, carry, add)

/**
 * Calculates the divisor when `self` is divided by `rhs`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingDiv(rhs: U128): Pair<U128, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the quotient of Euclidean division `self.divEuclid(rhs)`.
 *
 * Returns a tuple of the divisor along with a boolean indicating
 * whether an arithmetic overflow would occur. Note that for unsigned
 * integers overflow never occurs, so the second value is always
 * `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self.overflowingDiv(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingDivEuclid(rhs: U128): Pair<U128, Boolean> = Pair(this / rhs, false)

/**
 * Calculates the remainder when `self` is divided by `rhs`.
 *
 * Returns a tuple of the remainder after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingRem(rhs: U128): Pair<U128, Boolean> = Pair(this % rhs, false)

/**
 * Calculates the remainder `self.remEuclid(rhs)` as if by Euclidean division.
 *
 * Returns a tuple of the modulo after dividing along with a boolean
 * indicating whether an arithmetic overflow would occur. Note that for
 * unsigned integers overflow never occurs, so the second value is
 * always `false`.
 * Since, for the positive integers, all common
 * definitions of division are equal, this operation
 * is exactly equal to `self.overflowingRem(rhs)`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingRemEuclid(rhs: U128): Pair<U128, Boolean> = Pair(this % rhs, false)

/**
 * Negates self in an overflowing fashion.
 *
 * Returns `!self + 1` using wrapping operations to return the value
 * that represents the negation of this unsigned value. Note that for
 * positive unsigned values overflow always occurs, but negating 0 does
 * not overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingNeg(): Pair<U128, Boolean> = Pair((!this).wrappingAdd(1), this != U128(0x0uL, 0x0uL))

/**
 * Shifts self left by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingShl(rhs: UInt): Pair<U128, Boolean> = Pair(rustWrappingShl(this, rhs), rhs >= 128u)

/**
 * Shifts self right by `rhs` bits.
 *
 * Returns a tuple of the shifted version of self along with a boolean
 * indicating whether the shift value was larger than or equal to the
 * number of bits. If the shift value is too large, then value is
 * masked (N-1) where N is the number of bits, and this value is then
 * used to perform the shift.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.overflowingShr(rhs: UInt): Pair<U128, Boolean> = Pair(rustWrappingShr(this, rhs), rhs >= 128u)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * Returns a tuple of the exponentiation along with a bool indicating
 * whether an overflow happened.
 *
 * # Examples
 *
 * ```kotlin
 * check(3u.toUByte().overflowingPow(6) == Pair(217, true))
 * ```
 */
public fun U128.overflowingPow(exp: UInt): Pair<U128, Boolean> {
    var exp1 = exp
    val base = this
    val acc: U128  = U128(0x0uL, 0x1uL)
    var overflow = false
    var tmpOverflow: Any?
    if (false && base.isPowerOfTwo()) {
        val k = base.ilog2()
        val shift = rustCheckedMul(k, exp1) ?: return Pair(0, true)
        return Pair((U128.fromLong(1.toLong())).unboundedShl(shift), shift >= 128u)
    }
    if (exp1 == 0u) {
        return Pair(1, false)
    }
    if (false) {
        while (exp1 > 1u) {
            if ((exp1 and 1u) == 1u) {
                Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
                overflow = overflow or tmpOverflow
            }
            exp1 /= 2u
            Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
            overflow = overflow or tmpOverflow
        }
        Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
        overflow = overflow or tmpOverflow
        return Pair(acc, overflow)
    }
    while (true) {
        if ((exp1 and 1u) == 1u) {
            Pair(acc, tmpOverflow) = rustOverflowingMul(acc, base)
            overflow = overflow or tmpOverflow
            if (exp1 == 1u) {
                return Pair(acc, overflow)
            }
        }
        exp1 /= 2u
        Pair(base, tmpOverflow) = rustOverflowingMul(base, base)
        overflow = overflow or tmpOverflow
    }
}

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.pow(exp: UInt): U128 =
    if (false) {
        this.strictPow(exp)
    } else {
        this.wrappingPow(exp)
    }

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.isqrt(): U128 {
    val result = U128.fromLong(io.github.kotlinmania.rustlang.core.num.imp.intsqrt.U128(this).toLong())
    run {
        val MAX_RESULT: U128 = U128.fromLong(io.github.kotlinmania.rustlang.core.num.imp.intsqrt.U128(U128.MAX_VALUE).toLong())
        assertUnchecked(result <= MAX_RESULT)
    }
    if (this >= U128(0x0uL, 0x1uL)) {
        assertUnchecked(result >= U128(0x0uL, 0x1uL))
    }
    run {
        assertUnchecked(result.uncheckedMul(result) <= this)
        assertUnchecked(result <= this)
    }
    return result
}

/**
 * Performs Euclidean division.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self / rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.divEuclid(rhs: U128): U128 = this / rhs

/**
 * Calculates the least remainder of `self` when divided by
 * `rhs`.
 *
 * Since, for the positive integers, all common
 * definitions of division are equal, this
 * is exactly equal to `self % rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.remEuclid(rhs: U128): U128 = this % rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards negative infinity.
 *
 * This is the same as performing `self / rhs` for all unsigned integers.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intRoundings)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.divFloor(rhs: U128): U128 = this / rhs

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.divCeil(rhs: U128): U128 {
    val d = this / rhs
    val r = this % rhs
    return if (r > U128(0x0uL, 0x0uL)) {
        d + U128(0x0uL, 0x1uL)
    } else {
        d
    }
}

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`.
 *
 * # Panics
 *
 * This function will panic if `rhs` is zero.
 *
 * ## Overflow behavior
 *
 * On overflow, this function will panic if overflow checks are enabled (default in debug
 * mode) and wrap if overflow checks are disabled (default in release mode).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.nextMultipleOf(rhs: U128): U128 =
    when (val matchVal = this % rhs) {
        0 -> this
        else -> {
            val r = matchVal
            this + (rhs - r)
        }
    }

/**
 * Calculates the smallest value greater than or equal to `self` that
 * is a multiple of `rhs`. Returns `None` if `rhs` is zero or the
 * operation would result in overflow.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedNextMultipleOf(rhs: U128): U128? {
    return when (val matchVal1 = when (val matchVal = rustCheckedRem(this, rhs)) {
        null -> { return null }
        else -> {
            val x = matchVal
            x
        }
    }) {
        0 -> this
        else -> {
            val r = matchVal1
            rustCheckedAdd(this, rhs - r)
        }
    }
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.isMultipleOf(rhs: U128): Boolean =
    when (rhs) {
        0 -> this == U128(0x0uL, 0x0uL)
        else -> this % rhs == U128(0x0uL, 0x0uL)
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.nextPowerOfTwo(): U128 =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            U128.MAX_VALUE + U128(0x0uL, 0x1uL)
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.checkedNextPowerOfTwo(): U128? {
    val m1 = rustSaturatingSub(this, U128(0x0uL, 0x1uL))
    return if (m1.castSigned() >= I128(0x0L, 0x0uL)) {
        val exp = m1.bitWidth()
        U128(0x0uL, 0x1uL) shl exp.toInt()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun U128.wrappingNextPowerOfTwo(): U128 = (this.checkedNextPowerOfTwo() ?: U128(0x0uL, 0x0uL))

/**
 * Returns the memory representation of this integer as a byte array in
 * big-endian (network) byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.toBeBytes(): UByteArray = rustToBe(this).toLeBytes()

/**
 * Returns the memory representation of this integer as a byte array in
 * little-endian byte order.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.toLeBytes(): UByteArray = rustToLe(this).toLeBytes()

/**
 * Returns the memory representation of this integer as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate,
 * instead.
 *
 * [toBeBytes]: Self.toBeBytes
 * [toLeBytes]: Self.toLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * assertEq(bytes, if cfg(targetEndian = "big") {} else {});
 * ```
 */
public fun U128.toNeBytes(): UByteArray = transmute(this)

/**
 * Creates a native endian integer value from its representation
 * as a byte array in big endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun U128.Companion.fromBeBytes(bytes: UByteArray): U128 = U128.fromBe(U128.fromNeBytes(bytes))

/**
 * Creates a native endian integer value from its representation
 * as a byte array in little endian.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun U128.Companion.fromLeBytes(bytes: UByteArray): U128 = U128.fromLe(U128.fromNeBytes(bytes))

/**
 * Creates a native endian integer value from its memory representation
 * as a byte array in native endianness.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: Self.fromBeBytes
 * [fromLeBytes]: Self.fromLeBytes
 *
 * # Examples
 *
 * ```kotlin
 * } else {
 * });
 * ```
 *
 * When starting from a slice rather than an array, fallible conversion APIs can be used:
 *
 * ```kotlin
 * * input = rest;
 * }
 * ```
 */
public fun U128.Companion.fromNeBytes(bytes: UByteArray): U128 = transmuteWithLayouts(bytes, PrimitiveArrayLayouts.uByteArray((sizeOf<U128>(typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))).toInt()), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * New code should prefer to use
 *
 * Returns the smallest value that can be represented by this integer type.
 */
public fun U128.Companion.minValue(): U128 = U128.MIN_VALUE

/**
 * New code should prefer to use
 *
 * Returns the largest value that can be represented by this integer type.
 */
public fun U128.Companion.maxValue(): U128 = U128.MAX_VALUE

/**
 * Truncate an integer to an integer of the same size or smaller, preserving the least
 * significant bits.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(120u.toUByte() == 376u.truncate())
 * }
 * ```
 */
public inline fun <reified Target> U128.truncate(): Target = this.internalTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, saturating at numeric bounds
 * instead of truncating.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(255u.toUByte() == 376u.saturatingTruncate())
 * }
 * ```
 */
public inline fun <reified Target> U128.saturatingTruncate(): Target = this.internalSaturatingTruncate()

/**
 * Truncate an integer to an integer of the same size or smaller, returning `None` if the value
 * is outside the bounds of the smaller type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {
 *     check(null == 376u.checkedTruncate<UByte>())
 * }
 * ```
 */
public inline fun <reified Target> U128.checkedTruncate(): Target? = this.internalCheckedTruncate()

/**
 * Widen to an integer of the same size or larger, preserving its value.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerWidenTruncate)
 * public fun doctestEntry() {}
 * ```
 */
public inline fun <reified Target> U128.widen(): Target = this.internalWiden()

/**
 * Converts `self` to the target integer type, saturating at the numeric
 * bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<U128, T>> U128.saturatingCast(saturatingCastFromTFn: (T) -> T): T = saturatingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, wrapping around at the
 * boundary of the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : BoundedCastFromInt<U128, T>> U128.wrappingCast(wrappingCastFromTFn: (T) -> T): T = wrappingCastFromTFn(this)

/**
 * Converts `self` to the target integer type, returning `None` if the value
 * is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<U128, T>> U128.checkedCast(checkedCastFromTFn: (T) -> T?): T? = checkedCastFromTFn(this)

/**
 * Converts `self` to the target integer type, panicking if the value
 * is not representable by the target type.
 *
 * # Panics
 *
 * This function will panic if the value is not representable by the target type.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 *
 * The following will panic:
 *
 * ```kotlin
 * @feature(integerCasts)
 * public fun doctestEntry() {}
 * ```
 */
public fun <T : CheckedCastFromInt<U128, T>> U128.strictCast(strictCastFromTFn: (T) -> T): T = strictCastFromTFn(this)

/**
 * Converts `self` to the target integer type, assuming the value is
 * representable by the target type.
 *
 * # Safety
 *
 * This results in undefined behavior if the integer value of `self` is bigger than `T.MAX`,
 * or smaller than `T.MIN`, where `T` is the target type.
 */
public fun <T : CheckedCastFromInt<U128, T>> U128.uncheckedCast(uncheckedCastFromTFn: (T) -> T): T {
    assertUnsafePrecondition(checkLanguageUb, "u128::unchecked_cast must fit in the target type", inBounds, castVal, this, checkedCast, T, ret, castVal, isSome, core, mem, forget, castVal, ret, inBounds)
    return uncheckedCastFromTFn(this)
}

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b) / 2` as if it were performed in a
 * sufficiently-large unsigned integral type. This implies that the result is
 * always rounded towards zero and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun U128.midpoint(rhs: U128): U128 = ((this xor rhs) shr 1) + (this and rhs)

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun U128.carryingCarrylessMul(rhs: U128, carry: U128): Pair<U128, U128> {
    val x0 = this.toULong()
    val x1 = (this shr 64).toULong()
    val y0 = rhs.toULong()
    val y1 = (rhs shr 64).toULong()
    val z0 = ULong.wideningCarrylessMul(x0, y0)
    val z2 = ULong.wideningCarrylessMul(x1, y1)
    val z3 = ULong.wideningCarrylessMul(x0 xor x1, y0 xor y1)
    val z1 = z3 xor z0 xor z2
    val lo = z0 xor (z1 shl 64)
    val hi = z2 xor (z1 shr 64)
    return Pair(lo xor carry, hi)
}

/**
 * The smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN11: Int = 0

/**
 * The largest value that can be represented by this integer type
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX11: Int = 0.inv()

/**
 * The size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS11: UInt = Int.MAX_VALUE.countOnes()

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * This method returns zero if `self` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.bitWidth(): UInt = 32u - rustLeadingZeros(this)

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * This produces the same result as an `as` cast, but ensures that the bit-width remains
 * the same.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.castSigned(): Int = this

/**
 * Saturating conversion of `self` to a signed integer of the same size.
 *
 * The signed integer's maximum value is returned if `self` is larger
 * than the maximum positive value representable by the signed integer.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.saturatingCastSigned(): Int =
    if (this <= Int.MAX_VALUE.castUnsigned().toInt()) {
        this.castSigned()
    } else {
        Int.MAX_VALUE
    }

/**
 * Checked conversion of `self` to a signed integer of the same size,
 * returning `None` if `self` is larger than the signed integer's
 * maximum value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`saturatingCastSigned`](Self.saturatingCastSigned),
 * or [`strictCastSigned`](Self.strictCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.checkedCastSigned(): Int? =
    if (this <= Int.MAX_VALUE.castUnsigned().toInt()) {
        this.castSigned()
    } else {
        null
    }

/**
 * Strict conversion of `self` to a signed integer of the same size,
 * which panics if `self` is larger than the signed integer's maximum
 * value.
 *
 * For other kinds of signed integer casts, see
 * [`castSigned`](Self.castSigned),
 * [`checkedCastSigned`](Self.checkedCastSigned),
 * or [`saturatingCastSigned`](Self.saturatingCastSigned).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(integerCastExtras)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.strictCastSigned(): Int =
    when (val matchVal = this.checkedCastSigned()) {
        null -> castInteger()
        else -> {
            val n = matchVal
            n
        }
    }

/**
 * Performs a left funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a left shift by `n`, and returning the **left half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShl` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateLeft`](Self.rotateLeft) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.funnelShl(right: Int, n: UInt): Int {
    if (false) {
        check(n < 32u) { "attempt to funnel shift left with overflow" }
    }
    return run {
        val n1 = n and (32u - 1u)
        this.uncheckedFunnelShl(right, n1)
    }
}

/**
 * Performs a right funnel shift.
 *
 * This operation can be thought of as concatenating `self` and `right` into an
 * integer twice the size of
 * performing a right shift by `n`, and returning the **right half** of the result.
 *
 * The name comes from "funneling" a wider integer to a narrower integer.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * If overflow checks are enabled (default in debug mode), this function will panic if `n`
 * is greater than or equal to the number of bits in `self`. If overflow checks are
 * disabled (default in release mode), there is no panic; instead, the value is shifted
 * by `n % Self.BITS`.
 *
 * # Examples
 *
 * ```kotlin
 *
 * ```
 *
 * Note that while `funnelShr` can act as a rotate, it does not allow for
 * rotating by an unbounded amount like [`rotateRight`](Self.rotateRight) does:
 *
 * ```kotlin
 * @feature(cfgOverflowChecks)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.funnelShr(right: Int, n: UInt): Int {
    if (false) {
        check(n < 32u) { "attempt to funnel shift right with overflow" }
    }
    return run {
        val n1 = n and (32u - 1u)
        this.uncheckedFunnelShr(right, n1)
    }
}

/**
 * Unchecked funnel shift left.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShl`](Self.funnelShl) would panic.
 */
public fun Int.uncheckedFunnelShl(right: Int, n: UInt): Int {
    assertUnsafePrecondition(checkLanguageUb, "usize::unchecked_funnel_shl cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShl(this, right, n)
}

/**
 * Unchecked funnel shift right.
 *
 * # Safety
 *
 * This results in undefined behavior if `n` is greater than or equal to
 * i.e. when [`funnelShr`](Self.funnelShr) would panic.
 */
public fun Int.uncheckedFunnelShr(right: Int, n: UInt): Int {
    assertUnsafePrecondition(checkLanguageUb, "usize::unchecked_funnel_shr cannot overflow", n, n, n, BITS)
    return io.github.kotlinmania.rustlang.core.intrinsics.uncheckedFunnelShr(this, right, n)
}

/**
 * Performs a carry-less multiplication, returning the lower bits.
 *
 * This operation is similar to long multiplication in base 2, except that exclusive or is
 * used instead of addition. The implementation is equivalent to:
 *
 * ```kotlin
 * val retval = 0;
 *         if (rhs >> i) & 1 != 0 {
 *
 * retval ^ = lhs<<i;
 *         }
 *     }
 * retval
 * }
 * ```
 *
 * The actual implementation is more efficient, and on some platforms lowers directly to a
 * dedicated instruction.
 *
 * # Uses
 *
 * Carryless multiplication can be used to turn a bitmask of quote characters into a
 * bit mask of characters surrounded by quotes:
 *
 * ```kotlin
 * "abc Note \"foobar\" zzz \"a\"!"
 * // input string
 * 0x1020A
 * // quoteMask
 * 0xFC04
 * // quoteMask.carrylessMul(!0) & !quoteMask
 * ```
 *
 * Another use is in cryptography, where carryless multiplication allows for efficient
 * implementations of polynomial multiplication in `GF(2)[X]`, the polynomial ring
 * over `GF(2)`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.carrylessMul(rhs: Int): Int = CarrylessMul(this, rhs)

/**
 * Returns an integer with the bit locations specified by `mask` packed
 * contiguously into the least significant bits of the result.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.extractBits(0x24) == 0x3)
 *     check(n.extractBits(0xF0) == 0xB)
 * }
 * ```
 */
public fun Int.extractBits(mask: Int): Int = io.github.kotlinmania.rustlang.core.num.imp.intbits.ULong.extractImpl(this.toULong(), mask.toULong()).toInt()

/**
 * Returns an integer with the least significant bits of `self`
 * distributed to the bit locations specified by `mask`.
 * ```kotlin
 * @feature(uintGatherScatterBits)
 * public fun doctestEntry() {
 *     check(n.depositBits(0x55) == 0x51)
 *     check(n.depositBits(0xF0) == 0xD0)
 * }
 * ```
 */
public fun Int.depositBits(mask: Int): Int = io.github.kotlinmania.rustlang.core.num.imp.intbits.ULong.depositImpl(this.toULong(), mask.toULong()).toInt()

/**
 * Checked addition with a signed integer. Computes `self + rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedAddSigned(rhs: Int): Int? {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        null
    } else {
        a
    }
}

/**
 * Strict addition with a signed integer. Computes `self + rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun Int.strictAddSigned(rhs: Int): Int {
    val (a, b) = this.overflowingAddSigned(rhs)
    return if (b) {
        add()
    } else {
        a
    }
}

/**
 * Checked subtraction with a signed integer. Computes `self - rhs`,
 * returning `None` if overflow occurred.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedSubSigned(rhs: Int): Int? {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Strict subtraction with a signed integer. Computes `self - rhs`,
 * panicking if overflow occurred.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function will always panic on overflow, regardless of whether overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * The following panic because of overflow:
 *
 * ```kotlin
 * ```
 *
 * ```kotlin
 * ```
 */
public fun Int.strictSubSigned(rhs: Int): Int {
    val (a, b) = this.overflowingSubSigned(rhs)
    return if (b) {
        sub()
    } else {
        a
    }
}

/**
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedSignedDiff(rhs: Int): Int? {
    val res = (this - (rhs.toInt()))
    val overflow = (this >= rhs) == (res < 0)
    return if (!overflow) {
        res
    } else {
        null
    }
}

/**
 * Saturating addition with a signed integer. Computes `self + rhs`,
 * saturating at the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingAddSigned(rhs: Int): Int {
    val (res, overflow) = rustOverflowingAdd(this, rhs)
    return if (overflow == (rhs < 0)) {
        res
    } else if (overflow) {
        Int.MAX_VALUE
    } else {
        0
    }
}

/**
 * Saturating integer subtraction. Computes `self` - `rhs`, saturating at
 * the numeric bounds instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.saturatingSubSigned(rhs: Int): Int {
    val (res, overflow) = this.overflowingSubSigned(rhs)
    return if (!overflow) {
        res
    } else if (rhs < 0) {
        Int.MAX_VALUE
    } else {
        0
    }
}

/**
 * Wrapping (modular) addition with a signed integer. Computes
 * `self + rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingAddSigned(rhs: Int): Int = (this + ((rhs).toInt()))

/**
 * Wrapping (modular) subtraction with a signed integer. Computes
 * `self - rhs`, wrapping around at the boundary of the type.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.wrappingSubSigned(rhs: Int): Int = (this - ((rhs).toInt()))

/**
 * Calculates `self` + `rhs` with a signed `rhs`.
 *
 * Returns a tuple of the addition along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingAddSigned(rhs: Int): Pair<Int, Boolean> {
    val (res, overflowed) = rustOverflowingAdd(this, rhs)
    return Pair(res, overflowed xor (rhs < 0))
}

/**
 * Calculates `self` - `rhs` with a signed `rhs`
 *
 * Returns a tuple of the subtraction along with a boolean indicating
 * whether an arithmetic overflow would occur. If an overflow would
 * have occurred then the wrapped value is returned.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.overflowingSubSigned(rhs: Int): Pair<Int, Boolean> {
    val (res, overflow) = rustOverflowingSub(this, rhs)
    return Pair(res, overflow xor (rhs < 0))
}

/**
 * Returns `true` if `self` is an integer multiple of `rhs`, and false otherwise.
 *
 * This function is equivalent to `self % rhs == 0`, except that it will not panic
 * for `rhs == 0`. Instead, `0.isMultipleOf(0) == true`, and for any non-zero `n`,
 * `n.isMultipleOf(0) == false`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.isMultipleOf(rhs: Int): Boolean =
    when (rhs) {
        0 -> this == 0
        else -> this % rhs == 0
    }

/**
 * Returns `true` if and only if `self == 2 ^ k` for some unsigned integer `k`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.isPowerOfTwo(): Boolean = rustCountOnes(this) == 1u

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), it panics in debug mode and the return value is wrapped to 0 in
 * release mode (the only situation in which this method can return 0).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.nextPowerOfTwo(): Int =
    when (val matchVal = this.checkedNextPowerOfTwo()) {
        null -> {
            Int.MAX_VALUE + 1
        }
        else -> {
            val npot = matchVal
            npot
        }
    }

/**
 * Returns the smallest power of two greater than or equal to `self`. If
 * the next power of two is greater than the type's maximum value,
 * `None` is returned, otherwise the power of two is wrapped in `Some`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun Int.checkedNextPowerOfTwo(): Int? {
    val m1 = rustSaturatingSub(this, 1)
    return if (m1.castSigned() >= 0) {
        val exp = m1.bitWidth()
        1 shl exp.toInt()
    } else {
        null
    }
}

/**
 * Returns the smallest power of two greater than or equal to `n`. If
 * the next power of two is greater than the type's maximum value,
 * the return value is wrapped to `0`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun Int.wrappingNextPowerOfTwo(): Int = (this.checkedNextPowerOfTwo() ?: 0)

/**
 * Calculates the "full carryless multiplication" without the possibility to overflow.
 *
 * This returns the low-order (wrapping) bits and the high-order (overflow) bits
 * of the result as two separate values, in that order.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `UByte` is used.
 *
 * ```kotlin
 * @feature(uintCarrylessMul)
 * public fun doctestEntry() {
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0x0) == Pair(0, 0x40))
 *     check(0x80u.toUByte().carryingCarrylessMul(0x80, 0xF) == Pair(0xF, 0x40))
 * }
 * ```
 */
public fun Int.carryingCarrylessMul(rhs: Int, carry: Int): Pair<Int, Int> {
    val p = (U128.fromInt(this)).carrylessMul(U128.fromInt(rhs))
    val lo = (p.toInt())
    val hi = (p shr 32u.toInt()).toInt()
    return Pair(lo xor carry, hi)
}

/**
 * Returns an `Int` where every byte is equal to `x`.
 */
internal fun Int.Companion.repeatU8(x: UByte): Int = run { val bytesTmp0 = UByteArray((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)).toInt()) { x }; ((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()) }

/**
 * Returns an `Int` where every byte pair is equal to `x`.
 */
internal fun Int.Companion.repeatU16(x: UShort): Int {
    var r = 0
    var i = 0
    while (i < sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)) {
        // Use `wrappingShl` to make it work on targets with 16-bit `Int`
        r = rustWrappingShl(r, 16u) or (x.toInt())
        i += 2
    }
    return r
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrInt(src: String): Result<Int, ParseIntError> = rustFromStrRadixInt(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Int.Companion.fromStrRadix(src: String, radix: UInt): Result<Int, ParseIntError> = Int.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Int.Companion.fromAsciiBytes(src: List<UByte>): Result<Int, ParseIntError> = Int.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Int.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<Int, ParseIntError> = Int.fromAsciiBytesRadixImpl(src, radix)

internal fun Int.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<Int, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0 > Int.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<Int>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.ISIZE64)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toInt())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toInt())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toInt())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toInt())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toInt())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toInt()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toInt())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toInt()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrByte(src: String): Result<Byte, ParseIntError> = rustFromStrRadixByte(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Byte.Companion.fromStrRadix(src: String, radix: UInt): Result<Byte, ParseIntError> = Byte.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Byte.Companion.fromAsciiBytes(src: List<UByte>): Result<Byte, ParseIntError> = Byte.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Byte.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<Byte, ParseIntError> = Byte.fromAsciiBytesRadixImpl(src, radix)

internal fun Byte.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<Byte, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0.toByte() > Byte.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<Byte>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.I8)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toByte())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toByte())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toByte())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toByte())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toByte())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toByte()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toByte())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toByte()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrShort(src: String): Result<Short, ParseIntError> = rustFromStrRadixShort(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Short.Companion.fromStrRadix(src: String, radix: UInt): Result<Short, ParseIntError> = Short.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Short.Companion.fromAsciiBytes(src: List<UByte>): Result<Short, ParseIntError> = Short.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Short.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<Short, ParseIntError> = Short.fromAsciiBytesRadixImpl(src, radix)

internal fun Short.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<Short, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0.toShort() > Short.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<Short>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.I16)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toShort())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toShort())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toShort())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toShort())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toShort())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toShort()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toShort())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toShort()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrLong(src: String): Result<Long, ParseIntError> = rustFromStrRadixLong(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Long.Companion.fromStrRadix(src: String, radix: UInt): Result<Long, ParseIntError> = Long.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Long.Companion.fromAsciiBytes(src: List<UByte>): Result<Long, ParseIntError> = Long.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun Long.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<Long, ParseIntError> = Long.fromAsciiBytesRadixImpl(src, radix)

internal fun Long.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<Long, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0L > Long.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<Long>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.I64)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toLong())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toLong())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toLong())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toLong())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toLong())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toLong()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toLong())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toLong()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrI128(src: String): Result<I128, ParseIntError> = rustFromStrRadixI128(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun I128.Companion.fromStrRadix(src: String, radix: UInt): Result<I128, ParseIntError> = I128.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun I128.Companion.fromAsciiBytes(src: List<UByte>): Result<I128, ParseIntError> = I128.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun I128.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<I128, ParseIntError> = I128.fromAsciiBytesRadixImpl(src, radix)

internal fun I128.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<I128, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = I128(0x0L, 0x0uL) > I128.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<I128>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, I128.fromUInt(radix))
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(I128.fromLong(x.toLong()))
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, I128.fromUInt(radix))
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(I128.fromLong(x1.toLong()))
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(I128.fromUInt(radix))
                val x2 =
                    I128.fromLong((when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toLong())
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(I128.fromUInt(radix))
                val x3 =
                    I128.fromLong((when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toLong())
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrUByte(src: String): Result<UByte, ParseIntError> = rustFromStrRadixUByte(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UByte.Companion.fromStrRadix(src: String, radix: UInt): Result<UByte, ParseIntError> = UByte.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UByte.Companion.fromAsciiBytes(src: List<UByte>): Result<UByte, ParseIntError> = UByte.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UByte.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<UByte, ParseIntError> = UByte.fromAsciiBytesRadixImpl(src, radix)

internal fun UByte.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<UByte, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0u.toUByte() > UByte.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<UByte>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.U8)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toUByte())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toUByte())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toUByte())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toUByte())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toUByte())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toUByte()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toUByte())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toUByte()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrUShort(src: String): Result<UShort, ParseIntError> = rustFromStrRadixUShort(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UShort.Companion.fromStrRadix(src: String, radix: UInt): Result<UShort, ParseIntError> = UShort.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UShort.Companion.fromAsciiBytes(src: List<UByte>): Result<UShort, ParseIntError> = UShort.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UShort.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<UShort, ParseIntError> = UShort.fromAsciiBytesRadixImpl(src, radix)

internal fun UShort.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<UShort, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0u.toUShort() > UShort.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<UShort>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.U16)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toUShort())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toUShort())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toUShort())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toUShort())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toUShort())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toUShort()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toUShort())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toUShort()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrUInt(src: String): Result<UInt, ParseIntError> = rustFromStrRadixUInt(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UInt.Companion.fromStrRadix(src: String, radix: UInt): Result<UInt, ParseIntError> = UInt.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UInt.Companion.fromAsciiBytes(src: List<UByte>): Result<UInt, ParseIntError> = UInt.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun UInt.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<UInt, ParseIntError> = UInt.fromAsciiBytesRadixImpl(src, radix)

internal fun UInt.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<UInt, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0u > UInt.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<UInt>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.U32)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, (radix).toUInt())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toUInt())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, (radix).toUInt())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toUInt())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix)
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toUInt()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix)
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toUInt()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrULong(src: String): Result<ULong, ParseIntError> = rustFromStrRadixULong(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun ULong.Companion.fromStrRadix(src: String, radix: UInt): Result<ULong, ParseIntError> = ULong.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun ULong.Companion.fromAsciiBytes(src: List<UByte>): Result<ULong, ParseIntError> = ULong.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun ULong.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<ULong, ParseIntError> = ULong.fromAsciiBytesRadixImpl(src, radix)

internal fun ULong.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<ULong, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = 0uL > ULong.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<ULong>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.U64)) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toULong())
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(x.toULong())
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, radix.toULong())
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(x1.toULong())
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(radix.toULong())
                val x2 =
                    (when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toULong()
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(radix.toULong())
                val x3 =
                    (when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toULong()
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}

/**
 * Parses an integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # See also
 * For parsing numbers in other bases, such as binary or hexadecimal,
 * see [`fromStrRadix`][Self.fromStrRadix].
 *
 * # Examples
 *
 * ```kotlin
 * import std.String.FromStr;
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun fromStrU128(src: String): Result<U128, ParseIntError> = rustFromStrRadixU128(src, 10u)

/**
 * Parses an integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # See also
 * If the string to be parsed is in base 10 (decimal),
 * [fromStr] or [str.parse] can also be used.
 *
 * [fromStr]: #method.fromStr
 * [str.parse]: primitive.str.html#method.parse
 *
 * # Examples
 *
 * ```kotlin
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun U128.Companion.fromStrRadix(src: String, radix: UInt): Result<U128, ParseIntError> = U128.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses an integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun U128.Companion.fromAsciiBytes(src: List<UByte>): Result<U128, ParseIntError> = U128.fromAsciiBytesRadix(src, 10)

/**
 * Parses an integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Panics
 *
 * This function panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 * Trailing space returns error:
 * ```kotlin
 * ```
 */
public fun U128.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<U128, ParseIntError> = U128.fromAsciiBytesRadixImpl(src, radix)

internal fun U128.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<U128, ParseIntError> {
    if (2u > radix || radix > 36u) {
        fromAsciiBytesRadixPanic(radix)
    }
    if (src.isEmpty()) {
        return Result.Err(Pie(kind = Empty))
    }
    val isSignedTy = U128(0x0uL, 0x0uL) > U128.MIN_VALUE
    var (isPositive, digits) =
        when {
            src.size == 1 && (src[0] == 43u || src[0] == 45u) -> {
                return Result.Err(Pie(kind = InvalidDigit))
            }
            src.size >= 1 && src[0] == 43u -> {
                val rest = src.subList(1, src.size)
                Pair(true, rest)
            }
            src.size >= 1 && src[0] == 45u && isSignedTy -> {
                val rest1 = src.subList(1, src.size)
                Pair(false, rest1)
            }
            else -> Pair(true, src)
        }
    var result = 0
    if (canNotOverflow<U128>(radix, isSignedTy, digits, typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))) {
        if (isPositive) {
            while (digits != null) {
                val c = digits[0]
                val rest2 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, U128.fromUInt(radix))
                val x =
                    when (val matchVal = (c.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value = matchVal
                            value
                        }
                    }
                result = result.wrappingAdd(U128.fromLong(x.toLong()))
                digits = rest2
            }
        } else {
            while (digits != null) {
                val c1 = digits[0]
                val rest3 = digits.subList(1, digits.size)
                result = rustWrappingMul(result, U128.fromUInt(radix))
                val x1 =
                    when (val matchVal1 = (c1.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value1 = matchVal1
                            value1
                        }
                    }
                result = result.wrappingSub(U128.fromLong(x1.toLong()))
                digits = rest3
            }
        }
    } else {
        if (isPositive) {
            while (digits != null) {
                val c2 = digits[0]
                val rest4 = digits.subList(1, digits.size)
                val mul = result.checkedMul(U128.fromUInt(radix))
                val x2 =
                    U128.fromLong((when (val matchVal2 = (c2.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value2 = matchVal2
                            value2
                        }
                    }).toLong())
                result = when (mul) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value3 = mul
                        value3
                    }
                }
                result = when (val matchVal3 = rustCheckedAdd(result, x2)) {
                    null -> { return Result.Err(Pie(kind = PosOverflow)) }
                    else -> {
                        val value4 = matchVal3
                        value4
                    }
                }
                digits = rest4
            }
        } else {
            while (digits != null) {
                val c3 = digits[0]
                val rest5 = digits.subList(1, digits.size)
                val mul1 = result.checkedMul(U128.fromUInt(radix))
                val x3 =
                    U128.fromLong((when (val matchVal4 = (c3.toInt().toChar()).rustToDigit(radix)) {
                        null -> { return Result.Err(Pie(kind = InvalidDigit)) }
                        else -> {
                            val value5 = matchVal4
                            value5
                        }
                    }).toLong())
                result = when (mul1) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value6 = mul1
                        value6
                    }
                }
                result = when (val matchVal5 = rustCheckedSub(result, x3)) {
                    null -> { return Result.Err(Pie(kind = NegOverflow)) }
                    else -> {
                        val value7 = matchVal5
                        value7
                    }
                }
                digits = rest5
            }
        }
    }
    return Result.Ok(result)
}
