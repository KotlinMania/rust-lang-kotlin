@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.powerpc64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedInt

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun lxvl(a: ConstPtr, l: ULong): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpc64VsxLxvl")

public actual fun stxvl(v: VectorSignedInt, a: MutPtr, l: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchPowerpc64VsxStxvl")
}
