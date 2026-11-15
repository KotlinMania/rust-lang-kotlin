@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64.lsx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.loongarch64.lsx.V16i8
import io.github.kotlinmania.rustlang.loongarch64.lsx.V16u8
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2f64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2i64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V2u64
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4f32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4i32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V4u32
import io.github.kotlinmania.rustlang.loongarch64.lsx.V8i16
import io.github.kotlinmania.rustlang.loongarch64.lsx.V8u16

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun lsxVsrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarB")

public actual fun lsxVsrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarH")

public actual fun lsxVsrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarW")

public actual fun lsxVsrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarD")

public actual fun lsxVsrariB(a: V16i8, b: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrariB")

public actual fun lsxVsrariH(a: V8i16, b: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrariH")

public actual fun lsxVsrariW(a: V4i32, b: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrariW")

public actual fun lsxVsrariD(a: V2i64, b: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrariD")

public actual fun lsxVsrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrB")

public actual fun lsxVsrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrH")

public actual fun lsxVsrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrW")

public actual fun lsxVsrlrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrD")

public actual fun lsxVsrlriB(a: V16i8, b: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlriB")

public actual fun lsxVsrlriH(a: V8i16, b: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlriH")

public actual fun lsxVsrlriW(a: V4i32, b: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlriW")

public actual fun lsxVsrlriD(a: V2i64, b: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlriD")

public actual fun lsxVbitclriB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitclriB")

public actual fun lsxVbitclriH(a: V8u16, b: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitclriH")

public actual fun lsxVbitclriW(a: V4u32, b: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitclriW")

public actual fun lsxVbitclriD(a: V2u64, b: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitclriD")

public actual fun lsxVbitsetiB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitsetiB")

public actual fun lsxVbitsetiH(a: V8u16, b: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitsetiH")

public actual fun lsxVbitsetiW(a: V4u32, b: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitsetiW")

public actual fun lsxVbitsetiD(a: V2u64, b: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitsetiD")

public actual fun lsxVbitreviB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitreviB")

public actual fun lsxVbitreviH(a: V8u16, b: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitreviH")

public actual fun lsxVbitreviW(a: V4u32, b: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitreviW")

public actual fun lsxVbitreviD(a: V2u64, b: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitreviD")

public actual fun lsxVsubiBu(a: V16i8, b: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubiBu")

public actual fun lsxVsubiHu(a: V8i16, b: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubiHu")

public actual fun lsxVsubiWu(a: V4i32, b: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubiWu")

public actual fun lsxVsubiDu(a: V2i64, b: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubiDu")

public actual fun lsxVsatB(a: V16i8, b: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatB")

public actual fun lsxVsatH(a: V8i16, b: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatH")

public actual fun lsxVsatW(a: V4i32, b: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatW")

public actual fun lsxVsatD(a: V2i64, b: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatD")

public actual fun lsxVsatBu(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatBu")

public actual fun lsxVsatHu(a: V8u16, b: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatHu")

public actual fun lsxVsatWu(a: V4u32, b: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatWu")

public actual fun lsxVsatDu(a: V2u64, b: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsatDu")

public actual fun lsxVdivB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivB")

public actual fun lsxVdivH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivH")

public actual fun lsxVdivW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivW")

public actual fun lsxVdivD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivD")

public actual fun lsxVdivBu(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivBu")

public actual fun lsxVdivHu(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivHu")

public actual fun lsxVdivWu(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivWu")

public actual fun lsxVdivDu(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVdivDu")

public actual fun lsxVhaddwHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwHB")

public actual fun lsxVhaddwWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwWH")

public actual fun lsxVhaddwDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwDW")

public actual fun lsxVhaddwHuBu(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwHuBu")

public actual fun lsxVhaddwWuHu(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwWuHu")

public actual fun lsxVhaddwDuWu(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwDuWu")

public actual fun lsxVhsubwHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwHB")

public actual fun lsxVhsubwWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwWH")

public actual fun lsxVhsubwDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwDW")

public actual fun lsxVhsubwHuBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwHuBu")

public actual fun lsxVhsubwWuHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwWuHu")

public actual fun lsxVhsubwDuWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwDuWu")

public actual fun lsxVmodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodB")

public actual fun lsxVmodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodH")

public actual fun lsxVmodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodW")

public actual fun lsxVmodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodD")

public actual fun lsxVmodBu(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodBu")

public actual fun lsxVmodHu(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodHu")

public actual fun lsxVmodWu(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodWu")

public actual fun lsxVmodDu(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmodDu")

public actual fun lsxVreplveB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVreplveB")

public actual fun lsxVreplveH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVreplveH")

public actual fun lsxVreplveW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVreplveW")

public actual fun lsxVreplveD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVreplveD")

public actual fun lsxVshufH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVshufH")

public actual fun lsxVshufW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVshufW")

public actual fun lsxVshufD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVshufD")

public actual fun lsxVandiB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVandiB")

public actual fun lsxVoriB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVoriB")

public actual fun lsxVnoriB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVnoriB")

public actual fun lsxVxoriB(a: V16u8, b: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVxoriB")

public actual fun lsxVbitselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitselV")

public actual fun lsxVbitseliB(a: V16u8, b: V16u8, c: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVbitseliB")

public actual fun lsxVcloB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVcloB")

public actual fun lsxVcloH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVcloH")

public actual fun lsxVcloW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVcloW")

public actual fun lsxVcloD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVcloD")

public actual fun lsxVfcvtHS(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvtHS")

public actual fun lsxVfcvtSD(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvtSD")

public actual fun lsxVfminS(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfminS")

public actual fun lsxVfminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfminD")

public actual fun lsxVfminaS(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfminaS")

public actual fun lsxVfminaD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfminaD")

public actual fun lsxVfmaxS(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfmaxS")

public actual fun lsxVfmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfmaxD")

public actual fun lsxVfmaxaS(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfmaxaS")

public actual fun lsxVfmaxaD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfmaxaD")

public actual fun lsxVfclassS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfclassS")

public actual fun lsxVfclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfclassD")

public actual fun lsxVfrecipeS(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrecipeS")

public actual fun lsxVfrecipeD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrecipeD")

public actual fun lsxVfrsqrteS(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrsqrteS")

public actual fun lsxVfrsqrteD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrsqrteD")

public actual fun lsxVfrintS(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrintS")

public actual fun lsxVfrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrintD")

public actual fun lsxVflogbS(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVflogbS")

public actual fun lsxVflogbD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVflogbD")

public actual fun lsxVfcvthSH(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvthSH")

public actual fun lsxVfcvthDS(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvthDS")

public actual fun lsxVfcvtlSH(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvtlSH")

public actual fun lsxVfcvtlDS(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcvtlDS")

public actual fun lsxVftintWS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintWS")

public actual fun lsxVftintLD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintLD")

public actual fun lsxVftintWuS(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintWuS")

public actual fun lsxVftintLuD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintLuD")

public actual fun lsxVftintrzWS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzWS")

public actual fun lsxVftintrzLD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzLD")

public actual fun lsxVftintrzWuS(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzWuS")

public actual fun lsxVftintrzLuD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzLuD")

public actual fun lsxVffintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintSW")

public actual fun lsxVffintDL(a: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintDL")

public actual fun lsxVffintSWu(a: V4u32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintSWu")

public actual fun lsxVffintDLu(a: V2u64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintDLu")

public actual fun lsxVsllwilHB(a: V16i8, b: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilHB")

public actual fun lsxVsllwilWH(a: V8i16, b: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilWH")

public actual fun lsxVsllwilDW(a: V4i32, b: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilDW")

public actual fun lsxVsllwilHuBu(a: V16u8, b: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilHuBu")

public actual fun lsxVsllwilWuHu(a: V8u16, b: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilWuHu")

public actual fun lsxVsllwilDuWu(a: V4u32, b: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsllwilDuWu")

public actual fun lsxVsranBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsranBH")

public actual fun lsxVsranHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsranHW")

public actual fun lsxVsranWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsranWD")

public actual fun lsxVssranBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranBH")

public actual fun lsxVssranHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranHW")

public actual fun lsxVssranWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranWD")

public actual fun lsxVssranBuH(a: V8u16, b: V8u16): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranBuH")

public actual fun lsxVssranHuW(a: V4u32, b: V4u32): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranHuW")

public actual fun lsxVssranWuD(a: V2u64, b: V2u64): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssranWuD")

public actual fun lsxVsrarnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarnBH")

public actual fun lsxVsrarnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarnHW")

public actual fun lsxVsrarnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarnWD")

public actual fun lsxVssrarnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnBH")

public actual fun lsxVssrarnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnHW")

public actual fun lsxVssrarnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnWD")

public actual fun lsxVssrarnBuH(a: V8u16, b: V8u16): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnBuH")

public actual fun lsxVssrarnHuW(a: V4u32, b: V4u32): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnHuW")

public actual fun lsxVssrarnWuD(a: V2u64, b: V2u64): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarnWuD")

public actual fun lsxVsrlnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlnBH")

public actual fun lsxVsrlnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlnHW")

public actual fun lsxVsrlnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlnWD")

public actual fun lsxVssrlnBuH(a: V8u16, b: V8u16): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnBuH")

public actual fun lsxVssrlnHuW(a: V4u32, b: V4u32): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnHuW")

public actual fun lsxVssrlnWuD(a: V2u64, b: V2u64): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnWuD")

public actual fun lsxVsrlrnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrnBH")

public actual fun lsxVsrlrnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrnHW")

public actual fun lsxVsrlrnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrnWD")

public actual fun lsxVssrlrnBuH(a: V8u16, b: V8u16): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnBuH")

public actual fun lsxVssrlrnHuW(a: V4u32, b: V4u32): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnHuW")

public actual fun lsxVssrlrnWuD(a: V2u64, b: V2u64): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnWuD")

public actual fun lsxVfrstpiB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrstpiB")

public actual fun lsxVfrstpiH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrstpiH")

public actual fun lsxVfrstpB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrstpB")

public actual fun lsxVfrstpH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrstpH")

public actual fun lsxVextrinsB(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextrinsB")

public actual fun lsxVextrinsH(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextrinsH")

public actual fun lsxVextrinsW(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextrinsW")

public actual fun lsxVextrinsD(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextrinsD")

public actual fun lsxVmskltzB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmskltzB")

public actual fun lsxVmskltzH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmskltzH")

public actual fun lsxVmskltzW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmskltzW")

public actual fun lsxVmskltzD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmskltzD")

public actual fun lsxVsigncovB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsigncovB")

public actual fun lsxVsigncovH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsigncovH")

public actual fun lsxVsigncovW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsigncovW")

public actual fun lsxVsigncovD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsigncovD")

public actual fun lsxVftintrneWS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrneWS")

public actual fun lsxVftintrneLD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrneLD")

public actual fun lsxVftintrpWS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrpWS")

public actual fun lsxVftintrpLD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrpLD")

public actual fun lsxVftintrmWS(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrmWS")

public actual fun lsxVftintrmLD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrmLD")

public actual fun lsxVftintWD(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintWD")

public actual fun lsxVffintSL(a: V2i64, b: V2i64): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintSL")

public actual fun lsxVftintrzWD(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzWD")

public actual fun lsxVftintrpWD(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrpWD")

public actual fun lsxVftintrmWD(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrmWD")

public actual fun lsxVftintrneWD(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrneWD")

public actual fun lsxVftintlLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintlLS")

public actual fun lsxVftinthLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftinthLS")

public actual fun lsxVffinthDW(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffinthDW")

public actual fun lsxVffintlDW(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVffintlDW")

public actual fun lsxVftintrzlLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzlLS")

public actual fun lsxVftintrzhLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrzhLS")

public actual fun lsxVftintrplLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrplLS")

public actual fun lsxVftintrphLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrphLS")

public actual fun lsxVftintrmlLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrmlLS")

public actual fun lsxVftintrmhLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrmhLS")

public actual fun lsxVftintrnelLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrnelLS")

public actual fun lsxVftintrnehLS(a: V4f32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVftintrnehLS")

public actual fun lsxVfrintrneS(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrintrneS")

public actual fun lsxVfrintrneD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfrintrneD")

public actual fun lsxVstelmB(a: V16i8, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVstelmB")
}

public actual fun lsxVstelmH(a: V8i16, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVstelmH")
}

public actual fun lsxVstelmW(a: V4i32, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVstelmW")
}

public actual fun lsxVstelmD(a: V2i64, b: MutPtr, c: Int, d: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVstelmD")
}

public actual fun lsxVaddwevDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevDW")

public actual fun lsxVaddwevWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevWH")

public actual fun lsxVaddwevHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevHB")

public actual fun lsxVaddwodDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodDW")

public actual fun lsxVaddwodWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodWH")

public actual fun lsxVaddwodHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodHB")

public actual fun lsxVaddwevDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevDWu")

public actual fun lsxVaddwevWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevWHu")

public actual fun lsxVaddwevHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevHBu")

public actual fun lsxVaddwodDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodDWu")

public actual fun lsxVaddwodWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodWHu")

public actual fun lsxVaddwodHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodHBu")

public actual fun lsxVaddwevDWuW(a: V4u32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevDWuW")

public actual fun lsxVaddwevWHuH(a: V8u16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevWHuH")

public actual fun lsxVaddwevHBuB(a: V16u8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevHBuB")

public actual fun lsxVaddwodDWuW(a: V4u32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodDWuW")

public actual fun lsxVaddwodWHuH(a: V8u16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodWHuH")

public actual fun lsxVaddwodHBuB(a: V16u8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodHBuB")

public actual fun lsxVsubwevDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevDW")

public actual fun lsxVsubwevWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevWH")

public actual fun lsxVsubwevHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevHB")

public actual fun lsxVsubwodDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodDW")

public actual fun lsxVsubwodWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodWH")

public actual fun lsxVsubwodHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodHB")

public actual fun lsxVsubwevDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevDWu")

public actual fun lsxVsubwevWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevWHu")

public actual fun lsxVsubwevHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevHBu")

public actual fun lsxVsubwodDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodDWu")

public actual fun lsxVsubwodWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodWHu")

public actual fun lsxVsubwodHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodHBu")

public actual fun lsxVaddwevQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevQD")

public actual fun lsxVaddwodQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodQD")

public actual fun lsxVaddwevQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevQDu")

public actual fun lsxVaddwodQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodQDu")

public actual fun lsxVsubwevQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevQD")

public actual fun lsxVsubwodQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodQD")

public actual fun lsxVsubwevQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwevQDu")

public actual fun lsxVsubwodQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubwodQDu")

public actual fun lsxVaddwevQDuD(a: V2u64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwevQDuD")

public actual fun lsxVaddwodQDuD(a: V2u64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddwodQDuD")

public actual fun lsxVmulwevDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevDW")

public actual fun lsxVmulwevWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevWH")

public actual fun lsxVmulwevHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevHB")

public actual fun lsxVmulwodDW(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodDW")

public actual fun lsxVmulwodWH(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodWH")

public actual fun lsxVmulwodHB(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodHB")

public actual fun lsxVmulwevDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevDWu")

public actual fun lsxVmulwevWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevWHu")

public actual fun lsxVmulwevHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevHBu")

public actual fun lsxVmulwodDWu(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodDWu")

public actual fun lsxVmulwodWHu(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodWHu")

public actual fun lsxVmulwodHBu(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodHBu")

public actual fun lsxVmulwevDWuW(a: V4u32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevDWuW")

public actual fun lsxVmulwevWHuH(a: V8u16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevWHuH")

public actual fun lsxVmulwevHBuB(a: V16u8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevHBuB")

public actual fun lsxVmulwodDWuW(a: V4u32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodDWuW")

public actual fun lsxVmulwodWHuH(a: V8u16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodWHuH")

public actual fun lsxVmulwodHBuB(a: V16u8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodHBuB")

public actual fun lsxVmulwevQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevQD")

public actual fun lsxVmulwodQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodQD")

public actual fun lsxVmulwevQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevQDu")

public actual fun lsxVmulwodQDu(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodQDu")

public actual fun lsxVmulwevQDuD(a: V2u64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwevQDuD")

public actual fun lsxVmulwodQDuD(a: V2u64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmulwodQDuD")

public actual fun lsxVhaddwQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwQD")

public actual fun lsxVhaddwQuDu(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhaddwQuDu")

public actual fun lsxVhsubwQD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwQD")

public actual fun lsxVhsubwQuDu(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVhsubwQuDu")

public actual fun lsxVmaddwevDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevDW")

public actual fun lsxVmaddwevWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevWH")

public actual fun lsxVmaddwevHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevHB")

public actual fun lsxVmaddwevDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevDWu")

public actual fun lsxVmaddwevWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevWHu")

public actual fun lsxVmaddwevHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevHBu")

public actual fun lsxVmaddwodDW(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodDW")

public actual fun lsxVmaddwodWH(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodWH")

public actual fun lsxVmaddwodHB(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodHB")

public actual fun lsxVmaddwodDWu(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodDWu")

public actual fun lsxVmaddwodWHu(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodWHu")

public actual fun lsxVmaddwodHBu(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodHBu")

public actual fun lsxVmaddwevDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevDWuW")

public actual fun lsxVmaddwevWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevWHuH")

public actual fun lsxVmaddwevHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevHBuB")

public actual fun lsxVmaddwodDWuW(a: V2i64, b: V4u32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodDWuW")

public actual fun lsxVmaddwodWHuH(a: V4i32, b: V8u16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodWHuH")

public actual fun lsxVmaddwodHBuB(a: V8i16, b: V16u8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodHBuB")

public actual fun lsxVmaddwevQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevQD")

public actual fun lsxVmaddwodQD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodQD")

public actual fun lsxVmaddwevQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevQDu")

public actual fun lsxVmaddwodQDu(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodQDu")

public actual fun lsxVmaddwevQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwevQDuD")

public actual fun lsxVmaddwodQDuD(a: V2i64, b: V2u64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmaddwodQDuD")

public actual fun lsxVaddQ(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVaddQ")

public actual fun lsxVsubQ(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsubQ")

public actual fun lsxVldreplB(a: ConstPtr, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVldreplB")

public actual fun lsxVldreplH(a: ConstPtr, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVldreplH")

public actual fun lsxVldreplW(a: ConstPtr, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVldreplW")

public actual fun lsxVldreplD(a: ConstPtr, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVldreplD")

public actual fun lsxVmskgezB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmskgezB")

public actual fun lsxVmsknzB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVmsknzB")

public actual fun lsxVexthHB(a: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthHB")

public actual fun lsxVexthWH(a: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthWH")

public actual fun lsxVexthDW(a: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthDW")

public actual fun lsxVexthQD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthQD")

public actual fun lsxVexthHuBu(a: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthHuBu")

public actual fun lsxVexthWuHu(a: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthWuHu")

public actual fun lsxVexthDuWu(a: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthDuWu")

public actual fun lsxVexthQuDu(a: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVexthQuDu")

public actual fun lsxVextlQD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextlQD")

public actual fun lsxVsrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlniBH")

public actual fun lsxVsrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlniHW")

public actual fun lsxVsrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlniWD")

public actual fun lsxVsrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlniDQ")

public actual fun lsxVsrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrniBH")

public actual fun lsxVsrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrniHW")

public actual fun lsxVsrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrniWD")

public actual fun lsxVsrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrlrniDQ")

public actual fun lsxVssrlniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniBH")

public actual fun lsxVssrlniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniHW")

public actual fun lsxVssrlniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniWD")

public actual fun lsxVssrlniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniDQ")

public actual fun lsxVssrlniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniBuH")

public actual fun lsxVssrlniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniHuW")

public actual fun lsxVssrlniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniWuD")

public actual fun lsxVssrlniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlniDuQ")

public actual fun lsxVssrlrniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniBH")

public actual fun lsxVssrlrniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniHW")

public actual fun lsxVssrlrniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniWD")

public actual fun lsxVssrlrniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniDQ")

public actual fun lsxVssrlrniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniBuH")

public actual fun lsxVssrlrniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniHuW")

public actual fun lsxVssrlrniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniWuD")

public actual fun lsxVssrlrniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrniDuQ")

public actual fun lsxVsraniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsraniBH")

public actual fun lsxVsraniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsraniHW")

public actual fun lsxVsraniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsraniWD")

public actual fun lsxVsraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsraniDQ")

public actual fun lsxVsrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarniBH")

public actual fun lsxVsrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarniHW")

public actual fun lsxVsrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarniWD")

public actual fun lsxVsrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVsrarniDQ")

public actual fun lsxVssraniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniBH")

public actual fun lsxVssraniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniHW")

public actual fun lsxVssraniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniWD")

public actual fun lsxVssraniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniDQ")

public actual fun lsxVssraniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniBuH")

public actual fun lsxVssraniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniHuW")

public actual fun lsxVssraniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniWuD")

public actual fun lsxVssraniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssraniDuQ")

public actual fun lsxVssrarniBH(a: V16i8, b: V16i8, c: UInt): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniBH")

public actual fun lsxVssrarniHW(a: V8i16, b: V8i16, c: UInt): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniHW")

public actual fun lsxVssrarniWD(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniWD")

public actual fun lsxVssrarniDQ(a: V2i64, b: V2i64, c: UInt): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniDQ")

public actual fun lsxVssrarniBuH(a: V16u8, b: V16i8, c: UInt): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniBuH")

public actual fun lsxVssrarniHuW(a: V8u16, b: V8i16, c: UInt): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniHuW")

public actual fun lsxVssrarniWuD(a: V4u32, b: V4i32, c: UInt): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniWuD")

public actual fun lsxVssrarniDuQ(a: V2u64, b: V2i64, c: UInt): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrarniDuQ")

public actual fun lsxVpermiW(a: V4i32, b: V4i32, c: UInt): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVpermiW")

public actual fun lsxVssrlrnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnBH")

public actual fun lsxVssrlrnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnHW")

public actual fun lsxVssrlrnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlrnWD")

public actual fun lsxVssrlnBH(a: V8i16, b: V8i16): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnBH")

public actual fun lsxVssrlnHW(a: V4i32, b: V4i32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnHW")

public actual fun lsxVssrlnWD(a: V2i64, b: V2i64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVssrlnWD")

public actual fun lsxVshufB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVshufB")

public actual fun lsxVextlQuDu(a: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVextlQuDu")

public actual fun lsxBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBnzB")

public actual fun lsxBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBnzD")

public actual fun lsxBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBnzH")

public actual fun lsxBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBnzV")

public actual fun lsxBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBnzW")

public actual fun lsxBzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBzB")

public actual fun lsxBzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBzD")

public actual fun lsxBzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBzH")

public actual fun lsxBzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBzV")

public actual fun lsxBzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxBzW")

public actual fun lsxVfcmpCafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCafD")

public actual fun lsxVfcmpCafS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCafS")

public actual fun lsxVfcmpCeqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCeqD")

public actual fun lsxVfcmpCeqS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCeqS")

public actual fun lsxVfcmpCleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCleD")

public actual fun lsxVfcmpCleS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCleS")

public actual fun lsxVfcmpCltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCltD")

public actual fun lsxVfcmpCltS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCltS")

public actual fun lsxVfcmpCneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCneD")

public actual fun lsxVfcmpCneS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCneS")

public actual fun lsxVfcmpCorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCorD")

public actual fun lsxVfcmpCorS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCorS")

public actual fun lsxVfcmpCueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCueqD")

public actual fun lsxVfcmpCueqS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCueqS")

public actual fun lsxVfcmpCuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCuleD")

public actual fun lsxVfcmpCuleS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCuleS")

public actual fun lsxVfcmpCultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCultD")

public actual fun lsxVfcmpCultS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCultS")

public actual fun lsxVfcmpCunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCunD")

public actual fun lsxVfcmpCuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCuneD")

public actual fun lsxVfcmpCuneS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCuneS")

public actual fun lsxVfcmpCunS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpCunS")

public actual fun lsxVfcmpSafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSafD")

public actual fun lsxVfcmpSafS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSafS")

public actual fun lsxVfcmpSeqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSeqD")

public actual fun lsxVfcmpSeqS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSeqS")

public actual fun lsxVfcmpSleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSleD")

public actual fun lsxVfcmpSleS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSleS")

public actual fun lsxVfcmpSltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSltD")

public actual fun lsxVfcmpSltS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSltS")

public actual fun lsxVfcmpSneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSneD")

public actual fun lsxVfcmpSneS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSneS")

public actual fun lsxVfcmpSorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSorD")

public actual fun lsxVfcmpSorS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSorS")

public actual fun lsxVfcmpSueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSueqD")

public actual fun lsxVfcmpSueqS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSueqS")

public actual fun lsxVfcmpSuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSuleD")

public actual fun lsxVfcmpSuleS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSuleS")

public actual fun lsxVfcmpSultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSultD")

public actual fun lsxVfcmpSultS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSultS")

public actual fun lsxVfcmpSunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSunD")

public actual fun lsxVfcmpSuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSuneD")

public actual fun lsxVfcmpSuneS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSuneS")

public actual fun lsxVfcmpSunS(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchLoongarch64LsxGeneratedLsxVfcmpSunS")
