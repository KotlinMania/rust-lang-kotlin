@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private val corearchAarch64MteNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    if (corearchAarch64MteNapiAddon != null) ConstPtr((corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteIrg(ptr.rawAddress.toString(), exclude.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    if (corearchAarch64MteNapiAddon != null) (corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteGmi(ptr.rawAddress.toString(), exclude.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    if (corearchAarch64MteNapiAddon != null) ConstPtr((corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteLdg(ptr.rawAddress.toString(), tagPtr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    if (corearchAarch64MteNapiAddon != null) {
        corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteStg(taggedPtr.rawAddress.toString(), addrToTag.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    if (corearchAarch64MteNapiAddon != null) ConstPtr((corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteAddg(ptr.rawAddress.toString(), value.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    if (corearchAarch64MteNapiAddon != null) (corearchAarch64MteNapiAddon.rustlangCorearchAarch64MteSubp(ptrA.rawAddress.toString(), ptrB.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
