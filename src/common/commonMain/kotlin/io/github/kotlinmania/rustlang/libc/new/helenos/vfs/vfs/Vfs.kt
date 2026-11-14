// port-lint: source new/helenos/vfs/vfs.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.vfs.vfs

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.ipc.loc.ServiceIdT
import io.github.kotlinmania.rustlang.libc.new.helenos.ipc.vfs.FsHandleT
import io.github.kotlinmania.rustlang.libc.new.helenos.ipc.vfs.FsIndexT
import io.github.kotlinmania.rustlang.libc.new.helenos.offset.Aoff64T
import io.github.kotlinmania.rustlang.libc.types.CEnumRepr

// HelenOS libc virtual file system APIs
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/vfs/vfs.h>

public data class VfsStatT(
    public var fsHandle: FsHandleT = 0,
    public var serviceId: ServiceIdT = 0uL,
    public var index: FsIndexT = 0u,
    public var lnkcnt: CUint = 0u,
    public var isFile: Boolean = false,
    public var isDirectory: Boolean = false,
    public var size: Aoff64T = 0uL,
    public var service: ServiceIdT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<VfsStatT> =
            object : TypeLayout<VfsStatT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): VfsStatT =
                    VfsStatT(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 24L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 25L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: VfsStatT) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.fsHandle)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.serviceId)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.lnkcnt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 24L, value.isFile)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 25L, value.isDirectory)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.size)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.service)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VfsStatT =
                    VfsStatT(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 25),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VfsStatT) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.fsHandle)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.serviceId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.lnkcnt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 24, value.isFile)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 25, value.isDirectory)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.size)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.service)
                }
            }
    }
}

public typealias VfsFileKindT = CEnumRepr

public val KIND_FILE: VfsFileKindT = 0

public val KIND_DIRECTORY: VfsFileKindT = KIND_FILE + 1

public expect fun vfsFhandle(file: MutPtr, handle: MutPtr): ErrnoT

public expect fun vfsStat(handle: CInt, stat: MutPtr): ErrnoT

public expect fun vfsLinkPath(path: ConstPtr, kind: VfsFileKindT, linkedfd: MutPtr): ErrnoT

public expect fun vfsUnlinkPath(path: ConstPtr): ErrnoT

public expect fun vfsRenamePath(oldpath: ConstPtr, newpath: ConstPtr): ErrnoT

public expect fun vfsStatPath(path: ConstPtr, stat: MutPtr): ErrnoT
