@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object NvptxJniBridge {
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
                "windows" -> "corearch_jni.dll"
                "macos" -> "libcorearch_jni.dylib"
                else -> "libcorearch_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = NvptxJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("corearch_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchNvptxSyncthreads(a: Int)

    @JvmStatic
    external fun corearchNvptxBlockDimX(): Int

    @JvmStatic
    external fun corearchNvptxBlockDimY(): Int

    @JvmStatic
    external fun corearchNvptxBlockDimZ(): Int

    @JvmStatic
    external fun corearchNvptxBlockIdxX(): Int

    @JvmStatic
    external fun corearchNvptxBlockIdxY(): Int

    @JvmStatic
    external fun corearchNvptxBlockIdxZ(): Int

    @JvmStatic
    external fun corearchNvptxGridDimX(): Int

    @JvmStatic
    external fun corearchNvptxGridDimY(): Int

    @JvmStatic
    external fun corearchNvptxGridDimZ(): Int

    @JvmStatic
    external fun corearchNvptxThreadIdxX(): Int

    @JvmStatic
    external fun corearchNvptxThreadIdxY(): Int

    @JvmStatic
    external fun corearchNvptxThreadIdxZ(): Int

    @JvmStatic
    external fun corearchNvptxVprintf(format: Long, valist: Long): Int

    @JvmStatic
    external fun corearchNvptxMalloc(size: Long): Long

    @JvmStatic
    external fun corearchNvptxFree(ptr: Long)

    @JvmStatic
    external fun corearchNvptxAssertfail(message: Long, file: Long, line: Int, function: Long, charSize: Long)
}

public actual fun syncthreads(a: UInt) {
    NvptxJniBridge.corearchNvptxSyncthreads(a.toInt())
}

public actual fun blockDimX(): UInt =
    NvptxJniBridge.corearchNvptxBlockDimX().toUInt()

public actual fun blockDimY(): UInt =
    NvptxJniBridge.corearchNvptxBlockDimY().toUInt()

public actual fun blockDimZ(): UInt =
    NvptxJniBridge.corearchNvptxBlockDimZ().toUInt()

public actual fun blockIdxX(): UInt =
    NvptxJniBridge.corearchNvptxBlockIdxX().toUInt()

public actual fun blockIdxY(): UInt =
    NvptxJniBridge.corearchNvptxBlockIdxY().toUInt()

public actual fun blockIdxZ(): UInt =
    NvptxJniBridge.corearchNvptxBlockIdxZ().toUInt()

public actual fun gridDimX(): UInt =
    NvptxJniBridge.corearchNvptxGridDimX().toUInt()

public actual fun gridDimY(): UInt =
    NvptxJniBridge.corearchNvptxGridDimY().toUInt()

public actual fun gridDimZ(): UInt =
    NvptxJniBridge.corearchNvptxGridDimZ().toUInt()

public actual fun threadIdxX(): UInt =
    NvptxJniBridge.corearchNvptxThreadIdxX().toUInt()

public actual fun threadIdxY(): UInt =
    NvptxJniBridge.corearchNvptxThreadIdxY().toUInt()

public actual fun threadIdxZ(): UInt =
    NvptxJniBridge.corearchNvptxThreadIdxZ().toUInt()

public actual fun vprintf(format: ConstPtr, valist: ConstPtr): Int =
    NvptxJniBridge.corearchNvptxVprintf(format.rawAddress, valist.rawAddress)

public actual fun malloc(size: ULong): MutPtr =
    MutPtr(NvptxJniBridge.corearchNvptxMalloc(size.toLong()))

public actual fun free(ptr: MutPtr) {
    NvptxJniBridge.corearchNvptxFree(ptr.rawAddress)
}

public actual fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong) {
    NvptxJniBridge.corearchNvptxAssertfail(message.rawAddress, file.rawAddress, line.toInt(), function.rawAddress, charSize.toLong())
}
