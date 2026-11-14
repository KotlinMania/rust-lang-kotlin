@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val x86SseNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun rcpss(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rcpps(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rsqrtss(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rsqrtps(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtss2si(a: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttss2si(a: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sfence() {
    if (x86SseNapiAddon != null) {
        x86SseNapiAddon.corearchX86SseSfence()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun stmxcsr(p: MutPtr) {
    if (x86SseNapiAddon != null) {
        x86SseNapiAddon.corearchX86SseStmxcsr(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ldmxcsr(p: ConstPtr) {
    if (x86SseNapiAddon != null) {
        x86SseNapiAddon.corearchX86SseLdmxcsr(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    if (x86SseNapiAddon != null) {
        x86SseNapiAddon.corearchX86SsePrefetch(p.rawAddress.toString(), rw, loc, ty)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")
