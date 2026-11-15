@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewAppleLibpthreadPthreadQosNapiAddon(): Boolean

private val libcNewAppleLibpthreadPthreadQosNapiAvailable: Boolean = initLibcNewAppleLibpthreadPthreadQosNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(p0: String, p1: String, p2: String): Int

public actual fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt =
    if (libcNewAppleLibpthreadPthreadQosNapiAvailable) wasmRustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr.rawAddress.toString(), cls.rawAddress.toString(), priority.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt =
    if (libcNewAppleLibpthreadPthreadQosNapiAvailable) wasmRustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread.toLong().toString(), cls.rawAddress.toString(), priority.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
