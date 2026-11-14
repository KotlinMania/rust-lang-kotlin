@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initAarch64PrefetchNapiAddon(): Boolean

private val aarch64PrefetchNapiAvailable: Boolean = initAarch64PrefetchNapiAddon()

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_corearch.corearchAarch64PrefetchPrefetch(p0, p1, p2, p3)")
private external fun wasmCorearchAarch64PrefetchPrefetch(p0: String, p1: Int, p2: Int, p3: Int)

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    if (aarch64PrefetchNapiAvailable) {
        wasmCorearchAarch64PrefetchPrefetch(p.rawAddress.toString(), rw, loc, ty)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
