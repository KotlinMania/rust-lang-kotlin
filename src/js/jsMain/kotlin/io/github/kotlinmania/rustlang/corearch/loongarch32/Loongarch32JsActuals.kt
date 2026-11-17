@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

private val loongarch32NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun externCacop(a: Int, b: Int, c: Int) {
    if (loongarch32NapiAddon != null) {
        loongarch32NapiAddon.corearchLoongarch32ExternCacop(a, b, c)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externCsrrd(a: Int): Int =
    if (loongarch32NapiAddon != null) (loongarch32NapiAddon.corearchLoongarch32ExternCsrrd(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCsrwr(a: Int, b: Int): Int =
    if (loongarch32NapiAddon != null) (loongarch32NapiAddon.corearchLoongarch32ExternCsrwr(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCsrxchg(a: Int, b: Int, c: Int): Int =
    if (loongarch32NapiAddon != null) (loongarch32NapiAddon.corearchLoongarch32ExternCsrxchg(a, b, c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
