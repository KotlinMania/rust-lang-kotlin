@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.common.posix.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcNewCommonPosixPthreadNapiAddon(): Boolean

private val libcNewCommonPosixPthreadNapiAvailable: Boolean = initLibcNewCommonPosixPthreadNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAtfork(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAtfork(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierInit(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierWait(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCancel(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCancel(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadCreate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadCreate(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadGetschedparam(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadKill(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadKill(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadOnce(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadOnce(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSetschedparam(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSetschedprio(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSigmask(p0, p1, p2)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSigmask(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSpinInit(p0, p1)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSpinInit(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSpinLock(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSpinLock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(p0)")
private external fun wasmRustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(p0: String): Int

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAtfork(0L.toString(), 0L.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, guardsize: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr.rawAddress.toString(), guardsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetinheritsched(attr: ConstPtr, inheritsched: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr.rawAddress.toString(), inheritsched.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedpolicy(attr: ConstPtr, policy: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr.rawAddress.toString(), policy.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstack(attr: ConstPtr, stackaddr: MutPtr, stacksize: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, guardsize: SizeT): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr.rawAddress.toString(), guardsize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetinheritsched(attr: MutPtr, inheritsched: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr.rawAddress.toString(), inheritsched) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedpolicy(attr: MutPtr, policy: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr.rawAddress.toString(), policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstack(attr: MutPtr, stackaddr: MutPtr, stacksize: SizeT): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierInit(barrier.rawAddress.toString(), attr.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierWait(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, shared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr.rawAddress.toString(), shared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, shared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr.rawAddress.toString(), shared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCancel(thread: PthreadT): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCancel(thread.toLong().toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr.rawAddress.toString(), clockId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr.rawAddress.toString(), clockId.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetcpuclockid(thread: PthreadT, clkId: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread.toLong().toString(), clkId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetschedparam(native: PthreadT, policy: MutPtr, param: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadGetschedparam(native.toLong().toString(), policy.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKill(thread: PthreadT, sig: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadKill(thread.toLong().toString(), sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexConsistent(mutex: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTimedlock(lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, protocol: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr.rawAddress.toString(), protocol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, robustness: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr.rawAddress.toString(), robustness.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetprotocol(attr: MutPtr, protocol: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr.rawAddress.toString(), protocol) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetrobust(attr: MutPtr, robustness: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr.rawAddress.toString(), robustness) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, value: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, value: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr.rawAddress.toString(), value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadOnce(control: MutPtr, routine: PthreadAtforkPrepareCallback): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadOnce(control.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedparam(native: PthreadT, policy: CInt, param: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSetschedparam(native.toLong().toString(), policy, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedprio(native: PthreadT, priority: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSetschedprio(native.toLong().toString(), priority) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSigmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinInit(lock: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSpinInit(lock.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSpinLock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAvailable) wasmRustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
