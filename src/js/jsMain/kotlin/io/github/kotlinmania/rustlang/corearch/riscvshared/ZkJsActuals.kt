@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private val corearchRiscvsharedZkNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSm3p0(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sm3p1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSm3p1(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSha256sig0(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sig1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSha256sig1(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum0(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSha256sum0(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sha256sum1(rs1: Int): Int =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkSha256sum1(rs1) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkXperm864(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    if (corearchRiscvsharedZkNapiAddon != null) (corearchRiscvsharedZkNapiAddon.rustlangCorearchRiscvsharedZkXperm464(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
