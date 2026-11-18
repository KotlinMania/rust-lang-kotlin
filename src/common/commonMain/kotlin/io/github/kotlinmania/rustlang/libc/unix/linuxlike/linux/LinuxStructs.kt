@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.IFNAMSIZ
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.Timeval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.ClockidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIZEOF_PTHREAD_COND_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SaFamilyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SchedParam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Sockaddr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.SIZEOF_PTHREAD_RWLOCKATTR_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.SigsetT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.SIZEOF_PTHREAD_MUTEXATTR_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_BARRIER_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_MUTEX_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_RWLOCK_T

private class DebugStruct(
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

// linux/cnProc.h
// uapi/linux/magic.h
// Most `* _SUPER_MAGIC` constants are defined at the `linuxLike` level; the
// following are only available on newer Linux versions than the versions
// currently used in CI in some configurations, so we define them here.
// These functions are not available on OpenHarmony
// LFS64 extensions
//
// * musl has 64-bit versions only so aliases the LFS64 symbols to the standard ones
public open class Dqblk(
    public var dqbBhardlimit: ULong,
    public var dqbBsoftlimit: ULong,
    public var dqbCurspace: ULong,
    public var dqbIhardlimit: ULong,
    public var dqbIsoftlimit: ULong,
    public var dqbCurinodes: ULong,
    public var dqbBtime: ULong,
    public var dqbItime: ULong,
    public var dqbValid: UInt,
) {
    public open fun clone(): Dqblk = Dqblk(dqbBhardlimit, dqbBsoftlimit, dqbCurspace, dqbIhardlimit, dqbIsoftlimit, dqbCurinodes, dqbBtime, dqbItime, dqbValid)

    public companion object {
        public fun typeLayout(): TypeLayout<Dqblk> =
            object : TypeLayout<Dqblk> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): Dqblk =
                    Dqblk(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: Dqblk) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dqbBhardlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.dqbBsoftlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.dqbCurspace)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.dqbIhardlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.dqbIsoftlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.dqbCurinodes)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.dqbBtime)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.dqbItime)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.dqbValid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dqblk =
                    Dqblk(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dqblk) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dqbBhardlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.dqbBsoftlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.dqbCurspace)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.dqbIhardlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.dqbIsoftlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.dqbCurinodes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.dqbBtime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.dqbItime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.dqbValid)
                }
            }
    }
}

public open class SignalfdSiginfo(
    public var ssiSigno: UInt,
    public var ssiErrno: Int,
    public var ssiCode: Int,
    public var ssiPid: UInt,
    public var ssiUid: UInt,
    public var ssiFd: Int,
    public var ssiTid: UInt,
    public var ssiBand: UInt,
    public var ssiOverrun: UInt,
    public var ssiTrapno: UInt,
    public var ssiStatus: Int,
    public var ssiInt: Int,
    public var ssiPtr: ULong,
    public var ssiUtime: ULong,
    public var ssiStime: ULong,
    public var ssiAddr: ULong,
    public var ssiAddrLsb: UShort,
    public var pad2: UShort,
    public var ssiSyscall: Int,
    public var ssiCallAddr: ULong,
    public var ssiArch: UInt,
    public var pad: UByteArray,
) {
    public open fun clone(): SignalfdSiginfo = SignalfdSiginfo(ssiSigno, ssiErrno, ssiCode, ssiPid, ssiUid, ssiFd, ssiTid, ssiBand, ssiOverrun, ssiTrapno, ssiStatus, ssiInt, ssiPtr, ssiUtime, ssiStime, ssiAddr, ssiAddrLsb, pad2, ssiSyscall, ssiCallAddr, ssiArch, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<SignalfdSiginfo> =
            object : TypeLayout<SignalfdSiginfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SignalfdSiginfo =
                    SignalfdSiginfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U16).readNative(addr + 80L),
                        (PrimitiveLayouts.U16).readNative(addr + 82L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U32).readNative(addr + 96L),
                        (PrimitiveArrayLayouts.uByteArray(28)).readNative(addr + 100L),
                    )
                override fun writeNative(addr: Long, value: SignalfdSiginfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ssiSigno)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ssiErrno)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ssiCode)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.ssiPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ssiUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.ssiFd)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.ssiTid)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.ssiBand)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.ssiOverrun)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.ssiTrapno)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.ssiStatus)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.ssiInt)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.ssiPtr)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.ssiUtime)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.ssiStime)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.ssiAddr)
                    (PrimitiveLayouts.U16).writeNative(addr + 80L, value.ssiAddrLsb)
                    (PrimitiveLayouts.U16).writeNative(addr + 82L, value.pad2)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.ssiSyscall)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.ssiCallAddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 96L, value.ssiArch)
                    (PrimitiveArrayLayouts.uByteArray(28)).writeNative(addr + 100L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SignalfdSiginfo =
                    SignalfdSiginfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 96),
                        (PrimitiveArrayLayouts.uByteArray(28)).readBytes(buf, offset + 100),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SignalfdSiginfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ssiSigno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ssiErrno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ssiCode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.ssiPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ssiUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.ssiFd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.ssiTid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.ssiBand)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.ssiOverrun)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.ssiTrapno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.ssiStatus)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.ssiInt)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.ssiPtr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.ssiUtime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.ssiStime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.ssiAddr)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 80, value.ssiAddrLsb)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 82, value.pad2)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.ssiSyscall)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.ssiCallAddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 96, value.ssiArch)
                    (PrimitiveArrayLayouts.uByteArray(28)).writeBytes(buf, offset + 100, value.pad)
                }
            }
    }
}

// System V IPC
public open class Msginfo(
    public var msgpool: CInt,
    public var msgmap: CInt,
    public var msgmax: CInt,
    public var msgmnb: CInt,
    public var msgmni: CInt,
    public var msgssz: CInt,
    public var msgtql: CInt,
    public var msgseg: CUshort,
) {
    public open fun clone(): Msginfo = Msginfo(msgpool, msgmap, msgmax, msgmnb, msgmni, msgssz, msgtql, msgseg)

    public companion object {
        public fun typeLayout(): TypeLayout<Msginfo> =
            object : TypeLayout<Msginfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Msginfo =
                    Msginfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: Msginfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.msgpool)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.msgmap)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.msgmax)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.msgmnb)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.msgmni)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.msgssz)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.msgtql)
                    (PrimitiveLayouts.U16).writeNative(addr + 28L, value.msgseg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Msginfo =
                    Msginfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Msginfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.msgpool)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.msgmap)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.msgmax)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.msgmnb)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.msgmni)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.msgssz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.msgtql)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 28, value.msgseg)
                }
            }
    }
}

public open class InputEvent(
    // Note(1.0): Change to the commented variant, see https://github.com/rust-lang/libc/pull/4148#discussionR1857511742
    public var time: Timeval,
    public var type: U16,
    public var code: U16,
    public var value: S32,
) {
    public open fun clone(): InputEvent = InputEvent(time, type, code, value)

    public companion object
}

public open class InputId(
    public var bustype: U16,
    public var vendor: U16,
    public var product: U16,
    public var version: U16,
) {
    public open fun clone(): InputId = InputId(bustype, vendor, product, version)

    public companion object {
        public fun typeLayout(): TypeLayout<InputId> =
            object : TypeLayout<InputId> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): InputId =
                    InputId(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: InputId) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.bustype)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.vendor)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.product)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.version)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputId =
                    InputId(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputId) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.bustype)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.vendor)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.product)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.version)
                }
            }
    }
}

public open class InputAbsinfo(
    public var value: S32,
    public var minimum: S32,
    public var maximum: S32,
    public var fuzz: S32,
    public var flat: S32,
    public var resolution: S32,
) {
    public open fun clone(): InputAbsinfo = InputAbsinfo(value, minimum, maximum, fuzz, flat, resolution)

    public companion object {
        public fun typeLayout(): TypeLayout<InputAbsinfo> =
            object : TypeLayout<InputAbsinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): InputAbsinfo =
                    InputAbsinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: InputAbsinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.minimum)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.maximum)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.fuzz)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.flat)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.resolution)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputAbsinfo =
                    InputAbsinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputAbsinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.minimum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.maximum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.fuzz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.flat)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.resolution)
                }
            }
    }
}

public open class InputKeymapEntry(
    public var flags: U8,
    public var len: U8,
    public var index: U16,
    public var keycode: U32,
    public var scancode: UByteArray,
) {
    public open fun clone(): InputKeymapEntry = InputKeymapEntry(flags, len, index, keycode, scancode)

    public companion object {
        public fun typeLayout(): TypeLayout<InputKeymapEntry> =
            object : TypeLayout<InputKeymapEntry> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): InputKeymapEntry =
                    InputKeymapEntry(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InputKeymapEntry) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.len)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.keycode)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr + 8L, value.scancode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputKeymapEntry =
                    InputKeymapEntry(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputKeymapEntry) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.len)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.keycode)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset + 8, value.scancode)
                }
            }
    }
}

public open class InputMask(
    public var type: U32,
    public var codesSize: U32,
    public var codesPtr: U64,
) {
    public open fun clone(): InputMask = InputMask(type, codesSize, codesPtr)

    public companion object {
        public fun typeLayout(): TypeLayout<InputMask> =
            object : TypeLayout<InputMask> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InputMask =
                    InputMask(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InputMask) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.type)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.codesSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.codesPtr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputMask =
                    InputMask(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputMask) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.type)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.codesSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.codesPtr)
                }
            }
    }
}

public open class FfReplay(
    public var length: U16,
    public var delay: U16,
) {
    public open fun clone(): FfReplay = FfReplay(length, delay)

    public companion object {
        public fun typeLayout(): TypeLayout<FfReplay> =
            object : TypeLayout<FfReplay> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfReplay =
                    FfReplay(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfReplay) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.length)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.delay)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfReplay =
                    FfReplay(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfReplay) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.length)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.delay)
                }
            }
    }
}

public open class FfTrigger(
    public var button: U16,
    public var interval: U16,
) {
    public open fun clone(): FfTrigger = FfTrigger(button, interval)

    public companion object {
        public fun typeLayout(): TypeLayout<FfTrigger> =
            object : TypeLayout<FfTrigger> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfTrigger =
                    FfTrigger(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfTrigger) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.button)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.interval)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfTrigger =
                    FfTrigger(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfTrigger) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.button)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.interval)
                }
            }
    }
}

public open class FfEnvelope(
    public var attackLength: U16,
    public var attackLevel: U16,
    public var fadeLength: U16,
    public var fadeLevel: U16,
) {
    public open fun clone(): FfEnvelope = FfEnvelope(attackLength, attackLevel, fadeLength, fadeLevel)

    public companion object {
        public fun typeLayout(): TypeLayout<FfEnvelope> =
            object : TypeLayout<FfEnvelope> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FfEnvelope =
                    FfEnvelope(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: FfEnvelope) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.attackLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.attackLevel)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.fadeLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fadeLevel)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfEnvelope =
                    FfEnvelope(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfEnvelope) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.attackLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.attackLevel)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.fadeLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fadeLevel)
                }
            }
    }
}

public open class FfConstantEffect(
    public var level: S16,
    public var envelope: FfEnvelope,
) {
    public open fun clone(): FfConstantEffect = FfConstantEffect(level, envelope)

    public companion object {
        public fun typeLayout(): TypeLayout<FfConstantEffect> =
            object : TypeLayout<FfConstantEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 10L
                override fun readNative(addr: Long): FfConstantEffect =
                    FfConstantEffect(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (FfEnvelope.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfConstantEffect) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.level)
                    (FfEnvelope.typeLayout()).writeNative(addr + 2L, value.envelope)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfConstantEffect =
                    FfConstantEffect(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfConstantEffect) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.level)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 2, value.envelope)
                }
            }
    }
}

public open class FfRampEffect(
    public var startLevel: S16,
    public var endLevel: S16,
    public var envelope: FfEnvelope,
) {
    public open fun clone(): FfRampEffect = FfRampEffect(startLevel, endLevel, envelope)

    public companion object {
        public fun typeLayout(): TypeLayout<FfRampEffect> =
            object : TypeLayout<FfRampEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): FfRampEffect =
                    FfRampEffect(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (FfEnvelope.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FfRampEffect) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.startLevel)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.endLevel)
                    (FfEnvelope.typeLayout()).writeNative(addr + 4L, value.envelope)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfRampEffect =
                    FfRampEffect(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfRampEffect) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.startLevel)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.endLevel)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 4, value.envelope)
                }
            }
    }
}

public open class FfConditionEffect(
    public var rightSaturation: U16,
    public var leftSaturation: U16,
    public var rightCoeff: S16,
    public var leftCoeff: S16,
    public var deadband: U16,
    public var center: S16,
) {
    public open fun clone(): FfConditionEffect = FfConditionEffect(rightSaturation, leftSaturation, rightCoeff, leftCoeff, deadband, center)

    public companion object {
        public fun typeLayout(): TypeLayout<FfConditionEffect> =
            object : TypeLayout<FfConditionEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): FfConditionEffect =
                    FfConditionEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.I16).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: FfConditionEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rightSaturation)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.leftSaturation)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.rightCoeff)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.leftCoeff)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.deadband)
                    (PrimitiveLayouts.I16).writeNative(addr + 10L, value.center)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfConditionEffect =
                    FfConditionEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfConditionEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rightSaturation)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.leftSaturation)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.rightCoeff)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.leftCoeff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.deadband)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 10, value.center)
                }
            }
    }
}

public open class FfPeriodicEffect internal constructor(
    public var waveform: U16,
    public var period: U16,
    public var magnitude: S16,
    public var offset: S16,
    public var phase: U16,
    public var envelope: FfEnvelope,
    public var customLen: U32,
    customDataCtorArg: MutPtr,
) {
    public var customData: MutPtr = customDataCtorArg
        internal set

    public open fun clone(): FfPeriodicEffect = FfPeriodicEffect(waveform, period, magnitude, offset, phase, envelope, customLen, customData)

    public companion object {
        public fun typeLayout(): TypeLayout<FfPeriodicEffect> =
            object : TypeLayout<FfPeriodicEffect> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): FfPeriodicEffect =
                    FfPeriodicEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (FfEnvelope.typeLayout()).readNative(addr + 10L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: FfPeriodicEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.waveform)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.period)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.magnitude)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.offset)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.phase)
                    (FfEnvelope.typeLayout()).writeNative(addr + 10L, value.envelope)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.customLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.customData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfPeriodicEffect =
                    FfPeriodicEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfPeriodicEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.waveform)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.period)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.magnitude)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.offset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.phase)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 10, value.envelope)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.customLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.customData)
                }
            }

        public operator fun invoke(
            waveform: U16,
            period: U16,
            magnitude: S16,
            offset: S16,
            phase: U16,
            envelope: FfEnvelope,
            customLen: U32,
            customData: MutPtr,
        ): FfPeriodicEffect = FfPeriodicEffect(
            waveform = waveform,
            period = period,
            magnitude = magnitude,
            offset = offset,
            phase = phase,
            envelope = envelope,
            customLen = customLen,
            customDataCtorArg = customData,
        )
    }
}

public open class FfRumbleEffect(
    public var strongMagnitude: U16,
    public var weakMagnitude: U16,
) {
    public open fun clone(): FfRumbleEffect = FfRumbleEffect(strongMagnitude, weakMagnitude)

    public companion object {
        public fun typeLayout(): TypeLayout<FfRumbleEffect> =
            object : TypeLayout<FfRumbleEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfRumbleEffect =
                    FfRumbleEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfRumbleEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.strongMagnitude)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.weakMagnitude)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfRumbleEffect =
                    FfRumbleEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfRumbleEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.strongMagnitude)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.weakMagnitude)
                }
            }
    }
}

public open class FfEffect(
    public var type: U16,
    public var id: S16,
    public var direction: U16,
    public var trigger: FfTrigger,
    public var replay: FfReplay,
    // Note(1.0): this is actually a union
    public var u: ULongArray,
) {
    public open fun clone(): FfEffect = FfEffect(type, id, direction, trigger, replay, u)

    public companion object {
        public fun typeLayout(): TypeLayout<FfEffect> =
            object : TypeLayout<FfEffect> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): FfEffect =
                    FfEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (FfTrigger.typeLayout()).readNative(addr + 6L),
                        (FfReplay.typeLayout()).readNative(addr + 10L),
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: FfEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.type)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.id)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.direction)
                    (FfTrigger.typeLayout()).writeNative(addr + 6L, value.trigger)
                    (FfReplay.typeLayout()).writeNative(addr + 10L, value.replay)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 16L, value.u)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfEffect =
                    FfEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (FfTrigger.typeLayout()).readBytes(buf, offset + 6),
                        (FfReplay.typeLayout()).readBytes(buf, offset + 10),
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.type)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.id)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.direction)
                    (FfTrigger.typeLayout()).writeBytes(buf, offset + 6, value.trigger)
                    (FfReplay.typeLayout()).writeBytes(buf, offset + 10, value.replay)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 16, value.u)
                }
            }
    }
}

public open class UinputFfUpload(
    public var requestId: U32,
    public var retval: S32,
    public var effect: FfEffect,
    public var old: FfEffect,
) {
    public open fun clone(): UinputFfUpload = UinputFfUpload(requestId, retval, effect, old)

    public companion object {
        public fun typeLayout(): TypeLayout<UinputFfUpload> =
            object : TypeLayout<UinputFfUpload> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 104L
                override fun readNative(addr: Long): UinputFfUpload =
                    UinputFfUpload(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (FfEffect.typeLayout()).readNative(addr + 8L),
                        (FfEffect.typeLayout()).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: UinputFfUpload) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.requestId)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.retval)
                    (FfEffect.typeLayout()).writeNative(addr + 8L, value.effect)
                    (FfEffect.typeLayout()).writeNative(addr + 56L, value.old)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputFfUpload =
                    UinputFfUpload(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (FfEffect.typeLayout()).readBytes(buf, offset + 8),
                        (FfEffect.typeLayout()).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputFfUpload) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.requestId)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.retval)
                    (FfEffect.typeLayout()).writeBytes(buf, offset + 8, value.effect)
                    (FfEffect.typeLayout()).writeBytes(buf, offset + 56, value.old)
                }
            }
    }
}

public open class UinputFfErase(
    public var requestId: U32,
    public var retval: S32,
    public var effectId: U32,
) {
    public open fun clone(): UinputFfErase = UinputFfErase(requestId, retval, effectId)

    public companion object {
        public fun typeLayout(): TypeLayout<UinputFfErase> =
            object : TypeLayout<UinputFfErase> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): UinputFfErase =
                    UinputFfErase(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: UinputFfErase) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.requestId)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.retval)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.effectId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputFfErase =
                    UinputFfErase(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputFfErase) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.requestId)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.retval)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.effectId)
                }
            }
    }
}

public open class UinputAbsSetup(
    public var code: U16,
    public var absinfo: InputAbsinfo,
) {
    public open fun clone(): UinputAbsSetup = UinputAbsSetup(code, absinfo)

    public companion object {
        public fun typeLayout(): TypeLayout<UinputAbsSetup> =
            object : TypeLayout<UinputAbsSetup> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 28L
                override fun readNative(addr: Long): UinputAbsSetup =
                    UinputAbsSetup(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (InputAbsinfo.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: UinputAbsSetup) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.code)
                    (InputAbsinfo.typeLayout()).writeNative(addr + 4L, value.absinfo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputAbsSetup =
                    UinputAbsSetup(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (InputAbsinfo.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputAbsSetup) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.code)
                    (InputAbsinfo.typeLayout()).writeBytes(buf, offset + 4, value.absinfo)
                }
            }
    }
}

public open class CAnonymousKernelFsidT(
    public var value: IntArray,
) {
    public open fun clone(): CAnonymousKernelFsidT = CAnonymousKernelFsidT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousKernelFsidT> =
            object : TypeLayout<CAnonymousKernelFsidT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousKernelFsidT =
                    CAnonymousKernelFsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CAnonymousKernelFsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousKernelFsidT =
                    CAnonymousKernelFsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousKernelFsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class PosixSpawnFileActionsT internal constructor(
    public var allocated: CInt,
    public var used: CInt,
    actionsCtorArg: MutPtr,
    public var pad: IntArray,
) {
    public var actions: MutPtr = actionsCtorArg
        internal set

    public open fun clone(): PosixSpawnFileActionsT = PosixSpawnFileActionsT(allocated, used, actions, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<PosixSpawnFileActionsT> =
            object : TypeLayout<PosixSpawnFileActionsT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): PosixSpawnFileActionsT =
                    PosixSpawnFileActionsT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.intArray(16)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: PosixSpawnFileActionsT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.allocated)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.used)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.actions)
                    (PrimitiveArrayLayouts.intArray(16)).writeNative(addr + 16L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PosixSpawnFileActionsT =
                    PosixSpawnFileActionsT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.intArray(16)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PosixSpawnFileActionsT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.allocated)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.used)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.actions)
                    (PrimitiveArrayLayouts.intArray(16)).writeBytes(buf, offset + 16, value.pad)
                }
            }

        public operator fun invoke(
            allocated: CInt,
            used: CInt,
            actions: MutPtr,
            pad: IntArray,
        ): PosixSpawnFileActionsT = PosixSpawnFileActionsT(
            allocated = allocated,
            used = used,
            actionsCtorArg = actions,
            pad = pad,
        )
    }
}

public open class PosixSpawnattrT(
    public var flags: CShort,
    public var pgrp: PidT,
    public var sd: SigsetT,
    public var ss: SigsetT,
    public var prio: CInt,
    public var sp: SchedParam,
    public var policy: CInt,
    public var pad: IntArray,
) {
    public open fun clone(): PosixSpawnattrT = PosixSpawnattrT(flags, pgrp, sd, ss, prio, sp, policy, pad)

    public companion object
}

public open class Genlmsghdr(
    public var cmd: UByte,
    public var version: UByte,
    public var reserved: UShort,
) {
    public open fun clone(): Genlmsghdr = Genlmsghdr(cmd, version, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Genlmsghdr> =
            object : TypeLayout<Genlmsghdr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Genlmsghdr =
                    Genlmsghdr(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Genlmsghdr) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.version)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Genlmsghdr =
                    Genlmsghdr(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Genlmsghdr) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.version)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.reserved)
                }
            }
    }
}

public open class InotifyEvent(
    public var wd: CInt,
    public var mask: UInt,
    public var cookie: UInt,
    public var len: UInt,
) {
    public open fun clone(): InotifyEvent = InotifyEvent(wd, mask, cookie, len)

    public companion object {
        public fun typeLayout(): TypeLayout<InotifyEvent> =
            object : TypeLayout<InotifyEvent> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InotifyEvent =
                    InotifyEvent(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: InotifyEvent) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.wd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.mask)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cookie)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InotifyEvent =
                    InotifyEvent(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InotifyEvent) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.wd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.mask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cookie)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.len)
                }
            }
    }
}

public open class FanotifyResponse(
    public var fd: CInt,
    public var response: U32,
) {
    public open fun clone(): FanotifyResponse = FanotifyResponse(fd, response)

    public companion object {
        public fun typeLayout(): TypeLayout<FanotifyResponse> =
            object : TypeLayout<FanotifyResponse> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FanotifyResponse =
                    FanotifyResponse(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FanotifyResponse) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.fd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.response)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanotifyResponse =
                    FanotifyResponse(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanotifyResponse) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.fd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.response)
                }
            }
    }
}

public open class FanotifyEventInfoHeader(
    public var infoType: U8,
    public var pad: U8,
    public var len: U16,
) {
    public open fun clone(): FanotifyEventInfoHeader = FanotifyEventInfoHeader(infoType, pad, len)

    public companion object {
        public fun typeLayout(): TypeLayout<FanotifyEventInfoHeader> =
            object : TypeLayout<FanotifyEventInfoHeader> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FanotifyEventInfoHeader =
                    FanotifyEventInfoHeader(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FanotifyEventInfoHeader) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.infoType)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.pad)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanotifyEventInfoHeader =
                    FanotifyEventInfoHeader(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanotifyEventInfoHeader) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.infoType)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.pad)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.len)
                }
            }
    }
}

public open class FanotifyEventInfoFid(
    public var hdr: FanotifyEventInfoHeader,
    public var fsid: KernelFsidT,
    public var handle: UByteArray,
) {
    public open fun clone(): FanotifyEventInfoFid = FanotifyEventInfoFid(hdr, fsid, handle)

    public companion object {
        public fun typeLayout(): TypeLayout<FanotifyEventInfoFid> =
            object : TypeLayout<FanotifyEventInfoFid> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): FanotifyEventInfoFid =
                    FanotifyEventInfoFid(
                        (FanotifyEventInfoHeader.typeLayout()).readNative(addr),
                        (CAnonymousKernelFsidT.typeLayout()).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: FanotifyEventInfoFid) {
                    (FanotifyEventInfoHeader.typeLayout()).writeNative(addr, value.hdr)
                    (CAnonymousKernelFsidT.typeLayout()).writeNative(addr + 4L, value.fsid)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 12L, value.handle)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanotifyEventInfoFid =
                    FanotifyEventInfoFid(
                        (FanotifyEventInfoHeader.typeLayout()).readBytes(buf, offset),
                        (CAnonymousKernelFsidT.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanotifyEventInfoFid) {
                    (FanotifyEventInfoHeader.typeLayout()).writeBytes(buf, offset, value.hdr)
                    (CAnonymousKernelFsidT.typeLayout()).writeBytes(buf, offset + 4, value.fsid)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 12, value.handle)
                }
            }
    }
}

public open class SockaddrVm(
    public var svmFamily: SaFamilyT,
    public var svmReserved1: CUshort,
    public var svmPort: CUint,
    public var svmCid: CUint,
    public var svmZero: UByteArray,
) {
    public open fun clone(): SockaddrVm = SockaddrVm(svmFamily, svmReserved1, svmPort, svmCid, svmZero)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrVm> =
            object : TypeLayout<SockaddrVm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrVm) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.svmFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.svmPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 12L, value.svmZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrVm) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.svmFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.svmPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 12, value.svmZero)
                }
            }
    }
}

public open class SockExtendedErr(
    public var eeErrno: UInt,
    public var eeOrigin: UByte,
    public var eeType: UByte,
    public var eeCode: UByte,
    public var eePad: UByte,
    public var eeInfo: UInt,
    public var eeData: UInt,
) {
    public open fun clone(): SockExtendedErr = SockExtendedErr(eeErrno, eeOrigin, eeType, eeCode, eePad, eeInfo, eeData)

    public companion object {
        public fun typeLayout(): TypeLayout<SockExtendedErr> =
            object : TypeLayout<SockExtendedErr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockExtendedErr =
                    SockExtendedErr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockExtendedErr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.eeErrno)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.eeOrigin)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.eeType)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.eeCode)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.eePad)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.eeInfo)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.eeData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockExtendedErr =
                    SockExtendedErr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockExtendedErr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.eeErrno)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.eeOrigin)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.eeType)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.eeCode)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.eePad)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.eeInfo)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.eeData)
                }
            }
    }
}

// linux/seccomp.h
public open class SeccompData(
    public var nr: CInt,
    public var arch: U32,
    public var instructionPointer: U64,
    public var args: ULongArray,
) {
    public open fun clone(): SeccompData = SeccompData(nr, arch, instructionPointer, args)

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompData> =
            object : TypeLayout<SeccompData> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): SeccompData =
                    SeccompData(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uLongArray(6)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SeccompData) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.nr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.arch)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.instructionPointer)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeNative(addr + 16L, value.args)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompData =
                    SeccompData(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uLongArray(6)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompData) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.nr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.arch)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.instructionPointer)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeBytes(buf, offset + 16, value.args)
                }
            }
    }
}

public open class SeccompNotifSizes(
    public var seccompNotif: U16,
    public var seccompNotifResp: U16,
    public var seccompData: U16,
) {
    public open fun clone(): SeccompNotifSizes = SeccompNotifSizes(seccompNotif, seccompNotifResp, seccompData)

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifSizes> =
            object : TypeLayout<SeccompNotifSizes> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): SeccompNotifSizes =
                    SeccompNotifSizes(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifSizes) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.seccompNotif)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.seccompNotifResp)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.seccompData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifSizes =
                    SeccompNotifSizes(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifSizes) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.seccompNotif)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.seccompNotifResp)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.seccompData)
                }
            }
    }
}

public open class SeccompNotif(
    public var id: U64,
    public var pid: U32,
    public var flags: U32,
    public var data: SeccompData,
) {
    public open fun clone(): SeccompNotif = SeccompNotif(id, pid, flags, data)

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotif> =
            object : TypeLayout<SeccompNotif> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): SeccompNotif =
                    SeccompNotif(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (SeccompData.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotif) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pid)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.flags)
                    (SeccompData.typeLayout()).writeNative(addr + 16L, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotif =
                    SeccompNotif(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (SeccompData.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotif) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.flags)
                    (SeccompData.typeLayout()).writeBytes(buf, offset + 16, value.data)
                }
            }
    }
}

public open class SeccompNotifResp(
    public var id: U64,
    public var value: S64,
    public var error: S32,
    public var flags: U32,
) {
    public open fun clone(): SeccompNotifResp = SeccompNotifResp(id, value, error, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifResp> =
            object : TypeLayout<SeccompNotifResp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SeccompNotifResp =
                    SeccompNotifResp(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifResp) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.value)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.error)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifResp =
                    SeccompNotifResp(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifResp) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.value)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.error)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.flags)
                }
            }
    }
}

public open class SeccompNotifAddfd(
    public var id: U64,
    public var flags: U32,
    public var srcfd: U32,
    public var newfd: U32,
    public var newfdFlags: U32,
) {
    public open fun clone(): SeccompNotifAddfd = SeccompNotifAddfd(id, flags, srcfd, newfd, newfdFlags)

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifAddfd> =
            object : TypeLayout<SeccompNotifAddfd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SeccompNotifAddfd =
                    SeccompNotifAddfd(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifAddfd) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.srcfd)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.newfd)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.newfdFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifAddfd =
                    SeccompNotifAddfd(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifAddfd) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.srcfd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.newfd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.newfdFlags)
                }
            }
    }
}

public open class In6Ifreq(
    public var ifr6Addr: In6Addr,
    public var ifr6Prefixlen: UInt,
    public var ifr6Ifindex: CInt,
) {
    public open fun clone(): In6Ifreq = In6Ifreq(ifr6Addr, ifr6Prefixlen, ifr6Ifindex)

    public companion object
}

public open class OpenHow(
    public var flags: U64,
    public var mode: U64,
    public var resolve: U64,
) {
    public open fun clone(): OpenHow = OpenHow(flags, mode, resolve)

    public companion object {
        public fun typeLayout(): TypeLayout<OpenHow> =
            object : TypeLayout<OpenHow> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): OpenHow =
                    OpenHow(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: OpenHow) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.mode)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.resolve)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenHow =
                    OpenHow(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenHow) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.mode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.resolve)
                }
            }
    }
}

// linux/ptpClock.h
public open class PtpClockTime(
    public var sec: S64,
    public var nsec: U32,
    public var reserved: U32,
) {
    public open fun clone(): PtpClockTime = PtpClockTime(sec, nsec, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpClockTime> =
            object : TypeLayout<PtpClockTime> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): PtpClockTime =
                    PtpClockTime(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: PtpClockTime) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sec)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nsec)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpClockTime =
                    PtpClockTime(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpClockTime) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nsec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.reserved)
                }
            }
    }
}

public open class PtpExttsRequest(
    public var index: CUint,
    public var flags: CUint,
    public var rsv: UIntArray,
) {
    public open fun clone(): PtpExttsRequest = PtpExttsRequest(index, flags, rsv)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpExttsRequest> =
            object : TypeLayout<PtpExttsRequest> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): PtpExttsRequest =
                    PtpExttsRequest(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uIntArray(2)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PtpExttsRequest) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.flags)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeNative(addr + 8L, value.rsv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpExttsRequest =
                    PtpExttsRequest(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uIntArray(2)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpExttsRequest) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.flags)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeBytes(buf, offset + 8, value.rsv)
                }
            }
    }
}

public open class PtpSysOffsetExtended(
    public var nSamples: CUint,
    public var clockid: KernelClockidT,
    public var rsv: UIntArray,
    public var ts: List<List<PtpClockTime>>,
) {
    public open fun clone(): PtpSysOffsetExtended = PtpSysOffsetExtended(nSamples, clockid, rsv, ts)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpSysOffsetExtended> =
            object : TypeLayout<PtpSysOffsetExtended> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((16L + ((PTP_MAX_SAMPLES.toInt()).toLong() * (fixedListTypeLayout(PtpClockTime.typeLayout(), 3)).strideBytes)), 8L)
                override fun readNative(addr: Long): PtpSysOffsetExtended =
                    PtpSysOffsetExtended(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uIntArray(2)).readNative(addr + 8L),
                        (fixedListTypeLayout(fixedListTypeLayout(PtpClockTime.typeLayout(), 3), (PTP_MAX_SAMPLES.toInt()).toInt())).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: PtpSysOffsetExtended) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nSamples)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.clockid)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeNative(addr + 8L, value.rsv)
                    (fixedListTypeLayout(fixedListTypeLayout(PtpClockTime.typeLayout(), 3), (PTP_MAX_SAMPLES.toInt()).toInt())).writeNative(addr + 16L, value.ts)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpSysOffsetExtended =
                    PtpSysOffsetExtended(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uIntArray(2)).readBytes(buf, offset + 8),
                        (fixedListTypeLayout(fixedListTypeLayout(PtpClockTime.typeLayout(), 3), (PTP_MAX_SAMPLES.toInt()).toInt())).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpSysOffsetExtended) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nSamples)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.clockid)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeBytes(buf, offset + 8, value.rsv)
                    (fixedListTypeLayout(fixedListTypeLayout(PtpClockTime.typeLayout(), 3), (PTP_MAX_SAMPLES.toInt()).toInt())).writeBytes(buf, offset + 16, value.ts)
                }
            }
    }
}

public open class PtpSysOffsetPrecise(
    public var device: PtpClockTime,
    public var sysRealtime: PtpClockTime,
    public var sysMonoraw: PtpClockTime,
    public var rsv: UIntArray,
) {
    public open fun clone(): PtpSysOffsetPrecise = PtpSysOffsetPrecise(device, sysRealtime, sysMonoraw, rsv)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpSysOffsetPrecise> =
            object : TypeLayout<PtpSysOffsetPrecise> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): PtpSysOffsetPrecise =
                    PtpSysOffsetPrecise(
                        (PtpClockTime.typeLayout()).readNative(addr),
                        (PtpClockTime.typeLayout()).readNative(addr + 16L),
                        (PtpClockTime.typeLayout()).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: PtpSysOffsetPrecise) {
                    (PtpClockTime.typeLayout()).writeNative(addr, value.device)
                    (PtpClockTime.typeLayout()).writeNative(addr + 16L, value.sysRealtime)
                    (PtpClockTime.typeLayout()).writeNative(addr + 32L, value.sysMonoraw)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr + 48L, value.rsv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpSysOffsetPrecise =
                    PtpSysOffsetPrecise(
                        (PtpClockTime.typeLayout()).readBytes(buf, offset),
                        (PtpClockTime.typeLayout()).readBytes(buf, offset + 16),
                        (PtpClockTime.typeLayout()).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpSysOffsetPrecise) {
                    (PtpClockTime.typeLayout()).writeBytes(buf, offset, value.device)
                    (PtpClockTime.typeLayout()).writeBytes(buf, offset + 16, value.sysRealtime)
                    (PtpClockTime.typeLayout()).writeBytes(buf, offset + 32, value.sysMonoraw)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset + 48, value.rsv)
                }
            }
    }
}

public open class PtpExttsEvent(
    public var t: PtpClockTime,
    public var index: CUint,
    public var flags: CUint,
    public var rsv: UIntArray,
) {
    public open fun clone(): PtpExttsEvent = PtpExttsEvent(t, index, flags, rsv)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpExttsEvent> =
            object : TypeLayout<PtpExttsEvent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): PtpExttsEvent =
                    PtpExttsEvent(
                        (PtpClockTime.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uIntArray(2)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: PtpExttsEvent) {
                    (PtpClockTime.typeLayout()).writeNative(addr, value.t)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.flags)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeNative(addr + 24L, value.rsv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpExttsEvent =
                    PtpExttsEvent(
                        (PtpClockTime.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uIntArray(2)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpExttsEvent) {
                    (PtpClockTime.typeLayout()).writeBytes(buf, offset, value.t)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.flags)
                    (PrimitiveArrayLayouts.uIntArray(2)).writeBytes(buf, offset + 24, value.rsv)
                }
            }
    }
}

// linux/wireless.h
public open class IwParam(
    public var value: S32,
    public var fixed: U8,
    public var disabled: U8,
    public var flags: U16,
) {
    public open fun clone(): IwParam = IwParam(value, fixed, disabled, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<IwParam> =
            object : TypeLayout<IwParam> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): IwParam =
                    IwParam(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: IwParam) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.fixed)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.disabled)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwParam =
                    IwParam(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwParam) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.fixed)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.disabled)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.flags)
                }
            }
    }
}

public open class IwPoint internal constructor(
    pointerCtorArg: MutPtr,
    public var length: U16,
    public var flags: U16,
) {
    public var pointer: MutPtr = pointerCtorArg
        internal set

    public open fun clone(): IwPoint = IwPoint(pointer, length, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<IwPoint> =
            object : TypeLayout<IwPoint> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): IwPoint =
                    IwPoint(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: IwPoint) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.pointer)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.length)
                    (PrimitiveLayouts.U16).writeNative(addr + 10L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwPoint =
                    IwPoint(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwPoint) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.pointer)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.length)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 10, value.flags)
                }
            }

        public operator fun invoke(
            pointer: MutPtr,
            length: U16,
            flags: U16,
        ): IwPoint = IwPoint(
            pointerCtorArg = pointer,
            length = length,
            flags = flags,
        )
    }
}

public open class IwFreq(
    public var m: S32,
    public var e: S16,
    public var i: U8,
    public var flags: U8,
) {
    public open fun clone(): IwFreq = IwFreq(m, e, i, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<IwFreq> =
            object : TypeLayout<IwFreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): IwFreq =
                    IwFreq(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                    )
                override fun writeNative(addr: Long, value: IwFreq) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.m)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.e)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.i)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwFreq =
                    IwFreq(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwFreq) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.m)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.e)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.i)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.flags)
                }
            }
    }
}

public open class IwQuality(
    public var qual: U8,
    public var level: U8,
    public var noise: U8,
    public var updated: U8,
) {
    public open fun clone(): IwQuality = IwQuality(qual, level, noise, updated)

    public companion object {
        public fun typeLayout(): TypeLayout<IwQuality> =
            object : TypeLayout<IwQuality> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): IwQuality =
                    IwQuality(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: IwQuality) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.qual)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.level)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.noise)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.updated)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwQuality =
                    IwQuality(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwQuality) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.qual)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.level)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.noise)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.updated)
                }
            }
    }
}

public open class IwDiscarded(
    public var nwid: U32,
    public var code: U32,
    public var fragment: U32,
    public var retries: U32,
    public var pubmisc: U32,
) {
    public open fun clone(): IwDiscarded = IwDiscarded(nwid, code, fragment, retries, pubmisc)

    public companion object {
        public fun typeLayout(): TypeLayout<IwDiscarded> =
            object : TypeLayout<IwDiscarded> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): IwDiscarded =
                    IwDiscarded(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: IwDiscarded) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nwid)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.code)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.fragment)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.retries)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pubmisc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwDiscarded =
                    IwDiscarded(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwDiscarded) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nwid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.code)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.fragment)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.retries)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pubmisc)
                }
            }
    }
}

public open class IwMissed(
    public var beacon: U32,
) {
    public open fun clone(): IwMissed = IwMissed(beacon)

    public companion object {
        public fun typeLayout(): TypeLayout<IwMissed> =
            object : TypeLayout<IwMissed> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): IwMissed =
                    IwMissed(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: IwMissed) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.beacon)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwMissed =
                    IwMissed(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwMissed) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.beacon)
                }
            }
    }
}

public open class IwScanReq(
    public var scanType: U8,
    public var essidLen: U8,
    public var numChannels: U8,
    public var flags: U8,
    public var bssid: Sockaddr,
    public var essid: UByteArray,
    public var minChannelTime: U32,
    public var maxChannelTime: U32,
    public var channelList: List<IwFreq>,
) {
    public open fun clone(): IwScanReq = IwScanReq(scanType, essidLen, numChannels, flags, bssid, essid, minChannelTime, maxChannelTime, channelList)

    public companion object
}

public open class IwEncodeExt(
    public var extFlags: U32,
    public var txSeq: UByteArray,
    public var rxSeq: UByteArray,
    public var addr: Sockaddr,
    public var alg: U16,
    public var keyLen: U16,
    public var key: UByteArray,
) {
    public open fun clone(): IwEncodeExt = IwEncodeExt(extFlags, txSeq, rxSeq, addr, alg, keyLen, key)

    public companion object
}

public open class IwPmksa(
    public var cmd: U32,
    public var bssid: Sockaddr,
    public var pmkid: UByteArray,
) {
    public open fun clone(): IwPmksa = IwPmksa(cmd, bssid, pmkid)

    public companion object
}

public open class IwPmkidCand(
    public var flags: U32,
    public var index: U32,
    public var bssid: Sockaddr,
) {
    public open fun clone(): IwPmkidCand = IwPmkidCand(flags, index, bssid)

    public companion object
}

public open class IwStatistics(
    public var status: U16,
    public var qual: IwQuality,
    public var discard: IwDiscarded,
    public var miss: IwMissed,
) {
    public open fun clone(): IwStatistics = IwStatistics(status, qual, discard, miss)

    public companion object {
        public fun typeLayout(): TypeLayout<IwStatistics> =
            object : TypeLayout<IwStatistics> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): IwStatistics =
                    IwStatistics(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (IwQuality.typeLayout()).readNative(addr + 2L),
                        (IwDiscarded.typeLayout()).readNative(addr + 8L),
                        (IwMissed.typeLayout()).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: IwStatistics) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.status)
                    (IwQuality.typeLayout()).writeNative(addr + 2L, value.qual)
                    (IwDiscarded.typeLayout()).writeNative(addr + 8L, value.discard)
                    (IwMissed.typeLayout()).writeNative(addr + 28L, value.miss)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwStatistics =
                    IwStatistics(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (IwQuality.typeLayout()).readBytes(buf, offset + 2),
                        (IwDiscarded.typeLayout()).readBytes(buf, offset + 8),
                        (IwMissed.typeLayout()).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwStatistics) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.status)
                    (IwQuality.typeLayout()).writeBytes(buf, offset + 2, value.qual)
                    (IwDiscarded.typeLayout()).writeBytes(buf, offset + 8, value.discard)
                    (IwMissed.typeLayout()).writeBytes(buf, offset + 28, value.miss)
                }
            }
    }
}

public open class IwRange(
    public var throughput: U32,
    public var minNwid: U32,
    public var maxNwid: U32,
    public var oldNumChannels: U16,
    public var oldNumFrequency: U8,
    public var scanCapa: U8,
    public var eventCapa: UIntArray,
    public var sensitivity: S32,
    public var maxQual: IwQuality,
    public var avgQual: IwQuality,
    public var numBitrates: U8,
    public var bitrate: IntArray,
    public var minRts: S32,
    public var maxRts: S32,
    public var minFrag: S32,
    public var maxFrag: S32,
    public var minPmp: S32,
    public var maxPmp: S32,
    public var minPmt: S32,
    public var maxPmt: S32,
    public var pmpFlags: U16,
    public var pmtFlags: U16,
    public var pmCapa: U16,
    public var encodingSize: UShortArray,
    public var numEncodingSizes: U8,
    public var maxEncodingTokens: U8,
    public var encodingLoginIndex: U8,
    public var txpowerCapa: U16,
    public var numTxpower: U8,
    public var txpower: IntArray,
    public var weVersionCompiled: U8,
    public var weVersionSource: U8,
    public var retryCapa: U16,
    public var retryFlags: U16,
    public var rTimeFlags: U16,
    public var minRetry: S32,
    public var maxRetry: S32,
    public var minRTime: S32,
    public var maxRTime: S32,
    public var numChannels: U16,
    public var numFrequency: U8,
    public var freq: List<IwFreq>,
    public var encCapa: U32,
) {
    public open fun clone(): IwRange = IwRange(throughput, minNwid, maxNwid, oldNumChannels, oldNumFrequency, scanCapa, eventCapa, sensitivity, maxQual, avgQual, numBitrates, bitrate, minRts, maxRts, minFrag, maxFrag, minPmp, maxPmp, minPmt, maxPmt, pmpFlags, pmtFlags, pmCapa, encodingSize, numEncodingSizes, maxEncodingTokens, encodingLoginIndex, txpowerCapa, numTxpower, txpower, weVersionCompiled, weVersionSource, retryCapa, retryFlags, rTimeFlags, minRetry, maxRetry, minRTime, maxRTime, numChannels, numFrequency, freq, encCapa)

    public companion object {
        public fun typeLayout(): TypeLayout<IwRange> =
            object : TypeLayout<IwRange> {
                override val alignBytes: Long = 4L
                private val off12: Long = alignUpBytes((56L + ((IW_MAX_BITRATES).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                private val off13: Long = alignUpBytes((off12 + 4L), 4L)
                private val off14: Long = alignUpBytes((off13 + 4L), 4L)
                private val off15: Long = alignUpBytes((off14 + 4L), 4L)
                private val off16: Long = alignUpBytes((off15 + 4L), 4L)
                private val off17: Long = alignUpBytes((off16 + 4L), 4L)
                private val off18: Long = alignUpBytes((off17 + 4L), 4L)
                private val off19: Long = alignUpBytes((off18 + 4L), 4L)
                private val off20: Long = alignUpBytes((off19 + 4L), 2L)
                private val off21: Long = alignUpBytes((off20 + 2L), 2L)
                private val off22: Long = alignUpBytes((off21 + 2L), 2L)
                private val off23: Long = alignUpBytes((off22 + 2L), 2L)
                private val off24: Long = alignUpBytes((off23 + ((IW_MAX_ENCODING_SIZES).toLong() * (PrimitiveLayouts.U16).strideBytes)), 1L)
                private val off25: Long = alignUpBytes((off24 + 1L), 1L)
                private val off26: Long = alignUpBytes((off25 + 1L), 1L)
                private val off27: Long = alignUpBytes((off26 + 1L), 2L)
                private val off28: Long = alignUpBytes((off27 + 2L), 1L)
                private val off29: Long = alignUpBytes((off28 + 1L), 4L)
                private val off30: Long = alignUpBytes((off29 + ((IW_MAX_TXPOWER).toLong() * (PrimitiveLayouts.I32).strideBytes)), 1L)
                private val off31: Long = alignUpBytes((off30 + 1L), 1L)
                private val off32: Long = alignUpBytes((off31 + 1L), 2L)
                private val off33: Long = alignUpBytes((off32 + 2L), 2L)
                private val off34: Long = alignUpBytes((off33 + 2L), 2L)
                private val off35: Long = alignUpBytes((off34 + 2L), 4L)
                private val off36: Long = alignUpBytes((off35 + 4L), 4L)
                private val off37: Long = alignUpBytes((off36 + 4L), 4L)
                private val off38: Long = alignUpBytes((off37 + 4L), 4L)
                private val off39: Long = alignUpBytes((off38 + 4L), 2L)
                private val off40: Long = alignUpBytes((off39 + 2L), 1L)
                private val off41: Long = alignUpBytes((off40 + 1L), 4L)
                private val off42: Long = alignUpBytes((off41 + ((IW_MAX_FREQUENCIES).toLong() * (IwFreq.typeLayout()).strideBytes)), 4L)
                override val sizeBytes: Long = alignUpBytes((off42 + 4L), 4L)
                override fun readNative(addr: Long): IwRange =
                    IwRange(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 14L),
                        (PrimitiveLayouts.U8).readNative(addr + 15L),
                        (PrimitiveArrayLayouts.uIntArray(6)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (IwQuality.typeLayout()).readNative(addr + 44L),
                        (IwQuality.typeLayout()).readNative(addr + 48L),
                        (PrimitiveLayouts.U8).readNative(addr + 52L),
                        (PrimitiveArrayLayouts.intArray((IW_MAX_BITRATES).toInt())).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + off12),
                        (PrimitiveLayouts.I32).readNative(addr + off13),
                        (PrimitiveLayouts.I32).readNative(addr + off14),
                        (PrimitiveLayouts.I32).readNative(addr + off15),
                        (PrimitiveLayouts.I32).readNative(addr + off16),
                        (PrimitiveLayouts.I32).readNative(addr + off17),
                        (PrimitiveLayouts.I32).readNative(addr + off18),
                        (PrimitiveLayouts.I32).readNative(addr + off19),
                        (PrimitiveLayouts.U16).readNative(addr + off20),
                        (PrimitiveLayouts.U16).readNative(addr + off21),
                        (PrimitiveLayouts.U16).readNative(addr + off22),
                        (PrimitiveArrayLayouts.uShortArray((IW_MAX_ENCODING_SIZES).toInt())).readNative(addr + off23),
                        (PrimitiveLayouts.U8).readNative(addr + off24),
                        (PrimitiveLayouts.U8).readNative(addr + off25),
                        (PrimitiveLayouts.U8).readNative(addr + off26),
                        (PrimitiveLayouts.U16).readNative(addr + off27),
                        (PrimitiveLayouts.U8).readNative(addr + off28),
                        (PrimitiveArrayLayouts.intArray((IW_MAX_TXPOWER).toInt())).readNative(addr + off29),
                        (PrimitiveLayouts.U8).readNative(addr + off30),
                        (PrimitiveLayouts.U8).readNative(addr + off31),
                        (PrimitiveLayouts.U16).readNative(addr + off32),
                        (PrimitiveLayouts.U16).readNative(addr + off33),
                        (PrimitiveLayouts.U16).readNative(addr + off34),
                        (PrimitiveLayouts.I32).readNative(addr + off35),
                        (PrimitiveLayouts.I32).readNative(addr + off36),
                        (PrimitiveLayouts.I32).readNative(addr + off37),
                        (PrimitiveLayouts.I32).readNative(addr + off38),
                        (PrimitiveLayouts.U16).readNative(addr + off39),
                        (PrimitiveLayouts.U8).readNative(addr + off40),
                        (fixedListTypeLayout(IwFreq.typeLayout(), (IW_MAX_FREQUENCIES).toInt())).readNative(addr + off41),
                        (PrimitiveLayouts.U32).readNative(addr + off42),
                    )
                override fun writeNative(addr: Long, value: IwRange) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.throughput)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.minNwid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.maxNwid)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.oldNumChannels)
                    (PrimitiveLayouts.U8).writeNative(addr + 14L, value.oldNumFrequency)
                    (PrimitiveLayouts.U8).writeNative(addr + 15L, value.scanCapa)
                    (PrimitiveArrayLayouts.uIntArray(6)).writeNative(addr + 16L, value.eventCapa)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.sensitivity)
                    (IwQuality.typeLayout()).writeNative(addr + 44L, value.maxQual)
                    (IwQuality.typeLayout()).writeNative(addr + 48L, value.avgQual)
                    (PrimitiveLayouts.U8).writeNative(addr + 52L, value.numBitrates)
                    (PrimitiveArrayLayouts.intArray((IW_MAX_BITRATES).toInt())).writeNative(addr + 56L, value.bitrate)
                    (PrimitiveLayouts.I32).writeNative(addr + off12, value.minRts)
                    (PrimitiveLayouts.I32).writeNative(addr + off13, value.maxRts)
                    (PrimitiveLayouts.I32).writeNative(addr + off14, value.minFrag)
                    (PrimitiveLayouts.I32).writeNative(addr + off15, value.maxFrag)
                    (PrimitiveLayouts.I32).writeNative(addr + off16, value.minPmp)
                    (PrimitiveLayouts.I32).writeNative(addr + off17, value.maxPmp)
                    (PrimitiveLayouts.I32).writeNative(addr + off18, value.minPmt)
                    (PrimitiveLayouts.I32).writeNative(addr + off19, value.maxPmt)
                    (PrimitiveLayouts.U16).writeNative(addr + off20, value.pmpFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + off21, value.pmtFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + off22, value.pmCapa)
                    (PrimitiveArrayLayouts.uShortArray((IW_MAX_ENCODING_SIZES).toInt())).writeNative(addr + off23, value.encodingSize)
                    (PrimitiveLayouts.U8).writeNative(addr + off24, value.numEncodingSizes)
                    (PrimitiveLayouts.U8).writeNative(addr + off25, value.maxEncodingTokens)
                    (PrimitiveLayouts.U8).writeNative(addr + off26, value.encodingLoginIndex)
                    (PrimitiveLayouts.U16).writeNative(addr + off27, value.txpowerCapa)
                    (PrimitiveLayouts.U8).writeNative(addr + off28, value.numTxpower)
                    (PrimitiveArrayLayouts.intArray((IW_MAX_TXPOWER).toInt())).writeNative(addr + off29, value.txpower)
                    (PrimitiveLayouts.U8).writeNative(addr + off30, value.weVersionCompiled)
                    (PrimitiveLayouts.U8).writeNative(addr + off31, value.weVersionSource)
                    (PrimitiveLayouts.U16).writeNative(addr + off32, value.retryCapa)
                    (PrimitiveLayouts.U16).writeNative(addr + off33, value.retryFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + off34, value.rTimeFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + off35, value.minRetry)
                    (PrimitiveLayouts.I32).writeNative(addr + off36, value.maxRetry)
                    (PrimitiveLayouts.I32).writeNative(addr + off37, value.minRTime)
                    (PrimitiveLayouts.I32).writeNative(addr + off38, value.maxRTime)
                    (PrimitiveLayouts.U16).writeNative(addr + off39, value.numChannels)
                    (PrimitiveLayouts.U8).writeNative(addr + off40, value.numFrequency)
                    (fixedListTypeLayout(IwFreq.typeLayout(), (IW_MAX_FREQUENCIES).toInt())).writeNative(addr + off41, value.freq)
                    (PrimitiveLayouts.U32).writeNative(addr + off42, value.encCapa)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwRange =
                    IwRange(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 15),
                        (PrimitiveArrayLayouts.uIntArray(6)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (IwQuality.typeLayout()).readBytes(buf, offset + 44),
                        (IwQuality.typeLayout()).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 52),
                        (PrimitiveArrayLayouts.intArray((IW_MAX_BITRATES).toInt())).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off12.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off13.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off14.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off15.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off16.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off17.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off18.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off19.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off20.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off21.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off22.toInt()),
                        (PrimitiveArrayLayouts.uShortArray((IW_MAX_ENCODING_SIZES).toInt())).readBytes(buf, offset + off23.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off24.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off25.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off26.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off27.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off28.toInt()),
                        (PrimitiveArrayLayouts.intArray((IW_MAX_TXPOWER).toInt())).readBytes(buf, offset + off29.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off30.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off31.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off32.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off33.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off34.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off35.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off36.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off37.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off38.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off39.toInt()),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off40.toInt()),
                        (fixedListTypeLayout(IwFreq.typeLayout(), (IW_MAX_FREQUENCIES).toInt())).readBytes(buf, offset + off41.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off42.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwRange) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.throughput)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.minNwid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.maxNwid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.oldNumChannels)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 14, value.oldNumFrequency)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 15, value.scanCapa)
                    (PrimitiveArrayLayouts.uIntArray(6)).writeBytes(buf, offset + 16, value.eventCapa)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.sensitivity)
                    (IwQuality.typeLayout()).writeBytes(buf, offset + 44, value.maxQual)
                    (IwQuality.typeLayout()).writeBytes(buf, offset + 48, value.avgQual)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 52, value.numBitrates)
                    (PrimitiveArrayLayouts.intArray((IW_MAX_BITRATES).toInt())).writeBytes(buf, offset + 56, value.bitrate)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off12.toInt(), value.minRts)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off13.toInt(), value.maxRts)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off14.toInt(), value.minFrag)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off15.toInt(), value.maxFrag)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off16.toInt(), value.minPmp)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off17.toInt(), value.maxPmp)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off18.toInt(), value.minPmt)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off19.toInt(), value.maxPmt)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off20.toInt(), value.pmpFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off21.toInt(), value.pmtFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off22.toInt(), value.pmCapa)
                    (PrimitiveArrayLayouts.uShortArray((IW_MAX_ENCODING_SIZES).toInt())).writeBytes(buf, offset + off23.toInt(), value.encodingSize)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off24.toInt(), value.numEncodingSizes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off25.toInt(), value.maxEncodingTokens)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off26.toInt(), value.encodingLoginIndex)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off27.toInt(), value.txpowerCapa)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off28.toInt(), value.numTxpower)
                    (PrimitiveArrayLayouts.intArray((IW_MAX_TXPOWER).toInt())).writeBytes(buf, offset + off29.toInt(), value.txpower)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off30.toInt(), value.weVersionCompiled)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off31.toInt(), value.weVersionSource)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off32.toInt(), value.retryCapa)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off33.toInt(), value.retryFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off34.toInt(), value.rTimeFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off35.toInt(), value.minRetry)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off36.toInt(), value.maxRetry)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off37.toInt(), value.minRTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off38.toInt(), value.maxRTime)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off39.toInt(), value.numChannels)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off40.toInt(), value.numFrequency)
                    (fixedListTypeLayout(IwFreq.typeLayout(), (IW_MAX_FREQUENCIES).toInt())).writeBytes(buf, offset + off41.toInt(), value.freq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off42.toInt(), value.encCapa)
                }
            }
    }
}

public open class IwPrivArgs(
    public var cmd: U32,
    public var setArgs: U16,
    public var getArgs: U16,
    public var name: UByteArray,
) {
    public open fun clone(): IwPrivArgs = IwPrivArgs(cmd, setArgs, getArgs, name)

    public companion object {
        public fun typeLayout(): TypeLayout<IwPrivArgs> =
            object : TypeLayout<IwPrivArgs> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes((8L + ((IFNAMSIZ).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override fun readNative(addr: Long): IwPrivArgs =
                    IwPrivArgs(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IwPrivArgs) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.setArgs)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.getArgs)
                    (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).writeNative(addr + 8L, value.name)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IwPrivArgs =
                    IwPrivArgs(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IwPrivArgs) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.setArgs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.getArgs)
                    (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).writeBytes(buf, offset + 8, value.name)
                }
            }
    }
}

// #include <linux/eventpoll.h>
public open class EpollParams(
    public var busyPollUsecs: UInt,
    public var busyPollBudget: UShort,
    public var preferBusyPoll: UByte,
    public var pad: UByte,
) {
    public open fun clone(): EpollParams = EpollParams(busyPollUsecs, busyPollBudget, preferBusyPoll, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<EpollParams> =
            object : TypeLayout<EpollParams> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): EpollParams =
                    EpollParams(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                    )
                override fun writeNative(addr: Long, value: EpollParams) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.busyPollUsecs)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.busyPollBudget)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.preferBusyPoll)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EpollParams =
                    EpollParams(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EpollParams) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.busyPollUsecs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.busyPollBudget)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.preferBusyPoll)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.pad)
                }
            }
    }
}

public open class PthreadMutexattrT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadMutexattrT = PthreadMutexattrT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexattrT> =
            object : TypeLayout<PthreadMutexattrT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_MUTEXATTR_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEXATTR_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEXATTR_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEXATTR_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEXATTR_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class PthreadRwlockattrT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadRwlockattrT = PthreadRwlockattrT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockattrT> =
            object : TypeLayout<PthreadRwlockattrT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_RWLOCKATTR_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCKATTR_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCKATTR_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCKATTR_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCKATTR_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class PthreadCondattrT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout(),
) {
    public var size: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(size: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(size, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))

    public fun pointerLayout(): TypeLayout<PthreadCondattrT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PthreadCondattrT(ptr, layout) }, { it.size })

    public open fun clone(): PthreadCondattrT = PthreadCondattrT(size, fieldLayout)

    public companion object {
        public operator fun invoke(size: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): PthreadCondattrT = PthreadCondattrT(size, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): PthreadCondattrT = PthreadCondattrT(fieldStorage, fieldLayout)
    }
}

public open class PthreadBarrierattrT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout(),
) {
    public var size: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(size: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(size, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))

    public fun pointerLayout(): TypeLayout<PthreadBarrierattrT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PthreadBarrierattrT(ptr, layout) }, { it.size })

    public open fun clone(): PthreadBarrierattrT = PthreadBarrierattrT(size, fieldLayout)

    public companion object {
        public operator fun invoke(size: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): PthreadBarrierattrT = PthreadBarrierattrT(size, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): PthreadBarrierattrT = PthreadBarrierattrT(fieldStorage, fieldLayout)
    }
}

public open class FanotifyEventMetadata(
    public var eventLen: U32,
    public var vers: U8,
    public var reserved: U8,
    public var metadataLen: U16,
    public var mask: U64,
    public var fd: CInt,
    public var pid: CInt,
) {
    public open fun clone(): FanotifyEventMetadata = FanotifyEventMetadata(eventLen, vers, reserved, metadataLen, mask, fd, pid)

    public companion object {
        public fun typeLayout(): TypeLayout<FanotifyEventMetadata> =
            object : TypeLayout<FanotifyEventMetadata> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FanotifyEventMetadata =
                    FanotifyEventMetadata(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: FanotifyEventMetadata) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.eventLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.vers)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.reserved)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.metadataLen)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.mask)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.fd)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanotifyEventMetadata =
                    FanotifyEventMetadata(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanotifyEventMetadata) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.eventLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.vers)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.reserved)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.metadataLen)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.mask)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.fd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pid)
                }
            }
    }
}

// linux/ptpClock.h
public open class PtpSysOffset(
    public var nSamples: CUint,
    public var rsv: UIntArray,
    // Note(garando): replace length with `2 * PTP_MAX_SAMPLES + 1` when supported
    public var ts: List<PtpClockTime>,
) {
    public open fun clone(): PtpSysOffset = PtpSysOffset(nSamples, rsv, ts)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpSysOffset> =
            object : TypeLayout<PtpSysOffset> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 832L
                override fun readNative(addr: Long): PtpSysOffset =
                    PtpSysOffset(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 4L),
                        (fixedListTypeLayout(PtpClockTime.typeLayout(), 51)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: PtpSysOffset) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nSamples)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 4L, value.rsv)
                    (fixedListTypeLayout(PtpClockTime.typeLayout(), 51)).writeNative(addr + 16L, value.ts)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpSysOffset =
                    PtpSysOffset(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 4),
                        (fixedListTypeLayout(PtpClockTime.typeLayout(), 51)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpSysOffset) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nSamples)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 4, value.rsv)
                    (fixedListTypeLayout(PtpClockTime.typeLayout(), 51)).writeBytes(buf, offset + 16, value.ts)
                }
            }
    }
}

public open class PtpPinDesc(
    public var name: UByteArray,
    public var index: CUint,
    public var func: CUint,
    public var chan: CUint,
    public var rsv: UIntArray,
) {
    public open fun clone(): PtpPinDesc = PtpPinDesc(name, index, func, chan, rsv)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpPinDesc> =
            object : TypeLayout<PtpPinDesc> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): PtpPinDesc =
                    PtpPinDesc(
                        (PrimitiveArrayLayouts.uByteArray(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveLayouts.U32).readNative(addr + 68L),
                        (PrimitiveLayouts.U32).readNative(addr + 72L),
                        (PrimitiveArrayLayouts.uIntArray(5)).readNative(addr + 76L),
                    )
                override fun writeNative(addr: Long, value: PtpPinDesc) {
                    (PrimitiveArrayLayouts.uByteArray(64)).writeNative(addr, value.name)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.func)
                    (PrimitiveLayouts.U32).writeNative(addr + 72L, value.chan)
                    (PrimitiveArrayLayouts.uIntArray(5)).writeNative(addr + 76L, value.rsv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpPinDesc =
                    PtpPinDesc(
                        (PrimitiveArrayLayouts.uByteArray(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 68),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (PrimitiveArrayLayouts.uIntArray(5)).readBytes(buf, offset + 76),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpPinDesc) {
                    (PrimitiveArrayLayouts.uByteArray(64)).writeBytes(buf, offset, value.name)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.func)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.chan)
                    (PrimitiveArrayLayouts.uIntArray(5)).writeBytes(buf, offset + 76, value.rsv)
                }
            }
    }
}

public open class PtpClockCaps(
    public var maxAdj: CInt,
    public var nAlarm: CInt,
    public var nExtTs: CInt,
    public var nPerOut: CInt,
    public var pps: CInt,
    public var nPins: CInt,
    public var crossTimestamping: CInt,
    public var adjustPhase: CInt,
    public var maxPhaseAdj: CInt,
    public var rsv: IntArray,
) {
    public open fun clone(): PtpClockCaps = PtpClockCaps(maxAdj, nAlarm, nExtTs, nPerOut, pps, nPins, crossTimestamping, adjustPhase, maxPhaseAdj, rsv)

    public companion object {
        public fun typeLayout(): TypeLayout<PtpClockCaps> =
            object : TypeLayout<PtpClockCaps> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): PtpClockCaps =
                    PtpClockCaps(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.intArray(11)).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: PtpClockCaps) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.maxAdj)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.nAlarm)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.nExtTs)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.nPerOut)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pps)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.nPins)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.crossTimestamping)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.adjustPhase)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.maxPhaseAdj)
                    (PrimitiveArrayLayouts.intArray(11)).writeNative(addr + 36L, value.rsv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtpClockCaps =
                    PtpClockCaps(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.intArray(11)).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtpClockCaps) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.maxAdj)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.nAlarm)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.nExtTs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.nPerOut)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pps)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.nPins)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.crossTimestamping)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.adjustPhase)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.maxPhaseAdj)
                    (PrimitiveArrayLayouts.intArray(11)).writeBytes(buf, offset + 36, value.rsv)
                }
            }
    }
}

// linux/ifXdp.h
public open class SockaddrXdp(
    public var sxdpFamily: U16,
    public var sxdpFlags: U16,
    public var sxdpIfindex: U32,
    public var sxdpQueueId: U32,
    public var sxdpSharedUmemFd: U32,
) {
    public open fun clone(): SockaddrXdp = SockaddrXdp(sxdpFamily, sxdpFlags, sxdpIfindex, sxdpQueueId, sxdpSharedUmemFd)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrXdp> =
            object : TypeLayout<SockaddrXdp> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrXdp =
                    SockaddrXdp(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrXdp) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sxdpFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sxdpFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.sxdpIfindex)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.sxdpQueueId)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.sxdpSharedUmemFd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrXdp =
                    SockaddrXdp(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrXdp) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sxdpFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sxdpFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.sxdpIfindex)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.sxdpQueueId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.sxdpSharedUmemFd)
                }
            }
    }
}

public open class XdpRingOffset(
    public var producer: U64,
    public var consumer: U64,
    public var desc: U64,
    public var flags: U64,
) {
    public open fun clone(): XdpRingOffset = XdpRingOffset(producer, consumer, desc, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpRingOffset> =
            object : TypeLayout<XdpRingOffset> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): XdpRingOffset =
                    XdpRingOffset(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: XdpRingOffset) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.producer)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.consumer)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.desc)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpRingOffset =
                    XdpRingOffset(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpRingOffset) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.producer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.consumer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.desc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.flags)
                }
            }
    }
}

public open class XdpMmapOffsets(
    public var rx: XdpRingOffset,
    public var tx: XdpRingOffset,
    public var fr: XdpRingOffset,
    public var cr: XdpRingOffset,
) {
    public open fun clone(): XdpMmapOffsets = XdpMmapOffsets(rx, tx, fr, cr)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpMmapOffsets> =
            object : TypeLayout<XdpMmapOffsets> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): XdpMmapOffsets =
                    XdpMmapOffsets(
                        (XdpRingOffset.typeLayout()).readNative(addr),
                        (XdpRingOffset.typeLayout()).readNative(addr + 32L),
                        (XdpRingOffset.typeLayout()).readNative(addr + 64L),
                        (XdpRingOffset.typeLayout()).readNative(addr + 96L),
                    )
                override fun writeNative(addr: Long, value: XdpMmapOffsets) {
                    (XdpRingOffset.typeLayout()).writeNative(addr, value.rx)
                    (XdpRingOffset.typeLayout()).writeNative(addr + 32L, value.tx)
                    (XdpRingOffset.typeLayout()).writeNative(addr + 64L, value.fr)
                    (XdpRingOffset.typeLayout()).writeNative(addr + 96L, value.cr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpMmapOffsets =
                    XdpMmapOffsets(
                        (XdpRingOffset.typeLayout()).readBytes(buf, offset),
                        (XdpRingOffset.typeLayout()).readBytes(buf, offset + 32),
                        (XdpRingOffset.typeLayout()).readBytes(buf, offset + 64),
                        (XdpRingOffset.typeLayout()).readBytes(buf, offset + 96),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpMmapOffsets) {
                    (XdpRingOffset.typeLayout()).writeBytes(buf, offset, value.rx)
                    (XdpRingOffset.typeLayout()).writeBytes(buf, offset + 32, value.tx)
                    (XdpRingOffset.typeLayout()).writeBytes(buf, offset + 64, value.fr)
                    (XdpRingOffset.typeLayout()).writeBytes(buf, offset + 96, value.cr)
                }
            }
    }
}

public open class XdpRingOffsetV1(
    public var producer: U64,
    public var consumer: U64,
    public var desc: U64,
) {
    public open fun clone(): XdpRingOffsetV1 = XdpRingOffsetV1(producer, consumer, desc)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpRingOffsetV1> =
            object : TypeLayout<XdpRingOffsetV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): XdpRingOffsetV1 =
                    XdpRingOffsetV1(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: XdpRingOffsetV1) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.producer)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.consumer)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.desc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpRingOffsetV1 =
                    XdpRingOffsetV1(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpRingOffsetV1) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.producer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.consumer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.desc)
                }
            }
    }
}

public open class XdpMmapOffsetsV1(
    public var rx: XdpRingOffsetV1,
    public var tx: XdpRingOffsetV1,
    public var fr: XdpRingOffsetV1,
    public var cr: XdpRingOffsetV1,
) {
    public open fun clone(): XdpMmapOffsetsV1 = XdpMmapOffsetsV1(rx, tx, fr, cr)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpMmapOffsetsV1> =
            object : TypeLayout<XdpMmapOffsetsV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): XdpMmapOffsetsV1 =
                    XdpMmapOffsetsV1(
                        (XdpRingOffsetV1.typeLayout()).readNative(addr),
                        (XdpRingOffsetV1.typeLayout()).readNative(addr + 24L),
                        (XdpRingOffsetV1.typeLayout()).readNative(addr + 48L),
                        (XdpRingOffsetV1.typeLayout()).readNative(addr + 72L),
                    )
                override fun writeNative(addr: Long, value: XdpMmapOffsetsV1) {
                    (XdpRingOffsetV1.typeLayout()).writeNative(addr, value.rx)
                    (XdpRingOffsetV1.typeLayout()).writeNative(addr + 24L, value.tx)
                    (XdpRingOffsetV1.typeLayout()).writeNative(addr + 48L, value.fr)
                    (XdpRingOffsetV1.typeLayout()).writeNative(addr + 72L, value.cr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpMmapOffsetsV1 =
                    XdpMmapOffsetsV1(
                        (XdpRingOffsetV1.typeLayout()).readBytes(buf, offset),
                        (XdpRingOffsetV1.typeLayout()).readBytes(buf, offset + 24),
                        (XdpRingOffsetV1.typeLayout()).readBytes(buf, offset + 48),
                        (XdpRingOffsetV1.typeLayout()).readBytes(buf, offset + 72),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpMmapOffsetsV1) {
                    (XdpRingOffsetV1.typeLayout()).writeBytes(buf, offset, value.rx)
                    (XdpRingOffsetV1.typeLayout()).writeBytes(buf, offset + 24, value.tx)
                    (XdpRingOffsetV1.typeLayout()).writeBytes(buf, offset + 48, value.fr)
                    (XdpRingOffsetV1.typeLayout()).writeBytes(buf, offset + 72, value.cr)
                }
            }
    }
}

public open class XdpUmemReg(
    public var addr: U64,
    public var len: U64,
    public var chunkSize: U32,
    public var headroom: U32,
    public var flags: U32,
    public var txMetadataLen: U32,
) {
    public open fun clone(): XdpUmemReg = XdpUmemReg(addr, len, chunkSize, headroom, flags, txMetadataLen)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpUmemReg> =
            object : TypeLayout<XdpUmemReg> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): XdpUmemReg =
                    XdpUmemReg(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: XdpUmemReg) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.addr)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.len)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.chunkSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.headroom)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.txMetadataLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpUmemReg =
                    XdpUmemReg(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpUmemReg) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.addr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.len)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.chunkSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.headroom)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.txMetadataLen)
                }
            }
    }
}

public open class XdpUmemRegV1(
    public var addr: U64,
    public var len: U64,
    public var chunkSize: U32,
    public var headroom: U32,
) {
    public open fun clone(): XdpUmemRegV1 = XdpUmemRegV1(addr, len, chunkSize, headroom)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpUmemRegV1> =
            object : TypeLayout<XdpUmemRegV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): XdpUmemRegV1 =
                    XdpUmemRegV1(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: XdpUmemRegV1) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.addr)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.len)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.chunkSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.headroom)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpUmemRegV1 =
                    XdpUmemRegV1(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpUmemRegV1) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.addr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.len)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.chunkSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.headroom)
                }
            }
    }
}

public open class XdpStatistics(
    public var rxDropped: U64,
    public var rxInvalidDescs: U64,
    public var txInvalidDescs: U64,
    public var rxRingFull: U64,
    public var rxFillRingEmptyDescs: U64,
    public var txRingEmptyDescs: U64,
) {
    public open fun clone(): XdpStatistics = XdpStatistics(rxDropped, rxInvalidDescs, txInvalidDescs, rxRingFull, rxFillRingEmptyDescs, txRingEmptyDescs)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpStatistics> =
            object : TypeLayout<XdpStatistics> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): XdpStatistics =
                    XdpStatistics(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: XdpStatistics) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rxDropped)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rxInvalidDescs)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.txInvalidDescs)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.rxRingFull)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.rxFillRingEmptyDescs)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.txRingEmptyDescs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpStatistics =
                    XdpStatistics(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpStatistics) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rxDropped)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rxInvalidDescs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.txInvalidDescs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.rxRingFull)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.rxFillRingEmptyDescs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.txRingEmptyDescs)
                }
            }
    }
}

public open class XdpStatisticsV1(
    public var rxDropped: U64,
    public var rxInvalidDescs: U64,
    public var txInvalidDescs: U64,
) {
    public open fun clone(): XdpStatisticsV1 = XdpStatisticsV1(rxDropped, rxInvalidDescs, txInvalidDescs)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpStatisticsV1> =
            object : TypeLayout<XdpStatisticsV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): XdpStatisticsV1 =
                    XdpStatisticsV1(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: XdpStatisticsV1) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rxDropped)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rxInvalidDescs)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.txInvalidDescs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpStatisticsV1 =
                    XdpStatisticsV1(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpStatisticsV1) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rxDropped)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rxInvalidDescs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.txInvalidDescs)
                }
            }
    }
}

public open class XdpOptions(
    public var flags: U32,
) {
    public open fun clone(): XdpOptions = XdpOptions(flags)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpOptions> =
            object : TypeLayout<XdpOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): XdpOptions =
                    XdpOptions(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: XdpOptions) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpOptions =
                    XdpOptions(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpOptions) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.flags)
                }
            }
    }
}

public open class XdpDesc(
    public var addr: U64,
    public var len: U32,
    public var options: U32,
) {
    public open fun clone(): XdpDesc = XdpDesc(addr, len, options)

    public companion object {
        public fun typeLayout(): TypeLayout<XdpDesc> =
            object : TypeLayout<XdpDesc> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): XdpDesc =
                    XdpDesc(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: XdpDesc) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.addr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.len)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.options)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XdpDesc =
                    XdpDesc(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XdpDesc) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.addr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.len)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.options)
                }
            }
    }
}

public open class XskTxMetadataCompletion(
    public var txTimestamp: U64,
) {
    public open fun clone(): XskTxMetadataCompletion = XskTxMetadataCompletion(txTimestamp)

    public companion object {
        public fun typeLayout(): TypeLayout<XskTxMetadataCompletion> =
            object : TypeLayout<XskTxMetadataCompletion> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): XskTxMetadataCompletion =
                    XskTxMetadataCompletion(
                        (PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: XskTxMetadataCompletion) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.txTimestamp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XskTxMetadataCompletion =
                    XskTxMetadataCompletion(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XskTxMetadataCompletion) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.txTimestamp)
                }
            }
    }
}

public open class XskTxMetadataRequest(
    public var csumStart: U16,
    public var csumOffset: U16,
) {
    public open fun clone(): XskTxMetadataRequest = XskTxMetadataRequest(csumStart, csumOffset)

    public companion object {
        public fun typeLayout(): TypeLayout<XskTxMetadataRequest> =
            object : TypeLayout<XskTxMetadataRequest> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): XskTxMetadataRequest =
                    XskTxMetadataRequest(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: XskTxMetadataRequest) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.csumStart)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.csumOffset)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XskTxMetadataRequest =
                    XskTxMetadataRequest(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XskTxMetadataRequest) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.csumStart)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.csumOffset)
                }
            }
    }
}

// linux/nsfs.h
public open class MntNsInfo(
    public var size: U32,
    public var nrMounts: U32,
    public var mntNsId: U64,
) {
    public open fun clone(): MntNsInfo = MntNsInfo(size, nrMounts, mntNsId)

    public companion object {
        public fun typeLayout(): TypeLayout<MntNsInfo> =
            object : TypeLayout<MntNsInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): MntNsInfo =
                    MntNsInfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: MntNsInfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.size)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nrMounts)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.mntNsId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MntNsInfo =
                    MntNsInfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MntNsInfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.size)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nrMounts)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.mntNsId)
                }
            }
    }
}

// linux/pidfd.h
// linux/uio.h
public open class DmabufCmsg(
    public var fragOffset: U64,
    public var fragSize: U32,
    public var fragToken: U32,
    public var dmabufId: U32,
    public var flags: U32,
) {
    public open fun clone(): DmabufCmsg = DmabufCmsg(fragOffset, fragSize, fragToken, dmabufId, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<DmabufCmsg> =
            object : TypeLayout<DmabufCmsg> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): DmabufCmsg =
                    DmabufCmsg(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: DmabufCmsg) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fragOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.fragSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.fragToken)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dmabufId)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DmabufCmsg =
                    DmabufCmsg(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DmabufCmsg) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fragOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.fragSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.fragToken)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dmabufId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.flags)
                }
            }
    }
}

public open class DmabufToken(
    public var tokenStart: U32,
    public var tokenCount: U32,
) {
    public open fun clone(): DmabufToken = DmabufToken(tokenStart, tokenCount)

    public companion object {
        public fun typeLayout(): TypeLayout<DmabufToken> =
            object : TypeLayout<DmabufToken> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): DmabufToken =
                    DmabufToken(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: DmabufToken) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tokenStart)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tokenCount)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DmabufToken =
                    DmabufToken(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DmabufToken) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tokenStart)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tokenCount)
                }
            }
    }
}

public open class SockaddrAlg(
    public var salgFamily: SaFamilyT,
    public var salgType: UByteArray,
    public var salgFeat: UInt,
    public var salgMask: UInt,
    public var salgName: UByteArray,
) {
    public open fun clone(): SockaddrAlg = SockaddrAlg(salgFamily, salgType, salgFeat, salgMask, salgName)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrAlg> =
            object : TypeLayout<SockaddrAlg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uByteArray(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrAlg) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.salgType)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.salgFeat)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeNative(addr + 24L, value.salgName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uByteArray(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrAlg) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.salgType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.salgFeat)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeBytes(buf, offset + 24, value.salgName)
                }
            }
    }
}

public open class PthreadCondT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadCondT = PthreadCondT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondT> =
            object : TypeLayout<PthreadCondT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_COND_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_COND_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadCondT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_COND_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_COND_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_COND_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class PthreadMutexT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadMutexT = PthreadMutexT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexT> =
            object : TypeLayout<PthreadMutexT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_MUTEX_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEX_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEX_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEX_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_MUTEX_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class PthreadRwlockT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadRwlockT = PthreadRwlockT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockT> =
            object : TypeLayout<PthreadRwlockT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_RWLOCK_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCK_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCK_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCK_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_RWLOCK_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class PthreadBarrierT(
    public var size: UByteArray,
) {
    public open fun clone(): PthreadBarrierT = PthreadBarrierT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadBarrierT> =
            object : TypeLayout<PthreadBarrierT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_BARRIER_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                override fun readNative(addr: Long): PthreadBarrierT =
                    PthreadBarrierT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIER_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadBarrierT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIER_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadBarrierT =
                    PthreadBarrierT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIER_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadBarrierT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIER_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class UinputSetup(
    public var id: InputId,
    public var name: UByteArray,
    public var ffEffectsMax: U32,
) {
    public open fun clone(): UinputSetup = UinputSetup(id, name, ffEffectsMax)

    public companion object {
        public fun typeLayout(): TypeLayout<UinputSetup> =
            object : TypeLayout<UinputSetup> {
                override val alignBytes: Long = 4L
                private val off2: Long = alignUpBytes((8L + ((UINPUT_MAX_NAME_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override val sizeBytes: Long = alignUpBytes((off2 + 4L), 4L)
                override fun readNative(addr: Long): UinputSetup =
                    UinputSetup(
                        (InputId.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: UinputSetup) {
                    (InputId.typeLayout()).writeNative(addr, value.id)
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeNative(addr + 8L, value.name)
                    (PrimitiveLayouts.U32).writeNative(addr + off2, value.ffEffectsMax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputSetup =
                    UinputSetup(
                        (InputId.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputSetup) {
                    (InputId.typeLayout()).writeBytes(buf, offset, value.id)
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeBytes(buf, offset + 8, value.name)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off2.toInt(), value.ffEffectsMax)
                }
            }
    }
}

public open class UinputUserDev(
    public var name: UByteArray,
    public var id: InputId,
    public var ffEffectsMax: U32,
    public var absmax: IntArray,
    public var absmin: IntArray,
    public var absfuzz: IntArray,
    public var absflat: IntArray,
) {
    public open fun clone(): UinputUserDev = UinputUserDev(name, id, ffEffectsMax, absmax, absmin, absfuzz, absflat)

    public companion object {
        public fun typeLayout(): TypeLayout<UinputUserDev> =
            object : TypeLayout<UinputUserDev> {
                override val alignBytes: Long = 4L
                private val off1: Long = alignUpBytes(((UINPUT_MAX_NAME_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes), 2L)
                private val off2: Long = alignUpBytes((off1 + 8L), 4L)
                private val off3: Long = alignUpBytes((off2 + 4L), 4L)
                private val off4: Long = alignUpBytes((off3 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                private val off5: Long = alignUpBytes((off4 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                private val off6: Long = alignUpBytes((off5 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                override val sizeBytes: Long = alignUpBytes((off6 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                override fun readNative(addr: Long): UinputUserDev =
                    UinputUserDev(
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readNative(addr),
                        (InputId.typeLayout()).readNative(addr + off1),
                        (PrimitiveLayouts.U32).readNative(addr + off2),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off4),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off5),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off6),
                    )
                override fun writeNative(addr: Long, value: UinputUserDev) {
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeNative(addr, value.name)
                    (InputId.typeLayout()).writeNative(addr + off1, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + off2, value.ffEffectsMax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off3, value.absmax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off4, value.absmin)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off5, value.absfuzz)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off6, value.absflat)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputUserDev =
                    UinputUserDev(
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readBytes(buf, offset),
                        (InputId.typeLayout()).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off4.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off5.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off6.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputUserDev) {
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeBytes(buf, offset, value.name)
                    (InputId.typeLayout()).writeBytes(buf, offset + off1.toInt(), value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off2.toInt(), value.ffEffectsMax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off3.toInt(), value.absmax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off4.toInt(), value.absmin)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off5.toInt(), value.absfuzz)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off6.toInt(), value.absflat)
                }
            }
    }
}

// x32 compatibility
// See https://sourceware.org/bugzilla/showBug.cgi?id=21279
public open class MqAttr(
    public var mqFlags: CLong,
    public var mqMaxmsg: CLong,
    public var mqMsgsize: CLong,
    public var mqCurmsgs: CLong,
    public var pad: LongArray,
) {
    public open fun clone(): MqAttr = MqAttr(mqFlags, mqMaxmsg, mqMsgsize, mqCurmsgs, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<MqAttr> =
            object : TypeLayout<MqAttr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): MqAttr =
                    MqAttr(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: MqAttr) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.mqFlags)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.mqMaxmsg)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.mqMsgsize)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.mqCurmsgs)
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr + 32L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MqAttr =
                    MqAttr(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MqAttr) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.mqFlags)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.mqMaxmsg)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.mqMsgsize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.mqCurmsgs)
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset + 32, value.pad)
                }
            }
    }
}

public open class HwtstampConfig(
    public var flags: CInt,
    public var txType: CInt,
    public var rxFilter: CInt,
) {
    public open fun clone(): HwtstampConfig = HwtstampConfig(flags, txType, rxFilter)

    public companion object {
        public fun typeLayout(): TypeLayout<HwtstampConfig> =
            object : TypeLayout<HwtstampConfig> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): HwtstampConfig =
                    HwtstampConfig(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: HwtstampConfig) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.flags)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.txType)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.rxFilter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): HwtstampConfig =
                    HwtstampConfig(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: HwtstampConfig) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.txType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.rxFilter)
                }
            }
    }
}

public open class SchedAttr(
    public var size: U32,
    public var schedPolicy: U32,
    public var schedFlags: U64,
    public var schedNice: S32,
    public var schedPriority: U32,
    public var schedRuntime: U64,
    public var schedDeadline: U64,
    public var schedPeriod: U64,
) {
    public open fun clone(): SchedAttr = SchedAttr(size, schedPolicy, schedFlags, schedNice, schedPriority, schedRuntime, schedDeadline, schedPeriod)

    public companion object {
        public fun typeLayout(): TypeLayout<SchedAttr> =
            object : TypeLayout<SchedAttr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): SchedAttr =
                    SchedAttr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: SchedAttr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.size)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.schedPolicy)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.schedFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.schedNice)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.schedPriority)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.schedRuntime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.schedDeadline)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.schedPeriod)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SchedAttr =
                    SchedAttr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SchedAttr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.size)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.schedPolicy)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.schedFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.schedNice)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.schedPriority)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.schedRuntime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.schedDeadline)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.schedPeriod)
                }
            }
    }
}

// linux/fcntl.h
public open class FileHandle(
    public var handleBytes: CUint,
    public var handleType: CInt,
    public var fHandle: UByteArray,
) {
    public open fun clone(): FileHandle = FileHandle(handleBytes, handleType, fHandle)

    public companion object {
        public fun typeLayout(): TypeLayout<FileHandle> =
            object : TypeLayout<FileHandle> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileHandle =
                    FileHandle(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FileHandle) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.handleBytes)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.handleType)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 8L, value.fHandle)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileHandle =
                    FileHandle(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileHandle) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.handleBytes)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.handleType)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 8, value.fHandle)
                }
            }
    }
}

// include/uapi/linux/rtnetlink.h
public open class Ifinfomsg(
    public var ifiFamily: CUchar,
    public var ifiPad: CUchar,
    public var ifiType: CUshort,
    public var ifiIndex: CInt,
    public var ifiFlags: CUint,
    public var ifiChange: CUint,
) {
    public open fun clone(): Ifinfomsg = Ifinfomsg(ifiFamily, ifiPad, ifiType, ifiIndex, ifiFlags, ifiChange)

    public companion object {
        public fun typeLayout(): TypeLayout<Ifinfomsg> =
            object : TypeLayout<Ifinfomsg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Ifinfomsg =
                    Ifinfomsg(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Ifinfomsg) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.ifiFamily)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.ifiPad)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.ifiType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ifiIndex)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.ifiFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.ifiChange)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ifinfomsg =
                    Ifinfomsg(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ifinfomsg) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.ifiFamily)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.ifiPad)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.ifiType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ifiIndex)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.ifiFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.ifiChange)
                }
            }
    }
}

public open class Rtattr(
    public var rtaLen: CUshort,
    public var rtaType: CUshort,
) {
    public open fun clone(): Rtattr = Rtattr(rtaLen, rtaType)

    public companion object {
        public fun typeLayout(): TypeLayout<Rtattr> =
            object : TypeLayout<Rtattr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Rtattr =
                    Rtattr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Rtattr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rtaLen)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.rtaType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rtattr =
                    Rtattr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rtattr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rtaLen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.rtaType)
                }
            }
    }
}

// netdb.h
public open class Netent internal constructor(
    nNameCtorArg: MutPtr,
    nAliasesCtorArg: MutPtr,
    public var nAddrtype: CInt,
    public var nNet: UInt,
) {
    public var nName: MutPtr = nNameCtorArg
        internal set

    public var nAliases: MutPtr = nAliasesCtorArg
        internal set

    public open fun clone(): Netent = Netent(nName, nAliases, nAddrtype, nNet)

    public companion object {
        public fun typeLayout(): TypeLayout<Netent> =
            object : TypeLayout<Netent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Netent =
                    Netent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: Netent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.nName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.nAliases)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.nAddrtype)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.nNet)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Netent =
                    Netent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Netent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.nName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.nAliases)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.nAddrtype)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.nNet)
                }
            }

        public operator fun invoke(
            nName: MutPtr,
            nAliases: MutPtr,
            nAddrtype: CInt,
            nNet: UInt,
        ): Netent = Netent(
            nNameCtorArg = nName,
            nAliasesCtorArg = nAliases,
            nAddrtype = nAddrtype,
            nNet = nNet,
        )
    }
}

// Note(1.0,deprecate): lfs binding to be removed

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public open class Fpos64T(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): Fpos64T = Fpos64T(data, marker)

    public companion object
}

public open class IwThrspy(
    public var addr: Sockaddr,
    public var qual: IwQuality,
    public var low: IwQuality,
    public var high: IwQuality,
) {
    public open fun clone(): IwThrspy = IwThrspy(addr, qual, low, high)

    public companion object
}

public open class IwMlme(
    public var cmd: U16,
    public var reasonCode: U16,
    public var addr: Sockaddr,
) {
    public open fun clone(): IwMlme = IwMlme(cmd, reasonCode, addr)

    public companion object
}

public open class IwMichaelmicfailure(
    public var flags: U32,
    public var srcAddr: Sockaddr,
    public var tsc: UByteArray,
) {
    public open fun clone(): IwMichaelmicfailure = IwMichaelmicfailure(flags, srcAddr, tsc)

    public companion object
}

public open class AfAlgIv(
    public var ivlen: UInt,
    public var iv: UByteArray,
) {
    public open fun clone(): AfAlgIv = AfAlgIv(ivlen, iv)

    public companion object {
        public fun typeLayout(): TypeLayout<AfAlgIv> =
            object : TypeLayout<AfAlgIv> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): AfAlgIv =
                    AfAlgIv(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: AfAlgIv) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ivlen)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 4L, value.iv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AfAlgIv =
                    AfAlgIv(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AfAlgIv) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ivlen)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 4, value.iv)
                }
            }
    }
}

// linux/netTstamp.h
public open class SockTxtime(
    public var clockid: ClockidT,
    public var flags: U32,
) {
    public open fun clone(): SockTxtime = SockTxtime(clockid, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<SockTxtime> =
            object : TypeLayout<SockTxtime> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SockTxtime =
                    SockTxtime(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SockTxtime) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.clockid)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockTxtime =
                    SockTxtime(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockTxtime) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.clockid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.flags)
                }
            }
    }
}

// linux/wireless.h
public open class IwreqData(
    public var name: UByteArray? = null,
    public var essid: IwPoint? = null,
    public var nwid: IwParam? = null,
    public var freq: IwFreq? = null,
    public var sens: IwParam? = null,
    public var bitrate: IwParam? = null,
    public var txpower: IwParam? = null,
    public var rts: IwParam? = null,
    public var frag: IwParam? = null,
    public var mode: U32? = null,
    public var retry: IwParam? = null,
    public var encoding: IwPoint? = null,
    public var power: IwParam? = null,
    public var qual: IwQuality? = null,
    public var apAddr: Sockaddr? = null,
    public var addr: Sockaddr? = null,
    public var param: IwParam? = null,
    public var data: IwPoint? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "iwreq_data").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

public open class IwEvent(
    public var len: U16,
    public var cmd: U16,
    public var u: IwreqData,
) {
    public open fun clone(): IwEvent = IwEvent(len, cmd, u)

    public companion object
}

public open class CAnonymousIwreq(
    public var ifrnName: UByteArray? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_iwreq").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

public open class Iwreq(
    public var ifrIfrn: CAnonymousIwreq,
    public var u: IwreqData,
) {
    public open fun clone(): Iwreq = Iwreq(ifrIfrn, u)

    public companion object
}

// linux/ptpClock.h
public open class CAnonymousPtpPeroutRequest1(
    public var start: PtpClockTime? = null,
    public var phase: PtpClockTime? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ptp_perout_request_1").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

public open class CAnonymousPtpPeroutRequest2(
    public var on: PtpClockTime? = null,
    public var rsv: UIntArray? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ptp_perout_request_2").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

public open class PtpPeroutRequest(
    public var anonymous1: CAnonymousPtpPeroutRequest1,
    public var period: PtpClockTime,
    public var index: CUint,
    public var flags: CUint,
    public var anonymous2: CAnonymousPtpPeroutRequest2,
) {
    public open fun clone(): PtpPeroutRequest = PtpPeroutRequest(anonymous1, period, index, flags, anonymous2)

    public companion object
}

// linux/ifXdp.h
public open class XskTxMetadata(
    public var flags: U64,
    public var xskTxMetadataUnion: CAnonymousXskTxMetadataUnion,
) {
    public open fun clone(): XskTxMetadata = XskTxMetadata(flags, xskTxMetadataUnion)

    public companion object
}

public open class CAnonymousXskTxMetadataUnion(
    public var request: XskTxMetadataRequest? = null,
    public var completion: XskTxMetadataCompletion? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_xsk_tx_metadata_union").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}
