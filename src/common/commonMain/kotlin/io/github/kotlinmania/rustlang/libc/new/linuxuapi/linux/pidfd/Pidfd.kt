// port-lint: source new/linux_uapi/linux/pidfd.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd

import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.io
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.iowr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.O_EXCL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.O_NONBLOCK

// Header: `uapi / linux / pidfd.h`

// Flags for pidfdOpen().
public val PIDFD_NONBLOCK: CUint = O_NONBLOCK.toUInt()

public val PIDFD_THREAD: CUint = O_EXCL.toUInt()

// Flags for pidfdSendSignal().
public val PIDFD_SIGNAL_THREAD: CUint = 1u shl 0

public val PIDFD_SIGNAL_THREAD_GROUP: CUint = 1u shl 1

public val PIDFD_SIGNAL_PROCESS_GROUP: CUint = 1u shl 2

// Flags for pidfdInfo.
public val PIDFD_INFO_PID: CUint = 1u shl 0

public val PIDFD_INFO_CREDS: CUint = 1u shl 1

public val PIDFD_INFO_CGROUPID: CUint = 1u shl 2

public val PIDFD_INFO_EXIT: CUint = 1u shl 3

public val PIDFD_INFO_SIZE_VER0: CUint = 64u

public data class PidfdInfo(
    public var mask: U64 = 0uL,
    public var cgroupid: U64 = 0uL,
    public var pid: U32 = 0u,
    public var tgid: U32 = 0u,
    public var ppid: U32 = 0u,
    public var ruid: U32 = 0u,
    public var rgid: U32 = 0u,
    public var euid: U32 = 0u,
    public var egid: U32 = 0u,
    public var suid: U32 = 0u,
    public var sgid: U32 = 0u,
    public var fsuid: U32 = 0u,
    public var fsgid: U32 = 0u,
    public var exitCode: S32 = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PidfdInfo> =
            object : TypeLayout<PidfdInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): PidfdInfo =
                    PidfdInfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 52L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                    )
                override fun writeNative(addr: Long, value: PidfdInfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.mask)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cgroupid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pid)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.ppid)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.ruid)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.rgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.euid)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.egid)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.suid)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.sgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.fsuid)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.fsgid)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.exitCode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PidfdInfo =
                    PidfdInfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PidfdInfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.mask)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cgroupid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.ppid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.ruid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.rgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.euid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.egid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.suid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.sgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.fsuid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.fsgid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.exitCode)
                }
            }
    }
}

public val PIDFS_IOCTL_MAGIC: CUint = 0xFFu

public val PIDFD_GET_CGROUP_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 1u)

public val PIDFD_GET_IPC_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 2u)

public val PIDFD_GET_MNT_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 3u)

public val PIDFD_GET_NET_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 4u)

public val PIDFD_GET_PID_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 5u)

public val PIDFD_GET_PID_FOR_CHILDREN_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 6u)

public val PIDFD_GET_TIME_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 7u)

public val PIDFD_GET_TIME_FOR_CHILDREN_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 8u)

public val PIDFD_GET_USER_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 9u)

public val PIDFD_GET_UTS_NAMESPACE: Ioctl = io(PIDFS_IOCTL_MAGIC, 10u)

public val PIDFD_GET_INFO: Ioctl = iowr<PidfdInfo>(PIDFS_IOCTL_MAGIC, 11u)
