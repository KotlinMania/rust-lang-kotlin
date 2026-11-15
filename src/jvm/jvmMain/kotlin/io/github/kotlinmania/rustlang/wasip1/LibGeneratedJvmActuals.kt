@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.wasip1

private object LibGeneratedJniBridge {
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
        val stream = LibGeneratedJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibGeneratedArgsGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedArgsSizesGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedEnvironGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedEnvironSizesGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedClockResGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedClockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdAllocate(arg0: Int, arg1: Long, arg2: Long): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdClose(arg0: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdDatasync(arg0: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFdstatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFdstatSetFlags(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFilestatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFilestatSetSize(arg0: Int, arg1: Long): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdPrestatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdRenumber(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdSync(arg0: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdTell(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedFdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedPollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedProcExit(arg0: Int)

    @JvmStatic
    external fun rustlangLibGeneratedProcRaise(arg0: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedSchedYield(): Int

    @JvmStatic
    external fun rustlangLibGeneratedRandomGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedSockAccept(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedSockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedSockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun rustlangLibGeneratedSockShutdown(arg0: Int, arg1: Int): Int
}

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedArgsGet(arg0, arg1)

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedArgsSizesGet(arg0, arg1)

public actual fun environGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedEnvironGet(arg0, arg1)

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedEnvironSizesGet(arg0, arg1)

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedClockResGet(arg0, arg1)

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedClockTimeGet(arg0, arg1, arg2)

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdAdvise(arg0, arg1, arg2, arg3)

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdAllocate(arg0, arg1, arg2)

public actual fun fdClose(arg0: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdClose(arg0)

public actual fun fdDatasync(arg0: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdDatasync(arg0)

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFdstatGet(arg0, arg1)

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFdstatSetFlags(arg0, arg1)

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFdstatSetRights(arg0, arg1, arg2)

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFilestatGet(arg0, arg1)

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFilestatSetSize(arg0, arg1)

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3)

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4)

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdPrestatGet(arg0, arg1)

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdPrestatDirName(arg0, arg1, arg2)

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdRead(arg0, arg1, arg2, arg3)

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdRenumber(arg0, arg1)

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdSeek(arg0, arg1, arg2, arg3)

public actual fun fdSync(arg0: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdSync(arg0)

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdTell(arg0, arg1)

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedFdWrite(arg0, arg1, arg2, arg3)

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathCreateDirectory(arg0, arg1, arg2)

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4)

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathRemoveDirectory(arg0, arg1, arg2)

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4)

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPathUnlinkFile(arg0, arg1, arg2)

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedPollOneoff(arg0, arg1, arg2, arg3)

public actual fun procExit(arg0: Int): Nothing {
    LibGeneratedJniBridge.rustlangLibGeneratedProcExit(arg0)
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedProcRaise(arg0)

public actual fun schedYield(): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedSchedYield()

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedRandomGet(arg0, arg1)

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedSockAccept(arg0, arg1, arg2)

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4)

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.rustlangLibGeneratedSockShutdown(arg0, arg1)
