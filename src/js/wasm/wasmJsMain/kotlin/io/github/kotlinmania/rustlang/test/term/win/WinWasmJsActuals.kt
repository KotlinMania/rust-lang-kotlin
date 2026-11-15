@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initTestTermWinNapiAddon(): Boolean

private val testTermWinNapiAvailable: Boolean = initTestTermWinNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangTestTermWinSetConsoleTextAttribute(p0, p1)")
private external fun wasmRustlangTestTermWinSetConsoleTextAttribute(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangTestTermWinGetStdHandle(p0)")
private external fun wasmRustlangTestTermWinGetStdHandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangTestTermWinGetConsoleScreenBufferInfo(p0, p1)")
private external fun wasmRustlangTestTermWinGetConsoleScreenBufferInfo(p0: String, p1: String): Int

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    if (testTermWinNapiAvailable) wasmRustlangTestTermWinSetConsoleTextAttribute(handle.rawAddress.toString(), attr.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(which: Dword): Handle =
    if (testTermWinNapiAvailable) MutPtr(wasmRustlangTestTermWinGetStdHandle(which.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    if (testTermWinNapiAvailable) wasmRustlangTestTermWinGetConsoleScreenBufferInfo(handle.rawAddress.toString(), info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
