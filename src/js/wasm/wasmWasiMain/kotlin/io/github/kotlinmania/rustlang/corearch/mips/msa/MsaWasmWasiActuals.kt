@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun externMsaAddAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddAB")

public actual fun externMsaAddAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddAH")

public actual fun externMsaAddAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddAW")

public actual fun externMsaAddAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddAD")

public actual fun externMsaAddsAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsAB")

public actual fun externMsaAddsAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsAH")

public actual fun externMsaAddsAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsAW")

public actual fun externMsaAddsAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsAD")

public actual fun externMsaAddsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsSB")

public actual fun externMsaAddsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsSH")

public actual fun externMsaAddsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsSW")

public actual fun externMsaAddsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsSD")

public actual fun externMsaAddsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsUB")

public actual fun externMsaAddsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsUH")

public actual fun externMsaAddsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsUW")

public actual fun externMsaAddsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddsUD")

public actual fun externMsaAddvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddvB")

public actual fun externMsaAddvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddvH")

public actual fun externMsaAddvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddvW")

public actual fun externMsaAddvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddvD")

public actual fun externMsaAddviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddviB")

public actual fun externMsaAddviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddviH")

public actual fun externMsaAddviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddviW")

public actual fun externMsaAddviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAddviD")

public actual fun externMsaAndV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAndV")

public actual fun externMsaAndiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAndiB")

public actual fun externMsaAsubSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubSB")

public actual fun externMsaAsubSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubSH")

public actual fun externMsaAsubSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubSW")

public actual fun externMsaAsubSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubSD")

public actual fun externMsaAsubUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubUB")

public actual fun externMsaAsubUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubUH")

public actual fun externMsaAsubUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubUW")

public actual fun externMsaAsubUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAsubUD")

public actual fun externMsaAveSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveSB")

public actual fun externMsaAveSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveSH")

public actual fun externMsaAveSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveSW")

public actual fun externMsaAveSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveSD")

public actual fun externMsaAveUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveUB")

public actual fun externMsaAveUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveUH")

public actual fun externMsaAveUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveUW")

public actual fun externMsaAveUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAveUD")

public actual fun externMsaAverSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverSB")

public actual fun externMsaAverSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverSH")

public actual fun externMsaAverSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverSW")

public actual fun externMsaAverSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverSD")

public actual fun externMsaAverUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverUB")

public actual fun externMsaAverUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverUH")

public actual fun externMsaAverUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverUW")

public actual fun externMsaAverUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaAverUD")

public actual fun externMsaBclrB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclrB")

public actual fun externMsaBclrH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclrH")

public actual fun externMsaBclrW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclrW")

public actual fun externMsaBclrD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclrD")

public actual fun externMsaBclriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclriB")

public actual fun externMsaBclriH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclriH")

public actual fun externMsaBclriW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclriW")

public actual fun externMsaBclriD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBclriD")

public actual fun externMsaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinslB")

public actual fun externMsaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinslH")

public actual fun externMsaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinslW")

public actual fun externMsaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinslD")

public actual fun externMsaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsliB")

public actual fun externMsaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsliH")

public actual fun externMsaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsliW")

public actual fun externMsaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsliD")

public actual fun externMsaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsrB")

public actual fun externMsaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsrH")

public actual fun externMsaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsrW")

public actual fun externMsaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsrD")

public actual fun externMsaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsriB")

public actual fun externMsaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsriH")

public actual fun externMsaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsriW")

public actual fun externMsaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBinsriD")

public actual fun externMsaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBmnzV")

public actual fun externMsaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBmnziB")

public actual fun externMsaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBmzV")

public actual fun externMsaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBmziB")

public actual fun externMsaBnegB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegB")

public actual fun externMsaBnegH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegH")

public actual fun externMsaBnegW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegW")

public actual fun externMsaBnegD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegD")

public actual fun externMsaBnegiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegiB")

public actual fun externMsaBnegiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegiH")

public actual fun externMsaBnegiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegiW")

public actual fun externMsaBnegiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnegiD")

public actual fun externMsaBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnzB")

public actual fun externMsaBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnzH")

public actual fun externMsaBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnzW")

public actual fun externMsaBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnzD")

public actual fun externMsaBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBnzV")

public actual fun externMsaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBselV")

public actual fun externMsaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBseliB")

public actual fun externMsaBsetB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetB")

public actual fun externMsaBsetH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetH")

public actual fun externMsaBsetW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetW")

public actual fun externMsaBsetD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetD")

public actual fun externMsaBsetiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetiB")

public actual fun externMsaBsetiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetiH")

public actual fun externMsaBsetiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetiW")

public actual fun externMsaBsetiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBsetiD")

public actual fun externMsaBzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBzB")

public actual fun externMsaBzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBzH")

public actual fun externMsaBzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBzW")

public actual fun externMsaBzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBzD")

public actual fun externMsaBzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaBzV")

public actual fun externMsaCeqB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqB")

public actual fun externMsaCeqH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqH")

public actual fun externMsaCeqW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqW")

public actual fun externMsaCeqD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqD")

public actual fun externMsaCeqiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqiB")

public actual fun externMsaCeqiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqiH")

public actual fun externMsaCeqiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqiW")

public actual fun externMsaCeqiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCeqiD")

@kotlin.wasm.WasmImport("kmcore", "corearchMipsMsaExternMsaCfcmsa")
private external fun wasiCorearchMipsMsaExternMsaCfcmsa(a: Int): Int

public actual fun externMsaCfcmsa(a: Int): Int =
    wasiCorearchMipsMsaExternMsaCfcmsa(a).toInt()

public actual fun externMsaCleSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleSB")

public actual fun externMsaCleSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleSH")

public actual fun externMsaCleSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleSW")

public actual fun externMsaCleSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleSD")

public actual fun externMsaCleUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleUB")

public actual fun externMsaCleUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleUH")

public actual fun externMsaCleUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleUW")

public actual fun externMsaCleUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleUD")

public actual fun externMsaCleiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiSB")

public actual fun externMsaCleiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiSH")

public actual fun externMsaCleiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiSW")

public actual fun externMsaCleiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiSD")

public actual fun externMsaCleiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiUB")

public actual fun externMsaCleiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiUH")

public actual fun externMsaCleiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiUW")

public actual fun externMsaCleiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCleiUD")

public actual fun externMsaCltSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltSB")

public actual fun externMsaCltSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltSH")

public actual fun externMsaCltSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltSW")

public actual fun externMsaCltSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltSD")

public actual fun externMsaCltUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltUB")

public actual fun externMsaCltUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltUH")

public actual fun externMsaCltUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltUW")

public actual fun externMsaCltUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltUD")

public actual fun externMsaCltiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiSB")

public actual fun externMsaCltiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiSH")

public actual fun externMsaCltiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiSW")

public actual fun externMsaCltiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiSD")

public actual fun externMsaCltiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiUB")

public actual fun externMsaCltiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiUH")

public actual fun externMsaCltiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiUW")

public actual fun externMsaCltiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCltiUD")

public actual fun externMsaCopySB(a: V16i8, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopySB")

public actual fun externMsaCopySH(a: V8i16, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopySH")

public actual fun externMsaCopySW(a: V4i32, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopySW")

public actual fun externMsaCopySD(a: V2i64, b: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopySD")

public actual fun externMsaCopyUB(a: V16i8, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopyUB")

public actual fun externMsaCopyUH(a: V8i16, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopyUH")

public actual fun externMsaCopyUW(a: V4i32, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopyUW")

public actual fun externMsaCopyUD(a: V2i64, b: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaCopyUD")

@kotlin.wasm.WasmImport("kmcore", "corearchMipsMsaExternMsaCtcmsa")
private external fun wasiCorearchMipsMsaExternMsaCtcmsa(imm5: Int, a: Int): Unit

public actual fun externMsaCtcmsa(imm5: Int, a: Int): Unit =
    wasiCorearchMipsMsaExternMsaCtcmsa(imm5, a)

public actual fun externMsaDivSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivSB")

public actual fun externMsaDivSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivSH")

public actual fun externMsaDivSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivSW")

public actual fun externMsaDivSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivSD")

public actual fun externMsaDivUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivUB")

public actual fun externMsaDivUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivUH")

public actual fun externMsaDivUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivUW")

public actual fun externMsaDivUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDivUD")

public actual fun externMsaDotpSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpSH")

public actual fun externMsaDotpSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpSW")

public actual fun externMsaDotpSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpSD")

public actual fun externMsaDotpUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpUH")

public actual fun externMsaDotpUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpUW")

public actual fun externMsaDotpUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDotpUD")

public actual fun externMsaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddSH")

public actual fun externMsaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddSW")

public actual fun externMsaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddSD")

public actual fun externMsaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddUH")

public actual fun externMsaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddUW")

public actual fun externMsaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpaddUD")

public actual fun externMsaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubSH")

public actual fun externMsaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubSW")

public actual fun externMsaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubSD")

public actual fun externMsaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubUH")

public actual fun externMsaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubUW")

public actual fun externMsaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaDpsubUD")

public actual fun externMsaFaddW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFaddW")

public actual fun externMsaFaddD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFaddD")

public actual fun externMsaFcafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcafW")

public actual fun externMsaFcafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcafD")

public actual fun externMsaFceqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFceqW")

public actual fun externMsaFceqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFceqD")

public actual fun externMsaFclassW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFclassW")

public actual fun externMsaFclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFclassD")

public actual fun externMsaFcleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcleW")

public actual fun externMsaFcleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcleD")

public actual fun externMsaFcltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcltW")

public actual fun externMsaFcltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcltD")

public actual fun externMsaFcneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcneW")

public actual fun externMsaFcneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcneD")

public actual fun externMsaFcorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcorW")

public actual fun externMsaFcorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcorD")

public actual fun externMsaFcueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcueqW")

public actual fun externMsaFcueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcueqD")

public actual fun externMsaFculeW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFculeW")

public actual fun externMsaFculeD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFculeD")

public actual fun externMsaFcultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcultW")

public actual fun externMsaFcultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcultD")

public actual fun externMsaFcunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcunW")

public actual fun externMsaFcunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcunD")

public actual fun externMsaFcuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcuneW")

public actual fun externMsaFcuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFcuneD")

public actual fun externMsaFdivW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFdivW")

public actual fun externMsaFdivD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFdivD")

public actual fun externMsaFexdoW(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFexdoW")

public actual fun externMsaFexp2W(a: V4f32, b: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFexp2W")

public actual fun externMsaFexp2D(a: V2f64, b: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFexp2D")

public actual fun externMsaFexuplD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFexuplD")

public actual fun externMsaFexuprD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFexuprD")

public actual fun externMsaFfintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfintSW")

public actual fun externMsaFfintSD(a: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfintSD")

public actual fun externMsaFfintUW(a: V4u32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfintUW")

public actual fun externMsaFfintUD(a: V2u64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfintUD")

public actual fun externMsaFfqlW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfqlW")

public actual fun externMsaFfqlD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfqlD")

public actual fun externMsaFfqrW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfqrW")

public actual fun externMsaFfqrD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFfqrD")

public actual fun externMsaFillB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFillB")

public actual fun externMsaFillH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFillH")

public actual fun externMsaFillW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFillW")

public actual fun externMsaFillD(a: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFillD")

public actual fun externMsaFlog2W(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFlog2W")

public actual fun externMsaFlog2D(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFlog2D")

public actual fun externMsaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaddW")

public actual fun externMsaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaddD")

public actual fun externMsaFmaxW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaxW")

public actual fun externMsaFmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaxD")

public actual fun externMsaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaxAW")

public actual fun externMsaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmaxAD")

public actual fun externMsaFminW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFminW")

public actual fun externMsaFminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFminD")

public actual fun externMsaFminAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFminAW")

public actual fun externMsaFminAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFminAD")

public actual fun externMsaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmsubW")

public actual fun externMsaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmsubD")

public actual fun externMsaFmulW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmulW")

public actual fun externMsaFmulD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFmulD")

public actual fun externMsaFrintW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrintW")

public actual fun externMsaFrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrintD")

public actual fun externMsaFrcpW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrcpW")

public actual fun externMsaFrcpD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrcpD")

public actual fun externMsaFrsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrsqrtW")

public actual fun externMsaFrsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFrsqrtD")

public actual fun externMsaFsafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsafW")

public actual fun externMsaFsafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsafD")

public actual fun externMsaFseqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFseqW")

public actual fun externMsaFseqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFseqD")

public actual fun externMsaFsleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsleW")

public actual fun externMsaFsleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsleD")

public actual fun externMsaFsltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsltW")

public actual fun externMsaFsltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsltD")

public actual fun externMsaFsneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsneW")

public actual fun externMsaFsneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsneD")

public actual fun externMsaFsorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsorW")

public actual fun externMsaFsorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsorD")

public actual fun externMsaFsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsqrtW")

public actual fun externMsaFsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsqrtD")

public actual fun externMsaFsubW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsubW")

public actual fun externMsaFsubD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsubD")

public actual fun externMsaFsueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsueqW")

public actual fun externMsaFsueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsueqD")

public actual fun externMsaFsuleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsuleW")

public actual fun externMsaFsuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsuleD")

public actual fun externMsaFsultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsultW")

public actual fun externMsaFsultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsultD")

public actual fun externMsaFsunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsunW")

public actual fun externMsaFsunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsunD")

public actual fun externMsaFsuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsuneW")

public actual fun externMsaFsuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFsuneD")

public actual fun externMsaFtintSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtintSW")

public actual fun externMsaFtintSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtintSD")

public actual fun externMsaFtintUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtintUW")

public actual fun externMsaFtintUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtintUD")

public actual fun externMsaFtqH(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtqH")

public actual fun externMsaFtqW(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtqW")

public actual fun externMsaFtruncSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtruncSW")

public actual fun externMsaFtruncSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtruncSD")

public actual fun externMsaFtruncUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtruncUW")

public actual fun externMsaFtruncUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaFtruncUD")

public actual fun externMsaHaddSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddSH")

public actual fun externMsaHaddSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddSW")

public actual fun externMsaHaddSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddSD")

public actual fun externMsaHaddUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddUH")

public actual fun externMsaHaddUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddUW")

public actual fun externMsaHaddUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHaddUD")

public actual fun externMsaHsubSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubSH")

public actual fun externMsaHsubSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubSW")

public actual fun externMsaHsubSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubSD")

public actual fun externMsaHsubUH(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubUH")

public actual fun externMsaHsubUW(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubUW")

public actual fun externMsaHsubUD(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaHsubUD")

public actual fun externMsaIlvevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvevB")

public actual fun externMsaIlvevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvevH")

public actual fun externMsaIlvevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvevW")

public actual fun externMsaIlvevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvevD")

public actual fun externMsaIlvlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvlB")

public actual fun externMsaIlvlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvlH")

public actual fun externMsaIlvlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvlW")

public actual fun externMsaIlvlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvlD")

public actual fun externMsaIlvodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvodB")

public actual fun externMsaIlvodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvodH")

public actual fun externMsaIlvodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvodW")

public actual fun externMsaIlvodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvodD")

public actual fun externMsaIlvrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvrB")

public actual fun externMsaIlvrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvrH")

public actual fun externMsaIlvrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvrW")

public actual fun externMsaIlvrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaIlvrD")

public actual fun externMsaInsertB(a: V16i8, b: Int, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsertB")

public actual fun externMsaInsertH(a: V8i16, b: Int, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsertH")

public actual fun externMsaInsertW(a: V4i32, b: Int, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsertW")

public actual fun externMsaInsertD(a: V2i64, b: Int, c: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsertD")

public actual fun externMsaInsveB(a: V16i8, b: Int, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsveB")

public actual fun externMsaInsveH(a: V8i16, b: Int, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsveH")

public actual fun externMsaInsveW(a: V4i32, b: Int, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsveW")

public actual fun externMsaInsveD(a: V2i64, b: Int, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaInsveD")

public actual fun externMsaLdB(memAddr: MutPtr, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdB")

public actual fun externMsaLdH(memAddr: MutPtr, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdH")

public actual fun externMsaLdW(memAddr: MutPtr, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdW")

public actual fun externMsaLdD(memAddr: MutPtr, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdD")

public actual fun externMsaLdiB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdiB")

public actual fun externMsaLdiH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdiH")

public actual fun externMsaLdiW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdiW")

public actual fun externMsaLdiD(a: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaLdiD")

public actual fun externMsaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddQH")

public actual fun externMsaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddQW")

public actual fun externMsaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddrQH")

public actual fun externMsaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddrQW")

public actual fun externMsaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddvB")

public actual fun externMsaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddvH")

public actual fun externMsaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddvW")

public actual fun externMsaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaddvD")

public actual fun externMsaMaxAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxAB")

public actual fun externMsaMaxAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxAH")

public actual fun externMsaMaxAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxAW")

public actual fun externMsaMaxAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxAD")

public actual fun externMsaMaxSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxSB")

public actual fun externMsaMaxSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxSH")

public actual fun externMsaMaxSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxSW")

public actual fun externMsaMaxSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxSD")

public actual fun externMsaMaxUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxUB")

public actual fun externMsaMaxUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxUH")

public actual fun externMsaMaxUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxUW")

public actual fun externMsaMaxUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxUD")

public actual fun externMsaMaxiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiSB")

public actual fun externMsaMaxiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiSH")

public actual fun externMsaMaxiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiSW")

public actual fun externMsaMaxiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiSD")

public actual fun externMsaMaxiUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiUB")

public actual fun externMsaMaxiUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiUH")

public actual fun externMsaMaxiUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiUW")

public actual fun externMsaMaxiUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMaxiUD")

public actual fun externMsaMinAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinAB")

public actual fun externMsaMinAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinAH")

public actual fun externMsaMinAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinAW")

public actual fun externMsaMinAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinAD")

public actual fun externMsaMinSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinSB")

public actual fun externMsaMinSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinSH")

public actual fun externMsaMinSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinSW")

public actual fun externMsaMinSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinSD")

public actual fun externMsaMinUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinUB")

public actual fun externMsaMinUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinUH")

public actual fun externMsaMinUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinUW")

public actual fun externMsaMinUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMinUD")

public actual fun externMsaMiniSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniSB")

public actual fun externMsaMiniSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniSH")

public actual fun externMsaMiniSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniSW")

public actual fun externMsaMiniSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniSD")

public actual fun externMsaMiniUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniUB")

public actual fun externMsaMiniUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniUH")

public actual fun externMsaMiniUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniUW")

public actual fun externMsaMiniUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMiniUD")

public actual fun externMsaModSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModSB")

public actual fun externMsaModSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModSH")

public actual fun externMsaModSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModSW")

public actual fun externMsaModSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModSD")

public actual fun externMsaModUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModUB")

public actual fun externMsaModUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModUH")

public actual fun externMsaModUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModUW")

public actual fun externMsaModUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaModUD")

public actual fun externMsaMoveV(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMoveV")

public actual fun externMsaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubQH")

public actual fun externMsaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubQW")

public actual fun externMsaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubrQH")

public actual fun externMsaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubrQW")

public actual fun externMsaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubvB")

public actual fun externMsaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubvH")

public actual fun externMsaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubvW")

public actual fun externMsaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMsubvD")

public actual fun externMsaMulQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulQH")

public actual fun externMsaMulQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulQW")

public actual fun externMsaMulrQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulrQH")

public actual fun externMsaMulrQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulrQW")

public actual fun externMsaMulvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulvB")

public actual fun externMsaMulvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulvH")

public actual fun externMsaMulvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulvW")

public actual fun externMsaMulvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaMulvD")

public actual fun externMsaNlocB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlocB")

public actual fun externMsaNlocH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlocH")

public actual fun externMsaNlocW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlocW")

public actual fun externMsaNlocD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlocD")

public actual fun externMsaNlzcB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlzcB")

public actual fun externMsaNlzcH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlzcH")

public actual fun externMsaNlzcW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlzcW")

public actual fun externMsaNlzcD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNlzcD")

public actual fun externMsaNorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNorV")

public actual fun externMsaNoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaNoriB")

public actual fun externMsaOrV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaOrV")

public actual fun externMsaOriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaOriB")

public actual fun externMsaPckevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckevB")

public actual fun externMsaPckevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckevH")

public actual fun externMsaPckevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckevW")

public actual fun externMsaPckevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckevD")

public actual fun externMsaPckodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckodB")

public actual fun externMsaPckodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckodH")

public actual fun externMsaPckodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckodW")

public actual fun externMsaPckodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPckodD")

public actual fun externMsaPcntB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPcntB")

public actual fun externMsaPcntH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPcntH")

public actual fun externMsaPcntW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPcntW")

public actual fun externMsaPcntD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaPcntD")

public actual fun externMsaSatSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatSB")

public actual fun externMsaSatSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatSH")

public actual fun externMsaSatSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatSW")

public actual fun externMsaSatSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatSD")

public actual fun externMsaSatUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatUB")

public actual fun externMsaSatUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatUH")

public actual fun externMsaSatUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatUW")

public actual fun externMsaSatUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSatUD")

public actual fun externMsaShfB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaShfB")

public actual fun externMsaShfH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaShfH")

public actual fun externMsaShfW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaShfW")

public actual fun externMsaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldB")

public actual fun externMsaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldH")

public actual fun externMsaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldW")

public actual fun externMsaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldD")

public actual fun externMsaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldiB")

public actual fun externMsaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldiH")

public actual fun externMsaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldiW")

public actual fun externMsaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSldiD")

public actual fun externMsaSllB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSllB")

public actual fun externMsaSllH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSllH")

public actual fun externMsaSllW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSllW")

public actual fun externMsaSllD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSllD")

public actual fun externMsaSlliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSlliB")

public actual fun externMsaSlliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSlliH")

public actual fun externMsaSlliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSlliW")

public actual fun externMsaSlliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSlliD")

public actual fun externMsaSplatB(a: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatB")

public actual fun externMsaSplatH(a: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatH")

public actual fun externMsaSplatW(a: V4i32, w: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatW")

public actual fun externMsaSplatD(a: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatD")

public actual fun externMsaSplatiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatiB")

public actual fun externMsaSplatiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatiH")

public actual fun externMsaSplatiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatiW")

public actual fun externMsaSplatiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSplatiD")

public actual fun externMsaSraB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraB")

public actual fun externMsaSraH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraH")

public actual fun externMsaSraW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraW")

public actual fun externMsaSraD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraD")

public actual fun externMsaSraiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraiB")

public actual fun externMsaSraiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraiH")

public actual fun externMsaSraiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraiW")

public actual fun externMsaSraiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSraiD")

public actual fun externMsaSrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrarB")

public actual fun externMsaSrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrarH")

public actual fun externMsaSrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrarW")

public actual fun externMsaSrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrarD")

public actual fun externMsaSrariB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrariB")

public actual fun externMsaSrariH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrariH")

public actual fun externMsaSrariW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrariW")

public actual fun externMsaSrariD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrariD")

public actual fun externMsaSrlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlB")

public actual fun externMsaSrlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlH")

public actual fun externMsaSrlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlW")

public actual fun externMsaSrlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlD")

public actual fun externMsaSrliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrliB")

public actual fun externMsaSrliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrliH")

public actual fun externMsaSrliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrliW")

public actual fun externMsaSrliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrliD")

public actual fun externMsaSrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlrB")

public actual fun externMsaSrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlrH")

public actual fun externMsaSrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlrW")

public actual fun externMsaSrlrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlrD")

public actual fun externMsaSrlriB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlriB")

public actual fun externMsaSrlriH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlriH")

public actual fun externMsaSrlriW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlriW")

public actual fun externMsaSrlriD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSrlriD")

public actual fun externMsaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaStB")
}

public actual fun externMsaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaStH")
}

public actual fun externMsaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaStW")
}

public actual fun externMsaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaStD")
}

public actual fun externMsaSubsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsSB")

public actual fun externMsaSubsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsSH")

public actual fun externMsaSubsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsSW")

public actual fun externMsaSubsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsSD")

public actual fun externMsaSubsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsUB")

public actual fun externMsaSubsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsUH")

public actual fun externMsaSubsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsUW")

public actual fun externMsaSubsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsUD")

public actual fun externMsaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsusUB")

public actual fun externMsaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsusUH")

public actual fun externMsaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsusUW")

public actual fun externMsaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsusUD")

public actual fun externMsaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsuuSB")

public actual fun externMsaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsuuSH")

public actual fun externMsaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsuuSW")

public actual fun externMsaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubsuuSD")

public actual fun externMsaSubvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubvB")

public actual fun externMsaSubvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubvH")

public actual fun externMsaSubvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubvW")

public actual fun externMsaSubvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubvD")

public actual fun externMsaSubviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubviB")

public actual fun externMsaSubviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubviH")

public actual fun externMsaSubviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubviW")

public actual fun externMsaSubviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaSubviD")

public actual fun externMsaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaVshfB")

public actual fun externMsaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaVshfH")

public actual fun externMsaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaVshfW")

public actual fun externMsaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaVshfD")

public actual fun externMsaXorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaXorV")

public actual fun externMsaXoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaExternMsaXoriB")
