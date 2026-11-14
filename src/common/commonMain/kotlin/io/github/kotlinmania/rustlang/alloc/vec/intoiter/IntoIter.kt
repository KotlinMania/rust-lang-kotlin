// port-lint: source vec/into_iter.rs
package io.github.kotlinmania.rustlang.alloc.vec.intoiter

import io.github.kotlinmania.rustlang.alloc.collections.btree.node.B
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

public typealias Source = Any

/**
 * An iterator that moves out of a vector.
 *
 * This `class` is created by the `intoIter` method on [`Vec`](Vec)
 * (provided by the [IntoIterator] trait).
 *
 * # Example
 *
 * ```kotlin
 * val v = mutableListOf(0, 1, 2)
 * val iter: IntoIter = ArrayDeque(v)
 * ```
 */
public open class IntoIter<T, A : Allocator>(
    internal var buf: NonNull,
    internal var phantom: Unit,
    internal var cap: Int,
    // the drop impl reconstructs a RawVec from buf, cap and alloc
    // to avoid dropping the allocator twice we need to wrap it into ManuallyDrop
    internal var alloc: A,
    internal var ptr: NonNull,
    /**
     * If T is a ZST, this is actually ptr+len. This encoding is picked so that
     * ptr == end is a quick test for the Iterator being empty, that works
     * for both ZST and non-ZST.
     * For non-ZSTs the pointer is treated as `NonNull<T>`
     */
    internal var end: ConstPtr,
) : AsRef<List<T>>, TrustedFused, TrustedLen, TrustedRandomAccessNoCoerce, InPlaceIterable, SourceIter, Iterable<T> {
    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Manually mirroring what `List` has,
    // because otherwise we get `T: RefUnwindSafe` from `NonNull`.

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "IntoIter").field(this.asSlice()).finish()
    }

    /**
     * Returns the remaining items of this iterator as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * val vec = mutableListOf('a', 'b', 'c')
     * var intoIter = ArrayDeque(vec)
     * check(intoIter.asSlice() == listOf('a', 'b', 'c'))
     * intoIter.removeFirstOrNull()!!
     * check(intoIter.asSlice() == listOf('b', 'c'))
     * ```
     */
    public open fun asSlice(): List<T> {
        // ignore-tidy-undocumented-unchecked
        return rawSliceList(this.ptr.asPtr(), this.len().toInt(), PrimitiveLayouts.U8)
    }

    /**
     * Returns the remaining items of this iterator as a mutable slice.
     *
     * # Examples
     *
     * ```kotlin
     * val vec = mutableListOf('a', 'b', 'c')
     * var intoIter = ArrayDeque(vec)
     * check(intoIter.asSlice() == listOf('a', 'b', 'c'))
     * intoIter.asMutSlice()[2] = 'z'
     * check(intoIter.removeFirstOrNull()!! == 'a')
     * check(intoIter.removeFirstOrNull()!! == 'b')
     * check(intoIter.removeFirstOrNull()!! == 'z')
     * ```
     */
    public open fun asMutSlice(): MutableList<T> {
        // ignore-tidy-undocumented-unchecked
        return this.asRawMutSlice()
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.alloc

    public open fun asRawMutSlice(): MutPtr = this.ptr.asPtr().castSlice(this.len())

    /**
     * Drops remaining elements and relinquishes the backing allocation.
     *
     * This method guarantees it won't panic before relinquishing the backing
     * allocation.
     *
     * This is roughly equivalent to the following, but more efficient
     *
     * ```kotlin
     * var intoIter = replace(intoIter, ArrayDeque((mutableListOf()).toList()))
     * (intoIter).forEach(drop)
     * forget(intoIter)
     * ```
     *
     * This method is used by in-place iteration, refer to the vec.inPlaceCollect
     * documentation for an overview.
     */
    internal open fun forgetAllocationDropRemaining() {
        val remaining = this.asRawMutSlice()
        // overwrite the individual fields instead of creating a new
        // struct and then overwriting self.
        // this creates less assembly
        this.cap = 0
        this.buf = RawVec.new().nonNull()
        this.ptr = this.buf
        this.end = this.buf.asPtr()
        // Dropping the remaining elements can panic, so this needs to be
        // done only after updating the other fields.
        // ignore-tidy-undocumented-unchecked
        run {
            ptr.dropInPlace(remaining)
        }
    }

    /**
     * Forgets to Drop the remaining elements while still allowing the backing allocation to be freed.
     *
     * This method does not consume `self`, and leaves deallocation to `implementation Drop for IntoIter`.
     * If consuming `self` is possible, consider calling
     * [Self.forgetRemainingElementsAndDealloc] instead.
     */
    internal open fun forgetRemainingElements() {
        // For the ZST case, it is crucial that we mutate `end` here, not `ptr`.
        // `ptr` must stay aligned, while `end` may be unaligned.
        this.end = this.ptr.asPtr()
    }

    /**
     * Forgets to Drop the remaining elements and frees the backing allocation.
     * Consuming version of [Self.forgetRemainingElements].
     *
     * This can be used in place of `drop(self)` when `self` is known to be exhausted,
     * to avoid producing a needless `dropInPlace<[T]>()`.
     */
    internal open fun forgetRemainingElementsAndDealloc() {
        val thisRef = this
        // SAFETY: `this` is in ManuallyDrop, so it will not be double-freed.
        run {
            thisRef.deallocOnly()
        }
    }

    /**
     * Frees the allocation, without checking or dropping anything else.
     *
     * The safe version of this method is [Self.forgetRemainingElementsAndDealloc].
     * This function exists only to share code between that method and the `implementation Drop`.
     *
     * # Safety
     *
     * This function must only be called with an [IntoIter] that is not going to be dropped
     * or otherwise used in any way, either because it is being forgotten or because its `Drop`
     * is already executing; otherwise a double-free will occur, and possibly a read from freed
     * memory if there are any remaining elements.
     */
    public open fun deallocOnly() {
        // SAFETY: our caller promises not to touch `* self` again.
        val alloc = ManuallyDrop.take(this.alloc)
        // SAFETY: We're using this to deallocate a preexisting `RawVec`.
        RawVec.fromNonnullIn(this.buf, this.cap, alloc)
    }

    internal open fun intoVecdeque(): VecDeque<T, A> {
        // Keep our `Drop` impl from dropping the elements and the allocator
        val thisRef = this
        val buf = thisRef.buf.asPtr()
        val initialized =
            if (T.IS_ZST || thisRef.len() == 0) {
                // All the pointers are the same for ZSTs, so it's fine to
                // say that they're all at the beginning of the "allocation".
                // For non-ZSTs, we have length 0, so we can choose the (empty)
                // range to be at the start of the buffer.
                //
                // Due to `0` ≤ `this.len()` ≤ `this.cap`, the range is well-formed,
                // and due to the argument above it spans exactly the elements of
                // this iterator. Because `init.start` = `0`, it follows that either
                // `init.start` < `cap` or `cap` = `init.start` = `0`; thus the range
                // satisfies the requirements of `fromContiguousRawPartsIn`.
                0 until thisRef.len()
            } else {
                // SAFETY: `this.ptr` and `this.end` are created via offsets of `this.buf`,
                // so they point to the same allocation. We have `this.buf` ≤ `this.ptr` ≤ `this.end`,
                // so this cannot wrap, and will produce a well-formed range that spans exactly
                // the elements of this iterator.
                //
                // Additionally, due to `end ≤ buf + cap`, we have `init.start` ≤ `init.end` ≤ `cap`.
                // Due to the length check above, `init.start<cap`, so the range satisfies the
                // requirements of `fromContiguousRawPartsIn`.
                thisRef.ptr.offsetFromUnsigned(thisRef.buf) until thisRef.end.offsetFromUnsigned(buf)
            }
        val cap = thisRef.cap
        // SAFETY: `this` is forgotten afterwards, so we can move out the allocator.
        val alloc = ManuallyDrop.take(thisRef.alloc)
        // SAFETY: This allocation originally came from a `List`, so it satisfies all
        // requirements for the `buf` pointer with capacity `cap` allocated in `alloc`.
        // Correctness of `initialized` was shown above.
        return VecDeque.fromContiguousRawPartsIn(buf, initialized, cap, alloc)
    }

    public open fun asRef(): List<T> = this.asSlice()

    public open fun next(typeLayoutT: TypeLayout<T>): T? {
        val ptr =
            if (T.IS_ZST) {
                if (((this.ptr.asPtr()).rawAddress.toULong() == ((this.end).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong())) {
                    return null
                }
                // `ptr` has to stay where it is to remain aligned, so we reduce the length by 1 by
                // reducing the `end`.
                this.end = ConstPtr((this.end).rawAddress - ((1).toLong()))
                this.ptr
            } else {
                if (this.ptr == (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)) {
                    return null
                }
                val old = this.ptr
                // ignore-tidy-undocumented-unchecked
                this.ptr = old.add(1)
                old
            }
        // ignore-tidy-undocumented-unchecked
        return ptr.read()
    }

    public open fun sizeHint(typeLayoutT: TypeLayout<T>): Pair<Int, Int?> {
        val exact =
            if (T.IS_ZST) {
                (this.end).rawAddress.toInt().wrappingSub(this.ptr.asPtr().addr())
            } else {
                // ignore-tidy-undocumented-unchecked
                (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress).offsetFromUnsigned(this.ptr)
            }
        return Pair(exact, exact)
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int>> {
        val stepSize = this.len().min(n)
        val toDrop = this.ptr.asPtr().castSlice(stepSize)
        if (T.IS_ZST) {
            // See `next` for why we sub `end` here.
            this.end = ConstPtr((this.end).rawAddress - ((stepSize).toLong()))
        } else {
            // SAFETY: the min() above ensures that stepSize is in bounds
            this.ptr = this.ptr.add(stepSize)
        }
        // SAFETY: the min() above ensures that stepSize is in bounds
        run {
            ptr.dropInPlace(toDrop)
        }
        return NonZero.new(n - stepSize).mapOr(Result.Ok(Unit), { Result.Err(it) })
    }

    public open fun count(): Int = this.len()

    public open fun last(): T? = this.nextBack()

    public open fun nextChunk(n: Int): Result<List<T>, array.IntoIter<T>> {
        val rawAry = MutableList(n) { MaybeUninit.uninit(ObjectSlotLayout<Result<List<T>, array.IntoIter<T>>>()) }
        val len = this.len()
        if (T.IS_ZST) {
            if (len < n) {
                this.forgetRemainingElements()
                // SAFETY: ZSTs can be conjured ex nihilo, only the amount has to be correct
                return Result.Err(array.IntoIter.newUnchecked(rawAry, 0 until len))
            }
            this.end = ConstPtr((this.end).rawAddress - ((n).toLong()))
            // SAFETY: ditto
            return Result.Ok(rawAry.transpose().assumeInit())
        }
        if (len < n) {
            // SAFETY: `len` indicates that this many elements are available and we
            // just checked that it fits into the array.
            run {
                ptr.copyNonoverlapping(this.ptr.asPtr(), mutPtrFromRustCast(rawAry.asMutPtr()), len)
                this.forgetRemainingElements()
                return Result.Err(array.IntoIter.newUnchecked(rawAry, 0 until len))
            }
        }
        // SAFETY: `len` is larger than the array size. Copy a fixed amount here to fully initialize
        // the array.
        return run {
            ptr.copyNonoverlapping(this.ptr.asPtr(), mutPtrFromRustCast(rawAry.asMutPtr()), n)
            this.ptr = this.ptr.add(n)
            Result.Ok(rawAry.transpose().assumeInit())
        }
    }

    public open fun <B> fold(accum: B, f: (B, T) -> B, typeLayoutT: TypeLayout<T>): B {
        var accum1 = accum
        if (T.IS_ZST) {
            while (((this.ptr.asPtr()).rawAddress.toULong() != (MutPtr((this.end).rawAddress)).rawAddress.toULong())) {
                // SAFETY: we just checked that `self.ptr` is in bounds.
                val tmp = this.ptr.read()
                // See `next` for why we subtract from `end` here.
                this.end = ConstPtr((this.end).rawAddress - ((1).toLong()))
                accum1 = f(accum1, tmp)
            }
        } else {
            // SAFETY: `self.end` can only be null if `T` is a ZST.
            while (this.ptr != (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)) {
                // SAFETY: we just checked that `self.ptr` is in bounds.
                val tmp1 = this.ptr.read()
                // SAFETY: the maximum this can be is `self.end`.
                // Increment `self.ptr` first to avoid double dropping in the event of a panic.
                this.ptr = this.ptr.add(1)
                accum1 = f(accum1, tmp1)
            }
        }
        // There are in fact no remaining elements to forget, but by doing this we can avoid
        // potentially generating a needless loop to drop the elements that cannot exist at
        // this point.
        this.forgetRemainingElementsAndDealloc()
        return accum1
    }

    public open fun <B, R> tryFold(accum: B, f: (B, T) -> R, typeLayoutT: TypeLayout<T>, fromOutputRFn: (B) -> R): R where R : Try<B> {
        var accum1 = accum
        if (T.IS_ZST) {
            while (((this.ptr.asPtr()).rawAddress.toULong() != (MutPtr((this.end).rawAddress)).rawAddress.toULong())) {
                // SAFETY: we just checked that `self.ptr` is in bounds.
                val tmp = this.ptr.read()
                // See `next` for why we subtract from `end` here.
                this.end = ConstPtr((this.end).rawAddress - ((1).toLong()))
                accum1 = when (val tryResult = f(accum1, tmp)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
        } else {
            // SAFETY: `self.end` can only be null if `T` is a ZST.
            while (this.ptr != (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)) {
                // SAFETY: we just checked that `self.ptr` is in bounds.
                val tmp1 = this.ptr.read()
                // SAFETY: the maximum this can be is `self.end`.
                // Increment `self.ptr` first to avoid double dropping in the event of a panic.
                this.ptr = this.ptr.add(1)
                accum1 = when (val tryResult = f(accum1, tmp1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
        }
        return fromOutputRFn(accum1)
    }

    public open fun iteratorGetUnchecked(i: Int): T {
        // SAFETY: the caller must guarantee that `i` is in bounds of the
        // `List<T>`, so `i` cannot overflow an `Long`, and the `self.ptr.add(i)`
        // is guaranteed to pointer to an element of the `List<T>` and
        // thus guaranteed to be valid to dereference.
        //
        // Also note the implementation of `Self: TrustedRandomAccess` requires
        // that `T: Copy` so reading elements from the buffer doesn't invalidate
        // them for `Drop`.
        return this.ptr.add(i).read()
    }

    public open fun nextBack(typeLayoutT: TypeLayout<T>): T? {
        return if (T.IS_ZST) {
            if (((this.ptr.asPtr()).rawAddress.toULong() == ((this.end).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong())) {
                return null
            }
            // See above for why ptr.offset' isn't used
            this.end = ConstPtr((this.end).rawAddress - ((1).toLong()))
            // Note that even though this is nextBack() we're reading from `self.ptr`, not
            // `self.end`. We track our length using the byte offset from `self.ptr` to `self.end`,
            // so the end pointer may not be suitably aligned for T.
            // ignore-tidy-undocumented-unchecked
            ptr.read(this.ptr.asPtr())
        } else {
            if (this.ptr == (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)) {
                return null
            }
            // ignore-tidy-undocumented-unchecked
            run {
                this.end = ConstPtr((this.end).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
                ptr.read(this.end)
            }
        }
    }

    public open fun nextChunkBack(n: Int): Result<List<T>, array.IntoIter<T>> {
        val rawAry = MutableList(n) { MaybeUninit.uninit(ObjectSlotLayout<Result<List<T>, array.IntoIter<T>>>()) }
        val len = this.len()
        if (T.IS_ZST) {
            if (len < n) {
                this.forgetRemainingElements()
                // SAFETY: ZSTs can be conjured ex nihilo, only the amount has to be correct
                return Result.Err(array.IntoIter.newUnchecked(rawAry, n - len until n))
            }
            this.end = ConstPtr((this.end).rawAddress - ((n).toLong()))
            // SAFETY: ditto
            return Result.Ok(MaybeUninit.arrayAssumeInit(rawAry))
        }
        if (len < n) {
            // SAFETY: `len` indicates that this many elements are available
            // and we just checked that it fits into the array.
            run {
                ptr.copyNonoverlapping(this.ptr.asPtr(), mutPtrFromRustCast(rawAry.asMutPtr()), len)
                this.forgetRemainingElements()
                return Result.Err(array.IntoIter.newUnchecked(rawAry, 0 until len))
            }
        }
        // SAFETY: `len` is larger than the array size. Copy a fixed amount here to fully initialize
        // the array.
        return run {
            ptr.copyNonoverlapping(this.ptr.add(len - n).asPtr(), mutPtrFromRustCast(rawAry.asMutPtr()), n)
            this.end = ConstPtr((this.end).rawAddress - ((n).toLong() * (ObjectSlotLayout<T>()).strideBytes))
            Result.Ok(MaybeUninit.arrayAssumeInit(rawAry))
        }
    }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> {
        val stepSize = this.len().min(n)
        if (T.IS_ZST) {
            // SAFETY: same as for advanceBy()
            this.end = ConstPtr((this.end).rawAddress - ((stepSize).toLong()))
        } else {
            // SAFETY: same as for advanceBy()
            this.end = ConstPtr((this.end).rawAddress - ((stepSize).toLong() * (ObjectSlotLayout<T>()).strideBytes))
        }
        val toDrop =
            if (T.IS_ZST) {
                // ZST may cause unalignment
                ptr.NonNull.dangling().asPtr().castSlice(stepSize)
            } else {
                MutPtr((this.end).rawAddress).castSlice(stepSize)
            }
        // SAFETY: same as for advanceBy()
        run {
            ptr.dropInPlace(toDrop)
        }
        return NonZero.new(n - stepSize).mapOr(Result.Ok(Unit), { Result.Err(it) })
    }

    public open fun isEmpty(typeLayoutT: TypeLayout<T>): Boolean =
        if (T.IS_ZST) {
            ((this.ptr.asPtr()).rawAddress.toULong() == ((this.end).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong())
        } else {
            this.ptr == (ObjectSlotLayout<NonNull>()).readNative(((((addrOf(this.end))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }

    public open fun clone(): IntoIter<T, A> = this.asSlice().toVecIn(this.alloc.deref().clone()).intoIter()

    public open fun drop() {
        open class DropGuard<T, A : Allocator>(
            public var value: IntoIter<T, A>,
        ) {
            public var field0: IntoIter<T, A>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                // ignore-tidy-undocumented-unchecked
                run {
                    this.value.deallocOnly()
                }
            }
        }
        val guard = DropGuard(this)
        // destroy the remaining elements
        // ignore-tidy-undocumented-unchecked
        run {
            ptr.dropInPlace(guard.first.asRawMutSlice())
        }
        // now `guard` will be dropped and do the rest
    }

    public open fun asInner(): IntoIter<T, A> = this

    public companion object {
        /**
         * Creates an empty `vec.IntoIter`.
         *
         * ```kotlin
         * val iter: ArrayDeque<UByte> = Default.default()
         * check(iter.size == 0)
         * check(iter.asSlice() == listOf())
         * ```
         */
        public fun <T, A : Allocator> default(defaultAFn: () -> A): IntoIter<T, A> where A : Allocator = Vec.newIn(defaultAFn()).intoIter()

        // TrustedRandomAccess (without NoCoerce) must not be implemented because
        // subtypes/supertypes of `T` might not be `NonDrop`
        public val MAY_HAVE_SIDE_EFFECT: Boolean = false

        // In addition to the SAFETY invariants of the following three unchecked traits
        // also refer to the vec.inPlaceCollect module documentation to get an overview
        public val EXPAND_BY: NonZero<Int>? = NonZero.new(1)

        public val MERGE_BY: NonZero<Int>? = NonZero.new(1)
    }
}

public interface NonDrop

// T: Copy as approximation for !Drop since getUnchecked does not advance self.ptr
// and thus we can't implement drop-handling

public fun <T> IntoIter<T>.asIntoIter(): IntoIter<T> = this
