@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchRiscv64ZkNapiAddon(): Boolean

private val corearchRiscv64ZkNapiAvailable: Boolean = initCorearchRiscv64ZkNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkAes64es(p0, p1)")
private external fun wasmRustlangCorearchRiscv64ZkAes64es(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkAes64esm(p0, p1)")
private external fun wasmRustlangCorearchRiscv64ZkAes64esm(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkAes64ds(p0, p1)")
private external fun wasmRustlangCorearchRiscv64ZkAes64ds(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkAes64dsm(p0, p1)")
private external fun wasmRustlangCorearchRiscv64ZkAes64dsm(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkAes64im(p0)")
private external fun wasmRustlangCorearchRiscv64ZkAes64im(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkSha512sig0(p0)")
private external fun wasmRustlangCorearchRiscv64ZkSha512sig0(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkSha512sig1(p0)")
private external fun wasmRustlangCorearchRiscv64ZkSha512sig1(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkSha512sum0(p0)")
private external fun wasmRustlangCorearchRiscv64ZkSha512sum0(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchRiscv64ZkSha512sum1(p0)")
private external fun wasmRustlangCorearchRiscv64ZkSha512sum1(p0: String): String

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkAes64es(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkAes64esm(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkAes64ds(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkAes64dsm(rs1.toString(), rs2.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64im(rs1: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkAes64im(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sig0(rs1: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkSha512sig0(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sig1(rs1: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkSha512sig1(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sum0(rs1: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkSha512sum0(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sum1(rs1: Long): Long =
    if (corearchRiscv64ZkNapiAvailable) wasmRustlangCorearchRiscv64ZkSha512sum1(rs1.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
