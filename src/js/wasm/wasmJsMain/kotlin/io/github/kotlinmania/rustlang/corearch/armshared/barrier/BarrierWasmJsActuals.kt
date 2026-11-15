@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchArmsharedBarrierNapiAddon(): Boolean

private val corearchArmsharedBarrierNapiAvailable: Boolean = initCorearchArmsharedBarrierNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchArmsharedBarrierDmb(p0)")
private external fun wasmRustlangCorearchArmsharedBarrierDmb(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchArmsharedBarrierDsb(p0)")
private external fun wasmRustlangCorearchArmsharedBarrierDsb(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchArmsharedBarrierIsb(p0)")
private external fun wasmRustlangCorearchArmsharedBarrierIsb(p0: Int)

public actual fun dmb(arg0: Int) {
    if (corearchArmsharedBarrierNapiAvailable) {
        wasmRustlangCorearchArmsharedBarrierDmb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun dsb(arg0: Int) {
    if (corearchArmsharedBarrierNapiAvailable) {
        wasmRustlangCorearchArmsharedBarrierDsb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun isb(arg0: Int) {
    if (corearchArmsharedBarrierNapiAvailable) {
        wasmRustlangCorearchArmsharedBarrierIsb(arg0)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
