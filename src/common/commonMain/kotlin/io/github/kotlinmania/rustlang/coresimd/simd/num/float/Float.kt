// port-lint: source simd/num/float.rs
package io.github.kotlinmania.rustlang.coresimd.simd.num.float

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.coresimd.Mask
import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.SimdCast

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
    public fun <Mask, T : SimdCast<Mask>> cast(): Cast

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
    public fun <Mask, I : SimdCast<Mask>> toIntUnchecked(): Cast

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
     * val v = Simd<Float>((listOf(1.0, 2.0)).toList())
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
     * val v = Simd<Float>((listOf(3.0, 4.0)).toList())
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
     * val v = Simd<Float>((listOf(1.0, 2.0)).toList())
     * check(v.reduceMax() == 2.0)
     * // NaN values are skipped...
     * val v1 = Simd<Float>((floatArrayOf(1.0f, Float.NaN)).toList())
     * check(v1.reduceMax() == 1.0)
     * // ...unless all values are NaN
     * val v2 = Simd<Float>((floatArrayOf(Float.NaN, Float.NaN)).toList())
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
     * val v = Simd<Float>((listOf(3.0, 7.0)).toList())
     * check(v.reduceMin() == 3.0)
     * // NaN values are skipped...
     * val v1 = Simd<Float>((floatArrayOf(1.0f, Float.NaN)).toList())
     * check(v1.reduceMin() == 1.0)
     * // ...unless all values are NaN
     * val v2 = Simd<Float>((floatArrayOf(Float.NaN, Float.NaN)).toList())
     * check(v2.reduceMin().isNan())
     * ```
     */
    public fun reduceMin(): Scalar
}

public fun <Mask, T : SimdCast<Mask>> Simd<F16>.cast(n: Int = 0): Simd<T> = run { val simdArg0 = this; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<T>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); res } }

public fun <Mask, I : SimdCast<Mask>> Simd<F16>.toIntUnchecked(n: Int = 0): Simd<T> = run { val simdArg0 = this; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<T>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<T>>()).alignBytes)); res } }

public fun Simd<F16>.toBits(n: Int = 0): Simd<UShort> {
    check(sizeOf<Simd<F16>>(typeLayoutT = UnresolvedTypeLayout<Simd<F16>>()) == sizeOf<Simd<UShort>>(typeLayoutT = UnresolvedTypeLayout<Simd<UShort>>()))
    return transmuteCopy(this)
}

public fun simdFloatFromBitsSimdF16N(bits: Simd<UShort>, n: Int = 0): Simd<F16> {
    check(sizeOf<Simd<F16>>(typeLayoutT = UnresolvedTypeLayout<Simd<F16>>()) == sizeOf<Simd<UShort>>(typeLayoutT = UnresolvedTypeLayout<Simd<UShort>>()))
    return transmuteCopy(bits)
}

public fun Simd<F16>.abs(n: Int = 0): Simd<F16> = run { val simdArg0 = this; simdArg0 }

public fun Simd<F16>.recip(n: Int = 0): Simd<F16> = Simd.splat(1.0, n) / this

public fun Simd<F16>.toDegrees(n: Int = 0): Simd<F16> = this * Simd.splat(Simd.Scalar.toDegrees(1.0), n)

public fun Simd<F16>.toRadians(n: Int = 0): Simd<F16> = this * Simd.splat(Simd.Scalar.toRadians(1.0), n)

public fun Simd<F16>.isSignPositive(n: Int = 0): Mask<Mask> = !this.isSignNegative(n)

public fun Simd<F16>.isSignNegative(n: Int = 0): Mask<Mask> {
    val signBits = this.toBits(n) and Simd.splat((0.inv() shr 1) + 1, n)
    return signBits.simdGt(Simd.splat(0, n))
}

public fun Simd<F16>.isNan(n: Int = 0): Mask<Mask> = this.simdNe(this)

public fun Simd<F16>.isInfinite(n: Int = 0): Mask<Mask> = this.abs(n).simdEq(Simd.splat(Simd.Scalar.INFINITY, n))

public fun Simd<F16>.isFinite(n: Int = 0): Mask<Mask> = this.abs(n).simdLt(Simd.splat(Simd.Scalar.INFINITY, n))

public fun Simd<F16>.isSubnormal(n: Int = 0): Mask<Mask> {
    val notZero = this.abs(n).toBits(n).simdNe(Simd.splat(0.0, n).toBits())
    return notZero and (this.toBits(n) and Simd.splat(Simd.Scalar.INFINITY, n).toBits()).simdEq(Simd.splat(0, n))
}

public fun Simd<F16>.isNormal(n: Int = 0): Mask<Mask> = (this.abs(n).simdEq(Simd.splat(0.0, n)) or this.isNan(n) or this.isSubnormal(n) or this.isInfinite(n)).inv()

public fun Simd<F16>.signum(n: Int = 0): Simd<F16> = this.isNan(n).select(Simd.splat(Simd.Scalar.NAN, n), Simd.splat(1.0, n).copysign(this))

public fun Simd<F16>.copysign(sign: Simd<F16>, n: Int = 0): Simd<F16> {
    val signBit = sign.toBits(n) and Simd.splat(-0.0, n).toBits()
    val magnitude = this.toBits(n) and Simd.splat(-0.0, n).toBits().inv()
    return Simd.fromBits(signBit or magnitude, n)
}

public fun Simd<F16>.simdMin(other: Simd<F16>, n: Int = 0): Simd<F16> = run { val simdArg0 = this; val simdArg1 = other; simdArg0 }

public fun Simd<F16>.simdMax(other: Simd<F16>, n: Int = 0): Simd<F16> = run { val simdArg0 = this; val simdArg1 = other; simdArg0 }

public fun Simd<F16>.simdClamp(min: Simd<F16>, max: Simd<F16>, n: Int = 0): Simd<F16> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    var x = this
    x = x.simdLt(min).select(min, x)
    x = x.simdGt(max).select(max, x)
    return x
}

public fun Simd<F16>.reduceSum(n: Int = 0): F16 =
    if (false) {
        this.asArray().iter().sum()
    } else {
        run { val simdArg0 = this; val simdArg1 = -F16.fromFloat(0.0f); simdArg1 }
    }

public fun Simd<F16>.reduceProduct(n: Int = 0): F16 =
    if (false) {
        this.asArray().iter().product()
    } else {
        run { val simdArg0 = this; val simdArg1 = F16.fromFloat(1.0f); simdArg1 }
    }

public fun Simd<F16>.reduceMax(n: Int = 0): F16 = this.asArray().iter().copied().fold(Simd.Scalar.NAN, Simd.Scalar::max)

public fun Simd<F16>.reduceMin(n: Int = 0): F16 = this.asArray().iter().copied().fold(Simd.Scalar.NAN, Simd.Scalar::min)

public fun simdFloatFromBitsSimdFloatN(bits: Simd<UInt>, n: Int = 0): Simd<Float> {
    check(sizeOf<Simd<Float>>(typeLayoutT = UnresolvedTypeLayout<Simd<Float>>()) == sizeOf<Simd<UInt>>(typeLayoutT = UnresolvedTypeLayout<Simd<UInt>>()))
    return transmuteCopy(bits)
}

public fun simdFloatFromBitsSimdDoubleN(bits: Simd<ULong>, n: Int = 0): Simd<Double> {
    check(sizeOf<Simd<Double>>(typeLayoutT = UnresolvedTypeLayout<Simd<Double>>()) == sizeOf<Simd<ULong>>(typeLayoutT = UnresolvedTypeLayout<Simd<ULong>>()))
    return transmuteCopy(bits)
}
