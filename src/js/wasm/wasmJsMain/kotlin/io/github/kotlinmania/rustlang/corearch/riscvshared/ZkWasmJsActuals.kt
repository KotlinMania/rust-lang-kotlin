@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initRiscvsharedZkNapiAddon(): Boolean

private val riscvsharedZkNapiAvailable: Boolean = initRiscvsharedZkNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSm4ed(p0, p1, p2)")
private external fun wasmCorearchRiscvsharedZkSm4ed(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSm4ks(p0, p1, p2)")
private external fun wasmCorearchRiscvsharedZkSm4ks(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSm3p0(p0)")
private external fun wasmCorearchRiscvsharedZkSm3p0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSm3p1(p0)")
private external fun wasmCorearchRiscvsharedZkSm3p1(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSha256sig0(p0)")
private external fun wasmCorearchRiscvsharedZkSha256sig0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSha256sig1(p0)")
private external fun wasmCorearchRiscvsharedZkSha256sig1(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSha256sum0(p0)")
private external fun wasmCorearchRiscvsharedZkSha256sum0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchRiscvsharedZkSha256sum1(p0)")
private external fun wasmCorearchRiscvsharedZkSha256sum1(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchRiscvsharedZkXperm864(p0, p1)")
private external fun wasmCorearchRiscvsharedZkXperm864(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchRiscvsharedZkXperm464(p0, p1)")
private external fun wasmCorearchRiscvsharedZkXperm464(p0: String, p1: String): String

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSm4ed(rs1, rs2, bs) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSm4ks(rs1, rs2, bs) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p0(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSm3p0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p1(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSm3p1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig0(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSha256sig0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig1(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSha256sig1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum0(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSha256sum0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum1(rs1: Int): Int =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkSha256sum1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkXperm864(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    if (riscvsharedZkNapiAvailable) wasmCorearchRiscvsharedZkXperm464(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
