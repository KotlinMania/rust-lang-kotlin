@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.libc.fuchsia.UintptrT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object BacktraceLibunwindJniBridge {
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
                "windows" -> "backtrace_jni.dll"
                "macos" -> "libbacktrace_jni.dylib"
                else -> "libbacktrace_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/backtrace/native/$hostPlatform-$hostArch/$libFileName"
        val stream = BacktraceLibunwindJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("backtrace_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("backtrace_jni")
    }

    @JvmStatic
    external fun backtraceBacktraceLibunwindUnwindGetIp(ctx: Long): Long

    @JvmStatic
    external fun backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc: Long): Long

    @JvmStatic
    external fun backtraceBacktraceLibunwindGetSp(ctx: Long): Long
}

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress).toULong()

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress))

public actual fun getSp(ctx: MutPtr): UintptrT =
    BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindGetSp(ctx.rawAddress).toULong()
