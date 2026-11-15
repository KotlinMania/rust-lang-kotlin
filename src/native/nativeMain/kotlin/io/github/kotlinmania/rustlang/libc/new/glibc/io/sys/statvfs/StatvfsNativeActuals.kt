@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.rustlangLibcNewGlibcIoSysStatvfsFstatvfs
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.rustlangLibcNewGlibcIoSysStatvfsFstatvfs64
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.rustlangLibcNewGlibcIoSysStatvfsStatvfs
import io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs.cinterop.rustlangLibcNewGlibcIoSysStatvfsStatvfs64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcNewGlibcIoSysStatvfsStatvfs(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress.toCPointer<ByteVar>())
