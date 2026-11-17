@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.V4f32

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun externLasxXvsrarB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarB")

public actual fun externLasxXvsrarH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarH")

public actual fun externLasxXvsrarW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarW")

public actual fun externLasxXvsrarD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarD")

public actual fun lasxXvsrariB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariB")

public actual fun lasxXvsrariH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariH")

public actual fun lasxXvsrariW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariW")

public actual fun lasxXvsrariD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvsrariD")

public actual fun externLasxXvsrlrB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrB")

public actual fun externLasxXvsrlrH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrH")

public actual fun externLasxXvsrlrW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrW")

public actual fun externLasxXvsrlrD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrD")

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

public actual fun externLasxXvdivB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivB")

public actual fun externLasxXvdivH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivH")

public actual fun externLasxXvdivW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivW")

public actual fun externLasxXvdivD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivD")

public actual fun externLasxXvdivBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivBu")

public actual fun externLasxXvdivHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivHu")

public actual fun externLasxXvdivWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivWu")

public actual fun externLasxXvdivDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvdivDu")

public actual fun externLasxXvhaddwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwHB")

public actual fun externLasxXvhaddwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwWH")

public actual fun externLasxXvhaddwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwDW")

public actual fun externLasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwHuBu")

public actual fun externLasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwWuHu")

public actual fun externLasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwDuWu")

public actual fun externLasxXvhsubwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwHB")

public actual fun externLasxXvhsubwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwWH")

public actual fun externLasxXvhsubwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwDW")

public actual fun externLasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwHuBu")

public actual fun externLasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwWuHu")

public actual fun externLasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwDuWu")

public actual fun externLasxXvmodB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodB")

public actual fun externLasxXvmodH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodH")

public actual fun externLasxXvmodW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodW")

public actual fun externLasxXvmodD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodD")

public actual fun externLasxXvmodBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodBu")

public actual fun externLasxXvmodHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodHu")

public actual fun externLasxXvmodWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodWu")

public actual fun externLasxXvmodDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmodDu")

public actual fun externLasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvshufB")

public actual fun externLasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvshufH")

public actual fun externLasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvshufW")

public actual fun externLasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvshufD")

public actual fun lasxXvandiB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvandiB")

public actual fun lasxXvoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvoriB")

public actual fun lasxXvnoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvnoriB")

public actual fun lasxXvxoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvxoriB")

public actual fun externLasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvbitselV")

public actual fun lasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvbitseliB")

public actual fun externLasxXvcloB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvcloB")

public actual fun externLasxXvcloH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvcloH")

public actual fun externLasxXvcloW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvcloW")

public actual fun externLasxXvcloD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvcloD")

public actual fun externLasxXvfcvtHS(a: V8f32, b: V8f32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvtHS")

public actual fun externLasxXvfcvtSD(a: V4f64, b: V4f64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvtSD")

public actual fun externLasxXvfminS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfminS")

public actual fun externLasxXvfminD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfminD")

public actual fun externLasxXvfminaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfminaS")

public actual fun externLasxXvfminaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfminaD")

public actual fun externLasxXvfmaxS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfmaxS")

public actual fun externLasxXvfmaxD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfmaxD")

public actual fun externLasxXvfmaxaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfmaxaS")

public actual fun externLasxXvfmaxaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfmaxaD")

public actual fun externLasxXvfclassS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfclassS")

public actual fun externLasxXvfclassD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfclassD")

public actual fun externLasxXvfrecipeS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrecipeS")

public actual fun externLasxXvfrecipeD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrecipeD")

public actual fun externLasxXvfrsqrteS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrsqrteS")

public actual fun externLasxXvfrsqrteD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrsqrteD")

public actual fun externLasxXvfrintS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrintS")

public actual fun externLasxXvfrintD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrintD")

public actual fun externLasxXvflogbS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvflogbS")

public actual fun externLasxXvflogbD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvflogbD")

public actual fun externLasxXvfcvthSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvthSH")

public actual fun externLasxXvfcvthDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvthDS")

public actual fun externLasxXvfcvtlSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvtlSH")

public actual fun externLasxXvfcvtlDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcvtlDS")

public actual fun externLasxXvftintWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintWS")

public actual fun externLasxXvftintLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintLD")

public actual fun externLasxXvftintWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintWuS")

public actual fun externLasxXvftintLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintLuD")

public actual fun externLasxXvftintrzWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzWS")

public actual fun externLasxXvftintrzLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzLD")

public actual fun externLasxXvftintrzWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzWuS")

public actual fun externLasxXvftintrzLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzLuD")

public actual fun externLasxXvffintSW(a: V8i32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintSW")

public actual fun externLasxXvffintDL(a: V4i64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintDL")

public actual fun externLasxXvffintSWu(a: V8u32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintSWu")

public actual fun externLasxXvffintDLu(a: V4u64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintDLu")

public actual fun externLasxXvreplveB(a: V32i8, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvreplveB")

public actual fun externLasxXvreplveH(a: V16i16, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvreplveH")

public actual fun externLasxXvreplveW(a: V8i32, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvreplveW")

public actual fun externLasxXvreplveD(a: V4i64, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvreplveD")

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

public actual fun externLasxXvsranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsranBH")

public actual fun externLasxXvsranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsranHW")

public actual fun externLasxXvsranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsranWD")

public actual fun externLasxXvssranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranBH")

public actual fun externLasxXvssranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranHW")

public actual fun externLasxXvssranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranWD")

public actual fun externLasxXvssranBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranBuH")

public actual fun externLasxXvssranHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranHuW")

public actual fun externLasxXvssranWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssranWuD")

public actual fun externLasxXvsrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarnBH")

public actual fun externLasxXvsrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarnHW")

public actual fun externLasxXvsrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrarnWD")

public actual fun externLasxXvssrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnBH")

public actual fun externLasxXvssrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnHW")

public actual fun externLasxXvssrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnWD")

public actual fun externLasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnBuH")

public actual fun externLasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnHuW")

public actual fun externLasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrarnWuD")

public actual fun externLasxXvsrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlnBH")

public actual fun externLasxXvsrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlnHW")

public actual fun externLasxXvsrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlnWD")

public actual fun externLasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnBuH")

public actual fun externLasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnHuW")

public actual fun externLasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnWuD")

public actual fun externLasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrnBH")

public actual fun externLasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrnHW")

public actual fun externLasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsrlrnWD")

public actual fun externLasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnBuH")

public actual fun externLasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnHuW")

public actual fun externLasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnWuD")

public actual fun lasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpiB")

public actual fun lasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvfrstpiH")

public actual fun externLasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrstpB")

public actual fun externLasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrstpH")

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

public actual fun externLasxXvmskltzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmskltzB")

public actual fun externLasxXvmskltzH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmskltzH")

public actual fun externLasxXvmskltzW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmskltzW")

public actual fun externLasxXvmskltzD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmskltzD")

public actual fun externLasxXvsigncovB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsigncovB")

public actual fun externLasxXvsigncovH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsigncovH")

public actual fun externLasxXvsigncovW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsigncovW")

public actual fun externLasxXvsigncovD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsigncovD")

public actual fun externLasxXvftintrneWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrneWS")

public actual fun externLasxXvftintrneLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrneLD")

public actual fun externLasxXvftintrpWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrpWS")

public actual fun externLasxXvftintrpLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrpLD")

public actual fun externLasxXvftintrmWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrmWS")

public actual fun externLasxXvftintrmLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrmLD")

public actual fun externLasxXvftintWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintWD")

public actual fun externLasxXvffintSL(a: V4i64, b: V4i64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintSL")

public actual fun externLasxXvftintrzWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzWD")

public actual fun externLasxXvftintrpWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrpWD")

public actual fun externLasxXvftintrmWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrmWD")

public actual fun externLasxXvftintrneWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrneWD")

public actual fun externLasxXvftinthLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftinthLS")

public actual fun externLasxXvftintlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintlLS")

public actual fun externLasxXvffinthDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffinthDW")

public actual fun externLasxXvffintlDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvffintlDW")

public actual fun externLasxXvftintrzhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzhLS")

public actual fun externLasxXvftintrzlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrzlLS")

public actual fun externLasxXvftintrphLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrphLS")

public actual fun externLasxXvftintrplLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrplLS")

public actual fun externLasxXvftintrmhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrmhLS")

public actual fun externLasxXvftintrmlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrmlLS")

public actual fun externLasxXvftintrnehLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrnehLS")

public actual fun externLasxXvftintrnelLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvftintrnelLS")

public actual fun externLasxXvfrintrneS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrintrneS")

public actual fun externLasxXvfrintrneD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfrintrneD")

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

public actual fun externLasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnBH")

public actual fun externLasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnHW")

public actual fun externLasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlrnWD")

public actual fun externLasxXvssrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnBH")

public actual fun externLasxXvssrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnHW")

public actual fun externLasxXvssrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvssrlnWD")

public actual fun externLasxXvextlQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvextlQuDu")

public actual fun externLasxVext2xvHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvHB")

public actual fun externLasxVext2xvWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvWH")

public actual fun externLasxVext2xvDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDW")

public actual fun externLasxVext2xvWB(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvWB")

public actual fun externLasxVext2xvDH(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDH")

public actual fun externLasxVext2xvDB(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDB")

public actual fun externLasxVext2xvHuBu(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvHuBu")

public actual fun externLasxVext2xvWuHu(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvWuHu")

public actual fun externLasxVext2xvDuWu(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDuWu")

public actual fun externLasxVext2xvWuBu(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvWuBu")

public actual fun externLasxVext2xvDuHu(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDuHu")

public actual fun externLasxVext2xvDuBu(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxVext2xvDuBu")

public actual fun lasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermiQ")

public actual fun lasxXvpermiD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpermiD")

public actual fun externLasxXvpermW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvpermW")

public actual fun lasxXvldreplB(a: ConstPtr, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplB")

public actual fun lasxXvldreplH(a: ConstPtr, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplH")

public actual fun lasxXvldreplW(a: ConstPtr, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplW")

public actual fun lasxXvldreplD(a: ConstPtr, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvldreplD")

public actual fun externLasxXvaddwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevQD")

public actual fun externLasxXvaddwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevDW")

public actual fun externLasxXvaddwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevWH")

public actual fun externLasxXvaddwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevHB")

public actual fun externLasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevQDu")

public actual fun externLasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevDWu")

public actual fun externLasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevWHu")

public actual fun externLasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevHBu")

public actual fun externLasxXvsubwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevQD")

public actual fun externLasxXvsubwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevDW")

public actual fun externLasxXvsubwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevWH")

public actual fun externLasxXvsubwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevHB")

public actual fun externLasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevQDu")

public actual fun externLasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevDWu")

public actual fun externLasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevWHu")

public actual fun externLasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwevHBu")

public actual fun externLasxXvmulwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevQD")

public actual fun externLasxXvmulwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevDW")

public actual fun externLasxXvmulwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevWH")

public actual fun externLasxXvmulwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevHB")

public actual fun externLasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevQDu")

public actual fun externLasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevDWu")

public actual fun externLasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevWHu")

public actual fun externLasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevHBu")

public actual fun externLasxXvaddwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodQD")

public actual fun externLasxXvaddwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodDW")

public actual fun externLasxXvaddwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodWH")

public actual fun externLasxXvaddwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodHB")

public actual fun externLasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodQDu")

public actual fun externLasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodDWu")

public actual fun externLasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodWHu")

public actual fun externLasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodHBu")

public actual fun externLasxXvsubwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodQD")

public actual fun externLasxXvsubwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodDW")

public actual fun externLasxXvsubwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodWH")

public actual fun externLasxXvsubwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodHB")

public actual fun externLasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodQDu")

public actual fun externLasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodDWu")

public actual fun externLasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodWHu")

public actual fun externLasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubwodHBu")

public actual fun externLasxXvmulwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodQD")

public actual fun externLasxXvmulwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodDW")

public actual fun externLasxXvmulwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodWH")

public actual fun externLasxXvmulwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodHB")

public actual fun externLasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodQDu")

public actual fun externLasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodDWu")

public actual fun externLasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodWHu")

public actual fun externLasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodHBu")

public actual fun externLasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevDWuW")

public actual fun externLasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevWHuH")

public actual fun externLasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevHBuB")

public actual fun externLasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevDWuW")

public actual fun externLasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevWHuH")

public actual fun externLasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevHBuB")

public actual fun externLasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodDWuW")

public actual fun externLasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodWHuH")

public actual fun externLasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodHBuB")

public actual fun externLasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodDWuW")

public actual fun externLasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodWHuH")

public actual fun externLasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodHBuB")

public actual fun externLasxXvhaddwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwQD")

public actual fun externLasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhaddwQuDu")

public actual fun externLasxXvhsubwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwQD")

public actual fun externLasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvhsubwQuDu")

public actual fun externLasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevQD")

public actual fun externLasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevDW")

public actual fun externLasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevWH")

public actual fun externLasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevHB")

public actual fun externLasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevQDu")

public actual fun externLasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevDWu")

public actual fun externLasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevWHu")

public actual fun externLasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevHBu")

public actual fun externLasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodQD")

public actual fun externLasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodDW")

public actual fun externLasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodWH")

public actual fun externLasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodHB")

public actual fun externLasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodQDu")

public actual fun externLasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodDWu")

public actual fun externLasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodWHu")

public actual fun externLasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodHBu")

public actual fun externLasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevQDuD")

public actual fun externLasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevDWuW")

public actual fun externLasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevWHuH")

public actual fun externLasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwevHBuB")

public actual fun externLasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodQDuD")

public actual fun externLasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodDWuW")

public actual fun externLasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodWHuH")

public actual fun externLasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmaddwodHBuB")

public actual fun externLasxXvaddQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddQ")

public actual fun externLasxXvsubQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvsubQ")

public actual fun externLasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwevQDuD")

public actual fun externLasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvaddwodQDuD")

public actual fun externLasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwevQDuD")

public actual fun externLasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmulwodQDuD")

public actual fun externLasxXvmskgezB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmskgezB")

public actual fun externLasxXvmsknzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvmsknzB")

public actual fun externLasxXvexthHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthHB")

public actual fun externLasxXvexthWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthWH")

public actual fun externLasxXvexthDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthDW")

public actual fun externLasxXvexthQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthQD")

public actual fun externLasxXvexthHuBu(a: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthHuBu")

public actual fun externLasxXvexthWuHu(a: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthWuHu")

public actual fun externLasxXvexthDuWu(a: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthDuWu")

public actual fun externLasxXvexthQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvexthQuDu")

public actual fun externLasxXvextlQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvextlQD")

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

public actual fun externLasxXbnzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbnzB")

public actual fun externLasxXbnzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbnzD")

public actual fun externLasxXbnzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbnzH")

public actual fun externLasxXbnzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbnzV")

public actual fun externLasxXbnzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbnzW")

public actual fun externLasxXbzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbzB")

public actual fun externLasxXbzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbzD")

public actual fun externLasxXbzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbzH")

public actual fun externLasxXbzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbzV")

public actual fun externLasxXbzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXbzW")

public actual fun externLasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCafD")

public actual fun externLasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCafS")

public actual fun externLasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCeqD")

public actual fun externLasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCeqS")

public actual fun externLasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCleD")

public actual fun externLasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCleS")

public actual fun externLasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCltD")

public actual fun externLasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCltS")

public actual fun externLasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCneD")

public actual fun externLasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCneS")

public actual fun externLasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCorD")

public actual fun externLasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCorS")

public actual fun externLasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCueqD")

public actual fun externLasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCueqS")

public actual fun externLasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCuleD")

public actual fun externLasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCuleS")

public actual fun externLasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCultD")

public actual fun externLasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCultS")

public actual fun externLasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCunD")

public actual fun externLasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCuneD")

public actual fun externLasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCuneS")

public actual fun externLasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpCunS")

public actual fun externLasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSafD")

public actual fun externLasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSafS")

public actual fun externLasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSeqD")

public actual fun externLasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSeqS")

public actual fun externLasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSleD")

public actual fun externLasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSleS")

public actual fun externLasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSltD")

public actual fun externLasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSltS")

public actual fun externLasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSneD")

public actual fun externLasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSneS")

public actual fun externLasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSorD")

public actual fun externLasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSorS")

public actual fun externLasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSueqD")

public actual fun externLasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSueqS")

public actual fun externLasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSuleD")

public actual fun externLasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSuleS")

public actual fun externLasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSultD")

public actual fun externLasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSultS")

public actual fun externLasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSunD")

public actual fun externLasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSuneD")

public actual fun externLasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSuneS")

public actual fun externLasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxXvfcmpSunS")

public actual fun lasxXvpickveDF(a: V4f64, b: UInt): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveDF")

public actual fun lasxXvpickveWF(a: V8f32, b: UInt): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedLasxXvpickveWF")

public actual fun externLasxCast128S(a: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxCast128S")

public actual fun externLasxCast128D(a: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxCast128D")

public actual fun externLasxCast128(a: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxCast128")

public actual fun externLasxConcat128S(a: V4f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxConcat128S")

public actual fun externLasxConcat128D(a: V2f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxConcat128D")

public actual fun externLasxConcat128(a: V2i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxConcat128")

public actual fun externLasxExtract128LoS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128LoS")

public actual fun externLasxExtract128HiS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128HiS")

public actual fun externLasxExtract128LoD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128LoD")

public actual fun externLasxExtract128HiD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128HiD")

public actual fun externLasxExtract128Lo(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128Lo")

public actual fun externLasxExtract128Hi(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxExtract128Hi")

public actual fun externLasxInsert128LoS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128LoS")

public actual fun externLasxInsert128HiS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128HiS")

public actual fun externLasxInsert128LoD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128LoD")

public actual fun externLasxInsert128HiD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128HiD")

public actual fun externLasxInsert128Lo(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128Lo")

public actual fun externLasxInsert128Hi(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchLoongarch64LasxGeneratedExternLasxInsert128Hi")
