@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.x8664

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.DevT
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.InoT
import io.github.kotlinmania.rustlang.libc.Sigset64T
import io.github.kotlinmania.rustlang.libc.SigsetT
import io.github.kotlinmania.rustlang.libc.StackT
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.Off64T

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
    public var stNlink: CUlong = 0uL,
    public var stMode: CUint = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0,
    public var stSize: Off64T = 0L,
    public var stBlksize: CLong = 0L,
    public var stBlocks: CLong = 0L,
    public var stAtime: CLong = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: CLong = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: CLong = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused: LongArray = LongArray(3),
) {
    public fun clone(): Stat = Stat(stDev, stIno, stNlink, stMode, stUid, stGid, stRdev, stSize, stBlksize, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 136L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 112L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 112L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 112),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 112, value.unused)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public class Stat64(
    public var stDev: DevT = 0,
    public var stIno: InoT = 0uL,
    public var stNlink: CUlong = 0uL,
    public var stMode: CUint = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0,
    public var stSize: Off64T = 0L,
    public var stBlksize: CLong = 0L,
    public var stBlocks: CLong = 0L,
    public var stAtime: CLong = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: CLong = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: CLong = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused: LongArray = LongArray(3),
) {
    public fun clone(): Stat64 = Stat64(stDev, stIno, stNlink, stMode, stUid, stGid, stRdev, stSize, stBlksize, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat64> =
            object : TypeLayout<Stat64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 136L
                override fun readNative(addr: Long): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 112L),
                    )
                override fun writeNative(addr: Long, value: Stat64) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 112L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 112),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat64) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 112, value.unused)
                }
            }
    }
}

public data class LibcXmmreg(
    public var element: UIntArray = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcXmmreg) return false
        if (!this.element.contentEquals(other.element)) return false
        return true
    }

    override fun hashCode(): Int = this.element.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<LibcXmmreg> =
            object : TypeLayout<LibcXmmreg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LibcXmmreg =
                    LibcXmmreg(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: LibcXmmreg) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.element)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcXmmreg =
                    LibcXmmreg(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcXmmreg) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.element)
                }
            }
    }
}

public class UserRegsStruct(
    public var r15: CUlong = 0uL,
    public var r14: CUlong = 0uL,
    public var r13: CUlong = 0uL,
    public var r12: CUlong = 0uL,
    public var rbp: CUlong = 0uL,
    public var rbx: CUlong = 0uL,
    public var r11: CUlong = 0uL,
    public var r10: CUlong = 0uL,
    public var r9: CUlong = 0uL,
    public var r8: CUlong = 0uL,
    public var rax: CUlong = 0uL,
    public var rcx: CUlong = 0uL,
    public var rdx: CUlong = 0uL,
    public var rsi: CUlong = 0uL,
    public var rdi: CUlong = 0uL,
    public var origRax: CUlong = 0uL,
    public var rip: CUlong = 0uL,
    public var cs: CUlong = 0uL,
    public var eflags: CUlong = 0uL,
    public var rsp: CUlong = 0uL,
    public var ss: CUlong = 0uL,
    public var fsBase: CUlong = 0uL,
    public var gsBase: CUlong = 0uL,
    public var ds: CUlong = 0uL,
    public var es: CUlong = 0uL,
    public var fs: CUlong = 0uL,
    public var gs: CUlong = 0uL,
) {
    public fun clone(): UserRegsStruct = UserRegsStruct(r15, r14, r13, r12, rbp, rbx, r11, r10, r9, r8, rax, rcx, rdx, rsi, rdi, origRax, rip, cs, eflags, rsp, ss, fsBase, gsBase, ds, es, fs, gs)

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 216L
                override fun readNative(addr: Long): UserRegsStruct =
                    UserRegsStruct(
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
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.r15)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.r14)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.r13)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.r12)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.rbp)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.rbx)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.r11)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.r10)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.r9)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.r8)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.rax)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.rcx)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.rdx)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.rsi)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.rdi)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.origRax)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.cs)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.eflags)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.rsp)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.ss)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.fsBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.gsBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.ds)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.es)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.fs)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.gs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserRegsStruct =
                    UserRegsStruct(
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
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.r15)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.r14)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.r13)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.r12)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.rbp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.rbx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.r11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.r10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.r9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.r8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.rax)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.rcx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.rdx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.rsi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.rdi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.origRax)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.cs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.eflags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.rsp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.ss)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.fsBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.gsBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.ds)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.es)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.fs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.gs)
                }
            }
    }
}

public class User internal constructor(
    public var regs: UserRegsStruct,
    public var uFpvalid: CInt,
    public var i387: UserFpregsStruct,
    public var uTsize: CUlong,
    public var uDsize: CUlong,
    public var uSsize: CUlong,
    public var startCode: CUlong,
    public var startStack: CUlong,
    public var signal: CLong,
    public var reserved: CInt,
    uAr0CtorArg: MutPtr,
    uFpstateCtorArg: MutPtr,
    public var magic: CUlong,
    public var uComm: UByteArray,
    public var uDebugreg: ULongArray,
    public var errorCode: CUlong,
    public var faultAddress: CUlong,
) {
    public var uAr0: MutPtr = uAr0CtorArg
        internal set

    public var uFpstate: MutPtr = uFpstateCtorArg
        internal set

    public constructor() : this(
        regs = UserRegsStruct(),
        uFpvalid = 0,
        i387 = UserFpregsStruct(),
        uTsize = 0uL,
        uDsize = 0uL,
        uSsize = 0uL,
        startCode = 0uL,
        startStack = 0uL,
        signal = 0L,
        reserved = 0,
        uAr0CtorArg = MutPtr(0L),
        uFpstateCtorArg = MutPtr(0L),
        magic = 0uL,
        uComm = UByteArray(32),
        uDebugreg = ULongArray(8),
        errorCode = 0uL,
        faultAddress = 0uL,
    )

    public fun clone(): User = User(regs, uFpvalid, i387, uTsize, uDsize, uSsize, startCode, startStack, signal, reserved, uAr0, uFpstate, magic, uComm, uDebugreg, errorCode, faultAddress)

    public companion object {
        public fun typeLayout(): TypeLayout<User> =
            object : TypeLayout<User> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 928L
                override fun readNative(addr: Long): User =
                    User(
                        (UserRegsStruct.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 216L),
                        (UserFpregsStruct.typeLayout()).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 736L),
                        (PrimitiveLayouts.U64).readNative(addr + 744L),
                        (PrimitiveLayouts.U64).readNative(addr + 752L),
                        (PrimitiveLayouts.U64).readNative(addr + 760L),
                        (PrimitiveLayouts.U64).readNative(addr + 768L),
                        (PrimitiveLayouts.I64).readNative(addr + 776L),
                        (PrimitiveLayouts.I32).readNative(addr + 784L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 792L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 800L),
                        (PrimitiveLayouts.U64).readNative(addr + 808L),
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr + 816L),
                        (PrimitiveArrayLayouts.uLongArray(8)).readNative(addr + 848L),
                        (PrimitiveLayouts.U64).readNative(addr + 912L),
                        (PrimitiveLayouts.U64).readNative(addr + 920L),
                    )
                override fun writeNative(addr: Long, value: User) {
                    (UserRegsStruct.typeLayout()).writeNative(addr, value.regs)
                    (PrimitiveLayouts.I32).writeNative(addr + 216L, value.uFpvalid)
                    (UserFpregsStruct.typeLayout()).writeNative(addr + 224L, value.i387)
                    (PrimitiveLayouts.U64).writeNative(addr + 736L, value.uTsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 744L, value.uDsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 752L, value.uSsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 760L, value.startCode)
                    (PrimitiveLayouts.U64).writeNative(addr + 768L, value.startStack)
                    (PrimitiveLayouts.I64).writeNative(addr + 776L, value.signal)
                    (PrimitiveLayouts.I32).writeNative(addr + 784L, value.reserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 792L, value.uAr0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 800L, value.uFpstate)
                    (PrimitiveLayouts.U64).writeNative(addr + 808L, value.magic)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr + 816L, value.uComm)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeNative(addr + 848L, value.uDebugreg)
                    (PrimitiveLayouts.U64).writeNative(addr + 912L, value.errorCode)
                    (PrimitiveLayouts.U64).writeNative(addr + 920L, value.faultAddress)
                }
                override fun readBytes(buf: ByteArray, offset: Int): User =
                    User(
                        (UserRegsStruct.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 216),
                        (UserFpregsStruct.typeLayout()).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 736),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 744),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 752),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 760),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 768),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 776),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 784),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 792),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 800),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 808),
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset + 816),
                        (PrimitiveArrayLayouts.uLongArray(8)).readBytes(buf, offset + 848),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 912),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 920),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: User) {
                    (UserRegsStruct.typeLayout()).writeBytes(buf, offset, value.regs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 216, value.uFpvalid)
                    (UserFpregsStruct.typeLayout()).writeBytes(buf, offset + 224, value.i387)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 736, value.uTsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 744, value.uDsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 752, value.uSsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 760, value.startCode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 768, value.startStack)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 776, value.signal)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 784, value.reserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 792, value.uAr0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 800, value.uFpstate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 808, value.magic)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset + 816, value.uComm)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeBytes(buf, offset + 848, value.uDebugreg)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 912, value.errorCode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 920, value.faultAddress)
                }
            }

        public operator fun invoke(
            regs: UserRegsStruct = UserRegsStruct(),
            uFpvalid: CInt = 0,
            i387: UserFpregsStruct = UserFpregsStruct(),
            uTsize: CUlong = 0uL,
            uDsize: CUlong = 0uL,
            uSsize: CUlong = 0uL,
            startCode: CUlong = 0uL,
            startStack: CUlong = 0uL,
            signal: CLong = 0L,
            reserved: CInt = 0,
            uAr0: MutPtr = MutPtr(0L),
            uFpstate: MutPtr = MutPtr(0L),
            magic: CUlong = 0uL,
            uComm: UByteArray = UByteArray(32),
            uDebugreg: ULongArray = ULongArray(8),
            errorCode: CUlong = 0uL,
            faultAddress: CUlong = 0uL,
        ): User = User(
            regs = regs,
            uFpvalid = uFpvalid,
            i387 = i387,
            uTsize = uTsize,
            uDsize = uDsize,
            uSsize = uSsize,
            startCode = startCode,
            startStack = startStack,
            signal = signal,
            reserved = reserved,
            uAr0CtorArg = uAr0,
            uFpstateCtorArg = uFpstate,
            magic = magic,
            uComm = uComm,
            uDebugreg = uDebugreg,
            errorCode = errorCode,
            faultAddress = faultAddress,
        )
    }
}

public data class LibcFpxreg(
    public var significand: UShortArray = UShortArray(4),
    public var exponent: UShort = 0u,
    public var padding: UShortArray = UShortArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcFpxreg) return false
        if (!this.significand.contentEquals(other.significand)) return false
        if (this.exponent != other.exponent) return false
        if (!this.padding.contentEquals(other.padding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.significand.contentHashCode()
        result = 31 * result + this.exponent.hashCode()
        result = 31 * result + this.padding.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<LibcFpxreg> =
            object : TypeLayout<LibcFpxreg> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LibcFpxreg =
                    LibcFpxreg(
                        (PrimitiveArrayLayouts.uShortArray(4)).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uShortArray(3)).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: LibcFpxreg) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeNative(addr, value.significand)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.exponent)
                    (PrimitiveArrayLayouts.uShortArray(3)).writeNative(addr + 10L, value.padding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcFpxreg =
                    LibcFpxreg(
                        (PrimitiveArrayLayouts.uShortArray(4)).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uShortArray(3)).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcFpxreg) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeBytes(buf, offset, value.significand)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.exponent)
                    (PrimitiveArrayLayouts.uShortArray(3)).writeBytes(buf, offset + 10, value.padding)
                }
            }
    }
}

public data class LibcFpstate(
    public var cwd: UShort = 0u,
    public var swd: UShort = 0u,
    public var ftw: UShort = 0u,
    public var fop: UShort = 0u,
    public var rip: ULong = 0uL,
    public var rdp: ULong = 0uL,
    public var mxcsr: UInt = 0u,
    public var mxcrMask: UInt = 0u,
    public var st: List<LibcFpxreg> = List(8) { LibcFpxreg() },
    public var xmm: List<LibcXmmreg> = List(16) { LibcXmmreg() },
    public var private: UIntArray = UIntArray(24),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcFpstate) return false
        if (this.cwd != other.cwd) return false
        if (this.swd != other.swd) return false
        if (this.ftw != other.ftw) return false
        if (this.fop != other.fop) return false
        if (this.rip != other.rip) return false
        if (this.rdp != other.rdp) return false
        if (this.mxcsr != other.mxcsr) return false
        if (this.mxcrMask != other.mxcrMask) return false
        if (this.st != other.st) return false
        if (this.xmm != other.xmm) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cwd.hashCode()
        result = 31 * result + this.swd.hashCode()
        result = 31 * result + this.ftw.hashCode()
        result = 31 * result + this.fop.hashCode()
        result = 31 * result + this.rip.hashCode()
        result = 31 * result + this.rdp.hashCode()
        result = 31 * result + this.mxcsr.hashCode()
        result = 31 * result + this.mxcrMask.hashCode()
        result = 31 * result + this.st.hashCode()
        result = 31 * result + this.xmm.hashCode()
        result = 31 * result + this.private.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<LibcFpstate> =
            object : TypeLayout<LibcFpstate> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): LibcFpstate =
                    LibcFpstate(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).readNative(addr + 32L),
                        (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uIntArray(24)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: LibcFpstate) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.cwd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.swd)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.ftw)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fop)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.rdp)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxcsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxcrMask)
                    (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).writeNative(addr + 32L, value.st)
                    (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).writeNative(addr + 160L, value.xmm)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeNative(addr + 416L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcFpstate =
                    LibcFpstate(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uIntArray(24)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcFpstate) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.cwd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.swd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.ftw)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fop)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.rdp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxcsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxcrMask)
                    (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).writeBytes(buf, offset + 32, value.st)
                    (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).writeBytes(buf, offset + 160, value.xmm)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeBytes(buf, offset + 416, value.private)
                }
            }
    }
}

public class McontextT internal constructor(
    public var gregs: LongArray,
    fpregsCtorArg: MutPtr,
    public var private: ULongArray,
) {
    public var fpregs: MutPtr = fpregsCtorArg
        internal set

    public constructor() : this(
        gregs = LongArray(23),
        fpregsCtorArg = MutPtr(0L),
        private = ULongArray(8),
    )

    public operator fun component1(): LongArray = this.gregs

    public operator fun component2(): MutPtr = this.fpregs

    public operator fun component3(): ULongArray = this.private

    public fun copy(gregs: LongArray = this.gregs, fpregs: MutPtr = this.fpregs, private: ULongArray = this.private): McontextT = McontextT(gregs = gregs, fpregsCtorArg = fpregs, private = private)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (!this.gregs.contentEquals(other.gregs)) return false
        if (this.fpregs != other.fpregs) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.gregs.contentHashCode()
        result = 31 * result + this.fpregs.hashCode()
        result = 31 * result + this.private.contentHashCode()
        return result
    }

    public fun clone(): McontextT = McontextT(gregs, fpregs, private)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 184L),
                        (PrimitiveArrayLayouts.uLongArray(8)).readNative(addr + 192L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeNative(addr, value.gregs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 184L, value.fpregs)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeNative(addr + 192L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 184),
                        (PrimitiveArrayLayouts.uLongArray(8)).readBytes(buf, offset + 192),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeBytes(buf, offset, value.gregs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 184, value.fpregs)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeBytes(buf, offset + 192, value.private)
                }
            }

        public operator fun invoke(
            gregs: LongArray = LongArray(23),
            fpregs: MutPtr = MutPtr(0L),
            private: ULongArray = ULongArray(8),
        ): McontextT = McontextT(
            gregs = gregs,
            fpregsCtorArg = fpregs,
            private = private,
        )
    }
}

public class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: StackT,
    ucMcontextCtorArg: McontextT,
    public var ucSigmask64: CAnonymousUcSigmask,
    public var fpregsMem: LibcFpstate,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public constructor() : this(
        ucFlags = 0uL,
        ucLinkCtorArg = MutPtr(0L),
        ucStack = StackT(),
        ucMcontextCtorArg = McontextT(),
        ucSigmask64 = CAnonymousUcSigmask(),
        fpregsMem = LibcFpstate(),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): StackT = this.ucStack

    public operator fun component4(): McontextT = this.ucMcontext

    public operator fun component5(): CAnonymousUcSigmask = this.ucSigmask64

    public operator fun component6(): LibcFpstate = this.fpregsMem

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: StackT = this.ucStack, ucMcontext: McontextT = this.ucMcontext, ucSigmask64: CAnonymousUcSigmask = this.ucSigmask64, fpregsMem: LibcFpstate = this.fpregsMem): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucMcontextCtorArg = ucMcontext, ucSigmask64 = ucSigmask64, fpregsMem = fpregsMem)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UcontextT) return false
        if (this.ucFlags != other.ucFlags) return false
        if (this.ucLink != other.ucLink) return false
        if (this.ucStack != other.ucStack) return false
        if (this.ucMcontext != other.ucMcontext) return false
        if (this.ucSigmask64 != other.ucSigmask64) return false
        if (this.fpregsMem != other.fpregsMem) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ucFlags.hashCode()
        result = 31 * result + this.ucLink.hashCode()
        result = 31 * result + this.ucStack.hashCode()
        result = 31 * result + this.ucMcontext.hashCode()
        result = 31 * result + this.ucSigmask64.hashCode()
        result = 31 * result + this.fpregsMem.hashCode()
        return result
    }

    public fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucMcontext, ucSigmask64, fpregsMem)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong = 0uL,
            ucLink: MutPtr = MutPtr(0L),
            ucStack: StackT = StackT(),
            ucMcontext: McontextT = McontextT(),
            ucSigmask64: CAnonymousUcSigmask = CAnonymousUcSigmask(),
            fpregsMem: LibcFpstate = LibcFpstate(),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucMcontextCtorArg = ucMcontext,
            ucSigmask64 = ucSigmask64,
            fpregsMem = fpregsMem,
        )
    }
}

public data class UserFpregsStruct(
    public var cwd: CUshort = 0u,
    public var swd: CUshort = 0u,
    public var ftw: CUshort = 0u,
    public var fop: CUshort = 0u,
    public var rip: CUlong = 0uL,
    public var rdp: CUlong = 0uL,
    public var mxcsr: CUint = 0u,
    public var mxcrMask: CUint = 0u,
    public var stSpace: UIntArray = UIntArray(32),
    public var xmmSpace: UIntArray = UIntArray(64),
    public var padding: UIntArray = UIntArray(24),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserFpregsStruct) return false
        if (this.cwd != other.cwd) return false
        if (this.swd != other.swd) return false
        if (this.ftw != other.ftw) return false
        if (this.fop != other.fop) return false
        if (this.rip != other.rip) return false
        if (this.rdp != other.rdp) return false
        if (this.mxcsr != other.mxcsr) return false
        if (this.mxcrMask != other.mxcrMask) return false
        if (!this.stSpace.contentEquals(other.stSpace)) return false
        if (!this.xmmSpace.contentEquals(other.xmmSpace)) return false
        if (!this.padding.contentEquals(other.padding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cwd.hashCode()
        result = 31 * result + this.swd.hashCode()
        result = 31 * result + this.ftw.hashCode()
        result = 31 * result + this.fop.hashCode()
        result = 31 * result + this.rip.hashCode()
        result = 31 * result + this.rdp.hashCode()
        result = 31 * result + this.mxcsr.hashCode()
        result = 31 * result + this.mxcrMask.hashCode()
        result = 31 * result + this.stSpace.contentHashCode()
        result = 31 * result + this.xmmSpace.contentHashCode()
        result = 31 * result + this.padding.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UserFpregsStruct> =
            object : TypeLayout<UserFpregsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): UserFpregsStruct =
                    UserFpregsStruct(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveArrayLayouts.uIntArray(32)).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uIntArray(64)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uIntArray(24)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: UserFpregsStruct) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.cwd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.swd)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.ftw)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fop)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.rdp)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxcsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxcrMask)
                    (PrimitiveArrayLayouts.uIntArray(32)).writeNative(addr + 32L, value.stSpace)
                    (PrimitiveArrayLayouts.uIntArray(64)).writeNative(addr + 160L, value.xmmSpace)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeNative(addr + 416L, value.padding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserFpregsStruct =
                    UserFpregsStruct(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveArrayLayouts.uIntArray(32)).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uIntArray(64)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uIntArray(24)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserFpregsStruct) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.cwd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.swd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.ftw)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fop)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.rdp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxcsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxcrMask)
                    (PrimitiveArrayLayouts.uIntArray(32)).writeBytes(buf, offset + 32, value.stSpace)
                    (PrimitiveArrayLayouts.uIntArray(64)).writeBytes(buf, offset + 160, value.xmmSpace)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeBytes(buf, offset + 416, value.padding)
                }
            }
    }
}

public data class CAnonymousUcSigmask(
    public var ucSigmask: SigsetT = SigsetT(),
    public var ucSigmask64: Sigset64T = Sigset64T(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_uc_sigmask").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousUcSigmask): Boolean = this.ucSigmask == other.ucSigmask

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.ucSigmask).hashCode()
        return state
    }
}

public data class MaxAlignT(
    public var priv: DoubleArray = DoubleArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaxAlignT) return false
        if (!this.priv.contentEquals(other.priv)) return false
        return true
    }

    override fun hashCode(): Int = this.priv.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<MaxAlignT> =
            object : TypeLayout<MaxAlignT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeNative(addr, value.priv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeBytes(buf, offset, value.priv)
                }
            }
    }
}
