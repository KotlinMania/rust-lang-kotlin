@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun fxsave64(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664FxsrFxsave64")
}

public actual fun fxrstor64(p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664FxsrFxrstor64")
}
