@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I8x16

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun extrq(x: I64x2, y: I8x16): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Sse4aExtrq")

public actual fun extrqi(x: I64x2, len: UByte, idx: UByte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Sse4aExtrqi")

public actual fun insertq(x: I64x2, y: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Sse4aInsertq")

public actual fun insertqi(x: I64x2, y: I64x2, len: UByte, idx: UByte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Sse4aInsertqi")
