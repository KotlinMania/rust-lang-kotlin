@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun vcmpph128(a: M128h, b: M128h, imm5: Int, mask: Mmask8): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcmpph128")

public actual fun vcmpph256(a: M256h, b: M256h, imm5: Int, mask: Mmask16): Mmask16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcmpph256")

public actual fun vcmpph512(a: M512h, b: M512h, imm5: Int, mask: Mmask32, sae: Int): Mmask32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcmpph512")

public actual fun vcmpsh(a: M128h, b: M128h, imm5: Int, mask: Mmask8, sae: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcmpsh")

public actual fun vcomish(a: M128h, b: M128h, imm5: Int, sae: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcomish")

public actual fun vaddph(a: M512h, b: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vaddph")

public actual fun vsubph(a: M512h, b: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vsubph")

public actual fun vmulph(a: M512h, b: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmulph")

public actual fun vdivph(a: M512h, b: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vdivph")

public actual fun vaddsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vaddsh")

public actual fun vsubsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vsubsh")

public actual fun vmulsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmulsh")

public actual fun vdivsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vdivsh")

public actual fun vfmulcph128(a: M128, b: M128, src: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmulcph128")

public actual fun vfmulcph256(a: M256, b: M256, src: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmulcph256")

public actual fun vfmulcph512(a: M512, b: M512, src: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmulcph512")

public actual fun vfmulcsh(a: M128, b: M128, src: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmulcsh")

public actual fun vfcmulcph128(a: M128, b: M128, src: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfcmulcph128")

public actual fun vfcmulcph256(a: M256, b: M256, src: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfcmulcph256")

public actual fun vfcmulcph512(a: M512, b: M512, src: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfcmulcph512")

public actual fun vfcmulcsh(a: M128, b: M128, src: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfcmulcsh")

public actual fun vfmaddcphMask3128(a: M128, b: M128, c: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMask3128")

public actual fun vfmaddcphMaskz128(a: M128, b: M128, c: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMaskz128")

public actual fun vfmaddcphMask3256(a: M256, b: M256, c: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMask3256")

public actual fun vfmaddcphMaskz256(a: M256, b: M256, c: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMaskz256")

public actual fun vfmaddcphMask3512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMask3512")

public actual fun vfmaddcphMaskz512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcphMaskz512")

public actual fun vfmaddcshMask(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcshMask")

public actual fun vfmaddcshMaskz(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfmaddcshMaskz")

public actual fun vfcmaddcphMask3128(a: M128, b: M128, c: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMask3128")

public actual fun vfcmaddcphMaskz128(a: M128, b: M128, c: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMaskz128")

public actual fun vfcmaddcphMask3256(a: M256, b: M256, c: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMask3256")

public actual fun vfcmaddcphMaskz256(a: M256, b: M256, c: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMaskz256")

public actual fun vfcmaddcphMask3512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMask3512")

public actual fun vfcmaddcphMaskz512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcphMaskz512")

public actual fun vfcmaddcshMask(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcshMask")

public actual fun vfcmaddcshMaskz(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16VfcmaddcshMaskz")

public actual fun vfmaddph512(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmaddph512")

public actual fun vfmaddsh(a: F16, b: F16, c: F16, rounding: Int): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmaddsh")

public actual fun vfmaddsubph512(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfmaddsubph512")

public actual fun vrcpph128(a: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrcpph128")

public actual fun vrcpph256(a: M256h, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrcpph256")

public actual fun vrcpph512(a: M512h, src: M512h, k: Mmask32): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrcpph512")

public actual fun vrcpsh(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrcpsh")

public actual fun vrsqrtph128(a: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrsqrtph128")

public actual fun vrsqrtph256(a: M256h, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrsqrtph256")

public actual fun vrsqrtph512(a: M512h, src: M512h, k: Mmask32): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrsqrtph512")

public actual fun vrsqrtsh(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrsqrtsh")

public actual fun vsqrtph512(a: M512h, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vsqrtph512")

public actual fun vsqrtsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vsqrtsh")

public actual fun vmaxph128(a: M128h, b: M128h): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmaxph128")

public actual fun vmaxph256(a: M256h, b: M256h): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmaxph256")

public actual fun vmaxph512(a: M512h, b: M512h, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmaxph512")

public actual fun vmaxsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vmaxsh")

public actual fun vminph128(a: M128h, b: M128h): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vminph128")

public actual fun vminph256(a: M256h, b: M256h): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vminph256")

public actual fun vminph512(a: M512h, b: M512h, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vminph512")

public actual fun vminsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vminsh")

public actual fun vgetexpph128(a: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetexpph128")

public actual fun vgetexpph256(a: M256h, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetexpph256")

public actual fun vgetexpph512(a: M512h, src: M512h, k: Mmask32, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetexpph512")

public actual fun vgetexpsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetexpsh")

public actual fun vgetmantph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetmantph128")

public actual fun vgetmantph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetmantph256")

public actual fun vgetmantph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetmantph512")

public actual fun vgetmantsh(a: M128h, b: M128h, imm8: Int, src: M128h, k: Mmask8, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vgetmantsh")

public actual fun vrndscaleph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrndscaleph128")

public actual fun vrndscaleph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrndscaleph256")

public actual fun vrndscaleph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrndscaleph512")

public actual fun vrndscalesh(a: M128h, b: M128h, src: M128h, k: Mmask8, imm8: Int, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vrndscalesh")

public actual fun vscalefph128(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vscalefph128")

public actual fun vscalefph256(a: M256h, b: M256h, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vscalefph256")

public actual fun vscalefph512(a: M512h, b: M512h, src: M512h, k: Mmask32, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vscalefph512")

public actual fun vscalefsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vscalefsh")

public actual fun vreduceph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vreduceph128")

public actual fun vreduceph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vreduceph256")

public actual fun vreduceph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vreduceph512")

public actual fun vreducesh(a: M128h, b: M128h, src: M128h, k: Mmask8, imm8: Int, sae: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vreducesh")

public actual fun vfpclassph128(a: M128h, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfpclassph128")

public actual fun vfpclassph256(a: M256h, imm8: Int): Mmask16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfpclassph256")

public actual fun vfpclassph512(a: M512h, imm8: Int): Mmask32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfpclassph512")

public actual fun vfpclasssh(a: M128h, imm8: Int, k: Mmask8): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vfpclasssh")

public actual fun vcvtw2ph128(a: I16x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtw2ph128")

public actual fun vcvtw2ph256(a: I16x16, rounding: Int): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtw2ph256")

public actual fun vcvtw2ph512(a: I16x32, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtw2ph512")

public actual fun vcvtuw2ph128(a: U16x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuw2ph128")

public actual fun vcvtuw2ph256(a: U16x16, rounding: Int): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuw2ph256")

public actual fun vcvtuw2ph512(a: U16x32, rounding: Int): M512h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuw2ph512")

public actual fun vcvtdq2ph128(a: I32x4, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtdq2ph128")

public actual fun vcvtdq2ph256(a: I32x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtdq2ph256")

public actual fun vcvtdq2ph512(a: I32x16, rounding: Int): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtdq2ph512")

public actual fun vcvtsi2sh(a: M128h, b: Int, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsi2sh")

public actual fun vcvtudq2ph128(a: U32x4, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtudq2ph128")

public actual fun vcvtudq2ph256(a: U32x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtudq2ph256")

public actual fun vcvtudq2ph512(a: U32x16, rounding: Int): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtudq2ph512")

public actual fun vcvtusi2sh(a: M128h, b: UInt, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtusi2sh")

public actual fun vcvtqq2ph128(a: I64x2, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtqq2ph128")

public actual fun vcvtqq2ph256(a: I64x4, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtqq2ph256")

public actual fun vcvtqq2ph512(a: I64x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtqq2ph512")

public actual fun vcvtuqq2ph128(a: U64x2, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuqq2ph128")

public actual fun vcvtuqq2ph256(a: U64x4, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuqq2ph256")

public actual fun vcvtuqq2ph512(a: U64x8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtuqq2ph512")

public actual fun vcvtps2phx128(a: M128, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtps2phx128")

public actual fun vcvtps2phx256(a: M256, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtps2phx256")

public actual fun vcvtps2phx512(a: M512, src: M256h, k: Mmask16, rounding: Int): M256h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtps2phx512")

public actual fun vcvtss2sh(a: M128h, b: M128, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtss2sh")

public actual fun vcvtpd2ph128(a: M128d, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtpd2ph128")

public actual fun vcvtpd2ph256(a: M256d, src: M128h, k: Mmask8): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtpd2ph256")

public actual fun vcvtpd2ph512(a: M512d, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtpd2ph512")

public actual fun vcvtsd2sh(a: M128h, b: M128d, src: M128h, k: Mmask8, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsd2sh")

public actual fun vcvtph2w128(a: M128h, src: I16x8, k: Mmask8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2w128")

public actual fun vcvtph2w256(a: M256h, src: I16x16, k: Mmask16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2w256")

public actual fun vcvtph2w512(a: M512h, src: I16x32, k: Mmask32, rounding: Int): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2w512")

public actual fun vcvtph2uw128(a: M128h, src: U16x8, k: Mmask8): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uw128")

public actual fun vcvtph2uw256(a: M256h, src: U16x16, k: Mmask16): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uw256")

public actual fun vcvtph2uw512(a: M512h, src: U16x32, k: Mmask32, sae: Int): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uw512")

public actual fun vcvttph2w128(a: M128h, src: I16x8, k: Mmask8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2w128")

public actual fun vcvttph2w256(a: M256h, src: I16x16, k: Mmask16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2w256")

public actual fun vcvttph2w512(a: M512h, src: I16x32, k: Mmask32, sae: Int): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2w512")

public actual fun vcvttph2uw128(a: M128h, src: U16x8, k: Mmask8): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uw128")

public actual fun vcvttph2uw256(a: M256h, src: U16x16, k: Mmask16): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uw256")

public actual fun vcvttph2uw512(a: M512h, src: U16x32, k: Mmask32, sae: Int): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uw512")

public actual fun vcvtph2dq128(a: M128h, src: I32x4, k: Mmask8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2dq128")

public actual fun vcvtph2dq256(a: M128h, src: I32x8, k: Mmask8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2dq256")

public actual fun vcvtph2dq512(a: M256h, src: I32x16, k: Mmask16, rounding: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2dq512")

public actual fun vcvtsh2si32(a: M128h, rounding: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsh2si32")

public actual fun vcvtph2udq128(a: M128h, src: U32x4, k: Mmask8): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2udq128")

public actual fun vcvtph2udq256(a: M128h, src: U32x8, k: Mmask8): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2udq256")

public actual fun vcvtph2udq512(a: M256h, src: U32x16, k: Mmask16, rounding: Int): U32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2udq512")

public actual fun vcvtsh2usi32(a: M128h, sae: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsh2usi32")

public actual fun vcvttph2dq128(a: M128h, src: I32x4, k: Mmask8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2dq128")

public actual fun vcvttph2dq256(a: M128h, src: I32x8, k: Mmask8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2dq256")

public actual fun vcvttph2dq512(a: M256h, src: I32x16, k: Mmask16, sae: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2dq512")

public actual fun vcvttsh2si32(a: M128h, sae: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttsh2si32")

public actual fun vcvttph2udq128(a: M128h, src: U32x4, k: Mmask8): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2udq128")

public actual fun vcvttph2udq256(a: M128h, src: U32x8, k: Mmask8): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2udq256")

public actual fun vcvttph2udq512(a: M256h, src: U32x16, k: Mmask16, sae: Int): U32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2udq512")

public actual fun vcvttsh2usi32(a: M128h, sae: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttsh2usi32")

public actual fun vcvtph2qq128(a: M128h, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2qq128")

public actual fun vcvtph2qq256(a: M128h, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2qq256")

public actual fun vcvtph2qq512(a: M128h, src: I64x8, k: Mmask8, rounding: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2qq512")

public actual fun vcvtph2uqq128(a: M128h, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uqq128")

public actual fun vcvtph2uqq256(a: M128h, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uqq256")

public actual fun vcvtph2uqq512(a: M128h, src: U64x8, k: Mmask8, rounding: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2uqq512")

public actual fun vcvttph2qq128(a: M128h, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2qq128")

public actual fun vcvttph2qq256(a: M128h, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2qq256")

public actual fun vcvttph2qq512(a: M128h, src: I64x8, k: Mmask8, sae: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2qq512")

public actual fun vcvttph2uqq128(a: M128h, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uqq128")

public actual fun vcvttph2uqq256(a: M128h, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uqq256")

public actual fun vcvttph2uqq512(a: M128h, src: U64x8, k: Mmask8, sae: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvttph2uqq512")

public actual fun vcvtph2psx128(a: M128h, src: M128, k: Mmask8): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2psx128")

public actual fun vcvtph2psx256(a: M128h, src: M256, k: Mmask8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2psx256")

public actual fun vcvtph2psx512(a: M256h, src: M512, k: Mmask16, sae: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2psx512")

public actual fun vcvtsh2ss(a: M128, b: M128h, src: M128, k: Mmask8, sae: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsh2ss")

public actual fun vcvtph2pd128(a: M128h, src: M128d, k: Mmask8): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2pd128")

public actual fun vcvtph2pd256(a: M128h, src: M256d, k: Mmask8): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2pd256")

public actual fun vcvtph2pd512(a: M128h, src: M512d, k: Mmask8, sae: Int): M512d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtph2pd512")

public actual fun vcvtsh2sd(a: M128d, b: M128h, src: M128d, k: Mmask8, sae: Int): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fp16Vcvtsh2sd")
