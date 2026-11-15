@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCoreFfiNapiAddon(): Boolean

private val coreFfiNapiAvailable: Boolean = initCoreFfiNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreFfiMemcpy(p0, p1, p2)")
private external fun wasmRustlangCoreFfiMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreFfiMemmove(p0, p1, p2)")
private external fun wasmRustlangCoreFfiMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreFfiMemset(p0, p1, p2)")
private external fun wasmRustlangCoreFfiMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreFfiMemcmp(p0, p1, p2)")
private external fun wasmRustlangCoreFfiMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreFfiBcmp(p0, p1, p2)")
private external fun wasmRustlangCoreFfiBcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreFfiStrlen(p0)")
private external fun wasmRustlangCoreFfiStrlen(p0: String): String

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    if (coreFfiNapiAvailable) MutPtr(wasmRustlangCoreFfiMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    if (coreFfiNapiAvailable) MutPtr(wasmRustlangCoreFfiMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    if (coreFfiNapiAvailable) MutPtr(wasmRustlangCoreFfiMemset(s.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    if (coreFfiNapiAvailable) wasmRustlangCoreFfiMemcmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    if (coreFfiNapiAvailable) wasmRustlangCoreFfiBcmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(s: ConstPtr): ULong =
    if (coreFfiNapiAvailable) wasmRustlangCoreFfiStrlen(s.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
