@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix

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
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ClockT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.WcharT

private object LibcUnixJniBridge {
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
        val stream = LibcUnixJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcUnixIsalnum(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsalpha(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIscntrl(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsgraph(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIslower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsprint(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIspunct(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsspace(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsxdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixIsblank(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTolower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixToupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixQsort(base: Long, num: Long, size: Long, compar: Long)

    @JvmStatic
    external fun rustlangLibcUnixBsearch(key: Long, base: Long, num: Long, size: Long, compar: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFopen(filename: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFreopen(filename: Long, mode: Long, file: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFflush(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFclose(file: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixRemove(filename: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixRename(oldname: Long, newname: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTmpfile(): Long

    @JvmStatic
    external fun rustlangLibcUnixSetvbuf(stream: Long, buffer: Long, mode: Int, size: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSetbuf(stream: Long, buf: Long)

    @JvmStatic
    external fun rustlangLibcUnixGetchar(): Int

    @JvmStatic
    external fun rustlangLibcUnixPutchar(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFgetc(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFgets(buf: Long, n: Int, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFputc(c: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFputs(s: Long, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPuts(s: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixUngetc(c: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFread(ptr: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFwrite(ptr: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFseek(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFtell(stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixRewind(stream: Long)

    @JvmStatic
    external fun rustlangLibcUnixFgetpos(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFsetpos(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFeof(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFerror(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixClearerr(stream: Long)

    @JvmStatic
    external fun rustlangLibcUnixPerror(s: Long)

    @JvmStatic
    external fun rustlangLibcUnixAtof(s: Long): Double

    @JvmStatic
    external fun rustlangLibcUnixAtoi(s: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixAtol(s: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixAtoll(s: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtod(s: Long, endp: Long): Double

    @JvmStatic
    external fun rustlangLibcUnixStrtof(s: Long, endp: Long): Float

    @JvmStatic
    external fun rustlangLibcUnixStrtol(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtoll(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtoul(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtoull(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixCalloc(nobj: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMalloc(size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixRealloc(p: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFree(p: Long)

    @JvmStatic
    external fun rustlangLibcUnixAbort()

    @JvmStatic
    external fun rustlangLibcUnixExit(status: Int)

    @JvmStatic
    external fun rustlangLibcUnixSystem(s: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetenv(s: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrcpy(dst: Long, src: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrncpy(dst: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStpcpy(dst: Long, src: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrcat(s: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrncat(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrcmp(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStrncmp(cs: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStrcoll(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrcspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrdup(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrndup(cs: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrpbrk(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrstr(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrcasecmp(s1: Long, s2: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStrncasecmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStrlen(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrnlen(cs: Long, maxlen: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrerror(n: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtok(s: Long, t: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrtokR(s: Long, t: Long, p: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrxfrm(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixStrsignal(sig: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixWcslen(buf: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixWcstombs(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMemchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixWmemchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMemcmp(cx: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMemset(dest: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMemccpy(dest: Long, src: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetpwnam(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetpwuid(uid: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixFprintf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPrintf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSnprintf(s: Long, n: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSprintf(s: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFscanf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixScanf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSscanf(s: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetcharUnlocked(): Int

    @JvmStatic
    external fun rustlangLibcUnixPutcharUnlocked(c: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSocket(domain: Int, ty: Int, protocol: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixConnect(socket: Int, address: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixListen(socket: Int, backlog: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixAccept(socket: Int, address: Long, addressLen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetpeername(socket: Int, address: Long, addressLen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetsockname(socket: Int, address: Long, addressLen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSetsockopt(socket: Int, level: Int, name: Int, value: Long, optionLen: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSocketpair(domain: Int, type: Int, protocol: Int, socketVector: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSendto(socket: Int, buf: Long, len: Long, flags: Int, addr: Long, addrlen: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixShutdown(socket: Int, how: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixChmod(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFchmod(fd: Int, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFstat(fildes: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixMkdir(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixStat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPclose(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFdopen(fd: Int, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFileno(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixOpen(path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixCreat(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFcntl(fd: Int, cmd: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixOpendir(dirname: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixFdopendir(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixReaddir(dirp: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixClosedir(dirp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixRewinddir(dirp: Long)

    @JvmStatic
    external fun rustlangLibcUnixFchmodat(dirfd: Int, pathname: Long, mode: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFchown(fd: Int, owner: Int, group: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFchownat(dirfd: Int, pathname: Long, owner: Int, group: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixOpenat(dirfd: Int, pathname: Long, flags: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFstatat(dirfd: Int, pathname: Long, buf: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinkat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixMkdirat(dirfd: Int, pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixRenameat(olddirfd: Int, oldpath: Long, newdirfd: Int, newpath: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSymlinkat(target: Long, newdirfd: Int, linkpath: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixUnlinkat(dirfd: Int, pathname: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixAccess(path: Long, amode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixAlarm(seconds: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixChdir(dir: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFchdir(dirfd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixChown(path: Long, uid: Int, gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLchown(path: Long, uid: Int, gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixClose(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixDup(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixDup2(src: Int, dst: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixExecl(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixExecle(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixExeclp(file: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixExecv(prog: Long, argv: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixExecve(prog: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixExecvp(c: Long, argv: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFork(): Int

    @JvmStatic
    external fun rustlangLibcUnixFpathconf(filedes: Int, name: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixGetcwd(buf: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetegid(): Int

    @JvmStatic
    external fun rustlangLibcUnixGeteuid(): Int

    @JvmStatic
    external fun rustlangLibcUnixGetgid(): Int

    @JvmStatic
    external fun rustlangLibcUnixGetgroups(ngroupsMax: Int, groups: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetlogin(): Long

    @JvmStatic
    external fun rustlangLibcUnixGetopt(argc: Int, argv: Long, optstr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetpgid(pid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixGetpgrp(): Int

    @JvmStatic
    external fun rustlangLibcUnixGetpid(): Int

    @JvmStatic
    external fun rustlangLibcUnixGetppid(): Int

    @JvmStatic
    external fun rustlangLibcUnixGetuid(): Int

    @JvmStatic
    external fun rustlangLibcUnixIsatty(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLink(src: Long, dst: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLseek(fd: Int, offset: Long, whence: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixPathconf(path: Long, name: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixPause(): Int

    @JvmStatic
    external fun rustlangLibcUnixPipe(fds: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPosixMemalign(memptr: Long, align: Long, size: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixAlignedAlloc(alignment: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixRead(fd: Int, buf: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixRmdir(path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSeteuid(uid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetegid(gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetgid(gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetpgid(pid: Int, pgid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetsid(): Int

    @JvmStatic
    external fun rustlangLibcUnixSetuid(uid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetreuid(ruid: Int, euid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetregid(rgid: Int, egid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSleep(secs: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixNanosleep(rqtp: Long, rmtp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTcgetpgrp(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTcsetpgrp(fd: Int, pgrp: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTtyname(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixTtynameR(fd: Int, buf: Long, buflen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixUnlink(c: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixWait(status: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixWaitpid(pid: Int, status: Long, options: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixWrite(fd: Int, buf: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixPread(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixPwrite(fd: Int, buf: Long, count: Long, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixUmask(mask: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixUtime(file: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixKill(pid: Int, sig: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixKillpg(pgrp: Int, sig: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixMlock(addr: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixMunlock(addr: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixMlockall(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixMunlockall(): Int

    @JvmStatic
    external fun rustlangLibcUnixMmap(addr: Long, len: Long, prot: Int, flags: Int, fd: Int, offset: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMunmap(addr: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixIfNametoindex(ifname: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixIfIndextoname(ifindex: Int, ifname: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLstat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFsync(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSetenv(name: Long, value: Long, overwrite: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixUnsetenv(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSymlink(path1: Long, path2: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTruncate(path: Long, length: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFtruncate(fd: Int, length: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSignal(signum: Int, handler: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetrusage(resource: Int, usage: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixRealpath(pathname: Long, resolved: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixTimes(buf: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixPthreadSelf(): Long

    @JvmStatic
    external fun rustlangLibcUnixPthreadEqual(t1: Long, t2: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadJoin(native: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadExit(value: Long)

    @JvmStatic
    external fun rustlangLibcUnixPthreadAttrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadAttrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadAttrGetstacksize(attr: Long, stacksize: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadAttrSetstacksize(attr: Long, stackSize: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadAttrSetdetachstate(attr: Long, state: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadDetach(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSchedYield(): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadKeyCreate(key: Long, dtor: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadKeyDelete(key: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexInit(lock: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexLock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexTrylock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexUnlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadMutexattrSettype(attr: Long, type: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondInit(cond: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondWait(cond: Long, lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondTimedwait(cond: Long, lock: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondSignal(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondBroadcast(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondDestroy(cond: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadCondattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockInit(lock: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockRdlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockTryrdlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockWrlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockTrywrlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockUnlock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPthreadRwlockattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetsockopt(sockfd: Int, level: Int, optname: Int, optval: Long, optlen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixRaise(signum: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixUtimes(filename: Long, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixDlopen(filename: Long, flag: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixDlerror(): Long

    @JvmStatic
    external fun rustlangLibcUnixDlsym(handle: Long, symbol: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixDlclose(handle: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixGetaddrinfo(node: Long, service: Long, hints: Long, res: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFreeaddrinfo(res: Long)

    @JvmStatic
    external fun rustlangLibcUnixHstrerror(errcode: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixGaiStrerror(errcode: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixResInit(): Int

    @JvmStatic
    external fun rustlangLibcUnixGmtimeR(timeP: Long, result: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLocaltimeR(timeP: Long, result: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMktime(tm: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixTime(time: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGmtime(timeP: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLocaltime(timeP: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixDifftime(time1: Long, time0: Long): Double

    @JvmStatic
    external fun rustlangLibcUnixTimegm(tm: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixMknod(pathname: Long, mode: Int, dev: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixGethostname(name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixEndservent()

    @JvmStatic
    external fun rustlangLibcUnixGetservbyname(name: Long, proto: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetservbyport(port: Int, proto: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetservent(): Long

    @JvmStatic
    external fun rustlangLibcUnixSetservent(stayopen: Int)

    @JvmStatic
    external fun rustlangLibcUnixGetprotobyname(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetprotobynumber(proto: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixChroot(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixUsleep(secs: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSend(socket: Int, buf: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixRecv(socket: Int, buf: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixPutenv(string: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPoll(fds: Long, nfds: Long, timeout: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSelect(nfds: Int, readfds: Long, writefds: Long, errorfds: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSetlocale(category: Int, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLocaleconv(): Long

    @JvmStatic
    external fun rustlangLibcUnixSemWait(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSemTrywait(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSemPost(sem: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStatvfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFstatvfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSigemptyset(set: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSigaddset(set: Long, signum: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSigfillset(set: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSigdelset(set: Long, signum: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSigismember(set: Long, signum: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSigprocmask(how: Int, set: Long, oldset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSigpending(set: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSysconf(name: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixMkfifo(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFseeko(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixFtello(stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixTcdrain(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixCfgetispeed(termios: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixCfgetospeed(termios: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixCfsetispeed(termios: Long, speed: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixCfsetospeed(termios: Long, speed: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTcgetattr(fd: Int, termios: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTcsetattr(fd: Int, optionalActions: Int, termios: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixTcflow(fd: Int, action: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTcflush(fd: Int, action: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTcgetsid(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixTcsendbreak(fd: Int, duration: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixMkstemp(template: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixMkdtemp(template: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixTmpnam(ptr: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixOpenlog(ident: Long, logopt: Int, facility: Int)

    @JvmStatic
    external fun rustlangLibcUnixCloselog()

    @JvmStatic
    external fun rustlangLibcUnixSetlogmask(maskpri: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixSyslog(priority: Int, message: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long)

    @JvmStatic
    external fun rustlangLibcUnixNice(incr: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixGrantpt(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixPosixOpenpt(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixPtsname(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixUnlockpt(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixStrcasestr(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetline(lineptr: Long, n: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLockf(fd: Int, cmd: Int, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixAdjtime(delta: Long, olddelta: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixStpncpy(dst: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixConfstr(name: Int, buf: Long, len: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixDladdr(addr: Long, info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFlock(fd: Int, operation: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixOpenWmemstream(ptr: Long, sizeloc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixGetsid(pid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixReaddirR(dirp: Long, entry: Long, result: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixReadlinkat(dirfd: Int, pathname: Long, buf: Long, bufsiz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixReadlink(path: Long, buf: Long, bufsz: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixPselect(nfds: Int, readfds: Long, writefds: Long, errorfds: Long, timeout: Long, sigmask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixSigaction(signum: Int, act: Long, oldact: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFmemopen(buf: Long, size: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixOpenMemstream(ptr: Long, sizeloc: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixAtexit(cb: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixCfmakeraw(termios: Long)

    @JvmStatic
    external fun rustlangLibcUnixCfsetspeed(termios: Long, speed: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixFnmatch(pattern: Long, name: Long, flags: Int): Int
}

public actual val IN6ADDR_LOOPBACK: In6Addr
    get() = In6Addr()

public actual val IN6ADDR_ANY: In6Addr
    get() = In6Addr()

public actual fun isalnum(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsgraph(c)

public actual fun islower(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIslower(c)

public actual fun isprint(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIspunct(c)

public actual fun isspace(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsspace(c)

public actual fun isupper(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsblank(c)

public actual fun tolower(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTolower(c)

public actual fun toupper(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixToupper(c)

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    LibcUnixJniBridge.rustlangLibcUnixQsort(base.rawAddress, num.toLong(), size.toLong(), 0L)
}

public actual fun bsearch(key: ConstPtr, base: ConstPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixBsearch(key.rawAddress, base.rawAddress, num.toLong(), size.toLong(), 0L))

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFopen(filename.rawAddress, mode.rawAddress))

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFreopen(filename.rawAddress, mode.rawAddress, file.rawAddress))

public actual fun fflush(file: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFflush(file.rawAddress)

public actual fun fclose(file: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFclose(file.rawAddress)

public actual fun remove(filename: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixRemove(filename.rawAddress)

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixRename(oldname.rawAddress, newname.rawAddress)

public actual fun tmpfile(): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixTmpfile())

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetvbuf(stream.rawAddress, buffer.rawAddress, mode, size.toLong())

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixSetbuf(stream.rawAddress, buf.rawAddress)
}

public actual fun getchar(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetchar()

public actual fun putchar(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPutchar(c)

public actual fun fgetc(stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFgetc(stream.rawAddress)

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFgets(buf.rawAddress, n, stream.rawAddress))

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFputc(c, stream.rawAddress)

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFputs(s.rawAddress, stream.rawAddress)

public actual fun puts(s: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPuts(s.rawAddress)

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUngetc(c, stream.rawAddress)

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixFread(ptr.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixFwrite(ptr.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFseek(stream.rawAddress, offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    LibcUnixJniBridge.rustlangLibcUnixFtell(stream.rawAddress)

public actual fun rewind(stream: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixRewind(stream.rawAddress)
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFgetpos(stream.rawAddress, ptr.rawAddress)

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFsetpos(stream.rawAddress, ptr.rawAddress)

public actual fun feof(stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFeof(stream.rawAddress)

public actual fun ferror(stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFerror(stream.rawAddress)

public actual fun clearerr(stream: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixClearerr(stream.rawAddress)
}

public actual fun perror(s: ConstPtr) {
    LibcUnixJniBridge.rustlangLibcUnixPerror(s.rawAddress)
}

public actual fun atof(s: ConstPtr): CDouble =
    LibcUnixJniBridge.rustlangLibcUnixAtof(s.rawAddress)

public actual fun atoi(s: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixAtoi(s.rawAddress)

public actual fun atol(s: ConstPtr): CLong =
    LibcUnixJniBridge.rustlangLibcUnixAtol(s.rawAddress)

public actual fun atoll(s: ConstPtr): CLonglong =
    LibcUnixJniBridge.rustlangLibcUnixAtoll(s.rawAddress)

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    LibcUnixJniBridge.rustlangLibcUnixStrtod(s.rawAddress, endp.rawAddress)

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    LibcUnixJniBridge.rustlangLibcUnixStrtof(s.rawAddress, endp.rawAddress)

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    LibcUnixJniBridge.rustlangLibcUnixStrtol(s.rawAddress, endp.rawAddress, base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    LibcUnixJniBridge.rustlangLibcUnixStrtoll(s.rawAddress, endp.rawAddress, base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    LibcUnixJniBridge.rustlangLibcUnixStrtoul(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    LibcUnixJniBridge.rustlangLibcUnixStrtoull(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixCalloc(nobj.toLong(), size.toLong()))

public actual fun malloc(size: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMalloc(size.toLong()))

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixRealloc(p.rawAddress, size.toLong()))

public actual fun free(p: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixFree(p.rawAddress)
}

public actual fun abort(): Nothing {
    LibcUnixJniBridge.rustlangLibcUnixAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    LibcUnixJniBridge.rustlangLibcUnixExit(status)
    error("Unreachable noreturn C function exit")
}

public actual fun system(s: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSystem(s.rawAddress)

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetenv(s.rawAddress))

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrcpy(dst.rawAddress, src.rawAddress))

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrncpy(dst.rawAddress, src.rawAddress, n.toLong()))

public actual fun stpcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStpcpy(dst.rawAddress, src.rawAddress))

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrcat(s.rawAddress, ct.rawAddress))

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrncat(s.rawAddress, ct.rawAddress, n.toLong()))

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStrcmp(cs.rawAddress, ct.rawAddress)

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStrncmp(cs.rawAddress, ct.rawAddress, n.toLong())

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStrcoll(cs.rawAddress, ct.rawAddress)

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrchr(cs.rawAddress, c))

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrrchr(cs.rawAddress, c))

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixStrspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixStrcspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrdup(cs.rawAddress))

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrndup(cs.rawAddress, n.toLong()))

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrpbrk(cs.rawAddress, ct.rawAddress))

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrstr(cs.rawAddress, ct.rawAddress))

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStrcasecmp(s1.rawAddress, s2.rawAddress)

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStrncasecmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun strlen(cs: ConstPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixStrlen(cs.rawAddress).toULong()

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixStrnlen(cs.rawAddress, maxlen.toLong()).toULong()

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrerror(n))

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrtok(s.rawAddress, t.rawAddress))

public actual fun strtokR(s: MutPtr, t: ConstPtr, p: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrtokR(s.rawAddress, t.rawAddress, p.rawAddress))

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixStrxfrm(s.rawAddress, ct.rawAddress, n.toLong()).toULong()

public actual fun strsignal(sig: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrsignal(sig))

public actual fun wcslen(buf: ConstPtr): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixWcslen(buf.rawAddress).toULong()

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixWcstombs(dest.rawAddress, src.rawAddress, n.toLong()).toULong()

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMemchr(cx.rawAddress, c, n.toLong()))

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixWmemchr(cx.rawAddress, c.toInt(), n.toLong()))

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMemcmp(cx.rawAddress, ct.rawAddress, n.toLong())

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMemset(dest.rawAddress, c, n.toLong()))

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMemccpy(dest.rawAddress, src.rawAddress, c, n.toLong()))

public actual fun getpwnam(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetpwnam(name.rawAddress))

public actual fun getpwuid(uid: io.github.kotlinmania.rustlang.unix.UidT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetpwuid(uid))

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFprintf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPrintf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSnprintf(s.rawAddress, n.toLong(), format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSprintf(s.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFscanf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixScanf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSscanf(s.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getcharUnlocked(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetcharUnlocked()

public actual fun putcharUnlocked(c: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPutcharUnlocked(c)

public actual fun socket(domain: CInt, ty: CInt, protocol: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSocket(domain, ty, protocol)

public actual fun connect(socket: CInt, address: ConstPtr, len: SocklenT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixConnect(socket, address.rawAddress, len.toInt())

public actual fun listen(socket: CInt, backlog: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixListen(socket, backlog)

public actual fun accept(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixAccept(socket, address.rawAddress, addressLen.rawAddress)

public actual fun getpeername(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetpeername(socket, address.rawAddress, addressLen.rawAddress)

public actual fun getsockname(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetsockname(socket, address.rawAddress, addressLen.rawAddress)

public actual fun setsockopt(socket: CInt, level: CInt, name: CInt, value: ConstPtr, optionLen: SocklenT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetsockopt(socket, level, name, value.rawAddress, optionLen.toInt())

public actual fun socketpair(domain: CInt, type: CInt, protocol: CInt, socketVector: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSocketpair(domain, type, protocol, socketVector.rawAddress)

public actual fun sendto(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt, addr: ConstPtr, addrlen: SocklenT): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixSendto(socket, buf.rawAddress, len.toLong(), flags, addr.rawAddress, addrlen.toInt())

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixShutdown(socket, how)

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixChmod(path.rawAddress, mode.toInt())

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFchmod(fd, mode.toInt())

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFstat(fildes, buf.rawAddress)

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMkdir(path.rawAddress, mode.toInt())

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStat(path.rawAddress, buf.rawAddress)

public actual fun pclose(stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPclose(stream.rawAddress)

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFdopen(fd, mode.rawAddress))

public actual fun fileno(stream: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFileno(stream.rawAddress)

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixOpen(path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixCreat(path.rawAddress, mode.toInt())

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun opendir(dirname: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixOpendir(dirname.rawAddress))

public actual fun fdopendir(fd: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFdopendir(fd))

public actual fun readdir(dirp: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixReaddir(dirp.rawAddress))

public actual fun closedir(dirp: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixClosedir(dirp.rawAddress)

public actual fun rewinddir(dirp: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixRewinddir(dirp.rawAddress)
}

public actual fun fchmodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFchmodat(dirfd, pathname.rawAddress, mode.toInt(), flags)

public actual fun fchown(fd: CInt, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFchown(fd, owner, group)

public actual fun fchownat(dirfd: CInt, pathname: ConstPtr, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFchownat(dirfd, pathname.rawAddress, owner, group, flags)

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixOpenat(dirfd, pathname.rawAddress, flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFstatat(dirfd, pathname.rawAddress, buf.rawAddress, flags)

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixLinkat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress, flags)

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMkdirat(dirfd, pathname.rawAddress, mode.toInt())

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixRenameat(olddirfd, oldpath.rawAddress, newdirfd, newpath.rawAddress)

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSymlinkat(target.rawAddress, newdirfd, linkpath.rawAddress)

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUnlinkat(dirfd, pathname.rawAddress, flags)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixAccess(path.rawAddress, amode)

public actual fun alarm(seconds: CUint): CUint =
    LibcUnixJniBridge.rustlangLibcUnixAlarm(seconds.toInt()).toUInt()

public actual fun chdir(dir: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixChdir(dir.rawAddress)

public actual fun fchdir(dirfd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFchdir(dirfd)

public actual fun chown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixChown(path.rawAddress, uid, gid)

public actual fun lchown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixLchown(path.rawAddress, uid, gid)

public actual fun close(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixClose(fd)

public actual fun dup(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixDup(fd)

public actual fun dup2(src: CInt, dst: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixDup2(src, dst)

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExecl(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExecle(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlp(file: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExeclp(file.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execv(prog: ConstPtr, argv: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExecv(prog.rawAddress, argv.rawAddress)

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExecve(prog.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixExecvp(c.rawAddress, argv.rawAddress)

public actual fun fork(): PidT =
    LibcUnixJniBridge.rustlangLibcUnixFork()

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    LibcUnixJniBridge.rustlangLibcUnixFpathconf(filedes, name)

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetcwd(buf.rawAddress, size.toLong()))

public actual fun getegid(): GidT =
    LibcUnixJniBridge.rustlangLibcUnixGetegid()

public actual fun geteuid(): UidT =
    LibcUnixJniBridge.rustlangLibcUnixGeteuid()

public actual fun getgid(): GidT =
    LibcUnixJniBridge.rustlangLibcUnixGetgid()

public actual fun getgroups(ngroupsMax: CInt, groups: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetgroups(ngroupsMax, groups.rawAddress)

public actual fun getlogin(): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetlogin())

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetopt(argc, argv.rawAddress, optstr.rawAddress)

public actual fun getpgid(pid: PidT): PidT =
    LibcUnixJniBridge.rustlangLibcUnixGetpgid(pid)

public actual fun getpgrp(): PidT =
    LibcUnixJniBridge.rustlangLibcUnixGetpgrp()

public actual fun getpid(): PidT =
    LibcUnixJniBridge.rustlangLibcUnixGetpid()

public actual fun getppid(): PidT =
    LibcUnixJniBridge.rustlangLibcUnixGetppid()

public actual fun getuid(): UidT =
    LibcUnixJniBridge.rustlangLibcUnixGetuid()

public actual fun isatty(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixIsatty(fd)

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixLink(src.rawAddress, dst.rawAddress)

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    LibcUnixJniBridge.rustlangLibcUnixLseek(fd, offset, whence)

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    LibcUnixJniBridge.rustlangLibcUnixPathconf(path.rawAddress, name)

public actual fun pause(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPause()

public actual fun pipe(fds: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPipe(fds.rawAddress)

public actual fun posixMemalign(memptr: MutPtr, align: SizeT, size: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPosixMemalign(memptr.rawAddress, align.toLong(), size.toLong())

public actual fun alignedAlloc(alignment: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixAlignedAlloc(alignment.toLong(), size.toLong()))

public actual fun read(fd: CInt, buf: MutPtr, count: SizeT): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixRead(fd, buf.rawAddress, count.toLong())

public actual fun rmdir(path: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixRmdir(path.rawAddress)

public actual fun seteuid(uid: UidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSeteuid(uid)

public actual fun setegid(gid: GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetegid(gid)

public actual fun setgid(gid: GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetgid(gid)

public actual fun setpgid(pid: PidT, pgid: PidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetpgid(pid, pgid)

public actual fun setsid(): PidT =
    LibcUnixJniBridge.rustlangLibcUnixSetsid()

public actual fun setuid(uid: UidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetuid(uid)

public actual fun setreuid(ruid: UidT, euid: UidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetreuid(ruid, euid)

public actual fun setregid(rgid: GidT, egid: GidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetregid(rgid, egid)

public actual fun sleep(secs: CUint): CUint =
    LibcUnixJniBridge.rustlangLibcUnixSleep(secs.toInt()).toUInt()

public actual fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixNanosleep(rqtp.rawAddress, rmtp.rawAddress)

public actual fun tcgetpgrp(fd: CInt): PidT =
    LibcUnixJniBridge.rustlangLibcUnixTcgetpgrp(fd)

public actual fun tcsetpgrp(fd: CInt, pgrp: io.github.kotlinmania.rustlang.unix.PidT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcsetpgrp(fd, pgrp)

public actual fun ttyname(fd: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixTtyname(fd))

public actual fun ttynameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTtynameR(fd, buf.rawAddress, buflen.toLong())

public actual fun unlink(c: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUnlink(c.rawAddress)

public actual fun wait(status: MutPtr): PidT =
    LibcUnixJniBridge.rustlangLibcUnixWait(status.rawAddress)

public actual fun waitpid(pid: PidT, status: MutPtr, options: CInt): PidT =
    LibcUnixJniBridge.rustlangLibcUnixWaitpid(pid, status.rawAddress, options)

public actual fun write(fd: CInt, buf: ConstPtr, count: SizeT): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixWrite(fd, buf.rawAddress, count.toLong())

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixPread(fd, buf.rawAddress, count.toLong(), offset)

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixPwrite(fd, buf.rawAddress, count.toLong(), offset)

public actual fun umask(mask: ModeT): ModeT =
    LibcUnixJniBridge.rustlangLibcUnixUmask(mask.toInt()).toUInt()

public actual fun utime(file: ConstPtr, buf: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUtime(file.rawAddress, buf.rawAddress)

public actual fun kill(pid: PidT, sig: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixKill(pid, sig)

public actual fun killpg(pgrp: PidT, sig: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixKillpg(pgrp, sig)

public actual fun mlock(addr: ConstPtr, len: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMlock(addr.rawAddress, len.toLong())

public actual fun munlock(addr: ConstPtr, len: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMunlock(addr.rawAddress, len.toLong())

public actual fun mlockall(flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMlockall(flags)

public actual fun munlockall(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMunlockall()

public actual fun mmap(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: OffT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMmap(addr.rawAddress, len.toLong(), prot, flags, fd, offset))

public actual fun munmap(addr: MutPtr, len: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMunmap(addr.rawAddress, len.toLong())

public actual fun ifNametoindex(ifname: ConstPtr): CUint =
    LibcUnixJniBridge.rustlangLibcUnixIfNametoindex(ifname.rawAddress).toUInt()

public actual fun ifIndextoname(ifindex: CUint, ifname: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixIfIndextoname(ifindex.toInt(), ifname.rawAddress))

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixLstat(path.rawAddress, buf.rawAddress)

public actual fun fsync(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFsync(fd)

public actual fun setenv(name: ConstPtr, value: ConstPtr, overwrite: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetenv(name.rawAddress, value.rawAddress, overwrite)

public actual fun unsetenv(name: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUnsetenv(name.rawAddress)

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSymlink(path1.rawAddress, path2.rawAddress)

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTruncate(path.rawAddress, length)

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFtruncate(fd, length)

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    LibcUnixJniBridge.rustlangLibcUnixSignal(signum, handler.toLong()).toULong()

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetrusage(resource, usage.rawAddress)

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixRealpath(pathname.rawAddress, resolved.rawAddress))

public actual fun times(buf: MutPtr): ClockT =
    LibcUnixJniBridge.rustlangLibcUnixTimes(buf.rawAddress)

public actual fun pthreadSelf(): PthreadT =
    LibcUnixJniBridge.rustlangLibcUnixPthreadSelf().toULong()

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadEqual(t1.toLong(), t2.toLong())

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadJoin(native.toLong(), value.rawAddress)

public actual fun pthreadExit(value: MutPtr): Nothing {
    LibcUnixJniBridge.rustlangLibcUnixPthreadExit(value.rawAddress)
    error("Unreachable noreturn C function pthreadExit")
}

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadAttrInit(attr.rawAddress)

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadAttrDestroy(attr.rawAddress)

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadAttrGetstacksize(attr.rawAddress, stacksize.rawAddress)

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadAttrSetstacksize(attr.rawAddress, stackSize.toLong())

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadAttrSetdetachstate(attr.rawAddress, state)

public actual fun pthreadDetach(thread: PthreadT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadDetach(thread.toLong())

public actual fun schedYield(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSchedYield()

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadKeyDelete(key.toInt())

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixPthreadGetspecific(key.toInt()))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadSetspecific(key.toInt(), value.rawAddress)

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexInit(lock.rawAddress, attr.rawAddress)

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexDestroy(lock.rawAddress)

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexLock(lock.rawAddress)

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexTrylock(lock.rawAddress)

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexUnlock(lock.rawAddress)

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexattrInit(attr.rawAddress)

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexattrDestroy(attr.rawAddress)

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadMutexattrSettype(attr.rawAddress, type)

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondInit(cond.rawAddress, attr.rawAddress)

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondWait(cond.rawAddress, lock.rawAddress)

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondTimedwait(cond.rawAddress, lock.rawAddress, abstime.rawAddress)

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondSignal(cond.rawAddress)

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondBroadcast(cond.rawAddress)

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondDestroy(cond.rawAddress)

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondattrInit(attr.rawAddress)

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadCondattrDestroy(attr.rawAddress)

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockInit(lock.rawAddress, attr.rawAddress)

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockDestroy(lock.rawAddress)

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockRdlock(lock.rawAddress)

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockTryrdlock(lock.rawAddress)

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockWrlock(lock.rawAddress)

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockTrywrlock(lock.rawAddress)

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockUnlock(lock.rawAddress)

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockattrInit(attr.rawAddress)

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPthreadRwlockattrDestroy(attr.rawAddress)

public actual fun getsockopt(sockfd: CInt, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetsockopt(sockfd, level, optname, optval.rawAddress, optlen.rawAddress)

public actual fun raise(signum: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixRaise(signum)

public actual fun utimes(filename: ConstPtr, times: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUtimes(filename.rawAddress, times.rawAddress)

public actual fun dlopen(filename: ConstPtr, flag: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixDlopen(filename.rawAddress, flag))

public actual fun dlerror(): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixDlerror())

public actual fun dlsym(handle: MutPtr, symbol: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixDlsym(handle.rawAddress, symbol.rawAddress))

public actual fun dlclose(handle: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixDlclose(handle.rawAddress)

public actual fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGetaddrinfo(node.rawAddress, service.rawAddress, hints.rawAddress, res.rawAddress)

public actual fun freeaddrinfo(res: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixFreeaddrinfo(res.rawAddress)
}

public actual fun hstrerror(errcode: CInt): ConstPtr =
    ConstPtr(LibcUnixJniBridge.rustlangLibcUnixHstrerror(errcode))

public actual fun gaiStrerror(errcode: CInt): ConstPtr =
    ConstPtr(LibcUnixJniBridge.rustlangLibcUnixGaiStrerror(errcode))

public actual fun resInit(): CInt =
    LibcUnixJniBridge.rustlangLibcUnixResInit()

public actual fun gmtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGmtimeR(timeP.rawAddress, result.rawAddress))

public actual fun localtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixLocaltimeR(timeP.rawAddress, result.rawAddress))

public actual fun mktime(tm: MutPtr): TimeT =
    LibcUnixJniBridge.rustlangLibcUnixMktime(tm.rawAddress)

public actual fun time(time: MutPtr): TimeT =
    LibcUnixJniBridge.rustlangLibcUnixTime(time.rawAddress)

public actual fun gmtime(timeP: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGmtime(timeP.rawAddress))

public actual fun localtime(timeP: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixLocaltime(timeP.rawAddress))

public actual fun difftime(time1: TimeT, time0: TimeT): CDouble =
    LibcUnixJniBridge.rustlangLibcUnixDifftime(time1, time0)

public actual fun timegm(tm: MutPtr): TimeT =
    LibcUnixJniBridge.rustlangLibcUnixTimegm(tm.rawAddress)

public actual fun mknod(pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMknod(pathname.rawAddress, mode.toInt(), dev)

public actual fun gethostname(name: MutPtr, len: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGethostname(name.rawAddress, len.toLong())

public actual fun endservent() {
    LibcUnixJniBridge.rustlangLibcUnixEndservent()
}

public actual fun getservbyname(name: ConstPtr, proto: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetservbyname(name.rawAddress, proto.rawAddress))

public actual fun getservbyport(port: CInt, proto: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetservbyport(port, proto.rawAddress))

public actual fun getservent(): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetservent())

public actual fun setservent(stayopen: CInt) {
    LibcUnixJniBridge.rustlangLibcUnixSetservent(stayopen)
}

public actual fun getprotobyname(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetprotobyname(name.rawAddress))

public actual fun getprotobynumber(proto: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixGetprotobynumber(proto))

public actual fun chroot(name: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixChroot(name.rawAddress)

public actual fun usleep(secs: CUint): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUsleep(secs.toInt())

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixSend(socket, buf.rawAddress, len.toLong(), flags)

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixRecv(socket, buf.rawAddress, len.toLong(), flags)

public actual fun putenv(string: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPutenv(string.rawAddress)

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPoll(fds.rawAddress, nfds.toLong(), timeout)

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSelect(nfds, readfds.rawAddress, writefds.rawAddress, errorfds.rawAddress, timeout.rawAddress)

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixSetlocale(category, locale.rawAddress))

public actual fun localeconv(): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixLocaleconv())

public actual fun semWait(sem: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSemWait(sem.rawAddress)

public actual fun semTrywait(sem: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSemTrywait(sem.rawAddress)

public actual fun semPost(sem: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSemPost(sem.rawAddress)

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixStatvfs(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFstatvfs(fd, buf.rawAddress)

public actual fun sigemptyset(set: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigemptyset(set.rawAddress)

public actual fun sigaddset(set: MutPtr, signum: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigaddset(set.rawAddress, signum)

public actual fun sigfillset(set: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigfillset(set.rawAddress)

public actual fun sigdelset(set: MutPtr, signum: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigdelset(set.rawAddress, signum)

public actual fun sigismember(set: ConstPtr, signum: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigismember(set.rawAddress, signum)

public actual fun sigprocmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigprocmask(how, set.rawAddress, oldset.rawAddress)

public actual fun sigpending(set: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigpending(set.rawAddress)

public actual fun sysconf(name: CInt): CLong =
    LibcUnixJniBridge.rustlangLibcUnixSysconf(name)

public actual fun mkfifo(path: ConstPtr, mode: ModeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMkfifo(path.rawAddress, mode.toInt())

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFseeko(stream.rawAddress, offset, whence)

public actual fun ftello(stream: MutPtr): OffT =
    LibcUnixJniBridge.rustlangLibcUnixFtello(stream.rawAddress)

public actual fun tcdrain(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcdrain(fd)

public actual fun cfgetispeed(termios: ConstPtr): SpeedT =
    LibcUnixJniBridge.rustlangLibcUnixCfgetispeed(termios.rawAddress).toULong()

public actual fun cfgetospeed(termios: ConstPtr): SpeedT =
    LibcUnixJniBridge.rustlangLibcUnixCfgetospeed(termios.rawAddress).toULong()

public actual fun cfsetispeed(termios: MutPtr, speed: SpeedT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixCfsetispeed(termios.rawAddress, speed.toLong())

public actual fun cfsetospeed(termios: MutPtr, speed: SpeedT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixCfsetospeed(termios.rawAddress, speed.toLong())

public actual fun tcgetattr(fd: CInt, termios: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcgetattr(fd, termios.rawAddress)

public actual fun tcsetattr(fd: CInt, optionalActions: CInt, termios: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcsetattr(fd, optionalActions, termios.rawAddress)

public actual fun tcflow(fd: CInt, action: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcflow(fd, action)

public actual fun tcflush(fd: CInt, action: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcflush(fd, action)

public actual fun tcgetsid(fd: CInt): io.github.kotlinmania.rustlang.unix.PidT =
    LibcUnixJniBridge.rustlangLibcUnixTcgetsid(fd)

public actual fun tcsendbreak(fd: CInt, duration: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixTcsendbreak(fd, duration)

public actual fun mkstemp(template: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixMkstemp(template.rawAddress)

public actual fun mkdtemp(template: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixMkdtemp(template.rawAddress))

public actual fun tmpnam(ptr: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixTmpnam(ptr.rawAddress))

public actual fun openlog(ident: ConstPtr, logopt: CInt, facility: CInt) {
    LibcUnixJniBridge.rustlangLibcUnixOpenlog(ident.rawAddress, logopt, facility)
}

public actual fun closelog() {
    LibcUnixJniBridge.rustlangLibcUnixCloselog()
}

public actual fun setlogmask(maskpri: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSetlogmask(maskpri)

public actual fun syslog(priority: CInt, message: ConstPtr, vararg variadicArgs: Long) {
    LibcUnixJniBridge.rustlangLibcUnixSyslog(priority, message.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
}

public actual fun nice(incr: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixNice(incr)

public actual fun grantpt(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixGrantpt(fd)

public actual fun posixOpenpt(flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPosixOpenpt(flags)

public actual fun ptsname(fd: CInt): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixPtsname(fd))

public actual fun unlockpt(fd: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixUnlockpt(fd)

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStrcasestr(cs.rawAddress, ct.rawAddress))

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    LibcUnixJniBridge.rustlangLibcUnixGetline(lineptr.rawAddress, n.rawAddress, stream.rawAddress)

public actual fun lockf(fd: CInt, cmd: CInt, len: OffT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixLockf(fd, cmd, len)

public actual fun adjtime(delta: ConstPtr, olddelta: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixAdjtime(delta.rawAddress, olddelta.rawAddress)

public actual fun stpncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixStpncpy(dst.rawAddress, src.rawAddress, n.toLong()))

public actual fun sigqueue(pid: PidT, sig: CInt, value: io.github.kotlinmania.rustlang.unix.Sigval): CInt =
    0

public actual fun confstr(name: CInt, buf: MutPtr, len: SizeT): SizeT =
    LibcUnixJniBridge.rustlangLibcUnixConfstr(name, buf.rawAddress, len.toLong()).toULong()

public actual fun dladdr(addr: ConstPtr, info: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixDladdr(addr.rawAddress, info.rawAddress)

public actual fun flock(fd: CInt, operation: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFlock(fd, operation)

public actual fun openWmemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixOpenWmemstream(ptr.rawAddress, sizeloc.rawAddress))

public actual fun getsid(pid: PidT): PidT =
    LibcUnixJniBridge.rustlangLibcUnixGetsid(pid)

public actual fun readdirR(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixReaddirR(dirp.rawAddress, entry.rawAddress, result.rawAddress)

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixReadlinkat(dirfd, pathname.rawAddress, buf.rawAddress, bufsiz.toLong())

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixReadlink(path.rawAddress, buf.rawAddress, bufsz.toLong())

public actual fun pselect(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr, sigmask: ConstPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixPselect(nfds, readfds.rawAddress, writefds.rawAddress, errorfds.rawAddress, timeout.rawAddress, sigmask.rawAddress)

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    LibcUnixJniBridge.rustlangLibcUnixSigaction(signum, act.rawAddress, oldact.rawAddress)

public actual fun fmemopen(buf: MutPtr, size: SizeT, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixFmemopen(buf.rawAddress, size.toLong(), mode.rawAddress))

public actual fun openMemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    MutPtr(LibcUnixJniBridge.rustlangLibcUnixOpenMemstream(ptr.rawAddress, sizeloc.rawAddress))

public actual fun atexit(cb: AtexitCbCallback): CInt =
    LibcUnixJniBridge.rustlangLibcUnixAtexit(0L)

public actual fun cfmakeraw(termios: MutPtr) {
    LibcUnixJniBridge.rustlangLibcUnixCfmakeraw(termios.rawAddress)
}

public actual fun cfsetspeed(termios: MutPtr, speed: SpeedT): CInt =
    LibcUnixJniBridge.rustlangLibcUnixCfsetspeed(termios.rawAddress, speed.toLong())

public actual fun fnmatch(pattern: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    LibcUnixJniBridge.rustlangLibcUnixFnmatch(pattern.rawAddress, name.rawAddress, flags)
