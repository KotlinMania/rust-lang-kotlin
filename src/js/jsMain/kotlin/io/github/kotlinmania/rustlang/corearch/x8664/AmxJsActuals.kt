@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.F16x32
import io.github.kotlinmania.rustlang.simd.F32x16
import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.U16x32
import io.github.kotlinmania.rustlang.x8664.Tile

private val corearchX8664AmxNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun ldtilecfg(memAddr: ConstPtr) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxLdtilecfg(memAddr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sttilecfg(memAddr: MutPtr) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxSttilecfg(memAddr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tileloadd64(dst: Byte, base: ConstPtr, stride: ULong) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTileloadd64(dst.toInt(), base.rawAddress.toString(), stride.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tileloadd64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tileloaddt164(dst: Byte, base: ConstPtr, stride: ULong) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTileloaddt164(dst.toInt(), base.rawAddress.toString(), stride.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tileloaddt164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tilerelease() {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTilerelease()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tilestored64(dst: Byte, base: MutPtr, stride: ULong) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTilestored64(dst.toInt(), base.rawAddress.toString(), stride.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tilestored64Internal(rows: UShort, colsb: UShort, base: MutPtr, stride: ULong, src: Tile) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun tilezero(dst: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTilezero(dst.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tilezeroInternal(rows: UShort, colsb: UShort): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbf16ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbf16ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbf16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbuud(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbuud(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbuudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbusd(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbusd(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbusdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbsud(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbsud(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbsudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbssd(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbssd(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbssdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpfp16ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpfp16ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcmmimfp16ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTcmmimfp16ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcmmimfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcmmrlfp16ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTcmmrlfp16ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcmmrlfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbf8ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbf8ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdpbhf8ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdpbhf8ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdpbhf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdphbf8ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdphbf8ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdphbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tdphf8ps(dst: Byte, a: Byte, b: Byte) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTdphf8ps(dst.toInt(), a.toInt(), b.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tdphf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tileloaddrs64(dst: Byte, base: ConstPtr, stride: ULong) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTileloaddrs64(dst.toInt(), base.rawAddress.toString(), stride.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tileloaddrs64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tileloaddrst164(dst: Byte, base: ConstPtr, stride: ULong) {
    if (corearchX8664AmxNapiAddon != null) {
        corearchX8664AmxNapiAddon.rustlangCorearchX8664AmxTileloaddrst164(dst.toInt(), base.rawAddress.toString(), stride.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tileloaddrst164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowd2ps(tile: Byte, row: UInt): F32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowd2psi(tile: Byte, row: UInt): F32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowd2psInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phh(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phhi(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phhInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phl(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phli(tile: Byte, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2phlInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16h(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16hi(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16hInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16l(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16li(tile: Byte, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcvtrowps2bf16lInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tilemovrow(tile: Byte, row: UInt): I32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tilemovrowi(tile: Byte, row: UInt): I32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tilemovrowInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): I32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")
