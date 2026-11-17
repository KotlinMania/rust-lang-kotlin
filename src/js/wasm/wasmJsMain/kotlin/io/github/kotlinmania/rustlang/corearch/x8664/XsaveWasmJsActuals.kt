@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initX8664XsaveNapiAddon(): Boolean

private val x8664XsaveNapiAvailable: Boolean = initX8664XsaveNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXsave64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXsave64(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXrstor64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXrstor64(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXsaveopt64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXsaveopt64(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXsavec64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXsavec64(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXsaves64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXsaves64(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchX8664XsaveExternXrstors64(p0, p1, p2)")
private external fun wasmCorearchX8664XsaveExternXrstors64(p0: String, p1: Int, p2: Int)

public actual fun externXsave64(p: MutPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXsave64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXrstor64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXsaveopt64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXsavec64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXsaves64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externXrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    if (x8664XsaveNapiAvailable) {
        wasmCorearchX8664XsaveExternXrstors64(p.rawAddress.toString(), hi.toInt(), lo.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
