@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun lasxXvsrarB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarB")

public actual fun lasxXvsrarH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarH")

public actual fun lasxXvsrarW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarW")

public actual fun lasxXvsrarD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarD")

public actual fun lasxXvsrariB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariB")

public actual fun lasxXvsrariH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariH")

public actual fun lasxXvsrariW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariW")

public actual fun lasxXvsrariD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariD")

public actual fun lasxXvsrlrB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrB")

public actual fun lasxXvsrlrH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrH")

public actual fun lasxXvsrlrW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrW")

public actual fun lasxXvsrlrD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrD")

public actual fun lasxXvsrlriB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlriB")

public actual fun lasxXvsrlriH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlriH")

public actual fun lasxXvsrlriW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlriW")

public actual fun lasxXvsrlriD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlriD")

public actual fun lasxXvbitclriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitclriB")

public actual fun lasxXvbitclriH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitclriH")

public actual fun lasxXvbitclriW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitclriW")

public actual fun lasxXvbitclriD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitclriD")

public actual fun lasxXvbitsetiB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitsetiB")

public actual fun lasxXvbitsetiH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitsetiH")

public actual fun lasxXvbitsetiW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitsetiW")

public actual fun lasxXvbitsetiD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitsetiD")

public actual fun lasxXvbitreviB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitreviB")

public actual fun lasxXvbitreviH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitreviH")

public actual fun lasxXvbitreviW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitreviW")

public actual fun lasxXvbitreviD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitreviD")

public actual fun lasxXvsubiBu(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubiBu")

public actual fun lasxXvsubiHu(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubiHu")

public actual fun lasxXvsubiWu(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubiWu")

public actual fun lasxXvsubiDu(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubiDu")

public actual fun lasxXvsatB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatB")

public actual fun lasxXvsatH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatH")

public actual fun lasxXvsatW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatW")

public actual fun lasxXvsatD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatD")

public actual fun lasxXvsatBu(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatBu")

public actual fun lasxXvsatHu(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatHu")

public actual fun lasxXvsatWu(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatWu")

public actual fun lasxXvsatDu(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsatDu")

public actual fun lasxXvdivB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivB")

public actual fun lasxXvdivH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivH")

public actual fun lasxXvdivW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivW")

public actual fun lasxXvdivD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivD")

public actual fun lasxXvdivBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivBu")

public actual fun lasxXvdivHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivHu")

public actual fun lasxXvdivWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivWu")

public actual fun lasxXvdivDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvdivDu")

public actual fun lasxXvhaddwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwHB")

public actual fun lasxXvhaddwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwWH")

public actual fun lasxXvhaddwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwDW")

public actual fun lasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwHuBu")

public actual fun lasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwWuHu")

public actual fun lasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwDuWu")

public actual fun lasxXvhsubwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwHB")

public actual fun lasxXvhsubwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwWH")

public actual fun lasxXvhsubwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwDW")

public actual fun lasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwHuBu")

public actual fun lasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwWuHu")

public actual fun lasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwDuWu")

public actual fun lasxXvmodB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodB")

public actual fun lasxXvmodH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodH")

public actual fun lasxXvmodW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodW")

public actual fun lasxXvmodD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodD")

public actual fun lasxXvmodBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodBu")

public actual fun lasxXvmodHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodHu")

public actual fun lasxXvmodWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodWu")

public actual fun lasxXvmodDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmodDu")

public actual fun lasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvshufB")

public actual fun lasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvshufH")

public actual fun lasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvshufW")

public actual fun lasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvshufD")

public actual fun lasxXvandiB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvandiB")

public actual fun lasxXvoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvoriB")

public actual fun lasxXvnoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvnoriB")

public actual fun lasxXvxoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvxoriB")

public actual fun lasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitselV")

public actual fun lasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitseliB")

public actual fun lasxXvcloB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvcloB")

public actual fun lasxXvcloH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvcloH")

public actual fun lasxXvcloW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvcloW")

public actual fun lasxXvcloD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvcloD")

public actual fun lasxXvfcvtHS(a: V8f32, b: V8f32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvtHS")

public actual fun lasxXvfcvtSD(a: V4f64, b: V4f64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvtSD")

public actual fun lasxXvfminS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfminS")

public actual fun lasxXvfminD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfminD")

public actual fun lasxXvfminaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfminaS")

public actual fun lasxXvfminaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfminaD")

public actual fun lasxXvfmaxS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfmaxS")

public actual fun lasxXvfmaxD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfmaxD")

public actual fun lasxXvfmaxaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfmaxaS")

public actual fun lasxXvfmaxaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfmaxaD")

public actual fun lasxXvfclassS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfclassS")

public actual fun lasxXvfclassD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfclassD")

public actual fun lasxXvfrecipeS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrecipeS")

public actual fun lasxXvfrecipeD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrecipeD")

public actual fun lasxXvfrsqrteS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrsqrteS")

public actual fun lasxXvfrsqrteD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrsqrteD")

public actual fun lasxXvfrintS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrintS")

public actual fun lasxXvfrintD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrintD")

public actual fun lasxXvflogbS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvflogbS")

public actual fun lasxXvflogbD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvflogbD")

public actual fun lasxXvfcvthSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvthSH")

public actual fun lasxXvfcvthDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvthDS")

public actual fun lasxXvfcvtlSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvtlSH")

public actual fun lasxXvfcvtlDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcvtlDS")

public actual fun lasxXvftintWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintWS")

public actual fun lasxXvftintLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintLD")

public actual fun lasxXvftintWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintWuS")

public actual fun lasxXvftintLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintLuD")

public actual fun lasxXvftintrzWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzWS")

public actual fun lasxXvftintrzLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzLD")

public actual fun lasxXvftintrzWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzWuS")

public actual fun lasxXvftintrzLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzLuD")

public actual fun lasxXvffintSW(a: V8i32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintSW")

public actual fun lasxXvffintDL(a: V4i64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintDL")

public actual fun lasxXvffintSWu(a: V8u32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintSWu")

public actual fun lasxXvffintDLu(a: V4u64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintDLu")

public actual fun lasxXvreplveB(a: V32i8, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvreplveB")

public actual fun lasxXvreplveH(a: V16i16, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvreplveH")

public actual fun lasxXvreplveW(a: V8i32, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvreplveW")

public actual fun lasxXvreplveD(a: V4i64, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvreplveD")

public actual fun lasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermiW")

public actual fun lasxXvsllwilHB(a: V32i8, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilHB")

public actual fun lasxXvsllwilWH(a: V16i16, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilWH")

public actual fun lasxXvsllwilDW(a: V8i32, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilDW")

public actual fun lasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilHuBu")

public actual fun lasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilWuHu")

public actual fun lasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsllwilDuWu")

public actual fun lasxXvsranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsranBH")

public actual fun lasxXvsranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsranHW")

public actual fun lasxXvsranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsranWD")

public actual fun lasxXvssranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranBH")

public actual fun lasxXvssranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranHW")

public actual fun lasxXvssranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranWD")

public actual fun lasxXvssranBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranBuH")

public actual fun lasxXvssranHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranHuW")

public actual fun lasxXvssranWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssranWuD")

public actual fun lasxXvsrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarnBH")

public actual fun lasxXvsrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarnHW")

public actual fun lasxXvsrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarnWD")

public actual fun lasxXvssrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnBH")

public actual fun lasxXvssrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnHW")

public actual fun lasxXvssrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnWD")

public actual fun lasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnBuH")

public actual fun lasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnHuW")

public actual fun lasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarnWuD")

public actual fun lasxXvsrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlnBH")

public actual fun lasxXvsrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlnHW")

public actual fun lasxXvsrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlnWD")

public actual fun lasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnBuH")

public actual fun lasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnHuW")

public actual fun lasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnWuD")

public actual fun lasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrnBH")

public actual fun lasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrnHW")

public actual fun lasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrnWD")

public actual fun lasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnBuH")

public actual fun lasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnHuW")

public actual fun lasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnWuD")

public actual fun lasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpiB")

public actual fun lasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpiH")

public actual fun lasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpB")

public actual fun lasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpH")

public actual fun lasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvshuf4iD")

public actual fun lasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextrinsB")

public actual fun lasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextrinsH")

public actual fun lasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextrinsW")

public actual fun lasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextrinsD")

public actual fun lasxXvmskltzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmskltzB")

public actual fun lasxXvmskltzH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmskltzH")

public actual fun lasxXvmskltzW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmskltzW")

public actual fun lasxXvmskltzD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmskltzD")

public actual fun lasxXvsigncovB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsigncovB")

public actual fun lasxXvsigncovH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsigncovH")

public actual fun lasxXvsigncovW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsigncovW")

public actual fun lasxXvsigncovD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsigncovD")

public actual fun lasxXvftintrneWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrneWS")

public actual fun lasxXvftintrneLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrneLD")

public actual fun lasxXvftintrpWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrpWS")

public actual fun lasxXvftintrpLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrpLD")

public actual fun lasxXvftintrmWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrmWS")

public actual fun lasxXvftintrmLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrmLD")

public actual fun lasxXvftintWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintWD")

public actual fun lasxXvffintSL(a: V4i64, b: V4i64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintSL")

public actual fun lasxXvftintrzWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzWD")

public actual fun lasxXvftintrpWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrpWD")

public actual fun lasxXvftintrmWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrmWD")

public actual fun lasxXvftintrneWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrneWD")

public actual fun lasxXvftinthLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftinthLS")

public actual fun lasxXvftintlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintlLS")

public actual fun lasxXvffinthDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffinthDW")

public actual fun lasxXvffintlDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvffintlDW")

public actual fun lasxXvftintrzhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzhLS")

public actual fun lasxXvftintrzlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrzlLS")

public actual fun lasxXvftintrphLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrphLS")

public actual fun lasxXvftintrplLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrplLS")

public actual fun lasxXvftintrmhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrmhLS")

public actual fun lasxXvftintrmlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrmlLS")

public actual fun lasxXvftintrnehLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrnehLS")

public actual fun lasxXvftintrnelLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvftintrnelLS")

public actual fun lasxXvfrintrneS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrintrneS")

public actual fun lasxXvfrintrneD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrintrneD")

public actual fun lasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvstelmB")
}

public actual fun lasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvstelmH")
}

public actual fun lasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvstelmW")
}

public actual fun lasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvstelmD")
}

public actual fun lasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvinsve0W")

public actual fun lasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvinsve0D")

public actual fun lasxXvpickveW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveW")

public actual fun lasxXvpickveD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveD")

public actual fun lasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnBH")

public actual fun lasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnHW")

public actual fun lasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrnWD")

public actual fun lasxXvssrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnBH")

public actual fun lasxXvssrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnHW")

public actual fun lasxXvssrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlnWD")

public actual fun lasxXvextlQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextlQuDu")

public actual fun lasxVext2xvHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvHB")

public actual fun lasxVext2xvWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvWH")

public actual fun lasxVext2xvDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDW")

public actual fun lasxVext2xvWB(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvWB")

public actual fun lasxVext2xvDH(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDH")

public actual fun lasxVext2xvDB(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDB")

public actual fun lasxVext2xvHuBu(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvHuBu")

public actual fun lasxVext2xvWuHu(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvWuHu")

public actual fun lasxVext2xvDuWu(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDuWu")

public actual fun lasxVext2xvWuBu(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvWuBu")

public actual fun lasxVext2xvDuHu(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDuHu")

public actual fun lasxVext2xvDuBu(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxVext2xvDuBu")

public actual fun lasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermiQ")

public actual fun lasxXvpermiD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermiD")

public actual fun lasxXvpermW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermW")

public actual fun lasxXvldreplB(a: ConstPtr, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplB")

public actual fun lasxXvldreplH(a: ConstPtr, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplH")

public actual fun lasxXvldreplW(a: ConstPtr, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplW")

public actual fun lasxXvldreplD(a: ConstPtr, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplD")

public actual fun lasxXvaddwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevQD")

public actual fun lasxXvaddwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevDW")

public actual fun lasxXvaddwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevWH")

public actual fun lasxXvaddwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevHB")

public actual fun lasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevQDu")

public actual fun lasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevDWu")

public actual fun lasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevWHu")

public actual fun lasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevHBu")

public actual fun lasxXvsubwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevQD")

public actual fun lasxXvsubwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevDW")

public actual fun lasxXvsubwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevWH")

public actual fun lasxXvsubwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevHB")

public actual fun lasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevQDu")

public actual fun lasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevDWu")

public actual fun lasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevWHu")

public actual fun lasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwevHBu")

public actual fun lasxXvmulwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevQD")

public actual fun lasxXvmulwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevDW")

public actual fun lasxXvmulwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevWH")

public actual fun lasxXvmulwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevHB")

public actual fun lasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevQDu")

public actual fun lasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevDWu")

public actual fun lasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevWHu")

public actual fun lasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevHBu")

public actual fun lasxXvaddwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodQD")

public actual fun lasxXvaddwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodDW")

public actual fun lasxXvaddwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodWH")

public actual fun lasxXvaddwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodHB")

public actual fun lasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodQDu")

public actual fun lasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodDWu")

public actual fun lasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodWHu")

public actual fun lasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodHBu")

public actual fun lasxXvsubwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodQD")

public actual fun lasxXvsubwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodDW")

public actual fun lasxXvsubwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodWH")

public actual fun lasxXvsubwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodHB")

public actual fun lasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodQDu")

public actual fun lasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodDWu")

public actual fun lasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodWHu")

public actual fun lasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubwodHBu")

public actual fun lasxXvmulwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodQD")

public actual fun lasxXvmulwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodDW")

public actual fun lasxXvmulwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodWH")

public actual fun lasxXvmulwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodHB")

public actual fun lasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodQDu")

public actual fun lasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodDWu")

public actual fun lasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodWHu")

public actual fun lasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodHBu")

public actual fun lasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevDWuW")

public actual fun lasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevWHuH")

public actual fun lasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevHBuB")

public actual fun lasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevDWuW")

public actual fun lasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevWHuH")

public actual fun lasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevHBuB")

public actual fun lasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodDWuW")

public actual fun lasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodWHuH")

public actual fun lasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodHBuB")

public actual fun lasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodDWuW")

public actual fun lasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodWHuH")

public actual fun lasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodHBuB")

public actual fun lasxXvhaddwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwQD")

public actual fun lasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhaddwQuDu")

public actual fun lasxXvhsubwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwQD")

public actual fun lasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvhsubwQuDu")

public actual fun lasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevQD")

public actual fun lasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevDW")

public actual fun lasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevWH")

public actual fun lasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevHB")

public actual fun lasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevQDu")

public actual fun lasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevDWu")

public actual fun lasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevWHu")

public actual fun lasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevHBu")

public actual fun lasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodQD")

public actual fun lasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodDW")

public actual fun lasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodWH")

public actual fun lasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodHB")

public actual fun lasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodQDu")

public actual fun lasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodDWu")

public actual fun lasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodWHu")

public actual fun lasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodHBu")

public actual fun lasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevQDuD")

public actual fun lasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevDWuW")

public actual fun lasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevWHuH")

public actual fun lasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwevHBuB")

public actual fun lasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodQDuD")

public actual fun lasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodDWuW")

public actual fun lasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodWHuH")

public actual fun lasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmaddwodHBuB")

public actual fun lasxXvaddQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddQ")

public actual fun lasxXvsubQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsubQ")

public actual fun lasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwevQDuD")

public actual fun lasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvaddwodQDuD")

public actual fun lasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwevQDuD")

public actual fun lasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmulwodQDuD")

public actual fun lasxXvmskgezB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmskgezB")

public actual fun lasxXvmsknzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvmsknzB")

public actual fun lasxXvexthHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthHB")

public actual fun lasxXvexthWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthWH")

public actual fun lasxXvexthDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthDW")

public actual fun lasxXvexthQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthQD")

public actual fun lasxXvexthHuBu(a: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthHuBu")

public actual fun lasxXvexthWuHu(a: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthWuHu")

public actual fun lasxXvexthDuWu(a: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthDuWu")

public actual fun lasxXvexthQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvexthQuDu")

public actual fun lasxXvextlQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvextlQD")

public actual fun lasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlniBH")

public actual fun lasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlniHW")

public actual fun lasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlniWD")

public actual fun lasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlniDQ")

public actual fun lasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrniBH")

public actual fun lasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrniHW")

public actual fun lasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrniWD")

public actual fun lasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrlrniDQ")

public actual fun lasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniBH")

public actual fun lasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniHW")

public actual fun lasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniWD")

public actual fun lasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniDQ")

public actual fun lasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniBuH")

public actual fun lasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniHuW")

public actual fun lasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniWuD")

public actual fun lasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlniDuQ")

public actual fun lasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniBH")

public actual fun lasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniHW")

public actual fun lasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniWD")

public actual fun lasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniDQ")

public actual fun lasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniBuH")

public actual fun lasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniHuW")

public actual fun lasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniWuD")

public actual fun lasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrlrniDuQ")

public actual fun lasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsraniBH")

public actual fun lasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsraniHW")

public actual fun lasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsraniWD")

public actual fun lasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsraniDQ")

public actual fun lasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarniBH")

public actual fun lasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarniHW")

public actual fun lasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarniWD")

public actual fun lasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrarniDQ")

public actual fun lasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniBH")

public actual fun lasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniHW")

public actual fun lasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniWD")

public actual fun lasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniDQ")

public actual fun lasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniBuH")

public actual fun lasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniHuW")

public actual fun lasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniWuD")

public actual fun lasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssraniDuQ")

public actual fun lasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniBH")

public actual fun lasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniHW")

public actual fun lasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniWD")

public actual fun lasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniDQ")

public actual fun lasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniBuH")

public actual fun lasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniHuW")

public actual fun lasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniWuD")

public actual fun lasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvssrarniDuQ")

public actual fun lasxXbnzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbnzB")

public actual fun lasxXbnzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbnzD")

public actual fun lasxXbnzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbnzH")

public actual fun lasxXbnzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbnzV")

public actual fun lasxXbnzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbnzW")

public actual fun lasxXbzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbzB")

public actual fun lasxXbzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbzD")

public actual fun lasxXbzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbzH")

public actual fun lasxXbzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbzV")

public actual fun lasxXbzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXbzW")

public actual fun lasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCafD")

public actual fun lasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCafS")

public actual fun lasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCeqD")

public actual fun lasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCeqS")

public actual fun lasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCleD")

public actual fun lasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCleS")

public actual fun lasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCltD")

public actual fun lasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCltS")

public actual fun lasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCneD")

public actual fun lasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCneS")

public actual fun lasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCorD")

public actual fun lasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCorS")

public actual fun lasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCueqD")

public actual fun lasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCueqS")

public actual fun lasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCuleD")

public actual fun lasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCuleS")

public actual fun lasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCultD")

public actual fun lasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCultS")

public actual fun lasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCunD")

public actual fun lasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCuneD")

public actual fun lasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCuneS")

public actual fun lasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpCunS")

public actual fun lasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSafD")

public actual fun lasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSafS")

public actual fun lasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSeqD")

public actual fun lasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSeqS")

public actual fun lasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSleD")

public actual fun lasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSleS")

public actual fun lasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSltD")

public actual fun lasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSltS")

public actual fun lasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSneD")

public actual fun lasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSneS")

public actual fun lasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSorD")

public actual fun lasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSorS")

public actual fun lasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSueqD")

public actual fun lasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSueqS")

public actual fun lasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSuleD")

public actual fun lasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSuleS")

public actual fun lasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSultD")

public actual fun lasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSultS")

public actual fun lasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSunD")

public actual fun lasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSuneD")

public actual fun lasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSuneS")

public actual fun lasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfcmpSunS")

public actual fun lasxXvpickveDF(a: V4f64, b: UInt): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveDF")

public actual fun lasxXvpickveWF(a: V8f32, b: UInt): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveWF")

public actual fun lasxCast128S(a: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxCast128S")

public actual fun lasxCast128D(a: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxCast128D")

public actual fun lasxCast128(a: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxCast128")

public actual fun lasxConcat128S(a: V4f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxConcat128S")

public actual fun lasxConcat128D(a: V2f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxConcat128D")

public actual fun lasxConcat128(a: V2i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxConcat128")

public actual fun lasxExtract128LoS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128LoS")

public actual fun lasxExtract128HiS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128HiS")

public actual fun lasxExtract128LoD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128LoD")

public actual fun lasxExtract128HiD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128HiD")

public actual fun lasxExtract128Lo(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128Lo")

public actual fun lasxExtract128Hi(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxExtract128Hi")

public actual fun lasxInsert128LoS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128LoS")

public actual fun lasxInsert128HiS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128HiS")

public actual fun lasxInsert128LoD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128LoD")

public actual fun lasxInsert128HiD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128HiD")

public actual fun lasxInsert128Lo(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128Lo")

public actual fun lasxInsert128Hi(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxInsert128Hi")
