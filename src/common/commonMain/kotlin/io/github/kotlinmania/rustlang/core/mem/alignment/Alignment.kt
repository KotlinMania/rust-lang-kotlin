// port-lint: source mem/alignment.rs
package io.github.kotlinmania.rustlang.core.mem.alignment

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOfVal
import io.github.kotlinmania.rustlang.core.mem.alignOfValRaw
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.error.IntErrorKind
import io.github.kotlinmania.rustlang.core.num.error.TryFromIntError
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * A type storing a `Int` which is a power of two, and thus
 * represents a possible alignment in the Rust abstract machine.
 *
 * Note that particularly large alignments, while representable in this type,
 * are likely not to be supported by actual allocators and linkers.
 */
public open class Alignment internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<AlignmentEnum> = UnresolvedTypeLayout(),
) {
    public var innerReprTrick: AlignmentEnum
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(innerReprTrick: AlignmentEnum, fieldLayout: TypeLayout<AlignmentEnum> = UnresolvedTypeLayout()) : this(allocateNativeCell(innerReprTrick, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Alignment> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Alignment(ptr, layout) }, { it.innerReprTrick })

    public open fun clone(): Alignment = Alignment(innerReprTrick, fieldLayout)

    /**
     * Returns the alignment as a [usize].
     */
    public open fun asUsize(): Int {
        // Going through `asNonzeroUsize` helps this be more clearly the inverse of
        // `newUnchecked`, letting MIR optimizations fold it away.
        return this.asNonzeroUsize().get()
    }

    /**
     * Returns the alignment as a <code>[NonZero]<[Int]></code>.
     */
    public open fun asNonzeroUsize(): NonZero<Int, NonZeroUsizeInner> {
        // This transmutes directly to avoid the UbCheck in `NonZero.newUnchecked`
        // since there's no way for the user to trip that check anyway -- the
        // validity invariant of the type would have to have been broken earlier --
        // and emitting it in an otherwise simple method is bad for compile time.
        // SAFETY: All the discriminants are non-zero.
        return transmuteWithLayouts<Alignment, NonZero<Int, NonZeroUsizeInner>>(this, TransparentTypeLayout(this.fieldLayout, { ptr, layout -> Alignment(ptr, layout) }, { it.innerReprTrick }), UnresolvedTypeLayout<NonZero<Int, NonZeroUsizeInner>>())
    }

    /**
     * Returns the base-2 logarithm of the alignment.
     *
     * This is always exact, as `self` represents a power of two.
     *
     * # Examples
     *
     * ```kotlin
     * import std.mem.Alignment
     *
     * @feature(ptrAlignmentType)
     * public fun doctestEntry() {
     *     check(Alignment.of<UByte>().log2() == 0)
     *     check(Alignment.new(1024).unwrap().log2() == 10)
     * }
     * ```
     */
    public open fun log2(): UInt = this.asNonzeroUsize().trailingZeros()

    // Also @unstable(feature = "ptrMask", issue = "98290")

    /**
     * Returns a bit mask that can be used to match this alignment.
     *
     * This is equivalent to `!(self.asUsize() - 1)`.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     * import std.mem.Alignment
     * import std.ptr.NonNull
     *
     * @feature(ptrAlignmentType, ptrMask)
     * public fun doctestEntry() {
     *     @repr(align(1))
     *     open class Align1(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) {
     *         public var field0: UByte
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public var value: UByte
     *             get() = fieldLayout.readNative(fieldStorage.rawAddress)
     *             set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
     *
     *         public constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 1L)), AlignedTypeLayout(fieldLayout, 1L))
     *
     *         public fun pointerLayout(): TypeLayout<Align1> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align1(ptr, layout) }, { it.value })
     *     }
     *     @repr(align(2))
     *     open class Align2(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()) {
     *         public var field0: UShort
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public var value: UShort
     *             get() = fieldLayout.readNative(fieldStorage.rawAddress)
     *             set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
     *
     *         public constructor(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 2L)), AlignedTypeLayout(fieldLayout, 2L))
     *
     *         public fun pointerLayout(): TypeLayout<Align2> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align2(ptr, layout) }, { it.value })
     *     }
     *     @repr(align(4))
     *     open class Align4(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) {
     *         public var field0: UInt
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public var value: UInt
     *             get() = fieldLayout.readNative(fieldStorage.rawAddress)
     *             set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
     *
     *         public constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))
     *
     *         public fun pointerLayout(): TypeLayout<Align4> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align4(ptr, layout) }, { it.value })
     *     }
     *     val one = NonNull.dangling().asPtr()
     *     val two = NonNull.dangling().asPtr()
     *     val four = NonNull.dangling().asPtr()
     *     check(four.mask(Alignment.of<Align1>().mask()) == four)
     *     check(four.mask(Alignment.of<Align2>().mask()) == four)
     *     check(four.mask(Alignment.of<Align4>().mask()) == four)
     *     check(one.mask(Alignment.of<Align4>().mask()) != one)
     * }
     * ```
     */
    public open fun mask(): Int {
        // SAFETY: The alignment is always nonzero, and therefore decrementing won't overflow.
        return !(this.asUsize().uncheckedSub(1))
    }

    public open fun fmt(f: StringBuilder): Result<Unit, TryFromIntError> = f.writeFmt("${this.asNonzeroUsize()} (1 << ${this.log2()})")

    public open fun cmp(other: Alignment): Ordering = this.asNonzeroUsize().cmp(other.asNonzeroUsize())

    public open fun partialCmp(other: Alignment): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.asNonzeroUsize()).hashCode()
        return state
    }

    public companion object {
        public operator fun invoke(innerReprTrick: AlignmentEnum, fieldLayout: TypeLayout<AlignmentEnum> = UnresolvedTypeLayout()): Alignment = Alignment(innerReprTrick, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<AlignmentEnum> = UnresolvedTypeLayout()): Alignment = Alignment(fieldStorage, fieldLayout)

        /**
         * The smallest possible alignment, 1.
         *
         * All addresses are always aligned at least this much.
         *
         * # Examples
         *
         * ```kotlin
         * import std.mem.Alignment
         *
         * check(Alignment.MIN.asUsize() == 1)
         * ```
         */
        public val MIN: Alignment = Alignment.new(1)!!

        /**
         * Returns the alignment for a type.
         *
         * This provides the same numerical value as [alignOf],
         * but in an `Alignment` instead of a `Int`.
         */
        public fun <T> of(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Alignment = Alignment.newUnchecked((typeLayoutT).alignBytes.toInt())

        /**
         * Returns the [ABI]-required minimum alignment of the type of the value that `val` points to.
         *
         * This function is identical to [`Alignment.of.T`][Self.of] whenever
         * <code>T: [Sized]</code>,
         * but also supports determining the alignment required by a `dynamic Trait` value, which is the
         * alignment of the underlying concrete type.
         *
         * This provides the same numerical value as [alignOfVal],
         * but in an `Alignment` instead of a `Int`.
         *
         * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
         *
         * # Examples
         *
         * ```kotlin
         * import std.mem.Alignment
         *
         * check(Alignment.ofVal(5).asUsize() == 4)
         * ```
         *
         * (Caution: [it is not guaranteed][type-layout] that the alignment of `Int` is `4`;
         * that is, the above assertion does not pass on all platforms.)
         *
         * `dynamic` types may have different alignments for different values;
         * `Alignment.ofVal()` can be used to learn those alignments:
         *
         * ```kotlin
         * import std.mem.Alignment
         *
         * val a: ToString = 1234u.toUShort()
         * val b: ToString = "abcd"
         * check(Alignment.ofVal(a) == Alignment.of<UShort>())
         * check(Alignment.ofVal(b) == Alignment.of<String>())
         * ```
         *
         * [type-layout]: ../../reference/type-layout.html#r-layout.primitive
         */
        public fun <T> ofVal(value: T): Alignment {
            val align = alignOfVal(value, typeLayoutT = UnresolvedTypeLayout<T>())
            // SAFETY: `alignOfVal` returns valid alignment
            return Alignment.newUnchecked(align)
        }

        /**
         * Returns the [ABI]-required minimum alignment of the type of the value that `val` points to.
         *
         * This function is identical to [Alignment.ofVal], except that it can be used with raw
         * pointers in situations where it would be unsound or undesirable to convert them to
         * [ references][primitive@reference] and impose the aliasing rules that come with that.
         *
         * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
         *
         * # Safety
         *
         * This function is safe to call if the pointer is safe to reborrow as `T`
         * (in which case you could also call [`ofVal`][Self.ofVal]).
         * Otherwise, the following conditions must hold:
         *
         * - If `T` is `Sized`, this function is always safe to call.
         * - If the unsized tail of `T` is:
         *     - a [slice], then the length of the slice tail must be an initialized
         *       integer, and the size of the *entire value*
         *       (dynamic tail length + statically sized prefix) must fit in `Long`.
         *       For the special case where the dynamic tail length is 0, this function
         *       is safe to call.
         *     - a [trait object], then the vtable part of the pointer must point
         *       to a valid vtable acquired by an unsizing coercion, and the size
         *       of the *entire value* (dynamic tail length + statically sized prefix)
         *       must fit in `Long`.
         *     - an (unstable) [extern type], then this function is always safe to
         *       call, but may panic or otherwise return the wrong value, as the
         *       extern type's layout is not known. This is the same behavior as
         *       [Alignment.ofVal] on a reference to a type with an extern type tail.
         *     - otherwise, it is conservatively not allowed to call this function.
         *
         * [trait object]: ../../book/ch17-02-trait-objects.html
         * [extern type]: ../../unstable-book/language-features/extern-types.html
         *
         * # Examples
         *
         * ```kotlin
         * import std.mem.Alignment
         *
         * check(Alignment.ofValRaw(5).asUsize() == 4)
         * ```
         *
         * (Caution: [it is not guaranteed][type-layout] that the alignment of `Int` is `4`;
         * that is, the above assertion does not pass on all platforms.)
         *
         * [type-layout]: ../../reference/type-layout.html#r-layout.primitive
         */
        public fun ofValRaw(value: ConstPtr): Alignment {
            // SAFETY: precondition propagated to the caller
            val align = alignOfValRaw(value)
            // SAFETY: `alignOfValRaw` returns valid alignment
            return Alignment.newUnchecked(align)
        }

        /**
         * Creates an `Alignment` from a `Int`, or returns `None` if it's
         * not a power of two.
         *
         * Note that `0` is not a power of two, nor a valid alignment.
         */
        public fun new(align: Int): Alignment? =
            if (align.isPowerOfTwo()) {
                // SAFETY: Just checked it only has one bit set
                Alignment.newUnchecked(align)
            } else {
                null
            }

        /**
         * Creates an `Alignment` from a power-of-two `Int`.
         *
         * # Safety
         *
         * `align` must be a power of two.
         *
         * Equivalently, it must be `1<<exp` for some `exp` in `0 .. Int.BITS`.
         * It must *not* be zero.
         */
        public fun newUnchecked(align: Int): Alignment {
            Unit
            // SAFETY: By precondition, this must be a power of two, and
            // our variants encompass all possible powers of two.
            return transmuteWithLayouts<Int, Alignment>(align, PrimitiveLayouts.USIZE64, UnresolvedTypeLayout<Alignment>())
        }

        // Note(const-Note) Remove me once `Ord.max` is usable in const
        public fun max(a: Alignment, b: Alignment): Alignment =
            if (a.asUsize() > b.asUsize()) {
                a
            } else {
                b
            }

        public fun tryFrom(align: NonZero<Int, NonZeroUsizeInner>): Result<Alignment, TryFromIntError> = align.get().tryInto()

        public fun tryFrom(align: Int): Result<Alignment, TryFromIntError> = (Alignment.new(align)?.let { Result.Ok<Alignment, TryFromIntError>(it) } ?: Result.Err<Alignment, TryFromIntError>(TryFromIntError(IntErrorKind.NotAPowerOfTwo)))

        /**
         * Returns [Alignment.Min], which is valid for any type.
         */
        public fun default(): Alignment = Alignment.MIN
    }
}

public fun alignmentCanBeStructurallyMatched(a: Alignment): Boolean =
    when (a) {
        Alignment.min -> true
        else -> false
    }

public fun fromNonZeroInt(align: Alignment): NonZero<Int, NonZeroUsizeInner> = align.asNonzeroUsize()

public fun Int.Companion.from(align: Alignment): Int = align.asUsize()

public sealed class AlignmentEnum(
    public val value: Int,
) {
    public data object Align1Shl0 : AlignmentEnum(1 shl 0)
    public data object Align1Shl1 : AlignmentEnum(1 shl 1)
    public data object Align1Shl2 : AlignmentEnum(1 shl 2)
    public data object Align1Shl3 : AlignmentEnum(1 shl 3)
    public data object Align1Shl4 : AlignmentEnum(1 shl 4)
    public data object Align1Shl5 : AlignmentEnum(1 shl 5)
    public data object Align1Shl6 : AlignmentEnum(1 shl 6)
    public data object Align1Shl7 : AlignmentEnum(1 shl 7)
    public data object Align1Shl8 : AlignmentEnum(1 shl 8)
    public data object Align1Shl9 : AlignmentEnum(1 shl 9)
    public data object Align1Shl10 : AlignmentEnum(1 shl 10)
    public data object Align1Shl11 : AlignmentEnum(1 shl 11)
    public data object Align1Shl12 : AlignmentEnum(1 shl 12)
    public data object Align1Shl13 : AlignmentEnum(1 shl 13)
    public data object Align1Shl14 : AlignmentEnum(1 shl 14)
    public data object Align1Shl15 : AlignmentEnum(1 shl 15)
    public data object Align1Shl16 : AlignmentEnum(1 shl 16)
    public data object Align1Shl17 : AlignmentEnum(1 shl 17)
    public data object Align1Shl18 : AlignmentEnum(1 shl 18)
    public data object Align1Shl19 : AlignmentEnum(1 shl 19)
    public data object Align1Shl20 : AlignmentEnum(1 shl 20)
    public data object Align1Shl21 : AlignmentEnum(1 shl 21)
    public data object Align1Shl22 : AlignmentEnum(1 shl 22)
    public data object Align1Shl23 : AlignmentEnum(1 shl 23)
    public data object Align1Shl24 : AlignmentEnum(1 shl 24)
    public data object Align1Shl25 : AlignmentEnum(1 shl 25)
    public data object Align1Shl26 : AlignmentEnum(1 shl 26)
    public data object Align1Shl27 : AlignmentEnum(1 shl 27)
    public data object Align1Shl28 : AlignmentEnum(1 shl 28)
    public data object Align1Shl29 : AlignmentEnum(1 shl 29)
    public data object Align1Shl30 : AlignmentEnum(1 shl 30)
    public data object Align1Shl31 : AlignmentEnum(1 shl 31)
    public data object Align1Shl32 : AlignmentEnum(1 shl 32)
    public data object Align1Shl33 : AlignmentEnum(1 shl 33)
    public data object Align1Shl34 : AlignmentEnum(1 shl 34)
    public data object Align1Shl35 : AlignmentEnum(1 shl 35)
    public data object Align1Shl36 : AlignmentEnum(1 shl 36)
    public data object Align1Shl37 : AlignmentEnum(1 shl 37)
    public data object Align1Shl38 : AlignmentEnum(1 shl 38)
    public data object Align1Shl39 : AlignmentEnum(1 shl 39)
    public data object Align1Shl40 : AlignmentEnum(1 shl 40)
    public data object Align1Shl41 : AlignmentEnum(1 shl 41)
    public data object Align1Shl42 : AlignmentEnum(1 shl 42)
    public data object Align1Shl43 : AlignmentEnum(1 shl 43)
    public data object Align1Shl44 : AlignmentEnum(1 shl 44)
    public data object Align1Shl45 : AlignmentEnum(1 shl 45)
    public data object Align1Shl46 : AlignmentEnum(1 shl 46)
    public data object Align1Shl47 : AlignmentEnum(1 shl 47)
    public data object Align1Shl48 : AlignmentEnum(1 shl 48)
    public data object Align1Shl49 : AlignmentEnum(1 shl 49)
    public data object Align1Shl50 : AlignmentEnum(1 shl 50)
    public data object Align1Shl51 : AlignmentEnum(1 shl 51)
    public data object Align1Shl52 : AlignmentEnum(1 shl 52)
    public data object Align1Shl53 : AlignmentEnum(1 shl 53)
    public data object Align1Shl54 : AlignmentEnum(1 shl 54)
    public data object Align1Shl55 : AlignmentEnum(1 shl 55)
    public data object Align1Shl56 : AlignmentEnum(1 shl 56)
    public data object Align1Shl57 : AlignmentEnum(1 shl 57)
    public data object Align1Shl58 : AlignmentEnum(1 shl 58)
    public data object Align1Shl59 : AlignmentEnum(1 shl 59)
    public data object Align1Shl60 : AlignmentEnum(1 shl 60)
    public data object Align1Shl61 : AlignmentEnum(1 shl 61)
    public data object Align1Shl62 : AlignmentEnum(1 shl 62)
    public data object Align1Shl63 : AlignmentEnum(1 shl 63)

    public fun rustEquals(other: AlignmentEnum): Boolean = this == other

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value

    public fun toUInt(): UInt = value.toUInt()

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()
}
