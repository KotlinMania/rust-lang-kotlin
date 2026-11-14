@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.alloc.alloc

@JsFun("() => { if (typeof globalThis.__km_napi_alloc !== 'undefined') return globalThis.__km_napi_alloc !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_alloc = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_alloc = null; return false; } try { globalThis.__km_napi_alloc = req('@kotlinmania/alloc-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['alloc_native.node', 'build/node-ffi/alloc_native.node', 'build/node-ffi/release/alloc_native.node', 'build/node-ffi/debug/alloc_native.node', 'src/js/node-alloc/build/Release/alloc_native.node', 'src/js/node-alloc/build/Debug/alloc_native.node', 'src/js/node-alloc/dist/build/Release/alloc_native.node', 'src/js/node-alloc/dist/build/Debug/alloc_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_alloc = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_alloc = null; return false; }")
private external fun initAllocNapiAddon(): Boolean

private val allocNapiAvailable: Boolean = initAllocNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_alloc.allocRustAllocErrorHandler(p0, p1)")
private external fun wasmAllocRustAllocErrorHandler(p0: String, p1: String)

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    if (allocNapiAvailable) {
        wasmAllocRustAllocErrorHandler(size.toString(), align.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function rustAllocErrorHandler")
}
