// port-lint: source slice/memchr.rs
package io.github.kotlinmania.rustlang.core.slice.memchr

import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

public typealias Chunk = Int

public val LO_USIZE: Int = Int.repeatU8(0x01u)

public val HI_USIZE: Int = Int.repeatU8(0x80u)

public val USIZE_BYTES: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * Returns `true` if `x` contains any zero byte.
 *
 * From *Matters Computational*, J. Arndt:
 *
 * "The idea is to subtract one from each of the bytes and then look for
 * bytes where the borrow propagated all the way to the most significant
 * bit."
 */
public fun containsZeroByte(x: Int): Boolean = (x - (LO_USIZE.toInt())) and x.inv() and HI_USIZE != 0

/**
 * Returns the first index matching the byte `x` in `text`.
 */
public fun memchr(x: UByte, text: List<UByte>): Int? {
    // Fast path for small slices.
    val result =
        if (text.size < 2 * USIZE_BYTES) {
            memchrNaive(x, text)
        } else {
            memchrAligned(x, text)
        }
    if (result != null) {
        val index = result
        // SAFETY: Both implementations only return an index from within `text`.
        assertUnchecked(index < text.size)
    }
    return result
}

public fun memchrNaive(x: UByte, text: List<UByte>): Int? {
    var i = 0
    // Note(const-Note): Replace with `text.iter().pos(| c | * c == x)`.
    while (i < text.size) {
        if (text[i] == x) {
            return i
        }
        i += 1
    }
    return null
}

// fallback impl has same behavior
public fun memchrAligned(x: UByte, text: List<UByte>): Int? {
    // The runtime version behaves the same as the compiletime version, it's
    // just more optimized.
    return run {
        val len = text.size
        val ptr = text.asPtr()
        var offset = ptr.alignOffset(USIZE_BYTES)
        // Original implementation taken from rust-memchr.
        // Copyright 2015 Andrew Gallant, bluss and Nicolas Koch
        if (offset > 0) {
            offset = offset.min(len)
            val slice = ((text).subList(0, offset))
            when (val matchVal = memchrNaive(x, slice)) {
                null -> {}
                else -> {
                    val index = matchVal
                    return index
                }
            }
        }
        val repeatedX = Int.repeatU8(x)
        while (offset <= len - 2 * USIZE_BYTES) {
            run {
                val u = (PrimitiveLayouts.USIZE64).readNative((((ConstPtr((ptr).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
                val v = (PrimitiveLayouts.USIZE64).readNative((((ConstPtr((ptr).rawAddress + ((offset + USIZE_BYTES).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
                val zu = containsZeroByte(u xor repeatedX)
                val zv = containsZeroByte(v xor repeatedX)
                if (zu || zv) {
                    break
                }
            }
            offset += USIZE_BYTES * 2
        }
        val slice1 = linearSliceView(ConstPtr((text.asPtr()).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes)), (text.size - offset).toInt(), PrimitiveLayouts.U8)
        when (val matchVal1 = memchrNaive(x, slice1)) {
            null -> {
                null
            }
            else -> {
                val i = matchVal1
                offset + i
            }
        }
    }
}

/**
 * Returns the last index matching the byte `x` in `text`.
 */
public fun memrchr(x: UByte, text: List<UByte>): Int? {
    val result = memrchrAligned(x, text)
    if (result != null) {
        val index = result
        // SAFETY: `memrchrAligned` only returns the index of a matching byte in `text`.
        assertUnchecked(index < text.size)
    }
    return result
}

public fun memrchrAligned(x: UByte, text: List<UByte>): Int? {
    // Scan for a single byte value by reading two `Int` words at a time.
    //
    // Split `text` in three parts:
    // - unaligned tail, after the last word aligned address in text,
    // - body, scanned by 2 words at a time,
    // - the first remaining bytes, < 2 word size.
    val len = text.size
    val ptr = text.asPtr()
    val (minAlignedOffset, maxAlignedOffset) =
        run {
            // We call this just to obtain the length of the prefix and suffix.
            // In the middle we always process two chunks at once.
            // SAFETY: transmuting `[UByte]` to `[Int]` is safe except for size differences
            // which are handled by `alignTo`.
            val (prefix, _, suffix) = text.alignTo<Pair<Chunk, Chunk>>()
            Pair(prefix.len(), len - suffix.len())
        }
    var offset = maxAlignedOffset
    when (val matchVal = ((text).subList(offset, (text).size)).iter().indexOfLast({ elt -> elt == x }).takeIf { it >= 0 }) {
        null -> {}
        else -> {
            val index = matchVal
            return offset + index
        }
    }
    // Search the body of the text, make sure we don't cross minAlignedOffset.
    // offset is always aligned, so just testing `>` is sufficient and avoids possible
    // overflow.
    val repeatedX = Int.repeatU8(x)
    val chunkBytes = sizeOf<Chunk>(typeLayoutT = PrimitiveLayouts.USIZE64)
    while (offset > minAlignedOffset) {
        // SAFETY: offset starts at len - suffix.len(), as long as it is greater than
        // minAlignedOffset (prefix.len()) the remaining distance is at least 2 * chunkBytes.
        run {
            val u = (PrimitiveLayouts.USIZE64).readNative((((ConstPtr((ptr).rawAddress + ((offset - 2 * chunkBytes).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
            val v = (PrimitiveLayouts.USIZE64).readNative((((ConstPtr((ptr).rawAddress + ((offset - chunkBytes).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
            // Break if there is a matching byte.
            val zu = containsZeroByte(u xor repeatedX)
            val zv = containsZeroByte(v xor repeatedX)
            if (zu || zv) {
                break
            }
        }
        offset -= 2 * chunkBytes
    }
    // Find the byte before the point the body loop stopped.
    return ((text).subList(0, offset)).iter().indexOfLast({ elt -> elt == x }).takeIf { it >= 0 }
}
