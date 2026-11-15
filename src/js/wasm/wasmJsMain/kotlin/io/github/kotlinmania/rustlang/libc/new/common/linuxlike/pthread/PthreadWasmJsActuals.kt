@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewCommonLinuxlikePthreadNapiAddon(): Boolean

private val libcNewCommonLinuxlikePthreadNapiAvailable: Boolean = initLibcNewCommonLinuxlikePthreadNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(p0, p1)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(p0)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(p0, p1)")
private external fun wasmRustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(p0: String, p1: String): Int

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.toLong().toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native.toLong().toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.toLong().toString(), name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread.toLong().toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.toLong().toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    if (libcNewCommonLinuxlikePthreadNapiAvailable) wasmRustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.toLong().toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
