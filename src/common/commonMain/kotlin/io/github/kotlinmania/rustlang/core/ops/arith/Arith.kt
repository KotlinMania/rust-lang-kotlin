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
 *     open class Point(public var x: Int, public var y: Int) : Add<Point, Point> {
 *         public open fun clone(): Point = Point(x, y)
 *
 *         public override fun add(other: Point): Point {
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
 *     open class Point<T>(public var x: T, public var y: T) {
 *         public open fun clone(): Point<T> = Point(x, y)
 *     }
 *     // Notice that the implementation uses the associated type `Output`.
 *     fun <T : Add<T, T>> Point<T>.add(other: Point<T>): Point<T> {
 *         return Point(x = this.x + other.x, y = this.y + other.y)
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

public fun UByte.add(other: UByte): UByte = (this + other).toUByte()

public fun UShort.add(other: UShort): UShort = (this + other).toUShort()

public fun UInt.add(other: UInt): UInt = this + other

public fun ULong.add(other: ULong): ULong = this + other

public fun U128.add(other: U128): U128 = this + other

public fun Byte.add(other: Byte): Byte = (this + other).toByte()

public fun Short.add(other: Short): Short = (this + other).toShort()

public fun Long.add(other: Long): Long = this + other

public fun I128.add(other: I128): I128 = this + other

public fun F16.add(other: F16): F16 = this + other

public fun kotlin.Float.add(other: kotlin.Float): kotlin.Float = this + other

public fun Double.add(other: Double): Double = this + other

public fun F128.add(other: F128): F128 = this + other

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
 *     open class Point(public var x: Int, public var y: Int) : Sub<Point, Point> {
 *         public open fun clone(): Point = Point(x, y)
 *
 *         public override fun sub(other: Point): Point {
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
 *     open class Point<T>(public var x: T, public var y: T)
 *     // Notice that the implementation uses the associated type `Output`.
 *     fun <T : Sub<T, T>> Point<T>.sub(other: Point<T>): Point<T> {
 *         return Point(x = this.x - other.x, y = this.y - other.y)
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

public fun UByte.sub(other: UByte): UByte = (this - other).toUByte()

public fun UShort.sub(other: UShort): UShort = (this - other).toUShort()

public fun UInt.sub(other: UInt): UInt = this - other

public fun ULong.sub(other: ULong): ULong = this - other

public fun U128.sub(other: U128): U128 = this - other

public fun Byte.sub(other: Byte): Byte = (this - other).toByte()

public fun Short.sub(other: Short): Short = (this - other).toShort()

public fun Long.sub(other: Long): Long = this - other

public fun I128.sub(other: I128): I128 = this - other

public fun F16.sub(other: F16): F16 = this - other

public fun kotlin.Float.sub(other: kotlin.Float): kotlin.Float = this - other

public fun Double.sub(other: Double): Double = this - other

public fun F128.sub(other: F128): F128 = this - other

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
 *     open class Rational(public var numerator: Int, public var denominator: Int) : Mul<Rational, Rational> {
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
 *         public override fun mul(rhs: Rational): Rational {
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
 *         public override fun mul(rhs: Scalar): Vector {
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

public fun UByte.mul(other: UByte): UByte = (this * other).toUByte()

public fun UShort.mul(other: UShort): UShort = (this * other).toUShort()

public fun UInt.mul(other: UInt): UInt = this * other

public fun ULong.mul(other: ULong): ULong = this * other

public fun U128.mul(other: U128): U128 = this * other

public fun Byte.mul(other: Byte): Byte = (this * other).toByte()

public fun Short.mul(other: Short): Short = (this * other).toShort()

public fun Long.mul(other: Long): Long = this * other

public fun I128.mul(other: I128): I128 = this * other

public fun F16.mul(other: F16): F16 = this * other

public fun kotlin.Float.mul(other: kotlin.Float): kotlin.Float = this * other

public fun Double.mul(other: Double): Double = this * other

public fun F128.mul(other: F128): F128 = this * other

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
 *     open class Rational(public var numerator: Int, public var denominator: Int) : Div<Rational, Rational> {
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
 *         public override operator fun div(rhs: Rational): Rational {
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
 *         public override operator fun div(rhs: Scalar): Vector {
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
    public operator fun div(rhs: Rhs): Output
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

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UByte.div(other: UByte): UByte = (this / other).toUByte()

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UShort.div(other: UShort): UShort = (this / other).toUShort()

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UInt.div(other: UInt): UInt = this / other

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun ULong.div(other: ULong): ULong = this / other

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public operator fun U128.div(other: U128): U128 = this / other

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

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public fun Short.div(other: Short): Short = (this / other).toShort()

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

/**
 * This operation rounds towards zero, truncating any
 * fractional part of the exact result.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or the division results in overflow.
 */
public operator fun I128.div(other: I128): I128 = this / other

public operator fun F16.div(other: F16): F16 = this / other

public operator fun kotlin.Float.div(other: kotlin.Float): kotlin.Float = this / other

public fun Double.div(other: Double): Double = this / other

public operator fun F128.div(other: F128): F128 = this / other

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
 *         public override operator fun rem(modulus: Int): SplitSlice<T> {
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
    public operator fun rem(rhs: Rhs): Output
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

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UByte.rem(other: UByte): UByte = (this % other).toUByte()

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UShort.rem(other: UShort): UShort = (this % other).toUShort()

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun UInt.rem(other: UInt): UInt = this % other

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public fun ULong.rem(other: ULong): ULong = this % other

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0`.
 */
public operator fun U128.rem(other: U128): U128 = this % other

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

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public fun Short.rem(other: Short): Short = (this % other).toShort()

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

/**
 * This operation satisfies `n % d == n - (n / d) * d`. The
 * result has the same sign as the left operand.
 *
 * # Panics
 *
 * This operation will panic if `other == 0` or if `self / other` results in overflow.
 */
public operator fun I128.rem(other: I128): I128 = this % other

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
public operator fun F16.rem(other: F16): F16 = this % other

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
public operator fun kotlin.Float.rem(other: kotlin.Float): kotlin.Float = this % other

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
public operator fun F128.rem(other: F128): F128 = this % other

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
 *     sealed class Sign : Neg<Sign> {
 *         public data object Negative : Sign()
 *         public data object Zero : Sign()
 *         public data object Positive : Sign()
 *
 *         public fun rustEquals(other: Sign): Boolean = this == other
 *
 *         private fun kmDiscriminant(): Int =
 *             when (this) {
 *             Negative -> 0
 *             Zero -> 1
 *             Positive -> 2
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
 *         public override fun neg(): Sign {
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
 *         var selfMut = this
 *         selfMut = Point(x = selfMut.x + other.x, y = selfMut.y + other.y)
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
    (this + other).let {}
}

public fun UByte.addAssign(other: UByte) {
    (this + other).let {}
}

public fun UShort.addAssign(other: UShort) {
    (this + other).let {}
}

public fun UInt.addAssign(other: UInt) {
    (this + other).let {}
}

public fun ULong.addAssign(other: ULong) {
    (this + other).let {}
}

public fun U128.addAssign(other: U128) {
    (this + other).let {}
}

public fun Byte.addAssign(other: Byte) {
    (this + other).let {}
}

public fun Short.addAssign(other: Short) {
    (this + other).let {}
}

public fun Long.addAssign(other: Long) {
    (this + other).let {}
}

public fun I128.addAssign(other: I128) {
    (this + other).let {}
}

public fun F16.addAssign(other: F16) {
    (this + other).let {}
}

public fun kotlin.Float.addAssign(other: kotlin.Float) {
    (this + other).let {}
}

public fun Double.addAssign(other: Double) {
    (this + other).let {}
}

public fun F128.addAssign(other: F128) {
    (this + other).let {}
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
 *         var selfMut = this
 *         selfMut = Point(x = selfMut.x - other.x, y = selfMut.y - other.y)
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
    (this - other).let {}
}

public fun UByte.subAssign(other: UByte) {
    (this - other).let {}
}

public fun UShort.subAssign(other: UShort) {
    (this - other).let {}
}

public fun UInt.subAssign(other: UInt) {
    (this - other).let {}
}

public fun ULong.subAssign(other: ULong) {
    (this - other).let {}
}

public fun U128.subAssign(other: U128) {
    (this - other).let {}
}

public fun Byte.subAssign(other: Byte) {
    (this - other).let {}
}

public fun Short.subAssign(other: Short) {
    (this - other).let {}
}

public fun Long.subAssign(other: Long) {
    (this - other).let {}
}

public fun I128.subAssign(other: I128) {
    (this - other).let {}
}

public fun F16.subAssign(other: F16) {
    (this - other).let {}
}

public fun kotlin.Float.subAssign(other: kotlin.Float) {
    (this - other).let {}
}

public fun Double.subAssign(other: Double) {
    (this - other).let {}
}

public fun F128.subAssign(other: F128) {
    (this - other).let {}
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
    (this * other).let {}
}

public fun UByte.mulAssign(other: UByte) {
    (this * other).let {}
}

public fun UShort.mulAssign(other: UShort) {
    (this * other).let {}
}

public fun UInt.mulAssign(other: UInt) {
    (this * other).let {}
}

public fun ULong.mulAssign(other: ULong) {
    (this * other).let {}
}

public fun U128.mulAssign(other: U128) {
    (this * other).let {}
}

public fun Byte.mulAssign(other: Byte) {
    (this * other).let {}
}

public fun Short.mulAssign(other: Short) {
    (this * other).let {}
}

public fun Long.mulAssign(other: Long) {
    (this * other).let {}
}

public fun I128.mulAssign(other: I128) {
    (this * other).let {}
}

public fun F16.mulAssign(other: F16) {
    (this * other).let {}
}

public fun kotlin.Float.mulAssign(other: kotlin.Float) {
    (this * other).let {}
}

public fun Double.mulAssign(other: Double) {
    (this * other).let {}
}

public fun F128.mulAssign(other: F128) {
    (this * other).let {}
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
    (this / other).let {}
}

public fun UByte.divAssign(other: UByte) {
    (this / other).let {}
}

public fun UShort.divAssign(other: UShort) {
    (this / other).let {}
}

public fun UInt.divAssign(other: UInt) {
    (this / other).let {}
}

public fun ULong.divAssign(other: ULong) {
    (this / other).let {}
}

public fun U128.divAssign(other: U128) {
    (this / other).let {}
}

public fun Byte.divAssign(other: Byte) {
    (this / other).let {}
}

public fun Short.divAssign(other: Short) {
    (this / other).let {}
}

public fun Long.divAssign(other: Long) {
    (this / other).let {}
}

public fun I128.divAssign(other: I128) {
    (this / other).let {}
}

public fun F16.divAssign(other: F16) {
    (this / other).let {}
}

public fun kotlin.Float.divAssign(other: kotlin.Float) {
    (this / other).let {}
}

public fun Double.divAssign(other: Double) {
    (this / other).let {}
}

public fun F128.divAssign(other: F128) {
    (this / other).let {}
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
    (this % other).let {}
}

public fun UByte.remAssign(other: UByte) {
    (this % other).let {}
}

public fun UShort.remAssign(other: UShort) {
    (this % other).let {}
}

public fun UInt.remAssign(other: UInt) {
    (this % other).let {}
}

public fun ULong.remAssign(other: ULong) {
    (this % other).let {}
}

public fun U128.remAssign(other: U128) {
    (this % other).let {}
}

public fun Byte.remAssign(other: Byte) {
    (this % other).let {}
}

public fun Short.remAssign(other: Short) {
    (this % other).let {}
}

public fun Long.remAssign(other: Long) {
    (this % other).let {}
}

public fun I128.remAssign(other: I128) {
    (this % other).let {}
}

public fun F16.remAssign(other: F16) {
    (this % other).let {}
}

public fun kotlin.Float.remAssign(other: kotlin.Float) {
    (this % other).let {}
}

public fun Double.remAssign(other: Double) {
    (this % other).let {}
}

public fun F128.remAssign(other: F128) {
    (this % other).let {}
}
