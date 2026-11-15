// port-lint: source simd/num/int.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.int

import io.github.kotlinmania.rustlang.Mask
import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.SimdCast

/**
 * Operations on SIMD vectors of signed integers.
 */
public interface SimdInt<SelfType, out Mask, out Scalar, out Unsigned, out Cast> {
    /**
     * Performs elementwise conversion of this vector's elements to another SIMD-valid type.
     *
     * This follows the semantics of Rust's `as` conversion for casting integers (wrapping to
     * other integer types, and saturating to float types).
     */
    public fun <T : SimdCast> cast(): Cast

    /**
     * Lanewise saturating add.
     *
     * # Examples
     * ```kotlin
     * val x = Simd.fromArray(intArrayOf(Int.MIN_VALUE, 0, 1, Int.MAX_VALUE))
     * val max = Simd.splat(Int.MAX_VALUE)
     * val unsat = x + max
     * val sat = x.saturatingAdd(max)
     * check(unsat == Simd.fromArray(intArrayOf(-1, Int.MAX_VALUE, Int.MIN_VALUE, -2)))
     * check(sat == Simd.fromArray(intArrayOf(-1, Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)))
     * ```
     */
    public fun saturatingAdd(second: SelfType): SelfType

    /**
     * Lanewise saturating subtract.
     *
     * # Examples
     * ```kotlin
     * val x = Simd.fromArray(intArrayOf(Int.MIN_VALUE, -2, -1, Int.MAX_VALUE))
     * val max = Simd.splat(Int.MAX_VALUE)
     * val unsat = x - max
     * val sat = x.saturatingSub(max)
     * check(unsat == Simd.fromArray(intArrayOf(1, Int.MAX_VALUE, Int.MIN_VALUE, 0)))
     * check(sat == Simd.fromArray(intArrayOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, 0)))
     * ```
     */
    public fun saturatingSub(second: SelfType): SelfType

    /**
     * Lanewise absolute value, implemented in Rust.
     * Every element becomes its absolute value.
     *
     * # Examples
     * ```kotlin
     * val xs = Simd.fromArray(intArrayOf(Int.MIN_VALUE, Int.MIN_VALUE + 1, -5, 0))
     * check(xs.abs() == Simd.fromArray(intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 5, 0)))
     * ```
     */
    public fun abs(): SelfType

    /**
     * Lanewise absolute difference.
     * Every element becomes the absolute difference of `self` and `second`.
     *
     * # Examples
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val a = Simd.fromArray(intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 100, -100))
     *     val b = Simd.fromArray(intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE, -80, -120))
     *     check(a.absDiff(b) == Simd.fromArray(uintArrayOf(UInt.MAX_VALUE, UInt.MAX_VALUE, 180u, 20u)))
     * }
     * ```
     */
    public fun absDiff(second: SelfType): Unsigned

    /**
     * Lanewise saturating absolute value, implemented in Rust.
     * As abs(), except the MIN value becomes MAX instead of itself.
     *
     * # Examples
     * ```kotlin
     * val xs = Simd.fromArray(intArrayOf(Int.MIN_VALUE, -2, 0, 3))
     * val unsat = xs.abs()
     * val sat = xs.saturatingAbs()
     * check(unsat == Simd.fromArray(intArrayOf(Int.MIN_VALUE, 2, 0, 3)))
     * check(sat == Simd.fromArray(intArrayOf(Int.MAX_VALUE, 2, 0, 3)))
     * ```
     */
    public fun saturatingAbs(): SelfType

    /**
     * Lanewise saturating negation, implemented in Rust.
     * As neg(), except the MIN value becomes MAX instead of itself.
     *
     * # Examples
     * ```kotlin
     * val x = Simd.fromArray(intArrayOf(Int.MIN_VALUE, -2, 3, Int.MAX_VALUE))
     * val unsat = -x
     * val sat = x.saturatingNeg()
     * check(unsat == Simd.fromArray(intArrayOf(Int.MIN_VALUE, 2, -3, Int.MIN_VALUE + 1)))
     * check(sat == Simd.fromArray(intArrayOf(Int.MAX_VALUE, 2, -3, Int.MIN_VALUE + 1)))
     * ```
     */
    public fun saturatingNeg(): SelfType

    /**
     * Returns true for each positive element and false if it is zero or negative.
     */
    public fun isPositive(): Mask

    /**
     * Returns true for each negative element and false if it is zero or positive.
     */
    public fun isNegative(): Mask

    /**
     * Returns numbers representing the sign of each element.
     * * `0` if the number is zero
     * * `1` if the number is positive
     * * `- 1` if the number is negative
     */
    public fun signum(): SelfType

    /**
     * Returns the sum of the elements of the vector, with wrapping addition.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1, 2, 3, 4))
     * check(v.reduceSum() == 10)
     * // SIMD integer addition is always wrapping
     * val v1 = fromArray(intArrayOf(Int.MAX_VALUE, 1, 0, 0))
     * check(v1.reduceSum() == Int.MIN_VALUE)
     * ```
     */
    public fun reduceSum(): Scalar

    /**
     * Returns the product of the elements of the vector, with wrapping multiplication.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1, 2, 3, 4))
     * check(v.reduceProduct() == 24)
     * // SIMD integer multiplication is always wrapping
     * val v1 = fromArray(intArrayOf(Int.MAX_VALUE, 2, 1, 1))
     * check(v1.reduceProduct() < Int.MAX_VALUE)
     * ```
     */
    public fun reduceProduct(): Scalar

    /**
     * Returns the maximum element in the vector.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1, 2, 3, 4))
     * check(v.reduceMax() == 4)
     * ```
     */
    public fun reduceMax(): Scalar

    /**
     * Returns the minimum element in the vector.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1, 2, 3, 4))
     * check(v.reduceMin() == 1)
     * ```
     */
    public fun reduceMin(): Scalar

    /**
     * Returns the cumulative bitwise "and" across the elements of the vector.
     */
    public fun reduceAnd(): Scalar

    /**
     * Returns the cumulative bitwise "or" across the elements of the vector.
     */
    public fun reduceOr(): Scalar

    /**
     * Returns the cumulative bitwise "xor" across the elements of the vector.
     */
    public fun reduceXor(): Scalar

    /**
     * Reverses the byte order of each element.
     */
    public fun swapBytes(): SelfType

    /**
     * Reverses the order of bits in each elemnent.
     * The least significant bit becomes the most significant bit, second least-significant bit becomes second most-significant bit, etc.
     */
    public fun reverseBits(): SelfType

    /**
     * Returns the number of ones in the binary representation of each element.
     */
    public fun countOnes(): Unsigned

    /**
     * Returns the number of zeros in the binary representation of each element.
     */
    public fun countZeros(): Unsigned

    /**
     * Returns the number of leading zeros in the binary representation of each element.
     */
    public fun leadingZeros(): Unsigned

    /**
     * Returns the number of trailing zeros in the binary representation of each element.
     */
    public fun trailingZeros(): Unsigned

    /**
     * Returns the number of leading ones in the binary representation of each element.
     */
    public fun leadingOnes(): Unsigned

    /**
     * Returns the number of trailing ones in the binary representation of each element.
     */
    public fun trailingOnes(): Unsigned
}

public fun <T : SimdCast> Simd<Byte, N>.cast(n: Int): Simd<T> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.saturatingAdd(second: Simd<Byte, N>, n: Int): Simd<Byte, N> = run { val _simdArg0 = this; val _simdArg1 = second; _simdArg0 }

public fun Simd<Byte, N>.saturatingSub(second: Simd<Byte, N>, n: Int): Simd<Byte, N> = run { val _simdArg0 = this; val _simdArg1 = second; _simdArg0 }

public fun Simd<Byte, N>.abs(n: Int): Simd<Byte, N> {
    val SHR: Byte = (8u.toByte() - 1).toByte()
    val m = this shr Simd.splat(SHR).toInt()
    return (this xor m) - m
}

public fun Simd<Byte, N>.absDiff(second: Simd<Byte, N>, n: Int): Simd<UByte> {
    val max = this.simdMax(second)
    val min = this.simdMin(second)
    return (max - min).cast()
}

public fun Simd<Byte, N>.saturatingAbs(n: Int): Simd<Byte, N> {
    val SHR: Byte = (8u.toByte() - 1).toByte()
    val m = this shr Simd.splat(SHR).toInt()
    return (this xor m).saturatingSub(m)
}

public fun Simd<Byte, N>.saturatingNeg(n: Int): Simd<Byte, N> = Simd.splat(0).saturatingSub(this)

public fun Simd<Byte, N>.isPositive(n: Int): Mask<Mask> = this.simdGt(Simd.splat(0))

public fun Simd<Byte, N>.isNegative(n: Int): Mask<Mask> = this.simdLt(Simd.splat(0))

public fun Simd<Byte, N>.signum(n: Int): Simd<Byte, N> = this.isPositive().select(Simd.splat(1), this.isNegative().select(Simd.splat(-1), Simd.splat(0)))

public fun Simd<Byte, N>.reduceSum(n: Int): Byte = run { val _simdArg0 = this; val _simdArg1 = 0; _simdArg1 }

public fun Simd<Byte, N>.reduceProduct(n: Int): Byte = run { val _simdArg0 = this; val _simdArg1 = 1; _simdArg1 }

public fun Simd<Byte, N>.reduceMax(n: Int): Byte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.reduceMin(n: Int): Byte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.reduceAnd(n: Int): Byte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.reduceOr(n: Int): Byte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.reduceXor(n: Int): Byte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.swapBytes(n: Int): Simd<Byte, N> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.reverseBits(n: Int): Simd<Byte, N> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<Byte, N>.countOnes(n: Int): Simd<UByte> = this.cast().countOnes()

public fun Simd<Byte, N>.countZeros(n: Int): Simd<UByte> = this.cast().countZeros()

public fun Simd<Byte, N>.leadingZeros(n: Int): Simd<UByte> = this.cast().leadingZeros()

public fun Simd<Byte, N>.trailingZeros(n: Int): Simd<UByte> = this.cast().trailingZeros()

public fun Simd<Byte, N>.leadingOnes(n: Int): Simd<UByte> = this.cast().leadingOnes()

public fun Simd<Byte, N>.trailingOnes(n: Int): Simd<UByte> = this.cast().trailingOnes()
