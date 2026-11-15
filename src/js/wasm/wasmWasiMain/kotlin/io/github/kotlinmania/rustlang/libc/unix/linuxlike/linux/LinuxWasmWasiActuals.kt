@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetspnamR")

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqOpen")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMqClose")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMqClose(mqd: Int): Int

public actual fun mqClose(mqd: MqdT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxMqClose(mqd).toInt()

public actual fun mqUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqUnlink")

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqReceive")

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqTimedreceive")

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqSend")

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqTimedsend")

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqGetattr")

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMqSetattr")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMrand48")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMrand48(): Long

public actual fun mrand48(): CLong =
    wasiRustlangLibcUnixLinuxlikeLinuxMrand48().toLong()

public actual fun seed48(xseed: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSeed48")

public actual fun lcong48(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLcong48")
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLutimes")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxShmOpen")

public actual fun shmUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxShmUnlink")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFtok")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSemget")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSemget(key: Int, nsems: Int, semflag: Int): Int

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSemget(key, nsems, semflag).toInt()

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSemop")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSemctl")

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMsgctl")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxMsgget")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxMsgget(key: Int, msgflg: Int): Int

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxMsgget(key, msgflg).toInt()

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMsgrcv")

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMsgsnd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxFallocate")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxFallocate(fd: Int, mode: Int, offset: Long, len: Long): Int

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxFallocate(fd, mode, offset, len).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxPosixFallocate")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd: Int, offset: Long, len: Long): Int

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd, offset, len).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxReadahead")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxReadahead(fd: Int, offset: Long, count: Long): Long

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    wasiRustlangLibcUnixLinuxlikeLinuxReadahead(fd, offset, count.toLong()).toLong()

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetxattr")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLgetxattr")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFgetxattr")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSetxattr")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLsetxattr")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFsetxattr")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxListxattr")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLlistxattr")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFlistxattr")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxRemovexattr")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxLremovexattr")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFremovexattr")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSignalfd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxTimerfdCreate")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid: Int, flags: Int): Int

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid.toInt(), flags).toInt()

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxTimerfdGettime")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxTimerfdSettime")

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxQuotactl")

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxEpollPwait")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxDup3")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxDup3(oldfd: Int, newfd: Int, flags: Int): Int

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags).toInt()

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSigtimedwait")

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSigwaitinfo")

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxAccept4")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxReboot")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxReboot(howTo: Int): Int

public actual fun reboot(howTo: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxReboot(howTo).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSetfsgid")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSetfsgid(gid: Int): Int

public actual fun setfsgid(gid: GidT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSetfsgid(gid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSetfsuid")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSetfsuid(uid: Int): Int

public actual fun setfsuid(uid: UidT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSetfsuid(uid).toInt()

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMkfifoat")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSyncFileRange")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd: Int, offset: Long, nbytes: Long, flags: Int): Int

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd, offset, nbytes, flags.toInt()).toInt()

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixMadvise")

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMremap")

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxRemapFilePages")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxMkstemps")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxVhangup")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxVhangup(): Int

public actual fun vhangup(): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxVhangup().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSync")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSync(): Unit

public actual fun sync(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxSync()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSyncfs")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSyncfs(fd: Int): Int

public actual fun syncfs(fd: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSyncfs(fd).toInt()

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSyscall")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxEpollCreate")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxEpollCreate(size: Int): Int

public actual fun epollCreate(size: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxEpollCreate(size).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxEpollCreate1")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags: Int): Int

public actual fun epollCreate1(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags).toInt()

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxEpollWait")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxEpollCtl")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxUnshare")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxUnshare(flags: Int): Int

public actual fun unshare(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxUnshare(flags).toInt()

public actual fun umount(target: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxUmount")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxTee")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxTee(fdIn: Int, fdOut: Int, len: Long, flags: Int): Long

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    wasiRustlangLibcUnixLinuxlikeLinuxTee(fdIn, fdOut, len.toLong(), flags.toInt()).toLong()

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSplice")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxEventfd")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxEventfd(initval: Int, flags: Int): Int

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxEventfd(initval.toInt(), flags).toInt()

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxEventfdRead")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxEventfdWrite")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd: Int, value: Long): Int

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd, value.toLong()).toInt()

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSchedSetparam")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSetns")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSetns(fd: Int, nstype: Int): Int

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSetns(fd, nstype).toInt()

public actual fun swapoff(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSwapoff")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxVmsplice")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxPersonality")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxPersonality(persona: Long): Int

public actual fun personality(persona: CUlong): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxPersonality(persona.toLong()).toInt()

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSchedGetparam")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxClone")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid: Int): Int

public actual fun schedGetscheduler(pid: PidT): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid).toInt()

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxClockNanosleep")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxUmount2")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSwapon")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSendfile")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSigaltstack")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxGetdtablesize")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxGetdtablesize(): Int

public actual fun getdtablesize(): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxGetdtablesize().toInt()

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetgrouplist")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawn")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnp")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup")

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy")

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy")

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam")

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFreadUnlocked")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd: Int, wd: Int): Int

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxInotifyInit")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxInotifyInit(): Int

public actual fun inotifyInit(): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxInotifyInit().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxInotifyInit1")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags: Int): Int

public actual fun inotifyInit1(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags).toInt()

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxFanotifyInit")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags: Int, eventFFlags: Int): Int

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags.toInt(), eventFFlags.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxGethostid")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxGethostid(): Long

public actual fun gethostid(): CLong =
    wasiRustlangLibcUnixLinuxlikeLinuxGethostid().toLong()

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxKlogctl")

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxNameToHandleAt")

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt")

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetnetbyname")

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr")

public actual fun getnetent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxGetnetent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxSetnetent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen: Int): Unit

public actual fun setnetent(stayopen: CInt): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen)

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxEndnetent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxEndnetent(): Unit

public actual fun endnetent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxEndnetent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxFallocate64")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxFallocate64(fd: Int, mode: Int, offset: Long, len: Long): Int

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxFallocate64(fd, mode, offset, len).toInt()

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFgetpos64")

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxFopen64")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxPosixFallocate64")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd: Int, offset: Long, len: Long): Int

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd, offset, len).toInt()

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxSendfile64")

public actual fun tmpfile64(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxTmpfile64")
