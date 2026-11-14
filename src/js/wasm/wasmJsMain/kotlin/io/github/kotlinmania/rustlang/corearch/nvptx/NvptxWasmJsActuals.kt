@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initNvptxNapiAddon(): Boolean

private val nvptxNapiAvailable: Boolean = initNvptxNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchNvptxSyncthreads(p0)")
private external fun wasmCorearchNvptxSyncthreads(p0: Int)

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockDimX()")
private external fun wasmCorearchNvptxBlockDimX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockDimY()")
private external fun wasmCorearchNvptxBlockDimY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockDimZ()")
private external fun wasmCorearchNvptxBlockDimZ(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockIdxX()")
private external fun wasmCorearchNvptxBlockIdxX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockIdxY()")
private external fun wasmCorearchNvptxBlockIdxY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxBlockIdxZ()")
private external fun wasmCorearchNvptxBlockIdxZ(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxGridDimX()")
private external fun wasmCorearchNvptxGridDimX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxGridDimY()")
private external fun wasmCorearchNvptxGridDimY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxGridDimZ()")
private external fun wasmCorearchNvptxGridDimZ(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxThreadIdxX()")
private external fun wasmCorearchNvptxThreadIdxX(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxThreadIdxY()")
private external fun wasmCorearchNvptxThreadIdxY(): Int

@JsFun("() => globalThis.__km_napi_corearch.corearchNvptxThreadIdxZ()")
private external fun wasmCorearchNvptxThreadIdxZ(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchNvptxVprintf(p0, p1)")
private external fun wasmCorearchNvptxVprintf(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchNvptxMalloc(p0)")
private external fun wasmCorearchNvptxMalloc(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_corearch.corearchNvptxFree(p0)")
private external fun wasmCorearchNvptxFree(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_corearch.corearchNvptxAssertfail(p0, p1, p2, p3, p4)")
private external fun wasmCorearchNvptxAssertfail(p0: String, p1: String, p2: Int, p3: String, p4: String)

public actual fun syncthreads(a: UInt) {
    if (nvptxNapiAvailable) {
        wasmCorearchNvptxSyncthreads(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun blockDimX(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockDimX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockDimY(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockDimY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockDimZ(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockDimZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxX(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockIdxX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxY(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockIdxY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun blockIdxZ(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxBlockIdxZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimX(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxGridDimX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimY(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxGridDimY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gridDimZ(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxGridDimZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxX(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxThreadIdxX().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxY(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxThreadIdxY().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadIdxZ(): UInt =
    if (nvptxNapiAvailable) wasmCorearchNvptxThreadIdxZ().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vprintf(format: ConstPtr, valist: ConstPtr): Int =
    if (nvptxNapiAvailable) wasmCorearchNvptxVprintf(format.rawAddress.toString(), valist.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: ULong): MutPtr =
    if (nvptxNapiAvailable) MutPtr(wasmCorearchNvptxMalloc(size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(ptr: MutPtr) {
    if (nvptxNapiAvailable) {
        wasmCorearchNvptxFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong) {
    if (nvptxNapiAvailable) {
        wasmCorearchNvptxAssertfail(message.rawAddress.toString(), file.rawAddress.toString(), line.toInt(), function.rawAddress.toString(), charSize.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
