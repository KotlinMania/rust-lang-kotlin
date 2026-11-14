@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.backtrace.print.fuchsia

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsdliteratephdr.callback

// The host must provide the C companion wrapper exports in import module backtrace.
public actual fun dlIteratePhdr(f: DlIteratePhdrFCallback, data: DsoPrinter): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for backtracePrintFuchsiaDlIteratePhdr")
