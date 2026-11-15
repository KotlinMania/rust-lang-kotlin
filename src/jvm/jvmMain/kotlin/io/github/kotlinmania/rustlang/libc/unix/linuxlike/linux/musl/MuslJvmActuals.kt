@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT

private object LibcUnixLinuxlikeLinuxMuslJniBridge {
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
        val stream = LibcUnixLinuxlikeLinuxMuslJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(resource: Int, rlim: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(pid: Int, resource: Int, newLimit: Long, oldLimit: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(tp: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPtrace(request: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which: Int, who: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which: Int, who: Int, prio: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(fd: Int, flags: Int, mask: Long, dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(fd: Int, iov: Long, iovcnt: Int, offset: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(s: Long, len: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(ptr: Long, nmemb: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(clkId: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslCtermid(s: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(name: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslMlock2(addr: Long, len: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslEaccess(pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(tm: Long, buf: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslDirname(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslBasename(path: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(buf: Long, buflen: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(actions: Long, path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetutxent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPututxline(ut: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslSetutxent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslEndutxent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(thread: Long, retval: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(thread: Long, retval: Long, abstime: Long): Int
}

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(resource, rlim.rawAddress)

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(resource, rlim.rawAddress)

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(pid, resource, newLimit.rawAddress, oldLimit.rawAddress)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(tp.rawAddress, tz.rawAddress)

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPtrace(request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getpriority(which: CInt, who: IdT): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(which, who.toInt())

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(which, who.toInt(), prio)

public actual fun fanotifyMark(fd: CInt, flags: CUint, mask: CUlonglong, dirfd: CInt, path: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(fd, flags.toInt(), mask.toLong(), dirfd, path.rawAddress)

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(fd, iov.rawAddress, iovcnt, offset, flags)

public actual fun getauxval(type: CUlong): CUlong =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(type.toLong()).toULong()

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags.toInt())

public actual fun explicitBzero(s: MutPtr, len: SizeT) {
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(s.rawAddress, len.toLong())
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(ptr.rawAddress, nmemb.toLong(), size.toLong()))

public actual fun adjtimex(buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(buf.rawAddress)

public actual fun clockAdjtime(clkId: ClockidT, buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(clkId.toInt(), buf.rawAddress)

public actual fun ctermid(s: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslCtermid(s.rawAddress))

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(name.rawAddress, flags.toInt())

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslMlock2(addr.rawAddress, len.toLong(), flags.toInt())

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(ptr.rawAddress).toULong()

public actual fun euidaccess(pathname: ConstPtr, mode: CInt): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(pathname.rawAddress, mode)

public actual fun eaccess(pathname: ConstPtr, mode: CInt): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslEaccess(pathname.rawAddress, mode)

public actual fun asctimeR(tm: ConstPtr, buf: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(tm.rawAddress, buf.rawAddress))

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslDirname(path.rawAddress))

public actual fun basename(path: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslBasename(path.rawAddress))

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(buf.rawAddress, buflen.toLong(), flags.toInt())

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(actions.rawAddress, path.rawAddress)

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(actions.rawAddress, fd)

public actual fun getutxent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetutxent())

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(ut.rawAddress))

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(ut.rawAddress))

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPututxline(ut.rawAddress))

public actual fun setutxent() {
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslSetutxent()
}

public actual fun endutxent() {
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslEndutxent()
}

public actual fun utmpxname(file: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(file.rawAddress)

public actual fun pthreadTryjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(thread.rawAddress, retval.rawAddress)

public actual fun pthreadTimedjoinNp(thread: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.PthreadT, retval: MutPtr, abstime: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxMuslJniBridge.rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(thread.rawAddress, retval.rawAddress, abstime.rawAddress)
