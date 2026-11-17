// port-lint: source collections/vec_deque/iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.iter

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.swap
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.alloc.iteratorGetUnchecked as underlyingIteratorGetUnchecked

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
 * An iterator over the elements of a `VecDeque`.
 *
 * This `class` is created by the [iter] method on [VecDeque]. See its
 * documentation for more.
 */
public open class Iter<T>(
    public var i1: ArrayDeque<T>,
    public var i2: ArrayDeque<T>,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Views the underlying data as a pair of subslices of the original data.
     *
     * The slices contain, in order, the contents of the deque not yet yielded
     * by the iterator.
     *
     * This has the same scope as the original `VecDeque`, and so the
     * iterator can continue to be used while this exists.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(vecDequeIterAsSlices)
     * public fun doctestEntry() {
     *     var deque = VecDeque.new()
     *     deque.addLast(0)
     *     deque.addLast(1)
     *     deque.addLast(2)
     *     deque.pushFront(10)
     *     deque.pushFront(9)
     *     deque.pushFront(8)
     *     var iter = deque.iter()
     *     iter.next()
     *     iter.nextBack()
     *     check(iter.asSlices() == Pair(listOf(9, 10), listOf(0, 1)))
     * }
     * ```
     */
    public open fun asSlices(): Pair<List<T>, List<T>> = Pair(this.i1.toList(), this.i2.toList())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "Iter").field(this.i1.toList()).field(this.i2.toList()).finish()

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<T> = Iter(i1 = ArrayDeque(this.i1), i2 = ArrayDeque(this.i2))

    public open fun next(): T? =
        when (val matchVal = this.i1.removeFirstOrNull()) {
            null -> {
                // most of the time, the iterator will either always
                // call next(), or always call nextBack(). By swapping
                // the iterators once the first one is empty, we ensure
                // that the first branch is taken as often as possible,
                // without sacrificing correctness, as i1 is empty anyways
                swap(this.i1, this.i2)
                this.i1.removeFirstOrNull()
            }
            else -> {
                val value = matchVal
                value
            }
        }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val remaining = this.i1.advanceBy(n)
        return when (remaining) { is Result.Ok -> {
            Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }; is Result.Err -> {
            val n1 = remaining.error
            swap(this.i1, this.i2)
            this.i1.advanceBy(n1.get())
        } }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.len()
        return Pair(len, len)
    }

    public open fun <Acc> fold(accum: Acc, f: (Acc, T) -> Acc): Acc {
        val accum1 = this.i1.fold(accum, f)
        return this.i2.fold(accum1, f)
    }

    public open fun <Residual, B, R> tryFold(init: B, f: (B, T) -> R): R where R : Try<B> {
        val acc = when (val tryResult = this.i1.tryFold(init, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        return this.i2.tryFold(acc, f)
    }

    public open fun last(): T? = this.nextBack()

    public open fun iteratorGetUnchecked(idx: Int): T {
        // SAFETY: The TrustedRandomAccess contract requires that callers only pass an index
        // that is in bounds.
        return run {
            val i1Len = this.i1.size
            if (idx < i1Len) {
                this.i1.underlyingIteratorGetUnchecked(idx)
            } else {
                this.i2.underlyingIteratorGetUnchecked(idx - i1Len)
            }
        }
    }

    public open fun nextBack(): T? =
        when (val matchVal = this.i2.removeLastOrNull()) {
            null -> {
                // most of the time, the iterator will either always
                // call next(), or always call nextBack(). By swapping
                // the iterators once the second one is empty, we ensure
                // that the first branch is taken as often as possible,
                // without sacrificing correctness, as i2 is empty anyways
                swap(this.i1, this.i2)
                this.i2.removeLastOrNull()
            }
            else -> {
                val value = matchVal
                value
            }
        }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        run {
            val matchVal = this.i2.advanceBackBy(n)
            when (matchVal) { is Result.Ok -> {
                Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
            }; is Result.Err -> {
                val n1 = matchVal.error
                swap(this.i1, this.i2)
                this.i2.advanceBackBy(n1.get())
            } }
        }

    public open fun <Acc> rfold(accum: Acc, f: (Acc, T) -> Acc): Acc {
        val accum1 = this.i2.rfold(accum, f)
        return this.i1.rfold(accum1, f)
    }

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, T) -> R): R where R : Try<B> {
        val acc = when (val tryResult = this.i2.tryRfold(init, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        return this.i1.tryRfold(acc, f)
    }

    public open fun len(): Int = this.i1.size + this.i2.size

    public open fun isEmpty(): Boolean = this.i1.isEmpty() && this.i2.isEmpty()

    public companion object {
        public fun <T> new(i1: ArrayDeque<T>, i2: ArrayDeque<T>): Iter<T> = Iter(i1 = i1, i2 = i2)

        /**
         * Creates an empty `vecDeque.Iter`.
         *
         * ```kotlin
         * import vecdeque.Iter
         *
         * val iter: Iter<UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T> default(): Iter<T> = Iter(i1 = Default.default(), i2 = Default.default())

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}
