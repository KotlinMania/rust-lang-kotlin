@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

public actual fun externRndr(): Tuple =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externRndrrs(): Tuple =
    throw UnsupportedOperationException("Native FFI addon is not available")
