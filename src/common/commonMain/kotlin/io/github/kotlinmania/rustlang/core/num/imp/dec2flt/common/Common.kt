// port-lint: source num/imp/dec2flt/common.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.splitFirst

// Common utilities, for internal use only.

/**
 * Helper methods to process immutable bytes.
 */
public interface ByteSlice<T> {
    /**
     * Reads 8 bytes as a 64-bit integer in little-endian order.
     */
    public fun readU64(): ULong

    /**
     * Writes a 64-bit integer as 8 bytes in little-endian order.
     */
    public fun writeU64(value: ULong)

    /**
     * Calculate the difference in length between two slices.
     */
    public fun offsetFrom(other: T): Int

    /**
     * Iteratively parse and consume digits from bytes.
     *
     * Returns the same bytes with consumed digits being elided. Breaks on invalid digits.
     */
    public fun parseDigits(func: (UByte) -> Unit): T
}

// inlining this is crucial to remove bound checks
public fun List<UByte>.readU64(): ULong {
    val tmp = MutableList(8) { 0 }
    tmp.copyFromSlice(((this).subList(0, 8)))
    return run { val bytesTmp0 = tmp; ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }
}

// inlining this is crucial to remove bound checks
public fun List<UByte>.writeU64(value: ULong) {
    run { val dstTmp1 = this; val srcTmp1 = (run { val rawBits = value; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }); for (idxTmp1 in srcTmp1.indices) dstTmp1[idxTmp1] = srcTmp1[idxTmp1] }
}

public fun List<UByte>.offsetFrom(other: List<UByte>): Int = other.size - this.size

public fun List<UByte>.parseDigits(func: (UByte) -> Unit): List<UByte> {
    var s = this
    while (true) {
        val matchVal = s.splitFirst()
        if (matchVal == null) break
        val c = matchVal.first
        val rest = matchVal.second
        val c1 = c.wrappingSub(48u)
        if (c1 < 10) {
            func(c1)
            s = rest
        } else {
            break
        }
    }
    return s
}

/**
 * Determine if all characters in an 8-byte byte string (represented as a `ULong`) are all decimal
 * digits.
 *
 * This does not care about the order in which the bytes were loaded.
 */
public fun is8digits(v: ULong): Boolean {
    val a = (v + 0x4646464646464646uL)
    val b = (v - 0x3030303030303030uL)
    return (a or b) and 0x8080808080808080uL == 0uL
}

/**
 * A custom 64-bit floating point type, representing `m * 2 ^ p`.
 * p is biased, so it be directly shifted into the exponent bits.
 */
public open class BiasedFp(
    /**
     * The significant digits.
     */
    public var m: ULong,
    /**
     * The biased, binary exponent.
     */
    public var pBiased: Int,
) {
    public open fun clone(): BiasedFp = BiasedFp(m, pBiased)

    public companion object {
        public fun typeLayout(): TypeLayout<BiasedFp> =
            object : TypeLayout<BiasedFp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): BiasedFp =
                    BiasedFp(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: BiasedFp) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.m)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.pBiased)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BiasedFp =
                    BiasedFp(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BiasedFp) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.m)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.pBiased)
                }
            }

        /**
         * Represent `0 ^ p`
         */
        public fun zeroPow2(pBiased: Int): BiasedFp = BiasedFp(m = 0uL, pBiased = pBiased)
    }
}
