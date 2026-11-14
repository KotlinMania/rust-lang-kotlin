@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

private val newHelenosFibrilNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT =
    if (newHelenosFibrilNapiAddon != null) MutPtr((newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilCreateGeneric(0L.toString(), arg.rawAddress.toString(), stacksize.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilStart(f: FidT) {
    if (newHelenosFibrilNapiAddon != null) {
        newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilStart(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilExit(retval: CLong): Nothing {
    if (newHelenosFibrilNapiAddon != null) {
        newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilExit(retval.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function fibrilExit")
}

public actual fun fibrilDetach(f: FidT) {
    if (newHelenosFibrilNapiAddon != null) {
        newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilDetach(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilYield() {
    if (newHelenosFibrilNapiAddon != null) {
        newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilYield()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilUsleep(usec: UsecT) {
    if (newHelenosFibrilNapiAddon != null) {
        newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilUsleep(usec.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilGetId(): FidT =
    if (newHelenosFibrilNapiAddon != null) MutPtr((newHelenosFibrilNapiAddon.libcNewHelenosFibrilFibrilGetId() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
