@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

private object LibunwindJniBridge {
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
                "windows" -> "unwind_jni.dll"
                "macos" -> "libunwind_jni.dylib"
                else -> "libunwind_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/unwind/native/$hostPlatform-$hostArch/$libFileName"
        val stream = LibunwindJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("unwind_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("unwind_jni")
    }

    @JvmStatic
    external fun unwindLibunwindUnwindResume(exception: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindDeleteException(exception: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindGetLanguageSpecificData(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetRegionStart(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetTextRelBase(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetDataRelBase(ctx: Long): Long
}

public actual fun unwindResume(exception: MutPtr): Nothing {
    LibunwindJniBridge.unwindLibunwindUnwindResume(exception.rawAddress)
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    LibunwindJniBridge.unwindLibunwindUnwindDeleteException(exception.rawAddress)
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    MutPtr(LibunwindJniBridge.unwindLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress))

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetRegionStart(ctx.rawAddress))

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetTextRelBase(ctx.rawAddress))

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetDataRelBase(ctx.rawAddress))

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode
