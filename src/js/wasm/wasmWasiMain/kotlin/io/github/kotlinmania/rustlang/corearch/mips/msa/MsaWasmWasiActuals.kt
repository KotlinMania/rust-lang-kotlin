@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun msaAddAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddAB")

public actual fun msaAddAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddAH")

public actual fun msaAddAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddAW")

public actual fun msaAddAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddAD")

public actual fun msaAddsAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsAB")

public actual fun msaAddsAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsAH")

public actual fun msaAddsAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsAW")

public actual fun msaAddsAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsAD")

public actual fun msaAddsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsSB")

public actual fun msaAddsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsSH")

public actual fun msaAddsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsSW")

public actual fun msaAddsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsSD")

public actual fun msaAddsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsUB")

public actual fun msaAddsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsUH")

public actual fun msaAddsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsUW")

public actual fun msaAddsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddsUD")

public actual fun msaAddvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddvB")

public actual fun msaAddvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddvH")

public actual fun msaAddvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddvW")

public actual fun msaAddvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddvD")

public actual fun msaAddviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddviB")

public actual fun msaAddviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddviH")

public actual fun msaAddviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddviW")

public actual fun msaAddviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAddviD")

public actual fun msaAndV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAndV")

public actual fun msaAndiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAndiB")

public actual fun msaAsubSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubSB")

public actual fun msaAsubSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubSH")

public actual fun msaAsubSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubSW")

public actual fun msaAsubSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubSD")

public actual fun msaAsubUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubUB")

public actual fun msaAsubUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubUH")

public actual fun msaAsubUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubUW")

public actual fun msaAsubUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAsubUD")

public actual fun msaAveSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveSB")

public actual fun msaAveSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveSH")

public actual fun msaAveSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveSW")

public actual fun msaAveSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveSD")

public actual fun msaAveUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveUB")

public actual fun msaAveUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveUH")

public actual fun msaAveUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveUW")

public actual fun msaAveUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAveUD")

public actual fun msaAverSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverSB")

public actual fun msaAverSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverSH")

public actual fun msaAverSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverSW")

public actual fun msaAverSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverSD")

public actual fun msaAverUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverUB")

public actual fun msaAverUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverUH")

public actual fun msaAverUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverUW")

public actual fun msaAverUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaAverUD")

public actual fun msaBclrB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclrB")

public actual fun msaBclrH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclrH")

public actual fun msaBclrW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclrW")

public actual fun msaBclrD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclrD")

public actual fun msaBclriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclriB")

public actual fun msaBclriH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclriH")

public actual fun msaBclriW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclriW")

public actual fun msaBclriD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBclriD")

public actual fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinslB")

public actual fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinslH")

public actual fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinslW")

public actual fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinslD")

public actual fun msaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsliB")

public actual fun msaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsliH")

public actual fun msaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsliW")

public actual fun msaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsliD")

public actual fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsrB")

public actual fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsrH")

public actual fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsrW")

public actual fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsrD")

public actual fun msaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsriB")

public actual fun msaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsriH")

public actual fun msaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsriW")

public actual fun msaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBinsriD")

public actual fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBmnzV")

public actual fun msaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBmnziB")

public actual fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBmzV")

public actual fun msaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBmziB")

public actual fun msaBnegB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegB")

public actual fun msaBnegH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegH")

public actual fun msaBnegW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegW")

public actual fun msaBnegD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegD")

public actual fun msaBnegiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegiB")

public actual fun msaBnegiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegiH")

public actual fun msaBnegiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegiW")

public actual fun msaBnegiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnegiD")

public actual fun msaBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnzB")

public actual fun msaBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnzH")

public actual fun msaBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnzW")

public actual fun msaBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnzD")

public actual fun msaBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBnzV")

public actual fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBselV")

public actual fun msaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBseliB")

public actual fun msaBsetB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetB")

public actual fun msaBsetH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetH")

public actual fun msaBsetW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetW")

public actual fun msaBsetD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetD")

public actual fun msaBsetiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetiB")

public actual fun msaBsetiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetiH")

public actual fun msaBsetiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetiW")

public actual fun msaBsetiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBsetiD")

public actual fun msaBzB(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBzB")

public actual fun msaBzH(a: V8u16): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBzH")

public actual fun msaBzW(a: V4u32): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBzW")

public actual fun msaBzD(a: V2u64): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBzD")

public actual fun msaBzV(a: V16u8): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaBzV")

public actual fun msaCeqB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqB")

public actual fun msaCeqH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqH")

public actual fun msaCeqW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqW")

public actual fun msaCeqD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqD")

public actual fun msaCeqiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqiB")

public actual fun msaCeqiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqiH")

public actual fun msaCeqiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqiW")

public actual fun msaCeqiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCeqiD")

@kotlin.wasm.WasmImport("kmcore", "corearchMipsMsaMsaCfcmsa")
private external fun wasiCorearchMipsMsaMsaCfcmsa(a: Int): Int

public actual fun msaCfcmsa(a: Int): Int =
    wasiCorearchMipsMsaMsaCfcmsa(a).toInt()

public actual fun msaCleSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleSB")

public actual fun msaCleSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleSH")

public actual fun msaCleSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleSW")

public actual fun msaCleSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleSD")

public actual fun msaCleUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleUB")

public actual fun msaCleUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleUH")

public actual fun msaCleUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleUW")

public actual fun msaCleUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleUD")

public actual fun msaCleiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiSB")

public actual fun msaCleiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiSH")

public actual fun msaCleiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiSW")

public actual fun msaCleiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiSD")

public actual fun msaCleiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiUB")

public actual fun msaCleiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiUH")

public actual fun msaCleiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiUW")

public actual fun msaCleiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCleiUD")

public actual fun msaCltSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltSB")

public actual fun msaCltSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltSH")

public actual fun msaCltSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltSW")

public actual fun msaCltSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltSD")

public actual fun msaCltUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltUB")

public actual fun msaCltUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltUH")

public actual fun msaCltUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltUW")

public actual fun msaCltUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltUD")

public actual fun msaCltiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiSB")

public actual fun msaCltiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiSH")

public actual fun msaCltiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiSW")

public actual fun msaCltiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiSD")

public actual fun msaCltiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiUB")

public actual fun msaCltiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiUH")

public actual fun msaCltiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiUW")

public actual fun msaCltiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCltiUD")

public actual fun msaCopySB(a: V16i8, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopySB")

public actual fun msaCopySH(a: V8i16, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopySH")

public actual fun msaCopySW(a: V4i32, b: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopySW")

public actual fun msaCopySD(a: V2i64, b: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopySD")

public actual fun msaCopyUB(a: V16i8, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopyUB")

public actual fun msaCopyUH(a: V8i16, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopyUH")

public actual fun msaCopyUW(a: V4i32, b: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopyUW")

public actual fun msaCopyUD(a: V2i64, b: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaCopyUD")

@kotlin.wasm.WasmImport("kmcore", "corearchMipsMsaMsaCtcmsa")
private external fun wasiCorearchMipsMsaMsaCtcmsa(imm5: Int, a: Int): Unit

public actual fun msaCtcmsa(imm5: Int, a: Int): Unit =
    wasiCorearchMipsMsaMsaCtcmsa(imm5, a)

public actual fun msaDivSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivSB")

public actual fun msaDivSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivSH")

public actual fun msaDivSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivSW")

public actual fun msaDivSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivSD")

public actual fun msaDivUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivUB")

public actual fun msaDivUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivUH")

public actual fun msaDivUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivUW")

public actual fun msaDivUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDivUD")

public actual fun msaDotpSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpSH")

public actual fun msaDotpSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpSW")

public actual fun msaDotpSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpSD")

public actual fun msaDotpUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpUH")

public actual fun msaDotpUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpUW")

public actual fun msaDotpUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDotpUD")

public actual fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddSH")

public actual fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddSW")

public actual fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddSD")

public actual fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddUH")

public actual fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddUW")

public actual fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpaddUD")

public actual fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubSH")

public actual fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubSW")

public actual fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubSD")

public actual fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubUH")

public actual fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubUW")

public actual fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaDpsubUD")

public actual fun msaFaddW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFaddW")

public actual fun msaFaddD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFaddD")

public actual fun msaFcafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcafW")

public actual fun msaFcafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcafD")

public actual fun msaFceqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFceqW")

public actual fun msaFceqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFceqD")

public actual fun msaFclassW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFclassW")

public actual fun msaFclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFclassD")

public actual fun msaFcleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcleW")

public actual fun msaFcleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcleD")

public actual fun msaFcltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcltW")

public actual fun msaFcltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcltD")

public actual fun msaFcneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcneW")

public actual fun msaFcneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcneD")

public actual fun msaFcorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcorW")

public actual fun msaFcorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcorD")

public actual fun msaFcueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcueqW")

public actual fun msaFcueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcueqD")

public actual fun msaFculeW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFculeW")

public actual fun msaFculeD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFculeD")

public actual fun msaFcultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcultW")

public actual fun msaFcultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcultD")

public actual fun msaFcunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcunW")

public actual fun msaFcunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcunD")

public actual fun msaFcuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcuneW")

public actual fun msaFcuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFcuneD")

public actual fun msaFdivW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFdivW")

public actual fun msaFdivD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFdivD")

public actual fun msaFexdoW(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFexdoW")

public actual fun msaFexp2W(a: V4f32, b: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFexp2W")

public actual fun msaFexp2D(a: V2f64, b: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFexp2D")

public actual fun msaFexuplD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFexuplD")

public actual fun msaFexuprD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFexuprD")

public actual fun msaFfintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfintSW")

public actual fun msaFfintSD(a: V2i64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfintSD")

public actual fun msaFfintUW(a: V4u32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfintUW")

public actual fun msaFfintUD(a: V2u64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfintUD")

public actual fun msaFfqlW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfqlW")

public actual fun msaFfqlD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfqlD")

public actual fun msaFfqrW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfqrW")

public actual fun msaFfqrD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFfqrD")

public actual fun msaFillB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFillB")

public actual fun msaFillH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFillH")

public actual fun msaFillW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFillW")

public actual fun msaFillD(a: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFillD")

public actual fun msaFlog2W(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFlog2W")

public actual fun msaFlog2D(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFlog2D")

public actual fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaddW")

public actual fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaddD")

public actual fun msaFmaxW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaxW")

public actual fun msaFmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaxD")

public actual fun msaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaxAW")

public actual fun msaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmaxAD")

public actual fun msaFminW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFminW")

public actual fun msaFminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFminD")

public actual fun msaFminAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFminAW")

public actual fun msaFminAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFminAD")

public actual fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmsubW")

public actual fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmsubD")

public actual fun msaFmulW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmulW")

public actual fun msaFmulD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFmulD")

public actual fun msaFrintW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrintW")

public actual fun msaFrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrintD")

public actual fun msaFrcpW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrcpW")

public actual fun msaFrcpD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrcpD")

public actual fun msaFrsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrsqrtW")

public actual fun msaFrsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFrsqrtD")

public actual fun msaFsafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsafW")

public actual fun msaFsafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsafD")

public actual fun msaFseqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFseqW")

public actual fun msaFseqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFseqD")

public actual fun msaFsleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsleW")

public actual fun msaFsleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsleD")

public actual fun msaFsltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsltW")

public actual fun msaFsltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsltD")

public actual fun msaFsneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsneW")

public actual fun msaFsneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsneD")

public actual fun msaFsorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsorW")

public actual fun msaFsorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsorD")

public actual fun msaFsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsqrtW")

public actual fun msaFsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsqrtD")

public actual fun msaFsubW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsubW")

public actual fun msaFsubD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsubD")

public actual fun msaFsueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsueqW")

public actual fun msaFsueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsueqD")

public actual fun msaFsuleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsuleW")

public actual fun msaFsuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsuleD")

public actual fun msaFsultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsultW")

public actual fun msaFsultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsultD")

public actual fun msaFsunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsunW")

public actual fun msaFsunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsunD")

public actual fun msaFsuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsuneW")

public actual fun msaFsuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFsuneD")

public actual fun msaFtintSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtintSW")

public actual fun msaFtintSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtintSD")

public actual fun msaFtintUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtintUW")

public actual fun msaFtintUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtintUD")

public actual fun msaFtqH(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtqH")

public actual fun msaFtqW(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtqW")

public actual fun msaFtruncSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtruncSW")

public actual fun msaFtruncSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtruncSD")

public actual fun msaFtruncUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtruncUW")

public actual fun msaFtruncUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaFtruncUD")

public actual fun msaHaddSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddSH")

public actual fun msaHaddSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddSW")

public actual fun msaHaddSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddSD")

public actual fun msaHaddUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddUH")

public actual fun msaHaddUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddUW")

public actual fun msaHaddUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHaddUD")

public actual fun msaHsubSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubSH")

public actual fun msaHsubSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubSW")

public actual fun msaHsubSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubSD")

public actual fun msaHsubUH(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubUH")

public actual fun msaHsubUW(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubUW")

public actual fun msaHsubUD(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaHsubUD")

public actual fun msaIlvevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvevB")

public actual fun msaIlvevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvevH")

public actual fun msaIlvevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvevW")

public actual fun msaIlvevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvevD")

public actual fun msaIlvlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvlB")

public actual fun msaIlvlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvlH")

public actual fun msaIlvlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvlW")

public actual fun msaIlvlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvlD")

public actual fun msaIlvodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvodB")

public actual fun msaIlvodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvodH")

public actual fun msaIlvodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvodW")

public actual fun msaIlvodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvodD")

public actual fun msaIlvrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvrB")

public actual fun msaIlvrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvrH")

public actual fun msaIlvrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvrW")

public actual fun msaIlvrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaIlvrD")

public actual fun msaInsertB(a: V16i8, b: Int, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsertB")

public actual fun msaInsertH(a: V8i16, b: Int, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsertH")

public actual fun msaInsertW(a: V4i32, b: Int, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsertW")

public actual fun msaInsertD(a: V2i64, b: Int, c: Long): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsertD")

public actual fun msaInsveB(a: V16i8, b: Int, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsveB")

public actual fun msaInsveH(a: V8i16, b: Int, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsveH")

public actual fun msaInsveW(a: V4i32, b: Int, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsveW")

public actual fun msaInsveD(a: V2i64, b: Int, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaInsveD")

public actual fun msaLdB(memAddr: MutPtr, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdB")

public actual fun msaLdH(memAddr: MutPtr, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdH")

public actual fun msaLdW(memAddr: MutPtr, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdW")

public actual fun msaLdD(memAddr: MutPtr, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdD")

public actual fun msaLdiB(a: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdiB")

public actual fun msaLdiH(a: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdiH")

public actual fun msaLdiW(a: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdiW")

public actual fun msaLdiD(a: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaLdiD")

public actual fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddQH")

public actual fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddQW")

public actual fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddrQH")

public actual fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddrQW")

public actual fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddvB")

public actual fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddvH")

public actual fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddvW")

public actual fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaddvD")

public actual fun msaMaxAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxAB")

public actual fun msaMaxAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxAH")

public actual fun msaMaxAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxAW")

public actual fun msaMaxAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxAD")

public actual fun msaMaxSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxSB")

public actual fun msaMaxSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxSH")

public actual fun msaMaxSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxSW")

public actual fun msaMaxSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxSD")

public actual fun msaMaxUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxUB")

public actual fun msaMaxUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxUH")

public actual fun msaMaxUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxUW")

public actual fun msaMaxUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxUD")

public actual fun msaMaxiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiSB")

public actual fun msaMaxiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiSH")

public actual fun msaMaxiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiSW")

public actual fun msaMaxiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiSD")

public actual fun msaMaxiUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiUB")

public actual fun msaMaxiUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiUH")

public actual fun msaMaxiUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiUW")

public actual fun msaMaxiUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMaxiUD")

public actual fun msaMinAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinAB")

public actual fun msaMinAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinAH")

public actual fun msaMinAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinAW")

public actual fun msaMinAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinAD")

public actual fun msaMinSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinSB")

public actual fun msaMinSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinSH")

public actual fun msaMinSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinSW")

public actual fun msaMinSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinSD")

public actual fun msaMinUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinUB")

public actual fun msaMinUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinUH")

public actual fun msaMinUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinUW")

public actual fun msaMinUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMinUD")

public actual fun msaMiniSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniSB")

public actual fun msaMiniSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniSH")

public actual fun msaMiniSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniSW")

public actual fun msaMiniSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniSD")

public actual fun msaMiniUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniUB")

public actual fun msaMiniUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniUH")

public actual fun msaMiniUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniUW")

public actual fun msaMiniUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMiniUD")

public actual fun msaModSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModSB")

public actual fun msaModSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModSH")

public actual fun msaModSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModSW")

public actual fun msaModSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModSD")

public actual fun msaModUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModUB")

public actual fun msaModUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModUH")

public actual fun msaModUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModUW")

public actual fun msaModUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaModUD")

public actual fun msaMoveV(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMoveV")

public actual fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubQH")

public actual fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubQW")

public actual fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubrQH")

public actual fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubrQW")

public actual fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubvB")

public actual fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubvH")

public actual fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubvW")

public actual fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMsubvD")

public actual fun msaMulQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulQH")

public actual fun msaMulQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulQW")

public actual fun msaMulrQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulrQH")

public actual fun msaMulrQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulrQW")

public actual fun msaMulvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulvB")

public actual fun msaMulvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulvH")

public actual fun msaMulvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulvW")

public actual fun msaMulvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaMulvD")

public actual fun msaNlocB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlocB")

public actual fun msaNlocH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlocH")

public actual fun msaNlocW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlocW")

public actual fun msaNlocD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlocD")

public actual fun msaNlzcB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlzcB")

public actual fun msaNlzcH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlzcH")

public actual fun msaNlzcW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlzcW")

public actual fun msaNlzcD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNlzcD")

public actual fun msaNorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNorV")

public actual fun msaNoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaNoriB")

public actual fun msaOrV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaOrV")

public actual fun msaOriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaOriB")

public actual fun msaPckevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckevB")

public actual fun msaPckevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckevH")

public actual fun msaPckevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckevW")

public actual fun msaPckevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckevD")

public actual fun msaPckodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckodB")

public actual fun msaPckodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckodH")

public actual fun msaPckodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckodW")

public actual fun msaPckodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPckodD")

public actual fun msaPcntB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPcntB")

public actual fun msaPcntH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPcntH")

public actual fun msaPcntW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPcntW")

public actual fun msaPcntD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaPcntD")

public actual fun msaSatSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatSB")

public actual fun msaSatSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatSH")

public actual fun msaSatSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatSW")

public actual fun msaSatSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatSD")

public actual fun msaSatUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatUB")

public actual fun msaSatUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatUH")

public actual fun msaSatUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatUW")

public actual fun msaSatUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSatUD")

public actual fun msaShfB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaShfB")

public actual fun msaShfH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaShfH")

public actual fun msaShfW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaShfW")

public actual fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldB")

public actual fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldH")

public actual fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldW")

public actual fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldD")

public actual fun msaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldiB")

public actual fun msaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldiH")

public actual fun msaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldiW")

public actual fun msaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSldiD")

public actual fun msaSllB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSllB")

public actual fun msaSllH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSllH")

public actual fun msaSllW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSllW")

public actual fun msaSllD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSllD")

public actual fun msaSlliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSlliB")

public actual fun msaSlliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSlliH")

public actual fun msaSlliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSlliW")

public actual fun msaSlliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSlliD")

public actual fun msaSplatB(a: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatB")

public actual fun msaSplatH(a: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatH")

public actual fun msaSplatW(a: V4i32, w: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatW")

public actual fun msaSplatD(a: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatD")

public actual fun msaSplatiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatiB")

public actual fun msaSplatiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatiH")

public actual fun msaSplatiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatiW")

public actual fun msaSplatiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSplatiD")

public actual fun msaSraB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraB")

public actual fun msaSraH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraH")

public actual fun msaSraW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraW")

public actual fun msaSraD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraD")

public actual fun msaSraiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraiB")

public actual fun msaSraiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraiH")

public actual fun msaSraiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraiW")

public actual fun msaSraiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSraiD")

public actual fun msaSrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrarB")

public actual fun msaSrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrarH")

public actual fun msaSrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrarW")

public actual fun msaSrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrarD")

public actual fun msaSrariB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrariB")

public actual fun msaSrariH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrariH")

public actual fun msaSrariW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrariW")

public actual fun msaSrariD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrariD")

public actual fun msaSrlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlB")

public actual fun msaSrlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlH")

public actual fun msaSrlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlW")

public actual fun msaSrlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlD")

public actual fun msaSrliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrliB")

public actual fun msaSrliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrliH")

public actual fun msaSrliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrliW")

public actual fun msaSrliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrliD")

public actual fun msaSrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlrB")

public actual fun msaSrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlrH")

public actual fun msaSrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlrW")

public actual fun msaSrlrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlrD")

public actual fun msaSrlriB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlriB")

public actual fun msaSrlriH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlriH")

public actual fun msaSrlriW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlriW")

public actual fun msaSrlriD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSrlriD")

public actual fun msaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaStB")
}

public actual fun msaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaStH")
}

public actual fun msaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaStW")
}

public actual fun msaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaStD")
}

public actual fun msaSubsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsSB")

public actual fun msaSubsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsSH")

public actual fun msaSubsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsSW")

public actual fun msaSubsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsSD")

public actual fun msaSubsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsUB")

public actual fun msaSubsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsUH")

public actual fun msaSubsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsUW")

public actual fun msaSubsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsUD")

public actual fun msaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsusUB")

public actual fun msaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsusUH")

public actual fun msaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsusUW")

public actual fun msaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsusUD")

public actual fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsuuSB")

public actual fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsuuSH")

public actual fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsuuSW")

public actual fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubsuuSD")

public actual fun msaSubvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubvB")

public actual fun msaSubvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubvH")

public actual fun msaSubvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubvW")

public actual fun msaSubvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubvD")

public actual fun msaSubviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubviB")

public actual fun msaSubviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubviH")

public actual fun msaSubviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubviW")

public actual fun msaSubviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaSubviD")

public actual fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaVshfB")

public actual fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaVshfH")

public actual fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaVshfW")

public actual fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaVshfD")

public actual fun msaXorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaXorV")

public actual fun msaXoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchMipsMsaMsaXoriB")
