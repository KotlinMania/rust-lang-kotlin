// port-lint: source collections/vec_deque/iter_mut.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.itermut

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
 * A mutable iterator over the elements of a `VecDeque`.
 *
 * This `class` is created by the [iterMut] method on [VecDeque]. See its
 * documentation for more.
 */
public open class IterMut<T>(
    public var i1: ArrayDeque<T>,
    public var i2: ArrayDeque<T>,
) : TrustedLen<Any>, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
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
     * To avoid creating  references that alias, this is forced to
     * consume the iterator.
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
     *     var iter = deque.iterMut()
     *     iter.next()
     *     iter.nextBack()
     *     val slices = iter.intoSlices()
     *     slices.first[0] = 42
     *     slices.second[0] = 24
     *     check(deque.asSlices() == Pair(listOf(8, 42, 10), listOf(24, 1, 2)))
     * }
     * ```
     */
    public open fun intoSlices(): Pair<MutableList<T>, MutableList<T>> = Pair(this.i1.intoSlice(), this.i2.intoSlice())

    /**
     * Views the underlying data as a pair of subslices of the original data.
     *
     * The slices contain, in order, the contents of the deque not yet yielded
     * by the iterator.
     *
     * To avoid creating `[T]` references that alias, the returned slices
     * borrow their scopes from the iterator the method is applied on.
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
     *     var iter = deque.iterMut()
     *     iter.next()
     *     iter.nextBack()
     *     check(iter.asSlices() == Pair(listOf(9, 10), listOf(0, 1)))
     * }
     * ```
     */
    public open fun asSlices(): Pair<List<T>, List<T>> = Pair(this.i1.toList(), this.i2.toList())

    /**
     * Views the underlying data as a pair of subslices of the original data.
     *
     * The slices contain, in order, the contents of the deque not yet yielded
     * by the iterator.
     *
     * To avoid creating `[T]` references that alias, the returned slices
     * borrow their scopes from the iterator the method is applied on.
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
     *     var iter = deque.iterMut()
     *     iter.next()
     *     iter.nextBack()
     *     iter.asMutSlices().first[0] = 42
     *     iter.asMutSlices().second[0] = 24
     *     check(deque.asSlices() == Pair(listOf(8, 42, 10), listOf(24, 1, 2)))
     * }
     * ```
     */
    public open fun asMutSlices(): Pair<MutableList<T>, MutableList<T>> = Pair(this.i1.asMutSlice(), this.i2.asMutSlice())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "IterMut").field(this.i1.toList()).field(this.i2.toList()).finish(); Result.Ok<Unit, Error>(Unit) }

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

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        run {
            val matchVal = this.i1.advanceBy(n)
            when (matchVal) { is Result.Ok<*, *> -> {
                Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
            }; is Result.Err<*, *> -> {
                val remaining = matchVal.errValue()
                swap(this.i1, this.i2)
                this.i1.advanceBy(remaining.get())
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

    public open fun <Residual, B, R> tryFold(init: B, f: (B, T) -> R): R where R : Try<B, Residual> {
        val acc = when (val tryResult = this.i1.tryFold(init, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
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
                // the iterators once the first one is empty, we ensure
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
            when (matchVal) { is Result.Ok<*, *> -> {
                Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
            }; is Result.Err<*, *> -> {
                val remaining = matchVal.errValue()
                swap(this.i1, this.i2)
                this.i2.advanceBackBy(remaining.get())
            } }
        }

    public open fun <Acc> rfold(accum: Acc, f: (Acc, T) -> Acc): Acc {
        val accum1 = this.i2.rfold(accum, f)
        return this.i1.rfold(accum1, f)
    }

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, T) -> R): R where R : Try<B, Residual> {
        val acc = when (val tryResult = this.i2.tryRfold(init, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
        return this.i1.tryRfold(acc, f)
    }

    public open fun len(): Int = this.i1.size + this.i2.size

    public open fun isEmpty(): Boolean = this.i1.isEmpty() && this.i2.isEmpty()

    public companion object {
        public fun <T> new(i1: ArrayDeque<T>, i2: ArrayDeque<T>): IterMut<T> = IterMut(i1 = i1, i2 = i2)

        /**
         * Creates an empty `vecDeque.IterMut`.
         *
         * ```kotlin
         * import vecdeque.IterMut
         *
         * val iter: IterMut<UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T> default(): IterMut<T> = IterMut(i1 = ArrayDeque(), i2 = ArrayDeque())

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}
