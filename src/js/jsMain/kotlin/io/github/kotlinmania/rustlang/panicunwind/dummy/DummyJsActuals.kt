@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

private val dummyNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_panicunwind !== 'undefined') return globalThis.__km_napi_panicunwind; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/panicunwind-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_panicunwind = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['panicunwind_native.node', 'build/node-ffi/panicunwind_native.node', 'build/node-ffi/release/panicunwind_native.node', 'build/node-ffi/debug/panicunwind_native.node', 'src/js/node-panicunwind/build/Release/panicunwind_native.node', 'src/js/node-panicunwind/build/Debug/panicunwind_native.node', 'src/js/node-panicunwind/dist/build/Release/panicunwind_native.node', 'src/js/node-panicunwind/dist/build/Debug/panicunwind_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_panicunwind = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_panicunwind = null; return null; })()")

public actual fun rustAbort(): Nothing {
    if (dummyNapiAddon != null) {
        dummyNapiAddon.panicunwindDummyRustAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function rustAbort")
}
