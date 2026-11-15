@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchRiscvsharedZkNapiAddon(): Boolean

private val corearchRiscvsharedZkNapiAvailable: Boolean = initCorearchRiscvsharedZkNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSm4ed(p0, p1, p2)")
private external fun wasmRustlangCorearchRiscvsharedZkSm4ed(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSm4ks(p0, p1, p2)")
private external fun wasmRustlangCorearchRiscvsharedZkSm4ks(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSm3p0(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSm3p0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSm3p1(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSm3p1(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSha256sig0(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSha256sig0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSha256sig1(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSha256sig1(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSha256sum0(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSha256sum0(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkSha256sum1(p0)")
private external fun wasmRustlangCorearchRiscvsharedZkSha256sum1(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkXperm864(p0, p1)")
private external fun wasmRustlangCorearchRiscvsharedZkXperm864(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscvsharedZkXperm464(p0, p1)")
private external fun wasmRustlangCorearchRiscvsharedZkXperm464(p0: String, p1: String): String

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSm3p0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSm3p1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSha256sig0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSha256sig1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSha256sum0(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkSha256sum1(rs1) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkXperm864(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZkNapiAvailable) wasmRustlangCorearchRiscvsharedZkXperm464(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
