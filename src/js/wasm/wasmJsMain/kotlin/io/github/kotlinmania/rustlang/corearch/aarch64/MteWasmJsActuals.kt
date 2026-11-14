@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initAarch64MteNapiAddon(): Boolean

private val aarch64MteNapiAvailable: Boolean = initAarch64MteNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteIrg(p0, p1)")
private external fun wasmCorearchAarch64MteIrg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteGmi(p0, p1)")
private external fun wasmCorearchAarch64MteGmi(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteLdg(p0, p1)")
private external fun wasmCorearchAarch64MteLdg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteStg(p0, p1)")
private external fun wasmCorearchAarch64MteStg(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteAddg(p0, p1)")
private external fun wasmCorearchAarch64MteAddg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchAarch64MteSubp(p0, p1)")
private external fun wasmCorearchAarch64MteSubp(p0: String, p1: String): String

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    if (aarch64MteNapiAvailable) ConstPtr(wasmCorearchAarch64MteIrg(ptr.rawAddress.toString(), exclude.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    if (aarch64MteNapiAvailable) wasmCorearchAarch64MteGmi(ptr.rawAddress.toString(), exclude.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    if (aarch64MteNapiAvailable) ConstPtr(wasmCorearchAarch64MteLdg(ptr.rawAddress.toString(), tagPtr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    if (aarch64MteNapiAvailable) {
        wasmCorearchAarch64MteStg(taggedPtr.rawAddress.toString(), addrToTag.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    if (aarch64MteNapiAvailable) ConstPtr(wasmCorearchAarch64MteAddg(ptr.rawAddress.toString(), value.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    if (aarch64MteNapiAvailable) wasmCorearchAarch64MteSubp(ptrA.rawAddress.toString(), ptrB.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
