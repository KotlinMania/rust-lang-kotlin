package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.drain.Drain
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.extractif.ExtractIf
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.intoiter.IntoIter
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.iter.Iter
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.itermut.IterMut
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.specfromiter.SpecFromIter
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.splice.Splice
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.iter.adapters.byrefsized.ByRefSized
import io.github.kotlinmania.rustlang.core.iter.sources.repeatn.repeatN
import io.github.kotlinmania.rustlang.core.iter.sources.repeatwith.repeatWith
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.sliceFromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * A double-ended queue implemented with a growable ring buffer.
 *
 * The "default" usage of this type as a queue is to use [pushBack] to add to
 * the queue, and [popFront] to remove from the queue. [extend] and [append]
 * push onto the back in this manner, and iterating over `VecDeque` goes front
 * to back.
 *
 * A `VecDeque` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val deq = VecDeque.from(listOf(-1, 0, 1))
 * ```
 *
 * Since `VecDeque` is a ring buffer, its elements are not necessarily contiguous
 * in memory. If you want to access the elements as a single slice, such as for
 * efficient sorting, you can use [makeContiguous]. It rotates the `VecDeque`
 * so that its elements do not wrap, and returns a mutable slice to the
 * now-contiguous element sequence.
 *
 * [pushBack]: VecDeque.pushBack
 * [popFront]: VecDeque.popFront
 * [extend]: VecDeque.extend
 * [append]: VecDeque.append
 * [makeContiguous]: VecDeque.makeContiguous
 */
public open class VecDeque<T, A : Allocator> internal constructor(
    // `self[0]`, if it exists, is `buf[head]`.
    // `head<buf.capacity()`, unless `buf.capacity() == 0` when `head == 0`.
    public var head: WrappedIndex,
    // the number of initialized elements, starting from the one at `head` and potentially wrapping around.
    // if `len == 0`, the exact value of `head` is unimportant.
    // if `T` is zero-Sized, then `self.len <= Int.MAX`, otherwise `self.len <= Long.MAX as Int`.
    public var len: Int,
    bufCtorArg: RawVec<T, A>,
) : SpecExtendFromWithin, Iterable<T> {
    public var buf: RawVec<T, A> = bufCtorArg
        internal set

    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    public open fun clone(): VecDeque<T, A> {
        val deq = VecDeque.withCapacityIn(this.len(), this.allocator().clone())
        deq.extend(this.iter().cloned())
        return deq
    }

    /**
     * Overwrites the contents of `self` with a clone of the contents of `source`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     */
    public open fun cloneFrom(source: VecDeque<T, A>) {
        this.clear()
        this.extend(source.iter().cloned())
    }

    public open fun drop() {
        val (front, back) = this.asMutSlices()
        // ignore-tidy-undocumented-unchecked
        run {
            // use drop for [T]
            dropInPlace(front)
        }
        // RawVec handles deallocation
    }

    /**
     * Marginally more convenient
     */
    public open fun ptr(): MutPtr = this.buf.ptr()

    /**
     * Appends an element to the buffer.
     *
     * # Safety
     *
     * May only be called if `deque.len()<deque.capacity()`
     */
    public open fun pushUnchecked(element: T) {
        // SAFETY: Because of the precondition, it's guaranteed that there is space
        // in the logical array after the last element.
        this.bufferWrite(this.toWrappedIndex(this.len), element)
        // This can't overflow because `deque.len()<deque.capacity() <= Int.MAX`.
        this.len += 1
    }

    /**
     * Prepends an element to the buffer.
     *
     * # Safety
     *
     * May only be called if `deque.len()<deque.capacity()`
     */
    public open fun pushFrontUnchecked(element: T) {
        this.head = this.wrapSub(this.head, 1)
        // SAFETY: Because of the precondition, it's guaranteed that there is space
        // in the logical array before the first element (where self.head is now).
        this.bufferWrite(this.head, element)
        // This can't overflow because `deque.len()<deque.capacity() <= Int.MAX`.
        this.len += 1
    }

    /**
     * Moves an element out of the buffer
     */
    public open fun bufferRead(off: WrappedIndex): T {
        // SAFETY: Upheld by caller.
        return read(this.ptr().add(off.asIndex()))
    }

    /**
     * Writes an element into the buffer, moving it and returning a pointer to it.
     * # Safety
     *
     * May only be called if `off<self.capacity()`.
     */
    public open fun bufferWrite(off: WrappedIndex, value: T): T {
        // SAFETY: Upheld by caller.
        return run {
            val ptr = this.ptr().add(off.asIndex())
            write(ptr, value)
            ptr
        }
    }

    /**
     * Returns a slice pointer into the buffer.
     * `range` must lie inside `0 .. self.capacity()`.
     */
    public open fun bufferRange(range: IntRange): MutPtr {
        // SAFETY: Upheld by caller.
        return this.ptr().add(range.start).castSlice(range.end - range.start)
    }

    /**
     * Returns `true` if the buffer is at full capacity.
     */
    public open fun isFull(): Boolean = this.len == this.capacity()

    /**
     * Returns the index in the underlying buffer for a given logical element
     * index + addend.
     */
    public open fun wrapAdd(idx: WrappedIndex, addend: Int): WrappedIndex = wrapIndex((idx.asIndex() + (addend.toInt())), this.capacity())

    public open fun toWrappedIndex(idx: Int): WrappedIndex = this.wrapAdd(this.head, idx)

    /**
     * Returns the index in the underlying buffer for a given logical element
     * index - subtrahend.
     */
    public open fun wrapSub(idx: WrappedIndex, subtrahend: Int): WrappedIndex = wrapIndex(((idx.asIndex() - (subtrahend.toInt())) + this.capacity().toInt()), this.capacity())

    /**
     * Get source, destination and count (like the arguments to [ptr.copyNonoverlapping])
     * for copying `count` values from index `src` to index `dst`.
     * One of the ranges can wrap around the physical buffer, for this reason 2 triples are returned.
     *
     * Use of the word "ranges" specifically refers to `src .. src + count` and `dst .. dst + count`.
     *
     * # Safety
     *
     * - Ranges must not overlap: `src.absDiff(dst) >= count`.
     * - Ranges must be in bounds of the logical buffer: `src + count <= self.capacity()` and `dst + count <= self.capacity()`.
     * - `head` must be in bounds: `head<self.capacity()`, unless `self.capacity() == 0`, in which case `head == 0`.
     */
    public open fun nonoverlappingRanges(src: Int, dst: Int, count: Int, head: WrappedIndex): List<Triple<ConstPtr, MutPtr, Int>> {
        // "`src` and `dst` must be at least as far apart as `count`"
        check(src.absDiff(dst) >= count) { "`src` and `dst` must not overlap. src=$src dst=$dst count=$count" }
        check(src.max(dst) + count <= this.capacity()) { "ranges must be in bounds. src=$src dst=$dst count=$count cap=${this.capacity()}" }
        val wrappedSrc = this.wrapAdd(head, src)
        val wrappedDst = this.wrapAdd(head, dst)
        val roomAfterSrc = this.capacity() - wrappedSrc.asIndex()
        val roomAfterDst = this.capacity() - wrappedDst.asIndex()
        val srcWraps = roomAfterSrc < count
        val dstWraps = roomAfterDst < count
        // Wrapping occurs if `capacity` is contained within `wrappedSrc .. wrappedSrc + count` or `wrappedDst .. wrappedDst + count`.
        // Since these two ranges must not overlap as per the safety invariants of this function, only one range can wrap.
        check(!(srcWraps && dstWraps)) { "BUG: at most one of src and dst can wrap. src=$src dst=$dst count=$count cap=${this.capacity()}" }
        // ignore-tidy-undocumented-unchecked
        return run {
            val ptr = this.ptr()
            val srcPtr = ptr.add(wrappedSrc.asIndex())
            val dstPtr = ptr.add(wrappedDst.asIndex())
            if (srcWraps) {
                listOf(Triple(srcPtr, dstPtr, roomAfterSrc), Triple(ptr, dstPtr.add(roomAfterSrc), count - roomAfterSrc))
            } else if (dstWraps) {
                listOf(Triple(srcPtr, dstPtr, roomAfterDst), Triple(srcPtr.add(roomAfterDst), ptr, count - roomAfterDst))
            } else {
                listOf(Triple(srcPtr, dstPtr, count), Triple(ConstPtr(0L), MutPtr(0L), 0))
            }
        }
    }

    /**
     * Copies a contiguous block of memory len long from src to dst
     */
    public open fun copy(src: WrappedIndex, dst: WrappedIndex, len: Int) {
        check(dst + len <= this.capacity()) { "cpy dst=$dst src=$src len=$len cap=${this.capacity()}" }
        check(src + len <= this.capacity()) { "cpy dst=$dst src=$src len=$len cap=${this.capacity()}" }
        // SAFETY: Upheld by caller.
        run {
            io.github.kotlinmania.rustlang.core.ptr.copy(this.ptr().add(src.asIndex()), this.ptr().add(dst.asIndex()), len)
        }
    }

    /**
     * Copies a contiguous block of memory len long from src to dst
     */
    public open fun copyNonoverlapping(src: WrappedIndex, dst: WrappedIndex, len: Int) {
        check(dst + len <= this.capacity()) { "cno dst=$dst src=$src len=$len cap=${this.capacity()}" }
        check(src + len <= this.capacity()) { "cno dst=$dst src=$src len=$len cap=${this.capacity()}" }
        // SAFETY: Upheld by caller.
        run {
            io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping(this.ptr().add(src.asIndex()), this.ptr().add(dst.asIndex()), len)
        }
    }

    /**
     * Copies a potentially wrapping block of memory len long from src to dest.
     * (abs(dst - src) + len) must be no larger than capacity() (There must be at
     * most one continuous overlapping region between src and dest).
     */
    public open fun wrapCopy(src: WrappedIndex, dst: WrappedIndex, len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        check(minOf(src.absDiff(dst), this.capacity() - src.absDiff(dst)) + len <= this.capacity()) { "wrc dst=$dst src=$src len=$len cap=${this.capacity()}" }
        // If T is a ZST, don't do any copying.
        if (((typeLayoutT).sizeBytes == 0L) || src == dst || len == 0) {
            return
        }
        val dstAfterSrc = this.wrapSub(dst, src.asIndex()) < len
        val srcPreWrapLen = this.capacity() - src.asIndex()
        val dstPreWrapLen = this.capacity() - dst.asIndex()
        val srcWraps = srcPreWrapLen < len
        val dstWraps = dstPreWrapLen < len
        run {
            val matchVal = Triple(dstAfterSrc, srcWraps, dstWraps)
            when {
                matchVal.second == false && matchVal.third == false -> {
                    // src doesn't wrap, dst doesn't wrap
                    //
                    // S . . .
                    // 1 [_ _ A A B B C C _]
                    // 2 [_ _ A A A A B B _]
                    // D . . .
                    //
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(src, dst, len)
                    }
                }
                matchVal.first == false && matchVal.second == false && matchVal.third == true -> {
                    // dst before src, src doesn't wrap, dst wraps
                    //
                    // S . . .
                    // 1 [A A B B _ _ _ C C]
                    // 2 [A A B B _ _ _ A A]
                    // 3 [B B B B _ _ _ A A]
                    // . .           D .
                    //
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(src, dst, dstPreWrapLen)
                        this.copy(src.add(dstPreWrapLen), WrappedIndex.zero(), len - dstPreWrapLen)
                    }
                }
                matchVal.first == true && matchVal.second == false && matchVal.third == true -> {
                    // src before dst, src doesn't wrap, dst wraps
                    //
                    // S . . .
                    // 1 [C C _ _ _ A A B B]
                    // 2 [B B _ _ _ A A B B]
                    // 3 [B B _ _ _ A A A A]
                    // . .           D .
                    //
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(src.add(dstPreWrapLen), WrappedIndex.zero(), len - dstPreWrapLen)
                        this.copy(src, dst, dstPreWrapLen)
                    }
                }
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> {
                    // dst before src, src wraps, dst doesn't wrap
                    //
                    // . .           S .
                    // 1 [C C _ _ _ A A B B]
                    // 2 [C C _ _ _ B B B B]
                    // 3 [C C _ _ _ B B C C]
                    // D . . .
                    //
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(src, dst, srcPreWrapLen)
                        this.copy(WrappedIndex.zero(), dst.add(srcPreWrapLen), len - srcPreWrapLen)
                    }
                }
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> {
                    // src before dst, src wraps, dst doesn't wrap
                    //
                    // . .           S .
                    // 1 [A A B B _ _ _ C C]
                    // 2 [A A A A _ _ _ C C]
                    // 3 [C C A A _ _ _ C C]
                    // D . . .
                    //
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(WrappedIndex.zero(), dst.add(srcPreWrapLen), len - srcPreWrapLen)
                        this.copy(src, dst, srcPreWrapLen)
                    }
                }
                matchVal.first == false && matchVal.second == true && matchVal.third == true -> {
                    // dst before src, src wraps, dst wraps
                    //
                    // . . .         S .
                    // 1 [A B C D _ E F G H]
                    // 2 [A B C D _ E G H H]
                    // 3 [A B C D _ E G H A]
                    // 4 [B C C D _ E G H A]
                    // . .         D . .
                    //
                    check(dstPreWrapLen > srcPreWrapLen)
                    val delta = dstPreWrapLen - srcPreWrapLen
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(src, dst, srcPreWrapLen)
                        this.copy(WrappedIndex.zero(), dst.add(srcPreWrapLen), delta)
                        this.copy(WrappedIndex.fromArbitraryNumber(delta), WrappedIndex.zero(), len - dstPreWrapLen)
                    }
                }
                else -> {
                    // src before dst, src wraps, dst wraps
                    //
                    // . .         S . .
                    // 1 [A B C D _ E F G H]
                    // 2 [A A B D _ E F G H]
                    // 3 [H A B D _ E F G H]
                    // 4 [H A B D _ E F F G]
                    // . . .         D .
                    //
                    check(srcPreWrapLen > dstPreWrapLen)
                    val delta1 = srcPreWrapLen - dstPreWrapLen
                    // ignore-tidy-undocumented-unchecked
                    run {
                        this.copy(WrappedIndex.zero(), WrappedIndex.fromArbitraryNumber(delta1), len - srcPreWrapLen)
                        this.copy(WrappedIndex.fromArbitraryNumber(this.capacity() - delta1), WrappedIndex.zero(), delta1)
                        this.copy(src, dst, dstPreWrapLen)
                    }
                }
            }
        }
    }

    /**
     * Copies all values from `src` to `dst`, wrapping around if needed.
     * Assumes capacity is sufficient.
     */
    public open fun copySlice(dst: WrappedIndex, src: List<T>) {
        check(src.size <= this.capacity())
        val headRoom = this.capacity() - dst.asIndex()
        if (src.size <= headRoom) {
            // ignore-tidy-undocumented-unchecked
            run {
                copyNonoverlapping(src.asPtr(), this.ptr().add(dst.asIndex()), src.size, typeLayoutT = UnresolvedTypeLayout<T>())
            }
        } else {
            val (left, right) = src.splitAt(headRoom)
            // ignore-tidy-undocumented-unchecked
            run {
                copyNonoverlapping(left.asPtr(), this.ptr().add(dst.asIndex()), left.len())
                copyNonoverlapping(right.asPtr(), this.ptr(), right.len())
            }
        }
    }

    /**
     * Copies all values from `src` to `dst` in reversed order, wrapping around if needed.
     * Assumes capacity is sufficient.
     * Equivalent to calling [VecDeque.copySlice] with a [reversed](https://doc.rust-lang.org/std/primitive.slice.html#method.reverse) slice.
     */
    public open fun copySliceReversed(dst: WrappedIndex, src: List<T>) {
        /**
         * # Safety
         *
         * See [ptr.copyNonoverlapping].
         */
        fun <T> copyNonoverlappingReversed(src1: ConstPtr, dst1: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
            for (i in 0 until count) {
                // SAFETY: Upheld by caller.
                copyNonoverlapping(ConstPtr((src1).rawAddress + ((count - 1 - i).toLong() * (typeLayoutT).strideBytes)), MutPtr((dst1).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes)), 1, typeLayoutT = typeLayoutT)
            }
        }
        check(src.size <= this.capacity())
        val headRoom = this.capacity() - dst.asIndex()
        if (src.size <= headRoom) {
            // ignore-tidy-undocumented-unchecked
            run {
                copyNonoverlappingReversed(src.asPtr(), this.ptr().add(dst.asIndex()), src.size, typeLayoutT = UnresolvedTypeLayout<T>())
            }
        } else {
            val (left, right) = src.splitAt(src.size - headRoom)
            // ignore-tidy-undocumented-unchecked
            run {
                copyNonoverlappingReversed(right.asPtr(), this.ptr().add(dst.asIndex()), right.len())
                copyNonoverlappingReversed(left.asPtr(), this.ptr(), left.len())
            }
        }
    }

    /**
     * Writes all values from `iter` to `dst`.
     *
     * # Safety
     *
     * Assumes no wrapping around happens.
     * Assumes capacity is sufficient.
     */
    public open fun writeIter(dst: WrappedIndex, iter: Any, written: RustMutableReference<Int>) {
        val written1 = written
        // ignore-tidy-undocumented-unchecked
        iter.mapIndexed { index, item -> Pair(index, item) }.forEach({ (i, element) -> run {
            this.bufferWrite(dst.add(i), element)
            (written1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
        } })
    }

    /**
     * Writes all values from `iter` to `dst`, wrapping
     * at the end of the buffer and returns the number
     * of written values.
     *
     * # Safety
     *
     * Assumes that `iter` yields at most `len` items.
     * Assumes capacity is sufficient.
     */
    public open fun writeIterWrapping(dst: WrappedIndex, iter: Any, len: Int): Int {
        open class Guard<T, A : Allocator>(
            public var deque: VecDeque<T, A>,
            public var written: Int,
        ) {
            public open fun drop() {
                this.deque.len += this.written
            }
        }
        val headRoom = this.capacity() - dst.asIndex()
        val guard = Guard(deque = this, written = 0)
        if (headRoom >= len) {
            // ignore-tidy-undocumented-unchecked
            guard.deque.writeIter(dst, iter, guard.written)
        } else {
            // ignore-tidy-undocumented-unchecked
            run {
                guard.deque.writeIter(dst, ByRefSized(iter).take(headRoom), guard.written)
                guard.deque.writeIter(WrappedIndex.zero(), iter, guard.written)
            }
        }
        return guard.written
    }

    /**
     * Frobs the head and tail sections around to handle the fact that we
     * just reallocated. Unsafe because it trusts oldCapacity.
     */
    public open fun handleCapacityIncrease(oldCapacity: Int) {
        val newCapacity = this.capacity()
        check(newCapacity >= oldCapacity)
        // Move the shortest contiguous section of the ring buffer
        //
        // H := head
        // L := last element (`self.toPhysicalIdx(self.len - 1)`)
        //
        // H             L
        // [o o o o o o o o ]
        // H             L
        // A [o o o o o o o o . . . . . . . . ]
        // L H
        // [o o o o o o o o ]
        // H             L
        // B [. . . o o o o o o o o . . . . . ]
        // L H
        // [o o o o o o o o ]
        // L                 H
        // C [o o o o o o . . . . . . . . o o ]
        // can't use isContiguous() because the capacity is already updated.
        if (this.head <= oldCapacity - this.len) {
            // A
            // Nop
        } else {
            val headLen = oldCapacity - this.head.asIndex()
            val tailLen = this.len - headLen
            if (headLen > tailLen && newCapacity - oldCapacity >= tailLen) {
                // B
                // ignore-tidy-undocumented-unchecked
                run {
                    this.copyNonoverlapping(WrappedIndex.zero(), WrappedIndex.fromArbitraryNumber(oldCapacity), tailLen)
                }
            } else {
                // C
                val newHead = WrappedIndex.fromArbitraryNumber(newCapacity - headLen)
                // ignore-tidy-undocumented-unchecked
                run {
                    // can't use copyNonoverlapping here, because if e.g. headLen = 2
                    // and newCapacity = oldCapacity + 1, then the heads overlap.
                    this.copy(this.head, newHead, headLen)
                }
                this.head = newHead
            }
        }
        check(this.head < this.capacity() || this.capacity() == 0)
    }

    /**
     * Creates an iterator which uses a closure to determine if an element in the range should be removed.
     *
     * If the closure returns `true`, the element is removed from the deque and yielded. If the closure
     * returns `false`, or panics, the element remains in the deque and will not be yielded.
     *
     * Only elements that fall in the provided range are considered for extraction, but any elements
     * after the range will still have to be moved if any element has been extracted.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use `extractIf().forEach(drop)` if you do not need the returned iterator,
     * or [retainMut] with a negated predicate if you also do not need to restrict the range.
     *
     * [retainMut]: VecDeque.retainMut
     *
     * Using this method is equivalent to the following code:
     *
     * ```kotlin
     * @feature(vecDequeExtractIf)
     * public fun doctestEntry() {
     *     var i = range.start
     *     val endItems = deq.len() - range.end
     *     while (i < deq.len() - endItems) {
     *         if (somePredicate(deq[i])) {
     *             val value = deq.remove(i).unwrap()
     *             // your code here
     *         } else {
     *             i += 1
     *         }
     *     }
     * }
     * ```
     *
     * But `extractIf` is easier to use. `extractIf` is also more efficient,
     * because it can backshift the elements of the array in bulk.
     *
     * The iterator also lets you mutate the value of each element in the
     * closure, regardless of whether you choose to keep or remove it.
     *
     * # Panics
     *
     * If `range` is out of bounds.
     *
     * # Examples
     *
     * Splitting a deque into even and odd values, reusing the original deque:
     *
     * ```kotlin
     * @feature(vecDequeExtractIf)
     * public fun doctestEntry() {
     *     var numbers = VecDeque.from(listOf(1, 2, 3, 4, 5, 6, 8, 9, 11, 13, 14, 15))
     *     val evens = numbers.extractIf(0 until 0, { x -> x % 2 == 0 }).collect<VecDeque>()
     *     val odds = numbers
     *     check(evens == VecDeque.from(listOf(2, 4, 6, 8, 14)))
     *     check(odds == VecDeque.from(listOf(1, 3, 5, 9, 11, 13, 15)))
     * }
     * ```
     *
     * Using the range argument to only process a part of the deque:
     *
     * ```kotlin
     * @feature(vecDequeExtractIf)
     * public fun doctestEntry() {
     *     var items = VecDeque.from(listOf(0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 2))
     *     val ones = items.extractIf(7 until Int.MAX_VALUE, { x -> x == 1 }).collect<VecDeque>()
     *     check(items == VecDeque.from(listOf(0, 0, 0, 0, 0, 0, 0, 2, 2, 2)))
     *     check(ones.len() == 3)
     * }
     * ```
     */
    public open fun <R> extractIf(range: R, filter: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> where R : RangeBounds<Int> = ExtractIf.new(this, filter, range)

    /**
     * Provides a reference to the element at the given index.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(3)
     * buf.addLast(4)
     * buf.addLast(5)
     * buf.addLast(6)
     * check(buf.get(1) == 4)
     * ```
     */
    public open fun get(index: Int): T? =
        if (index < this.len) {
            val idx = this.toWrappedIndex(index)
            // ignore-tidy-undocumented-unchecked
            this.ptr().add(idx.asIndex())
        } else {
            null
        }

    /**
     * Provides a mutable reference to the element at the given index.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(3)
     * buf.addLast(4)
     * buf.addLast(5)
     * buf.addLast(6)
     * check(buf[1] == 4)
     * when (val matchVal = buf.getMut(1)) {
     *     null -> {}
     *     else -> {
     *         val elem = matchVal
     *         elem = 7
     *     }
     * }
     * check(buf[1] == 7)
     * ```
     */
    public open fun getMut(index: Int): T? =
        if (index < this.len) {
            val idx = this.toWrappedIndex(index)
            // ignore-tidy-undocumented-unchecked
            this.ptr().add(idx.asIndex())
        } else {
            null
        }

    /**
     * Swaps elements at indices `i` and `j`.
     *
     * `i` and `j` may be equal.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Panics
     *
     * Panics if either index is out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(3)
     * buf.addLast(4)
     * buf.addLast(5)
     * check(buf == listOf(3, 4, 5))
     * buf.swap(0, 2)
     * check(buf == listOf(5, 4, 3))
     * ```
     */
    public open fun swap(i: Int, j: Int) {
        check(i < this.len())
        check(j < this.len())
        val ri = this.toWrappedIndex(i)
        val rj = this.toWrappedIndex(j)
        // ignore-tidy-undocumented-unchecked
        io.github.kotlinmania.rustlang.core.ptr.swap(this.ptr().add(ri.asIndex()), this.ptr().add(rj.asIndex()))
    }

    /**
     * Returns the number of elements the deque can hold without
     * reallocating.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * val buf: VecDeque<Int, Global> = VecDeque.withCapacity(10)
     * check(buf.capacity() >= 10)
     * ```
     */
    public open fun capacity(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int =
        if (((typeLayoutT).sizeBytes == 0L)) {
            Int.MAX_VALUE
        } else {
            this.buf.capacity()
        }

    /**
     * Reserves the minimum capacity for at least `additional` more elements to be inserted in the
     * given deque. Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the collection more space than it requests. Therefore
     * capacity can not be relied upon to be precisely minimal. Prefer [reserve] if future
     * insertions are expected.
     *
     * # Panics
     *
     * Panics if the new capacity overflows `Int`.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * var buf: VecDeque<Int, Global> = listOf(1).into()
     * buf.reserveExact(10)
     * check(buf.capacity() >= 11)
     * ```
     *
     * [reserve]: VecDeque.reserve
     */
    public open fun reserveExact(additional: Int) {
        val newCap = (this.len.checkedAdd(additional) ?: error("capacity overflow"))
        val oldCap = this.capacity()
        if (newCap > oldCap) {
            this.buf.reserveExact(this.len, additional)
            // ignore-tidy-undocumented-unchecked
            run {
                this.handleCapacityIncrease(oldCap)
            }
        }
    }

    /**
     * Reserves capacity for at least `additional` more elements to be inserted in the given
     * deque. The collection may reserve more space to speculatively avoid frequent reallocations.
     *
     * # Panics
     *
     * Panics if the new capacity overflows `Int`.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * var buf: VecDeque<Int, Global> = listOf(1).into()
     * buf.reserve(10)
     * check(buf.capacity() >= 11)
     * ```
     */
    public open fun reserve(additional: Int) {
        val newCap = (this.len.checkedAdd(additional) ?: error("capacity overflow"))
        val oldCap = this.capacity()
        if (newCap > oldCap) {
            // we don't need to reserveExact(), as the size doesn't have
            // to be a power of 2.
            this.buf.reserve(this.len, additional)
            // ignore-tidy-undocumented-unchecked
            run {
                this.handleCapacityIncrease(oldCap)
            }
        }
    }

    /**
     * Tries to reserve the minimum capacity for at least `additional` more elements to
     * be inserted in the given deque. After calling `tryReserveExact`,
     * capacity will be greater than or equal to `self.len() + additional` if
     * it returns `Ok(())`. Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: VecDeque.tryReserve
     *
     * # Errors
     *
     * If the capacity overflows `Int`, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * fun processData(data: List<UInt>): Result<VecDeque<UInt, Global>> {
     *     var output: VecDeque<UInt, Global> = VecDeque.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserveExact(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM(Out-Of-Memory) in the middle of our complex work
     *     output.extend(data.map({ value ->
     *         value * 2 + 5
     *     }))
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> {
        val newCap = when (val tryResult = (this.len.checkedAdd(additional)?.let { Result.Ok<Int, TryReserveError>(it) } ?: Result.Err<Int, TryReserveError>(TryReserveErrorKind.CapacityOverflow))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue()) }
        val oldCap = this.capacity()
        if (newCap > oldCap) {
            when (val tryResult = this.buf.tryReserveExact(this.len, additional)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue()) }
            // ignore-tidy-undocumented-unchecked
            run {
                this.handleCapacityIncrease(oldCap)
            }
        }
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the given deque. The collection may reserve more space to speculatively avoid
     * frequent reallocations. After calling `tryReserve`, capacity will be
     * greater than or equal to `self.len() + additional` if it returns
     * `Ok(())`. Does nothing if capacity is already sufficient. This method
     * preserves the contents even if an error occurs.
     *
     * # Errors
     *
     * If the capacity overflows `Int`, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * fun processData(data: List<UInt>): Result<VecDeque<UInt, Global>> {
     *     var output: VecDeque<UInt, Global> = VecDeque.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserve(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     output.extend(data.map({ value ->
     *         value * 2 + 5
     *     }))
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> {
        val newCap = when (val tryResult = (this.len.checkedAdd(additional)?.let { Result.Ok<Int, TryReserveError>(it) } ?: Result.Err<Int, TryReserveError>(TryReserveErrorKind.CapacityOverflow))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue()) }
        val oldCap = this.capacity()
        if (newCap > oldCap) {
            when (val tryResult = this.buf.tryReserve(this.len, additional)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, TryReserveError>(tryResult.errValue()) }
            // ignore-tidy-undocumented-unchecked
            run {
                this.handleCapacityIncrease(oldCap)
            }
        }
        return Result.Ok<Unit, TryReserveError>(Unit)
    }

    /**
     * Shrinks the capacity of the deque as much as possible.
     *
     * It will drop down as close as possible to the length but the allocator may still inform the
     * deque that there is space for a few more elements.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.withCapacity(15)
     * buf.extend(0 until 4)
     * check(buf.capacity() == 15)
     * buf.shrinkToFit()
     * check(buf.capacity() >= 4)
     * ```
     */
    public open fun shrinkToFit() {
        this.shrinkTo(0)
    }

    /**
     * Shrinks the capacity of the deque with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.withCapacity(15)
     * buf.extend(0 until 4)
     * check(buf.capacity() == 15)
     * buf.shrinkTo(6)
     * check(buf.capacity() >= 6)
     * buf.shrinkTo(0)
     * check(buf.capacity() >= 4)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        open class Guard<T, A : Allocator>(
            public var deque: VecDeque<T, A>,
            public var oldHead: WrappedIndex,
            public var targetCap: Int,
        ) {
            public open fun drop() {
                // SAFETY: This is only called if `buf.shrinkToFit` unwinds,
                // which is the only time it's safe to call `abortShrink`.
                this.deque.abortShrink(this.oldHead, this.targetCap)
            }
        }
        val targetCap = minCapacity.max(this.len)
        // never shrink ZSTs
        if (((typeLayoutT).sizeBytes == 0L) || this.capacity() <= targetCap) {
            return
        }
        // There are three cases of interest:
        // All elements are out of desired bounds
        // Elements are contiguous, and tail is out of desired bounds
        // Elements are discontiguous
        //
        // At all other times, element positions are unaffected.
        // `head` and `len` are at most `Long.MAX` and `targetCap<self.capacity()`, so nothing can
        // overflow.
        val tailOutside = (targetCap + 1..this.capacity()).contains((this.head + this.len))
        // Used in the drop guard below.
        val oldHead = this.head
        if (this.len == 0) {
            this.head = WrappedIndex.zero()
        } else if (this.head.asIndex() >= targetCap && tailOutside) {
            // Head and tail are both out of bounds, so copy all of them to the front.
            //
            // H := head
            // L := last element
            // H           L
            // [. . . . . . . . o o o o o o o . ]
            // H           L
            // [o o o o o o o . ]
            //
            // SAFETY: `self.head >= targetCap >= self.len`, therefore these accesses
            // do not overlap.
            run {
                this.copyNonoverlapping(this.head, WrappedIndex.zero(), this.len)
            }
            this.head = WrappedIndex.zero()
        } else if (this.head < targetCap && tailOutside) {
            // Head is in bounds, tail is out of bounds.
            // Copy the overflowing part to the beginning of the
            // buffer. This won't overlap because `targetCap >= self.len`.
            //
            // H := head
            // L := last element
            // H           L
            // [. . . o o o o o o o . . . . . . ]
            // L   H
            // [o o . o o o o o ]
            val len = this.head + this.len - targetCap
            // SAFETY: head is < targetCap, so the index is wrapped
            run {
                this.copyNonoverlapping(WrappedIndex.fromArbitraryNumber(targetCap), WrappedIndex.zero(), len)
            }
        } else if (!this.isContiguous()) {
            // The head slice is at least partially out of bounds, tail is in bounds.
            // Copy the head backwards so it lines up with the target capacity.
            // This won't overlap because `targetCap >= self.len`.
            //
            // H := head
            // L := last element
            // L                   H
            // [o o o o o . . . . . . . . . o o ]
            // L   H
            // [o o o o o . o o ]
            val headLen = this.capacity() - this.head.asIndex()
            // headLen is at least one, so newHead will be < targetCap
            val newHead = WrappedIndex.fromArbitraryNumber(targetCap - headLen)
            // ignore-tidy-undocumented-unchecked
            run {
                // can't use `copyNonoverlapping()` here because the new and old
                // regions for the head might overlap.
                this.copy(this.head, newHead, headLen)
            }
            this.head = newHead
        }
        val guard = Guard(deque = this, oldHead = oldHead, targetCap = targetCap)
        guard.deque.buf.shrinkToFit(targetCap)
        // Don't drop the guard if we didn't unwind.
        forget(guard)
        check(this.head < this.capacity() || this.capacity() == 0)
        check(this.len <= this.capacity())
    }

    /**
     * Reverts the deque back into a consistent state in case `shrinkTo` failed.
     * This is necessary to prevent UB if the backing allocator returns an error
     * from `shrink` and `handleAllocError` subsequently unwinds (see #123369).
     *
     * `oldHead` refers to the head index before `shrinkTo` was called. `targetCap`
     * is the capacity that it was trying to shrink to.
     */
    public open fun abortShrink(oldHead: WrappedIndex, targetCap: Int) {
        // Moral equivalent of self.head + self.len <= targetCap. Won't overflow
        // because `self.len <= targetCap`.
        if (this.head <= targetCap - this.len) {
            // The deque's buffer is contiguous, so no need to copy anything around.
            return
        }
        // `shrinkTo` already copied the head to fit into the new capacity, so this won't overflow.
        val headLen = targetCap - this.head.asIndex()
        // `self.head> targetCap - self.len` => `self.len> targetCap - self.head =: headLen` so this must be positive.
        val tailLen = this.len - headLen
        if (tailLen <= minOf(headLen, this.capacity() - targetCap)) {
            // There's enough spare capacity to copy the tail to the back (because `tailLen<self.capacity() - targetCap`),
            // and copying the tail should be cheaper than copying the head (because `tailLen <= headLen`).
            // SAFETY: The old tail and the new tail can't overlap because the head slice lies
            // between them. The head slice ends at `targetCap`, so that's where we copy to.
            run {
                this.copyNonoverlapping(WrappedIndex.zero(), WrappedIndex.fromArbitraryNumber(targetCap), tailLen)
            }
        } else {
            // Either there's not enough spare capacity to make the deque contiguous, or the head is shorter than the tail
            // (and therefore hopefully cheaper to copy).
            // ignore-tidy-undocumented-unchecked
            run {
                // The old and the new head slice can overlap, so we can't use `copyNonoverlapping` here.
                this.copy(this.head, oldHead, headLen)
                this.head = oldHead
            }
        }
    }

    /**
     * Shortens the deque, keeping the first `len` elements and dropping
     * the rest.
     *
     * If `len` is greater or equal to the deque's current length, this has
     * no effect.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(5)
     * buf.addLast(10)
     * buf.addLast(15)
     * check(buf == listOf(5, 10, 15))
     * buf.truncate(1)
     * check(buf == listOf(5))
     * ```
     */
    public open fun truncate(len: Int) {
        if (len >= this.len) {
            return
        }
        val (front, back) = this.asMutSlices()
        // SAFETY:
        // * Any slice passed to `dropInPlace` is valid; the second case has
        // `len <= front.len()` and returning on `len >= self.len()` ensures
        // `begin<back.len()` in the first case
        // * The head of the VecDeque is moved before calling `dropInPlace`,
        // so no value is dropped twice if `dropInPlace` panics
        if (len > front.len()) {
            val begin = len - front.len()
            val dropBack = mutPtrFromRustCast(back.getUncheckedMut(begin until Int.MAX_VALUE))
            this.len = len
            dropInPlace(dropBack)
        } else {
            val dropFront = mutPtrFromRustCast(front.getUncheckedMut(len until Int.MAX_VALUE))
            this.len = len
            // Make sure the second half is dropped even when a destructor
            // in the first one panics.
            dropInPlace(dropFront)
        }
    }

    /**
     * Shortens the deque, keeping the last `len` elements and dropping
     * the rest.
     *
     * If `len` is greater or equal to the deque's current length, this has
     * no effect.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.pushFront(5)
     * buf.pushFront(10)
     * buf.pushFront(15)
     * check(buf == listOf(15, 10, 5))
     * check(buf.asSlices() == Pair(listOf(15, 10, 5), listOf()))
     * buf.retainBack(1)
     * check(buf.asSlices() == Pair(listOf(5), listOf()))
     * ```
     */
    public open fun retainBack(len: Int) {
        if (len >= this.len) {
            // No action is taken
            return
        }
        val (front, back) = this.asMutSlices()
        // ignore-tidy-undocumented-unchecked
        if (len > back.len()) {
            // The `back` slice remains unchanged.
            // front.len() + back.len() == self.len, so `end` is non-negative
            // and end < front.len()
            val end = front.len() - (len - back.len())
            val dropFront = mutPtrFromRustCast(front.getUncheckedMut(0 until end))
            this.head = this.head.add(end)
            this.len = len
            dropInPlace(dropFront)
        } else {
            val dropFront1 = mutPtrFromRustCast(front)
            // `end` is non-negative by the condition above
            this.head = this.toWrappedIndex(this.len - len)
            this.len = len
            // Make sure the second half is dropped even when a destructor
            // in the first one panics.
            dropInPlace(dropFront1)
        }
    }

    /**
     * Shortens the deque to the elements within `range`, dropping the rest.
     *
     * # Panics
     *
     * Panics if the starting point is greater than the end point or if
     * the end point is greater than the length of the deque.
     *
     * # Examples
     *
     * ```kotlin
     * var buf: VecDeque = (0 until 6).collect()
     * buf.truncateToRange(2 until 5)
     * check(buf == listOf(2, 3, 4))
     * ```
     */
    public open fun <R> truncateToRange(range: R) where R : RangeBounds<Int> {
        val structVal = Range(range, 0 until this.len)
        val start = structVal.start
        val end = structVal.end
        if (start == 0 && end == this.len) {
            return
        } else if (start == end) {
            this.clear()
            return
        } else if (start == 0) {
            this.truncate(end)
            return
        } else if (end == this.len) {
            this.retainBack(this.len - start)
            return
        }
        // Both the dropped prefix [0..start) and the dropped suffix [end..self.len) are
        // non-empty.  Plan up to three physical slices to drop, then update head/len, then
        // drop.  Only one of the dropped prefix or dropped suffix can cross between slices.
        val (front, back) = this.asMutSlices()
        val flen = front.len()
        val blen = back.len()
        val fptr = front.asMutPtr()
        val bptr = back.asMutPtr()
        // ignore-tidy-undocumented-unchecked
        run {
            val (dropA, dropB, dropC) =
                if (end <= flen) {
                    // Kept range lies in `front`.  The dropped suffix is the rest of `front`
                    // plus all of `back`.
                    val pre = sliceFromRawPartsMut(fptr, start)
                    val mid = sliceFromRawPartsMut(fptr.add(end), flen - end)
                    Triple(pre, mid, mutPtrFromRustCast(back))
                } else if (start >= flen) {
                    // Kept range lies in `back`.  The dropped prefix is all of `front` plus the
                    // start of `back`.
                    val mid1 = sliceFromRawPartsMut(bptr, start - flen)
                    val suf = sliceFromRawPartsMut(bptr.add(end - flen), blen - (end - flen))
                    Triple(mutPtrFromRustCast(front), mid1, suf)
                } else {
                    // Kept range straddles the boundary.  The dropped prefix is in `front`, the
                    // dropped suffix is in `back`.  Only two regions to drop.
                    val pre1 = sliceFromRawPartsMut(fptr, start)
                    val suf1 = sliceFromRawPartsMut(bptr.add(end - flen), blen - (end - flen))
                    Triple(pre1, suf1, null)
                }
            // Set these once only, then drop.  If we called truncate + retainBack, a panic in
            // a destructor could leave this truncation in a half completed state.
            this.head = this.toWrappedIndex(start)
            this.len = end - start
            when (dropC) {
                null -> {
                    dropInPlace(dropB)
                }
                else -> {
                    val c = dropC
                    val gA1 = Dropper(dropA)
                    val gB = Dropper(dropB)
                    dropInPlace(c)
                }
            }
        }
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.buf.allocator()

    /**
     * Returns a front-to-back iterator.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(5)
     * buf.addLast(3)
     * buf.addLast(4)
     * val b: List<Any?> = listOf(5, 3, 4)
     * val c: List<Int> = buf.iter().toMutableList()
     * check(c == b)
     * ```
     */
    public open fun iter(): Iter<T> {
        val (a, b) = this.asSlices()
        return Iter.new(a.iter(), b.iter())
    }

    /**
     * Returns a front-to-back iterator that returns mutable references.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var buf = VecDeque.new()
     * buf.addLast(5)
     * buf.addLast(3)
     * buf.addLast(4)
     * for (idx in buf.indices) {
     *     buf[idx] = buf[idx] - 2
     * }
     * val b: List<Any?> = listOf(RustMutableReference(allocateNativeCell(3, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(1, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(2, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
     * check(buf.iterMut().toMutableList() == b)
     * ```
     */
    public open fun iterMut(): IterMut<T> {
        val (a, b) = this.asMutSlices()
        return IterMut.new(a.iterMut(), b.iterMut())
    }

    /**
     * Returns a pair of slices which contain, in order, the contents of the
     * deque.
     *
     * If [makeContiguous] was previously called, all elements of the
     * deque will be in the first slice and the second slice will be empty.
     * Otherwise, the exact split point depends on implementation details
     * and is not guaranteed.
     *
     * [makeContiguous]: VecDeque.makeContiguous
     *
     * # Examples
     *
     * ```kotlin
     * var deque = VecDeque.new()
     * deque.addLast(0)
     * deque.addLast(1)
     * deque.addLast(2)
     * val expected = listOf(0, 1, 2)
     * val (front, back) = deque.asSlices()
     * check(expected[0 until front.len()] == front)
     * check(expected[front.len() until expected.size] == back)
     * deque.pushFront(10)
     * deque.pushFront(9)
     * val expected1 = listOf(9, 10, 0, 1, 2)
     * val (front1, back1) = deque.asSlices()
     * check(expected1[0 until front1.len()] == front1)
     * check(expected1[front1.len() until expected1.size] == back1)
     * ```
     */
    public open fun asSlices(): Pair<List<T>, List<T>> {
        val (aRange, bRange) = this.sliceRanges(0 until 0, this.len)
        // SAFETY: `sliceRanges` always returns valid ranges into
        // the physical buffer.
        return Pair(this.bufferRange(aRange), this.bufferRange(bRange))
    }

    /**
     * Returns a pair of slices which contain, in order, the contents of the
     * deque.
     *
     * If [makeContiguous] was previously called, all elements of the
     * deque will be in the first slice and the second slice will be empty.
     * Otherwise, the exact split point depends on implementation details
     * and is not guaranteed.
     *
     * [makeContiguous]: VecDeque.makeContiguous
     *
     * # Examples
     *
     * ```kotlin
     * import vec.Vec
     *
     * var deque = VecDeque.new()
     * deque.addLast(0)
     * deque.addLast(1)
     * deque.pushFront(10)
     * deque.pushFront(9)
     * // Since the split point is not guaranteed, we may need to update
     * // either slice.
     * var updateNth = { index: Int, value: UInt ->
     *     val (front, back) = deque.asMutSlices()
     *     if (index > front.len() - 1) {
     *         back[index - front.len()] = value
     *     } else {
     *         front[index] = value
     *     }
     * }
     * updateNth(0, 42)
     * updateNth(2, 24)
     * val v: Vec = deque.into()
     * check(v == listOf(42, 10, 24, 1))
     * ```
     */
    public open fun asMutSlices(): Pair<MutableList<T>, MutableList<T>> {
        val (aRange, bRange) = this.sliceRanges(0 until 0, this.len)
        // SAFETY: `sliceRanges` always returns valid ranges into
        // the physical buffer.
        return Pair(this.bufferRange(aRange), this.bufferRange(bRange))
    }

    /**
     * Returns the number of elements in the deque.
     *
     * # Examples
     *
     * ```kotlin
     * var deque = VecDeque.new()
     * check(deque.len() == 0)
     * deque.addLast(1)
     * check(deque.len() == 1)
     * ```
     */
    public open fun len(): Int = this.len

    /**
     * Returns `true` if the deque is empty.
     *
     * # Examples
     *
     * ```kotlin
     * var deque = VecDeque.new()
     * check(deque.isEmpty())
     * deque.pushFront(1)
     * check(!deque.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len == 0

    /**
     * Given a range into the logical buffer of the deque, this function
     * return two ranges into the physical buffer that correspond to
     * the given range. The `len` parameter should usually just be `self.len`;
     * the reason it's passed explicitly is that if the deque is wrapped in
     * a `Drain`, then `self.len` is not actually the length of the deque.
     *
     * # Safety
     *
     * This function is always safe to call. For the resulting ranges to be valid
     * ranges into the physical buffer, the caller must ensure that the result of
     * calling `slice.range(range, .. len)` represents a valid range into the
     * logical buffer, and that all elements in that range are initialized.
     */
    public open fun <R> sliceRanges(range: R, len: Int): Pair<IntRange, IntRange> where R : RangeBounds<Int> {
        val structVal = Range(range, 0 until len)
        val start = structVal.start
        val end = structVal.end
        val len1 = end - start
        return if (len1 == 0) {
            Pair(0 until 0, 0 until 0)
        } else {
            // `slice.range` guarantees that `start <= end <= len`.
            // because `len != 0`, we know that `start<end`, so `start<len`
            // and the indexing is valid.
            val wrappedStart = this.toWrappedIndex(start)
            // this subtraction can never overflow because `wrappedStart` is
            // at most `self.capacity()` (and if `self.capacity != 0`, then `wrappedStart` is strictly less
            // than `self.capacity`).
            val headLen = this.capacity() - wrappedStart.asIndex()
            if (headLen >= len1) {
                // we know that `len + wrappedStart <= self.capacity <= Int.MAX`, so this addition can't overflow
                Pair(wrappedStart.asIndex() until wrappedStart + len1, 0 until 0)
            } else {
                // can't overflow because of the if condition
                val tailLen = len1 - headLen
                Pair(wrappedStart.asIndex() until this.capacity(), 0 until tailLen)
            }
        }
    }

    /**
     * Creates an iterator that covers the specified range in the deque.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the deque.
     *
     * # Examples
     *
     * ```kotlin
     * val deque: VecDeque = listOf(1, 2, 3).into()
     * val range = deque.range(2 until Int.MAX_VALUE).copied().collect<VecDeque>()
     * check(range == listOf(3))
     * // A full range covers all contents
     * val all = deque.range(0 until 0)
     * check(all.len() == 3)
     * ```
     */
    public open fun <R> range(range: R): Iter<T> where R : RangeBounds<Int> {
        val (aRange, bRange) = this.sliceRanges(range, this.len)
        // SAFETY: The ranges returned by `sliceRanges`
        // are valid ranges into the physical buffer, so
        // it's ok to pass them to `bufferRange` and
        // dereference the result.
        val (a, b) = Pair(this.bufferRange(aRange), this.bufferRange(bRange))
        return Iter.new(a.iter(), b.iter())
    }

    /**
     * Creates an iterator that covers the specified mutable range in the deque.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the deque.
     *
     * # Examples
     *
     * ```kotlin
     * var deque: VecDeque = listOf(1, 2, 3).into()
     * for (v in deque.rangeMut(2 until Int.MAX_VALUE)) {
     *     v *= 2
     * }
     * check(deque == listOf(1, 2, 6))
     * // A full range covers all contents
     * for (v1 in deque.rangeMut(0 until 0)) {
     *     v1 *= 2
     * }
     * check(deque == listOf(2, 4, 12))
     * ```
     */
    public open fun <R> rangeMut(range: R): IterMut<T> where R : RangeBounds<Int> {
        val (aRange, bRange) = this.sliceRanges(range, this.len)
        val (a, b) = Pair(this.bufferRange(aRange), this.bufferRange(bRange))
        return IterMut.new(a.iterMut(), b.iterMut())
    }

    /**
     * Removes the specified range from the deque in bulk, returning all
     * removed elements as an iterator. If the iterator is dropped before
     * being fully consumed, it drops the remaining removed elements.
     *
     * The returned iterator keeps a mutable borrow on the queue to optimize
     * its implementation.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the deque.
     *
     * # Leaking
     *
     * If the returned iterator goes out of scope without being dropped (due to
     * [mem.forget], for example), the deque may have lost and leaked
     * elements arbitrarily, including elements outside the range.
     *
     * # Examples
     *
     * ```kotlin
     * var deque: VecDeque = listOf(1, 2, 3).into()
     * val drained = deque.drain(2 until Int.MAX_VALUE).collect<VecDeque>()
     * check(drained == listOf(3))
     * check(deque == listOf(1, 2))
     * // A full range clears all contents, like `clear()` does
     * deque.drain(0 until 0)
     * check(deque.isEmpty())
     * ```
     */
    public open fun <R> drain(range: R): Drain<T, A> where R : RangeBounds<Int> {
        // Memory safety
        //
        // When the Drain is first created, the source deque is shortened to
        // make sure no uninitialized or moved-from elements are accessible at
        // all if the Drain's destructor never gets to run.
        //
        // Drain will ptr.read out the values to remove.
        // When finished, the remaining data will be copied back to cover the hole,
        // and the head/tail values will be restored correctly.
        //
        val structVal = Range(range, 0 until this.len)
        val start = structVal.start
        val end = structVal.end
        val drainStart = start
        val drainLen = end - start
        // The deque's elements are parted into three segments:
        // * 0  -> drainStart
        // * drainStart -> drainStart+drainLen
        // * drainStart+drainLen -> self.len
        //
        // H = self.head; T = self.head+self.len; t = drainStart+drainLen; h = drainHead
        //
        // We store drainStart as self.len, and drainLen and self.len as
        // drainLen and origLen respectively on the Drain. This also
        // truncates the effective array such that if the Drain is leaked, we
        // have forgotten about the potentially moved values after the start of
        // the drain.
        //
        // H   h   t   T
        // [. . . o o x x o o . . .]
        //
        // "forget" about the values after the start of the drain until after
        // the drain is complete and the Drain destructor is run.
        // ignore-tidy-undocumented-unchecked
        return Drain.new(this, drainStart, drainLen)
    }

    /**
     * Creates a splicing iterator that replaces the specified range in the deque with the given
     * `replaceWith` iterator and yields the removed items. `replaceWith` does not need to be the
     * same length as `range`.
     *
     * `range` is removed even if the `Splice` iterator is not consumed before it is dropped.
     *
     * It is unspecified how many elements are removed from the deque if the `Splice` value is
     * leaked.
     *
     * The input iterator `replaceWith` is only consumed when the `Splice` value is dropped.
     *
     * This is optimal if:
     *
     * * The tail (elements in the deque after `range`) is empty,
     * * or `replaceWith` yields fewer or equal elements than `range`s length
     * * or the lower bound of its `sizeHint()` is exact.
     *
     * Otherwise, a temporary vector is allocated and the tail is moved twice.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and past the length of the deque.
     *
     * # Examples
     *
     * ```kotlin
     * import vec.Vec
     *
     * var v = VecDeque.from(mutableListOf(1, 2, 3, 4))
     * val new = listOf(7, 8, 9)
     * val u: Vec = v.splice(1 until 3, new).collect()
     * check(v == listOf(1, 7, 8, 9, 4))
     * check(u == listOf(2, 3))
     * ```
     *
     * Using `splice` to insert new items into a vector efficiently at a specific position
     * indicated by an empty range:
     *
     * ```kotlin
     * var v = VecDeque.from(mutableListOf(1, 5))
     * val new = listOf(2, 3, 4)
     * v.splice(1 until 1, new)
     * check(v == listOf(1, 2, 3, 4, 5))
     * ```
     */
    public open fun <R, I> splice(range: R, replaceWith: I): Splice<IntoIter, A> where R : RangeBounds<Int>, I : Iterable<T> = Splice(drain = this.drain(range), replaceWith = replaceWith.iterator())

    /**
     * Clears the deque, removing all values.
     *
     * # Examples
     *
     * ```kotlin
     * var deque = VecDeque.new()
     * deque.addLast(1)
     * deque.clear()
     * check(deque.isEmpty())
     * ```
     */
    public open fun clear() {
        this.truncate(0)
        // Not strictly necessary, but leaves things in a more consistent/predictable state.
        this.head = WrappedIndex.zero()
    }

    /**
     * Returns `true` if the deque contains an element equal to the
     * given value.
     *
     * This operation is *O*(*n*).
     *
     * Note that if you have a sorted `VecDeque`, [binarySearch] may be faster.
     *
     * [binarySearch]: VecDeque.binarySearch
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * var deque: VecDeque<UInt, Global> = VecDeque.new()
     * deque.addLast(0)
     * deque.addLast(1)
     * check(deque.contains(1) == true)
     * check(deque.contains(10) == false)
     * ```
     */
    public open fun contains(x: T): Boolean {
        val (a, b) = this.asSlices()
        return a.contains(x) || b.contains(x)
    }

    /**
     * Provides a reference to the front element, or `None` if the deque is
     * empty.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * check(d.front() == null)
     * d.addLast(1)
     * d.addLast(2)
     * check(d.front() == 1)
     * ```
     */
    public open fun front(): T? = this.get(0)

    /**
     * Provides a mutable reference to the front element, or `None` if the
     * deque is empty.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * check(d.frontMut() == null)
     * d.addLast(1)
     * d.addLast(2)
     * when (val matchVal = d.frontMut()) {
     *     null -> Unit
     *     else -> {
     *         val x = matchVal
     *         x = 9
     *     }
     * }
     * check(d.front() == 9)
     * ```
     */
    public open fun frontMut(): T? = this.getMut(0)

    /**
     * Provides a reference to the back element, or `None` if the deque is
     * empty.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * check(d.back() == null)
     * d.addLast(1)
     * d.addLast(2)
     * check(d.back() == 2)
     * ```
     */
    public open fun back(): T? = this.get((this.len - 1))

    /**
     * Provides a mutable reference to the back element, or `None` if the
     * deque is empty.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * check(d.back() == null)
     * d.addLast(1)
     * d.addLast(2)
     * when (val matchVal = d.backMut()) {
     *     null -> Unit
     *     else -> {
     *         val x = matchVal
     *         x = 9
     *     }
     * }
     * check(d.back() == 9)
     * ```
     */
    public open fun backMut(): T? = this.getMut((this.len - 1))

    /**
     * Removes the first element and returns it, or `None` if the deque is
     * empty.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * d.addLast(1)
     * d.addLast(2)
     * check(d.removeFirstOrNull() == 1)
     * check(d.removeFirstOrNull() == 2)
     * check(d.removeFirstOrNull() == null)
     * ```
     */
    public open fun popFront(): T? =
        if (this.isEmpty()) {
            null
        } else {
            val oldHead = this.head
            this.head = this.toWrappedIndex(1)
            this.len -= 1
            // ignore-tidy-undocumented-unchecked
            run {
                assertUnchecked(this.len < this.capacity())
                this.bufferRead(oldHead)
            }
        }

    /**
     * Removes the last element from the deque and returns it, or `None` if
     * it is empty.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * check(buf.popBack() == null)
     * buf.addLast(1)
     * buf.addLast(3)
     * check(buf.popBack() == 3)
     * ```
     */
    public open fun popBack(): T? =
        if (this.isEmpty()) {
            null
        } else {
            this.len -= 1
            // ignore-tidy-undocumented-unchecked
            run {
                assertUnchecked(this.len < this.capacity())
                this.bufferRead(this.toWrappedIndex(this.len))
            }
        }

    /**
     * Removes and returns the first element from the deque if the predicate
     * returns `true`, or [None] if the predicate returns false or the deque
     * is empty (the predicate will not be called in that case).
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var deque: VecDeque<Int, Global> = mutableListOf(0, 1, 2, 3, 4).into()
     * val pred = { x: RustMutableReference<Int> -> x % 2 == 0 }
     * check(deque.popFrontIf(pred) == 0)
     * check(deque == listOf(1, 2, 3, 4))
     * check(deque.popFrontIf(pred) == null)
     * ```
     */
    public open fun popFrontIf(predicate: (T) -> Boolean): T? {
        val first = (this.frontMut() ?: return null)
        return if (predicate(first)) {
            this.removeFirstOrNull()
        } else {
            null
        }
    }

    /**
     * Removes and returns the last element from the deque if the predicate
     * returns `true`, or [None] if the predicate returns false or the deque
     * is empty (the predicate will not be called in that case).
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var deque: VecDeque<Int, Global> = mutableListOf(0, 1, 2, 3, 4).into()
     * val pred = { x: RustMutableReference<Int> -> x % 2 == 0 }
     * check(deque.popBackIf(pred) == 4)
     * check(deque == listOf(0, 1, 2, 3))
     * check(deque.popBackIf(pred) == null)
     * ```
     */
    public open fun popBackIf(predicate: (T) -> Boolean): T? {
        val last = (this.backMut() ?: return null)
        return if (predicate(last)) {
            this.popBack()
        } else {
            null
        }
    }

    /**
     * Prepends an element to the deque.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.new()
     * d.pushFront(1)
     * d.pushFront(2)
     * check(d.front() == 2)
     * ```
     */
    public open fun pushFront(value: T) {
        this.pushFrontMut(value)
    }

    /**
     * Prepends an element to the deque, returning a reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.from(listOf(1, 2, 3))
     * val x = d.pushFrontMut(8)
     * x -= 1
     * check(d.front() == 7)
     * ```
     */
    public open fun pushFrontMut(value: T): T {
        if (this.isFull()) {
            this.grow()
        }
        this.head = this.wrapSub(this.head, 1)
        this.len += 1
        // SAFETY: We know that self.head is within range of the deque.
        return this.bufferWrite(this.head, value)
    }

    /**
     * Appends an element to the back of the deque.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(1)
     * buf.addLast(3)
     * check(3 == buf.back().unwrap())
     * ```
     */
    public open fun pushBack(value: T) {
        this.pushBackMut(value)
    }

    /**
     * Appends an element to the back of the deque, returning a reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * var d = VecDeque.from(listOf(1, 2, 3))
     * val x = d.pushBackMut(9)
     * x += 1
     * check(d.back() == 10)
     * ```
     */
    public open fun pushBackMut(value: T): T {
        if (this.isFull()) {
            this.grow()
        }
        val len = this.len
        this.len += 1
        // ignore-tidy-undocumented-unchecked
        return this.bufferWrite(this.toWrappedIndex(len), value)
    }

    /**
     * Prepends all contents of the iterator to the front of the deque.
     * The order of the contents is preserved.
     *
     * To get behavior like [`append`][VecDeque.append] where elements are moved
     * from the other collection to this one, use `self.prepend(other.drain(..))`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(dequeExtendFront)
     * public fun doctestEntry() {
     *     var deque = VecDeque.from(listOf(4, 5, 6))
     *     deque.prepend(listOf(1, 2, 3))
     *     check(deque == listOf(1, 2, 3, 4, 5, 6))
     * }
     * ```
     *
     * Move values between collections like [`append`][VecDeque.append] does but prepend to the front:
     *
     * ```kotlin
     * @feature(dequeExtendFront)
     * public fun doctestEntry() {
     *     var deque1 = VecDeque.from(listOf(4, 5, 6))
     *     var deque2 = VecDeque.from(listOf(1, 2, 3))
     *     deque1.prepend(deque2.drain(0 until 0))
     *     check(deque1 == listOf(1, 2, 3, 4, 5, 6))
     *     check(deque2.isEmpty())
     * }
     * ```
     */
    public open fun <I : Iterable<T>> prepend(other: I) {
        this.extendFront(other.iterator().rev())
    }

    /**
     * Prepends all contents of the iterator to the front of the deque,
     * as if [`pushFront`][VecDeque.pushFront] was called repeatedly with
     * the values yielded by the iterator.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(dequeExtendFront)
     * public fun doctestEntry() {
     *     var deque = VecDeque.from(listOf(4, 5, 6))
     *     deque.extendFront(listOf(3, 2, 1))
     *     check(deque == listOf(1, 2, 3, 4, 5, 6))
     * }
     * ```
     *
     * This behaves like [`pushFront`][VecDeque.pushFront] was called repeatedly:
     *
     * ```kotlin
     * var deque = VecDeque.from(listOf(4, 5, 6))
     * for (v in listOf(3, 2, 1)) {
     *     deque.pushFront(v)
     * }
     * check(deque == listOf(1, 2, 3, 4, 5, 6))
     * ```
     */
    public open fun <I : Iterable<T>> extendFront(iter: I) {
        VecDeque.specExtendFront(this, iter.iterator())
    }

    public open fun isContiguous(): Boolean {
        // Do the calculation like this to avoid overflowing if len + head > usize.Max
        return this.head <= this.capacity() - this.len
    }

    /**
     * Removes an element from anywhere in the deque and returns it,
     * replacing it with the first element.
     *
     * This does not preserve ordering, but is *O*(1).
     *
     * Returns `None` if `index` is out of bounds.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * check(buf.swapRemoveFront(0) == null)
     * buf.addLast(1)
     * buf.addLast(2)
     * buf.addLast(3)
     * check(buf == listOf(1, 2, 3))
     * check(buf.swapRemoveFront(2) == 3)
     * check(buf == listOf(2, 1))
     * ```
     */
    public open fun swapRemoveFront(index: Int): T? {
        val length = this.len
        if (index < length && index != 0) {
            this.swap(index, 0)
        } else if (index >= length) {
            return null
        }
        return this.removeFirstOrNull()
    }

    /**
     * Removes an element from anywhere in the deque and returns it,
     * replacing it with the last element.
     *
     * This does not preserve ordering, but is *O*(1).
     *
     * Returns `None` if `index` is out of bounds.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * check(buf.swapRemoveBack(0) == null)
     * buf.addLast(1)
     * buf.addLast(2)
     * buf.addLast(3)
     * check(buf == listOf(1, 2, 3))
     * check(buf.swapRemoveBack(0) == 1)
     * check(buf == listOf(3, 2))
     * ```
     */
    public open fun swapRemoveBack(index: Int): T? {
        val length = this.len
        if (length > 0 && index < length - 1) {
            this.swap(index, length - 1)
        } else if (index >= length) {
            return null
        }
        return this.popBack()
    }

    /**
     * Inserts an element at `index` within the deque, shifting all elements
     * with indices greater than or equal to `index` towards the back.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Panics
     *
     * Panics if `index` is strictly greater than the deque's length.
     *
     * # Examples
     *
     * ```kotlin
     * var vecDeque = VecDeque.new()
     * vecDeque.addLast('a')
     * vecDeque.addLast('b')
     * vecDeque.addLast('c')
     * check(vecDeque == listOf('a', 'b', 'c'))
     * vecDeque.insert(1, 'd')
     * check(vecDeque == listOf('a', 'd', 'b', 'c'))
     * vecDeque.insert(4, 'e')
     * check(vecDeque == listOf('a', 'd', 'b', 'c', 'e'))
     * ```
     */
    public open fun insert(index: Int, value: T) {
        this.insertMut(index, value)
    }

    /**
     * Inserts an element at `index` within the deque, shifting all elements
     * with indices greater than or equal to `index` towards the back, and
     * returning a reference to it.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Panics
     *
     * Panics if `index` is strictly greater than the deque's length.
     *
     * # Examples
     *
     * ```kotlin
     * var vecDeque = VecDeque.from(listOf(1, 2, 3))
     * val x = vecDeque.insertMut(1, 5)
     * x += 7
     * check(vecDeque == listOf(1, 12, 2, 3))
     * ```
     */
    public open fun insertMut(index: Int, value: T): T {
        check(index <= this.len()) { "index out of bounds" }
        if (this.isFull()) {
            this.grow()
        }
        val k = this.len - index
        return if (k < index) {
            // `index + 1` can't overflow, because if index was usize.Max, then either the
            // assert would've failed, or the deque would've tried to grow past usize.Max
            // and panicked.
            // ignore-tidy-undocumented-unchecked
            run {
                // see `remove()` for explanation why this wrapCopy() call is safe.
                this.wrapCopy(this.toWrappedIndex(index), this.toWrappedIndex(index + 1), k)
                this.len += 1
                this.bufferWrite(this.toWrappedIndex(index), value)
            }
        } else {
            val oldHead = this.head
            this.head = this.wrapSub(this.head, 1)
            // ignore-tidy-undocumented-unchecked
            run {
                this.wrapCopy(oldHead, this.head, index)
                this.len += 1
                this.bufferWrite(this.toWrappedIndex(index), value)
            }
        }
    }

    /**
     * Removes and returns the element at `index` from the deque.
     * Whichever end is closer to the removal point will be moved to make
     * room, and all the affected elements will be moved to new positions.
     * Returns `None` if `index` is out of bounds.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast('a')
     * buf.addLast('b')
     * buf.addLast('c')
     * check(buf == listOf('a', 'b', 'c'))
     * check(buf.remove(1) == 'b')
     * check(buf == listOf('a', 'c'))
     * ```
     */
    public open fun remove(index: Int): T? {
        if (this.len <= index) {
            return null
        }
        val wrappedIdx = this.toWrappedIndex(index)
        // ignore-tidy-undocumented-unchecked
        val elem = this.bufferRead(wrappedIdx)
        val k = this.len - index - 1
        if (k < index) {
            // SAFETY: due to the nature of the if-condition, whichever wrapCopy gets called,
            // its length argument will be at most `self.len / 2`, so there can't be more than
            // one overlapping area.
            this.wrapCopy(this.wrapAdd(wrappedIdx, 1), wrappedIdx, k)
            this.len -= 1
        } else {
            val oldHead = this.head
            this.head = this.toWrappedIndex(1)
            // ignore-tidy-undocumented-unchecked
            this.wrapCopy(oldHead, this.head, index)
            this.len -= 1
        }
        return elem
    }

    /**
     * Splits the deque into two at the given index.
     *
     * Returns a newly allocated `VecDeque`. `self` contains elements `[0, at)`,
     * and the returned deque contains elements `[at, len)`.
     *
     * Note that the capacity of `self` does not change.
     *
     * Element at index 0 is the front of the queue.
     *
     * # Panics
     *
     * Panics if `at> len`.
     *
     * # Examples
     *
     * ```kotlin
     * var buf: VecDeque = listOf('a', 'b', 'c').into()
     * val buf2 = buf.splitOff(1)
     * check(buf == listOf('a'))
     * check(buf2 == listOf('b', 'c'))
     * ```
     */
    public open fun splitOff(at: Int): VecDeque<T, A> {
        val len = this.len
        check(at <= len) { "`at` out of bounds" }
        val otherLen = len - at
        val other = VecDeque.withCapacityIn(otherLen, this.allocator().clone())
        val (firstHalf, secondHalf) = this.asSlices()
        val firstLen = firstHalf.len()
        val secondLen = secondHalf.len()
        // ignore-tidy-undocumented-unchecked
        if (at < firstLen) {
            // `at` lies in the first half.
            val amountInFirst = firstLen - at
            copyNonoverlapping(firstHalf.asPtr().add(at), other.ptr(), amountInFirst)
            // just take all of the second half.
            copyNonoverlapping(secondHalf.asPtr(), other.ptr().add(amountInFirst), secondLen)
        } else {
            // `at` lies in the second half, need to factor in the elements we skipped
            // in the first half.
            val offset = at - firstLen
            val amountInSecond = secondLen - offset
            copyNonoverlapping(secondHalf.asPtr().add(offset), other.ptr(), amountInSecond)
        }
        // Cleanup where the ends of the buffers are
        this.len = at
        other.len = otherLen
        return other
    }

    /**
     * Moves all the elements of `other` into `self`, leaving `other` empty.
     *
     * # Panics
     *
     * Panics if the new number of elements in self overflows a `Int`.
     *
     * # Examples
     *
     * ```kotlin
     * var buf: VecDeque = listOf(1, 2).into()
     * var buf2: VecDeque = listOf(3, 4).into()
     * buf.append(buf2)
     * check(buf == listOf(1, 2, 3, 4))
     * check(buf2 == listOf())
     * ```
     */
    public open fun append(other: VecDeque<T, A>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        if (((typeLayoutT).sizeBytes == 0L)) {
            this.len = (this.len.checkedAdd(other.len) ?: error("capacity overflow"))
            other.len = 0
            other.head = WrappedIndex.zero()
            return
        }
        this.reserve(other.len)
        val (left, right) = other.asSlices()
        // ignore-tidy-undocumented-unchecked
        run {
            this.copySlice(this.toWrappedIndex(this.len), left)
            // no overflow, because self.capacity() >= oldCap + left.len() >= self.len + left.len()
            this.copySlice(this.toWrappedIndex(this.len + left.len()), right)
        }
        // SAFETY: Update pointers after copying to avoid leaving doppelganger
        // in case of panics.
        this.len += other.len
        // Now that we own its values, forget everything in `other`.
        other.len = 0
        other.head = WrappedIndex.zero()
    }

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns false.
     * This method operates in place, visiting each element exactly once in the
     * original order, and preserves the order of the retained elements.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.extend(1 until 5)
     * buf.retain({ x -> x % 2 == 0 })
     * check(buf == listOf(2, 4))
     * ```
     *
     * Because the elements are visited exactly once in the original order,
     * external state may be used to decide which elements to keep.
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.extend(1 until 6)
     * val keep = booleanArrayOf(false, true, true, false, true)
     * var iter = keep.iter()
     * buf.retain({ iter.next()!! })
     * check(buf == listOf(2, 3, 5))
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        this.retainMut({ elem -> f(elem) })
    }

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns false.
     * This method operates in place, visiting each element exactly once in the
     * original order, and preserves the order of the retained elements.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.extend(1 until 5)
     * buf.retainMut({ x -> if (x % 2 == 0) {
     *     x += 1
     *     true
     * } else {
     *     false
     * } })
     * check(buf == listOf(3, 5))
     * ```
     */
    public open fun retainMut(f: (T) -> Boolean) {
        val len = this.len
        var idx = 0
        var cur = 0
        // Stage 1: All values are retained.
        while (cur < len) {
            if (!f(this[cur])) {
                cur += 1
                break
            }
            cur += 1
            idx += 1
        }
        // Stage 2: Swap retained value into current idx.
        while (cur < len) {
            if (!f(this[cur])) {
                cur += 1
                continue
            }
            this.swap(idx, cur)
            cur += 1
            idx += 1
        }
        // Stage 3: Truncate all values after idx.
        if (cur != idx) {
            this.truncate(idx)
        }
    }

    // Double the buffer size. This method is inline(never), so we expect it to only
    // be called in cold paths.
    // This may panic or abort
    public open fun grow() {
        // Extend or possibly remove this assertion when valid use-cases for growing the
        // buffer without it being full emerge
        check(this.isFull())
        val oldCap = this.capacity()
        this.buf.growOne()
        // ignore-tidy-undocumented-unchecked
        run {
            this.handleCapacityIncrease(oldCap)
        }
        check(!this.isFull())
    }

    /**
     * Modifies the deque in-place so that `len()` is equal to `newLen`,
     * either by removing excess elements from the back or by appending
     * elements generated by calling `generator` to the back.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(5)
     * buf.addLast(10)
     * buf.addLast(15)
     * check(buf == listOf(5, 10, 15))
     * buf.resizeWith(5, Default.default)
     * check(buf == listOf(5, 10, 15, 0, 0))
     * buf.resizeWith(2, { error("unreachable") })
     * check(buf == listOf(5, 10))
     * var state = 100
     * buf.resizeWith(5, {
     *     state += 1
     *     state
     * })
     * check(buf == listOf(5, 10, 101, 102, 103))
     * ```
     */
    public open fun resizeWith(newLen: Int, generator: () -> T) {
        val len = this.len
        if (newLen > len) {
            this.extend(repeatWith(generator).take(newLen - len))
        } else {
            this.truncate(newLen)
        }
    }

    /**
     * Rearranges the internal storage of this deque so it is one contiguous
     * slice, which is then returned.
     *
     * This method does not allocate and does not change the order of the
     * inserted elements. As it returns a mutable slice, this can be used to
     * sort a deque.
     *
     * Once the internal storage is contiguous, the [asSlices] and
     * [asMutSlices] methods will return the entire contents of the
     * deque in a single slice.
     *
     * [asSlices]: VecDeque.asSlices
     * [asMutSlices]: VecDeque.asMutSlices
     *
     * # Examples
     *
     * Sorting the content of a deque.
     *
     * ```kotlin
     * var buf = VecDeque.withCapacity(15)
     * buf.addLast(2)
     * buf.addLast(1)
     * buf.pushFront(3)
     * // sorting the deque
     * buf.makeContiguous().sort()
     * check(buf.asSlices() == Pair(((listOf(1, 2, 3) as Any?) as List<Any?>), ((listOf() as Any?) as List<Any?>)))
     * // sorting it in reverse order
     * buf.makeContiguous().sortBy({ a, b -> b.cmp(a) })
     * check(buf.asSlices() == Pair(((listOf(3, 2, 1) as Any?) as List<Any?>), ((listOf() as Any?) as List<Any?>)))
     * ```
     *
     * Getting immutable access to the contiguous slice.
     *
     * ```kotlin
     * import core.slice
     *
     * var buf = VecDeque.new()
     * buf.addLast(2)
     * buf.addLast(1)
     * buf.pushFront(3)
     * buf.makeContiguous()
     * if (buf.asSlices() != null) {
     *     val slice = buf.asSlices().first
     *     // we can now be sure that `slice` contains all elements of the deque,
     *     // while still having immutable access to `buf`.
     *     check(buf.len() == slice.len())
     *     check(slice == ((listOf(3, 2, 1) as Any?) as List<Any?>))
     * }
     * ```
     */
    public open fun makeContiguous(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutableList<T> {
        if (((typeLayoutT).sizeBytes == 0L)) {
            this.head = WrappedIndex.zero()
        }
        if (this.isContiguous()) {
            // ignore-tidy-undocumented-unchecked
            run {
                return linearMutSliceView(this.ptr().add(this.head.asIndex()), this.len.toInt(), PrimitiveLayouts.U8)
            }
        }
        val head = this.head
        val len = this.len
        val ptr = this.ptr()
        val cap = this.capacity()
        val free = cap - len
        val headLen = cap - head.asIndex()
        // tail <= head < capacity
        // head cannot be <= capacity, because we know that VecDeque is non-empty, since it is not
        // contiguous at this point
        val tail = WrappedIndex.fromArbitraryNumber(len - headLen)
        val tailLen = tail.asIndex()
        if (free >= headLen) {
            // there is enough free space to copy the head in one go,
            // this means that we first shift the tail backwards, and then
            // copy the head to the correct position.
            //
            // from: DEFGH....ABC
            // to:   ABCDEFGH....
            // ignore-tidy-undocumented-unchecked
            run {
                this.copy(WrappedIndex.zero(), WrappedIndex.fromArbitraryNumber(headLen), tailLen)
                // ...DEFGH.ABC
                this.copyNonoverlapping(head, WrappedIndex.zero(), headLen)
                // ABCDEFGH....
            }
            this.head = WrappedIndex.zero()
        } else if (free >= tailLen) {
            // there is enough free space to copy the tail in one go,
            // this means that we first shift the head forwards, and then
            // copy the tail to the correct position.
            //
            // from: FGH....ABCDE
            // to:   ...ABCDEFGH.
            // ignore-tidy-undocumented-unchecked
            run {
                this.copy(head, tail, headLen)
                // FGHABCDE....
                this.copyNonoverlapping(WrappedIndex.zero(), tail.add(headLen), tailLen)
                // ...ABCDEFGH.
            }
            this.head = tail
        } else {
            // `free` is smaller than both `headLen` and `tailLen`.
            // the general algorithm for this first moves the slices
            // right next to each other and then uses `slice.rotate`
            // to rotate them into place:
            //
            // initially:   HIJK..ABCDEFG
            // step 1:      ..HIJKABCDEFG
            // step 2:      ..ABCDEFGHIJK
            //
            // or:
            //
            // initially:   FGHIJK..ABCDE
            // step 1:      FGHIJKABCDE..
            // step 2:      ABCDEFGHIJK..
            // pick the shorter of the 2 slices to reduce the amount
            // of memory that needs to be moved around.
            if (headLen > tailLen) {
                // tail is shorter, so:
                // 1. copy tail forwards
                // 2. rotate used part of the buffer
                // 3. update head to point to the new beginning (which is just `free`)
                // ignore-tidy-undocumented-unchecked
                run {
                    // if there is no free space in the buffer, then the slices are already
                    // right next to each other and we don't need to move any memory.
                    if (free != 0) {
                        // because we only move the tail forward as much as there's free space
                        // behind it, we don't overwrite any elements of the head slice, and
                        // the slices end up right next to each other.
                        this.copy(WrappedIndex.zero(), WrappedIndex.fromArbitraryNumber(free), tailLen)
                    }
                    // We just copied the tail right next to the head slice,
                    // so all of the elements in the range are initialized
                    val slice = this.bufferRange(free until this.capacity())
                    // because the deque wasn't contiguous, we know that `tailLen<self.len == slice.len()`,
                    // so this will never panic.
                    slice.rotateLeft(tailLen)
                    // the used part of the buffer now is `free .. self.capacity()`, so set
                    // `head` to the beginning of that range.
                    this.head = WrappedIndex.fromArbitraryNumber(free)
                }
            } else {
                // head is shorter so:
                // 1. copy head backwards
                // 2. rotate used part of the buffer
                // 3. update head to point to the new beginning (which is the beginning of the buffer)
                // ignore-tidy-undocumented-unchecked
                run {
                    // if there is no free space in the buffer, then the slices are already
                    // right next to each other and we don't need to move any memory.
                    if (free != 0) {
                        // copy the head slice to lie right behind the tail slice.
                        this.copy(this.head, WrappedIndex.fromArbitraryNumber(tailLen), headLen)
                    }
                    // because we copied the head slice so that both slices lie right
                    // next to each other, all the elements in the range are initialized.
                    val slice1 = this.bufferRange(0 until this.len)
                    // because the deque wasn't contiguous, we know that `headLen<self.len == slice.len()`
                    // so this will never panic.
                    slice1.rotateRight(headLen)
                    // the used part of the buffer now is `0 .. self.len`, so set
                    // `head` to the beginning of that range.
                    this.head = WrappedIndex.zero()
                }
            }
        }
        // ignore-tidy-undocumented-unchecked
        return linearMutSliceView(ptr.add(this.head.asIndex()), this.len.toInt(), PrimitiveLayouts.U8)
    }

    /**
     * Rotates the double-ended queue `n` places to the left.
     *
     * Equivalently,
     * - Rotates item `n` into the first position.
     * - Pops the first `n` items and pushes them to the end.
     * - Rotates `len() - n` places to the right.
     *
     * # Panics
     *
     * If `n` is greater than `len()`. Note that `n == len()`
     * does _not_ panic and is a no-op rotation.
     *
     * # Complexity
     *
     * Takes `* O * (min(n, len() - n))` time and no extra space.
     *
     * # Examples
     *
     * ```kotlin
     * var buf: VecDeque = (0 until 10).collect()
     * buf.rotateLeft(3)
     * check(buf == listOf(3, 4, 5, 6, 7, 8, 9, 0, 1, 2))
     * for (i in 1 until 10) {
     *     check(i * 3 % 10 == buf[0])
     *     buf.rotateLeft(3)
     * }
     * check(buf == listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
     * ```
     */
    public open fun rotateLeft(n: Int) {
        check(n <= this.len())
        val k = this.len - n
        if (n <= k) {
            // SAFETY: Ensured by check.
            this.rotateLeftInner(n)
        } else {
            // SAFETY: Ensured by check.
            this.rotateRightInner(k)
        }
    }

    /**
     * Rotates the double-ended queue `n` places to the right.
     *
     * Equivalently,
     * - Rotates the first item into position `n`.
     * - Pops the last `n` items and pushes them to the front.
     * - Rotates `len() - n` places to the left.
     *
     * # Panics
     *
     * If `n` is greater than `len()`. Note that `n == len()`
     * does _not_ panic and is a no-op rotation.
     *
     * # Complexity
     *
     * Takes `* O * (min(n, len() - n))` time and no extra space.
     *
     * # Examples
     *
     * ```kotlin
     * var buf: VecDeque = (0 until 10).collect()
     * buf.rotateRight(3)
     * check(buf == listOf(7, 8, 9, 0, 1, 2, 3, 4, 5, 6))
     * for (i in 1 until 10) {
     *     check(0 == buf[i * 3 % 10])
     *     buf.rotateRight(3)
     * }
     * check(buf == listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
     * ```
     */
    public open fun rotateRight(n: Int) {
        check(n <= this.len())
        val k = this.len - n
        if (n <= k) {
            // SAFETY: Ensured by check.
            this.rotateRightInner(n)
        } else {
            // SAFETY: Ensured by check.
            this.rotateLeftInner(k)
        }
    }

    // SAFETY: the following two methods require that the rotation amount
    // be less than half the length of the deque.
    //
    // `wrapCopy` requires that `min(x, capacity() - x) + copyLen <= capacity()`,
    // but then `min` is never more than half the capacity, regardless of x,
    // so it's sound to call here because we're calling with something
    // less than half the length, which is never above half the capacity.
    public open fun rotateLeftInner(mid: Int) {
        check(mid * 2 <= this.len())
        // SAFETY: Upheld by caller.
        run {
            this.wrapCopy(this.head, this.toWrappedIndex(this.len), mid)
        }
        this.head = this.toWrappedIndex(mid)
    }

    public open fun rotateRightInner(k: Int) {
        check(k * 2 <= this.len())
        this.head = this.wrapSub(this.head, k)
        // SAFETY: Upheld by caller.
        run {
            this.wrapCopy(this.toWrappedIndex(this.len), this.head, k)
        }
    }

    /**
     * Binary searches this `VecDeque` for a given element.
     * If the `VecDeque` is not sorted, the returned result is unspecified and
     * meaningless.
     *
     * If the value is found then [Result.Ok] is returned, containing the
     * index of the matching element. If there are multiple matches, then any
     * one of the matches could be returned. If the value is not found then
     * [Result.Err] is returned, containing the index where a matching
     * element could be inserted while maintaining sorted order.
     *
     * See also [binarySearchBy], [binarySearchByKey], and [partitionPoint].
     *
     * [binarySearchBy]: VecDeque.binarySearchBy
     * [binarySearchByKey]: VecDeque.binarySearchByKey
     * [partitionPoint]: VecDeque.partitionPoint
     *
     * # Examples
     *
     * Looks up a series of four elements. The first is found, with a
     * uniquely determined position; the second and third are not
     * found; the fourth could match any position in `[1, 4]`.
     *
     * ```kotlin
     * val deque: VecDeque = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55).into()
     * check(deque.binarySearch(13) == Result.success(9))
     * check(deque.binarySearch(4) == Result.failure(7))
     * check(deque.binarySearch(100) == Result.failure(13))
     * val r = deque.binarySearch(1)
     * check(when {
     *     r is Ok && r.value in 1..4 -> true
     *     else -> false
     * })
     * ```
     *
     * If you want to insert an item to a sorted deque, while maintaining
     * sort order, consider using [partitionPoint]:
     *
     * ```kotlin
     * var deque: VecDeque = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55).into()
     * val num = 42
     * val idx = deque.partitionPoint({ x -> x <= num })
     * // If `num` is unique, `s.partitionPoint(| & x | x<num)` (with `<`) is equivalent to
     * // `s.binarySearch(num).unwrapOrElse(| x | x)`, but using `<=` may allow `insert`
     * // to shift less elements.
     * deque.insert(idx, num)
     * check(deque == listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 42, 55))
     * ```
     */
    public open fun binarySearch(x: T): Result<Int, Int> = this.binarySearchBy({ e -> e.cmp(x) })

    /**
     * Binary searches this `VecDeque` with a comparator function.
     *
     * The comparator function should return an order code that indicates
     * whether its argument is `Less`, `Equal` or `Greater` the desired
     * target.
     * If the `VecDeque` is not sorted or if the comparator function does not
     * implement an order consistent with the sort order of the underlying
     * `VecDeque`, the returned result is unspecified and meaningless.
     *
     * If the value is found then [Result.Ok] is returned, containing the
     * index of the matching element. If there are multiple matches, then any
     * one of the matches could be returned. If the value is not found then
     * [Result.Err] is returned, containing the index where a matching
     * element could be inserted while maintaining sorted order.
     *
     * See also [binarySearch], [binarySearchByKey], and [partitionPoint].
     *
     * [binarySearch]: VecDeque.binarySearch
     * [binarySearchByKey]: VecDeque.binarySearchByKey
     * [partitionPoint]: VecDeque.partitionPoint
     *
     * # Examples
     *
     * Looks up a series of four elements. The first is found, with a
     * uniquely determined position; the second and third are not
     * found; the fourth could match any position in `[1, 4]`.
     *
     * ```kotlin
     * val deque: VecDeque = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55).into()
     * check(deque.binarySearchBy({ x -> x.cmp(13) }) == Result.success(9))
     * check(deque.binarySearchBy({ x -> x.cmp(4) }) == Result.failure(7))
     * check(deque.binarySearchBy({ x -> x.cmp(100) }) == Result.failure(13))
     * val r = deque.binarySearchBy({ x -> x.cmp(1) })
     * check(when {
     *     r is Ok && r.value in 1..4 -> true
     *     else -> false
     * })
     * ```
     */
    public open fun binarySearchBy(f: (T) -> Ordering): Result<Int, Int> {
        val (front, back) = this.asSlices()
        val cmpBack = back.first()?.let(f)
        return if (cmpBack == Ordering.Equal) {
            Result.Ok<Int, Int>(front.len())
        } else if (cmpBack == Ordering.Less) {
            back.binarySearchBy(f).map({ idx -> idx + front.len() }).mapErr({ idx -> idx + front.len() })
        } else {
            front.binarySearchBy(f)
        }
    }

    /**
     * Binary searches this `VecDeque` with a key extraction function.
     *
     * Assumes that the deque is sorted by the key, for instance with
     * [makeContiguous.sortByKey] using the same key extraction function.
     * If the deque is not sorted by the key, the returned result is
     * unspecified and meaningless.
     *
     * If the value is found then [Result.Ok] is returned, containing the
     * index of the matching element. If there are multiple matches, then any
     * one of the matches could be returned. If the value is not found then
     * [Result.Err] is returned, containing the index where a matching
     * element could be inserted while maintaining sorted order.
     *
     * See also [binarySearch], [binarySearchBy], and [partitionPoint].
     *
     * [makeContiguous.sortByKey]: VecDeque.makeContiguous
     * [binarySearch]: VecDeque.binarySearch
     * [binarySearchBy]: VecDeque.binarySearchBy
     * [partitionPoint]: VecDeque.partitionPoint
     *
     * # Examples
     *
     * Looks up a series of four elements in a slice of pairs sorted by
     * their second elements. The first is found, with a uniquely
     * determined position; the second and third are not found; the
     * fourth could match any position in `[1, 4]`.
     *
     * ```kotlin
     * val deque: VecDeque = listOf(Pair(0, 0), Pair(2, 1), Pair(4, 1), Pair(5, 1), Pair(3, 1), Pair(1, 2), Pair(2, 3), Pair(4, 5), Pair(5, 8), Pair(3, 13), Pair(1, 21), Pair(2, 34), Pair(4, 55)).into()
     * check(deque.binarySearchByKey(13, { (a, b) -> b }) == Result.success(9))
     * check(deque.binarySearchByKey(4, { (a, b) -> b }) == Result.failure(7))
     * check(deque.binarySearchByKey(100, { (a, b) -> b }) == Result.failure(13))
     * val r = deque.binarySearchByKey(1, { (a, b) -> b })
     * check(when {
     *     r is Ok && r.value in 1..4 -> true
     *     else -> false
     * })
     * ```
     */
    public open fun <B> binarySearchByKey(b: B, f: (T) -> B): Result<Int, Int> = this.binarySearchBy({ k -> kmCompareValues(f(k), b).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } } })

    /**
     * Returns the index of the partition point according to the given predicate
     * (the index of the first element of the second partition).
     *
     * The deque is assumed to be partitioned according to the given predicate.
     * This means that all elements for which the predicate returns true are at the start of the deque
     * and all elements for which the predicate returns false are at the end.
     * For example, `[7, 15, 3, 5, 4, 12, 6]` is partitioned under the predicate `x % 2 != 0`
     * (all odd numbers are at the start, all even at the end).
     *
     * If the deque is not partitioned, the returned result is unspecified and meaningless,
     * as this method performs a kind of binary search.
     *
     * See also [binarySearch], [binarySearchBy], and [binarySearchByKey].
     *
     * [binarySearch]: VecDeque.binarySearch
     * [binarySearchBy]: VecDeque.binarySearchBy
     * [binarySearchByKey]: VecDeque.binarySearchByKey
     *
     * # Examples
     *
     * ```kotlin
     * val deque: VecDeque = listOf(1, 2, 3, 3, 5, 6, 7).into()
     * val i = deque.partitionPoint({ x -> x < 5 })
     * check(i == 4)
     * check(deque.iter().take(i).all({ x -> x < 5 }))
     * check(deque.iter().skip(i).all({ x -> !(x < 5) }))
     * ```
     *
     * If you want to insert an item to a sorted deque, while maintaining
     * sort order:
     *
     * ```kotlin
     * var deque: VecDeque = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55).into()
     * val num = 42
     * val idx = deque.partitionPoint({ x -> x < num })
     * deque.insert(idx, num)
     * check(deque == listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 42, 55))
     * ```
     */
    public open fun partitionPoint(pred: (T) -> Boolean): Int {
        val (front, back) = this.asSlices()
        return when (val matchVal = back.first()?.let(pred)) {
            is Some -> {
                back.partitionPoint(pred) + front.len()
            }
            else -> {
                front.partitionPoint(pred)
            }
        }
    }

    /**
     * Modifies the deque in-place so that `len()` is equal to newLen,
     * either by removing excess elements from the back or by appending clones of `value`
     * to the back.
     *
     * # Examples
     *
     * ```kotlin
     * var buf = VecDeque.new()
     * buf.addLast(5)
     * buf.addLast(10)
     * buf.addLast(15)
     * check(buf == listOf(5, 10, 15))
     * buf.resize(2, 0)
     * check(buf == listOf(5, 10))
     * buf.resize(5, 20)
     * check(buf == listOf(5, 10, 20, 20, 20))
     * ```
     */
    public open fun resize(newLen: Int, value: T) {
        if (newLen > this.len()) {
            val extra = newLen - this.len()
            this.extend(repeatN(value, extra))
        } else {
            this.truncate(newLen)
        }
    }

    /**
     * Clones the elements at the range `src` and appends them to the end.
     *
     * # Panics
     *
     * Panics if the starting index is greater than the end index
     * or if either index is greater than the length of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(dequeExtendFront)
     * public fun doctestEntry() {
     *     var characters = VecDeque.from(listOf('a', 'b', 'c', 'd', 'e'))
     *     characters.extendFromWithin(2 until Int.MAX_VALUE)
     *     check(characters == listOf('a', 'b', 'c', 'd', 'e', 'c', 'd', 'e'))
     *     var numbers = VecDeque.from(listOf(0, 1, 2, 3, 4))
     *     numbers.extendFromWithin(0 until 2)
     *     check(numbers == listOf(0, 1, 2, 3, 4, 0, 1))
     *     var strings = VecDeque.from(listOf("hello", "world", "!"))
     *     strings.extendFromWithin(1..2)
     *     check(strings == listOf("hello", "world", "!", "world", "!"))
     * }
     * ```
     */
    public open fun <R> extendFromWithin(src: R) where R : RangeBounds<Int> {
        val range = Range(src, 0 until this.len())
        this.reserve(range.len())
        // SAFETY:
        // - `slice.range` guarantees that the given range is valid for indexing self
        // - at least `range.len()` additional space is available
        run {
            this.specExtendFromWithin(range)
        }
    }

    /**
     * Clones the elements at the range `src` and prepends them to the front.
     *
     * # Panics
     *
     * Panics if the starting index is greater than the end index
     * or if either index is greater than the length of the vector.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(dequeExtendFront)
     * public fun doctestEntry() {
     *     var characters = VecDeque.from(listOf('a', 'b', 'c', 'd', 'e'))
     *     characters.prependFromWithin(2 until Int.MAX_VALUE)
     *     check(characters == listOf('c', 'd', 'e', 'a', 'b', 'c', 'd', 'e'))
     *     var numbers = VecDeque.from(listOf(0, 1, 2, 3, 4))
     *     numbers.prependFromWithin(0 until 2)
     *     check(numbers == listOf(0, 1, 0, 1, 2, 3, 4))
     *     var strings = VecDeque.from(listOf("hello", "world", "!"))
     *     strings.prependFromWithin(1..2)
     *     check(strings == listOf("world", "!", "hello", "world", "!"))
     * }
     * ```
     */
    public open fun <R> prependFromWithin(src: R) where R : RangeBounds<Int> {
        val range = Range(src, 0 until this.len())
        this.reserve(range.len())
        // SAFETY:
        // - `slice.range` guarantees that the given range is valid for indexing self
        // - at least `range.len()` additional space is available
        run {
            this.specPrependFromWithin(range)
        }
    }

    public override fun specExtendFromWithin(src: IntRange) {
        val dst = this.len()
        val count = src.end - src.start
        val src1 = src.start
        // SAFETY:
        // - Ranges do not overlap: src entirely spans initialized values, dst entirely spans uninitialized values.
        // - Ranges are in bounds: guaranteed by the caller.
        val ranges = this.nonoverlappingRanges(src1, dst, count, this.head)
        // `len` is updated after every clone to prevent leaking and
        // leave the deque in the right state when a clone implementation panics
        for ((src2, dst1, count1) in ranges) {
            for (offset in 0 until count1) {
                // SAFETY: The allocations of `dst` and `src` go up to `count` elems,
                // and `nonoverlappingRanges` ensures `dst` and `src` are valid
                // for writes and reads respectively.
                dst1.add(offset).write(src2.add(offset).clone())
                this.len += 1
            }
        }
    }

    public override fun specPrependFromWithin(src: IntRange) {
        val dst = 0
        val count = src.end - src.start
        val src1 = src.start + count
        val newHead = this.wrapSub(this.head, count)
        val cap = this.capacity()
        // SAFETY:
        // - Ranges do not overlap: src entirely spans initialized values, dst entirely spans uninitialized values.
        // - Ranges are in bounds: guaranteed by the caller.
        val ranges = this.nonoverlappingRanges(src1, dst, count, newHead)
        // Cloning is done in reverse because we prepend to the front of the deque,
        // we can't get holes in the *logical* buffer.
        // `head` and `len` are updated after every clone to prevent leaking and
        // leave the deque in the right state when a clone implementation panics
        // Clone the first range
        val (src2, dst1, count1) = ranges[1]
        for (offset in (0 until count1).rev()) {
            // ignore-tidy-undocumented-unchecked
            dst1.add(offset).write(src2.add(offset).clone())
            // ignore-tidy-undocumented-unchecked
            this.head = this.head.sub(1)
            this.len += 1
        }
        // Clone the second range
        val (src3, dst2, count2) = ranges[0]
        val iter = (0 until count2).rev()
        when (val matchVal = iter.next()) {
            null -> {}
            else -> {
                val offset1 = matchVal
                // ignore-tidy-undocumented-unchecked
                dst2.add(offset1).write(src3.add(offset1).clone())
                // After the first clone of the second range, wrap `head` around
                if (this.head.isZero()) {
                    // SAFETY: the wrapped index may be temporarily equal to the capacity even if it
                    // is not zero, because we subtract it one line below.
                    // Note: should `fromArbitraryNumber` be unchecked? its docs imply so...
                    this.head = WrappedIndex.fromArbitraryNumber(cap)
                }
                // ignore-tidy-undocumented-unchecked
                this.head = this.head.sub(1)
                this.len += 1
                // Continue like normal
                for (offset2 in iter) {
                    // ignore-tidy-undocumented-unchecked
                    dst2.add(offset2).write(src3.add(offset2).clone())
                    // ignore-tidy-undocumented-unchecked
                    this.head = this.head.sub(1)
                    this.len += 1
                }
            }
        }
    }

    public open fun eq(other: VecDeque<T, A>): Boolean {
        if (this.len != other.len()) {
            return false
        }
        val (sa, sb) = this.asSlices()
        val (oa, ob) = other.asSlices()
        return if (sa.len() == oa.len()) {
            sa == oa && sb == ob
        } else if (sa.len() < oa.len()) {
            // Always divisible in three sections, for example:
            // self:  [a b c|d e f]
            // other: [0 1 2 3|4 5]
            // front = 3, mid = 1,
            // [a b c] == [0 1 2] && [d] == [3] && [e f] == [4 5]
            val front = sa.len()
            val mid = oa.len() - front
            val (oaFront, oaMid) = oa.splitAt(front)
            val (sbMid, sbBack) = sb.splitAt(mid)
            check(sa.len() == oaFront.len())
            check(sbMid.len() == oaMid.len())
            check(sbBack.len() == ob.len())
            sa == oaFront && sbMid == oaMid && sbBack == ob
        } else {
            val front1 = oa.len()
            val mid1 = sa.len() - front1
            val (saFront, saMid) = sa.splitAt(front1)
            val (obMid, obBack) = ob.splitAt(mid1)
            check(saFront.len() == oa.len())
            check(saMid.len() == obMid.len())
            check(sb.len() == obBack.len())
            saFront == oa && saMid == obMid && sb == obBack
        }
    }

    public open fun partialCmp(other: VecDeque<T, A>): Ordering? = this.iter().partialCmp(other.iter())

    public open fun cmp(other: VecDeque<T, A>): Ordering = this.iter().cmp(other.iter())

    public override fun hashCode(): Int {
        var state = 0
        state.writeLengthPrefix(this.len)
        // It's not possible to use Hash.hashSlice on slices
        // returned by asSlices method as their length can vary
        // in otherwise identical deques.
        //
        // Hasher only guarantees equivalence for the exact same
        // set of calls to its methods.
        this.iter().forEach({ elem -> state = 31 * state + (elem).hashCode() })
        return state
    }

    public open fun index(index: Int): T = (this.get(index) ?: error("out of bounds access"))

    public open fun indexMut(index: Int): T = (this.getMut(index) ?: error("out of bounds access"))

    /**
     * Consumes the deque into a front-to-back iterator yielding elements by
     * value.
     */
    public open fun intoIter(): IntoIter<T, A> = IntoIter.new(this)

    public open fun <I : Iterable<T>> extend(iter: I) {
        VecDeque.specExtend(this, iter.iterator())
    }

    public open fun extendOne(elem: T) {
        this.addLast(elem)
    }

    public open fun extendReserve(additional: Int) {
        this.reserve(additional)
    }

    public open fun extendOneUnchecked(item: T) {
        // SAFETY: Our preconditions ensure the space has been reserved, and `extendReserve` is implemented correctly.
        run {
            this.pushUnchecked(item)
        }
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.specExtend(iter.iterator())
    }

    public open fun extendOneT(elem: T) {
        this.addLast(elem)
    }

    public open fun extendReserveT(additional: Int) {
        this.reserve(additional)
    }

    public open fun extendOneUncheckedT(item: T) {
        // SAFETY: Our preconditions ensure the space has been reserved, and `extendReserve` is implemented correctly.
        run {
            this.pushUnchecked(item)
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            head: WrappedIndex,
            len: Int,
            buf: RawVec<T, A>,
        ): VecDeque<T, A> = VecDeque<T, A>(
            head = head,
            len = len,
            bufCtorArg = buf,
        )

        /**
         * Creates an empty deque.
         *
         * # Examples
         *
         * ```kotlin
         * import alloc.Global
         *
         * val deque: VecDeque<Int, Global> = VecDeque.newIn(Global)
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): VecDeque<T, A> = VecDeque(head = WrappedIndex.zero(), len = 0, buf = RawVec.newIn(alloc))

        /**
         * Creates an empty deque with space for at least `capacity` elements.
         *
         * # Examples
         *
         * ```kotlin
         * import alloc.Global
         *
         * val deque: VecDeque<Int, Global> = VecDeque.withCapacityIn(10, Global)
         * ```
         */
        public fun <T, A : Allocator> withCapacityIn(capacity: Int, alloc: A): VecDeque<T, A> = VecDeque(head = WrappedIndex.zero(), len = 0, buf = RawVec.withCapacityIn(capacity, alloc))

        /**
         * Creates a `VecDeque` from a raw allocation, when the initialized
         * part of that allocation forms a *contiguous* subslice thereof.
         *
         * For use by `vec.IntoIter.intoVecdeque`
         *
         * # Safety
         *
         * All the usual requirements on the allocated memory like in
         * `List.fromRawPartsIn`, but takes a *range* of elements that are
         * initialized rather than only supporting `0 .. len`.  Requires that
         * `initialized.start` ≤ `initialized.end` ≤ `capacity`.
         * Also, `initialized.start` < `capacity`, unless both are 0.
         */
        public fun <T, A : Allocator> fromContiguousRawPartsIn(ptr: MutPtr, initialized: IntRange, capacity: Int, alloc: A): VecDeque<T, A> {
            check(initialized.start <= initialized.end)
            check(initialized.end <= capacity)
            check(initialized.start == 0 && capacity == 0 || initialized.start < capacity)
            // SAFETY: Our safety precondition guarantees the range length won't wrap,
            // that the allocation is valid for use in `RawVec` with `alloc`,
            // and that the range contains valid elements.
            // We have `head`, `len` ≤ `cap`, since `start`, `end` ≤ `cap`.
            // Also, `head` < `cap` unless `head` = `cap` = `0`.
            return VecDeque(head = WrappedIndex.fromArbitraryNumber(initialized.start), len = initialized.end.uncheckedSub(initialized.start), buf = RawVec.fromRawPartsIn(ptr, capacity, alloc))
        }

        /**
         * Turn a [Vec.T] into a [VecDeque.T].
         *
         * This conversion is guaranteed to run in *O*(1) time
         * and to not re-allocate the `List`s buffer or allocate
         * any additional memory.
         */
        public fun <T, A : Allocator> from(other: List<T>): VecDeque<T, A> {
            val (ptr, len, cap, alloc) = other.intoRawPartsWithAllocator()
            return VecDeque(head = WrappedIndex.zero(), len = len, buf = RawVec.fromRawPartsIn(ptr, cap, alloc))
        }
    }
}

/**
 * Creates an empty deque.
 */
public fun <T> defaultVecDequeT(): VecDeque<T, Global> = VecDeque.new()

/**
 * Creates an empty deque.
 *
 * # Examples
 *
 * ```kotlin
 * import alloc.Global
 *
 * val deque: VecDeque<UInt, Global> = VecDeque.new()
 * ```
 */
public fun <T> VecDeque.Companion.new(): VecDeque<T, Global> {
    // Note(const-Note): This should just be `VecDeque.newIn(Global)` once that hits stable.
    return VecDeque(head = WrappedIndex.zero(), len = 0, buf = RawVec.new())
}

/**
 * Creates an empty deque with space for at least `capacity` elements.
 *
 * # Examples
 *
 * ```kotlin
 * import alloc.Global
 *
 * val deque: VecDeque<Int, Global> = VecDeque.withCapacity(10)
 * ```
 */
public fun <T> VecDeque.Companion.withCapacity(capacity: Int): VecDeque<T, Global> = VecDeque.withCapacityIn(capacity, Global())

/**
 * Creates an empty deque with space for at least `capacity` elements.
 *
 * # Errors
 *
 * Returns an error if the capacity exceeds `Long.MAX` _bytes_,
 * or if the allocator reports allocation failure.
 *
 * # Examples
 *
 * ```kotlin
 * import alloc.Global
 *
 * val deque: VecDeque<UInt, Global> = VecDeque.tryWithCapacity(10).getOrElse { return Result.failure(it) }
 * ```
 */
public fun <T> VecDeque.Companion.tryWithCapacity(capacity: Int): Result<VecDeque<T, Global>, TryReserveError> {
    return Result.Ok<VecDeque<T, Global>, TryReserveError>(VecDeque(head = WrappedIndex.zero(), len = 0, buf = when (val tryResult = RawVec.tryWithCapacityIn(capacity, Global())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<VecDeque<T, Global>, TryReserveError>(tryResult.errValue()) }))
}

public fun <T : TrivialClone, A : Allocator> VecDeque<T, A>.specExtendFromWithin(src: IntRange) {
    val dst = this.len()
    val count = src.end - src.start
    val src1 = src.start
    // SAFETY:
    // - Ranges do not overlap: src entirely spans initialized values, dst entirely spans uninitialized values.
    // - Ranges are in bounds: guaranteed by the caller.
    val ranges = this.nonoverlappingRanges(src1, dst, count, this.head)
    for ((src2, dst1, count1) in ranges) {
        // SAFETY: Ditto.
        copyNonoverlapping(src2, dst1, count1)
    }
    // SAFETY:
    // - The elements were just initialized by `copyNonoverlapping`
    this.len += count
}

public fun <T : TrivialClone, A : Allocator> VecDeque<T, A>.specPrependFromWithin(src: IntRange) {
    val dst = 0
    val count = src.end - src.start
    val src1 = src.start + count
    val newHead = this.wrapSub(this.head, count)
    // SAFETY:
    // - Ranges do not overlap: src entirely spans initialized values, dst entirely spans uninitialized values.
    // - Ranges are in bounds: guaranteed by the caller.
    val ranges = this.nonoverlappingRanges(src1, dst, count, newHead)
    for ((src2, dst1, count1) in ranges) {
        // SAFETY: Ditto.
        copyNonoverlapping(src2, dst1, count1)
    }
    // SAFETY:
    // - The elements were just initialized by `copyNonoverlapping`
    this.head = newHead
    this.len += count
}

public fun <T, U, A : Allocator> VecDeque<T, A>.eq(other: List<U>): Boolean {
    if (this.len() != other.size) {
        return false
    }
    val (sa, sb) = this.asSlices()
    val (oa, ob) = other.splitAt(sa.len())
    return sa == oa && sb == ob
}

public fun <T, U, A : Allocator> VecDeque<T, A>.eqUSlice(other: List<U>): Boolean {
    if (this.len() != other.size) {
        return false
    }
    val (sa, sb) = this.asSlices()
    val (oa, ob) = other.splitAt(sa.len())
    return sa == oa && sb == ob
}

public fun <T, U, A : Allocator> VecDeque<T, A>.eq(other: MutableList<U>): Boolean {
    if (this.len() != other.size) {
        return false
    }
    val (sa, sb) = this.asSlices()
    val (oa, ob) = other.splitAt(sa.len())
    return sa == oa && sb == ob
}

public fun <T, U, A : Allocator> VecDeque<T, A>.eq(other: List<U>, n: Int): Boolean {
    if (this.len() != other.size) {
        return false
    }
    val (sa, sb) = this.asSlices()
    val (oa, ob) = other.splitAt(sa.len())
    return sa == oa && sb == ob
}

public fun <T, U, A : Allocator> VecDeque<T, A>.eqUArrayn(other: List<U>, n: Int): Boolean {
    if (this.len() != other.size) {
        return false
    }
    val (sa, sb) = this.asSlices()
    val (oa, ob) = other.splitAt(sa.len())
    return sa == oa && sb == ob
}

public fun <T, I : Iterable<T>> fromIteratorFromIterVecDequeT(iter: I): VecDeque<T, Global> = SpecFromIter.SpecFromIter(iter.iterator())

/**
 * Converts a `[T; N]` into a `VecDeque<T>`.
 *
 * ```kotlin
 * val deq1 = VecDeque.from(listOf(1, 2, 3, 4))
 * val deq2: VecDeque = listOf(1, 2, 3, 4).into()
 * check(deq1 == deq2)
 * ```
 */
public fun <T> fromVecDequeT(arr: List<T>, n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): VecDeque<T, Global> {
    val deq = VecDeque.withCapacity(n)
    val arr1 = arr
    if (!((typeLayoutT).sizeBytes == 0L)) {
        // SAFETY: VecDeque.withCapacity ensures that there is enough capacity.
        run {
            copyNonoverlapping(arr1.asPtr(), deq.ptr(), n)
        }
    }
    deq.head = WrappedIndex.zero()
    deq.len = n
    return deq
}

/**
 * Runs the destructor for all items in the slice when it gets dropped (normally or
 * during unwinding).
 */
public open class Dropper<T>(
    public var value: MutableList<T>,
) {
    public var field0: MutableList<T>
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        run {
            dropInPlace(this.value)
        }
    }
}
