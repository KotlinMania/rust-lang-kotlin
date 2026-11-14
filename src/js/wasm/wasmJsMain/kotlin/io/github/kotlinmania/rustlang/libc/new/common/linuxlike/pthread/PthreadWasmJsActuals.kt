@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.PthreadT
import io.github.kotlinmania.rustlang.libc.unix.SizeT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewCommonLinuxlikePthreadNapiAddon(): Boolean

private val newCommonLinuxlikePthreadNapiAvailable: Boolean = initNewCommonLinuxlikePthreadNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadGetaffinityNp(p0, p1, p2)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadGetattrNp(p0, p1)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadGetattrNp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadGetnameNp(p0, p1, p2)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadGetnameNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadGettidNp(p0)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadGettidNp(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadSetaffinityNp(p0, p1, p2)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonLinuxlikePthreadPthreadSetnameNp(p0, p1)")
private external fun wasmLibcNewCommonLinuxlikePthreadPthreadSetnameNp(p0: String, p1: String): Int

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.rawAddress.toString(), name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.rawAddress.toString(), cpusetsize.toString(), cpuset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    if (newCommonLinuxlikePthreadNapiAvailable) wasmLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.rawAddress.toString(), name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
