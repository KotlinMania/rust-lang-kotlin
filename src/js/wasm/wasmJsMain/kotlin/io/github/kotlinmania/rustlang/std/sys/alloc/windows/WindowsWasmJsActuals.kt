@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initStdSysAllocWindowsNapiAddon(): Boolean

private val stdSysAllocWindowsNapiAvailable: Boolean = initStdSysAllocWindowsNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysAllocWindowsGetProcessHeap()")
private external fun wasmRustlangStdSysAllocWindowsGetProcessHeap(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysAllocWindowsHeapAlloc(p0, p1, p2)")
private external fun wasmRustlangStdSysAllocWindowsHeapAlloc(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysAllocWindowsHeapReAlloc(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysAllocWindowsHeapReAlloc(p0: String, p1: Int, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysAllocWindowsHeapFree(p0, p1, p2)")
private external fun wasmRustlangStdSysAllocWindowsHeapFree(p0: String, p1: Int, p2: String): Int

public actual fun getProcessHeap(): Handle =
    if (stdSysAllocWindowsNapiAvailable) MutPtr(wasmRustlangStdSysAllocWindowsGetProcessHeap().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    if (stdSysAllocWindowsNapiAvailable) MutPtr(wasmRustlangStdSysAllocWindowsHeapAlloc(hheap.rawAddress.toString(), dwflags.toInt(), dwbytes.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    if (stdSysAllocWindowsNapiAvailable) MutPtr(wasmRustlangStdSysAllocWindowsHeapReAlloc(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString(), dwbytes.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    if (stdSysAllocWindowsNapiAvailable) wasmRustlangStdSysAllocWindowsHeapFree(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
