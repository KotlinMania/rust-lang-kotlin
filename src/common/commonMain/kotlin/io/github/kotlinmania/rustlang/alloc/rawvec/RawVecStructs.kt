// rust-kotlin: manual
package io.github.kotlinmania.rustlang.alloc.rawvec

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.KmForgettableResource
import io.github.kotlinmania.rustlang.core.intrinsics.KmResourceCleaner
import io.github.kotlinmania.rustlang.core.intrinsics.kmRegisterResourceCleaner
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.nichetypes.UsizeNoHighBit
import io.github.kotlinmania.rustlang.core.num.uncheckedMul
import io.github.kotlinmania.rustlang.core.num.wrappingSub
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

internal class RawVecAllocState<A : Allocator>(
    var ptr: NonNull,
    var byteCap: Int,
    var alignBytes: Int,
    var alloc: A,
    var owned: Boolean,
) {
    fun deallocIfOwned() {
        if (owned) {
            owned = false
            if (byteCap > 0) {
                alloc.deallocate(ptr, Layout.fromSizeAlignUnchecked(byteCap, alignBytes))
            }
        }
    }
}

/**
 * A low-level utility for more ergonomically allocating, reallocating, and deallocating
 * a buffer of memory on the heap without having to worry about all the corner cases
 * involved. This type is excellent for building your own data structures like Vec and VecDeque.
 * In particular:
 *
 * * Produces `NonNull.dangling()` on zero-sized types.
 * * Produces `NonNull.dangling()` on zero-length allocations.
 * * Avoids freeing `NonNull.dangling()`.
 * * Catches all overflows in capacity computations (promotes them to "capacity overflow" panics).
 * * Guards against 32-bit systems allocating more than `Long.MAX` bytes.
 * * Provides niches for capacities greater than `Long.MAX`.
 * * Guards against overflowing your length.
 * * Calls `handleAllocError` for fallible allocations.
 * * Implements `Send`, `Sync` and `UnwindSafe` iff `(T, A)` does.
 * * Carries `PhantomData<T>` for auto trait and `mayDangle` correctness.
 * * Uses the excess returned from the allocator to use the largest available capacity.
 *
 * This type does not in anyway inspect the memory that it manages. When dropped it *will*
 * free its memory, but it *won't* try to drop its contents. It is up to the user of `RawVec`
 * to handle the actual things *stored* inside of a `RawVec`.
 *
 * Note that the excess of a zero-sized types is always infinite, so `capacity()` always returns
 * `Int.MAX`. This means that you need to be careful when round-tripping this type with a
 * `Holder<[T]>`, since `capacity()` won't yield the length.
 */
public open class RawVec<T, A : Allocator> internal constructor(
    innerCtorArg: RawVecInner<A>,
) : Drop,
    KmForgettableResource {
    public var inner: RawVecInner<A> = innerCtorArg
        internal set

    public override fun close() {
        this.drop()
    }

    public override fun drop() {
        this.inner.drop()
    }

    public override fun disarmCleaner() {
        this.inner.disarmCleaner()
    }

    /**
     * A specialized version of `self.reserve(len, 1)` which requires the
     * caller to ensure `len == self.capacity()`.
     */
    public open fun growOne(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        this.inner.growOne(Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * Converts the entire buffer into `Holder<[MaybeUninit<T>]>` with the specified `len`.
     *
     * Note that this will correctly reconstitute any `cap` changes
     * that may have been performed. (See description of type for details.)
     *
     * # Safety
     *
     * * `len` must be greater than or equal to the most recently requested capacity, and
     * * `len` must be less than or equal to `self.capacity()`.
     *
     * Note, that the requested capacity and `self.capacity()` could differ, as
     * an allocator could overallocate and return a greater memory block than requested.
     */
    public open fun intoBox(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Box<List<MaybeUninit<T>>, A> {
        // Sanity-check one half of the safety requirement (we cannot check the other half).
        val currentCap =
            if (typeLayoutT is UnresolvedTypeLayout) {
                this.inner.cap.asInner()
            } else {
                this.capacity(typeLayoutT)
            }
        check(len <= currentCap) { "`len` must be smaller than or equal to `self.capacity()`" }
        val me = this
        val slice = me.ptr().castSlice(len)
        me.disarmCleaner()
        return Box.fromRawIn(slice, me.inner.alloc)
    }

    /**
     * Gets a raw pointer to the start of the allocation. Note that this is
     * `NonNull.dangling()` if `capacity == 0` or `T` is zero-sized. In the former case, you must
     * be careful.
     */
    public open fun ptr(): MutPtr = this.inner.ptr()

    public open fun nonNull(): NonNull = this.inner.nonNull()

    /**
     * Gets the capacity of the allocation.
     *
     * This will always be `Int.MAX` if `T` is zero-sized.
     */
    public open fun capacity(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int = this.inner.capacity(sizeOf<T>(typeLayoutT = typeLayoutT))

    /**
     * Returns a shared reference to the allocator backing this `RawVec`.
     */
    public open fun allocator(): A = this.inner.allocator()

    /**
     * Ensures that the buffer contains at least enough space to hold `len +
     * additional` elements. If it doesn't already have enough capacity, will
     * reallocate enough space plus comfortable slack space to get amortized
     * *O*(1) behavior. Will limit this behavior if it would needlessly cause
     * itself to panic.
     *
     * If `len` exceeds `self.capacity()`, this may fail to actually allocate
     * the requested space. This is not really unchecked, but the unchecked
     * code *you* write that relies on the behavior of this function may break.
     *
     * This is ideal for implementing a bulk-push operation like `extend`.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Aborts
     *
     * Aborts on OOM.
     */
    public open fun reserve(len: Int, additional: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        this.inner.reserve(len, additional, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * The same as `reserve`, but returns on errors instead of panicking or aborting.
     */
    public open fun tryReserve(len: Int, additional: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Unit, TryReserveError> {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        return this.inner.tryReserve(len, additional, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * Ensures that the buffer contains at least enough space to hold `len +
     * additional` elements. If it doesn't already, will reallocate the
     * minimum possible amount of memory necessary. Generally this will be
     * exactly the amount of memory necessary, but in principle the allocator
     * is free to give back more than we asked for.
     *
     * If `len` exceeds `self.capacity()`, this may fail to actually allocate
     * the requested space. This is not really unchecked, but the unchecked code
     * *you* write that relies on the behavior of this function may break.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Aborts
     *
     * Aborts on OOM.
     */
    public open fun reserveExact(len: Int, additional: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        this.inner.reserveExact(len, additional, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * The same as `reserveExact`, but returns on errors instead of panicking or aborting.
     */
    public open fun tryReserveExact(len: Int, additional: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Unit, TryReserveError> {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        return this.inner.tryReserveExact(len, additional, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * Shrinks the buffer down to the specified capacity. If the given amount
     * is 0, actually completely deallocates.
     *
     * # Panics
     *
     * Panics if the given amount is *larger* than the current capacity.
     *
     * # Aborts
     *
     * Aborts on OOM.
     */
    public open fun shrinkToFit(cap: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        // SAFETY: All calls on self.inner pass T.Layout as the elemLayout
        this.inner.shrinkToFit(cap, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * Shrinks the buffer down to the specified capacity. If the given amount
     * is 0, actually completely deallocates.
     *
     * # Errors
     *
     * This function returns an error if the allocator cannot shrink the allocation.
     *
     * # Panics
     *
     * Panics if the given amount is *larger* than the current capacity.
     */
    public open fun tryShrinkToFit(cap: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Unit, TryReserveError> {
        // SAFETY: Layout is valid for T.
        return this.inner.tryShrinkToFit(cap, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    /**
     * Frees the memory owned by the `RawVec` *without* trying to drop its contents.
     */
    public open fun drop(typeLayoutT: TypeLayout<T>) {
        if (typeLayoutT is UnresolvedTypeLayout) {
            this.inner.drop()
            return
        }
        // SAFETY: We are in a Drop impl, self.inner will not be used again.
        this.inner.deallocate(Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: RawVecInner<A>,
        ): RawVec<T, A> =
            RawVec<T, A>(
                innerCtorArg = inner,
            )

        /**
         * Like `withCapacity`, but parameterized over the choice of
         * allocator for the returned `RawVec`.
         */
        public fun <T, A : Allocator> withCapacityIn(capacity: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, A> = RawVec(inner = RawVecInner.withCapacityIn(capacity, alloc, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())))

        public fun <T> minNonZeroCapFor(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int = minNonZeroCap(sizeOf<T>(typeLayoutT = typeLayoutT))

        /**
         * Like `new`, but parameterized over the choice of allocator for
         * the returned `RawVec`.
         */
        public fun <T, A : Allocator> newIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, A> {
            val align =
                if (typeLayoutT is UnresolvedTypeLayout) {
                    Alignment.MIN
                } else {
                    val elemLayout = Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())
                    check(elemLayout.size() % elemLayout.align() == 0)
                    elemLayout.alignment()
                }
            return RawVec(inner = RawVecInner.newIn(alloc, align))
        }

        /**
         * Like `tryWithCapacity`, but parameterized over the choice of
         * allocator for the returned `RawVec`.
         */
        public fun <T, A : Allocator> tryWithCapacityIn(capacity: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<RawVec<T, A>, TryReserveError> =
            run {
                val matchVal = RawVecInner.tryWithCapacityIn(capacity, alloc, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()))
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val inner = matchVal.okValue()
                        Result.Ok<RawVec<T, A>, TryReserveError>(RawVec(inner = inner))
                    }
                    is Result.Err<*, *> -> {
                        val e = matchVal.errValue()
                        Result.Err<RawVec<T, A>, TryReserveError>(e)
                    }
                }
            }

        /**
         * Like `withCapacityZeroed`, but parameterized over the choice
         * of allocator for the returned `RawVec`.
         */
        public fun <T, A : Allocator> withCapacityZeroedIn(capacity: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, A> = RawVec(inner = RawVecInner.withCapacityZeroedIn(capacity, alloc, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())))

        /**
         * Reconstitutes a `RawVec` from a pointer, capacity, and allocator.
         *
         * # Safety
         *
         * The `ptr` must be allocated (via the given allocator `alloc`), and with the given
         * `capacity`.
         * The `capacity` cannot exceed `Long.MAX` for sized types. (only a concern on 32-bit
         * systems). For ZSTs capacity is ignored.
         * If the `ptr` and `capacity` come from a `RawVec` created via `alloc`, then this is
         * guaranteed.
         */
        public fun <T, A : Allocator> fromRawPartsIn(ptr: MutPtr, capacity: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, A> {
            // SAFETY: Precondition passed to the caller
            return run {
                val ptr1 = ptr
                val capacity1 =
                    if (typeLayoutT is UnresolvedTypeLayout) {
                        UsizeNoHighBit.newUnchecked(capacity)
                    } else {
                        newCap<T>(capacity, typeLayoutT = typeLayoutT)
                    }
                val inner = RawVecInner.fromRawPartsIn(ptr1, capacity1, alloc)
                if (typeLayoutT !is UnresolvedTypeLayout && capacity1.asInner() > 0 && typeLayoutT.sizeBytes > 0L) {
                    inner.trackAllocation(inner.nonNull(), capacity1.asInner() * typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt(), true)
                }
                RawVec(inner = inner)
            }
        }

        /**
         * A convenience method for hoisting the non-null precondition out of [RawVec.fromRawPartsIn].
         *
         * # Safety
         *
         * See [RawVec.fromRawPartsIn].
         */
        public fun <T, A : Allocator> fromNonnullIn(ptr: NonNull, capacity: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, A> {
            // SAFETY: Precondition passed to the caller
            return run {
                val ptr1 = ptr.cast()
                val capacity1 =
                    if (typeLayoutT is UnresolvedTypeLayout) {
                        UsizeNoHighBit.newUnchecked(capacity)
                    } else {
                        newCap<T>(capacity, typeLayoutT = typeLayoutT)
                    }
                val inner = RawVecInner.fromNonnullIn(ptr1, capacity1, alloc)
                if (typeLayoutT !is UnresolvedTypeLayout && capacity1.asInner() > 0 && typeLayoutT.sizeBytes > 0L) {
                    inner.trackAllocation(inner.nonNull(), capacity1.asInner() * typeLayoutT.sizeBytes.toInt(), typeLayoutT.alignBytes.toInt(), true)
                }
                RawVec(inner = inner)
            }
        }
    }
}

/**
 * Creates the biggest possible `RawVec` (on the system heap)
 * without allocating. If `T` has positive size, then this makes a
 * `RawVec` with capacity `0`. If `T` is zero-sized, then it makes a
 * `RawVec` with capacity `Int.MAX`. Useful for implementing
 * delayed allocation.
 */
public fun <T> RawVec.Companion.new(): RawVec<T, Global> =
    RawVec.newIn(
        io.github.kotlinmania.rustlang.alloc.alloc
            .Global(),
    )

/**
 * Creates a `RawVec` (on the system heap) with exactly the
 * capacity and alignment requirements for a `[T; capacity]`. This is
 * equivalent to calling `RawVec.new` when `capacity` is `0` or `T` is
 * zero-sized. Note that if `T` is zero-sized this means you will
 * *not* get a `RawVec` with the requested capacity.
 *
 * Non-fallible version of `tryWithCapacity`
 *
 * # Panics
 *
 * Panics if the requested capacity exceeds `Long.MAX` bytes.
 *
 * # Aborts
 *
 * Aborts on OOM.
 */
public fun <T> RawVec.Companion.withCapacity(capacity: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, Global> = RawVec(inner = RawVecInner.withCapacity(capacity, Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt())))

/**
 * Like `withCapacity`, but guarantees the buffer is zeroed.
 */
public fun <T> RawVec.Companion.withCapacityZeroed(capacity: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RawVec<T, Global> =
    RawVec(
        inner =
            RawVecInner.withCapacityZeroedIn(
                capacity,
                io.github.kotlinmania.rustlang.alloc.alloc
                    .Global(),
                Layout.fromSizeAlignUnchecked((typeLayoutT).sizeBytes.toInt(), (typeLayoutT).alignBytes.toInt()),
            ),
    )

/**
 * Like a `RawVec`, but only generic over the allocator, not the type.
 *
 * As such, all the methods need the layout passed-in as a parameter.
 *
 * Having this separation reduces the amount of code we need to monomorphize,
 * as most operations don't need the actual type, just its layout.
 */
public open class RawVecInner<A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    /**
     * Never used for ZSTs; it's `capacity()`s responsibility to return usize.Max in that case.
     *
     * # Safety
     *
     * `cap` must be in the `0 ..= Long.MAX` range.
     */
    public var cap: Cap,
    public var alloc: A,
) : Drop,
    KmForgettableResource {
    internal var ptr: NonNull = ptrCtorArg

    private val allocState: RawVecAllocState<A> = RawVecAllocState(ptrCtorArg, 0, 1, alloc, false)
    private val cleaner: KmResourceCleaner =
        allocState.let { state ->
            kmRegisterResourceCleaner(this) { state.deallocIfOwned() }
        }

    internal fun trackAllocation(
        newPtr: NonNull,
        byteCap: Int,
        alignBytes: Int,
        owned: Boolean,
    ) {
        this.allocState.ptr = newPtr
        this.allocState.byteCap = byteCap
        this.allocState.alignBytes = alignBytes
        this.allocState.alloc = this.alloc
        this.allocState.owned = owned
    }

    public override fun close() {
        this.drop()
    }

    public override fun drop() {
        this.cleaner.cleanNow()
    }

    public override fun disarmCleaner() {
        this.allocState.owned = false
        this.cleaner.disarm()
    }

    // Note: Consider moving these impls to `RawVec`, once #162850 is resolved.

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun growOne(elemLayout: Layout) {
        // SAFETY: Precondition passed to caller
        when (val matchVal = this.growAmortized(this.cap.asInner(), 1, elemLayout)) {
            is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                handleError(err)
            }
            else -> {}
        }
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - The sum of `len` and `additional` must be greater than the current capacity
     */
    public open fun growAmortized(len: Int, additional: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        // This is ensured by the calling contexts.
        check(additional > 0)
        if (elemLayout.size() == 0) {
            // Since we return a capacity of `Int.MAX` when `elemSize` is
            // 0, getting to here necessarily means the `RawVec` is overfull.
            return Result.Err<Unit, TryReserveError>(TryReserveError.from(TryReserveErrorKind.CapacityOverflow))
        }
        // Nothing we can really do about these checks, sadly.
        val requiredCap =
            len.checkedAdd(additional)
                ?: return Result.Err<Unit, TryReserveError>(TryReserveError.from(TryReserveErrorKind.CapacityOverflow))
        // This guarantees exponential growth. The doubling cannot overflow
        // because `cap <= Long.MAX` and the type of `cap` is `Int`.
        val cap = maxOf(this.cap.asInner() * 2, requiredCap)
        val cap1 = maxOf(minNonZeroCap(elemLayout.size()), cap)
        // SAFETY:
        // - cap >= len + additional
        // - other preconditions passed to caller
        val ptr =
            when (val tryResult = this.finishGrow(cap1, elemLayout)) {
                is Result.Ok<*, *> -> tryResult.okValue()
                is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue())
            }
        // SAFETY: `finishGrow` would have failed if `cap> Long.MAX`
        this.setPtrAndCap(ptr, cap1)
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    // not marked inline(never) since we want optimizers to be able to observe the specifics of this
    // function, see tests/codegen-llvm/vec-reserve-extend.rs.

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - `cap` must be greater than the current capacity
     */
    public open fun finishGrow(cap: Int, elemLayout: Layout): Result<NonNull, TryReserveError> {
        val newLayout =
            when (val tryResult = layoutArray(cap, elemLayout)) {
                is Result.Ok<*, *> -> tryResult.okValue()
                is Result.Err<*, *> -> return Result.Err<NonNull, TryReserveError>(tryResult.errValue())
            }
        // SAFETY: Upheld by caller.
        val memory =
            when (val matchVal = this.currentMemory(elemLayout)) {
                null -> {
                    this.alloc.allocate(newLayout)
                }
                else -> {
                    val ptr = matchVal.first
                    val oldLayout = matchVal.second
                    // Note(const-Note): switch to `debugAssertEq`
                    check(oldLayout.align() == newLayout.align())
                    // SAFETY: Upheld by caller.
                    run {
                        // The allocator checks for alignment equality
                        assertUnchecked(oldLayout.align() == newLayout.align())
                        this.alloc.grow(ptr, oldLayout, newLayout)
                    }
                }
            }
        val mapped = memory.mapErr { TryReserveError.from(TryReserveErrorKind.AllocError(layout = newLayout)) }
        if (mapped is Result.Ok) {
            this.trackAllocation(mapped.value.cast(), newLayout.size(), newLayout.align(), true)
        }
        return mapped
    }

    public open fun ptr(): MutPtr = this.nonNull().asPtr()

    public open fun nonNull(): NonNull = this.ptr.cast()

    public open fun capacity(elemSize: Int): Int =
        if (elemSize == 0) {
            Int.MAX_VALUE
        } else {
            this.cap.asInner()
        }

    public open fun allocator(): A = this.alloc

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun currentMemory(elemLayout: Layout): Pair<NonNull, Layout>? =
        if (elemLayout.size() == 0 || this.cap.asInner() == 0) {
            null
        } else {
            // We could use Layout.array here which ensures the absence of isize and Int overflows
            // and could hypothetically handle differences between stride and size, but this memory
            // has already been allocated so we know it can't overflow and currently Rust does not
            // support such types. So we can do better by skipping some checks and avoid an unwrap.
            // SAFETY: Upheld by caller, unless the element size is 0 which is checked against.
            run {
                val allocSize = elemLayout.size().uncheckedMul(this.cap.asInner())
                val layout = Layout.fromSizeAlignUnchecked(allocSize, elemLayout.align())
                Pair(this.ptr, layout)
            }
        }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun reserve(len: Int, additional: Int, elemLayout: Layout) {
        // Callers expect this function to be very cheap when there is already sufficient capacity.
        // Therefore, we move all the resizing and error-handling logic from growAmortized and
        // handleReserve behind a call, while making sure that this function is likely to be
        // inlined as just a comparison and a call if the comparison fails.
        fun <A : Allocator> doReserveAndHandle(slf: RawVecInner<A>, len1: Int, additional1: Int, elemLayout1: Layout) {
            // SAFETY: Precondition passed to caller
            when (val matchVal = slf.growAmortized(len1, additional1, elemLayout1)) {
                is Result.Err<*, *> -> {
                    val err = matchVal.errValue()
                    handleError(err)
                }
                else -> {}
            }
        }
        if (this.needsToGrow(len, additional, elemLayout)) {
            // SAFETY: `needsToGrow` ensures that `len + additional` is greater than
            // the current capacity, with the other preconditions upheld by our caller.
            run {
                doReserveAndHandle(this, len, additional, elemLayout)
            }
        }
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun tryReserve(len: Int, additional: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        if (this.needsToGrow(len, additional, elemLayout)) {
            // SAFETY: Precondition passed to caller
            run {
                when (val tryResult = this.growAmortized(len, additional, elemLayout)) {
                    is Result.Ok<*, *> -> tryResult.okValue()
                    is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue())
                }
            }
        }
        // SAFETY: If we've already grown, we will not need to again immediately after.
        run {
            // Inform the optimizer that the reservation has succeeded or wasn't needed
            assertUnchecked(!this.needsToGrow(len, additional, elemLayout))
        }
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun reserveExact(len: Int, additional: Int, elemLayout: Layout) {
        // SAFETY: Precondition passed to caller
        when (val matchVal = this.tryReserveExact(len, additional, elemLayout)) {
            is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                handleError(err)
            }
            else -> {}
        }
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     */
    public open fun tryReserveExact(len: Int, additional: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        if (this.needsToGrow(len, additional, elemLayout)) {
            // SAFETY: Precondition passed to caller
            run {
                when (val tryResult = this.growExact(len, additional, elemLayout)) {
                    is Result.Ok<*, *> -> tryResult.okValue()
                    is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue())
                }
            }
        }
        // SAFETY: If we've already grown, we will not need to again immediately after.
        run {
            // Inform the optimizer that the reservation has succeeded or wasn't needed
            assertUnchecked(!this.needsToGrow(len, additional, elemLayout))
        }
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - `cap` must be less than or equal to `self.capacity(elemLayout.size())`
     */
    public open fun shrinkToFit(cap: Int, elemLayout: Layout) {
        // SAFETY: Upheld by caller.
        when (val matchVal = this.shrink(cap, elemLayout)) {
            is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                handleError(err)
            }
            else -> {}
        }
    }

    /**
     * # Safety
     *
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - `cap` must be less than or equal to `self.capacity(elemLayout.size())`
     */
    public open fun tryShrinkToFit(cap: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        // SAFETY: Upheld by caller.
        return this.shrink(cap, elemLayout)
    }

    public open fun needsToGrow(len: Int, additional: Int, elemLayout: Layout): Boolean = additional > this.capacity(elemLayout.size()).wrappingSub(len)

    public open fun setPtrAndCap(ptr: NonNull, cap: Int) {
        // Allocators currently return a `NonNull<[UByte]>` whose length matches
        // the size requested. If that ever changes, the capacity here should
        // change to `ptr.len() / sizeOf<T>()`.
        this.ptr = ptr.cast()
        // SAFETY: Upheld by caller.
        this.cap = UsizeNoHighBit.newUnchecked(cap)
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - The sum of `len` and `additional` must be greater than the current capacity
     */
    public open fun growExact(len: Int, additional: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        if (elemLayout.size() == 0) {
            // Since we return a capacity of `Int.MAX` when the type size is
            // 0, getting to here necessarily means the `RawVec` is overfull.
            return Result.Err<Unit, TryReserveError>(TryReserveError.from(TryReserveErrorKind.CapacityOverflow))
        }
        val cap =
            len.checkedAdd(additional)
                ?: return Result.Err<Unit, TryReserveError>(TryReserveError.from(TryReserveErrorKind.CapacityOverflow))
        // SAFETY: preconditions passed to caller
        val ptr =
            when (val tryResult = this.finishGrow(cap, elemLayout)) {
                is Result.Ok<*, *> -> tryResult.okValue()
                is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue())
            }
        // SAFETY: `finishGrow` would have failed if `cap> Long.MAX`
        this.setPtrAndCap(ptr, cap)
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * # Safety
     * - `elemLayout` must be valid for `self`, i.e. it must be the same `elemLayout` used to
     *   initially construct `self`
     * - `elemLayout`s size must be a multiple of its alignment
     * - `cap` must be less than or equal to `self.capacity(elemLayout.size())`
     */
    public open fun shrink(cap: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        check(cap <= this.capacity(elemLayout.size())) { "Tried to shrink to a larger capacity" }
        // SAFETY: Just checked this isn't trying to grow
        return this.shrinkUnchecked(cap, elemLayout)
    }

    /**
     * `shrink`, but without the capacity check.
     *
     * This is split out so that `shrink` can inline the check, since it
     * optimizes out in things like `shrinkToFit`, without needing to
     * also inline all this code, as doing that ends up failing the
     * `vec - shrink - panic` codegen test when `shrinkToFit` ends up being too
     * big for LLVM to be willing to inline.
     *
     * # Safety
     * - `cap <= self.capacity()`
     * - `elemLayout` must be valid for `self`.
     */
    public open fun shrinkUnchecked(cap: Int, elemLayout: Layout): Result<Unit, TryReserveError> {
        // SAFETY: Precondition passed to caller
        val (ptr, layout) = (this.currentMemory(elemLayout)) ?: return Result.Ok<Unit, TryReserveError>(Unit)
        // If shrinking to 0, deallocate the buffer. We don't reach this point
        // for the T.IsZst case since currentMemory() will have returned
        // None.
        if (cap == 0) {
            this.disarmCleaner()
            // SAFETY: T isn't a ZST if we're here and `ptr` is our pointer that `currentMemory`
            // ensures was allocated with `layout`.
            this.alloc.deallocate(ptr, layout)
            this.ptr = NonNull.withoutProvenance(elemLayout.alignment().asNonzeroUsize())
            this.cap = ZERO_CAP
        } else {
            // SAFETY: `cap` is less than the previous capacity, which must have fit in an
            // isize already for the non-ZST case. `shrink` is also sound to call since
            // `currentMemory` ensures `ptr` and `layout` are correct for the old allocation,
            // while `newLayout` is computed with a smaller size than the old one per the
            // requirement we instate on our callers.
            val ptr1 =
                run {
                    val newSize = elemLayout.size().uncheckedMul(cap)
                    val newLayout = Layout.fromSizeAlignUnchecked(newSize, layout.align())
                    when (val tryResult = this.alloc.shrink(ptr, layout, newLayout).mapErr { TryReserveError.from(TryReserveErrorKind.AllocError(layout = newLayout)) }) {
                        is Result.Ok<*, *> -> {
                            val shrunkPtr = tryResult.okValue()
                            this.trackAllocation(shrunkPtr.cast(), newSize, layout.align(), true)
                            shrunkPtr
                        }
                        is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue())
                    }
                }
            // SAFETY: if the allocation is valid, then the capacity is too
            run {
                this.setPtrAndCap(ptr1, cap)
            }
        }
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * # Safety
     *
     * This function deallocates the owned allocation, but does not update `ptr` or `cap` to
     * prevent double-free or use-after-free. Essentially, do not do anything with the caller
     * after this function returns.
     * Ideally this function would take `self` by move, but it cannot because it exists to be
     * called from a `Drop` impl.
     */
    public open fun deallocate(elemLayout: Layout) {
        if (!this.allocState.owned) {
            return
        }
        // SAFETY: Caller ensures `elemLayout` is correct for `self`.
        when (val matchVal = this.currentMemory(elemLayout)) {
            null -> this.disarmCleaner()
            else -> {
                val ptr = matchVal.first
                val layout = matchVal.second
                this.allocState.ptr = ptr
                this.allocState.byteCap = layout.size()
                this.allocState.alignBytes = layout.align()
                this.cleaner.cleanNow()
            }
        }
    }

    public companion object {
        public operator fun <A : Allocator> invoke(
            ptr: NonNull,
            cap: Cap,
            alloc: A,
        ): RawVecInner<A> =
            RawVecInner<A>(
                ptrCtorArg = ptr,
                cap = cap,
                alloc = alloc,
            )

        public fun <A : Allocator> withCapacityIn(capacity: Int, alloc: A, elemLayout: Layout): RawVecInner<A> =
            run {
                val matchVal = RawVecInner.tryAllocateIn(capacity, AllocInit.Uninitialized, alloc, elemLayout)
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val thisRef = matchVal.okValue()
                        // SAFETY: We already allocated at least `capacity`.
                        run {
                            // Make it more obvious that a subsequent Vec.reserve(capacity) will not allocate.
                            assertUnchecked(!thisRef.needsToGrow(0, capacity, elemLayout))
                        }
                        thisRef
                    }
                    is Result.Err<*, *> -> {
                        val err = matchVal.errValue()
                        handleError(err)
                    }
                }
            }

        public fun <A : Allocator> tryAllocateIn(capacity: Int, init: AllocInit, alloc: A, elemLayout: Layout): Result<RawVecInner<A>, TryReserveError> {
            // We avoid `unwrapOrElse` here because it bloats the amount of
            // LLVM IR generated.
            val layout1 =
                run {
                    val matchVal = layoutArray(capacity, elemLayout)
                    when (matchVal) {
                        is Result.Ok<*, *> -> {
                            val layout = matchVal.okValue()
                            layout
                        }
                        is Result.Err<*, *> -> {
                            return Result.Err<RawVecInner<A>, TryReserveError>(TryReserveError.from(TryReserveErrorKind.CapacityOverflow))
                        }
                    }
                }
            // Don't allocate here because `Drop` will not deallocate when `capacity` is 0.
            if (layout1.size() == 0) {
                return Result.Ok<RawVecInner<A>, TryReserveError>(RawVecInner.newIn(alloc, elemLayout.alignment()))
            }
            val result =
                when (init) {
                    AllocInit.Uninitialized -> alloc.allocate(layout1)
                    AllocInit.Zeroed -> alloc.allocateZeroed(layout1)
                }
            val ptr1 =
                when (result) {
                    is Result.Ok<*, *> -> {
                        val ptr = result.okValue()
                        ptr
                    }
                    is Result.Err<*, *> -> {
                        return Result.Err<RawVecInner<A>, TryReserveError>(TryReserveError.from(TryReserveErrorKind.AllocError(layout = layout1)))
                    }
                }
            // Allocators currently return a `NonNull<[UByte]>` whose length
            // matches the size requested. If that ever changes, the capacity
            // here should change to `ptr.len() / sizeOf<T>()`.
            val res = RawVecInner(ptr = ptr1.cast(), cap = UsizeNoHighBit.newUnchecked(capacity), alloc = alloc)
            res.trackAllocation(ptr1.cast(), layout1.size(), layout1.align(), true)
            return Result.Ok<RawVecInner<A>, TryReserveError>(res)
        }

        public fun <A : Allocator> newIn(alloc: A, align: Alignment): RawVecInner<A> {
            val ptr = NonNull.withoutProvenance(align.asNonzeroUsize())
            // `cap: 0` means "unallocated". zero-sized types are ignored.
            return RawVecInner(ptr = ptr, cap = ZERO_CAP, alloc = alloc)
        }

        public fun <A : Allocator> tryWithCapacityIn(capacity: Int, alloc: A, elemLayout: Layout): Result<RawVecInner<A>, TryReserveError> = RawVecInner.tryAllocateIn(capacity, AllocInit.Uninitialized, alloc, elemLayout)

        public fun <A : Allocator> withCapacityZeroedIn(capacity: Int, alloc: A, elemLayout: Layout): RawVecInner<A> =
            run {
                val matchVal = RawVecInner.tryAllocateIn(capacity, AllocInit.Zeroed, alloc, elemLayout)
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val res = matchVal.okValue()
                        res
                    }
                    is Result.Err<*, *> -> {
                        val err = matchVal.errValue()
                        handleError(err)
                    }
                }
            }

        public fun <A : Allocator> fromRawPartsIn(ptr: MutPtr, cap: Cap, alloc: A): RawVecInner<A> {
            // SAFETY: Upheld by caller.
            return RawVecInner(ptr = NonNull.newUnchecked(ptr), cap = cap, alloc = alloc)
        }

        public fun <A : Allocator> fromNonnullIn(ptr: NonNull, cap: Cap, alloc: A): RawVecInner<A> = RawVecInner(ptr = ptr, cap = cap, alloc = alloc)
    }
}

public fun RawVecInner.Companion.withCapacity(capacity: Int, elemLayout: Layout): RawVecInner<Global> =
    run {
        val matchVal =
            RawVecInner.tryAllocateIn(
                capacity,
                AllocInit.Uninitialized,
                io.github.kotlinmania.rustlang.alloc.alloc
                    .Global(),
                elemLayout,
            )
        when (matchVal) {
            is Result.Ok<*, *> -> {
                val res = matchVal.okValue()
                res
            }
            is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                handleError(err)
            }
        }
    }
