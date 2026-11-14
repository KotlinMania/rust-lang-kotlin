@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val termWinNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_test !== 'undefined') return globalThis.__km_napi_test; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/test-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_test = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['test_native.node', 'build/node-ffi/test_native.node', 'build/node-ffi/release/test_native.node', 'build/node-ffi/debug/test_native.node', 'src/js/node-test/build/Release/test_native.node', 'src/js/node-test/build/Debug/test_native.node', 'src/js/node-test/dist/build/Release/test_native.node', 'src/js/node-test/dist/build/Debug/test_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_test = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_test = null; return null; })()")

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    if (termWinNapiAddon != null) (termWinNapiAddon.testTermWinSetConsoleTextAttribute(handle.rawAddress.toString(), attr.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(which: Dword): Handle =
    if (termWinNapiAddon != null) MutPtr((termWinNapiAddon.testTermWinGetStdHandle(which.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    if (termWinNapiAddon != null) (termWinNapiAddon.testTermWinGetConsoleScreenBufferInfo(handle.rawAddress.toString(), info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
