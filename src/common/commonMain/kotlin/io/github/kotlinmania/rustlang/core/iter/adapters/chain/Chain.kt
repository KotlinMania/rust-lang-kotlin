// port-lint: source iter/adapters/chain.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.chain

import io.github.kotlinmania.rustlang.core.default.default
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An iterator that links two iterators together, in a chain.
 *
 * This `class` is created by [chain] or [Iterator.chain]. See their
 * documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import std.iter.Chain
 * import std.slice.Iter
 *
 * val a1 = listOf(1, 2, 3)
 * val a2 = listOf(4, 5, 6)
 * val iter: Chain<Iter, Iter> = a1.iter().chain(a2.iter())
 * ```
 */
public open class Chain<A, B>(
    // These are "fused" with `Option` so we don't need separate state to track which part is
    // already exhausted, and we may also get niche layout for `None`. We don't use the real `Fuse`
    // adapter because its specialization for `FusedIterator` unconditionally descends into the
    // iterator, and that could be expensive to keep revisiting stuff like nested chains. It also
    // hurts compiler performance to add more iterator layers to `Chain`.
    //
    // Only the "first" iterator is actually set `None` when exhausted, depending on whether you
    // iterate forward or backward. If you mix directions, then both sides may be `None`.
    public var a: A?,
    public var b: B?,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Chain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Chain<A, B> = Chain(a, b)

    public open fun next(): Item? {
        return (andThenOrClear(this.a, Iterator::next) ?: closure@{ when (val tryResult = this.b) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }.next() }())
    }

    public open fun count(): Int {
        val aCount =
            when (val matchVal = this.a) {
                null -> 0
                else -> {
                    val a = matchVal
                    a.count()
                }
            }
        val bCount =
            when (val matchVal1 = this.b) {
                null -> 0
                else -> {
                    val b = matchVal1
                    b.count()
                }
            }
        return aCount + bCount
    }

    public open fun <Residual, Acc, R> tryFold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.a) {
            null -> {}
            else -> {
                val a = matchVal
                acc1 = when (val tryResult = a.tryFold(acc1, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                this.a = null
            }
        }
        when (val matchVal1 = this.b) {
            null -> {}
            else -> {
                val b = matchVal1
                acc1 = when (val tryResult = b.tryFold(acc1, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                // we don't fuse the second iterator
            }
        }
        return acc1
    }

    public open fun <Acc> fold(acc: Acc, f: (Acc, Item) -> Acc): Acc {
        var acc1 = acc
        when (val matchVal = this.a) {
            null -> {}
            else -> {
                val a = matchVal
                acc1 = a.fold(acc1, f)
            }
        }
        when (val matchVal1 = this.b) {
            null -> {}
            else -> {
                val b = matchVal1
                acc1 = b.fold(acc1, f)
            }
        }
        return acc1
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        var n1 = n
        when (val matchVal = this.a) {
            null -> {}
            else -> {
                val a = matchVal
                n1 = run {
                    val matchVal1 = a.advanceBy(n1)
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                    }; is Result.Err<*, *> -> {
                        val k = matchVal1.errValue()
                        k.get()
                    } }
                }
                this.a = null
            }
        }
        when (val matchVal2 = this.b) {
            null -> {}
            else -> {
                val b = matchVal2
                return b.advanceBy(n1)
                // we don't fuse the second iterator
            }
        }
        return ((NonZero.new<NonZeroUsizeInner, Int>(n1))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun nth(n: Int): Item? {
        var n1 = n
        when (val matchVal = this.a) {
            null -> {}
            else -> {
                val a = matchVal
                n1 = run {
                    val matchVal1 = a.advanceBy(n1)
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        when (val matchVal2 = a.next()) {
                            null -> 0
                            else -> {
                                val x = matchVal2
                                return x
                            }
                        }
                    }; is Result.Err<*, *> -> {
                        val k = matchVal1.errValue()
                        k.get()
                    } }
                }
                this.a = null
            }
        }
        return (this.b ?: return null).nth(n1)
    }

    public open fun find(predicate: (Item) -> Boolean): Item? {
        return (andThenOrClear(this.a, { a -> a.indexOf(predicate).takeIf { it >= 0 } }) ?: closure1@{ when (val tryResult = this.b) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }.indexOf(predicate).takeIf { it >= 0 } }())
    }

    public open fun last(): Item? {
        // Must exhaust a before b.
        val aLast = this.a?.let(Iterator::last)
        val bLast = this.b?.let(Iterator::last)
        return (bLast ?: aLast)
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        when {
            this.a != null && this.b != null -> {
                val a = this.a
                val b = this.b
                val (aLower, aUpper) = a.sizeHint()
                val (bLower, bUpper) = b.sizeHint()
                val lower = aLower.saturatingAdd(bLower)
                val upper =
                    run {
                        val matchVal = Pair(aUpper, bUpper)
                        when {
                            matchVal.first != null && matchVal.second != null -> {
                                val x = matchVal.first
                                val y = matchVal.second
                                x.checkedAdd(y)
                            }
                            else -> null
                        }
                    }
                Pair(lower, upper)
            }
            this.a != null && this.b == null -> {
                val a1 = this.a
                a1.sizeHint()
            }
            this.a == null && this.b != null -> {
                val b1 = this.b
                b1.sizeHint()
            }
            else -> Pair(0, 0)
        }

    public open fun nextBack(): Item? {
        return (andThenOrClear(this.b, { b -> b.nextBack() }) ?: closure2@{ when (val tryResult = this.a) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure2 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }.nextBack() }())
    }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        var n1 = n
        when (val matchVal = this.b) {
            null -> {}
            else -> {
                val b = matchVal
                n1 = run {
                    val matchVal1 = b.advanceBackBy(n1)
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                    }; is Result.Err<*, *> -> {
                        val k = matchVal1.errValue()
                        k.get()
                    } }
                }
                this.b = null
            }
        }
        when (val matchVal2 = this.a) {
            null -> {}
            else -> {
                val a = matchVal2
                return a.advanceBackBy(n1)
                // we don't fuse the second iterator
            }
        }
        return ((NonZero.new<NonZeroUsizeInner, Int>(n1))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun nthBack(n: Int): Item? {
        var n1 = n
        when (val matchVal = this.b) {
            null -> {}
            else -> {
                val b = matchVal
                n1 = run {
                    val matchVal1 = b.advanceBackBy(n1)
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        when (val matchVal2 = b.nextBack()) {
                            null -> 0
                            else -> {
                                val x = matchVal2
                                return x
                            }
                        }
                    }; is Result.Err<*, *> -> {
                        val k = matchVal1.errValue()
                        k.get()
                    } }
                }
                this.b = null
            }
        }
        return (this.a ?: return null).nthBack(n1)
    }

    public open fun rfind(predicate: (Item) -> Boolean): Item? {
        return (andThenOrClear(this.b, { b -> b.rfind(predicate) }) ?: closure3@{ when (val tryResult = this.a) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }.rfind(predicate) }())
    }

    public open fun <Residual, Acc, R> tryRfold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.b) {
            null -> {}
            else -> {
                val b = matchVal
                acc1 = when (val tryResult = b.tryRfold(acc1, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                this.b = null
            }
        }
        when (val matchVal1 = this.a) {
            null -> {}
            else -> {
                val a = matchVal1
                acc1 = when (val tryResult = a.tryRfold(acc1, f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                // we don't fuse the second iterator
            }
        }
        return acc1
    }

    public open fun <Acc> rfold(acc: Acc, f: (Acc, Item) -> Acc): Acc {
        var acc1 = acc
        when (val matchVal = this.b) {
            null -> {}
            else -> {
                val b = matchVal
                acc1 = b.rfold(acc1, f)
            }
        }
        when (val matchVal1 = this.a) {
            null -> {}
            else -> {
                val a = matchVal1
                acc1 = a.rfold(acc1, f)
            }
        }
        return acc1
    }

    // Note: *both* must be fused to handle double-ended iterators.

    public companion object {
        public fun <A, B> new(a: A, b: B): Chain<A, B> = Chain(a = a, b = b)

        /**
         * Creates a `Chain` from the default values for `A` and `B`.
         *
         * ```kotlin
         * import slice.Iter
         *
         * open class Foo(public var value: Chain<ArrayDeque<UByte>, btreeset.Iter<UByte>>) {
         *     public var field0: Chain<ArrayDeque<UByte>, btreeset.Iter<UByte>>
         *         get() = value
         *         set(v) {
         *             value = v
         *         }
         * }
         * val set = mutableSetOf()
         * val slice: List<UByte> = listOf()
         * var foo = Foo(slice.iter().chain(set.iter()))
         * // take requires `Default`
         * val unused: Chain = take(foo.first)
         * ```
         */
        public fun <A, B> default(): Chain<A, B> = Chain.new(Chain.default(), Chain.default())
    }
}

/**
 * Converts the arguments to iterators and links them together, in a chain.
 *
 * See the documentation of [Iterator.chain] for more.
 *
 * # Examples
 *
 * ```kotlin
 * import std.iter.chain
 *
 * val a = listOf(1, 2, 3)
 * val b = listOf(4, 5, 6)
 * var iter = chain(a, b)
 * check(iter.next() == 1)
 * check(iter.next() == 2)
 * check(iter.next() == 3)
 * check(iter.next() == 4)
 * check(iter.next() == 5)
 * check(iter.next() == 6)
 * check(iter.next() == null)
 * ```
 */
public fun <A, B> chain(a: A, b: B): Chain<IntoIter, IntoIter> where B : Iterable<Item> = Chain.new(a.iterator(), b.iterator())

public fun <T, U> andThenOrClear(opt: T?, f: (T) -> U?): U? {
    var opt1 = opt
    val x = f((opt1 ?: return null))
    if (x == null) {
        opt1 = null
    }
    return x
}
