@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun msaAddAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddAB")

public actual fun msaAddAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddAH")

public actual fun msaAddAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddAW")

public actual fun msaAddAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddAD")

public actual fun msaAddsAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsAB")

public actual fun msaAddsAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsAH")

public actual fun msaAddsAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsAW")

public actual fun msaAddsAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsAD")

public actual fun msaAddsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsSB")

public actual fun msaAddsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsSH")

public actual fun msaAddsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsSW")

public actual fun msaAddsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsSD")

public actual fun msaAddsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsUB")

public actual fun msaAddsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsUH")

public actual fun msaAddsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsUW")

public actual fun msaAddsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddsUD")

public actual fun msaAddvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddvB")

public actual fun msaAddvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddvH")

public actual fun msaAddvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddvW")

public actual fun msaAddvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddvD")

public actual fun msaAddviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddviB")

public actual fun msaAddviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddviH")

public actual fun msaAddviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddviW")

public actual fun msaAddviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAddviD")

public actual fun msaAndV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAndV")

public actual fun msaAndiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAndiB")

public actual fun msaAsubSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubSB")

public actual fun msaAsubSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubSH")

public actual fun msaAsubSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubSW")

public actual fun msaAsubSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubSD")

public actual fun msaAsubUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubUB")

public actual fun msaAsubUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubUH")

public actual fun msaAsubUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubUW")

public actual fun msaAsubUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAsubUD")

public actual fun msaAveSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveSB")

public actual fun msaAveSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveSH")

public actual fun msaAveSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveSW")

public actual fun msaAveSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveSD")

public actual fun msaAveUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveUB")

public actual fun msaAveUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveUH")

public actual fun msaAveUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveUW")

public actual fun msaAveUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAveUD")

public actual fun msaAverSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverSB")

public actual fun msaAverSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverSH")

public actual fun msaAverSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverSW")

public actual fun msaAverSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverSD")

public actual fun msaAverUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverUB")

public actual fun msaAverUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverUH")

public actual fun msaAverUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverUW")

public actual fun msaAverUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaAverUD")

public actual fun msaBclrB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclrB")

public actual fun msaBclrH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclrH")

public actual fun msaBclrW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclrW")

public actual fun msaBclrD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclrD")

public actual fun msaBclriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclriB")

public actual fun msaBclriH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclriH")

public actual fun msaBclriW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclriW")

public actual fun msaBclriD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBclriD")

public actual fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinslB")

public actual fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinslH")

public actual fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinslW")

public actual fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinslD")

public actual fun msaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsliB")

public actual fun msaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsliH")

public actual fun msaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsliW")

public actual fun msaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsliD")

public actual fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsrB")

public actual fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsrH")

public actual fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsrW")

public actual fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsrD")

public actual fun msaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsriB")

public actual fun msaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsriH")

public actual fun msaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsriW")

public actual fun msaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBinsriD")

public actual fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBmnzV")

public actual fun msaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBmnziB")

public actual fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBmzV")

public actual fun msaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBmziB")

public actual fun msaBnegB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegB")

public actual fun msaBnegH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegH")

public actual fun msaBnegW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegW")

public actual fun msaBnegD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegD")

public actual fun msaBnegiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegiB")

public actual fun msaBnegiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegiH")

public actual fun msaBnegiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegiW")

public actual fun msaBnegiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnegiD")

public actual fun msaBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnzB")

public actual fun msaBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnzH")

public actual fun msaBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnzW")

public actual fun msaBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnzD")

public actual fun msaBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBnzV")

public actual fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBselV")

public actual fun msaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBseliB")

public actual fun msaBsetB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetB")

public actual fun msaBsetH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetH")

public actual fun msaBsetW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetW")

public actual fun msaBsetD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetD")

public actual fun msaBsetiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetiB")

public actual fun msaBsetiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetiH")

public actual fun msaBsetiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetiW")

public actual fun msaBsetiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBsetiD")

public actual fun msaBzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBzB")

public actual fun msaBzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBzH")

public actual fun msaBzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBzW")

public actual fun msaBzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBzD")

public actual fun msaBzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaBzV")

public actual fun msaCeqB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqB")

public actual fun msaCeqH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqH")

public actual fun msaCeqW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqW")

public actual fun msaCeqD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqD")

public actual fun msaCeqiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqiB")

public actual fun msaCeqiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqiH")

public actual fun msaCeqiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqiW")

public actual fun msaCeqiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCeqiD")

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchMipsMsaMsaCfcmsa")
private external fun wasiRustlangCorearchMipsMsaMsaCfcmsa(a: Int): Int

public actual fun msaCfcmsa(a: Int): Int =
    wasiRustlangCorearchMipsMsaMsaCfcmsa(a).toInt()

public actual fun msaCleSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleSB")

public actual fun msaCleSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleSH")

public actual fun msaCleSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleSW")

public actual fun msaCleSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleSD")

public actual fun msaCleUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleUB")

public actual fun msaCleUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleUH")

public actual fun msaCleUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleUW")

public actual fun msaCleUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleUD")

public actual fun msaCleiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiSB")

public actual fun msaCleiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiSH")

public actual fun msaCleiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiSW")

public actual fun msaCleiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiSD")

public actual fun msaCleiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiUB")

public actual fun msaCleiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiUH")

public actual fun msaCleiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiUW")

public actual fun msaCleiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCleiUD")

public actual fun msaCltSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltSB")

public actual fun msaCltSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltSH")

public actual fun msaCltSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltSW")

public actual fun msaCltSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltSD")

public actual fun msaCltUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltUB")

public actual fun msaCltUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltUH")

public actual fun msaCltUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltUW")

public actual fun msaCltUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltUD")

public actual fun msaCltiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiSB")

public actual fun msaCltiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiSH")

public actual fun msaCltiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiSW")

public actual fun msaCltiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiSD")

public actual fun msaCltiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiUB")

public actual fun msaCltiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiUH")

public actual fun msaCltiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiUW")

public actual fun msaCltiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCltiUD")

public actual fun msaCopySB(a: V16i8, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopySB")

public actual fun msaCopySH(a: V8i16, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopySH")

public actual fun msaCopySW(a: V4i32, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopySW")

public actual fun msaCopySD(a: V2i64, b: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopySD")

public actual fun msaCopyUB(a: V16i8, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopyUB")

public actual fun msaCopyUH(a: V8i16, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopyUH")

public actual fun msaCopyUW(a: V4i32, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopyUW")

public actual fun msaCopyUD(a: V2i64, b: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaCopyUD")

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchMipsMsaMsaCtcmsa")
private external fun wasiRustlangCorearchMipsMsaMsaCtcmsa(imm5: Int, a: Int): Unit

public actual fun msaCtcmsa(imm5: Int, a: Int): Unit =
    wasiRustlangCorearchMipsMsaMsaCtcmsa(imm5, a)

public actual fun msaDivSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivSB")

public actual fun msaDivSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivSH")

public actual fun msaDivSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivSW")

public actual fun msaDivSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivSD")

public actual fun msaDivUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivUB")

public actual fun msaDivUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivUH")

public actual fun msaDivUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivUW")

public actual fun msaDivUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDivUD")

public actual fun msaDotpSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpSH")

public actual fun msaDotpSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpSW")

public actual fun msaDotpSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpSD")

public actual fun msaDotpUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpUH")

public actual fun msaDotpUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpUW")

public actual fun msaDotpUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDotpUD")

public actual fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddSH")

public actual fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddSW")

public actual fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddSD")

public actual fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddUH")

public actual fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddUW")

public actual fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpaddUD")

public actual fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubSH")

public actual fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubSW")

public actual fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubSD")

public actual fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubUH")

public actual fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubUW")

public actual fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaDpsubUD")

public actual fun msaFaddW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFaddW")

public actual fun msaFaddD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFaddD")

public actual fun msaFcafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcafW")

public actual fun msaFcafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcafD")

public actual fun msaFceqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFceqW")

public actual fun msaFceqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFceqD")

public actual fun msaFclassW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFclassW")

public actual fun msaFclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFclassD")

public actual fun msaFcleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcleW")

public actual fun msaFcleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcleD")

public actual fun msaFcltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcltW")

public actual fun msaFcltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcltD")

public actual fun msaFcneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcneW")

public actual fun msaFcneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcneD")

public actual fun msaFcorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcorW")

public actual fun msaFcorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcorD")

public actual fun msaFcueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcueqW")

public actual fun msaFcueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcueqD")

public actual fun msaFculeW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFculeW")

public actual fun msaFculeD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFculeD")

public actual fun msaFcultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcultW")

public actual fun msaFcultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcultD")

public actual fun msaFcunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcunW")

public actual fun msaFcunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcunD")

public actual fun msaFcuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcuneW")

public actual fun msaFcuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFcuneD")

public actual fun msaFdivW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFdivW")

public actual fun msaFdivD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFdivD")

public actual fun msaFexdoW(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFexdoW")

public actual fun msaFexp2W(a: V4f32, b: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFexp2W")

public actual fun msaFexp2D(a: V2f64, b: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFexp2D")

public actual fun msaFexuplD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFexuplD")

public actual fun msaFexuprD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFexuprD")

public actual fun msaFfintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfintSW")

public actual fun msaFfintSD(a: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfintSD")

public actual fun msaFfintUW(a: V4u32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfintUW")

public actual fun msaFfintUD(a: V2u64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfintUD")

public actual fun msaFfqlW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfqlW")

public actual fun msaFfqlD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfqlD")

public actual fun msaFfqrW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfqrW")

public actual fun msaFfqrD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFfqrD")

public actual fun msaFillB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFillB")

public actual fun msaFillH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFillH")

public actual fun msaFillW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFillW")

public actual fun msaFillD(a: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFillD")

public actual fun msaFlog2W(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFlog2W")

public actual fun msaFlog2D(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFlog2D")

public actual fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaddW")

public actual fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaddD")

public actual fun msaFmaxW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaxW")

public actual fun msaFmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaxD")

public actual fun msaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaxAW")

public actual fun msaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmaxAD")

public actual fun msaFminW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFminW")

public actual fun msaFminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFminD")

public actual fun msaFminAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFminAW")

public actual fun msaFminAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFminAD")

public actual fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmsubW")

public actual fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmsubD")

public actual fun msaFmulW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmulW")

public actual fun msaFmulD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFmulD")

public actual fun msaFrintW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrintW")

public actual fun msaFrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrintD")

public actual fun msaFrcpW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrcpW")

public actual fun msaFrcpD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrcpD")

public actual fun msaFrsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrsqrtW")

public actual fun msaFrsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFrsqrtD")

public actual fun msaFsafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsafW")

public actual fun msaFsafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsafD")

public actual fun msaFseqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFseqW")

public actual fun msaFseqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFseqD")

public actual fun msaFsleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsleW")

public actual fun msaFsleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsleD")

public actual fun msaFsltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsltW")

public actual fun msaFsltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsltD")

public actual fun msaFsneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsneW")

public actual fun msaFsneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsneD")

public actual fun msaFsorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsorW")

public actual fun msaFsorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsorD")

public actual fun msaFsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsqrtW")

public actual fun msaFsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsqrtD")

public actual fun msaFsubW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsubW")

public actual fun msaFsubD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsubD")

public actual fun msaFsueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsueqW")

public actual fun msaFsueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsueqD")

public actual fun msaFsuleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsuleW")

public actual fun msaFsuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsuleD")

public actual fun msaFsultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsultW")

public actual fun msaFsultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsultD")

public actual fun msaFsunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsunW")

public actual fun msaFsunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsunD")

public actual fun msaFsuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsuneW")

public actual fun msaFsuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFsuneD")

public actual fun msaFtintSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtintSW")

public actual fun msaFtintSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtintSD")

public actual fun msaFtintUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtintUW")

public actual fun msaFtintUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtintUD")

public actual fun msaFtqH(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtqH")

public actual fun msaFtqW(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtqW")

public actual fun msaFtruncSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtruncSW")

public actual fun msaFtruncSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtruncSD")

public actual fun msaFtruncUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtruncUW")

public actual fun msaFtruncUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaFtruncUD")

public actual fun msaHaddSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddSH")

public actual fun msaHaddSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddSW")

public actual fun msaHaddSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddSD")

public actual fun msaHaddUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddUH")

public actual fun msaHaddUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddUW")

public actual fun msaHaddUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHaddUD")

public actual fun msaHsubSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubSH")

public actual fun msaHsubSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubSW")

public actual fun msaHsubSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubSD")

public actual fun msaHsubUH(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubUH")

public actual fun msaHsubUW(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubUW")

public actual fun msaHsubUD(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaHsubUD")

public actual fun msaIlvevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvevB")

public actual fun msaIlvevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvevH")

public actual fun msaIlvevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvevW")

public actual fun msaIlvevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvevD")

public actual fun msaIlvlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvlB")

public actual fun msaIlvlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvlH")

public actual fun msaIlvlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvlW")

public actual fun msaIlvlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvlD")

public actual fun msaIlvodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvodB")

public actual fun msaIlvodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvodH")

public actual fun msaIlvodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvodW")

public actual fun msaIlvodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvodD")

public actual fun msaIlvrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvrB")

public actual fun msaIlvrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvrH")

public actual fun msaIlvrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvrW")

public actual fun msaIlvrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaIlvrD")

public actual fun msaInsertB(a: V16i8, b: Int, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsertB")

public actual fun msaInsertH(a: V8i16, b: Int, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsertH")

public actual fun msaInsertW(a: V4i32, b: Int, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsertW")

public actual fun msaInsertD(a: V2i64, b: Int, c: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsertD")

public actual fun msaInsveB(a: V16i8, b: Int, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsveB")

public actual fun msaInsveH(a: V8i16, b: Int, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsveH")

public actual fun msaInsveW(a: V4i32, b: Int, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsveW")

public actual fun msaInsveD(a: V2i64, b: Int, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaInsveD")

public actual fun msaLdB(memAddr: MutPtr, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdB")

public actual fun msaLdH(memAddr: MutPtr, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdH")

public actual fun msaLdW(memAddr: MutPtr, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdW")

public actual fun msaLdD(memAddr: MutPtr, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdD")

public actual fun msaLdiB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdiB")

public actual fun msaLdiH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdiH")

public actual fun msaLdiW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdiW")

public actual fun msaLdiD(a: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaLdiD")

public actual fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddQH")

public actual fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddQW")

public actual fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddrQH")

public actual fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddrQW")

public actual fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddvB")

public actual fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddvH")

public actual fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddvW")

public actual fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaddvD")

public actual fun msaMaxAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxAB")

public actual fun msaMaxAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxAH")

public actual fun msaMaxAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxAW")

public actual fun msaMaxAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxAD")

public actual fun msaMaxSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxSB")

public actual fun msaMaxSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxSH")

public actual fun msaMaxSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxSW")

public actual fun msaMaxSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxSD")

public actual fun msaMaxUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxUB")

public actual fun msaMaxUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxUH")

public actual fun msaMaxUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxUW")

public actual fun msaMaxUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxUD")

public actual fun msaMaxiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiSB")

public actual fun msaMaxiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiSH")

public actual fun msaMaxiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiSW")

public actual fun msaMaxiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiSD")

public actual fun msaMaxiUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiUB")

public actual fun msaMaxiUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiUH")

public actual fun msaMaxiUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiUW")

public actual fun msaMaxiUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMaxiUD")

public actual fun msaMinAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinAB")

public actual fun msaMinAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinAH")

public actual fun msaMinAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinAW")

public actual fun msaMinAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinAD")

public actual fun msaMinSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinSB")

public actual fun msaMinSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinSH")

public actual fun msaMinSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinSW")

public actual fun msaMinSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinSD")

public actual fun msaMinUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinUB")

public actual fun msaMinUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinUH")

public actual fun msaMinUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinUW")

public actual fun msaMinUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMinUD")

public actual fun msaMiniSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniSB")

public actual fun msaMiniSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniSH")

public actual fun msaMiniSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniSW")

public actual fun msaMiniSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniSD")

public actual fun msaMiniUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniUB")

public actual fun msaMiniUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniUH")

public actual fun msaMiniUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniUW")

public actual fun msaMiniUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMiniUD")

public actual fun msaModSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModSB")

public actual fun msaModSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModSH")

public actual fun msaModSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModSW")

public actual fun msaModSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModSD")

public actual fun msaModUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModUB")

public actual fun msaModUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModUH")

public actual fun msaModUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModUW")

public actual fun msaModUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaModUD")

public actual fun msaMoveV(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMoveV")

public actual fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubQH")

public actual fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubQW")

public actual fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubrQH")

public actual fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubrQW")

public actual fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubvB")

public actual fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubvH")

public actual fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubvW")

public actual fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMsubvD")

public actual fun msaMulQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulQH")

public actual fun msaMulQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulQW")

public actual fun msaMulrQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulrQH")

public actual fun msaMulrQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulrQW")

public actual fun msaMulvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulvB")

public actual fun msaMulvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulvH")

public actual fun msaMulvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulvW")

public actual fun msaMulvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaMulvD")

public actual fun msaNlocB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlocB")

public actual fun msaNlocH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlocH")

public actual fun msaNlocW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlocW")

public actual fun msaNlocD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlocD")

public actual fun msaNlzcB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlzcB")

public actual fun msaNlzcH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlzcH")

public actual fun msaNlzcW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlzcW")

public actual fun msaNlzcD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNlzcD")

public actual fun msaNorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNorV")

public actual fun msaNoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaNoriB")

public actual fun msaOrV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaOrV")

public actual fun msaOriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaOriB")

public actual fun msaPckevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckevB")

public actual fun msaPckevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckevH")

public actual fun msaPckevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckevW")

public actual fun msaPckevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckevD")

public actual fun msaPckodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckodB")

public actual fun msaPckodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckodH")

public actual fun msaPckodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckodW")

public actual fun msaPckodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPckodD")

public actual fun msaPcntB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPcntB")

public actual fun msaPcntH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPcntH")

public actual fun msaPcntW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPcntW")

public actual fun msaPcntD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaPcntD")

public actual fun msaSatSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatSB")

public actual fun msaSatSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatSH")

public actual fun msaSatSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatSW")

public actual fun msaSatSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatSD")

public actual fun msaSatUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatUB")

public actual fun msaSatUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatUH")

public actual fun msaSatUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatUW")

public actual fun msaSatUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSatUD")

public actual fun msaShfB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaShfB")

public actual fun msaShfH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaShfH")

public actual fun msaShfW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaShfW")

public actual fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldB")

public actual fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldH")

public actual fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldW")

public actual fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldD")

public actual fun msaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldiB")

public actual fun msaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldiH")

public actual fun msaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldiW")

public actual fun msaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSldiD")

public actual fun msaSllB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSllB")

public actual fun msaSllH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSllH")

public actual fun msaSllW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSllW")

public actual fun msaSllD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSllD")

public actual fun msaSlliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSlliB")

public actual fun msaSlliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSlliH")

public actual fun msaSlliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSlliW")

public actual fun msaSlliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSlliD")

public actual fun msaSplatB(a: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatB")

public actual fun msaSplatH(a: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatH")

public actual fun msaSplatW(a: V4i32, w: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatW")

public actual fun msaSplatD(a: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatD")

public actual fun msaSplatiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatiB")

public actual fun msaSplatiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatiH")

public actual fun msaSplatiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatiW")

public actual fun msaSplatiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSplatiD")

public actual fun msaSraB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraB")

public actual fun msaSraH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraH")

public actual fun msaSraW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraW")

public actual fun msaSraD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraD")

public actual fun msaSraiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraiB")

public actual fun msaSraiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraiH")

public actual fun msaSraiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraiW")

public actual fun msaSraiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSraiD")

public actual fun msaSrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrarB")

public actual fun msaSrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrarH")

public actual fun msaSrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrarW")

public actual fun msaSrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrarD")

public actual fun msaSrariB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrariB")

public actual fun msaSrariH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrariH")

public actual fun msaSrariW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrariW")

public actual fun msaSrariD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrariD")

public actual fun msaSrlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlB")

public actual fun msaSrlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlH")

public actual fun msaSrlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlW")

public actual fun msaSrlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlD")

public actual fun msaSrliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrliB")

public actual fun msaSrliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrliH")

public actual fun msaSrliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrliW")

public actual fun msaSrliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrliD")

public actual fun msaSrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlrB")

public actual fun msaSrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlrH")

public actual fun msaSrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlrW")

public actual fun msaSrlrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlrD")

public actual fun msaSrlriB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlriB")

public actual fun msaSrlriH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlriH")

public actual fun msaSrlriW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlriW")

public actual fun msaSrlriD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSrlriD")

public actual fun msaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaStB")
}

public actual fun msaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaStH")
}

public actual fun msaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaStW")
}

public actual fun msaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaStD")
}

public actual fun msaSubsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsSB")

public actual fun msaSubsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsSH")

public actual fun msaSubsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsSW")

public actual fun msaSubsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsSD")

public actual fun msaSubsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsUB")

public actual fun msaSubsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsUH")

public actual fun msaSubsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsUW")

public actual fun msaSubsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsUD")

public actual fun msaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsusUB")

public actual fun msaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsusUH")

public actual fun msaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsusUW")

public actual fun msaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsusUD")

public actual fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsuuSB")

public actual fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsuuSH")

public actual fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsuuSW")

public actual fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubsuuSD")

public actual fun msaSubvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubvB")

public actual fun msaSubvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubvH")

public actual fun msaSubvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubvW")

public actual fun msaSubvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubvD")

public actual fun msaSubviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubviB")

public actual fun msaSubviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubviH")

public actual fun msaSubviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubviW")

public actual fun msaSubviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaSubviD")

public actual fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaVshfB")

public actual fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaVshfH")

public actual fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaVshfW")

public actual fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaVshfD")

public actual fun msaXorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaXorV")

public actual fun msaXoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchMipsMsaMsaXoriB")
