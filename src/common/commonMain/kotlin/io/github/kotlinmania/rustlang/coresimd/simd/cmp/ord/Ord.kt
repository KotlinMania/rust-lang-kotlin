// port-lint: source simd/cmp/ord.rs
package io.github.kotlinmania.rustlang.coresimd.simd.cmp.ord

import io.github.kotlinmania.rustlang.coresimd.Mask
import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.simd.cmp.eq.SimdPartialEq

/**
 * Parallel `PartialOrd`.
 */
public interface SimdPartialOrd<T, out Mask> : SimdPartialEq<T, Mask> {
    /**
     * Test if each element is less than the corresponding element in `other`.
     */
    public fun simdLt(other: T): Mask

    /**
     * Test if each element is less than or equal to the corresponding element in `other`.
     */
    public fun simdLe(other: T): Mask

    /**
     * Test if each element is greater than the corresponding element in `other`.
     */
    public fun simdGt(other: T): Mask

    /**
     * Test if each element is greater than or equal to the corresponding element in `other`.
     */
    public fun simdGe(other: T): Mask
}

/**
 * Parallel `Ord`.
 */
public interface SimdOrd<T, out Mask> : SimdPartialOrd<T, Mask> {
    /**
     * Returns the element-wise maximum with `other`.
     */
    public fun simdMax(other: T): T

    /**
     * Returns the element-wise minimum with `other`.
     */
    public fun simdMin(other: T): T

    /**
     * Restrict each element to a certain interval.
     *
     * For each element, returns `max` if `self` is greater than `max`, and `min` if `self` is
     * less than `min`. Otherwise returns `self`.
     *
     * # Panics
     *
     * Panics if `min> max` on any element.
     */
    public fun simdClamp(min: T, max: T): T
}

public fun Simd<UByte>.simdLt(other: Simd<UByte>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte>.simdLe(other: Simd<UByte>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte>.simdGt(other: Simd<UByte>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte>.simdGe(other: Simd<UByte>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte>.simdMax(other: Simd<UByte>, n: Int): Simd<UByte> = this.simdLt(other).select(other, this)

public fun Simd<UByte>.simdMin(other: Simd<UByte>, n: Int): Simd<UByte> = this.simdGt(other).select(other, this)

public fun Simd<UByte>.simdClamp(min: Simd<UByte>, max: Simd<UByte>, n: Int): Simd<UByte> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    return this.simdMax(min).simdMin(max)
}

public fun Mask<Byte>.simdLt(other: Mask<Byte>, n: Int): Mask = this.inv() and other

public fun Mask<Byte>.simdLe(other: Mask<Byte>, n: Int): Mask = this.inv() or other

public fun Mask<Byte>.simdGt(other: Mask<Byte>, n: Int): Mask = this and other.inv()

public fun Mask<Byte>.simdGe(other: Mask<Byte>, n: Int): Mask = this or other.inv()

public fun Mask<Byte>.simdMax(other: Mask<Byte>, n: Int): Mask<Byte> = this or other

public fun Mask<Byte>.simdMin(other: Mask<Byte>, n: Int): Mask<Byte> = this and other

public fun Mask<Byte>.simdClamp(min: Mask<Byte>, max: Mask<Byte>, n: Int): Mask<Byte> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    return this.simdMax(min).simdMin(max)
}
