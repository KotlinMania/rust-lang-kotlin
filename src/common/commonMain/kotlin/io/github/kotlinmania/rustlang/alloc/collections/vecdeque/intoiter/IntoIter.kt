// port-lint: source collections/vec_deque/into_iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.intoiter

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.index.WrappedIndex
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

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
 * An owning iterator over the elements of a `VecDeque`.
 *
 * This `class` is created by the [intoIter] method on [VecDeque]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: VecDeque.intoIter
 */
public open class IntoIter<T, A : Allocator> internal constructor(
    innerCtorArg: VecDeque<T, A>,
) : TrustedLen, Iterable<T> {
    public var inner: VecDeque<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<T, A> = IntoIter(inner)

    public open fun intoVecdeque(): VecDeque<T, A> = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "IntoIter").field(this.inner).finish()

    public open fun next(): T? = this.inner.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.inner.len()
        return Pair(len, len)
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val len = this.inner.len
        val rem =
            if (len < n) {
                this.inner.clear()
                n - len
            } else {
                this.inner.drain(0 until n)
                0
            }
        return ((NonZero.new<NonZeroUsizeInner, Int>(rem))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun count(): Int = this.inner.len

    public open fun <Residual, B, R> tryFold(init: B, f: (B, T) -> R): R where R : Try<B> {
        var init1 = init
        open class Guard<T, A : Allocator>(
            public var deque: VecDeque<T, A>,
            // `consumed <= deque.len` always holds.
            public var consumed: Int,
        ) {
            public open fun drop() {
                this.deque.len -= this.consumed
                this.deque.head = this.deque.toWrappedIndex(this.consumed)
            }
        }
        val guard = Guard(deque = this.inner, consumed = 0)
        val (head, tail) = guard.deque.asSlices()
        init1 = when (val tryResult = head.iter().map({ elem ->
            guard.consumed += 1
            // SAFETY: Because we incremented `guard.consumed`, the
            // deque effectively forgot the element, so we can take
            // ownership
            read(elem)
        }).tryFold(init1, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        return tail.iter().map({ elem ->
            guard.consumed += 1
            // SAFETY: Same as above.
            read(elem)
        }).tryFold(init1, f)
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B =
        when (val matchVal = this.tryFold(init, { b, item -> Result.Ok<B, Nothing>(f(b, item)) })) {
            is Result.Ok -> {
                val b = matchVal.value
                b
            }
        }

    public open fun last(): T? = this.inner.popBack()

    public open fun nextChunk(n: Int): Result<List<T>, io.github.kotlinmania.rustlang.core.array.iter.IntoIter<T>> {
        val rawArr = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<Result<List<T>, io.github.kotlinmania.rustlang.core.array.iter.IntoIter<T>>>()) }
        val rawArrPtr = rawArr.asMutPtr().cast()
        val (head, tail) = this.inner.asSlices()
        if (head.len() >= n) {
            // SAFETY: By manually adjusting the head and length of the deque, we effectively
            // make it forget the first `N` elements, so taking ownership of them is safe.
            copyNonoverlapping(head.asPtr(), rawArrPtr, n)
            this.inner.head = this.inner.toWrappedIndex(n)
            this.inner.len -= n
            // SAFETY: We initialized the entire array with items from `head`
            return Result.Ok<List<T>, io.github.kotlinmania.rustlang.core.array.iter.IntoIter<T>>(rawArr.transpose().assumeInit())
        }
        // SAFETY: Same argument as above.
        copyNonoverlapping(head.asPtr(), rawArrPtr, head.len())
        val remaining = n - head.len()
        return if (tail.len() >= remaining) {
            // SAFETY: Same argument as above.
            copyNonoverlapping(tail.asPtr(), rawArrPtr.add(head.len()), remaining)
            this.inner.head = this.inner.toWrappedIndex(n)
            this.inner.len -= n
            // SAFETY: We initialized the entire array with items from `head` and `tail`
            Result.Ok<List<T>, io.github.kotlinmania.rustlang.core.array.iter.IntoIter<T>>(rawArr.transpose().assumeInit())
        } else {
            // SAFETY: Same argument as above.
            copyNonoverlapping(tail.asPtr(), rawArrPtr.add(head.len()), tail.len())
            val init = head.len() + tail.len()
            // We completely drained all the deques elements.
            this.inner.head = WrappedIndex.zero()
            this.inner.len = 0
            // SAFETY: We copied all elements from both slices to the beginning of the array, so
            // the given range is initialized.
            Result.Err<List<T>, io.github.kotlinmania.rustlang.core.array.iter.IntoIter<T>>(io.github.kotlinmania.rustlang.core.array.iter.IntoIter.newUnchecked(rawArr, 0 until init))
        }
    }

    public open fun nextBack(): T? = this.inner.popBack()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val len = this.inner.len
        val rem =
            if (len < n) {
                this.inner.clear()
                n - len
            } else {
                this.inner.truncate(len - n)
                0
            }
        return ((NonZero.new<NonZeroUsizeInner, Int>(rem))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, T) -> R): R where R : Try<B> {
        var init1 = init
        open class Guard<T, A : Allocator>(
            public var deque: VecDeque<T, A>,
            // `consumed <= deque.len` always holds.
            public var consumed: Int,
        ) {
            public open fun drop() {
                this.deque.len -= this.consumed
            }
        }
        val guard = Guard(deque = this.inner, consumed = 0)
        val (head, tail) = guard.deque.asSlices()
        init1 = when (val tryResult = tail.iter().map({ elem ->
            guard.consumed += 1
            // SAFETY: See `tryFold`s safety comment.
            read(elem)
        }).tryRfold(init1, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        return head.iter().map({ elem ->
            guard.consumed += 1
            // SAFETY: Same as above.
            read(elem)
        }).tryRfold(init1, f)
    }

    public open fun <B> rfold(init: B, f: (B, T) -> B): B =
        when (val matchVal = this.tryRfold(init, { b, item -> Result.Ok<B, Nothing>(f(b, item)) })) {
            is Result.Ok -> {
                val b = matchVal.value
                b
            }
        }

    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: VecDeque<T, A>,
        ): IntoIter<T, A> = IntoIter<T, A>(
            innerCtorArg = inner,
        )

        public fun <T, A : Allocator> new(inner: VecDeque<T, A>): IntoIter<T, A> = IntoIter(inner = inner)
    }
}
