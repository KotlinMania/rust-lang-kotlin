@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_test !== 'undefined') return globalThis.__km_napi_test !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_test = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_test = null; return false; } try { globalThis.__km_napi_test = req('@kotlinmania/test-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['test_native.node', 'build/node-ffi/test_native.node', 'build/node-ffi/release/test_native.node', 'build/node-ffi/debug/test_native.node', 'src/js/node-test/build/Release/test_native.node', 'src/js/node-test/build/Debug/test_native.node', 'src/js/node-test/dist/build/Release/test_native.node', 'src/js/node-test/dist/build/Debug/test_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_test = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_test = null; return false; }")
private external fun initTermWinNapiAddon(): Boolean

private val termWinNapiAvailable: Boolean = initTermWinNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_test.testTermWinSetConsoleTextAttribute(p0, p1)")
private external fun wasmTestTermWinSetConsoleTextAttribute(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_test.testTermWinGetStdHandle(p0)")
private external fun wasmTestTermWinGetStdHandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_test.testTermWinGetConsoleScreenBufferInfo(p0, p1)")
private external fun wasmTestTermWinGetConsoleScreenBufferInfo(p0: String, p1: String): Int

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    if (termWinNapiAvailable) wasmTestTermWinSetConsoleTextAttribute(handle.rawAddress.toString(), attr.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(which: Dword): Handle =
    if (termWinNapiAvailable) MutPtr(wasmTestTermWinGetStdHandle(which.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    if (termWinNapiAvailable) wasmTestTermWinGetConsoleScreenBufferInfo(handle.rawAddress.toString(), info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
