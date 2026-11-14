@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdio

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT

private object NewHelenosStdioJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/libc/native/$hostPlatform-$hostArch/$libFileName"
        val stream = NewHelenosStdioJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun libcNewHelenosStdioGetSTDIN(): Long

    @JvmStatic
    external fun libcNewHelenosStdioGetSTDOUT(): Long

    @JvmStatic
    external fun libcNewHelenosStdioGetSTDERR(): Long

    @JvmStatic
    external fun libcNewHelenosStdioFputc(c: Int, stream: Long): Int

    @JvmStatic
    external fun libcNewHelenosStdioFputs(s: Long, stream: Long): Int

    @JvmStatic
    external fun libcNewHelenosStdioFopen(filename: Long, mode: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdioFclose(stream: Long): Int

    @JvmStatic
    external fun libcNewHelenosStdioFflush(stream: Long): Int

    @JvmStatic
    external fun libcNewHelenosStdioFread(buf: Long, size: Long, nmemb: Long, stream: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdioFwrite(buf: Long, size: Long, nmemb: Long, stream: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdioFseek(stream: Long, offset: Long, whence: Int): Int

    @JvmStatic
    external fun libcNewHelenosStdioFtell(stream: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdioFileno(stream: Long): Int
}

public actual val STDIN: MutPtr
    get() = MutPtr(NewHelenosStdioJniBridge.libcNewHelenosStdioGetSTDIN())

public actual val STDOUT: MutPtr
    get() = MutPtr(NewHelenosStdioJniBridge.libcNewHelenosStdioGetSTDOUT())

public actual val STDERR: MutPtr
    get() = MutPtr(NewHelenosStdioJniBridge.libcNewHelenosStdioGetSTDERR())

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFputc(c, stream.rawAddress)

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFputs(s.rawAddress, stream.rawAddress)

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(NewHelenosStdioJniBridge.libcNewHelenosStdioFopen(filename.rawAddress, mode.rawAddress))

public actual fun fclose(stream: MutPtr): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFclose(stream.rawAddress)

public actual fun fflush(stream: MutPtr): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFflush(stream.rawAddress)

public actual fun fread(buf: MutPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFread(buf.rawAddress, size.toLong(), nmemb.toLong(), stream.rawAddress).toULong()

public actual fun fwrite(buf: ConstPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFwrite(buf.rawAddress, size.toLong(), nmemb.toLong(), stream.rawAddress).toULong()

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFseek(stream.rawAddress, offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFtell(stream.rawAddress)

public actual fun fileno(stream: MutPtr): CInt =
    NewHelenosStdioJniBridge.libcNewHelenosStdioFileno(stream.rawAddress)
