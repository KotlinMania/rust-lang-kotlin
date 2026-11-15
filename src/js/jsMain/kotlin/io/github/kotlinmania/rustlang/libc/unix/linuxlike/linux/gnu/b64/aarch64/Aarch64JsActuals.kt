@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.SizeT

private val libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun sysctl(name: MutPtr, namelen: CInt, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon != null) (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuB64Aarch64Sysctl(name.rawAddress.toString(), namelen, oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcontext(ucp: MutPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon != null) (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuB64Aarch64Getcontext(ucp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setcontext(ucp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon != null) (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuB64Aarch64Setcontext(ucp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun makecontext(ucp: MutPtr, func: MakecontextFuncCallback, argc: CInt, vararg variadicArgs: Long) {
    if (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon != null) {
        libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuB64Aarch64Makecontext(ucp.rawAddress.toString(), 0L.toString(), argc, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun swapcontext(uocp: MutPtr, ucp: ConstPtr): CInt =
    if (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon != null) (libcUnixLinuxlikeLinuxGnuB64Aarch64NapiAddon.rustlangLibcUnixLinuxlikeLinuxGnuB64Aarch64Swapcontext(uocp.rawAddress.toString(), ucp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
