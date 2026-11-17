@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

private object X86Sse42JniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = X86Sse42JniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchX86Sse42Crc32328(crc: Int, v: Byte): Int

    @JvmStatic
    external fun corearchX86Sse42Crc323216(crc: Int, v: Short): Int

    @JvmStatic
    external fun corearchX86Sse42Crc323232(crc: Int, v: Int): Int
}

public actual fun pcmpestrm128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): U8x16 =
    null as U8x16

public actual fun pcmpestri128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestriz128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestric128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestris128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestrio128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestria128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpistrm128(a: I8x16, b: I8x16, imm8: Byte): I8x16 =
    null as I8x16

public actual fun pcmpistri128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistriz128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistric128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistris128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistrio128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistria128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun crc32328(crc: UInt, v: UByte): UInt =
    X86Sse42JniBridge.corearchX86Sse42Crc32328(crc.toInt(), v.toByte()).toUInt()

public actual fun crc323216(crc: UInt, v: UShort): UInt =
    X86Sse42JniBridge.corearchX86Sse42Crc323216(crc.toInt(), v.toShort()).toUInt()

public actual fun crc323232(crc: UInt, v: UInt): UInt =
    X86Sse42JniBridge.corearchX86Sse42Crc323232(crc.toInt(), v.toInt()).toUInt()
