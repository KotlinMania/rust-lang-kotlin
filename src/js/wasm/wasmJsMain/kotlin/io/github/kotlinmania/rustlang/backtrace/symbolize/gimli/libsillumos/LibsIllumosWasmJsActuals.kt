@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsillumos

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_backtrace !== 'undefined') return globalThis.__km_napi_backtrace !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_backtrace = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_backtrace = null; return false; } try { globalThis.__km_napi_backtrace = req('@kotlinmania/backtrace-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['backtrace_native.node', 'build/node-ffi/backtrace_native.node', 'build/node-ffi/release/backtrace_native.node', 'build/node-ffi/debug/backtrace_native.node', 'src/js/node-backtrace/build/Release/backtrace_native.node', 'src/js/node-backtrace/build/Debug/backtrace_native.node', 'src/js/node-backtrace/dist/build/Release/backtrace_native.node', 'src/js/node-backtrace/dist/build/Debug/backtrace_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_backtrace = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_backtrace = null; return false; }")
private external fun initSymbolizeGimliLibsillumosLibsIllumosNapiAddon(): Boolean

private val symbolizeGimliLibsillumosLibsIllumosNapiAvailable: Boolean = initSymbolizeGimliLibsillumosLibsIllumosNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(p0, p1, p2)")
private external fun wasmBacktraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(p0: String, p1: Int, p2: String): Int

public actual fun dlinfo(handle: ConstPtr, request: CInt, p: MutPtr): CInt =
    if (symbolizeGimliLibsillumosLibsIllumosNapiAvailable) wasmBacktraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(handle.rawAddress.toString(), request, p.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
