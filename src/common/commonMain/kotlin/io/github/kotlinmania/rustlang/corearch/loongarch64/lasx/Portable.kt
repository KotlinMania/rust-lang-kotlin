// port-lint: source loongarch64/lasx/portable.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.simdExtSplat
import io.github.kotlinmania.rustlang.corearch.simdShuffle

// LoongArch64 LASX intrinsics - intrinsics.simd implementation

public fun <T> simdExtPickevB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14, 32, 34, 36, 38, 40, 42, 44, 46, 16, 18, 20, 22, 24, 26, 28, 30, 48, 50, 52, 54, 56, 58, 60, 62)))

public fun <T> simdExtPickevH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 4, 6, 16, 18, 20, 22, 8, 10, 12, 14, 24, 26, 28, 30)))

public fun <T> simdExtPickevW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 8, 10, 4, 6, 12, 14)))

public fun <T> simdExtPickevD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 4, 2, 6)))

public fun <T> simdExtPickodB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15, 33, 35, 37, 39, 41, 43, 45, 47, 17, 19, 21, 23, 25, 27, 29, 31, 49, 51, 53, 55, 57, 59, 61, 63)))

public fun <T> simdExtPickodH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 5, 7, 17, 19, 21, 23, 9, 11, 13, 15, 25, 27, 29, 31)))

public fun <T> simdExtPickodW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 9, 11, 5, 7, 13, 15)))

public fun <T> simdExtPickodD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 5, 3, 7)))

public fun <T> simdExtIlvhB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(8, 40, 9, 41, 10, 42, 11, 43, 12, 44, 13, 45, 14, 46, 15, 47, 24, 56, 25, 57, 26, 58, 27, 59, 28, 60, 29, 61, 30, 62, 31, 63)))

public fun <T> simdExtIlvhH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(4, 20, 5, 21, 6, 22, 7, 23, 12, 28, 13, 29, 14, 30, 15, 31)))

public fun <T> simdExtIlvhW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(2, 10, 3, 11, 6, 14, 7, 15)))

public fun <T> simdExtIlvhD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 5, 3, 7)))

public fun <T> simdExtIlvlB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 32, 1, 33, 2, 34, 3, 35, 4, 36, 5, 37, 6, 38, 7, 39, 16, 48, 17, 49, 18, 50, 19, 51, 20, 52, 21, 53, 22, 54, 23, 55)))

public fun <T> simdExtIlvlH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 8, 24, 9, 25, 10, 26, 11, 27)))

public fun <T> simdExtIlvlW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 8, 1, 9, 4, 12, 5, 13)))

public fun <T> simdExtIlvlD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 4, 2, 6)))

public fun <T> simdExtReplveiB(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u)))

public fun <T> simdExtReplveiH(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u)))

public fun <T> simdExtReplveiW(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i, i + 4u, i + 4u, i + 4u, i + 4u)))

public fun <T> simdExtReplveiD(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i + 2u, i + 2u)))

public fun <T> simdExtReplve0B(a: T): T = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)))

public fun <T> simdExtReplve0H(a: T): T = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)))

public fun <T> simdExtReplve0W(a: T): T = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0, 0, 0, 0, 0, 0, 0)))

public fun <T> simdExtReplve0D(a: T): T = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0, 0, 0)))

public fun <T> simdExtReplve0Q(a: T): T = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 1, 0, 1)))

public fun <T> simdExtPackevB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 32, 2, 34, 4, 36, 6, 38, 8, 40, 10, 42, 12, 44, 14, 46, 16, 48, 18, 50, 20, 52, 22, 54, 24, 56, 26, 58, 28, 60, 30, 62)))

public fun <T> simdExtPackevH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)))

public fun <T> simdExtPackevW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)))

public fun <T> simdExtPackevD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 4, 2, 6)))

public fun <T> simdExtPackodB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 33, 3, 35, 5, 37, 7, 39, 9, 41, 11, 43, 13, 45, 15, 47, 17, 49, 19, 51, 21, 53, 23, 55, 25, 57, 27, 59, 29, 61, 31, 63)))

public fun <T> simdExtPackodH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 17, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31)))

public fun <T> simdExtPackodW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)))

public fun <T> simdExtPackodD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 5, 3, 7)))

public fun <T> simdExtShuf4iB(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u, ((i shr 0) and 3u) + 16u, ((i shr 2) and 3u) + 16u, ((i shr 4) and 3u) + 16u, ((i shr 6) and 3u) + 16u, ((i shr 0) and 3u) + 20u, ((i shr 2) and 3u) + 20u, ((i shr 4) and 3u) + 20u, ((i shr 6) and 3u) + 20u, ((i shr 0) and 3u) + 24u, ((i shr 2) and 3u) + 24u, ((i shr 4) and 3u) + 24u, ((i shr 6) and 3u) + 24u, ((i shr 0) and 3u) + 28u, ((i shr 2) and 3u) + 28u, ((i shr 4) and 3u) + 28u, ((i shr 6) and 3u) + 28u)))

public fun <T> simdExtShuf4iH(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u)))

public fun <T> simdExtShuf4iW(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u)))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBsll(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)))
        }
        1 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30)))
        }
        2 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29)))
        }
        3 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)))
        }
        4 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27)))
        }
        5 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)))
        }
        6 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)))
        }
        7 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 8, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23, 24)))
        }
        8 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 7, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22, 23)))
        }
        9 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 6, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21, 22)))
        }
        10 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 5, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20, 21)))
        }
        11 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 4, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19, 20)))
        }
        12 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 3, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18, 19)))
        }
        13 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 2, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17, 18)))
        }
        14 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 1, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 17)))
        }
        15 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16)))
        }
        else -> error("unreachable")
    }
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBsrl(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)))
        }
        1 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)))
        }
        2 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32)))
        }
        3 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32)))
        }
        4 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32)))
        }
        5 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32)))
        }
        6 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32)))
        }
        7 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(7, 8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32)))
        }
        8 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 24, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        9 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(9, 10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 25, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        10 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(10, 11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 26, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        11 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(11, 12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 27, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        12 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(12, 13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 28, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        13 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(13, 14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 29, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        14 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(14, 15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 30, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        15 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(15, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32)))
        }
        else -> error("unreachable")
    }
}
