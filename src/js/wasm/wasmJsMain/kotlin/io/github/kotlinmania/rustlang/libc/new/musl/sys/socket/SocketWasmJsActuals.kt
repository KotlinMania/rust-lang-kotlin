@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.musl.sys.socket

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewMuslSysSocketNapiAddon(): Boolean

private val libcNewMuslSysSocketNapiAvailable: Boolean = initLibcNewMuslSysSocketNapiAddon()

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcNewMuslSysSocketSendmmsg(p0, p1, p2, p3)")
private external fun wasmRustlangLibcNewMuslSysSocketSendmmsg(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcNewMuslSysSocketRecvmmsg(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcNewMuslSysSocketRecvmmsg(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

public actual fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CUint): CInt =
    if (libcNewMuslSysSocketNapiAvailable) wasmRustlangLibcNewMuslSysSocketSendmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CUint, timeout: MutPtr): CInt =
    if (libcNewMuslSysSocketNapiAvailable) wasmRustlangLibcNewMuslSysSocketRecvmmsg(sockfd, msgvec.rawAddress.toString(), vlen.toInt(), flags.toInt(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
