@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewGlibcIoSysStatvfsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsStatvfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsFstatvfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsStatvfs64(path: Long, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsFstatvfs64(fd: Int, buf: Long): Int
}

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsStatvfs(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress)

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress)
