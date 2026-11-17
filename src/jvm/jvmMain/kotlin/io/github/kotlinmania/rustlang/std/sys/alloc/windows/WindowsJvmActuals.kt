@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Handle

private object SysAllocWindowsJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/std/native/$hostPlatform-$hostArch/$libFileName"
        val stream = SysAllocWindowsJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun stdSysAllocWindowsGetProcessHeap(): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapAlloc(hheap: Long, dwflags: Int, dwbytes: Long): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapReAlloc(hheap: Long, dwflags: Int, lpmem: Long, dwbytes: Long): Long

    @JvmStatic
    external fun stdSysAllocWindowsHeapFree(hheap: Long, dwflags: Int, lpmem: Long): Int
}

public actual fun getProcessHeap(): Handle =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsGetProcessHeap())

public actual fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsHeapAlloc(hheap.rawAddress, dwflags.toInt(), dwbytes.toLong()))

public actual fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr =
    MutPtr(SysAllocWindowsJniBridge.stdSysAllocWindowsHeapReAlloc(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress, dwbytes.toLong()))

public actual fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool =
    SysAllocWindowsJniBridge.stdSysAllocWindowsHeapFree(hheap.rawAddress, dwflags.toInt(), lpmem.rawAddress)
