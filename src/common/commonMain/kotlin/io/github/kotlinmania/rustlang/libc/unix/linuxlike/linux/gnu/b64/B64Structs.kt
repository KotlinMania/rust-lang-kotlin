@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SaFamilyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Timeval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.IpcPerm

public open class SigsetT(
    public var value: ULongArray,
) {
    public open fun clone(): SigsetT = SigsetT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<SigsetT> =
            object : TypeLayout<SigsetT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Sysinfo(
    public var uptime: Long,
    public var loads: ULongArray,
    public var totalram: ULong,
    public var freeram: ULong,
    public var sharedram: ULong,
    public var bufferram: ULong,
    public var totalswap: ULong,
    public var freeswap: ULong,
    public var procs: CUshort,
    public var pad: CUshort,
    public var totalhigh: ULong,
    public var freehigh: ULong,
    public var memUnit: CUint,
    public var f: UByteArray,
) {
    public open fun clone(): Sysinfo = Sysinfo(uptime, loads, totalram, freeram, sharedram, bufferram, totalswap, freeswap, procs, pad, totalhigh, freehigh, memUnit, f)

    public companion object {
        public fun typeLayout(): TypeLayout<Sysinfo> =
            object : TypeLayout<Sysinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U16).readNative(addr + 80L),
                        (PrimitiveLayouts.U16).readNative(addr + 82L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U32).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 108L),
                    )
                override fun writeNative(addr: Long, value: Sysinfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 8L, value.loads)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.totalram)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.freeram)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.sharedram)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.bufferram)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.totalswap)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.freeswap)
                    (PrimitiveLayouts.U16).writeNative(addr + 80L, value.procs)
                    (PrimitiveLayouts.U16).writeNative(addr + 82L, value.pad)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.totalhigh)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.freehigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 104L, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 108L, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 108),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sysinfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 8, value.loads)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.totalram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.freeram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.sharedram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.bufferram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.totalswap)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.freeswap)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 80, value.procs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 82, value.pad)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.totalhigh)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.freehigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 104, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 108, value.f)
                }
            }
    }
}

public open class MsqidDs(
    public var msgPerm: IpcPerm,
    public var msgStime: TimeT,
    public var msgRtime: TimeT,
    public var msgCtime: TimeT,
    public var msgCbytes: ULong,
    public var msgQnum: MsgqnumT,
    public var msgQbytes: MsglenT,
    public var msgLspid: PidT,
    public var msgLrpid: PidT,
    public var glibcReserved4: ULong,
    public var glibcReserved5: ULong,
) {
    public open fun clone(): MsqidDs = MsqidDs(msgPerm, msgStime, msgRtime, msgCtime, msgCbytes, msgQnum, msgQbytes, msgLspid, msgLrpid, glibcReserved4, glibcReserved5)
}

public open class SemidDs(
    public var semPerm: IpcPerm,
    public var semOtime: TimeT,
    public var reserved: SyscallUlongT,
    public var semCtime: TimeT,
    public var reserved2: SyscallUlongT,
    public var semNsems: SyscallUlongT,
    public var glibcReserved3: SyscallUlongT,
    public var glibcReserved4: SyscallUlongT,
) {
    public open fun clone(): SemidDs = SemidDs(semPerm, semOtime, reserved, semCtime, reserved2, semNsems, glibcReserved3, glibcReserved4)
}

// net/iucv/iucv.h
public open class SockaddrIucv(
    public var siucvFamily: SaFamilyT,
    public var siucvPort: UShort,
    // unused inPortT
    public var siucvAddr: UInt,
    // unused inAddrT
    public var siucvNodeid: UByteArray,
    public var siucvUserId: UByteArray,
    public var siucvName: UByteArray,
) {
    public open fun clone(): SockaddrIucv = SockaddrIucv(siucvFamily, siucvPort, siucvAddr, siucvNodeid, siucvUserId, siucvName)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIucv> =
            object : TypeLayout<SockaddrIucv> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SockaddrIucv =
                    SockaddrIucv(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIucv) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.siucvFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.siucvPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.siucvAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.siucvNodeid)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 16L, value.siucvUserId)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 24L, value.siucvName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIucv =
                    SockaddrIucv(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIucv) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.siucvFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.siucvPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.siucvAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.siucvNodeid)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 16, value.siucvUserId)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 24, value.siucvName)
                }
            }
    }
}

public open class Timex(
    public var modes: CUint,
    public var offset: CLong,
    public var freq: CLong,
    public var maxerror: CLong,
    public var esterror: CLong,
    public var status: CInt,
    public var constant: CLong,
    public var precision: CLong,
    public var tolerance: CLong,
    public var time: Timeval,
    public var tick: CLong,
    public var ppsfreq: CLong,
    public var jitter: CLong,
    public var shift: CInt,
    public var stabil: CLong,
    public var jitcnt: CLong,
    public var calcnt: CLong,
    public var errcnt: CLong,
    public var stbcnt: CLong,
    public var tai: CInt,
    public var unused1: Int,
    public var unused2: Int,
    public var unused3: Int,
    public var unused4: Int,
    public var unused5: Int,
    public var unused6: Int,
    public var unused7: Int,
    public var unused8: Int,
    public var unused9: Int,
    public var unused10: Int,
    public var unused11: Int,
) {
    public open fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, time, tick, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt, tai, unused1, unused2, unused3, unused4, unused5, unused6, unused7, unused8, unused9, unused10, unused11)
}
