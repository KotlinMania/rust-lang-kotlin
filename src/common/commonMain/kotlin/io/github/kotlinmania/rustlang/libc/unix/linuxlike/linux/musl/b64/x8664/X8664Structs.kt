@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.x8664

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
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
    public var stNlink: NlinkT,
    public var stMode: ModeT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var pad0: CInt,
    public var stRdev: DevT,
    public var stSize: OffT,
    public var stBlksize: BlksizeT,
    public var stBlocks: BlkcntT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var unused: LongArray,
) {
    public open fun clone(): Stat = Stat(stDev, stIno, stNlink, stMode, stUid, stGid, pad0, stRdev, stSize, stBlksize, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 120L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 120, value.unused)
                }
            }
    }
}

public open class UserRegsStruct(
    public var r15: CUlong,
    public var r14: CUlong,
    public var r13: CUlong,
    public var r12: CUlong,
    public var rbp: CUlong,
    public var rbx: CUlong,
    public var r11: CUlong,
    public var r10: CUlong,
    public var r9: CUlong,
    public var r8: CUlong,
    public var rax: CUlong,
    public var rcx: CUlong,
    public var rdx: CUlong,
    public var rsi: CUlong,
    public var rdi: CUlong,
    public var origRax: CUlong,
    public var rip: CUlong,
    public var cs: CUlong,
    public var eflags: CUlong,
    public var rsp: CUlong,
    public var ss: CUlong,
    public var fsBase: CUlong,
    public var gsBase: CUlong,
    public var ds: CUlong,
    public var es: CUlong,
    public var fs: CUlong,
    public var gs: CUlong,
) {
    public open fun clone(): UserRegsStruct = UserRegsStruct(r15, r14, r13, r12, rbp, rbx, r11, r10, r9, r8, rax, rcx, rdx, rsi, rdi, origRax, rip, cs, eflags, rsp, ss, fsBase, gsBase, ds, es, fs, gs)

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

public open class User internal constructor(
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
) {
    public var uAr0: MutPtr = uAr0CtorArg
        internal set

    public var uFpstate: MutPtr = uFpstateCtorArg
        internal set

    public open fun clone(): User = User(regs, uFpvalid, i387, uTsize, uDsize, uSsize, startCode, startStack, signal, reserved, uAr0, uFpstate, magic, uComm, uDebugreg)

    public companion object {
        public fun typeLayout(): TypeLayout<User> =
            object : TypeLayout<User> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 912L
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
                }
            }

        public operator fun invoke(
            regs: UserRegsStruct,
            uFpvalid: CInt,
            i387: UserFpregsStruct,
            uTsize: CUlong,
            uDsize: CUlong,
            uSsize: CUlong,
            startCode: CUlong,
            startStack: CUlong,
            signal: CLong,
            reserved: CInt,
            uAr0: MutPtr,
            uFpstate: MutPtr,
            magic: CUlong,
            uComm: UByteArray,
            uDebugreg: ULongArray,
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
        )
    }
}

// GitHub repo: ifduyue/musl/
// commit: b4b1e10364c8737a632be61582e05a8d3acf5690
// file: arch/x8664/bits/signal.h#L80-L84
public open class McontextT(
    public var gregs: LongArray,
    public var private: ULongArray,
) {
    public open fun clone(): McontextT = McontextT(gregs, private)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(9)).readNative(addr + 184L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeNative(addr, value.gregs)
                    (PrimitiveArrayLayouts.uLongArray(9)).writeNative(addr + 184L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(9)).readBytes(buf, offset + 184),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeBytes(buf, offset, value.gregs)
                    (PrimitiveArrayLayouts.uLongArray(9)).writeBytes(buf, offset + 184, value.private)
                }
            }
    }
}

public open class IpcPerm(
    public var key: KeyT,
    public var ipcPermKey: KeyT,
    public var uid: UidT,
    public var gid: GidT,
    public var cuid: UidT,
    public var cgid: GidT,
    public var mode: ModeT,
    public var seq: CInt,
    public var unused1: CLong,
    public var unused2: CLong,
) {
    public open fun clone(): IpcPerm = IpcPerm(key, ipcPermKey, uid, gid, cuid, cgid, mode, seq, unused1, unused2)

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
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.key)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ipcPermKey)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.gid)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.cuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.cgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.seq)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.unused1)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.unused2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.key)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ipcPermKey)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.gid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.cuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.cgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.seq)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.unused2)
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

public open class UserFpregsStruct(
    public var cwd: CUshort,
    public var swd: CUshort,
    public var ftw: CUshort,
    public var fop: CUshort,
    public var rip: CUlong,
    public var rdp: CUlong,
    public var mxcsr: CUint,
    public var mxcrMask: CUint,
    public var stSpace: UIntArray,
    public var xmmSpace: UIntArray,
    public var padding: UIntArray,
) {
    public open fun clone(): UserFpregsStruct = UserFpregsStruct(cwd, swd, ftw, fop, rip, rdp, mxcsr, mxcrMask, stSpace, xmmSpace, padding)

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

public open class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: StackT,
    ucMcontextCtorArg: McontextT,
    public var ucSigmask: SigsetT,
    public var private: UByteArray,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public open fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucMcontext, ucSigmask, private)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong,
            ucLink: MutPtr,
            ucStack: StackT,
            ucMcontext: McontextT,
            ucSigmask: SigsetT,
            private: UByteArray,
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucMcontextCtorArg = ucMcontext,
            ucSigmask = ucSigmask,
            private = private,
        )
    }
}

public open class MaxAlignT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
) {
    public var priv: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(priv, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MaxAlignT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MaxAlignT(ptr, layout) }, { it.priv })

    public open fun clone(): MaxAlignT = MaxAlignT(priv, fieldLayout)

    public companion object {
        public operator fun invoke(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): MaxAlignT = MaxAlignT(priv, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): MaxAlignT = MaxAlignT(fieldStorage, fieldLayout)
    }
}
