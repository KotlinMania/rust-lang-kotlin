@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.unix.IFNAMSIZ
import io.github.kotlinmania.rustlang.unix.In6Addr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.bsd.SaFamilyT
import io.github.kotlinmania.rustlang.unix.bsd.Sockaddr
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.linuxlike.SIZEOF_PTHREAD_COND_T
import io.github.kotlinmania.rustlang.unix.linuxlike.SchedParam
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Timeval
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SIZEOF_PTHREAD_RWLOCKATTR_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.SIZEOF_PTHREAD_BARRIERATTR_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.SIZEOF_PTHREAD_CONDATTR_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.SIZEOF_PTHREAD_MUTEXATTR_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_BARRIER_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_MUTEX_T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_RWLOCK_T

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

// linux/cnProc.h
// uapi/linux/magic.h
// Most `* _SUPER_MAGIC` constants are defined at the `linuxLike` level; the
// following are only available on newer Linux versions than the versions
// currently used in CI in some configurations, so we define them here.
// These functions are not available on OpenHarmony
// LFS64 extensions
//
// * musl has 64-bit versions only so aliases the LFS64 symbols to the standard ones
public data class Dqblk(
    public var dqbBhardlimit: ULong = 0uL,
    public var dqbBsoftlimit: ULong = 0uL,
    public var dqbCurspace: ULong = 0uL,
    public var dqbIhardlimit: ULong = 0uL,
    public var dqbIsoftlimit: ULong = 0uL,
    public var dqbCurinodes: ULong = 0uL,
    public var dqbBtime: ULong = 0uL,
    public var dqbItime: ULong = 0uL,
    public var dqbValid: UInt = 0u,
) {
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

public class SignalfdSiginfo(
    public var ssiSigno: UInt = 0u,
    public var ssiErrno: Int = 0,
    public var ssiCode: Int = 0,
    public var ssiPid: UInt = 0u,
    public var ssiUid: UInt = 0u,
    public var ssiFd: Int = 0,
    public var ssiTid: UInt = 0u,
    public var ssiBand: UInt = 0u,
    public var ssiOverrun: UInt = 0u,
    public var ssiTrapno: UInt = 0u,
    public var ssiStatus: Int = 0,
    public var ssiInt: Int = 0,
    public var ssiPtr: ULong = 0uL,
    public var ssiUtime: ULong = 0uL,
    public var ssiStime: ULong = 0uL,
    public var ssiAddr: ULong = 0uL,
    public var ssiAddrLsb: UShort = 0u,
    public var pad2: UShort = 0u,
    public var ssiSyscall: Int = 0,
    public var ssiCallAddr: ULong = 0uL,
    public var ssiArch: UInt = 0u,
    public var pad: UByteArray = UByteArray(28),
) {
    public fun clone(): SignalfdSiginfo = SignalfdSiginfo(ssiSigno, ssiErrno, ssiCode, ssiPid, ssiUid, ssiFd, ssiTid, ssiBand, ssiOverrun, ssiTrapno, ssiStatus, ssiInt, ssiPtr, ssiUtime, ssiStime, ssiAddr, ssiAddrLsb, pad2, ssiSyscall, ssiCallAddr, ssiArch, pad)

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
public data class Msginfo(
    public var msgpool: CInt = 0,
    public var msgmap: CInt = 0,
    public var msgmax: CInt = 0,
    public var msgmnb: CInt = 0,
    public var msgmni: CInt = 0,
    public var msgssz: CInt = 0,
    public var msgtql: CInt = 0,
    public var msgseg: CUshort = 0u,
) {
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

public data class InputEvent(
    // Note(1.0): Change to the commented variant, see https://github.com/rust-lang/libc/pull/4148#discussionR1857511742
    public var time: Timeval = Timeval(),
    public var type: U16 = 0u,
    public var code: U16 = 0u,
    public var value: S32 = 0,
)

public data class InputId(
    public var bustype: U16 = 0u,
    public var vendor: U16 = 0u,
    public var product: U16 = 0u,
    public var version: U16 = 0u,
) {
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

public data class InputAbsinfo(
    public var value: S32 = 0,
    public var minimum: S32 = 0,
    public var maximum: S32 = 0,
    public var fuzz: S32 = 0,
    public var flat: S32 = 0,
    public var resolution: S32 = 0,
) {
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

public data class InputKeymapEntry(
    public var flags: U8 = 0u,
    public var len: U8 = 0u,
    public var index: U16 = 0u,
    public var keycode: U32 = 0u,
    public var scancode: UByteArray = UByteArray(32),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputKeymapEntry) return false
        if (this.flags != other.flags) return false
        if (this.len != other.len) return false
        if (this.index != other.index) return false
        if (this.keycode != other.keycode) return false
        if (!this.scancode.contentEquals(other.scancode)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.flags.hashCode()
        result = 31 * result + this.len.hashCode()
        result = 31 * result + this.index.hashCode()
        result = 31 * result + this.keycode.hashCode()
        result = 31 * result + this.scancode.contentHashCode()
        return result
    }

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

public data class InputMask(
    public var type: U32 = 0u,
    public var codesSize: U32 = 0u,
    public var codesPtr: U64 = 0uL,
) {
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

public data class FfReplay(
    public var length: U16 = 0u,
    public var delay: U16 = 0u,
) {
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

public data class FfTrigger(
    public var button: U16 = 0u,
    public var interval: U16 = 0u,
) {
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

public data class FfEnvelope(
    public var attackLength: U16 = 0u,
    public var attackLevel: U16 = 0u,
    public var fadeLength: U16 = 0u,
    public var fadeLevel: U16 = 0u,
) {
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

public data class FfConstantEffect(
    public var level: S16 = 0,
    public var envelope: FfEnvelope = FfEnvelope(),
) {
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

public data class FfRampEffect(
    public var startLevel: S16 = 0,
    public var endLevel: S16 = 0,
    public var envelope: FfEnvelope = FfEnvelope(),
) {
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

public data class FfConditionEffect(
    public var rightSaturation: U16 = 0u,
    public var leftSaturation: U16 = 0u,
    public var rightCoeff: S16 = 0,
    public var leftCoeff: S16 = 0,
    public var deadband: U16 = 0u,
    public var center: S16 = 0,
) {
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

public class FfPeriodicEffect internal constructor(
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

    public constructor() : this(
        waveform = 0u,
        period = 0u,
        magnitude = 0,
        offset = 0,
        phase = 0u,
        envelope = FfEnvelope(),
        customLen = 0u,
        customDataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): U16 = this.waveform

    public operator fun component2(): U16 = this.period

    public operator fun component3(): S16 = this.magnitude

    public operator fun component4(): S16 = this.offset

    public operator fun component5(): U16 = this.phase

    public operator fun component6(): FfEnvelope = this.envelope

    public operator fun component7(): U32 = this.customLen

    public operator fun component8(): MutPtr = this.customData

    public fun copy(waveform: U16 = this.waveform, period: U16 = this.period, magnitude: S16 = this.magnitude, offset: S16 = this.offset, phase: U16 = this.phase, envelope: FfEnvelope = this.envelope, customLen: U32 = this.customLen, customData: MutPtr = this.customData): FfPeriodicEffect = FfPeriodicEffect(waveform = waveform, period = period, magnitude = magnitude, offset = offset, phase = phase, envelope = envelope, customLen = customLen, customDataCtorArg = customData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FfPeriodicEffect) return false
        if (this.waveform != other.waveform) return false
        if (this.period != other.period) return false
        if (this.magnitude != other.magnitude) return false
        if (this.offset != other.offset) return false
        if (this.phase != other.phase) return false
        if (this.envelope != other.envelope) return false
        if (this.customLen != other.customLen) return false
        if (this.customData != other.customData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.waveform.hashCode()
        result = 31 * result + this.period.hashCode()
        result = 31 * result + this.magnitude.hashCode()
        result = 31 * result + this.offset.hashCode()
        result = 31 * result + this.phase.hashCode()
        result = 31 * result + this.envelope.hashCode()
        result = 31 * result + this.customLen.hashCode()
        result = 31 * result + this.customData.hashCode()
        return result
    }

    public fun clone(): FfPeriodicEffect = FfPeriodicEffect(waveform, period, magnitude, offset, phase, envelope, customLen, customData)

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
            waveform: U16 = 0u,
            period: U16 = 0u,
            magnitude: S16 = 0,
            offset: S16 = 0,
            phase: U16 = 0u,
            envelope: FfEnvelope = FfEnvelope(),
            customLen: U32 = 0u,
            customData: MutPtr = MutPtr(0L),
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

public data class FfRumbleEffect(
    public var strongMagnitude: U16 = 0u,
    public var weakMagnitude: U16 = 0u,
) {
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

public data class FfEffect(
    public var type: U16 = 0u,
    public var id: S16 = 0,
    public var direction: U16 = 0u,
    public var trigger: FfTrigger = FfTrigger(),
    public var replay: FfReplay = FfReplay(),
    // Note(1.0): this is actually a union
    public var u: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FfEffect) return false
        if (this.type != other.type) return false
        if (this.id != other.id) return false
        if (this.direction != other.direction) return false
        if (this.trigger != other.trigger) return false
        if (this.replay != other.replay) return false
        if (!this.u.contentEquals(other.u)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.type.hashCode()
        result = 31 * result + this.id.hashCode()
        result = 31 * result + this.direction.hashCode()
        result = 31 * result + this.trigger.hashCode()
        result = 31 * result + this.replay.hashCode()
        result = 31 * result + this.u.contentHashCode()
        return result
    }

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

public data class UinputFfUpload(
    public var requestId: U32 = 0u,
    public var retval: S32 = 0,
    public var effect: FfEffect = FfEffect(),
    public var old: FfEffect = FfEffect(),
) {
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

public data class UinputFfErase(
    public var requestId: U32 = 0u,
    public var retval: S32 = 0,
    public var effectId: U32 = 0u,
) {
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

public data class UinputAbsSetup(
    public var code: U16 = 0u,
    public var absinfo: InputAbsinfo = InputAbsinfo(),
) {
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

public data class CAnonymousKernelFsidT(
    public var value: IntArray = IntArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousKernelFsidT) return false
        if (!this.value.contentEquals(other.value)) return false
        return true
    }

    override fun hashCode(): Int = this.value.contentHashCode()

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

public class PosixSpawnFileActionsT internal constructor(
    public var allocated: CInt,
    public var used: CInt,
    actionsCtorArg: MutPtr,
    public var pad: IntArray,
) {
    public var actions: MutPtr = actionsCtorArg
        internal set

    public constructor() : this(
        allocated = 0,
        used = 0,
        actionsCtorArg = MutPtr(0L),
        pad = IntArray(16),
    )

    public operator fun component1(): CInt = this.allocated

    public operator fun component2(): CInt = this.used

    public operator fun component3(): MutPtr = this.actions

    public operator fun component4(): IntArray = this.pad

    public fun copy(allocated: CInt = this.allocated, used: CInt = this.used, actions: MutPtr = this.actions, pad: IntArray = this.pad): PosixSpawnFileActionsT = PosixSpawnFileActionsT(allocated = allocated, used = used, actionsCtorArg = actions, pad = pad)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PosixSpawnFileActionsT) return false
        if (this.allocated != other.allocated) return false
        if (this.used != other.used) return false
        if (this.actions != other.actions) return false
        if (!this.pad.contentEquals(other.pad)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.allocated.hashCode()
        result = 31 * result + this.used.hashCode()
        result = 31 * result + this.actions.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        return result
    }

    public fun clone(): PosixSpawnFileActionsT = PosixSpawnFileActionsT(allocated, used, actions, pad)

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
            allocated: CInt = 0,
            used: CInt = 0,
            actions: MutPtr = MutPtr(0L),
            pad: IntArray = IntArray(16),
        ): PosixSpawnFileActionsT = PosixSpawnFileActionsT(
            allocated = allocated,
            used = used,
            actionsCtorArg = actions,
            pad = pad,
        )
    }
}

public data class PosixSpawnattrT(
    public var flags: CShort = 0,
    public var pgrp: PidT = 0,
    public var sd: SigsetT = SigsetT(),
    public var ss: SigsetT = SigsetT(),
    public var prio: CInt = 0,
    public var sp: SchedParam = SchedParam(),
    public var policy: CInt = 0,
    public var pad: IntArray = IntArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PosixSpawnattrT) return false
        if (this.flags != other.flags) return false
        if (this.pgrp != other.pgrp) return false
        if (this.sd != other.sd) return false
        if (this.ss != other.ss) return false
        if (this.prio != other.prio) return false
        if (this.sp != other.sp) return false
        if (this.policy != other.policy) return false
        if (!this.pad.contentEquals(other.pad)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.flags.hashCode()
        result = 31 * result + this.pgrp.hashCode()
        result = 31 * result + this.sd.hashCode()
        result = 31 * result + this.ss.hashCode()
        result = 31 * result + this.prio.hashCode()
        result = 31 * result + this.sp.hashCode()
        result = 31 * result + this.policy.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        return result
    }
}

public data class Genlmsghdr(
    public var cmd: UByte = 0u,
    public var version: UByte = 0u,
    public var reserved: UShort = 0u,
) {
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

public data class InotifyEvent(
    public var wd: CInt = 0,
    public var mask: UInt = 0u,
    public var cookie: UInt = 0u,
    public var len: UInt = 0u,
) {
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

public data class FanotifyResponse(
    public var fd: CInt = 0,
    public var response: U32 = 0u,
) {
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

public data class FanotifyEventInfoHeader(
    public var infoType: U8 = 0u,
    public var pad: U8 = 0u,
    public var len: U16 = 0u,
) {
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

public data class FanotifyEventInfoFid(
    public var hdr: FanotifyEventInfoHeader = FanotifyEventInfoHeader(),
    public var fsid: KernelFsidT = CAnonymousKernelFsidT(),
    public var handle: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FanotifyEventInfoFid) return false
        if (this.hdr != other.hdr) return false
        if (this.fsid != other.fsid) return false
        if (!this.handle.contentEquals(other.handle)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.hdr.hashCode()
        result = 31 * result + this.fsid.hashCode()
        result = 31 * result + this.handle.contentHashCode()
        return result
    }

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

public data class SockaddrVm(
    public var svmFamily: SaFamilyT = 0u,
    public var svmReserved1: CUshort = 0u,
    public var svmPort: CUint = 0u,
    public var svmCid: CUint = 0u,
    public var svmZero: UByteArray = UByteArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrVm) return false
        if (this.svmFamily != other.svmFamily) return false
        if (this.svmReserved1 != other.svmReserved1) return false
        if (this.svmPort != other.svmPort) return false
        if (this.svmCid != other.svmCid) return false
        if (!this.svmZero.contentEquals(other.svmZero)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.svmFamily.hashCode()
        result = 31 * result + this.svmReserved1.hashCode()
        result = 31 * result + this.svmPort.hashCode()
        result = 31 * result + this.svmCid.hashCode()
        result = 31 * result + this.svmZero.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrVm> =
            object : TypeLayout<SockaddrVm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.svmFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.svmPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 12L, value.svmZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.svmFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.svmPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 12, value.svmZero)
                }
            }
    }
}

public data class SockExtendedErr(
    public var eeErrno: UInt = 0u,
    public var eeOrigin: UByte = 0u,
    public var eeType: UByte = 0u,
    public var eeCode: UByte = 0u,
    public var eePad: UByte = 0u,
    public var eeInfo: UInt = 0u,
    public var eeData: UInt = 0u,
) {
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
public data class SeccompData(
    public var nr: CInt = 0,
    public var arch: U32 = 0u,
    public var instructionPointer: U64 = 0uL,
    public var args: ULongArray = ULongArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SeccompData) return false
        if (this.nr != other.nr) return false
        if (this.arch != other.arch) return false
        if (this.instructionPointer != other.instructionPointer) return false
        if (!this.args.contentEquals(other.args)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nr.hashCode()
        result = 31 * result + this.arch.hashCode()
        result = 31 * result + this.instructionPointer.hashCode()
        result = 31 * result + this.args.contentHashCode()
        return result
    }

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

public data class SeccompNotifSizes(
    public var seccompNotif: U16 = 0u,
    public var seccompNotifResp: U16 = 0u,
    public var seccompData: U16 = 0u,
) {
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

public data class SeccompNotif(
    public var id: U64 = 0uL,
    public var pid: U32 = 0u,
    public var flags: U32 = 0u,
    public var data: SeccompData = SeccompData(),
) {
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

public data class SeccompNotifResp(
    public var id: U64 = 0uL,
    public var value: S64 = 0L,
    public var error: S32 = 0,
    public var flags: U32 = 0u,
) {
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

public data class SeccompNotifAddfd(
    public var id: U64 = 0uL,
    public var flags: U32 = 0u,
    public var srcfd: U32 = 0u,
    public var newfd: U32 = 0u,
    public var newfdFlags: U32 = 0u,
) {
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

public data class In6Ifreq(
    public var ifr6Addr: In6Addr = In6Addr(),
    public var ifr6Prefixlen: UInt = 0u,
    public var ifr6Ifindex: CInt = 0,
)

public data class OpenHow(
    public var flags: U64 = 0uL,
    public var mode: U64 = 0uL,
    public var resolve: U64 = 0uL,
) {
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
public data class PtpClockTime(
    public var sec: S64 = 0L,
    public var nsec: U32 = 0u,
    public var reserved: U32 = 0u,
) {
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

public data class PtpExttsRequest(
    public var index: CUint = 0u,
    public var flags: CUint = 0u,
    public var rsv: UIntArray = UIntArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpExttsRequest) return false
        if (this.index != other.index) return false
        if (this.flags != other.flags) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.index.hashCode()
        result = 31 * result + this.flags.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

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

public data class PtpSysOffsetExtended(
    public var nSamples: CUint = 0u,
    public var clockid: KernelClockidT = 0,
    public var rsv: UIntArray = UIntArray(2),
    public var ts: List<List<PtpClockTime>> = List(PTP_MAX_SAMPLES.toInt()) { emptyList() },
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpSysOffsetExtended) return false
        if (this.nSamples != other.nSamples) return false
        if (this.clockid != other.clockid) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        if (this.ts != other.ts) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nSamples.hashCode()
        result = 31 * result + this.clockid.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        result = 31 * result + this.ts.hashCode()
        return result
    }

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

public data class PtpSysOffsetPrecise(
    public var device: PtpClockTime = PtpClockTime(),
    public var sysRealtime: PtpClockTime = PtpClockTime(),
    public var sysMonoraw: PtpClockTime = PtpClockTime(),
    public var rsv: UIntArray = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpSysOffsetPrecise) return false
        if (this.device != other.device) return false
        if (this.sysRealtime != other.sysRealtime) return false
        if (this.sysMonoraw != other.sysMonoraw) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.device.hashCode()
        result = 31 * result + this.sysRealtime.hashCode()
        result = 31 * result + this.sysMonoraw.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

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

public data class PtpExttsEvent(
    public var t: PtpClockTime = PtpClockTime(),
    public var index: CUint = 0u,
    public var flags: CUint = 0u,
    public var rsv: UIntArray = UIntArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpExttsEvent) return false
        if (this.t != other.t) return false
        if (this.index != other.index) return false
        if (this.flags != other.flags) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.t.hashCode()
        result = 31 * result + this.index.hashCode()
        result = 31 * result + this.flags.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

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
public data class IwParam(
    public var value: S32 = 0,
    public var fixed: U8 = 0u,
    public var disabled: U8 = 0u,
    public var flags: U16 = 0u,
) {
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

public class IwPoint internal constructor(
    pointerCtorArg: MutPtr,
    public var length: U16,
    public var flags: U16,
) {
    public var pointer: MutPtr = pointerCtorArg
        internal set

    public constructor() : this(
        pointerCtorArg = MutPtr(0L),
        length = 0u,
        flags = 0u,
    )

    public operator fun component1(): MutPtr = this.pointer

    public operator fun component2(): U16 = this.length

    public operator fun component3(): U16 = this.flags

    public fun copy(pointer: MutPtr = this.pointer, length: U16 = this.length, flags: U16 = this.flags): IwPoint = IwPoint(pointerCtorArg = pointer, length = length, flags = flags)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwPoint) return false
        if (this.pointer != other.pointer) return false
        if (this.length != other.length) return false
        if (this.flags != other.flags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pointer.hashCode()
        result = 31 * result + this.length.hashCode()
        result = 31 * result + this.flags.hashCode()
        return result
    }

    public fun clone(): IwPoint = IwPoint(pointer, length, flags)

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
            pointer: MutPtr = MutPtr(0L),
            length: U16 = 0u,
            flags: U16 = 0u,
        ): IwPoint = IwPoint(
            pointerCtorArg = pointer,
            length = length,
            flags = flags,
        )
    }
}

public data class IwFreq(
    public var m: S32 = 0,
    public var e: S16 = 0,
    public var i: U8 = 0u,
    public var flags: U8 = 0u,
) {
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

public data class IwQuality(
    public var qual: U8 = 0u,
    public var level: U8 = 0u,
    public var noise: U8 = 0u,
    public var updated: U8 = 0u,
) {
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

public data class IwDiscarded(
    public var nwid: U32 = 0u,
    public var code: U32 = 0u,
    public var fragment: U32 = 0u,
    public var retries: U32 = 0u,
    public var pubmisc: U32 = 0u,
) {
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

public data class IwMissed(
    public var beacon: U32 = 0u,
) {
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

public data class IwScanReq(
    public var scanType: U8 = 0u,
    public var essidLen: U8 = 0u,
    public var numChannels: U8 = 0u,
    public var flags: U8 = 0u,
    public var bssid: Sockaddr = Sockaddr(),
    public var essid: UByteArray = UByteArray(IW_ESSID_MAX_SIZE),
    public var minChannelTime: U32 = 0u,
    public var maxChannelTime: U32 = 0u,
    public var channelList: List<IwFreq> = List(IW_MAX_FREQUENCIES) { IwFreq() },
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwScanReq) return false
        if (this.scanType != other.scanType) return false
        if (this.essidLen != other.essidLen) return false
        if (this.numChannels != other.numChannels) return false
        if (this.flags != other.flags) return false
        if (this.bssid != other.bssid) return false
        if (!this.essid.contentEquals(other.essid)) return false
        if (this.minChannelTime != other.minChannelTime) return false
        if (this.maxChannelTime != other.maxChannelTime) return false
        if (this.channelList != other.channelList) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.scanType.hashCode()
        result = 31 * result + this.essidLen.hashCode()
        result = 31 * result + this.numChannels.hashCode()
        result = 31 * result + this.flags.hashCode()
        result = 31 * result + this.bssid.hashCode()
        result = 31 * result + this.essid.contentHashCode()
        result = 31 * result + this.minChannelTime.hashCode()
        result = 31 * result + this.maxChannelTime.hashCode()
        result = 31 * result + this.channelList.hashCode()
        return result
    }
}

public data class IwEncodeExt(
    public var extFlags: U32 = 0u,
    public var txSeq: UByteArray = UByteArray(IW_ENCODE_SEQ_MAX_SIZE),
    public var rxSeq: UByteArray = UByteArray(IW_ENCODE_SEQ_MAX_SIZE),
    public var addr: Sockaddr = Sockaddr(),
    public var alg: U16 = 0u,
    public var keyLen: U16 = 0u,
    public var key: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwEncodeExt) return false
        if (this.extFlags != other.extFlags) return false
        if (!this.txSeq.contentEquals(other.txSeq)) return false
        if (!this.rxSeq.contentEquals(other.rxSeq)) return false
        if (this.addr != other.addr) return false
        if (this.alg != other.alg) return false
        if (this.keyLen != other.keyLen) return false
        if (!this.key.contentEquals(other.key)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.extFlags.hashCode()
        result = 31 * result + this.txSeq.contentHashCode()
        result = 31 * result + this.rxSeq.contentHashCode()
        result = 31 * result + this.addr.hashCode()
        result = 31 * result + this.alg.hashCode()
        result = 31 * result + this.keyLen.hashCode()
        result = 31 * result + this.key.contentHashCode()
        return result
    }
}

public data class IwPmksa(
    public var cmd: U32 = 0u,
    public var bssid: Sockaddr = Sockaddr(),
    public var pmkid: UByteArray = UByteArray(IW_PMKID_LEN),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwPmksa) return false
        if (this.cmd != other.cmd) return false
        if (this.bssid != other.bssid) return false
        if (!this.pmkid.contentEquals(other.pmkid)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cmd.hashCode()
        result = 31 * result + this.bssid.hashCode()
        result = 31 * result + this.pmkid.contentHashCode()
        return result
    }
}

public data class IwPmkidCand(
    public var flags: U32 = 0u,
    public var index: U32 = 0u,
    public var bssid: Sockaddr = Sockaddr(),
)

public data class IwStatistics(
    public var status: U16 = 0u,
    public var qual: IwQuality = IwQuality(),
    public var discard: IwDiscarded = IwDiscarded(),
    public var miss: IwMissed = IwMissed(),
) {
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

public class IwRange(
    public var throughput: U32 = 0u,
    public var minNwid: U32 = 0u,
    public var maxNwid: U32 = 0u,
    public var oldNumChannels: U16 = 0u,
    public var oldNumFrequency: U8 = 0u,
    public var scanCapa: U8 = 0u,
    public var eventCapa: UIntArray = UIntArray(6),
    public var sensitivity: S32 = 0,
    public var maxQual: IwQuality = IwQuality(),
    public var avgQual: IwQuality = IwQuality(),
    public var numBitrates: U8 = 0u,
    public var bitrate: IntArray = IntArray(IW_MAX_BITRATES),
    public var minRts: S32 = 0,
    public var maxRts: S32 = 0,
    public var minFrag: S32 = 0,
    public var maxFrag: S32 = 0,
    public var minPmp: S32 = 0,
    public var maxPmp: S32 = 0,
    public var minPmt: S32 = 0,
    public var maxPmt: S32 = 0,
    public var pmpFlags: U16 = 0u,
    public var pmtFlags: U16 = 0u,
    public var pmCapa: U16 = 0u,
    public var encodingSize: UShortArray = UShortArray(IW_MAX_ENCODING_SIZES),
    public var numEncodingSizes: U8 = 0u,
    public var maxEncodingTokens: U8 = 0u,
    public var encodingLoginIndex: U8 = 0u,
    public var txpowerCapa: U16 = 0u,
    public var numTxpower: U8 = 0u,
    public var txpower: IntArray = IntArray(IW_MAX_TXPOWER),
    public var weVersionCompiled: U8 = 0u,
    public var weVersionSource: U8 = 0u,
    public var retryCapa: U16 = 0u,
    public var retryFlags: U16 = 0u,
    public var rTimeFlags: U16 = 0u,
    public var minRetry: S32 = 0,
    public var maxRetry: S32 = 0,
    public var minRTime: S32 = 0,
    public var maxRTime: S32 = 0,
    public var numChannels: U16 = 0u,
    public var numFrequency: U8 = 0u,
    public var freq: List<IwFreq> = List(IW_MAX_FREQUENCIES) { IwFreq() },
    public var encCapa: U32 = 0u,
) {
    public fun clone(): IwRange = IwRange(throughput, minNwid, maxNwid, oldNumChannels, oldNumFrequency, scanCapa, eventCapa, sensitivity, maxQual, avgQual, numBitrates, bitrate, minRts, maxRts, minFrag, maxFrag, minPmp, maxPmp, minPmt, maxPmt, pmpFlags, pmtFlags, pmCapa, encodingSize, numEncodingSizes, maxEncodingTokens, encodingLoginIndex, txpowerCapa, numTxpower, txpower, weVersionCompiled, weVersionSource, retryCapa, retryFlags, rTimeFlags, minRetry, maxRetry, minRTime, maxRTime, numChannels, numFrequency, freq, encCapa)

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

public data class IwPrivArgs(
    public var cmd: U32 = 0u,
    public var setArgs: U16 = 0u,
    public var getArgs: U16 = 0u,
    public var name: UByteArray = UByteArray(IFNAMSIZ.toInt()),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwPrivArgs) return false
        if (this.cmd != other.cmd) return false
        if (this.setArgs != other.setArgs) return false
        if (this.getArgs != other.getArgs) return false
        if (!this.name.contentEquals(other.name)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cmd.hashCode()
        result = 31 * result + this.setArgs.hashCode()
        result = 31 * result + this.getArgs.hashCode()
        result = 31 * result + this.name.contentHashCode()
        return result
    }

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
public data class EpollParams(
    public var busyPollUsecs: UInt = 0u,
    public var busyPollBudget: UShort = 0u,
    public var preferBusyPoll: UByte = 0u,
    public var pad: UByte = 0u,
) {
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

public data class PthreadMutexattrT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_MUTEXATTR_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexattrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class PthreadRwlockattrT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_RWLOCKATTR_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockattrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class PthreadCondattrT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_CONDATTR_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondattrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondattrT> =
            object : TypeLayout<PthreadCondattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_CONDATTR_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 4L)
                override fun readNative(addr: Long): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_CONDATTR_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadCondattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_CONDATTR_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_CONDATTR_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_CONDATTR_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public data class PthreadBarrierattrT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_BARRIERATTR_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadBarrierattrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadBarrierattrT> =
            object : TypeLayout<PthreadBarrierattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((SIZEOF_PTHREAD_BARRIERATTR_T).toLong() * (PrimitiveLayouts.U8).strideBytes), 4L)
                override fun readNative(addr: Long): PthreadBarrierattrT =
                    PthreadBarrierattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIERATTR_T).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadBarrierattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIERATTR_T).toInt())).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadBarrierattrT =
                    PthreadBarrierattrT(
                        (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIERATTR_T).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadBarrierattrT) {
                    (PrimitiveArrayLayouts.uByteArray((SIZEOF_PTHREAD_BARRIERATTR_T).toInt())).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public data class FanotifyEventMetadata(
    public var eventLen: U32 = 0u,
    public var vers: U8 = 0u,
    public var reserved: U8 = 0u,
    public var metadataLen: U16 = 0u,
    public var mask: U64 = 0uL,
    public var fd: CInt = 0,
    public var pid: CInt = 0,
) {
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
public data class PtpSysOffset(
    public var nSamples: CUint = 0u,
    public var rsv: UIntArray = UIntArray(3),
    // Note(garando): replace length with `2 * PTP_MAX_SAMPLES + 1` when supported
    public var ts: List<PtpClockTime> = List(51) { PtpClockTime() },
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpSysOffset) return false
        if (this.nSamples != other.nSamples) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        if (this.ts != other.ts) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nSamples.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        result = 31 * result + this.ts.hashCode()
        return result
    }

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

public data class PtpPinDesc(
    public var name: UByteArray = UByteArray(64),
    public var index: CUint = 0u,
    public var func: CUint = 0u,
    public var chan: CUint = 0u,
    public var rsv: UIntArray = UIntArray(5),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpPinDesc) return false
        if (!this.name.contentEquals(other.name)) return false
        if (this.index != other.index) return false
        if (this.func != other.func) return false
        if (this.chan != other.chan) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.name.contentHashCode()
        result = 31 * result + this.index.hashCode()
        result = 31 * result + this.func.hashCode()
        result = 31 * result + this.chan.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

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

public data class PtpClockCaps(
    public var maxAdj: CInt = 0,
    public var nAlarm: CInt = 0,
    public var nExtTs: CInt = 0,
    public var nPerOut: CInt = 0,
    public var pps: CInt = 0,
    public var nPins: CInt = 0,
    public var crossTimestamping: CInt = 0,
    public var adjustPhase: CInt = 0,
    public var maxPhaseAdj: CInt = 0,
    public var rsv: IntArray = IntArray(11),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtpClockCaps) return false
        if (this.maxAdj != other.maxAdj) return false
        if (this.nAlarm != other.nAlarm) return false
        if (this.nExtTs != other.nExtTs) return false
        if (this.nPerOut != other.nPerOut) return false
        if (this.pps != other.pps) return false
        if (this.nPins != other.nPins) return false
        if (this.crossTimestamping != other.crossTimestamping) return false
        if (this.adjustPhase != other.adjustPhase) return false
        if (this.maxPhaseAdj != other.maxPhaseAdj) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.maxAdj.hashCode()
        result = 31 * result + this.nAlarm.hashCode()
        result = 31 * result + this.nExtTs.hashCode()
        result = 31 * result + this.nPerOut.hashCode()
        result = 31 * result + this.pps.hashCode()
        result = 31 * result + this.nPins.hashCode()
        result = 31 * result + this.crossTimestamping.hashCode()
        result = 31 * result + this.adjustPhase.hashCode()
        result = 31 * result + this.maxPhaseAdj.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

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
public data class SockaddrXdp(
    public var sxdpFamily: U16 = 0u,
    public var sxdpFlags: U16 = 0u,
    public var sxdpIfindex: U32 = 0u,
    public var sxdpQueueId: U32 = 0u,
    public var sxdpSharedUmemFd: U32 = 0u,
) {
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

public data class XdpRingOffset(
    public var producer: U64 = 0uL,
    public var consumer: U64 = 0uL,
    public var desc: U64 = 0uL,
    public var flags: U64 = 0uL,
) {
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

public data class XdpMmapOffsets(
    public var rx: XdpRingOffset = XdpRingOffset(),
    public var tx: XdpRingOffset = XdpRingOffset(),
    public var fr: XdpRingOffset = XdpRingOffset(),
    public var cr: XdpRingOffset = XdpRingOffset(),
) {
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

public data class XdpRingOffsetV1(
    public var producer: U64 = 0uL,
    public var consumer: U64 = 0uL,
    public var desc: U64 = 0uL,
) {
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

public data class XdpMmapOffsetsV1(
    public var rx: XdpRingOffsetV1 = XdpRingOffsetV1(),
    public var tx: XdpRingOffsetV1 = XdpRingOffsetV1(),
    public var fr: XdpRingOffsetV1 = XdpRingOffsetV1(),
    public var cr: XdpRingOffsetV1 = XdpRingOffsetV1(),
) {
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

public data class XdpUmemReg(
    public var addr: U64 = 0uL,
    public var len: U64 = 0uL,
    public var chunkSize: U32 = 0u,
    public var headroom: U32 = 0u,
    public var flags: U32 = 0u,
    public var txMetadataLen: U32 = 0u,
) {
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

public data class XdpUmemRegV1(
    public var addr: U64 = 0uL,
    public var len: U64 = 0uL,
    public var chunkSize: U32 = 0u,
    public var headroom: U32 = 0u,
) {
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

public data class XdpStatistics(
    public var rxDropped: U64 = 0uL,
    public var rxInvalidDescs: U64 = 0uL,
    public var txInvalidDescs: U64 = 0uL,
    public var rxRingFull: U64 = 0uL,
    public var rxFillRingEmptyDescs: U64 = 0uL,
    public var txRingEmptyDescs: U64 = 0uL,
) {
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

public data class XdpStatisticsV1(
    public var rxDropped: U64 = 0uL,
    public var rxInvalidDescs: U64 = 0uL,
    public var txInvalidDescs: U64 = 0uL,
) {
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

public data class XdpOptions(
    public var flags: U32 = 0u,
) {
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

public data class XdpDesc(
    public var addr: U64 = 0uL,
    public var len: U32 = 0u,
    public var options: U32 = 0u,
) {
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

public data class XskTxMetadataCompletion(
    public var txTimestamp: U64 = 0uL,
) {
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

public data class XskTxMetadataRequest(
    public var csumStart: U16 = 0u,
    public var csumOffset: U16 = 0u,
) {
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
public data class MntNsInfo(
    public var size: U32 = 0u,
    public var nrMounts: U32 = 0u,
    public var mntNsId: U64 = 0uL,
) {
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
public data class DmabufCmsg(
    public var fragOffset: U64 = 0uL,
    public var fragSize: U32 = 0u,
    public var fragToken: U32 = 0u,
    public var dmabufId: U32 = 0u,
    public var flags: U32 = 0u,
) {
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

public data class DmabufToken(
    public var tokenStart: U32 = 0u,
    public var tokenCount: U32 = 0u,
) {
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

public data class SockaddrAlg(
    public var salgFamily: SaFamilyT = 0u,
    public var salgType: UByteArray = UByteArray(14),
    public var salgFeat: UInt = 0u,
    public var salgMask: UInt = 0u,
    public var salgName: UByteArray = UByteArray(64),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrAlg) return false
        if (this.salgFamily != other.salgFamily) return false
        if (!this.salgType.contentEquals(other.salgType)) return false
        if (this.salgFeat != other.salgFeat) return false
        if (this.salgMask != other.salgMask) return false
        if (!this.salgName.contentEquals(other.salgName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.salgFamily.hashCode()
        result = 31 * result + this.salgType.contentHashCode()
        result = 31 * result + this.salgFeat.hashCode()
        result = 31 * result + this.salgMask.hashCode()
        result = 31 * result + this.salgName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrAlg> =
            object : TypeLayout<SockaddrAlg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 1L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uByteArray(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrAlg) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 1L, value.salgType)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.salgFeat)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeNative(addr + 24L, value.salgName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uByteArray(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrAlg) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 1, value.salgType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.salgFeat)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeBytes(buf, offset + 24, value.salgName)
                }
            }
    }
}

public data class PthreadCondT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_COND_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class PthreadMutexT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_MUTEX_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class PthreadRwlockT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_RWLOCK_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class PthreadBarrierT(
    public var size: UByteArray = UByteArray(SIZEOF_PTHREAD_BARRIER_T),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadBarrierT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class UinputSetup(
    public var id: InputId = InputId(),
    public var name: UByteArray = UByteArray(UINPUT_MAX_NAME_SIZE),
    public var ffEffectsMax: U32 = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UinputSetup) return false
        if (this.id != other.id) return false
        if (!this.name.contentEquals(other.name)) return false
        if (this.ffEffectsMax != other.ffEffectsMax) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.id.hashCode()
        result = 31 * result + this.name.contentHashCode()
        result = 31 * result + this.ffEffectsMax.hashCode()
        return result
    }

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

public data class UinputUserDev(
    public var name: UByteArray = UByteArray(UINPUT_MAX_NAME_SIZE),
    public var id: InputId = InputId(),
    public var ffEffectsMax: U32 = 0u,
    public var absmax: IntArray = IntArray(ABS_CNT),
    public var absmin: IntArray = IntArray(ABS_CNT),
    public var absfuzz: IntArray = IntArray(ABS_CNT),
    public var absflat: IntArray = IntArray(ABS_CNT),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UinputUserDev) return false
        if (!this.name.contentEquals(other.name)) return false
        if (this.id != other.id) return false
        if (this.ffEffectsMax != other.ffEffectsMax) return false
        if (!this.absmax.contentEquals(other.absmax)) return false
        if (!this.absmin.contentEquals(other.absmin)) return false
        if (!this.absfuzz.contentEquals(other.absfuzz)) return false
        if (!this.absflat.contentEquals(other.absflat)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.name.contentHashCode()
        result = 31 * result + this.id.hashCode()
        result = 31 * result + this.ffEffectsMax.hashCode()
        result = 31 * result + this.absmax.contentHashCode()
        result = 31 * result + this.absmin.contentHashCode()
        result = 31 * result + this.absfuzz.contentHashCode()
        result = 31 * result + this.absflat.contentHashCode()
        return result
    }

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
public data class MqAttr(
    public var mqFlags: CLong = 0L,
    public var mqMaxmsg: CLong = 0L,
    public var mqMsgsize: CLong = 0L,
    public var mqCurmsgs: CLong = 0L,
    public var pad: LongArray = LongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MqAttr) return false
        if (this.mqFlags != other.mqFlags) return false
        if (this.mqMaxmsg != other.mqMaxmsg) return false
        if (this.mqMsgsize != other.mqMsgsize) return false
        if (this.mqCurmsgs != other.mqCurmsgs) return false
        if (!this.pad.contentEquals(other.pad)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.mqFlags.hashCode()
        result = 31 * result + this.mqMaxmsg.hashCode()
        result = 31 * result + this.mqMsgsize.hashCode()
        result = 31 * result + this.mqCurmsgs.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        return result
    }

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

public data class HwtstampConfig(
    public var flags: CInt = 0,
    public var txType: CInt = 0,
    public var rxFilter: CInt = 0,
) {
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

public data class SchedAttr(
    public var size: U32 = 0u,
    public var schedPolicy: U32 = 0u,
    public var schedFlags: U64 = 0uL,
    public var schedNice: S32 = 0,
    public var schedPriority: U32 = 0u,
    public var schedRuntime: U64 = 0uL,
    public var schedDeadline: U64 = 0uL,
    public var schedPeriod: U64 = 0uL,
) {
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
public data class FileHandle(
    public var handleBytes: CUint = 0u,
    public var handleType: CInt = 0,
    public var fHandle: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileHandle) return false
        if (this.handleBytes != other.handleBytes) return false
        if (this.handleType != other.handleType) return false
        if (!this.fHandle.contentEquals(other.fHandle)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.handleBytes.hashCode()
        result = 31 * result + this.handleType.hashCode()
        result = 31 * result + this.fHandle.contentHashCode()
        return result
    }

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
public data class Ifinfomsg(
    public var ifiFamily: CUchar = 0u,
    public var ifiPad: CUchar = 0u,
    public var ifiType: CUshort = 0u,
    public var ifiIndex: CInt = 0,
    public var ifiFlags: CUint = 0u,
    public var ifiChange: CUint = 0u,
) {
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

public data class Rtattr(
    public var rtaLen: CUshort = 0u,
    public var rtaType: CUshort = 0u,
) {
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
public class Netent internal constructor(
    nNameCtorArg: MutPtr,
    nAliasesCtorArg: MutPtr,
    public var nAddrtype: CInt,
    public var nNet: UInt,
) {
    public var nName: MutPtr = nNameCtorArg
        internal set

    public var nAliases: MutPtr = nAliasesCtorArg
        internal set

    public constructor() : this(
        nNameCtorArg = MutPtr(0L),
        nAliasesCtorArg = MutPtr(0L),
        nAddrtype = 0,
        nNet = 0u,
    )

    public operator fun component1(): MutPtr = this.nName

    public operator fun component2(): MutPtr = this.nAliases

    public operator fun component3(): CInt = this.nAddrtype

    public operator fun component4(): UInt = this.nNet

    public fun copy(nName: MutPtr = this.nName, nAliases: MutPtr = this.nAliases, nAddrtype: CInt = this.nAddrtype, nNet: UInt = this.nNet): Netent = Netent(nNameCtorArg = nName, nAliasesCtorArg = nAliases, nAddrtype = nAddrtype, nNet = nNet)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Netent) return false
        if (this.nName != other.nName) return false
        if (this.nAliases != other.nAliases) return false
        if (this.nAddrtype != other.nAddrtype) return false
        if (this.nNet != other.nNet) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nName.hashCode()
        result = 31 * result + this.nAliases.hashCode()
        result = 31 * result + this.nAddrtype.hashCode()
        result = 31 * result + this.nNet.hashCode()
        return result
    }

    public fun clone(): Netent = Netent(nName, nAliases, nAddrtype, nNet)

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
            nName: MutPtr = MutPtr(0L),
            nAliases: MutPtr = MutPtr(0L),
            nAddrtype: CInt = 0,
            nNet: UInt = 0u,
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
public data class Fpos64T(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public data class IwThrspy(
    public var addr: Sockaddr = Sockaddr(),
    public var qual: IwQuality = IwQuality(),
    public var low: IwQuality = IwQuality(),
    public var high: IwQuality = IwQuality(),
)

public data class IwMlme(
    public var cmd: U16 = 0u,
    public var reasonCode: U16 = 0u,
    public var addr: Sockaddr = Sockaddr(),
)

public data class IwMichaelmicfailure(
    public var flags: U32 = 0u,
    public var srcAddr: Sockaddr = Sockaddr(),
    public var tsc: UByteArray = UByteArray(IW_ENCODE_SEQ_MAX_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IwMichaelmicfailure) return false
        if (this.flags != other.flags) return false
        if (this.srcAddr != other.srcAddr) return false
        if (!this.tsc.contentEquals(other.tsc)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.flags.hashCode()
        result = 31 * result + this.srcAddr.hashCode()
        result = 31 * result + this.tsc.contentHashCode()
        return result
    }
}

public data class AfAlgIv(
    public var ivlen: UInt = 0u,
    public var iv: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AfAlgIv) return false
        if (this.ivlen != other.ivlen) return false
        if (!this.iv.contentEquals(other.iv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ivlen.hashCode()
        result = 31 * result + this.iv.contentHashCode()
        return result
    }

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
public data class SockTxtime(
    public var clockid: ClockidT = 0u,
    public var flags: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockTxtime> =
            object : TypeLayout<SockTxtime> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SockTxtime =
                    SockTxtime(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SockTxtime) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.clockid)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockTxtime =
                    SockTxtime(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockTxtime) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.clockid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.flags)
                }
            }
    }
}

// linux/wireless.h
public class IwreqData(
    public var name: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    public var essid: IwPoint = IwPoint(),
    public var nwid: IwParam = IwParam(),
    public var freq: IwFreq = IwFreq(),
    public var sens: IwParam = IwParam(),
    public var bitrate: IwParam = IwParam(),
    public var txpower: IwParam = IwParam(),
    public var rts: IwParam = IwParam(),
    public var frag: IwParam = IwParam(),
    public var mode: U32 = 0u,
    public var retry: IwParam = IwParam(),
    public var encoding: IwPoint = IwPoint(),
    public var power: IwParam = IwParam(),
    public var qual: IwQuality = IwQuality(),
    public var apAddr: Sockaddr = Sockaddr(),
    public var addr: Sockaddr = Sockaddr(),
    public var param: IwParam = IwParam(),
    public var data: IwPoint = IwPoint(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "iwreq_data").finishNonExhaustive()
    }
}

public data class IwEvent(
    public var len: U16 = 0u,
    public var cmd: U16 = 0u,
    public var u: IwreqData = IwreqData(),
)

public data class CAnonymousIwreq(
    public var ifrnName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIwreq) return false
        if (!this.ifrnName.contentEquals(other.ifrnName)) return false
        return true
    }

    override fun hashCode(): Int = this.ifrnName.contentHashCode()

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_iwreq").finishNonExhaustive()
    }
}

public data class Iwreq(
    public var ifrIfrn: CAnonymousIwreq = CAnonymousIwreq(),
    public var u: IwreqData = IwreqData(),
)

// linux/ptpClock.h
public data class CAnonymousPtpPeroutRequest1(
    public var start: PtpClockTime = PtpClockTime(),
    public var phase: PtpClockTime = PtpClockTime(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ptp_perout_request_1").finishNonExhaustive()
    }
}

public data class CAnonymousPtpPeroutRequest2(
    public var on: PtpClockTime = PtpClockTime(),
    public var rsv: UIntArray = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousPtpPeroutRequest2) return false
        if (this.on != other.on) return false
        if (!this.rsv.contentEquals(other.rsv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.on.hashCode()
        result = 31 * result + this.rsv.contentHashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ptp_perout_request_2").finishNonExhaustive()
    }
}

public data class PtpPeroutRequest(
    public var anonymous1: CAnonymousPtpPeroutRequest1 = CAnonymousPtpPeroutRequest1(),
    public var period: PtpClockTime = PtpClockTime(),
    public var index: CUint = 0u,
    public var flags: CUint = 0u,
    public var anonymous2: CAnonymousPtpPeroutRequest2 = CAnonymousPtpPeroutRequest2(),
)

// linux/ifXdp.h
public data class XskTxMetadata(
    public var flags: U64 = 0uL,
    public var xskTxMetadataUnion: CAnonymousXskTxMetadataUnion = CAnonymousXskTxMetadataUnion(),
)

public data class CAnonymousXskTxMetadataUnion(
    public var request: XskTxMetadataRequest = XskTxMetadataRequest(),
    public var completion: XskTxMetadataCompletion = XskTxMetadataCompletion(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_xsk_tx_metadata_union").finishNonExhaustive()
    }
}
