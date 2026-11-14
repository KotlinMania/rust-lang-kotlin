@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ptr

// The host must provide the C companion wrapper exports in import module std.
public actual fun malloc(size: CSizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocUnixMalloc")

public actual fun realloc(ptr: MutPtr, size: CSizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocUnixRealloc")

public actual fun free(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysAllocUnixFree")
}
