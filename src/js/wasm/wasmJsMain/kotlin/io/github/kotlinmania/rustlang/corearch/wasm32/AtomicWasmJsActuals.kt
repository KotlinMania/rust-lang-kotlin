@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchWasm32AtomicNapiAddon(): Boolean

private val corearchWasm32AtomicNapiAvailable: Boolean = initCorearchWasm32AtomicNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchWasm32AtomicLlvmAtomicWaitI32(p0, p1, p2)")
private external fun wasmRustlangCorearchWasm32AtomicLlvmAtomicWaitI32(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchWasm32AtomicLlvmAtomicWaitI64(p0, p1, p2)")
private external fun wasmRustlangCorearchWasm32AtomicLlvmAtomicWaitI64(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchWasm32AtomicLlvmAtomicNotify(p0, p1)")
private external fun wasmRustlangCorearchWasm32AtomicLlvmAtomicNotify(p0: String, p1: Int): Int

public actual fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int =
    if (corearchWasm32AtomicNapiAvailable) wasmRustlangCorearchWasm32AtomicLlvmAtomicWaitI32(ptr.rawAddress.toString(), exp, timeout.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int =
    if (corearchWasm32AtomicNapiAvailable) wasmRustlangCorearchWasm32AtomicLlvmAtomicWaitI64(ptr.rawAddress.toString(), exp.toString(), timeout.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int =
    if (corearchWasm32AtomicNapiAvailable) wasmRustlangCorearchWasm32AtomicLlvmAtomicNotify(ptr.rawAddress.toString(), cnt) else throw UnsupportedOperationException("Native FFI addon is not available")
