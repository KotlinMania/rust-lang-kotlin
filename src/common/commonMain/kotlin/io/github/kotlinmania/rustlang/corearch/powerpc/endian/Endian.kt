// port-lint: source powerpc/altivec.rs
package io.github.kotlinmania.rustlang.corearch.powerpc.endian

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorMule
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorMulo
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorPerm
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorUnsignedChar
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum2sws
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// Endian-biased intrinsics

/**
 * Vector permute.
 */
public fun <T> vecPerm(a: T, b: T, c: VectorUnsignedChar): T where T : VectorPerm<T> {
    // vperm has big-endian bias
    //
    // Xor the mask and flip the arguments
    val d = transmuteWithLayouts<Any?, T>(Simd<UByte>(listOf(255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<T>())
    val c1 = run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    return b.vecVperm(a, c1)
}

/**
 * Vector Sum Across Partial (1/2) Saturated
 */
public fun vecSum2s(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    // vsum2sws has big-endian bias
    //
    // swap the even b elements with the odd ones
    val flip = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<UByte>(listOf(4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
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
