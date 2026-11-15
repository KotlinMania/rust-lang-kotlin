@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.types.UnwindWord

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibunwindNapiAddon(): Boolean

private val libunwindNapiAvailable: Boolean = initLibunwindNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindResume(p0)")
private external fun wasmRustlangLibunwindUnwindResume(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindDeleteException(p0)")
private external fun wasmRustlangLibunwindUnwindDeleteException(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetLanguageSpecificData(p0)")
private external fun wasmRustlangLibunwindUnwindGetLanguageSpecificData(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetRegionStart(p0)")
private external fun wasmRustlangLibunwindUnwindGetRegionStart(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetTextRelBase(p0)")
private external fun wasmRustlangLibunwindUnwindGetTextRelBase(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetDataRelBase(p0)")
private external fun wasmRustlangLibunwindUnwindGetDataRelBase(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetGr(p0, p1)")
private external fun wasmRustlangLibunwindUnwindGetGr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindSetGr(p0, p1, p2)")
private external fun wasmRustlangLibunwindUnwindSetGr(p0: String, p1: Int, p2: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindSetIp(p0, p1)")
private external fun wasmRustlangLibunwindUnwindSetIp(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibunwindUnwindGetIpinfo(p0, p1)")
private external fun wasmRustlangLibunwindUnwindGetIpinfo(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibunwindGccSpecificHandler(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibunwindGccSpecificHandler(p0: String, p1: String, p2: String, p3: String, p4: String): Int

public actual fun unwindResume(exception: MutPtr): Nothing {
    if (libunwindNapiAvailable) {
        wasmRustlangLibunwindUnwindResume(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    if (libunwindNapiAvailable) {
        wasmRustlangLibunwindUnwindDeleteException(exception.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    if (libunwindNapiAvailable) MutPtr(wasmRustlangLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmRustlangLibunwindUnwindGetRegionStart(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmRustlangLibunwindUnwindGetTextRelBase(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    if (libunwindNapiAvailable) ConstPtr(wasmRustlangLibunwindUnwindGetDataRelBase(ctx.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    if (libunwindNapiAvailable) ConstPtr(wasmRustlangLibunwindUnwindGetGr(ctx.rawAddress.toString(), regIndex).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    if (libunwindNapiAvailable) {
        wasmRustlangLibunwindUnwindSetGr(ctx.rawAddress.toString(), regIndex, value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    if (libunwindNapiAvailable) {
        wasmRustlangLibunwindUnwindSetIp(ctx.rawAddress.toString(), value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    if (libunwindNapiAvailable) ConstPtr(wasmRustlangLibunwindUnwindGetIpinfo(ctx.rawAddress.toString(), ipBeforeInsn.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    if (libunwindNapiAvailable) wasmRustlangLibunwindGccSpecificHandler(exceptionRecord.rawAddress.toString(), establisherFrame.rawAddress.toString(), contextRecord.rawAddress.toString(), dispatcherContext.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
