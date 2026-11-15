@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchX86XsaveNapiAddon(): Boolean

private val corearchX86XsaveNapiAvailable: Boolean = initCorearchX86XsaveNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXsave(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXsave(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXrstor(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXrstor(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXsetbv(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXsetbv(p0: Int, p1: Int, p2: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXgetbv(p0)")
private external fun wasmRustlangCorearchX86XsaveXgetbv(p0: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXsaveopt(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXsaveopt(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXsavec(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXsavec(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXsaves(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXsaves(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchX86XsaveXrstors(p0, p1, p2)")
private external fun wasmRustlangCorearchX86XsaveXrstors(p0: String, p1: Int, p2: Int)

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXsave(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXrstor(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xgetbv(v: UInt): Long =
    if (corearchX86XsaveNapiAvailable) wasmRustlangCorearchX86XsaveXgetbv(v.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXsaveopt(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXsavec(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXsaves(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    if (corearchX86XsaveNapiAvailable) {
        wasmRustlangCorearchX86XsaveXrstors(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
