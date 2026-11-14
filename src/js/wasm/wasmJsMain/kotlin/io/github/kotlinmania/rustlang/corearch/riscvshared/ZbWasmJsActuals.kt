@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initRiscvsharedZbNapiAddon(): Boolean

private val riscvsharedZbNapiAvailable: Boolean = initRiscvsharedZbNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZbOrcB64(p0)")
private external fun wasmCorearchRiscvsharedZbOrcB64(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchRiscvsharedZbClmul64(p0, p1)")
private external fun wasmCorearchRiscvsharedZbClmul64(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchRiscvsharedZbClmulh64(p0, p1)")
private external fun wasmCorearchRiscvsharedZbClmulh64(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchRiscvsharedZbClmulr64(p0, p1)")
private external fun wasmCorearchRiscvsharedZbClmulr64(p0: String, p1: String): String

public actual fun orcB64(rs1: Long): Long =
    if (riscvsharedZbNapiAvailable) wasmCorearchRiscvsharedZbOrcB64(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAvailable) wasmCorearchRiscvsharedZbClmul64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAvailable) wasmCorearchRiscvsharedZbClmulh64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAvailable) wasmCorearchRiscvsharedZbClmulr64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
