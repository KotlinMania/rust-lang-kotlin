@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

@JsFun("() => { if (typeof globalThis.__km_napi_unwind !== 'undefined') return globalThis.__km_napi_unwind !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_unwind = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_unwind = null; return false; } try { globalThis.__km_napi_unwind = req('@kotlinmania/unwind-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['unwind_native.node', 'build/node-ffi/unwind_native.node', 'build/node-ffi/release/unwind_native.node', 'build/node-ffi/debug/unwind_native.node', 'src/js/node-unwind/build/Release/unwind_native.node', 'src/js/node-unwind/build/Debug/unwind_native.node', 'src/js/node-unwind/dist/build/Release/unwind_native.node', 'src/js/node-unwind/dist/build/Debug/unwind_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_unwind = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_unwind = null; return false; }")
private external fun initLibunwindNapiAddon(): Boolean

private val libunwindNapiAvailable: Boolean = initLibunwindNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindResume(p0)")
private external fun wasmUnwindLibunwindUnwindResume(p0: String)

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindDeleteException(p0)")
private external fun wasmUnwindLibunwindUnwindDeleteException(p0: String)

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindGetLanguageSpecificData(p0)")
private external fun wasmUnwindLibunwindUnwindGetLanguageSpecificData(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindGetRegionStart(p0)")
private external fun wasmUnwindLibunwindUnwindGetRegionStart(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindGetTextRelBase(p0)")
private external fun wasmUnwindLibunwindUnwindGetTextRelBase(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_unwind.unwindLibunwindUnwindGetDataRelBase(p0)")
private external fun wasmUnwindLibunwindUnwindGetDataRelBase(p0: String): String

public actual fun unwindResume(exception: MutPtr): Nothing {
    if (libunwindNapiAvailable) {
        wasmUnwindLibunwindUnwindResume(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    if (libunwindNapiAvailable) {
        wasmUnwindLibunwindUnwindDeleteException(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    if (libunwindNapiAvailable) MutPtr(wasmUnwindLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmUnwindLibunwindUnwindGetRegionStart(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmUnwindLibunwindUnwindGetTextRelBase(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmUnwindLibunwindUnwindGetDataRelBase(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")
