// port-lint: source vec/in_place_collect.rs
package io.github.kotlinmania.rustlang.alloc.vec.inplacecollect

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.inplacedrop.InPlaceDrop
import io.github.kotlinmania.rustlang.alloc.vec.inplacedrop.InPlaceDstDataSrcBufDrop
import io.github.kotlinmania.rustlang.alloc.vec.intoiter.IntoIter
import io.github.kotlinmania.rustlang.alloc.vec.specfromiternested.SpecFromIterNested
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr

// Inplace iterate-and-collect specialization for `List`
//
// Note: This documents Vec internals, some of the following sections explain implementation
// details and are best read together with the source of this module.
//
// The specialization in this module applies to iterators in the shape of
// `source.adapter().adapter().adapter().collect<List<U>>()`
// where `source` is an owning iterator obtained from [Vec.T], [`Box`][box] (by conversion to `List`)
// or [BinaryHeap.T], the adapters guarantee to consume enough items per step to make room
// for the results (represented by [InPlaceIterable]), provide transitive access to `source`
// (via [SourceIter]) and thus the underlying allocation.
// And finally there are alignment and size constraints to consider, this is currently ensured via
// const eval instead of trait bounds in the specialized [SpecFromIter] implementation.
//
// By extension some other collections which use `collect<List<_>>()` internally in their
// `FromIterator` implementation benefit from this too.
//
// Access to the underlying source goes through a further layer of indirection via the private
// trait [AsVecIntoIter] to hide the implementation detail that other collections may use
// `vec.IntoIter` internally.
//
// In-place iteration depends on the interaction of several unchecked traits, implementation
// details of multiple parts in the iterator pipeline and often requires holistic reasoning
// across multiple structs since iterators are executed cooperatively rather than having
// a central evaluator/visitor struct executing all iterator components.
//
// # Reading from and writing to the same allocation
//
// By its nature collecting in place means that the reader and writer side of the iterator
// use the same allocation. Since `tryFold()` (used in [SpecInPlaceCollect]) takes a
// reference to the iterator for the duration of the iteration that means we can't interleave
// the step of reading a value and getting a reference to write to. Instead raw pointers must be
// used on the reader and writer side.
//
// That writes never clobber a yet-to-be-read items is ensured by the [InPlaceIterable] requirements.
//
// # Layout constraints
//
// When recycling an allocation between different types we must uphold the [Allocator] contract
// which means that the input and output Layouts have to "fit".
//
// To complicate things further `InPlaceIterable` supports splitting or merging items into smaller/
// larger ones to enable (de)aggregation of arrays.
//
// Ultimately each step of the iterator must free up enough *bytes* in the source to make room
// for the next output item.
// If `T` and `U` have the same size no fixup is needed.
// If `T`s size is a multiple of `U`s we can compensate by multiplying the capacity accordingly.
// Otherwise the input capacity (and thus layout) in bytes may not be representable by the output
// `List<U>`. In that case `alloc.shrink()` is used to update the allocation's layout.
//
// Alignments of `T` must be the same or larger than `U`. Since alignments are always a power
// of two _larger_ implies _is a multiple of_.
//
// See `inPlaceCollectible()` for the current conditions.
//
// Additionally this specialization doesn't make sense for ZSTs as there is no reallocation to
// avoid and it would make pointer arithmetic more difficult.
//
// # Drop- and panic-safety
//
// Iteration can panic, requiring dropping the already written parts but also the remainder of
// the source. Iteration can also leave some source items unconsumed which must be dropped.
// All those drops in turn can panic which then must either leak the allocation or abort to avoid
// double-drops.
//
// This is handled by the [InPlaceDrop] guard for sink items (`U`) and by
// [vec.IntoIter.forgetAllocationDropRemaining] for remaining source items (`T`).
//
// If dropping any remaining source item (`T`) panics then [InPlaceDstDataSrcBufDrop] will handle dropping
// the already collected sink items (`U`) and freeing the allocation.
//
// # O(1) collect
//
// The main iteration itself is further specialized when the iterator implements
// [TrustedRandomAccessNoCoerce] to let the optimizer see that it is a counted loop with a single
// [induction variable]. This can turn some iterators into a noop, i.e. it reduces them from O(n) to
// O(1). This particular optimization is quite fickle and doesn't always work, see [#79308]
//
// [#79308]: https://github.com/rust-lang/rust/issues/79308
// [induction variable]: https://en.wikipedia.org/wiki/InductionVariable
//
// Since unchecked accesses through that trait do not advance the read pointer of `IntoIter`
// this would interact unsoundly with the requirements about dropping the tail described above.
// But since the normal `Drop` implementation of `IntoIter` would suffer from the same problem it
// is only correct for `TrustedRandomAccessNoCoerce` to be implemented when the items don't
// have a destructor. Thus that implicit requirement also makes the specialization safe to use for
// in-place collection.
// Note that this safety concern is about the correctness of `implementation Drop for IntoIter`,
// not the guarantees of `InPlaceIterable`.
//
// # Adapter implementations
//
// The invariants for adapters are documented in [SourceIter] and [InPlaceIterable], but
// getting them right can be rather subtle for multiple, sometimes non-local reasons.
// For example `InPlaceIterable` would be valid to implement for [Peekable], except
// that it is stateful, cloneable and `IntoIter`s clone implementation shortens the underlying
// allocation which means if the iterator has been peeked and then gets cloned there no longer is
// enough room, thus breaking an invariant ([#85322]).
//
// [#85322]: https://github.com/rust-lang/rust/issues/85322
//
// # Examples
//
// Some cases that are optimized by this specialization, more can be found in the `List`
// benchmarks:
//
// ```kotlin
// / **
//  * Converts a Int vec into an isize one.
//  * /
// fun cast(vec: List<Int>): List<Int> {
//     // Does not allocate, free or panic. On optlevel>=2 it does not loop.
//     // Of course this particular case could and should be written with `intoRawParts` and
//     // `fromRawParts` instead.
//     return vec.map({ u -> u.toInt() }).toMutableList()
// }
// ```
//
// ```kotlin
// / **
//  * Drops remaining items in `src` and if the layouts of `T` and `U` match it
//  * returns an empty Vec backed by the original allocation. Otherwise it returns a new
//  * empty vec.
//  * /
// fun <T, U> recycleAllocation(src: List<T>): List<U> {
//     return src.mapNotNull({ null }).toMutableList()
// }
// ```
//
// ```kotlin
// val vec = mutableListOf(13, 1024)
// (ArrayDeque(vec).mapIndexed { index, item -> Pair(index, item) }).mapNotNull({ (idx, value) -> if (idx % 2 == 0) {
//     value + idx
// } else {
//     null
// } }).collect<Vec>()
// // is equivalent to the following, but doesn't require bounds checks
// var vec1 = mutableListOf(13, 1024)
// var writeIdx = 0
// for (idx in 0 until vec1.size) {
//     if (idx % 2 == 0) {
//         vec1[writeIdx] = vec1[idx] + idx
//         writeIdx += 1
//     }
// }
// vec1.truncate(writeIdx)
// ```

public typealias Src = Item

public fun <Dest, Src> inPlaceCollectible(stepMerge: NonZero<Int, NonZeroUsizeInner>?, stepExpand: NonZero<Int, NonZeroUsizeInner>?, typeLayoutSrc: TypeLayout<Src> = UnresolvedTypeLayout(), typeLayoutDest: TypeLayout<Dest> = UnresolvedTypeLayout()): Boolean {
    // Require matching alignments because an alignment-changing realloc is inefficient on many
    // system allocators and better implementations would require the unstable Allocator trait.
    if (((typeLayoutSrc).sizeBytes == 0L) || ((typeLayoutDest).sizeBytes == 0L) || alignOf<Src>(typeLayoutT = typeLayoutSrc) != alignOf<Dest>(typeLayoutT = typeLayoutDest)) {
        return false
    }
    return run {
        val matchVal = Pair(stepMerge, stepExpand)
        when {
            matchVal.first != null && matchVal.second != null -> {
                val stepMerge1 = matchVal.first
                val stepExpand1 = matchVal.second
                // At least N merged source items -> at most M expanded destination items
                // e.g.
                // - 1 x [u8; 4] -> 4x u8, via flatten
                // - 4 x u8 -> 1x [u8; 4], via arrayChunks
                sizeOf<Src>(typeLayoutT = typeLayoutSrc) * stepMerge1.get() >= sizeOf<Dest>(typeLayoutT = typeLayoutDest) * stepExpand1.get()
            }
            // Fall back to other fromIter impls if an overflow occurred in the step merge/expansion
            // tracking.
            else -> false
        }
    }
}

public fun <Src, Dest> needsRealloc(srcCap: Int, dstCap: Int, typeLayoutSrc: TypeLayout<Src> = UnresolvedTypeLayout(), typeLayoutDest: TypeLayout<Dest> = UnresolvedTypeLayout()): Boolean {
    if (alignOf<Src>(typeLayoutT = typeLayoutSrc) != alignOf<Dest>(typeLayoutT = typeLayoutDest)) {
        // Note(const-Note): use unreachable once that works in const
        error("in_place_collectible() prevents this")
    }
    // If src type size is an integer multiple of the destination type size then
    // the caller will have calculated a `dstCap` that is an integer multiple of
    // `srcCap` without remainder.
    if (run {
        val srcSz = sizeOf<Src>(typeLayoutT = typeLayoutSrc)
        val destSz = sizeOf<Dest>(typeLayoutT = typeLayoutDest)
        destSz != 0 && srcSz % destSz == 0
    }) {
        return false
    }
    // type layouts don't guarantee a fit, so do a runtime check to see if
    // the allocations happen to match
    return srcCap > 0 && srcCap * sizeOf<Src>(typeLayoutT = typeLayoutSrc) != dstCap * sizeOf<Dest>(typeLayoutT = typeLayoutDest)
}

/**
 * This provides a shorthand for the source type since local type aliases aren't a thing.
 */
public interface InPlaceCollect<out Src> : SourceIter, InPlaceIterable

public fun <Src, Item, T, I> specFromIterFromIterVecT(iterator: I, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> where I : Iterator<T>, I : InPlaceCollect<Src> {
    // Select the implementation in const eval to avoid codegen of the dead branch to improve compile times.
    val fn: (I) -> List<T> =
        if (inPlaceCollectible<T, Src>(I.MERGE_BY, I.EXPAND_BY, typeLayoutSrc = UnresolvedTypeLayout<Src>(), typeLayoutDest = typeLayoutT)) {
            ::fromIterInPlace
        } else {
            // fallback
            SpecFromIterNested::fromIter
        }
    return fn(iterator)
}

public fun <Src, Item, I, T> fromIterInPlace(iterator: I, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> where I : Iterator<T>, I : InPlaceCollect<Src> {
    // ignore-tidy-undocumented-unchecked
    var (srcBuf, srcPtr, srcCap, dstBuf, dstEnd, dstCap) =
        run {
            val inner = iterator.asInner().asIntoIter()
            listOf(inner.buf, inner.ptr, inner.cap, inner.buf.cast(), constPtrFromRustCast(inner.end), inner.cap.uncheckedMul(sizeOf<Src>(typeLayoutT = UnresolvedTypeLayout<Src>())) / sizeOf<T>(typeLayoutT = typeLayoutT))
        }
    // SAFETY: `dstBuf` and `dstEnd` are the start and end of the buffer.
    val len = iterator.collectInPlace(mutPtrFromRustCast(dstBuf.asPtr()), dstEnd)
    // ignore-tidy-undocumented-unchecked
    val src = iterator.asInner().asIntoIter()
    // check if SourceIter contract was upheld
    // caveat: if they weren't we might not even make it to this point
    check(srcBuf == src.buf)
    // check InPlaceIterable contract. This is only possible if the iterator advanced the
    // source pointer at all. If it uses unchecked access via TrustedRandomAccess
    // then the source pointer will stay in its initial position and we can't use it as reference
    if (src.ptr != srcPtr) {
        check(dstBuf.add(len).cast() <= src.ptr) { "InPlaceIterable contract violation, write pointer advanced beyond read pointer" }
    }
    // The ownership of the source allocation and the new `T` values is temporarily moved into `dstGuard`.
    // This is safe because
    // * `forgetAllocationDropRemaining` immediately forgets the allocation
    // before any panic can occur in order to avoid any double free, and then proceeds to drop
    // any remaining values at the tail of the source.
    // * the shrink either panics without invalidating the allocation, aborts or
    // succeeds. In the last case we disarm the guard.
    //
    // Note: This access to the source wouldn't be allowed by the TrustedRandomIteratorNoCoerce
    // contract (used by SpecInPlaceCollect below). But see the "O(1) collect" section in the
    // module documentation why this is ok anyway.
    val dstGuard = InPlaceDstDataSrcBufDrop(ptr = dstBuf, len = len, srcCap = srcCap, src = Unit)
    src.forgetAllocationDropRemaining()
    // Adjust the allocation if the source had a capacity in bytes that wasn't a multiple
    // of the destination type size.
    // Since the discrepancy should generally be small this should only result in some
    // bookkeeping updates and no memmove.
    if (needsRealloc<Src, T>(srcCap, dstCap, typeLayoutSrc = UnresolvedTypeLayout<Src>(), typeLayoutDest = typeLayoutT)) {
        val alloc = Global()
        check(srcCap != 0)
        check(dstCap != 0)
        // ignore-tidy-undocumented-unchecked
        run {
            // The old allocation exists, therefore it must have a valid layout.
            val srcAlign = alignOf<Src>(typeLayoutT = UnresolvedTypeLayout<Src>())
            val srcSize = sizeOf<Src>(typeLayoutT = UnresolvedTypeLayout<Src>()).uncheckedMul(srcCap)
            val oldLayout = Layout.fromSizeAlignUnchecked(srcSize, srcAlign)
            // The allocation must be equal or smaller for in-place iteration to be possible
            // therefore the new layout must be ≤ the old one and therefore valid.
            val dstAlign = alignOf<T>(typeLayoutT = typeLayoutT)
            val dstSize = sizeOf<T>(typeLayoutT = typeLayoutT).uncheckedMul(dstCap)
            val newLayout = Layout.fromSizeAlignUnchecked(dstSize, dstAlign)
            val result = alloc.shrink(dstBuf.cast(), oldLayout, newLayout)
            val reallocated = when (val tryResult = result) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> handleAllocError(newLayout) }
            dstBuf = reallocated.cast()
        }
    } else {
        check(srcCap * sizeOf<Src>(typeLayoutT = UnresolvedTypeLayout<Src>()) == dstCap * sizeOf<T>(typeLayoutT = typeLayoutT))
    }
    forget(dstGuard)
    // ignore-tidy-undocumented-unchecked
    return Vec.fromParts(dstBuf, len, dstCap)
}

public fun <T> writeInPlaceWithDrop(srcEnd: ConstPtr): (InPlaceDrop<T>, T) -> Result<InPlaceDrop<T>, Nothing> =
    { sink, item ->
        // the InPlaceIterable contract cannot be verified precisely here since
        // tryFold has an exclusive reference to the source pointer
        // all we can do is check if it's still in range
        check(((constPtrFromRustCast(sink.dst)).rawAddress.toULong() <= (srcEnd).rawAddress.toULong())) { "InPlaceIterable contract violation" }
        // ignore-tidy-undocumented-unchecked
        run {
            write(sink.dst, item)
            // Since this executes user code which can panic we have to bump the pointer
            // after each step.
            sink.dst = sink.dst.add(1)
        }
        Result.Ok(sink)
    }

/**
 * Helper trait to hold specialized implementations of the in-place iterate-collect loop
 */
public interface SpecInPlaceCollect<T, I> : Iterator<T> {
    /**
     * Collects an iterator (`self`) into the destination buffer (`dst`) and returns the number of items
     * collected. `end` is the last writable element of the allocation and used for bounds checks.
     *
     * This method is specialized and one of its implementations makes use of
     * `Iterator.__iterator_get_unchecked` calls with a `TrustedRandomAccessNoCoerce` bound
     * on `I` which means the caller of this method must take the safety conditions
     * of that trait into consideration.
     */
    public fun collectInPlace(dst: MutPtr, end: ConstPtr): Int

    public fun next(): T?
}

public fun <T, I> I.collectInPlace(dstBuf: MutPtr, end: ConstPtr): Int where I : Iterator<T> {
    // use try-fold since
    // - it vectorizes better for some iterator adapters
    // - unlike most internal iteration methods, it only takes a self
    // - it lets us thread the write pointer through its innards and get it back in the end
    val sink = InPlaceDrop(inner = dstBuf, dst = dstBuf)
    val sink1 = this.tryFold<Result<Nothing>>(sink, writeInPlaceWithDrop(end)).intoOk()
    // iteration succeeded, don't drop head
    // ignore-tidy-undocumented-unchecked
    return sink1.dst.offsetFromUnsigned(dstBuf)
}

public fun <T, I> I.collectInPlace(dstBuf: MutPtr, end: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int where I : Iterator<T>, I : TrustedRandomAccessNoCoerce {
    val len = this.size()
    val dropGuard = InPlaceDrop(inner = dstBuf, dst = dstBuf)
    for (i in 0 until len) {
        // SAFETY: InplaceIterable contract guarantees that for every element we read
        // one slot in the underlying storage will have been freed up and we can immediately
        // write back the result.
        run {
            val dst = MutPtr((dstBuf).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes))
            check(((ConstPtr(dst.rawAddress, dst.rawMeta)).rawAddress.toULong() <= (end).rawAddress.toULong())) { "InPlaceIterable contract violation" }
            write(dst, this.iteratorGetUnchecked(i), typeLayoutT = typeLayoutT)
            // Since this executes user code which can panic we have to bump the pointer
            // after each step.
            dropGuard.dst = MutPtr((dst).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
        }
    }
    forget(dropGuard)
    return len
}

/**
 * Internal helper trait for in-place iteration specialization.
 *
 * Currently this is only implemented by [vec.IntoIter] - returning a reference to itself - and
 * [binaryHeap.IntoIter] which returns a reference to its inner representation.
 *
 * Since this is an internal trait it hides the implementation detail `binaryHeap.IntoIter`
 * uses `vec.IntoIter` internally.
 *
 * # Safety
 *
 * In-place iteration relies on implementation details of `vec.IntoIter`, most importantly that
 * it does not create references to the whole allocation during iteration, only raw pointers
 */
public interface AsVecIntoIter<Item> {
    public fun asIntoIter(): IntoIter<Item>
}
