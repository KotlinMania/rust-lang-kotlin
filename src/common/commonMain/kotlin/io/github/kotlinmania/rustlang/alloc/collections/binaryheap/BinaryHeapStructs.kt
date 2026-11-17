package io.github.kotlinmania.rustlang.alloc.collections.binaryheap

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.swap
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

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

public typealias Source<T, A> = IntoIter<T, A>

/**
 * A priority queue implemented with a binary heap.
 *
 * This will be a max-heap.
 *
 * It is a logic error for an item to be modified in such a way that the
 * item's ordering relative to any other item, as determined by the [Ord]
 * trait, changes while it is in the heap. This is normally only possible
 * through interior mutability, global state, I/O, or unchecked code. The
 * behavior resulting from such a logic error is not specified, but will
 * be encapsulated to the `BinaryHeap` that observed the logic error and not
 * result in undefined behavior. This could include panics, incorrect results,
 * aborts, memory leaks, and non-termination.
 *
 * As long as no elements change their relative order while being in the heap
 * as described above, the API of `BinaryHeap` guarantees that the heap
 * invariant remains intact i.e. its methods all behave as documented. For
 * example if a method is documented as iterating in sorted order, that's
 * guaranteed to work as long as elements in the heap have not changed order,
 * even in the presence of closures getting unwinded out of, iterators getting
 * leaked, and similar foolishness.
 *
 * # Examples
 *
 * ```kotlin
 * // Type inference lets us omit an explicit type signature (which
 * // would be `BinaryHeap<Int>` in this example).
 * var heap = BinaryHeap.new()
 * // We can use peek to look at the next item in the heap. In this case,
 * // there's no items in there yet so we get None.
 * check(heap.peek() == null)
 * // Let's add some scores...
 * heap.push(1)
 * heap.push(5)
 * heap.push(2)
 * // Now peek shows the most important item in the heap.
 * check(heap.peek() == 5)
 * // We can check the length of a heap.
 * check(heap.len() == 3)
 * // We can iterate over the items in the heap, although they are returned in
 * // a random order.
 * for (x in heap) {
 *     println("$x")
 * }
 * // If we instead pop these scores, they should come back in order.
 * check(heap.pop() == 5)
 * check(heap.pop() == 2)
 * check(heap.pop() == 1)
 * check(heap.pop() == null)
 * // We can clear the heap of any remaining items.
 * heap.clear()
 * // The heap should now be empty.
 * check(heap.isEmpty())
 * ```
 *
 * A `BinaryHeap` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val heap = BinaryHeap.from(listOf(1, 5, 2))
 * ```
 *
 * ## Min-heap
 *
 * Either [core.cmp.Reverse] or a custom [Ord] implementation can be used to
 * make `BinaryHeap` a min-heap. This makes `heap.pop()` return the smallest
 * value instead of the greatest one.
 *
 * ```kotlin
 * import std.cmp.Reverse
 *
 * var heap = BinaryHeap.new()
 * // Wrap values in `Reverse`
 * heap.push(Reverse(1))
 * heap.push(Reverse(5))
 * heap.push(Reverse(2))
 * // If we pop these scores now, they should come back in the reverse order.
 * check(heap.pop() == Reverse(1))
 * check(heap.pop() == Reverse(2))
 * check(heap.pop() == Reverse(5))
 * check(heap.pop() == null)
 * ```
 *
 * # Time complexity
 *
 * | [push]  | [pop]         | [peek]/[peek\_mut] |
 * |---------|---------------|--------------------|
 * | *O*(1)~ | *O*(log(*n*)) | *O*(1)             |
 *
 * The value for `push` is an expected cost; the method documentation gives a
 * more detailed analysis.
 *
 * [push]: BinaryHeap.push
 * [pop]: BinaryHeap.pop
 * [peek]: BinaryHeap.peek
 * [peek\_mut]: BinaryHeap.peekMut
 */
public open class BinaryHeap<T, A : Allocator>(
    public var data: MutableList<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    public open fun clone(): BinaryHeap<T, A> = BinaryHeap(data = this.data.toMutableList())

    /**
     * Overwrites the contents of `self` with a clone of the contents of `source`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     *
     * See [Vec.cloneFrom] for more details.
     */
    public open fun cloneFrom(source: BinaryHeap<T, A>) {
        run { this.data.clear(); this.data.addAll(source.data) }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Returns a mutable reference to the greatest item in the binary heap, or
     * `None` if it is empty.
     *
     * Note: If the `PeekMut` value is leaked, some heap elements might get
     * leaked along with it, but the remaining elements will remain a valid
     * heap.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * check(heap.peekMut() == null)
     * heap.push(1)
     * heap.push(5)
     * heap.push(2)
     * when (val matchVal = heap.peekMut()) {
     *     null -> {}
     *     else -> {
     *         val value = matchVal
     *         value = 0
     *     }
     * }
     * check(heap.peek() == 2)
     * ```
     *
     * # Time complexity
     *
     * If the item is modified then the worst case time complexity is *O*(log(*n*)),
     * otherwise it's *O*(1).
     */
    public open fun peekMut(): PeekMut<T, A>? =
        if (this.isEmpty()) {
            null
        } else {
            PeekMut(heap = this, originalLen = null)
        }

    /**
     * Removes the greatest item from the binary heap and returns it, or `None` if it
     * is empty.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.from(listOf(1, 3))
     * check(heap.pop() == 3)
     * check(heap.pop() == 1)
     * check(heap.pop() == null)
     * ```
     *
     * # Time complexity
     *
     * The worst case cost of `pop` on a heap containing *n* elements is *O*(log(*n*)).
     */
    public open fun pop(): T? =
        this.data.removeLastOrNull()?.let({ item ->
            if (!this.isEmpty()) {
                swap(item, this.data[0])
                // SAFETY: !self.isEmpty() means that self.len() > 0
                this.siftDownToBottom(0)
            }
            item
        })

    /**
     * Removes and returns the greatest item from the binary heap if the predicate
     * returns `true`, or [None] if the predicate returns false or the heap
     * is empty (the predicate will not be called in that case).
     *
     * # Examples
     *
     * ```kotlin
     * @feature(binaryHeapPopIf)
     * public fun doctestEntry() {
     *     var heap = BinaryHeap.from(listOf(1, 2))
     *     val pred = { x: Int -> x % 2 == 0 }
     *     check(heap.popIf(pred) == 2)
     *     check(heap.asSlice() == listOf(1))
     *     check(heap.popIf(pred) == null)
     *     check(heap.asSlice() == listOf(1))
     * }
     * ```
     *
     * # Time complexity
     *
     * The worst case cost of `popIf` on a heap containing *n* elements is *O*(log(*n*)).
     */
    public open fun popIf(predicate: (T) -> Boolean): T? {
        val first = (this.peek() ?: return null)
        return if (predicate(first)) {
            this.pop()
        } else {
            null
        }
    }

    /**
     * Pushes an item onto the binary heap.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * heap.push(3)
     * heap.push(5)
     * heap.push(1)
     * check(heap.len() == 3)
     * check(heap.peek() == 5)
     * ```
     *
     * # Time complexity
     *
     * The expected cost of `push`, averaged over every possible ordering of
     * the elements being pushed, and over a sufficiently large number of
     * pushes, is *O*(1). This is the most meaningful cost metric when pushing
     * elements that are *not* already in any sorted pattern.
     *
     * The time complexity degrades if elements are pushed in predominantly
     * ascending order. In the worst case, elements are pushed in ascending
     * sorted order and the amortized cost per push is *O*(log(*n*)) against a heap
     * containing *n* elements.
     *
     * The worst case cost of a *single* call to `push` is *O*(*n*). The worst case
     * occurs when capacity is exhausted and needs a resize. The resize cost
     * has been amortized in the previous figures.
     */
    public open fun push(item: T) {
        val oldLen = this.len()
        this.data.add(item)
        // SAFETY: Since we pushed a new item it means that
        // oldLen = self.len() - 1 < self.len()
        this.siftUp(0, oldLen)
    }

    /**
     * Consumes the `BinaryHeap` and returns a vector in sorted
     * (ascending) order.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import vec
     *
     * var heap = BinaryHeap.from(listOf(1, 2, 4, 5, 7))
     * heap.push(6)
     * heap.push(3)
     * val vec = heap.intoSortedVec()
     * check(vec == listOf(1, 2, 3, 4, 5, 6, 7))
     * ```
     */
    public open fun intoSortedVec(): List<T> {
        var end = this.len()
        while (end > 1) {
            end -= 1
            // SAFETY: `end` goes from `self.len() - 1` to 1 (both included),
            // so it's always a valid index to access.
            // It is safe to access index 0 (i.e. `ptr`), because
            // 1 <= end < self.len(), which means self.len() >= 2.
            run {
                val ptr = this.data.asMutPtr()
                swap(ptr, MutPtr((ptr).rawAddress + ((end).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)))
            }
            // SAFETY: `end` goes from `self.len() - 1` to 1 (both included) so:
            // 0 < 1 <= end <= self.len() - 1 < self.len()
            // Which means 0 < end and end < self.len().
            this.siftDownRange(0, end)
        }
        return this.intoVec()
    }

    // The implementations of siftUp and siftDown use unchecked blocks in
    // order to move an element out of the vector (leaving behind a
    // hole), shift along the others and move the removed element back into the
    // vector at the final location of the hole.
    // The `Hole` type is used to represent this, and make sure
    // the hole is filled back at the end of its scope, even on panic.
    // Using a hole reduces the constant factor compared to using swaps,
    // which involves twice as many moves.

    /**
     * # Safety
     *
     * The caller must guarantee that `pos<self.len()`.
     *
     * Returns the new position of the element.
     */
    public open fun siftUp(start: Int, pos: Int): Int {
        // Take out the value at `pos` and create a hole.
        // SAFETY: The caller guarantees that pos < self.len()
        val hole = Hole.new(this.data, pos)
        while (hole.pos() > start) {
            val parent = (hole.pos() - 1) / 2
            // SAFETY: hole.pos() > start >= 0, which means hole.pos() > 0
            // and so hole.pos() - 1 can't underflow.
            // This guarantees that parent < hole.pos() so
            // it's a valid index and also != hole.pos().
            if (hole.element() <= hole.get(parent)) {
                break
            }
            // SAFETY: Same as above
            hole.moveTo(parent)
        }
        return hole.pos()
    }

    /**
     * Take an element at `pos` and move it down the heap,
     * while its children are larger.
     *
     * Returns the new position of the element.
     *
     * # Safety
     *
     * The caller must guarantee that `pos<end <= self.len()`.
     */
    public open fun siftDownRange(pos: Int, end: Int): Int {
        // SAFETY: The caller guarantees that pos < end <= self.len().
        val hole = Hole.new(this.data, pos)
        var child = 2 * hole.pos() + 1
        // Loop invariant: child == 2 * hole.pos() + 1.
        while (child <= end.saturatingSub(2)) {
            // compare with the greater of the two children
            // SAFETY: child < end - 1 < self.len() and
            // child + 1 < end <= self.len(), so they're valid indexes.
            // child == 2 * hole.pos() + 1 != hole.pos() and
            // child + 1 == 2 * hole.pos() + 2 != hole.pos().
            // Note: 2 * hole.pos() + 1 or 2 * hole.pos() + 2 could overflow
            // if T is a ZST
            child += (hole.get(child) <= hole.get(child + 1)).toInt()
            // if we are already in order, stop.
            // SAFETY: child is now either the old child or the old child+1
            // We already proven that both are < self.len() and != hole.pos()
            if (hole.element() >= hole.get(child)) {
                return hole.pos()
            }
            // SAFETY: same as above.
            hole.moveTo(child)
            child = 2 * hole.pos() + 1
        }
        // SAFETY: && short circuit, which means that in the
        // second condition it's already true that child == end - 1 < self.len().
        if (child == end - 1 && hole.element() < hole.get(child)) {
            // SAFETY: child is already proven to be a valid index and
            // child == 2 * hole.pos() + 1 != hole.pos().
            hole.moveTo(child)
        }
        return hole.pos()
    }

    /**
     * # Safety
     *
     * The caller must guarantee that `pos<self.len()`.
     */
    public open fun siftDown(pos: Int): Int {
        val len = this.len()
        // SAFETY: pos < len is guaranteed by the caller and
        // obviously len = self.len() <= self.len().
        return this.siftDownRange(pos, len)
    }

    /**
     * Take an element at `pos` and move it all the way down the heap,
     * then sift it up to its position.
     *
     * Note: This is faster when the element is known to be large / should
     * be closer to the bottom.
     *
     * # Safety
     *
     * The caller must guarantee that `pos<self.len()`.
     */
    public open fun siftDownToBottom(pos: Int) {
        var pos1 = pos
        val end = this.len()
        val start = pos1
        // SAFETY: The caller guarantees that pos < self.len().
        val hole = Hole.new(this.data, pos1)
        var child = 2 * hole.pos() + 1
        // Loop invariant: child == 2 * hole.pos() + 1.
        while (child <= end.saturatingSub(2)) {
            // SAFETY: child < end - 1 < self.len() and
            // child + 1 < end <= self.len(), so they're valid indexes.
            // child == 2 * hole.pos() + 1 != hole.pos() and
            // child + 1 == 2 * hole.pos() + 2 != hole.pos().
            // Note: 2 * hole.pos() + 1 or 2 * hole.pos() + 2 could overflow
            // if T is a ZST
            child += (hole.get(child) <= hole.get(child + 1)).toInt()
            // SAFETY: Same as above
            hole.moveTo(child)
            child = 2 * hole.pos() + 1
        }
        if (child == end - 1) {
            // SAFETY: child == end - 1 < self.len(), so it's a valid index
            // and child == 2 * hole.pos() + 1 != hole.pos().
            hole.moveTo(child)
        }
        pos1 = hole.pos()
        (hole).let { }
        // SAFETY: pos is the position in the hole and was already proven
        // to be a valid index.
        this.siftUp(start, pos1)
    }

    /**
     * Rebuild assuming data[0..start] is still a proper heap.
     */
    public open fun rebuildTail(start: Int) {
        fun log2Fast(x: Int): Int = (32u - x.leadingZeros() - 1u).toInt()
        if (start == this.len()) {
            return
        }
        val tailLen = this.len() - start
        // `rebuild` takes O(self.len()) operations
        // and about 2 * self.len() comparisons in the worst case
        // while repeating `siftUp` takes O(tailLen * log(start)) operations
        // and about 1 * tailLen * log2(start) comparisons in the worst case,
        // assuming start >= tailLen. For larger heaps, the crossover point
        // no longer follows this reasoning and was determined empirically.
        val betterToRebuild =
            if (start < tailLen) {
                true
            } else if (this.len() <= 2048) {
                2 * this.len() < tailLen * log2Fast(start)
            } else {
                2 * this.len() < tailLen * 11
            }
        if (betterToRebuild) {
            this.rebuild()
        } else {
            for (i in start until this.len()) {
                // SAFETY: The index `i` is always less than self.len().
                this.siftUp(0, i)
            }
        }
    }

    public open fun rebuild() {
        var n = this.len() / 2
        while (n > 0) {
            n -= 1
            // SAFETY: n starts from self.len() / 2 and goes down to 0.
            // The only case whenBranch(n < self.len()) is if
            // self.len() == 0, but it's ruled out by the loop condition.
            this.siftDown(n)
        }
    }

    /**
     * Moves all the elements of `other` into `self`, leaving `other` empty.
     *
     * For performance, `self` and `other` may be swapped before appending.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var a = BinaryHeap.from(listOf(-10, 1, 2, 3, 3))
     * var b = BinaryHeap.from(listOf(-20, 5, 43))
     * a.append(b)
     * check(a.intoSortedVec() == listOf(-20, -10, 1, 2, 3, 3, 5, 43))
     * check(b.isEmpty())
     * ```
     */
    public open fun append(other: BinaryHeap<T, A>) {
        if (this.len() < other.len()) {
            swap(this, other)
        }
        val start = this.data.size
        this.data.append(other.data)
        this.rebuildTail(start)
    }

    /**
     * Clears the binary heap, returning an iterator over the removed elements
     * in heap order. If the iterator is dropped before being fully consumed,
     * it drops the remaining elements in heap order.
     *
     * The returned iterator keeps a mutable borrow on the heap to optimize
     * its implementation.
     *
     * Note:
     * * `.drainSorted()` is *O*(*n* \* log(*n*)); much slower than `.drain()`.
     *   You should use the latter for most cases.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(binaryHeapDrainSorted)
     * public fun doctestEntry() {
     *     var heap = BinaryHeap.from(listOf(1, 2, 3, 4, 5))
     *     check(heap.len() == 5)
     *     (heap.drainSorted()).let { }
     *     // removes all elements in heap order
     *     check(heap.len() == 0)
     * }
     * ```
     */
    public open fun drainSorted(): DrainSorted<T, A> = DrainSorted(inner = this)

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns
     * `false`. The elements are visited in unsorted (and unspecified) order.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.from(listOf(-10, -5, 1, 2, 4, 13))
     * heap.retain({ x -> x % 2 == 0 })
     * // only keep even numbers
     * check(heap.intoSortedVec() == listOf(-10, 2, 4))
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        // rebuildStart will be updated to the first touched element below, and the rebuild will
        // only be done for the tail.
        val guard = RebuildOnDrop(rebuildFrom = this.len(), heap = this)
        var i = 0
        guard.heap.data.retain({ e ->
            val keep = f(e)
            if (!keep && i < guard.rebuildFrom) {
                guard.rebuildFrom = i
            }
            i += 1
            keep
        })
    }

    /**
     * Returns an iterator visiting all values in the underlying vector, in
     * arbitrary order.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val heap = BinaryHeap.from(listOf(1, 2, 3, 4))
     * // Print 1, 2, 3, 4 in arbitrary order
     * for (x in heap.iter()) {
     *     println("$x")
     * }
     * ```
     */
    public open fun iter(): Iter<T> = Iter(iter = ArrayDeque(this.data))

    /**
     * Returns an iterator which retrieves elements in heap order.
     *
     * This method consumes the original heap.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import vec.Vec
     *
     * @feature(binaryHeapIntoIterSorted)
     * public fun doctestEntry() {
     *     val heap = BinaryHeap.from(listOf(1, 2, 3, 4, 5))
     *     check(heap.intoIterSorted().take(2).collect<Vec>() == listOf(5, 4))
     * }
     * ```
     */
    public open fun intoIterSorted(): IntoIterSorted<T, A> = IntoIterSorted(inner = this)

    /**
     * Returns the greatest item in the binary heap, or `None` if it is empty.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * check(heap.peek() == null)
     * heap.push(1)
     * heap.push(5)
     * heap.push(2)
     * check(heap.peek() == 5)
     * ```
     *
     * # Time complexity
     *
     * Cost is *O*(1) in the worst case.
     */
    public open fun peek(): T? = this.data.getOrNull(0)

    /**
     * Returns the number of elements the binary heap can hold without reallocating.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.withCapacity(100)
     * check(heap.capacity() >= 100)
     * heap.push(4)
     * ```
     */
    public open fun capacity(): Int = this.data.capacity()

    /**
     * Reserves the minimum capacity for at least `additional` elements more than
     * the current length. Unlike [reserve], this will not
     * deliberately over-allocate to speculatively avoid frequent allocations.
     * After calling `reserveExact`, capacity will be greater than or equal to
     * `self.len() + additional`. Does nothing if the capacity is already
     * sufficient.
     *
     * [reserve]: BinaryHeap.reserve
     *
     * # Panics
     *
     * Panics if the new capacity overflows [usize].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * heap.reserveExact(100)
     * check(heap.capacity() >= 100)
     * heap.push(4)
     * ```
     *
     * [reserve]: BinaryHeap.reserve
     */
    public open fun reserveExact(additional: Int) {
        this.data.reserveExact(additional)
    }

    /**
     * Reserves capacity for at least `additional` elements more than the
     * current length. The allocator may reserve more space to speculatively
     * avoid frequent allocations. After calling `reserve`,
     * capacity will be greater than or equal to `self.len() + additional`.
     * Does nothing if capacity is already sufficient.
     *
     * # Panics
     *
     * Panics if the new capacity overflows [usize].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * heap.reserve(100)
     * check(heap.capacity() >= 100)
     * heap.push(4)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.data.reserve(additional)
    }

    /**
     * Tries to reserve the minimum capacity for at least `additional` elements
     * more than the current length. Unlike [tryReserve], this will not
     * deliberately over-allocate to speculatively avoid frequent allocations.
     * After calling `tryReserveExact`, capacity will be greater than or
     * equal to `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: BinaryHeap.tryReserve
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * fun findMaxSlow(data: List<UInt>): Result<UInt?> {
     *     var heap = BinaryHeap.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     heap.tryReserveExact(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     heap.extend(data.iter())
     *     return Result.success(heap.pop())
     * }
     * ```
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.data.tryReserveExact(additional)

    /**
     * Tries to reserve capacity for at least `additional` elements more than the
     * current length. The allocator may reserve more space to speculatively
     * avoid frequent allocations. After calling `tryReserve`, capacity will be
     * greater than or equal to `self.len() + additional` if it returns
     * `Ok(())`. Does nothing if capacity is already sufficient. This method
     * preserves the contents even if an error occurs.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * fun findMaxSlow(data: List<UInt>): Result<UInt?> {
     *     var heap = BinaryHeap.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     heap.tryReserve(data.size).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     heap.extend(data.iter())
     *     return Result.success(heap.pop())
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.data.tryReserve(additional)

    /**
     * Discards as much additional capacity as possible.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import alloc.Global
     *
     * var heap: BinaryHeap<Int, Global> = BinaryHeap.withCapacity(100)
     * check(heap.capacity() >= 100)
     * heap.shrinkToFit()
     * check(heap.capacity() == 0)
     * ```
     */
    public open fun shrinkToFit() {
        this.data.shrinkToFit()
    }

    /**
     * Discards capacity with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Examples
     *
     * ```kotlin
     * import alloc.Global
     *
     * var heap: BinaryHeap<Int, Global> = BinaryHeap.withCapacity(100)
     * check(heap.capacity() >= 100)
     * heap.shrinkTo(10)
     * check(heap.capacity() >= 10)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.data.shrinkTo(minCapacity)
    }

    /**
     * Returns a slice of all values in the underlying vector, in arbitrary
     * order.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val heap = BinaryHeap.from(listOf(1, 2, 3, 4, 5, 6, 7))
     * io.sink().write(heap.asSlice()).unwrap()
     * ```
     */
    public open fun asSlice(): List<T> = this.data.asSlice()

    /**
     * Returns a mutable slice of all values in the underlying vector.
     *
     * # Safety
     *
     * The caller must ensure that the slice remains a max-heap, i.e. for all indices
     * `0<i<slice.len()`, `slice[(i - 1) / 2] >= slice[i]`, before the borrow ends
     * and the binary heap is used.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(binaryHeapAsMutSlice)
     * public fun doctestEntry() {
     *     var heap = BinaryHeap.from(listOf(1, 2, 3, 4, 5, 6, 7))
     *     for (value in heap.asMutSlice()) {
     *         value = value.saturatingMul(2)
     *     }
     * }
     * ```
     */
    public open fun asMutSlice(): MutableList<T> = this.data.asMutSlice()

    /**
     * Consumes the `BinaryHeap` and returns the underlying vector
     * in arbitrary order.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import vec
     *
     * val heap = BinaryHeap.from(listOf(1, 2, 3, 4, 5, 6, 7))
     * val vec = heap.intoVec()
     * // Will print in some order
     * for (x in vec) {
     *     println("$x")
     * }
     * ```
     */
    public open fun intoVec(): List<T> = this.into()

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.data.allocator()

    /**
     * Returns the length of the binary heap.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val heap = BinaryHeap.from(listOf(1, 3))
     * check(heap.len() == 2)
     * ```
     */
    public open fun len(): Int = this.data.size

    /**
     * Checks if the binary heap is empty.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.new()
     * check(heap.isEmpty())
     * heap.push(3)
     * heap.push(5)
     * heap.push(1)
     * check(!heap.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Clears the binary heap, returning an iterator over the removed elements
     * in arbitrary order. If the iterator is dropped before being fully
     * consumed, it drops the remaining elements in arbitrary order.
     *
     * The returned iterator keeps a mutable borrow on the heap to optimize
     * its implementation.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.from(listOf(1, 3))
     * check(!heap.isEmpty())
     * for (x in heap.drain()) {
     *     println("$x")
     * }
     * check(heap.isEmpty())
     * ```
     */
    public open fun drain(): Drain<T, A> = Drain(iter = this.data.drain(0 until 0))

    /**
     * Drops all items from the binary heap.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var heap = BinaryHeap.from(listOf(1, 3))
     * check(!heap.isEmpty())
     * heap.clear()
     * check(heap.isEmpty())
     * ```
     */
    public open fun clear() {
        this.drain()
    }

    /**
     * Creates a consuming iterator, that is, one that moves each value out of
     * the binary heap in arbitrary order. The binary heap cannot be used
     * after calling this.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val heap = BinaryHeap.from(listOf(1, 2, 3, 4))
     * // Print 1, 2, 3, 4 in arbitrary order
     * for (x in heap.intoIter()) {
     *     // x has type i32, not &i32
     *     println("$x")
     * }
     * ```
     */
    public open fun intoIter(): IntoIter<T, A> = IntoIter(iter = ArrayDeque(this.data))

    public open fun <I : Iterable<T>> extend(iter: I) {
        val guard = RebuildOnDrop(rebuildFrom = this.len(), heap = this)
        guard.heap.data.extend(iter)
    }

    public open fun extendOne(item: T) {
        this.push(item)
    }

    public open fun extendReserve(additional: Int) {
        this.reserve(additional)
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.extend(iter.iterator().cloned())
    }

    public open fun extendOneT(item: T) {
        this.push(item)
    }

    public open fun extendReserveT(additional: Int) {
        this.reserve(additional)
    }

    public companion object {
        /**
         * Creates an empty `BinaryHeap` as a max-heap, using `A` as allocator.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val heap: BinaryHeap<Int, System> = BinaryHeap.newIn(System)
         * }
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): BinaryHeap<T, A> = BinaryHeap(data = Vec.newIn(alloc))

        /**
         * Creates an empty `BinaryHeap` with at least the specified capacity, using `A` as allocator.
         *
         * The binary heap will be able to hold at least `capacity` elements without
         * reallocating. This method is allowed to allocate for more elements than
         * `capacity`. If `capacity` is zero, the binary heap will not allocate.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val heap: BinaryHeap<Int, System> = BinaryHeap.withCapacityIn(10, System)
         * }
         * ```
         */
        public fun <T, A : Allocator> withCapacityIn(capacity: Int, alloc: A): BinaryHeap<T, A> = BinaryHeap(data = Vec.withCapacityIn(capacity, alloc))

        /**
         * Creates a `BinaryHeap` using the supplied `vec`. This does not rebuild the heap,
         * so `vec` must already be a max-heap.
         *
         * # Safety
         *
         * The supplied `vec` must be a max-heap, i.e. for all indices `0<i<vec.len()`,
         * `vec[(i - 1) / 2] >= vec[i]`.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * import vec
         *
         * @feature(binaryHeapFromRawVec)
         * public fun doctestEntry() {
         *     val heap = BinaryHeap.from(listOf(1, 2, 3))
         *     val vec = heap.intoVec()
         *     // Safety: vec is the output of heap.fromVec(), so is a max-heap.
         *     var newHeap = BinaryHeap.fromRawVec(vec)
         *     check(newHeap.pop() == 3)
         *     check(newHeap.pop() == 2)
         *     check(newHeap.pop() == 1)
         *     check(newHeap.pop() == null)
         * }
         * ```
         */
        public fun <T, A : Allocator> fromRawVec(vec: List<T>): BinaryHeap<T, A> = BinaryHeap(data = vec)

        /**
         * Converts a `List<T>` into a `BinaryHeap<T>`.
         *
         * This conversion happens in-place, and has *O*(*n*) time complexity.
         */
        public fun <T, A : Allocator> from(vec: List<T>): BinaryHeap<T, A> {
            val heap = BinaryHeap(data = vec)
            heap.rebuild()
            return heap
        }
    }
}

/**
 * Creates an empty `BinaryHeap<T>`.
 */
public fun <T> defaultBinaryHeapT(): BinaryHeap<T, Global> = BinaryHeap.new()

/**
 * Creates an empty `BinaryHeap` as a max-heap.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var heap = BinaryHeap.new()
 * heap.push(4)
 * ```
 */
public fun <T> BinaryHeap.Companion.new(): BinaryHeap<T, Global> = BinaryHeap(data = mutableListOf())

/**
 * Creates an empty `BinaryHeap` with at least the specified capacity.
 *
 * The binary heap will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the binary heap will not allocate.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var heap = BinaryHeap.withCapacity(10)
 * heap.push(4)
 * ```
 */
public fun <T> BinaryHeap.Companion.withCapacity(capacity: Int): BinaryHeap<T, Global> = BinaryHeap(data = ArrayList(capacity))

/**
 * ```kotlin
 * var h1 = BinaryHeap.from(listOf(1, 4, 2, 3))
 * var h2: BinaryHeap = listOf(1, 4, 2, 3).into()
 * while (true) {
 *     val matchVal = (h1.pop().zip(h2.pop()))
 *     if (matchVal == null) break
 *     val a = matchVal.first
 *     val b = matchVal.second
 *     check(a == b)
 * }
 * ```
 */
public fun <T> fromBinaryHeapT(arr: List<T>, n: Int): BinaryHeap<T, Global> = BinaryHeap.fromIter(arr)

public fun <T, I : Iterable<T>> fromIteratorFromIterBinaryHeapT(iter: I): BinaryHeap<T, Global> = BinaryHeap.from(iter.iterator().collect<Vec>())

/**
 * Structure wrapping a mutable reference to the greatest item on a
 * `BinaryHeap`.
 *
 * This `class` is created by the [peekMut] method on [BinaryHeap]. See
 * its documentation for more.
 *
 * [peekMut]: BinaryHeap.peekMut
 */
public open class PeekMut<T, A : Allocator> internal constructor(
    heapCtorArg: BinaryHeap<T, A>,
    originalLenCtorArg: NonZero<Int, NonZeroUsizeInner>?,
) {
    public var heap: BinaryHeap<T, A> = heapCtorArg
        internal set

    // If a setLen + siftDown are required, this is Some. If a T has not
    // yet been exposed to peekMut()'s caller, it's None.
    public var originalLen: NonZero<Int, NonZeroUsizeInner>? = originalLenCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "PeekMut").field(this.heap.data[0]).finish(); Result.Ok<Unit, Error>(Unit) }

    public open fun drop() {
        when (val matchVal = this.originalLen) {
            null -> {}
            else -> {
                val originalLen = matchVal
                // SAFETY: That's how many elements were in the Vec at the time of
                // the PeekMut.derefMut call, and therefore also at the time of
                // the BinaryHeap.peekMut call. Since the PeekMut did not end up
                // getting leaked, we are now undoing the leak amplification that
                // the DerefMut prepared for.
                this.heap.data.setLen(originalLen.get())
                // SAFETY: PeekMut is only instantiated for non-empty heaps.
                this.heap.siftDown(0)
            }
        }
    }

    public open fun deref(): T {
        check(!this.heap.isEmpty())
        // SAFETY: PeekMut is only instantiated for non-empty heaps
        return this.heap.data.getUnchecked(0)
    }

    public open fun derefMut(): T {
        check(!this.heap.isEmpty())
        val len = this.heap.len()
        if (len > 1) {
            // Here we preemptively leak all the rest of the underlying vector
            // after the currently max element. If the caller mutates the T
            // we're about to give them, and then leaks the PeekMut, all these
            // elements will remain leaked. If they don't leak the PeekMut, then
            // either Drop or PeekMut.pop will un-leak the vector elements.
            //
            // This is technique is described throughout several other places in
            // the standard library as "leak amplification".
            // SAFETY: len > 1 so len != 0.
            this.originalLen = NonZero.newUnchecked<NonZeroUsizeInner, Int>(len)
            // SAFETY: len > 1 so all this does for now is leak elements,
            // which is safe.
            this.heap.data.setLen(1)
        }
        // SAFETY: PeekMut is only instantiated for non-empty heaps
        return this.heap.data.getUncheckedMut(0)
    }

    /**
     * Sifts the current element to its new position.
     *
     * Afterwards refers to the new element. Returns if the element changed.
     *
     * ## Examples
     *
     * The condition can be used to upper bound all elements in the heap. When only few elements
     * are affected, the heap's sort ensures this is faster than a reconstruction from the raw
     * element list and requires no additional allocation.
     *
     * ```kotlin
     * import alloc.Global
     *
     * @feature(binaryHeapPeekMutRefresh)
     * public fun doctestEntry() {
     *     var heap: BinaryHeap<UInt, Global> = (0 until 128).collect()
     *     var peek = heap.peekMut().unwrap()
     *     while (true) {
     *         peek = 99
     *         if (!peek.refresh()) {
     *             break
     *         }
     *     }
     *     // Post condition, this is now an upper bound.
     *     check(peek < 100)
     * }
     * ```
     *
     * When the element remains the maximum after modification, the peek remains unchanged:
     *
     * ```kotlin
     * import alloc.Global
     *
     * @feature(binaryHeapPeekMutRefresh)
     * public fun doctestEntry() {
     *     var heap: BinaryHeap<UInt, Global> = listOf(1, 2, 3).into()
     *     var peek = heap.peekMut().unwrap()
     *     check(peek == 3)
     *     peek = 42
     *     // When we refresh, the peek is updated to the new maximum.
     *     check(!peek.refresh()) { "42 is even larger than 3" }
     *     check(peek == 42)
     * }
     * ```
     */
    public open fun refresh(): Boolean {
        // The length of the underlying heap is unchanged by sifting down. The value stored for leak
        // amplification thus remains accurate. We erase the leak amplification firstly because the
        // operation is then equivalent to constructing a new PeekMut and secondly this avoids any
        // future complication where originalLen being non-empty would be interpreted as the heap
        // having been leak amplified instead of checking the heap itself.
        return when (val matchVal = this.originalLen.also { this.originalLen = null }) {
            null -> {
                // The element was not modified.
                false
            }
            else -> {
                val originalLen = matchVal
                // SAFETY: This is how many elements were in the Vec at the time of
                // the BinaryHeap.peekMut call.
                this.heap.data.setLen(originalLen.get())
                // The length of the heap did not change by sifting, upholding our own invariants.
                // SAFETY: PeekMut is only instantiated for non-empty heaps.
                (this.heap.siftDown(0)) != 0
            }
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            heap: BinaryHeap<T, A>,
            originalLen: NonZero<Int, NonZeroUsizeInner>?,
        ): PeekMut<T, A> = PeekMut<T, A>(
            heapCtorArg = heap,
            originalLenCtorArg = originalLen,
        )

        /**
         * Removes the peeked value from the heap and returns it.
         */
        public fun <T, A : Allocator> pop(thisRef: PeekMut<T, A>): T {
            when (val matchVal = thisRef.originalLen.also { thisRef.originalLen = null }) {
                null -> {}
                else -> {
                    val originalLen = matchVal
                    // SAFETY: This is how many elements were in the Vec at the time of
                    // the BinaryHeap.peekMut call.
                    thisRef.heap.data.setLen(originalLen.get())
                    // Unlike in Drop, here we don't also need to do a siftDown even if
                    // the caller could've mutated the element. It is removed from the
                    // heap on the next line and pop() is not sensitive to its value.
                }
            }
            // SAFETY: Have a `PeekMut` element proves that the associated binary heap being non-empty,
            // so the `pop` operation will not fail.
            return thisRef.heap.pop().unwrapUnchecked()
        }
    }
}

public open class RebuildOnDrop<T, A : Allocator> internal constructor(
    heapCtorArg: BinaryHeap<T, A>,
    public var rebuildFrom: Int,
) {
    public var heap: BinaryHeap<T, A> = heapCtorArg
        internal set

    public open fun drop() {
        this.heap.rebuildTail(this.rebuildFrom)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            heap: BinaryHeap<T, A>,
            rebuildFrom: Int,
        ): RebuildOnDrop<T, A> = RebuildOnDrop<T, A>(
            heapCtorArg = heap,
            rebuildFrom = rebuildFrom,
        )
    }
}

/**
 * Hole represents a hole in a slice i.e., an index without valid value
 * (because it was moved from or duplicated).
 * In drop, `Hole` will restore the slice by filling the hole
 * position with the value that was originally removed.
 */
public open class Hole<T>(
    public var data: MutableList<T>,
    public var elt: T,
    public var pos: Int,
) {
    public open fun pos(): Int = this.pos

    /**
     * Returns a reference to the element removed.
     */
    public open fun element(): T = this.elt

    /**
     * Returns a reference to the element at `index`.
     *
     * # Safety
     *
     * `index` must be within the data slice and not equal to the current position.
     */
    public open fun get(index: Int): T {
        check(index != this.pos)
        check(index < this.data.size)
        // SAFETY: Upheld by caller.
        return this.data.getUnchecked(index)
    }

    /**
     * Move hole to new location
     *
     * # Safety
     *
     * `index` must be within the data slice and not equal to the current position.
     */
    public open fun moveTo(index: Int) {
        check(index != this.pos)
        check(index < this.data.size)
        val ptr = this.data.asMutPtr()
        // ignore-tidy-undocumented-unchecked
        run {
            val indexPtr: ConstPtr = MutPtr((ptr).rawAddress + ((index).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            val holePtr = MutPtr((ptr).rawAddress + ((this.pos).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            copyNonoverlapping(indexPtr, holePtr, 1, typeLayoutT = UnresolvedTypeLayout<Any?>())
        }
        this.pos = index
    }

    public open fun drop() {
        // fill the hole again
        val pos = this.pos
        // ignore-tidy-undocumented-unchecked
        run {
            copyNonoverlapping(this.elt, this.data.getUncheckedMut(pos), 1)
        }
    }

    public companion object {
        /**
         * Creates a new `Hole` at index `pos`.
         *
         * # Safety
         *
         * `pos` must be within the data slice.
         */
        public fun <T> new(data: MutableList<T>, pos: Int): Hole<T> {
            check(pos < data.size)
            // SAFETY: Caller ensures pos is inside the slice.
            val elt = read(data.getUnchecked(pos))
            return Hole(data = data, elt = elt, pos = pos)
        }
    }
}

/**
 * An iterator over the elements of a `BinaryHeap`.
 *
 * This `class` is created by [BinaryHeap.iter]. See its
 * documentation for more.
 *
 * [iter]: BinaryHeap.iter
 */
public open class Iter<T>(
    public var iter: ArrayDeque<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "Iter").field(this.iter.toList()).finish(); Result.Ok<Unit, Error>(Unit) }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<T> = Iter(iter = ArrayDeque(this.iter))

    public open fun next(): T? = this.iter.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun last(): T? = this.iter.last()

    public open fun nextBack(): T? = this.iter.removeLastOrNull()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        /**
         * Creates an empty `binaryHeap.Iter`.
         *
         * ```kotlin
         * val iter: Iter<UByte> = Iter.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T> default(): Iter<T> = Iter(iter = ArrayDeque())
    }
}

/**
 * An owning iterator over the elements of a `BinaryHeap`.
 *
 * This `class` is created by [BinaryHeap.intoIter]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: BinaryHeap.intoIter
 */
public open class IntoIter<T, A : Allocator>(
    public var iter: ArrayDeque<T>,
) : TrustedFused, SourceIter, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<T, A> = IntoIter(iter)

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.iter.allocator()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "IntoIter").field(this.iter.toList()).finish(); Result.Ok<Unit, Error>(Unit) }

    public open fun next(): T? = this.iter.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun nextBack(): T? = this.iter.removeLastOrNull()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    // In addition to the SAFETY invariants of the following three unchecked traits
    // also refer to the vec.inPlaceCollect module documentation to get an overview
    public open fun asInner(): IntoIter<T, A> = this
}

/**
 * Creates an empty `binaryHeap.IntoIter`.
 *
 * ```kotlin
 * val iter: IntoIter<UByte> = IntoIter.default()
 * check(iter.len() == 0)
 * ```
 */
public fun <T> defaultIntoIterT(): IntoIter<T, Global> = IntoIter(iter = ArrayDeque())

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = NonZero.new(1)

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = NonZero.new(1)

public fun <I> IntoIter<I, Global>.asIntoIter(): ArrayDeque<I> = this.iter

public open class IntoIterSorted<T, A : Allocator> internal constructor(
    innerCtorArg: BinaryHeap<T, A>,
) : TrustedLen<Any>, Iterable<T> {
    public var inner: BinaryHeap<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIterSorted.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIterSorted<T, A> = IntoIterSorted(inner)

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.inner.allocator()

    public open fun next(): T? = this.inner.pop()

    public open fun sizeHint(): Pair<Int, Int?> {
        val exact = this.inner.len()
        return Pair(exact, exact)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: BinaryHeap<T, A>,
        ): IntoIterSorted<T, A> = IntoIterSorted<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * A draining iterator over the elements of a `BinaryHeap`.
 *
 * This `class` is created by [BinaryHeap.drain]. See its
 * documentation for more.
 *
 * [drain]: BinaryHeap.drain
 */
public open class Drain<T, A : Allocator> internal constructor(
    iterCtorArg: io.github.kotlinmania.rustlang.alloc.vec.drain.Drain<T, A>,
) : Iterable<T> {
    public var iter: io.github.kotlinmania.rustlang.alloc.vec.drain.Drain<T, A> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.iter.allocator()

    public open fun next(): T? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun nextBack(): T? = this.iter.nextBack()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            iter: io.github.kotlinmania.rustlang.alloc.vec.drain.Drain<T, A>,
        ): Drain<T, A> = Drain<T, A>(
            iterCtorArg = iter,
        )
    }
}

/**
 * A draining iterator over the elements of a `BinaryHeap`.
 *
 * This `class` is created by [BinaryHeap.drainSorted]. See its
 * documentation for more.
 *
 * [drainSorted]: BinaryHeap.drainSorted
 */
public open class DrainSorted<T, A : Allocator> internal constructor(
    innerCtorArg: BinaryHeap<T, A>,
) : TrustedLen<Any>, Iterable<T> {
    public var inner: BinaryHeap<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@DrainSorted.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.inner.allocator()

    /**
     * Removes heap elements in heap order.
     */
    public open fun drop() {
        open class DropGuard<T, A : Allocator>(
            public var value: DrainSorted<T, A>,
        ) {
            public var field0: DrainSorted<T, A>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                while (this.value.inner.pop() != null) {}
            }
        }
        while (true) {
            val matchVal = this.inner.pop()
            if (matchVal == null) break
            val item = matchVal
            val guard = DropGuard(this)
            (item).let { }
            forget(guard)
        }
    }

    public open fun next(): T? = this.inner.pop()

    public open fun sizeHint(): Pair<Int, Int?> {
        val exact = this.inner.len()
        return Pair(exact, exact)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: BinaryHeap<T, A>,
        ): DrainSorted<T, A> = DrainSorted<T, A>(
            innerCtorArg = inner,
        )
    }
}
