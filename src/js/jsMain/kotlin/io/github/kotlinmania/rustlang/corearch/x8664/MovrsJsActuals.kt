@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private val x8664MovrsNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun movrsqi(src: ConstPtr): Byte =
    if (x8664MovrsNapiAddon != null) (x8664MovrsNapiAddon.corearchX8664MovrsMovrsqi(src.rawAddress.toString()) as Int).toByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrshi(src: ConstPtr): Short =
    if (x8664MovrsNapiAddon != null) (x8664MovrsNapiAddon.corearchX8664MovrsMovrshi(src.rawAddress.toString()) as Int).toShort() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrssi(src: ConstPtr): Int =
    if (x8664MovrsNapiAddon != null) (x8664MovrsNapiAddon.corearchX8664MovrsMovrssi(src.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrsdi(src: ConstPtr): Long =
    if (x8664MovrsNapiAddon != null) (x8664MovrsNapiAddon.corearchX8664MovrsMovrsdi(src.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
