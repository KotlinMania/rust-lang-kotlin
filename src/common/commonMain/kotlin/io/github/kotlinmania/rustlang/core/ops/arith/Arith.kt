// port-lint: source ops/arith.rs
package io.github.kotlinmania.rustlang.core.ops.arith

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128

/**
 * The addition operator `+`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory. For
 * example, [std.time.SystemTime] implements `Add<Duration>`, which permits
 * operations of the form `SystemTime = SystemTime + Duration`.
 *
 * [std.time.SystemTime]: ../../std/time/struct.SystemTime.html
 *
 * # Examples
 *
 * ## `Add`able points
 *
 * ```kotlin
 * import std.ops.Add
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Point(public var x: Int, public var y: Int) : Add<Point, Any?, Point> {
 *         public open fun clone(): Point = Point(x, y)
 *
 *         public open fun add(other: Point): Point {
 *             return Point(x = this.x + other.x, y = this.y + other.y)
 *         }
 *     }
 *     check(Point(x = 1, y = 0) + Point(x = 2, y = 3) == Point(x = 3, y = 3))
 * }
 * ```
 *
 * ## Implementing `Add` with generics
 *
 * Here is an example of the same `Point` struct implementing the `Add` trait
 * using generics.
 *
 * ```kotlin
 * import std.ops.Add
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Point<T>(public var x: T, public var y: T) : Add<Point<T>, Any?, Point<T>> {
 *         public open fun clone(): Point<T> = Point(x, y)
 *
 *         // Notice that the implementation uses the associated type `Output`.
 *         public open fun add(other: Point<T>): Point<T> {
 *             return Point(x = this.x + other.x, y = this.y + other.y)
 *         }
 *     }
 *     check(Point(x = 1, y = 0) + Point(x = 2, y = 3) == Point(x = 3, y = 3))
 * }
 * ```
 */
public interface Add<Rhs, out Output> {
    /**
     * Performs the `+` operation.
     *
     * # Example
     *
     * ```kotlin
     * check(12 + 1 == 13)
     * ```
     */
    public fun add(rhs: Rhs): Output
}

public fun Int.add(other: Int): Int = this + other

public fun Int.addInt(other: Int): Output = Add.Add(this, other)

public fun UByte.add(other: UByte): UByte = (this + other).toUByte()

public fun UByte.addUByte(other: UByte): Output = Add.Add(this, other)

public fun UShort.add(other: UShort): UShort = (this + other).toUShort()

public fun UShort.addUShort(other: UShort): Output = Add.Add(this, other)

public fun UInt.add(other: UInt): UInt = this + other

public fun UInt.addUInt(other: UInt): Output = Add.Add(this, other)

public fun ULong.add(other: ULong): ULong = this + other

public fun ULong.addULong(other: ULong): Output = Add.Add(this, other)

public fun U128.add(other: U128): U128 = this + other

public fun U128.addU128(other: U128): Output = Add.Add(this, other)

public fun Byte.add(other: Byte): Byte = (this + other).toByte()

public fun Byte.addByte(other: Byte): Output = Add.Add(this, other)

public fun Short.add(other: Short): Short = (this + other).toShort()

public fun Short.addShort(other: Short): Output = Add.Add(this, other)

public fun Long.add(other: Long): Long = this + other

public fun Long.addLong(other: Long): Output = Add.Add(this, other)

public fun I128.add(other: I128): I128 = this + other

public fun I128.addI128(other: I128): Output = Add.Add(this, other)

public fun F16.add(other: F16): F16 = this + other

public fun F16.addF16(other: F16): Output = Add.Add(this, other)

public fun kotlin.Float.add(other: kotlin.Float): kotlin.Float = this + other

public fun kotlin.Float.addFloat(other: kotlin.Float): Output = Add.Add(this, other)

public fun Double.add(other: Double): Double = this + other

public fun Double.addDouble(other: Double): Output = Add.Add(this, other)

public fun F128.add(other: F128): F128 = this + other

public fun F128.addF128(other: F128): Output = Add.Add(this, other)

/**
 * The subtraction operator `-`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory. For
 * example, [std.time.SystemTime] implements `Sub<Duration>`, which permits
 * operations of the form `SystemTime = SystemTime - Duration`.
 *
 * [std.time.SystemTime]: ../../std/time/struct.SystemTime.html
 *
 * # Examples
 *
 * ## `Sub`tractable points
 *
 * ```kotlin
 * import std.ops.Sub
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Point(public var x: Int, public var y: Int) : Sub<Point, Any?, Point> {
 *         public open fun clone(): Point = Point(x, y)
 *
 *         public open fun sub(other: Point): Point {
 *             return Point(x = this.x - other.x, y = this.y - other.y)
 *         }
 *     }
 *     check(Point(x = 3, y = 3) - Point(x = 2, y = 3) == Point(x = 1, y = 0))
 * }
 * ```
 *
 * ## Implementing `Sub` with generics
 *
 * Here is an example of the same `Point` struct implementing the `Sub` trait
 * using generics.
 *
 * ```kotlin
 * import std.ops.Sub
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Point<T>(public var x: T, public var y: T) : Sub<Point<T>, Any?, Point<T>> {
 *         // Notice that the implementation uses the associated type `Output`.
 *         public open fun sub(other: Point<T>): Point<T> {
 *             return Point(x = this.x - other.x, y = this.y - other.y)
 *         }
 *     }
 *     check(Point(x = 2, y = 3) - Point(x = 1, y = 0) == Point(x = 1, y = 3))
 * }
 * ```
 */
public interface Sub<Rhs, out Output> {
    /**
     * Performs the `-` operation.
     *
     * # Example
     *
     * ```kotlin
     * check(12 - 1 == 11)
     * ```
     */
    public fun sub(rhs: Rhs): Output
}

public fun Int.sub(other: Int): Int = this - other

public fun Int.subInt(other: Int): Output = Sub.Sub(this, other)

public fun UByte.sub(other: UByte): UByte = (this - other).toUByte()

public fun UByte.subUByte(other: UByte): Output = Sub.Sub(this, other)

public fun UShort.sub(other: UShort): UShort = (this - other).toUShort()

public fun UShort.subUShort(other: UShort): Output = Sub.Sub(this, other)

public fun UInt.sub(other: UInt): UInt = this - other

public fun UInt.subUInt(other: UInt): Output = Sub.Sub(this, other)

public fun ULong.sub(other: ULong): ULong = this - other

public fun ULong.subULong(other: ULong): Output = Sub.Sub(this, other)

public fun U128.sub(other: U128): U128 = this - other

public fun U128.subU128(other: U128): Output = Sub.Sub(this, other)

public fun Byte.sub(other: Byte): Byte = (this - other).toByte()

public fun Byte.subByte(other: Byte): Output = Sub.Sub(this, other)

public fun Short.sub(other: Short): Short = (this - other).toShort()

public fun Short.subShort(other: Short): Output = Sub.Sub(this, other)

public fun Long.sub(other: Long): Long = this - other

public fun Long.subLong(other: Long): Output = Sub.Sub(this, other)

public fun I128.sub(other: I128): I128 = this - other

public fun I128.subI128(other: I128): Output = Sub.Sub(this, other)

public fun F16.sub(other: F16): F16 = this - other

public fun F16.subF16(other: F16): Output = Sub.Sub(this, other)

public fun kotlin.Float.sub(other: kotlin.Float): kotlin.Float = this - other

public fun kotlin.Float.subFloat(other: kotlin.Float): Output = Sub.Sub(this, other)

public fun Double.sub(other: Double): Double = this - other

public fun Double.subDouble(other: Double): Output = Sub.Sub(this, other)

public fun F128.sub(other: F128): F128 = this - other

public fun F128.subF128(other: F128): Output = Sub.Sub(this, other)

/**
 * The multiplication operator `*`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * ## `Mul`tipliable rational numbers
 *
 * ```kotlin
 * import std.ops.Mul
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     // Euclid's two-thousand-year-old algorithm for finding the greatest common
 *     // divisor.
 *     fun gcd(x: Int, y: Int): Int {
 *         var x = x
 *         var y = y
 *         var x1 = x
 *         var y1 = y
 *         while (y1 != 0) {
 *             val t = y1
 *             y1 = x1 % y1
 *             x1 = t
 *         }
 *         return x1
 *     }
 *     // By the fundamental theorem of arithmetic, rational numbers in lowest
 *     // terms are unique. So, by keeping `Rational`s in reduced form, we can
 *     // derive `Eq` and `PartialEq`.
 *     open class Rational(public var numerator: Int, public var denominator: Int) : Mul<Rational, Any?, Rational> {
 *         public open fun new(numerator: Int, denominator: Int): Rational {
 *             if (denominator == 0) {
 *                 error("Zero is an invalid denominator!")
 *             }
 *             // Reduce to lowest terms by dividing by the greatest common
 *             // divisor.
 *             val gcd = gcd(numerator, denominator)
 *             return Rational(numerator = numerator / gcd, denominator = denominator / gcd)
 *         }
 *
 *         public open fun mul(rhs: Rational): Rational {
 *             val numerator = this.numerator * rhs.numerator
 *             val denominator = this.denominator * rhs.denominator
 *             return Rational.new(numerator, denominator)
 *         }
 *     }
 *     check(Rational.new(1, 2) == Rational.new(2, 4))
 *     check(Rational.new(2, 3) * Rational.new(3, 4) == Rational.new(1, 2))
 * }
 * ```
 *
 * ## Multiplying vectors by scalars as in linear algebra
 *
 * ```kotlin
 * import std.ops.Mul
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Int)
 *     open class Vector(public var value: List<Int>) : Mul<Scalar, Vector> {
 *         public open fun mul(rhs: Scalar): Vector {
 *             return Vector(value = this.value.map({ v -> v * rhs.value }).toMutableList())
 *         }
 *     }
 *     val vector = Vector(value = mutableListOf(2, 4, 6))
 *     val scalar = Scalar(value = 3)
 *     check(vector * scalar == Vector(value = mutableListOf(6, 12, 18)))
 * }
 * ```
 */
public interface Mul<Rhs, out Output> {
    /**
     * Performs the `*` operation.
     *
     * # Example
     *
     * ```kotlin
     * check(12 * 2 == 24)
     * ```
     */
    public fun mul(rhs: Rhs): Output
}

public fun Int.mul(other: Int): Int = this * other

public fun Int.mulInt(other: Int): Output = Mul.Mul(this, other)

public fun UByte.mul(other: UByte): UByte = (this * other).toUByte()

public fun UByte.mulUByte(other: UByte): Output = Mul.Mul(this, other)

public fun UShort.mul(other: UShort): UShort = (this * other).toUShort()

public fun UShort.mulUShort(other: UShort): Output = Mul.Mul(this, other)

public fun UInt.mul(other: UInt): UInt = this * other

public fun UInt.mulUInt(other: UInt): Output = Mul.Mul(this, other)

public fun ULong.mul(other: ULong): ULong = this * other

public fun ULong.mulULong(other: ULong): Output = Mul.Mul(this, other)

public fun U128.mul(other: U128): U128 = this * other

public fun U128.mulU128(other: U128): Output = Mul.Mul(this, other)

public fun Byte.mul(other: Byte): Byte = (this * other).toByte()

public fun Byte.mulByte(other: Byte): Output = Mul.Mul(this, other)

public fun Short.mul(other: Short): Short = (this * other).toShort()

public fun Short.mulShort(other: Short): Output = Mul.Mul(this, other)

public fun Long.mul(other: Long): Long = this * other

public fun Long.mulLong(other: Long): Output = Mul.Mul(this, other)

public fun I128.mul(other: I128): I128 = this * other

public fun I128.mulI128(other: I128): Output = Mul.Mul(this, other)

public fun F16.mul(other: F16): F16 = this * other

public fun F16.mulF16(other: F16): Output = Mul.Mul(this, other)

public fun kotlin.Float.mul(other: kotlin.Float): kotlin.Float = this * other

public fun kotlin.Float.mulFloat(other: kotlin.Float): Output = Mul.Mul(this, other)

public fun Double.mul(other: Double): Double = this * other

public fun Double.mulDouble(other: Double): Output = Mul.Mul(this, other)

public fun F128.mul(other: F128): F128 = this * other

public fun F128.mulF128(other: F128): Output = Mul.Mul(this, other)

/**
 * The division operator `/`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * ## `Div`idable rational numbers
 *
 * ```kotlin
 * import std.ops.Div
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     // Euclid's two-thousand-year-old algorithm for finding the greatest common
 *     // divisor.
 *     fun gcd(x: Int, y: Int): Int {
 *         var x = x
 *         var y = y
 *         var x1 = x
 *         var y1 = y
 *         while (y1 != 0) {
 *             val t = y1
 *             y1 = x1 % y1
 *             x1 = t
 *         }
 *         return x1
 *     }
 *     // By the fundamental theorem of arithmetic, rational numbers in lowest
 *     // terms are unique. So, by keeping `Rational`s in reduced form, we can
 *     // derive `Eq` and `PartialEq`.
 *     open class Rational(public var numerator: Int, public var denominator: Int) : Div<Rational, Any?, Rational> {
 *         public open fun new(numerator: Int, denominator: Int): Rational {
 *             if (denominator == 0) {
 *                 error("Zero is an invalid denominator!")
 *             }
 *             // Reduce to lowest terms by dividing by the greatest common
 *             // divisor.
 *             val gcd = gcd(numerator, denominator)
 *             return Rational(numerator = numerator / gcd, denominator = denominator / gcd)
 *         }
 *
 *         public open fun div(rhs: Rational): Rational {
 *             if (rhs.numerator == 0) {
 *                 error("Cannot divide by zero-valued `Rational`!")
 *             }
 *             val numerator = this.numerator * rhs.denominator
 *             val denominator = this.denominator * rhs.numerator
 *             return Rational.new(numerator, denominator)
 *         }
 *     }
 *     check(Rational.new(1, 2) == Rational.new(2, 4))
 *     check(Rational.new(1, 2) / Rational.new(3, 4) == Rational.new(2, 3))
 * }
 * ```
 *
 * ## Dividing vectors by scalars as in linear algebra
 *
 * ```kotlin
 * import std.ops.Div
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class Scalar(public var value: Float)
 *     open class Vector(public var value: List<Float>) : Div<Scalar, Vector> {
 *         public open fun div(rhs: Scalar): Vector {
 *             return Vector(value = this.value.map({ v -> v / rhs.value }).toMutableList())
 *         }
 *     }
 *     val scalar = Scalar(value = 2.0f)
 *     val vector = Vector(value = mutableListOf(2.0f, 4.0f, 6.0f))
 *     check(vector / scalar == Vector(value = mutableListOf(1.0f, 2.0f, 3.0f)))
 * }
 * ```
 */
public interface Div<Rhs, out Output> {
    /**
     * Performs the `/` operation.
     *
     * # Example
     *
     * ```kotlin
     * check(12 / 2 == 6)
     * ```
     */
    public fun div(rhs: Rhs): Output
}

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun Int.div(other: Int): Int = this / other

public fun Int.divInt(other: Int): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UByte.div(other: UByte): UByte = (this / other).toUByte()

public fun UByte.divUByte(other: UByte): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UShort.div(other: UShort): UShort = (this / other).toUShort()

public fun UShort.divUShort(other: UShort): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UInt.div(other: UInt): UInt = this / other

public fun UInt.divUInt(other: UInt): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun ULong.div(other: ULong): ULong = this / other

public fun ULong.divULong(other: ULong): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun U128.div(other: U128): U128 = this / other

public fun U128.divU128(other: U128): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public fun Byte.div(other: Byte): Byte = (this / other).toByte()

public fun Byte.divByte(other: Byte): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public fun Short.div(other: Short): Short = (this / other).toShort()

public fun Short.divShort(other: Short): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public fun Long.div(other: Long): Long = this / other

public fun Long.divLong(other: Long): Output = Div.Div(this, other)

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public fun I128.div(other: I128): I128 = this / other

public fun I128.divI128(other: I128): Output = Div.Div(this, other)

public fun F16.div(other: F16): F16 = this / other

public fun F16.divF16(other: F16): Output = Div.Div(this, other)

public fun kotlin.Float.div(other: kotlin.Float): kotlin.Float = this / other

public fun kotlin.Float.divFloat(other: kotlin.Float): Output = Div.Div(this, other)

public fun Double.div(other: Double): Double = this / other

public fun Double.divDouble(other: Double): Output = Div.Div(this, other)

public fun F128.div(other: F128): F128 = this / other

public fun F128.divF128(other: F128): Output = Div.Div(this, other)

/**
 * The remainder operator `%`.
 *
 * Note that `Rhs` is `Self` by default, but this is not mandatory.
 *
 * # Examples
 *
 * This example implements `Rem` on a `SplitSlice` object. After `Rem` is
 * implemented, one can use the `%` operator to find out what the remaining
 * elements of the slice would be after splitting it into equal slices of a
 * given length.
 *
 * ```kotlin
 * import std.ops.Rem
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     open class SplitSlice<T>(public var slice: List<T>) : Rem<Int, SplitSlice<T>> {
 *         public open fun rem(modulus: Int): SplitSlice<T> {
 *             val len = this.slice.size
 *             val rem = len % modulus
 *             val start = len - rem
 *             return SplitSlice(slice = ((this.slice).subList(start, (this.slice).size)))
 *         }
 *     }
 *     // If we were to divide &[0, 1, 2, 3, 4, 5, 6, 7] into slices of size 3,
 *     // the remainder would be &[6, 7].
 *     check(SplitSlice(slice = listOf(0, 1, 2, 3, 4, 5, 6, 7)) % 3 == SplitSlice(slice = listOf(6, 7)))
 * }
 * ```
 */
public interface Rem<Rhs, out Output> {
    /**
     * Performs the `%` operation.
     *
     * # Example
     *
     * ```kotlin
     * check(12 % 10 == 2)
     * ```
     */
    public fun rem(rhs: Rhs): Output
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun Int.rem(other: Int): Int = this % other

public fun Int.remInt(other: Int): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UByte.rem(other: UByte): UByte = (this % other).toUByte()

public fun UByte.remUByte(other: UByte): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UShort.rem(other: UShort): UShort = (this % other).toUShort()

public fun UShort.remUShort(other: UShort): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UInt.rem(other: UInt): UInt = this % other

public fun UInt.remUInt(other: UInt): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun ULong.rem(other: ULong): ULong = this % other

public fun ULong.remULong(other: ULong): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun U128.rem(other: U128): U128 = this % other

public fun U128.remU128(other: U128): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public fun Byte.rem(other: Byte): Byte = (this % other).toByte()

public fun Byte.remByte(other: Byte): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public fun Short.rem(other: Short): Short = (this % other).toShort()

public fun Short.remShort(other: Short): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public fun Long.rem(other: Long): Long = this % other

public fun Long.remLong(other: Long): Output = Rem.Rem(this, other)

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public fun I128.rem(other: I128): I128 = this % other

public fun I128.remI128(other: I128): Output = Rem.Rem(this, other)

/**
 * The remainder from the division of two floats.
 *
 * The remainder has the same sign as the dividend and is computed as:
 * `x - (x / y).trunc() * y`.
 *
 * # Examples
 * ```kotlin
 * val x: Float = 50.50f
 * val y: Float = 8.125f
 * val remainder = x - (x / y).trunc() * y
 * // The answer to both operations is 1.75
 * check(x % y == remainder)
 * ```
 */
public fun F16.rem(other: F16): F16 = this % other

public fun F16.remF16(other: F16): Output = Rem.Rem(this, other)

/**
 * The remainder from the division of two floats.
 *
 * The remainder has the same sign as the dividend and is computed as:
 * `x - (x / y).trunc() * y`.
 *
 * # Examples
 * ```kotlin
 * val x: Float = 50.50f
 * val y: Float = 8.125f
 * val remainder = x - (x / y).trunc() * y
 * // The answer to both operations is 1.75
 * check(x % y == remainder)
 * ```
 */
public fun kotlin.Float.rem(other: kotlin.Float): kotlin.Float = this % other

public fun kotlin.Float.remFloat(other: kotlin.Float): Output = Rem.Rem(this, other)

/**
 * The remainder from the division of two floats.
 *
 * The remainder has the same sign as the dividend and is computed as:
 * `x - (x / y).trunc() * y`.
 *
 * # Examples
 * ```kotlin
 * val x: Float = 50.50f
 * val y: Float = 8.125f
 * val remainder = x - (x / y).trunc() * y
 * // The answer to both operations is 1.75
 * check(x % y == remainder)
 * ```
 */
public fun Double.rem(other: Double): Double = this % other

public fun Double.remDouble(other: Double): Output = Rem.Rem(this, other)

/**
 * The remainder from the division of two floats.
 *
 * The remainder has the same sign as the dividend and is computed as:
 * `x - (x / y).trunc() * y`.
 *
 * # Examples
 * ```kotlin
 * val x: Float = 50.50f
 * val y: Float = 8.125f
 * val remainder = x - (x / y).trunc() * y
 * // The answer to both operations is 1.75
 * check(x % y == remainder)
 * ```
 */
public fun F128.rem(other: F128): F128 = this % other

public fun F128.remF128(other: F128): Output = Rem.Rem(this, other)

/**
 * The unary negation operator `-`.
 *
 * # Examples
 *
 * An implementation of `Neg` for `Sign`, which allows the use of `-` to
 * negate its value.
 *
 * ```kotlin
 * import std.ops.Neg
 *
 * public typealias Output = Any
 *
 * public fun doctestEntry() {
 *     sealed class Sign : Neg<Any?, Sign> {
 *         public data object Negative : Sign()
 *         public data object Zero : Sign()
 *         public data object Positive : Sign()
 *
 *         public fun rustEquals(other: Sign): Boolean = this == other
 *
 *         public open fun neg(): Sign {
 *             return when (this) {
 *                 Sign.Negative -> Sign.Positive
 *                 Sign.Zero -> Sign.Zero
 *                 Sign.Positive -> Sign.Negative
 *             }
 *         }
 *     }
 *     // A negative positive is a negative.
 *     check(-Sign.Positive == Sign.Negative)
 *     // A double negative is a positive.
 *     check(-Sign.Negative == Sign.Positive)
 *     // Zero is its own negation.
 *     check(-Sign.Zero == Sign.Zero)
 * }
 * ```
 */
public interface Neg<out Output> {
    /**
     * Performs the unary `-` operation.
     *
     * # Example
     *
     * ```kotlin
     * val x: Int = 12
     * check(-x == -12)
     * ```
     */
    public fun neg(): Output
}

public fun Int.neg(): Int = -this

public fun Byte.neg(): Byte = (-this).toByte()

public fun Short.neg(): Short = (-this).toShort()

public fun Long.neg(): Long = -this

public fun I128.neg(): I128 = -this

public fun F16.neg(): F16 = -this

public fun kotlin.Float.neg(): kotlin.Float = -this

public fun Double.neg(): Double = -this

public fun F128.neg(): F128 = -this

/**
 * The addition assignment operator `+=`.
 *
 * # Examples
 *
 * This example creates a `Point` struct that implements the `AddAssign`
 * trait, and then demonstrates add-assigning to a mutable `Point`.
 *
 * ```kotlin
 * import std.ops.AddAssign
 *
 * open class Point(public var x: Int, public var y: Int) : AddAssign<Point> {
 *     public open fun clone(): Point = Point(x, y)
 *
 *     public open fun addAssign(other: Point) {
 *         this = Point(x = this.x + other.x, y = this.y + other.y)
 *     }
 * }
 * var point = Point(x = 1, y = 0)
 * point += Point(x = 2, y = 3)
 * check(point == Point(x = 3, y = 3))
 * ```
 */
public interface AddAssign<Rhs> {
    /**
     * Performs the `+=` operation.
     *
     * # Example
     *
     * ```kotlin
     * var x: UInt = 12u
     * x += 1u
     * check(x == 13u)
     * ```
     */
    public fun addAssign(rhs: Rhs)
}

public fun Int.addAssign(other: Int) {
    this += other
}

public fun Int.addAssignInt(other: Int) {
    AddAssign.AddAssign(this, other)
}

public fun UByte.addAssign(other: UByte) {
    this += other
}

public fun UByte.addAssignUByte(other: UByte) {
    AddAssign.AddAssign(this, other)
}

public fun UShort.addAssign(other: UShort) {
    this += other
}

public fun UShort.addAssignUShort(other: UShort) {
    AddAssign.AddAssign(this, other)
}

public fun UInt.addAssign(other: UInt) {
    this += other
}

public fun UInt.addAssignUInt(other: UInt) {
    AddAssign.AddAssign(this, other)
}

public fun ULong.addAssign(other: ULong) {
    this += other
}

public fun ULong.addAssignULong(other: ULong) {
    AddAssign.AddAssign(this, other)
}

public fun U128.addAssign(other: U128) {
    this += other
}

public fun U128.addAssignU128(other: U128) {
    AddAssign.AddAssign(this, other)
}

public fun Byte.addAssign(other: Byte) {
    this += other
}

public fun Byte.addAssignByte(other: Byte) {
    AddAssign.AddAssign(this, other)
}

public fun Short.addAssign(other: Short) {
    this += other
}

public fun Short.addAssignShort(other: Short) {
    AddAssign.AddAssign(this, other)
}

public fun Long.addAssign(other: Long) {
    this += other
}

public fun Long.addAssignLong(other: Long) {
    AddAssign.AddAssign(this, other)
}

public fun I128.addAssign(other: I128) {
    this += other
}

public fun I128.addAssignI128(other: I128) {
    AddAssign.AddAssign(this, other)
}

public fun F16.addAssign(other: F16) {
    this += other
}

public fun F16.addAssignF16(other: F16) {
    AddAssign.AddAssign(this, other)
}

public fun kotlin.Float.addAssign(other: kotlin.Float) {
    this += other
}

public fun kotlin.Float.addAssignFloat(other: kotlin.Float) {
    AddAssign.AddAssign(this, other)
}

public fun Double.addAssign(other: Double) {
    this += other
}

public fun Double.addAssignDouble(other: Double) {
    AddAssign.AddAssign(this, other)
}

public fun F128.addAssign(other: F128) {
    this += other
}

public fun F128.addAssignF128(other: F128) {
    AddAssign.AddAssign(this, other)
}

/**
 * The subtraction assignment operator `-=`.
 *
 * # Examples
 *
 * This example creates a `Point` struct that implements the `SubAssign`
 * trait, and then demonstrates sub-assigning to a mutable `Point`.
 *
 * ```kotlin
 * import std.ops.SubAssign
 *
 * open class Point(public var x: Int, public var y: Int) : SubAssign<Point> {
 *     public open fun clone(): Point = Point(x, y)
 *
 *     public open fun subAssign(other: Point) {
 *         this = Point(x = this.x - other.x, y = this.y - other.y)
 *     }
 * }
 * var point = Point(x = 3, y = 3)
 * point -= Point(x = 2, y = 3)
 * check(point == Point(x = 1, y = 0))
 * ```
 */
public interface SubAssign<Rhs> {
    /**
     * Performs the `-=` operation.
     *
     * # Example
     *
     * ```kotlin
     * var x: UInt = 12u
     * x -= 1u
     * check(x == 11u)
     * ```
     */
    public fun subAssign(rhs: Rhs)
}

public fun Int.subAssign(other: Int) {
    this -= other
}

public fun Int.subAssignInt(other: Int) {
    SubAssign.SubAssign(this, other)
}

public fun UByte.subAssign(other: UByte) {
    this -= other
}

public fun UByte.subAssignUByte(other: UByte) {
    SubAssign.SubAssign(this, other)
}

public fun UShort.subAssign(other: UShort) {
    this -= other
}

public fun UShort.subAssignUShort(other: UShort) {
    SubAssign.SubAssign(this, other)
}

public fun UInt.subAssign(other: UInt) {
    this -= other
}

public fun UInt.subAssignUInt(other: UInt) {
    SubAssign.SubAssign(this, other)
}

public fun ULong.subAssign(other: ULong) {
    this -= other
}

public fun ULong.subAssignULong(other: ULong) {
    SubAssign.SubAssign(this, other)
}

public fun U128.subAssign(other: U128) {
    this -= other
}

public fun U128.subAssignU128(other: U128) {
    SubAssign.SubAssign(this, other)
}

public fun Byte.subAssign(other: Byte) {
    this -= other
}

public fun Byte.subAssignByte(other: Byte) {
    SubAssign.SubAssign(this, other)
}

public fun Short.subAssign(other: Short) {
    this -= other
}

public fun Short.subAssignShort(other: Short) {
    SubAssign.SubAssign(this, other)
}

public fun Long.subAssign(other: Long) {
    this -= other
}

public fun Long.subAssignLong(other: Long) {
    SubAssign.SubAssign(this, other)
}

public fun I128.subAssign(other: I128) {
    this -= other
}

public fun I128.subAssignI128(other: I128) {
    SubAssign.SubAssign(this, other)
}

public fun F16.subAssign(other: F16) {
    this -= other
}

public fun F16.subAssignF16(other: F16) {
    SubAssign.SubAssign(this, other)
}

public fun kotlin.Float.subAssign(other: kotlin.Float) {
    this -= other
}

public fun kotlin.Float.subAssignFloat(other: kotlin.Float) {
    SubAssign.SubAssign(this, other)
}

public fun Double.subAssign(other: Double) {
    this -= other
}

public fun Double.subAssignDouble(other: Double) {
    SubAssign.SubAssign(this, other)
}

public fun F128.subAssign(other: F128) {
    this -= other
}

public fun F128.subAssignF128(other: F128) {
    SubAssign.SubAssign(this, other)
}

/**
 * The multiplication assignment operator `*=`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.MulAssign
 *
 * open class Frequency(public var hertz: Double) : MulAssign<Double> {
 *     public open fun mulAssign(rhs: Double) {
 *         this.hertz *= rhs
 *     }
 * }
 * var frequency = Frequency(hertz = 50.0)
 * frequency *= 4.0
 * check(Frequency(hertz = 200.0) == frequency)
 * ```
 */
public interface MulAssign<Rhs> {
    /**
     * Performs the `*=` operation.
     *
     * # Example
     *
     * ```kotlin
     * var x: UInt = 12u
     * x *= 2u
     * check(x == 24u)
     * ```
     */
    public fun mulAssign(rhs: Rhs)
}

public fun Int.mulAssign(other: Int) {
    this *= other
}

public fun Int.mulAssignInt(other: Int) {
    MulAssign.MulAssign(this, other)
}

public fun UByte.mulAssign(other: UByte) {
    this *= other
}

public fun UByte.mulAssignUByte(other: UByte) {
    MulAssign.MulAssign(this, other)
}

public fun UShort.mulAssign(other: UShort) {
    this *= other
}

public fun UShort.mulAssignUShort(other: UShort) {
    MulAssign.MulAssign(this, other)
}

public fun UInt.mulAssign(other: UInt) {
    this *= other
}

public fun UInt.mulAssignUInt(other: UInt) {
    MulAssign.MulAssign(this, other)
}

public fun ULong.mulAssign(other: ULong) {
    this *= other
}

public fun ULong.mulAssignULong(other: ULong) {
    MulAssign.MulAssign(this, other)
}

public fun U128.mulAssign(other: U128) {
    this *= other
}

public fun U128.mulAssignU128(other: U128) {
    MulAssign.MulAssign(this, other)
}

public fun Byte.mulAssign(other: Byte) {
    this *= other
}

public fun Byte.mulAssignByte(other: Byte) {
    MulAssign.MulAssign(this, other)
}

public fun Short.mulAssign(other: Short) {
    this *= other
}

public fun Short.mulAssignShort(other: Short) {
    MulAssign.MulAssign(this, other)
}

public fun Long.mulAssign(other: Long) {
    this *= other
}

public fun Long.mulAssignLong(other: Long) {
    MulAssign.MulAssign(this, other)
}

public fun I128.mulAssign(other: I128) {
    this *= other
}

public fun I128.mulAssignI128(other: I128) {
    MulAssign.MulAssign(this, other)
}

public fun F16.mulAssign(other: F16) {
    this *= other
}

public fun F16.mulAssignF16(other: F16) {
    MulAssign.MulAssign(this, other)
}

public fun kotlin.Float.mulAssign(other: kotlin.Float) {
    this *= other
}

public fun kotlin.Float.mulAssignFloat(other: kotlin.Float) {
    MulAssign.MulAssign(this, other)
}

public fun Double.mulAssign(other: Double) {
    this *= other
}

public fun Double.mulAssignDouble(other: Double) {
    MulAssign.MulAssign(this, other)
}

public fun F128.mulAssign(other: F128) {
    this *= other
}

public fun F128.mulAssignF128(other: F128) {
    MulAssign.MulAssign(this, other)
}

/**
 * The division assignment operator `/=`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.DivAssign
 *
 * open class Frequency(public var hertz: Double) : DivAssign<Double> {
 *     public open fun divAssign(rhs: Double) {
 *         this.hertz /= rhs
 *     }
 * }
 * var frequency = Frequency(hertz = 200.0)
 * frequency /= 4.0
 * check(Frequency(hertz = 50.0) == frequency)
 * ```
 */
public interface DivAssign<Rhs> {
    /**
     * Performs the `/=` operation.
     *
     * # Example
     *
     * ```kotlin
     * var x: UInt = 12u
     * x /= 2u
     * check(x == 6u)
     * ```
     */
    public fun divAssign(rhs: Rhs)
}

public fun Int.divAssign(other: Int) {
    this /= other
}

public fun Int.divAssignInt(other: Int) {
    DivAssign.DivAssign(this, other)
}

public fun UByte.divAssign(other: UByte) {
    this /= other
}

public fun UByte.divAssignUByte(other: UByte) {
    DivAssign.DivAssign(this, other)
}

public fun UShort.divAssign(other: UShort) {
    this /= other
}

public fun UShort.divAssignUShort(other: UShort) {
    DivAssign.DivAssign(this, other)
}

public fun UInt.divAssign(other: UInt) {
    this /= other
}

public fun UInt.divAssignUInt(other: UInt) {
    DivAssign.DivAssign(this, other)
}

public fun ULong.divAssign(other: ULong) {
    this /= other
}

public fun ULong.divAssignULong(other: ULong) {
    DivAssign.DivAssign(this, other)
}

public fun U128.divAssign(other: U128) {
    this /= other
}

public fun U128.divAssignU128(other: U128) {
    DivAssign.DivAssign(this, other)
}

public fun Byte.divAssign(other: Byte) {
    this /= other
}

public fun Byte.divAssignByte(other: Byte) {
    DivAssign.DivAssign(this, other)
}

public fun Short.divAssign(other: Short) {
    this /= other
}

public fun Short.divAssignShort(other: Short) {
    DivAssign.DivAssign(this, other)
}

public fun Long.divAssign(other: Long) {
    this /= other
}

public fun Long.divAssignLong(other: Long) {
    DivAssign.DivAssign(this, other)
}

public fun I128.divAssign(other: I128) {
    this /= other
}

public fun I128.divAssignI128(other: I128) {
    DivAssign.DivAssign(this, other)
}

public fun F16.divAssign(other: F16) {
    this /= other
}

public fun F16.divAssignF16(other: F16) {
    DivAssign.DivAssign(this, other)
}

public fun kotlin.Float.divAssign(other: kotlin.Float) {
    this /= other
}

public fun kotlin.Float.divAssignFloat(other: kotlin.Float) {
    DivAssign.DivAssign(this, other)
}

public fun Double.divAssign(other: Double) {
    this /= other
}

public fun Double.divAssignDouble(other: Double) {
    DivAssign.DivAssign(this, other)
}

public fun F128.divAssign(other: F128) {
    this /= other
}

public fun F128.divAssignF128(other: F128) {
    DivAssign.DivAssign(this, other)
}

/**
 * The remainder assignment operator `% =`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.RemAssign
 *
 * open class CookieJar(public var cookies: UInt) : RemAssign<UInt> {
 *     public open fun remAssign(piles: UInt) {
 *         this.cookies %= piles
 *     }
 * }
 * var jar = CookieJar(cookies = 31u)
 * val piles = 4
 * println("Splitting up ${jar.cookies} cookies into $piles even piles!")
 * jar %= piles
 * println("${jar.cookies} cookies remain in the cookie jar!")
 * ```
 */
public interface RemAssign<Rhs> {
    /**
     * Performs the `% =` operation.
     *
     * # Example
     *
     * ```kotlin
     * var x: UInt = 12u
     * x %= 10u
     * check(x == 2u)
     * ```
     */
    public fun remAssign(rhs: Rhs)
}

public fun Int.remAssign(other: Int) {
    this %= other
}

public fun Int.remAssignInt(other: Int) {
    RemAssign.RemAssign(this, other)
}

public fun UByte.remAssign(other: UByte) {
    this %= other
}

public fun UByte.remAssignUByte(other: UByte) {
    RemAssign.RemAssign(this, other)
}

public fun UShort.remAssign(other: UShort) {
    this %= other
}

public fun UShort.remAssignUShort(other: UShort) {
    RemAssign.RemAssign(this, other)
}

public fun UInt.remAssign(other: UInt) {
    this %= other
}

public fun UInt.remAssignUInt(other: UInt) {
    RemAssign.RemAssign(this, other)
}

public fun ULong.remAssign(other: ULong) {
    this %= other
}

public fun ULong.remAssignULong(other: ULong) {
    RemAssign.RemAssign(this, other)
}

public fun U128.remAssign(other: U128) {
    this %= other
}

public fun U128.remAssignU128(other: U128) {
    RemAssign.RemAssign(this, other)
}

public fun Byte.remAssign(other: Byte) {
    this %= other
}

public fun Byte.remAssignByte(other: Byte) {
    RemAssign.RemAssign(this, other)
}

public fun Short.remAssign(other: Short) {
    this %= other
}

public fun Short.remAssignShort(other: Short) {
    RemAssign.RemAssign(this, other)
}

public fun Long.remAssign(other: Long) {
    this %= other
}

public fun Long.remAssignLong(other: Long) {
    RemAssign.RemAssign(this, other)
}

public fun I128.remAssign(other: I128) {
    this %= other
}

public fun I128.remAssignI128(other: I128) {
    RemAssign.RemAssign(this, other)
}

public fun F16.remAssign(other: F16) {
    this %= other
}

public fun F16.remAssignF16(other: F16) {
    RemAssign.RemAssign(this, other)
}

public fun kotlin.Float.remAssign(other: kotlin.Float) {
    this %= other
}

public fun kotlin.Float.remAssignFloat(other: kotlin.Float) {
    RemAssign.RemAssign(this, other)
}

public fun Double.remAssign(other: Double) {
    this %= other
}

public fun Double.remAssignDouble(other: Double) {
    RemAssign.RemAssign(this, other)
}

public fun F128.remAssign(other: F128) {
    this %= other
}

public fun F128.remAssignF128(other: F128) {
    RemAssign.RemAssign(this, other)
}
