@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.CSsizeT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val sysFsUnixNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/std-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = null; return null; })()")

public actual fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    if (sysFsUnixNapiAddon != null) (sysFsUnixNapiAddon.stdSysFsUnixOpen(pathname.rawAddress.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (sysFsUnixNapiAddon != null) (sysFsUnixNapiAddon.stdSysFsUnixClose(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
