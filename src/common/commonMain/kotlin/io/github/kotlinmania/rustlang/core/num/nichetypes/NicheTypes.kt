// port-lint: source num/niche_types.rs
package io.github.kotlinmania.rustlang.core.num.nichetypes

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.transmute
import io.github.kotlinmania.rustlang.core.marker.StructuralPartialEq
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

public open class Nanoseconds internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt>,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Nanoseconds> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Nanoseconds(ptr, layout) }, { it.value })

    public open fun asInner(): UInt = transmute(this)

    public open fun eq(other: Nanoseconds): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: Nanoseconds): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: Nanoseconds): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt>): Nanoseconds = Nanoseconds(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt>): Nanoseconds = Nanoseconds(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt>): Nanoseconds? =
            if (value in 0..999999999) {
                Nanoseconds(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): Nanoseconds = transmuteWithLayouts(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> Nanoseconds(ptr, layout) }, { it.value }))

        // SAFETY: 0 is within the valid range
        public val ZERO: Nanoseconds = Nanoseconds.newUnchecked(0u)

        public fun default(): Nanoseconds = Nanoseconds.ZERO
    }
}

public val HALF_USIZE: Int = Int.MAX_VALUE shr 1

public val HALF_USIZE_MINUS_ONE: Int = HALF_USIZE - 1

public open class NonZeroU8Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte>,
) : StructuralPartialEq {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU8Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU8Inner(ptr, layout) }, { it.value })

    public open fun asInner(): UByte = transmute(this)

    public open fun eq(other: NonZeroU8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UByte.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte>): NonZeroU8Inner = NonZeroU8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte>): NonZeroU8Inner = NonZeroU8Inner(fieldStorage, fieldLayout)

        public fun new(value: UByte, fieldLayout: TypeLayout<UByte>): NonZeroU8Inner? =
            if (value >= 1) {
                NonZeroU8Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UByte): NonZeroU8Inner = transmuteWithLayouts(value, PrimitiveLayouts.U8, TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> NonZeroU8Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU16Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UShort>,
) : StructuralPartialEq {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public var value: UShort
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UShort, fieldLayout: TypeLayout<UShort>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU16Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU16Inner(ptr, layout) }, { it.value })

    public open fun asInner(): UShort = transmute(this)

    public open fun eq(other: NonZeroU16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UShort.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort>): NonZeroU16Inner = NonZeroU16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort>): NonZeroU16Inner = NonZeroU16Inner(fieldStorage, fieldLayout)

        public fun new(value: UShort, fieldLayout: TypeLayout<UShort>): NonZeroU16Inner? =
            if (value >= 1) {
                NonZeroU16Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UShort): NonZeroU16Inner = transmuteWithLayouts(value, PrimitiveLayouts.U16, TransparentTypeLayout(PrimitiveLayouts.U16, { ptr, layout -> NonZeroU16Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU32Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt>,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU32Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU32Inner(ptr, layout) }, { it.value })

    public open fun asInner(): UInt = transmute(this)

    public open fun eq(other: NonZeroU32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt>): NonZeroU32Inner = NonZeroU32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt>): NonZeroU32Inner = NonZeroU32Inner(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt>): NonZeroU32Inner? =
            if (value >= 1) {
                NonZeroU32Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): NonZeroU32Inner = transmuteWithLayouts(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> NonZeroU32Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU64Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ULong>,
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public var value: ULong
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: ULong, fieldLayout: TypeLayout<ULong>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU64Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU64Inner(ptr, layout) }, { it.value })

    public open fun asInner(): ULong = transmute(this)

    public open fun eq(other: NonZeroU64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = ULong.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong>): NonZeroU64Inner = NonZeroU64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong>): NonZeroU64Inner = NonZeroU64Inner(fieldStorage, fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong>): NonZeroU64Inner? =
            if (value >= 1) {
                NonZeroU64Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: ULong): NonZeroU64Inner = transmuteWithLayouts(value, PrimitiveLayouts.U64, TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> NonZeroU64Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU128Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<U128>,
) : StructuralPartialEq {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public var value: U128
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: U128, fieldLayout: TypeLayout<U128>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU128Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU128Inner(ptr, layout) }, { it.value })

    public open fun asInner(): U128 = transmute(this)

    public open fun eq(other: NonZeroU128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = U128.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: U128, fieldLayout: TypeLayout<U128>): NonZeroU128Inner = NonZeroU128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<U128>): NonZeroU128Inner = NonZeroU128Inner(fieldStorage, fieldLayout)

        public fun new(value: U128, fieldLayout: TypeLayout<U128>): NonZeroU128Inner? =
            if (value >= 1) {
                NonZeroU128Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: U128): NonZeroU128Inner = transmuteWithLayouts(value, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), TransparentTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), { ptr, layout -> NonZeroU128Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI8Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Byte>,
) : StructuralPartialEq {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public var value: Byte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Byte, fieldLayout: TypeLayout<Byte>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI8Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI8Inner(ptr, layout) }, { it.value })

    public open fun asInner(): Byte = transmute(this)

    public open fun eq(other: NonZeroI8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Byte.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Byte, fieldLayout: TypeLayout<Byte>): NonZeroI8Inner = NonZeroI8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Byte>): NonZeroI8Inner = NonZeroI8Inner(fieldStorage, fieldLayout)

        public fun new(value: Byte, fieldLayout: TypeLayout<Byte>): NonZeroI8Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI8Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Byte): NonZeroI8Inner = transmuteWithLayouts(value, PrimitiveLayouts.I8, TransparentTypeLayout(PrimitiveLayouts.I8, { ptr, layout -> NonZeroI8Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI16Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Short>,
) : StructuralPartialEq {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public var value: Short
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Short, fieldLayout: TypeLayout<Short>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI16Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI16Inner(ptr, layout) }, { it.value })

    public open fun asInner(): Short = transmute(this)

    public open fun eq(other: NonZeroI16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Short.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Short, fieldLayout: TypeLayout<Short>): NonZeroI16Inner = NonZeroI16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Short>): NonZeroI16Inner = NonZeroI16Inner(fieldStorage, fieldLayout)

        public fun new(value: Short, fieldLayout: TypeLayout<Short>): NonZeroI16Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI16Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Short): NonZeroI16Inner = transmuteWithLayouts(value, PrimitiveLayouts.I16, TransparentTypeLayout(PrimitiveLayouts.I16, { ptr, layout -> NonZeroI16Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI32Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI32Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI32Inner(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: NonZeroI32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): NonZeroI32Inner = NonZeroI32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): NonZeroI32Inner = NonZeroI32Inner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): NonZeroI32Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI32Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroI32Inner = transmuteWithLayouts(value, PrimitiveLayouts.I32, TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> NonZeroI32Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI64Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Long>,
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public var value: Long
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Long, fieldLayout: TypeLayout<Long>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI64Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI64Inner(ptr, layout) }, { it.value })

    public open fun asInner(): Long = transmute(this)

    public open fun eq(other: NonZeroI64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Long.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long>): NonZeroI64Inner = NonZeroI64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long>): NonZeroI64Inner = NonZeroI64Inner(fieldStorage, fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long>): NonZeroI64Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI64Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Long): NonZeroI64Inner = transmuteWithLayouts(value, PrimitiveLayouts.I64, TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> NonZeroI64Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI128Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<I128>,
) : StructuralPartialEq {
    public var field0: I128
        get() = value
        set(v) {
            value = v
        }

    public var value: I128
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: I128, fieldLayout: TypeLayout<I128>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI128Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI128Inner(ptr, layout) }, { it.value })

    public open fun asInner(): I128 = transmute(this)

    public open fun eq(other: NonZeroI128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = I128.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: I128, fieldLayout: TypeLayout<I128>): NonZeroI128Inner = NonZeroI128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<I128>): NonZeroI128Inner = NonZeroI128Inner(fieldStorage, fieldLayout)

        public fun new(value: I128, fieldLayout: TypeLayout<I128>): NonZeroI128Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI128Inner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: I128): NonZeroI128Inner = transmuteWithLayouts(value, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), TransparentTypeLayout(PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), { ptr, layout -> NonZeroI128Inner(ptr, layout) }, { it.value }))
    }
}

public open class UsizeNoHighBit internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UsizeNoHighBit> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBit(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: UsizeNoHighBit): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBit): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBit): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): UsizeNoHighBit = UsizeNoHighBit(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): UsizeNoHighBit = UsizeNoHighBit(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): UsizeNoHighBit? =
            if (value in 0..HALF_USIZE) {
                UsizeNoHighBit(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): UsizeNoHighBit = transmuteWithLayouts(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> UsizeNoHighBit(ptr, layout) }, { it.value }))
    }
}

public open class UsizeNoHighBitMinusOne internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UsizeNoHighBitMinusOne> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBitMinusOne(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: UsizeNoHighBitMinusOne): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBitMinusOne): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBitMinusOne): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): UsizeNoHighBitMinusOne? =
            if (value in 0..HALF_USIZE_MINUS_ONE) {
                UsizeNoHighBitMinusOne(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): UsizeNoHighBitMinusOne = transmuteWithLayouts(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> UsizeNoHighBitMinusOne(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroUsizeInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroUsizeInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroUsizeInner(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: NonZeroUsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroUsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroUsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): NonZeroUsizeInner = NonZeroUsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): NonZeroUsizeInner = NonZeroUsizeInner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): NonZeroUsizeInner? =
            if (value >= 1) {
                NonZeroUsizeInner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroUsizeInner = transmuteWithLayouts(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> NonZeroUsizeInner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroIsizeInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroIsizeInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroIsizeInner(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: NonZeroIsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroIsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroIsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): NonZeroIsizeInner = NonZeroIsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): NonZeroIsizeInner = NonZeroIsizeInner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): NonZeroIsizeInner? =
            if (value < 0 || value >= 1) {
                NonZeroIsizeInner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroIsizeInner = transmuteWithLayouts(value, PrimitiveLayouts.ISIZE64, TransparentTypeLayout(PrimitiveLayouts.ISIZE64, { ptr, layout -> NonZeroIsizeInner(ptr, layout) }, { it.value }))
    }
}

public open class U32NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt>,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<U32NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> U32NotAllOnes(ptr, layout) }, { it.value })

    public open fun asInner(): UInt = transmute(this)

    public open fun eq(other: U32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt>): U32NotAllOnes = U32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt>): U32NotAllOnes = U32NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt>): U32NotAllOnes? =
            if (value in 0 until UInt.MAX_VALUE) {
                U32NotAllOnes(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): U32NotAllOnes = transmuteWithLayouts(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> U32NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class I32NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<I32NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> I32NotAllOnes(ptr, layout) }, { it.value })

    public open fun asInner(): Int = transmute(this)

    public open fun eq(other: I32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): I32NotAllOnes = I32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): I32NotAllOnes = I32NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int>): I32NotAllOnes? =
            if (value < -1 || value >= 0) {
                I32NotAllOnes(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): I32NotAllOnes = transmuteWithLayouts(value, PrimitiveLayouts.I32, TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> I32NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class U64NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ULong>,
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public var value: ULong
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: ULong, fieldLayout: TypeLayout<ULong>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<U64NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> U64NotAllOnes(ptr, layout) }, { it.value })

    public open fun asInner(): ULong = transmute(this)

    public open fun eq(other: U64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = ULong.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong>): U64NotAllOnes = U64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong>): U64NotAllOnes = U64NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong>): U64NotAllOnes? =
            if (value in 0 until ULong.MAX_VALUE) {
                U64NotAllOnes(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: ULong): U64NotAllOnes = transmuteWithLayouts(value, PrimitiveLayouts.U64, TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> U64NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class I64NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Long>,
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public var value: Long
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Long, fieldLayout: TypeLayout<Long>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<I64NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> I64NotAllOnes(ptr, layout) }, { it.value })

    public open fun asInner(): Long = transmute(this)

    public open fun eq(other: I64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Long.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long>): I64NotAllOnes = I64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long>): I64NotAllOnes = I64NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long>): I64NotAllOnes? =
            if (value < -1 || value >= 0) {
                I64NotAllOnes(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Long): I64NotAllOnes = transmuteWithLayouts(value, PrimitiveLayouts.I64, TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> I64NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroCharInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Char>,
) : StructuralPartialEq {
    public var field0: Char
        get() = value
        set(v) {
            value = v
        }

    public var value: Char
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Char, fieldLayout: TypeLayout<Char>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroCharInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroCharInner(ptr, layout) }, { it.value })

    public open fun asInner(): Char = transmute(this)

    public open fun eq(other: NonZeroCharInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroCharInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroCharInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Char.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Char, fieldLayout: TypeLayout<Char>): NonZeroCharInner = NonZeroCharInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Char>): NonZeroCharInner = NonZeroCharInner(fieldStorage, fieldLayout)

        public fun new(value: Char, fieldLayout: TypeLayout<Char>): NonZeroCharInner? =
            if (value in '\u0001'..0x10ffff) {
                NonZeroCharInner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Char): NonZeroCharInner = transmuteWithLayouts(value, PrimitiveLayouts.CHAR, TransparentTypeLayout(PrimitiveLayouts.CHAR, { ptr, layout -> NonZeroCharInner(ptr, layout) }, { it.value }))
    }
}

public interface NotAllOnesHelper<out Type>

public typealias NotAllOnes<T> = Type

public open class CodePointInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt>,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<CodePointInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CodePointInner(ptr, layout) }, { it.value })

    public open fun asInner(): UInt = transmute(this)

    public open fun eq(other: CodePointInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: CodePointInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: CodePointInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        this.asInner().hash(state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt>): CodePointInner = CodePointInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt>): CodePointInner = CodePointInner(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt>): CodePointInner? =
            if (value in 0..0x10ffff) {
                CodePointInner(transmute(value), fieldLayout = fieldLayout)
            } else {
                null
            }

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): CodePointInner = transmuteWithLayouts(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> CodePointInner(ptr, layout) }, { it.value }))

        public val ZERO: CodePointInner = CodePointInner.new(0u, fieldLayout = PrimitiveLayouts.U32)!!

        public fun default(): CodePointInner = CodePointInner.ZERO
    }
}
