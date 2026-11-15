@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.UintptrT

private object BacktraceBacktraceLibunwindJniBridge {
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
        val stream = BacktraceBacktraceLibunwindJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangBacktraceBacktraceLibunwindUnwindGetIp(ctx: Long): Long

    @JvmStatic
    external fun rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc: Long): Long

    @JvmStatic
    external fun rustlangBacktraceBacktraceLibunwindGetSp(ctx: Long): Long
}

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress).toULong()

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress))

public actual fun getSp(ctx: MutPtr): UintptrT =
    BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindGetSp(ctx.rawAddress).toULong()
