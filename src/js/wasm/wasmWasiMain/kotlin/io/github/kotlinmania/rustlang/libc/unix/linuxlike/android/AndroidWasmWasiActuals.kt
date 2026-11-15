@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
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
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetpwent")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetpwent(): Unit

public actual fun setpwent(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidSetpwent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEndpwent")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEndpwent(): Unit

public actual fun endpwent(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidEndpwent()

public actual fun getpwent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetpwent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetgrent")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetgrent(): Unit

public actual fun setgrent(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidSetgrent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEndgrent")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEndgrent(): Unit

public actual fun endgrent(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidEndgrent()

public actual fun getgrent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrent")

public actual fun getrlimit64(resource: CInt, rlim: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetrlimit64")

public actual fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSetrlimit64")

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetrlimit")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSetrlimit")

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPrlimit")

public actual fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPrlimit64")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidStrerrorR")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGettimeofday")

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMlock2")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMadvise")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMsync")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMprotect")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SizeT, serv: MutPtr, servlen: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetnameinfo")

public actual fun preadv(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPreadv")

public actual fun pwritev(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPwritev")

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidProcessVmReadv")

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidProcessVmWritev")

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPtrace")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidGetpriority")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidGetpriority(which: Int, who: Int): Int

public actual fun getpriority(which: CInt, who: IdT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidGetpriority(which, who.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetpriority")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetpriority(which: Int, who: Int, prio: Int): Int

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSetpriority(which, who.toInt(), prio).toInt()

public actual fun schedCpualloc(count: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedCpualloc")

public actual fun schedCpufree(set: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedCpufree")
}

public actual fun schedCpucount(setsize: SizeT, set: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedCpucount")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSchedGetcpu")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSchedGetcpu(): Int

public actual fun schedGetcpu(): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSchedGetcpu().toInt()

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMallinfo")

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMallocInfo")

public actual fun mallocUsableSize(ptr: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMallocUsableSize")

public actual fun utmpname(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidUtmpname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetutent")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetutent(): Unit

public actual fun setutent(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidSetutent()

public actual fun getutent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetutent")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSeekdir")
}

public actual fun telldir(dirp: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTelldir")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidFallocate")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidFallocate(fd: Int, mode: Int, offset: Long, len: Long): Int

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidFallocate(fd, mode, offset, len).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidFallocate64")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidFallocate64(fd: Int, mode: Int, offset: Long, len: Long): Int

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidFallocate64(fd, mode, offset, len).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidPosixFallocate")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd: Int, offset: Long, len: Long): Int

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd, offset, len).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidPosixFallocate64")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd: Int, offset: Long, len: Long): Int

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd, offset, len).toInt()

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetxattr")

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidLgetxattr")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFgetxattr")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSetxattr")

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidLsetxattr")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFsetxattr")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidListxattr")

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidLlistxattr")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFlistxattr")

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRemovexattr")

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidLremovexattr")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFremovexattr")

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSignalfd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidTimerfdCreate")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock: Int, flags: Int): Int

public actual fun timerfdCreate(clock: ClockidT, flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock.toInt(), flags).toInt()

public actual fun timerfdGettime(fd: CInt, currentValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerfdGettime")

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerfdSettime")

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerCreate")

public actual fun timerDelete(timerid: TimerT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerDelete")

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun")

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerGettime")

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidTimerSettime")

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSyscall")

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity")

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEpollCreate")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEpollCreate(size: Int): Int

public actual fun epollCreate(size: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidEpollCreate(size).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEpollCreate1")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags: Int): Int

public actual fun epollCreate1(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags).toInt()

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidEpollWait")

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidEpollCtl")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidUnshare")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidUnshare(flags: Int): Int

public actual fun unshare(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidUnshare(flags).toInt()

public actual fun umount(target: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidUmount")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy: Int): Int

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidTee")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidTee(fdIn: Int, fdOut: Int, len: Long, flags: Int): Long

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    wasiRustlangLibcUnixLinuxlikeAndroidTee(fdIn, fdOut, len.toLong(), flags.toInt()).toLong()

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSettimeofday")

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSplice")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEventfd")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEventfd(initval: Int, flags: Int): Int

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidEventfd(initval.toInt(), flags).toInt()

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidEventfdRead")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidEventfdWrite")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd: Int, value: Long): Int

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd, value.toLong()).toInt()

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval")

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSemTimedwait")

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSemGetvalue")

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedSetparam")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetns")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetns(fd: Int, nstype: Int): Int

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSetns(fd, nstype).toInt()

public actual fun swapoff(puath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSwapoff")

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidVmsplice")

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMount")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidPersonality")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidPersonality(persona: Int): Int

public actual fun personality(persona: CUint): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidPersonality(persona.toInt()).toInt()

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPrctl")

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedGetparam")

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPpoll")

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidClone")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid: Int): Int

public actual fun schedGetscheduler(pid: PidT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid).toInt()

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidClockNanosleep")

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSethostname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy: Int): Int

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy).toInt()

public actual fun sysinfo(info: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSysinfo")

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidUmount2")

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSwapon")

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler")

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSendfile")

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSendfile64")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetfsgid")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetfsgid(gid: Int): Int

public actual fun setfsgid(gid: GidT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSetfsgid(gid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSetfsuid")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSetfsuid(uid: Int): Int

public actual fun setfsuid(uid: UidT): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSetfsuid(uid).toInt()

public actual fun sigsuspend(mask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSigsuspend")

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrgidR")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSigaltstack")

public actual fun semClose(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSemClose")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrnamR")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSemOpen")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrnam")

public actual fun semUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSemUnlink")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidDaemon")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidDaemon(nochdir: Int, noclose: Int): Int

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidDaemon(nochdir, noclose).toInt()

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSigtimedwait")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSigwait")

public actual fun getgrgid(gid: GidT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrgid")

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetgrouplist")

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidInitgroups")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPopen")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFaccessat")

public actual fun errno(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidErrno")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd: Int, wd: Int): Int

public actual fun inotifyRmWatch(fd: CInt, wd: UInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd, wd.toInt()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidInotifyInit")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidInotifyInit(): Int

public actual fun inotifyInit(): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidInotifyInit().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidInotifyInit1")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags: Int): Int

public actual fun inotifyInit1(flags: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags).toInt()

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRegcomp")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRegexec")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRegerror")

public actual fun regfree(preg: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRegfree")
}

public actual fun androidSetAbortMessage(msg: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidGettid")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidGettid(): Int

public actual fun gettid(): PidT =
    wasiRustlangLibcUnixLinuxlikeAndroidGettid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidTgkill")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidTgkill(tgid: Int, tid: Int, sig: Int): Int

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidTgkill(tgid, tid, sig).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidGetauxval")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidGetauxval(type: Long): Long

public actual fun getauxval(type: CUlong): CUlong =
    wasiRustlangLibcUnixLinuxlikeAndroidGetauxval(type.toLong()).toULong()

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetrandom")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetentropy")

public actual fun systemPropertySet(name: ConstPtr, value: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSystemPropertySet")

public actual fun systemPropertyGet(name: ConstPtr, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet")

public actual fun systemPropertyFind(name: ConstPtr): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind")

public actual fun systemPropertyFindNth(n: CUint): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth")

public actual fun systemPropertyForeach(callback: SystemPropertyForeachCallbackCallback, cookie: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach")

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidArc4random")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidArc4random(): Int

public actual fun arc4random(): UInt =
    wasiRustlangLibcUnixLinuxlikeAndroidArc4random().toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidArc4randomUniform")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound: Int): Int

public actual fun arc4randomUniform(upperBound: UInt): UInt =
    wasiRustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound.toInt()).toUInt()

public actual fun arc4randomBuf(buf: MutPtr, n: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidArc4randomBuf")
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidReallocarray")

public actual fun dirname(path: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidDirname")

public actual fun basename(path: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidBasename")

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidGetoptLong")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSync")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSync(): Unit

public actual fun sync(): Unit =
    wasiRustlangLibcUnixLinuxlikeAndroidSync()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeAndroidSyncfs")
private external fun wasiRustlangLibcUnixLinuxlikeAndroidSyncfs(fd: Int): Int

public actual fun syncfs(fd: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeAndroidSyncfs(fd).toInt()

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMemmem")

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFreadUnlocked")

public actual fun fwriteUnlocked(buf: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked")

public actual fun fflushUnlocked(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFflushUnlocked")

public actual fun fgetsUnlocked(buf: MutPtr, size: CInt, stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked")

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidKlogctl")

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidMemfdCreate")

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidRenameat2")

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPreadv2")

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeAndroidPwritev2")
