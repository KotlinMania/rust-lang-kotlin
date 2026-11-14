// port-lint: source iter/adapters/peekable.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.peekable

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator with a `peek()` that returns an optional reference to the next
 * element.
 *
 * This `class` is created by the [peekable] method on [Iterator]. See its
 * documentation for more.
 *
 * [peekable]: Iterator.peekable
 * [Iterator]: trait.Iterator.html
 */
public open class Peekable<I>(
    public var iter: I,
    /**
     * Remember a peeked value, even if it was None.
     */
    public var peeked: Item?,
) : TrustedLen<Peekable<I>>, SourceIter<Any?, Source>, Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Peekable.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Peekable<I> = Peekable(iter, peeked)

    // Peekable must remember if a None has been seen in the `.peek()` method.
    // It ensures that `.peek();.peek();` or `.peek();.next();` only advances the
    // underlying iterator at most once. This does not by itself make the iterator
    // fused.
    public override fun next(): Item? =
        when (val matchVal = this.peeked.also { this.peeked = null }) {
            null -> this.iter.next()
            else -> {
                val v = matchVal
                v
            }
        }

    public override fun count(): Int =
        when (val matchVal = this.peeked.also { this.peeked = null }) {
            null -> 0
            else -> 1 + this.iter.count()
            null -> this.iter.count()
        }

    public override fun nth(n: Int): Item? =
        run {
            val matchVal = this.peeked.also { this.peeked = null }
            when {
                matchVal != null && matchVal == null -> null
                matchVal != null && matchVal != null && n == 0 -> {
                    val v = matchVal
                    v
                }
                matchVal != null && matchVal != null -> this.iter.nth(n - 1)
                else -> this.iter.nth(n)
            }
        }

    public override fun last(): Item? {
        val peekOpt =
            run {
                val matchVal = this.peeked.also { this.peeked = null }
                when {
                    matchVal != null && matchVal == null -> { return null }
                    matchVal != null -> {
                        val v = matchVal
                        v
                    }
                    else -> null
                }
            }
        return (this.iter.last() ?: peekOpt)
    }

    public override fun sizeHint(): Pair<Int, Int?> {
        val peekLen =
            when (val matchVal = this.peeked) {
                null -> { return Pair(0, 0) }
                else -> 1
                null -> 0
            }
        val (lo, hi) = this.iter.sizeHint()
        val lo1 = lo.saturatingAdd(peekLen)
        val hi1 =
            when (hi) {
                null -> null
                else -> {
                    val x = hi
                    x.checkedAdd(peekLen)
                }
            }
        return Pair(lo1, hi1)
    }

    public override fun <B, R> tryFold(init: B, f: (B, I.Item) -> R): R where R : Try<R, B, Any?> {
        val acc =
            when (val matchVal = this.peeked.also { this.peeked = null }) {
                null -> { return init }
                else -> {
                    val v = matchVal
                    when (val tryResult = f(init, v)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                null -> init
            }
        return this.iter.tryFold(acc, f)
    }

    public override fun <Acc> fold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc {
        val acc =
            when (val matchVal = this.peeked) {
                null -> { return init }
                else -> {
                    val v = matchVal
                    fold(init, v)
                }
                null -> init
            }
        return this.iter.fold(acc, fold)
    }

    public open fun nextBack(): Item? =
        when (val matchVal = this.peeked) {
            else -> {
                val v = matchVal
                (this.iter.nextBack() ?: v.also { v = null })
            }
            null -> null
            null -> this.iter.nextBack()
        }

    public open fun <B, R> tryRfold(init: B, f: (B, I.Item) -> R, fromResidualRFn: (R) -> R): R where R : Try<R, B, Any?> =
        when (val matchVal = this.peeked.also { this.peeked = null }) {
            null -> init
            else -> {
                val v = matchVal
                when (val matchVal1 = this.iter.tryRfold(init, f).branch()) {
                    is ControlFlow.Continue -> {
                        val (acc) = matchVal1
                        f(acc, v)
                    }
                    is ControlFlow.Break -> {
                        val (r) = matchVal1
                        this.peeked = v
                        fromResidualRFn(r)
                    }
                }
            }
            null -> this.iter.tryRfold(init, f)
        }

    public open fun <Acc> rfold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc =
        when (val matchVal = this.peeked) {
            null -> init
            else -> {
                val v = matchVal
                val acc = this.iter.rfold(init, fold)
                fold(acc, v)
            }
            null -> this.iter.rfold(init, fold)
        }

    /**
     * Returns a reference to the next() value without advancing the iterator.
     *
     * Like [next], if there is a value, it is wrapped in a `Some(T)`.
     * But if the iteration is over, `None` is returned.
     *
     * [next]: Iterator.next
     *
     * Because `peek()` returns a reference, and many iterators iterate over
     * references, there can be a possibly confusing situation where the
     * return value is a double reference. You can see this effect in the
     * examples below.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val xs = listOf(1, 2, 3)
     * var iter = xs.iter().peekable()
     * // peek() lets us see into the future
     * check(iter.peek() == 1)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * // The iterator does not advance even if we `peek` multiple times
     * check(iter.peek() == 3)
     * check(iter.peek() == 3)
     * check(iter.next() == 3)
     * // After the iterator is finished, so is `peek()`
     * check(iter.peek() == null)
     * check(iter.next() == null)
     * ```
     */
    public open fun peek(): Item? {
        val iter = this.iter
        return this.peeked.getOrInsertWith({ iter.next() }).asRef()
    }

    /**
     * Returns a mutable reference to the next() value without advancing the iterator.
     *
     * Like [next], if there is a value, it is wrapped in a `Some(T)`.
     * But if the iteration is over, `None` is returned.
     *
     * Because `peekMut()` returns a reference, and many iterators iterate over
     * references, there can be a possibly confusing situation where the
     * return value is a double reference. You can see this effect in the examples
     * below.
     *
     * [next]: Iterator.next
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var iter = listOf(1, 2, 3).iter().peekable()
     * // Like with `peek()`, we can see into the future without advancing the iterator.
     * check(iter.peekMut() == 1)
     * check(iter.peekMut() == 1)
     * check(iter.next() == 1)
     * // Peek into the iterator and set the value behind the mutable reference.
     * when (val matchVal = iter.peekMut()) {
     *     null -> {}
     *     else -> {
     *         val p = matchVal
     *         check(p == 2)
     *         p = 5
     *     }
     * }
     * // The value we put in reappears as the iterator continues.
     * check(iter.collect<Vec>() == mutableListOf(5, 3))
     * ```
     */
    public open fun peekMut(): Item? {
        val iter = this.iter
        return this.peeked.getOrInsertWith({ iter.next() }).asMut()
    }

    /**
     * Consume and return the next value of this iterator if a condition is true.
     *
     * If `func` returns `true` for the next value of this iterator, consume and return it.
     * Otherwise, return `None`.
     *
     * # Examples
     * Consume a number if it's equal to 0.
     * ```kotlin
     * var iter = (0 until 5).peekable()
     * // The first item of the iterator is 0; consume it.
     * check(iter.nextIf({ x -> x == 0 }) == 0)
     * // The next item returned is now 1, so `nextIf` will return `None`.
     * check(iter.nextIf({ x -> x == 0 }) == null)
     * // `nextIf` retains the next item if the predicate evaluates to `false` for it.
     * check(iter.next() == 1)
     * ```
     *
     * Consume any number less than 10.
     * ```kotlin
     * var iter = (1 until 20).peekable()
     * // Consume all numbers less than 10
     * while (iter.nextIf({ x -> x < 10 }) != null) {}
     * // The next value returned will be 10
     * check(iter.next() == 10)
     * ```
     */
    public open fun nextIf(func: (Item) -> Boolean): Item? =
        run {
            val matchVal = this.next()
            when {
                matchVal != null && func(matchVal) -> {
                    val matched = matchVal
                    matched
                }
                else -> {
                    val other = matchVal
                    // Since we called `self.next()`, we consumed `self.peeked`.
                    check(this.peeked == null)
                    this.peeked = other
                    null
                }
            }
        }

    /**
     * Consume and return the next item if it is equal to `expected`.
     *
     * # Example
     * Consume a number if it's equal to 0.
     * ```kotlin
     * var iter = (0 until 5).peekable()
     * // The first item of the iterator is 0; consume it.
     * check(iter.nextIfEq(0) == 0)
     * // The next item returned is now 1, so `nextIfEq` will return `None`.
     * check(iter.nextIfEq(0) == null)
     * // `nextIfEq` retains the next item if it was not equal to `expected`.
     * check(iter.next() == 1)
     * ```
     */
    public open fun <T> nextIfEq(expected: T): Item? = this.nextIf({ next -> next == expected })

    /**
     * Consumes the next value of this iterator and applies a function `f` on it,
     * returning the result if the closure returns `Ok`.
     *
     * Otherwise if the closure returns `Err` the value is put back for the next iteration.
     *
     * The content of the `Err` variant is typically the original value of the closure,
     * but this is not required. If a different value is returned,
     * the next `peek()` or `next()` call will result in this new value.
     * This is similar to modifying the output of `peekMut()`.
     *
     * If the closure panics, the next value will always be consumed and dropped
     * even if the panic is caught, because the closure never returned an `Err` value to put back.
     *
     * See also: [`nextIfMapMut`](Self.nextIfMapMut).
     *
     * # Examples
     *
     * Parse the leading decimal number from an iterator of characters.
     * ```kotlin
     * var iter = "125 GOTO 10".chars().peekable()
     * var lineNum = 0u
     * while (true) {
     *     val matchVal = iter.nextIfMap({ c -> c.toDigit(10u).okOr(c) })
     *     if (matchVal == null) break
     *     val digit = matchVal
     *     lineNum = lineNum * 10u + digit
     * }
     * check(lineNum == 125u)
     * check(iter.joinToString("") == " GOTO 10")
     * ```
     *
     * Matching custom types.
     * ```kotlin
     * sealed class Node {
     *     public data class Comment(public var value: String) : Node()
     *     public data class Red(public var value: String) : Node()
     *     public data class Green(public var value: String) : Node()
     *     public data class Blue(public var value: String) : Node()
     *
     *     public fun rustEquals(other: Node): Boolean = this == other
     * }
     * / **
     *  * Combines all consecutive `Comment` nodes into a single one.
     *  * /
     * fun combineComments(nodes: List<Node>): List<Node> {
     *     var result = ArrayList<Node>(nodes.size)
     *     var iter = ArrayDeque((nodes).toList()).peekable()
     *     var commentText = null
     *     while (true) {
     *         // Typically the closure in .nextIfMap() matches on the input,
     *         // extracts the desired pattern into an `Ok`,
     *         // and puts the rest into an `Err`.
     *         while (true) {
     *             val matchVal = iter.nextIfMap({ node -> when (node) {
     *                 is Node.Comment -> {
     *                     val (text) = node
     *                     Result.success(text)
     *                 }
     *                 else -> {
     *                     val other = node
     *                     Result.failure(other)
     *                 }
     *             } })
     *             if (matchVal == null) break
     *             val text = matchVal
     *             commentText.getOrInsertDefault().pushStr(text)
     *         }
     *         when (val matchVal1 = commentText.also { commentText = null }) {
     *             null -> {}
     *             else -> {
     *                 val text1 = matchVal1
     *                 result.add(Node.Comment(text1))
     *             }
     *         }
     *         when (val matchVal2 = iter.removeFirstOrNull()) {
     *             null -> {
     *                 break
     *             }
     *             else -> {
     *                 val node = matchVal2
     *                 result.add(node)
     *             }
     *         }
     *     }
     *     return result
     * }
     * ```
     */
    public open fun <R> nextIfMap(f: (Item) -> Result<R, Item>): R? {
        val unpeek =
            when (val matchVal = this.next()) {
                null -> {
                    null
                }
                else -> {
                    val item = matchVal
                    run {
                        val matchVal1 = f(item)
                        when (matchVal1) { is Result.Ok<*> -> {
                            val result = matchVal1.value
                            return result
                        }; is Result.Err<*> -> {
                            val item1 = matchVal1.error
                            item1
                        } }
                    }
                }
            }
        this.peeked = unpeek
        return null
    }

    /**
     * Gives a mutable reference to the next value of the iterator and applies a function `f` to it,
     * returning the result and advancing the iterator if `f` returns `Some`.
     *
     * Otherwise, if `f` returns `None`, the next value is kept for the next iteration.
     *
     * If `f` panics, the item that is consumed from the iterator as if `Some` was returned from `f`.
     * The value will be dropped.
     *
     * This is similar to [`nextIfMap`](Self.nextIfMap), except ownership of the item is not given to `f`.
     * This can be preferable if `f` would copy the item anyway.
     *
     * # Examples
     *
     * Parse the leading decimal number from an iterator of characters.
     * ```kotlin
     * var iter = "125 GOTO 10".chars().peekable()
     * var lineNum = 0u
     * while (true) {
     *     val matchVal = iter.nextIfMapMut({ c -> c.toDigit(10u) })
     *     if (matchVal == null) break
     *     val digit = matchVal
     *     lineNum = lineNum * 10u + digit
     * }
     * check(lineNum == 125u)
     * check(iter.joinToString("") == " GOTO 10")
     * ```
     */
    public open fun <R> nextIfMapMut(f: (Item) -> R?): R? {
        val unpeek =
            when (val matchVal = this.next()) {
                null -> {
                    null
                }
                else -> {
                    val item = matchVal
                    when (val matchVal1 = f(item)) {
                        null -> item
                        else -> {
                            val result = matchVal1
                            return result
                        }
                    }
                }
            }
        this.peeked = unpeek
        return null
    }

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <Item, I> new(iter: I): Peekable<I> = Peekable(iter = iter, peeked = null)
    }
}
