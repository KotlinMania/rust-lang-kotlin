// port-lint: source simd/cmp/eq.rs
package io.github.kotlinmania.rustlang.coresimd.simd.cmp.eq

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.coresimd.Mask
import io.github.kotlinmania.rustlang.coresimd.Simd

/**
 * Parallel `PartialEq`.
 */
public interface SimdPartialEq<T, out Mask> {
    /**
     * Test if each element is equal to the corresponding element in `other`.
     */
    public fun simdEq(other: T): Mask

    /**
     * Test if each element is not equal to the corresponding element in `other`.
     */
    public fun simdNe(other: T): Mask
}

public fun Simd<F16>.simdEq(other: Simd<F16>, n: Int): Mask<Mask> = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Simd<F16>.simdNe(other: Simd<F16>, n: Int): Mask<Mask> = Mask.fromSimdUnchecked(run { val _simdArg0 = this; val _simdArg1 = other; _simdArg0 })

public fun Mask<Byte>.simdEq(other: Mask<Byte>, n: Int): Mask<Byte> = Mask.fromSimdUnchecked(run { val _simdArg0 = this.toSimd(); val _simdArg1 = other.toSimd(); _simdArg0 })

public fun Mask<Byte>.simdNe(other: Mask<Byte>, n: Int): Mask<Byte> = Mask.fromSimdUnchecked(run { val _simdArg0 = this.toSimd(); val _simdArg1 = other.toSimd(); _simdArg0 })
