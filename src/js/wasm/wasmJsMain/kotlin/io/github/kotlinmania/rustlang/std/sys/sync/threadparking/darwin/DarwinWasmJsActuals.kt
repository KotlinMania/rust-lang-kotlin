@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Release

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initSysSyncThreadparkingDarwinNapiAddon(): Boolean

private val sysSyncThreadparkingDarwinNapiAvailable: Boolean = initSysSyncThreadparkingDarwinNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.stdSysSyncThreadparkingDarwinDispatchTime(p0, p1)")
private external fun wasmStdSysSyncThreadparkingDarwinDispatchTime(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(p0)")
private external fun wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(p0, p1)")
private external fun wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(p0)")
private external fun wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.stdSysSyncThreadparkingDarwinDispatchRelease(p0)")
private external fun wasmStdSysSyncThreadparkingDarwinDispatchRelease(p0: String)

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    if (sysSyncThreadparkingDarwinNapiAvailable) wasmStdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toString(), delta.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    if (sysSyncThreadparkingDarwinNapiAvailable) MutPtr(wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    if (sysSyncThreadparkingDarwinNapiAvailable) wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress.toString(), timeout.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    if (sysSyncThreadparkingDarwinNapiAvailable) wasmStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchRelease(obj: MutPtr) {
    if (sysSyncThreadparkingDarwinNapiAvailable) {
        wasmStdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
