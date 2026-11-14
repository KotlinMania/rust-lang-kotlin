@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32AtomicLlvmAtomicWaitI32")

public actual fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32AtomicLlvmAtomicWaitI64")

public actual fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32AtomicLlvmAtomicNotify")
