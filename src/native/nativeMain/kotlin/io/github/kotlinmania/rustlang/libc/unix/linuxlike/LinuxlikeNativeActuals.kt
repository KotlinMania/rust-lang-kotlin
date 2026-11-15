@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeAcct
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeBind
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeBrk
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeClearenv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeClockGetcpuclockid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeClockGetres
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeClockGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeClockSettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeCreat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeDirfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeDuplocale
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeExecvpe
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFdatasync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFexecve
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeForkpty
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFreeifaddrs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFreelocale
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFstat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFstatat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFstatfs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFstatfs64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFstatvfs64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFtruncate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeFutimens
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetdomainname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetifaddrs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetitimer
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetpwnamR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetpwuidR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetresgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeGetresuid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeIfFreenameindex
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeIfNameindex
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeIoctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLoginTty
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLseek64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeLstat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMemalign
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMemrchr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMincore
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMknodat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMkostemp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMkostemps
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeMmap64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeNewlocale
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeOpen64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeOpenat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeOpenpty
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePipe2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePosixFadvise
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePosixFadvise64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePread64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePreadv64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePtsnameR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePwrite64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikePwritev64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeReaddir64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeReaddir64R
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeReadv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeRecvmsg
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSbrk
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSemDestroy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSemInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSendmsg
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSetdomainname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSetgroups
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSetitimer
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSetresgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeSetresuid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStat64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStatfs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStatfs64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStatvfs64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStatx
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStrchrnul
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStrftime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStrftimeL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeStrptime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeTruncate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeUname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeUselocale
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeUtimensat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeVfork
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeWait4
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeWaitid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.cinterop.rustlangLibcUnixLinuxlikeWritev
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdtypeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun ioctl(fd: CInt, request: Ioctl, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeIoctl(fd, request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun libcCurrentSigrtmax(): CInt =
    rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax()

public actual fun libcCurrentSigrtmin(): CInt =
    rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin()

public actual fun semDestroy(sem: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeSemDestroy(sem.rawAddress.toCPointer<ByteVar>())

public actual fun semInit(sem: MutPtr, pshared: CInt, value: CUint): CInt =
    rustlangLibcUnixLinuxlikeSemInit(sem.rawAddress.toCPointer<ByteVar>(), pshared, value)

public actual fun fdatasync(fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeFdatasync(fd)

public actual fun mincore(addr: MutPtr, len: SizeT, vec: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeMincore(addr.rawAddress.toCPointer<ByteVar>(), len, vec.rawAddress.toCPointer<ByteVar>())

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeClockGetres(clkId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeClockGettime(clkId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockSettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeClockSettime(clkId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockGetcpuclockid(pid: PidT, clkId: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeClockGetcpuclockid(pid, clkId.rawAddress.toCPointer<ByteVar>())

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetitimer(which, currValue.rawAddress.toCPointer<ByteVar>())

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeSetitimer(which, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun dirfd(dirp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeDirfd(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun memalign(align: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeMemalign(align, size)?.toLong() ?: 0L)

public actual fun setgroups(ngroups: SizeT, ptr: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeSetgroups(ngroups, ptr.rawAddress.toCPointer<ByteVar>())

public actual fun pipe2(fds: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikePipe2(fds.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeStatfs(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeFstatfs(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeMemrchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    rustlangLibcUnixLinuxlikePosixFadvise(fd, offset, len, advise)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeFutimens(fd, times.rawAddress.toCPointer<ByteVar>())

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    rustlangLibcUnixLinuxlikeUtimensat(dirfd, path.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>(), flag)

public actual fun duplocale(base: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixLinuxlikeDuplocale(base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freelocale(loc: LocaleT) {
    rustlangLibcUnixLinuxlikeFreelocale(loc.rawAddress.toCPointer<ByteVar>())
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixLinuxlikeNewlocale(mask, locale.rawAddress.toCPointer<ByteVar>(), base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun uselocale(loc: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixLinuxlikeUselocale(loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    rustlangLibcUnixLinuxlikeMknodat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, dev)

public actual fun ptsnameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixLinuxlikePtsnameR(fd, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun clearenv(): CInt =
    rustlangLibcUnixLinuxlikeClearenv()

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    rustlangLibcUnixLinuxlikeWaitid(idtype, id, infop.rawAddress.toCPointer<ByteVar>(), options)

public actual fun getresuid(ruid: MutPtr, euid: MutPtr, suid: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetresuid(ruid.rawAddress.toCPointer<ByteVar>(), euid.rawAddress.toCPointer<ByteVar>(), suid.rawAddress.toCPointer<ByteVar>())

public actual fun getresgid(rgid: MutPtr, egid: MutPtr, sgid: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetresgid(rgid.rawAddress.toCPointer<ByteVar>(), egid.rawAddress.toCPointer<ByteVar>(), sgid.rawAddress.toCPointer<ByteVar>())

public actual fun acct(filename: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAcct(filename.rawAddress.toCPointer<ByteVar>())

public actual fun brk(addr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeBrk(addr.rawAddress.toCPointer<ByteVar>())

public actual fun sbrk(increment: IntptrT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeSbrk(increment)?.toLong() ?: 0L)

public actual fun vfork(): PidT =
    rustlangLibcUnixLinuxlikeVfork()

public actual fun setresgid(rgid: GidT, egid: GidT, sgid: GidT): CInt =
    rustlangLibcUnixLinuxlikeSetresgid(rgid, egid, sgid)

public actual fun setresuid(ruid: UidT, euid: UidT, suid: UidT): CInt =
    rustlangLibcUnixLinuxlikeSetresuid(ruid, euid, suid)

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    rustlangLibcUnixLinuxlikeWait4(pid, status.rawAddress.toCPointer<ByteVar>(), options, rusage.rawAddress.toCPointer<ByteVar>())

public actual fun loginTty(fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLoginTty(fd)

public actual fun execvpe(file: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeExecvpe(file.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun fexecve(fd: CInt, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeFexecve(fd, argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun getifaddrs(ifap: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetifaddrs(ifap.rawAddress.toCPointer<ByteVar>())

public actual fun freeifaddrs(ifa: MutPtr) {
    rustlangLibcUnixLinuxlikeFreeifaddrs(ifa.rawAddress.toCPointer<ByteVar>())
}

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: SocklenT): CInt =
    rustlangLibcUnixLinuxlikeBind(socket, address.rawAddress.toCPointer<ByteVar>(), addressLen)

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeWritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeReadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeSendmsg(fd, msg.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeRecvmsg(fd, msg.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun uname(buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeUname(buf.rawAddress.toCPointer<ByteVar>())

public actual fun strchrnul(s: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeStrchrnul(s.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strftime(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr): SizeT =
    rustlangLibcUnixLinuxlikeStrftime(s.rawAddress.toCPointer<ByteVar>(), max, format.rawAddress.toCPointer<ByteVar>(), tm.rawAddress.toCPointer<ByteVar>())

public actual fun strftimeL(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr, locale: LocaleT): SizeT =
    rustlangLibcUnixLinuxlikeStrftimeL(s.rawAddress.toCPointer<ByteVar>(), max, format.rawAddress.toCPointer<ByteVar>(), tm.rawAddress.toCPointer<ByteVar>(), locale.rawAddress.toCPointer<ByteVar>())

public actual fun strptime(s: ConstPtr, format: ConstPtr, tm: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeStrptime(s.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), tm.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mkostemp(template: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeMkostemp(template.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun mkostemps(template: MutPtr, suffixlen: CInt, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeMkostemps(template.rawAddress.toCPointer<ByteVar>(), suffixlen, flags)

public actual fun getdomainname(name: MutPtr, len: SizeT): CInt =
    rustlangLibcUnixLinuxlikeGetdomainname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun setdomainname(name: ConstPtr, len: SizeT): CInt =
    rustlangLibcUnixLinuxlikeSetdomainname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun ifNameindex(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeIfNameindex()?.toLong() ?: 0L)

public actual fun ifFreenameindex(ptr: MutPtr) {
    rustlangLibcUnixLinuxlikeIfFreenameindex(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetpwnamR(name.rawAddress.toCPointer<ByteVar>(), pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeGetpwuidR(uid, pwd.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun fstatfs64(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeFstatfs64(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeStatvfs64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeFstatvfs64(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun statfs64(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeStatfs64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun creat64(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixLinuxlikeCreat64(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fstat64(fildes: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeFstat64(fildes, buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatat64(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeFstatat64(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun ftruncate64(fd: CInt, length: Off64T): CInt =
    rustlangLibcUnixLinuxlikeFtruncate64(fd, length)

public actual fun lseek64(fd: CInt, offset: Off64T, whence: CInt): Off64T =
    rustlangLibcUnixLinuxlikeLseek64(fd, offset, whence)

public actual fun lstat64(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLstat64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun mmap64(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: Off64T): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeMmap64(addr.rawAddress.toCPointer<ByteVar>(), len, prot, flags, fd, offset)?.toLong() ?: 0L)

public actual fun open64(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeOpen64(path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun openat64(fd: CInt, path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeOpenat64(fd, path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun posixFadvise64(fd: CInt, offset: Off64T, len: Off64T, advise: CInt): CInt =
    rustlangLibcUnixLinuxlikePosixFadvise64(fd, offset, len, advise)

public actual fun pread64(fd: CInt, buf: MutPtr, count: SizeT, offset: Off64T): SsizeT =
    rustlangLibcUnixLinuxlikePread64(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun pwrite64(fd: CInt, buf: ConstPtr, count: SizeT, offset: Off64T): SsizeT =
    rustlangLibcUnixLinuxlikePwrite64(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun readdir64(dirp: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeReaddir64(dirp.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun readdir64R(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeReaddir64R(dirp.rawAddress.toCPointer<ByteVar>(), entry.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())

public actual fun stat64(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeStat64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun truncate64(path: ConstPtr, length: Off64T): CInt =
    rustlangLibcUnixLinuxlikeTruncate64(path.rawAddress.toCPointer<ByteVar>(), length)

public actual fun preadv64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    rustlangLibcUnixLinuxlikePreadv64(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun pwritev64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    rustlangLibcUnixLinuxlikePwritev64(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): PidT =
    rustlangLibcUnixLinuxlikeForkpty(amaster.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), termp.rawAddress.toCPointer<ByteVar>(), winp.rawAddress.toCPointer<ByteVar>())

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeOpenpty(amaster.rawAddress.toCPointer<ByteVar>(), aslave.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), termp.rawAddress.toCPointer<ByteVar>(), winp.rawAddress.toCPointer<ByteVar>())

public actual fun statx(dirfd: CInt, pathname: ConstPtr, flags: CInt, mask: CUint, statxbuf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeStatx(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), flags, mask, statxbuf.rawAddress.toCPointer<ByteVar>())
