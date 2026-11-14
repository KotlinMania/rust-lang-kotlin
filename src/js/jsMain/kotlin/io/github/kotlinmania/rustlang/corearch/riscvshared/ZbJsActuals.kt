@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private val riscvsharedZbNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun orcB64(rs1: Long): Long =
    if (riscvsharedZbNapiAddon != null) (riscvsharedZbNapiAddon.corearchRiscvsharedZbOrcB64(rs1.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAddon != null) (riscvsharedZbNapiAddon.corearchRiscvsharedZbClmul64(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAddon != null) (riscvsharedZbNapiAddon.corearchRiscvsharedZbClmulh64(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    if (riscvsharedZbNapiAddon != null) (riscvsharedZbNapiAddon.corearchRiscvsharedZbClmulr64(rs1.toString(), rs2.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
