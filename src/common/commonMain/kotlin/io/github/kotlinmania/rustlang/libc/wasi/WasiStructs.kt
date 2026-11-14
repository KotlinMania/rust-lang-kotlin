@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.core.ffi.CChar
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

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
public data class LocaleStruct(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public class ClockidT internal constructor(
    valueCtorArg: ConstPtr,
) {
    public var value: ConstPtr = valueCtorArg
        internal set

    public var field0: ConstPtr
        get() = value
        set(v) {
            value = v
        }

    public constructor() : this(
        valueCtorArg = ConstPtr(0L),
    )

    public operator fun component1(): ConstPtr = this.value

    public fun copy(value: ConstPtr = this.value): ClockidT = ClockidT(valueCtorArg = value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClockidT) return false
        if (this.value != other.value) return false
        return true
    }

    override fun hashCode(): Int = this.value.hashCode()

    public fun clone(): ClockidT = ClockidT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<ClockidT> =
            object : TypeLayout<ClockidT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ClockidT =
                    ClockidT(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ClockidT) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ClockidT =
                    ClockidT(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ClockidT) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: ConstPtr = ConstPtr(0L),
        ): ClockidT = ClockidT(
            valueCtorArg = value,
        )
    }
}

public data class FposT(
    public var data: UByteArray = UByteArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FposT) return false
        if (!this.data.contentEquals(other.data)) return false
        return true
    }

    override fun hashCode(): Int = this.data.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<FposT> =
            object : TypeLayout<FposT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): FposT =
                    FposT(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FposT) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FposT =
                    FposT(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FposT) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.data)
                }
            }
    }
}

public class Tm internal constructor(
    public var tmSec: CInt,
    public var tmMin: CInt,
    public var tmHour: CInt,
    public var tmMday: CInt,
    public var tmMon: CInt,
    public var tmYear: CInt,
    public var tmWday: CInt,
    public var tmYday: CInt,
    public var tmIsdst: CInt,
    public var tmGmtoff: CInt,
    tmZoneCtorArg: ConstPtr,
    public var tmNsec: CInt,
) {
    public var tmZone: ConstPtr = tmZoneCtorArg
        internal set

    public constructor() : this(
        tmSec = 0,
        tmMin = 0,
        tmHour = 0,
        tmMday = 0,
        tmMon = 0,
        tmYear = 0,
        tmWday = 0,
        tmYday = 0,
        tmIsdst = 0,
        tmGmtoff = 0,
        tmZoneCtorArg = ConstPtr(0L),
        tmNsec = 0,
    )

    public operator fun component1(): CInt = this.tmSec

    public operator fun component2(): CInt = this.tmMin

    public operator fun component3(): CInt = this.tmHour

    public operator fun component4(): CInt = this.tmMday

    public operator fun component5(): CInt = this.tmMon

    public operator fun component6(): CInt = this.tmYear

    public operator fun component7(): CInt = this.tmWday

    public operator fun component8(): CInt = this.tmYday

    public operator fun component9(): CInt = this.tmIsdst

    public operator fun component10(): CInt = this.tmGmtoff

    public operator fun component11(): ConstPtr = this.tmZone

    public operator fun component12(): CInt = this.tmNsec

    public fun copy(tmSec: CInt = this.tmSec, tmMin: CInt = this.tmMin, tmHour: CInt = this.tmHour, tmMday: CInt = this.tmMday, tmMon: CInt = this.tmMon, tmYear: CInt = this.tmYear, tmWday: CInt = this.tmWday, tmYday: CInt = this.tmYday, tmIsdst: CInt = this.tmIsdst, tmGmtoff: CInt = this.tmGmtoff, tmZone: ConstPtr = this.tmZone, tmNsec: CInt = this.tmNsec): Tm = Tm(tmSec = tmSec, tmMin = tmMin, tmHour = tmHour, tmMday = tmMday, tmMon = tmMon, tmYear = tmYear, tmWday = tmWday, tmYday = tmYday, tmIsdst = tmIsdst, tmGmtoff = tmGmtoff, tmZoneCtorArg = tmZone, tmNsec = tmNsec)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tm) return false
        if (this.tmSec != other.tmSec) return false
        if (this.tmMin != other.tmMin) return false
        if (this.tmHour != other.tmHour) return false
        if (this.tmMday != other.tmMday) return false
        if (this.tmMon != other.tmMon) return false
        if (this.tmYear != other.tmYear) return false
        if (this.tmWday != other.tmWday) return false
        if (this.tmYday != other.tmYday) return false
        if (this.tmIsdst != other.tmIsdst) return false
        if (this.tmGmtoff != other.tmGmtoff) return false
        if (this.tmZone != other.tmZone) return false
        if (this.tmNsec != other.tmNsec) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.tmSec.hashCode()
        result = 31 * result + this.tmMin.hashCode()
        result = 31 * result + this.tmHour.hashCode()
        result = 31 * result + this.tmMday.hashCode()
        result = 31 * result + this.tmMon.hashCode()
        result = 31 * result + this.tmYear.hashCode()
        result = 31 * result + this.tmWday.hashCode()
        result = 31 * result + this.tmYday.hashCode()
        result = 31 * result + this.tmIsdst.hashCode()
        result = 31 * result + this.tmGmtoff.hashCode()
        result = 31 * result + this.tmZone.hashCode()
        result = 31 * result + this.tmNsec.hashCode()
        return result
    }

    public fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst, tmGmtoff, tmZone, tmNsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Tm> =
            object : TypeLayout<Tm> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
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
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
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
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.tmGmtoff)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 40L, value.tmZone)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.tmNsec)
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
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
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
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.tmGmtoff)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 40, value.tmZone)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.tmNsec)
                }
            }

        public operator fun invoke(
            tmSec: CInt = 0,
            tmMin: CInt = 0,
            tmHour: CInt = 0,
            tmMday: CInt = 0,
            tmMon: CInt = 0,
            tmYear: CInt = 0,
            tmWday: CInt = 0,
            tmYday: CInt = 0,
            tmIsdst: CInt = 0,
            tmGmtoff: CInt = 0,
            tmZone: ConstPtr = ConstPtr(0L),
            tmNsec: CInt = 0,
        ): Tm = Tm(
            tmSec = tmSec,
            tmMin = tmMin,
            tmHour = tmHour,
            tmMday = tmMday,
            tmMon = tmMon,
            tmYear = tmYear,
            tmWday = tmWday,
            tmYday = tmYday,
            tmIsdst = tmIsdst,
            tmGmtoff = tmGmtoff,
            tmZoneCtorArg = tmZone,
            tmNsec = tmNsec,
        )
    }
}

public data class Timeval(
    public var tvSec: TimeT = 0L,
    public var tvUsec: SusecondsT = 0L,
) {
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

public data class Timespec(
    public var tvSec: TimeT = 0L,
    public var tvNsec: CLong = 0L,
) {
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

public data class Tms(
    public var tmsUtime: ClockT = 0L,
    public var tmsStime: ClockT = 0L,
    public var tmsCutime: ClockT = 0L,
    public var tmsCstime: ClockT = 0L,
) {
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
    }
}

public data class Itimerspec(
    public var itInterval: Timespec = Timespec(),
    public var itValue: Timespec = Timespec(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Itimerspec> =
            object : TypeLayout<Itimerspec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Itimerspec =
                    Itimerspec(
                        (Timespec.typeLayout()).readNative(addr),
                        (Timespec.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Itimerspec) {
                    (Timespec.typeLayout()).writeNative(addr, value.itInterval)
                    (Timespec.typeLayout()).writeNative(addr + 16L, value.itValue)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Itimerspec =
                    Itimerspec(
                        (Timespec.typeLayout()).readBytes(buf, offset),
                        (Timespec.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Itimerspec) {
                    (Timespec.typeLayout()).writeBytes(buf, offset, value.itInterval)
                    (Timespec.typeLayout()).writeBytes(buf, offset + 16, value.itValue)
                }
            }
    }
}

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

public class Lconv internal constructor(
    decimalPointCtorArg: MutPtr,
    thousandsSepCtorArg: MutPtr,
    groupingCtorArg: MutPtr,
    intCurrSymbolCtorArg: MutPtr,
    currencySymbolCtorArg: MutPtr,
    monDecimalPointCtorArg: MutPtr,
    monThousandsSepCtorArg: MutPtr,
    monGroupingCtorArg: MutPtr,
    positiveSignCtorArg: MutPtr,
    negativeSignCtorArg: MutPtr,
    public var intFracDigits: CChar,
    public var fracDigits: CChar,
    public var pCsPrecedes: CChar,
    public var pSepBySpace: CChar,
    public var nCsPrecedes: CChar,
    public var nSepBySpace: CChar,
    public var pSignPosn: CChar,
    public var nSignPosn: CChar,
    public var intPCsPrecedes: CChar,
    public var intPSepBySpace: CChar,
    public var intNCsPrecedes: CChar,
    public var intNSepBySpace: CChar,
    public var intPSignPosn: CChar,
    public var intNSignPosn: CChar,
) {
    public var decimalPoint: MutPtr = decimalPointCtorArg
        internal set

    public var thousandsSep: MutPtr = thousandsSepCtorArg
        internal set

    public var grouping: MutPtr = groupingCtorArg
        internal set

    public var intCurrSymbol: MutPtr = intCurrSymbolCtorArg
        internal set

    public var currencySymbol: MutPtr = currencySymbolCtorArg
        internal set

    public var monDecimalPoint: MutPtr = monDecimalPointCtorArg
        internal set

    public var monThousandsSep: MutPtr = monThousandsSepCtorArg
        internal set

    public var monGrouping: MutPtr = monGroupingCtorArg
        internal set

    public var positiveSign: MutPtr = positiveSignCtorArg
        internal set

    public var negativeSign: MutPtr = negativeSignCtorArg
        internal set

    public constructor() : this(
        decimalPointCtorArg = MutPtr(0L),
        thousandsSepCtorArg = MutPtr(0L),
        groupingCtorArg = MutPtr(0L),
        intCurrSymbolCtorArg = MutPtr(0L),
        currencySymbolCtorArg = MutPtr(0L),
        monDecimalPointCtorArg = MutPtr(0L),
        monThousandsSepCtorArg = MutPtr(0L),
        monGroupingCtorArg = MutPtr(0L),
        positiveSignCtorArg = MutPtr(0L),
        negativeSignCtorArg = MutPtr(0L),
        intFracDigits = 0u,
        fracDigits = 0u,
        pCsPrecedes = 0u,
        pSepBySpace = 0u,
        nCsPrecedes = 0u,
        nSepBySpace = 0u,
        pSignPosn = 0u,
        nSignPosn = 0u,
        intPCsPrecedes = 0u,
        intPSepBySpace = 0u,
        intNCsPrecedes = 0u,
        intNSepBySpace = 0u,
        intPSignPosn = 0u,
        intNSignPosn = 0u,
    )

    public fun clone(): Lconv = Lconv(decimalPoint, thousandsSep, grouping, intCurrSymbol, currencySymbol, monDecimalPoint, monThousandsSep, monGrouping, positiveSign, negativeSign, intFracDigits, fracDigits, pCsPrecedes, pSepBySpace, nCsPrecedes, nSepBySpace, pSignPosn, nSignPosn, intPCsPrecedes, intPSepBySpace, intNCsPrecedes, intNSepBySpace, intPSignPosn, intNSignPosn)

    public companion object {
        public fun typeLayout(): TypeLayout<Lconv> =
            object : TypeLayout<Lconv> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.U8).readNative(addr + 80L),
                        (PrimitiveLayouts.U8).readNative(addr + 81L),
                        (PrimitiveLayouts.U8).readNative(addr + 82L),
                        (PrimitiveLayouts.U8).readNative(addr + 83L),
                        (PrimitiveLayouts.U8).readNative(addr + 84L),
                        (PrimitiveLayouts.U8).readNative(addr + 85L),
                        (PrimitiveLayouts.U8).readNative(addr + 86L),
                        (PrimitiveLayouts.U8).readNative(addr + 87L),
                        (PrimitiveLayouts.U8).readNative(addr + 88L),
                        (PrimitiveLayouts.U8).readNative(addr + 89L),
                        (PrimitiveLayouts.U8).readNative(addr + 90L),
                        (PrimitiveLayouts.U8).readNative(addr + 91L),
                        (PrimitiveLayouts.U8).readNative(addr + 92L),
                        (PrimitiveLayouts.U8).readNative(addr + 93L),
                    )
                override fun writeNative(addr: Long, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.negativeSign)
                    (PrimitiveLayouts.U8).writeNative(addr + 80L, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 81L, value.fracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 82L, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 83L, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 84L, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 85L, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 86L, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 87L, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 88L, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 89L, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 90L, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 91L, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 92L, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 93L, value.intNSignPosn)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 81),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 83),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 85),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 86),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 87),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 89),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 90),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 91),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 93),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.negativeSign)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 80, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 81, value.fracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 82, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 83, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 84, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 85, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 86, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 87, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 88, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 89, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 90, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 91, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 92, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 93, value.intNSignPosn)
                }
            }

        public operator fun invoke(
            decimalPoint: MutPtr = MutPtr(0L),
            thousandsSep: MutPtr = MutPtr(0L),
            grouping: MutPtr = MutPtr(0L),
            intCurrSymbol: MutPtr = MutPtr(0L),
            currencySymbol: MutPtr = MutPtr(0L),
            monDecimalPoint: MutPtr = MutPtr(0L),
            monThousandsSep: MutPtr = MutPtr(0L),
            monGrouping: MutPtr = MutPtr(0L),
            positiveSign: MutPtr = MutPtr(0L),
            negativeSign: MutPtr = MutPtr(0L),
            intFracDigits: CChar = 0u,
            fracDigits: CChar = 0u,
            pCsPrecedes: CChar = 0u,
            pSepBySpace: CChar = 0u,
            nCsPrecedes: CChar = 0u,
            nSepBySpace: CChar = 0u,
            pSignPosn: CChar = 0u,
            nSignPosn: CChar = 0u,
            intPCsPrecedes: CChar = 0u,
            intPSepBySpace: CChar = 0u,
            intNCsPrecedes: CChar = 0u,
            intNSepBySpace: CChar = 0u,
            intPSignPosn: CChar = 0u,
            intNSignPosn: CChar = 0u,
        ): Lconv = Lconv(
            decimalPointCtorArg = decimalPoint,
            thousandsSepCtorArg = thousandsSep,
            groupingCtorArg = grouping,
            intCurrSymbolCtorArg = intCurrSymbol,
            currencySymbolCtorArg = currencySymbol,
            monDecimalPointCtorArg = monDecimalPoint,
            monThousandsSepCtorArg = monThousandsSep,
            monGroupingCtorArg = monGrouping,
            positiveSignCtorArg = positiveSign,
            negativeSignCtorArg = negativeSign,
            intFracDigits = intFracDigits,
            fracDigits = fracDigits,
            pCsPrecedes = pCsPrecedes,
            pSepBySpace = pSepBySpace,
            nCsPrecedes = nCsPrecedes,
            nSepBySpace = nSepBySpace,
            pSignPosn = pSignPosn,
            nSignPosn = nSignPosn,
            intPCsPrecedes = intPCsPrecedes,
            intPSepBySpace = intPSepBySpace,
            intNCsPrecedes = intNCsPrecedes,
            intNSepBySpace = intNSepBySpace,
            intPSignPosn = intPSignPosn,
            intNSignPosn = intNSignPosn,
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

public data class Rusage(
    public var ruUtime: Timeval = Timeval(),
    public var ruStime: Timeval = Timeval(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Rusage> =
            object : TypeLayout<Rusage> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Rusage =
                    Rusage(
                        (Timeval.typeLayout()).readNative(addr),
                        (Timeval.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Rusage) {
                    (Timeval.typeLayout()).writeNative(addr, value.ruUtime)
                    (Timeval.typeLayout()).writeNative(addr + 16L, value.ruStime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rusage =
                    Rusage(
                        (Timeval.typeLayout()).readBytes(buf, offset),
                        (Timeval.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rusage) {
                    (Timeval.typeLayout()).writeBytes(buf, offset, value.ruUtime)
                    (Timeval.typeLayout()).writeBytes(buf, offset + 16, value.ruStime)
                }
            }
    }
}

public data class Stat(
    public var stDev: DevT = 0uL,
    public var stIno: InoT = 0uL,
    public var stNlink: NlinkT = 0uL,
    public var stMode: ModeT = 0u,
    public var stUid: UidT = 0u,
    public var stGid: GidT = 0u,
    public var pad0: CUint = 0u,
    public var stRdev: DevT = 0uL,
    public var stSize: OffT = 0L,
    public var stBlksize: BlksizeT = 0L,
    public var stBlocks: BlkcntT = 0L,
    public var stAtim: Timespec = Timespec(),
    public var stMtim: Timespec = Timespec(),
    public var stCtim: Timespec = Timespec(),
    public var reserved: LongArray = LongArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Stat) return false
        if (this.stDev != other.stDev) return false
        if (this.stIno != other.stIno) return false
        if (this.stNlink != other.stNlink) return false
        if (this.stMode != other.stMode) return false
        if (this.stUid != other.stUid) return false
        if (this.stGid != other.stGid) return false
        if (this.pad0 != other.pad0) return false
        if (this.stRdev != other.stRdev) return false
        if (this.stSize != other.stSize) return false
        if (this.stBlksize != other.stBlksize) return false
        if (this.stBlocks != other.stBlocks) return false
        if (this.stAtim != other.stAtim) return false
        if (this.stMtim != other.stMtim) return false
        if (this.stCtim != other.stCtim) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.stDev.hashCode()
        result = 31 * result + this.stIno.hashCode()
        result = 31 * result + this.stNlink.hashCode()
        result = 31 * result + this.stMode.hashCode()
        result = 31 * result + this.stUid.hashCode()
        result = 31 * result + this.stGid.hashCode()
        result = 31 * result + this.pad0.hashCode()
        result = 31 * result + this.stRdev.hashCode()
        result = 31 * result + this.stSize.hashCode()
        result = 31 * result + this.stBlksize.hashCode()
        result = 31 * result + this.stBlocks.hashCode()
        result = 31 * result + this.stAtim.hashCode()
        result = 31 * result + this.stMtim.hashCode()
        result = 31 * result + this.stCtim.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

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
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (Timespec.typeLayout()).readNative(addr + 72L),
                        (Timespec.typeLayout()).readNative(addr + 88L),
                        (Timespec.typeLayout()).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (Timespec.typeLayout()).writeNative(addr + 72L, value.stAtim)
                    (Timespec.typeLayout()).writeNative(addr + 88L, value.stMtim)
                    (Timespec.typeLayout()).writeNative(addr + 104L, value.stCtim)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 120L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (Timespec.typeLayout()).readBytes(buf, offset + 72),
                        (Timespec.typeLayout()).readBytes(buf, offset + 88),
                        (Timespec.typeLayout()).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (Timespec.typeLayout()).writeBytes(buf, offset + 72, value.stAtim)
                    (Timespec.typeLayout()).writeBytes(buf, offset + 88, value.stMtim)
                    (Timespec.typeLayout()).writeBytes(buf, offset + 104, value.stCtim)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 120, value.reserved)
                }
            }
    }
}

public data class FdSet(
    public var nfds: Int = 0,
    public var fds: IntArray = IntArray(FD_SETSIZE.toInt()),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FdSet) return false
        if (this.nfds != other.nfds) return false
        if (!this.fds.contentEquals(other.fds)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nfds.hashCode()
        result = 31 * result + this.fds.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<FdSet> =
            object : TypeLayout<FdSet> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((FD_SETSIZE.toInt()).toLong() * (PrimitiveLayouts.I32).strideBytes)), 8L)
                override fun readNative(addr: Long): FdSet =
                    FdSet(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveArrayLayouts.intArray((FD_SETSIZE.toInt()).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FdSet) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.nfds)
                    (PrimitiveArrayLayouts.intArray((FD_SETSIZE.toInt()).toInt())).writeNative(addr + 8L, value.fds)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FdSet =
                    FdSet(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.intArray((FD_SETSIZE.toInt()).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FdSet) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.nfds)
                    (PrimitiveArrayLayouts.intArray((FD_SETSIZE.toInt()).toInt())).writeBytes(buf, offset + 8, value.fds)
                }
            }
    }
}

public data class PthreadAttrT(
    public var size: LongArray = LongArray(9),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadAttrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadAttrT> =
            object : TypeLayout<PthreadAttrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.longArray(9)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.longArray(9)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.longArray(9)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.longArray(9)).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public data class PthreadMutexattrT(
    public var attr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexattrT> =
            object : TypeLayout<PthreadMutexattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexattrT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.attr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexattrT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.attr)
                }
            }
    }
}

public data class PthreadCondattrT(
    public var attr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondattrT> =
            object : TypeLayout<PthreadCondattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadCondattrT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.attr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondattrT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.attr)
                }
            }
    }
}

public data class PthreadBarrierattrT(
    public var attr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PthreadBarrierattrT> =
            object : TypeLayout<PthreadBarrierattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): PthreadBarrierattrT =
                    PthreadBarrierattrT(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadBarrierattrT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.attr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadBarrierattrT =
                    PthreadBarrierattrT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadBarrierattrT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.attr)
                }
            }
    }
}

public data class PthreadRwlockattrT(
    public var attr: UIntArray = UIntArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockattrT) return false
        if (!this.attr.contentEquals(other.attr)) return false
        return true
    }

    override fun hashCode(): Int = this.attr.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockattrT> =
            object : TypeLayout<PthreadRwlockattrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveArrayLayouts.uIntArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockattrT) {
                    (PrimitiveArrayLayouts.uIntArray(2)).writeNative(addr, value.attr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveArrayLayouts.uIntArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockattrT) {
                    (PrimitiveArrayLayouts.uIntArray(2)).writeBytes(buf, offset, value.attr)
                }
            }
    }
}

public class PthreadCondT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public constructor() : this(
        sizeCtorArg = List(12) { MutPtr(0L) },
    )

    public operator fun component1(): List<MutPtr> = this.size

    public fun copy(size: List<MutPtr> = this.size): PthreadCondT = PthreadCondT(sizeCtorArg = size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondT) return false
        if (this.size != other.size) return false
        return true
    }

    override fun hashCode(): Int = this.size.hashCode()

    public fun clone(): PthreadCondT = PthreadCondT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondT> =
            object : TypeLayout<PthreadCondT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): PthreadCondT =
                    PthreadCondT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 12)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadCondT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 12)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondT =
                    PthreadCondT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 12)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 12)).writeBytes(buf, offset, value.size)
                }
            }

        public operator fun invoke(
            size: List<MutPtr> = List(12) { MutPtr(0L) },
        ): PthreadCondT = PthreadCondT(
            sizeCtorArg = size,
        )
    }
}

public class PthreadMutexT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public constructor() : this(
        sizeCtorArg = List(6) { MutPtr(0L) },
    )

    public operator fun component1(): List<MutPtr> = this.size

    public fun copy(size: List<MutPtr> = this.size): PthreadMutexT = PthreadMutexT(sizeCtorArg = size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexT) return false
        if (this.size != other.size) return false
        return true
    }

    override fun hashCode(): Int = this.size.hashCode()

    public fun clone(): PthreadMutexT = PthreadMutexT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexT> =
            object : TypeLayout<PthreadMutexT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): PthreadMutexT =
                    PthreadMutexT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 6)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 6)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexT =
                    PthreadMutexT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 6)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 6)).writeBytes(buf, offset, value.size)
                }
            }

        public operator fun invoke(
            size: List<MutPtr> = List(6) { MutPtr(0L) },
        ): PthreadMutexT = PthreadMutexT(
            sizeCtorArg = size,
        )
    }
}

public class PthreadRwlockT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public constructor() : this(
        sizeCtorArg = List(8) { MutPtr(0L) },
    )

    public operator fun component1(): List<MutPtr> = this.size

    public fun copy(size: List<MutPtr> = this.size): PthreadRwlockT = PthreadRwlockT(sizeCtorArg = size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockT) return false
        if (this.size != other.size) return false
        return true
    }

    override fun hashCode(): Int = this.size.hashCode()

    public fun clone(): PthreadRwlockT = PthreadRwlockT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockT> =
            object : TypeLayout<PthreadRwlockT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): PthreadRwlockT =
                    PthreadRwlockT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 8)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockT =
                    PthreadRwlockT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 8)).writeBytes(buf, offset, value.size)
                }
            }

        public operator fun invoke(
            size: List<MutPtr> = List(8) { MutPtr(0L) },
        ): PthreadRwlockT = PthreadRwlockT(
            sizeCtorArg = size,
        )
    }
}

public class PthreadBarrierT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public constructor() : this(
        sizeCtorArg = List(5) { MutPtr(0L) },
    )

    public operator fun component1(): List<MutPtr> = this.size

    public fun copy(size: List<MutPtr> = this.size): PthreadBarrierT = PthreadBarrierT(sizeCtorArg = size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadBarrierT) return false
        if (this.size != other.size) return false
        return true
    }

    override fun hashCode(): Int = this.size.hashCode()

    public fun clone(): PthreadBarrierT = PthreadBarrierT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadBarrierT> =
            object : TypeLayout<PthreadBarrierT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): PthreadBarrierT =
                    PthreadBarrierT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 5)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadBarrierT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 5)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadBarrierT =
                    PthreadBarrierT(
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 5)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadBarrierT) {
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 5)).writeBytes(buf, offset, value.size)
                }
            }

        public operator fun invoke(
            size: List<MutPtr> = List(5) { MutPtr(0L) },
        ): PthreadBarrierT = PthreadBarrierT(
            sizeCtorArg = size,
        )
    }
}

// Declare dirent outside of s! so that it doesn't implement Copy, Eq, Hash,
// etc., since it contains a flexible array member with a dynamic size.
public data class Dirent(
    public var dIno: InoT = 0uL,
    public var dType: CUchar = 0u,
    /**
     * dName is declared in WASI libc as a flexible array member, which
     * can't be directly expressed in Rust. As an imperfect workaround,
     * declare it as a zero-length array instead.
     */
    public var dName: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dirent) return false
        if (this.dIno != other.dIno) return false
        if (this.dType != other.dType) return false
        if (!this.dName.contentEquals(other.dName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dIno.hashCode()
        result = 31 * result + this.dType.hashCode()
        result = 31 * result + this.dName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Dirent> =
            object : TypeLayout<Dirent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 9L),
                    )
                override fun writeNative(addr: Long, value: Dirent) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (PrimitiveLayouts.U8).writeNative(addr + 8L, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 9L, value.dName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 9),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 8, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 9, value.dName)
                }
            }
    }
}
