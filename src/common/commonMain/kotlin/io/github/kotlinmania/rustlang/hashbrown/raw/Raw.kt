// port-lint: source raw.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.hashbrown.raw

import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.array.fromFn
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.swap
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.saturatingMul
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.swapNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.hashbrown.Guard
import io.github.kotlinmania.rustlang.hashbrown.TryReserveError
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Global
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.doAlloc
import io.github.kotlinmania.rustlang.hashbrown.control.bitmask.BitMaskIter
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.Group
import io.github.kotlinmania.rustlang.hashbrown.control.tag.Tag
import io.github.kotlinmania.rustlang.hashbrown.scopeguard.ScopeGuard
import io.github.kotlinmania.rustlang.hashbrown.util.invalidMut
import io.github.kotlinmania.rustlang.hashbrown.util.likely
import io.github.kotlinmania.rustlang.hashbrown.util.unlikely

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr)
}

public fun <T> offsetFrom(to: ConstPtr, from: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int = (((to).rawAddress - (from).rawAddress) / (typeLayoutT).strideBytes).toInt()

/**
 * Whether memory allocation errors should return an error or abort.
 */
public sealed class Fallibility {
    public data object Fallible : Fallibility()
    public data object Nothing : Fallibility()

    /**
     * Error to return on capacity overflow.
     */
    public open fun capacityOverflow(): TryReserveError =
        when (this) {
            Fallibility.Fallible -> TryReserveError.CapacityOverflow
            Fallibility.Nothing -> error("Hash table capacity overflow")
        }

    /**
     * Error to return on allocation error.
     */
    public open fun allocErr(layout: Layout): TryReserveError =
        when (this) {
            Fallibility.Fallible -> TryReserveError.AllocError(layout = layout)
            Fallibility.Nothing -> handleAllocError(layout)
        }
}

public interface SizedTypeProperties {
    public val IS_ZERO_SIZED: Boolean
        get() = sizeOf<SizedTypeProperties>(typeLayoutT = UnresolvedTypeLayout<SizedTypeProperties>()) == 0

    public val NEEDS_DROP: Boolean
        get() = needsDrop<SizedTypeProperties>()
}

/**
 * Primary hash function, used to select the initial bucket to probe from.
 */
public fun h1(hash: ULong): Int {
    // On 32-bit platforms we simply ignore the higher hash bits.
    return hash.toInt()
}

/**
 * Probe sequence based on triangular numbers, which is guaranteed (since our
 * table size is a power of two) to visit every group of elements exactly once.
 *
 * A triangular probe has us jump by 1 more group every time. So first we
 * jump by 1 group (meaning we just continue our linear scan), then 2 groups
 * (skipping over 1 group), then 3 groups (skipping over 2 groups), and so on.
 *
 * Proof that the probe will visit every group in the table:
 * <https://fgiesen.wordpress.com/2015/02/22/triangular-numbers-mod-2n/>
 */
public open class ProbeSeq(
    public var pos: Int,
    public var stride: Int,
) {
    public open fun clone(): ProbeSeq = ProbeSeq(pos, stride)

    public open fun moveNext(bucketMask: Int) {
        // We should have found an empty bucket by now and ended the probe.
        check(this.stride <= bucketMask) { "Went past end of probe sequence" }
        this.stride += Group.WIDTH
        this.pos += this.stride
        this.pos = this.pos and bucketMask
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ProbeSeq> =
            object : TypeLayout<ProbeSeq> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ProbeSeq =
                    ProbeSeq(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: ProbeSeq) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.pos)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.stride)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProbeSeq =
                    ProbeSeq(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProbeSeq) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.pos)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.stride)
                }
            }
    }
}

// Workaround for emscripten bug emscripten-core/emscripten-fastcomp#258

/**
 * Returns the number of buckets needed to hold the given number of items,
 * taking the maximum load factor into account.
 *
 * Returns `None` if an overflow occurs.
 *
 * This ensures that `buckets * tableLayout.size >= tableLayout.ctrlAlign`.
 */
public fun capacityToBuckets(cap: Int, tableLayout: TableLayout): Int? {
    check(cap != 0)
    // For small tables we require at least 1 empty bucket so that lookups are
    // guaranteed to terminate if an element doesn't exist in the table.
    if (cap < 15) {
        // Consider a small TableLayout like ( size: 1, ctrlAlign: 16 ) on a
        // platform with Group.Width of 16 (like x8664 with SSE2). For small
        // bucket sizes, this ends up wasting quite a few bytes just to pad to
        // the relatively larger ctrlAlign:
        //
        // | capacity | buckets | bytes allocated | bytes per item |
        // | -------- | ------- | --------------- | -------------- |
        // |        3 |       4 |              36 | (Yikes!)  12.0 |
        // |        7 |       8 |              40 | (Poor)     5.7 |
        // |       14 |      16 |              48 |            3.4 |
        // |       28 |      32 |              80 |            3.3 |
        //
        // In general, buckets * tableLayout.size >= tableLayout.ctrlAlign
        // must be true to avoid these edges. This is implemented by adjusting
        // the minimum capacity upwards for small items. This code only needs
        // to handle ctrlAlign which are less than or equal to Group.Width,
        // because valid layout sizes are always a multiple of the alignment,
        // so anything with alignment over the Group.Width won't hit this edge
        // case.
        // This is brittle, e.g. if we ever add 32 byte groups, it will select
        // 3 regardless of the tableLayout.size.
        val minCap =
            run {
                val matchVal = Pair(Group.WIDTH, tableLayout.size)
                when {
                    matchVal.first == 16 && matchVal.second in 0..1 -> 14
                    matchVal.first == 16 && matchVal.second in 2..3 -> 7
                    matchVal.first == 8 && matchVal.second in 0..1 -> 7
                    else -> 3
                }
            }
        val cap1 = minCap.max(cap)
        // We don't bother with a table size of 2 buckets since that can only
        // hold a single element. Instead, we skip directly to a 4 bucket table
        // which can hold 3 elements.
        val buckets =
            if (cap1 < 4) {
                4
            } else if (cap1 < 8) {
                8
            } else {
                16
            }
        ensureBucketBytesAtLeastCtrlAlign(tableLayout, buckets)
        return buckets
    }
    // Otherwise require 1/8 buckets to be empty (87.5% load)
    //
    // Be careful when modifying this, calculateLayout relies on the
    // overflow check here.
    val adjustedCap = (cap.checkedMul(8) ?: return null) / 7
    // Any overflows will have been caught by the checkedMul. Also, any
    // rounding errors from the division above will be cleaned up by
    // nextPowerOfTwo (which can't overflow because of the previous division).
    val buckets1 = adjustedCap.nextPowerOfTwo()
    ensureBucketBytesAtLeastCtrlAlign(tableLayout, buckets1)
    return buckets1
}

// `maximumBucketsIn` relies on the property that for non-ZST `T`, any
// chosen `buckets` will satisfy `buckets * tableLayout.size >=
// tableLayout.ctrlAlign`, so`calculateLayoutFor` does not need to add
// extra padding beyond `tableLayout.size * buckets`. If small-table bucket
// selection or growth policy changes, revisit `maximumBucketsIn`.
public fun ensureBucketBytesAtLeastCtrlAlign(tableLayout: TableLayout, buckets: Int) {
    if (tableLayout.size != 0) {
        val prod = tableLayout.size.saturatingMul(buckets)
        check(prod >= tableLayout.ctrlAlign)
    }
}

/**
 * Returns the maximum effective capacity for the given bucket mask, taking
 * the maximum load factor into account.
 */
public fun bucketMaskToCapacity(bucketMask: Int): Int =
    if (bucketMask < 8) {
        // For tables with 1/2/4/8 buckets, we always reserve one empty slot.
        // Keep in mind that the bucket mask is one less than the bucket count.
        bucketMask
    } else {
        // For larger tables we reserve 12.5% of the slots as empty.
        ((bucketMask + 1) / 8) * 7
    }

/**
 * Helper which allows the max calculation for `ctrlAlign` to be statically computed for each `T`
 * while keeping the rest of `calculateLayoutFor` independent of `T`
 */
public open class TableLayout(
    public var size: Int,
    public var ctrlAlign: Int,
) {
    public open fun clone(): TableLayout = TableLayout(size, ctrlAlign)

    public open fun calculateLayoutFor(buckets: Int): Pair<Layout, Int>? {
        check(buckets.isPowerOfTwo())
        val size = this.size
        val ctrlAlign = this.ctrlAlign
        // Manual layout calculation since Layout methods are not yet stable.
        val ctrlOffset = ((size.checkedMul(buckets) ?: return null).checkedAdd(ctrlAlign - 1) ?: return null) and (ctrlAlign - 1).inv()
        val len = (ctrlOffset.checkedAdd(buckets + Group.WIDTH) ?: return null)
        // We need an additional check to ensure that the allocation doesn't
        // exceed `Long.MAX` (https://github.com/rust-lang/rust/pull/95295).
        if (len > Int.MAX_VALUE - (ctrlAlign - 1)) {
            return null
        }
        return Pair(Layout.fromSizeAlignUnchecked(len, ctrlAlign), ctrlOffset)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<TableLayout> =
            object : TypeLayout<TableLayout> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): TableLayout =
                    TableLayout(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: TableLayout) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.size)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.ctrlAlign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TableLayout =
                    TableLayout(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TableLayout) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.size)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.ctrlAlign)
                }
            }

        public fun <T> new(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): TableLayout {
            val layout = Layout.new<T>(typeLayoutT = typeLayoutT)
            return TableLayout(size = layout.size(), ctrlAlign = if (layout.align() > Group.WIDTH) {
                layout.align()
            } else {
                Group.WIDTH
            })
        }
    }
}

/**
 * A reference to a hash table bucket containing a `T`.
 *
 * This is usually just a pointer to the element itself. However if the element
 * is a ZST, then we instead track the index of the element in the table so
 * that `erase` works properly.
 */
public open class Bucket<T>(
    // Actually it is pointer to next element than element itself
    // this is needed to maintain pointer arithmetic invariants
    // keeping direct pointer to element introduces difficulty.
    // Using `NonNull` for variance and niche layout
    public var ptr: NonNull,
) {
    // This Send impl is needed for rayon support. This is safe since Bucket is
    // never exposed in a public API.

    public open fun clone(): Bucket<T> = Bucket(ptr = this.ptr)

    /**
     * Calculates the index of a [Bucket] as distance between two pointers
     * (convenience for `base.asPtr().offsetFrom(self.ptr.asPtr()) as Int`).
     * The returned value is in units of T: the distance in bytes divided by
     * [core.mem.sizeOf.T].
     *
     * If the `T` is a ZST, then we return the index of the element in
     * the table so that `erase` works properly (return `self.ptr.asPtr() as Int - 1`).
     *
     * This function is the inverse of [fromBaseIndex].
     *
     * # Safety
     *
     * If `mem.sizeOf<T>() != 0`, then the safety rules are directly derived
     * from the safety rules for [const.T.offsetFrom] method of `* const T`.
     *
     * Thus, in order to uphold the safety contracts for [const.T.offsetFrom]
     * method, as well as for the correct logic of the work of this crate, the
     * following rules are necessary and sufficient:
     *
     * * `base` contained pointer must not be `dangling` and must point to the
     *   end of the first `element` from the `data part` of the table, i.e.
     *   must be a pointer that returns by [RawTable.dataEnd] or by
     *   [RawTableInner.dataEnd.T];
     *
     * * `self` also must not contain dangling pointer;
     *
     * * both `self` and `base` must be created from the same [RawTable]
     *   (or [RawTableInner]).
     *
     * If `mem.sizeOf<T>() == 0`, this function is always safe.
     */
    public open fun toBaseIndex(base: NonNull): Int {
        // If mem.sizeOf::<T>() != 0 then return an index under which we used to store the
        // `element` in the data part of the table (we start counting from "0", so
        // that in the expression T[last], the "last" index actually is one less than the
        // "buckets" number in the table, i.e. "last = RawTableInner.bucketMask").
        // For example for 5th element in table calculation is performed like this:
        //
        // mem.sizeOf::<T>()
        // |
        // |         `self = fromBaseIndex(base, 5)` that returns pointer
        // |         that points here in the data part of the table
        // |         (to the end of T5)
        // |           |                    `base: NonNull<T>` must point here
        // v           |                    (to the end of T0 or to the start of C0)
        // /???\         v                      v
        // [Padding], Tlast, ..., |T10|, ..., T5|, T4, T3, T2, T1, T0, |C0, C1, C2, C3, C4, C5, ..., C10, ..., Clast
        // \__________  __________/
        // \/
        // `bucket.toBaseIndex(base)` = 5
        // (base.asPtr() as Int - self.ptr.asPtr() as Int) / mem.sizeOf::<T>()
        //
        // where: T0...Tlast - our stored data; C0...Clast - control bytes or metadata for data.
        return if (T.IS_ZERO_SIZED) {
            // this can not be UB
            this.ptr.asPtr().toInt() - 1
        } else {
            offsetFrom((base.asPtr()).asConstPtr(), (this.ptr.asPtr()).asConstPtr(), typeLayoutT = UnresolvedTypeLayout<T>())
        }
    }

    /**
     * Acquires the underlying raw pointer `* T` to `data`.
     *
     * # Note
     *
     * If `T` is not [Copy], do not use `* T` methods that can cause calling the
     * destructor of `T` (for example the [mut.T.dropInPlace] method), because
     * for properly dropping the data we also need to clear `data` control bytes. If we
     * drop data, but do not clear `data control byte` it leads to double drop when
     * [RawTable] goes out of scope.
     *
     * If you modify an already initialized `value`, so [Hash] and [Eq] on the new
     * `T` value and its borrowed form *must* match those for the old `T` value, as the map
     * will not re-evaluate where the new value should go, meaning the value may become
     * "lost" if their location does not reflect their state.
     */
    public open fun asPtr(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
        return if (T.IS_ZERO_SIZED) {
            // Just return an arbitrary ZST pointer which is properly aligned
            // invalid pointer is good enough for ZST
            invalidMut(alignOf<T>(typeLayoutT = typeLayoutT))
        } else {
            this.ptr.asPtr().sub(1)
        }
    }

    /**
     * Acquires the underlying non-null pointer `* T` to `data`.
     */
    public open fun asNonNull(): NonNull {
        // SAFETY: `self.ptr` is already a `NonNull`
        return NonNull.newUnchecked(this.asPtr())
    }

    /**
     * Create a new [Bucket] that is offset from the `self` by the given
     * `offset`. The pointer calculation is performed by calculating the
     * offset from `self` pointer (convenience for `self.ptr.asPtr().sub(offset)`).
     * This function is used for iterators.
     *
     * `offset` is in units of `T`; e.g., a `offset` of 3 represents a pointer
     * offset of `3 * sizeOf<T>()` bytes.
     *
     * # Safety
     *
     * If `mem.sizeOf<T>() != 0`, then the safety rules are directly derived
     * from the safety rules for [mut.T.sub] method of `* T` and safety
     * rules of [NonNull.newUnchecked] function.
     *
     * Thus, in order to uphold the safety contracts for [mut.T.sub] method
     * and [NonNull.newUnchecked] function, as well as for the correct
     * logic of the work of this crate, the following rules are necessary and
     * sufficient:
     *
     * * `self` contained pointer must not be `dangling`;
     *
     * * `self.toBaseIndex() + offset` must not be greater than `RawTableInner.bucketMask`,
     *   i.e. `(self.toBaseIndex() + offset) <= RawTableInner.bucketMask` or, in other
     *   words, `self.toBaseIndex() + offset + 1` must be no greater than the number returned
     *   by the function [RawTable.numBuckets] or [RawTableInner.numBuckets].
     *
     * If `mem.sizeOf<T>() == 0`, then the only requirement is that the
     * `self.toBaseIndex() + offset` must not be greater than `RawTableInner.bucketMask`,
     * i.e. `(self.toBaseIndex() + offset) <= RawTableInner.bucketMask` or, in other words,
     * `self.toBaseIndex() + offset + 1` must be no greater than the number returned by the
     * function [RawTable.numBuckets] or [RawTableInner.numBuckets].
     */
    public open fun nextN(offset: Int): Bucket<T> {
        val ptr =
            if (T.IS_ZERO_SIZED) {
                // invalid pointer is good enough for ZST
                invalidMut(this.ptr.asPtr().toInt() + offset)
            } else {
                this.ptr.asPtr().sub(offset)
            }
        return Bucket(ptr = NonNull.newUnchecked(ptr))
    }

    /**
     * Executes the destructor (if any) of the pointed-to `data`.
     *
     * # Safety
     *
     * See [ptr.dropInPlace] for safety concerns.
     *
     * You should use [RawTable.erase] instead of this function,
     * or be careful with calling this function directly, because for
     * properly dropping the data we need also clear `data` control bytes.
     * If we drop data, but do not erase `data control byte` it leads to
     * double drop when [RawTable] goes out of scope.
     */
    public open fun drop() {
        run {
            this.asPtr().dropInPlace()
        }
    }

    /**
     * Reads the `value` from `self` without moving it. This leaves the
     * memory in `self` unchanged.
     *
     * # Safety
     *
     * See [ptr.read] for safety concerns.
     *
     * You should use [RawTable.remove] instead of this function,
     * or be careful with calling this function directly, because compiler
     * calls its destructor when the read `value` goes out of scope. It
     * can cause double dropping when [RawTable] goes out of scope,
     * because of not erased `data control byte`.
     */
    public open fun read(): T = this.asPtr().read()

    /**
     * Overwrites a memory location with the given `value` without reading
     * or dropping the old value (like [ptr.write] function).
     *
     * # Safety
     *
     * See [ptr.write] for safety concerns.
     *
     * # Note
     *
     * [Hash] and [Eq] on the new `T` value and its borrowed form *must* match
     * those for the old `T` value, as the map will not re-evaluate where the new
     * value should go, meaning the value may become "lost" if their location
     * does not reflect their state.
     */
    public open fun write(value: T) {
        run {
            this.asPtr().write(value)
        }
    }

    /**
     * Returns a shared immutable reference to the `value`.
     *
     * # Safety
     *
     * See [NonNull.asRef] for safety concerns.
     */
    public open fun asRef(): T = this.asPtr()

    /**
     * Returns a unique mutable reference to the `value`.
     *
     * # Safety
     *
     * See [NonNull.asMut] for safety concerns.
     *
     * # Note
     *
     * [Hash] and [Eq] on the new `T` value and its borrowed form *must* match
     * those for the old `T` value, as the map will not re-evaluate where the new
     * value should go, meaning the value may become "lost" if their location
     * does not reflect their state.
     */
    public open fun asMut(): T = this.asPtr()

    public companion object {
        /**
         * Creates a [Bucket] that contain pointer to the data.
         * The pointer calculation is performed by calculating the
         * offset from given `base` pointer (convenience for
         * `base.asPtr().sub(index)`).
         *
         * `index` is in units of `T`; e.g., an `index` of 3 represents a pointer
         * offset of `3 * sizeOf<T>()` bytes.
         *
         * If the `T` is a ZST, then we instead track the index of the element
         * in the table so that `erase` works properly (return
         * `NonNull.newUnchecked((index + 1) as * T)`)
         *
         * # Safety
         *
         * If `mem.sizeOf<T>() != 0`, then the safety rules are directly derived
         * from the safety rules for [mut.T.sub] method of `* T` and the safety
         * rules of [NonNull.newUnchecked] function.
         *
         * Thus, in order to uphold the safety contracts for the [mut.T.sub] method
         * and [NonNull.newUnchecked] function, as well as for the correct
         * logic of the work of this crate, the following rules are necessary and
         * sufficient:
         *
         * * the `base` pointer must not be `dangling` and must points to the
         *   end of the first `value element` from the `data part` of the table, i.e.
         *   must be the pointer that returned by [RawTable.dataEnd] or by
         *   [RawTableInner.dataEnd.T];
         *
         * * `index` must not be greater than `RawTableInner.bucketMask`, i.e.
         *   `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)`
         *   must be no greater than the number returned by the function
         *   [RawTable.numBuckets] or [RawTableInner.numBuckets].
         *
         * If `mem.sizeOf<T>() == 0`, then the only requirement is that the
         * `index` must not be greater than `RawTableInner.bucketMask`, i.e.
         * `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)`
         * must be no greater than the number returned by the function
         * [RawTable.numBuckets] or [RawTableInner.numBuckets].
         */
        public fun <T> fromBaseIndex(base: NonNull, index: Int): Bucket<T> {
            // If mem.sizeOf::<T>() != 0 then return a pointer to an `element` in
            // the data part of the table (we start counting from "0", so that
            // in the expression T[last], the "last" index actually one less than the
            // "buckets" number in the table, i.e. "last = RawTableInner.bucketMask"):
            //
            // `fromBaseIndex(base, 1).asPtr()` returns a pointer that
            // points here in the data part of the table
            // (to the start of T1)
            // |
            // |        `base: NonNull<T>` must point here
            // |         (to the end of T0 or to the start of C0)
            // v         v
            // [Padding], Tlast, ..., |T1|, T0, |C0, C1, ..., Clast
            // ^
            // `fromBaseIndex(base, 1)` returns a pointer
            // that points here in the data part of the table
            // (to the end of T1)
            //
            // where: T0...Tlast - our stored data; C0...Clast - control bytes
            // or metadata for data.
            val ptr =
                if (T.IS_ZERO_SIZED) {
                    // won't overflow because index must be less than length (bucketMask)
                    // and bucketMask is guaranteed to be less than `Long.MAX`
                    // (see TableLayout.calculateLayoutFor method)
                    invalidMut(index + 1)
                } else {
                    base.asPtr().sub(index)
                }
            return Bucket(ptr = NonNull.newUnchecked(ptr))
        }
    }
}

/**
 * A raw hash table with an unchecked API.
 */
public open class RawTable<T, A : Allocator>(
    public var table: RawTableInner,
    public var alloc: A,
    // Tell dropck that we own instances of T.
    public var marker: Unit,
) : RawTableClone<RawTable<T, A>>, Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.alloc

    /**
     * Returns pointer to one past last `data` element in the table as viewed from
     * the start point of the allocation.
     *
     * The caller must ensure that the `RawTable` outlives the returned [NonNull.T],
     * otherwise using it may result in [undefined.behavior].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun dataEnd(): NonNull {
        // `self.table.ctrl.cast()` returns pointer that
        // points here (to the end of `T0`)
        // ∨
        // [Pad], TN, ..., T1, T0, |CT0, CT1, ..., CtN|, Cta0, Cta1, ..., CtaM
        // \________  ________/
        // \/
        // `n = buckets - 1`, i.e. `RawTable.numBuckets() - 1`
        //
        // where: T0...TN  - our stored data;
        // CT0...CtN - control bytes or metadata for `data`.
        // Cta0...CtaM - additional control bytes, where `m = Group.WIDTH - 1` (so that the search
        // with loading `Group` bytes from the heap works properly, even if the result
        // of `h1(hash) & self.bucketMask` is equal to `self.bucketMask`). See also
        // `RawTableInner.setCtrl` function.
        //
        // P.S. `h1(hash) & self.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
        // of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
        return this.table.ctrl.cast()
    }

    /**
     * Returns pointer to start of data table.
     */
    public open fun dataStart(): NonNull = NonNull.newUnchecked(this.dataEnd().asPtr().wrappingSub(this.numBuckets()))

    /**
     * Returns the total amount of memory allocated internally by the hash
     * table, in bytes.
     *
     * The returned number is informational only. It is intended to be
     * primarily used for memory profiling.
     */
    public open fun allocationSize(): Int {
        // SAFETY: We use the same `tableLayout` that was used to allocate
        // this table.
        return this.table.allocationSizeOrZero(RawTable.TABLE_LAYOUT)
    }

    /**
     * Returns the index of a bucket from a `Bucket`.
     */
    public open fun bucketIndex(bucket: Bucket<T>): Int = bucket.toBaseIndex(this.dataEnd())

    /**
     * Returns a pointer to an element in the table.
     *
     * The caller must ensure that the `RawTable` outlives the returned [Bucket.T],
     * otherwise using it may result in [undefined.behavior].
     *
     * # Safety
     *
     * If `mem.sizeOf<T>() != 0`, then the caller of this function must observe the
     * following safety rules:
     *
     * * The table must already be allocated;
     *
     * * The `index` must not be greater than the number returned by the [RawTable.numBuckets]
     *   function, i.e. `(index + 1) <= self.numBuckets()`.
     *
     * It is safe to call this function with index of zero (`index == 0`) on a table that has
     * not been allocated, but using the returned [Bucket] results in [undefined.behavior].
     *
     * If `mem.sizeOf<T>() == 0`, then the only requirement is that the `index` must
     * not be greater than the number returned by the [RawTable.numBuckets] function, i.e.
     * `(index + 1) <= self.numBuckets()`.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun bucket(index: Int): Bucket<T> {
        // If mem.sizeOf::<T>() != 0 then return a pointer to the `element` in the `data part` of the table
        // (we start counting from "0", so that in the expression T[n], the "n" index actually one less than
        // the "buckets" number of our `RawTable`, i.e. "n = RawTable.numBuckets() - 1"):
        //
        // `table.bucket(3).asPtr()` returns a pointer that points here in the `data`
        // part of the `RawTable`, i.e. to the start of T3 (see `Bucket.asPtr`)
        // |
        // |               `base = self.dataEnd()` points here
        // |               (to the start of CT0 or to the end of T0)
        // v                 v
        // [Pad], TN, ..., |T3|, T2, T1, T0, |CT0, CT1, CT2, CT3, ..., CtN, Cta0, Cta1, ..., CtaM
        // ^                                              \__________  __________/
        // `table.bucket(3)` returns a pointer that points                        \/
        // here in the `data` part of the `RawTable` (to              additional control bytes
        // the end of T3)                                              `m = Group.WIDTH - 1`
        //
        // where: T0...TN  - our stored data;
        // CT0...CtN - control bytes or metadata for `data`;
        // Cta0...CtaM - additional control bytes (so that the search with loading `Group` bytes from
        // the heap works properly, even if the result of `h1(hash) & self.table.bucketMask`
        // is equal to `self.table.bucketMask`). See also `RawTableInner.setCtrl` function.
        //
        // P.S. `h1(hash) & self.table.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
        // of buckets is a power of two, and `self.table.bucketMask = self.numBuckets() - 1`.
        check(this.table.bucketMask != 0)
        check(index < this.numBuckets())
        return Bucket.fromBaseIndex(this.dataEnd(), index)
    }

    /**
     * Erases an element from the table without dropping it.
     */
    public open fun eraseNoDrop(item: Bucket<T>) {
        run {
            val index = this.bucketIndex(item)
            this.table.erase(index)
        }
    }

    /**
     * Erases an element from the table, dropping it in place.
     */
    public open fun erase(item: Bucket<T>) {
        run {
            // Erase the element from the table first since drop might panic.
            this.eraseNoDrop(item)
            item.drop()
        }
    }

    /**
     * Removes an element from the table, returning it.
     *
     * This also returns an index to the newly free bucket.
     */
    public open fun remove(item: Bucket<T>): Pair<T, Int> =
        run {
            this.eraseNoDrop(item)
            Pair(item.read(), this.bucketIndex(item))
        }

    /**
     * Removes an element from the table, returning it.
     *
     * This also returns an index to the newly free bucket
     * and the former `Tag` for that bucket.
     */
    public open fun removeTagged(item: Bucket<T>): Triple<T, Int, Tag> =
        run {
            val index = this.bucketIndex(item)
            val tag = (UnresolvedTypeLayout<Tag>()).readNative((this.table.ctrl(index)).rawAddress)
            this.table.erase(index)
            Triple(item.read(), index, tag)
        }

    /**
     * Finds and removes an element from the table, returning it.
     */
    public open fun removeEntry(hash: ULong, eq: (T) -> Boolean): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.find(hash, eq)) {
            null -> null
            else -> {
                val bucket = matchVal
                this.remove(bucket).first
            }
        }
    }

    /**
     * Marks all table buckets as empty without dropping their contents.
     */
    public open fun clearNoDrop() {
        this.table.clearNoDrop()
    }

    /**
     * Removes all elements from the table without freeing the backing memory.
     */
    public open fun clear() {
        if (this.isEmpty()) {
            // Special case empty table to avoid surprising O(capacity) time.
            return
        }
        // Ensure that the table is reset even if one of the drops panic
        val selfRef = Guard(this, { selfRef -> selfRef.clearNoDrop() })
        run {
            // SAFETY: ScopeGuard sets to zero the `items` field of the table
            // even in case of panic during the dropping of the elements so
            // that there will be no double drop of the elements.
            selfRef.table.dropElements<T>()
        }
    }

    /**
     * Shrinks the table to fit `max(self.len(), minSize)` elements.
     */
    public open fun shrinkTo(minSize: Int, hasher: (T) -> ULong) {
        // Calculate the minimal number of elements that we need to reserve
        // space for.
        val minSize1 = maxOf(this.table.items, minSize)
        if (minSize1 == 0) {
            val oldInner = replace(this.table, RawTableInner.NEW)
            run {
                // SAFETY:
                // 1. We call the function only once;
                // 2. We know for sure that `alloc` and `tableLayout` matches the [Allocator]
                // and [TableLayout] that were used to allocate this table.
                // 3. If any elements' drop function panics, then there will only be a memory leak,
                // because we have replaced the inner table with a new one.
                oldInner.dropInnerTable<T>(this.alloc, RawTable.TABLE_LAYOUT)
            }
            return
        }
        // Calculate the number of buckets that we need for this number of
        // elements. If the calculation overflows then the requested bucket
        // count must be larger than what we have right and nothing needs to be
        // done.
        val minBuckets = capacityToBuckets(minSize1, RawTable.TABLE_LAYOUT) ?: return
        // If we have more buckets than we need, shrink the table.
        if (minBuckets < this.numBuckets()) {
            // Fast path if the table is empty
            if (this.table.items == 0) {
                val newInner = RawTableInner.withCapacity(this.alloc, RawTable.TABLE_LAYOUT, minSize1)
                val oldInner1 = replace(this.table, newInner)
                run {
                    // SAFETY:
                    // 1. We call the function only once;
                    // 2. We know for sure that `alloc` and `tableLayout` matches the [Allocator]
                    // and [TableLayout] that were used to allocate this table.
                    // 3. If any elements' drop function panics, then there will only be a memory leak,
                    // because we have replaced the inner table with a new one.
                    oldInner1.dropInnerTable<T>(this.alloc, RawTable.TABLE_LAYOUT)
                }
            } else {
                // SAFETY:
                // 1. We know for sure that `minSize >= self.table.items`.
                // 2. The [RawTableInner] must already have properly initialized control bytes since
                // we will never expose RawTable.newUninitialized in a public API.
                val result = this.resize(minSize1, hasher, Fallibility.Nothing)
                // SAFETY: The result of calling the `resize` function cannot be an error
                // because `fallibility == Fallibility.Nothing.
                result.unwrapUnchecked()
            }
        }
    }

    /**
     * Ensures that at least `additional` items can be inserted into the table
     * without reallocation.
     */
    public open fun reserve(additional: Int, hasher: (T) -> ULong) {
        if (unlikely(additional > this.table.growthLeft)) {
            // SAFETY: The [RawTableInner] must already have properly initialized control
            // bytes since we will never expose RawTable.newUninitialized in a public API.
            val result = this.reserveRehash(additional, hasher, Fallibility.Nothing)
            // SAFETY: All allocation errors will be caught inside `RawTableInner.reserveRehash`.
            result.unwrapUnchecked()
        }
    }

    /**
     * Tries to ensure that at least `additional` items can be inserted into
     * the table without reallocation.
     */
    public open fun tryReserve(additional: Int, hasher: (T) -> ULong): Result<Unit, TryReserveError> =
        if (additional > this.table.growthLeft) {
            // SAFETY: The [RawTableInner] must already have properly initialized control
            // bytes since we will never expose RawTable.newUninitialized in a public API.
            this.reserveRehash(additional, hasher, Fallibility.Fallible)
        } else {
            Result.Ok<Unit, TryReserveError>(Unit)
        }

    /**
     * Out-of-line slow path for `reserve` and `tryReserve`.
     *
     * # Safety
     *
     * The [RawTableInner] must have properly initialized control bytes,
     * otherwise calling this function results in [undefined.behavior]
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun reserveRehash(additional: Int, hasher: (T) -> ULong, fallibility: Fallibility): Result<Unit, TryReserveError> =
        this.table.reserveRehashInner(this.alloc, additional, { table, index -> hasher(table.bucket<T>(index).asRef()) }, fallibility, RawTable.TABLE_LAYOUT, if (T.NEEDS_DROP) {
            { ptr -> dropInPlace(ptr.cast()) }
        } else {
            null
        })

    /**
     * Allocates a new table of a different size and moves the contents of the
     * current table into it.
     *
     * # Safety
     *
     * The [RawTableInner] must have properly initialized control bytes,
     * otherwise calling this function results in [undefined.behavior]
     *
     * The caller of this function must ensure that `capacity >= self.table.items`
     * otherwise:
     *
     * * If `self.table.items != 0`, calling of this function with `capacity`
     *   equal to 0 (`capacity == 0`) results in [undefined.behavior].
     *
     * * If `self.table.items> capacityToBuckets(capacity, Self.TABLE_LAYOUT)`
     *   calling this function are never return (will loop infinitely).
     *
     * See [RawTableInner.findInsertIndex] for more information.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun resize(capacity: Int, hasher: (T) -> ULong, fallibility: Fallibility): Result<Unit, TryReserveError> {
        // SAFETY:
        // 1. The caller of this function guarantees that `capacity >= self.table.items`.
        // 2. We know for sure that `alloc` and `layout` matches the [Allocator] and
        // [TableLayout] that were used to allocate this table.
        // 3. The caller ensures that the control bytes of the `RawTableInner`
        // are already initialized.
        return this.table.resizeInner(this.alloc, capacity, { table, index -> hasher(table.bucket<T>(index).asRef()) }, fallibility, RawTable.TABLE_LAYOUT)
    }

    /**
     * Inserts a new element into the table, and returns its raw bucket.
     *
     * This does not check if the given element already exists in the table.
     */
    public open fun insert(hash: ULong, value: T, hasher: (T) -> ULong): Bucket<T> {
        return run {
            // SAFETY:
            // 1. The [RawTableInner] must already have properly initialized control bytes since
            // we will never expose `RawTable.newUninitialized` in a public API.
            //
            // 2. We reserve additional space (if necessary) right after calling this function.
            var index = this.table.findInsertIndex(hash)
            // We can avoid growing the table once we have reached our load factor if we are replacing
            // a tombstone. This works since the number of EMPTY slots does not change in this case.
            //
            // SAFETY: The function is guaranteed to return an index in the range `0 ..= self.numBuckets()`.
            val oldCtrl = (UnresolvedTypeLayout<Tag>()).readNative((this.table.ctrl(index)).rawAddress)
            if (unlikely(this.table.growthLeft == 0 && oldCtrl.specialIsEmpty())) {
                this.reserve(1, hasher)
                // SAFETY: We know for sure that `RawTableInner` has control bytes
                // initialized and that there is extra space in the table.
                index = this.table.findInsertIndex(hash)
            }
            this.insertAtIndex(hash, index, value)
        }
    }

    /**
     * Inserts a new element into the table, and returns a mutable reference to it.
     *
     * This does not check if the given element already exists in the table.
     */
    public open fun insertEntry(hash: ULong, value: T, hasher: (T) -> ULong): T = this.insert(hash, value, hasher).asMut()

    /**
     * Inserts a new element into the table, without growing the table.
     *
     * There must be enough space in the table to insert the new element.
     *
     * This does not check if the given element already exists in the table.
     */
    public open fun insertNoGrow(hash: ULong, value: T): Bucket<T> =
        run {
            val (index, oldCtrl) = this.table.prepareInsertIndex(hash)
            val bucket = this.table.bucket(index)
            // If we are replacing a DELETED entry then we don't need to update
            // the load counter.
            this.table.growthLeft -= oldCtrl.specialIsEmpty().toInt()
            bucket.write(value)
            this.table.items += 1
            bucket
        }

    /**
     * Temporarily removes a bucket, applying the given function to the removed
     * element and optionally put back the returned value in the same bucket.
     *
     * Returns tag for bucket if the bucket is emptied out.
     *
     * This does not check if the given bucket is actually occupied.
     */
    public open fun replaceBucketWith(bucket: Bucket<T>, f: (T) -> T?): Tag? =
        run {
            val index = this.bucketIndex(bucket)
            val oldCtrl = (UnresolvedTypeLayout<Tag>()).readNative((this.table.ctrl(index)).rawAddress)
            check(this.isBucketFull(index))
            val oldGrowthLeft = this.table.growthLeft
            val item = this.remove(bucket).first
            when (val matchVal = f(item)) {
                null -> {
                    oldCtrl
                }
                else -> {
                    val newItem = matchVal
                    this.table.growthLeft = oldGrowthLeft
                    this.table.setCtrl(index, oldCtrl)
                    this.table.items += 1
                    this.bucket(index).write(newItem)
                    null
                }
            }
        }

    /**
     * Searches for an element in the table. If the element is not found,
     * returns `Err` with the position of a slot where an element with the
     * same hash could be inserted.
     *
     * This function may resize the table if additional space is required for
     * inserting an element.
     */
    public open fun findOrFindInsertIndex(hash: ULong, eq: (T) -> Boolean, hasher: (T) -> ULong): Result<Bucket<T>, Int> {
        this.reserve(1, hasher)
        return run {
            val matchVal = this.table.findOrFindInsertIndexInner(hash, { index -> eq(this.bucket(index).asRef()) })
            when (matchVal) { is Result.Ok<*, *> -> {
                val index = matchVal.value
                Result.Ok<Bucket<T>, Int>(this.bucket(index))
            }; is Result.Err<*, *> -> {
                val index1 = matchVal.error
                Result.Err<Bucket<T>, Int>(index1)
            } }
        }
    }

    /**
     * Inserts a new element into the table at the given index with the given hash,
     * and returns its raw bucket.
     *
     * # Safety
     *
     * `index` must point to a slot previously returned by
     * `findOrFindInsertIndex`, and no mutation of the table must have
     * occurred since that call.
     */
    public open fun insertAtIndex(hash: ULong, index: Int, value: T): Bucket<T> = this.insertTaggedAtIndex(Tag.full(hash), index, value)

    /**
     * Inserts a new element into the table at the given index with the given tag,
     * and returns its raw bucket.
     *
     * # Safety
     *
     * `index` must point to a slot previously returned by
     * `findOrFindInsertIndex`, and no mutation of the table must have
     * occurred since that call.
     */
    public open fun insertTaggedAtIndex(tag: Tag, index: Int, value: T): Bucket<T> =
        run {
            val oldCtrl = (UnresolvedTypeLayout<Tag>()).readNative((this.table.ctrl(index)).rawAddress)
            this.table.recordItemInsertAt(index, oldCtrl, tag)
            val bucket = this.bucket(index)
            bucket.write(value)
            bucket
        }

    /**
     * Searches for an element in the table.
     */
    public open fun find(hash: ULong, eq: (T) -> Boolean): Bucket<T>? =
        run {
            // SAFETY:
            // 1. The [RawTableInner] must already have properly initialized control bytes since we
            // will never expose `RawTable.newUninitialized` in a public API.
            // 1. The `findInner` function returns the `index` of only the full bucket, which is in
            // the range `0 .. self.numBuckets()`, so calling `self.bucket(index)` and `Bucket.asRef`
            // is safe.
            val result = this.table.findInner(hash, { index -> eq(this.bucket(index).asRef()) })
            // Avoid `Option.map` because it bloats LLVM IR.
            when (result) {
                null -> null
                // SAFETY: See explanation above.
                else -> {
                    val index = result
                    this.bucket(index)
                }
            }
        }

    /**
     * Gets a reference to an element in the table.
     */
    public open fun get(hash: ULong, eq: (T) -> Boolean): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.find(hash, eq)) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asRef()
            }
        }
    }

    /**
     * Gets a mutable reference to an element in the table.
     */
    public open fun getMut(hash: ULong, eq: (T) -> Boolean): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.find(hash, eq)) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asMut()
            }
        }
    }

    /**
     * Gets a reference to an element in the table at the given bucket index.
     */
    public open fun getBucket(index: Int): T? =
        if (index < this.numBuckets() && this.isBucketFull(index)) {
            this.bucket(index).asRef()
        } else {
            null
        }

    /**
     * Gets a mutable reference to an element in the table at the given bucket index.
     */
    public open fun getBucketMut(index: Int): T? =
        if (index < this.numBuckets() && this.isBucketFull(index)) {
            this.bucket(index).asMut()
        } else {
            null
        }

    /**
     * Returns a pointer to an element in the table, but only after verifying that
     * the index is in-bounds and the bucket is occupied.
     */
    public open fun checkedBucket(index: Int): Bucket<T>? =
        if (index < this.numBuckets() && this.isBucketFull(index)) {
            this.bucket(index)
        } else {
            null
        }

    /**
     * Attempts to get mutable references to `N` entries in the table at once.
     *
     * Returns an array of length `N` with the results of each query.
     *
     * At most one mutable reference will be returned to any entry. `None` will be returned if any
     * of the hashes are duplicates. `None` will be returned if the hash is not found.
     *
     * The `eq` argument should be a closure such that `eq(i, k)` returns true if `k` is equal to
     * the `i`th key to be looked up.
     */
    public open fun getDisjointMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int): List<T> {
        return run {
            val ptrs = this.getDisjointMutPointers(hashes, eq, n)
            for ((i, cur) in ptrs.iter().mapIndexed { index, item -> Pair(index, item) }) {
                if (cur != null && ptrs[0 until i].contains(cur)) {
                    error("duplicate keys found")
                }
            }
            // All bucket are distinct from all previous buckets so we're clear to return the result
            // of the lookup.
            ptrs.map({ ptr -> ptr.map({ ptr1 -> ptr1.asMut() }) })
        }
    }

    public open fun getDisjointUncheckedMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int): List<T> {
        val ptrs = this.getDisjointMutPointers(hashes, eq, n)
        return ptrs.map({ ptr -> ptr.map({ ptr1 -> ptr1.asMut() }) })
    }

    public open fun getDisjointMutPointers(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int): List<NonNull> =
        fromFn({ i ->
            this.find(hashes[i], { k -> eq(i, k) })?.let({ cur -> cur.asNonNull() })
        }, n)

    /**
     * Returns the number of elements the map can hold without reallocating.
     *
     * This number is a lower bound; the table might be able to hold
     * more, but is guaranteed to be able to hold at least this many.
     */
    public open fun capacity(): Int = this.table.items + this.table.growthLeft

    /**
     * Returns the number of elements in the table.
     */
    public open fun len(): Int = this.table.items

    /**
     * Returns `true` if the table contains no elements.
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Returns the number of buckets in the table.
     */
    public open fun numBuckets(): Int = this.table.bucketMask + 1

    /**
     * Checks whether the bucket at `index` is full.
     *
     * # Safety
     *
     * The caller must ensure `index` is less than the number of buckets.
     */
    public open fun isBucketFull(index: Int): Boolean = this.table.isBucketFull(index)

    /**
     * Returns an iterator over every element in the table. It is up to
     * the caller to ensure that the `RawTable` outlives the `RawIter`.
     * Because we cannot make the `next` method unchecked on the `RawIter`
     * struct, we have to make the `iter` method unchecked.
     */
    public open fun iter(): RawIter<T> {
        // SAFETY:
        // 1. The caller must uphold the safety contract for `iter` method.
        // 2. The [RawTableInner] must already have properly initialized control bytes since
        // we will never expose RawTable.newUninitialized in a public API.
        return this.table.iter()
    }

    /**
     * Returns an iterator over occupied buckets that could match a given hash.
     *
     * `RawTable` only stores 7 bits of the hash value, so this iterator may
     * return items that have a hash value different than the one provided. You
     * should always validate the returned values before using them.
     *
     * It is up to the caller to ensure that the `RawTable` outlives the
     * `RawIterHash`. Because we cannot make the `next` method unchecked on the
     * `RawIterHash` struct, we have to make the `iterHash` method unchecked.
     */
    public open fun iterHash(hash: ULong): RawIterHash<T> = RawIterHash.new(this, hash)

    /**
     * Returns an iterator over occupied bucket indices that could match a given hash.
     *
     * `RawTable` only stores 7 bits of the hash value, so this iterator may
     * return items that have a hash value different than the one provided. You
     * should always validate the returned values before using them.
     *
     * It is up to the caller to ensure that the `RawTable` outlives the
     * `RawIterHashIndices`. Because we cannot make the `next` method unchecked on the
     * `RawIterHashIndices` struct, we have to make the `iterHashBuckets` method unchecked.
     */
    public open fun iterHashBuckets(hash: ULong): RawIterHashIndices = RawIterHashIndices.new(this.table, hash)

    /**
     * Returns an iterator over full buckets indices in the table.
     *
     * See [RawTableInner.fullBucketsIndices] for safety conditions.
     */
    public open fun fullBucketsIndices(): FullBucketsIndices = this.table.fullBucketsIndices()

    /**
     * Returns an iterator which removes all elements from the table without
     * freeing the memory.
     */
    public open fun drain(): RawDrain<T, A> =
        run {
            val iter = this.iter()
            this.drainIterFrom(iter)
        }

    /**
     * Returns an iterator which removes all elements from the table without
     * freeing the memory.
     *
     * Iteration starts at the provided iterator's current location.
     *
     * It is up to the caller to ensure that the iterator is valid for this
     * `RawTable` and covers all items that remain in the table.
     */
    public open fun drainIterFrom(iter: RawIter<T>): RawDrain<T, A> {
        check(iter.len() == this.len())
        return RawDrain(iter = iter, table = replace(this.table, RawTableInner.NEW), origTable = NonNull.from(this.table), marker = Unit)
    }

    /**
     * Returns an iterator which consumes all elements from the table.
     *
     * Iteration starts at the provided iterator's current location.
     *
     * It is up to the caller to ensure that the iterator is valid for this
     * `RawTable` and covers all items that remain in the table.
     */
    public open fun intoIterFrom(iter: RawIter<T>): RawIntoIter<T, A> {
        check(iter.len() == this.len())
        val allocation = this.intoAllocation()
        return RawIntoIter(iter = iter, allocation = allocation, marker = Unit)
    }

    /**
     * Converts the table into a raw allocation. The contents of the table
     * should be dropped using a `RawIter` before freeing the allocation.
     */
    public open fun intoAllocation(): Triple<NonNull, Layout, A>? {
        val alloc =
            if (this.table.isEmptySingleton()) {
                null
            } else {
                val (layout, ctrlOffset) = run {
                    val option = RawTable.TABLE_LAYOUT.calculateLayoutFor(this.table.numBuckets())
                    option.unwrapUnchecked()
                }
                Triple(NonNull.newUnchecked(this.table.ctrl.asPtr().sub(ctrlOffset).cast()), layout, read(addrOf(this.alloc)))
            }
        forget(this)
        return alloc
    }

    public open fun clone(): RawTable<T, A> =
        if (this.table.isEmptySingleton()) {
            RawTable.newIn(this.alloc.clone())
        } else {
            // SAFETY: This is safe as we are taking the size of an already allocated table
            // and therefore capacity overflow cannot occur, `self.table.numBuckets()` is power
            // of two and all allocator errors will be caught inside `RawTableInner.newUninitialized`.
            val result = RawTable.newUninitialized(this.alloc.clone(), this.table.numBuckets(), Fallibility.Nothing)
            // SAFETY: The result of calling the `newUninitialized` function cannot be an error
            // because `fallibility == Fallibility.Nothing.
            val newTable = result.unwrapUnchecked()
            // SAFETY:
            // Cloning elements may fail (the clone function may panic). But we don't
            // need to worry about uninitialized control bits, since:
            // 1. The number of items (elements) in the table is zero, which means that
            // the control bits will not be read by Drop function.
            // 2. The `cloneFromSpec` method will first copy all control bits from
            // `self` (thus initializing them). But this will not affect the `Drop`
            // function, since the `cloneFromSpec` function sets `items` only after
            // successfully cloning all elements.
            newTable.cloneFromSpec(this)
            newTable
        }

    public open fun cloneFrom(source: RawTable<T, A>) {
        if (source.table.isEmptySingleton()) {
            val oldInner = replace(this.table, RawTableInner.NEW)
            run {
                // SAFETY:
                // 1. We call the function only once;
                // 2. We know for sure that `alloc` and `tableLayout` matches the [Allocator]
                // and [TableLayout] that were used to allocate this table.
                // 3. If any elements' drop function panics, then there will only be a memory leak,
                // because we have replaced the inner table with a new one.
                oldInner.dropInnerTable<T>(this.alloc, RawTable.TABLE_LAYOUT)
            }
        } else {
            run {
                // Make sure that if any panics occurs, we clear the table and
                // leave it in an empty state.
                var selfRef =
                    Guard(this, { selfRef ->
                        selfRef.clearNoDrop()
                    })
                // First, drop all our elements without clearing the control
                // bytes. If this panics then the scope guard will clear the
                // table, leaking any elements that were not dropped yet.
                //
                // This leak is unavoidable: we can't try dropping more elements
                // since this could lead to another panic and abort the process.
                //
                // SAFETY: If something gets wrong we clear our table right after
                // dropping the elements, so there is no double drop, since `items`
                // will be equal to zero.
                selfRef.table.dropElements<T>()
                // If necessary, resize our table to match the source.
                if (selfRef.numBuckets() != source.numBuckets()) {
                    val newInner = run {
                        val result = RawTableInner.newUninitialized(selfRef.alloc, RawTable.TABLE_LAYOUT, source.numBuckets(), Fallibility.Nothing)
                        result.unwrapUnchecked()
                    }
                    // Replace the old inner with new uninitialized one. It's ok, since if something gets
                    // wrong `ScopeGuard` will initialize all control bytes and leave empty table.
                    val oldInner1 = replace(selfRef.table, newInner)
                    if (!oldInner1.isEmptySingleton()) {
                        // SAFETY:
                        // 1. We have checked that our table is allocated.
                        // 2. We know for sure that `alloc` and `tableLayout` matches
                        // the [Allocator] and [TableLayout] that were used to allocate this table.
                        oldInner1.freeBuckets(selfRef.alloc, RawTable.TABLE_LAYOUT)
                    }
                }
                // Cloning elements may fail (the clone function may panic), but the `ScopeGuard`
                // inside the `cloneFromImpl` function will take care of that, dropping all
                // cloned elements if necessary. Our `ScopeGuard` will clear the table.
                selfRef.cloneFromSpec(source)
                // Disarm the scope guard if cloning was successful.
                ScopeGuard.intoInner(selfRef)
            }
        }
    }

    public override fun cloneFromSpec(source: RawTable<T, A>) {
        run {
            this.cloneFromImpl(source)
        }
    }

    /**
     * Common code for `clone` and `cloneFrom`. Assumes:
     * - `self.numBuckets() == source.numBuckets()`.
     * - Any existing elements have been dropped.
     * - The control bytes are not initialized yet.
     */
    public open fun cloneFromImpl(source: RawTable<T, A>) {
        // Copy the control bytes unchanged. We do this in a single pass
        run {
            kmCopyNonoverlapping((source.table.ctrl(0)).rawAddress, (this.table.ctrl(0)).rawAddress, (this.table.numCtrlBytes()).toLong() * (UnresolvedTypeLayout<Tag>()).sizeBytes)
        }
        // The cloning of elements may panic, in which case we need
        // to make sure we drop only the elements that have been
        // cloned so far.
        var guard =
            Guard(Pair(0, this), { (index, selfRef) ->
                if (T.NEEDS_DROP) {
                    for (i in 0 until index) {
                        if (selfRef.isBucketFull(i)) {
                            selfRef.bucket(i).drop()
                        }
                    }
                }
            })
        for (from in source.iter()) {
            val index = source.bucketIndex(from)
            val to = guard.second.bucket(index)
            to.write(from.asRef().clone())
            // Update the index in case we need to unwind.
            guard.first = index + 1
        }
        // Successfully cloned all items, no need to clean up.
        forget(guard)
        this.table.items = source.table.items
        this.table.growthLeft = source.table.growthLeft
    }

    public open fun drop() {
        // SAFETY:
        // 1. We call the function only once;
        // 2. We know for sure that `alloc` and `tableLayout` matches the [Allocator]
        // and [TableLayout] that were used to allocate this table.
        // 3. If the drop function of any elements fails, then only a memory leak will occur,
        // and we don't care because we are inside the `Drop` function of the `RawTable`,
        // so there won't be any table left in an inconsistent state.
        run {
            this.table.dropInnerTable<T>(this.alloc, RawTable.TABLE_LAYOUT)
        }
    }

    public open fun intoIter(): RawIntoIter<T, A> =
        run {
            val iter = this.iter()
            this.intoIterFrom(iter)
        }

    public companion object {
        public val TABLE_LAYOUT: TableLayout = TableLayout.new<T>()

        /**
         * Creates a new empty hash table without allocating any memory, using the
         * given allocator.
         *
         * In effect this returns a table with exactly 1 bucket. However we can
         * leave the data pointer dangling since that bucket is never written to
         * due to our load factor forcing us to always have at least 1 free bucket.
         */
        public fun <T, A : Allocator> newIn(alloc: A): RawTable<T, A> = RawTable(table = RawTableInner.NEW, alloc = alloc, marker = Unit)

        /**
         * Allocates a new hash table with the given number of buckets.
         *
         * The control bytes are left uninitialized.
         */
        public fun <T, A : Allocator> newUninitialized(alloc: A, buckets: Int, fallibility: Fallibility): Result<RawTable<T, A>, TryReserveError> {
            check(buckets.isPowerOfTwo())
            return Result.Ok<RawTable<T, A>, TryReserveError>(RawTable(table = when (val tryResult = RawTableInner.newUninitialized(alloc, RawTable.TABLE_LAYOUT, buckets, fallibility)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<RawTable<T, A>, TryReserveError>(tryResult.error) }, alloc = alloc, marker = Unit))
        }

        /**
         * Allocates a new hash table using the given allocator, with at least enough capacity for
         * inserting the given number of elements without reallocating.
         */
        public fun <T, A : Allocator> withCapacityIn(capacity: Int, alloc: A): RawTable<T, A> = RawTable(table = RawTableInner.withCapacity(alloc, RawTable.TABLE_LAYOUT, capacity), alloc = alloc, marker = Unit)

        public fun <T, A : Allocator> default(defaultAFn: () -> A): RawTable<T, A> = RawTable.newIn(defaultAFn())
    }
}

/**
 * Non-generic part of `RawTable` which allows functions to be instantiated only once regardless
 * of how many different key-value types are used.
 */
public open class RawTableInner internal constructor(
    // Mask to get an index from a hash value. The value is one less than the
    // number of buckets in the table.
    public var bucketMask: Int,
    ctrlCtorArg: NonNull,
    // Number of elements that can be inserted before we need to grow the table
    public var growthLeft: Int,
    // Number of elements in the table, only really used by len()
    public var items: Int,
) {
    // [Padding], TN, ..., T1, T0, C0, C1, ...
    // ^ points here
    public var ctrl: NonNull = ctrlCtorArg
        internal set

    /**
     * Fixes up an insertion index returned by the [RawTableInner.findInsertIndexInGroup] method.
     *
     * In tables smaller than the group width (`self.numBuckets()<Group.WIDTH`), trailing control
     * bytes outside the range of the table are filled with [Tag.Empty] entries. These will unfortunately
     * trigger a match of [RawTableInner.findInsertIndexInGroup] function. This is because
     * the `Some(bit)` returned by `group.matchEmptyOrDeleted().lowestSetBit()` after masking
     * (`(probeSeq.pos + bit) & self.bucketMask`) may point to a full bucket that is already occupied.
     * We detect this situation here and perform a second scan starting at the beginning of the table.
     * This second scan is guaranteed to find an empty slot (due to the load factor) before hitting the
     * trailing control bytes (containing [Tag.Empty] bytes).
     *
     * If this function is called correctly, it is guaranteed to return an index of an empty or
     * deleted bucket in the range `0 .. self.numBuckets()` (see `Warning` and `Safety`).
     *
     * # Warning
     *
     * The table must have at least 1 empty or deleted `bucket`, otherwise if the table is less than
     * the group width (`self.numBuckets()<Group.WIDTH`) this function returns an index outside of the
     * table indices range `0 .. self.numBuckets()` (`0 ..= self.bucketMask`). Attempt to write data at that
     * index will cause immediate [undefined.behavior].
     *
     * # Safety
     *
     * The safety rules are directly derived from the safety rules for [RawTableInner.ctrl] method.
     * Thus, in order to uphold those safety contracts, as well as for the correct logic of the work
     * of this crate, the following rules are necessary and sufficient:
     *
     * * The [RawTableInner] must have properly initialized control bytes otherwise calling this
     *   function results in [undefined.behavior].
     *
     * * This function must only be used on insertion indices found by [RawTableInner.findInsertIndexInGroup]
     *   (after the `findInsertIndexInGroup` function, but before insertion into the table).
     *
     * * The `index` must not be greater than the `self.bucketMask`, i.e. `(index + 1) <= self.numBuckets()`
     *   (this one is provided by the [RawTableInner.findInsertIndexInGroup] function).
     *
     * Calling this function with an index not provided by [RawTableInner.findInsertIndexInGroup]
     * may result in [undefined.behavior] even if the index satisfies the safety rules of the
     * [RawTableInner.ctrl] function (`index<self.bucketMask + 1 + Group.WIDTH`).
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun fixInsertIndex(index: Int): Int {
        var index1 = index
        // SAFETY: The caller of this function ensures that `index` is in the range `0 ..= self.bucketMask`.
        if (unlikely(this.isBucketFull(index1))) {
            check(this.bucketMask < Group.WIDTH)
            // SAFETY:
            //
            // * Since the caller of this function ensures that the control bytes are properly
            // initialized and `ptr = self.ctrl(0)` points to the start of the array of control
            // bytes, therefore: `ctrl` is valid for reads, properly aligned to `Group.WIDTH`
            // and points to the properly initialized control bytes (see also
            // `TableLayout.calculateLayoutFor` and `ptr.read`);
            //
            // * Because the caller of this function ensures that the index was provided by the
            // `self.findInsertIndexInGroup()` function, so for for tables larger than the
            // group width (self.numBuckets() >= Group.Width), we will never end up in the given
            // branch, since `(probeSeq.pos + bit) & self.bucketMask` in `findInsertIndexInGroup`
            // cannot return a full bucket index. For tables smaller than the group width, calling
            // the `unwrapUnchecked` function is also safe, as the trailing control bytes outside
            // the range of the table are filled with EMPTY bytes (and we know for sure that there
            // is at least one FULL bucket), so this second scan either finds an empty slot (due to
            // the load factor) or hits the trailing control bytes (containing EMPTY).
            index1 = Group.loadAligned(this.ctrl(0)).matchEmptyOrDeleted().lowestSetBit().unwrapUnchecked()
        }
        return index1
    }

    /**
     * Finds the position to insert something in a group.
     *
     * **This may have false positives and must be fixed up with `fixInsertIndex`
     * before it's used.**
     *
     * The function is guaranteed to return the index of an empty or deleted [Bucket]
     * in the range `0 .. self.numBuckets()` (`0 ..= self.bucketMask`).
     */
    public open fun findInsertIndexInGroup(group: Group, probeSeq: ProbeSeq): Int? {
        val bit = group.matchEmptyOrDeleted().lowestSetBit()
        return if (likely(bit != null)) {
            // This is the same as `(probeSeq.pos + bit) % self.numBuckets()` because the number
            // of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
            (probeSeq.pos + bit.unwrap()) and this.bucketMask
        } else {
            null
        }
    }

    /**
     * Searches for an element in the table, or a potential slot where that element could
     * be inserted (an empty or deleted [Bucket] index).
     *
     * This uses dynamic dispatch to reduce the amount of code generated, but that is
     * eliminated by LLVM optimizations.
     *
     * This function does not make any changes to the `data` part of the table, or any
     * changes to the `items` or `growthLeft` field of the table.
     *
     * The table must have at least 1 empty or deleted `bucket`, otherwise, if the
     * `eq: dynamic FnMut(Int) -> Boolean` function does not return `true`, this function
     * will never return (will go into an infinite loop) for tables larger than the group
     * width, or return an index outside of the table indices range if the table is less
     * than the group width.
     *
     * This function is guaranteed to provide the `eq: dynamic FnMut(Int) -> Boolean`
     * function with only `FULL` buckets' indices and return the `index` of the found
     * element (as `Ok(index)`). If the element is not found and there is at least 1
     * empty or deleted [Bucket] in the table, the function is guaranteed to return
     * an index in the range `0 .. self.numBuckets()`, but in any case, if this function
     * returns `Err`, it will contain an index in the range `0 ..= self.numBuckets()`.
     *
     * # Safety
     *
     * The [RawTableInner] must have properly initialized control bytes otherwise calling
     * this function results in [undefined.behavior].
     *
     * Attempt to write data at the index returned by this function when the table is less than
     * the group width and if there was not at least one empty or deleted bucket in the table
     * will cause immediate [undefined.behavior]. This is because in this case the function
     * will return `self.bucketMask + 1` as an index due to the trailing [Tag.Empty] control
     * bytes outside the table range.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun findOrFindInsertIndexInner(hash: ULong, eq: (Int) -> Boolean): Result<Int, Int> {
        var insertIndex = null
        val tagHash = Tag.full(hash)
        val probeSeq = this.probeSeq(hash)
        while (true) {
            // SAFETY:
            // * Caller of this function ensures that the control bytes are properly initialized.
            //
            // * `ProbeSeq.pos` cannot be greater than `self.bucketMask = self.numBuckets() - 1`
            // of the table due to masking with `self.bucketMask` and also because the number
            // of buckets is a power of two (see `self.probeSeq` function).
            //
            // * Even if `ProbeSeq.pos` returns `position == self.bucketMask`, it is safe to
            // call `Group.load` due to the extended control bytes range, which is
            // `self.bucketMask + 1 + Group.WIDTH` (in fact, this means that the last control
            // byte will never be read for the allocated table);
            //
            // * Also, even if `RawTableInner` is not already allocated, `ProbeSeq.pos` will
            // always return "0" (zero), so Group.load will read unaligned `Group.staticEmpty()`
            // bytes, which is safe (see RawTableInner.new).
            val group = Group.load(this.ctrl(probeSeq.pos))
            for (bit in group.matchTag(tagHash)) {
                val index = (probeSeq.pos + bit) and this.bucketMask
                if (likely(eq(index))) {
                    return Result.Ok<Int, Int>(index)
                }
            }
            // We didn't find the element we were looking for in the group, try to get an
            // insertion slot from the group if we don't have one yet.
            if (likely(insertIndex == null)) {
                insertIndex = this.findInsertIndexInGroup(group, probeSeq)
            }
            if (insertIndex != null) {
                val insertIndex1 = insertIndex
                // Only stop the search if the group contains at least one empty element.
                // Otherwise, the element that we are looking for might be in a following group.
                if (likely(group.matchEmpty().anyBitSet())) {
                    // We must have found a insert slot by now, since the current group contains at
                    // least one. For tables smaller than the group width, there will still be an
                    // empty element in the current (and only) group due to the load factor.
                    run {
                        // SAFETY:
                        // * Caller of this function ensures that the control bytes are properly initialized.
                        //
                        // * We use this function with the index found by `self.findInsertIndexInGroup`
                        return Result.Err<Int, Int>(this.fixInsertIndex(insertIndex1))
                    }
                }
            }
            probeSeq.moveNext(this.bucketMask)
        }
    }

    /**
     * Searches for an empty or deleted bucket which is suitable for inserting a new
     * element and sets the hash for that slot. Returns an index of that slot and the
     * old control byte stored in the found index.
     *
     * This function does not check if the given element exists in the table. Also,
     * this function does not check if there is enough space in the table to insert
     * a new element. The caller of the function must make sure that the table has at
     * least 1 empty or deleted `bucket`, otherwise this function will never return
     * (will go into an infinite loop) for tables larger than the group width, or
     * return an index outside of the table indices range if the table is less than
     * the group width.
     *
     * If there is at least 1 empty or deleted `bucket` in the table, the function is
     * guaranteed to return an `index` in the range `0 .. self.numBuckets()`, but in any case,
     * if this function returns an `index` it will be in the range `0 ..= self.numBuckets()`.
     *
     * This function does not make any changes to the `data` parts of the table,
     * or any changes to the `items` or `growthLeft` field of the table.
     *
     * # Safety
     *
     * The safety rules are directly derived from the safety rules for the
     * [RawTableInner.setCtrlHash] and [RawTableInner.findInsertIndex] methods.
     * Thus, in order to uphold the safety contracts for that methods, as well as for
     * the correct logic of the work of this crate, you must observe the following rules
     * when calling this function:
     *
     * * The [RawTableInner] has already been allocated and has properly initialized
     *   control bytes otherwise calling this function results in [undefined.behavior].
     *
     * * The caller of this function must ensure that the "data" parts of the table
     *   will have an entry in the returned index (matching the given hash) right
     *   after calling this function.
     *
     * Attempt to write data at the `index` returned by this function when the table is
     * less than the group width and if there was not at least one empty or deleted bucket in
     * the table will cause immediate [undefined.behavior]. This is because in this case the
     * function will return `self.bucketMask + 1` as an index due to the trailing [Tag.Empty]
     * control bytes outside the table range.
     *
     * The caller must independently increase the `items` field of the table, and also,
     * if the old control byte was [Tag.Empty], then decrease the table's `growthLeft`
     * field, and do not change it if the old control byte was [Tag.Deleted].
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun prepareInsertIndex(hash: ULong): Pair<Int, Tag> =
        run {
            // SAFETY: Caller of this function ensures that the control bytes are properly initialized.
            val index: Int = this.findInsertIndex(hash)
            // SAFETY:
            // 1. The `findInsertIndex` function either returns an `index` less than or
            // equal to `self.numBuckets() = self.bucketMask + 1` of the table, or never
            // returns if it cannot find an empty or deleted slot.
            // 2. The caller of this function guarantees that the table has already been
            // allocated
            val oldCtrl = (UnresolvedTypeLayout<Tag>()).readNative((this.ctrl(index)).rawAddress)
            this.setCtrlHash(index, hash)
            Pair(index, oldCtrl)
        }

    /**
     * Searches for an empty or deleted bucket which is suitable for inserting
     * a new element, returning the `index` for the new [Bucket].
     *
     * This function does not make any changes to the `data` part of the table, or any
     * changes to the `items` or `growthLeft` field of the table.
     *
     * The table must have at least 1 empty or deleted `bucket`, otherwise this function
     * will never return (will go into an infinite loop) for tables larger than the group
     * width, or return an index outside of the table indices range if the table is less
     * than the group width.
     *
     * If there is at least 1 empty or deleted `bucket` in the table, the function is
     * guaranteed to return an index in the range `0 .. self.numBuckets()`, but in any case,
     * it will contain an index in the range `0 ..= self.numBuckets()`.
     *
     * # Safety
     *
     * The [RawTableInner] must have properly initialized control bytes otherwise calling
     * this function results in [undefined.behavior].
     *
     * Attempt to write data at the index returned by this function when the table is
     * less than the group width and if there was not at least one empty or deleted bucket in
     * the table will cause immediate [undefined.behavior]. This is because in this case the
     * function will return `self.bucketMask + 1` as an index due to the trailing [Tag.Empty]
     * control bytes outside the table range.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun findInsertIndex(hash: ULong): Int {
        val probeSeq = this.probeSeq(hash)
        while (true) {
            // SAFETY:
            // * Caller of this function ensures that the control bytes are properly initialized.
            //
            // * `ProbeSeq.pos` cannot be greater than `self.bucketMask = self.numBuckets() - 1`
            // of the table due to masking with `self.bucketMask` and also because the number
            // of buckets is a power of two (see `self.probeSeq` function).
            //
            // * Even if `ProbeSeq.pos` returns `position == self.bucketMask`, it is safe to
            // call `Group.load` due to the extended control bytes range, which is
            // `self.bucketMask + 1 + Group.WIDTH` (in fact, this means that the last control
            // byte will never be read for the allocated table);
            //
            // * Also, even if `RawTableInner` is not already allocated, `ProbeSeq.pos` will
            // always return "0" (zero), so Group.load will read unaligned `Group.staticEmpty()`
            // bytes, which is safe (see RawTableInner.new).
            val group = Group.load(this.ctrl(probeSeq.pos))
            val index = this.findInsertIndexInGroup(group, probeSeq)
            if (likely(index != null)) {
                // SAFETY:
                // * Caller of this function ensures that the control bytes are properly initialized.
                //
                // * We use this function with the slot / index found by `self.findInsertIndexInGroup`
                run {
                    return this.fixInsertIndex(index.unwrapUnchecked())
                }
            }
            probeSeq.moveNext(this.bucketMask)
        }
    }

    /**
     * Searches for an element in a table, returning the `index` of the found element.
     * This uses dynamic dispatch to reduce the amount of code generated, but it is
     * eliminated by LLVM optimizations.
     *
     * This function does not make any changes to the `data` part of the table, or any
     * changes to the `items` or `growthLeft` field of the table.
     *
     * The table must have at least 1 empty `bucket`, otherwise, if the
     * `eq: dynamic FnMut(Int) -> Boolean` function does not return `true`,
     * this function will also never return (will go into an infinite loop).
     *
     * This function is guaranteed to provide the `eq: dynamic FnMut(Int) -> Boolean`
     * function with only `FULL` buckets' indices and return the `index` of the found
     * element as `Some(index)`, so the index will always be in the range
     * `0 .. self.numBuckets()`.
     *
     * # Safety
     *
     * The [RawTableInner] must have properly initialized control bytes otherwise calling
     * this function results in [undefined.behavior].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun findInner(hash: ULong, eq: (Int) -> Boolean): Int? {
        val tagHash = Tag.full(hash)
        val probeSeq = this.probeSeq(hash)
        while (true) {
            // SAFETY:
            // * Caller of this function ensures that the control bytes are properly initialized.
            //
            // * `ProbeSeq.pos` cannot be greater than `self.bucketMask = self.numBuckets() - 1`
            // of the table due to masking with `self.bucketMask`.
            //
            // * Even if `ProbeSeq.pos` returns `position == self.bucketMask`, it is safe to
            // call `Group.load` due to the extended control bytes range, which is
            // `self.bucketMask + 1 + Group.WIDTH` (in fact, this means that the last control
            // byte will never be read for the allocated table);
            //
            // * Also, even if `RawTableInner` is not already allocated, `ProbeSeq.pos` will
            // always return "0" (zero), so Group.load will read unaligned `Group.staticEmpty()`
            // bytes, which is safe (see RawTableInner.newIn).
            val group = Group.load(this.ctrl(probeSeq.pos))
            for (bit in group.matchTag(tagHash)) {
                // This is the same as `(probeSeq.pos + bit) % self.numBuckets()` because the number
                // of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
                val index = (probeSeq.pos + bit) and this.bucketMask
                if (likely(eq(index))) {
                    return index
                }
            }
            if (likely(group.matchEmpty().anyBitSet())) {
                return null
            }
            probeSeq.moveNext(this.bucketMask)
        }
    }

    /**
     * Prepares for rehashing data in place (that is, without allocating new memory).
     * Converts all full index `control bytes` to `Tag.DELETED` and all `Tag.DELETED` control
     * bytes to `Tag.EMPTY`, i.e. performs the following conversion:
     *
     * - `Tag.EMPTY` control bytes   -> `Tag.EMPTY`;
     * - `Tag.DELETED` control bytes -> `Tag.EMPTY`;
     * - `FULL` control bytes    -> `Tag.DELETED`.
     *
     * This function does not make any changes to the `data` parts of the table,
     * or any changes to the `items` or `growthLeft` field of the table.
     *
     * # Safety
     *
     * You must observe the following safety rules when calling this function:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The caller of this function must convert the `Tag.DELETED` bytes back to `FULL`
     *   bytes when re-inserting them into their ideal position (which was impossible
     *   to do during the first insert due to tombstones). If the caller does not do
     *   this, then calling this function may result in a memory leak.
     *
     * * The [RawTableInner] must have properly initialized control bytes otherwise
     *   calling this function results in [undefined.behavior].
     *
     * Calling this function on a table that has not been allocated results in
     * [undefined.behavior].
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun prepareRehashInPlace() {
        // Bulk convert all full control bytes to DELETED, and all DELETED control bytes to EMPTY.
        // This effectively frees up all buckets containing a DELETED entry.
        //
        // SAFETY:
        // 1. `i` is guaranteed to be within bounds since we are iterating from zero to `buckets - 1`;
        // 2. Even if `i` will be `i == self.bucketMask`, it is safe to call `Group.loadAligned`
        // due to the extended control bytes range, which is `self.bucketMask + 1 + Group.WIDTH`;
        // 3. The caller of this function guarantees that [RawTableInner] has already been allocated;
        // 4. We can use `Group.loadAligned` and `Group.storeAligned` here since we start from 0
        // and go to the end with a step equal to `Group.WIDTH` (see TableLayout.calculateLayoutFor).
        for (i in (0 until this.numBuckets()).stepBy(Group.WIDTH)) {
            val group = Group.loadAligned(this.ctrl(i))
            val group1 = group.convertSpecialToEmptyAndFullToDeleted()
            group1.storeAligned(this.ctrl(i))
        }
        // Fix up the trailing control bytes. See the comments in setCtrl
        // for the handling of tables smaller than the group width.
        if (unlikely(this.numBuckets() < Group.WIDTH)) {
            // SAFETY: We have `self.bucketMask + 1 + Group.WIDTH` number of control bytes,
            // so copying `self.numBuckets() == self.bucketMask + 1` bytes with offset equal to
            // `Group.WIDTH` is safe
            run {
                kmCopy((this.ctrl(0)).rawAddress, (this.ctrl(Group.WIDTH)).rawAddress, (this.numBuckets()).toLong() * (UnresolvedTypeLayout<Tag>()).sizeBytes)
            }
        } else {
            // SAFETY: We have `self.bucketMask + 1 + Group.WIDTH` number of
            // control bytes,so copying `Group.WIDTH` bytes with offset equal
            // to `self.numBuckets() == self.bucketMask + 1` is safe
            run {
                kmCopy((this.ctrl(0)).rawAddress, (this.ctrl(this.numBuckets())).rawAddress, (Group.WIDTH).toLong() * (UnresolvedTypeLayout<Tag>()).sizeBytes)
            }
        }
    }

    /**
     * Returns an iterator over every element in the table.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result
     * is [undefined.behavior]:
     *
     * * The caller has to ensure that the `RawTableInner` outlives the
     *   `RawIter`. Because we cannot make the `next` method unchecked on
     *   the `RawIter` struct, we have to make the `iter` method unchecked.
     *
     * * The [RawTableInner] must have properly initialized control bytes.
     *
     * The type `T` must be the actual type of the elements stored in the table,
     * otherwise using the returned [RawIter] results in [undefined.behavior].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <T> iter(): RawIter<T> {
        // SAFETY:
        // 1. Since the caller of this function ensures that the control bytes
        // are properly initialized and `self.dataEnd()` points to the start
        // of the array of control bytes, therefore: `ctrl` is valid for reads,
        // properly aligned to `Group.WIDTH` and points to the properly initialized
        // control bytes.
        // 2. `data` bucket index in the table is equal to the `ctrl` index (i.e.
        // equal to zero).
        // 3. We pass the exact value of buckets of the table to the function.
        //
        // `ctrl` points here (to the start
        // of the first control byte `CT0`)
        // ∨
        // [Pad], TN, ..., T1, T0, |CT0, CT1, ..., CtN|, Cta0, Cta1, ..., CtaM
        // \________  ________/
        // \/
        // `n = buckets - 1`, i.e. `RawTableInner.numBuckets() - 1`
        //
        // where: T0...TN  - our stored data;
        // CT0...CtN - control bytes or metadata for `data`.
        // Cta0...CtaM - additional control bytes, where `m = Group.WIDTH - 1` (so that the search
        // with loading `Group` bytes from the heap works properly, even if the result
        // of `h1(hash) & self.bucketMask` is equal to `self.bucketMask`). See also
        // `RawTableInner.setCtrl` function.
        //
        // P.S. `h1(hash) & self.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
        // of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
        return run {
            val data = Bucket.fromBaseIndex(this.dataEnd(), 0)
            RawIter(iter = RawIterRange.new((this.ctrl.asPtr()).asConstPtr(), data, this.numBuckets()), items = this.items)
        }
    }

    /**
     * Executes the destructors (if any) of the values stored in the table.
     *
     * # Note
     *
     * This function does not erase the control bytes of the table and does
     * not make any changes to the `items` or `growthLeft` fields of the
     * table. If necessary, the caller of this function must manually set
     * up these table fields, for example using the [clearNoDrop] function.
     *
     * Be careful during calling this function, because drop function of
     * the elements can panic, and this can leave table in an inconsistent
     * state.
     *
     * # Safety
     *
     * The type `T` must be the actual type of the elements stored in the table,
     * otherwise calling this function may result in [undefined.behavior].
     *
     * If `T` is a type that should be dropped and **the table is not empty**,
     * calling this function more than once results in [undefined.behavior].
     *
     * If `T` is not [Copy], attempting to use values stored in the table after
     * calling this function may result in [undefined.behavior].
     *
     * It is safe to call this function on a table that has not been allocated,
     * on a table with uninitialized control bytes, and on a table with no actual
     * data but with `Full` control bytes if `self.items == 0`.
     *
     * See also [Bucket.drop] / [Bucket.asPtr] methods, for more information
     * about of properly removing or saving `element` from / into the [RawTable] /
     * [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <T> dropElements() {
        // Check that `self.items != 0`. Protects against the possibility
        // of creating an iterator on an table with uninitialized control bytes.
        if (T.NEEDS_DROP && this.items != 0) {
            // SAFETY: We know for sure that RawTableInner will outlive the
            // returned `RawIter` iterator, and the caller of this function
            // must uphold the safety contract for `dropElements` method.
            for (item in this.iter<T>()) {
                // SAFETY: The caller must uphold the safety contract for
                // `dropElements` method.
                item.drop()
            }
        }
    }

    /**
     * Executes the destructors (if any) of the values stored in the table and than
     * deallocates the table.
     *
     * # Note
     *
     * Calling this function automatically makes invalid (dangling) all instances of
     * buckets ([Bucket]) and makes invalid (dangling) the `ctrl` field of the table.
     *
     * This function does not make any changes to the `bucketMask`, `items` or `growthLeft`
     * fields of the table. If necessary, the caller of this function must manually set
     * up these table fields.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is [undefined.behavior]:
     *
     * * Calling this function more than once;
     *
     * * The type `T` must be the actual type of the elements stored in the table.
     *
     * * The `alloc` must be the same [Allocator] as the `Allocator` that was used
     *   to allocate this table.
     *
     * * The `tableLayout` must be the same [TableLayout] as the `TableLayout` that
     *   was used to allocate this table.
     *
     * The caller of this function should pay attention to the possibility of the
     * elements' drop function panicking, because this:
     *
     *    * May leave the table in an inconsistent state;
     *
     *    * Memory is never deallocated, so a memory leak may occur.
     *
     * Attempt to use the `ctrl` field of the table (dereference) after calling this
     * function results in [undefined.behavior].
     *
     * It is safe to call this function on a table that has not been allocated,
     * on a table with uninitialized control bytes, and on a table with no actual
     * data but with `Full` control bytes if `self.items == 0`.
     *
     * See also [RawTableInner.dropElements] or [RawTableInner.freeBuckets]
     * for more  information.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <T, A : Allocator> dropInnerTable(alloc: A, tableLayout: TableLayout) {
        if (!this.isEmptySingleton()) {
            // SAFETY: The caller must uphold the safety contract for `dropInnerTable` method.
            run {
                this.dropElements<T>()
            }
            // SAFETY:
            // 1. We have checked that our table is allocated.
            // 2. The caller must uphold the safety contract for `dropInnerTable` method.
            run {
                this.freeBuckets(alloc, tableLayout)
            }
        }
    }

    /**
     * Returns a pointer to an element in the table (convenience for
     * `Bucket.fromBaseIndex(self.dataEnd<T>(), index)`).
     *
     * The caller must ensure that the `RawTableInner` outlives the returned [Bucket.T],
     * otherwise using it may result in [undefined.behavior].
     *
     * # Safety
     *
     * If `mem.sizeOf<T>() != 0`, then the safety rules are directly derived from the
     * safety rules of the [Bucket.fromBaseIndex] function. Therefore, when calling
     * this function, the following safety rules must be observed:
     *
     * * The table must already be allocated;
     *
     * * The `index` must not be greater than the number returned by the [RawTableInner.numBuckets]
     *   function, i.e. `(index + 1) <= self.numBuckets()`.
     *
     * * The type `T` must be the actual type of the elements stored in the table, otherwise
     *   using the returned [Bucket] may result in [undefined.behavior].
     *
     * It is safe to call this function with index of zero (`index == 0`) on a table that has
     * not been allocated, but using the returned [Bucket] results in [undefined.behavior].
     *
     * If `mem.sizeOf<T>() == 0`, then the only requirement is that the `index` must
     * not be greater than the number returned by the [RawTable.numBuckets] function, i.e.
     * `(index + 1) <= self.numBuckets()`.
     *
     * ```kotlin
     * If mem.sizeOf::<T>() != 0 then return a pointer to the `element` in the `data part` of the table
     * (we start counting from "0", so that in the expression T[n], the "n" index actually one less than
     * the "buckets" number of our `RawTableInner`, i.e. "n = RawTableInner.numBuckets() - 1"):
     *
     *           `table.bucket(3).asPtr()` returns a pointer that points here in the `data`
     *           part of the `RawTableInner`, i.e. to the start of T3 (see [Bucket.asPtr])
     * |
     *                  |               `base = table.dataEnd<T>()` points here
     * |(to the start of CT0 or to the end of T0)
     * v v
     * [Pad], TN, ..., | T3 |, T2, T1, T0, | CT0, CT1, CT2, CT3, ..., CtN, Cta0, Cta1, ..., CtaM
     *                     ^                                              \__________  __________/
     *        `table.bucket(3)` returns a pointer that points                        \/
     *         here in the `data` part of the `RawTableInner`             additional control bytes
     *         (to the end of T3)                                          `m = Group.WIDTH - 1`
     *
     * where: T0 ... TN - our stored data;
     *        CT0...CtN - control bytes or metadata for `data`;
     *        Cta0...CtaM - additional control bytes (so that the search with loading `Group` bytes from
     *                        the heap works properly, even if the result of `h1(hash) & self.bucketMask`
     *                        is equal to `self.bucketMask`). See also `RawTableInner.setCtrl` function.
     *
     * P.S. `h1(hash) & self.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
     * of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
     * ```
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <T> bucket(index: Int): Bucket<T> {
        check(this.bucketMask != 0)
        check(index < this.numBuckets())
        return Bucket.fromBaseIndex(this.dataEnd(), index)
    }

    /**
     * Returns a raw `* UByte` pointer to the start of the `data` element in the table
     * (convenience for `self.dataEnd<UByte>().asPtr().sub((index + 1) * sizeOf)`).
     *
     * The caller must ensure that the `RawTableInner` outlives the returned `* UByte`,
     * otherwise using it may result in [undefined.behavior].
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is [undefined.behavior]:
     *
     * * The table must already be allocated;
     *
     * * The `index` must not be greater than the number returned by the [RawTableInner.numBuckets]
     *   function, i.e. `(index + 1) <= self.numBuckets()`;
     *
     * * The `sizeOf` must be equal to the size of the elements stored in the table;
     *
     * ```kotlin
     * If mem.sizeOf::<T>() != 0 then return a pointer to the `element` in the `data part` of the table
     * (we start counting from "0", so that in the expression T[n], the "n" index actually one less than
     * the "buckets" number of our `RawTableInner`, i.e. "n = RawTableInner.numBuckets() - 1"):
     *
     *           `table.bucketPtr(3, mem.sizeOf<T>())` returns a pointer that points here in the
     *           `data` part of the `RawTableInner`, i.e. to the start of T3
     * |
     *                  |               `base = table.dataEnd<UByte>()` points here
     * |(to the start of CT0 or to the end of T0)
     * v v
     * [Pad], TN, ..., | T3 |, T2, T1, T0, | CT0, CT1, CT2, CT3, ..., CtN, Cta0, Cta1, ..., CtaM
     *                                                                    \__________  __________/
     *                                                                               \/
     * additional control bytes
     *                                                                     `m = Group.WIDTH - 1`
     *
     * where: T0 ... TN - our stored data;
     *        CT0...CtN - control bytes or metadata for `data`;
     *        Cta0...CtaM - additional control bytes (so that the search with loading `Group` bytes from
     *                        the heap works properly, even if the result of `h1(hash) & self.bucketMask`
     *                        is equal to `self.bucketMask`). See also `RawTableInner.setCtrl` function.
     *
     * P.S. `h1(hash) & self.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
     * of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
     * ```
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun bucketPtr(index: Int, sizeOf: Int): MutPtr {
        check(this.bucketMask != 0)
        check(index < this.numBuckets())
        return run {
            val base: MutPtr = this.dataEnd().asPtr()
            MutPtr((base).rawAddress - (((index + 1) * sizeOf).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    /**
     * Returns pointer to one past last `data` element in the table as viewed from
     * the start point of the allocation (convenience for `self.ctrl.cast()`).
     *
     * This function actually returns a pointer to the end of the `data element` at
     * index "0" (zero).
     *
     * The caller must ensure that the `RawTableInner` outlives the returned [NonNull.T],
     * otherwise using it may result in [undefined.behavior].
     *
     * # Note
     *
     * The type `T` must be the actual type of the elements stored in the table, otherwise
     * using the returned [NonNull.T] may result in [undefined.behavior].
     *
     * ```kotlin
     *                        `table.dataEnd<T>()` returns pointer that points here
     *                        (to the end of `T0`)
     *                          ∨
     * [Pad], TN, ..., T1, T0, | CT0, CT1, ..., CtN |, Cta0, Cta1, ..., CtaM
     *                           \________  ________/
     *                                    \/
     *       `n = buckets - 1`, i.e. `RawTableInner.numBuckets() - 1`
     *
     * where: T0 ... TN - our stored data;
     *        CT0...CtN - control bytes or metadata for `data`.
     *        Cta0...CtaM - additional control bytes, where `m = Group.WIDTH - 1` (so that the search
     *                        with loading `Group` bytes from the heap works properly, even if the result
     *                        of `h1(hash) & self.bucketMask` is equal to `self.bucketMask`). See also
     *                        `RawTableInner.setCtrl` function.
     *
     * P.S. `h1(hash) & self.bucketMask` is the same as `hash as Int % self.numBuckets()` because the number
     * of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
     * ```
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun dataEnd(): NonNull = this.ctrl.cast()

    /**
     * Returns an iterator-like object for a probe sequence on the table.
     *
     * This iterator never terminates, but is guaranteed to visit each bucket
     * group exactly once. The loop using `probeSeq` must terminate upon
     * reaching a group containing an empty bucket.
     */
    public open fun probeSeq(hash: ULong): ProbeSeq = ProbeSeq(pos = h1(hash) and this.bucketMask, stride = 0)

    public open fun recordItemInsertAt(index: Int, oldCtrl: Tag, newCtrl: Tag) {
        this.growthLeft -= Int.from(oldCtrl.specialIsEmpty())
        run {
            this.setCtrl(index, newCtrl)
        }
        this.items += 1
    }

    public open fun isInSameGroup(i: Int, newI: Int, hash: ULong): Boolean {
        val probeSeqPos = this.probeSeq(hash).pos
        val probeIndex = { pos: Int -> (pos.wrappingSub(probeSeqPos) and this.bucketMask) / Group.WIDTH }
        return probeIndex(i) == probeIndex(newI)
    }

    /**
     * Sets a control byte to the hash, and possibly also the replicated control byte at
     * the end of the array.
     *
     * This function does not make any changes to the `data` parts of the table,
     * or any changes to the `items` or `growthLeft` field of the table.
     *
     * # Safety
     *
     * The safety rules are directly derived from the safety rules for [RawTableInner.setCtrl]
     * method. Thus, in order to uphold the safety contracts for the method, you must observe the
     * following rules when calling this function:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The `index` must not be greater than the `RawTableInner.bucketMask`, i.e.
     *   `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)` must
     *   be no greater than the number returned by the function [RawTableInner.numBuckets].
     *
     * Calling this function on a table that has not been allocated results in [undefined.behavior].
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun setCtrlHash(index: Int, hash: ULong) {
        run {
            // SAFETY: The caller must uphold the safety rules for the [RawTableInner.setCtrlHash]
            this.setCtrl(index, Tag.full(hash))
        }
    }

    /**
     * Replaces the hash in the control byte at the given index with the provided one,
     * and possibly also replicates the new control byte at the end of the array of control
     * bytes, returning the old control byte.
     *
     * This function does not make any changes to the `data` parts of the table,
     * or any changes to the `items` or `growthLeft` field of the table.
     *
     * # Safety
     *
     * The safety rules are directly derived from the safety rules for [RawTableInner.setCtrlHash]
     * and [RawTableInner.ctrl] methods. Thus, in order to uphold the safety contracts for both
     * methods, you must observe the following rules when calling this function:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The `index` must not be greater than the `RawTableInner.bucketMask`, i.e.
     *   `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)` must
     *   be no greater than the number returned by the function [RawTableInner.numBuckets].
     *
     * Calling this function on a table that has not been allocated results in [undefined.behavior].
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun replaceCtrlHash(index: Int, hash: ULong): Tag =
        run {
            // SAFETY: The caller must uphold the safety rules for the [RawTableInner.replaceCtrlHash]
            val prevCtrl = (UnresolvedTypeLayout<Tag>()).readNative((this.ctrl(index)).rawAddress)
            this.setCtrlHash(index, hash)
            prevCtrl
        }

    /**
     * Sets a control byte, and possibly also the replicated control byte at
     * the end of the array.
     *
     * This function does not make any changes to the `data` parts of the table,
     * or any changes to the `items` or `growthLeft` field of the table.
     *
     * # Safety
     *
     * You must observe the following safety rules when calling this function:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The `index` must not be greater than the `RawTableInner.bucketMask`, i.e.
     *   `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)` must
     *   be no greater than the number returned by the function [RawTableInner.numBuckets].
     *
     * Calling this function on a table that has not been allocated results in [undefined.behavior].
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun setCtrl(index: Int, ctrl: Tag) {
        // Replicate the first Group.Width control bytes at the end of
        // the array without using a branch. If the tables smaller than
        // the group width (self.numBuckets() < Group.Width),
        // `index2 = Group.WIDTH + index`, otherwise `index2` is:
        //
        // - If index >= Group.Width then index == index2.
        // - Otherwise index2 == self.bucketMask + 1 + index.
        //
        // The very last replicated control byte is never actually read because
        // we mask the initial index for unaligned loads, but we write it
        // anyways because it makes the setCtrl implementation simpler.
        //
        // If there are fewer buckets than Group.Width then this code will
        // replicate the buckets at the end of the trailing group. For example
        // with 2 buckets and a group size of 4, the control bytes will look
        // like this:
        //
        // Real    |             Replicated
        // ---------------------------------------------
        // | [A] | [B] | [Tag.Empty] | [EMPTY] | [A] | [B] |
        // ---------------------------------------------
        // This is the same as `(index.wrappingSub(Group.WIDTH)) % self.numBuckets() + Group.WIDTH`
        // because the number of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
        val index2 = (((index - (Group.WIDTH.toInt()))) and this.bucketMask) + Group.WIDTH
        // SAFETY: The caller must uphold the safety rules for the [RawTableInner.setCtrl]
        run {
            (UnresolvedTypeLayout<Tag>()).writeNative((this.ctrl(index)).rawAddress, ctrl)
            (UnresolvedTypeLayout<Tag>()).writeNative((this.ctrl(index2)).rawAddress, ctrl)
        }
    }

    /**
     * Returns a pointer to a control byte.
     *
     * # Safety
     *
     * For the allocated [RawTableInner], the result is [Undefined.Behavior],
     * if the `index` is greater than the `self.bucketMask + 1 + Group.WIDTH`.
     * In that case, calling this function with `index == self.bucketMask + 1 + Group.WIDTH`
     * will return a pointer to the end of the allocated table and it is useless on its own.
     *
     * Calling this function with `index >= self.bucketMask + 1 + Group.WIDTH` on a
     * table that has not been allocated results in [Undefined.Behavior].
     *
     * So to satisfy both requirements you should always follow the rule that
     * `index<self.bucketMask + 1 + Group.WIDTH`
     *
     * Calling this function on [RawTableInner] that are not already allocated is safe
     * for read-only purpose.
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [Undefined.Behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun ctrl(index: Int): MutPtr {
        check(index < this.numCtrlBytes())
        // SAFETY: The caller must uphold the safety rules for the [RawTableInner.ctrl]
        return this.ctrl.asPtr().add(index).cast()
    }

    /**
     * Gets the slice of all control bytes, as possibily uninitialized tags.
     */
    public open fun ctrlSlice(): MutableList<MaybeUninit<Tag>> {
        // SAFETY: We have the correct number of control bytes.
        return Slice.fromRawPartsMut(this.ctrl.asPtr().cast(), this.numCtrlBytes())
    }

    public open fun numBuckets(): Int = this.bucketMask + 1

    /**
     * Checks whether the bucket at `index` is full.
     *
     * # Safety
     *
     * The caller must ensure `index` is less than the number of buckets.
     */
    public open fun isBucketFull(index: Int): Boolean {
        check(index < this.numBuckets())
        return (UnresolvedTypeLayout<Tag>()).readNative((this.ctrl(index)).rawAddress).isFull()
    }

    public open fun numCtrlBytes(): Int = this.bucketMask + 1 + Group.WIDTH

    public open fun isEmptySingleton(): Boolean = this.bucketMask == 0

    /**
     * Attempts to allocate a new hash table with at least enough capacity
     * for inserting the given number of elements without reallocating,
     * and return it inside `ScopeGuard` to protect against panic in the hash
     * function.
     *
     * # Note
     *
     * It is recommended (but not required):
     *
     * * That the new table's `capacity` be greater than or equal to `self.items`.
     *
     * * The `alloc` is the same [Allocator] as the `Allocator` used
     *   to allocate this table.
     *
     * * The `tableLayout` is the same [TableLayout] as the `TableLayout` used
     *   to allocate this table.
     *
     * If `tableLayout` does not match the `TableLayout` that was used to allocate
     * this table, then using `mem.swap` with the `self` and the new table returned
     * by this function results in [undefined.behavior].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <A> prepareResize(alloc: A, tableLayout: TableLayout, capacity: Int, fallibility: Fallibility): Result<ScopeGuard<RawTableInner, (RawTableInner) -> Unit>, TryReserveError> where A : Allocator {
        check(this.items <= capacity)
        // Allocate and initialize the new table.
        val newTable = when (val tryResult = RawTableInner.fallibleWithCapacity(alloc, tableLayout, capacity, fallibility)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // The hash function may panic, in which case we simply free the new
        // table without dropping any elements that may have been copied into
        // it.
        //
        // This guard is also used to free the old table on success, see
        // the comment at the bottom of this function.
        return Result.Ok(Guard(newTable, { selfRef ->
            if (!selfRef.isEmptySingleton()) {
                // SAFETY:
                // 1. We have checked that our table is allocated.
                // 2. We know for sure that the `alloc` and `tableLayout` matches the
                // [Allocator] and [TableLayout] used to allocate this table.
                selfRef.freeBuckets(alloc, tableLayout)
            }
        }))
    }

    /**
     * Reserves or rehashes to make room for `additional` more elements.
     *
     * This uses dynamic dispatch to reduce the amount of
     * code generated, but it is eliminated by LLVM optimizations when inlined.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is
     * [undefined.behavior]:
     *
     * * The `alloc` must be the same [Allocator] as the `Allocator` used
     *   to allocate this table.
     *
     * * The `layout` must be the same [TableLayout] as the `TableLayout`
     *   used to allocate this table.
     *
     * * The `drop` function (`fun(* UByte)`) must be the actual drop function of
     *   the elements stored in the table.
     *
     * * The [RawTableInner] must have properly initialized control bytes.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <A> reserveRehashInner(alloc: A, additional: Int, hasher: (RawTableInner, Int) -> ULong, fallibility: Fallibility, layout: TableLayout, drop: FnCallback?): Result<Unit, TryReserveError> where A : Allocator {
        // Avoid `Option.okOrElse` because it bloats LLVM IR.
        val newItems = this.items.checkedAdd(additional) ?: return Result.Err<Unit, TryReserveError>(fallibility.capacityOverflow())
        val fullCapacity = bucketMaskToCapacity(this.bucketMask)
        return if (newItems <= fullCapacity / 2) {
            // Rehash in-place without re-allocating if we have plenty of spare
            // capacity that is locked up due to DELETED entries.
            // SAFETY:
            // 1. We know for sure that `[`RawTableInner`]` has already been allocated
            // (since newItems <= fullCapacity / 2);
            // 2. The caller ensures that `drop` function is the actual drop function of
            // the elements stored in the table.
            // 3. The caller ensures that `layout` matches the [TableLayout] that was
            // used to allocate this table.
            // 4. The caller ensures that the control bytes of the `RawTableInner`
            // are already initialized.
            run {
                this.rehashInPlace(hasher, layout.size, drop)
            }
            Result.Ok<Unit, TryReserveError>(Unit)
        } else {
            // Otherwise, conservatively resize to at least the next size up
            // to avoid churning deletes into frequent rehashes.
            //
            // SAFETY:
            // 1. We know for sure that `capacity >= self.items`.
            // 2. The caller ensures that `alloc` and `layout` matches the [Allocator] and
            // [TableLayout] that were used to allocate this table.
            // 3. The caller ensures that the control bytes of the `RawTableInner`
            // are already initialized.
            this.resizeInner(alloc, maxOf(newItems, fullCapacity + 1), hasher, fallibility, layout)
        }
    }

    /**
     * Returns an iterator over full buckets indices in the table.
     *
     * # Safety
     *
     * Behavior is undefined if any of the following conditions are violated:
     *
     * * The caller has to ensure that the `RawTableInner` outlives the
     *   `FullBucketsIndices`. Because we cannot make the `next` method
     *   unchecked on the `FullBucketsIndices` struct, we have to make the
     *   `fullBucketsIndices` method unchecked.
     *
     * * The [RawTableInner] must have properly initialized control bytes.
     */
    public open fun fullBucketsIndices(): FullBucketsIndices {
        // SAFETY:
        // 1. Since the caller of this function ensures that the control bytes
        // are properly initialized and `self.ctrl(0)` points to the start
        // of the array of control bytes, therefore: `ctrl` is valid for reads,
        // properly aligned to `Group.WIDTH` and points to the properly initialized
        // control bytes.
        // 2. The value of `items` is equal to the amount of data (values) added
        // to the table.
        //
        // `ctrl` points here (to the start
        // of the first control byte `CT0`)
        // ∨
        // [Pad], TN, ..., T1, T0, |CT0, CT1, ..., CtN|, Group.Width
        // \________  ________/
        // \/
        // `n = buckets - 1`, i.e. `RawTableInner.numBuckets() - 1`
        //
        // where: T0...TN  - our stored data;
        // CT0...CtN - control bytes or metadata for `data`.
        return run {
            val ctrl = NonNull.newUnchecked(MutPtr((this.ctrl(0)).rawAddress))
            FullBucketsIndices(currentGroup = Group.loadAligned(ctrl.asPtr().cast()).matchFull().intoIter(), groupFirstIndex = 0, ctrl = ctrl, items = this.items)
        }
    }

    /**
     * Allocates a new table of a different size and moves the contents of the
     * current table into it.
     *
     * This uses dynamic dispatch to reduce the amount of
     * code generated, but it is eliminated by LLVM optimizations when inlined.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is
     * [undefined.behavior]:
     *
     * * The `alloc` must be the same [Allocator] as the `Allocator` used
     *   to allocate this table;
     *
     * * The `layout` must be the same [TableLayout] as the `TableLayout`
     *   used to allocate this table;
     *
     * * The [RawTableInner] must have properly initialized control bytes.
     *
     * The caller of this function must ensure that `capacity >= self.items`
     * otherwise:
     *
     * * If `self.items != 0`, calling of this function with `capacity == 0`
     *   results in [undefined.behavior].
     *
     * * If `capacityToBuckets(capacity)<Group.WIDTH` and
     *   `self.items> capacityToBuckets(capacity)` calling this function
     *   results in [undefined.behavior].
     *
     * * If `capacityToBuckets(capacity) >= Group.WIDTH` and
     *   `self.items> capacityToBuckets(capacity)` calling this function
     *   are never return (will go into an infinite loop).
     *
     * Note: It is recommended (but not required) that the new table's `capacity`
     * be greater than or equal to `self.items`. In case if `capacity <= self.items`
     * this function can never return. See [RawTableInner.findInsertIndex] for
     * more information.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <A> resizeInner(alloc: A, capacity: Int, hasher: (RawTableInner, Int) -> ULong, fallibility: Fallibility, layout: TableLayout): Result<Unit, TryReserveError> where A : Allocator {
        // SAFETY: We know for sure that `alloc` and `layout` matches the [Allocator] and [TableLayout]
        // that were used to allocate this table.
        val newTable = when (val tryResult = this.prepareResize(alloc, layout, capacity, fallibility)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.error) }
        // SAFETY: We know for sure that RawTableInner will outlive the
        // returned `FullBucketsIndices` iterator, and the caller of this
        // function ensures that the control bytes are properly initialized.
        for (fullByteIndex in this.fullBucketsIndices()) {
            // This may panic.
            val hash = hasher(this, fullByteIndex)
            // SAFETY:
            // We can use a simpler version of insert() here since:
            // 1. There are no DELETED entries.
            // 2. We know there is enough space in the table.
            // 3. All elements are unique.
            // 4. The caller of this function guarantees that `capacity> 0`
            // so `newTable` must already have some allocated memory.
            // 5. We set `growthLeft` and `items` fields of the new table
            // after the loop.
            // 6. We insert into the table, at the returned index, the data
            // matching the given hash immediately after calling this function.
            val (newIndex, _) = newTable.prepareInsertIndex(hash)
            // SAFETY:
            //
            // * `src` is valid for reads of `layout.size` bytes, since the
            // table is alive and the `fullByteIndex` is guaranteed to be
            // within bounds (see `FullBucketsIndices.nextImpl`);
            //
            // * `dst` is valid for writes of `layout.size` bytes, since the
            // caller ensures that `tableLayout` matches the [TableLayout]
            // that was used to allocate old table and we have the `newIndex`
            // returned by `prepareInsertIndex`.
            //
            // * Both `src` and `dst` are properly aligned.
            //
            // * Both `src` and `dst` point to different region of memory.
            copyNonoverlapping((this.bucketPtr(fullByteIndex, layout.size)).asConstPtr(), newTable.bucketPtr(newIndex, layout.size), layout.size)
        }
        // The hash function didn't panic, so we can safely set the
        // `growthLeft` and `items` fields of the new table.
        newTable.growthLeft -= this.items
        newTable.items = this.items
        // We successfully copied all elements without panicking. Now replace
        // self with the new table. The old table will have its memory freed but
        // the items will not be dropped (since they have been moved into the
        // new table).
        // SAFETY: The caller ensures that `tableLayout` matches the [TableLayout]
        // that was used to allocate this table.
        swap(this, newTable)
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * Rehashes the contents of the table in place (i.e. without changing the
     * allocation).
     *
     * If `hasher` panics then some the table's contents may be lost.
     *
     * This uses dynamic dispatch to reduce the amount of
     * code generated, but it is eliminated by LLVM optimizations when inlined.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is [undefined.behavior]:
     *
     * * The `sizeOf` must be equal to the size of the elements stored in the table;
     *
     * * The `drop` function (`fun(* UByte)`) must be the actual drop function of
     *   the elements stored in the table.
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The [RawTableInner] must have properly initialized control bytes.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun rehashInPlace(hasher: (RawTableInner, Int) -> ULong, sizeOf: Int, drop: FnCallback?) {
        // If the hash function panics then properly clean up any elements
        // that we haven't rehashed yet. We unfortunately can't preserve the
        // element since we lost their hash and have no way of recovering it
        // without risking another panic.
        run {
            this.prepareRehashInPlace()
        }
        var guard =
            Guard(this, { selfRef ->
                for (i in 0 until selfRef.numBuckets()) {
                    if (selfRef.ctrl(i) == Tag.DELETED) {
                        selfRef.setCtrl(i, Tag.EMPTY)
                        if (drop != null) {
                            val drop1 = drop
                            drop1(selfRef.bucketPtr(i, sizeOf))
                        }
                        selfRef.items -= 1
                    }
                }
                selfRef.growthLeft = bucketMaskToCapacity(selfRef.bucketMask) - selfRef.items
            })
        // At this point, DELETED elements are elements that we haven't
        // rehashed yet. Find them and re-insert them at their ideal
        // position.
        for (i in 0 until guard.numBuckets()) {
            if (guard.ctrl(i) != Tag.DELETED) {
                continue
            }
            val iP = guard.bucketPtr(i, sizeOf)
            while (true) {
                // Hash the current item
                val hash = hasher(guard, i)
                // Search for a suitable place to put it
                //
                // SAFETY: Caller of this function ensures that the control bytes
                // are properly initialized.
                val newI = guard.findInsertIndex(hash)
                // Probing works by scanning through all of the control
                // bytes in groups, which may not be aligned to the group
                // size. If both the new and old position fall within the
                // same unaligned group, then there is no benefit in moving
                // it and we can just continue to the next item.
                if (likely(guard.isInSameGroup(i, newI, hash))) {
                    guard.setCtrlHash(i, hash)
                    continue
                }
                val newIP = guard.bucketPtr(newI, sizeOf)
                // We are moving the current item to a new position. Write
                // our H2 to the control byte of the new position.
                val prevCtrl = guard.replaceCtrlHash(newI, hash)
                if (prevCtrl == Tag.EMPTY) {
                    guard.setCtrl(i, Tag.EMPTY)
                    // If the target slot is empty, simply move the current
                    // element into the new slot and clear the old control
                    // byte.
                    run {
                        copyNonoverlapping(iP, newIP, sizeOf)
                    }
                    continue
                }
                // If the target slot is occupied, swap the two elements
                // and then continue processing the element that we just
                // swapped into the old slot.
                check(prevCtrl == Tag.DELETED)
                run {
                    swapNonoverlapping(iP, newIP, sizeOf)
                }
            }
        }
        guard.growthLeft = bucketMaskToCapacity(guard.bucketMask) - guard.items
        forget(guard)
    }

    /**
     * Deallocates the table without dropping any entries.
     *
     * # Note
     *
     * This function must be called only after [`dropElements`](RawTableInner.dropElements),
     * else it can lead to leaking of memory. Also calling this function automatically
     * makes invalid (dangling) all instances of buckets ([Bucket]) and makes invalid
     * (dangling) the `ctrl` field of the table.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is [Undefined.Behavior]:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * The `alloc` must be the same [Allocator] as the `Allocator` that was used
     *   to allocate this table.
     *
     * * The `tableLayout` must be the same [TableLayout] as the `TableLayout` that was used
     *   to allocate this table.
     *
     * See also [GlobalAlloc.dealloc] or [Allocator.deallocate] for more  information.
     *
     * [Undefined.Behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     * [GlobalAlloc.dealloc]: stdalloc.alloc.GlobalAlloc.dealloc
     * [Allocator.deallocate]: stdalloc.alloc.Allocator.deallocate
     */
    public open fun <A> freeBuckets(alloc: A, tableLayout: TableLayout) where A : Allocator {
        run {
            // SAFETY: The caller must uphold the safety contract for `freeBuckets`
            // method.
            val (ptr, layout) = this.allocationInfo(tableLayout)
            alloc.deallocate(ptr, layout)
        }
    }

    /**
     * Returns a pointer to the allocated memory and the layout that was used to
     * allocate the table.
     *
     * # Safety
     *
     * Caller of this function must observe the following safety rules:
     *
     * * The [RawTableInner] has already been allocated, otherwise
     *   calling this function results in [undefined.behavior]
     *
     * * The `tableLayout` must be the same [TableLayout] as the `TableLayout`
     *   that was used to allocate this table. Failure to comply with this condition
     *   may result in [undefined.behavior].
     *
     * See also [GlobalAlloc.dealloc] or [Allocator.deallocate] for more  information.
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     * [GlobalAlloc.dealloc]: stdalloc.GlobalAlloc.dealloc
     * [Allocator.deallocate]: stdalloc.Allocator.deallocate
     */
    public open fun allocationInfo(tableLayout: TableLayout): Pair<NonNull, Layout> {
        check(!this.isEmptySingleton()) { "this function can only be called on non-empty tables" }
        val (layout, ctrlOffset) = run {
            val option = tableLayout.calculateLayoutFor(this.numBuckets())
            option.unwrapUnchecked()
        }
        return Pair(NonNull.newUnchecked(this.ctrl.asPtr().sub(ctrlOffset)), layout)
    }

    /**
     * Returns the total amount of memory allocated internally by the hash
     * table, in bytes.
     *
     * The returned number is informational only. It is intended to be
     * primarily used for memory profiling.
     *
     * # Safety
     *
     * The `tableLayout` must be the same [TableLayout] as the `TableLayout`
     * that was used to allocate this table. Failure to comply with this condition
     * may result in [undefined.behavior].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun allocationSizeOrZero(tableLayout: TableLayout): Int =
        if (this.isEmptySingleton()) {
            0
        } else {
            // SAFETY:
            // 1. We have checked that our table is allocated.
            // 2. The caller ensures that `tableLayout` matches the [TableLayout]
            // that was used to allocate this table.
            this.allocationInfo(tableLayout).second.size()
        }

    /**
     * Marks all table buckets as empty without dropping their contents.
     */
    public open fun clearNoDrop() {
        if (!this.isEmptySingleton()) {
            this.ctrlSlice().fillEmpty()
        }
        this.items = 0
        this.growthLeft = bucketMaskToCapacity(this.bucketMask)
    }

    /**
     * Erases the [Bucket]s control byte at the given index so that it does not
     * triggered as full, decreases the `items` of the table and, if it can be done,
     * increases `self.growthLeft`.
     *
     * This function does not actually erase / drop the [Bucket] itself, i.e. it
     * does not make any changes to the `data` parts of the table. The caller of this
     * function must take care to properly drop the `data`, otherwise calling this
     * function may result in a memory leak.
     *
     * # Safety
     *
     * You must observe the following safety rules when calling this function:
     *
     * * The [RawTableInner] has already been allocated;
     *
     * * It must be the full control byte at the given position;
     *
     * * The `index` must not be greater than the `RawTableInner.bucketMask`, i.e.
     *   `index <= RawTableInner.bucketMask` or, in other words, `(index + 1)` must
     *   be no greater than the number returned by the function [RawTableInner.numBuckets].
     *
     * Calling this function on a table that has not been allocated results in [undefined.behavior].
     *
     * Calling this function on a table with no elements is unspecified, but calling subsequent
     * functions is likely to result in [undefined.behavior] due to overflow subtraction
     * (`self.items -= 1 cause overflow when self.items == 0`).
     *
     * See also [Bucket.asPtr] method, for more information about of properly removing
     * or saving `data element` from / into the [RawTable] / [RawTableInner].
     *
     * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun erase(index: Int) {
        run {
            check(this.isBucketFull(index))
        }
        // This is the same as `index.wrappingSub(Group.WIDTH) % self.numBuckets()` because
        // the number of buckets is a power of two, and `self.bucketMask = self.numBuckets() - 1`.
        val indexBefore = (index - (Group.WIDTH.toInt())) and this.bucketMask
        // SAFETY:
        // - The caller must uphold the safety contract for `erase` method;
        // - `indexBefore` is guaranteed to be in range due to masking with `self.bucketMask`
        val (emptyBefore, emptyAfter) = Pair(Group.load(this.ctrl(indexBefore)).matchEmpty(), Group.load(this.ctrl(index)).matchEmpty())
        // Inserting and searching in the map is performed by two key functions:
        //
        // - The `findInsertIndex` function that looks up the index of any `Tag.EMPTY` or `Tag.DELETED`
        // slot in a group to be able to insert. If it doesn't find an `Tag.EMPTY` or `Tag.DELETED`
        // slot immediately in the first group, it jumps to the next `Group` looking for it,
        // and so on until it has gone through all the groups in the control bytes.
        //
        // - The `findInner` function that looks for the index of the desired element by looking
        // at all the `FULL` bytes in the group. If it did not find the element right away, and
        // there is no `Tag.EMPTY` byte in the group, then this means that the `findInsertIndex`
        // function may have found a suitable slot in the next group. Therefore, `findInner`
        // jumps further, and if it does not find the desired element and again there is no `Tag.EMPTY`
        // byte, then it jumps further, and so on. The search stops only if `findInner` function
        // finds the desired element or hits an `Tag.EMPTY` slot/byte.
        //
        // Accordingly, this leads to two consequences:
        //
        // - The map must have `Tag.EMPTY` slots (bytes);
        //
        // - You can't just mark the byte to be erased as `Tag.EMPTY`, because otherwise the `findInner`
        // function may stumble upon an `Tag.EMPTY` byte before finding the desired element and stop
        // searching.
        //
        // Thus it is necessary to check all bytes after and before the erased element. If we are in
        // a contiguous `Group` of `FULL` or `Tag.DELETED` bytes (the number of `FULL` or `Tag.DELETED` bytes
        // before and after is greater than or equal to `Group.WIDTH`), then we must mark our byte as
        // `Tag.DELETED` in order for the `findInner` function to go further. On the other hand, if there
        // is at least one `Tag.EMPTY` slot in the `Group`, then the `findInner` function will still stumble
        // upon an `Tag.EMPTY` byte, so we can safely mark our erased byte as `Tag.EMPTY` as well.
        //
        // Finally, since `indexBefore == (index.wrappingSub(Group.WIDTH) & self.bucketMask) == index`
        // and given all of the above, tables smaller than the group width (self.numBuckets() < Group.Width)
        // cannot have `Tag.DELETED` bytes.
        //
        // Note that in this context `leadingZeros` refers to the bytes at the end of a group, while
        // `trailingZeros` refers to the bytes at the beginning of a group.
        val ctrl =
            if (emptyBefore.leadingZeros() + emptyAfter.trailingZeros() >= Group.WIDTH) {
                Tag.DELETED
            } else {
                this.growthLeft += 1
                Tag.EMPTY
            }
        // SAFETY: the caller must uphold the safety contract for `erase` method.
        run {
            this.setCtrl(index, ctrl)
        }
        this.items -= 1
    }

    public companion object {
        public operator fun invoke(
            bucketMask: Int,
            ctrl: NonNull,
            growthLeft: Int,
            items: Int,
        ): RawTableInner = RawTableInner(
            bucketMask = bucketMask,
            ctrlCtorArg = ctrl,
            growthLeft = growthLeft,
            items = items,
        )

        public val NEW: RawTableInner = RawTableInner.new()

        /**
         * Creates a new empty hash table without allocating any memory.
         *
         * In effect this returns a table with exactly 1 bucket. However we can
         * leave the data pointer dangling since that bucket is never accessed
         * due to our load factor forcing us to always have at least 1 free bucket.
         */
        public fun new(): RawTableInner = RawTableInner(ctrl = NonNull.newUnchecked(Group.staticEmpty().asPtr().castMut().cast()), bucketMask = 0, items = 0, growthLeft = 0)

        /**
         * Allocates a new [RawTableInner] with the given number of buckets.
         * The control bytes and buckets are left uninitialized.
         *
         * # Safety
         *
         * The caller of this function must ensure that the `buckets` is power of two
         * and also initialize all control bytes of the length `self.bucketMask + 1 +
         * Group.Width`with the [`Tag.Empty`] bytes.
         *
         * See also [Allocator] API for other safety concerns.
         *
         * [Allocator]: stdalloc.alloc.Allocator
         */
        public fun <A> newUninitialized(alloc: A, tableLayout: TableLayout, buckets: Int, fallibility: Fallibility): Result<RawTableInner, TryReserveError> where A : Allocator {
            var buckets1 = buckets
            check(buckets1.isPowerOfTwo())
            // Avoid `Option.okOrElse` because it bloats LLVM IR.
            var (layout, ctrlOffset) = tableLayout.calculateLayoutFor(buckets1) ?: return Result.Err<RawTableInner, TryReserveError>(fallibility.capacityOverflow())
            val ptr: NonNull =
                run {
                    val matchVal = doAlloc(alloc, layout)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val block = matchVal.value
                        // The allocator can't return a value smaller than was
                        // requested, so this can be != instead of >=.
                        if (block.len() != layout.size()) {
                            // Utilize over-sized allocations.
                            val x = maximumBucketsIn(block.len(), tableLayout, Group.WIDTH)
                            check(x >= buckets1)
                            // Calculate the new ctrlOffset.
                            val (oversizedLayout, oversizedCtrlOffset) = run {
                                val option = tableLayout.calculateLayoutFor(x)
                                option.unwrapUnchecked()
                            }
                            check(oversizedLayout.size() <= block.len())
                            check(oversizedCtrlOffset >= ctrlOffset)
                            ctrlOffset = oversizedCtrlOffset
                            buckets1 = x
                        }
                        block.cast()
                    }; is Result.Err<*, *> -> {
                        return Result.Err<RawTableInner, TryReserveError>(fallibility.allocErr(layout))
                    } }
                }
            // SAFETY: null pointer will be caught in above check
            val ctrl = NonNull.newUnchecked(ptr.asPtr().add(ctrlOffset))
            return Result.Ok<RawTableInner, TryReserveError>(RawTableInner(ctrl = ctrl, bucketMask = buckets1 - 1, items = 0, growthLeft = bucketMaskToCapacity(buckets1 - 1)))
        }

        /**
         * Attempts to allocate a new [RawTableInner] with at least enough
         * capacity for inserting the given number of elements without reallocating.
         *
         * All the control bytes are initialized with the [Tag.Empty] bytes.
         */
        public fun <A> fallibleWithCapacity(alloc: A, tableLayout: TableLayout, capacity: Int, fallibility: Fallibility): Result<RawTableInner, TryReserveError> where A : Allocator {
            return if (capacity == 0) {
                Result.Ok<RawTableInner, TryReserveError>(RawTableInner.NEW)
            } else {
                // SAFETY: We checked that we could successfully allocate the new table, and then
                // initialized all control bytes with the constant `Tag.EMPTY` byte.
                run {
                    val buckets = when (val tryResult = (capacityToBuckets(capacity, tableLayout)?.let { Result.Ok<Int, TryReserveError>(it) } ?: Result.Err<Int, TryReserveError>(fallibility.capacityOverflow()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<RawTableInner, TryReserveError>(tryResult.error) }
                    val result = when (val tryResult = RawTableInner.newUninitialized(alloc, tableLayout, buckets, fallibility)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<RawTableInner, TryReserveError>(tryResult.error) }
                    // SAFETY: We checked that the table is allocated and therefore the table already has
                    // `self.bucketMask + 1 + Group.WIDTH` number of control bytes (see TableLayout.calculateLayoutFor)
                    // so writing `self.numCtrlBytes() == bucketMask + 1 + Group.WIDTH` bytes is safe.
                    result.ctrlSlice().fillEmpty()
                    Result.Ok<RawTableInner, TryReserveError>(result)
                }
            }
        }

        /**
         * Allocates a new [RawTableInner] with at least enough capacity for inserting
         * the given number of elements without reallocating.
         *
         * Panics if the new capacity exceeds [isize.Max] bytes and [abort] the program
         * in case of allocation error. Use [fallibleWithCapacity] instead if you want to
         * handle memory allocation failure.
         *
         * All the control bytes are initialized with the [Tag.Empty] bytes.
         *
         * [fallibleWithCapacity]: RawTableInner.fallibleWithCapacity
         * [abort]: stdalloc.abort.handleAllocError
         */
        public fun <A> withCapacity(alloc: A, tableLayout: TableLayout, capacity: Int): RawTableInner where A : Allocator {
            val result = RawTableInner.fallibleWithCapacity(alloc, tableLayout, capacity, Fallibility.Nothing)
            // SAFETY: All allocation errors will be caught inside `RawTableInner.newUninitialized`.
            return result.unwrapUnchecked()
        }
    }
}

/**
 * Creates a new empty hash table without allocating any memory.
 *
 * In effect this returns a table with exactly 1 bucket. However we can
 * leave the data pointer dangling since that bucket is never written to
 * due to our load factor forcing us to always have at least 1 free bucket.
 */
public fun <T> RawTable.Companion.new(): RawTable<T, Global> = RawTable(table = RawTableInner.NEW, alloc = io.github.kotlinmania.rustlang.alloc.alloc.Global(), marker = Unit)

/**
 * Allocates a new hash table with at least enough capacity for inserting
 * the given number of elements without reallocating.
 */
public fun <T> RawTable.Companion.withCapacity(capacity: Int): RawTable<T, Global> = RawTable.withCapacityIn(capacity, io.github.kotlinmania.rustlang.alloc.alloc.Global())

/**
 * Find the previous power of 2. If it's already a power of 2, it's unchanged.
 * Passing zero is undefined behavior.
 */
public fun prevPow2(z: Int): Int {
    val shift = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1
    return 1 shl (shift - (z.leadingZeros().toInt()))
}

/**
 * Finds the largest number of buckets that can fit in `allocationSize`
 * provided the given TableLayout.
 *
 * This relies on some invariants of `capacityToBuckets`, so only feed in
 * an `allocationSize` calculated from `capacityToBuckets`.
 */
public fun maximumBucketsIn(allocationSize: Int, tableLayout: TableLayout, groupWidth: Int): Int {
    // Given an equation like:
    // z >= x * y + x + g
    // x can be maximized by doing:
    // x = (z - g) / (y + 1)
    // If you squint:
    // x is the number of buckets
    // y is the tableLayout.size
    // z is the size of the allocation
    // g is the group width
    // But this is ignoring the padding needed for ctrlAlign.
    // If we remember these restrictions:
    // x is always a power of 2
    // Layout size for T must always be a multiple of T
    // Then the alignment can be ignored if we add the constraint:
    // x * y >= tableLayout.ctrlAlign
    // This is taken care of by `capacityToBuckets`.
    // It may be helpful to understand this if you remember that:
    // ctrlOffset = align(x * y, ctrlAlign)
    val x = (allocationSize - groupWidth) / (tableLayout.size + 1)
    return prevPow2(x)
}

/**
 * Specialization of `cloneFrom` for `Copy` types
 */
public interface RawTableClone<T> {
    public fun cloneFromSpec(source: T)
}

public fun <T : TrivialClone, A : Allocator> RawTable<T, A>.cloneFromSpec(source: RawTable<T, A>) {
    run {
        kmCopyNonoverlapping((source.table.ctrl(0)).rawAddress, (this.table.ctrl(0)).rawAddress, (this.table.numCtrlBytes()).toLong() * (UnresolvedTypeLayout<Tag>()).sizeBytes)
        source.dataStart().asPtr().copyToNonoverlapping(this.dataStart().asPtr(), this.table.numBuckets())
    }
    this.table.items = source.table.items
    this.table.growthLeft = source.table.growthLeft
}

/**
 * Iterator over a sub-range of a table. Unlike `RawIter` this iterator does
 * not track an item count.
 */
public open class RawIterRange<T> internal constructor(
    // Mask of full buckets in the current group. Bits are cleared from this
    // mask as each element is processed.
    public var currentGroup: BitMaskIter,
    // Pointer to the buckets for the current group.
    public var data: Bucket<T>,
    nextCtrlCtorArg: ConstPtr,
    endCtorArg: ConstPtr,
) : Iterable<Bucket<T>> {
    // Pointer to the next group of control bytes,
    // Must be aligned to the group size.
    public var nextCtrl: ConstPtr = nextCtrlCtorArg
        internal set

    // Pointer one past the last control byte of this range.
    public var end: ConstPtr = endCtorArg
        internal set

    override fun iterator(): Iterator<Bucket<T>> = object : AbstractIterator<Bucket<T>>() {
        override fun computeNext() {
            when (val v = this@RawIterRange.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Splits a `RawIterRange` into two halves.
     *
     * Returns `None` if the remaining range is smaller than or equal to the
     * group width.
     */
    public open fun split(): Pair<RawIterRange<T>, RawIterRange<T>?> =
        if (((this.end).rawAddress.toULong() <= (this.nextCtrl).rawAddress.toULong())) {
            // Nothing to split if the group that we are current processing
            // is the last one.
            Pair(this, null)
        } else {
            // len is the remaining number of elements after the group that
            // we are currently processing. It must be a multiple of the
            // group size (small tables are caught by the check above).
            val len = offsetFrom(this.end, this.nextCtrl)
            check(len % Group.WIDTH == 0)
            // Split the remaining elements into two halves, but round the
            // midpoint down in case there is an odd number of groups
            // remaining. This ensures that:
            // - The tail is at least 1 group long.
            // - The split is roughly even considering we still have the
            // current group to process.
            val mid = (len / 2) and (Group.WIDTH - 1).inv()
            val tail = RawIterRange.new(ConstPtr((this.nextCtrl).rawAddress + ((mid).toLong() * (PrimitiveLayouts.U8).strideBytes)), this.data.nextN(Group.WIDTH).nextN(mid), len - mid)
            check(this.data.nextN(Group.WIDTH).nextN(mid).ptr == tail.data.ptr)
            check(this.end == tail.end)
            this.end = ConstPtr((this.nextCtrl).rawAddress + ((mid).toLong() * (PrimitiveLayouts.U8).strideBytes))
            check(ConstPtr((this.end).rawAddress + ((Group.WIDTH).toLong() * (PrimitiveLayouts.U8).strideBytes)) == tail.nextCtrl)
            Pair(this, tail)
        }

    /**
     * # Safety
     * If `DO_CHECK_PTR_RANGE` is false, caller must ensure that we never try to iterate
     * after yielding all elements.
     */
    public open fun nextImpl(doCheckPtrRange: Boolean): Bucket<T>? {
        while (true) {
            when (val matchVal = this.currentGroup.next()) {
                null -> {}
                else -> {
                    val index = matchVal
                    return this.data.nextN(index)
                }
            }
            if (doCheckPtrRange && ((this.nextCtrl).rawAddress.toULong() >= (this.end).rawAddress.toULong())) {
                return null
            }
            // We might read past self.end up to the next group boundary,
            // but this is fine because it only occurs on tables smaller
            // than the group size where the trailing control bytes are all
            // EMPTY. On larger tables self.end is guaranteed to be aligned
            // to the group size (since tables are power-of-two sized).
            run {
                this.currentGroup = Group.loadAligned(this.nextCtrl).matchFull().intoIter()
                this.data = this.data.nextN(Group.WIDTH)
                this.nextCtrl = ConstPtr((this.nextCtrl).rawAddress + ((Group.WIDTH).toLong() * (PrimitiveLayouts.U8).strideBytes))
            }
        }
    }

    /**
     * Folds every element into an accumulator by applying an operation,
     * returning the final result.
     *
     * `foldImpl()` takes three arguments: the number of items remaining in
     * the iterator, an initial value, and a closure with two arguments: an
     * `accumulator`, and an element. The closure returns the value that the
     * accumulator should have for the next iteration.
     *
     * The initial value is the value the accumulator will have on the first call.
     *
     * After applying this closure to every element of the iterator, `foldImpl()`
     * returns the accumulator.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is
     * [Undefined.Behavior]:
     *
     * * The [RawTableInner] / [RawTable] must be alive and not moved,
     *   i.e. table outlives the `RawIterRange`;
     *
     * * The provided `n` value must match the actual number of items
     *   in the table.
     *
     * [Undefined.Behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun <B> foldImpl(n: Int, acc: B, f: (B, Bucket<T>) -> B): B {
        var n1 = n
        var acc1 = acc
        while (true) {
            while (true) {
                val matchVal = this.currentGroup.next()
                if (matchVal == null) break
                val index = matchVal
                // The returned `index` will always be in the range `0 .. Group.WIDTH`,
                // so that calling `self.data.nextN(index)` is safe (see detailed explanation below).
                check(n1 != 0)
                val bucket = this.data.nextN(index)
                acc1 = f(acc1, bucket)
                n1 -= 1
            }
            if (n1 == 0) {
                return acc1
            }
            // SAFETY: The caller of this function ensures that:
            //
            // 1. The provided `n` value matches the actual number of items in the table;
            // 2. The table is alive and did not moved.
            //
            // Taking the above into account, we always stay within the bounds, because:
            //
            // 1. For tables smaller than the group width (self.numBuckets() <= Group.Width),
            // we will never end up in the given branch, since we should have already
            // yielded all the elements of the table.
            //
            // 2. For tables larger than the group width. The number of buckets is a
            // power of two (2 ^ n), Group.Width is also power of two (2 ^ k). Since
            // `(2 ^ n)>(2 ^ k)`, than `(2 ^ n) %(2 ^ k) = 0`. As we start from the
            // start of the array of control bytes, and never try to iterate after
            // getting all the elements, the last `self.currentGroup` will read bytes
            // from the `self.numBuckets() - Group.WIDTH` index.  We know also that
            // `self.currentGroup.next()` will always return indices within the range
            // `0 .. Group.WIDTH`.
            //
            // Knowing all of the above and taking into account that we are synchronizing
            // the `self.data` index with the index we used to read the `self.currentGroup`,
            // the subsequent `self.data.nextN(index)` will always return a bucket with
            // an index number less than `self.numBuckets()`.
            //
            // The last `self.nextCtrl`, whose index would be `self.numBuckets()`, will never
            // actually be read, since we should have already yielded all the elements of
            // the table.
            run {
                this.currentGroup = Group.loadAligned(this.nextCtrl).matchFull().intoIter()
                this.data = this.data.nextN(Group.WIDTH)
                this.nextCtrl = ConstPtr((this.nextCtrl).rawAddress + ((Group.WIDTH).toLong() * (PrimitiveLayouts.U8).strideBytes))
            }
        }
    }

    // We make raw iterators unconditionally Send and Sync, and let the PhantomData
    // in the actual iterator implementations determine the real Send/Sync bounds.

    public open fun clone(): RawIterRange<T> = RawIterRange(data = this.data.clone(), nextCtrl = this.nextCtrl, currentGroup = this.currentGroup.clone(), end = this.end)

    public open fun next(): Bucket<T>? = this.nextImpl()

    public open fun sizeHint(): Pair<Int, Int?> {
        // We don't have an item count, so just guess based on the range size.
        val remainingBuckets =
            if (((this.end).rawAddress.toULong() > (this.nextCtrl).rawAddress.toULong())) {
                offsetFrom(this.end, this.nextCtrl)
            } else {
                0
            }
        // Add a group width to include the group we are currently processing.
        return Pair(0, Group.WIDTH + remainingBuckets)
    }

    public companion object {
        public operator fun <T> invoke(
            currentGroup: BitMaskIter,
            data: Bucket<T>,
            nextCtrl: ConstPtr,
            end: ConstPtr,
        ): RawIterRange<T> = RawIterRange<T>(
            currentGroup = currentGroup,
            data = data,
            nextCtrlCtorArg = nextCtrl,
            endCtorArg = end,
        )

        /**
         * Returns a `RawIterRange` covering a subset of a table.
         *
         * # Safety
         *
         * If any of the following conditions are violated, the result is
         * [undefined.behavior]:
         *
         * * `ctrl` must be valid for reads, i.e. table outlives the `RawIterRange`;
         *
         * * `ctrl` must be properly aligned to the group size (`Group.WIDTH`);
         *
         * * `ctrl` must point to the array of properly initialized control bytes;
         *
         * * `data` must be the [Bucket] at the `ctrl` index in the table;
         *
         * * the value of `len` must be less than or equal to the number of table buckets,
         *   and the returned value of `ctrl.asPtr().add(len).offsetFrom(ctrl.asPtr())`
         *   must be positive.
         *
         * * The `ctrl.add(len)` pointer must be either in bounds or one
         *   byte past the end of the same [allocated table].
         *
         * * The `len` must be a power of two.
         *
         * [undefined.behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
         */
        public fun <T> new(ctrl: ConstPtr, data: Bucket<T>, len: Int): RawIterRange<T> {
            check(len != 0)
            check(ctrl.rawAddress.toInt() % Group.WIDTH == 0)
            // SAFETY: The caller must uphold the safety rules for the [RawIterRange.new]
            val end = ConstPtr((ctrl).rawAddress + ((len).toLong() * (PrimitiveLayouts.U8).strideBytes))
            // Load the first group and advance ctrl to point to the next group
            // SAFETY: The caller must uphold the safety rules for the [RawIterRange.new]
            val (currentGroup, nextCtrl) = Pair(Group.loadAligned(ctrl).matchFull(), ConstPtr((ctrl).rawAddress + ((Group.WIDTH).toLong() * (PrimitiveLayouts.U8).strideBytes)))
            return RawIterRange(currentGroup = currentGroup.intoIter(), data = data, nextCtrl = nextCtrl, end = end)
        }
    }
}

/**
 * Iterator which returns a raw pointer to every full bucket in the table.
 *
 * For maximum flexibility this iterator is not bound by a scope, but you
 * must observe several rules when using it:
 * - You must not free the hash table while iterating (including via growing/shrinking).
 * - It is fine to erase a bucket that has been yielded by the iterator.
 * - Erasing a bucket that has not yet been yielded by the iterator may still
 *   result in the iterator yielding that bucket (unless `reflectRemove` is called).
 * - It is unspecified whether an element inserted after the iterator was
 *   created will be yielded by that iterator (unless `reflectInsert` is called).
 * - The order in which the iterator yields bucket is unspecified and may
 *   change in the future.
 */
public open class RawIter<T>(
    public var iter: RawIterRange<T>,
    public var items: Int,
) : Iterable<Bucket<T>> {
    override fun iterator(): Iterator<Bucket<T>> = object : AbstractIterator<Bucket<T>>() {
        override fun computeNext() {
            when (val v = this@RawIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun dropElements() {
        if (T.NEEDS_DROP && this.items != 0) {
            for (item in this) {
                item.drop()
            }
        }
    }

    public open fun clone(): RawIter<T> = RawIter(iter = this.iter.clone(), items = this.items)

    public open fun next(): Bucket<T>? {
        // Inner iterator iterates over buckets
        // so it can do unnecessary work if we already yielded all items.
        if (this.items == 0) {
            return null
        }
        val nxt = this.iter.nextImpl()
        check(nxt != null)
        this.items -= 1
        return nxt
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.items, this.items)

    public open fun <B> fold(init: B, f: (B, Bucket<T>) -> B): B = this.iter.foldImpl(this.items, init, f)

    public companion object {
        public fun <T> default(): RawIter<T> {
            // SAFETY: Because the table is static, it always outlives the iter.
            return RawTableInner.NEW.iter()
        }
    }
}

/**
 * Iterator which returns an index of every full bucket in the table.
 *
 * For maximum flexibility this iterator is not bound by a scope, but you
 * must observe several rules when using it:
 * - You must not free the hash table while iterating (including via growing/shrinking).
 * - It is fine to erase a bucket that has been yielded by the iterator.
 * - Erasing a bucket that has not yet been yielded by the iterator may still
 *   result in the iterator yielding index of that bucket.
 * - It is unspecified whether an element inserted after the iterator was
 *   created will be yielded by that iterator.
 * - The order in which the iterator yields indices of the buckets is unspecified
 *   and may change in the future.
 */
public open class FullBucketsIndices internal constructor(
    // Mask of full buckets in the current group. Bits are cleared from this
    // mask as each element is processed.
    public var currentGroup: BitMaskIter,
    // Initial value of the bytes' indices of the current group (relative
    // to the start of the control bytes).
    public var groupFirstIndex: Int,
    ctrlCtorArg: NonNull,
    // Number of elements in the table.
    public var items: Int,
) : Iterable<Int> {
    // Pointer to the current group of control bytes,
    // Must be aligned to the group size (Group.Width).
    public var ctrl: NonNull = ctrlCtorArg
        internal set

    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@FullBucketsIndices.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): FullBucketsIndices = FullBucketsIndices(currentGroup, groupFirstIndex, ctrl, items)

    /**
     * Advances the iterator and returns the next value.
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is
     * [Undefined.Behavior]:
     *
     * * The [RawTableInner] / [RawTable] must be alive and not moved,
     *   i.e. table outlives the `FullBucketsIndices`;
     *
     * * It never tries to iterate after getting all elements.
     *
     * [Undefined.Behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public open fun nextImpl(): Int? {
        while (true) {
            when (val matchVal = this.currentGroup.next()) {
                null -> {}
                else -> {
                    val index = matchVal
                    // The returned `self.groupFirstIndex + index` will always
                    // be in the range `0 .. self.numBuckets()`. See explanation below.
                    return this.groupFirstIndex + index
                }
            }
            // SAFETY: The caller of this function ensures that:
            //
            // 1. It never tries to iterate after getting all the elements;
            // 2. The table is alive and did not moved;
            // 3. The first `self.ctrl` pointed to the start of the array of control bytes.
            //
            // Taking the above into account, we always stay within the bounds, because:
            //
            // 1. For tables smaller than the group width (self.numBuckets() <= Group.Width),
            // we will never end up in the given branch, since we should have already
            // yielded all the elements of the table.
            //
            // 2. For tables larger than the group width. The number of buckets is a
            // power of two (2 ^ n), Group.Width is also power of two (2 ^ k). Since
            // `(2 ^ n)>(2 ^ k)`, than `(2 ^ n) %(2 ^ k) = 0`. As we start from the
            // the start of the array of control bytes, and never try to iterate after
            // getting all the elements, the last `self.ctrl` will be equal to
            // the `self.numBuckets() - Group.WIDTH`, so `self.currentGroup.next()`
            // will always contains indices within the range `0 .. Group.WIDTH`,
            // and subsequent `self.groupFirstIndex + index` will always return a
            // number less than `self.numBuckets()`.
            run {
                this.ctrl = NonNull.newUnchecked(this.ctrl.asPtr().add(Group.WIDTH))
            }
            // SAFETY: See explanation above.
            run {
                this.currentGroup = Group.loadAligned(this.ctrl.asPtr().cast()).matchFull().intoIter()
                this.groupFirstIndex += Group.WIDTH
            }
        }
    }

    /**
     * Advances the iterator and returns the next value. It is up to
     * the caller to ensure that the `RawTable` outlives the `FullBucketsIndices`,
     * because we cannot make the `next` method unchecked.
     */
    public open fun next(): Int? {
        // Return if we already yielded all items.
        if (this.items == 0) {
            return null
        }
        // SAFETY:
        // 1. We check number of items to yield using `items` field.
        // 2. The caller ensures that the table is alive and has not moved.
        val nxt = this.nextImpl()
        check(nxt != null)
        this.items -= 1
        return nxt
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.items, this.items)

    public companion object {
        public operator fun invoke(
            currentGroup: BitMaskIter,
            groupFirstIndex: Int,
            ctrl: NonNull,
            items: Int,
        ): FullBucketsIndices = FullBucketsIndices(
            currentGroup = currentGroup,
            groupFirstIndex = groupFirstIndex,
            ctrlCtorArg = ctrl,
            items = items,
        )

        public fun default(): FullBucketsIndices {
            // SAFETY: Because the table is static, it always outlives the iter.
            return RawTableInner.NEW.fullBucketsIndices()
        }
    }
}

/**
 * Iterator which consumes a table and returns elements.
 */
public open class RawIntoIter<T, A : Allocator> internal constructor(
    public var iter: RawIter<T>,
    allocationCtorArg: Triple<NonNull, Layout, A>?,
    public var marker: Unit,
) : Iterable<T> {
    public var allocation: Triple<NonNull, Layout, A>? = allocationCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@RawIntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun iter(): RawIter<T> = this.iter.clone()

    public open fun drop() {
        run {
            // Drop all remaining elements
            this.iter.dropElements()
            // Free the table
            when (val matchVal = this.allocation) {
                null -> {}
                else -> {
                    val ptr = matchVal.first
                    val layout = matchVal.second
                    val alloc = matchVal.third
                    alloc.deallocate(ptr, layout)
                }
            }
        }
    }

    public open fun next(): T? {
        return (this.iter.next() ?: return null).read()
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            iter: RawIter<T>,
            allocation: Triple<NonNull, Layout, A>?,
            marker: Unit,
        ): RawIntoIter<T, A> = RawIntoIter<T, A>(
            iter = iter,
            allocationCtorArg = allocation,
            marker = marker,
        )

        public fun <T, A : Allocator> default(): RawIntoIter<T, A> = RawIntoIter(iter = RawIter.default(), allocation = null, marker = Unit)
    }
}

/**
 * Iterator which consumes elements without freeing the table storage.
 */
public open class RawDrain<T, A : Allocator> internal constructor(
    public var iter: RawIter<T>,
    // The table is moved into the iterator for the duration of the drain. This
    // ensures that an empty table is left if the drain iterator is leaked
    // without dropping.
    public var table: RawTableInner,
    origTableCtorArg: NonNull,
    // We don't use a &a mut RawTable<T> because we want RawDrain to be
    // covariant over T.
    public var marker: Unit,
) : Iterable<T> {
    public var origTable: NonNull = origTableCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@RawDrain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun iter(): RawIter<T> = this.iter.clone()

    public open fun drop() {
        run {
            // Drop all remaining elements. Note that this may panic.
            this.iter.dropElements()
            // Reset the contents of the table now that all elements have been
            // dropped.
            this.table.clearNoDrop()
            // Move the now empty table back to its original location.
            this.origTable.asPtr().copyFromNonoverlapping(addrOf(this.table), 1)
        }
    }

    public open fun next(): T? {
        return run {
            val item = (this.iter.next() ?: return null)
            item.read()
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            iter: RawIter<T>,
            table: RawTableInner,
            origTable: NonNull,
            marker: Unit,
        ): RawDrain<T, A> = RawDrain<T, A>(
            iter = iter,
            table = table,
            origTableCtorArg = origTable,
            marker = marker,
        )
    }
}

/**
 * Iterator over occupied buckets that could match a given hash.
 *
 * `RawTable` only stores 7 bits of the hash value, so this iterator may return
 * items that have a hash value different than the one provided. You should
 * always validate the returned values before using them.
 *
 * For maximum flexibility this iterator is not bound by a scope, but you
 * must observe several rules when using it:
 * - You must not free the hash table while iterating (including via growing/shrinking).
 * - It is fine to erase a bucket that has been yielded by the iterator.
 * - Erasing a bucket that has not yet been yielded by the iterator may still
 *   result in the iterator yielding that bucket.
 * - It is unspecified whether an element inserted after the iterator was
 *   created will be yielded by that iterator.
 * - The order in which the iterator yields buckets is unspecified and may
 *   change in the future.
 */
public open class RawIterHash<T>(
    public var inner: RawIterHashIndices,
    public var marker: Unit,
) : Iterable<Bucket<T>> {
    override fun iterator(): Iterator<Bucket<T>> = object : AbstractIterator<Bucket<T>>() {
        override fun computeNext() {
            when (val v = this@RawIterHash.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): RawIterHash<T> = RawIterHash(inner = this.inner.clone(), marker = Unit)

    public open fun next(): Bucket<T>? =
        when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val index = matchVal
                // Can't use `RawTable.bucket` here as we don't have
                // an actual `RawTable` reference to use.
                check(index <= this.inner.bucketMask)
                val bucket = Bucket.fromBaseIndex(this.inner.ctrl.cast(), index)
                bucket
            }
        }

    public companion object {
        public fun <T, A : Allocator> new(table: RawTable<T, A>, hash: ULong): RawIterHash<T> = RawIterHash(inner = RawIterHashIndices.new(table.table, hash), marker = Unit)

        public fun <T> default(): RawIterHash<T> = RawIterHash(inner = RawIterHashIndices.default(), marker = Unit)
    }
}

public open class RawIterHashIndices internal constructor(
    // See `RawTableInner`s corresponding fields for details.
    // We can't store a `* const RawTableInner` as it would get
    // invalidated by the user calling  methods on `RawTable`.
    public var bucketMask: Int,
    ctrlCtorArg: NonNull,
    // The top 7 bits of the hash.
    public var tagHash: Tag,
    // The sequence of groups to probe in the search.
    public var probeSeq: ProbeSeq,
    public var group: Group,
    // The elements within the group with a matching tag-hash.
    public var bitmask: BitMaskIter,
) : Iterable<Int> {
    public var ctrl: NonNull = ctrlCtorArg
        internal set

    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@RawIterHashIndices.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): RawIterHashIndices = RawIterHashIndices(bucketMask, ctrl, tagHash, probeSeq, group, bitmask)

    public open fun next(): Int? {
        while (true) {
            when (val matchVal = this.bitmask.next()) {
                null -> {}
                else -> {
                    val bit = matchVal
                    val index = (this.probeSeq.pos + bit) and this.bucketMask
                    return index
                }
            }
            if (likely(this.group.matchEmpty().anyBitSet())) {
                return null
            }
            this.probeSeq.moveNext(this.bucketMask)
            // Can't use `RawTableInner.ctrl` here as we don't have
            // an actual `RawTableInner` reference to use.
            val index1 = this.probeSeq.pos
            check(index1 < this.bucketMask + 1 + Group.WIDTH)
            val groupCtrl = this.ctrl.asPtr().add(index1).cast()
            this.group = Group.load(groupCtrl)
            this.bitmask = this.group.matchTag(this.tagHash).intoIter()
        }
    }

    public companion object {
        public operator fun invoke(
            bucketMask: Int,
            ctrl: NonNull,
            tagHash: Tag,
            probeSeq: ProbeSeq,
            group: Group,
            bitmask: BitMaskIter,
        ): RawIterHashIndices = RawIterHashIndices(
            bucketMask = bucketMask,
            ctrlCtorArg = ctrl,
            tagHash = tagHash,
            probeSeq = probeSeq,
            group = group,
            bitmask = bitmask,
        )

        public fun default(): RawIterHashIndices {
            // SAFETY: Because the table is static, it always outlives the iter.
            return RawIterHashIndices.new(RawTableInner.NEW, 0uL)
        }

        public fun new(table: RawTableInner, hash: ULong): RawIterHashIndices {
            val tagHash = Tag.full(hash)
            val probeSeq = table.probeSeq(hash)
            val group = Group.load(table.ctrl(probeSeq.pos))
            val bitmask = group.matchTag(tagHash).intoIter()
            return RawIterHashIndices(bucketMask = table.bucketMask, ctrl = table.ctrl, tagHash = tagHash, probeSeq = probeSeq, group = group, bitmask = bitmask)
        }
    }
}

public open class RawExtractIf<T, A : Allocator> internal constructor(
    public var iter: RawIter<T>,
    tableCtorArg: RawTable<T, A>,
) {
    public var table: RawTable<T, A> = tableCtorArg
        internal set

    public open fun next(f: (T) -> Boolean): T? {
        for (item in this.iter) {
            if (f(item.asMut())) {
                return this.table.remove(item).first
            }
        }
        return null
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            iter: RawIter<T>,
            table: RawTable<T, A>,
        ): RawExtractIf<T, A> = RawExtractIf<T, A>(
            iter = iter,
            tableCtorArg = table,
        )
    }
}
