// port-lint: source num/niche_types.rs
package io.github.kotlinmania.rustlang.core.num.nichetypes

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.hash.Hash
import io.github.kotlinmania.rustlang.core.marker.StructuralPartialEq
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

public open class Nanoseconds internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Nanoseconds> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Nanoseconds(ptr, layout) }, { it.value })

    public open fun clone(): Nanoseconds = Nanoseconds(value, fieldLayout)

    public open fun asInner(): UInt = transmuteWithLayouts<Nanoseconds, UInt>(this, UnresolvedTypeLayout<Nanoseconds>(), PrimitiveLayouts.U32)

    public open fun eq(other: Nanoseconds): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: Nanoseconds): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: Nanoseconds): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): Nanoseconds = Nanoseconds(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): Nanoseconds = Nanoseconds(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): Nanoseconds? =
            if (value in 0..999999999) {
                Nanoseconds(transmuteWithLayouts<UInt, Nanoseconds?>(value, fieldLayout, UnresolvedTypeLayout<Nanoseconds?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UInt): Nanoseconds = transmuteWithLayouts<UInt, Nanoseconds>(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> Nanoseconds(ptr, layout) }, { it.value }))

        // SAFETY: 0 is within the valid range
        public val ZERO: Nanoseconds = Nanoseconds.newUnchecked(0u)

        public fun default(): Nanoseconds = Nanoseconds.ZERO
    }
}

public val HALF_USIZE: Int = Int.MAX_VALUE shr 1

public val HALF_USIZE_MINUS_ONE: Int = HALF_USIZE - 1

public open class NonZeroU8Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU8Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU8Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroU8Inner = NonZeroU8Inner(value, fieldLayout)

    public open fun asInner(): UByte = transmuteWithLayouts<NonZeroU8Inner, UByte>(this, UnresolvedTypeLayout<NonZeroU8Inner>(), PrimitiveLayouts.U8)

    public open fun eq(other: NonZeroU8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UByte.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): NonZeroU8Inner = NonZeroU8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): NonZeroU8Inner = NonZeroU8Inner(fieldStorage, fieldLayout)

        public fun new(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): NonZeroU8Inner? =
            if (value >= 1) {
                NonZeroU8Inner(transmuteWithLayouts<UByte, NonZeroU8Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroU8Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UByte): NonZeroU8Inner = transmuteWithLayouts<UByte, NonZeroU8Inner>(value, PrimitiveLayouts.U8, TransparentTypeLayout(PrimitiveLayouts.U8, { ptr, layout -> NonZeroU8Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU16Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public var value: UShort
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU16Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU16Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroU16Inner = NonZeroU16Inner(value, fieldLayout)

    public open fun asInner(): UShort = transmuteWithLayouts<NonZeroU16Inner, UShort>(this, UnresolvedTypeLayout<NonZeroU16Inner>(), PrimitiveLayouts.U16)

    public open fun eq(other: NonZeroU16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UShort.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): NonZeroU16Inner = NonZeroU16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): NonZeroU16Inner = NonZeroU16Inner(fieldStorage, fieldLayout)

        public fun new(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): NonZeroU16Inner? =
            if (value >= 1) {
                NonZeroU16Inner(transmuteWithLayouts<UShort, NonZeroU16Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroU16Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UShort): NonZeroU16Inner = transmuteWithLayouts<UShort, NonZeroU16Inner>(value, PrimitiveLayouts.U16, TransparentTypeLayout(PrimitiveLayouts.U16, { ptr, layout -> NonZeroU16Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU32Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU32Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU32Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroU32Inner = NonZeroU32Inner(value, fieldLayout)

    public open fun asInner(): UInt = transmuteWithLayouts<NonZeroU32Inner, UInt>(this, UnresolvedTypeLayout<NonZeroU32Inner>(), PrimitiveLayouts.U32)

    public open fun eq(other: NonZeroU32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): NonZeroU32Inner = NonZeroU32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): NonZeroU32Inner = NonZeroU32Inner(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): NonZeroU32Inner? =
            if (value >= 1) {
                NonZeroU32Inner(transmuteWithLayouts<UInt, NonZeroU32Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroU32Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UInt): NonZeroU32Inner = transmuteWithLayouts<UInt, NonZeroU32Inner>(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> NonZeroU32Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU64Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public var value: ULong
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU64Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU64Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroU64Inner = NonZeroU64Inner(value, fieldLayout)

    public open fun asInner(): ULong = transmuteWithLayouts<NonZeroU64Inner, ULong>(this, UnresolvedTypeLayout<NonZeroU64Inner>(), PrimitiveLayouts.U64)

    public open fun eq(other: NonZeroU64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = ULong.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): NonZeroU64Inner = NonZeroU64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): NonZeroU64Inner = NonZeroU64Inner(fieldStorage, fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): NonZeroU64Inner? =
            if (value >= 1) {
                NonZeroU64Inner(transmuteWithLayouts<ULong, NonZeroU64Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroU64Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: ULong): NonZeroU64Inner = transmuteWithLayouts<ULong, NonZeroU64Inner>(value, PrimitiveLayouts.U64, TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> NonZeroU64Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroU128Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<U128> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public var value: U128
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: U128, fieldLayout: TypeLayout<U128> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroU128Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU128Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroU128Inner = NonZeroU128Inner(value, fieldLayout)

    public open fun asInner(): U128 = transmuteWithLayouts<NonZeroU128Inner, U128>(this, UnresolvedTypeLayout<NonZeroU128Inner>(), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

    public open fun eq(other: NonZeroU128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = U128.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: U128, fieldLayout: TypeLayout<U128> = UnresolvedTypeLayout()): NonZeroU128Inner = NonZeroU128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<U128> = UnresolvedTypeLayout()): NonZeroU128Inner = NonZeroU128Inner(fieldStorage, fieldLayout)

        public fun new(value: U128, fieldLayout: TypeLayout<U128> = UnresolvedTypeLayout()): NonZeroU128Inner? =
            if (value >= 1) {
                NonZeroU128Inner(transmuteWithLayouts<U128, NonZeroU128Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroU128Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: U128): NonZeroU128Inner = transmuteWithLayouts<U128, NonZeroU128Inner>(value, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), TransparentTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), { ptr, layout -> NonZeroU128Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI8Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Byte> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public var value: Byte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Byte, fieldLayout: TypeLayout<Byte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI8Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI8Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroI8Inner = NonZeroI8Inner(value, fieldLayout)

    public open fun asInner(): Byte = transmuteWithLayouts<NonZeroI8Inner, Byte>(this, UnresolvedTypeLayout<NonZeroI8Inner>(), PrimitiveLayouts.I8)

    public open fun eq(other: NonZeroI8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Byte.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Byte, fieldLayout: TypeLayout<Byte> = UnresolvedTypeLayout()): NonZeroI8Inner = NonZeroI8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Byte> = UnresolvedTypeLayout()): NonZeroI8Inner = NonZeroI8Inner(fieldStorage, fieldLayout)

        public fun new(value: Byte, fieldLayout: TypeLayout<Byte> = UnresolvedTypeLayout()): NonZeroI8Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI8Inner(transmuteWithLayouts<Byte, NonZeroI8Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroI8Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Byte): NonZeroI8Inner = transmuteWithLayouts<Byte, NonZeroI8Inner>(value, PrimitiveLayouts.I8, TransparentTypeLayout(PrimitiveLayouts.I8, { ptr, layout -> NonZeroI8Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI16Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Short> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public var value: Short
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Short, fieldLayout: TypeLayout<Short> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI16Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI16Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroI16Inner = NonZeroI16Inner(value, fieldLayout)

    public open fun asInner(): Short = transmuteWithLayouts<NonZeroI16Inner, Short>(this, UnresolvedTypeLayout<NonZeroI16Inner>(), PrimitiveLayouts.I16)

    public open fun eq(other: NonZeroI16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Short.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Short, fieldLayout: TypeLayout<Short> = UnresolvedTypeLayout()): NonZeroI16Inner = NonZeroI16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Short> = UnresolvedTypeLayout()): NonZeroI16Inner = NonZeroI16Inner(fieldStorage, fieldLayout)

        public fun new(value: Short, fieldLayout: TypeLayout<Short> = UnresolvedTypeLayout()): NonZeroI16Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI16Inner(transmuteWithLayouts<Short, NonZeroI16Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroI16Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Short): NonZeroI16Inner = transmuteWithLayouts<Short, NonZeroI16Inner>(value, PrimitiveLayouts.I16, TransparentTypeLayout(PrimitiveLayouts.I16, { ptr, layout -> NonZeroI16Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI32Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI32Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI32Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroI32Inner = NonZeroI32Inner(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<NonZeroI32Inner, Int>(this, UnresolvedTypeLayout<NonZeroI32Inner>(), PrimitiveLayouts.I32)

    public open fun eq(other: NonZeroI32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroI32Inner = NonZeroI32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroI32Inner = NonZeroI32Inner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroI32Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI32Inner(transmuteWithLayouts<Int, NonZeroI32Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroI32Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): NonZeroI32Inner = transmuteWithLayouts<Int, NonZeroI32Inner>(value, PrimitiveLayouts.I32, TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> NonZeroI32Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI64Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public var value: Long
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI64Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI64Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroI64Inner = NonZeroI64Inner(value, fieldLayout)

    public open fun asInner(): Long = transmuteWithLayouts<NonZeroI64Inner, Long>(this, UnresolvedTypeLayout<NonZeroI64Inner>(), PrimitiveLayouts.I64)

    public open fun eq(other: NonZeroI64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Long.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): NonZeroI64Inner = NonZeroI64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): NonZeroI64Inner = NonZeroI64Inner(fieldStorage, fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): NonZeroI64Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI64Inner(transmuteWithLayouts<Long, NonZeroI64Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroI64Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Long): NonZeroI64Inner = transmuteWithLayouts<Long, NonZeroI64Inner>(value, PrimitiveLayouts.I64, TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> NonZeroI64Inner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroI128Inner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<I128> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: I128
        get() = value
        set(v) {
            value = v
        }

    public var value: I128
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: I128, fieldLayout: TypeLayout<I128> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroI128Inner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI128Inner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroI128Inner = NonZeroI128Inner(value, fieldLayout)

    public open fun asInner(): I128 = transmuteWithLayouts<NonZeroI128Inner, I128>(this, UnresolvedTypeLayout<NonZeroI128Inner>(), PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

    public open fun eq(other: NonZeroI128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = I128.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: I128, fieldLayout: TypeLayout<I128> = UnresolvedTypeLayout()): NonZeroI128Inner = NonZeroI128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<I128> = UnresolvedTypeLayout()): NonZeroI128Inner = NonZeroI128Inner(fieldStorage, fieldLayout)

        public fun new(value: I128, fieldLayout: TypeLayout<I128> = UnresolvedTypeLayout()): NonZeroI128Inner? =
            if (value < 0 || value >= 1) {
                NonZeroI128Inner(transmuteWithLayouts<I128, NonZeroI128Inner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroI128Inner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: I128): NonZeroI128Inner = transmuteWithLayouts<I128, NonZeroI128Inner>(value, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), TransparentTypeLayout(PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }), { ptr, layout -> NonZeroI128Inner(ptr, layout) }, { it.value }))
    }
}

public open class UsizeNoHighBit internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UsizeNoHighBit> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBit(ptr, layout) }, { it.value })

    public open fun clone(): UsizeNoHighBit = UsizeNoHighBit(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<UsizeNoHighBit, Int>(this, UnresolvedTypeLayout<UsizeNoHighBit>(), PrimitiveLayouts.USIZE64)

    public open fun eq(other: UsizeNoHighBit): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBit): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBit): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBit = UsizeNoHighBit(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBit = UsizeNoHighBit(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBit? =
            if (value in 0..HALF_USIZE) {
                UsizeNoHighBit(transmuteWithLayouts<Int, UsizeNoHighBit?>(value, fieldLayout, UnresolvedTypeLayout<UsizeNoHighBit?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): UsizeNoHighBit = transmuteWithLayouts<Int, UsizeNoHighBit>(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> UsizeNoHighBit(ptr, layout) }, { it.value }))
    }
}

public open class UsizeNoHighBitMinusOne internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UsizeNoHighBitMinusOne> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBitMinusOne(ptr, layout) }, { it.value })

    public open fun clone(): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<UsizeNoHighBitMinusOne, Int>(this, UnresolvedTypeLayout<UsizeNoHighBitMinusOne>(), PrimitiveLayouts.USIZE64)

    public open fun eq(other: UsizeNoHighBitMinusOne): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBitMinusOne): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBitMinusOne): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): UsizeNoHighBitMinusOne? =
            if (value in 0..HALF_USIZE_MINUS_ONE) {
                UsizeNoHighBitMinusOne(transmuteWithLayouts<Int, UsizeNoHighBitMinusOne?>(value, fieldLayout, UnresolvedTypeLayout<UsizeNoHighBitMinusOne?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): UsizeNoHighBitMinusOne = transmuteWithLayouts<Int, UsizeNoHighBitMinusOne>(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> UsizeNoHighBitMinusOne(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroUsizeInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroUsizeInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroUsizeInner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroUsizeInner = NonZeroUsizeInner(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<NonZeroUsizeInner, Int>(this, UnresolvedTypeLayout<NonZeroUsizeInner>(), PrimitiveLayouts.USIZE64)

    public open fun eq(other: NonZeroUsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroUsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroUsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroUsizeInner = NonZeroUsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroUsizeInner = NonZeroUsizeInner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroUsizeInner? =
            if (value >= 1) {
                NonZeroUsizeInner(transmuteWithLayouts<Int, NonZeroUsizeInner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroUsizeInner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): NonZeroUsizeInner = transmuteWithLayouts<Int, NonZeroUsizeInner>(value, PrimitiveLayouts.USIZE64, TransparentTypeLayout(PrimitiveLayouts.USIZE64, { ptr, layout -> NonZeroUsizeInner(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroIsizeInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroIsizeInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroIsizeInner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroIsizeInner = NonZeroIsizeInner(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<NonZeroIsizeInner, Int>(this, UnresolvedTypeLayout<NonZeroIsizeInner>(), PrimitiveLayouts.ISIZE64)

    public open fun eq(other: NonZeroIsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroIsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroIsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroIsizeInner = NonZeroIsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroIsizeInner = NonZeroIsizeInner(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): NonZeroIsizeInner? =
            if (value < 0 || value >= 1) {
                NonZeroIsizeInner(transmuteWithLayouts<Int, NonZeroIsizeInner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroIsizeInner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): NonZeroIsizeInner = transmuteWithLayouts<Int, NonZeroIsizeInner>(value, PrimitiveLayouts.ISIZE64, TransparentTypeLayout(PrimitiveLayouts.ISIZE64, { ptr, layout -> NonZeroIsizeInner(ptr, layout) }, { it.value }))
    }
}

public open class U32NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<U32NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> U32NotAllOnes(ptr, layout) }, { it.value })

    public open fun clone(): U32NotAllOnes = U32NotAllOnes(value, fieldLayout)

    public open fun asInner(): UInt = transmuteWithLayouts<U32NotAllOnes, UInt>(this, UnresolvedTypeLayout<U32NotAllOnes>(), PrimitiveLayouts.U32)

    public open fun eq(other: U32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): U32NotAllOnes = U32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): U32NotAllOnes = U32NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): U32NotAllOnes? =
            if (value in 0 until UInt.MAX_VALUE) {
                U32NotAllOnes(transmuteWithLayouts<UInt, U32NotAllOnes?>(value, fieldLayout, UnresolvedTypeLayout<U32NotAllOnes?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UInt): U32NotAllOnes = transmuteWithLayouts<UInt, U32NotAllOnes>(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> U32NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class I32NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<I32NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> I32NotAllOnes(ptr, layout) }, { it.value })

    public open fun clone(): I32NotAllOnes = I32NotAllOnes(value, fieldLayout)

    public open fun asInner(): Int = transmuteWithLayouts<I32NotAllOnes, Int>(this, UnresolvedTypeLayout<I32NotAllOnes>(), PrimitiveLayouts.I32)

    public open fun eq(other: I32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Int.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): I32NotAllOnes = I32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): I32NotAllOnes = I32NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): I32NotAllOnes? =
            if (value < -1 || value >= 0) {
                I32NotAllOnes(transmuteWithLayouts<Int, I32NotAllOnes?>(value, fieldLayout, UnresolvedTypeLayout<I32NotAllOnes?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Int): I32NotAllOnes = transmuteWithLayouts<Int, I32NotAllOnes>(value, PrimitiveLayouts.I32, TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> I32NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class U64NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public var value: ULong
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<U64NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> U64NotAllOnes(ptr, layout) }, { it.value })

    public open fun clone(): U64NotAllOnes = U64NotAllOnes(value, fieldLayout)

    public open fun asInner(): ULong = transmuteWithLayouts<U64NotAllOnes, ULong>(this, UnresolvedTypeLayout<U64NotAllOnes>(), PrimitiveLayouts.U64)

    public open fun eq(other: U64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = ULong.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): U64NotAllOnes = U64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): U64NotAllOnes = U64NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong> = UnresolvedTypeLayout()): U64NotAllOnes? =
            if (value in 0 until ULong.MAX_VALUE) {
                U64NotAllOnes(transmuteWithLayouts<ULong, U64NotAllOnes?>(value, fieldLayout, UnresolvedTypeLayout<U64NotAllOnes?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: ULong): U64NotAllOnes = transmuteWithLayouts<ULong, U64NotAllOnes>(value, PrimitiveLayouts.U64, TransparentTypeLayout(PrimitiveLayouts.U64, { ptr, layout -> U64NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class I64NotAllOnes internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public var value: Long
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<I64NotAllOnes> = TransparentTypeLayout(fieldLayout, { ptr, layout -> I64NotAllOnes(ptr, layout) }, { it.value })

    public open fun clone(): I64NotAllOnes = I64NotAllOnes(value, fieldLayout)

    public open fun asInner(): Long = transmuteWithLayouts<I64NotAllOnes, Long>(this, UnresolvedTypeLayout<I64NotAllOnes>(), PrimitiveLayouts.I64)

    public open fun eq(other: I64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Long.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): I64NotAllOnes = I64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): I64NotAllOnes = I64NotAllOnes(fieldStorage, fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long> = UnresolvedTypeLayout()): I64NotAllOnes? =
            if (value < -1 || value >= 0) {
                I64NotAllOnes(transmuteWithLayouts<Long, I64NotAllOnes?>(value, fieldLayout, UnresolvedTypeLayout<I64NotAllOnes?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Long): I64NotAllOnes = transmuteWithLayouts<Long, I64NotAllOnes>(value, PrimitiveLayouts.I64, TransparentTypeLayout(PrimitiveLayouts.I64, { ptr, layout -> I64NotAllOnes(ptr, layout) }, { it.value }))
    }
}

public open class NonZeroCharInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Char> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: Char
        get() = value
        set(v) {
            value = v
        }

    public var value: Char
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Char, fieldLayout: TypeLayout<Char> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZeroCharInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroCharInner(ptr, layout) }, { it.value })

    public open fun clone(): NonZeroCharInner = NonZeroCharInner(value, fieldLayout)

    public open fun asInner(): Char = transmuteWithLayouts<NonZeroCharInner, Char>(this, UnresolvedTypeLayout<NonZeroCharInner>(), PrimitiveLayouts.CHAR)

    public open fun eq(other: NonZeroCharInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroCharInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroCharInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = Char.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: Char, fieldLayout: TypeLayout<Char> = UnresolvedTypeLayout()): NonZeroCharInner = NonZeroCharInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Char> = UnresolvedTypeLayout()): NonZeroCharInner = NonZeroCharInner(fieldStorage, fieldLayout)

        public fun new(value: Char, fieldLayout: TypeLayout<Char> = UnresolvedTypeLayout()): NonZeroCharInner? =
            if (value in '\u0001'..0x10ffff) {
                NonZeroCharInner(transmuteWithLayouts<Char, NonZeroCharInner?>(value, fieldLayout, UnresolvedTypeLayout<NonZeroCharInner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: Char): NonZeroCharInner = transmuteWithLayouts<Char, NonZeroCharInner>(value, PrimitiveLayouts.CHAR, TransparentTypeLayout(PrimitiveLayouts.CHAR, { ptr, layout -> NonZeroCharInner(ptr, layout) }, { it.value }))
    }
}

public interface NotAllOnesHelper<out Type>

public typealias NotAllOnes<T> = Type

public open class CodePointInner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout(),
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<CodePointInner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CodePointInner(ptr, layout) }, { it.value })

    public open fun clone(): CodePointInner = CodePointInner(value, fieldLayout)

    public open fun asInner(): UInt = transmuteWithLayouts<CodePointInner, UInt>(this, UnresolvedTypeLayout<CodePointInner>(), PrimitiveLayouts.U32)

    public open fun eq(other: CodePointInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: CodePointInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: CodePointInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        Hash.Hash(this.asInner(), state)
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = UInt.fmt(this.asInner(), f)

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): CodePointInner = CodePointInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): CodePointInner = CodePointInner(fieldStorage, fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): CodePointInner? =
            if (value in 0..0x10ffff) {
                CodePointInner(transmuteWithLayouts<UInt, CodePointInner?>(value, fieldLayout, UnresolvedTypeLayout<CodePointInner?>()), fieldLayout = fieldLayout)
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
        public fun newUnchecked(value: UInt): CodePointInner = transmuteWithLayouts<UInt, CodePointInner>(value, PrimitiveLayouts.U32, TransparentTypeLayout(PrimitiveLayouts.U32, { ptr, layout -> CodePointInner(ptr, layout) }, { it.value }))

        public val ZERO: CodePointInner = CodePointInner.new(0u, fieldLayout = PrimitiveLayouts.U32)!!

        public fun default(): CodePointInner = CodePointInner.ZERO
    }
}
