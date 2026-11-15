// port-lint: source swizzle_dyn.rs
package io.github.kotlinmania.rustlang.coresimd.swizzledyn

import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.Swizzle
import io.github.kotlinmania.rustlang.coresimd.x86.mm256Permute2x128Si256 as avx2CrossShuffle
import io.github.kotlinmania.rustlang.coresimd.x86.mm256ShuffleEpi8 as avx2HalfPshufb

public fun interface FnCallback {
    public operator fun invoke(arg0: T, arg1: T): T
}

/**
 * Swizzle a vector of bytes according to the index vector.
 * Indices within range select the appropriate byte.
 * Indices "out of bounds" instead select 0.
 *
 * Note that the current implementation is selected during build-time
 * of the standard library, so `cargo build - Zbuild - std` may be necessary
 * to unlock better performance, especially for larger vectors.
 * A planned compiler improvement will enable using `@targetFeature` instead.
 */
public fun Simd<UByte, N>.swizzleDyn(idxs: Simd<UByte>, n: Int): Simd<UByte, N> {
    // SAFETY: Intrinsics covered by cfg
    return when (n) {
        // Aarch64
        8, 16, 24, 32, 48, 64 -> aarch64Swizzle(this, idxs)
        // WASM SIMD128
        16 -> transize(wasm.i8x16Swizzle, this, idxs)
        32 -> transize(swizzleDynSplit, this, idxs)
        // LoongArch64
        16 -> transize(::loong64LsxSwizzle, this, idxs)
        32 -> transize(::loong64LasxSwizzle, this, idxs)
        32 -> transize(swizzleDynSplit, this, idxs)
        64 -> transize(swizzleDynSplit, this, idxs)
        // x86, x86-64
        16 -> transize(x86.mmShuffleEpi8, this, zeroingIdxs(idxs))
        32 -> {
            val swizzler =
                { bytes, idxs1 ->
                    // Clamp out-of-range indices to the first byte of a
                    // second, all-zero table.
                    val idxs2 = x86.mm256MinEpu8(idxs1, Simd.splat(n.toUByte()).into())
                    x86.mm256Permutex2varEpi8(bytes, idxs2, x86.mm256SetzeroSi256())
                }
            transize(swizzler, this, idxs)
        }
        32 -> transize(::avx2Pshufb, this, idxs)
        32 -> transize(swizzleDynSplit, this, idxs)
        // Notable absence: avx512bw pshufb shuffle
        64 -> {
            val swizzler1 =
                { bytes, idxs1 ->
                    // Clamp out-of-range indices to the first byte of a
                    // second, all-zero table.
                    val idxs2 = x86.mm512MinEpu8(idxs1, Simd.splat(n.toUByte()).into())
                    x86.mm512Permutex2varEpi8(bytes, idxs2, x86.mm512SetzeroSi512())
                }
            transize(swizzler1, this, idxs)
        }
        64 -> transize(swizzleDynSplit, this, idxs)
        // scalar fallback
        else -> {
            val array = MutableList(n) { 0 }
            for ((i, k) in idxs.toArray().intoIter().mapIndexed { index, item -> Pair(index, item) }) {
                if ((k.toInt()) < n) {
                    array[i] = this[k.toInt()]
                }
            }
            array.into()
        }
    }
}

/**
 * Implements an arbitrary shuffle over double the native vector width
 * using 4 native-width shuffles
 */
public fun swizzleDynSplit(bytes: Simd<UByte>, idxs: Simd<UByte>, n: Int, half: Int): Simd<UByte> {
    open class CombineHalves : Swizzle {
        public val INDEX: IntArray = run {
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = i
                i += 1
            }
            index
        }
    }
    val tableLow = bytes.extract()
    val tableHigh = bytes.extract()
    val idxsLow = idxs.extract()
    val idxsHigh = idxs.extract()
    val tableHighOffset = Simd.splat(half.toUByte())
    val outputLowFromLow = tableLow.swizzleDyn(idxsLow)
    val outputLowFromHigh = tableHigh.swizzleDyn(idxsLow - tableHighOffset)
    val outputLow = outputLowFromLow or outputLowFromHigh
    val outputHighFromLow = tableLow.swizzleDyn(idxsHigh)
    val outputHighFromHigh = tableHigh.swizzleDyn(idxsHigh - tableHighOffset)
    val outputHigh = outputHighFromLow or outputHighFromHigh
    return CombineHalves.concatSwizzle(outputLow, outputHigh)
}

/**
 * AArch64 NEON supports swizzling 8, 16, 24, 32, 48 or 64 by stacking multiple TBL instructions.
 *
 * # Safety
 * This requires AArch64 NEON to work
 */
public fun aarch64Swizzle(bytes: Simd<UByte>, idxs: Simd<UByte>, n: Int): Simd<UByte> {
    // SAFETY: Caller promised AArch64 NEON support
    return when (n) {
        8 -> transmuteCopy(vtbl1U8(transmuteCopy(bytes), transmuteCopy(idxs)))
        16 -> transmuteCopy(vqtbl1qU8(transmuteCopy(bytes), transmuteCopy(idxs)))
        24 -> {
            val bytes1: Uint8x8x3T = transmuteCopy(bytes)
            val idxs1: Uint8x8x3T = transmuteCopy(idxs)
            val ret0 = vtbl3U8(bytes1, idxs1.first)
            val ret1 = vtbl3U8(bytes1, idxs1.second)
            val ret2 = vtbl3U8(bytes1, idxs1.third)
            val ret = uint8x8x3T(ret0, ret1, ret2)
            transmuteCopy(ret)
        }
        32 -> {
            val bytes2: Uint8x16x2T = transmuteCopy(bytes)
            val idxs2: Uint8x16x2T = transmuteCopy(idxs)
            val ret01 = vqtbl2qU8(bytes2, idxs2.first)
            val ret11 = vqtbl2qU8(bytes2, idxs2.second)
            val ret1 = uint8x16x2T(ret01, ret11)
            transmuteCopy(ret1)
        }
        48 -> {
            val bytes3: Uint8x16x3T = transmuteCopy(bytes)
            val idxs3: Uint8x16x3T = transmuteCopy(idxs)
            val ret02 = vqtbl3qU8(bytes3, idxs3.first)
            val ret12 = vqtbl3qU8(bytes3, idxs3.second)
            val ret21 = vqtbl3qU8(bytes3, idxs3.third)
            val ret2 = uint8x16x3T(ret02, ret12, ret21)
            transmuteCopy(ret2)
        }
        64 -> {
            val bytes4: Uint8x16x4T = transmuteCopy(bytes)
            val idxs4: Uint8x16x4T = transmuteCopy(idxs)
            val ret03 = vqtbl4qU8(bytes4, idxs4.first)
            val ret13 = vqtbl4qU8(bytes4, idxs4.second)
            val ret22 = vqtbl4qU8(bytes4, idxs4.third)
            val ret3 = vqtbl4qU8(bytes4, idxs4.field3)
            val ret3 = uint8x16x4T(ret03, ret13, ret22, ret3)
            transmuteCopy(ret3)
        }
        else -> error("unreachable")
    }
}

/**
 * "vpshufb like it was meant to be" on AVX2
 *
 * # Safety
 * This requires AVX2 to work
 */
public fun avx2Pshufb(bytes: Simd<UByte>, idxs: Simd<UByte>): Simd<UByte> {
    // SAFETY: Caller promised AVX2
    return run {
        val bytes1 = bytes.into()
        val indices = idxs.into()
        val swapped = avx2CrossShuffle(bytes1, bytes1, 0x01)
        // Adding 0x60 preserves the low nibble and bit 4 for valid
        // indices 0..=31. Larger indices get their high bit set, so
        // VPSHUFB supplies the required out-of-bounds zeroing.
        val control = x86.mm256AddsEpu8(indices, x86.mm256Set1Epi8(0x60))
        val local = avx2HalfPshufb(bytes1, control)
        val remote = avx2HalfPshufb(swapped, control)
        // In the low lane, adding 0x10 moves the valid index's bit 4
        // into the sign bit. The high lane has the opposite
        // local/remote mapping, so adding 0x90 flips the selection.
        // Out-of-range indices already zeroed both shuffle results,
        // making the blend selection irrelevant for them.
        val selectBias = x86.mm256SetM128i(x86.mmSet1Epi8(-112), x86.mmSet1Epi8(16))
        val selectRemote = x86.mm256AddEpi8(control, selectBias)
        x86.mm256BlendvEpi8(local, remote, selectRemote).into()
    }
}

/**
 * LoongArch64 LSX supports swizzling `u8x16`
 *
 * # Safety
 * This requires LoongArch LSX to work
 */
public fun loong64LsxSwizzle(bytes: Simd<UByte>, idxs: Simd<UByte>): Simd<UByte> {
    // SAFETY: Caller promised loongarch lsx support
    return run {
        val bytes1 = lsxVshufB(bytes.into(), bytes.into(), idxs.into())
        val mask = lsxVsleiBu(idxs.into(), 15)
        lsxVandV(bytes1, mask).into()
    }
}

/**
 * LoongArch64 LASX supports swizzling `u8x32`
 *
 * # Safety
 * This requires LoongArch LASX to work
 */
public fun loong64LasxSwizzle(bytes: Simd<UByte>, idxs: Simd<UByte>): Simd<UByte> {
    // SAFETY: Caller promised loongarch lasx support
    return run {
        val lolo = lasxXvpermiQ(bytes.into(), bytes.into(), 0x00)
        val hihi = lasxXvpermiQ(bytes.into(), bytes.into(), 0x11)
        val bytes1 = lasxXvshufB(hihi, lolo, idxs.into())
        val mask = lasxXvsleiBu(idxs.into(), 31)
        lasxXvandV(bytes1, mask).into()
    }
}

/**
 * This sets up a call to an architecture-specific function, and in doing so
 * it persuades rustc that everything is the correct size. Which it is.
 * This would not be needed if one could convince Rust that, by matching on N,
 * N is that value, and thus it would be valid to substitute e.g. 16.
 *
 * # Safety
 * The correctness of this function hinges on the sizes agreeing in actuality.
 */
public fun <T> transize(f: (T, T) -> T, a: Simd<UByte>, b: Simd<UByte>, n: Int): Simd<UByte> {
    // SAFETY: Same obligation to use this function as to use mem.transmuteCopy.
    return transmuteCopy(f(transmuteCopy(a), transmuteCopy(b)))
}

/**
 * Make indices that yield 0 for x86
 */
public fun zeroingIdxs(idxs: Simd<UByte>, n: Int): Simd<UByte> {
    // Adding this sets the high bit for indices N..=127, while PSHUFB ignores
    // the other changed bits. The OR preserves the high bit for indices 128..=255.
    val zeroingBits = idxs + Simd.splat((127 - n + 1).toUByte())
    return idxs or zeroingBits
}
