@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.F16x32
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun ldtilecfg(memAddr: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxLdtilecfg")
}

public actual fun sttilecfg(memAddr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxSttilecfg")
}

public actual fun tileloadd64(dst: Byte, base: ConstPtr, stride: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloadd64")
}

public actual fun tileloadd64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloadd64Internal")

public actual fun tileloaddt164(dst: Byte, base: ConstPtr, stride: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddt164")
}

public actual fun tileloaddt164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddt164Internal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTilerelease")
private external fun wasiCorearchX8664AmxTilerelease(): Unit

public actual fun tilerelease(): Unit =
    wasiCorearchX8664AmxTilerelease()

public actual fun tilestored64(dst: Byte, base: MutPtr, stride: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilestored64")
}

public actual fun tilestored64Internal(rows: UShort, colsb: UShort, base: MutPtr, stride: ULong, src: Tile) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilestored64Internal")
}

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTilezero")
private external fun wasiCorearchX8664AmxTilezero(dst: Int): Unit

public actual fun tilezero(dst: Byte): Unit =
    wasiCorearchX8664AmxTilezero(dst.toInt())

public actual fun tilezeroInternal(rows: UShort, colsb: UShort): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilezeroInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbf16ps")
private external fun wasiCorearchX8664AmxTdpbf16ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbf16ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbf16ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbf16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbf16psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbuud")
private external fun wasiCorearchX8664AmxTdpbuud(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbuud(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbuud(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbuudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbuudInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbusd")
private external fun wasiCorearchX8664AmxTdpbusd(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbusd(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbusd(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbusdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbusdInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbsud")
private external fun wasiCorearchX8664AmxTdpbsud(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbsud(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbsud(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbsudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbsudInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbssd")
private external fun wasiCorearchX8664AmxTdpbssd(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbssd(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbssd(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbssdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbssdInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpfp16ps")
private external fun wasiCorearchX8664AmxTdpfp16ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdpfp16ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpfp16ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpfp16psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTcmmimfp16ps")
private external fun wasiCorearchX8664AmxTcmmimfp16ps(dst: Int, a: Int, b: Int): Unit

public actual fun tcmmimfp16ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTcmmimfp16ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tcmmimfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcmmimfp16psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTcmmrlfp16ps")
private external fun wasiCorearchX8664AmxTcmmrlfp16ps(dst: Int, a: Int, b: Int): Unit

public actual fun tcmmrlfp16ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTcmmrlfp16ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tcmmrlfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcmmrlfp16psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbf8ps")
private external fun wasiCorearchX8664AmxTdpbf8ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbf8ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbf8ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbf8psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdpbhf8ps")
private external fun wasiCorearchX8664AmxTdpbhf8ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdpbhf8ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdpbhf8ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdpbhf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdpbhf8psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdphbf8ps")
private external fun wasiCorearchX8664AmxTdphbf8ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdphbf8ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdphbf8ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdphbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdphbf8psInternal")

@kotlin.wasm.WasmImport("corearch", "corearchX8664AmxTdphf8ps")
private external fun wasiCorearchX8664AmxTdphf8ps(dst: Int, a: Int, b: Int): Unit

public actual fun tdphf8ps(dst: Byte, a: Byte, b: Byte): Unit =
    wasiCorearchX8664AmxTdphf8ps(dst.toInt(), a.toInt(), b.toInt())

public actual fun tdphf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTdphf8psInternal")

public actual fun tileloaddrs64(dst: Byte, base: ConstPtr, stride: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddrs64")
}

public actual fun tileloaddrs64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddrs64Internal")

public actual fun tileloaddrst164(dst: Byte, base: ConstPtr, stride: ULong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddrst164")
}

public actual fun tileloaddrst164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTileloaddrst164Internal")

public actual fun tcvtrowd2ps(tile: Byte, row: UInt): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowd2ps")

public actual fun tcvtrowd2psi(tile: Byte, row: UInt): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowd2psi")

public actual fun tcvtrowd2psInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowd2psInternal")

public actual fun tcvtrowps2phh(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phh")

public actual fun tcvtrowps2phhi(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phhi")

public actual fun tcvtrowps2phhInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phhInternal")

public actual fun tcvtrowps2phl(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phl")

public actual fun tcvtrowps2phli(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phli")

public actual fun tcvtrowps2phlInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2phlInternal")

public actual fun tcvtrowps2bf16h(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16h")

public actual fun tcvtrowps2bf16hi(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16hi")

public actual fun tcvtrowps2bf16hInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16hInternal")

public actual fun tcvtrowps2bf16l(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16l")

public actual fun tcvtrowps2bf16li(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16li")

public actual fun tcvtrowps2bf16lInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTcvtrowps2bf16lInternal")

public actual fun tilemovrow(tile: Byte, row: UInt): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilemovrow")

public actual fun tilemovrowi(tile: Byte, row: UInt): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilemovrowi")

public actual fun tilemovrowInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664AmxTilemovrowInternal")
