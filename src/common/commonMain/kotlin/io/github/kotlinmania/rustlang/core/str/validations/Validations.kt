// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.str.validations

import io.github.kotlinmania.rustlang.core.num.isMultipleOf
import io.github.kotlinmania.rustlang.core.num.repeatU8Int
import io.github.kotlinmania.rustlang.core.num.wrappingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error

public fun utf8FirstByte(byte: UByte, width: UInt): UInt =
    (byte.toUInt() and (0x7Fu shr width.toInt()))

public fun utf8AccContByte(ch: UInt, byte: UByte): UInt =
    (ch shl 6) or (byte and CONT_MASK).toUInt()

public fun utf8IsContByte(byte: UByte): Boolean = byte.toByte() < (-64).toByte()

private fun readUtf8Byte(value: Any?): UByte =
    when (value) {
        is UByte -> value
        is Byte -> value.toUByte()
        else -> throw IllegalArgumentException("Expected UByte from UTF-8 iterator, got $value")
    }

public fun <T, I : Iterator<T>> nextCodePoint(bytes: I): UInt? {
    if (!bytes.hasNext()) return null
    val x = readUtf8Byte(bytes.next())
    if (x < 128u.toUByte()) {
        return x.toUInt()
    }
    val init = utf8FirstByte(x, 2u)
    val y = readUtf8Byte(bytes.next())
    var ch = utf8AccContByte(init, y)
    if (x >= 0xE0u.toUByte()) {
        val z = readUtf8Byte(bytes.next())
        val yZ = utf8AccContByte((y and CONT_MASK).toUInt(), z)
        ch = (init shl 12) or yZ
        if (x >= 0xF0u.toUByte()) {
            val w = readUtf8Byte(bytes.next())
            ch = ((init and 7u) shl 18) or utf8AccContByte(yZ, w)
        }
    }
    return ch
}

public fun <T, I : Iterator<T>> nextCodePointReverse(bytes: I): UInt? {
    if (!bytes.hasNext()) return null
    val b0 = readUtf8Byte(bytes.next())
    if (b0 < 128u.toUByte()) {
        return b0.toUInt()
    }
    val z = readUtf8Byte(bytes.next())
    var ch = utf8AccContByte(utf8FirstByte(z, 2u), b0)
    if (utf8IsContByte(z)) {
        val y = readUtf8Byte(bytes.next())
        var acc = utf8FirstByte(y, 3u)
        if (utf8IsContByte(y)) {
            val x = readUtf8Byte(bytes.next())
            acc = utf8AccContByte(utf8FirstByte(x, 4u), y)
        }
        ch = utf8AccContByte(acc, z)
        ch = utf8AccContByte(ch, b0)
    }
    return ch
}

public val UTF8_CHAR_WIDTH: UByteArray =
    ubyteArrayOf(
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        1u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        2u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        3u,
        4u,
        4u,
        4u,
        4u,
        4u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
        0u,
    )

public fun containsNonascii(x: Int): Boolean = (x and NONASCII_MASK) != 0

public fun runUtf8Validation(v: List<UByte>): Result<Unit, Utf8Error> {
    var index = 0
    val len = v.size
    while (index < len) {
        val oldOffset = index
        val first = v[index]
        if (first >= 128u.toUByte()) {
            val w = utf8CharWidth(first)
            when (w) {
                2 -> {
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    if (v[index].toByte() >= (-64).toByte()) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 1u.toUByte()))
                    }
                }
                3 -> {
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    val firstInt = first.toInt()
                    val secondInt = v[index].toInt()
                    val validSecond =
                        when {
                            firstInt == 0xE0 && secondInt in 0xA0..0xBF -> true
                            firstInt in 0xE1..0xEC && secondInt in 0x80..0xBF -> true
                            firstInt == 0xED && secondInt in 0x80..0x9F -> true
                            firstInt in 0xEE..0xEF && secondInt in 0x80..0xBF -> true
                            else -> false
                        }
                    if (!validSecond) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 1u.toUByte()))
                    }
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    if (v[index].toByte() >= (-64).toByte()) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 2u.toUByte()))
                    }
                }
                4 -> {
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    val firstInt = first.toInt()
                    val secondInt = v[index].toInt()
                    val validSecond =
                        when {
                            firstInt == 0xF0 && secondInt in 0x90..0xBF -> true
                            firstInt in 0xF1..0xF3 && secondInt in 0x80..0xBF -> true
                            firstInt == 0xF4 && secondInt in 0x80..0x8F -> true
                            else -> false
                        }
                    if (!validSecond) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 1u.toUByte()))
                    }
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    if (v[index].toByte() >= (-64).toByte()) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 2u.toUByte()))
                    }
                    index += 1
                    if (index >= len) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = null))
                    }
                    if (v[index].toByte() >= (-64).toByte()) {
                        return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 3u.toUByte()))
                    }
                }
                else -> return Result.Err<Unit, Utf8Error>(Utf8Error(validUpTo = oldOffset, errorLen = 1u.toUByte()))
            }
            index += 1
        } else {
            val delta = index.wrappingSub(oldOffset)
            if (delta.isMultipleOf(8) || delta >= 0) {
                index += 1
            }
        }
    }
    return Result.Ok<Unit, Utf8Error>(Unit)
}

public val CONT_MASK: UByte = 0x3Fu.toUByte()

public fun utf8CharWidth(b: UByte): Int = UTF8_CHAR_WIDTH[b.toInt()].toInt()

public val NONASCII_MASK: Int = repeatU8Int(0x80u.toUByte())
