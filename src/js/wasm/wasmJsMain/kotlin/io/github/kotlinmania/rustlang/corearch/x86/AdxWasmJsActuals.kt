@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun llvmAddcarryU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmSubborrowU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    throw UnsupportedOperationException("Native FFI addon is not available")
