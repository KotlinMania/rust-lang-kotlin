@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*

// Special handling for all print and scan type functions because of https://github.com/rust-lang/libc/issues/2860

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
public open class Timezone(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): Timezone = Timezone(data, marker)
}

// note this is the struct called stat64 in Windows. Not stat, nor stati64.
public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stMode: UShort,
    public var stNlink: CShort,
    public var stUid: CShort,
    public var stGid: CShort,
    public var stRdev: DevT,
    public var stSize: Long,
    public var stAtime: Time64T,
    public var stMtime: Time64T,
    public var stCtime: Time64T,
) {
    public open fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, stSize, stAtime, stMtime, stCtime)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                        (PrimitiveLayouts.I16).readNative(addr + 10L),
                        (PrimitiveLayouts.I16).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.stIno)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.stMode)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.stNlink)
                    (PrimitiveLayouts.I16).writeNative(addr + 10L, value.stUid)
                    (PrimitiveLayouts.I16).writeNative(addr + 12L, value.stGid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stCtime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.stIno)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.stMode)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.stNlink)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 10, value.stUid)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 12, value.stGid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stCtime)
                }
            }
    }
}

// note that this is called utimbuf64 in Windows
public open class Utimbuf(
    public var actime: Time64T,
    public var modtime: Time64T,
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
    }
}

public open class Tm(
    public var tmSec: CInt,
    public var tmMin: CInt,
    public var tmHour: CInt,
    public var tmMday: CInt,
    public var tmMon: CInt,
    public var tmYear: CInt,
    public var tmWday: CInt,
    public var tmYday: CInt,
    public var tmIsdst: CInt,
) {
    public open fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst)

    public companion object {
        public fun typeLayout(): TypeLayout<Tm> =
            object : TypeLayout<Tm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 36L
                override fun readNative(addr: Long): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Tm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tmSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tmMin)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.tmHour)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.tmMday)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.tmMon)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.tmYear)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.tmWday)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.tmYday)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.tmIsdst)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tmSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tmMin)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.tmHour)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.tmMday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.tmMon)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.tmYear)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.tmWday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.tmYday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.tmIsdst)
                }
            }
    }
}

public open class Timeval(
    public var tvSec: CLong,
    public var tvUsec: CLong,
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
    }
}

public open class Timespec(
    public var tvSec: TimeT,
    public var tvNsec: CLong,
) {
    public open fun clone(): Timespec = Timespec(tvSec, tvNsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Timespec> =
            object : TypeLayout<Timespec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Timespec) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvNsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timespec) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvNsec)
                }
            }
    }
}

public open class Sockaddr(
    public var saFamily: CUshort,
    public var saData: UByteArray,
) {
    public open fun clone(): Sockaddr = Sockaddr(saFamily, saData)

    public companion object {
        public fun typeLayout(): TypeLayout<Sockaddr> =
            object : TypeLayout<Sockaddr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.saData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.saData)
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
public open class FILE(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): FILE = FILE(data, marker)
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
