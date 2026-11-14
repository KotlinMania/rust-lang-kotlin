@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private object NewHelenosVfsVfsJniBridge {
    init {
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
