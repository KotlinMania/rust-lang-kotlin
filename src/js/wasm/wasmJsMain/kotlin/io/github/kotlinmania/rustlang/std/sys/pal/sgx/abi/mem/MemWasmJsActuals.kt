@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysPalSgxAbiMemNapiAddon(): Boolean

private val sysPalSgxAbiMemNapiAvailable: Boolean = initSysPalSgxAbiMemNapiAddon()

@JsFun("() => globalThis.__km_napi_std.stdSysPalSgxAbiMemGetENCLAVESIZE()")
private external fun wasmStdSysPalSgxAbiMemGetENCLAVESIZE(): String

@JsFun("() => globalThis.__km_napi_std.stdSysPalSgxAbiMemGetHEAPBASE()")
private external fun wasmStdSysPalSgxAbiMemGetHEAPBASE(): String

@JsFun("() => globalThis.__km_napi_std.stdSysPalSgxAbiMemGetHEAPSIZE()")
private external fun wasmStdSysPalSgxAbiMemGetHEAPSIZE(): String

public actual val ENCLAVE_SIZE: ULong
    get() = if (sysPalSgxAbiMemNapiAvailable) wasmStdSysPalSgxAbiMemGetENCLAVESIZE().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val HEAP_BASE: ULong
    get() = if (sysPalSgxAbiMemNapiAvailable) wasmStdSysPalSgxAbiMemGetHEAPBASE().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val HEAP_SIZE: ULong
    get() = if (sysPalSgxAbiMemNapiAvailable) wasmStdSysPalSgxAbiMemGetHEAPSIZE().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
