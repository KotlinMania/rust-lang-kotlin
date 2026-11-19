// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.str

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.threeWayCompare
import io.github.kotlinmania.rustlang.core.mem.kmNormalizeUByteList
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error

public fun fromUtf8(v: List<UByte>): Result<String, Utf8Error> =
    io.github.kotlinmania.rustlang.core.str.converts
        .fromUtf8(kmNormalizeUByteList(v))

public fun fromUtf8Mut(v: MutableList<UByte>): Result<String, Utf8Error> =
    io.github.kotlinmania.rustlang.core.str.converts
        .fromUtf8Mut(v)

public fun fromUtf8Unchecked(v: List<UByte>): String =
    io.github.kotlinmania.rustlang.core.str.converts
        .fromUtf8Unchecked(kmNormalizeUByteList(v))

public fun fromUtf8UncheckedMut(v: MutableList<UByte>): String =
    io.github.kotlinmania.rustlang.core.str.converts
        .fromUtf8UncheckedMut(v)

public fun fromUtf8Lossy(v: List<UByte>): String {
    val normalized = kmNormalizeUByteList(v)
    val bytes = ByteArray(normalized.size) { normalized[it].toByte() }
    return bytes.decodeToString(throwOnInvalidSequence = false)
}

public fun String.len(): Int = this.encodeToByteArray().size

public fun String.asBytes(): List<UByte> =
    this.encodeToByteArray().map { it.toUByte() }

public fun String.isCharBoundary(index: Int): Boolean {
    val bytes = this.encodeToByteArray()
    if (index == 0 || index == bytes.size) return true
    if (index < 0 || index > bytes.size) return false
    return bytes[index] >= (-64).toByte()
}

public fun String.cmp(other: String): Ordering = threeWayCompare(this, other)

public fun String.partialCmp(other: String): Ordering = threeWayCompare(this, other)
