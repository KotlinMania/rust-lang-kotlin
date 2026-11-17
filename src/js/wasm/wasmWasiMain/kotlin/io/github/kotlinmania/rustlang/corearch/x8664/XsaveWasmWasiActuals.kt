@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun externXsave64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXsave64")
}

public actual fun externXrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXrstor64")
}

public actual fun externXsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXsaveopt64")
}

public actual fun externXsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXsavec64")
}

public actual fun externXsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXsaves64")
}

public actual fun externXrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664XsaveExternXrstors64")
}
