@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.posix.pthread

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAtfork
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierInit
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierWait
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCancel
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadCreate
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadGetschedparam
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadKill
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadOnce
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSetschedparam
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSetschedprio
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSigmask
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSpinInit
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSpinLock
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock
import io.github.kotlinmania.rustlang.libc.new.common.posix.pthread.cinterop.rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAtfork(0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, guardsize: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr.rawAddress.toCPointer<ByteVar>(), guardsize.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetinheritsched(attr: ConstPtr, inheritsched: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr.rawAddress.toCPointer<ByteVar>(), inheritsched.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr.rawAddress.toCPointer<ByteVar>(), param.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetschedpolicy(attr: ConstPtr, policy: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr.rawAddress.toCPointer<ByteVar>(), policy.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetstack(attr: ConstPtr, stackaddr: MutPtr, stacksize: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(attr.rawAddress.toCPointer<ByteVar>(), stackaddr.rawAddress.toCPointer<ByteVar>(), stacksize.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetguardsize(attr: MutPtr, guardsize: SizeT): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr.rawAddress.toCPointer<ByteVar>(), guardsize)

public actual fun pthreadAttrSetinheritsched(attr: MutPtr, inheritsched: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr.rawAddress.toCPointer<ByteVar>(), inheritsched)

public actual fun pthreadAttrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr.rawAddress.toCPointer<ByteVar>(), param.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetschedpolicy(attr: MutPtr, policy: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr.rawAddress.toCPointer<ByteVar>(), policy)

public actual fun pthreadAttrSetstack(attr: MutPtr, stackaddr: MutPtr, stacksize: SizeT): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(attr.rawAddress.toCPointer<ByteVar>(), stackaddr.rawAddress.toCPointer<ByteVar>(), stacksize)

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(barrier.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>(), count)

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(barrier.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, shared: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), shared.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, shared: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), shared)

public actual fun pthreadCancel(thread: PthreadT): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCancel(thread.toLong().toCPointer<ByteVar>())

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr.rawAddress.toCPointer<ByteVar>(), clockId.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), pshared.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr.rawAddress.toCPointer<ByteVar>(), clockId)

public actual fun pthreadCondattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), pshared)

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadCreate(native.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetcpuclockid(thread: PthreadT, clkId: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread.toLong().toCPointer<ByteVar>(), clkId.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetschedparam(native: PthreadT, policy: MutPtr, param: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(native.toLong().toCPointer<ByteVar>(), policy.rawAddress.toCPointer<ByteVar>(), param.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadKill(thread: PthreadT, sig: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadKill(thread.toLong().toCPointer<ByteVar>(), sig)

public actual fun pthreadMutexConsistent(mutex: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexTimedlock(lock: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, protocol: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr.rawAddress.toCPointer<ByteVar>(), protocol.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), pshared.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, robustness: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr.rawAddress.toCPointer<ByteVar>(), robustness.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrSetprotocol(attr: MutPtr, protocol: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr.rawAddress.toCPointer<ByteVar>(), protocol)

public actual fun pthreadMutexattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), pshared)

public actual fun pthreadMutexattrSetrobust(attr: MutPtr, robustness: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr.rawAddress.toCPointer<ByteVar>(), robustness)

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, value: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, value: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), value)

public actual fun pthreadOnce(control: MutPtr, routine: PthreadAtforkPrepareCallback): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadOnce(control.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadSetschedparam(native: PthreadT, policy: CInt, param: ConstPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(native.toLong().toCPointer<ByteVar>(), policy, param.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetschedprio(native: PthreadT, priority: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(native.toLong().toCPointer<ByteVar>(), priority)

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSigmask(how, set.rawAddress.toCPointer<ByteVar>(), oldset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinInit(lock: MutPtr, pshared: CInt): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSpinInit(lock.rawAddress.toCPointer<ByteVar>(), pshared)

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSpinLock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(lock.rawAddress.toCPointer<ByteVar>())
