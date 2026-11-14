// port-lint: source str/validations.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.str.validations

import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.USIZE_BYTES
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error

// Operations related to UTF-8 validation.

/**
 * Returns the initial codepoint accumulator for the first byte.
 * The first byte is special, only want bottom 5 bits for width 2, 4 bits
 * for width 3, and 3 bits for width 4.
 */
public fun utf8FirstByte(byte: UByte, width: UInt): UInt = (byte and (0x7Fu shr width.toInt())).toUInt()

/**
 * Returns the value of `ch` updated with continuation byte `byte`.
 */
public fun utf8AccContByte(ch: UInt, byte: UByte): UInt = (ch shl 6) or (byte and CONT_MASK).toUInt()

/**
 * Checks whether the byte is a UTF-8 continuation byte (i.e., starts with the
 * bits `10`).
 */
public fun utf8IsContByte(byte: UByte): Boolean = (byte.toByte()) < (-64).toByte()

/**
 * Reads the next code point out of a byte iterator (assuming a
 * UTF-8-like encoding).
 *
 * # Safety
 *
 * `bytes` must produce a valid UTF-8-like (UTF-8 or WTF-8) string
 */
public fun <I : Iterator<UByte>> nextCodePoint(bytes: I): UInt? {
    // Decode UTF-8
    val x = (bytes.next() ?: return null)
    if (x < 128) {
        return x.toUInt()
    }
    // Multibyte case follows
    // Decode from a byte combination out of: [[[x y] z] w]
    // NOTE: Performance is sensitive to the exact formulation here
    val init = utf8FirstByte(x, 2u)
    // SAFETY: `bytes` produces an UTF-8-like string,
    // so the iterator must produce a value here.
    val y = bytes.next().unwrapUnchecked()
    var ch = utf8AccContByte(init, y)
    if (x >= 0xE0) {
        // [[x y z] w] case
        // 5th bit in 0xE0 .. 0xEF is always clear, so `init` is still valid
        // SAFETY: `bytes` produces an UTF-8-like string,
        // so the iterator must produce a value here.
        val z = bytes.next().unwrapUnchecked()
        val yZ = utf8AccContByte((y and CONT_MASK).toUInt(), z)
        ch = init shl 12 or yZ
        if (x >= 0xF0) {
            // [x y z w] case
            // use only the lower 3 bits of `init`
            // SAFETY: `bytes` produces an UTF-8-like string,
            // so the iterator must produce a value here.
            val w = bytes.next().unwrapUnchecked()
            ch = (init and 7u) shl 18 or utf8AccContByte(yZ, w)
        }
    }
    return ch
}

/**
 * Reads the last code point out of a byte iterator (assuming a
 * UTF-8-like encoding).
 *
 * # Safety
 *
 * `bytes` must produce a valid UTF-8-like (UTF-8 or WTF-8) string
 */
public fun <I> nextCodePointReverse(bytes: I): UInt? {
    // Decode UTF-8
    val w =
        run {
            val matchVal = (bytes.nextBack() ?: return null)
            when {
                matchVal < 128 -> {
                    val nextByte = matchVal
                    return nextByte.toUInt()
                }
                else -> {
                    val backByte = matchVal
                    backByte
                }
            }
        }
    // Multibyte case follows
    // Decode from a byte combination out of: [x [y [z w]]]
    var ch: Any?
    // SAFETY: `bytes` produces an UTF-8-like string,
    // so the iterator must produce a value here.
    val z = bytes.nextBack().unwrapUnchecked()
    ch = utf8FirstByte(z, 2u)
    if (utf8IsContByte(z)) {
        // SAFETY: `bytes` produces an UTF-8-like string,
        // so the iterator must produce a value here.
        val y = bytes.nextBack().unwrapUnchecked()
        ch = utf8FirstByte(y, 3u)
        if (utf8IsContByte(y)) {
            // SAFETY: `bytes` produces an UTF-8-like string,
            // so the iterator must produce a value here.
            val x = bytes.nextBack().unwrapUnchecked()
            ch = utf8FirstByte(x, 4u)
            ch = utf8AccContByte(ch, y)
        }
        ch = utf8AccContByte(ch, z)
    }
    ch = utf8AccContByte(ch, w)
    return ch
}

// https://tools.ietf.org/html/rfc3629
public val UTF8_CHAR_WIDTH: UByteArray = ubyteArrayOf(1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 2u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 3u, 4u, 4u, 4u, 4u, 4u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)

/**
 * Returns `true` if any byte in the word `x` is nonascii (>= 128).
 */
public fun containsNonascii(x: Int): Boolean = (x and NONASCII_MASK) != 0

// fallback impl has same behavior

/**
 * Walks through `v` checking that it's a valid UTF-8 sequence,
 * returning `Ok(())` in that case, or, if it is invalid, `Err(err)`.
 */
public fun runUtf8Validation(v: List<UByte>): Result<Unit, Utf8Error> {
    val USIZE_BYTES: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
    var index = 0
    val len = v.size
    val asciiBlockSize = 2 * USIZE_BYTES
    val blocksEnd =
        if (len >= asciiBlockSize) {
            len - asciiBlockSize + 1
        } else {
            0
        }
    // Below, we safely fall back to a slower codepath if the offset is `Int.MAX`,
    // so the end-to-end behavior is the same at compiletime and runtime.
    val align = v.asPtr().alignOffset(USIZE_BYTES)
    while (index < len) {
        val oldOffset = index
        val first = v[index]
        if (first >= 128u.toUByte()) {
            val w = utf8CharWidth(first)
            // 2-byte encoding is for codepoints  \u{0080} to  \u{07ff}
            // first  C2 80        last DF BF
            // 3-byte encoding is for codepoints  \u{0800} to  \u{ffff}
            // first  E0 A0 80     last EF BF BF
            // excluding surrogates codepoints  \u{d800} to  \u{dfff}
            // ED A0 80 to       ED BF BF
            // 4-byte encoding is for codepoints \u{10000} to \u{10ffff}
            // first  F0 90 80 80  last F4 8F BF BF
            //
            // Use the UTF-8 syntax from the RFC
            //
            // https://tools.ietf.org/html/rfc3629
            // UTF8-1      = %x00-7F
            // UTF8-2      = %xC2-DF UTF8-tail
            // UTF8-3      = %xE0 %xA0-BF UTF8-tail / %xE1-EC 2( UTF8-tail ) /
            // %xED %x80-9F UTF8-tail / %xEE-EF 2( UTF8-tail )
            // UTF8-4      = %xF0 %x90-BF 2( UTF8-tail ) / %xF1-F3 3( UTF8-tail ) /
            // %xF4 %x80-8F 2( UTF8-tail )
            when (w) {
                2 -> {
                    if ((run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    }).toByte() >= (-64).toByte()) {
                        return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 1))
                    }
                }
                3 -> {
                    run {
                        val matchVal = Pair(first, run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    })
                        when {
                            matchVal.first == 0xE0 && matchVal.second in 0xA0..0xBF -> {}
                            matchVal.first in 0xE1..0xEC && matchVal.second in 0x80..0xBF -> {}
                            matchVal.first == 0xED && matchVal.second in 0x80..0x9F -> {}
                            matchVal.first in 0xEE..0xEF && matchVal.second in 0x80..0xBF -> {}
                            else -> { return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 1)) }
                        }
                    }
                    if ((run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    }).toByte() >= (-64).toByte()) {
                        return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 2))
                    }
                }
                4 -> {
                    run {
                        val matchVal1 = Pair(first, run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    })
                        when {
                            matchVal1.first == 0xF0 && matchVal1.second in 0x90..0xBF -> {}
                            matchVal1.first in 0xF1..0xF3 && matchVal1.second in 0x80..0xBF -> {}
                            matchVal1.first == 0xF4 && matchVal1.second in 0x80..0x8F -> {}
                            else -> { return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 1)) }
                        }
                    }
                    if ((run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    }).toByte() >= (-64).toByte()) {
                        return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 2))
                    }
                    if ((run {
                        index += 1
                        if (index >= len) {
                            return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = null))
                        }
                        v[index]
                    }).toByte() >= (-64).toByte()) {
                        return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 3))
                    }
                }
                else -> { return Result.Err(Utf8Error(validUpTo = oldOffset, errorLen = 1)) }
            }
            index += 1
        } else {
            // Ascii case, try to skip forward quickly.
            // When the pointer is aligned, read 2 words of data per iteration
            // until we find a word containing a non-ascii byte.
            if (align != Int.MAX_VALUE && align.wrappingSub(index).isMultipleOf(USIZE_BYTES)) {
                val ptr = v.asPtr()
                while (index < blocksEnd) {
                    // SAFETY: since `align - index` and `asciiBlockSize` are
                    // multiples of `USIZE_BYTES`, `block = ptr.add(index)` is
                    // always aligned with a `Int` so it's safe to dereference
                    // both `block` and `block.add(1)`.
                    run {
                        val block = (ConstPtr((ptr).rawAddress + ((index).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                        // break if there is a nonascii byte
                        val zu = containsNonascii((PrimitiveLayouts.USIZE64).readNative((block).rawAddress))
                        val zv = containsNonascii((PrimitiveLayouts.USIZE64).readNative((ConstPtr((block).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))).rawAddress))
                        if (zu || zv) {
                            break
                        }
                    }
                    index += asciiBlockSize
                }
                // step from the point where the wordwise loop stopped
                while (index < len && v[index] < 128u.toUByte()) {
                    index += 1
                }
            } else {
                index += 1
            }
        }
    }
    return Result.Ok(Unit)
}

/**
 * Mask of the value bits of a continuation byte.
 */
public val CONT_MASK: UByte = 0x3Fu

/**
 * Given a first byte, determines how many bytes are in this UTF-8 character.
 */
public fun utf8CharWidth(b: UByte): Int = UTF8_CHAR_WIDTH[b.toInt()].toInt()

public val NONASCII_MASK: Int = Int.repeatU8(0x80u)
