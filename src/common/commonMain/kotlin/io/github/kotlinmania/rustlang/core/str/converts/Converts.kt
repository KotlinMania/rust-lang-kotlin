// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.str.converts

import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.validations.runUtf8Validation

public fun fromUtf8(v: List<UByte>): Result<String, Utf8Error> =
    when (val matchVal = runUtf8Validation(v)) {
        is Result.Ok<*, *> -> Result.Ok<String, Utf8Error>(fromUtf8Unchecked(v))
        is Result.Err<*, *> -> Result.Err<String, Utf8Error>(matchVal.errValue())
    }

public fun fromUtf8Mut(v: MutableList<UByte>): Result<String, Utf8Error> =
    when (val matchVal = runUtf8Validation(v)) {
        is Result.Ok<*, *> -> Result.Ok<String, Utf8Error>(fromUtf8UncheckedMut(v))
        is Result.Err<*, *> -> Result.Err<String, Utf8Error>(matchVal.errValue())
    }

public fun fromUtf8Unchecked(v: List<UByte>): String {
    val bytes = ByteArray(v.size) { v[it].toByte() }
    return bytes.decodeToString(throwOnInvalidSequence = false)
}

public fun fromUtf8UncheckedMut(v: MutableList<UByte>): String =
    fromUtf8Unchecked(v)

public fun fromRawParts(ptr: ConstPtr, len: Int): String {
    val bytes = ByteArray(len) { idx -> kmLoadU8(ptr.rawAddress + idx.toLong()).toByte() }
    return bytes.decodeToString(throwOnInvalidSequence = false)
}

public fun fromRawPartsMut(ptr: MutPtr, len: Int): String =
    fromRawParts(ptr.asConstPtr(), len)
