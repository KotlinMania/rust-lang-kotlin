@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.types.UnwindWord

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibunwindNapiAddon(): Boolean

private val libunwindNapiAvailable: Boolean = initLibunwindNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindResume(p0)")
private external fun wasmUnwindLibunwindUnwindResume(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindDeleteException(p0)")
private external fun wasmUnwindLibunwindUnwindDeleteException(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetLanguageSpecificData(p0)")
private external fun wasmUnwindLibunwindUnwindGetLanguageSpecificData(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetRegionStart(p0)")
private external fun wasmUnwindLibunwindUnwindGetRegionStart(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetTextRelBase(p0)")
private external fun wasmUnwindLibunwindUnwindGetTextRelBase(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetDataRelBase(p0)")
private external fun wasmUnwindLibunwindUnwindGetDataRelBase(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetGr(p0, p1)")
private external fun wasmUnwindLibunwindUnwindGetGr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindSetGr(p0, p1, p2)")
private external fun wasmUnwindLibunwindUnwindSetGr(p0: String, p1: Int, p2: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindSetIp(p0, p1)")
private external fun wasmUnwindLibunwindUnwindSetIp(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.unwindLibunwindUnwindGetIpinfo(p0, p1)")
private external fun wasmUnwindLibunwindUnwindGetIpinfo(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.unwindLibunwindGccSpecificHandler(p0, p1, p2, p3, p4)")
private external fun wasmUnwindLibunwindGccSpecificHandler(p0: String, p1: String, p2: String, p3: String, p4: String): Int

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

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    if (libunwindNapiAvailable) ConstPtr(wasmUnwindLibunwindUnwindGetGr(ctx.rawAddress.toString(), regIndex).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    if (libunwindNapiAvailable) {
        wasmUnwindLibunwindUnwindSetGr(ctx.rawAddress.toString(), regIndex, value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    if (libunwindNapiAvailable) {
        wasmUnwindLibunwindUnwindSetIp(ctx.rawAddress.toString(), value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    if (libunwindNapiAvailable) ConstPtr(wasmUnwindLibunwindUnwindGetIpinfo(ctx.rawAddress.toString(), ipBeforeInsn.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    if (libunwindNapiAvailable) wasmUnwindLibunwindGccSpecificHandler(exceptionRecord.rawAddress.toString(), establisherFrame.rawAddress.toString(), contextRecord.rawAddress.toString(), dispatcherContext.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
