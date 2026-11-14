@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

public actual fun llvmRelaxedSwizzle(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmRelaxedTruncSigned(a: F32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmRelaxedTruncUnsigned(a: F32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmRelaxedTruncSignedZero(a: F64x2): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmRelaxedTruncUnsignedZero(a: F64x2): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4Fma(a: F32x4, b: F32x4, c: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4Fms(a: F32x4, b: F32x4, c: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2Fma(a: F64x2, b: F64x2, c: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2Fms(a: F64x2, b: F64x2, c: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI8x16Laneselect(a: I8x16, b: I8x16, c: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI16x8Laneselect(a: I16x8, b: I16x8, c: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4Laneselect(a: I32x4, b: I32x4, c: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI64x2Laneselect(a: I64x2, b: I64x2, c: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4RelaxedMin(a: F32x4, b: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2RelaxedMin(a: F64x2, b: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4RelaxedMax(a: F32x4, b: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2RelaxedMax(a: F64x2, b: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmRelaxedQ15mulrSigned(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI16x8RelaxedDotI8x16I7x16S(a: I8x16, b: I8x16): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4RelaxedDotI8x16I7x16AddS(a: I8x16, b: I8x16, c: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")
