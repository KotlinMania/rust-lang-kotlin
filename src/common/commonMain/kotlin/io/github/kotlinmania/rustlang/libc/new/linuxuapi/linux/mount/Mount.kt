// port-lint: source new/linux_uapi/linux/mount.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.mount

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.unix.linuxlike.MS_I_VERSION
import io.github.kotlinmania.rustlang.unix.linuxlike.MS_LAZYTIME
import io.github.kotlinmania.rustlang.unix.linuxlike.MS_MANDLOCK
import io.github.kotlinmania.rustlang.unix.linuxlike.MS_RDONLY
import io.github.kotlinmania.rustlang.unix.linuxlike.MS_SYNCHRONOUS
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.O_CLOEXEC

// Header: `uapi / linux / mount.h`

public val MS_RMT_MASK: CUlong = MS_RDONLY or MS_SYNCHRONOUS or MS_MANDLOCK or MS_I_VERSION or MS_LAZYTIME

public val OPEN_TREE_CLONE: CUint = 0x01u

public val OPEN_TREE_NAMESPACE: CUint = 0x02u

public val OPEN_TREE_CLOEXEC: CUint = O_CLOEXEC.toUInt()

public val MOVE_MOUNT_F_SYMLINKS: CUint = 0x00000001u

public val MOVE_MOUNT_F_AUTOMOUNTS: CUint = 0x00000002u

public val MOVE_MOUNT_F_EMPTY_PATH: CUint = 0x00000004u

public val MOVE_MOUNT_T_SYMLINKS: CUint = 0x00000010u

public val MOVE_MOUNT_T_AUTOMOUNTS: CUint = 0x00000020u

public val MOVE_MOUNT_T_EMPTY_PATH: CUint = 0x00000040u

public val MOVE_MOUNT_SET_GROUP: CUint = 0x00000100u

public val MOVE_MOUNT_BENEATH: CUint = 0x00000200u

public val FSOPEN_CLOEXEC: CUint = 0x00000001u

public val FSPICK_CLOEXEC: CUint = 0x00000001u

public val FSPICK_SYMLINK_NOFOLLOW: CUint = 0x00000002u

public val FSPICK_NO_AUTOMOUNT: CUint = 0x00000004u

public val FSPICK_EMPTY_PATH: CUint = 0x00000008u

public typealias FsconfigCommand = CEnumRepr

public val FSCONFIG_SET_FLAG: FsconfigCommand = 0

public val FSCONFIG_SET_STRING: FsconfigCommand = FSCONFIG_SET_FLAG + 1

public val FSCONFIG_SET_BINARY: FsconfigCommand = FSCONFIG_SET_STRING + 1

public val FSCONFIG_SET_PATH: FsconfigCommand = FSCONFIG_SET_BINARY + 1

public val FSCONFIG_SET_PATH_EMPTY: FsconfigCommand = FSCONFIG_SET_PATH + 1

public val FSCONFIG_SET_FD: FsconfigCommand = FSCONFIG_SET_PATH_EMPTY + 1

public val FSCONFIG_CMD_CREATE: FsconfigCommand = FSCONFIG_SET_FD + 1

public val FSCONFIG_CMD_RECONFIGURE: FsconfigCommand = FSCONFIG_CMD_CREATE + 1

public val FSCONFIG_CMD_CREATE_EXCL: FsconfigCommand = FSCONFIG_CMD_RECONFIGURE + 1

public val FSMOUNT_CLOEXEC: CUint = 0x00000001u

public val MOUNT_ATTR_RDONLY: ULong = 0x00000001uL

public val MOUNT_ATTR_NOSUID: ULong = 0x00000002uL

public val MOUNT_ATTR_NODEV: ULong = 0x00000004uL

public val MOUNT_ATTR_NOEXEC: ULong = 0x00000008uL

public val MOUNT_ATTR_ATIME: ULong = 0x00000070uL

public val MOUNT_ATTR_RELATIME: ULong = 0x00000000uL

public val MOUNT_ATTR_NOATIME: ULong = 0x00000010uL

public val MOUNT_ATTR_STRICTATIME: ULong = 0x00000020uL

public val MOUNT_ATTR_NODIRATIME: ULong = 0x00000080uL

public val MOUNT_ATTR_IDMAP: ULong = 0x00100000uL

public val MOUNT_ATTR_NOSYMFOLLOW: ULong = 0x00200000uL

public data class MountAttr(
    public var attrSet: U64 = 0uL,
    public var attrClr: U64 = 0uL,
    public var propagation: U64 = 0uL,
    public var usernsFd: U64 = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MountAttr> =
            object : TypeLayout<MountAttr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): MountAttr =
                    MountAttr(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: MountAttr) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.attrSet)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.attrClr)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.propagation)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.usernsFd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MountAttr =
                    MountAttr(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MountAttr) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.attrSet)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.attrClr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.propagation)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.usernsFd)
                }
            }
    }
}

public val MOUNT_ATTR_SIZE_VER0: CInt = 32
