@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release

private val sysSyncThreadparkingDarwinNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    if (sysSyncThreadparkingDarwinNapiAddon != null) (sysSyncThreadparkingDarwinNapiAddon.stdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toString(), delta.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    if (sysSyncThreadparkingDarwinNapiAddon != null) MutPtr((sysSyncThreadparkingDarwinNapiAddon.stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    if (sysSyncThreadparkingDarwinNapiAddon != null) (sysSyncThreadparkingDarwinNapiAddon.stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress.toString(), timeout.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    if (sysSyncThreadparkingDarwinNapiAddon != null) (sysSyncThreadparkingDarwinNapiAddon.stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dispatchRelease(obj: MutPtr) {
    if (sysSyncThreadparkingDarwinNapiAddon != null) {
        sysSyncThreadparkingDarwinNapiAddon.stdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
