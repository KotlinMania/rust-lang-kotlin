@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.riscv64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.BlkcntT
import io.github.kotlinmania.rustlang.libc.DevT
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.InoT
import io.github.kotlinmania.rustlang.libc.KeyT
import io.github.kotlinmania.rustlang.libc.ModeT
import io.github.kotlinmania.rustlang.libc.SigsetT
import io.github.kotlinmania.rustlang.libc.StackT
import io.github.kotlinmania.rustlang.libc.TimeT
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.OffT

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public class Stat(
    public var stDev: DevT = 0,
    public var stIno: InoT = 0uL,
    public var stMode: ModeT = 0u,
    public var stNlink: io.github.kotlinmania.rustlang.libc.NlinkT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0,
    public var pad1: DevT = 0,
    public var stSize: OffT = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.libc.BlksizeT = 0,
    public var pad2: CInt = 0,
    public var stBlocks: BlkcntT = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused: IntArray = IntArray(2),
) {
    public fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 120L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 52L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr + 112L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.stMode)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stRdev)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.stBlksize)
                    (PrimitiveLayouts.I32).writeNative(addr + 52L, value.pad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr + 112L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset + 112),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.stMode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stRdev)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.stBlksize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 52, value.pad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset + 112, value.unused)
                }
            }
    }
}

public data class IpcPerm(
    public var key: KeyT = 0,
    public var uid: UidT = 0,
    public var gid: GidT = 0,
    public var cuid: UidT = 0,
    public var cgid: GidT = 0,
    public var mode: ModeT = 0u,
    public var ipcPermSeq: CInt = 0,
    public var pad1: CLong = 0L,
    public var pad2: CLong = 0L,
) {
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
                        (PrimitiveLayouts.U16).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.key)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.gid)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.cgid)
                    (PrimitiveLayouts.U16).writeNative(addr + 20L, value.mode)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.ipcPermSeq)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.pad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.pad2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.key)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.gid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.cgid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.mode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.ipcPermSeq)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.pad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.pad2)
                }
            }
    }
}

public data class CloneArgs(
    public var flags: CUlonglong = 0uL,
    public var pidfd: CUlonglong = 0uL,
    public var childTid: CUlonglong = 0uL,
    public var parentTid: CUlonglong = 0uL,
    public var exitSignal: CUlonglong = 0uL,
    public var stack: CUlonglong = 0uL,
    public var stackSize: CUlonglong = 0uL,
    public var tls: CUlonglong = 0uL,
    public var setTid: CUlonglong = 0uL,
    public var setTidSize: CUlonglong = 0uL,
    public var cgroup: CUlonglong = 0uL,
) {
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

public class UcontextT internal constructor(
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

    public constructor() : this(
        ucFlags = 0uL,
        ucLinkCtorArg = MutPtr(0L),
        ucStack = StackT(),
        ucSigmask = SigsetT(),
        ucMcontextCtorArg = McontextT(),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): StackT = this.ucStack

    public operator fun component4(): SigsetT = this.ucSigmask

    public operator fun component5(): McontextT = this.ucMcontext

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: StackT = this.ucStack, ucSigmask: SigsetT = this.ucSigmask, ucMcontext: McontextT = this.ucMcontext): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucSigmask = ucSigmask, ucMcontextCtorArg = ucMcontext)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UcontextT) return false
        if (this.ucFlags != other.ucFlags) return false
        if (this.ucLink != other.ucLink) return false
        if (this.ucStack != other.ucStack) return false
        if (this.ucSigmask != other.ucSigmask) return false
        if (this.ucMcontext != other.ucMcontext) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ucFlags.hashCode()
        result = 31 * result + this.ucLink.hashCode()
        result = 31 * result + this.ucStack.hashCode()
        result = 31 * result + this.ucSigmask.hashCode()
        result = 31 * result + this.ucMcontext.hashCode()
        return result
    }

    public fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucSigmask, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong = 0uL,
            ucLink: MutPtr = MutPtr(0L),
            ucStack: StackT = StackT(),
            ucSigmask: SigsetT = SigsetT(),
            ucMcontext: McontextT = McontextT(),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public data class McontextT(
    public var gregs: ULongArray = ULongArray(32),
    public var fpregs: RiscvMcFpState = RiscvMcFpState(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (!this.gregs.contentEquals(other.gregs)) return false
        if (this.fpregs != other.fpregs) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.gregs.contentHashCode()
        result = 31 * result + this.fpregs.hashCode()
        return result
    }
}

public data class RiscvMcFpState(
    public var f: RiscvMcFExtState = RiscvMcFExtState(),
    public var d: RiscvMcDExtState = RiscvMcDExtState(),
    public var q: RiscvMcQExtState = RiscvMcQExtState(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__riscv_mc_fp_state").finishNonExhaustive()
    }
}

public data class RiscvMcFExtState(
    public var f: UIntArray = UIntArray(32),
    public var fcsr: CUint = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcFExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcFExtState> =
            object : TypeLayout<RiscvMcFExtState> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 132L
                override fun readNative(addr: Long): RiscvMcFExtState =
                    RiscvMcFExtState(
                        (PrimitiveArrayLayouts.uIntArray(32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcFExtState) {
                    (PrimitiveArrayLayouts.uIntArray(32)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 128L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcFExtState =
                    RiscvMcFExtState(
                        (PrimitiveArrayLayouts.uIntArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcFExtState) {
                    (PrimitiveArrayLayouts.uIntArray(32)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 128, value.fcsr)
                }
            }
    }
}

public data class RiscvMcDExtState(
    public var f: ULongArray = ULongArray(32),
    public var fcsr: CUint = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcDExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcDExtState> =
            object : TypeLayout<RiscvMcDExtState> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 264L
                override fun readNative(addr: Long): RiscvMcDExtState =
                    RiscvMcDExtState(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 256L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcDExtState) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 256L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcDExtState =
                    RiscvMcDExtState(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 256),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcDExtState) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 256, value.fcsr)
                }
            }
    }
}

public data class RiscvMcQExtState(
    public var f: ULongArray = ULongArray(64),
    public var fcsr: CUint = 0u,
    public var reserved: UIntArray = UIntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcQExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcQExtState> =
            object : TypeLayout<RiscvMcQExtState> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 528L
                override fun readNative(addr: Long): RiscvMcQExtState =
                    RiscvMcQExtState(
                        (PrimitiveArrayLayouts.uLongArray(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 512L),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 516L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcQExtState) {
                    (PrimitiveArrayLayouts.uLongArray(64)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 512L, value.fcsr)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 516L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcQExtState =
                    RiscvMcQExtState(
                        (PrimitiveArrayLayouts.uLongArray(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 512),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 516),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcQExtState) {
                    (PrimitiveArrayLayouts.uLongArray(64)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 512, value.fcsr)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 516, value.reserved)
                }
            }
    }
}
