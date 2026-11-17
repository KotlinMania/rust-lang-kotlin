// port-lint: source loongarch64/lasx/portable.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.SimdExt
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.simdExtSplat

// LoongArch64 LASX intrinsics - intrinsics.simd implementation

public fun <T> simdExtPickevB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u, 32u, 34u, 36u, 38u, 40u, 42u, 44u, 46u, 16u, 18u, 20u, 22u, 24u, 26u, 28u, 30u, 48u, 50u, 52u, 54u, 56u, 58u, 60u, 62u)); _simdArg0 }

public fun <T> simdExtPickevH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 16u, 18u, 20u, 22u, 8u, 10u, 12u, 14u, 24u, 26u, 28u, 30u)); _simdArg0 }

public fun <T> simdExtPickevW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 8u, 10u, 4u, 6u, 12u, 14u)); _simdArg0 }

public fun <T> simdExtPickevD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); _simdArg0 }

public fun <T> simdExtPickodB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u, 33u, 35u, 37u, 39u, 41u, 43u, 45u, 47u, 17u, 19u, 21u, 23u, 25u, 27u, 29u, 31u, 49u, 51u, 53u, 55u, 57u, 59u, 61u, 63u)); _simdArg0 }

public fun <T> simdExtPickodH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 17u, 19u, 21u, 23u, 9u, 11u, 13u, 15u, 25u, 27u, 29u, 31u)); _simdArg0 }

public fun <T> simdExtPickodW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 9u, 11u, 5u, 7u, 13u, 15u)); _simdArg0 }

public fun <T> simdExtPickodD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); _simdArg0 }

public fun <T> simdExtIlvhB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 40u, 9u, 41u, 10u, 42u, 11u, 43u, 12u, 44u, 13u, 45u, 14u, 46u, 15u, 47u, 24u, 56u, 25u, 57u, 26u, 58u, 27u, 59u, 28u, 60u, 29u, 61u, 30u, 62u, 31u, 63u)); _simdArg0 }

public fun <T> simdExtIlvhH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 20u, 5u, 21u, 6u, 22u, 7u, 23u, 12u, 28u, 13u, 29u, 14u, 30u, 15u, 31u)); _simdArg0 }

public fun <T> simdExtIlvhW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 10u, 3u, 11u, 6u, 14u, 7u, 15u)); _simdArg0 }

public fun <T> simdExtIlvhD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); _simdArg0 }

public fun <T> simdExtIlvlB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 32u, 1u, 33u, 2u, 34u, 3u, 35u, 4u, 36u, 5u, 37u, 6u, 38u, 7u, 39u, 16u, 48u, 17u, 49u, 18u, 50u, 19u, 51u, 20u, 52u, 21u, 53u, 22u, 54u, 23u, 55u)); _simdArg0 }

public fun <T> simdExtIlvlH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 1u, 17u, 2u, 18u, 3u, 19u, 8u, 24u, 9u, 25u, 10u, 26u, 11u, 27u)); _simdArg0 }

public fun <T> simdExtIlvlW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 4u, 12u, 5u, 13u)); _simdArg0 }

public fun <T> simdExtIlvlD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); _simdArg0 }

public fun <T> simdExtReplveiB(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u, i + 16u)); _simdArg0 }

public fun <T> simdExtReplveiH(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u, i + 8u)); _simdArg0 }

public fun <T> simdExtReplveiW(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i, i + 4u, i + 4u, i + 4u, i + 4u)); _simdArg0 }

public fun <T> simdExtReplveiD(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i + 2u, i + 2u)); _simdArg0 }

public fun <T> simdExtReplve0B(a: T): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)); _simdArg0 }

public fun <T> simdExtReplve0H(a: T): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)); _simdArg0 }

public fun <T> simdExtReplve0W(a: T): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)); _simdArg0 }

public fun <T> simdExtReplve0D(a: T): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u)); _simdArg0 }

public fun <T> simdExtReplve0Q(a: T): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 0u, 1u)); _simdArg0 }

public fun <T> simdExtPackevB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 32u, 2u, 34u, 4u, 36u, 6u, 38u, 8u, 40u, 10u, 42u, 12u, 44u, 14u, 46u, 16u, 48u, 18u, 50u, 20u, 52u, 22u, 54u, 24u, 56u, 26u, 58u, 28u, 60u, 30u, 62u)); _simdArg0 }

public fun <T> simdExtPackevH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 2u, 18u, 4u, 20u, 6u, 22u, 8u, 24u, 10u, 26u, 12u, 28u, 14u, 30u)); _simdArg0 }

public fun <T> simdExtPackevW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); _simdArg0 }

public fun <T> simdExtPackevD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); _simdArg0 }

public fun <T> simdExtPackodB(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 33u, 3u, 35u, 5u, 37u, 7u, 39u, 9u, 41u, 11u, 43u, 13u, 45u, 15u, 47u, 17u, 49u, 19u, 51u, 21u, 53u, 23u, 55u, 25u, 57u, 27u, 59u, 29u, 61u, 31u, 63u)); _simdArg0 }

public fun <T> simdExtPackodH(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 17u, 3u, 19u, 5u, 21u, 7u, 23u, 9u, 25u, 11u, 27u, 13u, 29u, 15u, 31u)); _simdArg0 }

public fun <T> simdExtPackodW(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); _simdArg0 }

public fun <T> simdExtPackodD(a: T, b: T): T = run { val _simdArg0 = b; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); _simdArg0 }

public fun <T> simdExtShuf4iB(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u, ((i shr 0) and 3u) + 16u, ((i shr 2) and 3u) + 16u, ((i shr 4) and 3u) + 16u, ((i shr 6) and 3u) + 16u, ((i shr 0) and 3u) + 20u, ((i shr 2) and 3u) + 20u, ((i shr 4) and 3u) + 20u, ((i shr 6) and 3u) + 20u, ((i shr 0) and 3u) + 24u, ((i shr 2) and 3u) + 24u, ((i shr 4) and 3u) + 24u, ((i shr 6) and 3u) + 24u, ((i shr 0) and 3u) + 28u, ((i shr 2) and 3u) + 28u, ((i shr 4) and 3u) + 28u, ((i shr 6) and 3u) + 28u)); _simdArg0 }

public fun <T> simdExtShuf4iH(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u)); _simdArg0 }

public fun <T> simdExtShuf4iW(a: T, i: UInt): T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u)); _simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtBsll(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        }
        1 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u)); _simdArg0 }
        }
        2 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u)); _simdArg0 }
        }
        3 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u)); _simdArg0 }
        }
        4 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u)); _simdArg0 }
        }
        5 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u)); _simdArg0 }
        }
        6 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u)); _simdArg0 }
        }
        7 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u)); _simdArg0 }
        }
        8 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u)); _simdArg0 }
        }
        9 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u, 22u)); _simdArg0 }
        }
        10 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 5u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u, 21u)); _simdArg0 }
        }
        11 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 4u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u, 20u)); _simdArg0 }
        }
        12 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 3u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u, 19u)); _simdArg0 }
        }
        13 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 2u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u, 18u)); _simdArg0 }
        }
        14 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 1u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u, 17u)); _simdArg0 }
        }
        15 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 0u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 16u)); _simdArg0 }
        }
        else -> error("unreachable")
    }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtBsrl(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        }
        1 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u)); _simdArg0 }
        }
        2 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u)); _simdArg0 }
        }
        3 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u)); _simdArg0 }
        }
        4 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        5 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        6 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        7 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        8 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        9 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(9u, 10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 25u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        10 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(10u, 11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 26u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        11 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(11u, 12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 27u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        12 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(12u, 13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 28u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        13 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(13u, 14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 29u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        14 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(14u, 15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 30u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        15 -> {
            run { val _simdArg0 = a; val _simdArg1 = z; val _simdArg2 = SimdShuffleIdx(uintArrayOf(15u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 31u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u, 32u)); _simdArg0 }
        }
        else -> error("unreachable")
    }
}
