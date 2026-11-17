// port-lint: source iter/traits/double_ended.rs
package io.github.kotlinmania.rustlang.core.iter.traits.doubleended

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.array.iterNextChunkBack
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An iterator able to yield elements from both ends.
 *
 * Something that implements `DoubleEndedIterator` has one extra capability
 * over something that implements [Iterator]: the ability to also take
 * `Item`s from the back, as well as the front.
 *
 * It is important to note that both back and forth work on the same range,
 * and do not cross: iteration is over when they meet in the middle.
 *
 * In a similar fashion to the [Iterator] protocol, once a
 * `DoubleEndedIterator` returns [None] from a [nextBack], calling it
 * again may or may not ever return [Some] again. [next] and
 * [nextBack] are interchangeable for this purpose.
 *
 * [nextBack]: DoubleEndedIterator.nextBack
 * [next]: Iterator.next
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val numbers = mutableListOf(1, 2, 3, 4, 5, 6)
 * var iter = ArrayDeque(numbers)
 * check(1 == iter.removeFirstOrNull())
 * check(6 == iter.removeLastOrNull())
 * check(5 == iter.removeLastOrNull())
 * check(2 == iter.removeFirstOrNull())
 * check(3 == iter.removeFirstOrNull())
 * check(4 == iter.removeFirstOrNull())
 * check(null == iter.removeFirstOrNull())
 * check(null == iter.removeLastOrNull())
 * ```
 */
public interface DoubleEndedIterator {
    /**
     * Removes and returns an element from the end of the iterator.
     *
     * Returns `None` when there are no more elements.
     *
     * The [trait-level] docs contain more details.
     *
     * [trait-level]: DoubleEndedIterator
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val numbers = mutableListOf(1, 2, 3, 4, 5, 6)
     * var iter = ArrayDeque(numbers)
     * check(1 == iter.removeFirstOrNull())
     * check(6 == iter.removeLastOrNull())
     * check(5 == iter.removeLastOrNull())
     * check(2 == iter.removeFirstOrNull())
     * check(3 == iter.removeFirstOrNull())
     * check(4 == iter.removeFirstOrNull())
     * check(null == iter.removeFirstOrNull())
     * check(null == iter.removeLastOrNull())
     * ```
     *
     * # Remarks
     *
     * The elements yielded by `DoubleEndedIterator`s methods may differ from
     * the ones yielded by [Iterator]s methods:
     *
     * ```kotlin
     * val vec = mutableListOf(Pair(1, 'a'), Pair(1, 'b'), Pair(1, 'c'), Pair(2, 'a'), Pair(2, 'b'))
     * val uniqByFstComp = {
     *     var seen = mutableSetOf()
     *     ArrayDeque(vec).copied().filter({ x -> seen.add(x.first) })
     * }
     * check(uniqByFstComp().last() == Pair(2, 'a'))
     * check(uniqByFstComp().nextBack() == Pair(2, 'b'))
     * check(uniqByFstComp().fold(mutableListOf(), { v, x ->
     *     v.push(x)
     *     v
     * }) == mutableListOf(Pair(1, 'a'), Pair(2, 'a')))
     * check(uniqByFstComp().rfold(mutableListOf(), { v, x ->
     *     v.push(x)
     *     v
     * }) == mutableListOf(Pair(2, 'b'), Pair(1, 'c')))
     * ```
     */
    public fun nextBack(): Item?

    /**
     * Advances from the back of the iterator and returns an array containing the next
     * `N` values in sequence.
     *
     * If there are not enough elements to fill the array then `Err` is returned
     * containing an iterator over the remaining elements.
     *
     * Note: This is not equivalent to doing `iter.rev().nextChunk()` as this method
     * takes elements from the back of the iterator and preserves the order that the
     * elements were seen in the original iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterNextChunk)
     * public fun doctestEntry() {
     *     var iter = "lorem".chars()
     *     check(iter.nextChunkBack().unwrap() == listOf('e', 'm'))
     *     // N is inferred as 2
     *     check(iter.nextChunkBack().unwrap() == listOf('l', 'o', 'r'))
     *     // N is inferred as 3
     *     check(iter.nextChunkBack().unwrapErr().asSlice() == listOf())
     *     // N is explicitly 4
     * }
     * ```
     *
     * Split a string and get the last three items in sequence.
     *
     * ```kotlin
     * @feature(iterNextChunk)
     * public fun doctestEntry() {
     *     val quote = "not all those who wander are lost"
     *     val (first, second, third) = quote.splitWhitespace().nextChunkBack().unwrap()
     *     check(first == "wander")
     *     check(second == "are")
     *     check(third == "lost")
     * }
     * ```
     */
    public fun nextChunkBack(n: Int): Result<List<Item>, IntoIter<Item>> = iterNextChunkBack(this, n)

    /**
     * Advances the iterator from the back by `n` elements.
     *
     * `advanceBackBy` is the reverse version of [advanceBy]. This method will
     * eagerly skip `n` elements starting from the back by calling [nextBack] up
     * to `n` times until [None] is encountered.
     *
     * `advanceBackBy(n)` will return `Ok(())` if the iterator successfully advances by
     * `n` elements, or a `Err(NonZero<Int>)` with value `k` if [None] is encountered, where `k`
     * is remaining number of steps that could not be advanced because the iterator ran out.
     * If `self` is empty and `n` is non-zero, then this returns `Err(n)`.
     * Otherwise, `k` is always less than `n`.
     *
     * Calling `advanceBackBy(0)` can do meaningful work, for example [Flatten] can advance its
     * outer iterator until it finds an inner iterator that is not empty, which then often
     * allows it to return a more accurate `sizeHint()` than in its initial state.
     *
     * [advanceBy]: Iterator.advanceBy
     * [nextBack]: DoubleEndedIterator.nextBack
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import std.num.NonZero
     *
     * @feature(iterAdvanceBy)
     * public fun doctestEntry() {
     *     val a = listOf(3, 4, 5, 6)
     *     var iter = a.iter()
     *     check(iter.advanceBackBy(2) == Unit)
     *     check(iter.nextBack() == 4)
     *     check(iter.advanceBackBy(0) == Unit)
     *     check(iter.advanceBackBy(100) == Result.failure(NonZero.new(99).unwrap()))
     *     // only `3` was skipped
     * }
     * ```
     *
     * [Ok]: Ok
     * [Err]: Err
     */
    public fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        /**
         * Helper trait to specialize `advanceBackBy` via `tryRfold` for `Sized` iterators.
         */
        interface SpecAdvanceBackBy {
            public fun specAdvanceBackBy(n1: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>
        }
        fun <I : DoubleEndedIterator> I.specAdvanceBackBy(n1: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
            for (i in 0 until n1) {
                if (this.nextBack() == null) {
                    // SAFETY: `i` is always less than `n`.
                    return Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(NonZero.newUnchecked<NonZeroUsizeInner, Int>(n1 - i))
                }
            }
            return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }
        return this.specAdvanceBackBy(n)
    }

    /**
     * Returns the `n`th element from the end of the iterator.
     *
     * This is essentially the reversed version of [Iterator.nth].
     * Although like most indexing operations, the count starts from zero, so
     * `nthBack(0)` returns the first value from the end, `nthBack(1)` the
     * second, and so on.
     *
     * Note that all elements between the end and the returned element will be
     * consumed, including the returned element. This also means that calling
     * `nthBack(0)` multiple times on the same iterator will return different
     * elements.
     *
     * `nthBack()` will return [None] if `n` is greater than or equal to the
     * length of the iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.iter().nthBack(2) == 1)
     * ```
     *
     * Calling `nthBack()` multiple times doesn't rewind the iterator:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.iter()
     * check(iter.nthBack(1) == 2)
     * check(iter.nthBack(1) == null)
     * ```
     *
     * Returning `None` if there are less than `n + 1` elements:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.iter().nthBack(10) == null)
     * ```
     */
    public fun nthBack(n: Int): Item? {
        (this.advanceBackBy(n).ok() ?: return null)
        return this.nextBack()
    }

    /**
     * This is the reverse version of [Iterator.tryFold]: it takes
     * elements starting from the back of the iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf("1", "2", "3")
     * val sum = a.iter().map({ s -> (s.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))) }).tryRfold(0, { acc, x -> x.andThen({ y -> Result.success(acc + y) }) })
     * check(sum == Result.success(6))
     * ```
     *
     * Short-circuiting:
     *
     * ```kotlin
     * val a = listOf("1", "rust", "3")
     * var it = a.iter()
     * val sum = it.byRef().map({ s -> (s.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))) }).tryRfold(0, { acc, x -> x.andThen({ y -> Result.success(acc + y) }) })
     * check(sum.isErr())
     * // Because it short-circuited, the remaining elements are still
     * // available through the iterator.
     * check(it.nextBack() == "1")
     * ```
     */
    public fun <Residual, B, R> tryRfold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual> {
        var accum = init
        while (true) {
            val matchVal = this.nextBack()
            if (matchVal == null) break
            val x = matchVal
            accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        }
        return accum
    }

    /**
     * An iterator method that reduces the iterator's elements to a single,
     * final value, starting from the back.
     *
     * This is the reverse version of [Iterator.fold]: it takes elements
     * starting from the back of the iterator.
     *
     * `rfold()` takes two arguments: an initial value, and a closure with two
     * arguments: an `accumulator`, and an element. The closure returns the value that
     * the accumulator should have for the next iteration.
     *
     * The initial value is the value the accumulator will have on the first
     * call.
     *
     * After applying this closure to every element of the iterator, `rfold()`
     * returns the accumulator.
     *
     * This operation is sometimes called `reduce` or `inject`.
     *
     * Folding is useful whenever you have a collection of something, and want
     * to produce a single value from it.
     *
     * Note: `rfold()` combines elements in a *right-associative* fashion. For associative
     * operators like `+`, the order the elements are combined in is not important, but for non-associative
     * operators like `-` the order will affect the final result.
     * For a *left-associative* version of `rfold()`, see [Iterator.fold].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * // the sum of all of the elements of a
     * val sum = a.iter().rfold(0, { acc, x -> acc + x })
     * check(sum == 6)
     * ```
     *
     * This example demonstrates the right-associative nature of `rfold()`:
     * it builds a string, starting with an initial value
     * and continuing with each element from the back until the front:
     *
     * ```kotlin
     * val numbers = listOf(1, 2, 3, 4, 5)
     * val zero = "0".toString()
     * val result = numbers.iter().rfold(zero, { acc, x ->
     *     "($x + $acc)"
     * })
     * check(result == "(1 + (2 + (3 + (4 + (5 + 0)))))")
     * ```
     */
    public fun <B> rfold(init: B, f: (B, Item) -> B): B {
        var accum = init
        while (true) {
            val matchVal = this.nextBack()
            if (matchVal == null) break
            val x = matchVal
            accum = f(accum, x)
        }
        return accum
    }

    /**
     * Searches for an element of an iterator from the back that satisfies a predicate.
     *
     * `rfind()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, starting at the end, and if any
     * of them return `true`, then `rfind()` returns [Some]. If they all return
     * `false`, it returns [None].
     *
     * `rfind()` is short-circuiting; in other words, it will stop processing
     * as soon as the closure returns `true`.
     *
     * Because `rfind()` takes a reference, and many iterators iterate over
     * references, this leads to a possibly confusing situation where the
     * argument is a double reference. You can see this effect in the
     * examples below, with `&& x`.
     *
     * [Some]: Some
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().rfind({ x -> x == 2 }) == 2)
     * check(a.intoIter().rfind({ x -> x == 5 }) == null)
     * ```
     *
     * Iterating over references:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * // `iter()` yields references i.e. `Int` and `rfind()` takes a
     * // reference to each element.
     * check(a.iter().rfind({ x -> x == 2 }) == 2)
     * check(a.iter().rfind({ x -> x == 5 }) == null)
     * ```
     *
     * Stopping at the first `true`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.iter()
     * check(iter.rfind({ x -> x == 2 }) == 2)
     * // we can still use `iter`, as there are more elements.
     * check(iter.nextBack() == 1)
     * ```
     */
    public fun rfind(predicate: (Item) -> Boolean): Item? {
        fun <T> check(predicate1: (T) -> Boolean): (Unit, T) -> ControlFlow<T, Unit> =
            { x ->
                if (predicate1(x)) {
                    ControlFlow.Break(x)
                } else {
                    ControlFlow.Continue(Unit)
                }
            }
        return this.tryRfold(Unit, check(predicate)).breakValue()
    }
}

/**
 * Helper trait to specialize `rfold` and `rtryFold` for `I where I: Sized`
 */
public interface DoubleEndedIteratorRefSpec : DoubleEndedIterator {
    public fun <B> specRfold(init: B, f: (B, Item) -> B): B

    public fun <Residual, B, R> specTryRfold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual>
}

public fun <I : DoubleEndedIterator, B> I.specRfold(init: B, f: (B, Item) -> B): B {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun <Residual, I : DoubleEndedIterator, B, R> I.specTryRfold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <I : DoubleEndedIterator, Aaa> I.specRfold(init: Aaa, fold: (Aaa, Item) -> Aaa): Aaa = this.specTryRfold(init, NeverShortCircuit.wrapMut2(fold)).first
