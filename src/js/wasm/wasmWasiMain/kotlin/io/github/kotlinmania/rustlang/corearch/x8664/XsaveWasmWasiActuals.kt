@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun xsave64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXsave64")
}

public actual fun xrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXrstor64")
}

public actual fun xsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXsaveopt64")
}

public actual fun xsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXsavec64")
}

public actual fun xsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXsaves64")
}

public actual fun xrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664XsaveXrstors64")
}
