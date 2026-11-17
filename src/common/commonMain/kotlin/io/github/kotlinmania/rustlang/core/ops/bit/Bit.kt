// port-lint: source ops/bit.rs
package io.github.kotlinmania.rustlang.core.ops.bit

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128

/**
 * The unary negation operator `!`. It performs either logical or bitwise negation,
 * depending on the type.
 *
 * # Examples
 *
 * An implementation of `Not` for `Answer`, which enables the use of `!` to
 * invert its value.
 *
 * ```kotlin
 * import std.ops.Not
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     sealed class Answer : Not<Answer> {
 *         public data object Yes : Answer()
 *         public data object No : Answer()
 *
 *         public fun rustEquals(other: Answer): Boolean = this == other
 *
 *         private fun kmDiscriminant(): Int =
 *             when (this) {
 *             Yes -> 0
 *             No -> 1
 *             }
 *
 *         public fun toByte(): Byte = kmDiscriminant().toByte()
 *
 *         public fun toUByte(): UByte = kmDiscriminant().toUByte()
 *
 *         public fun toShort(): Short = kmDiscriminant().toShort()
 *
 *         public fun toUShort(): UShort = kmDiscriminant().toUShort()
 *
 *         public fun toInt(): Int = kmDiscriminant()
 *
 *         public fun toUInt(): UInt = kmDiscriminant().toUInt()
 *
 *         public fun toLong(): Long = kmDiscriminant().toLong()
 *
 *         public fun toULong(): ULong = kmDiscriminant().toULong()
 *
 *         public open operator fun not(): Answer {
 *             return when (this) {
 *                 Answer.Yes -> Answer.No
 *                 Answer.No -> Answer.Yes
 *             }
 *         }
 *     }
 *     check(!Answer.Yes == Answer.No)
 *     check(!Answer.No == Answer.Yes)
 * }
 * ```
 */
public interface Not<out Output> {
    /**
     * Performs the unary `!` operation using logical or bitwise negation.
     *
     * # Examples
     *
     * ```kotlin
     * // Logical negation changes between true and false:
     * check(!true == false)
     * check(!false == true)
     * // Bitwise negation changes between 0 and 1 in the binary
     * // representation of integers:
     * check((1u.toUByte()).inv() == 254u.toUByte())
     * check((0u.toUByte()).inv() == 255u.toUByte())
     * // The same examples, represented in binary:
     * check((0x1u.toUByte()).inv() == 0xFEu.toUByte())
     * check((0x0u.toUByte()).inv() == 0xFFu.toUByte())
     * ```
     */
    public operator fun not(): Output
}

public fun Boolean.not(): Boolean = !this

public operator fun Int.not(): Int = this.inv()

public operator fun UByte.not(): UByte = this.inv()

public operator fun UShort.not(): UShort = this.inv()

public operator fun UInt.not(): UInt = this.inv()

public operator fun ULong.not(): ULong = this.inv()

public operator fun U128.not(): U128 = this.inv()

public operator fun Byte.not(): Byte = (this.toInt().inv()).toByte()

public operator fun Short.not(): Short = (this.toInt().inv()).toShort()

public operator fun Long.not(): Long = this.inv()

public operator fun I128.not(): I128 = this.inv()

public operator fun Nothing.not(): Nothing = when (this) {}

/**
 * The bitwise AND operator .
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * An implementation of `BitAnd` for a wrapper around `Boolean`.
 *
 * ```kotlin
 * import std.ops.BitAnd
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Boolean) : BitAnd<Scalar, Scalar> {
 *         public var field0: Boolean
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         // rhs is the "right-hand side" of the expression `a & b`
 *         public open fun bitand(rhs: Scalar): Scalar {
 *             return Scalar(this.value and rhs.value)
 *         }
 *     }
 *     check(Scalar(true) and Scalar(true) == Scalar(true))
 *     check(Scalar(true) and Scalar(false) == Scalar(false))
 *     check(Scalar(false) and Scalar(true) == Scalar(false))
 *     check(Scalar(false) and Scalar(false) == Scalar(false))
 * }
 * ```
 *
 * An implementation of `BitAnd` for a wrapper around `List<Boolean>`.
 *
 * ```kotlin
 * import std.ops.BitAnd
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class BooleanVector(public var value: List<Boolean>) : BitAnd<BooleanVector, BooleanVector> {
 *         public var field0: List<Boolean>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun bitand(destructuredArg0: BooleanVector): BooleanVector {
 *             val rhs = destructuredArg0.value
 *             val lhs = this.value
 *             check(lhs.size == rhs.size)
 *             return BooleanVector((lhs.zip(rhs)).map({ (x, y) -> x and y }).collect())
 *         }
 *     }
 *     val bv1 = BooleanVector(mutableListOf(true, true, false, false))
 *     val bv2 = BooleanVector(mutableListOf(true, false, true, false))
 *     val expected = BooleanVector(mutableListOf(true, false, false, false))
 *     check(bv1 and bv2 == expected)
 * }
 * ```
 */
public interface BitAnd<Rhs, out Output> {
    /**
     * Performs the  operation.
     *
     * # Examples
     *
     * ```kotlin
     * check(true and false == false)
     * check(true and true == true)
     * check(5u.toUByte() and 1u.toUByte() == 1u.toUByte())
     * check(5u.toUByte() and 2u.toUByte() == 0u.toUByte())
     * ```
     */
    public fun bitand(rhs: Rhs): Output
}

public fun Boolean.bitand(rhs: Boolean): Boolean = this and rhs

public fun Int.bitand(rhs: Int): Int = this and rhs

public fun UByte.bitand(rhs: UByte): UByte = this and rhs

public fun UShort.bitand(rhs: UShort): UShort = this and rhs

public fun UInt.bitand(rhs: UInt): UInt = this and rhs

public fun ULong.bitand(rhs: ULong): ULong = this and rhs

public fun U128.bitand(rhs: U128): U128 = this and rhs

public fun Byte.bitand(rhs: Byte): Byte = (this.toInt() and rhs.toInt()).toByte()

public fun Short.bitand(rhs: Short): Short = (this.toInt() and rhs.toInt()).toShort()

public fun Long.bitand(rhs: Long): Long = this and rhs

public fun I128.bitand(rhs: I128): I128 = this and rhs

/**
 * The bitwise OR operator `|`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * An implementation of `BitOr` for a wrapper around `Boolean`.
 *
 * ```kotlin
 * import std.ops.BitOr
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Boolean) : BitOr<Scalar, Scalar> {
 *         public var field0: Boolean
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         // rhs is the "right-hand side" of the expression `a | b`
 *         public open fun bitor(rhs: Scalar): Scalar {
 *             return Scalar(this.value or rhs.value)
 *         }
 *     }
 *     check(Scalar(true) or Scalar(true) == Scalar(true))
 *     check(Scalar(true) or Scalar(false) == Scalar(true))
 *     check(Scalar(false) or Scalar(true) == Scalar(true))
 *     check(Scalar(false) or Scalar(false) == Scalar(false))
 * }
 * ```
 *
 * An implementation of `BitOr` for a wrapper around `List<Boolean>`.
 *
 * ```kotlin
 * import std.ops.BitOr
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class BooleanVector(public var value: List<Boolean>) : BitOr<BooleanVector, BooleanVector> {
 *         public var field0: List<Boolean>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun bitor(destructuredArg0: BooleanVector): BooleanVector {
 *             val rhs = destructuredArg0.value
 *             val lhs = this.value
 *             check(lhs.size == rhs.size)
 *             return BooleanVector((lhs.zip(rhs)).map({ (x, y) -> x or y }).collect())
 *         }
 *     }
 *     val bv1 = BooleanVector(mutableListOf(true, true, false, false))
 *     val bv2 = BooleanVector(mutableListOf(true, false, true, false))
 *     val expected = BooleanVector(mutableListOf(true, true, true, false))
 *     check(bv1 or bv2 == expected)
 * }
 * ```
 */
public interface BitOr<Rhs, out Output> {
    /**
     * Performs the `|` operation.
     *
     * # Examples
     *
     * ```kotlin
     * check(true or false == true)
     * check(false or false == false)
     * check(5u.toUByte() or 1u.toUByte() == 5u.toUByte())
     * check(5u.toUByte() or 2u.toUByte() == 7u.toUByte())
     * ```
     */
    public fun bitor(rhs: Rhs): Output
}

public fun Boolean.bitor(rhs: Boolean): Boolean = this or rhs

public fun Int.bitor(rhs: Int): Int = this or rhs

public fun UByte.bitor(rhs: UByte): UByte = this or rhs

public fun UShort.bitor(rhs: UShort): UShort = this or rhs

public fun UInt.bitor(rhs: UInt): UInt = this or rhs

public fun ULong.bitor(rhs: ULong): ULong = this or rhs

public fun U128.bitor(rhs: U128): U128 = this or rhs

public fun Byte.bitor(rhs: Byte): Byte = (this.toInt() or rhs.toInt()).toByte()

public fun Short.bitor(rhs: Short): Short = (this.toInt() or rhs.toInt()).toShort()

public fun Long.bitor(rhs: Long): Long = this or rhs

public fun I128.bitor(rhs: I128): I128 = this or rhs

/**
 * The bitwise XOR operator `^`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * An implementation of `BitXor` that lifts `^` to a wrapper around `Boolean`.
 *
 * ```kotlin
 * import std.ops.BitXor
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Boolean) : BitXor<Scalar, Scalar> {
 *         public var field0: Boolean
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         // rhs is the "right-hand side" of the expression `a ^ b`
 *         public open fun bitxor(rhs: Scalar): Scalar {
 *             return Scalar(this.value xor rhs.value)
 *         }
 *     }
 *     check(Scalar(true) xor Scalar(true) == Scalar(false))
 *     check(Scalar(true) xor Scalar(false) == Scalar(true))
 *     check(Scalar(false) xor Scalar(true) == Scalar(true))
 *     check(Scalar(false) xor Scalar(false) == Scalar(false))
 * }
 * ```
 *
 * An implementation of `BitXor` trait for a wrapper around `List<Boolean>`.
 *
 * ```kotlin
 * import std.ops.BitXor
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class BooleanVector(public var value: List<Boolean>) : BitXor<BooleanVector, BooleanVector> {
 *         public var field0: List<Boolean>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun bitxor(destructuredArg0: BooleanVector): BooleanVector {
 *             val rhs = destructuredArg0.value
 *             val lhs = this.value
 *             check(lhs.size == rhs.size)
 *             return BooleanVector((lhs.zip(rhs)).map({ (x, y) -> x xor y }).collect())
 *         }
 *     }
 *     val bv1 = BooleanVector(mutableListOf(true, true, false, false))
 *     val bv2 = BooleanVector(mutableListOf(true, false, true, false))
 *     val expected = BooleanVector(mutableListOf(false, true, true, false))
 *     check(bv1 xor bv2 == expected)
 * }
 * ```
 */
public interface BitXor<Rhs, out Output> {
    /**
     * Performs the `^` operation.
     *
     * # Examples
     *
     * ```kotlin
     * check(true xor false == true)
     * check(true xor true == false)
     * check(5u.toUByte() xor 1u.toUByte() == 4u.toUByte())
     * check(5u.toUByte() xor 2u.toUByte() == 7u.toUByte())
     * ```
     */
    public fun bitxor(rhs: Rhs): Output
}

public fun Boolean.bitxor(other: Boolean): Boolean = this xor other

public fun Int.bitxor(other: Int): Int = this xor other

public fun UByte.bitxor(other: UByte): UByte = this xor other

public fun UShort.bitxor(other: UShort): UShort = this xor other

public fun UInt.bitxor(other: UInt): UInt = this xor other

public fun ULong.bitxor(other: ULong): ULong = this xor other

public fun U128.bitxor(other: U128): U128 = this xor other

public fun Byte.bitxor(other: Byte): Byte = (this.toInt() xor other.toInt()).toByte()

public fun Short.bitxor(other: Short): Short = (this.toInt() xor other.toInt()).toShort()

public fun Long.bitxor(other: Long): Long = this xor other

public fun I128.bitxor(other: I128): I128 = this xor other

/**
 * The left shift operator `<<`. Note that because this trait is implemented
 * for all integer types with multiple right-hand-side types, Rust's type
 * checker has special handling for `_<<_`, setting the result type for
 * integer operations to the type of the left-hand-side operand. This means
 * that though `a<<b` and `a.shl(b)` are one and the same from an evaluation
 * standpoint, they are different when it comes to type inference.
 *
 * # Examples
 *
 * An implementation of `Shl` that lifts the `<<` operation on integers to a
 * wrapper around `Int`.
 *
 * ```kotlin
 * import std.ops.Shl
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Int) : Shl<Scalar, Scalar> {
 *         public var field0: Int
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open infix fun shl(destructuredArg0: Scalar): Scalar {
 *             val rhs = destructuredArg0.value
 *             val lhs = this.value
 *             return Scalar(lhs shl rhs)
 *         }
 *     }
 *     check(Scalar(4) shl Scalar(2).toInt() == Scalar(16))
 * }
 * ```
 *
 * An implementation of `Shl` that spins a vector leftward by a given amount.
 *
 * ```kotlin
 * import std.ops.Shl
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class SpinVector<T>(public var vec: List<T>) : Shl<Int, SpinVector<T>> {
 *         public open infix fun shl(rhs: Int): SpinVector<T> {
 *             // Rotate the vector by `rhs` places.
 *             val (a, b) = this.vec.splitAt(rhs)
 *             var spunVector = mutableListOf<T>()
 *             spunVector.addAll(b)
 *             spunVector.addAll(a)
 *             return SpinVector(vec = spunVector)
 *         }
 *     }
 *     check(SpinVector(vec = mutableListOf(0, 1, 2, 3, 4)) shl 2 == SpinVector(vec = mutableListOf(2, 3, 4, 0, 1)))
 * }
 * ```
 */
public interface Shl<Rhs, out Output> {
    /**
     * Performs the `<<` operation.
     *
     * # Examples
     *
     * ```kotlin
     * check(((5u.toUByte()).toUInt() shl 1).toUByte() == 10u.toUByte())
     * check(((1u.toUByte()).toUInt() shl 1).toUByte() == 2u.toUByte())
     * ```
     */
    public infix fun shl(rhs: Rhs): Output
}

public infix fun UByte.shl(other: UByte): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: UShort): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: UInt): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: ULong): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: U128): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: Int): UByte = (this.toUInt() shl other).toUByte()

public infix fun UByte.shl(other: Byte): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: Short): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlInt(other: Int): UByte = (this.toUInt() shl other).toUByte()

public infix fun UByte.shl(other: Long): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UByte.shl(other: I128): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public infix fun UShort.shl(other: UByte): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: UShort): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: UInt): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: ULong): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: U128): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: Int): UShort = (this.toUInt() shl other).toUShort()

public infix fun UShort.shl(other: Byte): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: Short): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlInt(other: Int): UShort = (this.toUInt() shl other).toUShort()

public infix fun UShort.shl(other: Long): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UShort.shl(other: I128): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public infix fun UInt.shl(other: UByte): UInt = this shl other.toInt()

public infix fun UInt.shl(other: UShort): UInt = this shl other.toInt()

public infix fun UInt.shl(other: UInt): UInt = this shl other.toInt()

public infix fun UInt.shl(other: ULong): UInt = this shl other.toInt()

public infix fun UInt.shl(other: U128): UInt = this shl other.toInt()

public fun UInt.shl(other: Int): UInt = this shl other

public infix fun UInt.shl(other: Byte): UInt = this shl other.toInt()

public infix fun UInt.shl(other: Short): UInt = this shl other.toInt()

public fun UInt.shlInt(other: Int): UInt = this shl other

public infix fun UInt.shl(other: Long): UInt = this shl other.toInt()

public infix fun UInt.shl(other: I128): UInt = this shl other.toInt()

public infix fun ULong.shl(other: UByte): ULong = this shl other.toInt()

public infix fun ULong.shl(other: UShort): ULong = this shl other.toInt()

public infix fun ULong.shl(other: UInt): ULong = this shl other.toInt()

public infix fun ULong.shl(other: ULong): ULong = this shl other.toInt()

public infix fun ULong.shl(other: U128): ULong = this shl other.toInt()

public fun ULong.shl(other: Int): ULong = this shl other

public infix fun ULong.shl(other: Byte): ULong = this shl other.toInt()

public infix fun ULong.shl(other: Short): ULong = this shl other.toInt()

public fun ULong.shlInt(other: Int): ULong = this shl other

public infix fun ULong.shl(other: Long): ULong = this shl other.toInt()

public infix fun ULong.shl(other: I128): ULong = this shl other.toInt()

public infix fun U128.shl(other: UByte): U128 = this shl other.toInt()

public infix fun U128.shl(other: UShort): U128 = this shl other.toInt()

public infix fun U128.shl(other: UInt): U128 = this shl other.toInt()

public infix fun U128.shl(other: ULong): U128 = this shl other.toInt()

public infix fun U128.shl(other: U128): U128 = this shl other.toInt()

public infix fun U128.shl(other: Int): U128 = this shl other

public infix fun U128.shl(other: Byte): U128 = this shl other.toInt()

public infix fun U128.shl(other: Short): U128 = this shl other.toInt()

public fun U128.shlInt(other: Int): U128 = this shl other

public infix fun U128.shl(other: Long): U128 = this shl other.toInt()

public infix fun U128.shl(other: I128): U128 = this shl other.toInt()

public infix fun Int.shl(other: UByte): Int = this shl other.toInt()

public infix fun Int.shl(other: UShort): Int = this shl other.toInt()

public infix fun Int.shl(other: UInt): Int = this shl other.toInt()

public infix fun Int.shl(other: ULong): Int = this shl other.toInt()

public infix fun Int.shl(other: U128): Int = this shl other.toInt()

public fun Int.shl(other: Int): Int = this shl other

public infix fun Int.shl(other: Byte): Int = this shl other.toInt()

public infix fun Int.shl(other: Short): Int = this shl other.toInt()

public fun Int.shlInt(other: Int): Int = this shl other

public infix fun Int.shl(other: Long): Int = this shl other.toInt()

public infix fun Int.shl(other: I128): Int = this shl other.toInt()

public infix fun Byte.shl(other: UByte): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: UShort): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: UInt): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: ULong): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: U128): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: Int): Byte = (this.toInt() shl other).toByte()

public infix fun Byte.shl(other: Byte): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: Short): Byte = (this.toInt() shl (other.toInt())).toByte()

public fun Byte.shlInt(other: Int): Byte = (this.toInt() shl other).toByte()

public infix fun Byte.shl(other: Long): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Byte.shl(other: I128): Byte = (this.toInt() shl (other.toInt())).toByte()

public infix fun Short.shl(other: UByte): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: UShort): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: UInt): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: ULong): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: U128): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: Int): Short = (this.toInt() shl other).toShort()

public infix fun Short.shl(other: Byte): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: Short): Short = (this.toInt() shl (other.toInt())).toShort()

public fun Short.shlInt(other: Int): Short = (this.toInt() shl other).toShort()

public infix fun Short.shl(other: Long): Short = (this.toInt() shl (other.toInt())).toShort()

public infix fun Short.shl(other: I128): Short = (this.toInt() shl (other.toInt())).toShort()

public fun Int.shlUByte(other: UByte): Int = this shl other.toInt()

public fun Int.shlUShort(other: UShort): Int = this shl other.toInt()

public fun Int.shlUInt(other: UInt): Int = this shl other.toInt()

public fun Int.shlULong(other: ULong): Int = this shl other.toInt()

public fun Int.shlU128(other: U128): Int = this shl other.toInt()

public fun Int.shlByte(other: Byte): Int = this shl other.toInt()

public fun Int.shlShort(other: Short): Int = this shl other.toInt()

public fun Int.shlLong(other: Long): Int = this shl other.toInt()

public fun Int.shlI128(other: I128): Int = this shl other.toInt()

public infix fun Long.shl(other: UByte): Long = this shl other.toInt()

public infix fun Long.shl(other: UShort): Long = this shl other.toInt()

public infix fun Long.shl(other: UInt): Long = this shl other.toInt()

public infix fun Long.shl(other: ULong): Long = this shl other.toInt()

public infix fun Long.shl(other: U128): Long = this shl other.toInt()

public fun Long.shl(other: Int): Long = this shl other

public infix fun Long.shl(other: Byte): Long = this shl other.toInt()

public infix fun Long.shl(other: Short): Long = this shl other.toInt()

public fun Long.shlInt(other: Int): Long = this shl other

public infix fun Long.shl(other: Long): Long = this shl other.toInt()

public infix fun Long.shl(other: I128): Long = this shl other.toInt()

public infix fun I128.shl(other: UByte): I128 = this shl other.toInt()

public infix fun I128.shl(other: UShort): I128 = this shl other.toInt()

public infix fun I128.shl(other: UInt): I128 = this shl other.toInt()

public infix fun I128.shl(other: ULong): I128 = this shl other.toInt()

public infix fun I128.shl(other: U128): I128 = this shl other.toInt()

public infix fun I128.shl(other: Int): I128 = this shl other

public infix fun I128.shl(other: Byte): I128 = this shl other.toInt()

public infix fun I128.shl(other: Short): I128 = this shl other.toInt()

public fun I128.shlInt(other: Int): I128 = this shl other

public infix fun I128.shl(other: Long): I128 = this shl other.toInt()

public infix fun I128.shl(other: I128): I128 = this shl other.toInt()

/**
 * The right shift operator `>>`. Note that because this trait is implemented
 * for all integer types with multiple right-hand-side types, Rust's type
 * checker has special handling for `_>> _`, setting the result type for
 * integer operations to the type of the left-hand-side operand. This means
 * that though `a>> b` and `a.shr(b)` are one and the same from an evaluation
 * standpoint, they are different when it comes to type inference.
 *
 * # Examples
 *
 * An implementation of `Shr` that lifts the `>>` operation on integers to a
 * wrapper around `Int`.
 *
 * ```kotlin
 * import std.ops.Shr
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Int) : Shr<Scalar, Scalar> {
 *         public var field0: Int
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open infix fun shr(destructuredArg0: Scalar): Scalar {
 *             val rhs = destructuredArg0.value
 *             val lhs = this.value
 *             return Scalar(lhs shr rhs)
 *         }
 *     }
 *     check(Scalar(16) shr Scalar(2).toInt() == Scalar(4))
 * }
 * ```
 *
 * An implementation of `Shr` that spins a vector rightward by a given amount.
 *
 * ```kotlin
 * import std.ops.Shr
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class SpinVector<T>(public var vec: List<T>) : Shr<Int, SpinVector<T>> {
 *         public open infix fun shr(rhs: Int): SpinVector<T> {
 *             // Rotate the vector by `rhs` places.
 *             val (a, b) = this.vec.splitAt(this.vec.size - rhs)
 *             var spunVector = mutableListOf<T>()
 *             spunVector.addAll(b)
 *             spunVector.addAll(a)
 *             return SpinVector(vec = spunVector)
 *         }
 *     }
 *     check(SpinVector(vec = mutableListOf(0, 1, 2, 3, 4)) shr 2 == SpinVector(vec = mutableListOf(3, 4, 0, 1, 2)))
 * }
 * ```
 */
public interface Shr<Rhs, out Output> {
    /**
     * Performs the `>>` operation.
     *
     * # Examples
     *
     * ```kotlin
     * check(((5u.toUByte()).toUInt() shr 1).toUByte() == 2u.toUByte())
     * check(((2u.toUByte()).toUInt() shr 1).toUByte() == 1u.toUByte())
     * ```
     */
    public infix fun shr(rhs: Rhs): Output
}

public infix fun UByte.shr(other: UByte): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: UShort): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: UInt): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: ULong): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: U128): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: Int): UByte = (this.toUInt() shr other).toUByte()

public infix fun UByte.shr(other: Byte): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: Short): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrInt(other: Int): UByte = (this.toUInt() shr other).toUByte()

public infix fun UByte.shr(other: Long): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UByte.shr(other: I128): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public infix fun UShort.shr(other: UByte): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: UShort): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: UInt): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: ULong): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: U128): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: Int): UShort = (this.toUInt() shr other).toUShort()

public infix fun UShort.shr(other: Byte): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: Short): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrInt(other: Int): UShort = (this.toUInt() shr other).toUShort()

public infix fun UShort.shr(other: Long): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UShort.shr(other: I128): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public infix fun UInt.shr(other: UByte): UInt = this shr other.toInt()

public infix fun UInt.shr(other: UShort): UInt = this shr other.toInt()

public infix fun UInt.shr(other: UInt): UInt = this shr other.toInt()

public infix fun UInt.shr(other: ULong): UInt = this shr other.toInt()

public infix fun UInt.shr(other: U128): UInt = this shr other.toInt()

public fun UInt.shr(other: Int): UInt = this shr other

public infix fun UInt.shr(other: Byte): UInt = this shr other.toInt()

public infix fun UInt.shr(other: Short): UInt = this shr other.toInt()

public fun UInt.shrInt(other: Int): UInt = this shr other

public infix fun UInt.shr(other: Long): UInt = this shr other.toInt()

public infix fun UInt.shr(other: I128): UInt = this shr other.toInt()

public infix fun ULong.shr(other: UByte): ULong = this shr other.toInt()

public infix fun ULong.shr(other: UShort): ULong = this shr other.toInt()

public infix fun ULong.shr(other: UInt): ULong = this shr other.toInt()

public infix fun ULong.shr(other: ULong): ULong = this shr other.toInt()

public infix fun ULong.shr(other: U128): ULong = this shr other.toInt()

public fun ULong.shr(other: Int): ULong = this shr other

public infix fun ULong.shr(other: Byte): ULong = this shr other.toInt()

public infix fun ULong.shr(other: Short): ULong = this shr other.toInt()

public fun ULong.shrInt(other: Int): ULong = this shr other

public infix fun ULong.shr(other: Long): ULong = this shr other.toInt()

public infix fun ULong.shr(other: I128): ULong = this shr other.toInt()

public infix fun U128.shr(other: UByte): U128 = this shr other.toInt()

public infix fun U128.shr(other: UShort): U128 = this shr other.toInt()

public infix fun U128.shr(other: UInt): U128 = this shr other.toInt()

public infix fun U128.shr(other: ULong): U128 = this shr other.toInt()

public infix fun U128.shr(other: U128): U128 = this shr other.toInt()

public infix fun U128.shr(other: Int): U128 = this shr other

public infix fun U128.shr(other: Byte): U128 = this shr other.toInt()

public infix fun U128.shr(other: Short): U128 = this shr other.toInt()

public fun U128.shrInt(other: Int): U128 = this shr other

public infix fun U128.shr(other: Long): U128 = this shr other.toInt()

public infix fun U128.shr(other: I128): U128 = this shr other.toInt()

public infix fun Int.shr(other: UByte): Int = this shr other.toInt()

public infix fun Int.shr(other: UShort): Int = this shr other.toInt()

public infix fun Int.shr(other: UInt): Int = this shr other.toInt()

public infix fun Int.shr(other: ULong): Int = this shr other.toInt()

public infix fun Int.shr(other: U128): Int = this shr other.toInt()

public fun Int.shr(other: Int): Int = this shr other

public infix fun Int.shr(other: Byte): Int = this shr other.toInt()

public infix fun Int.shr(other: Short): Int = this shr other.toInt()

public fun Int.shrInt(other: Int): Int = this shr other

public infix fun Int.shr(other: Long): Int = this shr other.toInt()

public infix fun Int.shr(other: I128): Int = this shr other.toInt()

public infix fun Byte.shr(other: UByte): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: UShort): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: UInt): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: ULong): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: U128): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: Int): Byte = (this.toInt() shr other).toByte()

public infix fun Byte.shr(other: Byte): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: Short): Byte = (this.toInt() shr (other.toInt())).toByte()

public fun Byte.shrInt(other: Int): Byte = (this.toInt() shr other).toByte()

public infix fun Byte.shr(other: Long): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Byte.shr(other: I128): Byte = (this.toInt() shr (other.toInt())).toByte()

public infix fun Short.shr(other: UByte): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: UShort): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: UInt): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: ULong): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: U128): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: Int): Short = (this.toInt() shr other).toShort()

public infix fun Short.shr(other: Byte): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: Short): Short = (this.toInt() shr (other.toInt())).toShort()

public fun Short.shrInt(other: Int): Short = (this.toInt() shr other).toShort()

public infix fun Short.shr(other: Long): Short = (this.toInt() shr (other.toInt())).toShort()

public infix fun Short.shr(other: I128): Short = (this.toInt() shr (other.toInt())).toShort()

public fun Int.shrUByte(other: UByte): Int = this shr other.toInt()

public fun Int.shrUShort(other: UShort): Int = this shr other.toInt()

public fun Int.shrUInt(other: UInt): Int = this shr other.toInt()

public fun Int.shrULong(other: ULong): Int = this shr other.toInt()

public fun Int.shrU128(other: U128): Int = this shr other.toInt()

public fun Int.shrByte(other: Byte): Int = this shr other.toInt()

public fun Int.shrShort(other: Short): Int = this shr other.toInt()

public fun Int.shrLong(other: Long): Int = this shr other.toInt()

public fun Int.shrI128(other: I128): Int = this shr other.toInt()

public infix fun Long.shr(other: UByte): Long = this shr other.toInt()

public infix fun Long.shr(other: UShort): Long = this shr other.toInt()

public infix fun Long.shr(other: UInt): Long = this shr other.toInt()

public infix fun Long.shr(other: ULong): Long = this shr other.toInt()

public infix fun Long.shr(other: U128): Long = this shr other.toInt()

public fun Long.shr(other: Int): Long = this shr other

public infix fun Long.shr(other: Byte): Long = this shr other.toInt()

public infix fun Long.shr(other: Short): Long = this shr other.toInt()

public fun Long.shrInt(other: Int): Long = this shr other

public infix fun Long.shr(other: Long): Long = this shr other.toInt()

public infix fun Long.shr(other: I128): Long = this shr other.toInt()

public infix fun I128.shr(other: UByte): I128 = this shr other.toInt()

public infix fun I128.shr(other: UShort): I128 = this shr other.toInt()

public infix fun I128.shr(other: UInt): I128 = this shr other.toInt()

public infix fun I128.shr(other: ULong): I128 = this shr other.toInt()

public infix fun I128.shr(other: U128): I128 = this shr other.toInt()

public infix fun I128.shr(other: Int): I128 = this shr other

public infix fun I128.shr(other: Byte): I128 = this shr other.toInt()

public infix fun I128.shr(other: Short): I128 = this shr other.toInt()

public fun I128.shrInt(other: Int): I128 = this shr other

public infix fun I128.shr(other: Long): I128 = this shr other.toInt()

public infix fun I128.shr(other: I128): I128 = this shr other.toInt()

/**
 * The bitwise AND assignment operator `=`.
 *
 * # Examples
 *
 * An implementation of `BitAndAssign` that lifts the `=` operator to a
 * wrapper around `Boolean`.
 *
 * ```kotlin
 * import std.ops.BitAndAssign
 *
 * open class Scalar(public var value: Boolean) : BitAndAssign<Scalar> {
 *     public var field0: Boolean
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     // rhs is the "right-hand side" of the expression `a & = b`
 *     public open fun bitandAssign(rhs: Scalar) {
 *         var selfMut = this
 *         selfMut = Scalar(selfMut.value and rhs.value)
 *     }
 * }
 * var scalar = Scalar(true)
 * scalar = scalar and Scalar(true)
 * check(scalar == Scalar(true))
 * var scalar1 = Scalar(true)
 * scalar1 = scalar1 and Scalar(false)
 * check(scalar1 == Scalar(false))
 * var scalar2 = Scalar(false)
 * scalar2 = scalar2 and Scalar(true)
 * check(scalar2 == Scalar(false))
 * var scalar3 = Scalar(false)
 * scalar3 = scalar3 and Scalar(false)
 * check(scalar3 == Scalar(false))
 * ```
 *
 * Here, the `BitAndAssign` trait is implemented for a wrapper around
 * `List<Boolean>`.
 *
 * ```kotlin
 * import std.ops.BitAndAssign
 *
 * open class BooleanVector(public var value: List<Boolean>) : BitAndAssign<BooleanVector> {
 *     public var field0: List<Boolean>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     // `rhs` is the "right-hand side" of the expression `a & = b`.
 *     public open fun bitandAssign(rhs: BooleanVector) {
 *         var selfMut = this
 *         check(selfMut.value.size == rhs.value.size)
 *         selfMut = BooleanVector((selfMut.value.zip(rhs.value)).map({ (x, y) -> x and y }).collect())
 *     }
 * }
 * var bv = BooleanVector(mutableListOf(true, true, false, false))
 * bv = bv and BooleanVector(mutableListOf(true, false, true, false))
 * val expected = BooleanVector(mutableListOf(true, false, false, false))
 * check(bv == expected)
 * ```
 */
public interface BitAndAssign<Rhs> {
    /**
     * Performs the `=` operation.
     *
     * # Examples
     *
     * ```kotlin
     * var x = true
     * x = x and false
     * check(x == false)
     * var x1 = true
     * x1 = x1 and true
     * check(x1 == true)
     * var x2: UByte = 5u
     * x2 = x2 and 1u.toUByte()
     * check(x2 == 1u.toUByte())
     * var x3: UByte = 5u
     * x3 = x3 and 2u.toUByte()
     * check(x3 == 0u.toUByte())
     * ```
     */
    public fun bitandAssign(rhs: Rhs)
}

public fun Boolean.bitandAssign(other: Boolean) {
    (this and other).let {}
}

public fun Int.bitandAssign(other: Int) {
    (this and other).let {}
}

public fun UByte.bitandAssign(other: UByte) {
    (this and other).let {}
}

public fun UShort.bitandAssign(other: UShort) {
    (this and other).let {}
}

public fun UInt.bitandAssign(other: UInt) {
    (this and other).let {}
}

public fun ULong.bitandAssign(other: ULong) {
    (this and other).let {}
}

public fun U128.bitandAssign(other: U128) {
    (this and other).let {}
}

public fun Byte.bitandAssign(other: Byte) {
    (this and other).let {}
}

public fun Short.bitandAssign(other: Short) {
    (this and other).let {}
}

public fun Long.bitandAssign(other: Long) {
    (this and other).let {}
}

public fun I128.bitandAssign(other: I128) {
    (this and other).let {}
}

/**
 * The bitwise OR assignment operator `| =`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.BitOrAssign
 *
 * open class PersonalPreferences(public var likesCats: Boolean, public var likesDogs: Boolean) : BitOrAssign<PersonalPreferences> {
 *     public open fun bitorAssign(rhs: PersonalPreferences) {
 *         this.likesCats = this.likesCats or rhs.likesCats
 *         this.likesDogs = this.likesDogs or rhs.likesDogs
 *     }
 * }
 * var prefs = PersonalPreferences(likesCats = true, likesDogs = false)
 * prefs = prefs or PersonalPreferences(likesCats = false, likesDogs = true)
 * check(prefs == PersonalPreferences(likesCats = true, likesDogs = true))
 * ```
 */
public interface BitOrAssign<Rhs> {
    /**
     * Performs the `| =` operation.
     *
     * # Examples
     *
     * ```kotlin
     * var x = true
     * x = x or false
     * check(x == true)
     * var x1 = false
     * x1 = x1 or false
     * check(x1 == false)
     * var x2: UByte = 5u
     * x2 = x2 or 1u.toUByte()
     * check(x2 == 5u.toUByte())
     * var x3: UByte = 5u
     * x3 = x3 or 2u.toUByte()
     * check(x3 == 7u.toUByte())
     * ```
     */
    public fun bitorAssign(rhs: Rhs)
}

public fun Boolean.bitorAssign(other: Boolean) {
    (this or other).let {}
}

public fun Int.bitorAssign(other: Int) {
    (this or other).let {}
}

public fun UByte.bitorAssign(other: UByte) {
    (this or other).let {}
}

public fun UShort.bitorAssign(other: UShort) {
    (this or other).let {}
}

public fun UInt.bitorAssign(other: UInt) {
    (this or other).let {}
}

public fun ULong.bitorAssign(other: ULong) {
    (this or other).let {}
}

public fun U128.bitorAssign(other: U128) {
    (this or other).let {}
}

public fun Byte.bitorAssign(other: Byte) {
    (this or other).let {}
}

public fun Short.bitorAssign(other: Short) {
    (this or other).let {}
}

public fun Long.bitorAssign(other: Long) {
    (this or other).let {}
}

public fun I128.bitorAssign(other: I128) {
    (this or other).let {}
}

/**
 * The bitwise XOR assignment operator `^ =`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.BitXorAssign
 *
 * open class Personality(public var hasSoul: Boolean, public var likesKnitting: Boolean) : BitXorAssign<Personality> {
 *     public open fun bitxorAssign(rhs: Personality) {
 *         this.hasSoul = this.hasSoul xor rhs.hasSoul
 *         this.likesKnitting = this.likesKnitting xor rhs.likesKnitting
 *     }
 * }
 * var personality = Personality(hasSoul = false, likesKnitting = true)
 * personality = personality xor Personality(hasSoul = true, likesKnitting = true)
 * check(personality == Personality(hasSoul = true, likesKnitting = false))
 * ```
 */
public interface BitXorAssign<Rhs> {
    /**
     * Performs the `^ =` operation.
     *
     * # Examples
     *
     * ```kotlin
     * var x = true
     * x = x xor false
     * check(x == true)
     * var x1 = true
     * x1 = x1 xor true
     * check(x1 == false)
     * var x2: UByte = 5u
     * x2 = x2 xor 1u.toUByte()
     * check(x2 == 4u.toUByte())
     * var x3: UByte = 5u
     * x3 = x3 xor 2u.toUByte()
     * check(x3 == 7u.toUByte())
     * ```
     */
    public fun bitxorAssign(rhs: Rhs)
}

public fun Boolean.bitxorAssign(other: Boolean) {
    (this xor other).let {}
}

public fun Int.bitxorAssign(other: Int) {
    (this xor other).let {}
}

public fun UByte.bitxorAssign(other: UByte) {
    (this xor other).let {}
}

public fun UShort.bitxorAssign(other: UShort) {
    (this xor other).let {}
}

public fun UInt.bitxorAssign(other: UInt) {
    (this xor other).let {}
}

public fun ULong.bitxorAssign(other: ULong) {
    (this xor other).let {}
}

public fun U128.bitxorAssign(other: U128) {
    (this xor other).let {}
}

public fun Byte.bitxorAssign(other: Byte) {
    (this xor other).let {}
}

public fun Short.bitxorAssign(other: Short) {
    (this xor other).let {}
}

public fun Long.bitxorAssign(other: Long) {
    (this xor other).let {}
}

public fun I128.bitxorAssign(other: I128) {
    (this xor other).let {}
}

/**
 * The left shift assignment operator `< <=`.
 *
 * # Examples
 *
 * An implementation of `ShlAssign` for a wrapper around `Int`.
 *
 * ```kotlin
 * import std.ops.ShlAssign
 *
 * open class Scalar(public var value: Int) : ShlAssign<Int> {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun shlAssign(rhs: Int) {
 *         this.value = this.value shl rhs
 *     }
 * }
 * var scalar = Scalar(4)
 * scalar = scalar shl 2
 * check(scalar == Scalar(16))
 * ```
 */
public interface ShlAssign<Rhs> {
    /**
     * Performs the `< <=` operation.
     *
     * # Examples
     *
     * ```kotlin
     * var x: UByte = 5u
     * x = ((x).toUInt() shl 1).toUByte()
     * check(x == 10u.toUByte())
     * var x1: UByte = 1u
     * x1 = ((x1).toUInt() shl 1).toUByte()
     * check(x1 == 2u.toUByte())
     * ```
     */
    public fun shlAssign(rhs: Rhs)
}

public fun UByte.shlAssign(other: UByte) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: UShort) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: UInt) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: ULong) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: U128) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: Int) {
    (((this).toUInt() shl other).toUByte()).let {}
}

public fun UByte.shlAssign(other: Byte) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: Short) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssignInt(other: Int) {
    (((this).toUInt() shl other).toUByte()).let {}
}

public fun UByte.shlAssign(other: Long) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UByte.shlAssign(other: I128) {
    (((this).toUInt() shl other.toInt()).toUByte()).let {}
}

public fun UShort.shlAssign(other: UByte) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: UShort) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: UInt) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: ULong) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: U128) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: Int) {
    (((this).toUInt() shl other).toUShort()).let {}
}

public fun UShort.shlAssign(other: Byte) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: Short) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssignInt(other: Int) {
    (((this).toUInt() shl other).toUShort()).let {}
}

public fun UShort.shlAssign(other: Long) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UShort.shlAssign(other: I128) {
    (((this).toUInt() shl other.toInt()).toUShort()).let {}
}

public fun UInt.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun UInt.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun UInt.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun UInt.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun ULong.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun ULong.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun ULong.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun U128.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun U128.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun U128.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun Int.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun Int.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

public fun Byte.shlAssign(other: UByte) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: UShort) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: UInt) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: ULong) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: U128) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: Int) {
    (((this).toInt() shl other).toByte()).let {}
}

public fun Byte.shlAssign(other: Byte) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: Short) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssignInt(other: Int) {
    (((this).toInt() shl other).toByte()).let {}
}

public fun Byte.shlAssign(other: Long) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Byte.shlAssign(other: I128) {
    (((this).toInt() shl other.toInt()).toByte()).let {}
}

public fun Short.shlAssign(other: UByte) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: UShort) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: UInt) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: ULong) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: U128) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: Int) {
    (((this).toInt() shl other).toShort()).let {}
}

public fun Short.shlAssign(other: Byte) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: Short) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssignInt(other: Int) {
    (((this).toInt() shl other).toShort()).let {}
}

public fun Short.shlAssign(other: Long) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Short.shlAssign(other: I128) {
    (((this).toInt() shl other.toInt()).toShort()).let {}
}

public fun Int.shlAssignUByte(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignUShort(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignUInt(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignULong(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignU128(other: U128) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignByte(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignShort(other: Short) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignLong(other: Long) {
    (this shl other.toInt()).let {}
}

public fun Int.shlAssignI128(other: I128) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun Long.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun Long.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun Long.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: UByte) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: UShort) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: UInt) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: ULong) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: U128) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: Int) {
    (this shl other).let {}
}

public fun I128.shlAssign(other: Byte) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: Short) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssignInt(other: Int) {
    (this shl other).let {}
}

public fun I128.shlAssign(other: Long) {
    (this shl other.toInt()).let {}
}

public fun I128.shlAssign(other: I128) {
    (this shl other.toInt()).let {}
}

/**
 * The right shift assignment operator `> >=`.
 *
 * # Examples
 *
 * An implementation of `ShrAssign` for a wrapper around `Int`.
 *
 * ```kotlin
 * import std.ops.ShrAssign
 *
 * open class Scalar(public var value: Int) : ShrAssign<Int> {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun shrAssign(rhs: Int) {
 *         this.value = this.value shr rhs
 *     }
 * }
 * var scalar = Scalar(16)
 * scalar = scalar shr 2
 * check(scalar == Scalar(4))
 * ```
 */
public interface ShrAssign<Rhs> {
    /**
     * Performs the `> >=` operation.
     *
     * # Examples
     *
     * ```kotlin
     * var x: UByte = 5u
     * x = ((x).toUInt() shr 1).toUByte()
     * check(x == 2u.toUByte())
     * var x1: UByte = 2u
     * x1 = ((x1).toUInt() shr 1).toUByte()
     * check(x1 == 1u.toUByte())
     * ```
     */
    public fun shrAssign(rhs: Rhs)
}

public fun UByte.shrAssign(other: UByte) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: UShort) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: UInt) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: ULong) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: U128) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: Int) {
    (((this).toUInt() shr other).toUByte()).let {}
}

public fun UByte.shrAssign(other: Byte) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: Short) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssignInt(other: Int) {
    (((this).toUInt() shr other).toUByte()).let {}
}

public fun UByte.shrAssign(other: Long) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UByte.shrAssign(other: I128) {
    (((this).toUInt() shr other.toInt()).toUByte()).let {}
}

public fun UShort.shrAssign(other: UByte) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: UShort) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: UInt) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: ULong) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: U128) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: Int) {
    (((this).toUInt() shr other).toUShort()).let {}
}

public fun UShort.shrAssign(other: Byte) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: Short) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssignInt(other: Int) {
    (((this).toUInt() shr other).toUShort()).let {}
}

public fun UShort.shrAssign(other: Long) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UShort.shrAssign(other: I128) {
    (((this).toUInt() shr other.toInt()).toUShort()).let {}
}

public fun UInt.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun UInt.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun UInt.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun UInt.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun ULong.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun ULong.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun ULong.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun U128.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun U128.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun U128.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun Int.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun Int.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}

public fun Byte.shrAssign(other: UByte) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: UShort) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: UInt) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: ULong) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: U128) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: Int) {
    (((this).toInt() shr other).toByte()).let {}
}

public fun Byte.shrAssign(other: Byte) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: Short) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssignInt(other: Int) {
    (((this).toInt() shr other).toByte()).let {}
}

public fun Byte.shrAssign(other: Long) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Byte.shrAssign(other: I128) {
    (((this).toInt() shr other.toInt()).toByte()).let {}
}

public fun Short.shrAssign(other: UByte) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: UShort) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: UInt) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: ULong) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: U128) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: Int) {
    (((this).toInt() shr other).toShort()).let {}
}

public fun Short.shrAssign(other: Byte) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: Short) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssignInt(other: Int) {
    (((this).toInt() shr other).toShort()).let {}
}

public fun Short.shrAssign(other: Long) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Short.shrAssign(other: I128) {
    (((this).toInt() shr other.toInt()).toShort()).let {}
}

public fun Int.shrAssignUByte(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignUShort(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignUInt(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignULong(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignU128(other: U128) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignByte(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignShort(other: Short) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignLong(other: Long) {
    (this shr other.toInt()).let {}
}

public fun Int.shrAssignI128(other: I128) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun Long.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun Long.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun Long.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: UByte) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: UShort) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: UInt) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: ULong) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: U128) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: Int) {
    (this shr other).let {}
}

public fun I128.shrAssign(other: Byte) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: Short) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssignInt(other: Int) {
    (this shr other).let {}
}

public fun I128.shrAssign(other: Long) {
    (this shr other.toInt()).let {}
}

public fun I128.shrAssign(other: I128) {
    (this shr other.toInt()).let {}
}
