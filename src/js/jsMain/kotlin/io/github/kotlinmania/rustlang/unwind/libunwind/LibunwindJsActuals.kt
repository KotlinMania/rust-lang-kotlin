@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

private val libunwindNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_unwind !== 'undefined') return globalThis.__km_napi_unwind; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/unwind-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_unwind = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['unwind_native.node', 'build/node-ffi/unwind_native.node', 'build/node-ffi/release/unwind_native.node', 'build/node-ffi/debug/unwind_native.node', 'src/js/node-unwind/build/Release/unwind_native.node', 'src/js/node-unwind/build/Debug/unwind_native.node', 'src/js/node-unwind/dist/build/Release/unwind_native.node', 'src/js/node-unwind/dist/build/Debug/unwind_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_unwind = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_unwind = null; return null; })()")

public actual fun unwindResume(exception: MutPtr): Nothing {
    if (libunwindNapiAddon != null) {
        libunwindNapiAddon.unwindLibunwindUnwindResume(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    if (libunwindNapiAddon != null) {
        libunwindNapiAddon.unwindLibunwindUnwindDeleteException(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    if (libunwindNapiAddon != null) MutPtr((libunwindNapiAddon.unwindLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAddon != null) ConstPtr((libunwindNapiAddon.unwindLibunwindUnwindGetRegionStart(ctx.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAddon != null) ConstPtr((libunwindNapiAddon.unwindLibunwindUnwindGetTextRelBase(ctx.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAddon != null) ConstPtr((libunwindNapiAddon.unwindLibunwindUnwindGetDataRelBase(ctx.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")
