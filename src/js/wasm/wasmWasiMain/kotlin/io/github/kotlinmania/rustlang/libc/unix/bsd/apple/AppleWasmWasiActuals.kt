@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.b64.aarch64.BooleanT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleGetMACHTASKSELF")
private external fun wasiRustlangLibcUnixBsdAppleGetMACHTASKSELF(): Int

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSetMACHTASKSELF")
private external fun wasiRustlangLibcUnixBsdAppleSetMACHTASKSELF(value: Int): Unit

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT
    get() = wasiRustlangLibcUnixBsdAppleGetMACHTASKSELF().toUInt()
    set(value) {
        wasiRustlangLibcUnixBsdAppleSetMACHTASKSELF(value.toInt())
    }

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleGetVMPAGESIZE")
private external fun wasiRustlangLibcUnixBsdAppleGetVMPAGESIZE(): Long

public actual val VM_PAGE_SIZE: VmSizeT
    get() = wasiRustlangLibcUnixBsdAppleGetVMPAGESIZE().toULong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSetgrent")
private external fun wasiRustlangLibcUnixBsdAppleSetgrent(): Unit

public actual fun setgrent(): Unit =
    wasiRustlangLibcUnixBsdAppleSetgrent()

public actual fun aioRead(aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioRead")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioWrite")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioFsync")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioError")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioReturn")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioSuspend")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAioCancel")

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleChflags")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleFchflags")
private external fun wasiRustlangLibcUnixBsdAppleFchflags(fd: Int, flags: Int): Int

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    wasiRustlangLibcUnixBsdAppleFchflags(fd, flags.toInt()).toInt()

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleLchflags")

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleLchmod")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleClockGetres")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleClockGettime")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleLioListio")

public actual fun dirfd(dirp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleDirfd")

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleLutimes")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGettimeofday")

public actual fun getutxent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetutxent")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetutxid")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetutxline")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePututxline")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSetutxent")
private external fun wasiRustlangLibcUnixBsdAppleSetutxent(): Unit

public actual fun setutxent(): Unit =
    wasiRustlangLibcUnixBsdAppleSetutxent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleEndutxent")
private external fun wasiRustlangLibcUnixBsdAppleEndutxent(): Unit

public actual fun endutxent(): Unit =
    wasiRustlangLibcUnixBsdAppleEndutxent()

public actual fun utmpxname(file: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleUtmpxname")

public actual fun asctime(tm: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAsctime")

public actual fun ctime(clock: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCtime")

public actual fun getdate(datestr: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetdate")

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleStrptime")

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleAsctimeR")

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCtimeR")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetnameinfo")

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMincore")

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSysctlnametomib")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMprotect")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSemget")
private external fun wasiRustlangLibcUnixBsdAppleSemget(key: Int, nsems: Int, semflg: Int): Int

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    wasiRustlangLibcUnixBsdAppleSemget(key, nsems, semflg).toInt()

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSemctl")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSemop")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleShmOpen")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFtok")

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleShmat")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleShmdt")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleShmctl")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleShmget")
private external fun wasiRustlangLibcUnixBsdAppleShmget(key: Int, size: Long, shmflg: Int): Int

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    wasiRustlangLibcUnixBsdAppleShmget(key, size.toLong(), shmflg).toInt()

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSysctl")

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSysctlbyname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleMachAbsoluteTime")
private external fun wasiRustlangLibcUnixBsdAppleMachAbsoluteTime(): Long

public actual fun machAbsoluteTime(): ULong =
    wasiRustlangLibcUnixBsdAppleMachAbsoluteTime().toULong()

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMachTimebaseInfo")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleMachHostSelf")
private external fun wasiRustlangLibcUnixBsdAppleMachHostSelf(): Int

public actual fun machHostSelf(): MachPortT =
    wasiRustlangLibcUnixBsdAppleMachHostSelf().toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleMachThreadSelf")
private external fun wasiRustlangLibcUnixBsdAppleMachThreadSelf(): Int

public actual fun machThreadSelf(): MachPortT =
    wasiRustlangLibcUnixBsdAppleMachThreadSelf().toUInt()

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp")

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadAttrGetscope")

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadAttrGetstackaddr")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadAttrGetdetachstate")

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadAttrSetscope")

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadAttrSetstackaddr")

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadSetnameNp")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadGetnameNp")

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadMachThreadNp")

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT): PthreadT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadFromMachThreadNp")

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadGetStackaddrNp")

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadGetStacksizeNp")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdApplePthreadMainNp")
private external fun wasiRustlangLibcUnixBsdApplePthreadMainNp(): Int

public actual fun pthreadMainNp(): CInt =
    wasiRustlangLibcUnixBsdApplePthreadMainNp().toInt()

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadThreadidNp")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdApplePthreadJitWriteProtectNp")
private external fun wasiRustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled: Int): Unit

public actual fun pthreadJitWriteProtectNp(enabled: CInt): Unit =
    wasiRustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp")
private external fun wasiRustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp(): Int

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    wasiRustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp().toInt()

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.unix.bsd.apple.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp")
private external fun wasiRustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp(): Unit

public actual fun pthreadJitWriteFreezeCallbacksNp(): Unit =
    wasiRustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePthreadCpuNumberNp")

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSyncWaitOnAddress")

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline")

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout")

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny")

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll")

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsUnfairLockLock")
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsUnfairLockTrylock")

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsUnfairLockUnlock")
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner")
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner")
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsLogCreate")

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, tpe: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogTypeT): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsLogTypeEnabled")

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer")

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSignpostIdGenerate")

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOsSignpostEnabled")

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleThreadPolicySet")

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleThreadPolicyGet")

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleThreadInfo")

public actual fun error(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleError")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktrace")

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktraceSymbols")

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktraceSymbolsFd")
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktraceFromFp")

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktraceImageOffsets")
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBacktraceAsync")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleStatfs")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFstatfs")

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleKevent")

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleKevent64")

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMount")

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFmount")

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePtrace")

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleQuotactl")

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSethostname")

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSendfile")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFutimens")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleUtimensat")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleOpenpty")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleForkpty")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleLoginTty")
private external fun wasiRustlangLibcUnixBsdAppleLoginTty(fd: Int): Int

public actual fun loginTty(fd: CInt): CInt =
    wasiRustlangLibcUnixBsdAppleLoginTty(fd).toInt()

public actual fun duplocale(base: LocaleT): LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleDuplocale")

public actual fun freelocale(loc: LocaleT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFreelocale")

public actual fun localeconvL(loc: LocaleT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleLocaleconvL")

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNewlocale")

public actual fun uselocale(loc: LocaleT): LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleUselocale")

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleQuerylocale")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleGetpriority")
private external fun wasiRustlangLibcUnixBsdAppleGetpriority(which: Int, who: Int): Int

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT): CInt =
    wasiRustlangLibcUnixBsdAppleGetpriority(which, who.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSetpriority")
private external fun wasiRustlangLibcUnixBsdAppleSetpriority(which: Int, who: Int, prio: Int): Int

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT, prio: CInt): CInt =
    wasiRustlangLibcUnixBsdAppleSetpriority(which, who.toInt(), prio).toInt()

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetdomainname")

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSetdomainname")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePreadv")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePwritev")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetxattr")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFgetxattr")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSetxattr")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFsetxattr")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleListxattr")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFlistxattr")

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleRemovexattr")

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleRenamexNp")

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleRenameatxNp")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFremovexattr")

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetgrouplist")

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleInitgroups")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleWaitid")

public actual fun brk(addr: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBrk")

public actual fun sbrk(increment: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSbrk")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSettimeofday")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleDyldImageCount")
private external fun wasiRustlangLibcUnixBsdAppleDyldImageCount(): Int

public actual fun dyldImageCount(): UInt =
    wasiRustlangLibcUnixBsdAppleDyldImageCount().toUInt()

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleDyldGetImageHeader")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide")
private external fun wasiRustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex: Int): Long

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    wasiRustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex.toInt()).toLong()

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleDyldGetImageName")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawn")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnp")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrInit")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrDestroy")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetflags")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetflags")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup")

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp")

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp")

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp")

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsInit")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp")

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir")

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir")

public actual fun uname(buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleUname")

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleConnectx")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleDisconnectx")
private external fun wasiRustlangLibcUnixBsdAppleDisconnectx(socket: Int, associd: Int, connid: Int): Int

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    wasiRustlangLibcUnixBsdAppleDisconnectx(socket, associd.toInt(), connid.toInt()).toInt()

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNtpAdjtime")

public actual fun ntpGettime(buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNtpGettime")

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetmntinfo")

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetfsstat")

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleClonefile")

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleClonefileat")

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFclonefileat")

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCopyfile")

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFcopyfile")

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCopyfileStateFree")

public actual fun copyfileStateAlloc(): CopyfileStateT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCopyfileStateAlloc")

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCopyfileStateGet")

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCopyfileStateSet")

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.unix.bsd.apple.MachErrorT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMachErrorString")

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMemsetS")

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMemsetPattern4")
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMemsetPattern8")
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMemsetPattern16")
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleStrtonum")

public actual fun mstats(): Mstats =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMstats")

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocPrintf")
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneCheck")

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZonePrint")
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneStatistics")
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneLog")
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo")
}

public actual fun mallocDefaultZone(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocDefaultZone")

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneFromPtr")

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneMalloc")

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneValloc")

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneCalloc")

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneRealloc")

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocZoneFree")
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGethostuuid")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleGethostid")
private external fun wasiRustlangLibcUnixBsdAppleGethostid(): Long

public actual fun gethostid(): CLong =
    wasiRustlangLibcUnixBsdAppleGethostid().toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleSethostid")
private external fun wasiRustlangLibcUnixBsdAppleSethostid(hostid: Long): Unit

public actual fun sethostid(hostid: CLong): Unit =
    wasiRustlangLibcUnixBsdAppleSethostid(hostid)

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.CCRNGStatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleCCRandomGenerateBytes")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetentropy")

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNsgetExecutablePath")

public actual fun nsgetArgv(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNsgetArgv")

public actual fun nsgetArgc(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNsgetArgc")

public actual fun nsgetEnviron(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNsgetEnviron")

public actual fun nsgetProgname(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleNsgetProgname")

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmSizeT, mask: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.unix.bsd.apple.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.unix.bsd.apple.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, maxProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, inheritance: io.github.kotlinmania.rustlang.unix.bsd.apple.VmInheritT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMachVmMap")

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleVmAllocate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleVmDeallocate")
private external fun wasiRustlangLibcUnixBsdAppleVmDeallocate(targetTask: Int, address: Long, size: Long): Int

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    wasiRustlangLibcUnixBsdAppleVmDeallocate(targetTask.toInt(), address.toLong(), size.toLong()).toInt()

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleHostStatistics64")

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleHostProcessorInfo")

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleTaskForPid")

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleTaskInfo")

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, ledgers: io.github.kotlinmania.rustlang.unix.bsd.apple.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleTaskCreate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleTaskTerminate")
private external fun wasiRustlangLibcUnixBsdAppleTaskTerminate(targetTask: Int): Int

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    wasiRustlangLibcUnixBsdAppleTaskTerminate(targetTask.toInt()).toInt()

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleTaskThreads")

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleHostStatistics")

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSysdirStartSearchPathEnumeration")

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration")

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetattrlist")

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFgetattrlist")

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetattrlistat")

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSetattrlist")

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFsetattrlist")

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleSetattrlistat")

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleGetattrlistbulk")

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMallocSize")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleMallocGoodSize")
private external fun wasiRustlangLibcUnixBsdAppleMallocGoodSize(size: Long): Long

public actual fun mallocGoodSize(size: SizeT): SizeT =
    wasiRustlangLibcUnixBsdAppleMallocGoodSize(size.toLong()).toULong()

public actual fun dirname(path: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleDirname")

public actual fun basename(path: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleBasename")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMkfifoat")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMknodat")

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleFreadlink")

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleExecvP")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleQsortR")
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleClockSettime")

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcListpids")

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcListallpids")

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcListpgrppids")

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcListchildpids")

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcPidinfo")

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcPidfdinfo")

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcPidfileportinfo")

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcPidpath")

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcName")

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcRegionfilename")

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcKmsgbuf")

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcLibversion")

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleProcPidRusage")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleProcSetNoSmt")
private external fun wasiRustlangLibcUnixBsdAppleProcSetNoSmt(): Int

public actual fun procSetNoSmt(): CInt =
    wasiRustlangLibcUnixBsdAppleProcSetNoSmt().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleProcSetthreadNoSmt")
private external fun wasiRustlangLibcUnixBsdAppleProcSetthreadNoSmt(): Int

public actual fun procSetthreadNoSmt(): CInt =
    wasiRustlangLibcUnixBsdAppleProcSetthreadNoSmt().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleProcSetCsm")
private external fun wasiRustlangLibcUnixBsdAppleProcSetCsm(flags: Int): Int

public actual fun procSetCsm(flags: UInt): CInt =
    wasiRustlangLibcUnixBsdAppleProcSetCsm(flags.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAppleProcSetthreadCsm")
private external fun wasiRustlangLibcUnixBsdAppleProcSetthreadCsm(flags: Int): Int

public actual fun procSetthreadCsm(flags: UInt): CInt =
    wasiRustlangLibcUnixBsdAppleProcSetthreadCsm(flags.toInt()).toInt()

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleMemmem")

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, flavor: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleTaskSetInfo")

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleIconvOpen")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleIconv")

public actual fun iconvClose(cd: IconvT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAppleIconvClose")
