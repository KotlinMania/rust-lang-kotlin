@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewGlibcIoSysStatvfsNapiAddon(): Boolean

private val libcNewGlibcIoSysStatvfsNapiAvailable: Boolean = initLibcNewGlibcIoSysStatvfsNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewGlibcIoSysStatvfsStatvfs(p0, p1)")
private external fun wasmRustlangLibcNewGlibcIoSysStatvfsStatvfs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewGlibcIoSysStatvfsFstatvfs(p0, p1)")
private external fun wasmRustlangLibcNewGlibcIoSysStatvfsFstatvfs(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewGlibcIoSysStatvfsStatvfs64(p0, p1)")
private external fun wasmRustlangLibcNewGlibcIoSysStatvfsStatvfs64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewGlibcIoSysStatvfsFstatvfs64(p0, p1)")
private external fun wasmRustlangLibcNewGlibcIoSysStatvfsFstatvfs64(p0: Int, p1: String): Int

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcNewGlibcIoSysStatvfsNapiAvailable) wasmRustlangLibcNewGlibcIoSysStatvfsStatvfs(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    if (libcNewGlibcIoSysStatvfsNapiAvailable) wasmRustlangLibcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcNewGlibcIoSysStatvfsNapiAvailable) wasmRustlangLibcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    if (libcNewGlibcIoSysStatvfsNapiAvailable) wasmRustlangLibcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
