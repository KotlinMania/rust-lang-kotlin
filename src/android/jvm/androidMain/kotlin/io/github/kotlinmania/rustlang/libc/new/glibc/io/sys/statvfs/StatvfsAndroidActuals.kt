@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object LibcNewGlibcIoSysStatvfsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewGlibcIoSysStatvfsStatvfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcNewGlibcIoSysStatvfsFstatvfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcNewGlibcIoSysStatvfsStatvfs64(path: Long, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcNewGlibcIoSysStatvfsFstatvfs64(fd: Int, buf: Long): Int
}

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    LibcNewGlibcIoSysStatvfsJniBridge.rustlangLibcNewGlibcIoSysStatvfsStatvfs(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    LibcNewGlibcIoSysStatvfsJniBridge.rustlangLibcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress)

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    LibcNewGlibcIoSysStatvfsJniBridge.rustlangLibcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    LibcNewGlibcIoSysStatvfsJniBridge.rustlangLibcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress)
