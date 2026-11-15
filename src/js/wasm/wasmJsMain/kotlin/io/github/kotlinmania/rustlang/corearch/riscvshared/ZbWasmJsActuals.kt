@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchRiscvsharedZbNapiAddon(): Boolean

private val corearchRiscvsharedZbNapiAvailable: Boolean = initCorearchRiscvsharedZbNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZbOrcB64(p0)")
private external fun wasmRustlangCorearchRiscvsharedZbOrcB64(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZbClmul64(p0, p1)")
private external fun wasmRustlangCorearchRiscvsharedZbClmul64(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZbClmulh64(p0, p1)")
private external fun wasmRustlangCorearchRiscvsharedZbClmulh64(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZbClmulr64(p0, p1)")
private external fun wasmRustlangCorearchRiscvsharedZbClmulr64(p0: String, p1: String): String

public actual fun orcB64(rs1: Long): Long =
    if (corearchRiscvsharedZbNapiAvailable) wasmRustlangCorearchRiscvsharedZbOrcB64(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZbNapiAvailable) wasmRustlangCorearchRiscvsharedZbClmul64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZbNapiAvailable) wasmRustlangCorearchRiscvsharedZbClmulh64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZbNapiAvailable) wasmRustlangCorearchRiscvsharedZbClmulr64(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
