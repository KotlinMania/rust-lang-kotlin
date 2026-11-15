@file:OptIn(ExperimentalUnsignedTypes::class)

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

private object LibcUnixBsdJniBridge {
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
        val stream = LibcUnixBsdJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcUnixBsdGetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdStrerrorR(errnum: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAbs(i: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdLabs(i: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdRand(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSrand(seed: Int)

    @JvmStatic
    external fun rustlangLibcUnixBsdGetifaddrs(ifap: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdFreeifaddrs(ifa: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdSetgroups(ngroups: Int, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSetlogin(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdIoctl(fd: Int, request: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdKqueue(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdUnmount(target: Long, arg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetpwent(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdSetpwent()

    @JvmStatic
    external fun rustlangLibcUnixBsdEndpwent()

    @JvmStatic
    external fun rustlangLibcUnixBsdEndgrent()

    @JvmStatic
    external fun rustlangLibcUnixBsdGetgrent(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdGetprogname(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdSetprogname(name: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdGetloadavg(loadavg: Long, nelem: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdIfNameindex(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdIfFreenameindex(ptr: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdGetpeereid(socket: Int, euid: Long, egid: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGlob(pattern: Long, flags: Int, errfunc: Long, pglob: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGlobfree(pglob: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdPosixMadvise(addr: Long, len: Long, advice: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdShmUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSeekdir(dirp: Long, loc: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdTelldir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdMadvise(addr: Long, len: Long, advice: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdMinherit(addr: Long, len: Long, inherit: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdMsync(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdRecvfrom(socket: Int, buf: Long, len: Long, flags: Int, addr: Long, addrlen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdMkstemps(template: Long, suffixlen: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdFutimes(fd: Int, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdNlLanginfo(item: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdBind(socket: Int, address: Long, addressLen: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdWritev(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdReadv(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdSendmsg(fd: Int, msg: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdRecvmsg(fd: Int, msg: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdSync()

    @JvmStatic
    external fun rustlangLibcUnixBsdGetgrgidR(gid: Int, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSigaltstack(ss: Long, oss: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSigsuspend(mask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSemClose(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetdtablesize(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetgrnamR(name: Long, grp: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdPthreadSigmask(how: Int, set: Long, oldset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSemOpen(name: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdGetgrnam(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdPthreadCancel(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdPthreadKill(thread: Long, sig: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSchedGetPriorityMin(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSchedGetPriorityMax(policy: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSemUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetpwnamR(name: Long, pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetpwuidR(uid: Int, pwd: Long, buf: Long, buflen: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSigwait(set: Long, sig: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdPthreadAtfork(prepare: Long, parent: Long, child: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetgrgid(gid: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdPopen(command: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdFaccessat(dirfd: Int, pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdPthreadCreate(native: Long, attr: Long, f: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdAcct(filename: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdWait4(pid: Int, status: Long, options: Int, rusage: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdGetitimer(which: Int, currValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSetitimer(which: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdRegcomp(preg: Long, pattern: Long, cflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdRegexec(preg: Long, input: Long, nmatch: Long, pmatch: Long, eflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdRegerror(errcode: Int, preg: Long, errbuf: Long, errbufSize: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdRegfree(preg: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdArc4random(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdArc4randomBuf(buf: Long, size: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdArc4randomUniform(l: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdDrand48(): Double

    @JvmStatic
    external fun rustlangLibcUnixBsdErand48(xseed: Long): Double

    @JvmStatic
    external fun rustlangLibcUnixBsdLrand48(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdNrand48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdMrand48(): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdJrand48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdSrand48(seed: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdSeed48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdLcong48(p: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsdGetoptLong(argc: Int, argv: Long, optstring: Long, longopts: Long, longindex: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdStrftime(buf: Long, maxsize: Long, format: Long, timeptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdStrftimeL(buf: Long, maxsize: Long, format: Long, timeptr: Long, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdDevname(dev: Int, modeT: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixBsdIssetugid(): Int

    @JvmStatic
    external fun rustlangLibcUnixBsdSyscall(num: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int
}

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetrlimit(resource, rlim.rawAddress)

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetrlimit(resource, rlim.rawAddress)

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdStrerrorR(errnum, buf.rawAddress, buflen.toLong())

public actual fun abs(i: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdAbs(i)

public actual fun labs(i: CLong): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdLabs(i)

public actual fun rand(): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRand()

public actual fun srand(seed: CUint) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSrand(seed.toInt())
}

public actual fun getifaddrs(ifap: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetifaddrs(ifap.rawAddress)

public actual fun freeifaddrs(ifa: MutPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdFreeifaddrs(ifa.rawAddress)
}

public actual fun setgroups(ngroups: CInt, ptr: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetgroups(ngroups, ptr.rawAddress)

public actual fun setlogin(name: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetlogin(name.rawAddress)

public actual fun ioctl(fd: CInt, request: CUlong, vararg variadicArgs: Long): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdIoctl(fd, request.toLong(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun kqueue(): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdKqueue()

public actual fun unmount(target: ConstPtr, arg: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdUnmount(target.rawAddress, arg)

public actual fun getpwent(): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetpwent())

public actual fun setpwent() {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetpwent()
}

public actual fun endpwent() {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdEndpwent()
}

public actual fun endgrent() {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetgrent())

public actual fun getprogname(): ConstPtr =
    ConstPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetprogname())

public actual fun setprogname(name: ConstPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetprogname(name.rawAddress)
}

public actual fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetloadavg(loadavg.rawAddress, nelem)

public actual fun ifNameindex(): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdIfNameindex())

public actual fun ifFreenameindex(ptr: MutPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdIfFreenameindex(ptr.rawAddress)
}

public actual fun getpeereid(socket: CInt, euid: MutPtr, egid: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetpeereid(socket, euid.rawAddress, egid.rawAddress)

public actual fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGlob(pattern.rawAddress, flags, 0L, pglob.rawAddress)

public actual fun globfree(pglob: MutPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGlobfree(pglob.rawAddress)
}

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPosixMadvise(addr.rawAddress, len.toLong(), advice)

public actual fun shmUnlink(name: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdShmUnlink(name.rawAddress)

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSeekdir(dirp.rawAddress, loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdTelldir(dirp.rawAddress)

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdMadvise(addr.rawAddress, len.toLong(), advice)

public actual fun minherit(addr: MutPtr, len: SizeT, inherit: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdMinherit(addr.rawAddress, len.toLong(), inherit)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdMsync(addr.rawAddress, len.toLong(), flags)

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRecvfrom(socket, buf.rawAddress, len.toLong(), flags, addr.rawAddress, addrlen.rawAddress)

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdMkstemps(template.rawAddress, suffixlen)

public actual fun futimes(fd: CInt, times: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdFutimes(fd, times.rawAddress)

public actual fun nlLanginfo(item: NlItem): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdNlLanginfo(item))

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdBind(socket, address.rawAddress, addressLen.toInt())

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdWritev(fd, iov.rawAddress, iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdReadv(fd, iov.rawAddress, iovcnt)

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSendmsg(fd, msg.rawAddress, flags)

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRecvmsg(fd, msg.rawAddress, flags)

public actual fun sync() {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSync()
}

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetgrgidR(gid, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSigaltstack(ss.rawAddress, oss.rawAddress)

public actual fun sigsuspend(mask: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSigsuspend(mask.rawAddress)

public actual fun semClose(sem: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSemClose(sem.rawAddress)

public actual fun getdtablesize(): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetdtablesize()

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetgrnamR(name.rawAddress, grp.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPthreadSigmask(how, set.rawAddress, oldset.rawAddress)

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdSemOpen(name.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L))

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetgrnam(name.rawAddress))

public actual fun pthreadCancel(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPthreadCancel(thread.toLong())

public actual fun pthreadKill(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT, sig: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPthreadKill(thread.toLong(), sig)

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSchedGetPriorityMin(policy)

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSchedGetPriorityMax(policy)

public actual fun semUnlink(name: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSemUnlink(name.rawAddress)

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetpwnamR(name.rawAddress, pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetpwuidR(uid, pwd.rawAddress, buf.rawAddress, buflen.toLong(), result.rawAddress)

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSigwait(set.rawAddress, sig.rawAddress)

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPthreadAtfork(0L, 0L, 0L)

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetgrgid(gid))

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdPopen(command.rawAddress, mode.rawAddress))

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdFaccessat(dirfd, pathname.rawAddress, mode, flags)

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdPthreadCreate(native.rawAddress, attr.rawAddress, 0L, value.rawAddress)

public actual fun acct(filename: ConstPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdAcct(filename.rawAddress)

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdWait4(pid, status.rawAddress, options, rusage.rawAddress)

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetitimer(which, currValue.rawAddress)

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSetitimer(which, newValue.rawAddress, oldValue.rawAddress)

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRegcomp(preg.rawAddress, pattern.rawAddress, cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRegexec(preg.rawAddress, input.rawAddress, nmatch.toLong(), pmatch.rawAddress, eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRegerror(errcode, preg.rawAddress, errbuf.rawAddress, errbufSize.toLong()).toULong()

public actual fun regfree(preg: MutPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdRegfree(preg.rawAddress)
}

public actual fun arc4random(): UInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdArc4random().toUInt()

public actual fun arc4randomBuf(buf: MutPtr, size: SizeT) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdArc4randomBuf(buf.rawAddress, size.toLong())
}

public actual fun arc4randomUniform(l: UInt): UInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdArc4randomUniform(l.toInt()).toUInt()

public actual fun drand48(): CDouble =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdDrand48()

public actual fun erand48(xseed: MutPtr): CDouble =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdErand48(xseed.rawAddress)

public actual fun lrand48(): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdLrand48()

public actual fun nrand48(xseed: MutPtr): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdNrand48(xseed.rawAddress)

public actual fun mrand48(): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdMrand48()

public actual fun jrand48(xseed: MutPtr): CLong =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdJrand48(xseed.rawAddress)

public actual fun srand48(seed: CLong) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSrand48(seed)
}

public actual fun seed48(xseed: MutPtr): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdSeed48(xseed.rawAddress))

public actual fun lcong48(p: MutPtr) {
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdLcong48(p.rawAddress)
}

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdGetoptLong(argc, argv.rawAddress, optstring.rawAddress, longopts.rawAddress, longindex.rawAddress)

public actual fun strftime(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr): SizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdStrftime(buf.rawAddress, maxsize.toLong(), format.rawAddress, timeptr.rawAddress).toULong()

public actual fun strftimeL(buf: MutPtr, maxsize: SizeT, format: ConstPtr, timeptr: ConstPtr, locale: LocaleT): SizeT =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdStrftimeL(buf.rawAddress, maxsize.toLong(), format.rawAddress, timeptr.rawAddress, locale.rawAddress).toULong()

public actual fun devname(dev: DevT, modeT: ModeT): MutPtr =
    MutPtr(LibcUnixBsdJniBridge.rustlangLibcUnixBsdDevname(dev, modeT.toInt()))

public actual fun issetugid(): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdIssetugid()

public actual fun syscall(num: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixBsdJniBridge.rustlangLibcUnixBsdSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
