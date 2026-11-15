@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteIrg")

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteGmi")

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteLdg")

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteStg")

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteAddg")

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64MteSubp")
