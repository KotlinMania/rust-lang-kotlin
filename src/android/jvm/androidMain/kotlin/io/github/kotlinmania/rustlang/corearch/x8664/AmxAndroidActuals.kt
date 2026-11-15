@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.F16x32
import io.github.kotlinmania.rustlang.simd.F32x16
import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.U16x32
import io.github.kotlinmania.rustlang.x8664.Tile

private object CorearchX8664AmxJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664AmxLdtilecfg(memAddr: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxSttilecfg(memAddr: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTileloadd64(dst: Byte, base: Long, stride: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTileloaddt164(dst: Byte, base: Long, stride: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTilerelease()

    @JvmStatic
    external fun rustlangCorearchX8664AmxTilestored64(dst: Byte, base: Long, stride: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTilezero(dst: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbf16ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbuud(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbusd(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbsud(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbssd(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpfp16ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTcmmimfp16ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTcmmrlfp16ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbf8ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdpbhf8ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdphbf8ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTdphf8ps(dst: Byte, a: Byte, b: Byte)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTileloaddrs64(dst: Byte, base: Long, stride: Long)

    @JvmStatic
    external fun rustlangCorearchX8664AmxTileloaddrst164(dst: Byte, base: Long, stride: Long)
}

public actual fun ldtilecfg(memAddr: ConstPtr) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxLdtilecfg(memAddr.rawAddress)
}

public actual fun sttilecfg(memAddr: MutPtr) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxSttilecfg(memAddr.rawAddress)
}

public actual fun tileloadd64(dst: Byte, base: ConstPtr, stride: ULong) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTileloadd64(dst, base.rawAddress, stride.toLong())
}

public actual fun tileloadd64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    null as Tile

public actual fun tileloaddt164(dst: Byte, base: ConstPtr, stride: ULong) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTileloaddt164(dst, base.rawAddress, stride.toLong())
}

public actual fun tileloaddt164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    null as Tile

public actual fun tilerelease() {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTilerelease()
}

public actual fun tilestored64(dst: Byte, base: MutPtr, stride: ULong) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTilestored64(dst, base.rawAddress, stride.toLong())
}

public actual fun tilestored64Internal(rows: UShort, colsb: UShort, base: MutPtr, stride: ULong, src: Tile) {
}

public actual fun tilezero(dst: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTilezero(dst)
}

public actual fun tilezeroInternal(rows: UShort, colsb: UShort): Tile =
    null as Tile

public actual fun tdpbf16ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbf16ps(dst, a, b)
}

public actual fun tdpbf16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbuud(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbuud(dst, a, b)
}

public actual fun tdpbuudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbusd(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbusd(dst, a, b)
}

public actual fun tdpbusdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbsud(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbsud(dst, a, b)
}

public actual fun tdpbsudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbssd(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbssd(dst, a, b)
}

public actual fun tdpbssdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpfp16ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpfp16ps(dst, a, b)
}

public actual fun tdpfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tcmmimfp16ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTcmmimfp16ps(dst, a, b)
}

public actual fun tcmmimfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tcmmrlfp16ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTcmmrlfp16ps(dst, a, b)
}

public actual fun tcmmrlfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbf8ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbf8ps(dst, a, b)
}

public actual fun tdpbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdpbhf8ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdpbhf8ps(dst, a, b)
}

public actual fun tdpbhf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdphbf8ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdphbf8ps(dst, a, b)
}

public actual fun tdphbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tdphf8ps(dst: Byte, a: Byte, b: Byte) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTdphf8ps(dst, a, b)
}

public actual fun tdphf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    null as Tile

public actual fun tileloaddrs64(dst: Byte, base: ConstPtr, stride: ULong) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTileloaddrs64(dst, base.rawAddress, stride.toLong())
}

public actual fun tileloaddrs64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    null as Tile

public actual fun tileloaddrst164(dst: Byte, base: ConstPtr, stride: ULong) {
    CorearchX8664AmxJniBridge.rustlangCorearchX8664AmxTileloaddrst164(dst, base.rawAddress, stride.toLong())
}

public actual fun tileloaddrst164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    null as Tile

public actual fun tcvtrowd2ps(tile: Byte, row: UInt): F32x16 =
    null as F32x16

public actual fun tcvtrowd2psi(tile: Byte, row: UInt): F32x16 =
    null as F32x16

public actual fun tcvtrowd2psInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F32x16 =
    null as F32x16

public actual fun tcvtrowps2phh(tile: Byte, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2phhi(tile: Byte, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2phhInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2phl(tile: Byte, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2phli(tile: Byte, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2phlInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    null as F16x32

public actual fun tcvtrowps2bf16h(tile: Byte, row: UInt): U16x32 =
    null as U16x32

public actual fun tcvtrowps2bf16hi(tile: Byte, row: UInt): U16x32 =
    null as U16x32

public actual fun tcvtrowps2bf16hInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    null as U16x32

public actual fun tcvtrowps2bf16l(tile: Byte, row: UInt): U16x32 =
    null as U16x32

public actual fun tcvtrowps2bf16li(tile: Byte, row: UInt): U16x32 =
    null as U16x32

public actual fun tcvtrowps2bf16lInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    null as U16x32

public actual fun tilemovrow(tile: Byte, row: UInt): I32x16 =
    null as I32x16

public actual fun tilemovrowi(tile: Byte, row: UInt): I32x16 =
    null as I32x16

public actual fun tilemovrowInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): I32x16 =
    null as I32x16
