@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.panicking

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun rustPanicCleanup(payload: MutPtr): RustAny =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdPanickingRustPanicCleanup")

public actual fun rustStartPanic(payload: PanicPayload): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdPanickingRustStartPanic")
