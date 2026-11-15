@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CFloat
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object LibcWasiJniBridge {
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
        val stream = LibcWasiJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcWasiGetENVIRON(): Long

    @JvmStatic
    external fun rustlangLibcWasiSetENVIRON(value: Long)

    @JvmStatic
    external fun rustlangLibcWasiGetCLOCKMONOTONIC1(): Byte

    @JvmStatic
    external fun rustlangLibcWasiGetCLOCKREALTIME1(): Byte

    @JvmStatic
    external fun rustlangLibcWasiExit(code: Int)

    @JvmStatic
    external fun rustlangLibcWasiAbort()

    @JvmStatic
    external fun rustlangLibcWasiAlignedAlloc(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiCalloc(amt: Long, amt2: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFree(ptr: Long)

    @JvmStatic
    external fun rustlangLibcWasiGetenv(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMalloc(amt: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMallocUsableSize(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiSbrk(increment: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiRand(): Int

    @JvmStatic
    external fun rustlangLibcWasiRead(fd: Int, ptr: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiRealloc(ptr: Long, amt: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiSetenv(k: Long, v: Long, a: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiUnsetenv(k: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiClearenv(): Int

    @JvmStatic
    external fun rustlangLibcWasiWrite(fd: Int, ptr: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFopen(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFreopen(a: Long, b: Long, f: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFclose(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiRemove(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiRename(a: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFeof(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFerror(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFflush(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiClearerr(f: Long)

    @JvmStatic
    external fun rustlangLibcWasiFseek(f: Long, b: Long, c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFtell(f: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiRewind(f: Long)

    @JvmStatic
    external fun rustlangLibcWasiFgetpos(f: Long, pos: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFsetpos(f: Long, pos: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFread(buf: Long, a: Long, b: Long, f: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFwrite(buf: Long, a: Long, b: Long, f: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFgetc(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiGetc(f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiGetchar(): Int

    @JvmStatic
    external fun rustlangLibcWasiUngetc(a: Int, f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFputc(a: Int, f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPutc(a: Int, f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPutchar(a: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFputs(a: Long, f: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPuts(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPerror(a: Long)

    @JvmStatic
    external fun rustlangLibcWasiSrand(a: Int)

    @JvmStatic
    external fun rustlangLibcWasiAtexit(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiAtQuickExit(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiQuickExit(a: Int)

    @JvmStatic
    external fun rustlangLibcWasiPosixMemalign(a: Long, b: Long, c: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiRandR(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiRandom(): Long

    @JvmStatic
    external fun rustlangLibcWasiSrandom(a: Int)

    @JvmStatic
    external fun rustlangLibcWasiPutenv(a: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiClock(): Long

    @JvmStatic
    external fun rustlangLibcWasiTime(a: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiDifftime(a: Long, b: Long): Double

    @JvmStatic
    external fun rustlangLibcWasiMktime(a: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrftime(a: Long, b: Long, c: Long, d: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiGmtime(a: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiGmtimeR(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiLocaltime(a: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiLocaltimeR(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiAsctimeR(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiCtimeR(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiNanosleep(a: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiIsalnum(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsalpha(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIscntrl(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsgraph(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIslower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsprint(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIspunct(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsspace(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsxdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiIsblank(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiTolower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiToupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiSetvbuf(stream: Long, buffer: Long, mode: Int, size: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSetbuf(stream: Long, buf: Long)

    @JvmStatic
    external fun rustlangLibcWasiFgets(buf: Long, n: Int, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiAtof(s: Long): Double

    @JvmStatic
    external fun rustlangLibcWasiAtoi(s: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiAtol(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiAtoll(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrtod(s: Long, endp: Long): Double

    @JvmStatic
    external fun rustlangLibcWasiStrtof(s: Long, endp: Long): Float

    @JvmStatic
    external fun rustlangLibcWasiStrtol(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrtoll(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrtoul(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrtoull(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrcpy(dst: Long, src: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrncpy(dst: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrcat(s: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrncat(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrcmp(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrncmp(cs: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrcoll(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrcspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrdup(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrndup(cs: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrpbrk(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrstr(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrcasecmp(s1: Long, s2: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrncasecmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrlen(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrnlen(cs: Long, maxlen: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrerror(n: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiStrtok(s: Long, t: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrxfrm(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMemchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMemcmp(cx: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiMemset(dest: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFprintf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPrintf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSnprintf(s: Long, n: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSprintf(s: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFscanf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiScanf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSscanf(s: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiGetcharUnlocked(): Int

    @JvmStatic
    external fun rustlangLibcWasiPutcharUnlocked(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiShutdown(socket: Int, how: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFstat(fildes: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiMkdir(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiStat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFdopen(fd: Int, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFileno(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiOpen(path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiCreat(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFcntl(fd: Int, cmd: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiOpendir(dirname: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFdopendir(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiReaddir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiClosedir(dirp: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiRewinddir(dirp: Long)

    @JvmStatic
    external fun rustlangLibcWasiDirfd(dirp: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSeekdir(dirp: Long, loc: Long)

    @JvmStatic
    external fun rustlangLibcWasiTelldir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiOpenat(dirfd: Int, pathname: Long, flags: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFstatat(dirfd: Int, pathname: Long, buf: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiLinkat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiMkdirat(dirfd: Int, pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiReadlinkat(dirfd: Int, pathname: Long, buf: Long, bufsiz: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiRenameat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSymlinkat(target: Long, newdirfd: Int, linkpath: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiUnlinkat(dirfd: Int, pathname: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiAccess(path: Long, amode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiClose(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFpathconf(filedes: Int, name: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiGetopt(argc: Int, argv: Long, optstr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiIsatty(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiLink(src: Long, dst: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiLseek(fd: Int, offset: Long, whence: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiPathconf(path: Long, name: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiRmdir(path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiSleep(secs: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiUnlink(c: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPread(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiPwrite(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiLstat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFsync(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFdatasync(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiSymlink(path1: Long, path2: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiTruncate(path: Long, length: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFtruncate(fd: Int, length: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiGetrusage(resource: Int, usage: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiGettimeofday(tp: Long, tz: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiTimes(buf: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiStrerrorR(errnum: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiUsleep(secs: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiSend(socket: Int, buf: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiRecv(socket: Int, buf: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiPoll(fds: Long, nfds: Long, timeout: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiSetlocale(category: Int, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiLocaleconv(): Long

    @JvmStatic
    external fun rustlangLibcWasiReadlink(path: Long, buf: Long, bufsz: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiTimegm(tm: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiSysconf(name: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiIoctl(fd: Int, request: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiFseeko(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFtello(stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiPosixFallocate(fd: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiStrcasestr(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiGetline(lineptr: Long, n: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFaccessat(dirfd: Int, pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWritev(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiReadv(fd: Int, iov: Long, iovcnt: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiPwritev(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiPreadv(fd: Int, iov: Long, iovcnt: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiPosixFadvise(fd: Int, offset: Long, len: Long, advise: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFutimens(fd: Int, times: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiUtimensat(dirfd: Int, path: Long, times: Long, flag: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiGetentropy(buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiMemrchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiAbs(i: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiLabs(i: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiDuplocale(base: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiFreelocale(loc: Long)

    @JvmStatic
    external fun rustlangLibcWasiNewlocale(mask: Int, locale: Long, base: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiUselocale(loc: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiSchedYield(): Int

    @JvmStatic
    external fun rustlangLibcWasiGetcwd(buf: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiChdir(dir: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiNlLanginfo(item: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiNlLanginfoL(item: Int, loc: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiSelect(nfds: Int, readfds: Long, writefds: Long, errorfds: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcRegisterPreopenedFd(fd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcFdRenumber(fd: Int, newfd: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcUnlinkat(fd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcRmdirat(fd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcFindRelpath(path: Long, absPrefix: Long, relativePath: Long, relativePathLen: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcTell(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdWasilibcRmdirat(dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdLinkat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdSymlinkat(target: Long, dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdReadlinkat(dirfd: Int, path: Long, buf: Long, bufsize: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdFaccessat(dirfd: Int, path: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdRenameat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdOpenatNomode(dirfd: Int, path: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdFstatat(dirfd: Int, path: Long, buf: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdMkdiratNomode(dirfd: Int, path: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdUtimensat(dirfd: Int, path: Long, times: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcNocwdOpendirat(dirfd: Int, path: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiWasilibcAccess(pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcStat(pathname: Long, buf: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcUtimens(pathname: Long, times: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcLink(oldpath: Long, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcLinkOldat(olddirfd: Int, oldpath: Long, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcLinkNewat(oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcRenameOldat(olddirfd: Int, oldpath: Long, newpath: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiWasilibcRenameNewat(oldpath: Long, newdirfd: Int, newpath: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiArc4random(): Int

    @JvmStatic
    external fun rustlangLibcWasiArc4randomBuf(a: Long, b: Long)

    @JvmStatic
    external fun rustlangLibcWasiArc4randomUniform(a: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiErrnoLocation(): Long

    @JvmStatic
    external fun rustlangLibcWasiChmod(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiFchmod(fd: Int, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiRealpath(pathname: Long, resolved: Long): Long

    @JvmStatic
    external fun rustlangLibcWasiPthreadSelf(): Long

    @JvmStatic
    external fun rustlangLibcWasiPthreadCreate(native: Long, attr: Long, f: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadEqual(t1: Long, t2: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadJoin(native: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrGetstacksize(attr: Long, stacksize: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrSetstacksize(attr: Long, stackSize: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrGetdetachstate(attr: Long, state: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrSetdetachstate(attr: Long, state: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrGetguardsize(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrSetguardsize(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrGetstack(attr: Long, b: Long, c: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadAttrSetstack(attr: Long, b: Long, c: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadDetach(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSetcancelstate(a: Int, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSetcanceltype(a: Int, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadTestcancel()

    @JvmStatic
    external fun rustlangLibcWasiPthreadOnce(a: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadKeyCreate(key: Long, dtor: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadKeyDelete(key: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun rustlangLibcWasiPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexInit(lock: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexLock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexTrylock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexUnlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexTimedlock(lock: Long, ts: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexConsistent(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexGetprioceiling(lock: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrSettype(attr: Long, type: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrGetprotocol(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrGetpshared(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrGetrobust(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrGettype(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrSetprotocol(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrSetpshared(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadMutexattrSetrobust(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondInit(cond: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondWait(cond: Long, lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondTimedwait(cond: Long, lock: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondSignal(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondBroadcast(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondDestroy(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondattrGetclock(attr: Long, clockId: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondattrSetpshared(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadCondattrGetpshared(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockInit(lock: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockRdlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockTryrdlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockWrlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockTrywrlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockUnlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockTimedrdlock(lock: Long, at: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockTimedwrlock(lock: Long, at: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockattrSetpshared(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadRwlockattrGetpshared(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSpinInit(lock: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSpinDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSpinLock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSpinTrylock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadSpinUnlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierInit(barrier: Long, attr: Long, count: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierDestroy(barrier: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierWait(barrier: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierattrSetpshared(attr: Long, b: Int): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadBarrierattrGetpshared(attr: Long, b: Long): Int

    @JvmStatic
    external fun rustlangLibcWasiPthreadGetattrNp(thread: Long, attr: Long): Int
}

public actual var ENVIRON: MutPtr
    get() = MutPtr(LibcWasiJniBridge.rustlangLibcWasiGetENVIRON())
    set(value) {
        LibcWasiJniBridge.rustlangLibcWasiSetENVIRON(value.rawAddress)
    }

public actual val CLOCK_MONOTONIC1: UByte
    get() = LibcWasiJniBridge.rustlangLibcWasiGetCLOCKMONOTONIC1().toUByte()

public actual val CLOCK_REALTIME1: UByte
    get() = LibcWasiJniBridge.rustlangLibcWasiGetCLOCKREALTIME1().toUByte()

public actual fun exit(code: CInt): Nothing {
    LibcWasiJniBridge.rustlangLibcWasiExit(code)
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    LibcWasiJniBridge.rustlangLibcWasiAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun alignedAlloc(a: SizeT, b: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiAlignedAlloc(a.toLong(), b.toLong()))

public actual fun calloc(amt: SizeT, amt2: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiCalloc(amt.toLong(), amt2.toLong()))

public actual fun free(ptr: MutPtr) {
    LibcWasiJniBridge.rustlangLibcWasiFree(ptr.rawAddress)
}

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiGetenv(s.rawAddress))

public actual fun malloc(amt: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMalloc(amt.toLong()))

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiMallocUsableSize(ptr.rawAddress).toULong()

public actual fun sbrk(increment: IntptrT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiSbrk(increment))

public actual fun rand(): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRand()

public actual fun read(fd: CInt, ptr: MutPtr, size: SizeT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiRead(fd, ptr.rawAddress, size.toLong())

public actual fun realloc(ptr: MutPtr, amt: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiRealloc(ptr.rawAddress, amt.toLong()))

public actual fun setenv(k: ConstPtr, v: ConstPtr, a: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSetenv(k.rawAddress, v.rawAddress, a)

public actual fun unsetenv(k: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUnsetenv(k.rawAddress)

public actual fun clearenv(): CInt =
    LibcWasiJniBridge.rustlangLibcWasiClearenv()

public actual fun write(fd: CInt, ptr: ConstPtr, size: SizeT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiWrite(fd, ptr.rawAddress, size.toLong())

public actual fun fopen(a: ConstPtr, b: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiFopen(a.rawAddress, b.rawAddress))

public actual fun freopen(a: ConstPtr, b: ConstPtr, f: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiFreopen(a.rawAddress, b.rawAddress, f.rawAddress))

public actual fun fclose(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFclose(f.rawAddress)

public actual fun remove(a: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRemove(a.rawAddress)

public actual fun rename(a: ConstPtr, b: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRename(a.rawAddress, b.rawAddress)

public actual fun feof(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFeof(f.rawAddress)

public actual fun ferror(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFerror(f.rawAddress)

public actual fun fflush(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFflush(f.rawAddress)

public actual fun clearerr(f: MutPtr) {
    LibcWasiJniBridge.rustlangLibcWasiClearerr(f.rawAddress)
}

public actual fun fseek(f: MutPtr, b: CLong, c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFseek(f.rawAddress, b, c)

public actual fun ftell(f: MutPtr): CLong =
    LibcWasiJniBridge.rustlangLibcWasiFtell(f.rawAddress)

public actual fun rewind(f: MutPtr) {
    LibcWasiJniBridge.rustlangLibcWasiRewind(f.rawAddress)
}

public actual fun fgetpos(f: MutPtr, pos: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFgetpos(f.rawAddress, pos.rawAddress)

public actual fun fsetpos(f: MutPtr, pos: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFsetpos(f.rawAddress, pos.rawAddress)

public actual fun fread(buf: MutPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiFread(buf.rawAddress, a.toLong(), b.toLong(), f.rawAddress).toULong()

public actual fun fwrite(buf: ConstPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiFwrite(buf.rawAddress, a.toLong(), b.toLong(), f.rawAddress).toULong()

public actual fun fgetc(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFgetc(f.rawAddress)

public actual fun getc(f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetc(f.rawAddress)

public actual fun getchar(): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetchar()

public actual fun ungetc(a: CInt, f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUngetc(a, f.rawAddress)

public actual fun fputc(a: CInt, f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFputc(a, f.rawAddress)

public actual fun putc(a: CInt, f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPutc(a, f.rawAddress)

public actual fun putchar(a: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPutchar(a)

public actual fun fputs(a: ConstPtr, f: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFputs(a.rawAddress, f.rawAddress)

public actual fun puts(a: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPuts(a.rawAddress)

public actual fun perror(a: ConstPtr) {
    LibcWasiJniBridge.rustlangLibcWasiPerror(a.rawAddress)
}

public actual fun srand(a: CUint) {
    LibcWasiJniBridge.rustlangLibcWasiSrand(a.toInt())
}

public actual fun atexit(a: AtexitACallback): CInt =
    LibcWasiJniBridge.rustlangLibcWasiAtexit(0L)

public actual fun atQuickExit(a: AtexitACallback): CInt =
    LibcWasiJniBridge.rustlangLibcWasiAtQuickExit(0L)

public actual fun quickExit(a: CInt): Nothing {
    LibcWasiJniBridge.rustlangLibcWasiQuickExit(a)
    error("Unreachable noreturn C function quickExit")
}

public actual fun posixMemalign(a: MutPtr, b: SizeT, c: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPosixMemalign(a.rawAddress, b.toLong(), c.toLong())

public actual fun randR(a: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRandR(a.rawAddress)

public actual fun random(): CLong =
    LibcWasiJniBridge.rustlangLibcWasiRandom()

public actual fun srandom(a: CUint) {
    LibcWasiJniBridge.rustlangLibcWasiSrandom(a.toInt())
}

public actual fun putenv(a: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPutenv(a.rawAddress)

public actual fun clock(): ClockT =
    LibcWasiJniBridge.rustlangLibcWasiClock()

public actual fun time(a: MutPtr): TimeT =
    LibcWasiJniBridge.rustlangLibcWasiTime(a.rawAddress)

public actual fun difftime(a: TimeT, b: TimeT): CDouble =
    LibcWasiJniBridge.rustlangLibcWasiDifftime(a, b)

public actual fun mktime(a: MutPtr): TimeT =
    LibcWasiJniBridge.rustlangLibcWasiMktime(a.rawAddress)

public actual fun strftime(a: MutPtr, b: SizeT, c: ConstPtr, d: ConstPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrftime(a.rawAddress, b.toLong(), c.rawAddress, d.rawAddress).toULong()

public actual fun gmtime(a: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiGmtime(a.rawAddress))

public actual fun gmtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiGmtimeR(a.rawAddress, b.rawAddress))

public actual fun localtime(a: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiLocaltime(a.rawAddress))

public actual fun localtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiLocaltimeR(a.rawAddress, b.rawAddress))

public actual fun asctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiAsctimeR(a.rawAddress, b.rawAddress))

public actual fun ctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiCtimeR(a.rawAddress, b.rawAddress))

public actual fun nanosleep(a: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiNanosleep(a.rawAddress, b.rawAddress)

public actual fun clockGetres(a: ClockidT, b: MutPtr): CInt =
    0

public actual fun clockGettime(a: ClockidT, b: MutPtr): CInt =
    0

public actual fun clockNanosleep(a: ClockidT, a2: CInt, b: ConstPtr, c: MutPtr): CInt =
    0

public actual fun isalnum(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsgraph(c)

public actual fun islower(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIslower(c)

public actual fun isprint(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIspunct(c)

public actual fun isspace(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsspace(c)

public actual fun isupper(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsblank(c)

public actual fun tolower(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiTolower(c)

public actual fun toupper(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiToupper(c)

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSetvbuf(stream.rawAddress, buffer.rawAddress, mode, size.toLong())

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    LibcWasiJniBridge.rustlangLibcWasiSetbuf(stream.rawAddress, buf.rawAddress)
}

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiFgets(buf.rawAddress, n, stream.rawAddress))

public actual fun atof(s: ConstPtr): CDouble =
    LibcWasiJniBridge.rustlangLibcWasiAtof(s.rawAddress)

public actual fun atoi(s: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiAtoi(s.rawAddress)

public actual fun atol(s: ConstPtr): CLong =
    LibcWasiJniBridge.rustlangLibcWasiAtol(s.rawAddress)

public actual fun atoll(s: ConstPtr): CLonglong =
    LibcWasiJniBridge.rustlangLibcWasiAtoll(s.rawAddress)

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    LibcWasiJniBridge.rustlangLibcWasiStrtod(s.rawAddress, endp.rawAddress)

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    LibcWasiJniBridge.rustlangLibcWasiStrtof(s.rawAddress, endp.rawAddress)

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    LibcWasiJniBridge.rustlangLibcWasiStrtol(s.rawAddress, endp.rawAddress, base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    LibcWasiJniBridge.rustlangLibcWasiStrtoll(s.rawAddress, endp.rawAddress, base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    LibcWasiJniBridge.rustlangLibcWasiStrtoul(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    LibcWasiJniBridge.rustlangLibcWasiStrtoull(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrcpy(dst.rawAddress, src.rawAddress))

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrncpy(dst.rawAddress, src.rawAddress, n.toLong()))

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrcat(s.rawAddress, ct.rawAddress))

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrncat(s.rawAddress, ct.rawAddress, n.toLong()))

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrcmp(cs.rawAddress, ct.rawAddress)

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrncmp(cs.rawAddress, ct.rawAddress, n.toLong())

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrcoll(cs.rawAddress, ct.rawAddress)

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrchr(cs.rawAddress, c))

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrrchr(cs.rawAddress, c))

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrcspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrdup(cs.rawAddress))

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrndup(cs.rawAddress, n.toLong()))

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrpbrk(cs.rawAddress, ct.rawAddress))

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrstr(cs.rawAddress, ct.rawAddress))

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrcasecmp(s1.rawAddress, s2.rawAddress)

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrncasecmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun strlen(cs: ConstPtr): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrlen(cs.rawAddress).toULong()

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrnlen(cs.rawAddress, maxlen.toLong()).toULong()

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrerror(n))

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrtok(s.rawAddress, t.rawAddress))

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    LibcWasiJniBridge.rustlangLibcWasiStrxfrm(s.rawAddress, ct.rawAddress, n.toLong()).toULong()

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMemchr(cx.rawAddress, c, n.toLong()))

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiMemcmp(cx.rawAddress, ct.rawAddress, n.toLong())

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMemset(dest.rawAddress, c, n.toLong()))

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFprintf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPrintf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSnprintf(s.rawAddress, n.toLong(), format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSprintf(s.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFscanf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiScanf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSscanf(s.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getcharUnlocked(): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetcharUnlocked()

public actual fun putcharUnlocked(c: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPutcharUnlocked(c)

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiShutdown(socket, how)

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFstat(fildes, buf.rawAddress)

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiMkdir(path.rawAddress, mode.toInt())

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStat(path.rawAddress, buf.rawAddress)

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiFdopen(fd, mode.rawAddress))

public actual fun fileno(stream: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFileno(stream.rawAddress)

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiOpen(path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiCreat(path.rawAddress, mode.toInt())

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun opendir(dirname: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiOpendir(dirname.rawAddress))

public actual fun fdopendir(fd: CInt): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiFdopendir(fd))

public actual fun readdir(dirp: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiReaddir(dirp.rawAddress))

public actual fun closedir(dirp: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiClosedir(dirp.rawAddress)

public actual fun rewinddir(dirp: MutPtr) {
    LibcWasiJniBridge.rustlangLibcWasiRewinddir(dirp.rawAddress)
}

public actual fun dirfd(dirp: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiDirfd(dirp.rawAddress)

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    LibcWasiJniBridge.rustlangLibcWasiSeekdir(dirp.rawAddress, loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    LibcWasiJniBridge.rustlangLibcWasiTelldir(dirp.rawAddress)

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiOpenat(dirfd, pathname.rawAddress, flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFstatat(dirfd, pathname.rawAddress, buf.rawAddress, flags)

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiLinkat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags)

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiMkdirat(dirfd, pathname.rawAddress, mode.toInt())

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiReadlinkat(dirfd, pathname.rawAddress, buf.rawAddress, bufsiz.toLong())

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRenameat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress)

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSymlinkat(target.rawAddress, newdirfd, linkpath.rawAddress)

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUnlinkat(dirfd, pathname.rawAddress, flags)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiAccess(path.rawAddress, amode)

public actual fun close(fd: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiClose(fd)

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    LibcWasiJniBridge.rustlangLibcWasiFpathconf(filedes, name)

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetopt(argc, argv.rawAddress, optstr.rawAddress)

public actual fun isatty(fd: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIsatty(fd)

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiLink(src.rawAddress, dst.rawAddress)

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    LibcWasiJniBridge.rustlangLibcWasiLseek(fd, offset, whence)

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    LibcWasiJniBridge.rustlangLibcWasiPathconf(path.rawAddress, name)

public actual fun rmdir(path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiRmdir(path.rawAddress)

public actual fun sleep(secs: CUint): CUint =
    LibcWasiJniBridge.rustlangLibcWasiSleep(secs.toInt()).toUInt()

public actual fun unlink(c: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUnlink(c.rawAddress)

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiPread(fd, buf.rawAddress, count.toLong(), offset)

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiPwrite(fd, buf.rawAddress, count.toLong(), offset)

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiLstat(path.rawAddress, buf.rawAddress)

public actual fun fsync(fd: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFsync(fd)

public actual fun fdatasync(fd: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFdatasync(fd)

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSymlink(path1.rawAddress, path2.rawAddress)

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiTruncate(path.rawAddress, length)

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFtruncate(fd, length)

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetrusage(resource, usage.rawAddress)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGettimeofday(tp.rawAddress, tz.rawAddress)

public actual fun times(buf: MutPtr): io.github.kotlinmania.rustlang.wasi.ClockT =
    LibcWasiJniBridge.rustlangLibcWasiTimes(buf.rawAddress)

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiStrerrorR(errnum, buf.rawAddress, buflen.toLong())

public actual fun usleep(secs: CUint): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUsleep(secs.toInt())

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiSend(socket, buf.rawAddress, len.toLong(), flags)

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiRecv(socket, buf.rawAddress, len.toLong(), flags)

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPoll(fds.rawAddress, nfds.toLong(), timeout)

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiSetlocale(category, locale.rawAddress))

public actual fun localeconv(): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiLocaleconv())

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiReadlink(path.rawAddress, buf.rawAddress, bufsz.toLong())

public actual fun timegm(tm: MutPtr): TimeT =
    LibcWasiJniBridge.rustlangLibcWasiTimegm(tm.rawAddress)

public actual fun sysconf(name: CInt): CLong =
    LibcWasiJniBridge.rustlangLibcWasiSysconf(name)

public actual fun ioctl(fd: CInt, request: CInt, vararg variadicArgs: Long): CInt =
    LibcWasiJniBridge.rustlangLibcWasiIoctl(fd, request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFseeko(stream.rawAddress, offset, whence)

public actual fun ftello(stream: MutPtr): OffT =
    LibcWasiJniBridge.rustlangLibcWasiFtello(stream.rawAddress)

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPosixFallocate(fd, offset, len)

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiStrcasestr(cs.rawAddress, ct.rawAddress))

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiGetline(lineptr.rawAddress, n.rawAddress, stream.rawAddress)

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFaccessat(dirfd, pathname.rawAddress, mode, flags)

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiWritev(fd, iov.rawAddress, iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiReadv(fd, iov.rawAddress, iovcnt)

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiPwritev(fd, iov.rawAddress, iovcnt, offset)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    LibcWasiJniBridge.rustlangLibcWasiPreadv(fd, iov.rawAddress, iovcnt, offset)

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPosixFadvise(fd, offset, len, advise)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFutimens(fd, times.rawAddress)

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiUtimensat(dirfd, path.rawAddress, times.rawAddress, flag)

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiGetentropy(buf.rawAddress, buflen.toLong())

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiMemrchr(cx.rawAddress, c, n.toLong()))

public actual fun abs(i: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiAbs(i)

public actual fun labs(i: CLong): CLong =
    LibcWasiJniBridge.rustlangLibcWasiLabs(i)

public actual fun duplocale(base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiDuplocale(base.rawAddress))

public actual fun freelocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT) {
    LibcWasiJniBridge.rustlangLibcWasiFreelocale(loc.rawAddress)
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiNewlocale(mask, locale.rawAddress, base.rawAddress))

public actual fun uselocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiUselocale(loc.rawAddress))

public actual fun schedYield(): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSchedYield()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiGetcwd(buf.rawAddress, size.toLong()))

public actual fun chdir(dir: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiChdir(dir.rawAddress)

public actual fun nlLanginfo(item: io.github.kotlinmania.rustlang.wasi.NlItem): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiNlLanginfo(item))

public actual fun nlLanginfoL(item: io.github.kotlinmania.rustlang.wasi.NlItem, loc: io.github.kotlinmania.rustlang.wasi.LocaleT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiNlLanginfoL(item, loc.rawAddress))

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiSelect(nfds, readfds.rawAddress, writefds.rawAddress, errorfds.rawAddress, timeout.rawAddress)

public actual fun wasilibcRegisterPreopenedFd(fd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcRegisterPreopenedFd(fd, path.rawAddress)

public actual fun wasilibcFdRenumber(fd: CInt, newfd: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcFdRenumber(fd, newfd)

public actual fun wasilibcUnlinkat(fd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcUnlinkat(fd, path.rawAddress)

public actual fun wasilibcRmdirat(fd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcRmdirat(fd, path.rawAddress)

public actual fun wasilibcFindRelpath(path: ConstPtr, absPrefix: MutPtr, relativePath: MutPtr, relativePathLen: ULong): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcFindRelpath(path.rawAddress, absPrefix.rawAddress, relativePath.rawAddress, relativePathLen.toLong())

public actual fun wasilibcTell(fd: CInt): OffT =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcTell(fd)

public actual fun wasilibcNocwdWasilibcUnlinkat(dirfd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(dirfd, path.rawAddress)

public actual fun wasilibcNocwdWasilibcRmdirat(dirfd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdWasilibcRmdirat(dirfd, path.rawAddress)

public actual fun wasilibcNocwdLinkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdLinkat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags)

public actual fun wasilibcNocwdSymlinkat(target: ConstPtr, dirfd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdSymlinkat(target.rawAddress, dirfd, path.rawAddress)

public actual fun wasilibcNocwdReadlinkat(dirfd: CInt, path: ConstPtr, buf: MutPtr, bufsize: ULong): Long =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdReadlinkat(dirfd, path.rawAddress, buf.rawAddress, bufsize.toLong())

public actual fun wasilibcNocwdFaccessat(dirfd: CInt, path: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdFaccessat(dirfd, path.rawAddress, mode, flags)

public actual fun wasilibcNocwdRenameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdRenameat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress)

public actual fun wasilibcNocwdOpenatNomode(dirfd: CInt, path: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdOpenatNomode(dirfd, path.rawAddress, flags)

public actual fun wasilibcNocwdFstatat(dirfd: CInt, path: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdFstatat(dirfd, path.rawAddress, buf.rawAddress, flags)

public actual fun wasilibcNocwdMkdiratNomode(dirfd: CInt, path: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdMkdiratNomode(dirfd, path.rawAddress)

public actual fun wasilibcNocwdUtimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdUtimensat(dirfd, path.rawAddress, times.rawAddress, flags)

public actual fun wasilibcNocwdOpendirat(dirfd: CInt, path: ConstPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiWasilibcNocwdOpendirat(dirfd, path.rawAddress))

public actual fun wasilibcAccess(pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcAccess(pathname.rawAddress, mode, flags)

public actual fun wasilibcStat(pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcStat(pathname.rawAddress, buf.rawAddress, flags)

public actual fun wasilibcUtimens(pathname: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcUtimens(pathname.rawAddress, times.rawAddress, flags)

public actual fun wasilibcLink(oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcLink(oldpath.rawAddress, newpath.rawAddress, flags)

public actual fun wasilibcLinkOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcLinkOldat(olddirfd, oldpath.rawAddress, newpath.rawAddress, flags)

public actual fun wasilibcLinkNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcLinkNewat(oldpath.rawAddress, newdirfd, newpath.rawAddress, flags)

public actual fun wasilibcRenameOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcRenameOldat(olddirfd, oldpath.rawAddress, newpath.rawAddress)

public actual fun wasilibcRenameNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiWasilibcRenameNewat(oldpath.rawAddress, newdirfd, newpath.rawAddress)

public actual fun arc4random(): UInt =
    LibcWasiJniBridge.rustlangLibcWasiArc4random().toUInt()

public actual fun arc4randomBuf(a: MutPtr, b: SizeT) {
    LibcWasiJniBridge.rustlangLibcWasiArc4randomBuf(a.rawAddress, b.toLong())
}

public actual fun arc4randomUniform(a: UInt): UInt =
    LibcWasiJniBridge.rustlangLibcWasiArc4randomUniform(a.toInt()).toUInt()

public actual fun errnoLocation(): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiErrnoLocation())

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiChmod(path.rawAddress, mode.toInt())

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiFchmod(fd, mode.toInt())

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiRealpath(pathname.rawAddress, resolved.rawAddress))

public actual fun pthreadSelf(): PthreadT =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiPthreadSelf())

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCreate(native.rawAddress, attr.rawAddress, 0L, value.rawAddress)

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadEqual(t1.rawAddress, t2.rawAddress)

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadJoin(native.rawAddress, value.rawAddress)

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrInit(attr.rawAddress)

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrDestroy(attr.rawAddress)

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrGetstacksize(attr.rawAddress, stacksize.rawAddress)

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrSetstacksize(attr.rawAddress, stackSize.toLong())

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, state: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrGetdetachstate(attr.rawAddress, state.rawAddress)

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrSetdetachstate(attr.rawAddress, state)

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrGetguardsize(attr.rawAddress, b.rawAddress)

public actual fun pthreadAttrSetguardsize(attr: MutPtr, b: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrSetguardsize(attr.rawAddress, b.toLong())

public actual fun pthreadAttrGetstack(attr: ConstPtr, b: MutPtr, c: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrGetstack(attr.rawAddress, b.rawAddress, c.rawAddress)

public actual fun pthreadAttrSetstack(attr: MutPtr, b: MutPtr, c: SizeT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadAttrSetstack(attr.rawAddress, b.rawAddress, c.toLong())

public actual fun pthreadDetach(thread: PthreadT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadDetach(thread.rawAddress)

public actual fun pthreadSetcancelstate(a: CInt, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSetcancelstate(a, b.rawAddress)

public actual fun pthreadSetcanceltype(a: CInt, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSetcanceltype(a, b.rawAddress)

public actual fun pthreadTestcancel() {
    LibcWasiJniBridge.rustlangLibcWasiPthreadTestcancel()
}

public actual fun pthreadOnce(a: MutPtr, b: AtexitACallback): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadOnce(a.rawAddress, 0L)

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadKeyDelete(key.toInt())

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(LibcWasiJniBridge.rustlangLibcWasiPthreadGetspecific(key.toInt()))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSetspecific(key.toInt(), value.rawAddress)

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexInit(lock.rawAddress, attr.rawAddress)

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexDestroy(lock.rawAddress)

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexLock(lock.rawAddress)

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexTrylock(lock.rawAddress)

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexUnlock(lock.rawAddress)

public actual fun pthreadMutexTimedlock(lock: MutPtr, ts: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexTimedlock(lock.rawAddress, ts.rawAddress)

public actual fun pthreadMutexConsistent(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexConsistent(lock.rawAddress)

public actual fun pthreadMutexGetprioceiling(lock: MutPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexGetprioceiling(lock.rawAddress, b.rawAddress)

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrInit(attr.rawAddress)

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrDestroy(attr.rawAddress)

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrSettype(attr.rawAddress, type)

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrGetprotocol(attr.rawAddress, b.rawAddress)

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrGetpshared(attr.rawAddress, b.rawAddress)

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrGetrobust(attr.rawAddress, b.rawAddress)

public actual fun pthreadMutexattrGettype(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrGettype(attr.rawAddress, b.rawAddress)

public actual fun pthreadMutexattrSetprotocol(attr: ConstPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrSetprotocol(attr.rawAddress, b)

public actual fun pthreadMutexattrSetpshared(attr: ConstPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrSetpshared(attr.rawAddress, b)

public actual fun pthreadMutexattrSetrobust(attr: ConstPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadMutexattrSetrobust(attr.rawAddress, b)

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondInit(cond.rawAddress, attr.rawAddress)

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondWait(cond.rawAddress, lock.rawAddress)

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondTimedwait(cond.rawAddress, lock.rawAddress, abstime.rawAddress)

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondSignal(cond.rawAddress)

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondBroadcast(cond.rawAddress)

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondDestroy(cond.rawAddress)

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondattrInit(attr.rawAddress)

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondattrDestroy(attr.rawAddress)

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    0

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondattrGetclock(attr.rawAddress, clockId.rawAddress)

public actual fun pthreadCondattrSetpshared(attr: MutPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondattrSetpshared(attr.rawAddress, b)

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadCondattrGetpshared(attr.rawAddress, b.rawAddress)

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockInit(lock.rawAddress, attr.rawAddress)

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockDestroy(lock.rawAddress)

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockRdlock(lock.rawAddress)

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockTryrdlock(lock.rawAddress)

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockWrlock(lock.rawAddress)

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockTrywrlock(lock.rawAddress)

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockUnlock(lock.rawAddress)

public actual fun pthreadRwlockTimedrdlock(lock: MutPtr, at: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockTimedrdlock(lock.rawAddress, at.rawAddress)

public actual fun pthreadRwlockTimedwrlock(lock: MutPtr, at: ConstPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockTimedwrlock(lock.rawAddress, at.rawAddress)

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockattrInit(attr.rawAddress)

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockattrDestroy(attr.rawAddress)

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockattrSetpshared(attr.rawAddress, b)

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadRwlockattrGetpshared(attr.rawAddress, b.rawAddress)

public actual fun pthreadSpinInit(lock: MutPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSpinInit(lock.rawAddress, b)

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSpinDestroy(lock.rawAddress)

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSpinLock(lock.rawAddress)

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSpinTrylock(lock.rawAddress)

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadSpinUnlock(lock.rawAddress)

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierInit(barrier.rawAddress, attr.rawAddress, count.toInt())

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierDestroy(barrier.rawAddress)

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierWait(barrier.rawAddress)

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierattrInit(attr.rawAddress)

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierattrDestroy(attr.rawAddress)

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, b: CInt): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierattrSetpshared(attr.rawAddress, b)

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadBarrierattrGetpshared(attr.rawAddress, b.rawAddress)

public actual fun pthreadGetattrNp(thread: PthreadT, attr: MutPtr): CInt =
    LibcWasiJniBridge.rustlangLibcWasiPthreadGetattrNp(thread.rawAddress, attr.rawAddress)
