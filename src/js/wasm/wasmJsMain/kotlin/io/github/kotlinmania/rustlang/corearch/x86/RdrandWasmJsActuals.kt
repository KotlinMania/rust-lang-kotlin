@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun x86Rdrand16Step(): Pair<UShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Rdrand32Step(): Pair<UInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Rdseed16Step(): Pair<UShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Rdseed32Step(): Pair<UInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")
