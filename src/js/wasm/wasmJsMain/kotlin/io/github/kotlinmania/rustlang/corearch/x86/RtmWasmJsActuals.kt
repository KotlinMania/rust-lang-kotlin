@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initX86RtmNapiAddon(): Boolean

private val x86RtmNapiAvailable: Boolean = initX86RtmNapiAddon()

@JsFun("() => globalThis.__km_napi_corearch.corearchX86RtmX86Xbegin()")
private external fun wasmCorearchX86RtmX86Xbegin(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchX86RtmX86Xend()")
private external fun wasmCorearchX86RtmX86Xend()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchX86RtmX86Xabort(p0)")
private external fun wasmCorearchX86RtmX86Xabort(p0: Int)

@JsFun("() => globalThis.__km_napi_corearch.corearchX86RtmX86Xtest()")
private external fun wasmCorearchX86RtmX86Xtest(): Int

public actual fun x86Xbegin(): Int =
    if (x86RtmNapiAvailable) wasmCorearchX86RtmX86Xbegin() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Xend() {
    if (x86RtmNapiAvailable) {
        wasmCorearchX86RtmX86Xend()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun x86Xabort(imm8: Byte) {
    if (x86RtmNapiAvailable) {
        wasmCorearchX86RtmX86Xabort(imm8.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun x86Xtest(): Int =
    if (x86RtmNapiAvailable) wasmCorearchX86RtmX86Xtest() else throw UnsupportedOperationException("Native FFI addon is not available")
