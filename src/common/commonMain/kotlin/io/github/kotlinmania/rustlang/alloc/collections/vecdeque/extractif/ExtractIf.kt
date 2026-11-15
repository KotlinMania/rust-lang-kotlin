// port-lint: source collections/vec_deque/extract_if.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.extractif

import io.github.kotlinmania.rustlang.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * An iterator which uses a closure to determine if an element should be removed.
 *
 * This struct is created by [VecDeque.extractIf].
 * See its documentation for more.
 *
 * # Example
 *
 * ```kotlin
 * import std.collections.vecdeque.ExtractIf
 *
 * @feature(vecDequeExtractIf)
 * public fun doctestEntry() {
 *     var v = ArrayDeque(listOf(0, 1, 2))
 *     val iter: ExtractIf = v.extractIf(0 until 0, { x -> x % 2 == 0 })
 * }
 * ```
 */
public open class ExtractIf<T, F, A : Allocator> internal constructor(
    vecCtorArg: VecDeque<T, A>,
    /**
     * The index of the item that will be inspected by the next call to `next`.
     */
    public var idx: Int,
    /**
     * Elements at and beyond this point will be retained. Must be equal or smaller than `oldLen`.
     */
    public var end: Int,
    /**
     * The number of items that have been drained (removed) thus far.
     */
    public var del: Int,
    /**
     * The original length of `vec` prior to draining.
     */
    public var oldLen: Int,
    /**
     * The filter test predicate.
     */
    public var pred: F,
) : Iterable<T> {
    public var vec: VecDeque<T, A> = vecCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.vec.allocator()

    public open fun next(): T? {
        while (this.idx < this.end) {
            val i = this.idx
            val idx = this.vec.toWrappedIndex(i)
            // SAFETY:
            // We know that `i<self.end` from the if guard and that `self.end <= self.oldLen` from
            // the validity of `Self`. Therefore `i` points to an element within `vec`.
            //
            // Additionally, the i-th element is valid because each element is visited at most once
            // and it is the first time we access vec[i].
            //
            // Note: we can't use `vec.getMut(i).unwrap()` here since the precondition for that
            // function is that i < vec.len, but we've set vec's length to zero.
            val cur = this.vec.ptr().add(idx.asIndex())
            val drained = (this.pred)(cur)
            // Update the index *after* the predicate is called. If the index
            // is updated prior and the predicate panics, the element at this
            // index would be leaked.
            this.idx += 1
            if (drained) {
                this.del += 1
                // SAFETY: We never touch this element again after returning it.
                return ptr.read(cur)
            } else if (this.del > 0) {
                val holeSlot = this.vec.toWrappedIndex(i - this.del)
                // SAFETY: `self.del` > 0, so the hole slot must not overlap with current element.
                // We use copy for move, and never touch this element again.
                this.vec.wrapCopy(idx, holeSlot, 1)
            }
        }
        return null
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, this.end - this.idx)

    public open fun drop() {
        if (this.del > 0) {
            val src = this.vec.toWrappedIndex(this.idx)
            val dst = this.vec.toWrappedIndex(this.idx - this.del)
            val len = this.oldLen - this.idx
            // SAFETY: Trailing unchecked items must be valid since we never touch them.
            this.vec.wrapCopy(src, dst, len)
        }
        this.vec.len = this.oldLen - this.del
    }

    public open fun fmt(f: StringBuilder) {
        val peek =
            if (this.idx < this.end) {
                val idx = this.vec.toWrappedIndex(this.idx)
                // This has to use pointer arithmetic as `self.vec[self.idx]` or
                // `self.vec.getUnchecked(self.idx)` wouldn't work since we
                // temporarily set the length of `self.vec` to zero.
                //
                // SAFETY:
                // Since `self.idx` is smaller than `self.end` and `self.end` is
                // smaller than `self.oldLen`, `idx` is valid for indexing the
                // buffer. Also, per the invariant of `self.idx`, this element
                // has not been inspected/moved out yet.
                this.vec.ptr().add(idx.asIndex())
            } else {
                null
            }
        DebugStruct(f, "ExtractIf").field("peek", peek).finishNonExhaustive()
    }

    public companion object {
        public operator fun <T, F, A : Allocator> invoke(
            vec: VecDeque<T, A>,
            idx: Int,
            end: Int,
            del: Int,
            oldLen: Int,
            pred: F,
        ): ExtractIf<T, F, A> = ExtractIf<T, F, A>(
            vecCtorArg = vec,
            idx = idx,
            end = end,
            del = del,
            oldLen = oldLen,
            pred = pred,
        )

        public fun <T, F, A : Allocator, R : RangeBounds<Int>> new(vec: VecDeque<T, A>, pred: F, range: R): ExtractIf<T, F, A> {
            val oldLen = vec.len()
            val structVal = slice.range(range, 0 until oldLen)
            val start = structVal.start
            val end = structVal.end
            // Guard against the deque getting leaked (leak amplification)
            vec.len = 0
            return ExtractIf(vec = vec, idx = start, del = 0, end = end, oldLen = oldLen, pred = pred)
        }
    }
}
