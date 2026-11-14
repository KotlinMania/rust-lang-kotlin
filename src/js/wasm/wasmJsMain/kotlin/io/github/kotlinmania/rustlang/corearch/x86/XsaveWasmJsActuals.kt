@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initX86XsaveNapiAddon(): Boolean

private val x86XsaveNapiAvailable: Boolean = initX86XsaveNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXsave(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXsave(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXrstor(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXrstor(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXsetbv(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXsetbv(p0: Int, p1: Int, p2: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchX86XsaveXgetbv(p0)")
private external fun wasmCorearchX86XsaveXgetbv(p0: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXsaveopt(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXsaveopt(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXsavec(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXsavec(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXsaves(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXsaves(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchX86XsaveXrstors(p0, p1, p2)")
private external fun wasmCorearchX86XsaveXrstors(p0: String, p1: Int, p2: Int)

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXsave(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXrstor(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xgetbv(v: UInt): Long =
    if (x86XsaveNapiAvailable) wasmCorearchX86XsaveXgetbv(v.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXsaveopt(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXsavec(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXsaves(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveXrstors(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
