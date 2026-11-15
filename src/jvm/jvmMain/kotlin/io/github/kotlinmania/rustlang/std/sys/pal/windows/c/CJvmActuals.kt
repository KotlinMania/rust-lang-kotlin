@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.os.raw.CInt
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Ntstatus

private object StdSysPalWindowsCJniBridge {
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
        val stream = StdSysPalWindowsCJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangStdSysPalWindowsCProcessPrng(pbdata: Long, cbdata: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCNtCreateNamedPipeFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, shareaccess: Int, createdisposition: Int, createoptions: Int, namedpipetype: Int, readmode: Int, completionmode: Int, maximuminstances: Int, inboundquota: Int, outboundquota: Int, defaulttimeout: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCAtexit(cb: Long): Int
}

public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    StdSysPalWindowsCJniBridge.rustlangStdSysPalWindowsCProcessPrng(pbdata.rawAddress, cbdata.toLong())

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    StdSysPalWindowsCJniBridge.rustlangStdSysPalWindowsCNtCreateNamedPipeFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), namedpipetype.toInt(), readmode.toInt(), completionmode.toInt(), maximuminstances.toInt(), inboundquota.toInt(), outboundquota.toInt(), defaulttimeout.rawAddress)

public actual fun atexit(cb: AtexitCbCallback): CInt =
    StdSysPalWindowsCJniBridge.rustlangStdSysPalWindowsCAtexit(0L)
