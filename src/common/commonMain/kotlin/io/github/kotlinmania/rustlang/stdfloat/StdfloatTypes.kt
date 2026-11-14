package io.github.kotlinmania.rustlang.stdfloat

import io.github.kotlinmania.intrinsics.simdCeil
import io.github.kotlinmania.intrinsics.simdDiv
import io.github.kotlinmania.intrinsics.simdFcos
import io.github.kotlinmania.intrinsics.simdFexp
import io.github.kotlinmania.intrinsics.simdFexp2
import io.github.kotlinmania.intrinsics.simdFlog
import io.github.kotlinmania.intrinsics.simdFlog10
import io.github.kotlinmania.intrinsics.simdFlog2
import io.github.kotlinmania.intrinsics.simdFloor
import io.github.kotlinmania.intrinsics.simdFma
import io.github.kotlinmania.intrinsics.simdFsin
import io.github.kotlinmania.intrinsics.simdFsqrt
import io.github.kotlinmania.intrinsics.simdRound
import io.github.kotlinmania.intrinsics.simdRoundTiesEven
import io.github.kotlinmania.intrinsics.simdTrunc

/**
 * This trait provides a possibly-temporary implementation of float functions
 * that may, in the absence of hardware support, canonicalize to calling an
 * operating system's `math.h` dynamically-loaded library (also known as a
 * shared object). As these conditionally require runtime support, they
 * should only appear in binaries built assuming OS support: `std`.
 *
 * However, there is no reason SIMD types, in general, need OS support,
 * as for many architectures an embedded binary may simply configure that
 * support itself. This means these types must be visible in `core`
 * but have these functions available in `std`.
 *
 * [f32] and [f64] achieve a similar trick by using "lang items", but
 * due to compiler limitations, it is harder to implement this approach for
 * abstract data types like [Simd]. From that need, this trait is born.
 *
 * It is possible this trait will be replaced in some manner in the future,
 * when either the compiler or its supporting runtime functions are improved.
 * For now this trait is available to permit experimentation with SIMD float
 * operations that may lack hardware support, such as `mulAdd`.
 */
public interface StdFloat<T> {
    /**
     * Elementwise fused multiply-add. Computes `(self * a) + b` with only one rounding error,
     * yielding a more accurate result than an unfused multiply-add.
     *
     * Using `mulAdd` *may* be more performant than an unfused multiply-add if the target
     * architecture has a dedicated `fma` CPU instruction.  However, this is not always
     * true, and will be heavily dependent on designing algorithms with specific target
     * hardware in mind.
     */
    public fun mulAdd(a: T, b: T): T = simdFma(this, a, b)

    /**
     * Produces a vector where every element has the square root value
     * of the equivalently-indexed element in `self`
     */
    public fun sqrt(): T = simdFsqrt(this)

    /**
     * Produces a vector where every element has the sine of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun sin(): T = simdFsin(this)

    /**
     * Produces a vector where every element has the cosine of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun cos(): T = simdFcos(this)

    /**
     * Produces a vector where every element has the exponential (base e) of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun exp(): T = simdFexp(this)

    /**
     * Produces a vector where every element has the exponential (base 2) of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun exp2(): T = simdFexp2(this)

    /**
     * Produces a vector where every element has the natural logarithm of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun ln(): T = simdFlog(this)

    /**
     * Produces a vector where every element has the logarithm with respect to an arbitrary
     * in the equivalently-indexed elements in `self` and `base`.
     */
    public fun log(base: T): T = simdDiv(this.ln(), base.ln())

    /**
     * Produces a vector where every element has the base-2 logarithm of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun log2(): T = simdFlog2(this)

    /**
     * Produces a vector where every element has the base-10 logarithm of the value
     * in the equivalently-indexed element in `self`.
     */
    public fun log10(): T = simdFlog10(this)

    /**
     * Returns the smallest integer greater than or equal to each element.
     */
    public fun ceil(): T = simdCeil(this)

    /**
     * Returns the largest integer value less than or equal to each element.
     */
    public fun floor(): T = simdFloor(this)

    /**
     * Rounds to the nearest integer value. Ties round away from zero.
     */
    public fun round(): T = simdRound(this)

    /**
     * Returns the floating point's integer value, with its fractional part removed.
     */
    public fun trunc(): T = simdTrunc(this)

    /**
     * Rounds each element to the nearest integer-valued float.
     * Ties are resolved by rounding to the number with an even least significant digit.
     */
    public fun roundTiesEven(): T = simdRoundTiesEven(this)

    /**
     * Returns the floating point's fractional value, with its integer part removed.
     */
    public fun fract(): T
}
