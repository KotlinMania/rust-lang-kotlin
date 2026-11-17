// port-lint: source loongarch64/lsx/portable.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.SimdExt
import io.github.kotlinmania.rustlang.corearch.loongarch64.simd.simdExtSplat

// LoongArch64 LSX intrinsics - intrinsics.simd implementation

public fun <T> simdExtPickevB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u, 16u, 18u, 20u, 22u, 24u, 26u, 28u, 30u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickevH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickevW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickevD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickodB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u, 17u, 19u, 21u, 23u, 25u, 27u, 29u, 31u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickodH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickodW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPickodD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvhB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 24u, 9u, 25u, 10u, 26u, 11u, 27u, 12u, 28u, 13u, 29u, 14u, 30u, 15u, 31u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvhH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvhW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvhD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvlB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 1u, 17u, 2u, 18u, 3u, 19u, 4u, 20u, 5u, 21u, 6u, 22u, 7u, 23u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvlH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvlW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtIlvlD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtReplveiB(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtReplveiH(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i, i, i, i, i)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtReplveiW(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(i, i, i, i)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtReplveiD(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(i, i)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackevB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 2u, 18u, 4u, 20u, 6u, 22u, 8u, 24u, 10u, 26u, 12u, 28u, 14u, 30u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackevH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackevW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackevD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackodB(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 17u, 3u, 19u, 5u, 21u, 7u, 23u, 9u, 25u, 11u, 27u, 13u, 29u, 15u, 31u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackodH(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackodW(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtPackodD(a: T, b: T): T = run { val simdArg0 = b; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtShuf4iB(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u, ((i shr 0) and 3u) + 8u, ((i shr 2) and 3u) + 8u, ((i shr 4) and 3u) + 8u, ((i shr 6) and 3u) + 8u, ((i shr 0) and 3u) + 12u, ((i shr 2) and 3u) + 12u, ((i shr 4) and 3u) + 12u, ((i shr 6) and 3u) + 12u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtShuf4iH(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u) + 0u, ((i shr 2) and 3u) + 0u, ((i shr 4) and 3u) + 0u, ((i shr 6) and 3u) + 0u, ((i shr 0) and 3u) + 4u, ((i shr 2) and 3u) + 4u, ((i shr 4) and 3u) + 4u, ((i shr 6) and 3u) + 4u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtShuf4iW(a: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u), ((i shr 2) and 3u), ((i shr 4) and 3u), ((i shr 6) and 3u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <T> simdExtShuf4iD(a: T, b: T, i: UInt): T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(((i shr 0) and 3u), ((i shr 2) and 3u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }

public fun <Elem, T : SimdExt<T, Elem>> simdExtBsll(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        1u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        2u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        3u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        4u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        5u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        6u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        7u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        8u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        9u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u, 6u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        10u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u, 5u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        11u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u, 4u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        12u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        13u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u, 2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        14u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        15u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 0u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        else -> error("unreachable")
    }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtBsrl(a: T, i: UInt): T {
    val z = simdExtSplat(0L)
    return when (i and 0xfu) {
        0u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        1u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        2u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        3u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        4u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        5u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        6u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        7u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        8u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        9u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        10u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        11u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        12u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        13u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        14u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        15u -> {
            run { val simdArg0 = a; val simdArg1 = z; val simdArg2 = SimdShuffleIdx(uintArrayOf(15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
        }
        else -> error("unreachable")
    }
}
