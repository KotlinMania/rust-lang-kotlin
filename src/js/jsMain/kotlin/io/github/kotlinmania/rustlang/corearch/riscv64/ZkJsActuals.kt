@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

private val riscv64ZkNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkAes64es(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkAes64esm(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkAes64ds(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkAes64dsm(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aes64im(rs1: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkAes64im(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sig0(rs1: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkSha512sig0(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sig1(rs1: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkSha512sig1(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sum0(rs1: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkSha512sum0(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha512sum1(rs1: Long): Long =
    if (riscv64ZkNapiAddon != null) (riscv64ZkNapiAddon.corearchRiscv64ZkSha512sum1(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
