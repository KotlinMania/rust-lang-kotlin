@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.libcNewGlibcIoSysStatvfsFstatvfs
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.libcNewGlibcIoSysStatvfsFstatvfs64
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.libcNewGlibcIoSysStatvfsStatvfs
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.libcNewGlibcIoSysStatvfsStatvfs64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    libcNewGlibcIoSysStatvfsStatvfs(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    libcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    libcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    libcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress.toCPointer<ByteVar>())
