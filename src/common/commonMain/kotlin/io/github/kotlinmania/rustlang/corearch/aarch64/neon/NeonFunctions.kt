@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64.neon

import io.github.kotlinmania.rustlang.core.intrinsics.*
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.P64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2T
import io.github.kotlinmania.rustlang.corearch.simd.Simd

/**
 * Load multiple single-element structures to one, two, three, or four registers
 */
public fun vld1DupF64(ptr: ConstPtr): Float64x1T = vld1F64(ptr)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 */
public fun vld1qDupF64(ptr: ConstPtr): Float64x2T {
    val x = vld1qLaneF64(ptr, transmute(Simd<Double>(List(2) { 0.0.toDouble() })), 0)
    return run { val simdArg0 = x; val simdArg1 = x; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u)); Float64x2T() }
}

/**
 * Load one single-element structure to one lane of one register.
 */
public fun vld1LaneF64(ptr: ConstPtr, src: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return run { val simdArg0 = src; val simdArg1 = lane.toUInt(); val simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64); simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 */
public fun vld1qLaneF64(ptr: ConstPtr, src: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = src; val simdArg1 = lane.toUInt(); val simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64); simdArg0 }
}

/**
 * Bitwise Select instructions. This instruction sets each bit in the destination SIMD&FP register
 * to the corresponding bit from the first source SIMD&FP register when the original
 * destination bit was 1, otherwise from the second source SIMD&FP register.
 */
public fun vbslF64(a: Uint64x1T, b: Float64x1T, c: Float64x1T): Float64x1T {
    val not = Int64x1T.splat(-1)
    return transmute(run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Float64x1T, Uint64x1T>(b, Float64x1T.typeLayout(), UnresolvedTypeLayout<Uint64x1T>()); simdArg0 }; val simdArg1 = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmute(not); simdArg0 }; val simdArg1 = transmute(c); simdArg0 }; simdArg0 })
}

/**
 * Bitwise Select.
 */
public fun vbslP64(a: Poly64x1T, b: Poly64x1T, c: Poly64x1T): Poly64x1T {
    val not = Int64x1T.splat(-1)
    return run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }; val simdArg1 = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmute(not); simdArg0 }; val simdArg1 = c; simdArg0 }; simdArg0 }
}

/**
 * Bitwise Select. (128-bit)
 */
public fun vbslqF64(a: Uint64x2T, b: Float64x2T, c: Float64x2T): Float64x2T {
    val not = Int64x2T.splat(-1)
    return transmute(run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Float64x2T, Uint64x2T>(b, Float64x2T.typeLayout(), UnresolvedTypeLayout<Uint64x2T>()); simdArg0 }; val simdArg1 = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmute(not); simdArg0 }; val simdArg1 = transmute(c); simdArg0 }; simdArg0 })
}

/**
 * Bitwise Select. (128-bit)
 */
public fun vbslqP64(a: Poly64x2T, b: Poly64x2T, c: Poly64x2T): Poly64x2T {
    val not = Int64x2T.splat(-1)
    return run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }; val simdArg1 = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = transmute(not); simdArg0 }; val simdArg1 = c; simdArg0 }; simdArg0 }
}

/**
 * Vector add.
 */
public fun vaddF64(a: Float64x1T, b: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Vector add.
 */
public fun vaddqF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Vector add.
 */
public fun vaddS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Vector add.
 */
public fun vaddU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Vector add.
 */
public fun vadddS64(a: Long, b: Long): Long = (a + (b.toLong()))

/**
 * Vector add.
 */
public fun vadddU64(a: ULong, b: ULong): ULong = (a + (b.toULong()))

/**
 * Extract vector from pair of vectors
 */
public fun vextP64(a: Poly64x1T, b: Poly64x1T, n: Int): Poly64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Extract vector from pair of vectors
 */
public fun vextF64(a: Float64x1T, b: Float64x1T, n: Int): Float64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Duplicate vector element to vector or scalar
 */
public fun vdupNP64(value: P64): Poly64x1T = transmute(Simd<ULong>(listOf(value)))

/**
 * Duplicate vector element to vector or scalar
 */
public fun vdupNF64(value: Double): Float64x1T = Float64x1T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vdupqNP64(value: P64): Poly64x2T = transmute(Simd<ULong>(listOf(value, value)))

/**
 * Duplicate vector element to vector or scalar
 */
public fun vdupqNF64(value: Double): Float64x2T = Float64x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vmovNP64(value: P64): Poly64x1T = vdupNP64(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vmovNF64(value: Double): Float64x1T = vdupNF64(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vmovqNP64(value: P64): Poly64x2T = vdupqNP64(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vmovqNF64(value: Double): Float64x2T = vdupqNF64(value)

/**
 * Duplicate vector element to vector or scalar
 */
public fun vgetLaneF64(v: Float64x1T, imm5: Int): Double {
    run {
        check(imm5 == 0)
    }
    return run { val simdArg0 = v; val simdArg1 = imm5.toUInt(); 0.0 }
}

/**
 * Shift left
 */
public fun vshldNS64(a: Long, n: Int): Long {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return a shl n
}

/**
 * Shift left
 */
public fun vshldNU64(a: ULong, n: Int): ULong {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return a shl n
}

/**
 * Signed shift right
 */
public fun vshrdNS64(a: Long, n: Int): Long {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            63
        } else {
            n
        }
    return a shr n
}

/**
 * Unsigned shift right
 */
public fun vshrdNU64(a: ULong, n: Int): ULong {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            return 0uL
        } else {
            n
        }
    return a shr n
}

/**
 * Signed shift right and accumulate
 */
public fun vsradNS64(a: Long, b: Long, n: Int): Long {
    run {
        check(n >= 1 && n <= 64)
    }
    return (a + vshrdNS64(b, n).toLong())
}

/**
 * Unsigned shift right and accumulate
 */
public fun vsradNU64(a: ULong, b: ULong, n: Int): ULong {
    run {
        check(n >= 1 && n <= 64)
    }
    return (a + vshrdNU64(b, n).toULong())
}

public fun fromSimdDouble(simd: Float64x1T): Simd<Double> = transmuteWithLayouts<Float64x1T, Simd<Double>>(simd, Float64x1T.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromSimdDouble(simd: Float64x2T): Simd<Double> = transmuteWithLayouts<Float64x2T, Simd<Double>>(simd, Float64x2T.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())
