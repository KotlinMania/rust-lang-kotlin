@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

public actual fun llvmSwizzle(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBitselect(a: I8x16, b: I8x16, c: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAnyTrueI8x16(x: I8x16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI8x16AllTrue(x: I8x16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBitmaskI8x16(a: I8x16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAvgrUI8x16(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI16x8ExtaddPairwiseI8x16S(x: I8x16): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI16x8ExtaddPairwiseI8x16U(x: I8x16): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmQ15mulr(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI16x8AllTrue(x: I16x8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBitmaskI16x8(a: I16x8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAvgrUI16x8(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4ExtaddPairwiseI16x8S(x: I16x8): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4ExtaddPairwiseI16x8U(x: I16x8): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4AllTrue(x: I32x4): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBitmaskI32x4(a: I32x4): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI32x4DotI16x8S(a: I16x8, b: I16x8): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmI64x2AllTrue(x: I64x2): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmBitmaskI64x2(a: I64x2): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4Nearest(x: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4Min(x: F32x4, y: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF32x4Max(x: F32x4, y: F32x4): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2Nearest(x: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2Min(x: F64x2, y: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmF64x2Max(x: F64x2, y: F64x2): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")
