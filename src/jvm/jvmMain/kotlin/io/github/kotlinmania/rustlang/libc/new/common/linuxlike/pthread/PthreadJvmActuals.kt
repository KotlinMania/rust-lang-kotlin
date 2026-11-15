@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private object LibcNewCommonLinuxlikePthreadJniBridge {
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
        val stream = LibcNewCommonLinuxlikePthreadJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread: Long, name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread: Long, name: Long): Int
}

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.toLong(), cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native.toLong(), attr.rawAddress)

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.toLong(), name.rawAddress, len.toLong())

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread.toLong())

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.toLong(), cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.toLong(), name.rawAddress)
