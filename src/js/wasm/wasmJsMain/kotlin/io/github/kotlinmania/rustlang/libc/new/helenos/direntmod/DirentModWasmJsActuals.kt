@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.direntmod

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosDirentmodDirentModNapiAddon(): Boolean

private val newHelenosDirentmodDirentModNapiAvailable: Boolean = initNewHelenosDirentmodDirentModNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosDirentmodDirentModOpendir(p0)")
private external fun wasmLibcNewHelenosDirentmodDirentModOpendir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosDirentmodDirentModReaddir(p0)")
private external fun wasmLibcNewHelenosDirentmodDirentModReaddir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosDirentmodDirentModClosedir(p0)")
private external fun wasmLibcNewHelenosDirentmodDirentModClosedir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosDirentmodDirentModRewinddir(p0)")
private external fun wasmLibcNewHelenosDirentmodDirentModRewinddir(p0: String)

public actual fun opendir(name: ConstPtr): MutPtr =
    if (newHelenosDirentmodDirentModNapiAvailable) MutPtr(wasmLibcNewHelenosDirentmodDirentModOpendir(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir(dir: MutPtr): MutPtr =
    if (newHelenosDirentmodDirentModNapiAvailable) MutPtr(wasmLibcNewHelenosDirentmodDirentModReaddir(dir.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closedir(dir: MutPtr): CInt =
    if (newHelenosDirentmodDirentModNapiAvailable) wasmLibcNewHelenosDirentmodDirentModClosedir(dir.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewinddir(dir: MutPtr) {
    if (newHelenosDirentmodDirentModNapiAvailable) {
        wasmLibcNewHelenosDirentmodDirentModRewinddir(dir.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
