@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.posix.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private val libcNewCommonPosixPthreadNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAtfork(0L.toString(), 0L.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, guardsize: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr.rawAddress.toString(), guardsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetinheritsched(attr: ConstPtr, inheritsched: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr.rawAddress.toString(), inheritsched.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetschedpolicy(attr: ConstPtr, policy: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr.rawAddress.toString(), policy.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstack(attr: ConstPtr, stackaddr: MutPtr, stacksize: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, guardsize: SizeT): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr.rawAddress.toString(), guardsize.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetinheritsched(attr: MutPtr, inheritsched: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr.rawAddress.toString(), inheritsched) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr.rawAddress.toString(), param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetschedpolicy(attr: MutPtr, policy: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr.rawAddress.toString(), policy) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstack(attr: MutPtr, stackaddr: MutPtr, stacksize: SizeT): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(attr.rawAddress.toString(), stackaddr.rawAddress.toString(), stacksize.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(barrier.rawAddress.toString(), attr.rawAddress.toString(), count.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(barrier.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, shared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr.rawAddress.toString(), shared.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, shared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr.rawAddress.toString(), shared) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCancel(thread: PthreadT): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCancel(thread.toLong().toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr.rawAddress.toString(), clockId.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr.rawAddress.toString(), clockId.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr.rawAddress.toString(), pshared) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetcpuclockid(thread: PthreadT, clkId: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread.toLong().toString(), clkId.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetschedparam(native: PthreadT, policy: MutPtr, param: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(native.toLong().toString(), policy.rawAddress.toString(), param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKill(thread: PthreadT, sig: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadKill(thread.toLong().toString(), sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexConsistent(mutex: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTimedlock(lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, protocol: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr.rawAddress.toString(), protocol.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr.rawAddress.toString(), pshared.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, robustness: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr.rawAddress.toString(), robustness.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetprotocol(attr: MutPtr, protocol: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr.rawAddress.toString(), protocol) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr.rawAddress.toString(), pshared) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetrobust(attr: MutPtr, robustness: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr.rawAddress.toString(), robustness) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, value: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr.rawAddress.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, value: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr.rawAddress.toString(), value) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadOnce(control: MutPtr, routine: PthreadAtforkPrepareCallback): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadOnce(control.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedparam(native: PthreadT, policy: CInt, param: ConstPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(native.toLong().toString(), policy, param.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetschedprio(native: PthreadT, priority: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(native.toLong().toString(), priority) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSigmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinInit(lock: MutPtr, pshared: CInt): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSpinInit(lock.rawAddress.toString(), pshared) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSpinLock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    if (libcNewCommonPosixPthreadNapiAddon != null) (libcNewCommonPosixPthreadNapiAddon.rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
