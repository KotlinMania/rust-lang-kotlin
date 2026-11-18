// port-lint: source simd/num/int.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.int

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.num.absDiff
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.coresimd.Mask
import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.SimdCast
import kotlin.experimental.xor

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
    public fun <Mask, T : SimdCast<Mask>> cast(): Cast

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
     * val v = Simd<Int>((listOf(1, 2, 3, 4)).toList())
     * check(v.reduceSum() == 10)
     * // SIMD integer addition is always wrapping
     * val v1 = Simd<Int>((intArrayOf(Int.MAX_VALUE, 1, 0, 0)).toList())
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
     * val v = Simd<Int>((listOf(1, 2, 3, 4)).toList())
     * check(v.reduceProduct() == 24)
     * // SIMD integer multiplication is always wrapping
     * val v1 = Simd<Int>((intArrayOf(Int.MAX_VALUE, 2, 1, 1)).toList())
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
     * val v = Simd<Int>((listOf(1, 2, 3, 4)).toList())
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
     * val v = Simd<Int>((listOf(1, 2, 3, 4)).toList())
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

public fun <Mask, T : SimdCast<Mask>> Simd<Byte>.cast(n: Int = 0): Simd<T> = run { val simdArg0 = this; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<T>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); res } }

public fun Simd<Byte>.saturatingAdd(second: Simd<Byte>, n: Int = 0): Simd<Byte> = run { val simdArg0 = this; val simdArg1 = second; simdArg0 }

public fun Simd<Byte>.saturatingSub(second: Simd<Byte>, n: Int = 0): Simd<Byte> = run { val simdArg0 = this; val simdArg1 = second; simdArg0 }

public fun Simd<Byte>.abs(n: Int = 0): Simd<Byte> {
    val SHR: Byte = (8u.toByte() - 1).toByte()
    val m = this shr Simd.splat(SHR, n).toInt()
    return (this xor m) - m
}

public fun Simd<Byte>.absDiff(second: Simd<Byte>, n: Int = 0): Simd<UByte> {
    val max = this.simdMax(second)
    val min = this.simdMin(second)
    return (max - min).cast()
}

public fun Simd<Byte>.saturatingAbs(n: Int = 0): Simd<Byte> {
    val SHR: Byte = (8u.toByte() - 1).toByte()
    val m = this shr Simd.splat(SHR, n).toInt()
    return (this xor m).saturatingSub(m)
}

public fun Simd<Byte>.saturatingNeg(n: Int = 0): Simd<Byte> = Simd.splat(0, n).saturatingSub(this)

public fun Simd<Byte>.isPositive(n: Int = 0): Mask<Mask> = this.simdGt(Simd.splat(0, n))

public fun Simd<Byte>.isNegative(n: Int = 0): Mask<Mask> = this.simdLt(Simd.splat(0, n))

public fun Simd<Byte>.signum(n: Int = 0): Simd<Byte> = this.isPositive(n).select(Simd.splat(1, n), this.isNegative(n).select(Simd.splat(-1, n), Simd.splat(0, n)))

public fun Simd<Byte>.reduceSum(n: Int = 0): Byte = run { val simdArg0 = this; val simdArg1 = 0; simdArg1 }

public fun Simd<Byte>.reduceProduct(n: Int = 0): Byte = run { val simdArg0 = this; val simdArg1 = 1; simdArg1 }

public fun Simd<Byte>.reduceMax(n: Int = 0): Byte = run { val simdArg0 = this; 0 }

public fun Simd<Byte>.reduceMin(n: Int = 0): Byte = run { val simdArg0 = this; 0 }

public fun Simd<Byte>.reduceAnd(n: Int = 0): Byte = run { val simdArg0 = this; 0 }

public fun Simd<Byte>.reduceOr(n: Int = 0): Byte = run { val simdArg0 = this; 0 }

public fun Simd<Byte>.reduceXor(n: Int = 0): Byte = run { val simdArg0 = this; 0 }

public fun Simd<Byte>.swapBytes(n: Int = 0): Simd<Byte> = run { val simdArg0 = this; simdArg0 }

public fun Simd<Byte>.reverseBits(n: Int = 0): Simd<Byte> = run { val simdArg0 = this; simdArg0 }

public fun Simd<Byte>.countOnes(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).countOnes(n)

public fun Simd<Byte>.countZeros(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).countZeros(n)

public fun Simd<Byte>.leadingZeros(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).leadingZeros(n)

public fun Simd<Byte>.trailingZeros(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).trailingZeros(n)

public fun Simd<Byte>.leadingOnes(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).leadingOnes(n)

public fun Simd<Byte>.trailingOnes(n: Int = 0): Simd<UByte> = this.cast<UByte>(n).trailingOnes(n)
