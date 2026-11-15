@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level: Int): Int

public actual fun iopl(level: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(level).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from: Long, num: Long, turnOn: Int): Int

public actual fun ioperm(from: CUlong, num: CUlong, turnOn: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(from.toLong(), num.toLong(), turnOn).toInt()

public actual fun aioRead(aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo")

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg")

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv")

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev")

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i: Int): Int

public actual fun abs(i: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(i).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i: Long): Long

public actual fun labs(i: CLong): CLong =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(i).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedRand")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedRand(): Int

public actual fun rand(): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedRand().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed: Int): Unit

public actual fun srand(seed: CUint): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(seed.toInt())

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48(): Double

public actual fun drand48(): CDouble =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48().toDouble()

public actual fun erand48(xseed: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48(): Long

public actual fun lrand48(): CLong =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48().toLong()

public actual fun nrand48(xseed: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48")

public actual fun jrand48(xseed: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed: Long): Unit

public actual fun srand48(seed: CLong): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(seed)

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent(): Unit

public actual fun setpwent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent(): Unit

public actual fun endpwent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent()

public actual fun getpwent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent(): Unit

public actual fun setgrent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent(): Unit

public actual fun endgrent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent()

public actual fun getgrent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent(): Unit

public actual fun setspent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent(): Unit

public actual fun endspent(): Unit =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent()

public actual fun getspent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent")

public actual fun getspnam(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key: Int, size: Long, shmflg: Int): Int

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(key, size.toLong(), shmflg).toInt()

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect")

public actual fun errnoLocation(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation")

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob")

public actual fun globfree(pglob: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree")
}

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir")
}

public actual fun telldir(dirp: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync")

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom")

public actual fun nlLanginfo(item: NlItem): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo")

public actual fun nlLanginfoL(item: NlItem, locale: LocaleT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL")

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy: Int): Int

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(policy).toInt()

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday")

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait")

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue")

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedMount")

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl")

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll")

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy: Int): Int

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(policy).toInt()

public actual fun sysinfo(info: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend")

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR")

public actual fun semClose(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose")

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR")

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam")

public actual fun semUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir: Int, noclose: Int): Int

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(nochdir, noclose).toInt()

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait")

public actual fun getgrgid(gid: GidT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat")

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr")

public actual fun setmntent(filename: ConstPtr, ty: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent")

public actual fun getmntent(stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent")

public actual fun addmntent(stream: MutPtr, mnt: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent")

public actual fun endmntent(streamp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent")

public actual fun hasmntopt(mnt: ConstPtr, opt: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror")

public actual fun regfree(preg: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree")
}

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv")

public actual fun iconvClose(cd: IconvT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedGettid(): Int

public actual fun gettid(): PidT =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedGettid().toInt()

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate")

public actual fun timerDelete(timerid: TimerT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete")

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun")

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime")

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu")
private external fun wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu(): Int

public actual fun schedGetcpu(): CInt =
    wasiRustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu().toInt()

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong")

public actual fun copyFileRange(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange")

public actual fun freopen64(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64")

public actual fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64")

public actual fun fsetpos64(stream: MutPtr, ptr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64")

public actual fun ftello64(stream: MutPtr): Off64T =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64")
