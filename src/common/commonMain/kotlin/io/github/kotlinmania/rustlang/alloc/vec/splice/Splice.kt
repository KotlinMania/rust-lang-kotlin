// port-lint: source vec/splice.rs
package io.github.kotlinmania.rustlang.alloc.vec.splice

import io.github.kotlinmania.rustlang.alloc.vec.drain.Drain
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.mem.copy
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * A splicing iterator for `List`.
 *
 * This struct is created by [Vec.splice].
 * See its documentation for more.
 *
 * # Example
 *
 * ```kotlin
 * var v = mutableListOf(0, 1, 2)
 * val new = listOf(7, 8)
 * val iter: Splice = v.splice(1 until Int.MAX_VALUE, new)
 * ```
 */
public open class Splice<I, A : Allocator> internal constructor(
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

    // See also: [collections.vecDeque.Splice].
    public open fun drop() {
        this.drain.byRef().forEach(drop)
        // At this point draining is done and the only remaining tasks are splicing
        // and moving things into the final place.
        // Which means we can replace the slice.Iter with pointers that won't point to deallocated
        // memory, so that Drain.drop is still allowed to call iter.len(), otherwise it would break
        // the ptr.offsetFromUnsigned contract.
        this.drain.iter = listOf().iter()
        // ignore-tidy-undocumented-unchecked
        run {
            if (this.drain.tailLen == 0) {
                this.drain.vec.asMut().extend(this.replaceWith.byRef())
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
        // Let `Drain.drop` move the tail back if necessary and restore `vec.len`.
    }

    public companion object {
        public operator fun <I, A : Allocator> invoke(
            drain: Drain<Item, A>,
            replaceWith: I,
        ): Splice<I, A> = Splice<I, A>(
            drainCtorArg = drain,
            replaceWith = replaceWith,
        )
    }
}

/**
 * Private helper methods for `Splice.drop`
 */
/**
 * The range from `self.vec.len` to `self.tailStart` contains elements
 * that have been moved out.
 * Fill that range as much as possible with new elements from the `replaceWith` iterator.
 * Returns `true` if we filled the entire range. (`replaceWith.next()` didn’t return `None`.)
 */
public fun <T, A : Allocator, I : Iterator<T>> Drain<T, A>.fill(replaceWith: I): Boolean {
    // SAFETY: Pointer is valid.
    val vec = this.vec.asMut()
    val rangeStart = vec.len
    val rangeEnd = this.tailStart
    // The elements in this range are not initialized so we avoid creating a slice.
    for (idx in rangeStart until rangeEnd) {
        val newItem = replaceWith.next() ?: return false
        // ignore-tidy-undocumented-unchecked
        vec.asMutPtr().add(idx).write(newItem)
        vec.len += 1
    }
    return true
}

/**
 * Makes room for inserting more elements before the tail.
 */
public fun <T, A : Allocator> Drain<T, A>.moveTail(additional: Int) {
    // SAFETY: Pointer is valid.
    val vec = this.vec.asMut()
    val len = this.tailStart + this.tailLen
    vec.buf.reserve(len, additional)
    val newTailStart = this.tailStart + additional
    // ignore-tidy-undocumented-unchecked
    run {
        val src = vec.asPtr().add(this.tailStart)
        val dst = vec.asMutPtr().add(newTailStart)
        copy(src, dst, this.tailLen)
    }
    this.tailStart = newTailStart
}
