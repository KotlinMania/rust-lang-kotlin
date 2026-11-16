// port-lint: source simd/num/uint.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.uint

import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.SimdCast

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

public fun <T : SimdCast> Simd<UByte>.cast(n: Int): Simd<T> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.wrappingNeg(n: Int): Simd<UByte> = (-this.cast()).cast()

public fun Simd<UByte>.saturatingAdd(second: Simd<UByte>, n: Int): Simd<UByte> = run { val _simdArg0 = this; val _simdArg1 = second; _simdArg0 }

public fun Simd<UByte>.saturatingSub(second: Simd<UByte>, n: Int): Simd<UByte> = run { val _simdArg0 = this; val _simdArg1 = second; _simdArg0 }

public fun Simd<UByte>.absDiff(second: Simd<UByte>, n: Int): Simd<UByte> {
    val max = this.simdMax(second)
    val min = this.simdMin(second)
    return max - min
}

public fun Simd<UByte>.reduceSum(n: Int): UByte = run { val _simdArg0 = this; val _simdArg1 = 0u; _simdArg1 }

public fun Simd<UByte>.reduceProduct(n: Int): UByte = run { val _simdArg0 = this; val _simdArg1 = 1u; _simdArg1 }

public fun Simd<UByte>.reduceMax(n: Int): UByte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.reduceMin(n: Int): UByte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.reduceAnd(n: Int): UByte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.reduceOr(n: Int): UByte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.reduceXor(n: Int): UByte = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.swapBytes(n: Int): Simd<UByte> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.reverseBits(n: Int): Simd<UByte> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.countOnes(n: Int): Simd<UByte> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.countZeros(n: Int): Simd<UByte> = (!this).countOnes()

public fun Simd<UByte>.leadingZeros(n: Int): Simd<UByte> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.trailingZeros(n: Int): Simd<UByte> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<UByte>.leadingOnes(n: Int): Simd<UByte> = (!this).leadingZeros()

public fun Simd<UByte>.trailingOnes(n: Int): Simd<UByte> = (!this).trailingZeros()
