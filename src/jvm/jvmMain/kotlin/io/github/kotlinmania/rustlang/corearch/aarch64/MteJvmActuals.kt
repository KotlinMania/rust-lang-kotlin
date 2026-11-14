@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object Aarch64MteJniBridge {
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
        val stream = Aarch64MteJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchAarch64MteIrg(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun corearchAarch64MteGmi(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun corearchAarch64MteLdg(ptr: Long, tagPtr: Long): Long

    @JvmStatic
    external fun corearchAarch64MteStg(taggedPtr: Long, addrToTag: Long)

    @JvmStatic
    external fun corearchAarch64MteAddg(ptr: Long, value: Long): Long

    @JvmStatic
    external fun corearchAarch64MteSubp(ptrA: Long, ptrB: Long): Long
}

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteIrg(ptr.rawAddress, exclude))

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    Aarch64MteJniBridge.corearchAarch64MteGmi(ptr.rawAddress, exclude)

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteLdg(ptr.rawAddress, tagPtr.rawAddress))

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    Aarch64MteJniBridge.corearchAarch64MteStg(taggedPtr.rawAddress, addrToTag.rawAddress)
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteAddg(ptr.rawAddress, value))

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    Aarch64MteJniBridge.corearchAarch64MteSubp(ptrA.rawAddress, ptrB.rawAddress)
