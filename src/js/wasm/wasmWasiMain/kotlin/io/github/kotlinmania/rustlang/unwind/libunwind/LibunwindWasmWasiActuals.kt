@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

// The host must provide the C companion wrapper exports in import module unwind.
public actual fun unwindResume(exception: MutPtr): Nothing =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindResume")

public actual fun unwindDeleteException(exception: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindDeleteException")
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetLanguageSpecificData")

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetRegionStart")

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetTextRelBase")

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindGetDataRelBase")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for unwindLibunwindUnwindRaiseException")
