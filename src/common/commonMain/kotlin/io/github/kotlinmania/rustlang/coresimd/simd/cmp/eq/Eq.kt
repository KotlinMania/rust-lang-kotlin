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

public fun Simd<F16, N>.simdEq(other: Simd<F16, N>, n: Int): Mask<Mask> = Mask.fromSimdUnchecked(simd.simdEq(this, other))

public fun Simd<F16, N>.simdNe(other: Simd<F16, N>, n: Int): Mask<Mask> = Mask.fromSimdUnchecked(simd.simdNe(this, other))

public fun Mask<Byte, N>.simdEq(other: Mask<Byte, N>, n: Int): Mask<Byte, N> = Mask.fromSimdUnchecked(simd.simdEq(this.toSimd(), other.toSimd()))

public fun Mask<Byte, N>.simdNe(other: Mask<Byte, N>, n: Int): Mask<Byte, N> = Mask.fromSimdUnchecked(simd.simdNe(this.toSimd(), other.toSimd()))
