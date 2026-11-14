@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.ClockT
import io.github.kotlinmania.rustlang.libc.SusecondsT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.RlimT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.TimeT

// Note(1.0): We want to remove these directives and instead expect that no-std users add their
// own link configuration when required, rather than unconditionally linking everything that may
// possibly be needed.

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
public data class DIR(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
) {
    // Deprecated impls: see #5296
}

// Note(exhaustive): review for 1.0
public class Group internal constructor(
    grNameCtorArg: MutPtr,
    grPasswdCtorArg: MutPtr,
    public var grGid: io.github.kotlinmania.rustlang.libc.GidT,
    grMemCtorArg: MutPtr,
) {
    public var grName: MutPtr = grNameCtorArg
        internal set

    public var grPasswd: MutPtr = grPasswdCtorArg
        internal set

    public var grMem: MutPtr = grMemCtorArg
        internal set

    public constructor() : this(
        grNameCtorArg = MutPtr(0L),
        grPasswdCtorArg = MutPtr(0L),
        grGid = 0,
        grMemCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.grName

    public operator fun component2(): MutPtr = this.grPasswd

    public operator fun component3(): io.github.kotlinmania.rustlang.libc.GidT = this.grGid

    public operator fun component4(): MutPtr = this.grMem

    public fun copy(grName: MutPtr = this.grName, grPasswd: MutPtr = this.grPasswd, grGid: io.github.kotlinmania.rustlang.libc.GidT = this.grGid, grMem: MutPtr = this.grMem): Group = Group(grNameCtorArg = grName, grPasswdCtorArg = grPasswd, grGid = grGid, grMemCtorArg = grMem)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false
        if (this.grName != other.grName) return false
        if (this.grPasswd != other.grPasswd) return false
        if (this.grGid != other.grGid) return false
        if (this.grMem != other.grMem) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.grName.hashCode()
        result = 31 * result + this.grPasswd.hashCode()
        result = 31 * result + this.grGid.hashCode()
        result = 31 * result + this.grMem.hashCode()
        return result
    }

    public fun clone(): Group = Group(grName, grPasswd, grGid, grMem)

    public companion object {
        public fun typeLayout(): TypeLayout<Group> =
            object : TypeLayout<Group> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Group =
                    Group(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Group) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.grName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.grPasswd)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.grGid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.grMem)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Group =
                    Group(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Group) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.grName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.grPasswd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.grGid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.grMem)
                }
            }

        public operator fun invoke(
            grName: MutPtr = MutPtr(0L),
            grPasswd: MutPtr = MutPtr(0L),
            grGid: io.github.kotlinmania.rustlang.libc.GidT = 0,
            grMem: MutPtr = MutPtr(0L),
        ): Group = Group(
            grNameCtorArg = grName,
            grPasswdCtorArg = grPasswd,
            grGid = grGid,
            grMemCtorArg = grMem,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class Utimbuf(
    public var actime: TimeT = 0L,
    public var modtime: TimeT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Utimbuf> =
            object : TypeLayout<Utimbuf> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Utimbuf =
                    Utimbuf(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Utimbuf) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.actime)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.modtime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Utimbuf =
                    Utimbuf(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Utimbuf) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.actime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.modtime)
                }
            }
    }
}

// This one is not likely to ever change
public data class Timeval(
    public var tvSec: TimeT = 0L,
    public var tvUsec: SusecondsT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Timeval> =
            object : TypeLayout<Timeval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Timeval) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timeval) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.tvUsec)
                }
            }
    }
}

public data class Timespec(
    public var tvSec: TimeT = 0L,
    public var tvNsec: CLong = 0L,
    public var pad0: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Timespec> =
            object : TypeLayout<Timespec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Timespec) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvNsec)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pad0)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timespec) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvNsec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pad0)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Rlimit(
    public var rlimCur: RlimT = 0uL,
    public var rlimMax: RlimT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Rlimit> =
            object : TypeLayout<Rlimit> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Rlimit =
                    Rlimit(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Rlimit) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rlimCur)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rlimMax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rlimit =
                    Rlimit(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rlimit) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rlimCur)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rlimMax)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Rusage(
    public var ruUtime: Timeval = Timeval(),
    public var ruStime: Timeval = Timeval(),
    public var ruMaxrss: CLong = 0L,
    public var ruIxrss: CLong = 0L,
    public var ruIdrss: CLong = 0L,
    public var ruIsrss: CLong = 0L,
    public var ruMinflt: CLong = 0L,
    public var ruMajflt: CLong = 0L,
    public var ruNswap: CLong = 0L,
    public var ruInblock: CLong = 0L,
    public var ruOublock: CLong = 0L,
    public var ruMsgsnd: CLong = 0L,
    public var ruMsgrcv: CLong = 0L,
    public var ruNsignals: CLong = 0L,
    public var ruNvcsw: CLong = 0L,
    public var ruNivcsw: CLong = 0L,
    public var reserved: LongArray = LongArray(16),
) {
    public fun clone(): Rusage = Rusage(ruUtime, ruStime, ruMaxrss, ruIxrss, ruIdrss, ruIsrss, ruMinflt, ruMajflt, ruNswap, ruInblock, ruOublock, ruMsgsnd, ruMsgrcv, ruNsignals, ruNvcsw, ruNivcsw, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Rusage> =
            object : TypeLayout<Rusage> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): Rusage =
                    Rusage(
                        (Timeval.typeLayout()).readNative(addr),
                        (Timeval.typeLayout()).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.I64).readNative(addr + 120L),
                        (PrimitiveLayouts.I64).readNative(addr + 128L),
                        (PrimitiveLayouts.I64).readNative(addr + 136L),
                        (PrimitiveArrayLayouts.longArray(16)).readNative(addr + 144L),
                    )
                override fun writeNative(addr: Long, value: Rusage) {
                    (Timeval.typeLayout()).writeNative(addr, value.ruUtime)
                    (Timeval.typeLayout()).writeNative(addr + 16L, value.ruStime)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.ruMaxrss)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.ruIxrss)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.ruIdrss)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.ruIsrss)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.ruMinflt)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.ruMajflt)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.ruNswap)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.ruInblock)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.ruOublock)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.ruMsgsnd)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.ruMsgrcv)
                    (PrimitiveLayouts.I64).writeNative(addr + 120L, value.ruNsignals)
                    (PrimitiveLayouts.I64).writeNative(addr + 128L, value.ruNvcsw)
                    (PrimitiveLayouts.I64).writeNative(addr + 136L, value.ruNivcsw)
                    (PrimitiveArrayLayouts.longArray(16)).writeNative(addr + 144L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rusage =
                    Rusage(
                        (Timeval.typeLayout()).readBytes(buf, offset),
                        (Timeval.typeLayout()).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 136),
                        (PrimitiveArrayLayouts.longArray(16)).readBytes(buf, offset + 144),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rusage) {
                    (Timeval.typeLayout()).writeBytes(buf, offset, value.ruUtime)
                    (Timeval.typeLayout()).writeBytes(buf, offset + 16, value.ruStime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.ruMaxrss)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.ruIxrss)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.ruIdrss)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.ruIsrss)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.ruMinflt)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.ruMajflt)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.ruNswap)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.ruInblock)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.ruOublock)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.ruMsgsnd)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.ruMsgrcv)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 120, value.ruNsignals)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 128, value.ruNvcsw)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 136, value.ruNivcsw)
                    (PrimitiveArrayLayouts.longArray(16)).writeBytes(buf, offset + 144, value.reserved)
                }
            }
    }
}

public data class Ipv6Mreq(
    public var ipv6mrMultiaddr: In6Addr = In6Addr(),
    public var ipv6mrInterface: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Ipv6Mreq> =
            object : TypeLayout<Ipv6Mreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): Ipv6Mreq =
                    Ipv6Mreq(
                        (In6Addr.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Ipv6Mreq) {
                    (In6Addr.typeLayout()).writeNative(addr, value.ipv6mrMultiaddr)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.ipv6mrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ipv6Mreq =
                    Ipv6Mreq(
                        (In6Addr.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ipv6Mreq) {
                    (In6Addr.typeLayout()).writeBytes(buf, offset, value.ipv6mrMultiaddr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.ipv6mrInterface)
                }
            }
    }
}

public class Hostent internal constructor(
    hNameCtorArg: MutPtr,
    hAliasesCtorArg: MutPtr,
    public var hAddrtype: CInt,
    public var hLength: CInt,
    hAddrListCtorArg: MutPtr,
) {
    public var hName: MutPtr = hNameCtorArg
        internal set

    public var hAliases: MutPtr = hAliasesCtorArg
        internal set

    public var hAddrList: MutPtr = hAddrListCtorArg
        internal set

    public constructor() : this(
        hNameCtorArg = MutPtr(0L),
        hAliasesCtorArg = MutPtr(0L),
        hAddrtype = 0,
        hLength = 0,
        hAddrListCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.hName

    public operator fun component2(): MutPtr = this.hAliases

    public operator fun component3(): CInt = this.hAddrtype

    public operator fun component4(): CInt = this.hLength

    public operator fun component5(): MutPtr = this.hAddrList

    public fun copy(hName: MutPtr = this.hName, hAliases: MutPtr = this.hAliases, hAddrtype: CInt = this.hAddrtype, hLength: CInt = this.hLength, hAddrList: MutPtr = this.hAddrList): Hostent = Hostent(hNameCtorArg = hName, hAliasesCtorArg = hAliases, hAddrtype = hAddrtype, hLength = hLength, hAddrListCtorArg = hAddrList)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hostent) return false
        if (this.hName != other.hName) return false
        if (this.hAliases != other.hAliases) return false
        if (this.hAddrtype != other.hAddrtype) return false
        if (this.hLength != other.hLength) return false
        if (this.hAddrList != other.hAddrList) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.hName.hashCode()
        result = 31 * result + this.hAliases.hashCode()
        result = 31 * result + this.hAddrtype.hashCode()
        result = 31 * result + this.hLength.hashCode()
        result = 31 * result + this.hAddrList.hashCode()
        return result
    }

    public fun clone(): Hostent = Hostent(hName, hAliases, hAddrtype, hLength, hAddrList)

    public companion object {
        public fun typeLayout(): TypeLayout<Hostent> =
            object : TypeLayout<Hostent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Hostent =
                    Hostent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Hostent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.hName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.hAliases)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.hAddrtype)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.hLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.hAddrList)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Hostent =
                    Hostent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Hostent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.hName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.hAliases)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.hAddrtype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.hLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.hAddrList)
                }
            }

        public operator fun invoke(
            hName: MutPtr = MutPtr(0L),
            hAliases: MutPtr = MutPtr(0L),
            hAddrtype: CInt = 0,
            hLength: CInt = 0,
            hAddrList: MutPtr = MutPtr(0L),
        ): Hostent = Hostent(
            hNameCtorArg = hName,
            hAliasesCtorArg = hAliases,
            hAddrtype = hAddrtype,
            hLength = hLength,
            hAddrListCtorArg = hAddrList,
        )
    }
}

// Shouldn't ever change
public class Iovec internal constructor(
    iovBaseCtorArg: MutPtr,
    public var iovLen: SizeT,
) {
    public var iovBase: MutPtr = iovBaseCtorArg
        internal set

    public constructor() : this(
        iovBaseCtorArg = MutPtr(0L),
        iovLen = 0uL,
    )

    public operator fun component1(): MutPtr = this.iovBase

    public operator fun component2(): SizeT = this.iovLen

    public fun copy(iovBase: MutPtr = this.iovBase, iovLen: SizeT = this.iovLen): Iovec = Iovec(iovBaseCtorArg = iovBase, iovLen = iovLen)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Iovec) return false
        if (this.iovBase != other.iovBase) return false
        if (this.iovLen != other.iovLen) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.iovBase.hashCode()
        result = 31 * result + this.iovLen.hashCode()
        return result
    }

    public fun clone(): Iovec = Iovec(iovBase, iovLen)

    public companion object {
        public fun typeLayout(): TypeLayout<Iovec> =
            object : TypeLayout<Iovec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.iovBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.iovLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.iovBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.iovLen)
                }
            }

        public operator fun invoke(
            iovBase: MutPtr = MutPtr(0L),
            iovLen: SizeT = 0uL,
        ): Iovec = Iovec(
            iovBaseCtorArg = iovBase,
            iovLen = iovLen,
        )
    }
}

public data class Pollfd(
    public var fd: CInt = 0,
    public var events: CShort = 0,
    public var revents: CShort = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Pollfd> =
            object : TypeLayout<Pollfd> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Pollfd =
                    Pollfd(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Pollfd) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.fd)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.events)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.revents)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Pollfd =
                    Pollfd(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Pollfd) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.fd)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.events)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.revents)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Winsize(
    public var wsRow: CUshort = 0u,
    public var wsCol: CUshort = 0u,
    public var wsXpixel: CUshort = 0u,
    public var wsYpixel: CUshort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Winsize> =
            object : TypeLayout<Winsize> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Winsize =
                    Winsize(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Winsize) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.wsRow)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.wsCol)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.wsXpixel)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.wsYpixel)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Winsize =
                    Winsize(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Winsize) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.wsRow)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.wsCol)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.wsXpixel)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.wsYpixel)
                }
            }
    }
}

public data class Linger(
    public var lOnoff: CInt = 0,
    public var lLinger: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Linger> =
            object : TypeLayout<Linger> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Linger =
                    Linger(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Linger) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.lOnoff)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.lLinger)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Linger =
                    Linger(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Linger) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.lOnoff)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.lLinger)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Sigval internal constructor(
    sivalPtrCtorArg: MutPtr,
) {
    public var sivalPtr: MutPtr = sivalPtrCtorArg
        internal set

    public constructor() : this(
        sivalPtrCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.sivalPtr

    public fun copy(sivalPtr: MutPtr = this.sivalPtr): Sigval = Sigval(sivalPtrCtorArg = sivalPtr)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sigval) return false
        if (this.sivalPtr != other.sivalPtr) return false
        return true
    }

    override fun hashCode(): Int = this.sivalPtr.hashCode()

    public fun clone(): Sigval = Sigval(sivalPtr)

    public companion object {
        public fun typeLayout(): TypeLayout<Sigval> =
            object : TypeLayout<Sigval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Sigval =
                    Sigval(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Sigval) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.sivalPtr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sigval =
                    Sigval(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sigval) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.sivalPtr)
                }
            }

        public operator fun invoke(
            sivalPtr: MutPtr = MutPtr(0L),
        ): Sigval = Sigval(
            sivalPtrCtorArg = sivalPtr,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class Itimerval(
    public var itInterval: io.github.kotlinmania.rustlang.libc.Timeval = io.github.kotlinmania.rustlang.libc.Timeval(),
    public var itValue: io.github.kotlinmania.rustlang.libc.Timeval = io.github.kotlinmania.rustlang.libc.Timeval(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Itimerval> =
            object : TypeLayout<Itimerval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Itimerval =
                    Itimerval(
                        (Timeval.typeLayout()).readNative(addr),
                        (Timeval.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Itimerval) {
                    (Timeval.typeLayout()).writeNative(addr, value.itInterval)
                    (Timeval.typeLayout()).writeNative(addr + 16L, value.itValue)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Itimerval =
                    Itimerval(
                        (Timeval.typeLayout()).readBytes(buf, offset),
                        (Timeval.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Itimerval) {
                    (Timeval.typeLayout()).writeBytes(buf, offset, value.itInterval)
                    (Timeval.typeLayout()).writeBytes(buf, offset + 16, value.itValue)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Tms(
    public var tmsUtime: ClockT = 0uL,
    public var tmsStime: ClockT = 0uL,
    public var tmsCutime: ClockT = 0uL,
    public var tmsCstime: ClockT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Tms> =
            object : TypeLayout<Tms> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Tms =
                    Tms(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Tms) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.tmsUtime)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.tmsStime)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.tmsCutime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.tmsCstime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tms =
                    Tms(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tms) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.tmsUtime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.tmsStime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.tmsCutime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.tmsCstime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Servent internal constructor(
    sNameCtorArg: MutPtr,
    sAliasesCtorArg: MutPtr,
    public var sPort: CInt,
    sProtoCtorArg: MutPtr,
) {
    public var sName: MutPtr = sNameCtorArg
        internal set

    public var sAliases: MutPtr = sAliasesCtorArg
        internal set

    public var sProto: MutPtr = sProtoCtorArg
        internal set

    public constructor() : this(
        sNameCtorArg = MutPtr(0L),
        sAliasesCtorArg = MutPtr(0L),
        sPort = 0,
        sProtoCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.sName

    public operator fun component2(): MutPtr = this.sAliases

    public operator fun component3(): CInt = this.sPort

    public operator fun component4(): MutPtr = this.sProto

    public fun copy(sName: MutPtr = this.sName, sAliases: MutPtr = this.sAliases, sPort: CInt = this.sPort, sProto: MutPtr = this.sProto): Servent = Servent(sNameCtorArg = sName, sAliasesCtorArg = sAliases, sPort = sPort, sProtoCtorArg = sProto)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Servent) return false
        if (this.sName != other.sName) return false
        if (this.sAliases != other.sAliases) return false
        if (this.sPort != other.sPort) return false
        if (this.sProto != other.sProto) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sName.hashCode()
        result = 31 * result + this.sAliases.hashCode()
        result = 31 * result + this.sPort.hashCode()
        result = 31 * result + this.sProto.hashCode()
        return result
    }

    public fun clone(): Servent = Servent(sName, sAliases, sPort, sProto)

    public companion object {
        public fun typeLayout(): TypeLayout<Servent> =
            object : TypeLayout<Servent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Servent =
                    Servent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Servent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.sName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.sAliases)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.sPort)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.sProto)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Servent =
                    Servent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Servent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.sName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.sAliases)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.sPort)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.sProto)
                }
            }

        public operator fun invoke(
            sName: MutPtr = MutPtr(0L),
            sAliases: MutPtr = MutPtr(0L),
            sPort: CInt = 0,
            sProto: MutPtr = MutPtr(0L),
        ): Servent = Servent(
            sNameCtorArg = sName,
            sAliasesCtorArg = sAliases,
            sPort = sPort,
            sProtoCtorArg = sProto,
        )
    }
}

// Note(exhaustive): review for 1.0
public class Protoent internal constructor(
    pNameCtorArg: MutPtr,
    pAliasesCtorArg: MutPtr,
    public var pProto: CInt,
) {
    public var pName: MutPtr = pNameCtorArg
        internal set

    public var pAliases: MutPtr = pAliasesCtorArg
        internal set

    public constructor() : this(
        pNameCtorArg = MutPtr(0L),
        pAliasesCtorArg = MutPtr(0L),
        pProto = 0,
    )

    public operator fun component1(): MutPtr = this.pName

    public operator fun component2(): MutPtr = this.pAliases

    public operator fun component3(): CInt = this.pProto

    public fun copy(pName: MutPtr = this.pName, pAliases: MutPtr = this.pAliases, pProto: CInt = this.pProto): Protoent = Protoent(pNameCtorArg = pName, pAliasesCtorArg = pAliases, pProto = pProto)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Protoent) return false
        if (this.pName != other.pName) return false
        if (this.pAliases != other.pAliases) return false
        if (this.pProto != other.pProto) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pName.hashCode()
        result = 31 * result + this.pAliases.hashCode()
        result = 31 * result + this.pProto.hashCode()
        return result
    }

    public fun clone(): Protoent = Protoent(pName, pAliases, pProto)

    public companion object {
        public fun typeLayout(): TypeLayout<Protoent> =
            object : TypeLayout<Protoent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Protoent =
                    Protoent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Protoent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.pName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.pAliases)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pProto)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Protoent =
                    Protoent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Protoent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.pName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.pAliases)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pProto)
                }
            }

        public operator fun invoke(
            pName: MutPtr = MutPtr(0L),
            pAliases: MutPtr = MutPtr(0L),
            pProto: CInt = 0,
        ): Protoent = Protoent(
            pNameCtorArg = pName,
            pAliasesCtorArg = pAliases,
            pProto = pProto,
        )
    }
}

public data class In6Addr(
    public var s6Addr: UByteArray = UByteArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is In6Addr) return false
        if (!this.s6Addr.contentEquals(other.s6Addr)) return false
        return true
    }

    override fun hashCode(): Int = this.s6Addr.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<In6Addr> =
            object : TypeLayout<In6Addr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): In6Addr =
                    In6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: In6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.s6Addr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): In6Addr =
                    In6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: In6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.s6Addr)
                }
            }
    }
}

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
public data class FposT(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

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
public data class FILE(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)
