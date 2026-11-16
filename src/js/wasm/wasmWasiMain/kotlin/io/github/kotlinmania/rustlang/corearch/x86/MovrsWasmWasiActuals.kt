@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun prefetchrs(p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86MovrsPrefetchrs")
}
