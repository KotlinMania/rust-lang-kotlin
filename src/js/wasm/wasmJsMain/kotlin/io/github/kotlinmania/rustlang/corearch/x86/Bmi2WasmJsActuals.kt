@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initX86Bmi2NapiAddon(): Boolean

private val x86Bmi2NapiAvailable: Boolean = initX86Bmi2NapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchX86Bmi2X86Bmi2Bzhi32(p0, p1)")
private external fun wasmCorearchX86Bmi2X86Bmi2Bzhi32(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchX86Bmi2X86Bmi2Pdep32(p0, p1)")
private external fun wasmCorearchX86Bmi2X86Bmi2Pdep32(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchX86Bmi2X86Bmi2Pext32(p0, p1)")
private external fun wasmCorearchX86Bmi2X86Bmi2Pext32(p0: Int, p1: Int): Int

public actual fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt =
    if (x86Bmi2NapiAvailable) wasmCorearchX86Bmi2X86Bmi2Bzhi32(x.toInt(), y.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt =
    if (x86Bmi2NapiAvailable) wasmCorearchX86Bmi2X86Bmi2Pdep32(x.toInt(), y.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Bmi2Pext32(x: UInt, y: UInt): UInt =
    if (x86Bmi2NapiAvailable) wasmCorearchX86Bmi2X86Bmi2Pext32(x.toInt(), y.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")
