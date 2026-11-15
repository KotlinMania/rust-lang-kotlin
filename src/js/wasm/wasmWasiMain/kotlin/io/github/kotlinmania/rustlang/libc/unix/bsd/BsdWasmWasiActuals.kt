@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd

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
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetrlimit")

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSetrlimit")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdStrerrorR")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdAbs")
private external fun wasiRustlangLibcUnixBsdAbs(i: Int): Int

public actual fun abs(i: CInt): CInt =
    wasiRustlangLibcUnixBsdAbs(i).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdLabs")
private external fun wasiRustlangLibcUnixBsdLabs(i: Long): Long

public actual fun labs(i: CLong): CLong =
    wasiRustlangLibcUnixBsdLabs(i).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdRand")
private external fun wasiRustlangLibcUnixBsdRand(): Int

public actual fun rand(): CInt =
    wasiRustlangLibcUnixBsdRand().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSrand")
private external fun wasiRustlangLibcUnixBsdSrand(seed: Int): Unit

public actual fun srand(seed: CUint): Unit =
    wasiRustlangLibcUnixBsdSrand(seed.toInt())

public actual fun getifaddrs(ifap: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetifaddrs")

public actual fun freeifaddrs(ifa: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdFreeifaddrs")
}

public actual fun setgroups(ngroups: CInt, ptr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSetgroups")

public actual fun setlogin(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSetlogin")

public actual fun ioctl(fd: CInt, request: CUlong, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdIoctl")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdKqueue")
private external fun wasiRustlangLibcUnixBsdKqueue(): Int

public actual fun kqueue(): CInt =
    wasiRustlangLibcUnixBsdKqueue().toInt()

public actual fun unmount(target: ConstPtr, arg: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdUnmount")

public actual fun getpwent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetpwent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSetpwent")
private external fun wasiRustlangLibcUnixBsdSetpwent(): Unit

public actual fun setpwent(): Unit =
    wasiRustlangLibcUnixBsdSetpwent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdEndpwent")
private external fun wasiRustlangLibcUnixBsdEndpwent(): Unit

public actual fun endpwent(): Unit =
    wasiRustlangLibcUnixBsdEndpwent()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdEndgrent")
private external fun wasiRustlangLibcUnixBsdEndgrent(): Unit

public actual fun endgrent(): Unit =
    wasiRustlangLibcUnixBsdEndgrent()

public actual fun getgrent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetgrent")

public actual fun getprogname(): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetprogname")

public actual fun setprogname(name: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSetprogname")
}

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetloadavg")

public actual fun ifNameindex(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdIfNameindex")

public actual fun ifFreenameindex(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdIfFreenameindex")
}

public actual fun getpeereid(socket: CInt, euid: MutPtr, egid: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetpeereid")

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGlob")

public actual fun globfree(pglob: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGlobfree")
}

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdPosixMadvise")

public actual fun shmUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdShmUnlink")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSeekdir")
}

public actual fun telldir(dirp: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdTelldir")

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdMadvise")

public actual fun minherit(addr: MutPtr, len: SizeT, inherit: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdMinherit")

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdMsync")

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRecvfrom")

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdMkstemps")

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdFutimes")

public actual fun nlLanginfo(item: NlItem): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdNlLanginfo")

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdBind")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdWritev")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdReadv")

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSendmsg")

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRecvmsg")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSync")
private external fun wasiRustlangLibcUnixBsdSync(): Unit

public actual fun sync(): Unit =
    wasiRustlangLibcUnixBsdSync()

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetgrgidR")

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSigaltstack")

public actual fun sigsuspend(mask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSigsuspend")

public actual fun semClose(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSemClose")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdGetdtablesize")
private external fun wasiRustlangLibcUnixBsdGetdtablesize(): Int

public actual fun getdtablesize(): CInt =
    wasiRustlangLibcUnixBsdGetdtablesize().toInt()

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetgrnamR")

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdPthreadSigmask")

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSemOpen")

public actual fun getgrnam(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetgrnam")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdPthreadCancel")
private external fun wasiRustlangLibcUnixBsdPthreadCancel(thread: Long): Int

public actual fun pthreadCancel(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT): CInt =
    wasiRustlangLibcUnixBsdPthreadCancel(thread.toLong()).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdPthreadKill")
private external fun wasiRustlangLibcUnixBsdPthreadKill(thread: Long, sig: Int): Int

public actual fun pthreadKill(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt): CInt =
    wasiRustlangLibcUnixBsdPthreadKill(thread.toLong(), sig).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSchedGetPriorityMin")
private external fun wasiRustlangLibcUnixBsdSchedGetPriorityMin(policy: Int): Int

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    wasiRustlangLibcUnixBsdSchedGetPriorityMin(policy).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSchedGetPriorityMax")
private external fun wasiRustlangLibcUnixBsdSchedGetPriorityMax(policy: Int): Int

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    wasiRustlangLibcUnixBsdSchedGetPriorityMax(policy).toInt()

public actual fun semUnlink(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSemUnlink")

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetpwnamR")

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetpwuidR")

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSigwait")

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdPthreadAtfork")

public actual fun getgrgid(gid: GidT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetgrgid")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdPopen")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdFaccessat")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdPthreadCreate")

public actual fun acct(filename: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdAcct")

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdWait4")

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetitimer")

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSetitimer")

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRegcomp")

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRegexec")

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRegerror")

public actual fun regfree(preg: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdRegfree")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdArc4random")
private external fun wasiRustlangLibcUnixBsdArc4random(): Int

public actual fun arc4random(): UInt =
    wasiRustlangLibcUnixBsdArc4random().toUInt()

public actual fun arc4randomBuf(buf: MutPtr, size: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdArc4randomBuf")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdArc4randomUniform")
private external fun wasiRustlangLibcUnixBsdArc4randomUniform(l: Int): Int

public actual fun arc4randomUniform(l: UInt): UInt =
    wasiRustlangLibcUnixBsdArc4randomUniform(l.toInt()).toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdDrand48")
private external fun wasiRustlangLibcUnixBsdDrand48(): Double

public actual fun drand48(): CDouble =
    wasiRustlangLibcUnixBsdDrand48().toDouble()

public actual fun erand48(xseed: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdErand48")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdLrand48")
private external fun wasiRustlangLibcUnixBsdLrand48(): Long

public actual fun lrand48(): CLong =
    wasiRustlangLibcUnixBsdLrand48().toLong()

public actual fun nrand48(xseed: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdNrand48")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdMrand48")
private external fun wasiRustlangLibcUnixBsdMrand48(): Long

public actual fun mrand48(): CLong =
    wasiRustlangLibcUnixBsdMrand48().toLong()

public actual fun jrand48(xseed: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdJrand48")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdSrand48")
private external fun wasiRustlangLibcUnixBsdSrand48(seed: Long): Unit

public actual fun srand48(seed: CLong): Unit =
    wasiRustlangLibcUnixBsdSrand48(seed)

public actual fun seed48(xseed: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSeed48")

public actual fun lcong48(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdLcong48")
}

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdGetoptLong")

public actual fun strftime(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdStrftime")

public actual fun strftimeL(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr, locale: LocaleT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdStrftimeL")

public actual fun devname(dev: DevT, modeT: ModeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdDevname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixBsdIssetugid")
private external fun wasiRustlangLibcUnixBsdIssetugid(): Int

public actual fun issetugid(): CInt =
    wasiRustlangLibcUnixBsdIssetugid().toInt()

public actual fun syscall(num: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsdSyscall")
