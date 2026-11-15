@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lasx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.loongarch64.lasx.V16i16
import io.github.kotlinmania.rustlang.loongarch64.lasx.V16u16
import io.github.kotlinmania.rustlang.loongarch64.lasx.V32i8
import io.github.kotlinmania.rustlang.loongarch64.lasx.V32u8
import io.github.kotlinmania.rustlang.loongarch64.lasx.V4f64
import io.github.kotlinmania.rustlang.loongarch64.lasx.V4i64
import io.github.kotlinmania.rustlang.loongarch64.lasx.V4u64
import io.github.kotlinmania.rustlang.loongarch64.lasx.V8f32
import io.github.kotlinmania.rustlang.loongarch64.lasx.V8i32
import io.github.kotlinmania.rustlang.loongarch64.lasx.V8u32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4f32

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun lasxXvsrarB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarB")

public actual fun lasxXvsrarH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarH")

public actual fun lasxXvsrarW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarW")

public actual fun lasxXvsrarD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarD")

public actual fun lasxXvsrariB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrariB")

public actual fun lasxXvsrariH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrariH")

public actual fun lasxXvsrariW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrariW")

public actual fun lasxXvsrariD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrariD")

public actual fun lasxXvsrlrB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrB")

public actual fun lasxXvsrlrH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrH")

public actual fun lasxXvsrlrW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrW")

public actual fun lasxXvsrlrD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrD")

public actual fun lasxXvsrlriB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlriB")

public actual fun lasxXvsrlriH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlriH")

public actual fun lasxXvsrlriW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlriW")

public actual fun lasxXvsrlriD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlriD")

public actual fun lasxXvbitclriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitclriB")

public actual fun lasxXvbitclriH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitclriH")

public actual fun lasxXvbitclriW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitclriW")

public actual fun lasxXvbitclriD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitclriD")

public actual fun lasxXvbitsetiB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitsetiB")

public actual fun lasxXvbitsetiH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitsetiH")

public actual fun lasxXvbitsetiW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitsetiW")

public actual fun lasxXvbitsetiD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitsetiD")

public actual fun lasxXvbitreviB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitreviB")

public actual fun lasxXvbitreviH(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitreviH")

public actual fun lasxXvbitreviW(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitreviW")

public actual fun lasxXvbitreviD(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitreviD")

public actual fun lasxXvsubiBu(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubiBu")

public actual fun lasxXvsubiHu(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubiHu")

public actual fun lasxXvsubiWu(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubiWu")

public actual fun lasxXvsubiDu(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubiDu")

public actual fun lasxXvsatB(a: V32i8, b: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatB")

public actual fun lasxXvsatH(a: V16i16, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatH")

public actual fun lasxXvsatW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatW")

public actual fun lasxXvsatD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatD")

public actual fun lasxXvsatBu(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatBu")

public actual fun lasxXvsatHu(a: V16u16, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatHu")

public actual fun lasxXvsatWu(a: V8u32, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatWu")

public actual fun lasxXvsatDu(a: V4u64, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsatDu")

public actual fun lasxXvdivB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivB")

public actual fun lasxXvdivH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivH")

public actual fun lasxXvdivW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivW")

public actual fun lasxXvdivD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivD")

public actual fun lasxXvdivBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivBu")

public actual fun lasxXvdivHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivHu")

public actual fun lasxXvdivWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivWu")

public actual fun lasxXvdivDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvdivDu")

public actual fun lasxXvhaddwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwHB")

public actual fun lasxXvhaddwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwWH")

public actual fun lasxXvhaddwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwDW")

public actual fun lasxXvhaddwHuBu(a: V32u8, b: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwHuBu")

public actual fun lasxXvhaddwWuHu(a: V16u16, b: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwWuHu")

public actual fun lasxXvhaddwDuWu(a: V8u32, b: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwDuWu")

public actual fun lasxXvhsubwHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwHB")

public actual fun lasxXvhsubwWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwWH")

public actual fun lasxXvhsubwDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwDW")

public actual fun lasxXvhsubwHuBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwHuBu")

public actual fun lasxXvhsubwWuHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwWuHu")

public actual fun lasxXvhsubwDuWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwDuWu")

public actual fun lasxXvmodB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodB")

public actual fun lasxXvmodH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodH")

public actual fun lasxXvmodW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodW")

public actual fun lasxXvmodD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodD")

public actual fun lasxXvmodBu(a: V32u8, b: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodBu")

public actual fun lasxXvmodHu(a: V16u16, b: V16u16): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodHu")

public actual fun lasxXvmodWu(a: V8u32, b: V8u32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodWu")

public actual fun lasxXvmodDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmodDu")

public actual fun lasxXvshufB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvshufB")

public actual fun lasxXvshufH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvshufH")

public actual fun lasxXvshufW(a: V8i32, b: V8i32, c: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvshufW")

public actual fun lasxXvshufD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvshufD")

public actual fun lasxXvandiB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvandiB")

public actual fun lasxXvoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvoriB")

public actual fun lasxXvnoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvnoriB")

public actual fun lasxXvxoriB(a: V32u8, b: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvxoriB")

public actual fun lasxXvbitselV(a: V32u8, b: V32u8, c: V32u8): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitselV")

public actual fun lasxXvbitseliB(a: V32u8, b: V32u8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvbitseliB")

public actual fun lasxXvcloB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvcloB")

public actual fun lasxXvcloH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvcloH")

public actual fun lasxXvcloW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvcloW")

public actual fun lasxXvcloD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvcloD")

public actual fun lasxXvfcvtHS(a: V8f32, b: V8f32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvtHS")

public actual fun lasxXvfcvtSD(a: V4f64, b: V4f64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvtSD")

public actual fun lasxXvfminS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfminS")

public actual fun lasxXvfminD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfminD")

public actual fun lasxXvfminaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfminaS")

public actual fun lasxXvfminaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfminaD")

public actual fun lasxXvfmaxS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfmaxS")

public actual fun lasxXvfmaxD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfmaxD")

public actual fun lasxXvfmaxaS(a: V8f32, b: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfmaxaS")

public actual fun lasxXvfmaxaD(a: V4f64, b: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfmaxaD")

public actual fun lasxXvfclassS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfclassS")

public actual fun lasxXvfclassD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfclassD")

public actual fun lasxXvfrecipeS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrecipeS")

public actual fun lasxXvfrecipeD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrecipeD")

public actual fun lasxXvfrsqrteS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrsqrteS")

public actual fun lasxXvfrsqrteD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrsqrteD")

public actual fun lasxXvfrintS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrintS")

public actual fun lasxXvfrintD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrintD")

public actual fun lasxXvflogbS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvflogbS")

public actual fun lasxXvflogbD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvflogbD")

public actual fun lasxXvfcvthSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvthSH")

public actual fun lasxXvfcvthDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvthDS")

public actual fun lasxXvfcvtlSH(a: V16i16): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvtlSH")

public actual fun lasxXvfcvtlDS(a: V8f32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcvtlDS")

public actual fun lasxXvftintWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintWS")

public actual fun lasxXvftintLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintLD")

public actual fun lasxXvftintWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintWuS")

public actual fun lasxXvftintLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintLuD")

public actual fun lasxXvftintrzWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzWS")

public actual fun lasxXvftintrzLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzLD")

public actual fun lasxXvftintrzWuS(a: V8f32): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzWuS")

public actual fun lasxXvftintrzLuD(a: V4f64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzLuD")

public actual fun lasxXvffintSW(a: V8i32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintSW")

public actual fun lasxXvffintDL(a: V4i64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintDL")

public actual fun lasxXvffintSWu(a: V8u32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintSWu")

public actual fun lasxXvffintDLu(a: V4u64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintDLu")

public actual fun lasxXvreplveB(a: V32i8, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvreplveB")

public actual fun lasxXvreplveH(a: V16i16, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvreplveH")

public actual fun lasxXvreplveW(a: V8i32, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvreplveW")

public actual fun lasxXvreplveD(a: V4i64, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvreplveD")

public actual fun lasxXvpermiW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpermiW")

public actual fun lasxXvsllwilHB(a: V32i8, b: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilHB")

public actual fun lasxXvsllwilWH(a: V16i16, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilWH")

public actual fun lasxXvsllwilDW(a: V8i32, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilDW")

public actual fun lasxXvsllwilHuBu(a: V32u8, b: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilHuBu")

public actual fun lasxXvsllwilWuHu(a: V16u16, b: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilWuHu")

public actual fun lasxXvsllwilDuWu(a: V8u32, b: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsllwilDuWu")

public actual fun lasxXvsranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsranBH")

public actual fun lasxXvsranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsranHW")

public actual fun lasxXvsranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsranWD")

public actual fun lasxXvssranBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranBH")

public actual fun lasxXvssranHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranHW")

public actual fun lasxXvssranWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranWD")

public actual fun lasxXvssranBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranBuH")

public actual fun lasxXvssranHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranHuW")

public actual fun lasxXvssranWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssranWuD")

public actual fun lasxXvsrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarnBH")

public actual fun lasxXvsrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarnHW")

public actual fun lasxXvsrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarnWD")

public actual fun lasxXvssrarnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnBH")

public actual fun lasxXvssrarnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnHW")

public actual fun lasxXvssrarnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnWD")

public actual fun lasxXvssrarnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnBuH")

public actual fun lasxXvssrarnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnHuW")

public actual fun lasxXvssrarnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarnWuD")

public actual fun lasxXvsrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlnBH")

public actual fun lasxXvsrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlnHW")

public actual fun lasxXvsrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlnWD")

public actual fun lasxXvssrlnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnBuH")

public actual fun lasxXvssrlnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnHuW")

public actual fun lasxXvssrlnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnWuD")

public actual fun lasxXvsrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrnBH")

public actual fun lasxXvsrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrnHW")

public actual fun lasxXvsrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrnWD")

public actual fun lasxXvssrlrnBuH(a: V16u16, b: V16u16): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnBuH")

public actual fun lasxXvssrlrnHuW(a: V8u32, b: V8u32): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnHuW")

public actual fun lasxXvssrlrnWuD(a: V4u64, b: V4u64): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnWuD")

public actual fun lasxXvfrstpiB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrstpiB")

public actual fun lasxXvfrstpiH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrstpiH")

public actual fun lasxXvfrstpB(a: V32i8, b: V32i8, c: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrstpB")

public actual fun lasxXvfrstpH(a: V16i16, b: V16i16, c: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrstpH")

public actual fun lasxXvshuf4iD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvshuf4iD")

public actual fun lasxXvextrinsB(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextrinsB")

public actual fun lasxXvextrinsH(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextrinsH")

public actual fun lasxXvextrinsW(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextrinsW")

public actual fun lasxXvextrinsD(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextrinsD")

public actual fun lasxXvmskltzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmskltzB")

public actual fun lasxXvmskltzH(a: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmskltzH")

public actual fun lasxXvmskltzW(a: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmskltzW")

public actual fun lasxXvmskltzD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmskltzD")

public actual fun lasxXvsigncovB(a: V32i8, b: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsigncovB")

public actual fun lasxXvsigncovH(a: V16i16, b: V16i16): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsigncovH")

public actual fun lasxXvsigncovW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsigncovW")

public actual fun lasxXvsigncovD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsigncovD")

public actual fun lasxXvftintrneWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrneWS")

public actual fun lasxXvftintrneLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrneLD")

public actual fun lasxXvftintrpWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrpWS")

public actual fun lasxXvftintrpLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrpLD")

public actual fun lasxXvftintrmWS(a: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrmWS")

public actual fun lasxXvftintrmLD(a: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrmLD")

public actual fun lasxXvftintWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintWD")

public actual fun lasxXvffintSL(a: V4i64, b: V4i64): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintSL")

public actual fun lasxXvftintrzWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzWD")

public actual fun lasxXvftintrpWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrpWD")

public actual fun lasxXvftintrmWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrmWD")

public actual fun lasxXvftintrneWD(a: V4f64, b: V4f64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrneWD")

public actual fun lasxXvftinthLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftinthLS")

public actual fun lasxXvftintlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintlLS")

public actual fun lasxXvffinthDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffinthDW")

public actual fun lasxXvffintlDW(a: V8i32): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvffintlDW")

public actual fun lasxXvftintrzhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzhLS")

public actual fun lasxXvftintrzlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrzlLS")

public actual fun lasxXvftintrphLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrphLS")

public actual fun lasxXvftintrplLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrplLS")

public actual fun lasxXvftintrmhLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrmhLS")

public actual fun lasxXvftintrmlLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrmlLS")

public actual fun lasxXvftintrnehLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrnehLS")

public actual fun lasxXvftintrnelLS(a: V8f32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvftintrnelLS")

public actual fun lasxXvfrintrneS(a: V8f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrintrneS")

public actual fun lasxXvfrintrneD(a: V4f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfrintrneD")

public actual fun lasxXvstelmB(a: V32i8, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvstelmB")
}

public actual fun lasxXvstelmH(a: V16i16, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvstelmH")
}

public actual fun lasxXvstelmW(a: V8i32, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvstelmW")
}

public actual fun lasxXvstelmD(a: V4i64, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvstelmD")
}

public actual fun lasxXvinsve0W(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvinsve0W")

public actual fun lasxXvinsve0D(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvinsve0D")

public actual fun lasxXvpickveW(a: V8i32, b: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpickveW")

public actual fun lasxXvpickveD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpickveD")

public actual fun lasxXvssrlrnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnBH")

public actual fun lasxXvssrlrnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnHW")

public actual fun lasxXvssrlrnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrnWD")

public actual fun lasxXvssrlnBH(a: V16i16, b: V16i16): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnBH")

public actual fun lasxXvssrlnHW(a: V8i32, b: V8i32): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnHW")

public actual fun lasxXvssrlnWD(a: V4i64, b: V4i64): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlnWD")

public actual fun lasxXvextlQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextlQuDu")

public actual fun lasxVext2xvHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvHB")

public actual fun lasxVext2xvWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvWH")

public actual fun lasxVext2xvDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDW")

public actual fun lasxVext2xvWB(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvWB")

public actual fun lasxVext2xvDH(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDH")

public actual fun lasxVext2xvDB(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDB")

public actual fun lasxVext2xvHuBu(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvHuBu")

public actual fun lasxVext2xvWuHu(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvWuHu")

public actual fun lasxVext2xvDuWu(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDuWu")

public actual fun lasxVext2xvWuBu(a: V32i8): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvWuBu")

public actual fun lasxVext2xvDuHu(a: V16i16): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDuHu")

public actual fun lasxVext2xvDuBu(a: V32i8): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxVext2xvDuBu")

public actual fun lasxXvpermiQ(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpermiQ")

public actual fun lasxXvpermiD(a: V4i64, b: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpermiD")

public actual fun lasxXvpermW(a: V8i32, b: V8i32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpermW")

public actual fun lasxXvldreplB(a: ConstPtr, b: Int): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvldreplB")

public actual fun lasxXvldreplH(a: ConstPtr, b: Int): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvldreplH")

public actual fun lasxXvldreplW(a: ConstPtr, b: Int): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvldreplW")

public actual fun lasxXvldreplD(a: ConstPtr, b: Int): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvldreplD")

public actual fun lasxXvaddwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevQD")

public actual fun lasxXvaddwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevDW")

public actual fun lasxXvaddwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevWH")

public actual fun lasxXvaddwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevHB")

public actual fun lasxXvaddwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevQDu")

public actual fun lasxXvaddwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevDWu")

public actual fun lasxXvaddwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevWHu")

public actual fun lasxXvaddwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevHBu")

public actual fun lasxXvsubwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevQD")

public actual fun lasxXvsubwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevDW")

public actual fun lasxXvsubwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevWH")

public actual fun lasxXvsubwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevHB")

public actual fun lasxXvsubwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevQDu")

public actual fun lasxXvsubwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevDWu")

public actual fun lasxXvsubwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevWHu")

public actual fun lasxXvsubwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwevHBu")

public actual fun lasxXvmulwevQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevQD")

public actual fun lasxXvmulwevDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevDW")

public actual fun lasxXvmulwevWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevWH")

public actual fun lasxXvmulwevHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevHB")

public actual fun lasxXvmulwevQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevQDu")

public actual fun lasxXvmulwevDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevDWu")

public actual fun lasxXvmulwevWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevWHu")

public actual fun lasxXvmulwevHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevHBu")

public actual fun lasxXvaddwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodQD")

public actual fun lasxXvaddwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodDW")

public actual fun lasxXvaddwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodWH")

public actual fun lasxXvaddwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodHB")

public actual fun lasxXvaddwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodQDu")

public actual fun lasxXvaddwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodDWu")

public actual fun lasxXvaddwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodWHu")

public actual fun lasxXvaddwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodHBu")

public actual fun lasxXvsubwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodQD")

public actual fun lasxXvsubwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodDW")

public actual fun lasxXvsubwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodWH")

public actual fun lasxXvsubwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodHB")

public actual fun lasxXvsubwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodQDu")

public actual fun lasxXvsubwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodDWu")

public actual fun lasxXvsubwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodWHu")

public actual fun lasxXvsubwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubwodHBu")

public actual fun lasxXvmulwodQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodQD")

public actual fun lasxXvmulwodDW(a: V8i32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodDW")

public actual fun lasxXvmulwodWH(a: V16i16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodWH")

public actual fun lasxXvmulwodHB(a: V32i8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodHB")

public actual fun lasxXvmulwodQDu(a: V4u64, b: V4u64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodQDu")

public actual fun lasxXvmulwodDWu(a: V8u32, b: V8u32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodDWu")

public actual fun lasxXvmulwodWHu(a: V16u16, b: V16u16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodWHu")

public actual fun lasxXvmulwodHBu(a: V32u8, b: V32u8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodHBu")

public actual fun lasxXvaddwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevDWuW")

public actual fun lasxXvaddwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevWHuH")

public actual fun lasxXvaddwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevHBuB")

public actual fun lasxXvmulwevDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevDWuW")

public actual fun lasxXvmulwevWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevWHuH")

public actual fun lasxXvmulwevHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevHBuB")

public actual fun lasxXvaddwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodDWuW")

public actual fun lasxXvaddwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodWHuH")

public actual fun lasxXvaddwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodHBuB")

public actual fun lasxXvmulwodDWuW(a: V8u32, b: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodDWuW")

public actual fun lasxXvmulwodWHuH(a: V16u16, b: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodWHuH")

public actual fun lasxXvmulwodHBuB(a: V32u8, b: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodHBuB")

public actual fun lasxXvhaddwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwQD")

public actual fun lasxXvhaddwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhaddwQuDu")

public actual fun lasxXvhsubwQD(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwQD")

public actual fun lasxXvhsubwQuDu(a: V4u64, b: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvhsubwQuDu")

public actual fun lasxXvmaddwevQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevQD")

public actual fun lasxXvmaddwevDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevDW")

public actual fun lasxXvmaddwevWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevWH")

public actual fun lasxXvmaddwevHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevHB")

public actual fun lasxXvmaddwevQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevQDu")

public actual fun lasxXvmaddwevDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevDWu")

public actual fun lasxXvmaddwevWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevWHu")

public actual fun lasxXvmaddwevHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevHBu")

public actual fun lasxXvmaddwodQD(a: V4i64, b: V4i64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodQD")

public actual fun lasxXvmaddwodDW(a: V4i64, b: V8i32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodDW")

public actual fun lasxXvmaddwodWH(a: V8i32, b: V16i16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodWH")

public actual fun lasxXvmaddwodHB(a: V16i16, b: V32i8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodHB")

public actual fun lasxXvmaddwodQDu(a: V4u64, b: V4u64, c: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodQDu")

public actual fun lasxXvmaddwodDWu(a: V4u64, b: V8u32, c: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodDWu")

public actual fun lasxXvmaddwodWHu(a: V8u32, b: V16u16, c: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodWHu")

public actual fun lasxXvmaddwodHBu(a: V16u16, b: V32u8, c: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodHBu")

public actual fun lasxXvmaddwevQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevQDuD")

public actual fun lasxXvmaddwevDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevDWuW")

public actual fun lasxXvmaddwevWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevWHuH")

public actual fun lasxXvmaddwevHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwevHBuB")

public actual fun lasxXvmaddwodQDuD(a: V4i64, b: V4u64, c: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodQDuD")

public actual fun lasxXvmaddwodDWuW(a: V4i64, b: V8u32, c: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodDWuW")

public actual fun lasxXvmaddwodWHuH(a: V8i32, b: V16u16, c: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodWHuH")

public actual fun lasxXvmaddwodHBuB(a: V16i16, b: V32u8, c: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmaddwodHBuB")

public actual fun lasxXvaddQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddQ")

public actual fun lasxXvsubQ(a: V4i64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsubQ")

public actual fun lasxXvaddwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwevQDuD")

public actual fun lasxXvaddwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvaddwodQDuD")

public actual fun lasxXvmulwevQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwevQDuD")

public actual fun lasxXvmulwodQDuD(a: V4u64, b: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmulwodQDuD")

public actual fun lasxXvmskgezB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmskgezB")

public actual fun lasxXvmsknzB(a: V32i8): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvmsknzB")

public actual fun lasxXvexthHB(a: V32i8): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthHB")

public actual fun lasxXvexthWH(a: V16i16): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthWH")

public actual fun lasxXvexthDW(a: V8i32): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthDW")

public actual fun lasxXvexthQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthQD")

public actual fun lasxXvexthHuBu(a: V32u8): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthHuBu")

public actual fun lasxXvexthWuHu(a: V16u16): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthWuHu")

public actual fun lasxXvexthDuWu(a: V8u32): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthDuWu")

public actual fun lasxXvexthQuDu(a: V4u64): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvexthQuDu")

public actual fun lasxXvextlQD(a: V4i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvextlQD")

public actual fun lasxXvsrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlniBH")

public actual fun lasxXvsrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlniHW")

public actual fun lasxXvsrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlniWD")

public actual fun lasxXvsrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlniDQ")

public actual fun lasxXvsrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrniBH")

public actual fun lasxXvsrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrniHW")

public actual fun lasxXvsrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrniWD")

public actual fun lasxXvsrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrlrniDQ")

public actual fun lasxXvssrlniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniBH")

public actual fun lasxXvssrlniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniHW")

public actual fun lasxXvssrlniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniWD")

public actual fun lasxXvssrlniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniDQ")

public actual fun lasxXvssrlniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniBuH")

public actual fun lasxXvssrlniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniHuW")

public actual fun lasxXvssrlniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniWuD")

public actual fun lasxXvssrlniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlniDuQ")

public actual fun lasxXvssrlrniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniBH")

public actual fun lasxXvssrlrniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniHW")

public actual fun lasxXvssrlrniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniWD")

public actual fun lasxXvssrlrniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniDQ")

public actual fun lasxXvssrlrniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniBuH")

public actual fun lasxXvssrlrniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniHuW")

public actual fun lasxXvssrlrniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniWuD")

public actual fun lasxXvssrlrniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrlrniDuQ")

public actual fun lasxXvsraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsraniBH")

public actual fun lasxXvsraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsraniHW")

public actual fun lasxXvsraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsraniWD")

public actual fun lasxXvsraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsraniDQ")

public actual fun lasxXvsrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarniBH")

public actual fun lasxXvsrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarniHW")

public actual fun lasxXvsrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarniWD")

public actual fun lasxXvsrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvsrarniDQ")

public actual fun lasxXvssraniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniBH")

public actual fun lasxXvssraniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniHW")

public actual fun lasxXvssraniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniWD")

public actual fun lasxXvssraniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniDQ")

public actual fun lasxXvssraniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniBuH")

public actual fun lasxXvssraniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniHuW")

public actual fun lasxXvssraniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniWuD")

public actual fun lasxXvssraniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssraniDuQ")

public actual fun lasxXvssrarniBH(a: V32i8, b: V32i8, c: UInt): V32i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniBH")

public actual fun lasxXvssrarniHW(a: V16i16, b: V16i16, c: UInt): V16i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniHW")

public actual fun lasxXvssrarniWD(a: V8i32, b: V8i32, c: UInt): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniWD")

public actual fun lasxXvssrarniDQ(a: V4i64, b: V4i64, c: UInt): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniDQ")

public actual fun lasxXvssrarniBuH(a: V32u8, b: V32i8, c: UInt): V32u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniBuH")

public actual fun lasxXvssrarniHuW(a: V16u16, b: V16i16, c: UInt): V16u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniHuW")

public actual fun lasxXvssrarniWuD(a: V8u32, b: V8i32, c: UInt): V8u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniWuD")

public actual fun lasxXvssrarniDuQ(a: V4u64, b: V4i64, c: UInt): V4u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvssrarniDuQ")

public actual fun lasxXbnzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbnzB")

public actual fun lasxXbnzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbnzD")

public actual fun lasxXbnzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbnzH")

public actual fun lasxXbnzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbnzV")

public actual fun lasxXbnzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbnzW")

public actual fun lasxXbzB(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbzB")

public actual fun lasxXbzD(a: V4u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbzD")

public actual fun lasxXbzH(a: V16u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbzH")

public actual fun lasxXbzV(a: V32u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbzV")

public actual fun lasxXbzW(a: V8u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXbzW")

public actual fun lasxXvfcmpCafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCafD")

public actual fun lasxXvfcmpCafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCafS")

public actual fun lasxXvfcmpCeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCeqD")

public actual fun lasxXvfcmpCeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCeqS")

public actual fun lasxXvfcmpCleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCleD")

public actual fun lasxXvfcmpCleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCleS")

public actual fun lasxXvfcmpCltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCltD")

public actual fun lasxXvfcmpCltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCltS")

public actual fun lasxXvfcmpCneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCneD")

public actual fun lasxXvfcmpCneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCneS")

public actual fun lasxXvfcmpCorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCorD")

public actual fun lasxXvfcmpCorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCorS")

public actual fun lasxXvfcmpCueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCueqD")

public actual fun lasxXvfcmpCueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCueqS")

public actual fun lasxXvfcmpCuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCuleD")

public actual fun lasxXvfcmpCuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCuleS")

public actual fun lasxXvfcmpCultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCultD")

public actual fun lasxXvfcmpCultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCultS")

public actual fun lasxXvfcmpCunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCunD")

public actual fun lasxXvfcmpCuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCuneD")

public actual fun lasxXvfcmpCuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCuneS")

public actual fun lasxXvfcmpCunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpCunS")

public actual fun lasxXvfcmpSafD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSafD")

public actual fun lasxXvfcmpSafS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSafS")

public actual fun lasxXvfcmpSeqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSeqD")

public actual fun lasxXvfcmpSeqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSeqS")

public actual fun lasxXvfcmpSleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSleD")

public actual fun lasxXvfcmpSleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSleS")

public actual fun lasxXvfcmpSltD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSltD")

public actual fun lasxXvfcmpSltS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSltS")

public actual fun lasxXvfcmpSneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSneD")

public actual fun lasxXvfcmpSneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSneS")

public actual fun lasxXvfcmpSorD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSorD")

public actual fun lasxXvfcmpSorS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSorS")

public actual fun lasxXvfcmpSueqD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSueqD")

public actual fun lasxXvfcmpSueqS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSueqS")

public actual fun lasxXvfcmpSuleD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSuleD")

public actual fun lasxXvfcmpSuleS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSuleS")

public actual fun lasxXvfcmpSultD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSultD")

public actual fun lasxXvfcmpSultS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSultS")

public actual fun lasxXvfcmpSunD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSunD")

public actual fun lasxXvfcmpSuneD(a: V4f64, b: V4f64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSuneD")

public actual fun lasxXvfcmpSuneS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSuneS")

public actual fun lasxXvfcmpSunS(a: V8f32, b: V8f32): V8i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvfcmpSunS")

public actual fun lasxXvpickveDF(a: V4f64, b: UInt): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpickveDF")

public actual fun lasxXvpickveWF(a: V8f32, b: UInt): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxXvpickveWF")

public actual fun lasxCast128S(a: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxCast128S")

public actual fun lasxCast128D(a: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxCast128D")

public actual fun lasxCast128(a: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxCast128")

public actual fun lasxConcat128S(a: V4f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxConcat128S")

public actual fun lasxConcat128D(a: V2f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxConcat128D")

public actual fun lasxConcat128(a: V2i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxConcat128")

public actual fun lasxExtract128LoS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128LoS")

public actual fun lasxExtract128HiS(a: V8f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128HiS")

public actual fun lasxExtract128LoD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128LoD")

public actual fun lasxExtract128HiD(a: V4f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128HiD")

public actual fun lasxExtract128Lo(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128Lo")

public actual fun lasxExtract128Hi(a: V4i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxExtract128Hi")

public actual fun lasxInsert128LoS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128LoS")

public actual fun lasxInsert128HiS(a: V8f32, b: V4f32): V8f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128HiS")

public actual fun lasxInsert128LoD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128LoD")

public actual fun lasxInsert128HiD(a: V4f64, b: V2f64): V4f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128HiD")

public actual fun lasxInsert128Lo(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128Lo")

public actual fun lasxInsert128Hi(a: V4i64, b: V2i64): V4i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LasxGeneratedLasxInsert128Hi")
