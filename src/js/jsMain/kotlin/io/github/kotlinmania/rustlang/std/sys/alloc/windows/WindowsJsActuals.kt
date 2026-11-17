@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Handle

private val sysAllocWindowsNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun getProcessHeap(): Handle =
    if (sysAllocWindowsNapiAddon != null) MutPtr((sysAllocWindowsNapiAddon.stdSysAllocWindowsGetProcessHeap() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    if (sysAllocWindowsNapiAddon != null) MutPtr((sysAllocWindowsNapiAddon.stdSysAllocWindowsHeapAlloc(hheap.rawAddress.toString(), dwflags.toInt(), dwbytes.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    if (sysAllocWindowsNapiAddon != null) MutPtr((sysAllocWindowsNapiAddon.stdSysAllocWindowsHeapReAlloc(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString(), dwbytes.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    if (sysAllocWindowsNapiAddon != null) (sysAllocWindowsNapiAddon.stdSysAllocWindowsHeapFree(hheap.rawAddress.toString(), dwflags.toInt(), lpmem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
