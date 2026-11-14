@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.backtrace.print.fuchsia

public actual fun dlIteratePhdr(f: DlIteratePhdrFCallback, data: DsoPrinter): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")
