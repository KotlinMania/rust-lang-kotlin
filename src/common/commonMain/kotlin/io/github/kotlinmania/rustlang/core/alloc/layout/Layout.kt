// rust-kotlin: manual
// port-lint: source alloc/layout.rs
package io.github.kotlinmania.rustlang.core.alloc.layout

import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedAdd
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedMul
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.mem.sizeOfValRaw
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Layout of a block of memory.
 *
 * An instance of `Layout` describes a particular layout of memory.
 * You build a `Layout` up as an input to give to an allocator.
 *
 * All layouts have an associated size and a power-of-two alignment. The size, when rounded up to
 * the nearest multiple of `align`, does not overflow `Long` (i.e., the rounded value will always be
 * less than or equal to `Long.MAX`).
 *
 * (Note that layouts are *not* required to have non-zero size,
 * even though `GlobalAlloc` requires that all memory requests
 * be non-zero in size. A caller must either ensure that conditions
 * like this are met, use specific allocators with looser
 * requirements, or use the more lenient `Allocator` interface.)
 */
public open class Layout(
    // size of the requested block of memory, measured in bytes.
    size: Int,
    // alignment of the requested block of memory, measured in bytes.
    // we ensure that this is always a power-of-two, because API's
    // like `posixMemalign` require it and it is a reasonable
    // constraint to impose on Layout constructors.
    //
    // (However, we do not analogously require `align >= sizeof(void *)`,
    // even though that is *also* a requirement of `posixMemalign`.)
    align: Alignment,
) {
    internal var size: Int = size
    internal var align: Alignment = align

    public open fun clone(): Layout = Layout(size, align)

    /**
     * The minimum size in bytes for a memory block of this layout.
     */
    public open fun size(): Int = this.size

    /**
     * The minimum byte alignment for a memory block of this layout.
     *
     * The returned alignment is guaranteed to be a power of two.
     */
    public open fun align(): Int = this.align.asUsize()

    /**
     * The minimum byte alignment for a memory block of this layout.
     *
     * The returned alignment is guaranteed to be a power of two.
     */
    public open fun alignment(): Alignment = this.align

    /**
     * Creates a `NonNull` that is dangling, but well-aligned for this Layout.
     *
     * Note that the address of the returned pointer may potentially
     * be that of a valid pointer, which means this must not be used
     * as a "not yet initialized" sentinel value.
     * Types that lazily allocate must track initialization by some other means.
     */
    public open fun danglingPtr(): NonNull = NonNull.withoutProvenance(this.align.asNonzeroUsize())

    /**
     * Creates a layout describing the record that can hold a value
     * of the same layout as `self`, but that also is aligned to
     * alignment `align` (measured in bytes).
     *
     * If `self` already meets the prescribed alignment, then returns
     * `self`.
     *
     * Note that this method does not add any padding to the overall
     * size, regardless of whether the returned layout has a different
     * alignment. In other words, if `K` has size 16, `K.alignTo(32)`
     * will *still* have size 16.
     *
     * Returns an error if the combination of `self.size()` and the given
     * `align` violates the conditions listed in [Layout.fromSizeAlign].
     */
    public open fun alignTo(align: Int): Result<Layout, LayoutError> =
        when (val matchVal = Alignment.new(align)) {
            null -> {
                Result.Err<Layout, LayoutError>(LayoutError())
            }
            else -> {
                val alignment = matchVal
                this.adjustAlignmentTo(alignment)
            }
        }

    /**
     * Creates a layout describing the record that can hold a value
     * of the same layout as `self`, but that also is aligned to
     * alignment `alignment`.
     *
     * If `self` already meets the prescribed alignment, then returns
     * `self`.
     *
     * Note that this method does not add any padding to the overall
     * size, regardless of whether the returned layout has a different
     * alignment. In other words, if `K` has size 16, `K.alignTo(32)`
     * will *still* have size 16.
     *
     * Returns an error if the combination of `self.size()` and the given
     * `alignment` violates the conditions listed in [Layout.fromSizeAlignment].
     */
    public open fun adjustAlignmentTo(alignment: Alignment): Result<Layout, LayoutError> = Layout.fromSizeAlignment(this.size, Alignment.max(this.align, alignment))

    /**
     * Returns the amount of padding we must insert after `self`
     * to ensure that the following address will satisfy `alignment`.
     *
     * e.g., if `self.size()` is 9, then `self.paddingNeededFor(alignment4)`
     * (where `alignment4.asUsize() == 4`)
     * returns 3, because that is the minimum number of bytes of
     * padding required to get a 4-aligned address (assuming that the
     * corresponding memory block starts at a 4-aligned address).
     *
     * Note that the utility of the returned value requires `alignment`
     * to be less than or equal to the alignment of the starting
     * address for the whole allocated block of memory. One way to
     * satisfy this constraint is to ensure `alignment.asUsize() <= self.align()`.
     */
    public open fun paddingNeededFor(alignment: Alignment): Int {
        val lenRoundedUp = this.sizeRoundedUpToCustomAlignment(alignment)
        // SAFETY: Cannot overflow because the rounded-up value is never less
        return uncheckedSub(lenRoundedUp, this.size)
    }

    /**
     * Returns the smallest multiple of `align` greater than or equal to `self.size()`.
     *
     * This can return at most `Alignment.MAX` (aka `Long.MAX + 1`)
     * because the original size is at most `Long.MAX`.
     */
    public open fun sizeRoundedUpToCustomAlignment(alignment: Alignment): Int {
        // SAFETY:
        // Rounded up value is:
        // sizeRoundedUp = (size + align - 1) & !(align - 1);
        //
        // The arithmetic we do here can never overflow:
        //
        // 1. align is guaranteed to be > 0, so align - 1 is always
        // valid.
        //
        // 2. size is at most `Long.MAX`, so adding `align - 1` (which is at
        // most `Long.MAX`) can never overflow a `Int`.
        //
        // 3. masking by the alignment can remove at most `align - 1`,
        // which is what we just added, thus the value we return is never
        // less than the original `size`.
        //
        // (Size 0 Align MAX is already aligned, so stays the same, but things like
        // Size 1 Align MAX or Size isize.Max Align 2 round up to `Long.MAX + 1`.)
        return run {
            val alignM1 = uncheckedSub(alignment.asUsize(), 1)
            uncheckedAdd(this.size, alignM1) and alignM1.inv()
        }
    }

    /**
     * Creates a layout by rounding the size of this layout up to a multiple
     * of the layout's alignment.
     *
     * This is equivalent to adding the result of `paddingNeededFor`
     * to the layout's current size.
     */
    public open fun padToAlign(): Layout {
        // This cannot overflow. Quoting from the invariant of Layout:
        // > `size`, when rounded up to the nearest multiple of `align`,
        // > must not overflow isize (i.e., the rounded value must be
        // > less than or equal to `Long.MAX`)
        val newSize = this.sizeRoundedUpToCustomAlignment(this.align)
        // SAFETY: padded size is guaranteed to not exceed `Long.MAX`.
        return Layout.fromSizeAlignmentUnchecked(newSize, this.alignment())
    }

    /**
     * Creates a layout describing the record for `n` instances of
     * `self`, with a suitable amount of padding between each to
     * ensure that each instance is given its requested size and
     * alignment. On success, returns `(k, offs)` where `k` is the
     * layout of the array and `offs` is the distance between the start
     * of each element in the array.
     *
     * Does not include padding after the trailing element.
     *
     * (That distance between elements is sometimes known as "stride".)
     *
     * On arithmetic overflow, returns `LayoutError`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.alloc.Layout
     *
     * // All rust types have a size that's a multiple of their alignment.
     * val normal = Layout.fromSizeAlign(12, 4).unwrap()
     * val repeated = normal.repeat(3).unwrap()
     * check(repeated == Pair(Layout.fromSizeAlign(36, 4).unwrap(), 12))
     * // But you can manually make layouts which don't meet that rule.
     * val paddingNeeded = Layout.fromSizeAlign(6, 4).unwrap()
     * val repeated1 = paddingNeeded.repeat(3).unwrap()
     * check(repeated1 == Pair(Layout.fromSizeAlign(22, 4).unwrap(), 8))
     * // Repeating an element zero times has zero size, but keeps the alignment (like `[T; 0]`)
     * val repeated2 = normal.repeat(0).unwrap()
     * check(repeated2 == Pair(Layout.fromSizeAlign(0, 4).unwrap(), 12))
     * val repeated3 = paddingNeeded.repeat(0).unwrap()
     * check(repeated3 == Pair(Layout.fromSizeAlign(0, 4).unwrap(), 8))
     * ```
     */
    public open fun repeat(n: Int): Result<Pair<Layout, Int>, LayoutError> {
        // Note(const-Note): the following could be way shorter with `?`
        val padded = this.padToAlign()
        val result =
            when (
                val tryResult1 = (
                    when (val matchVal = n.checkedSub(1)) {
                        null -> {
                            if (false) {
                                check(n == 0)
                            }
                            this.repeatPacked(0)
                        }
                        else -> {
                            val k = matchVal
                            val repeated =
                                when (val tryResult = padded.repeatPacked(k)) {
                                    is Result.Ok<*, *> -> tryResult.okValue()
                                    is Result.Err<*, *> -> return Result.Err<Pair<Layout, Int>, LayoutError>(LayoutError())
                                }
                            repeated.extendPacked(this)
                        }
                    }
                )
            ) {
                is Result.Ok<*, *> -> tryResult1.okValue()
                is Result.Err<*, *> -> return Result.Err<Pair<Layout, Int>, LayoutError>(LayoutError())
            }
        return Result.Ok<Pair<Layout, Int>, LayoutError>(Pair(result, padded.size()))
    }

    /**
     * Creates a layout describing the record for `self` followed by
     * `next`, including any necessary padding to ensure that `next`
     * will be properly aligned, but *no trailing padding*.
     *
     * In order to match C representation layout `repr(C)`, you should
     * call `padToAlign` after extending the layout with all fields.
     * (There is no way to match the default Rust representation
     * layout `repr(Rust)`, as it is unspecified.)
     *
     * Note that the alignment of the resulting layout will be the maximum of
     * those of `self` and `next`, in order to ensure alignment of both parts.
     *
     * Returns `Ok((k, offset))`, where `k` is layout of the concatenated
     * record and `offset` is the relative location, in bytes, of the
     * start of the `next` embedded within the concatenated record
     * (assuming that the record itself starts at offset 0).
     *
     * On arithmetic overflow, returns `LayoutError`.
     *
     * # Examples
     *
     * To calculate the layout of a `@repr(C)` structure and the offsets of
     * the fields from its fields' layouts:
     *
     * ```kotlin
     * fun reprC(fields: List<Layout>): Result<Pair<Layout, List<Int>>> {
     *     var offsets = mutableListOf()
     *     var layout = Layout.fromSizeAlign(0, 1).getOrElse { return Result.failure(it) }
     *     for (field in fields) {
     *         val (newLayout, offset) = layout.extend(field).getOrElse { return Result.failure(it) }
     *         layout = newLayout
     *         offsets.add(offset)
     *     }
     *     // Remember to finalize with `padToAlign`!
     *     return Result.success(Pair(layout.padToAlign(), offsets))
     * }
     * ```
     */
    public open fun extend(next: Layout): Result<Pair<Layout, Int>, LayoutError> {
        val newAlignment = Alignment.max(this.align, next.align)
        val offset = this.sizeRoundedUpToCustomAlignment(next.align)
        // SAFETY: `offset` is at most `Long.MAX + 1` (such as from aligning
        // to `Alignment.MAX`) and `next.size` is at most `Long.MAX` (from the
        // `Layout` type invariant).  Thus the largest possible `newSize` is
        // `Long.MAX + 1 + Long.MAX`, which is `Int.MAX`, and cannot overflow.
        val newSize = uncheckedAdd(offset, next.size)
        return when (val matchVal = Layout.fromSizeAlignment(newSize, newAlignment)) {
            is Result.Ok<*, *> -> {
                val layout = matchVal.okValue()
                Result.Ok<Pair<Layout, Int>, LayoutError>(Pair(layout, offset))
            }
            else -> {
                Result.Err<Pair<Layout, Int>, LayoutError>(LayoutError())
            }
        }
    }

    /**
     * Creates a layout describing the record for `n` instances of
     * `self`, with no padding between each instance.
     *
     * Note that, unlike `repeat`, `repeatPacked` does not guarantee
     * that the repeated instances of `self` will be properly
     * aligned, even if a given instance of `self` is properly
     * aligned. In other words, if the layout returned by
     * `repeatPacked` is used to allocate an array, it is not
     * guaranteed that all elements in the array will be properly
     * aligned.
     *
     * On arithmetic overflow, returns `LayoutError`.
     */
    public open fun repeatPacked(n: Int): Result<Layout, LayoutError> =
        when (val matchVal = this.size.checkedMul(n)) {
            null -> {
                Result.Err<Layout, LayoutError>(LayoutError())
            }
            else -> {
                val size = matchVal
                // The safe constructor is called here to enforce the isize size limit.
                Layout.fromSizeAlignment(size, this.align)
            }
        }

    /**
     * Creates a layout describing the record for `self` followed by
     * `next` with no additional padding between the two. Since no
     * padding is inserted, the alignment of `next` is irrelevant,
     * and is not incorporated *at all* into the resulting layout.
     *
     * On arithmetic overflow, returns `LayoutError`.
     */
    public open fun extendPacked(next: Layout): Result<Layout, LayoutError> {
        // SAFETY: each `size` is at most `Long.MAX == Int.MAX / 2`, so the
        // sum is at most `Int.MAX / 2 * 2 == Int.MAX - 1`, and cannot overflow.
        val newSize = uncheckedAdd(this.size, next.size)
        // The safe constructor enforces that the new size isn't too big for the alignment
        return Layout.fromSizeAlignment(newSize, this.align)
    }

    public companion object {
        /**
         * Constructs a `Layout` from a given `size` and `align`,
         * or returns `LayoutError` if any of the following conditions
         * are not met:
         *
         * * `align` must not be zero,
         *
         * * `align` must be a power of two,
         *
         * * `size`, when rounded up to the nearest multiple of `align`,
         *   must not overflow `Long` (i.e., the rounded value must be
         *   less than or equal to `Long.MAX`).
         */
        public fun fromSizeAlign(size: Int, align: Int): Result<Layout, LayoutError> =
            if (Layout.isSizeAlignValid(size, align)) {
                // SAFETY: Layout.isSizeAlignValid checks the preconditions for this call.
                Result.Ok<Layout, LayoutError>(Layout(size = size, align = Alignment.newUnchecked(align)))
            } else {
                Result.Err<Layout, LayoutError>(LayoutError())
            }

        public fun isSizeAlignValid(size: Int, align: Int): Boolean {
            val alignment = Alignment.new(align) ?: return false
            return Layout.isSizeAlignmentValid(size, alignment)
        }

        public fun isSizeAlignmentValid(size: Int, alignment: Alignment): Boolean = size <= Layout.maxSizeForAlignment(alignment)

        public fun maxSizeForAlignment(alignment: Alignment): Int {
            // (power-of-two implies align != 0.)
            // Rounded up size is:
            // sizeRoundedUp = (size + align - 1) & !(align - 1);
            //
            // We know from above that align != 0. If adding (align - 1)
            // does not overflow, then rounding up will be fine.
            //
            // Conversely, &-masking with(align - 1) will subtract off
            // only low-order-bits. Thus if overflow occurs with the sum,
            // the &-mask cannot subtract enough to undo that overflow.
            //
            // Above implies that checking for summation overflow is both
            // necessary and sufficient.
            // SAFETY: the maximum possible alignment is `Long.MAX + 1`,
            // so the subtraction cannot overflow.
            return uncheckedSub(Int.MAX_VALUE + 1, alignment.asUsize())
        }

        /**
         * Constructs a `Layout` from a given `size` and `alignment`,
         * or returns `LayoutError` if any of the following conditions
         * are not met:
         *
         * * `size`, when rounded up to the nearest multiple of `alignment`,
         *   must not overflow `Long` (i.e., the rounded value must be
         *   less than or equal to `Long.MAX`).
         */
        public fun fromSizeAlignment(size: Int, alignment: Alignment): Result<Layout, LayoutError> =
            if (Layout.isSizeAlignmentValid(size, alignment)) {
                // SAFETY: Layout.size invariants checked above.
                Result.Ok<Layout, LayoutError>(Layout(size = size, align = alignment))
            } else {
                Result.Err<Layout, LayoutError>(LayoutError())
            }

        /**
         * Creates a layout, bypassing all checks.
         *
         * # Safety
         *
         * The arguments passed must meet the preconditions from
         * [Layout.fromSizeAlign].
         */
        public fun fromSizeAlignUnchecked(size: Int, align: Int): Layout {
            // SAFETY: the caller is required to uphold the preconditions.
            return Layout(size = size, align = Alignment.newUnchecked(align))
        }

        /**
         * Creates a layout, bypassing all checks.
         *
         * # Safety
         *
         * This function is unchecked as it does not verify the preconditions from
         * [Layout.fromSizeAlignment].
         */
        public fun fromSizeAlignmentUnchecked(size: Int, alignment: Alignment): Layout {
            // SAFETY: the caller is required to uphold the preconditions.
            return Layout(size = size, align = alignment)
        }

        /**
         * Constructs a `Layout` suitable for holding a value of type `T`.
         */
        public fun <T> new(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Layout = Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())

        /**
         * Produces layout describing a record that could be used to
         * allocate backing structure for `T` (which could be a trait
         * or other unsized type like a slice).
         */
        public fun <T> forValue(t: T): Layout {
            val (size, alignment) = Pair(sizeOfVal(t, typeLayoutT = UnresolvedTypeLayout<T>()), Alignment.ofVal(t))
            // SAFETY: see rationale in `new` for why this is using the unchecked variant
            return Layout.fromSizeAlignmentUnchecked(size, alignment)
        }

        // NOTE: the reason this is safe is that if an overflow were to occur already with size 0,
        // then we would stop compilation as even the "statically known" part of the type would
        // already be too big (or the call may be in dead code and optimized away, but then it
        // doesn't matter).

        /**
         * Produces layout describing a record that could be used to
         * allocate backing structure for `T` (which could be a trait
         * or other unsized type like a slice).
         *
         * # Safety
         *
         * This function is safe to call if the pointer is safe to reborrow as `T`
         * (in which case you could also call [`forValue`][Self.forValue]).
         * Otherwise, the following conditions must hold:
         *
         * - If `T` is `Sized`, this function is always safe to call.
         * - If the unsized tail of `T` is:
         *     - a [slice] `[U]`, `String`, or a [trait object] `dynamic Trait`, then the size of the *entire value*
         *       (dynamic tail length + statically sized prefix) must fit in `Long`.
         *       For the special case where the dynamic tail length is 0, this function
         *       is safe to call.
         *     - No other kind of unsized tail currently exists that satisfies the trait bounds for this
         *       function. If more kinds of unsized tails get introduced in the future, the documentation
         *       of this function will have to be extended before it can be used for such types.
         *
         * Here, *unsized tail* refers to the type obtained by recursively descending through the last
         * field of a tuple or struct until we arrived at a built-in unsized type.
         *
         * As a consequence of these rules, it is the case that whenever it is allowed to convert `val`
         * into a shared reference, then it is also allowed to invoke this function.
         *
         * [trait object]: ../../book/ch17-02-trait-objects.html
         * [extern type]: ../../unstable-book/language-features/extern-types.html
         */
        public fun forValueRaw(value: ConstPtr): Layout {
            // SAFETY: we pass along the prerequisites of these functions to the caller
            val (size, alignment) = Pair(sizeOfValRaw<Unit>(value), Alignment.ofValRaw(value))
            // SAFETY: see rationale in `new` for why this is using the unchecked variant
            return Layout.fromSizeAlignmentUnchecked(size, alignment)
        }

        /**
         * Creates a layout describing the record for a `[T; n]`.
         *
         * On arithmetic overflow or when the total size would exceed
         * `Long.MAX`, returns `LayoutError`.
         */
        public fun <T> array(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Layout, LayoutError> {
            fun inner(elementLayout: Layout, n1: Int): Result<Layout, LayoutError> {
                val elementSize = elementLayout.size
                val alignment = elementLayout.align
                // We need to check two things about the size:
                // - That the total size won't overflow a `Int`, and
                // - That the total size still fits in an `Long`.
                // By using division we can check them both with a single threshold.
                // That'd usually be a bad idea, but thankfully here the element size
                // and alignment are constants, so the compiler will fold all of it.
                if (elementSize != 0 && n1 > Layout.maxSizeForAlignment(alignment) / elementSize) {
                    return Result.Err<Layout, LayoutError>(LayoutError())
                }
                // SAFETY: We just checked that we won't overflow `Int` when we multiply.
                // This is a useless hint inside this function, but after inlining this helps
                // deduplicate checks for whether the overall capacity is zero (e.g., in RawVec's
                // allocation path) before/after this multiplication.
                val arraySize = uncheckedMul(elementSize, n1)
                // SAFETY: We just checked above that the `arraySize` will not
                // exceed `Long.MAX` even when rounded up to the alignment.
                // And `Alignment` guarantees it's a power of two.
                return Result.Ok<Layout, LayoutError>(Layout.fromSizeAlignmentUnchecked(arraySize, alignment))
            }
            // Reduce the amount of code we need to monomorphize per `T`.
            return inner(Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()), n)
        }
    }
}

public typealias LayoutErr = LayoutError

/**
 * The `LayoutError` is returned when the parameters given
 * to `Layout.fromSizeAlign`
 * or some other `Layout` constructor
 * do not satisfy its documented constraints.
 */
public open class LayoutError {
    public open fun clone(): LayoutError = LayoutError()

    // (we need this for downstream impl of trait Error)
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append("invalid parameters to Layout::from_size_align")
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        public fun typeLayout(): TypeLayout<LayoutError> =
            object : TypeLayout<LayoutError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): LayoutError =
                    LayoutError()
                override fun writeNative(addr: Long, value: LayoutError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): LayoutError =
                    LayoutError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: LayoutError) {
                }
            }
    }
}
