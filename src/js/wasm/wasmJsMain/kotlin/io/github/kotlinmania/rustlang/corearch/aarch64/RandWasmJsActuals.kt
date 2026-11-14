@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

public actual fun rndr(): Tuple =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rndrrs(): Tuple =
    throw UnsupportedOperationException("Native FFI addon is not available")
