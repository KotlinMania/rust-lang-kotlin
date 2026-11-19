// rust-kotlin: manual
// port-lint: source num/niche_types.rs
package io.github.kotlinmania.rustlang.core.num.nichetypes

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.charCmp
import io.github.kotlinmania.rustlang.core.cmp.cmp
import io.github.kotlinmania.rustlang.core.marker.StructuralPartialEq
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

public open class Nanoseconds internal constructor(
    public var value: UInt,
    public val fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<Nanoseconds> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> Nanoseconds(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): Nanoseconds = Nanoseconds(value, fieldLayout)

    public open fun asInner(): UInt = value

    public open fun eq(other: Nanoseconds): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: Nanoseconds): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: Nanoseconds): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): Nanoseconds =
            Nanoseconds(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): Nanoseconds =
            Nanoseconds(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): Nanoseconds? =
            if (value in 0u..999999999u) Nanoseconds(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): Nanoseconds = Nanoseconds(value, PrimitiveLayouts.U32)

        // SAFETY: 0 is within the valid range
        public val ZERO: Nanoseconds = Nanoseconds.newUnchecked(0u)

        public fun default(): Nanoseconds = Nanoseconds.ZERO
    }
}

public val HALF_USIZE: Int = Int.MAX_VALUE shr 1

public val HALF_USIZE_MINUS_ONE: Int = HALF_USIZE - 1

public open class NonZeroU8Inner internal constructor(
    public var value: UByte,
    public val fieldLayout: TypeLayout<UByte> = PrimitiveLayouts.U8,
) : StructuralPartialEq {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroU8Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU8Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroU8Inner = NonZeroU8Inner(value, fieldLayout)

    public open fun asInner(): UByte = value

    public open fun eq(other: NonZeroU8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = PrimitiveLayouts.U8): NonZeroU8Inner =
            NonZeroU8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = PrimitiveLayouts.U8): NonZeroU8Inner =
            NonZeroU8Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UByte, fieldLayout: TypeLayout<UByte> = PrimitiveLayouts.U8): NonZeroU8Inner? =
            if (value >= 1u.toUByte()) NonZeroU8Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UByte): NonZeroU8Inner = NonZeroU8Inner(value, PrimitiveLayouts.U8)
    }
}

public open class NonZeroU16Inner internal constructor(
    public var value: UShort,
    public val fieldLayout: TypeLayout<UShort> = PrimitiveLayouts.U16,
) : StructuralPartialEq {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroU16Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU16Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroU16Inner = NonZeroU16Inner(value, fieldLayout)

    public open fun asInner(): UShort = value

    public open fun eq(other: NonZeroU16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort> = PrimitiveLayouts.U16): NonZeroU16Inner =
            NonZeroU16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort> = PrimitiveLayouts.U16): NonZeroU16Inner =
            NonZeroU16Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UShort, fieldLayout: TypeLayout<UShort> = PrimitiveLayouts.U16): NonZeroU16Inner? =
            if (value >= 1u.toUShort()) NonZeroU16Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UShort): NonZeroU16Inner = NonZeroU16Inner(value, PrimitiveLayouts.U16)
    }
}

public open class NonZeroU32Inner internal constructor(
    public var value: UInt,
    public val fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroU32Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU32Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroU32Inner = NonZeroU32Inner(value, fieldLayout)

    public open fun asInner(): UInt = value

    public open fun eq(other: NonZeroU32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): NonZeroU32Inner =
            NonZeroU32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): NonZeroU32Inner =
            NonZeroU32Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): NonZeroU32Inner? =
            if (value >= 1u) NonZeroU32Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): NonZeroU32Inner = NonZeroU32Inner(value, PrimitiveLayouts.U32)
    }
}

public open class NonZeroU64Inner internal constructor(
    public var value: ULong,
    public val fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64,
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroU64Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU64Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroU64Inner = NonZeroU64Inner(value, fieldLayout)

    public open fun asInner(): ULong = value

    public open fun eq(other: NonZeroU64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): NonZeroU64Inner =
            NonZeroU64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): NonZeroU64Inner =
            NonZeroU64Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): NonZeroU64Inner? =
            if (value >= 1uL) NonZeroU64Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: ULong): NonZeroU64Inner = NonZeroU64Inner(value, PrimitiveLayouts.U64)
    }
}

public open class NonZeroU128Inner internal constructor(
    public var value: U128,
    public val fieldLayout: TypeLayout<U128> = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }),
) : StructuralPartialEq {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroU128Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroU128Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroU128Inner = NonZeroU128Inner(value, fieldLayout)

    public open fun asInner(): U128 = value

    public open fun eq(other: NonZeroU128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroU128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroU128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: U128, fieldLayout: TypeLayout<U128> = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })): NonZeroU128Inner =
            NonZeroU128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<U128> = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })): NonZeroU128Inner =
            NonZeroU128Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: U128, fieldLayout: TypeLayout<U128> = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })): NonZeroU128Inner? =
            if (value != U128.ZERO) NonZeroU128Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: U128): NonZeroU128Inner = NonZeroU128Inner(value)
    }
}

public open class NonZeroI8Inner internal constructor(
    public var value: Byte,
    public val fieldLayout: TypeLayout<Byte> = PrimitiveLayouts.I8,
) : StructuralPartialEq {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroI8Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI8Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroI8Inner = NonZeroI8Inner(value, fieldLayout)

    public open fun asInner(): Byte = value

    public open fun eq(other: NonZeroI8Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI8Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI8Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Byte, fieldLayout: TypeLayout<Byte> = PrimitiveLayouts.I8): NonZeroI8Inner =
            NonZeroI8Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Byte> = PrimitiveLayouts.I8): NonZeroI8Inner =
            NonZeroI8Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Byte, fieldLayout: TypeLayout<Byte> = PrimitiveLayouts.I8): NonZeroI8Inner? =
            if (value != 0.toByte()) NonZeroI8Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Byte): NonZeroI8Inner = NonZeroI8Inner(value, PrimitiveLayouts.I8)
    }
}

public open class NonZeroI16Inner internal constructor(
    public var value: Short,
    public val fieldLayout: TypeLayout<Short> = PrimitiveLayouts.I16,
) : StructuralPartialEq {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroI16Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI16Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroI16Inner = NonZeroI16Inner(value, fieldLayout)

    public open fun asInner(): Short = value

    public open fun eq(other: NonZeroI16Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI16Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI16Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Short, fieldLayout: TypeLayout<Short> = PrimitiveLayouts.I16): NonZeroI16Inner =
            NonZeroI16Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Short> = PrimitiveLayouts.I16): NonZeroI16Inner =
            NonZeroI16Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Short, fieldLayout: TypeLayout<Short> = PrimitiveLayouts.I16): NonZeroI16Inner? =
            if (value != 0.toShort()) NonZeroI16Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Short): NonZeroI16Inner = NonZeroI16Inner(value, PrimitiveLayouts.I16)
    }
}

public open class NonZeroI32Inner internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroI32Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI32Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroI32Inner = NonZeroI32Inner(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: NonZeroI32Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI32Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI32Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): NonZeroI32Inner =
            NonZeroI32Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): NonZeroI32Inner =
            NonZeroI32Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): NonZeroI32Inner? =
            if (value != 0) NonZeroI32Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroI32Inner = NonZeroI32Inner(value, PrimitiveLayouts.I32)
    }
}

public open class NonZeroI64Inner internal constructor(
    public var value: Long,
    public val fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64,
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroI64Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI64Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroI64Inner = NonZeroI64Inner(value, fieldLayout)

    public open fun asInner(): Long = value

    public open fun eq(other: NonZeroI64Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI64Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI64Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): NonZeroI64Inner =
            NonZeroI64Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): NonZeroI64Inner =
            NonZeroI64Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): NonZeroI64Inner? =
            if (value != 0L) NonZeroI64Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Long): NonZeroI64Inner = NonZeroI64Inner(value, PrimitiveLayouts.I64)
    }
}

public open class NonZeroI128Inner internal constructor(
    public var value: I128,
    public val fieldLayout: TypeLayout<I128> = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }),
) : StructuralPartialEq {
    public var field0: I128
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroI128Inner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroI128Inner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroI128Inner = NonZeroI128Inner(value, fieldLayout)

    public open fun asInner(): I128 = value

    public open fun eq(other: NonZeroI128Inner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroI128Inner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroI128Inner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: I128, fieldLayout: TypeLayout<I128> = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })): NonZeroI128Inner =
            NonZeroI128Inner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<I128> = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })): NonZeroI128Inner =
            NonZeroI128Inner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: I128, fieldLayout: TypeLayout<I128> = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low })): NonZeroI128Inner? =
            if (value != I128.ZERO) NonZeroI128Inner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: I128): NonZeroI128Inner = NonZeroI128Inner(value)
    }
}

public open class UsizeNoHighBit internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<UsizeNoHighBit> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBit(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): UsizeNoHighBit = UsizeNoHighBit(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: UsizeNoHighBit): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBit): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBit): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBit =
            UsizeNoHighBit(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBit =
            UsizeNoHighBit(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBit? =
            if (value in 0..HALF_USIZE) UsizeNoHighBit(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): UsizeNoHighBit = UsizeNoHighBit(value, PrimitiveLayouts.USIZE64)
    }
}

public open class UsizeNoHighBitMinusOne internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<UsizeNoHighBitMinusOne> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> UsizeNoHighBitMinusOne(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: UsizeNoHighBitMinusOne): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: UsizeNoHighBitMinusOne): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: UsizeNoHighBitMinusOne): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBitMinusOne =
            UsizeNoHighBitMinusOne(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBitMinusOne =
            UsizeNoHighBitMinusOne(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): UsizeNoHighBitMinusOne? =
            if (value in 0..HALF_USIZE_MINUS_ONE) UsizeNoHighBitMinusOne(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): UsizeNoHighBitMinusOne = UsizeNoHighBitMinusOne(value, PrimitiveLayouts.USIZE64)
    }
}

public open class NonZeroUsizeInner internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroUsizeInner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroUsizeInner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroUsizeInner = NonZeroUsizeInner(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: NonZeroUsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroUsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroUsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): NonZeroUsizeInner =
            NonZeroUsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): NonZeroUsizeInner =
            NonZeroUsizeInner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.USIZE64): NonZeroUsizeInner? =
            if (value >= 1) NonZeroUsizeInner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroUsizeInner = NonZeroUsizeInner(value, PrimitiveLayouts.USIZE64)
    }
}

public open class NonZeroIsizeInner internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.ISIZE32,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroIsizeInner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroIsizeInner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroIsizeInner = NonZeroIsizeInner(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: NonZeroIsizeInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroIsizeInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: NonZeroIsizeInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.ISIZE32): NonZeroIsizeInner =
            NonZeroIsizeInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.ISIZE32): NonZeroIsizeInner =
            NonZeroIsizeInner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.ISIZE32): NonZeroIsizeInner? =
            if (value != 0) NonZeroIsizeInner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): NonZeroIsizeInner = NonZeroIsizeInner(value, PrimitiveLayouts.ISIZE32)
    }
}

public open class U32NotAllOnes internal constructor(
    public var value: UInt,
    public val fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<U32NotAllOnes> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> U32NotAllOnes(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): U32NotAllOnes = U32NotAllOnes(value, fieldLayout)

    public open fun asInner(): UInt = value

    public open fun eq(other: U32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): U32NotAllOnes =
            U32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): U32NotAllOnes =
            U32NotAllOnes(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): U32NotAllOnes? =
            if (value < UInt.MAX_VALUE) U32NotAllOnes(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): U32NotAllOnes = U32NotAllOnes(value, PrimitiveLayouts.U32)
    }
}

public open class I32NotAllOnes internal constructor(
    public var value: Int,
    public val fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32,
) : StructuralPartialEq {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<I32NotAllOnes> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> I32NotAllOnes(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): I32NotAllOnes = I32NotAllOnes(value, fieldLayout)

    public open fun asInner(): Int = value

    public open fun eq(other: I32NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I32NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I32NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): I32NotAllOnes =
            I32NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): I32NotAllOnes =
            I32NotAllOnes(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Int, fieldLayout: TypeLayout<Int> = PrimitiveLayouts.I32): I32NotAllOnes? =
            if (value != -1) I32NotAllOnes(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Int): I32NotAllOnes = I32NotAllOnes(value, PrimitiveLayouts.I32)
    }
}

public open class U64NotAllOnes internal constructor(
    public var value: ULong,
    public val fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64,
) : StructuralPartialEq {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<U64NotAllOnes> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> U64NotAllOnes(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): U64NotAllOnes = U64NotAllOnes(value, fieldLayout)

    public open fun asInner(): ULong = value

    public open fun eq(other: U64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: U64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: U64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: ULong, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): U64NotAllOnes =
            U64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): U64NotAllOnes =
            U64NotAllOnes(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: ULong, fieldLayout: TypeLayout<ULong> = PrimitiveLayouts.U64): U64NotAllOnes? =
            if (value < ULong.MAX_VALUE) U64NotAllOnes(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: ULong): U64NotAllOnes = U64NotAllOnes(value, PrimitiveLayouts.U64)
    }
}

public open class I64NotAllOnes internal constructor(
    public var value: Long,
    public val fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64,
) : StructuralPartialEq {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<I64NotAllOnes> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> I64NotAllOnes(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): I64NotAllOnes = I64NotAllOnes(value, fieldLayout)

    public open fun asInner(): Long = value

    public open fun eq(other: I64NotAllOnes): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: I64NotAllOnes): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: I64NotAllOnes): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Long, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): I64NotAllOnes =
            I64NotAllOnes(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): I64NotAllOnes =
            I64NotAllOnes(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Long, fieldLayout: TypeLayout<Long> = PrimitiveLayouts.I64): I64NotAllOnes? =
            if (value != -1L) I64NotAllOnes(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Long): I64NotAllOnes = I64NotAllOnes(value, PrimitiveLayouts.I64)
    }
}

public open class NonZeroCharInner internal constructor(
    public var value: Char,
    public val fieldLayout: TypeLayout<Char> = PrimitiveLayouts.CHAR,
) : StructuralPartialEq {
    public var field0: Char
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<NonZeroCharInner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZeroCharInner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): NonZeroCharInner = NonZeroCharInner(value, fieldLayout)

    public open fun asInner(): Char = value

    public open fun eq(other: NonZeroCharInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: NonZeroCharInner): Ordering = this.asInner().charCmp(other.asInner())

    public open fun partialCmp(other: NonZeroCharInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: Char, fieldLayout: TypeLayout<Char> = PrimitiveLayouts.CHAR): NonZeroCharInner =
            NonZeroCharInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Char> = PrimitiveLayouts.CHAR): NonZeroCharInner =
            NonZeroCharInner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: Char, fieldLayout: TypeLayout<Char> = PrimitiveLayouts.CHAR): NonZeroCharInner? =
            if (value.code in 1..0x10ffff) NonZeroCharInner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: Char): NonZeroCharInner = NonZeroCharInner(value, PrimitiveLayouts.CHAR)
    }
}

public interface NotAllOnesHelper<out Type>

public open class NotAllOnes<T>(
    public var value: T,
)

public open class CodePointInner internal constructor(
    public var value: UInt,
    public val fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32,
) : StructuralPartialEq {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public fun pointerLayout(): TypeLayout<CodePointInner> =
        TransparentTypeLayout(fieldLayout, { ptr, layout -> CodePointInner(layout.readNative(ptr.rawAddress), layout) }, { it.value })

    public open fun clone(): CodePointInner = CodePointInner(value, fieldLayout)

    public open fun asInner(): UInt = value

    public open fun eq(other: CodePointInner): Boolean = this.asInner() == other.asInner()

    public open fun cmp(other: CodePointInner): Ordering = this.asInner().cmp(other.asInner())

    public open fun partialCmp(other: CodePointInner): Ordering? = this.cmp(other)

    public override fun hashCode(): Int = this.asInner().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append(this.asInner().toString())
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): CodePointInner =
            CodePointInner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): CodePointInner =
            CodePointInner(fieldLayout.readNative(fieldStorage.rawAddress), fieldLayout)

        public fun new(value: UInt, fieldLayout: TypeLayout<UInt> = PrimitiveLayouts.U32): CodePointInner? =
            if (value in 0u..0x10ffffu) CodePointInner(value, fieldLayout) else null

        /**
         * Constructs an instance of this type from the underlying integer
         * primitive without checking whether its valid.
         *
         * # Safety
         * Immediate language UB if `val` is not within the valid range for this
         * type, as it violates the validity invariant.
         */
        public fun newUnchecked(value: UInt): CodePointInner = CodePointInner(value, PrimitiveLayouts.U32)

        public val ZERO: CodePointInner = CodePointInner.new(0u, fieldLayout = PrimitiveLayouts.U32)!!

        public fun default(): CodePointInner = CodePointInner.ZERO
    }
}
