// port-lint: source simd/num/uint.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.uint

import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.simd.SimdCast

/**
 * Operations on SIMD vectors of unsigned integers.
 */
public interface SimdUint<SelfType, out Scalar, out Cast> {
    /**
     * Performs elementwise conversion of this vector's elements to another SIMD-valid type.
     *
     * This follows the semantics of Rust's `as` conversion for casting integers (wrapping to
     * other integer types, and saturating to float types).
     */
    public fun <T : SimdCast> cast(): Cast

    /**
     * Wrapping negation.
     *
     * Like [u32.wrappingNeg], all applications of this function will wrap, with the exception
     * of `- 0`.
     */
    public fun wrappingNeg(): SelfType

    /**
     * Lanewise saturating add.
     *
     * # Examples
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val x = Simd.fromArray(uintArrayOf(2u, 1u, 0u, UInt.MAX_VALUE))
     *     val max = Simd.splat(UInt.MAX_VALUE)
     *     val unsat = x + max
     *     val sat = x.saturatingAdd(max)
     *     check(unsat == Simd.fromArray(uintArrayOf(1u, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE - 1u)))
     *     check(sat == max)
     * }
     * ```
     */
    public fun saturatingAdd(second: SelfType): SelfType

    /**
     * Lanewise saturating subtract.
     *
     * # Examples
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val x = Simd.fromArray(uintArrayOf(2u, 1u, 0u, UInt.MAX_VALUE))
     *     val max = Simd.splat(UInt.MAX_VALUE)
     *     val unsat = x - max
     *     val sat = x.saturatingSub(max)
     *     check(unsat == Simd.fromArray(listOf(3, 2, 1, 0)))
     *     check(sat == Simd.splat(0))
     * }
     * ```
     */
    public fun saturatingSub(second: SelfType): SelfType

    /**
     * Lanewise absolute difference.
     * Every element becomes the absolute difference of `self` and `second`.
     *
     * # Examples
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val a = Simd.fromArray(uintArrayOf(0u, UInt.MAX_VALUE, 100u, 20u))
     *     val b = Simd.fromArray(uintArrayOf(UInt.MAX_VALUE, 0u, 80u, 200u))
     *     check(a.absDiff(b) == Simd.fromArray(uintArrayOf(UInt.MAX_VALUE, UInt.MAX_VALUE, 20u, 180u)))
     * }
     * ```
     */
    public fun absDiff(second: SelfType): SelfType

    /**
     * Returns the sum of the elements of the vector, with wrapping addition.
     */
    public fun reduceSum(): Scalar

    /**
     * Returns the product of the elements of the vector, with wrapping multiplication.
     */
    public fun reduceProduct(): Scalar

    /**
     * Returns the maximum element in the vector.
     */
    public fun reduceMax(): Scalar

    /**
     * Returns the minimum element in the vector.
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
    public fun countOnes(): SelfType

    /**
     * Returns the number of zeros in the binary representation of each element.
     */
    public fun countZeros(): SelfType

    /**
     * Returns the number of leading zeros in the binary representation of each element.
     */
    public fun leadingZeros(): SelfType

    /**
     * Returns the number of trailing zeros in the binary representation of each element.
     */
    public fun trailingZeros(): SelfType

    /**
     * Returns the number of leading ones in the binary representation of each element.
     */
    public fun leadingOnes(): SelfType

    /**
     * Returns the number of trailing ones in the binary representation of each element.
     */
    public fun trailingOnes(): SelfType
}

public fun <T : SimdCast> Simd<UByte, N>.cast(n: Int): Simd<T> = simd.simdAs(this)

public fun Simd<UByte, N>.wrappingNeg(n: Int): Simd<UByte, N> = (-this.cast()).cast()

public fun Simd<UByte, N>.saturatingAdd(second: Simd<UByte, N>, n: Int): Simd<UByte, N> = simd.simdSaturatingAdd(this, second)

public fun Simd<UByte, N>.saturatingSub(second: Simd<UByte, N>, n: Int): Simd<UByte, N> = simd.simdSaturatingSub(this, second)

public fun Simd<UByte, N>.absDiff(second: Simd<UByte, N>, n: Int): Simd<UByte, N> {
    val max = this.simdMax(second)
    val min = this.simdMin(second)
    return max - min
}

public fun Simd<UByte, N>.reduceSum(n: Int): UByte = simd.simdReduceAddOrdered(this, 0)

public fun Simd<UByte, N>.reduceProduct(n: Int): UByte = simd.simdReduceMulOrdered(this, 1)

public fun Simd<UByte, N>.reduceMax(n: Int): UByte = simd.simdReduceMax(this)

public fun Simd<UByte, N>.reduceMin(n: Int): UByte = simd.simdReduceMin(this)

public fun Simd<UByte, N>.reduceAnd(n: Int): UByte = simd.simdReduceAnd(this)

public fun Simd<UByte, N>.reduceOr(n: Int): UByte = simd.simdReduceOr(this)

public fun Simd<UByte, N>.reduceXor(n: Int): UByte = simd.simdReduceXor(this)

public fun Simd<UByte, N>.swapBytes(n: Int): Simd<UByte, N> = simd.simdBswap(this)

public fun Simd<UByte, N>.reverseBits(n: Int): Simd<UByte, N> = simd.simdBitreverse(this)

public fun Simd<UByte, N>.countOnes(n: Int): Simd<UByte, N> = simd.simdCtpop(this)

public fun Simd<UByte, N>.countZeros(n: Int): Simd<UByte, N> = (!this).countOnes()

public fun Simd<UByte, N>.leadingZeros(n: Int): Simd<UByte, N> = simd.simdCtlz(this)

public fun Simd<UByte, N>.trailingZeros(n: Int): Simd<UByte, N> = simd.simdCttz(this)

public fun Simd<UByte, N>.leadingOnes(n: Int): Simd<UByte, N> = (!this).leadingZeros()

public fun Simd<UByte, N>.trailingOnes(n: Int): Simd<UByte, N> = (!this).trailingZeros()
