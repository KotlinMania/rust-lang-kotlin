@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchNvptxSyncthreads")
private external fun wasiCorearchNvptxSyncthreads(a: Int): Unit

public actual fun syncthreads(a: UInt): Unit =
    wasiCorearchNvptxSyncthreads(a.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockDimX")
private external fun wasiCorearchNvptxBlockDimX(): Int

public actual fun blockDimX(): UInt =
    wasiCorearchNvptxBlockDimX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockDimY")
private external fun wasiCorearchNvptxBlockDimY(): Int

public actual fun blockDimY(): UInt =
    wasiCorearchNvptxBlockDimY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockDimZ")
private external fun wasiCorearchNvptxBlockDimZ(): Int

public actual fun blockDimZ(): UInt =
    wasiCorearchNvptxBlockDimZ().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockIdxX")
private external fun wasiCorearchNvptxBlockIdxX(): Int

public actual fun blockIdxX(): UInt =
    wasiCorearchNvptxBlockIdxX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockIdxY")
private external fun wasiCorearchNvptxBlockIdxY(): Int

public actual fun blockIdxY(): UInt =
    wasiCorearchNvptxBlockIdxY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxBlockIdxZ")
private external fun wasiCorearchNvptxBlockIdxZ(): Int

public actual fun blockIdxZ(): UInt =
    wasiCorearchNvptxBlockIdxZ().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxGridDimX")
private external fun wasiCorearchNvptxGridDimX(): Int

public actual fun gridDimX(): UInt =
    wasiCorearchNvptxGridDimX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxGridDimY")
private external fun wasiCorearchNvptxGridDimY(): Int

public actual fun gridDimY(): UInt =
    wasiCorearchNvptxGridDimY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxGridDimZ")
private external fun wasiCorearchNvptxGridDimZ(): Int

public actual fun gridDimZ(): UInt =
    wasiCorearchNvptxGridDimZ().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxThreadIdxX")
private external fun wasiCorearchNvptxThreadIdxX(): Int

public actual fun threadIdxX(): UInt =
    wasiCorearchNvptxThreadIdxX().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxThreadIdxY")
private external fun wasiCorearchNvptxThreadIdxY(): Int

public actual fun threadIdxY(): UInt =
    wasiCorearchNvptxThreadIdxY().toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchNvptxThreadIdxZ")
private external fun wasiCorearchNvptxThreadIdxZ(): Int

public actual fun threadIdxZ(): UInt =
    wasiCorearchNvptxThreadIdxZ().toUInt()

public actual fun vprintf(format: ConstPtr, valist: ConstPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxVprintf")

public actual fun malloc(size: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxMalloc")

public actual fun free(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxFree")
}

public actual fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchNvptxAssertfail")
}
