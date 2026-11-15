@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.ClockT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.RlimT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.SusecondsT

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
public open class DIR(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): DIR = DIR(data, marker)

    // Deprecated impls: see #5296
}

// Note(exhaustive): review for 1.0
public open class Group internal constructor(
    grNameCtorArg: MutPtr,
    grPasswdCtorArg: MutPtr,
    public var grGid: GidT,
    grMemCtorArg: MutPtr,
) {
    public var grName: MutPtr = grNameCtorArg
        internal set

    public var grPasswd: MutPtr = grPasswdCtorArg
        internal set

    public var grMem: MutPtr = grMemCtorArg
        internal set

    public open fun clone(): Group = Group(grName, grPasswd, grGid, grMem)

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
            grName: MutPtr,
            grPasswd: MutPtr,
            grGid: GidT,
            grMem: MutPtr,
        ): Group = Group(
            grNameCtorArg = grName,
            grPasswdCtorArg = grPasswd,
            grGid = grGid,
            grMemCtorArg = grMem,
        )

        public fun default(): Group = Group(grName = MutPtr(0L), grPasswd = MutPtr(0L), grGid = Default.default(), grMem = MutPtr(0L))
    }
}

// Note(exhaustive): review for 1.0
public open class Utimbuf(
    public var actime: TimeT,
    public var modtime: TimeT,
) {
    public open fun clone(): Utimbuf = Utimbuf(actime, modtime)

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

        public fun default(): Utimbuf = Utimbuf(actime = Default.default(), modtime = Default.default())
    }
}

// This one is not likely to ever change
public open class Timeval(
    public var tvSec: TimeT,
    public var tvUsec: SusecondsT,
) {
    public open fun clone(): Timeval = Timeval(tvSec, tvUsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Timeval> =
            object : TypeLayout<Timeval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Timeval) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timeval) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvUsec)
                }
            }

        public fun default(): Timeval = Timeval(tvSec = Default.default(), tvUsec = Default.default())
    }
}

public open class Timespec(
    public var tvSec: TimeT,
    public var tvNsec: CLong,
    public var pad0: UInt,
) {
    public open fun clone(): Timespec = Timespec(tvSec, tvNsec, pad0)

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

        public fun default(): Timespec = Timespec(tvSec = Default.default(), pad0 = Default.default(), tvNsec = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Rlimit(
    public var rlimCur: RlimT,
    public var rlimMax: RlimT,
) {
    public open fun clone(): Rlimit = Rlimit(rlimCur, rlimMax)

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

        public fun default(): Rlimit = Rlimit(rlimCur = Default.default(), rlimMax = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Rusage(
    public var ruUtime: Timeval,
    public var ruStime: Timeval,
    public var ruMaxrss: CLong,
    public var ruIxrss: CLong,
    public var ruIdrss: CLong,
    public var ruIsrss: CLong,
    public var ruMinflt: CLong,
    public var ruMajflt: CLong,
    public var ruNswap: CLong,
    public var ruInblock: CLong,
    public var ruOublock: CLong,
    public var ruMsgsnd: CLong,
    public var ruMsgrcv: CLong,
    public var ruNsignals: CLong,
    public var ruNvcsw: CLong,
    public var ruNivcsw: CLong,
    public var reserved: LongArray,
) {
    public open fun clone(): Rusage = Rusage(ruUtime, ruStime, ruMaxrss, ruIxrss, ruIdrss, ruIsrss, ruMinflt, ruMajflt, ruNswap, ruInblock, ruOublock, ruMsgsnd, ruMsgrcv, ruNsignals, ruNvcsw, ruNivcsw, reserved)

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

        public fun default(): Rusage = Rusage(ruUtime = Default.default(), ruStime = Default.default(), ruMaxrss = Default.default(), pad1 = Default.default(), ruIxrss = Default.default(), pad2 = Default.default(), ruIdrss = Default.default(), pad3 = Default.default(), ruIsrss = Default.default(), pad4 = Default.default(), ruMinflt = Default.default(), pad5 = Default.default(), ruMajflt = Default.default(), pad6 = Default.default(), ruNswap = Default.default(), pad7 = Default.default(), ruInblock = Default.default(), pad8 = Default.default(), ruOublock = Default.default(), pad9 = Default.default(), ruMsgsnd = Default.default(), pad10 = Default.default(), ruMsgrcv = Default.default(), pad11 = Default.default(), ruNsignals = Default.default(), pad12 = Default.default(), ruNvcsw = Default.default(), pad13 = Default.default(), ruNivcsw = Default.default(), pad14 = Default.default(), reserved = Default.default())
    }
}

public open class Ipv6Mreq(
    public var ipv6mrMultiaddr: In6Addr,
    public var ipv6mrInterface: CInt,
) {
    public open fun clone(): Ipv6Mreq = Ipv6Mreq(ipv6mrMultiaddr, ipv6mrInterface)

    public companion object {
        public fun default(): Ipv6Mreq = Ipv6Mreq(ipv6mrMultiaddr = Default.default(), ipv6mrInterface = Default.default())
    }
}

public open class Hostent internal constructor(
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

    public open fun clone(): Hostent = Hostent(hName, hAliases, hAddrtype, hLength, hAddrList)

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
            hName: MutPtr,
            hAliases: MutPtr,
            hAddrtype: CInt,
            hLength: CInt,
            hAddrList: MutPtr,
        ): Hostent = Hostent(
            hNameCtorArg = hName,
            hAliasesCtorArg = hAliases,
            hAddrtype = hAddrtype,
            hLength = hLength,
            hAddrListCtorArg = hAddrList,
        )

        public fun default(): Hostent = Hostent(hName = MutPtr(0L), hAliases = MutPtr(0L), hAddrtype = Default.default(), hLength = Default.default(), hAddrList = MutPtr(0L))
    }
}

// Shouldn't ever change
public open class Iovec internal constructor(
    iovBaseCtorArg: MutPtr,
    public var iovLen: SizeT,
) {
    public var iovBase: MutPtr = iovBaseCtorArg
        internal set

    public open fun clone(): Iovec = Iovec(iovBase, iovLen)

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
            iovBase: MutPtr,
            iovLen: SizeT,
        ): Iovec = Iovec(
            iovBaseCtorArg = iovBase,
            iovLen = iovLen,
        )

        public fun default(): Iovec = Iovec(iovBase = MutPtr(0L), iovLen = Default.default())
    }
}

public open class Pollfd(
    public var fd: CInt,
    public var events: CShort,
    public var revents: CShort,
) {
    public open fun clone(): Pollfd = Pollfd(fd, events, revents)

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

        public fun default(): Pollfd = Pollfd(fd = Default.default(), events = Default.default(), revents = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Winsize(
    public var wsRow: CUshort,
    public var wsCol: CUshort,
    public var wsXpixel: CUshort,
    public var wsYpixel: CUshort,
) {
    public open fun clone(): Winsize = Winsize(wsRow, wsCol, wsXpixel, wsYpixel)

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

        public fun default(): Winsize = Winsize(wsRow = Default.default(), wsCol = Default.default(), wsXpixel = Default.default(), wsYpixel = Default.default())
    }
}

public open class Linger(
    public var lOnoff: CInt,
    public var lLinger: CInt,
) {
    public open fun clone(): Linger = Linger(lOnoff, lLinger)

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

        public fun default(): Linger = Linger(lOnoff = Default.default(), lLinger = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Sigval internal constructor(
    sivalPtrCtorArg: MutPtr,
) {
    public var sivalPtr: MutPtr = sivalPtrCtorArg
        internal set

    public open fun clone(): Sigval = Sigval(sivalPtr)

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
            sivalPtr: MutPtr,
        ): Sigval = Sigval(
            sivalPtrCtorArg = sivalPtr,
        )

        public fun default(): Sigval = Sigval(sivalPtr = MutPtr(0L))
    }
}

// Note(exhaustive): review for 1.0
public open class Itimerval(
    public var itInterval: Timeval,
    public var itValue: Timeval,
) {
    public open fun clone(): Itimerval = Itimerval(itInterval, itValue)

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

        public fun default(): Itimerval = Itimerval(itInterval = Default.default(), itValue = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Tms(
    public var tmsUtime: ClockT,
    public var tmsStime: ClockT,
    public var tmsCutime: ClockT,
    public var tmsCstime: ClockT,
) {
    public open fun clone(): Tms = Tms(tmsUtime, tmsStime, tmsCutime, tmsCstime)

    public companion object {
        public fun typeLayout(): TypeLayout<Tms> =
            object : TypeLayout<Tms> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Tms =
                    Tms(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Tms) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tmsUtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tmsStime)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.tmsCutime)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.tmsCstime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tms =
                    Tms(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tms) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tmsUtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tmsStime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.tmsCutime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.tmsCstime)
                }
            }

        public fun default(): Tms = Tms(tmsUtime = Default.default(), tmsStime = Default.default(), tmsCutime = Default.default(), tmsCstime = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Servent internal constructor(
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

    public open fun clone(): Servent = Servent(sName, sAliases, sPort, sProto)

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
            sName: MutPtr,
            sAliases: MutPtr,
            sPort: CInt,
            sProto: MutPtr,
        ): Servent = Servent(
            sNameCtorArg = sName,
            sAliasesCtorArg = sAliases,
            sPort = sPort,
            sProtoCtorArg = sProto,
        )

        public fun default(): Servent = Servent(sName = MutPtr(0L), sAliases = MutPtr(0L), sPort = Default.default(), sProto = MutPtr(0L))
    }
}

// Note(exhaustive): review for 1.0
public open class Protoent internal constructor(
    pNameCtorArg: MutPtr,
    pAliasesCtorArg: MutPtr,
    public var pProto: CInt,
) {
    public var pName: MutPtr = pNameCtorArg
        internal set

    public var pAliases: MutPtr = pAliasesCtorArg
        internal set

    public open fun clone(): Protoent = Protoent(pName, pAliases, pProto)

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
            pName: MutPtr,
            pAliases: MutPtr,
            pProto: CInt,
        ): Protoent = Protoent(
            pNameCtorArg = pName,
            pAliasesCtorArg = pAliases,
            pProto = pProto,
        )

        public fun default(): Protoent = Protoent(pName = MutPtr(0L), pAliases = MutPtr(0L), pProto = Default.default())
    }
}

public open class In6Addr internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var s6Addr: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(s6Addr: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(s6Addr, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))

    public fun pointerLayout(): TypeLayout<In6Addr> = TransparentTypeLayout(fieldLayout, { ptr, layout -> In6Addr(ptr, layout) }, { it.s6Addr })

    public open fun clone(): In6Addr = In6Addr(s6Addr, fieldLayout)

    public companion object {
        public operator fun invoke(s6Addr: UByteArray, fieldLayout: TypeLayout<UByteArray>): In6Addr = In6Addr(s6Addr, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): In6Addr = In6Addr(fieldStorage, fieldLayout)

        public fun default(fieldLayout: TypeLayout<UByteArray>): In6Addr = In6Addr(s6Addr = Default.default(), fieldLayout = PrimitiveArrayLayouts.uByteArray((16).toInt()))
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
public open class FposT(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): FposT = FposT(data, marker)
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
public open class FILE(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): FILE = FILE(data, marker)
}
