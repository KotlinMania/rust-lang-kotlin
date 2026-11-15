@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.pthread.pthreadtypes.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewAppleLibpthreadPthreadIntrospectionNapiAddon(): Boolean

private val libcNewAppleLibpthreadPthreadIntrospectionNapiAvailable: Boolean = initLibcNewAppleLibpthreadPthreadIntrospectionNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionHookInstall(p0)")
private external fun wasmRustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionHookInstall(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(p0, p1, p2)")
private external fun wasmRustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(p0, p1)")
private external fun wasmRustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(p0: String, p1: String): String

public actual fun pthreadIntrospectionHookInstall(hook: PthreadIntrospectionHookT): PthreadIntrospectionHookT =
    if (libcNewAppleLibpthreadPthreadIntrospectionNapiAvailable) PthreadIntrospectionHookT { _, _, _, _ -> } else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadIntrospectionSetspecificNp(thread: PthreadT, key: PthreadKeyT, value: ConstPtr): CInt =
    if (libcNewAppleLibpthreadPthreadIntrospectionNapiAvailable) wasmRustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(thread.toLong().toString(), key.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadIntrospectionGetspecificNp(thread: PthreadT, key: PthreadKeyT): MutPtr =
    if (libcNewAppleLibpthreadPthreadIntrospectionNapiAvailable) MutPtr(wasmRustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(thread.toLong().toString(), key.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
