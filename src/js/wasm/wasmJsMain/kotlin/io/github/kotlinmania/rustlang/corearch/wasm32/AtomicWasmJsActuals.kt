@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initWasm32AtomicNapiAddon(): Boolean

private val wasm32AtomicNapiAvailable: Boolean = initWasm32AtomicNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchWasm32AtomicLlvmAtomicWaitI32(p0, p1, p2)")
private external fun wasmCorearchWasm32AtomicLlvmAtomicWaitI32(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchWasm32AtomicLlvmAtomicWaitI64(p0, p1, p2)")
private external fun wasmCorearchWasm32AtomicLlvmAtomicWaitI64(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchWasm32AtomicLlvmAtomicNotify(p0, p1)")
private external fun wasmCorearchWasm32AtomicLlvmAtomicNotify(p0: String, p1: Int): Int

public actual fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int =
    if (wasm32AtomicNapiAvailable) wasmCorearchWasm32AtomicLlvmAtomicWaitI32(ptr.rawAddress.toString(), exp, timeout.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int =
    if (wasm32AtomicNapiAvailable) wasmCorearchWasm32AtomicLlvmAtomicWaitI64(ptr.rawAddress.toString(), exp.toString(), timeout.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int =
    if (wasm32AtomicNapiAvailable) wasmCorearchWasm32AtomicLlvmAtomicNotify(ptr.rawAddress.toString(), cnt) else throw UnsupportedOperationException("Native FFI addon is not available")
