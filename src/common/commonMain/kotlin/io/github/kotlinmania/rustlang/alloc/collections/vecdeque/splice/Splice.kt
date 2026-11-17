// port-lint: source collections/vec_deque/splice.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.splice

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.drain.Drain
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.num.checkedAdd

/**
 * A splicing iterator for `VecDeque`.
 *
 * This struct is created by [`VecDeque.splice`][VecDeque.splice].
 * See its documentation for more.
 *
 * # Example
 *
 * ```kotlin
 * var v = ArrayDeque(mutableListOf(0, 1, 2))
 * val new = listOf(7, 8)
 * val iter: Splice = v.splice(1 until Int.MAX_VALUE, new)
 * ```
 */
public open class Splice<I, A : Allocator, Item> internal constructor(
    drainCtorArg: Drain<Item, A>,
    public var replaceWith: I,
) : Iterable<Item> {
    public var drain: Drain<Item, A> = drainCtorArg
        internal set

    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Splice.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Item? = this.drain.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.drain.sizeHint()

    public open fun nextBack(): Item? = this.drain.nextBack()

    // See also: [vec.Splice].
    public open fun drop() {
        // This will set drain.remaining to 0, so its drop won't try to read deallocated memory on
        // drop.
        this.drain.byRef().forEach({ drop(it) })
        // At this point draining is done and the only remaining tasks are splicing
        // and moving things into the final place.
        val tailLen = this.drain.tailLen
        // #elements behind the drain
        // ignore-tidy-undocumented-unchecked
        run {
            if (tailLen == 0) {
                this.drain.deque.asMut().extend(this.replaceWith.byRef())
                return
            }
            // First fill the range left by drain().
            if (!this.drain.fill(this.replaceWith)) {
                return
            }
            // There may be more elements. Use the lower bound as an estimate.
            // Note: Is the upper bound a better guess? Or something else?
            val (lowerBound, upperBound) = this.replaceWith.sizeHint()
            if (lowerBound > 0) {
                this.drain.moveTail(lowerBound)
                if (!this.drain.fill(this.replaceWith)) {
                    return
                }
            }
            // Collect any remaining elements.
            // This is a zero-length vector which does not allocate if `lowerBound` was exact.
            val collected = this.replaceWith.byRef().toMutableList().intoIter()
            // Now we have an exact count.
            if (collected.len() > 0) {
                this.drain.moveTail(collected.len())
                val filled = this.drain.fill(collected)
                check(filled)
                check(collected.len() == 0)
            }
        }
        // Let `Drain.drop` move the tail back if necessary and restore `deque.len`.
    }

    public companion object {
        public operator fun <I, A : Allocator, Item> invoke(
            drain: Drain<Item, A>,
            replaceWith: I,
        ): Splice<I, A, Item> = Splice<I, A, Item>(
            drainCtorArg = drain,
            replaceWith = replaceWith,
        )
    }
}

/**
 * Private helper methods for `Splice.drop`
 */
/**
 * The range from `self.deque.len` to `self.deque.len + self.drainLen` contains elements that
 * have been moved out.
 * Fill that range as much as possible with new elements from the `replaceWith` iterator.
 * Returns `true` if we filled the entire range. (`replaceWith.next()` didn’t return `None`.)
 *
 * # Safety
 *
 * self.deque must be valid. self.deque.len and self.deque.len + self.drainLen must be less
 * than twice the deque's capacity.
 */
public fun <T, A : Allocator, I : Iterator<T>> Drain<T, A>.fill(replaceWith: I): Boolean {
    // ignore-tidy-undocumented-unchecked
    val deque = this.deque.asMut()
    val rangeStart = deque.len
    val rangeEnd = rangeStart + this.drainLen
    for (idx in rangeStart until rangeEnd) {
        when (val matchVal = replaceWith.next()) {
            null -> {
                return false
            }
            else -> {
                val newItem = matchVal
                val index = deque.toWrappedIndex(idx)
                // ignore-tidy-undocumented-unchecked
                deque.bufferWrite(index, newItem)
                deque.len += 1
                this.drainLen -= 1
            }
        }
    }
    return true
}

/**
 * Makes room for inserting more elements before the tail.
 *
 * # Safety
 *
 * self.deque must be valid.
 */
public fun <T, A : Allocator> Drain<T, A>.moveTail(additional: Int) {
    // SAFETY: Upheld by caller.
    val deque = this.deque.asMut()
    // `Drain.new` modifies the deque's len (so does `Drain.fill` here)
    // directly with the start bound of the range passed into
    // `VecDeque.splice`. This causes a few different issue:
    // - Most notably, there will be a hole at the end of the
    // buffer when our buffer resizes in the case that our
    // data wraps around.
    // - We cannot use `VecDeque.reserve` directly because
    // how it reserves more space and updates the `VecDeque`s
    // `head` field accordingly depends on the `VecDeque`s
    // actual `len`.
    // - We cannot just directly modify `VecDeque`s `len` and
    // and call `VecDeque.reserve` afterward because if
    // `VecDeque.reserve` panics on capacity overflow,
    // well now our `VecDeque`s head does not get updated
    // and we still have a potential hole at the end of the
    // buffer.
    // Therefore, we manually reserve additional space (if necessary)
    // based on calculating the actual `len` of the `VecDeque` and adjust
    // `VecDeque`s len right *after* the panicking region of `VecDeque.reserve`
    // (that is `RawVec` `reserve()` call)
    val drainStart = deque.len
    val tailStart = drainStart + this.drainLen
    // Actual VecDeque's len = drainStart + tailLen + drainLen
    val actualLen = drainStart + this.tailLen + this.drainLen
    val newCap = (actualLen.checkedAdd(additional) ?: error("capacity overflow"))
    val oldCap = deque.capacity()
    if (newCap > oldCap) {
        deque.buf.reserve(actualLen, additional)
        // If newCap doesn't panic, we can safely set the `VecDeque` len to its
        // actual len; this needs to be done in order to set deque.head correctly
        // on `VecDeque.handleCapacityIncrease`
        deque.len = actualLen
        // SAFETY: this cannot panic since our internal buffer's newCap should
        // be bigger than the passed in oldCap
        run {
            deque.handleCapacityIncrease(oldCap)
        }
    }
    val newTailStart = tailStart + additional
    // ignore-tidy-undocumented-unchecked
    run {
        deque.wrapCopy(deque.toWrappedIndex(tailStart), deque.toWrappedIndex(newTailStart), this.tailLen)
    }
    // revert the `VecDeque` len to what it was before
    deque.len = drainStart
    this.drainLen += additional
}
