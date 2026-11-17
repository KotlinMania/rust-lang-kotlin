// port-lint: source powerpc/altivec.rs
package io.github.kotlinmania.rustlang.corearch.powerpc.endian

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorMule
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorMulo
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorPerm
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorUnsignedChar
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum2sws

// Endian-biased intrinsics

/**
 * Vector permute.
 */
public fun <T> vecPerm(a: T, b: T, c: VectorUnsignedChar): T where T : VectorPerm<T> {
    // vperm has big-endian bias
    //
    // Xor the mask and flip the arguments
    val d = transmute(u8x16.new(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255))
    val c1 = run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    return b.vecVperm(a, c1)
}

/**
 * Vector Sum Across Partial (1/2) Saturated
 */
public fun vecSum2s(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    // vsum2sws has big-endian bias
    //
    // swap the even b elements with the odd ones
    val flip = transmute(u8x16.new(4, 5, 6, 7, 0, 1, 2, 3, 12, 13, 14, 15, 8, 9, 10, 11))
    val b1 = vecPerm(b, b, flip)
    val c = vsum2sws(a, b1)
    return vecPerm(c, c, flip)
}

// Even and Odd are swapped in little-endian

/**
 * Vector Multiply Even
 */
public fun <T, U> vecMule(a: T, b: T): U where T : VectorMulo<U, T> = a.vecMulo(b)

/**
 * Vector Multiply Odd
 */
public fun <T, U> vecMulo(a: T, b: T): U where T : VectorMule<U, T> = a.vecMule(b)
