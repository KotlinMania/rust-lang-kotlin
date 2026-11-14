@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initLoongarch32NapiAddon(): Boolean

private val loongarch32NapiAvailable: Boolean = initLoongarch32NapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchLoongarch32Cacop(p0, p1, p2)")
private external fun wasmCorearchLoongarch32Cacop(p0: Int, p1: Int, p2: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchLoongarch32Csrrd(p0)")
private external fun wasmCorearchLoongarch32Csrrd(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchLoongarch32Csrwr(p0, p1)")
private external fun wasmCorearchLoongarch32Csrwr(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchLoongarch32Csrxchg(p0, p1, p2)")
private external fun wasmCorearchLoongarch32Csrxchg(p0: Int, p1: Int, p2: Int): Int

public actual fun cacop(a: Int, b: Int, c: Int) {
    if (loongarch32NapiAvailable) {
        wasmCorearchLoongarch32Cacop(a, b, c)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun csrrd(a: Int): Int =
    if (loongarch32NapiAvailable) wasmCorearchLoongarch32Csrrd(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrwr(a: Int, b: Int): Int =
    if (loongarch32NapiAvailable) wasmCorearchLoongarch32Csrwr(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    if (loongarch32NapiAvailable) wasmCorearchLoongarch32Csrxchg(a, b, c) else throw UnsupportedOperationException("Native FFI addon is not available")
