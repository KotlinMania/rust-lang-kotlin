// port-lint: source simd/cmp/ord.rs
package io.github.kotlinmania.rustlang.coresimd.simd.cmp.ord

import io.github.kotlinmania.rustlang.Mask
import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.simd.cmp.eq.SimdPartialEq

/**
 * Parallel `PartialOrd`.
 */
public interface SimdPartialOrd<T> : SimdPartialEq<T, Any?> {
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
public interface SimdOrd<T> : SimdPartialOrd<T> {
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

public fun Simd<UByte, N>.simdLt(other: Simd<UByte, N>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte, N>.simdLe(other: Simd<UByte, N>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte, N>.simdGt(other: Simd<UByte, N>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte, N>.simdGe(other: Simd<UByte, N>, n: Int): Mask = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<UByte, N>.simdMax(other: Simd<UByte, N>, n: Int): Simd<UByte, N> = this.simdLt(other).select(other, this)

public fun Simd<UByte, N>.simdMin(other: Simd<UByte, N>, n: Int): Simd<UByte, N> = this.simdGt(other).select(other, this)

public fun Simd<UByte, N>.simdClamp(min: Simd<UByte, N>, max: Simd<UByte, N>, n: Int): Simd<UByte, N> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    return this.simdMax(min).simdMin(max)
}

public fun Mask<Byte, N>.simdLt(other: Mask<Byte, N>, n: Int): Mask = this.inv() and other

public fun Mask<Byte, N>.simdLe(other: Mask<Byte, N>, n: Int): Mask = this.inv() or other

public fun Mask<Byte, N>.simdGt(other: Mask<Byte, N>, n: Int): Mask = this and other.inv()

public fun Mask<Byte, N>.simdGe(other: Mask<Byte, N>, n: Int): Mask = this or other.inv()

public fun Mask<Byte, N>.simdMax(other: Mask<Byte, N>, n: Int): Mask<Byte, N> = this or other

public fun Mask<Byte, N>.simdMin(other: Mask<Byte, N>, n: Int): Mask<Byte, N> = this and other

public fun Mask<Byte, N>.simdClamp(min: Mask<Byte, N>, max: Mask<Byte, N>, n: Int): Mask<Byte, N> {
    check(min.simdLe(max).all()) { "each element in `min` must be less than or equal to the corresponding element in `max`" }
    return this.simdMax(min).simdMin(max)
}
