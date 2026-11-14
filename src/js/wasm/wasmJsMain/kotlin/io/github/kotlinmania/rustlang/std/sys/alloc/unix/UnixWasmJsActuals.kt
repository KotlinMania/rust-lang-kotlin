@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysAllocUnixNapiAddon(): Boolean

private val sysAllocUnixNapiAvailable: Boolean = initSysAllocUnixNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_std.stdSysAllocUnixFree(p0)")
private external fun wasmStdSysAllocUnixFree(p0: String)

public actual fun malloc(size: CSizeT): MutPtr =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(ptr: MutPtr, size: CSizeT): MutPtr =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(ptr: MutPtr) {
    if (sysAllocUnixNapiAvailable) {
        wasmStdSysAllocUnixFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
