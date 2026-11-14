@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private val newHelenosStdlibNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun malloc(size: ULong): MutPtr =
    if (newHelenosStdlibNapiAddon != null) MutPtr((newHelenosStdlibNapiAddon.libcNewHelenosStdlibMalloc(size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nmemb: ULong, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAddon != null) MutPtr((newHelenosStdlibNapiAddon.libcNewHelenosStdlibCalloc(nmemb.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(addr: MutPtr, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAddon != null) MutPtr((newHelenosStdlibNapiAddon.libcNewHelenosStdlibRealloc(addr.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(addr: MutPtr) {
    if (newHelenosStdlibNapiAddon != null) {
        newHelenosStdlibNapiAddon.libcNewHelenosStdlibFree(addr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memalign(align: ULong, size: ULong): MutPtr =
    if (newHelenosStdlibNapiAddon != null) MutPtr((newHelenosStdlibNapiAddon.libcNewHelenosStdlibMemalign(align.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (newHelenosStdlibNapiAddon != null) (newHelenosStdlibNapiAddon.libcNewHelenosStdlibRand() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (newHelenosStdlibNapiAddon != null) {
        newHelenosStdlibNapiAddon.libcNewHelenosStdlibSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun exit(code: CInt): Nothing {
    if (newHelenosStdlibNapiAddon != null) {
        newHelenosStdlibNapiAddon.libcNewHelenosStdlibExit(code)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    if (newHelenosStdlibNapiAddon != null) {
        newHelenosStdlibNapiAddon.libcNewHelenosStdlibAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun getenv(env: ConstPtr): MutPtr =
    if (newHelenosStdlibNapiAddon != null) MutPtr((newHelenosStdlibNapiAddon.libcNewHelenosStdlibGetenv(env.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
