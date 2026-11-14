@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val nvptxNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun syncthreads(a: UInt) {
    if (nvptxNapiAddon != null) {
        nvptxNapiAddon.corearchNvptxSyncthreads(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun blockDimX(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockDimX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockDimY(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockDimY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockDimZ(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockDimZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxX(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockIdxX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxY(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockIdxY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxZ(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxBlockIdxZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimX(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxGridDimX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimY(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxGridDimY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimZ(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxGridDimZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxX(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxThreadIdxX() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxY(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxThreadIdxY() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxZ(): UInt =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxThreadIdxZ() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vprintf(format: ConstPtr, valist: ConstPtr): Int =
    if (nvptxNapiAddon != null) (nvptxNapiAddon.corearchNvptxVprintf(format.rawAddress.toString(), valist.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: ULong): MutPtr =
    if (nvptxNapiAddon != null) MutPtr((nvptxNapiAddon.corearchNvptxMalloc(size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(ptr: MutPtr) {
    if (nvptxNapiAddon != null) {
        nvptxNapiAddon.corearchNvptxFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong) {
    if (nvptxNapiAddon != null) {
        nvptxNapiAddon.corearchNvptxAssertfail(message.rawAddress.toString(), file.rawAddress.toString(), line.toInt(), function.rawAddress.toString(), charSize.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
