// port-lint: source collections/vec_deque/drain.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.drain

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.index.WrappedIndex
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

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

/**
 * A draining iterator over the elements of a `VecDeque`.
 *
 * This `class` is created by the [drain] method on [VecDeque]. See its
 * documentation for more.
 *
 * [drain]: VecDeque.drain
 */
public open class Drain<T, A : Allocator> internal constructor(
    dequeCtorArg: NonNull,
    // drainStart is stored in deque.len
    public var drainLen: Int,
    // index into the logical array, not the physical one (always lies in [0..deque.len))
    public var idx: Int,
    // number of elements after the drained range
    public var tailLen: Int,
    public var remaining: Int,
    // Needed to make Drain covariant over T
    public var marker: Unit,
) : Iterable<T> {
    // We can't just use a VecDeque<T, A>, as that would make Drain invariant over T
    // and we want it to be covariant instead
    public var deque: NonNull = dequeCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Only returns pointers to the slices, as that's all we need
    // to drop them. May only be called if `self.remaining != 0`.
    public open fun asSlices(): Pair<MutPtr, MutPtr> {
        // ignore-tidy-undocumented-unchecked
        return run {
            val deque = this.deque.asRef()
            // We know that `self.idx + self.remaining <= deque.len <= Int.MAX`, so this won't overflow.
            val logicalRemainingRange = this.idx until this.idx + this.remaining
            // SAFETY: `logicalRemainingRange` represents the
            // range into the logical buffer of elements that
            // haven't been drained yet, so they're all initialized,
            // and `slice.range(start .. end, end) == start .. end`,
            // so the preconditions for `sliceRanges` are met.
            val (aRange, bRange) = deque.sliceRanges(logicalRemainingRange.clone(), logicalRemainingRange.end)
            Pair(deque.bufferRange(aRange), deque.bufferRange(bRange))
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "Drain").field(this.drainLen).field(this.idx).field(this.tailLen).field(this.remaining).finish(); Result.Ok<Unit, Error>(Unit) }

    public open fun drop(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        open class DropGuard<T, A : Allocator>(
            public var value: Drain<T, A>,
        ) {
            public var field0: Drain<T, A>
                get() = value
                set(v) {
                    value = v
                }

            // Dropping `guard` handles moving the remaining elements into place.
            public open fun drop(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
                if (needsDrop<T>() && this.value.remaining != 0) {
                    // SAFETY: We just checked that `self.remaining != 0`.
                    run {
                        val (front, back) = this.value.asSlices()
                        dropInPlace(front)
                        dropInPlace(back)
                    }
                }
                // ignore-tidy-undocumented-unchecked
                val sourceDeque = this.value.deque.asMut()
                val drainLen = this.value.drainLen
                val headLen = sourceDeque.len
                // #elements in front of the drain
                val tailLen = this.value.tailLen
                // #elements behind the drain
                val newLen = headLen + tailLen
                if (((typeLayoutT).sizeBytes == 0L)) {
                    // no need to copy around any memory if T is a ZST
                    sourceDeque.len = newLen
                    return
                }
                // Next, we will fill the hole left by the drain with as few writes as possible.
                // The code below handles the following control flow and reduces the amount of
                // branches under the assumption that `headLen == 0 || tailLen == 0`, i.e.
                // draining at the front or at the back of the dequeue is especially common.
                //
                // H = "head index" = `deque.head`
                // h = elements in front of the drain
                // d = elements in the drain
                // t = elements behind the drain
                //
                // Note that the buffer may wrap at any point and the wrapping is handled by
                // `wrapCopy` and `toPhysicalIdx`.
                //
                // Case 1: if `headLen == 0 && tailLen == 0`
                // Everything was drained, reset the head index back to 0.
                // H
                // [ . . . . . d d d d . . . . . ]
                // H
                // [ . . . . . . . . . . . . . . ]
                //
                // Case 2: else if `tailLen == 0`
                // Don't move data or the head index.
                // H
                // [ . . . h h h h d d d d . . . ]
                // H
                // [ . . . h h h h . . . . . . . ]
                //
                // Case 3: else if `headLen == 0`
                // Don't move data, but move the head index.
                // H
                // [ . . . d d d d t t t t . . . ]
                // H
                // [ . . . . . . . t t t t . . . ]
                //
                // Case 4: else if `tailLen <= headLen`
                // Move data, but not the head index.
                // H
                // [ . . h h h h d d d d t t . . ]
                // H
                // [ . . h h h h t t . . . . . . ]
                //
                // Case 5: else
                // Move data and the head index.
                // H
                // [ . . h h d d d d t t t t . . ]
                // H
                // [ . . . . . . h h t t t t . . ]
                // When draining at the front (`.drain(.. n)`) or at the back (`.drain(n ..)`),
                // we don't need to copy any data. The number of elements copied would be 0.
                if (headLen != 0 && tailLen != 0) {
                    // Marking this function as cold helps LLVM to eliminate it entirely if
                    // this branch is never taken.
                    // We use `@cold` instead of `@inline(never)`, because inlining this
                    // function into the general case (`.drain(n .. m)`) is fine.
                    // See `tests / codegen - llvm / vecdeque - drain.rs` for a test.
                    fun <T, A : Allocator> joinHeadAndTailWrapping(sourceDeque1: VecDeque<T, A>, drainLen1: Int, headLen1: Int, tailLen1: Int) {
                        // Pick whether to move the head or the tail here.
                        val (src, dst, len)
                        if (headLen1 < tailLen1) {
                            src = sourceDeque1.head
                            dst = sourceDeque1.toWrappedIndex(drainLen1)
                            len = headLen1
                        } else {
                            src = sourceDeque1.toWrappedIndex(headLen1 + drainLen1)
                            dst = sourceDeque1.toWrappedIndex(headLen1)
                            len = tailLen1
                        }
                        // ignore-tidy-undocumented-unchecked
                        run {
                            sourceDeque1.wrapCopy(src, dst, len)
                        }
                    }
                    joinHeadAndTailWrapping(sourceDeque, drainLen, headLen, tailLen)
                }
                if (newLen == 0) {
                    // Special case: If the entire deque was drained, reset the head back to 0,
                    // like `.clear()` does.
                    sourceDeque.head = WrappedIndex.zero()
                } else if (headLen < tailLen) {
                    // If we moved the head above, then we need to adjust the head index here.
                    sourceDeque.head = sourceDeque.toWrappedIndex(drainLen)
                }
                sourceDeque.len = newLen
            }
        }
        val guard = DropGuard(this)
        if (needsDrop<T>() && guard.first.remaining != 0) {
            // SAFETY: We just checked that `self.remaining != 0`.
            val (front, back) = guard.first.asSlices()
            // since idx is a logical index, we don't need to worry about wrapping.
            guard.first.idx += front.len()
            guard.first.remaining -= front.len()
            // SAFETY: This can't have been dropped before since
            // `idx` & `remaining` track what's been dropped.
            dropInPlace(front)
            guard.first.remaining = 0
            // SAFETY: Ditto.
            dropInPlace(back)
        }
    }

    public open fun next(): T? {
        if (this.remaining == 0) {
            return null
        }
        // ignore-tidy-undocumented-unchecked
        val wrappedIdx = this.deque.asRef().toWrappedIndex(this.idx)
        this.idx += 1
        this.remaining -= 1
        // ignore-tidy-undocumented-unchecked
        return this.deque.asMut().bufferRead(wrappedIdx)
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.remaining
        return Pair(len, len)
    }

    public open fun nextBack(): T? {
        if (this.remaining == 0) {
            return null
        }
        this.remaining -= 1
        val wrappedIdx = this.deque.asRef().toWrappedIndex(this.idx + this.remaining)
        // ignore-tidy-undocumented-unchecked
        return this.deque.asMut().bufferRead(wrappedIdx)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            deque: NonNull,
            drainLen: Int,
            idx: Int,
            tailLen: Int,
            remaining: Int,
            marker: Unit,
        ): Drain<T, A> = Drain<T, A>(
            dequeCtorArg = deque,
            drainLen = drainLen,
            idx = idx,
            tailLen = tailLen,
            remaining = remaining,
            marker = marker,
        )

        public fun <T, A : Allocator> new(deque: VecDeque<T, A>, drainStart: Int, drainLen: Int): Drain<T, A> {
            val origLen = replace(deque.len, drainStart)
            val tailLen = origLen - drainStart - drainLen
            return Drain(deque = NonNull.from(deque), drainLen = drainLen, idx = drainStart, tailLen = tailLen, remaining = drainLen, marker = Unit)
        }
    }
}
