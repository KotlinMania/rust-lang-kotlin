@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.loongarch64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.KeyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.DevT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.ModeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.BlkcntT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.InoT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.OffT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.TimeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.SigsetT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.StackT

public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stMode: ModeT,
    public var stNlink: NlinkT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var stRdev: DevT,
    public var pad1: DevT,
    public var stSize: OffT,
    public var stBlksize: BlksizeT,
    public var pad2: CInt,
    public var stBlocks: BlkcntT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var unused: IntArray,
) {
    public open fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stMode)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stGid)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.stRdev)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.pad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.pad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr + 120L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stMode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stGid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.stRdev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.pad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.pad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset + 120, value.unused)
                }
            }
    }
}

public open class IpcPerm(
    public var key: KeyT,
    public var uid: UidT,
    public var gid: GidT,
    public var cuid: UidT,
    public var cgid: GidT,
    public var mode: CUint,
    public var seq: CInt,
    public var unused1: CUlong,
    public var unused2: CUlong,
) {
    public open fun clone(): IpcPerm = IpcPerm(key, uid, gid, cuid, cgid, mode, seq, unused1, unused2)

    public companion object {
        public fun typeLayout(): TypeLayout<IpcPerm> =
            object : TypeLayout<IpcPerm> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.key)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.gid)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.cgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.mode)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.seq)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.unused1)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.unused2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.key)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.gid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.cgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.mode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.seq)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.unused2)
                }
            }
    }
}

public open class UserRegsStruct(
    public var regs: ULongArray,
    public var origA0: ULong,
    public var csrEra: ULong,
    public var csrBadv: ULong,
    public var reserved: ULongArray,
) {
    public open fun clone(): UserRegsStruct = UserRegsStruct(regs, origA0, csrEra, csrBadv, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 360L
                override fun readNative(addr: Long): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                        (PrimitiveLayouts.U64).readNative(addr + 272L),
                        (PrimitiveArrayLayouts.uLongArray(10)).readNative(addr + 280L),
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.regs)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.origA0)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.csrEra)
                    (PrimitiveLayouts.U64).writeNative(addr + 272L, value.csrBadv)
                    (PrimitiveArrayLayouts.uLongArray(10)).writeNative(addr + 280L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 272),
                        (PrimitiveArrayLayouts.uLongArray(10)).readBytes(buf, offset + 280),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.regs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.origA0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.csrEra)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 272, value.csrBadv)
                    (PrimitiveArrayLayouts.uLongArray(10)).writeBytes(buf, offset + 280, value.reserved)
                }
            }
    }
}

public open class UserFpStruct(
    public var fpr: ULongArray,
    public var fcc: ULong,
    public var fcsr: UInt,
) {
    public open fun clone(): UserFpStruct = UserFpStruct(fpr, fcc, fcsr)

    public companion object {
        public fun typeLayout(): TypeLayout<UserFpStruct> =
            object : TypeLayout<UserFpStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): UserFpStruct =
                    UserFpStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                    )
                override fun writeNative(addr: Long, value: UserFpStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.fpr)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.fcc)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserFpStruct =
                    UserFpStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserFpStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.fpr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.fcc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.fcsr)
                }
            }
    }
}

public open class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: StackT,
    public var ucSigmask: SigsetT,
    ucMcontextCtorArg: McontextT,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public open fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucSigmask, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong,
            ucLink: MutPtr,
            ucStack: StackT,
            ucSigmask: SigsetT,
            ucMcontext: McontextT,
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public open class McontextT(
    public var pc: CUlong,
    public var gregs: ULongArray,
    public var flags: CUint,
    public var extcontext: ULongArray,
) {
    public open fun clone(): McontextT = McontextT(pc, gregs, flags, extcontext)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                        (PrimitiveArrayLayouts.uLongArray(0)).readNative(addr + 272L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.pc)
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr + 8L, value.gregs)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.flags)
                    (PrimitiveArrayLayouts.uLongArray(0)).writeNative(addr + 272L, value.extcontext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (PrimitiveArrayLayouts.uLongArray(0)).readBytes(buf, offset + 272),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.pc)
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset + 8, value.gregs)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.flags)
                    (PrimitiveArrayLayouts.uLongArray(0)).writeBytes(buf, offset + 272, value.extcontext)
                }
            }
    }
}

public open class CloneArgs(
    public var flags: CUlonglong,
    public var pidfd: CUlonglong,
    public var childTid: CUlonglong,
    public var parentTid: CUlonglong,
    public var exitSignal: CUlonglong,
    public var stack: CUlonglong,
    public var stackSize: CUlonglong,
    public var tls: CUlonglong,
    public var setTid: CUlonglong,
    public var setTidSize: CUlonglong,
    public var cgroup: CUlonglong,
) {
    public open fun clone(): CloneArgs = CloneArgs(flags, pidfd, childTid, parentTid, exitSignal, stack, stackSize, tls, setTid, setTidSize, cgroup)

    public companion object {
        public fun typeLayout(): TypeLayout<CloneArgs> =
            object : TypeLayout<CloneArgs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): CloneArgs =
                    CloneArgs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                    )
                override fun writeNative(addr: Long, value: CloneArgs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.pidfd)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.childTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.parentTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.exitSignal)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stack)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.stackSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.tls)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.setTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.setTidSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.cgroup)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CloneArgs =
                    CloneArgs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CloneArgs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pidfd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.childTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.parentTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.exitSignal)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stack)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.stackSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.tls)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.setTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.setTidSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.cgroup)
                }
            }
    }
}

public open class MaxAlignT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray>,
) {
    public var priv: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray>) : this(allocateNativeCell(priv, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MaxAlignT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MaxAlignT(ptr, layout) }, { it.priv })

    public open fun clone(): MaxAlignT = MaxAlignT(priv, fieldLayout)

    public companion object {
        public operator fun invoke(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray>): MaxAlignT = MaxAlignT(priv, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray>): MaxAlignT = MaxAlignT(fieldStorage, fieldLayout)
    }
}
