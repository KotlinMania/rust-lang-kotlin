@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchAarch64MteNapiAddon(): Boolean

private val corearchAarch64MteNapiAvailable: Boolean = initCorearchAarch64MteNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteIrg(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteIrg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteGmi(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteGmi(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteLdg(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteLdg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteStg(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteStg(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteAddg(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteAddg(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchAarch64MteSubp(p0, p1)")
private external fun wasmRustlangCorearchAarch64MteSubp(p0: String, p1: String): String

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    if (corearchAarch64MteNapiAvailable) ConstPtr(wasmRustlangCorearchAarch64MteIrg(ptr.rawAddress.toString(), exclude.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    if (corearchAarch64MteNapiAvailable) wasmRustlangCorearchAarch64MteGmi(ptr.rawAddress.toString(), exclude.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    if (corearchAarch64MteNapiAvailable) ConstPtr(wasmRustlangCorearchAarch64MteLdg(ptr.rawAddress.toString(), tagPtr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    if (corearchAarch64MteNapiAvailable) {
        wasmRustlangCorearchAarch64MteStg(taggedPtr.rawAddress.toString(), addrToTag.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    if (corearchAarch64MteNapiAvailable) ConstPtr(wasmRustlangCorearchAarch64MteAddg(ptr.rawAddress.toString(), value.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    if (corearchAarch64MteNapiAvailable) wasmRustlangCorearchAarch64MteSubp(ptrA.rawAddress.toString(), ptrB.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
