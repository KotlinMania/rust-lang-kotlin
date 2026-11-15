@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.common.posix.pthread

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.PthreadT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewCommonPosixPthreadNapiAddon(): Boolean

private val newCommonPosixPthreadNapiAvailable: Boolean = initNewCommonPosixPthreadNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAtfork(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAtfork(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrGetguardsize(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrGetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrGetinheritsched(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrGetschedparam(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrGetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrGetschedpolicy(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrGetstack(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrGetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrSetguardsize(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrSetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrSetinheritsched(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrSetschedparam(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrSetschedparam(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrSetschedpolicy(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadAttrSetstack(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadAttrSetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierDestroy(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierDestroy(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierInit(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierInit(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierWait(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierWait(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierattrDestroy(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierattrGetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierattrInit(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierattrInit(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadBarrierattrSetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCancel(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCancel(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCondattrGetclock(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCondattrGetclock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCondattrGetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCondattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCondattrSetclock(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCondattrSetclock(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCondattrSetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCondattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadCreate(p0, p1, p2, p3)")
private external fun wasmLibcNewCommonPosixPthreadPthreadCreate(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadGetcpuclockid(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadGetcpuclockid(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadGetschedparam(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadGetschedparam(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadKill(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadKill(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexConsistent(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexConsistent(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexTimedlock(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexTimedlock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrGetprotocol(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrGetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrGetrobust(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrSetprotocol(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrSetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadMutexattrSetrobust(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadRwlockattrGetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadRwlockattrSetpshared(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadOnce(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadOnce(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSetschedparam(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSetschedparam(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSetschedprio(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSetschedprio(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSigmask(p0, p1, p2)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSigmask(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSpinDestroy(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSpinDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSpinInit(p0, p1)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSpinInit(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSpinLock(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSpinLock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSpinTrylock(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSpinTrylock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewCommonPosixPthreadPthreadSpinUnlock(p0)")
private external fun wasmLibcNewCommonPosixPthreadPthreadSpinUnlock(p0: String): Int

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAtfork(0L.toString(), 0L.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, guardsize: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr.rawAddress.toString(), guardsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetinheritsched(attr: ConstPtr, inheritsched: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr.rawAddress.toString(), inheritsched.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedpolicy(attr: ConstPtr, policy: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr.rawAddress.toString(), policy.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstack(attr: ConstPtr, stackaddr: MutPtr, stacksize: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrGetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, guardsize: SizeT): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr.rawAddress.toString(), guardsize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetinheritsched(attr: MutPtr, inheritsched: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr.rawAddress.toString(), inheritsched) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedpolicy(attr: MutPtr, policy: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr.rawAddress.toString(), policy) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstack(attr: MutPtr, stackaddr: MutPtr, stacksize: SizeT): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadAttrSetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierInit(barrier.rawAddress.toString(), attr.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierWait(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, shared: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr.rawAddress.toString(), shared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, shared: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr.rawAddress.toString(), shared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCancel(thread: PthreadT): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCancel(thread.toLong().toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr.rawAddress.toString(), clockId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr.rawAddress.toString(), clockId.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetcpuclockid(thread: PthreadT, clkId: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread.toLong().toString(), clkId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetschedparam(native: PthreadT, policy: MutPtr, param: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadGetschedparam(native.toLong().toString(), policy.rawAddress.toString(), param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKill(thread: PthreadT, sig: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadKill(thread.toLong().toString(), sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexConsistent(mutex: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTimedlock(lock: MutPtr, abstime: ConstPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, protocol: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr.rawAddress.toString(), protocol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, robustness: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr.rawAddress.toString(), robustness.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetprotocol(attr: MutPtr, protocol: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr.rawAddress.toString(), protocol) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetrobust(attr: MutPtr, robustness: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr.rawAddress.toString(), robustness) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, value: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, value: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr.rawAddress.toString(), value) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadOnce(control: MutPtr, routine: PthreadAtforkPrepareCallback): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadOnce(control.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedparam(native: PthreadT, policy: CInt, param: ConstPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSetschedparam(native.toLong().toString(), policy, param.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedprio(native: PthreadT, priority: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSetschedprio(native.toLong().toString(), priority) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSigmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSpinDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinInit(lock: MutPtr, pshared: CInt): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSpinInit(lock.rawAddress.toString(), pshared) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSpinLock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSpinTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    if (newCommonPosixPthreadNapiAvailable) wasmLibcNewCommonPosixPthreadPthreadSpinUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
