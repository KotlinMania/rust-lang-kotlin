@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysSyncThreadparkingDarwinJniBridge {
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
        val stream = StdSysSyncThreadparkingDarwinJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch: Long, delta: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema: Long, timeout: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchRelease(obj: Long)
}

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toLong(), delta).toULong()

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    MutPtr(StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value))

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress, timeout.toLong())

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress)

public actual fun dispatchRelease(obj: MutPtr) {
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress)
}
