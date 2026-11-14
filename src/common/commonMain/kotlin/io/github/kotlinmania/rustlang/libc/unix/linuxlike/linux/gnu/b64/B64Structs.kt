@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.IpcPerm
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.SaFamilyT
import io.github.kotlinmania.rustlang.libc.Timeval

public data class SigsetT(
    public var value: ULongArray = ULongArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SigsetT) return false
        if (!this.value.contentEquals(other.value)) return false
        return true
    }

    override fun hashCode(): Int = this.value.contentHashCode()

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

public data class Sysinfo(
    public var uptime: Long = 0L,
    public var loads: ULongArray = ULongArray(3),
    public var totalram: ULong = 0uL,
    public var freeram: ULong = 0uL,
    public var sharedram: ULong = 0uL,
    public var bufferram: ULong = 0uL,
    public var totalswap: ULong = 0uL,
    public var freeswap: ULong = 0uL,
    public var procs: CUshort = 0u,
    public var pad: CUshort = 0u,
    public var totalhigh: ULong = 0uL,
    public var freehigh: ULong = 0uL,
    public var memUnit: CUint = 0u,
    public var f: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sysinfo) return false
        if (this.uptime != other.uptime) return false
        if (!this.loads.contentEquals(other.loads)) return false
        if (this.totalram != other.totalram) return false
        if (this.freeram != other.freeram) return false
        if (this.sharedram != other.sharedram) return false
        if (this.bufferram != other.bufferram) return false
        if (this.totalswap != other.totalswap) return false
        if (this.freeswap != other.freeswap) return false
        if (this.procs != other.procs) return false
        if (this.pad != other.pad) return false
        if (this.totalhigh != other.totalhigh) return false
        if (this.freehigh != other.freehigh) return false
        if (this.memUnit != other.memUnit) return false
        if (!this.f.contentEquals(other.f)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.uptime.hashCode()
        result = 31 * result + this.loads.contentHashCode()
        result = 31 * result + this.totalram.hashCode()
        result = 31 * result + this.freeram.hashCode()
        result = 31 * result + this.sharedram.hashCode()
        result = 31 * result + this.bufferram.hashCode()
        result = 31 * result + this.totalswap.hashCode()
        result = 31 * result + this.freeswap.hashCode()
        result = 31 * result + this.procs.hashCode()
        result = 31 * result + this.pad.hashCode()
        result = 31 * result + this.totalhigh.hashCode()
        result = 31 * result + this.freehigh.hashCode()
        result = 31 * result + this.memUnit.hashCode()
        result = 31 * result + this.f.contentHashCode()
        return result
    }

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

public data class MsqidDs(
    public var msgPerm: IpcPerm = IpcPerm(),
    public var msgStime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var msgRtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var msgCtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var msgCbytes: ULong = 0uL,
    public var msgQnum: io.github.kotlinmania.rustlang.libc.MsgqnumT = 0uL,
    public var msgQbytes: io.github.kotlinmania.rustlang.libc.MsglenT = 0uL,
    public var msgLspid: PidT = 0,
    public var msgLrpid: PidT = 0,
    public var glibcReserved4: ULong = 0uL,
    public var glibcReserved5: ULong = 0uL,
)

public data class SemidDs(
    public var semPerm: io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.IpcPerm = io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.IpcPerm(),
    public var semOtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var reserved: SyscallUlongT = 0uL,
    public var semCtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var reserved2: SyscallUlongT = 0uL,
    public var semNsems: SyscallUlongT = 0uL,
    public var glibcReserved3: SyscallUlongT = 0uL,
    public var glibcReserved4: SyscallUlongT = 0uL,
)

// net/iucv/iucv.h
public data class SockaddrIucv(
    public var siucvFamily: SaFamilyT = 0u,
    public var siucvPort: UShort = 0u,
    // unused inPortT
    public var siucvAddr: UInt = 0u,
    // unused inAddrT
    public var siucvNodeid: UByteArray = UByteArray(8),
    public var siucvUserId: UByteArray = UByteArray(8),
    public var siucvName: UByteArray = UByteArray(8),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrIucv) return false
        if (this.siucvFamily != other.siucvFamily) return false
        if (this.siucvPort != other.siucvPort) return false
        if (this.siucvAddr != other.siucvAddr) return false
        if (!this.siucvNodeid.contentEquals(other.siucvNodeid)) return false
        if (!this.siucvUserId.contentEquals(other.siucvUserId)) return false
        if (!this.siucvName.contentEquals(other.siucvName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siucvFamily.hashCode()
        result = 31 * result + this.siucvPort.hashCode()
        result = 31 * result + this.siucvAddr.hashCode()
        result = 31 * result + this.siucvNodeid.contentHashCode()
        result = 31 * result + this.siucvUserId.contentHashCode()
        result = 31 * result + this.siucvName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIucv> =
            object : TypeLayout<SockaddrIucv> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SockaddrIucv =
                    SockaddrIucv(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIucv) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.siucvFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.siucvPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.siucvAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.siucvNodeid)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 16L, value.siucvUserId)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 24L, value.siucvName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIucv =
                    SockaddrIucv(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIucv) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.siucvFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.siucvPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.siucvAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.siucvNodeid)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 16, value.siucvUserId)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 24, value.siucvName)
                }
            }
    }
}

public class Timex(
    public var modes: CUint = 0u,
    public var offset: CLong = 0L,
    public var freq: CLong = 0L,
    public var maxerror: CLong = 0L,
    public var esterror: CLong = 0L,
    public var status: CInt = 0,
    public var constant: CLong = 0L,
    public var precision: CLong = 0L,
    public var tolerance: CLong = 0L,
    public var time: Timeval = Timeval(),
    public var tick: CLong = 0L,
    public var ppsfreq: CLong = 0L,
    public var jitter: CLong = 0L,
    public var shift: CInt = 0,
    public var stabil: CLong = 0L,
    public var jitcnt: CLong = 0L,
    public var calcnt: CLong = 0L,
    public var errcnt: CLong = 0L,
    public var stbcnt: CLong = 0L,
    public var tai: CInt = 0,
    public var unused1: Int = 0,
    public var unused2: Int = 0,
    public var unused3: Int = 0,
    public var unused4: Int = 0,
    public var unused5: Int = 0,
    public var unused6: Int = 0,
    public var unused7: Int = 0,
    public var unused8: Int = 0,
    public var unused9: Int = 0,
    public var unused10: Int = 0,
    public var unused11: Int = 0,
) {
    public fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, time, tick, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt, tai, unused1, unused2, unused3, unused4, unused5, unused6, unused7, unused8, unused9, unused10, unused11)
}
