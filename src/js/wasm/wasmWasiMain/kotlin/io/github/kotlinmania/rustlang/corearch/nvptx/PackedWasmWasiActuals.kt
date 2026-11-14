@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun llvmF16x2Minimum(a: F16x2, b: F16x2): F16x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxPackedLlvmF16x2Minimum")

public actual fun llvmF16x2Maximum(a: F16x2, b: F16x2): F16x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxPackedLlvmF16x2Maximum")
