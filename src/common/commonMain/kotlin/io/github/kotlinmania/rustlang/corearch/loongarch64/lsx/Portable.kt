// port-lint: source loongarch64/lsx/portable.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.simdExtSplat
import io.github.kotlinmania.rustlang.corearch.simdShuffle

// LoongArch64 LSX intrinsics - intrinsics.simd implementation

public fun <T> simdExtPickevB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)))

public fun <T> simdExtPickevH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)))

public fun <T> simdExtPickevW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2, 4, 6)))

public fun <T> simdExtPickevD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2)))

public fun <T> simdExtPickodB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)))

public fun <T> simdExtPickodH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)))

public fun <T> simdExtPickodW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3, 5, 7)))

public fun <T> simdExtPickodD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3)))

public fun <T> simdExtIlvhB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31)))

public fun <T> simdExtIlvhH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)))

public fun <T> simdExtIlvhW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(2, 6, 3, 7)))

public fun <T> simdExtIlvhD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3)))

public fun <T> simdExtIlvlB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23)))

public fun <T> simdExtIlvlH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)))

public fun <T> simdExtIlvlW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 4, 1, 5)))

public fun <T> simdExtIlvlD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2)))

public fun <T> simdExtReplveiB(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i)))

public fun <T> simdExtReplveiH(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i)))

public fun <T> simdExtReplveiW(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i, i, i)))

public fun <T> simdExtReplveiD(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(i, i)))

public fun <T> simdExtPackevB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)))

public fun <T> simdExtPackevH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)))

public fun <T> simdExtPackevW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 4, 2, 6)))

public fun <T> simdExtPackevD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(0, 2)))

public fun <T> simdExtPackodB(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 17, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31)))

public fun <T> simdExtPackodH(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)))

public fun <T> simdExtPackodW(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 5, 3, 7)))

public fun <T> simdExtPackodD(a: T, b: T): T = simdShuffle(b, a, SimdShuffleIdx(listOf(1, 3)))

public fun <T> simdExtShuf4iB(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u)))

public fun <T> simdExtShuf4iH(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u)))

public fun <T> simdExtShuf4iW(a: T, i: UInt): T = simdShuffle(a, a, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u), ((i shr 2) and 3u), ((i shr 4) and 3u), ((i shr 6) and 3u))))

public fun <T> simdExtShuf4iD(a: T, b: T, i: UInt): T = simdShuffle(a, b, SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u), ((i shr 2) and 3u))))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBsll(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)))
        }
        1 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)))
        }
        2 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
        }
        3 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)))
        }
        4 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)))
        }
        5 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)))
        }
        6 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9)))
        }
        7 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8)))
        }
        8 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6, 7)))
        }
        9 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5, 6)))
        }
        10 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4, 5)))
        }
        11 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3, 4)))
        }
        12 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2, 3)))
        }
        13 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1, 2)))
        }
        14 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 1)))
        }
        15 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0)))
        }
        else -> error("unreachable")
    }
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBsrl(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)))
        }
        1 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)))
        }
        2 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16)))
        }
        3 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16)))
        }
        4 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16)))
        }
        5 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16)))
        }
        6 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16)))
        }
        7 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16)))
        }
        8 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        9 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        10 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        11 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        12 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        13 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        14 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        15 -> {
            simdShuffle(a, z, SimdShuffleIdx(listOf(15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)))
        }
        else -> error("unreachable")
    }
}
