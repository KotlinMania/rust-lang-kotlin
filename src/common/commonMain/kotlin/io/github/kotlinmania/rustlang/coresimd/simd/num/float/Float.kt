// port-lint: source simd/num/float.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.float

import io.github.kotlinmania.rustlang.Mask
import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.SimdCast
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf

/**
 * Operations on SIMD vectors of floats.
 */
public interface SimdFloat<SelfType, out Mask, Scalar, Bits, out Cast> {
    /**
     * Performs elementwise conversion of this vector's elements to another SIMD-valid type.
     *
     * This follows the semantics of Rust's `as` conversion for floats (truncating or saturating
     * at the limits) for each element.
     *
     * # Example
     * ```kotlin
     * val floats: Simd<Float> = Simd.fromArray(floatArrayOf(1.9f, -4.5f, Float.POSITIVE_INFINITY, Float.NaN))
     * val ints = floats.cast()
     * check(ints == Simd.fromArray(intArrayOf(1, -4, Int.MAX_VALUE, 0)))
     * // Formally equivalent, but `Simd.cast` can optimize better.
     * check(ints == Simd.fromArray(floats.toArray().map({ x -> x.toInt() })))
     * // The float conversion does not round-trip.
     * val floatsAgain = ints.cast()
     * check(floats != floatsAgain)
     * check(floatsAgain == Simd.fromArray(listOf(1.0, -4.0, 2147483647.0, 0.0)))
     * ```
     */
    public fun <T : SimdCast> cast(): Cast

    /**
     * Rounds toward zero and converts to the same-width integer type, assuming that
     * the value is finite and fits in that type.
     *
     * # Safety
     * The value must:
     *
     * * Not be NaN
     * * Not be infinite
     * * Be representable in the return type, after truncating off its fractional part
     *
     * If these requirements are infeasible or costly, consider using the safe function [cast],
     * which saturates on conversion.
     *
     * [cast]: Simd.cast
     */
    public fun <I : SimdCast> toIntUnchecked(): Cast

    /**
     * Raw transmutation to an unsigned integer vector type with the
     * same size and number of elements.
     */
    public fun toBits(): Bits

    /**
     * Raw transmutation from an unsigned integer vector type with the
     * same size and number of elements.
     */
    public fun fromBits(bits: Bits): SelfType

    /**
     * Produces a vector where every element has the absolute value of the
     * equivalently-indexed element in `self`.
     */
    public fun abs(): SelfType

    /**
     * Takes the reciprocal (inverse) of each element, `1 / x`.
     */
    public fun recip(): SelfType

    /**
     * Converts each element from radians to degrees.
     */
    public fun toDegrees(): SelfType

    /**
     * Converts each element from degrees to radians.
     */
    public fun toRadians(): SelfType

    /**
     * Returns true for each element if it has a positive sign, including
     * `+ 0.0`, `NaN`s with positive sign bit and positive infinity.
     */
    public fun isSignPositive(): Mask

    /**
     * Returns true for each element if it has a negative sign, including
     * `- 0.0`, `NaN`s with negative sign bit and negative infinity.
     */
    public fun isSignNegative(): Mask

    /**
     * Returns true for each element if its value is `NaN`.
     */
    public fun isNan(): Mask

    /**
     * Returns true for each element if its value is positive infinity or negative infinity.
     */
    public fun isInfinite(): Mask

    /**
     * Returns true for each element if its value is neither infinite nor `NaN`.
     */
    public fun isFinite(): Mask

    /**
     * Returns true for each element if its value is subnormal.
     */
    public fun isSubnormal(): Mask

    /**
     * Returns true for each element if its value is neither zero, infinite,
     * subnormal, nor `NaN`.
     */
    public fun isNormal(): Mask

    /**
     * Replaces each element with a number that represents its sign.
     *
     * * `1.0` if the number is positive, `+ 0.0`, or `INFINITY`
     * * `- 1.0` if the number is negative, `- 0.0`, or `NEG_INFINITY`
     * * `NAN` if the number is `NAN`
     */
    public fun signum(): SelfType

    /**
     * Returns each element with the magnitude of `self` and the sign of `sign`.
     *
     * For any element containing a `NAN`, a `NAN` with the sign of `sign` is returned.
     */
    public fun copysign(sign: SelfType): SelfType

    /**
     * Returns the minimum of each element.
     *
     * If one of the values is `NAN`, then the other value is returned.
     */
    public fun simdMin(other: SelfType): SelfType

    /**
     * Returns the maximum of each element.
     *
     * If one of the values is `NAN`, then the other value is returned.
     */
    public fun simdMax(other: SelfType): SelfType

    /**
     * Restrict each element to a certain interval unless it is NaN.
     *
     * For each element in `self`, returns the corresponding element in `max` if the element is
     * greater than `max`, and the corresponding element in `min` if the element is less
     * than `min`.  Otherwise returns the element in `self`.
     */
    public fun simdClamp(min: SelfType, max: SelfType): SelfType

    /**
     * Returns the sum of the elements of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1.0, 2.0))
     * check(v.reduceSum() == 3.0)
     * ```
     */
    public fun reduceSum(): Scalar

    /**
     * Reducing multiply.  Returns the product of the elements of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(3.0, 4.0))
     * check(v.reduceProduct() == 12.0)
     * ```
     */
    public fun reduceProduct(): Scalar

    /**
     * Returns the maximum element in the vector.
     *
     * Returns values based on equality, so a vector containing both `0.` and `- 0.` may
     * return either.
     *
     * This function will not return `NaN` unless all elements are `NaN`.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(1.0, 2.0))
     * check(v.reduceMax() == 2.0)
     * // NaN values are skipped...
     * val v1 = fromArray(floatArrayOf(1.0f, Float.NaN))
     * check(v1.reduceMax() == 1.0)
     * // ...unless all values are NaN
     * val v2 = fromArray(floatArrayOf(Float.NaN, Float.NaN))
     * check(v2.reduceMax().isNan())
     * ```
     */
    public fun reduceMax(): Scalar

    /**
     * Returns the minimum element in the vector.
     *
     * Returns values based on equality, so a vector containing both `0.` and `- 0.` may
     * return either.
     *
     * This function will not return `NaN` unless all elements are `NaN`.
     *
     * # Examples
     *
     * ```kotlin
     * val v = fromArray(listOf(3.0, 7.0))
     * check(v.reduceMin() == 3.0)
     * // NaN values are skipped...
     * val v1 = fromArray(floatArrayOf(1.0f, Float.NaN))
     * check(v1.reduceMin() == 1.0)
     * // ...unless all values are NaN
     * val v2 = fromArray(floatArrayOf(Float.NaN, Float.NaN))
     * check(v2.reduceMin().isNan())
     * ```
     */
    public fun reduceMin(): Scalar
}

public fun <T : SimdCast> Simd<F16, N>.cast(n: Int): Simd<T> = run { val _simdArg0 = this; _simdArg0 }

public fun <I : SimdCast> Simd<F16, N>.toIntUnchecked(n: Int): Simd<T> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<F16, N>.toBits(n: Int): Simd<UShort> {
    check(sizeOf<Simd<F16, N>>(typeLayoutT = ObjectSlotLayout<Simd<F16, N>>()) == sizeOf<Simd<UShort>>(typeLayoutT = ObjectSlotLayout<Simd<UShort>>()))
    return transmuteCopy(this)
}

public fun simdFloatFromBitsSimdF16N(bits: Simd<UShort>, n: Int): Simd<F16, N> {
    check(sizeOf<Simd<F16, N>>(typeLayoutT = ObjectSlotLayout<Simd<F16, N>>()) == sizeOf<Simd<UShort>>(typeLayoutT = ObjectSlotLayout<Simd<UShort>>()))
    return transmuteCopy(bits)
}

public fun Simd<F16, N>.abs(n: Int): Simd<F16, N> = run { val _simdArg0 = this; _simdArg0 }

public fun Simd<F16, N>.recip(n: Int): Simd<F16, N> = Simd.splat(1.0) / this

public fun Simd<F16, N>.toDegrees(n: Int): Simd<F16, N> = this * Simd.splat(Simd.Scalar.toDegrees(1.0))

public fun Simd<F16, N>.toRadians(n: Int): Simd<F16, N> = this * Simd.splat(Simd.Scalar.toRadians(1.0))

public fun Simd<F16, N>.isSignPositive(n: Int): Mask<Mask> = !this.isSignNegative()

public fun Simd<F16, N>.isSignNegative(n: Int): Mask<Mask> {
    val signBits = this.toBits() and Simd.splat((0.inv() shr 1) + 1)
    return signBits.simdGt(Simd.splat(0))
}

public fun Simd<F16, N>.isNan(n: Int): Mask<Mask> = this.simdNe(this)

public fun Simd<F16, N>.isInfinite(n: Int): Mask<Mask> = this.abs().simdEq(Simd.splat(Simd.Scalar.INFINITY))

public fun Simd<F16, N>.isFinite(n: Int): Mask<Mask> = this.abs().simdLt(Simd.splat(Simd.Scalar.INFINITY))

public fun Simd<F16, N>.isSubnormal(n: Int): Mask<Mask> {
    val notZero = this.abs().toBits().simdNe(Simd.splat(0.0).toBits())
    return notZero and (this.toBits() and Simd.splat(Simd.Scalar.INFINITY).toBits()).simdEq(Simd.splat(0))
}

public fun Simd<F16, N>.isNormal(n: Int): Mask<Mask> = (this.abs().simdEq(Simd.splat(0.0)) or this.isNan() or this.isSubnormal() or this.isInfinite()).inv()

public fun Simd<F16, N>.signum(n: Int): Simd<F16, N> = this.isNan().select(Simd.splat(Simd.Scalar.NAN), Simd.splat(1.0).copysign(this))

public fun Simd<F16, N>.copysign(sign: Simd<F16, N>, n: Int): Simd<F16, N> {
    val signBit = sign.toBits() and Simd.splat(-0.0).toBits()
    val magnitude = this.toBits() and Simd.splat(-0.0).toBits().inv()
    return Simd.fromBits(signBit or magnitude)
}

public fun Simd<F16, N>.simdMin(other: Simd<F16, N>, n: Int): Simd<F16, N> = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

public fun Simd<F16, N>.simdMax(other: Simd<F16, N>, n: Int): Simd<F16, N> = run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 }

public fun Simd<F16, N>.simdClamp(min: Simd<F16, N>, max: Simd<F16, N>, n: Int): Simd<F16, N> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    var x = this
    x = x.simdLt(min).select(min, x)
    x = x.simdGt(max).select(max, x)
    return x
}

public fun Simd<F16, N>.reduceSum(n: Int): F16 =
    if (false) {
        this.asArray().iter().sum()
    } else {
        run { val _simdArg0 = this; val _simdArg1 = -F16.fromFloat(0.0f); _simdArg1 }
    }

public fun Simd<F16, N>.reduceProduct(n: Int): F16 =
    if (false) {
        this.asArray().iter().product()
    } else {
        run { val _simdArg0 = this; val _simdArg1 = F16.fromFloat(1.0f); _simdArg1 }
    }

public fun Simd<F16, N>.reduceMax(n: Int): F16 = this.asArray().iter().copied().fold(Simd.Scalar.NAN, Simd.Scalar::max)

public fun Simd<F16, N>.reduceMin(n: Int): F16 = this.asArray().iter().copied().fold(Simd.Scalar.NAN, Simd.Scalar::min)

public fun simdFloatFromBitsSimdFloatN(bits: Simd<UInt>, n: Int): Simd<Float, N> {
    check(sizeOf<Simd<Float, N>>(typeLayoutT = ObjectSlotLayout<Simd<Float, N>>()) == sizeOf<Simd<UInt>>(typeLayoutT = ObjectSlotLayout<Simd<UInt>>()))
    return transmuteCopy(bits)
}

public fun simdFloatFromBitsSimdDoubleN(bits: Simd<ULong>, n: Int): Simd<Double, N> {
    check(sizeOf<Simd<Double, N>>(typeLayoutT = ObjectSlotLayout<Simd<Double, N>>()) == sizeOf<Simd<ULong>>(typeLayoutT = ObjectSlotLayout<Simd<ULong>>()))
    return transmuteCopy(bits)
}
