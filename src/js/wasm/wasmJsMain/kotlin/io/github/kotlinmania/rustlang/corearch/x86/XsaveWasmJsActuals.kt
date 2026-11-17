@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initX86XsaveNapiAddon(): Boolean

private val x86XsaveNapiAvailable: Boolean = initX86XsaveNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXsave(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXsave(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXrstor(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXrstor(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXsetbv(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXsetbv(p0: Int, p1: Int, p2: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXgetbv(p0)")
private external fun wasmCorearchX86XsaveExternXgetbv(p0: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXsaveopt(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXsaveopt(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXsavec(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXsavec(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXsaves(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXsaves(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX86XsaveExternXrstors(p0, p1, p2)")
private external fun wasmCorearchX86XsaveExternXrstors(p0: String, p1: Int, p2: Int)

public actual fun externXsave(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXsave(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXrstor(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsetbv(v: UInt, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXsetbv(v.toInt(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXgetbv(v: UInt): Long =
    if (x86XsaveNapiAvailable) wasmCorearchX86XsaveExternXgetbv(v.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXsaveopt(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsavec(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXsavec(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsaves(p: MutPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXsaves(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x86XsaveNapiAvailable) {
        wasmCorearchX86XsaveExternXrstors(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
