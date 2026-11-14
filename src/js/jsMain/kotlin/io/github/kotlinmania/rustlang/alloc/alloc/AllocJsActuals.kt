@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.alloc

private val allocNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_alloc !== 'undefined') return globalThis.__km_napi_alloc; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/alloc-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_alloc = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['alloc_native.node', 'build/node-ffi/alloc_native.node', 'build/node-ffi/release/alloc_native.node', 'build/node-ffi/debug/alloc_native.node', 'src/js/node-alloc/build/Release/alloc_native.node', 'src/js/node-alloc/build/Debug/alloc_native.node', 'src/js/node-alloc/dist/build/Release/alloc_native.node', 'src/js/node-alloc/dist/build/Debug/alloc_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_alloc = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_alloc = null; return null; })()")

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    if (allocNapiAddon != null) {
        allocNapiAddon.allocRustAllocErrorHandler(size.toString(), align.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function rustAllocErrorHandler")
}
