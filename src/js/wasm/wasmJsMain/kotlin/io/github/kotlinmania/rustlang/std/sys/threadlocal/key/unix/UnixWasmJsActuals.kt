@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysThreadlocalKeyUnixNapiAddon(): Boolean

private val sysThreadlocalKeyUnixNapiAvailable: Boolean = initSysThreadlocalKeyUnixNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysThreadlocalKeyUnixPthreadKeyCreate(p0, p1)")
private external fun wasmStdSysThreadlocalKeyUnixPthreadKeyCreate(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysThreadlocalKeyUnixPthreadGetspecific(p0)")
private external fun wasmStdSysThreadlocalKeyUnixPthreadGetspecific(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysThreadlocalKeyUnixPthreadSetspecific(p0, p1)")
private external fun wasmStdSysThreadlocalKeyUnixPthreadSetspecific(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysThreadlocalKeyUnixPthreadKeyDelete(p0)")
private external fun wasmStdSysThreadlocalKeyUnixPthreadKeyDelete(p0: Int): Int

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    if (sysThreadlocalKeyUnixNapiAvailable) wasmStdSysThreadlocalKeyUnixPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (sysThreadlocalKeyUnixNapiAvailable) MutPtr(wasmStdSysThreadlocalKeyUnixPthreadGetspecific(key.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (sysThreadlocalKeyUnixNapiAvailable) wasmStdSysThreadlocalKeyUnixPthreadSetspecific(key.toInt(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (sysThreadlocalKeyUnixNapiAvailable) wasmStdSysThreadlocalKeyUnixPthreadKeyDelete(key.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")
