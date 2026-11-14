@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt

private val sysIoErrorUnixNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/std-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = null; return null; })()")

public actual var LIBC_ERRNO: CInt
    get() = if (sysIoErrorUnixNapiAddon != null) (sysIoErrorUnixNapiAddon.stdSysIoErrorUnixGetLIBCERRNO() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (sysIoErrorUnixNapiAddon != null) {
            sysIoErrorUnixNapiAddon.stdSysIoErrorUnixSetLIBCERRNO(value)
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual fun errnoLocation(): MutPtr =
    if (sysIoErrorUnixNapiAddon != null) MutPtr((sysIoErrorUnixNapiAddon.stdSysIoErrorUnixErrnoLocation() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
