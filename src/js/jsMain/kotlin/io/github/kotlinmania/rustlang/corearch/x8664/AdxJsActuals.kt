@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

public actual fun llvmAddcarryU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSubborrowU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    throw UnsupportedOperationException("Native FFI addon is not available")
