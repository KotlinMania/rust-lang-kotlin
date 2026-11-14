// port-lint: source vec/extract_if.rs
package io.github.kotlinmania.rustlang.alloc.vec.extractif

import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.copy
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList

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
 * This struct is created by [Vec.extractIf].
 * See its documentation for more.
 *
 * # Example
 *
 * ```kotlin
 * var v = mutableListOf(0, 1, 2)
 * val iter: ExtractIf = v.extractIf(0 until 0, { x -> x % 2 == 0 })
 * ```
 */
public open class ExtractIf<T, F, A : Allocator>(
    public var vec: List<T>,
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
            // SAFETY:
            // We know that `i<self.end` from the if guard and that `self.end <= self.oldLen` from
            // the validity of `Self`. Therefore `i` points to an element within `vec`.
            //
            // Additionally, the i-th element is valid because each element is visited at most once
            // and it is the first time we access vec[i].
            //
            // Note: we can't use `vec.getUncheckedMut(i)` here since the precondition for that
            // function is that i < vec.len(), but we've set vec's length to zero.
            val cur = RustMutableReference(MutPtr((MutPtr((this.vec.asMutPtr()).rawAddress + ((i).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress), ObjectSlotLayout<T>())
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
                // SAFETY: `self.del` > 0, so the hole slot must not overlap with current element.
                // We use copy for move, and never touch this element again.
                run {
                    val holeSlot = MutPtr((this.vec.asMutPtr()).rawAddress + ((i - this.del).toLong() * (ObjectSlotLayout<T>()).strideBytes))
                    copyNonoverlapping(cur, holeSlot, 1)
                }
            }
        }
        return null
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, this.end - this.idx)

    public open fun drop() {
        if (this.del > 0) {
            // SAFETY: Trailing unchecked items must be valid since we never touch them.
            run {
                copy(ConstPtr((this.vec.asPtr()).rawAddress + ((this.idx).toLong() * (ObjectSlotLayout<T>()).strideBytes)), MutPtr((this.vec.asMutPtr()).rawAddress + ((this.idx - this.del).toLong() * (ObjectSlotLayout<T>()).strideBytes)), this.oldLen - this.idx)
            }
        }
        // SAFETY: After filling holes, all items are in contiguous memory.
        run {
            this.vec.setLen(this.oldLen - this.del)
        }
    }

    public open fun fmt(f: StringBuilder) {
        // We have to use pointer arithmetics here,
        // because the length of `self.vec` is temporarily set to 0.
        val start = this.vec.asPtr()
        // SAFETY: we always keep first `self.idx - self.del` elements valid.
        val retained = rawSliceList(start, (this.idx - this.del).toInt(), ObjectSlotLayout<T>())
        val validTail = rawSliceList(ConstPtr((start).rawAddress + ((this.idx).toLong() * (ObjectSlotLayout<T>()).strideBytes)), (this.oldLen - this.idx).toInt(), ObjectSlotLayout<T>())
        val (remainder, skippedTail) = validTail.splitAtUnchecked(this.end - this.idx)
        DebugStruct(f, "ExtractIf").field("retained", retained).field("remainder", remainder).field("skipped_tail", skippedTail).finishNonExhaustive()
    }

    public companion object {
        public fun <T, F, A : Allocator, R : RangeBounds<Int>> new(vec: List<T>, pred: F, range: R): ExtractIf<T, F, A> {
            val oldLen = vec.size
            val structVal = Range(range, 0 until oldLen)
            val start = structVal.start
            val end = structVal.end
            // Guard against the vec getting leaked (leak amplification)
            // SAFETY: Setting length to 0 is always okay.
            run {
                vec.setLen(0)
            }
            return ExtractIf(vec = vec, idx = start, del = 0, end = end, oldLen = oldLen, pred = pred)
        }
    }
}
