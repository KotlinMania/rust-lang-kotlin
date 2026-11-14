@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initArmsharedBarrierNapiAddon(): Boolean

private val armsharedBarrierNapiAvailable: Boolean = initArmsharedBarrierNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchArmsharedBarrierDmb(p0)")
private external fun wasmCorearchArmsharedBarrierDmb(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchArmsharedBarrierDsb(p0)")
private external fun wasmCorearchArmsharedBarrierDsb(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchArmsharedBarrierIsb(p0)")
private external fun wasmCorearchArmsharedBarrierIsb(p0: Int)

public actual fun dmb(arg0: Int) {
    if (armsharedBarrierNapiAvailable) {
        wasmCorearchArmsharedBarrierDmb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun dsb(arg0: Int) {
    if (armsharedBarrierNapiAvailable) {
        wasmCorearchArmsharedBarrierDsb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun isb(arg0: Int) {
    if (armsharedBarrierNapiAvailable) {
        wasmCorearchArmsharedBarrierIsb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
