@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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

private object LibcUnixLinuxlikeJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/native/$hostPlatform-$hostArch/$libFileName"
        val stream = LibcUnixLinuxlikeJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeIoctl(fd: Int, request: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSemDestroy(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSemInit(sem: Long, pshared: Int, value: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFdatasync(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMincore(addr: Long, len: Long, vec: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeClockGetres(clkId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeClockGettime(clkId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeClockSettime(clkId: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeClockGetcpuclockid(pid: Int, clkId: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetitimer(which: Int, currValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSetitimer(which: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeDirfd(dirp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMemalign(align: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSetgroups(ngroups: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePipe2(fds: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStatfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFstatfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMemrchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePosixFadvise(fd: Int, offset: Long, len: Long, advise: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFutimens(fd: Int, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeUtimensat(dirfd: Int, path: Long, times: Long, flag: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeDuplocale(base: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFreelocale(loc: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeNewlocale(mask: Int, locale: Long, base: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeUselocale(loc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMknodat(dirfd: Int, pathname: Long, mode: Int, dev: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePtsnameR(fd: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeClearenv(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeWaitid(idtype: Int, id: Int, infop: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetresuid(ruid: Long, euid: Long, suid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetresgid(rgid: Long, egid: Long, sgid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAcct(filename: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeBrk(addr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSbrk(increment: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeVfork(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSetresgid(rgid: Int, egid: Int, sgid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSetresuid(ruid: Int, euid: Int, suid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeWait4(pid: Int, status: Long, options: Int, rusage: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLoginTty(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeExecvpe(file: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFexecve(fd: Int, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetifaddrs(ifap: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFreeifaddrs(ifa: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeBind(socket: Int, address: Long, addressLen: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeWritev(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeReadv(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSendmsg(fd: Int, msg: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeRecvmsg(fd: Int, msg: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeUname(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStrchrnul(s: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStrftime(s: Long, max: Long, format: Long, tm: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStrftimeL(s: Long, max: Long, format: Long, tm: Long, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStrptime(s: Long, format: Long, tm: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMkostemp(template: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMkostemps(template: Long, suffixlen: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetdomainname(name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeSetdomainname(name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeIfNameindex(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeIfFreenameindex(ptr: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetpwnamR(name: Long, pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeGetpwuidR(uid: Int, pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFstatfs64(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStatvfs64(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFstatvfs64(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStatfs64(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeCreat64(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFstat64(fildes: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFstatat64(dirfd: Int, pathname: Long, buf: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeFtruncate64(fd: Int, length: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLseek64(fd: Int, offset: Long, whence: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLstat64(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeMmap64(addr: Long, len: Long, prot: Int, flags: Int, fd: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeOpen64(path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeOpenat64(fd: Int, path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePosixFadvise64(fd: Int, offset: Long, len: Long, advise: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePread64(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePwrite64(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeReaddir64(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeReaddir64R(dirp: Long, entry: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStat64(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeTruncate64(path: Long, length: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePreadv64(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikePwritev64(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeForkpty(amaster: Long, name: Long, termp: Long, winp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeOpenpty(amaster: Long, aslave: Long, name: Long, termp: Long, winp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeStatx(dirfd: Int, pathname: Long, flags: Int, mask: Int, statxbuf: Long): Int
}

public actual fun ioctl(fd: CInt, request: Ioctl, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeIoctl(fd, request.toLong(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun libcCurrentSigrtmax(): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax()

public actual fun libcCurrentSigrtmin(): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin()

public actual fun semDestroy(sem: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSemDestroy(sem.rawAddress)

public actual fun semInit(sem: MutPtr, pshared: CInt, value: CUint): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSemInit(sem.rawAddress, pshared, value.toInt())

public actual fun fdatasync(fd: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFdatasync(fd)

public actual fun mincore(addr: MutPtr, len: SizeT, vec: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMincore(addr.rawAddress, len.toLong(), vec.rawAddress)

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeClockGetres(clkId, tp.rawAddress)

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeClockGettime(clkId, tp.rawAddress)

public actual fun clockSettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeClockSettime(clkId, tp.rawAddress)

public actual fun clockGetcpuclockid(pid: PidT, clkId: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeClockGetcpuclockid(pid, clkId.rawAddress)

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetitimer(which, currValue.rawAddress)

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSetitimer(which, newValue.rawAddress, oldValue.rawAddress)

public actual fun dirfd(dirp: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeDirfd(dirp.rawAddress)

public actual fun memalign(align: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMemalign(align.toLong(), size.toLong()))

public actual fun setgroups(ngroups: SizeT, ptr: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSetgroups(ngroups.toLong(), ptr.rawAddress)

public actual fun pipe2(fds: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePipe2(fds.rawAddress, flags)

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStatfs(path.rawAddress, buf.rawAddress)

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFstatfs(fd, buf.rawAddress)

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMemrchr(cx.rawAddress, c, n.toLong()))

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePosixFadvise(fd, offset, len, advise)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFutimens(fd, times.rawAddress)

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeUtimensat(dirfd, path.rawAddress, times.rawAddress, flag)

public actual fun duplocale(base: LocaleT): LocaleT =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeDuplocale(base.rawAddress))

public actual fun freelocale(loc: LocaleT) {
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFreelocale(loc.rawAddress)
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeNewlocale(mask, locale.rawAddress, base.rawAddress))

public actual fun uselocale(loc: LocaleT): LocaleT =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeUselocale(loc.rawAddress))

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMknodat(dirfd, pathname.rawAddress, mode.toInt(), dev)

public actual fun ptsnameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePtsnameR(fd, buf.rawAddress, buflen.toLong())

public actual fun clearenv(): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeClearenv()

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeWaitid(idtype.toInt(), id.toInt(), infop.rawAddress, options)

public actual fun getresuid(ruid: MutPtr, euid: MutPtr, suid: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetresuid(ruid.rawAddress, euid.rawAddress, suid.rawAddress)

public actual fun getresgid(rgid: MutPtr, egid: MutPtr, sgid: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetresgid(rgid.rawAddress, egid.rawAddress, sgid.rawAddress)

public actual fun acct(filename: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeAcct(filename.rawAddress)

public actual fun brk(addr: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeBrk(addr.rawAddress)

public actual fun sbrk(increment: IntptrT): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSbrk(increment))

public actual fun vfork(): PidT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeVfork()

public actual fun setresgid(rgid: GidT, egid: GidT, sgid: GidT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSetresgid(rgid, egid, sgid)

public actual fun setresuid(ruid: UidT, euid: UidT, suid: UidT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSetresuid(ruid, euid, suid)

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeWait4(pid, status.rawAddress, options, rusage.rawAddress)

public actual fun loginTty(fd: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeLoginTty(fd)

public actual fun execvpe(file: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeExecvpe(file.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun fexecve(fd: CInt, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFexecve(fd, argv.rawAddress, envp.rawAddress)

public actual fun getifaddrs(ifap: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetifaddrs(ifap.rawAddress)

public actual fun freeifaddrs(ifa: MutPtr) {
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFreeifaddrs(ifa.rawAddress)
}

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: SocklenT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeBind(socket, address.rawAddress, addressLen.toInt())

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeWritev(fd, iov.rawAddress, iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeReadv(fd, iov.rawAddress, iovcnt)

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSendmsg(fd, msg.rawAddress, flags)

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeRecvmsg(fd, msg.rawAddress, flags)

public actual fun uname(buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeUname(buf.rawAddress)

public actual fun strchrnul(s: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStrchrnul(s.rawAddress, c))

public actual fun strftime(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr): SizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStrftime(s.rawAddress, max.toLong(), format.rawAddress, tm.rawAddress).toULong()

public actual fun strftimeL(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr, locale: LocaleT): SizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStrftimeL(s.rawAddress, max.toLong(), format.rawAddress, tm.rawAddress, locale.rawAddress).toULong()

public actual fun strptime(s: ConstPtr, format: ConstPtr, tm: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStrptime(s.rawAddress, format.rawAddress, tm.rawAddress))

public actual fun mkostemp(template: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMkostemp(template.rawAddress, flags)

public actual fun mkostemps(template: MutPtr, suffixlen: CInt, flags: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMkostemps(template.rawAddress, suffixlen, flags)

public actual fun getdomainname(name: MutPtr, len: SizeT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetdomainname(name.rawAddress, len.toLong())

public actual fun setdomainname(name: ConstPtr, len: SizeT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeSetdomainname(name.rawAddress, len.toLong())

public actual fun ifNameindex(): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeIfNameindex())

public actual fun ifFreenameindex(ptr: MutPtr) {
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeIfFreenameindex(ptr.rawAddress)
}

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetpwnamR(name.rawAddress, pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeGetpwuidR(uid, pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun fstatfs64(fd: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFstatfs64(fd, buf.rawAddress)

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStatvfs64(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFstatvfs64(fd, buf.rawAddress)

public actual fun statfs64(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStatfs64(path.rawAddress, buf.rawAddress)

public actual fun creat64(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeCreat64(path.rawAddress, mode.toInt())

public actual fun fstat64(fildes: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFstat64(fildes, buf.rawAddress)

public actual fun fstatat64(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFstatat64(dirfd, pathname.rawAddress, buf.rawAddress, flags)

public actual fun ftruncate64(fd: CInt, length: Off64T): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeFtruncate64(fd, length)

public actual fun lseek64(fd: CInt, offset: Off64T, whence: CInt): Off64T =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeLseek64(fd, offset, whence)

public actual fun lstat64(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeLstat64(path.rawAddress, buf.rawAddress)

public actual fun mmap64(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: Off64T): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeMmap64(addr.rawAddress, len.toLong(), prot, flags, fd, offset))

public actual fun open64(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeOpen64(path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun openat64(fd: CInt, path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeOpenat64(fd, path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun posixFadvise64(fd: CInt, offset: Off64T, len: Off64T, advise: CInt): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePosixFadvise64(fd, offset, len, advise)

public actual fun pread64(fd: CInt, buf: MutPtr, count: SizeT, offset: Off64T): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePread64(fd, buf.rawAddress, count.toLong(), offset)

public actual fun pwrite64(fd: CInt, buf: ConstPtr, count: SizeT, offset: Off64T): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePwrite64(fd, buf.rawAddress, count.toLong(), offset)

public actual fun readdir64(dirp: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeReaddir64(dirp.rawAddress))

public actual fun readdir64R(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeReaddir64R(dirp.rawAddress, entry.rawAddress, result.rawAddress)

public actual fun stat64(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStat64(path.rawAddress, buf.rawAddress)

public actual fun truncate64(path: ConstPtr, length: Off64T): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeTruncate64(path.rawAddress, length)

public actual fun preadv64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePreadv64(fd, iov.rawAddress, iovcnt, offset)

public actual fun pwritev64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikePwritev64(fd, iov.rawAddress, iovcnt, offset)

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): PidT =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeForkpty(amaster.rawAddress, name.rawAddress, termp.rawAddress, winp.rawAddress)

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeOpenpty(amaster.rawAddress, aslave.rawAddress, name.rawAddress, termp.rawAddress, winp.rawAddress)

public actual fun statx(dirfd: CInt, pathname: ConstPtr, flags: CInt, mask: CUint, statxbuf: MutPtr): CInt =
    LibcUnixLinuxlikeJniBridge.rustlangLibcUnixLinuxlikeStatx(dirfd, pathname.rawAddress, flags, mask.toInt(), statxbuf.rawAddress)
