@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosVfsVfsNapiAddon(): Boolean

private val newHelenosVfsVfsNapiAvailable: Boolean = initNewHelenosVfsVfsNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsFhandle(p0, p1)")
private external fun wasmLibcNewHelenosVfsVfsVfsFhandle(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsStat(p0, p1)")
private external fun wasmLibcNewHelenosVfsVfsVfsStat(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsLinkPath(p0, p1, p2)")
private external fun wasmLibcNewHelenosVfsVfsVfsLinkPath(p0: String, p1: Int, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsUnlinkPath(p0)")
private external fun wasmLibcNewHelenosVfsVfsVfsUnlinkPath(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsRenamePath(p0, p1)")
private external fun wasmLibcNewHelenosVfsVfsVfsRenamePath(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosVfsVfsVfsStatPath(p0, p1)")
private external fun wasmLibcNewHelenosVfsVfsVfsStatPath(p0: String, p1: String): Int

public actual fun vfsFhandle(file: MutPtr, handle: MutPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsFhandle(file.rawAddress.toString(), handle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfsStat(handle: CInt, stat: MutPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsStat(handle, stat.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfsLinkPath(path: ConstPtr, kind: VfsFileKindT, linkedfd: MutPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsLinkPath(path.rawAddress.toString(), kind, linkedfd.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfsUnlinkPath(path: ConstPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsUnlinkPath(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfsRenamePath(oldpath: ConstPtr, newpath: ConstPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsRenamePath(oldpath.rawAddress.toString(), newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfsStatPath(path: ConstPtr, stat: MutPtr): ErrnoT =
    if (newHelenosVfsVfsNapiAvailable) wasmLibcNewHelenosVfsVfsVfsStatPath(path.rawAddress.toString(), stat.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
