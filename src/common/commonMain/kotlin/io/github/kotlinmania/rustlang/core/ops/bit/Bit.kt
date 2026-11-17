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
 *     sealed class Answer : Not<Any?, Answer> {
 *         public data object Yes : Answer()
 *         public data object No : Answer()
 *
 *         public fun rustEquals(other: Answer): Boolean = this == other
 *
 *         public open fun not(): Answer {
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
    public fun not(): Output
}

public fun Boolean.not(): Boolean = !this

public fun Int.not(): Int = !this

public fun UByte.not(): UByte = (!this).toUByte()

public fun UShort.not(): UShort = (!this).toUShort()

public fun UInt.not(): UInt = !this

public fun ULong.not(): ULong = !this

public fun U128.not(): U128 = !this

public fun Byte.not(): Byte = (!this).toByte()

public fun Short.not(): Short = (!this).toShort()

public fun Long.not(): Long = !this

public fun I128.not(): I128 = !this

public fun Nothing.not(): Nothing = when (this) {}

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
 *     open class Scalar(public var value: Boolean) : BitAnd<Scalar, Any?, Scalar> {
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
 *     open class BooleanVector(public var value: List<Boolean>) : BitAnd<BooleanVector, Any?, BooleanVector> {
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

public fun Boolean.bitandBoolean(other: Boolean): Output = this.bitand(other)

public fun Int.bitand(rhs: Int): Int = this and rhs

public fun Int.bitandInt(other: Int): Output = this.bitand(other)

public fun UByte.bitand(rhs: UByte): UByte = this and rhs

public fun UByte.bitandUByte(other: UByte): Output = this.bitand(other)

public fun UShort.bitand(rhs: UShort): UShort = this and rhs

public fun UShort.bitandUShort(other: UShort): Output = this.bitand(other)

public fun UInt.bitand(rhs: UInt): UInt = this and rhs

public fun UInt.bitandUInt(other: UInt): Output = this.bitand(other)

public fun ULong.bitand(rhs: ULong): ULong = this and rhs

public fun ULong.bitandULong(other: ULong): Output = this.bitand(other)

public fun U128.bitand(rhs: U128): U128 = this and rhs

public fun U128.bitandU128(other: U128): Output = this.bitand(other)

public fun Byte.bitand(rhs: Byte): Byte = (this.toInt() and rhs.toInt()).toByte()

public fun Byte.bitandByte(other: Byte): Output = this.bitand(other)

public fun Short.bitand(rhs: Short): Short = (this.toInt() and rhs.toInt()).toShort()

public fun Short.bitandShort(other: Short): Output = this.bitand(other)

public fun Long.bitand(rhs: Long): Long = this and rhs

public fun Long.bitandLong(other: Long): Output = this.bitand(other)

public fun I128.bitand(rhs: I128): I128 = this and rhs

public fun I128.bitandI128(other: I128): Output = this.bitand(other)

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
 *     open class Scalar(public var value: Boolean) : BitOr<Scalar, Any?, Scalar> {
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
 *     open class BooleanVector(public var value: List<Boolean>) : BitOr<BooleanVector, Any?, BooleanVector> {
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

public fun Boolean.bitorBoolean(other: Boolean): Output = this.bitor(other)

public fun Int.bitor(rhs: Int): Int = this or rhs

public fun Int.bitorInt(other: Int): Output = this.bitor(other)

public fun UByte.bitor(rhs: UByte): UByte = this or rhs

public fun UByte.bitorUByte(other: UByte): Output = this.bitor(other)

public fun UShort.bitor(rhs: UShort): UShort = this or rhs

public fun UShort.bitorUShort(other: UShort): Output = this.bitor(other)

public fun UInt.bitor(rhs: UInt): UInt = this or rhs

public fun UInt.bitorUInt(other: UInt): Output = this.bitor(other)

public fun ULong.bitor(rhs: ULong): ULong = this or rhs

public fun ULong.bitorULong(other: ULong): Output = this.bitor(other)

public fun U128.bitor(rhs: U128): U128 = this or rhs

public fun U128.bitorU128(other: U128): Output = this.bitor(other)

public fun Byte.bitor(rhs: Byte): Byte = (this.toInt() or rhs.toInt()).toByte()

public fun Byte.bitorByte(other: Byte): Output = this.bitor(other)

public fun Short.bitor(rhs: Short): Short = (this.toInt() or rhs.toInt()).toShort()

public fun Short.bitorShort(other: Short): Output = this.bitor(other)

public fun Long.bitor(rhs: Long): Long = this or rhs

public fun Long.bitorLong(other: Long): Output = this.bitor(other)

public fun I128.bitor(rhs: I128): I128 = this or rhs

public fun I128.bitorI128(other: I128): Output = this.bitor(other)

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
 *     open class Scalar(public var value: Boolean) : BitXor<Scalar, Any?, Scalar> {
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
 *     open class BooleanVector(public var value: List<Boolean>) : BitXor<BooleanVector, Any?, BooleanVector> {
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

public fun Boolean.bitxorBoolean(other: Boolean): Output = this.bitxor(other)

public fun Int.bitxor(other: Int): Int = this xor other

public fun Int.bitxorInt(other: Int): Output = this.bitxor(other)

public fun UByte.bitxor(other: UByte): UByte = this xor other

public fun UByte.bitxorUByte(other: UByte): Output = this.bitxor(other)

public fun UShort.bitxor(other: UShort): UShort = this xor other

public fun UShort.bitxorUShort(other: UShort): Output = this.bitxor(other)

public fun UInt.bitxor(other: UInt): UInt = this xor other

public fun UInt.bitxorUInt(other: UInt): Output = this.bitxor(other)

public fun ULong.bitxor(other: ULong): ULong = this xor other

public fun ULong.bitxorULong(other: ULong): Output = this.bitxor(other)

public fun U128.bitxor(other: U128): U128 = this xor other

public fun U128.bitxorU128(other: U128): Output = this.bitxor(other)

public fun Byte.bitxor(other: Byte): Byte = (this.toInt() xor other.toInt()).toByte()

public fun Byte.bitxorByte(other: Byte): Output = this.bitxor(other)

public fun Short.bitxor(other: Short): Short = (this.toInt() xor other.toInt()).toShort()

public fun Short.bitxorShort(other: Short): Output = this.bitxor(other)

public fun Long.bitxor(other: Long): Long = this xor other

public fun Long.bitxorLong(other: Long): Output = this.bitxor(other)

public fun I128.bitxor(other: I128): I128 = this xor other

public fun I128.bitxorI128(other: I128): Output = this.bitxor(other)

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
 *         public open fun shl(destructuredArg0: Scalar): Scalar {
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
 *         public open fun shl(rhs: Int): SpinVector<T> {
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
    public fun shl(rhs: Rhs): Output
}

public fun UByte.shl(other: UByte): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun UByte.shl(other: UShort): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun UByte.shl(other: UInt): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun UByte.shl(other: ULong): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun UByte.shl(other: U128): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun UByte.shl(other: Int): UByte = (this.toUInt() shl other).toUByte()

public fun UByte.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun UByte.shl(other: Byte): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun UByte.shl(other: Short): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun UByte.shl(other: Long): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun UByte.shl(other: I128): UByte = (this.toUInt() shl (other.toInt())).toUByte()

public fun UByte.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun UShort.shl(other: UByte): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun UShort.shl(other: UShort): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun UShort.shl(other: UInt): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun UShort.shl(other: ULong): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun UShort.shl(other: U128): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun UShort.shl(other: Int): UShort = (this.toUInt() shl other).toUShort()

public fun UShort.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun UShort.shl(other: Byte): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun UShort.shl(other: Short): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun UShort.shl(other: Long): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun UShort.shl(other: I128): UShort = (this.toUInt() shl (other.toInt())).toUShort()

public fun UShort.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun UInt.shl(other: UByte): UInt = this shl other.toInt()

public fun UInt.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun UInt.shl(other: UShort): UInt = this shl other.toInt()

public fun UInt.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun UInt.shl(other: UInt): UInt = this shl other.toInt()

public fun UInt.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun UInt.shl(other: ULong): UInt = this shl other.toInt()

public fun UInt.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun UInt.shl(other: U128): UInt = this shl other.toInt()

public fun UInt.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun UInt.shl(other: Int): UInt = this shl other

public fun UInt.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun UInt.shl(other: Byte): UInt = this shl other.toInt()

public fun UInt.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun UInt.shl(other: Short): UInt = this shl other.toInt()

public fun UInt.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun UInt.shl(other: Long): UInt = this shl other.toInt()

public fun UInt.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun UInt.shl(other: I128): UInt = this shl other.toInt()

public fun UInt.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun ULong.shl(other: UByte): ULong = this shl other.toInt()

public fun ULong.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun ULong.shl(other: UShort): ULong = this shl other.toInt()

public fun ULong.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun ULong.shl(other: UInt): ULong = this shl other.toInt()

public fun ULong.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun ULong.shl(other: ULong): ULong = this shl other.toInt()

public fun ULong.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun ULong.shl(other: U128): ULong = this shl other.toInt()

public fun ULong.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun ULong.shl(other: Int): ULong = this shl other

public fun ULong.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun ULong.shl(other: Byte): ULong = this shl other.toInt()

public fun ULong.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun ULong.shl(other: Short): ULong = this shl other.toInt()

public fun ULong.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun ULong.shl(other: Long): ULong = this shl other.toInt()

public fun ULong.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun ULong.shl(other: I128): ULong = this shl other.toInt()

public fun ULong.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun U128.shl(other: UByte): U128 = this shl other.toInt()

public fun U128.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun U128.shl(other: UShort): U128 = this shl other.toInt()

public fun U128.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun U128.shl(other: UInt): U128 = this shl other.toInt()

public fun U128.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun U128.shl(other: ULong): U128 = this shl other.toInt()

public fun U128.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun U128.shl(other: U128): U128 = this shl other.toInt()

public fun U128.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun U128.shl(other: Int): U128 = this shl other

public fun U128.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun U128.shl(other: Byte): U128 = this shl other.toInt()

public fun U128.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun U128.shl(other: Short): U128 = this shl other.toInt()

public fun U128.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun U128.shl(other: Long): U128 = this shl other.toInt()

public fun U128.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun U128.shl(other: I128): U128 = this shl other.toInt()

public fun U128.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun Int.shl(other: UByte): Int = this shl other.toInt()

public fun Int.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun Int.shl(other: UShort): Int = this shl other.toInt()

public fun Int.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun Int.shl(other: UInt): Int = this shl other.toInt()

public fun Int.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun Int.shl(other: ULong): Int = this shl other.toInt()

public fun Int.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun Int.shl(other: U128): Int = this shl other.toInt()

public fun Int.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun Int.shl(other: Int): Int = this shl other

public fun Int.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun Int.shl(other: Byte): Int = this shl other.toInt()

public fun Int.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun Int.shl(other: Short): Int = this shl other.toInt()

public fun Int.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun Int.shl(other: Long): Int = this shl other.toInt()

public fun Int.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun Int.shl(other: I128): Int = this shl other.toInt()

public fun Int.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun Byte.shl(other: UByte): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun Byte.shl(other: UShort): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun Byte.shl(other: UInt): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun Byte.shl(other: ULong): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun Byte.shl(other: U128): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun Byte.shl(other: Int): Byte = (this shl other).toByte()

public fun Byte.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun Byte.shl(other: Byte): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun Byte.shl(other: Short): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun Byte.shl(other: Long): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun Byte.shl(other: I128): Byte = (this shl other.toInt()).toByte()

public fun Byte.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun Short.shl(other: UByte): Short = (this shl other.toInt()).toShort()

public fun Short.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun Short.shl(other: UShort): Short = (this shl other.toInt()).toShort()

public fun Short.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun Short.shl(other: UInt): Short = (this shl other.toInt()).toShort()

public fun Short.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun Short.shl(other: ULong): Short = (this shl other.toInt()).toShort()

public fun Short.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun Short.shl(other: U128): Short = (this shl other.toInt()).toShort()

public fun Short.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun Short.shl(other: Int): Short = (this shl other).toShort()

public fun Short.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun Short.shl(other: Byte): Short = (this shl other.toInt()).toShort()

public fun Short.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun Short.shl(other: Short): Short = (this shl other.toInt()).toShort()

public fun Short.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun Short.shl(other: Long): Short = (this shl other.toInt()).toShort()

public fun Short.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun Short.shl(other: I128): Short = (this shl other.toInt()).toShort()

public fun Short.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun Long.shl(other: UByte): Long = this shl other.toInt()

public fun Long.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun Long.shl(other: UShort): Long = this shl other.toInt()

public fun Long.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun Long.shl(other: UInt): Long = this shl other.toInt()

public fun Long.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun Long.shl(other: ULong): Long = this shl other.toInt()

public fun Long.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun Long.shl(other: U128): Long = this shl other.toInt()

public fun Long.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun Long.shl(other: Int): Long = this shl other

public fun Long.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun Long.shl(other: Byte): Long = this shl other.toInt()

public fun Long.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun Long.shl(other: Short): Long = this shl other.toInt()

public fun Long.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun Long.shl(other: Long): Long = this shl other.toInt()

public fun Long.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun Long.shl(other: I128): Long = this shl other.toInt()

public fun Long.shlI128(other: I128): Output = Shl.Shl(this, other)

public fun I128.shl(other: UByte): I128 = this shl other.toInt()

public fun I128.shlUByte(other: UByte): Output = Shl.Shl(this, other)

public fun I128.shl(other: UShort): I128 = this shl other.toInt()

public fun I128.shlUShort(other: UShort): Output = Shl.Shl(this, other)

public fun I128.shl(other: UInt): I128 = this shl other.toInt()

public fun I128.shlUInt(other: UInt): Output = Shl.Shl(this, other)

public fun I128.shl(other: ULong): I128 = this shl other.toInt()

public fun I128.shlULong(other: ULong): Output = Shl.Shl(this, other)

public fun I128.shl(other: U128): I128 = this shl other.toInt()

public fun I128.shlU128(other: U128): Output = Shl.Shl(this, other)

public fun I128.shl(other: Int): I128 = this shl other

public fun I128.shlInt(other: Int): Output = Shl.Shl(this, other)

public fun I128.shl(other: Byte): I128 = this shl other.toInt()

public fun I128.shlByte(other: Byte): Output = Shl.Shl(this, other)

public fun I128.shl(other: Short): I128 = this shl other.toInt()

public fun I128.shlShort(other: Short): Output = Shl.Shl(this, other)

public fun I128.shl(other: Long): I128 = this shl other.toInt()

public fun I128.shlLong(other: Long): Output = Shl.Shl(this, other)

public fun I128.shl(other: I128): I128 = this shl other.toInt()

public fun I128.shlI128(other: I128): Output = Shl.Shl(this, other)

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
 *         public open fun shr(destructuredArg0: Scalar): Scalar {
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
 *         public open fun shr(rhs: Int): SpinVector<T> {
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
    public fun shr(rhs: Rhs): Output
}

public fun UByte.shr(other: UByte): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun UByte.shr(other: UShort): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun UByte.shr(other: UInt): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun UByte.shr(other: ULong): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun UByte.shr(other: U128): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun UByte.shr(other: Int): UByte = (this.toUInt() shr other).toUByte()

public fun UByte.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun UByte.shr(other: Byte): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun UByte.shr(other: Short): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun UByte.shr(other: Long): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun UByte.shr(other: I128): UByte = (this.toUInt() shr (other.toInt())).toUByte()

public fun UByte.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun UShort.shr(other: UByte): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun UShort.shr(other: UShort): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun UShort.shr(other: UInt): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun UShort.shr(other: ULong): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun UShort.shr(other: U128): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun UShort.shr(other: Int): UShort = (this.toUInt() shr other).toUShort()

public fun UShort.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun UShort.shr(other: Byte): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun UShort.shr(other: Short): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun UShort.shr(other: Long): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun UShort.shr(other: I128): UShort = (this.toUInt() shr (other.toInt())).toUShort()

public fun UShort.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun UInt.shr(other: UByte): UInt = this shr other.toInt()

public fun UInt.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun UInt.shr(other: UShort): UInt = this shr other.toInt()

public fun UInt.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun UInt.shr(other: UInt): UInt = this shr other.toInt()

public fun UInt.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun UInt.shr(other: ULong): UInt = this shr other.toInt()

public fun UInt.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun UInt.shr(other: U128): UInt = this shr other.toInt()

public fun UInt.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun UInt.shr(other: Int): UInt = this shr other

public fun UInt.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun UInt.shr(other: Byte): UInt = this shr other.toInt()

public fun UInt.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun UInt.shr(other: Short): UInt = this shr other.toInt()

public fun UInt.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun UInt.shr(other: Long): UInt = this shr other.toInt()

public fun UInt.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun UInt.shr(other: I128): UInt = this shr other.toInt()

public fun UInt.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun ULong.shr(other: UByte): ULong = this shr other.toInt()

public fun ULong.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun ULong.shr(other: UShort): ULong = this shr other.toInt()

public fun ULong.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun ULong.shr(other: UInt): ULong = this shr other.toInt()

public fun ULong.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun ULong.shr(other: ULong): ULong = this shr other.toInt()

public fun ULong.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun ULong.shr(other: U128): ULong = this shr other.toInt()

public fun ULong.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun ULong.shr(other: Int): ULong = this shr other

public fun ULong.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun ULong.shr(other: Byte): ULong = this shr other.toInt()

public fun ULong.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun ULong.shr(other: Short): ULong = this shr other.toInt()

public fun ULong.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun ULong.shr(other: Long): ULong = this shr other.toInt()

public fun ULong.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun ULong.shr(other: I128): ULong = this shr other.toInt()

public fun ULong.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun U128.shr(other: UByte): U128 = this shr other.toInt()

public fun U128.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun U128.shr(other: UShort): U128 = this shr other.toInt()

public fun U128.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun U128.shr(other: UInt): U128 = this shr other.toInt()

public fun U128.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun U128.shr(other: ULong): U128 = this shr other.toInt()

public fun U128.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun U128.shr(other: U128): U128 = this shr other.toInt()

public fun U128.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun U128.shr(other: Int): U128 = this shr other

public fun U128.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun U128.shr(other: Byte): U128 = this shr other.toInt()

public fun U128.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun U128.shr(other: Short): U128 = this shr other.toInt()

public fun U128.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun U128.shr(other: Long): U128 = this shr other.toInt()

public fun U128.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun U128.shr(other: I128): U128 = this shr other.toInt()

public fun U128.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun Int.shr(other: UByte): Int = this shr other.toInt()

public fun Int.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun Int.shr(other: UShort): Int = this shr other.toInt()

public fun Int.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun Int.shr(other: UInt): Int = this shr other.toInt()

public fun Int.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun Int.shr(other: ULong): Int = this shr other.toInt()

public fun Int.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun Int.shr(other: U128): Int = this shr other.toInt()

public fun Int.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun Int.shr(other: Int): Int = this shr other

public fun Int.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun Int.shr(other: Byte): Int = this shr other.toInt()

public fun Int.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun Int.shr(other: Short): Int = this shr other.toInt()

public fun Int.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun Int.shr(other: Long): Int = this shr other.toInt()

public fun Int.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun Int.shr(other: I128): Int = this shr other.toInt()

public fun Int.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun Byte.shr(other: UByte): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun Byte.shr(other: UShort): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun Byte.shr(other: UInt): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun Byte.shr(other: ULong): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun Byte.shr(other: U128): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun Byte.shr(other: Int): Byte = (this shr other).toByte()

public fun Byte.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun Byte.shr(other: Byte): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun Byte.shr(other: Short): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun Byte.shr(other: Long): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun Byte.shr(other: I128): Byte = (this shr other.toInt()).toByte()

public fun Byte.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun Short.shr(other: UByte): Short = (this shr other.toInt()).toShort()

public fun Short.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun Short.shr(other: UShort): Short = (this shr other.toInt()).toShort()

public fun Short.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun Short.shr(other: UInt): Short = (this shr other.toInt()).toShort()

public fun Short.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun Short.shr(other: ULong): Short = (this shr other.toInt()).toShort()

public fun Short.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun Short.shr(other: U128): Short = (this shr other.toInt()).toShort()

public fun Short.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun Short.shr(other: Int): Short = (this shr other).toShort()

public fun Short.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun Short.shr(other: Byte): Short = (this shr other.toInt()).toShort()

public fun Short.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun Short.shr(other: Short): Short = (this shr other.toInt()).toShort()

public fun Short.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun Short.shr(other: Long): Short = (this shr other.toInt()).toShort()

public fun Short.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun Short.shr(other: I128): Short = (this shr other.toInt()).toShort()

public fun Short.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun Long.shr(other: UByte): Long = this shr other.toInt()

public fun Long.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun Long.shr(other: UShort): Long = this shr other.toInt()

public fun Long.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun Long.shr(other: UInt): Long = this shr other.toInt()

public fun Long.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun Long.shr(other: ULong): Long = this shr other.toInt()

public fun Long.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun Long.shr(other: U128): Long = this shr other.toInt()

public fun Long.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun Long.shr(other: Int): Long = this shr other

public fun Long.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun Long.shr(other: Byte): Long = this shr other.toInt()

public fun Long.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun Long.shr(other: Short): Long = this shr other.toInt()

public fun Long.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun Long.shr(other: Long): Long = this shr other.toInt()

public fun Long.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun Long.shr(other: I128): Long = this shr other.toInt()

public fun Long.shrI128(other: I128): Output = Shr.Shr(this, other)

public fun I128.shr(other: UByte): I128 = this shr other.toInt()

public fun I128.shrUByte(other: UByte): Output = Shr.Shr(this, other)

public fun I128.shr(other: UShort): I128 = this shr other.toInt()

public fun I128.shrUShort(other: UShort): Output = Shr.Shr(this, other)

public fun I128.shr(other: UInt): I128 = this shr other.toInt()

public fun I128.shrUInt(other: UInt): Output = Shr.Shr(this, other)

public fun I128.shr(other: ULong): I128 = this shr other.toInt()

public fun I128.shrULong(other: ULong): Output = Shr.Shr(this, other)

public fun I128.shr(other: U128): I128 = this shr other.toInt()

public fun I128.shrU128(other: U128): Output = Shr.Shr(this, other)

public fun I128.shr(other: Int): I128 = this shr other

public fun I128.shrInt(other: Int): Output = Shr.Shr(this, other)

public fun I128.shr(other: Byte): I128 = this shr other.toInt()

public fun I128.shrByte(other: Byte): Output = Shr.Shr(this, other)

public fun I128.shr(other: Short): I128 = this shr other.toInt()

public fun I128.shrShort(other: Short): Output = Shr.Shr(this, other)

public fun I128.shr(other: Long): I128 = this shr other.toInt()

public fun I128.shrLong(other: Long): Output = Shr.Shr(this, other)

public fun I128.shr(other: I128): I128 = this shr other.toInt()

public fun I128.shrI128(other: I128): Output = Shr.Shr(this, other)

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
 *         this = Scalar(this.value and rhs.value)
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
 *         check(this.value.size == rhs.value.size)
 *         this = BooleanVector((this.value.zip(rhs.value)).map({ (x, y) -> x and y }).collect())
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
    this = this and other
}

public fun Boolean.bitandAssignBoolean(other: Boolean) {
    this.bitandAssign(other)
}

public fun Int.bitandAssign(other: Int) {
    this = this and other
}

public fun Int.bitandAssignInt(other: Int) {
    this.bitandAssign(other)
}

public fun UByte.bitandAssign(other: UByte) {
    this = this and other
}

public fun UByte.bitandAssignUByte(other: UByte) {
    this.bitandAssign(other)
}

public fun UShort.bitandAssign(other: UShort) {
    this = this and other
}

public fun UShort.bitandAssignUShort(other: UShort) {
    this.bitandAssign(other)
}

public fun UInt.bitandAssign(other: UInt) {
    this = this and other
}

public fun UInt.bitandAssignUInt(other: UInt) {
    this.bitandAssign(other)
}

public fun ULong.bitandAssign(other: ULong) {
    this = this and other
}

public fun ULong.bitandAssignULong(other: ULong) {
    this.bitandAssign(other)
}

public fun U128.bitandAssign(other: U128) {
    this = this and other
}

public fun U128.bitandAssignU128(other: U128) {
    this.bitandAssign(other)
}

public fun Byte.bitandAssign(other: Byte) {
    this = this and other
}

public fun Byte.bitandAssignByte(other: Byte) {
    this.bitandAssign(other)
}

public fun Short.bitandAssign(other: Short) {
    this = this and other
}

public fun Short.bitandAssignShort(other: Short) {
    this.bitandAssign(other)
}

public fun Long.bitandAssign(other: Long) {
    this = this and other
}

public fun Long.bitandAssignLong(other: Long) {
    this.bitandAssign(other)
}

public fun I128.bitandAssign(other: I128) {
    this = this and other
}

public fun I128.bitandAssignI128(other: I128) {
    this.bitandAssign(other)
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
    this = this or other
}

public fun Boolean.bitorAssignBoolean(other: Boolean) {
    this.bitorAssign(other)
}

public fun Int.bitorAssign(other: Int) {
    this = this or other
}

public fun Int.bitorAssignInt(other: Int) {
    this.bitorAssign(other)
}

public fun UByte.bitorAssign(other: UByte) {
    this = this or other
}

public fun UByte.bitorAssignUByte(other: UByte) {
    this.bitorAssign(other)
}

public fun UShort.bitorAssign(other: UShort) {
    this = this or other
}

public fun UShort.bitorAssignUShort(other: UShort) {
    this.bitorAssign(other)
}

public fun UInt.bitorAssign(other: UInt) {
    this = this or other
}

public fun UInt.bitorAssignUInt(other: UInt) {
    this.bitorAssign(other)
}

public fun ULong.bitorAssign(other: ULong) {
    this = this or other
}

public fun ULong.bitorAssignULong(other: ULong) {
    this.bitorAssign(other)
}

public fun U128.bitorAssign(other: U128) {
    this = this or other
}

public fun U128.bitorAssignU128(other: U128) {
    this.bitorAssign(other)
}

public fun Byte.bitorAssign(other: Byte) {
    this = this or other
}

public fun Byte.bitorAssignByte(other: Byte) {
    this.bitorAssign(other)
}

public fun Short.bitorAssign(other: Short) {
    this = this or other
}

public fun Short.bitorAssignShort(other: Short) {
    this.bitorAssign(other)
}

public fun Long.bitorAssign(other: Long) {
    this = this or other
}

public fun Long.bitorAssignLong(other: Long) {
    this.bitorAssign(other)
}

public fun I128.bitorAssign(other: I128) {
    this = this or other
}

public fun I128.bitorAssignI128(other: I128) {
    this.bitorAssign(other)
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
    this = this xor other
}

public fun Boolean.bitxorAssignBoolean(other: Boolean) {
    this.bitxorAssign(other)
}

public fun Int.bitxorAssign(other: Int) {
    this = this xor other
}

public fun Int.bitxorAssignInt(other: Int) {
    this.bitxorAssign(other)
}

public fun UByte.bitxorAssign(other: UByte) {
    this = this xor other
}

public fun UByte.bitxorAssignUByte(other: UByte) {
    this.bitxorAssign(other)
}

public fun UShort.bitxorAssign(other: UShort) {
    this = this xor other
}

public fun UShort.bitxorAssignUShort(other: UShort) {
    this.bitxorAssign(other)
}

public fun UInt.bitxorAssign(other: UInt) {
    this = this xor other
}

public fun UInt.bitxorAssignUInt(other: UInt) {
    this.bitxorAssign(other)
}

public fun ULong.bitxorAssign(other: ULong) {
    this = this xor other
}

public fun ULong.bitxorAssignULong(other: ULong) {
    this.bitxorAssign(other)
}

public fun U128.bitxorAssign(other: U128) {
    this = this xor other
}

public fun U128.bitxorAssignU128(other: U128) {
    this.bitxorAssign(other)
}

public fun Byte.bitxorAssign(other: Byte) {
    this = this xor other
}

public fun Byte.bitxorAssignByte(other: Byte) {
    this.bitxorAssign(other)
}

public fun Short.bitxorAssign(other: Short) {
    this = this xor other
}

public fun Short.bitxorAssignShort(other: Short) {
    this.bitxorAssign(other)
}

public fun Long.bitxorAssign(other: Long) {
    this = this xor other
}

public fun Long.bitxorAssignLong(other: Long) {
    this.bitxorAssign(other)
}

public fun I128.bitxorAssign(other: I128) {
    this = this xor other
}

public fun I128.bitxorAssignI128(other: I128) {
    this.bitxorAssign(other)
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
     * x = x shl 1
     * check(x == 10u.toUByte())
     * var x1: UByte = 1u
     * x1 = x1 shl 1
     * check(x1 == 2u.toUByte())
     * ```
     */
    public fun shlAssign(rhs: Rhs)
}

public fun UByte.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: Int) {
    this = this shl other
}

public fun UByte.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun UByte.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun UByte.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: Int) {
    this = this shl other
}

public fun UShort.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun UShort.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun UShort.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: Int) {
    this = this shl other
}

public fun UInt.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun UInt.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun UInt.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: Int) {
    this = this shl other
}

public fun ULong.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun ULong.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun ULong.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun U128.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun U128.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun U128.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun U128.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun U128.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: Int) {
    this = this shl other
}

public fun U128.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun U128.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun U128.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun U128.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun U128.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun U128.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun Int.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun Int.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun Int.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun Int.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun Int.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: Int) {
    this = this shl other
}

public fun Int.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun Int.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun Int.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun Int.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun Int.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun Int.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: Int) {
    this = this shl other
}

public fun Byte.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun Byte.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun Byte.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun Short.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun Short.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun Short.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun Short.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun Short.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: Int) {
    this = this shl other
}

public fun Short.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun Short.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun Short.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun Short.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun Short.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun Short.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun Long.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun Long.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun Long.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun Long.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun Long.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: Int) {
    this = this shl other
}

public fun Long.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun Long.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun Long.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun Long.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun Long.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun Long.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: UByte) {
    this = this shl other.toInt()
}

public fun I128.shlAssignUByte(other: UByte) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: UShort) {
    this = this shl other.toInt()
}

public fun I128.shlAssignUShort(other: UShort) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: UInt) {
    this = this shl other.toInt()
}

public fun I128.shlAssignUInt(other: UInt) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: ULong) {
    this = this shl other.toInt()
}

public fun I128.shlAssignULong(other: ULong) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: U128) {
    this = this shl other.toInt()
}

public fun I128.shlAssignU128(other: U128) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: Int) {
    this = this shl other
}

public fun I128.shlAssignInt(other: Int) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: Byte) {
    this = this shl other.toInt()
}

public fun I128.shlAssignByte(other: Byte) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: Short) {
    this = this shl other.toInt()
}

public fun I128.shlAssignShort(other: Short) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: Long) {
    this = this shl other.toInt()
}

public fun I128.shlAssignLong(other: Long) {
    ShlAssign.ShlAssign(this, other)
}

public fun I128.shlAssign(other: I128) {
    this = this shl other.toInt()
}

public fun I128.shlAssignI128(other: I128) {
    ShlAssign.ShlAssign(this, other)
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
     * x = x shr 1
     * check(x == 2u.toUByte())
     * var x1: UByte = 2u
     * x1 = x1 shr 1
     * check(x1 == 1u.toUByte())
     * ```
     */
    public fun shrAssign(rhs: Rhs)
}

public fun UByte.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: Int) {
    this = this shr other
}

public fun UByte.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun UByte.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun UByte.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: Int) {
    this = this shr other
}

public fun UShort.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun UShort.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun UShort.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: Int) {
    this = this shr other
}

public fun UInt.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun UInt.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun UInt.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: Int) {
    this = this shr other
}

public fun ULong.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun ULong.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun ULong.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun U128.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun U128.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun U128.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun U128.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun U128.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: Int) {
    this = this shr other
}

public fun U128.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun U128.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun U128.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun U128.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun U128.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun U128.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun Int.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun Int.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun Int.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun Int.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun Int.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: Int) {
    this = this shr other
}

public fun Int.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun Int.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun Int.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun Int.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun Int.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun Int.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: Int) {
    this = this shr other
}

public fun Byte.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun Byte.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun Byte.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun Short.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun Short.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun Short.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun Short.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun Short.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: Int) {
    this = this shr other
}

public fun Short.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun Short.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun Short.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun Short.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun Short.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun Short.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun Long.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun Long.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun Long.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun Long.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun Long.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: Int) {
    this = this shr other
}

public fun Long.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun Long.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun Long.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun Long.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun Long.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun Long.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: UByte) {
    this = this shr other.toInt()
}

public fun I128.shrAssignUByte(other: UByte) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: UShort) {
    this = this shr other.toInt()
}

public fun I128.shrAssignUShort(other: UShort) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: UInt) {
    this = this shr other.toInt()
}

public fun I128.shrAssignUInt(other: UInt) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: ULong) {
    this = this shr other.toInt()
}

public fun I128.shrAssignULong(other: ULong) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: U128) {
    this = this shr other.toInt()
}

public fun I128.shrAssignU128(other: U128) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: Int) {
    this = this shr other
}

public fun I128.shrAssignInt(other: Int) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: Byte) {
    this = this shr other.toInt()
}

public fun I128.shrAssignByte(other: Byte) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: Short) {
    this = this shr other.toInt()
}

public fun I128.shrAssignShort(other: Short) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: Long) {
    this = this shr other.toInt()
}

public fun I128.shrAssignLong(other: Long) {
    ShrAssign.ShrAssign(this, other)
}

public fun I128.shrAssign(other: I128) {
    this = this shr other.toInt()
}

public fun I128.shrAssignI128(other: I128) {
    ShrAssign.ShrAssign(this, other)
}
