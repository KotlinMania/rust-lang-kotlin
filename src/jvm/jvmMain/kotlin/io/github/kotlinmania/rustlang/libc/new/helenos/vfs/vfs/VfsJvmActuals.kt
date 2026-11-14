@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private object NewHelenosVfsVfsJniBridge {
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
        val stream = NewHelenosVfsVfsJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun libcNewHelenosVfsVfsVfsFhandle(file: Long, handle: Long): Int

    @JvmStatic
    external fun libcNewHelenosVfsVfsVfsStat(handle: Int, stat: Long): Int

    @JvmStatic
    external fun libcNewHelenosVfsVfsVfsLinkPath(path: Long, kind: Int, linkedfd: Long): Int

    @JvmStatic
    external fun libcNewHelenosVfsVfsVfsUnlinkPath(path: Long): Int

    @JvmStatic
    external fun libcNewHelenosVfsVfsVfsRenamePath(oldpath: Long, newpath: Long): Int

    @JvmStatic
    external fun libcNewHelenosVfsVfsVfsStatPath(path: Long, stat: Long): Int
}

public actual fun vfsFhandle(file: MutPtr, handle: MutPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsFhandle(file.rawAddress, handle.rawAddress)

public actual fun vfsStat(handle: CInt, stat: MutPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsStat(handle, stat.rawAddress)

public actual fun vfsLinkPath(path: ConstPtr, kind: VfsFileKindT, linkedfd: MutPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsLinkPath(path.rawAddress, kind, linkedfd.rawAddress)

public actual fun vfsUnlinkPath(path: ConstPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsUnlinkPath(path.rawAddress)

public actual fun vfsRenamePath(oldpath: ConstPtr, newpath: ConstPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsRenamePath(oldpath.rawAddress, newpath.rawAddress)

public actual fun vfsStatPath(path: ConstPtr, stat: MutPtr): ErrnoT =
    NewHelenosVfsVfsJniBridge.libcNewHelenosVfsVfsVfsStatPath(path.rawAddress, stat.rawAddress)
