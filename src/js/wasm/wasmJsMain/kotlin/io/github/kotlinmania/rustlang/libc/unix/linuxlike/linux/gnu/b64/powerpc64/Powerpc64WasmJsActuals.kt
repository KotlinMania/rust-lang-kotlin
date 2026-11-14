@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.powerpc64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.SizeT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initUnixLinuxlikeLinuxGnuB64Powerpc64NapiAddon(): Boolean

private val unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable: Boolean = initUnixLinuxlikeLinuxGnuB64Powerpc64NapiAddon()

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuB64Powerpc64Sysctl(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Sysctl(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuB64Powerpc64Getcontext(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Getcontext(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuB64Powerpc64Setcontext(p0)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Setcontext(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuB64Powerpc64Swapcontext(p0, p1)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Swapcontext(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.libcUnixLinuxlikeLinuxGnuB64Powerpc64Makecontext(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Makecontext(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String)

public actual fun sysctl(name: MutPtr, namelen: CInt, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Sysctl(name.rawAddress.toString(), namelen, oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcontext(ucp: MutPtr): CInt =
    if (unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Getcontext(ucp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setcontext(ucp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Setcontext(ucp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun swapcontext(oucp: MutPtr, ucp: ConstPtr): CInt =
    if (unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable) wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Swapcontext(oucp.rawAddress.toString(), ucp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun makecontext(ucp: MutPtr, func: MakecontextFuncCallback, argc: CInt, vararg variadicArgs: Long) {
    if (unixLinuxlikeLinuxGnuB64Powerpc64NapiAvailable) {
        wasmLibcUnixLinuxlikeLinuxGnuB64Powerpc64Makecontext(ucp.rawAddress.toString(), 0L.toString(), argc, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
