@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.powerpc.VectorUnsignedChar

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchPowerpcVsxVperm")
