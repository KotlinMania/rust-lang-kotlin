@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

public open class MaxAlignT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray>,
) {
    public var priv: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray>) : this(allocateNativeCell(priv, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MaxAlignT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MaxAlignT(ptr, layout) }, { it.priv })

    public open fun clone(): MaxAlignT = MaxAlignT(priv, fieldLayout)

    public companion object {
        public operator fun invoke(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray>): MaxAlignT = MaxAlignT(priv, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray>): MaxAlignT = MaxAlignT(fieldStorage, fieldLayout)
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
public open class DIR(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): DIR = DIR(data, marker)

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
public open class LocaleStruct(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): LocaleStruct = LocaleStruct(data, marker)
}

public open class ClockidT internal constructor(
    valueCtorArg: ConstPtr,
) {
    public var value: ConstPtr = valueCtorArg
        internal set

    public var field0: ConstPtr
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ClockidT = ClockidT(value)

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
            value: ConstPtr,
        ): ClockidT = ClockidT(
            valueCtorArg = value,
        )
    }
}

public open class FposT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var data: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 8L)), AlignedTypeLayout(fieldLayout, 8L))

    public fun pointerLayout(): TypeLayout<FposT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> FposT(ptr, layout) }, { it.data })

    public open fun clone(): FposT = FposT(data, fieldLayout)

    public companion object {
        public operator fun invoke(data: UByteArray, fieldLayout: TypeLayout<UByteArray>): FposT = FposT(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): FposT = FposT(fieldStorage, fieldLayout)
    }
}

public open class Tm internal constructor(
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

    public open fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst, tmGmtoff, tmZone, tmNsec)

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
            tmSec: CInt,
            tmMin: CInt,
            tmHour: CInt,
            tmMday: CInt,
            tmMon: CInt,
            tmYear: CInt,
            tmWday: CInt,
            tmYday: CInt,
            tmIsdst: CInt,
            tmGmtoff: CInt,
            tmZone: ConstPtr,
            tmNsec: CInt,
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
    }
}

public open class Itimerspec(
    public var itInterval: Timespec,
    public var itValue: Timespec,
) {
    public open fun clone(): Itimerspec = Itimerspec(itInterval, itValue)

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
    }
}

public open class Lconv internal constructor(
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

    public open fun clone(): Lconv = Lconv(decimalPoint, thousandsSep, grouping, intCurrSymbol, currencySymbol, monDecimalPoint, monThousandsSep, monGrouping, positiveSign, negativeSign, intFracDigits, fracDigits, pCsPrecedes, pSepBySpace, nCsPrecedes, nSepBySpace, pSignPosn, nSignPosn, intPCsPrecedes, intPSepBySpace, intNCsPrecedes, intNSepBySpace, intPSignPosn, intNSignPosn)

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
            decimalPoint: MutPtr,
            thousandsSep: MutPtr,
            grouping: MutPtr,
            intCurrSymbol: MutPtr,
            currencySymbol: MutPtr,
            monDecimalPoint: MutPtr,
            monThousandsSep: MutPtr,
            monGrouping: MutPtr,
            positiveSign: MutPtr,
            negativeSign: MutPtr,
            intFracDigits: CChar,
            fracDigits: CChar,
            pCsPrecedes: CChar,
            pSepBySpace: CChar,
            nCsPrecedes: CChar,
            nSepBySpace: CChar,
            pSignPosn: CChar,
            nSignPosn: CChar,
            intPCsPrecedes: CChar,
            intPSepBySpace: CChar,
            intNCsPrecedes: CChar,
            intNSepBySpace: CChar,
            intPSignPosn: CChar,
            intNSignPosn: CChar,
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
    }
}

public open class Rusage(
    public var ruUtime: Timeval,
    public var ruStime: Timeval,
) {
    public open fun clone(): Rusage = Rusage(ruUtime, ruStime)

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

public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stNlink: NlinkT,
    public var stMode: ModeT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var pad0: CUint,
    public var stRdev: DevT,
    public var stSize: OffT,
    public var stBlksize: BlksizeT,
    public var stBlocks: BlkcntT,
    public var stAtim: Timespec,
    public var stMtim: Timespec,
    public var stCtim: Timespec,
    public var reserved: LongArray,
) {
    public open fun clone(): Stat = Stat(stDev, stIno, stNlink, stMode, stUid, stGid, pad0, stRdev, stSize, stBlksize, stBlocks, stAtim, stMtim, stCtim, reserved)

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

public open class FdSet(
    public var nfds: Int,
    public var fds: IntArray,
) {
    public open fun clone(): FdSet = FdSet(nfds, fds)

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

public open class PthreadAttrT(
    public var size: LongArray,
) {
    public open fun clone(): PthreadAttrT = PthreadAttrT(size)

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

public open class PthreadMutexattrT(
    public var attr: CUint,
) {
    public open fun clone(): PthreadMutexattrT = PthreadMutexattrT(attr)

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

public open class PthreadCondattrT(
    public var attr: CUint,
) {
    public open fun clone(): PthreadCondattrT = PthreadCondattrT(attr)

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

public open class PthreadBarrierattrT(
    public var attr: CUint,
) {
    public open fun clone(): PthreadBarrierattrT = PthreadBarrierattrT(attr)

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

public open class PthreadRwlockattrT(
    public var attr: UIntArray,
) {
    public open fun clone(): PthreadRwlockattrT = PthreadRwlockattrT(attr)

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

public open class PthreadCondT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public open fun clone(): PthreadCondT = PthreadCondT(size)

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
            size: List<MutPtr>,
        ): PthreadCondT = PthreadCondT(
            sizeCtorArg = size,
        )
    }
}

public open class PthreadMutexT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public open fun clone(): PthreadMutexT = PthreadMutexT(size)

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
            size: List<MutPtr>,
        ): PthreadMutexT = PthreadMutexT(
            sizeCtorArg = size,
        )
    }
}

public open class PthreadRwlockT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public open fun clone(): PthreadRwlockT = PthreadRwlockT(size)

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
            size: List<MutPtr>,
        ): PthreadRwlockT = PthreadRwlockT(
            sizeCtorArg = size,
        )
    }
}

public open class PthreadBarrierT internal constructor(
    sizeCtorArg: List<MutPtr>,
) {
    public var size: List<MutPtr> = sizeCtorArg
        internal set

    public open fun clone(): PthreadBarrierT = PthreadBarrierT(size)

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
            size: List<MutPtr>,
        ): PthreadBarrierT = PthreadBarrierT(
            sizeCtorArg = size,
        )
    }
}

// Declare dirent outside of s! so that it doesn't implement Copy, Eq, Hash,
// etc., since it contains a flexible array member with a dynamic size.
public open class Dirent(
    public var dIno: InoT,
    public var dType: CUchar,
    /**
     * dName is declared in WASI libc as a flexible array member, which
     * can't be directly expressed in Rust. As an imperfect workaround,
     * declare it as a zero-length array instead.
     */
    public var dName: UByteArray,
) {
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
