@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows

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

private object LibcWindowsJniBridge {
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
        val stream = LibcWindowsJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcWindowsPrintf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFprintf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSnprintf(buffer: Long, count: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSprintf(buffer: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsScanf(format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSscanf(buffer: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFscanf(stream: Long, format: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsalnum(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsalpha(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIscntrl(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsgraph(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIslower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsprint(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIspunct(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsspace(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsxdigit(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsblank(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsTolower(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsToupper(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsQsort(base: Long, num: Long, size: Long, compar: Long)

    @JvmStatic
    external fun rustlangLibcWindowsQsortS(base: Long, num: Long, size: Long, compar: Long, arg: Long)

    @JvmStatic
    external fun rustlangLibcWindowsFopen(filename: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFreopen(filename: Long, mode: Long, file: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFflush(file: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFclose(file: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsRemove(filename: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsRename(oldname: Long, newname: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsTmpfile(): Long

    @JvmStatic
    external fun rustlangLibcWindowsSetvbuf(stream: Long, buffer: Long, mode: Int, size: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSetbuf(stream: Long, buf: Long)

    @JvmStatic
    external fun rustlangLibcWindowsGetchar(): Int

    @JvmStatic
    external fun rustlangLibcWindowsPutchar(c: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsFgetc(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFgets(buf: Long, n: Int, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFputc(c: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFputs(s: Long, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsPuts(s: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsUngetc(c: Int, stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFread(ptr: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFwrite(ptr: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFseek(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsFtell(stream: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsRewind(stream: Long)

    @JvmStatic
    external fun rustlangLibcWindowsFgetpos(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFsetpos(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFeof(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFerror(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsPerror(s: Long)

    @JvmStatic
    external fun rustlangLibcWindowsAtof(s: Long): Double

    @JvmStatic
    external fun rustlangLibcWindowsAtoi(s: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsAtol(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsAtoll(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrtod(s: Long, endp: Long): Double

    @JvmStatic
    external fun rustlangLibcWindowsStrtof(s: Long, endp: Long): Float

    @JvmStatic
    external fun rustlangLibcWindowsStrtol(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrtoll(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrtoul(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrtoull(s: Long, endp: Long, base: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsCalloc(nobj: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMalloc(size: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMsize(p: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsRealloc(p: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFree(p: Long)

    @JvmStatic
    external fun rustlangLibcWindowsAbort()

    @JvmStatic
    external fun rustlangLibcWindowsExit(status: Int)

    @JvmStatic
    external fun rustlangLibcWindowsAtexit(cb: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSystem(s: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetenv(s: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrcpy(dst: Long, src: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrncpy(dst: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrcat(s: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrncat(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrcmp(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsStrncmp(cs: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsStrcoll(cs: Long, ct: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsStrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrrchr(cs: Long, c: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrcspn(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrdup(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrpbrk(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrstr(cs: Long, ct: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrlen(cs: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrnlen(cs: Long, maxlen: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrerror(n: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrtok(s: Long, t: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsStrxfrm(s: Long, ct: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWcslen(buf: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWcsnlen(str: Long, numberOfElements: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWcstombs(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMemchr(cx: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMemcmp(cx: Long, ct: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsMemset(dest: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsAbs(i: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsLabs(i: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsRand(): Int

    @JvmStatic
    external fun rustlangLibcWindowsSrand(seed: Int)

    @JvmStatic
    external fun rustlangLibcWindowsSignal(signum: Int, handler: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsRaise(signum: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsClock(): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetDaylight(hours: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetDstbias(seconds: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetTimezone(seconds: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetTzname(pReturnValue: Long, timeZoneName: Long, sizeInBytes: Long, index: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsTzset()

    @JvmStatic
    external fun rustlangLibcWindowsChmod(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsWchmod(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsMkdir(path: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWrmdir(path: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFstat(fildes: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsStat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWstat(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWutime(file: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsPopen(command: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsPclose(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsFdopen(fd: Int, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsFileno(stream: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsOpen(path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWopen(path: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsCreat(path: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsAccess(path: Long, amode: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsChdir(dir: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsClose(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsDup(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsDup2(src: Int, dst: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsExecl(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexecl(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsExecle(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexecle(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsExeclp(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexeclp(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsExeclpe(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexeclpe(path: Long, arg0: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsExecv(prog: Long, argv: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsExecve(prog: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsExecvp(c: Long, argv: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsExecvpe(c: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWexecv(prog: Long, argv: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexecve(prog: Long, argv: Long, envp: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexecvp(c: Long, argv: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWexecvpe(c: Long, argv: Long, envp: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsGetcwd(buf: Long, size: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsGetpid(): Int

    @JvmStatic
    external fun rustlangLibcWindowsIsatty(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsLseek(fd: Int, offset: Long, origin: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsLseek64(fd: Int, offset: Long, origin: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsPipe(fds: Long, psize: Int, textmode: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsRead(fd: Int, buf: Long, count: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsRmdir(path: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsUnlink(c: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWrite(fd: Int, buf: Long, count: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsCommit(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetOsfhandle(fd: Int): Long

    @JvmStatic
    external fun rustlangLibcWindowsOpenOsfhandle(osfhandle: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsSetlocale(category: Int, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsWsetlocale(category: Int, locale: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsAlignedMalloc(size: Long, alignment: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsAlignedFree(ptr: Long)

    @JvmStatic
    external fun rustlangLibcWindowsAlignedRealloc(memblock: Long, size: Long, alignment: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsPutenv(envstring: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWputenv(envstring: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsPutenvS(envstring: Long, valueString: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsWputenvS(envstring: Long, valueString: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsListen(s: Long, backlog: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsAccept(s: Long, addr: Long, addrlen: Long): Long

    @JvmStatic
    external fun rustlangLibcWindowsBind(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsConnect(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetpeername(s: Long, name: Long, nameln: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetsockname(s: Long, name: Long, nameln: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsRecvfrom(s: Long, buf: Long, len: Int, flags: Int, from: Long, fromlen: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsSendto(s: Long, buf: Long, len: Int, flags: Int, to: Long, tolen: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsSetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Int): Int

    @JvmStatic
    external fun rustlangLibcWindowsSocket(af: Int, socketType: Int, protocol: Int): Long
}

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPrintf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFprintf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSnprintf(buffer.rawAddress, count.toLong(), format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSprintf(buffer.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsScanf(format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSscanf(buffer.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFscanf(stream.rawAddress, format.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun isalnum(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsgraph(c)

public actual fun islower(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIslower(c)

public actual fun isprint(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIspunct(c)

public actual fun isspace(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsspace(c)

public actual fun isupper(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsblank(c)

public actual fun tolower(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsTolower(c)

public actual fun toupper(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsToupper(c)

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    LibcWindowsJniBridge.rustlangLibcWindowsQsort(base.rawAddress, num.toLong(), size.toLong(), 0L)
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsQsortS(base.rawAddress, num.toLong(), size.toLong(), 0L, arg.rawAddress)
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsFopen(filename.rawAddress, mode.rawAddress))

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsFreopen(filename.rawAddress, mode.rawAddress, file.rawAddress))

public actual fun fflush(file: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFflush(file.rawAddress)

public actual fun fclose(file: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFclose(file.rawAddress)

public actual fun remove(filename: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRemove(filename.rawAddress)

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRename(oldname.rawAddress, newname.rawAddress)

public actual fun tmpfile(): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsTmpfile())

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSetvbuf(stream.rawAddress, buffer.rawAddress, mode, size.toLong())

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsSetbuf(stream.rawAddress, buf.rawAddress)
}

public actual fun getchar(): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsGetchar()

public actual fun putchar(c: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPutchar(c)

public actual fun fgetc(stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFgetc(stream.rawAddress)

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsFgets(buf.rawAddress, n, stream.rawAddress))

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFputc(c, stream.rawAddress)

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFputs(s.rawAddress, stream.rawAddress)

public actual fun puts(s: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPuts(s.rawAddress)

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsUngetc(c, stream.rawAddress)

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsFread(ptr.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsFwrite(ptr.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFseek(stream.rawAddress, offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    LibcWindowsJniBridge.rustlangLibcWindowsFtell(stream.rawAddress)

public actual fun rewind(stream: MutPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsRewind(stream.rawAddress)
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFgetpos(stream.rawAddress, ptr.rawAddress)

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFsetpos(stream.rawAddress, ptr.rawAddress)

public actual fun feof(stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFeof(stream.rawAddress)

public actual fun ferror(stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFerror(stream.rawAddress)

public actual fun perror(s: ConstPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsPerror(s.rawAddress)
}

public actual fun atof(s: ConstPtr): CDouble =
    LibcWindowsJniBridge.rustlangLibcWindowsAtof(s.rawAddress)

public actual fun atoi(s: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsAtoi(s.rawAddress)

public actual fun atol(s: ConstPtr): CLong =
    LibcWindowsJniBridge.rustlangLibcWindowsAtol(s.rawAddress)

public actual fun atoll(s: ConstPtr): CLonglong =
    LibcWindowsJniBridge.rustlangLibcWindowsAtoll(s.rawAddress)

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtod(s.rawAddress, endp.rawAddress)

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtof(s.rawAddress, endp.rawAddress)

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtol(s.rawAddress, endp.rawAddress, base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtoll(s.rawAddress, endp.rawAddress, base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtoul(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    LibcWindowsJniBridge.rustlangLibcWindowsStrtoull(s.rawAddress, endp.rawAddress, base).toULong()

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsCalloc(nobj.toLong(), size.toLong()))

public actual fun malloc(size: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsMalloc(size.toLong()))

public actual fun msize(p: MutPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsMsize(p.rawAddress).toULong()

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsRealloc(p.rawAddress, size.toLong()))

public actual fun free(p: MutPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsFree(p.rawAddress)
}

public actual fun abort(): Nothing {
    LibcWindowsJniBridge.rustlangLibcWindowsAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    LibcWindowsJniBridge.rustlangLibcWindowsExit(status)
    error("Unreachable noreturn C function exit")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsAtexit(0L)

public actual fun system(s: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSystem(s.rawAddress)

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsGetenv(s.rawAddress))

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrcpy(dst.rawAddress, src.rawAddress))

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrncpy(dst.rawAddress, src.rawAddress, n.toLong()))

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrcat(s.rawAddress, ct.rawAddress))

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrncat(s.rawAddress, ct.rawAddress, n.toLong()))

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsStrcmp(cs.rawAddress, ct.rawAddress)

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsStrncmp(cs.rawAddress, ct.rawAddress, n.toLong())

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsStrcoll(cs.rawAddress, ct.rawAddress)

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrchr(cs.rawAddress, c))

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrrchr(cs.rawAddress, c))

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsStrspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsStrcspn(cs.rawAddress, ct.rawAddress).toULong()

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrdup(cs.rawAddress))

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrpbrk(cs.rawAddress, ct.rawAddress))

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrstr(cs.rawAddress, ct.rawAddress))

public actual fun strlen(cs: ConstPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsStrlen(cs.rawAddress).toULong()

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsStrnlen(cs.rawAddress, maxlen.toLong()).toULong()

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrerror(n))

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsStrtok(s.rawAddress, t.rawAddress))

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsStrxfrm(s.rawAddress, ct.rawAddress, n.toLong()).toULong()

public actual fun wcslen(buf: ConstPtr): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsWcslen(buf.rawAddress).toULong()

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsWcsnlen(str.rawAddress, numberOfElements.toLong()).toULong()

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    LibcWindowsJniBridge.rustlangLibcWindowsWcstombs(dest.rawAddress, src.rawAddress, n.toLong()).toULong()

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsMemchr(cx.rawAddress, c, n.toLong()))

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsMemcmp(cx.rawAddress, ct.rawAddress, n.toLong())

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsMemset(dest.rawAddress, c, n.toLong()))

public actual fun abs(i: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsAbs(i)

public actual fun labs(i: CLong): CLong =
    LibcWindowsJniBridge.rustlangLibcWindowsLabs(i)

public actual fun rand(): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRand()

public actual fun srand(seed: CUint) {
    LibcWindowsJniBridge.rustlangLibcWindowsSrand(seed.toInt())
}

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    LibcWindowsJniBridge.rustlangLibcWindowsSignal(signum, handler.toLong()).toULong()

public actual fun raise(signum: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRaise(signum)

public actual fun clock(): ClockT =
    LibcWindowsJniBridge.rustlangLibcWindowsClock()

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsGetDaylight(hours.rawAddress)

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsGetDstbias(seconds.rawAddress)

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsGetTimezone(seconds.rawAddress)

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsGetTzname(pReturnValue.rawAddress, timeZoneName.rawAddress, sizeInBytes.toLong(), index)

public actual fun tzset() {
    LibcWindowsJniBridge.rustlangLibcWindowsTzset()
}

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsChmod(path.rawAddress, mode)

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWchmod(path.rawAddress, mode)

public actual fun mkdir(path: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsMkdir(path.rawAddress)

public actual fun wrmdir(path: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWrmdir(path.rawAddress)

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFstat(fildes, buf.rawAddress)

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsStat(path.rawAddress, buf.rawAddress)

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWstat(path.rawAddress, buf.rawAddress)

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWutime(file.rawAddress, buf.rawAddress)

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsPopen(command.rawAddress, mode.rawAddress))

public actual fun pclose(stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPclose(stream.rawAddress)

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsFdopen(fd, mode.rawAddress))

public actual fun fileno(stream: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsFileno(stream.rawAddress)

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsOpen(path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWopen(path.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsCreat(path.rawAddress, mode)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsAccess(path.rawAddress, amode)

public actual fun chdir(dir: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsChdir(dir.rawAddress)

public actual fun close(fd: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsClose(fd)

public actual fun dup(fd: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsDup(fd)

public actual fun dup2(src: CInt, dst: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsDup2(src, dst)

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsExecl(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecl(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsExecle(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecle(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsExeclp(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexeclp(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsExeclpe(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexeclpe(path.rawAddress, arg0.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsExecv(prog.rawAddress, argv.rawAddress)

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsExecve(prog.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsExecvp(c.rawAddress, argv.rawAddress)

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsExecvpe(c.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecv(prog.rawAddress, argv.rawAddress)

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecve(prog.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecvp(c.rawAddress, argv.rawAddress)

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsWexecvpe(c.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsGetcwd(buf.rawAddress, size))

public actual fun getpid(): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsGetpid()

public actual fun isatty(fd: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsIsatty(fd)

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    LibcWindowsJniBridge.rustlangLibcWindowsLseek(fd, offset, origin)

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    LibcWindowsJniBridge.rustlangLibcWindowsLseek64(fd, offset, origin)

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPipe(fds.rawAddress, psize.toInt(), textmode)

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRead(fd, buf.rawAddress, count.toInt())

public actual fun rmdir(path: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRmdir(path.rawAddress)

public actual fun unlink(c: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsUnlink(c.rawAddress)

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWrite(fd, buf.rawAddress, count.toInt())

public actual fun commit(fd: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsCommit(fd)

public actual fun getOsfhandle(fd: CInt): IntptrT =
    LibcWindowsJniBridge.rustlangLibcWindowsGetOsfhandle(fd)

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsOpenOsfhandle(osfhandle, flags)

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsSetlocale(category, locale.rawAddress))

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsWsetlocale(category, locale.rawAddress))

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsAlignedMalloc(size.toLong(), alignment.toLong()))

public actual fun alignedFree(ptr: MutPtr) {
    LibcWindowsJniBridge.rustlangLibcWindowsAlignedFree(ptr.rawAddress)
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    MutPtr(LibcWindowsJniBridge.rustlangLibcWindowsAlignedRealloc(memblock.rawAddress, size.toLong(), alignment.toLong()))

public actual fun putenv(envstring: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsPutenv(envstring.rawAddress)

public actual fun wputenv(envstring: ConstPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsWputenv(envstring.rawAddress)

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsPutenvS(envstring.rawAddress, valueString.rawAddress)

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    LibcWindowsJniBridge.rustlangLibcWindowsWputenvS(envstring.rawAddress, valueString.rawAddress)

public actual fun listen(s: Socket, backlog: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsListen(s.toLong(), backlog)

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    LibcWindowsJniBridge.rustlangLibcWindowsAccept(s.toLong(), addr.rawAddress, addrlen.rawAddress).toULong()

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsBind(s.toLong(), name.rawAddress, namelen)

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsConnect(s.toLong(), name.rawAddress, namelen)

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsGetpeername(s.toLong(), name.rawAddress, nameln.rawAddress)

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsGetsockname(s.toLong(), name.rawAddress, nameln.rawAddress)

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsGetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen.rawAddress)

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsRecvfrom(s.toLong(), buf.rawAddress, len, flags, from.rawAddress, fromlen.rawAddress)

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSendto(s.toLong(), buf.rawAddress, len, flags, to.rawAddress, tolen)

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    LibcWindowsJniBridge.rustlangLibcWindowsSetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen)

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    LibcWindowsJniBridge.rustlangLibcWindowsSocket(af, socketType, protocol).toULong()
