@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_core !== 'undefined') return globalThis.__km_napi_core !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_core = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_core = null; return false; } try { globalThis.__km_napi_core = req('@kotlinmania/core-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['core_native.node', 'build/node-ffi/core_native.node', 'build/node-ffi/release/core_native.node', 'build/node-ffi/debug/core_native.node', 'src/js/node-core/build/Release/core_native.node', 'src/js/node-core/build/Debug/core_native.node', 'src/js/node-core/dist/build/Release/core_native.node', 'src/js/node-core/dist/build/Debug/core_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_core = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_core = null; return false; }")
private external fun initFfiNapiAddon(): Boolean

private val ffiNapiAvailable: Boolean = initFfiNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_core.coreFfiMemcpy(p0, p1, p2)")
private external fun wasmCoreFfiMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_core.coreFfiMemmove(p0, p1, p2)")
private external fun wasmCoreFfiMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_core.coreFfiMemset(p0, p1, p2)")
private external fun wasmCoreFfiMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_core.coreFfiMemcmp(p0, p1, p2)")
private external fun wasmCoreFfiMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_core.coreFfiBcmp(p0, p1, p2)")
private external fun wasmCoreFfiBcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_core.coreFfiStrlen(p0)")
private external fun wasmCoreFfiStrlen(p0: String): String

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    if (ffiNapiAvailable) MutPtr(wasmCoreFfiMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    if (ffiNapiAvailable) MutPtr(wasmCoreFfiMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    if (ffiNapiAvailable) MutPtr(wasmCoreFfiMemset(s.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    if (ffiNapiAvailable) wasmCoreFfiMemcmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    if (ffiNapiAvailable) wasmCoreFfiBcmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(s: ConstPtr): ULong =
    if (ffiNapiAvailable) wasmCoreFfiStrlen(s.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
