@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

@JsFun("() => { if (typeof globalThis.__km_napi_panicunwind !== 'undefined') return globalThis.__km_napi_panicunwind !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_panicunwind = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_panicunwind = null; return false; } try { globalThis.__km_napi_panicunwind = req('@kotlinmania/panicunwind-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['panicunwind_native.node', 'build/node-ffi/panicunwind_native.node', 'build/node-ffi/release/panicunwind_native.node', 'build/node-ffi/debug/panicunwind_native.node', 'src/js/node-panicunwind/build/Release/panicunwind_native.node', 'src/js/node-panicunwind/build/Debug/panicunwind_native.node', 'src/js/node-panicunwind/dist/build/Release/panicunwind_native.node', 'src/js/node-panicunwind/dist/build/Debug/panicunwind_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_panicunwind = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_panicunwind = null; return false; }")
private external fun initDummyNapiAddon(): Boolean

private val dummyNapiAvailable: Boolean = initDummyNapiAddon()

@JsFun("() => globalThis.__km_napi_panicunwind.panicunwindDummyRustAbort()")
private external fun wasmPanicunwindDummyRustAbort()

public actual fun rustAbort(): Nothing {
    if (dummyNapiAvailable) {
        wasmPanicunwindDummyRustAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function rustAbort")
}
