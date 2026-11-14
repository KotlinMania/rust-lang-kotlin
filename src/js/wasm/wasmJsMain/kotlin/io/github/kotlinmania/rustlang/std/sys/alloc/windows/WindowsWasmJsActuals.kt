@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysAllocWindowsNapiAddon(): Boolean

private val sysAllocWindowsNapiAvailable: Boolean = initSysAllocWindowsNapiAddon()

@JsFun("() => globalThis.__km_napi_std.stdSysAllocWindowsGetProcessHeap()")
private external fun wasmStdSysAllocWindowsGetProcessHeap(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysAllocWindowsHeapAlloc(p0, p1, p2)")
private external fun wasmStdSysAllocWindowsHeapAlloc(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysAllocWindowsHeapReAlloc(p0, p1, p2, p3)")
private external fun wasmStdSysAllocWindowsHeapReAlloc(p0: String, p1: Int, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysAllocWindowsHeapFree(p0, p1, p2)")
private external fun wasmStdSysAllocWindowsHeapFree(p0: String, p1: Int, p2: String): Int

public actual fun getProcessHeap(): Handle =
    if (sysAllocWindowsNapiAvailable) MutPtr(wasmStdSysAllocWindowsGetProcessHeap().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    if (sysAllocWindowsNapiAvailable) MutPtr(wasmStdSysAllocWindowsHeapAlloc(hheap.rawAddress.toString(), dwflags.toInt(), dwbytes.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    if (sysAllocWindowsNapiAvailable) MutPtr(wasmStdSysAllocWindowsHeapReAlloc(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString(), dwbytes.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    if (sysAllocWindowsNapiAvailable) wasmStdSysAllocWindowsHeapFree(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
