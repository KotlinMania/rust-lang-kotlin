@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosStdlibNapiAddon(): Boolean

private val newHelenosStdlibNapiAvailable: Boolean = initNewHelenosStdlibNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibMalloc(p0)")
private external fun wasmLibcNewHelenosStdlibMalloc(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibCalloc(p0, p1)")
private external fun wasmLibcNewHelenosStdlibCalloc(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibRealloc(p0, p1)")
private external fun wasmLibcNewHelenosStdlibRealloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibFree(p0)")
private external fun wasmLibcNewHelenosStdlibFree(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibMemalign(p0, p1)")
private external fun wasmLibcNewHelenosStdlibMemalign(p0: String, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosStdlibRand()")
private external fun wasmLibcNewHelenosStdlibRand(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibSrand(p0)")
private external fun wasmLibcNewHelenosStdlibSrand(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibExit(p0)")
private external fun wasmLibcNewHelenosStdlibExit(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosStdlibAbort()")
private external fun wasmLibcNewHelenosStdlibAbort()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosStdlibGetenv(p0)")
private external fun wasmLibcNewHelenosStdlibGetenv(p0: String): String

public actual fun malloc(size: ULong): MutPtr =
    if (newHelenosStdlibNapiAvailable) MutPtr(wasmLibcNewHelenosStdlibMalloc(size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nmemb: ULong, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAvailable) MutPtr(wasmLibcNewHelenosStdlibCalloc(nmemb.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(addr: MutPtr, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAvailable) MutPtr(wasmLibcNewHelenosStdlibRealloc(addr.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(addr: MutPtr) {
    if (newHelenosStdlibNapiAvailable) {
        wasmLibcNewHelenosStdlibFree(addr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memalign(align: ULong, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAvailable) MutPtr(wasmLibcNewHelenosStdlibMemalign(align.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (newHelenosStdlibNapiAvailable) wasmLibcNewHelenosStdlibRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (newHelenosStdlibNapiAvailable) {
        wasmLibcNewHelenosStdlibSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun exit(code: CInt): Nothing {
    if (newHelenosStdlibNapiAvailable) {
        wasmLibcNewHelenosStdlibExit(code)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    if (newHelenosStdlibNapiAvailable) {
        wasmLibcNewHelenosStdlibAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun getenv(env: ConstPtr): MutPtr =
    if (newHelenosStdlibNapiAvailable) MutPtr(wasmLibcNewHelenosStdlibGetenv(env.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
