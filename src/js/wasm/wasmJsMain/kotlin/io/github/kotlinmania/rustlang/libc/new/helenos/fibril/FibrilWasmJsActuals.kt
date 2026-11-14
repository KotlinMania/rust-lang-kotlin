@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosFibrilNapiAddon(): Boolean

private val newHelenosFibrilNapiAvailable: Boolean = initNewHelenosFibrilNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilCreateGeneric(p0, p1, p2)")
private external fun wasmLibcNewHelenosFibrilFibrilCreateGeneric(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilStart(p0)")
private external fun wasmLibcNewHelenosFibrilFibrilStart(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilExit(p0)")
private external fun wasmLibcNewHelenosFibrilFibrilExit(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilDetach(p0)")
private external fun wasmLibcNewHelenosFibrilFibrilDetach(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilYield()")
private external fun wasmLibcNewHelenosFibrilFibrilYield()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilUsleep(p0)")
private external fun wasmLibcNewHelenosFibrilFibrilUsleep(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.libcNewHelenosFibrilFibrilGetId()")
private external fun wasmLibcNewHelenosFibrilFibrilGetId(): String

public actual fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT =
    if (newHelenosFibrilNapiAvailable) MutPtr(wasmLibcNewHelenosFibrilFibrilCreateGeneric(0L.toString(), arg.rawAddress.toString(), stacksize.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilStart(f: FidT) {
    if (newHelenosFibrilNapiAvailable) {
        wasmLibcNewHelenosFibrilFibrilStart(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilExit(retval: CLong): Nothing {
    if (newHelenosFibrilNapiAvailable) {
        wasmLibcNewHelenosFibrilFibrilExit(retval.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function fibrilExit")
}

public actual fun fibrilDetach(f: FidT) {
    if (newHelenosFibrilNapiAvailable) {
        wasmLibcNewHelenosFibrilFibrilDetach(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilYield() {
    if (newHelenosFibrilNapiAvailable) {
        wasmLibcNewHelenosFibrilFibrilYield()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilUsleep(usec: UsecT) {
    if (newHelenosFibrilNapiAvailable) {
        wasmLibcNewHelenosFibrilFibrilUsleep(usec.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilGetId(): FidT =
    if (newHelenosFibrilNapiAvailable) MutPtr(wasmLibcNewHelenosFibrilFibrilGetId().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
