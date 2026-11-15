@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x32

private val x86AvxNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun roundpd256(a: M256d, b: Int): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundps256(a: M256, b: Int): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vdpps(a: M256, b: M256, imm8: Byte): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmppd256(a: M256d, b: M256d, imm8: UByte): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpps256(a: M256, b: M256, imm8: UByte): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vzeroall() {
    if (x86AvxNapiAddon != null) {
        x86AvxNapiAddon.corearchX86AvxVzeroall()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun vzeroupper() {
    if (x86AvxNapiAddon != null) {
        x86AvxNapiAddon.corearchX86AvxVzeroupper()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun vpermilps256(a: M256, b: I32x8): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpermilps(a: M128, b: I32x4): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpermilpd256(a: M256d, b: I64x4): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpermilpd(a: M128d, b: I64x2): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vlddqu(memAddr: ConstPtr): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vrcpps(a: M256): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vrsqrtps(a: M256): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptestnzc256(a: I64x4, b: I64x4): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestzpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestnzcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestnzcpd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestzps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestnzcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtestnzcps(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vminpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaxpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")
