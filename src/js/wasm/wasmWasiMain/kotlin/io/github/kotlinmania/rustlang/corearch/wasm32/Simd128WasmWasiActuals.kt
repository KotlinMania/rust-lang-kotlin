@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun llvmSwizzle(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmSwizzle")

public actual fun llvmBitselect(a: I8x16, b: I8x16, c: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmBitselect")

public actual fun llvmAnyTrueI8x16(x: I8x16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmAnyTrueI8x16")

public actual fun llvmI8x16AllTrue(x: I8x16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI8x16AllTrue")

public actual fun llvmBitmaskI8x16(a: I8x16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmBitmaskI8x16")

public actual fun llvmAvgrUI8x16(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmAvgrUI8x16")

public actual fun llvmI16x8ExtaddPairwiseI8x16S(x: I8x16): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI16x8ExtaddPairwiseI8x16S")

public actual fun llvmI16x8ExtaddPairwiseI8x16U(x: I8x16): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI16x8ExtaddPairwiseI8x16U")

public actual fun llvmQ15mulr(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmQ15mulr")

public actual fun llvmI16x8AllTrue(x: I16x8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI16x8AllTrue")

public actual fun llvmBitmaskI16x8(a: I16x8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmBitmaskI16x8")

public actual fun llvmAvgrUI16x8(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmAvgrUI16x8")

public actual fun llvmI32x4ExtaddPairwiseI16x8S(x: I16x8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI32x4ExtaddPairwiseI16x8S")

public actual fun llvmI32x4ExtaddPairwiseI16x8U(x: I16x8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI32x4ExtaddPairwiseI16x8U")

public actual fun llvmI32x4AllTrue(x: I32x4): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI32x4AllTrue")

public actual fun llvmBitmaskI32x4(a: I32x4): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmBitmaskI32x4")

public actual fun llvmI32x4DotI16x8S(a: I16x8, b: I16x8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI32x4DotI16x8S")

public actual fun llvmI64x2AllTrue(x: I64x2): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmI64x2AllTrue")

public actual fun llvmBitmaskI64x2(a: I64x2): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmBitmaskI64x2")

public actual fun llvmF32x4Nearest(x: F32x4): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF32x4Nearest")

public actual fun llvmF32x4Min(x: F32x4, y: F32x4): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF32x4Min")

public actual fun llvmF32x4Max(x: F32x4, y: F32x4): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF32x4Max")

public actual fun llvmF64x2Nearest(x: F64x2): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF64x2Nearest")

public actual fun llvmF64x2Min(x: F64x2, y: F64x2): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF64x2Min")

public actual fun llvmF64x2Max(x: F64x2, y: F64x2): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32Simd128LlvmF64x2Max")
