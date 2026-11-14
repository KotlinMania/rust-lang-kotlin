@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.ClockidT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysSyncFutexWasilibcNapiAddon(): Boolean

private val sysSyncFutexWasilibcNapiAvailable: Boolean = initSysSyncFutexWasilibcNapiAddon()

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysSyncFutexWasilibcWasilibcFutexWait(p0, p1, p2, p3, p4)")
private external fun wasmStdSysSyncFutexWasilibcWasilibcFutexWait(p0: String, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysSyncFutexWasilibcWasilibcFutexWake(p0, p1, p2)")
private external fun wasmStdSysSyncFutexWasilibcWasilibcFutexWake(p0: String, p1: Int, p2: Int): Int

public actual fun wasilibcFutexWait(addr: MutPtr, value: CInt, clock: ClockidT, at: ConstPtr, flags: CUint): CInt =
    if (sysSyncFutexWasilibcNapiAvailable) wasmStdSysSyncFutexWasilibcWasilibcFutexWait(addr.rawAddress.toString(), value, clock, at.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcFutexWake(addr: ConstPtr, count: CInt, flags: CUint): CInt =
    if (sysSyncFutexWasilibcNapiAvailable) wasmStdSysSyncFutexWasilibcWasilibcFutexWake(addr.rawAddress.toString(), count, flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")
