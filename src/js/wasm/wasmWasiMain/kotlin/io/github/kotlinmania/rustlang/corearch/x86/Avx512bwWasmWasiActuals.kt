@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.I16x16
import io.github.kotlinmania.rustlang.simd.I16x32
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.I8x64
import io.github.kotlinmania.rustlang.simd.U16x16
import io.github.kotlinmania.rustlang.simd.U16x32
import io.github.kotlinmania.rustlang.simd.U16x8
import io.github.kotlinmania.rustlang.simd.U64x8
import io.github.kotlinmania.rustlang.simd.U8x16
import io.github.kotlinmania.rustlang.simd.U8x32
import io.github.kotlinmania.rustlang.simd.U8x64

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vpmulhrsw(a: I16x32, b: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmulhrsw")

public actual fun vpmulhuw(a: U16x32, b: U16x32): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmulhuw")

public actual fun vpmulhw(a: I16x32, b: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmulhw")

public actual fun vpmaddwd(a: I16x32, b: I16x32): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmaddwd")

public actual fun vpmaddubsw(a: U8x64, b: I8x64): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmaddubsw")

public actual fun vpackssdw(a: I32x16, b: I32x16): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpackssdw")

public actual fun vpacksswb(a: I16x32, b: I16x32): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpacksswb")

public actual fun vpackusdw(a: I32x16, b: I32x16): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpackusdw")

public actual fun vpackuswb(a: I16x32, b: I16x32): U8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpackuswb")

public actual fun vpsllw(a: I16x32, count: I16x8): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsllw")

public actual fun vpsllvw(a: I16x32, b: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsllvw")

public actual fun vpsllvw256(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsllvw256")

public actual fun vpsllvw128(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsllvw128")

public actual fun vpsrlw(a: I16x32, count: I16x8): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsrlw")

public actual fun vpsrlvw(a: I16x32, b: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsrlvw")

public actual fun vpsrlvw256(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsrlvw256")

public actual fun vpsrlvw128(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsrlvw128")

public actual fun vpsraw(a: I16x32, count: I16x8): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsraw")

public actual fun vpsravw(a: I16x32, count: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsravw")

public actual fun vpsravw256(a: I16x16, count: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsravw256")

public actual fun vpsravw128(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsravw128")

public actual fun vpermi2w(a: I16x32, idx: I16x32, b: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermi2w")

public actual fun vpermi2w256(a: I16x16, idx: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermi2w256")

public actual fun vpermi2w128(a: I16x8, idx: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermi2w128")

public actual fun vpermw(a: I16x32, idx: I16x32): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermw")

public actual fun vpermw256(a: I16x16, idx: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermw256")

public actual fun vpermw128(a: I16x8, idx: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpermw128")

public actual fun vpshufb(a: I8x64, b: I8x64): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpshufb")

public actual fun vpsadbw(a: U8x64, b: U8x64): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpsadbw")

public actual fun vdbpsadbw(a: U8x64, b: U8x64, imm8: Int): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVdbpsadbw")

public actual fun vdbpsadbw256(a: U8x32, b: U8x32, imm8: Int): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVdbpsadbw256")

public actual fun vdbpsadbw128(a: U8x16, b: U8x16, imm8: Int): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVdbpsadbw128")

public actual fun vpmovswb128(a: I16x8, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmovswb128")

public actual fun vpmovuswb128(a: U16x8, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmovuswb128")

public actual fun vpmovswbmem(memAddr: MutPtr, a: I16x32, mask: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmovswbmem")
}

public actual fun vpmovuswbmem(memAddr: MutPtr, a: I16x32, mask: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpmovuswbmem")
}

public actual fun vpavgb(a: U8x64, b: U8x64): U8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpavgb")

public actual fun vpavgw(a: U16x32, b: U16x32): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512bwVpavgw")
