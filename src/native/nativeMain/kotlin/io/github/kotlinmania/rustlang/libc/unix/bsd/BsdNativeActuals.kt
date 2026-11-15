@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd

import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdAbs
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdAcct
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdArc4random
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdArc4randomBuf
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdArc4randomUniform
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdBind
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdDevname
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdDrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdEndgrent
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdEndpwent
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdErand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdFaccessat
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdFreeifaddrs
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdFutimes
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetdtablesize
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetgrent
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetgrgid
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetgrgidR
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetgrnam
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetgrnamR
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetifaddrs
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetitimer
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetloadavg
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetoptLong
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetpeereid
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetprogname
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetpwent
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetpwnamR
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetpwuidR
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGetrlimit
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGlob
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdGlobfree
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdIfFreenameindex
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdIfNameindex
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdIoctl
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdIssetugid
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdJrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdKqueue
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdLabs
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdLcong48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdLrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdMadvise
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdMinherit
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdMkstemps
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdMrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdMsync
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdNlLanginfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdNrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPopen
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPosixMadvise
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPthreadAtfork
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPthreadCancel
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPthreadCreate
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPthreadKill
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdPthreadSigmask
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRand
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdReadv
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRecvfrom
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRecvmsg
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRegcomp
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRegerror
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRegexec
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdRegfree
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSchedGetPriorityMax
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSchedGetPriorityMin
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSeed48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSeekdir
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSemClose
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSemOpen
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSemUnlink
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSendmsg
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetgroups
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetitimer
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetlogin
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetprogname
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetpwent
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSetrlimit
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdShmUnlink
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSigaltstack
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSigsuspend
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSigwait
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSrand
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSrand48
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdStrerrorR
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdStrftime
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdStrftimeL
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSync
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdSyscall
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdTelldir
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdUnmount
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdWait4
import io.github.kotlinmania.rustlang.libc.unix.bsd.cinterop.rustlangLibcUnixBsdWritev
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.NlItem
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    rustlangLibcUnixBsdGetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    rustlangLibcUnixBsdSetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixBsdStrerrorR(errnum, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun abs(i: CInt): CInt =
    rustlangLibcUnixBsdAbs(i)

public actual fun labs(i: CLong): CLong =
    rustlangLibcUnixBsdLabs(i)

public actual fun rand(): CInt =
    rustlangLibcUnixBsdRand()

public actual fun srand(seed: CUint) {
    rustlangLibcUnixBsdSrand(seed)
}

public actual fun getifaddrs(ifap: MutPtr): CInt =
    rustlangLibcUnixBsdGetifaddrs(ifap.rawAddress.toCPointer<ByteVar>())

public actual fun freeifaddrs(ifa: MutPtr) {
    rustlangLibcUnixBsdFreeifaddrs(ifa.rawAddress.toCPointer<ByteVar>())
}

public actual fun setgroups(ngroups: CInt, ptr: ConstPtr): CInt =
    rustlangLibcUnixBsdSetgroups(ngroups, ptr.rawAddress.toCPointer<ByteVar>())

public actual fun setlogin(name: ConstPtr): CInt =
    rustlangLibcUnixBsdSetlogin(name.rawAddress.toCPointer<ByteVar>())

public actual fun ioctl(fd: CInt, request: CUlong, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixBsdIoctl(fd, request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun kqueue(): CInt =
    rustlangLibcUnixBsdKqueue()

public actual fun unmount(target: ConstPtr, arg: CInt): CInt =
    rustlangLibcUnixBsdUnmount(target.rawAddress.toCPointer<ByteVar>(), arg)

public actual fun getpwent(): MutPtr =
    MutPtr(rustlangLibcUnixBsdGetpwent()?.toLong() ?: 0L)

public actual fun setpwent() {
    rustlangLibcUnixBsdSetpwent()
}

public actual fun endpwent() {
    rustlangLibcUnixBsdEndpwent()
}

public actual fun endgrent() {
    rustlangLibcUnixBsdEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(rustlangLibcUnixBsdGetgrent()?.toLong() ?: 0L)

public actual fun getprogname(): ConstPtr =
    ConstPtr(rustlangLibcUnixBsdGetprogname()?.toLong() ?: 0L)

public actual fun setprogname(name: ConstPtr) {
    rustlangLibcUnixBsdSetprogname(name.rawAddress.toCPointer<ByteVar>())
}

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    rustlangLibcUnixBsdGetloadavg(loadavg.rawAddress.toCPointer<ByteVar>(), nelem)

public actual fun ifNameindex(): MutPtr =
    MutPtr(rustlangLibcUnixBsdIfNameindex()?.toLong() ?: 0L)

public actual fun ifFreenameindex(ptr: MutPtr) {
    rustlangLibcUnixBsdIfFreenameindex(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun getpeereid(socket: CInt, euid: MutPtr, egid: MutPtr): CInt =
    rustlangLibcUnixBsdGetpeereid(socket, euid.rawAddress.toCPointer<ByteVar>(), egid.rawAddress.toCPointer<ByteVar>())

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    rustlangLibcUnixBsdGlob(pattern.rawAddress.toCPointer<ByteVar>(), flags, 0L.toCPointer<ByteVar>(), pglob.rawAddress.toCPointer<ByteVar>())

public actual fun globfree(pglob: MutPtr) {
    rustlangLibcUnixBsdGlobfree(pglob.rawAddress.toCPointer<ByteVar>())
}

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    rustlangLibcUnixBsdPosixMadvise(addr.rawAddress.toCPointer<ByteVar>(), len, advice)

public actual fun shmUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixBsdShmUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    rustlangLibcUnixBsdSeekdir(dirp.rawAddress.toCPointer<ByteVar>(), loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    rustlangLibcUnixBsdTelldir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    rustlangLibcUnixBsdMadvise(addr.rawAddress.toCPointer<ByteVar>(), len, advice)

public actual fun minherit(addr: MutPtr, len: SizeT, inherit: CInt): CInt =
    rustlangLibcUnixBsdMinherit(addr.rawAddress.toCPointer<ByteVar>(), len, inherit)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    rustlangLibcUnixBsdMsync(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    rustlangLibcUnixBsdRecvfrom(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    rustlangLibcUnixBsdMkstemps(template.rawAddress.toCPointer<ByteVar>(), suffixlen)

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    rustlangLibcUnixBsdFutimes(fd, times.rawAddress.toCPointer<ByteVar>())

public actual fun nlLanginfo(item: NlItem): MutPtr =
    MutPtr(rustlangLibcUnixBsdNlLanginfo(item)?.toLong() ?: 0L)

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT): CInt =
    rustlangLibcUnixBsdBind(socket, address.rawAddress.toCPointer<ByteVar>(), addressLen)

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcUnixBsdWritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcUnixBsdReadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    rustlangLibcUnixBsdSendmsg(fd, msg.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    rustlangLibcUnixBsdRecvmsg(fd, msg.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun sync() {
    rustlangLibcUnixBsdSync()
}

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixBsdGetgrgidR(gid, grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    rustlangLibcUnixBsdSigaltstack(ss.rawAddress.toCPointer<ByteVar>(), oss.rawAddress.toCPointer<ByteVar>())

public actual fun sigsuspend(mask: ConstPtr): CInt =
    rustlangLibcUnixBsdSigsuspend(mask.rawAddress.toCPointer<ByteVar>())

public actual fun semClose(sem: MutPtr): CInt =
    rustlangLibcUnixBsdSemClose(sem.rawAddress.toCPointer<ByteVar>())

public actual fun getdtablesize(): CInt =
    rustlangLibcUnixBsdGetdtablesize()

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixBsdGetgrnamR(name.rawAddress.toCPointer<ByteVar>(), grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    rustlangLibcUnixBsdPthreadSigmask(how, set.rawAddress.toCPointer<ByteVar>(), oldset.rawAddress.toCPointer<ByteVar>())

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(rustlangLibcUnixBsdSemOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)?.toLong() ?: 0L)

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdGetgrnam(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pthreadCancel(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT): CInt =
    rustlangLibcUnixBsdPthreadCancel(thread)

public actual fun pthreadKill(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt): CInt =
    rustlangLibcUnixBsdPthreadKill(thread, sig)

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    rustlangLibcUnixBsdSchedGetPriorityMin(policy)

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    rustlangLibcUnixBsdSchedGetPriorityMax(policy)

public actual fun semUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixBsdSemUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixBsdGetpwnamR(name.rawAddress.toCPointer<ByteVar>(), pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixBsdGetpwuidR(uid, pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    rustlangLibcUnixBsdSigwait(set.rawAddress.toCPointer<ByteVar>(), sig.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    rustlangLibcUnixBsdPthreadAtfork(0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(rustlangLibcUnixBsdGetgrgid(gid)?.toLong() ?: 0L)

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdPopen(command.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcUnixBsdFaccessat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    rustlangLibcUnixBsdPthreadCreate(native.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun acct(filename: ConstPtr): CInt =
    rustlangLibcUnixBsdAcct(filename.rawAddress.toCPointer<ByteVar>())

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    rustlangLibcUnixBsdWait4(pid, status.rawAddress.toCPointer<ByteVar>(), options, rusage.rawAddress.toCPointer<ByteVar>())

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    rustlangLibcUnixBsdGetitimer(which, currValue.rawAddress.toCPointer<ByteVar>())

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixBsdSetitimer(which, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    rustlangLibcUnixBsdRegcomp(preg.rawAddress.toCPointer<ByteVar>(), pattern.rawAddress.toCPointer<ByteVar>(), cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    rustlangLibcUnixBsdRegexec(preg.rawAddress.toCPointer<ByteVar>(), input.rawAddress.toCPointer<ByteVar>(), nmatch, pmatch.rawAddress.toCPointer<ByteVar>(), eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    rustlangLibcUnixBsdRegerror(errcode, preg.rawAddress.toCPointer<ByteVar>(), errbuf.rawAddress.toCPointer<ByteVar>(), errbufSize)

public actual fun regfree(preg: MutPtr) {
    rustlangLibcUnixBsdRegfree(preg.rawAddress.toCPointer<ByteVar>())
}

public actual fun arc4random(): UInt =
    rustlangLibcUnixBsdArc4random()

public actual fun arc4randomBuf(buf: MutPtr, size: SizeT) {
    rustlangLibcUnixBsdArc4randomBuf(buf.rawAddress.toCPointer<ByteVar>(), size)
}

public actual fun arc4randomUniform(l: UInt): UInt =
    rustlangLibcUnixBsdArc4randomUniform(l)

public actual fun drand48(): CDouble =
    rustlangLibcUnixBsdDrand48()

public actual fun erand48(xseed: MutPtr): CDouble =
    rustlangLibcUnixBsdErand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun lrand48(): CLong =
    rustlangLibcUnixBsdLrand48()

public actual fun nrand48(xseed: MutPtr): CLong =
    rustlangLibcUnixBsdNrand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun mrand48(): CLong =
    rustlangLibcUnixBsdMrand48()

public actual fun jrand48(xseed: MutPtr): CLong =
    rustlangLibcUnixBsdJrand48(xseed.rawAddress.toCPointer<ByteVar>())

public actual fun srand48(seed: CLong) {
    rustlangLibcUnixBsdSrand48(seed)
}

public actual fun seed48(xseed: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdSeed48(xseed.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun lcong48(p: MutPtr) {
    rustlangLibcUnixBsdLcong48(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    rustlangLibcUnixBsdGetoptLong(argc, argv.rawAddress.toCPointer<ByteVar>(), optstring.rawAddress.toCPointer<ByteVar>(), longopts.rawAddress.toCPointer<ByteVar>(), longindex.rawAddress.toCPointer<ByteVar>())

public actual fun strftime(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr): SizeT =
    rustlangLibcUnixBsdStrftime(buf.rawAddress.toCPointer<ByteVar>(), maxsize, format.rawAddress.toCPointer<ByteVar>(), timeptr.rawAddress.toCPointer<ByteVar>())

public actual fun strftimeL(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr, locale: LocaleT): SizeT =
    rustlangLibcUnixBsdStrftimeL(buf.rawAddress.toCPointer<ByteVar>(), maxsize, format.rawAddress.toCPointer<ByteVar>(), timeptr.rawAddress.toCPointer<ByteVar>(), locale.rawAddress.toCPointer<ByteVar>())

public actual fun devname(dev: DevT, modeT: ModeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdDevname(dev, modeT)?.toLong() ?: 0L)

public actual fun issetugid(): CInt =
    rustlangLibcUnixBsdIssetugid()

public actual fun syscall(num: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixBsdSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
