@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun llvmSwizzle(a: simd.I8x16, b: simd.I8x16): simd.I8x16 =
    null as simd.I8x16

public actual fun llvmBitselect(a: simd.I8x16, b: simd.I8x16, c: simd.I8x16): simd.I8x16 =
    null as simd.I8x16

public actual fun llvmAnyTrueI8x16(x: simd.I8x16): Int =
    0

public actual fun llvmI8x16AllTrue(x: simd.I8x16): Int =
    0

public actual fun llvmBitmaskI8x16(a: simd.I8x16): Int =
    0

public actual fun llvmAvgrUI8x16(a: simd.I8x16, b: simd.I8x16): simd.I8x16 =
    null as simd.I8x16

public actual fun llvmI16x8ExtaddPairwiseI8x16S(x: simd.I8x16): simd.I16x8 =
    null as simd.I16x8

public actual fun llvmI16x8ExtaddPairwiseI8x16U(x: simd.I8x16): simd.I16x8 =
    null as simd.I16x8

public actual fun llvmQ15mulr(a: simd.I16x8, b: simd.I16x8): simd.I16x8 =
    null as simd.I16x8

public actual fun llvmI16x8AllTrue(x: simd.I16x8): Int =
    0

public actual fun llvmBitmaskI16x8(a: simd.I16x8): Int =
    0

public actual fun llvmAvgrUI16x8(a: simd.I16x8, b: simd.I16x8): simd.I16x8 =
    null as simd.I16x8

public actual fun llvmI32x4ExtaddPairwiseI16x8S(x: simd.I16x8): simd.I32x4 =
    null as simd.I32x4

public actual fun llvmI32x4ExtaddPairwiseI16x8U(x: simd.I16x8): simd.I32x4 =
    null as simd.I32x4

public actual fun llvmI32x4AllTrue(x: simd.I32x4): Int =
    0

public actual fun llvmBitmaskI32x4(a: simd.I32x4): Int =
    0

public actual fun llvmI32x4DotI16x8S(a: simd.I16x8, b: simd.I16x8): simd.I32x4 =
    null as simd.I32x4

public actual fun llvmI64x2AllTrue(x: simd.I64x2): Int =
    0

public actual fun llvmBitmaskI64x2(a: simd.I64x2): Int =
    0

public actual fun llvmF32x4Nearest(x: simd.F32x4): simd.F32x4 =
    null as simd.F32x4

public actual fun llvmF32x4Min(x: simd.F32x4, y: simd.F32x4): simd.F32x4 =
    null as simd.F32x4

public actual fun llvmF32x4Max(x: simd.F32x4, y: simd.F32x4): simd.F32x4 =
    null as simd.F32x4

public actual fun llvmF64x2Nearest(x: simd.F64x2): simd.F64x2 =
    null as simd.F64x2

public actual fun llvmF64x2Min(x: simd.F64x2, y: simd.F64x2): simd.F64x2 =
    null as simd.F64x2

public actual fun llvmF64x2Max(x: simd.F64x2, y: simd.F64x2): simd.F64x2 =
    null as simd.F64x2
