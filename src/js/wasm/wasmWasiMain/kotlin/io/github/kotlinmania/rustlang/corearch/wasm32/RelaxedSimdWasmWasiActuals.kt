@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun llvmRelaxedSwizzle(a: simd.I8x16, b: simd.I8x16): simd.I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedSwizzle")

public actual fun llvmRelaxedTruncSigned(a: simd.F32x4): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedTruncSigned")

public actual fun llvmRelaxedTruncUnsigned(a: simd.F32x4): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedTruncUnsigned")

public actual fun llvmRelaxedTruncSignedZero(a: simd.F64x2): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedTruncSignedZero")

public actual fun llvmRelaxedTruncUnsignedZero(a: simd.F64x2): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedTruncUnsignedZero")

public actual fun llvmF32x4Fma(a: simd.F32x4, b: simd.F32x4, c: simd.F32x4): simd.F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF32x4Fma")

public actual fun llvmF32x4Fms(a: simd.F32x4, b: simd.F32x4, c: simd.F32x4): simd.F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF32x4Fms")

public actual fun llvmF64x2Fma(a: simd.F64x2, b: simd.F64x2, c: simd.F64x2): simd.F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF64x2Fma")

public actual fun llvmF64x2Fms(a: simd.F64x2, b: simd.F64x2, c: simd.F64x2): simd.F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF64x2Fms")

public actual fun llvmI8x16Laneselect(a: simd.I8x16, b: simd.I8x16, c: simd.I8x16): simd.I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI8x16Laneselect")

public actual fun llvmI16x8Laneselect(a: simd.I16x8, b: simd.I16x8, c: simd.I16x8): simd.I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI16x8Laneselect")

public actual fun llvmI32x4Laneselect(a: simd.I32x4, b: simd.I32x4, c: simd.I32x4): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI32x4Laneselect")

public actual fun llvmI64x2Laneselect(a: simd.I64x2, b: simd.I64x2, c: simd.I64x2): simd.I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI64x2Laneselect")

public actual fun llvmF32x4RelaxedMin(a: simd.F32x4, b: simd.F32x4): simd.F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF32x4RelaxedMin")

public actual fun llvmF64x2RelaxedMin(a: simd.F64x2, b: simd.F64x2): simd.F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF64x2RelaxedMin")

public actual fun llvmF32x4RelaxedMax(a: simd.F32x4, b: simd.F32x4): simd.F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF32x4RelaxedMax")

public actual fun llvmF64x2RelaxedMax(a: simd.F64x2, b: simd.F64x2): simd.F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmF64x2RelaxedMax")

public actual fun llvmRelaxedQ15mulrSigned(a: simd.I16x8, b: simd.I16x8): simd.I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmRelaxedQ15mulrSigned")

public actual fun llvmI16x8RelaxedDotI8x16I7x16S(a: simd.I8x16, b: simd.I8x16): simd.I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI16x8RelaxedDotI8x16I7x16S")

public actual fun llvmI32x4RelaxedDotI8x16I7x16AddS(a: simd.I8x16, b: simd.I8x16, c: simd.I32x4): simd.I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchWasm32RelaxedSimdLlvmI32x4RelaxedDotI8x16I7x16AddS")
