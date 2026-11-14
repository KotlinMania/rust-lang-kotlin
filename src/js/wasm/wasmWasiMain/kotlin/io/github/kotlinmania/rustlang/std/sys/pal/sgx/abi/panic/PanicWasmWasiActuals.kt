@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysPalSgxAbiPanicGetDEBUG")
private external fun wasiStdSysPalSgxAbiPanicGetDEBUG(): Int

public actual val DEBUG: UByte
    get() = wasiStdSysPalSgxAbiPanicGetDEBUG().toUByte()

public actual fun takeDebugPanicBufPtr(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalSgxAbiPanicTakeDebugPanicBufPtr")
