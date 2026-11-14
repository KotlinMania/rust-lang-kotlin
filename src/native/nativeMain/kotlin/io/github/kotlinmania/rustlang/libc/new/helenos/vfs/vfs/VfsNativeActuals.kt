@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsFhandle
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsLinkPath
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsRenamePath
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsStat
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsStatPath
import io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs.cinterop.libcNewHelenosVfsVfsVfsUnlinkPath
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun vfsFhandle(file: MutPtr, handle: MutPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsFhandle(file.rawAddress.toCPointer<ByteVar>(), handle.rawAddress.toCPointer<ByteVar>())

public actual fun vfsStat(handle: CInt, stat: MutPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsStat(handle, stat.rawAddress.toCPointer<ByteVar>())

public actual fun vfsLinkPath(path: ConstPtr, kind: VfsFileKindT, linkedfd: MutPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsLinkPath(path.rawAddress.toCPointer<ByteVar>(), kind, linkedfd.rawAddress.toCPointer<ByteVar>())

public actual fun vfsUnlinkPath(path: ConstPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsUnlinkPath(path.rawAddress.toCPointer<ByteVar>())

public actual fun vfsRenamePath(oldpath: ConstPtr, newpath: ConstPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsRenamePath(oldpath.rawAddress.toCPointer<ByteVar>(), newpath.rawAddress.toCPointer<ByteVar>())

public actual fun vfsStatPath(path: ConstPtr, stat: MutPtr): ErrnoT =
    libcNewHelenosVfsVfsVfsStatPath(path.rawAddress.toCPointer<ByteVar>(), stat.rawAddress.toCPointer<ByteVar>())
