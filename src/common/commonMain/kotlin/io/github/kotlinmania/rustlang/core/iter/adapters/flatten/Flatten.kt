// port-lint: source iter/adapters/flatten.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.flatten

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.fmt.Result
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.fuse.Fuse
import io.github.kotlinmania.rustlang.core.iter.adapters.map.Map
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try

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
 * An iterator that maps each element to an iterator, and yields the elements
 * of the produced iterators.
 *
 * This `class` is created by [Iterator.flatMap]. See its documentation
 * for more.
 */
public open class FlatMap<I, U, F> internal constructor(
    innerCtorArg: FlattenCompat<Map<I, F>, IntoIter>,
) : TrustedLen, SourceIter<Source>, Iterable<Item> {
    public var inner: FlattenCompat<Map<I, F>, IntoIter> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@FlatMap.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun intoParts(): Triple<IntoIter?, I?, IntoIter?> = Triple(this.inner.frontiter, this.inner.iter.intoInner().map({ it.intoInner() }), this.inner.backiter)

    public open fun clone(): FlatMap<I, U, F> = FlatMap(inner = this.inner.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "FlatMap").field("inner", this.inner).finish()

    public override fun next(): Item? = this.inner.next()

    public override fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public override fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryFold(init, fold)

    public override fun <Acc> fold(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.inner.fold(init, fold)

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBy(n)

    public override fun count(): Int = this.inner.count()

    public override fun last(): Item? = this.inner.last()

    public open fun nextBack(): Item? = this.inner.nextBack()

    public open fun <Residual, Acc, R> tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryRfold(init, fold)

    public open fun <Acc> rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.inner.rfold(init, fold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBackBy(n)

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.inner.iter.asInner()
    }

    public companion object {
        public operator fun <I, U, F> invoke(
            inner: FlattenCompat<Map<I, F>, IntoIter>,
        ): FlatMap<I, U, F> = FlatMap<I, U, F>(
            innerCtorArg = inner,
        )

        public fun <I, U> new(iter: I, f: (Item) -> U): FlatMap<I, U, (Item) -> U> = FlatMap(inner = FlattenCompat.new(iter.map(f)))
    }
}

/**
 * An iterator that flattens one level of nesting in an iterator of things
 * that can be turned into iterators.
 *
 * This `class` is created by the [flatten] method on [Iterator]. See its
 * documentation for more.
 *
 * [flatten]: Iterator.flatten()
 */
public open class Flatten<I> internal constructor(
    innerCtorArg: FlattenCompat<I, IntoIter>,
) : TrustedLen, SourceIter<Source>, Iterable<U.Item> {
    public var inner: FlattenCompat<I, IntoIter> = innerCtorArg
        internal set

    override fun iterator(): Iterator<U.Item> = object : AbstractIterator<U.Item>() {
        override fun computeNext() {
            when (val v = this@Flatten.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Flatten").field("inner", this.inner).finish()

    public open fun clone(): Flatten<I> = Flatten(inner = this.inner.clone())

    public override fun next(): Item? = this.inner.next()

    public override fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public override fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, U.Item) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryFold(init, fold)

    public override fun <Acc> fold(init: Acc, fold: (Acc, U.Item) -> Acc): Acc = this.inner.fold(init, fold)

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBy(n)

    public override fun count(): Int = this.inner.count()

    public override fun last(): Item? = this.inner.last()

    public open fun nextBack(): Item? = this.inner.nextBack()

    public open fun <Residual, Acc, R> tryRfold(init: Acc, fold: (Acc, U.Item) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryRfold(init, fold)

    public open fun <Acc> rfold(init: Acc, fold: (Acc, U.Item) -> Acc): Acc = this.inner.rfold(init, fold)

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.inner.advanceBackBy(n)

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.inner.iter.asInner()
    }

    public companion object {
        public operator fun <I> invoke(
            inner: FlattenCompat<I, IntoIter>,
        ): Flatten<I> = Flatten<I>(
            innerCtorArg = inner,
        )

        public fun <I> new(iter: I): Flatten<I> = Flatten(inner = FlattenCompat.new(iter))

        /**
         * Creates a `Flatten` iterator from the default value of `I`.
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * public fun doctestEntry() {
         *     val iter: Flatten<ArrayDeque<UByteArray>> = Default.default()
         *     check(iter.count() == 0)
         * }
         * ```
         */
        public fun <I> default(defaultIFn: () -> I): Flatten<I> = Flatten.new(defaultIFn())
    }
}

/**
 * Real logic of both `Flatten` and `FlatMap` which simply delegate to
 * this type.
 */
public open class FlattenCompat<I, U>(
    public var iter: Fuse<I>,
    public var frontiter: U?,
    public var backiter: U?,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@FlattenCompat.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): FlattenCompat<I, U> = FlattenCompat(iter, frontiter, backiter)

    /**
     * Folds the inner iterators into an accumulator by applying an operation.
     *
     * Folds over the inner iterators, not over their elements. Is used by the `fold`, `count`,
     * and `last` methods.
     */
    public open fun <Acc> iterFold(acc: Acc, fold: (Acc, U) -> Acc): Acc {
        var acc1 = acc
        fun <T, Acc> flatten(fold1: (Acc, IntoIter) -> Acc): (Acc, T) -> Acc = { acc2, iter -> fold1(acc2, iter.intoIter()) }
        when (val matchVal = this.frontiter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = fold(acc1, iter)
            }
        }
        acc1 = this.iter.fold(acc1, flatten(fold))
        when (val matchVal1 = this.backiter) {
            null -> {}
            else -> {
                val iter1 = matchVal1
                acc1 = fold(acc1, iter1)
            }
        }
        return acc1
    }

    /**
     * Folds over the inner iterators as long as the given function returns successfully,
     * always storing the most recent inner iterator in `self.frontiter`.
     *
     * Folds over the inner iterators, not over their elements. Is used by the `tryFold` and
     * `advanceBy` methods.
     */
    public open fun <Residual, Acc, R> iterTryFold(acc: Acc, fold: (Acc, U) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        fun <Residual, T, Acc, R : Try<R, Acc, Residual>> flatten(frontiter: IntoIter?, fold1: (Acc, IntoIter) -> R): (Acc, T) -> R = { acc2, iter -> fold1(acc2, frontiter.insert(iter.intoIter())) }
        when (val matchVal = this.frontiter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = fold(acc1, iter)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            }
        }
        this.frontiter = null
        acc1 = when (val tryResult = this.iter.tryFold(acc1, flatten(this.frontiter, fold))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        this.frontiter = null
        when (val matchVal1 = this.backiter) {
            null -> {}
            else -> {
                val iter1 = matchVal1
                acc1 = when (val tryResult = fold(acc1, iter1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            }
        }
        this.backiter = null
        return acc1
    }

    /**
     * Folds the inner iterators into an accumulator by applying an operation, starting form the
     * back.
     *
     * Folds over the inner iterators, not over their elements. Is used by the `rfold` method.
     */
    public open fun <Acc> iterRfold(acc: Acc, fold: (Acc, U) -> Acc): Acc {
        var acc1 = acc
        fun <T, Acc> flatten(fold1: (Acc, IntoIter) -> Acc): (Acc, T) -> Acc = { acc2, iter -> fold1(acc2, iter.intoIter()) }
        when (val matchVal = this.backiter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = fold(acc1, iter)
            }
        }
        acc1 = this.iter.rfold(acc1, flatten(fold))
        when (val matchVal1 = this.frontiter) {
            null -> {}
            else -> {
                val iter1 = matchVal1
                acc1 = fold(acc1, iter1)
            }
        }
        return acc1
    }

    /**
     * Folds over the inner iterators in reverse order as long as the given function returns
     * successfully, always storing the most recent inner iterator in `self.backiter`.
     *
     * Folds over the inner iterators, not over their elements. Is used by the `tryRfold` and
     * `advanceBackBy` methods.
     */
    public open fun <Residual, Acc, R> iterTryRfold(acc: Acc, fold: (Acc, U) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        fun <Output, Residual, T, Acc, R : Try<R, Output, Residual>> flatten(backiter: IntoIter?, fold1: (Acc, IntoIter) -> R): (Acc, T) -> R = { acc2, iter -> fold1(acc2, backiter.insert(iter.intoIter())) }
        when (val matchVal = this.backiter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = fold(acc1, iter)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            }
        }
        this.backiter = null
        acc1 = when (val tryResult = this.iter.tryRfold(acc1, flatten(this.backiter, fold))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        this.backiter = null
        when (val matchVal1 = this.frontiter) {
            null -> {}
            else -> {
                val iter1 = matchVal1
                acc1 = when (val tryResult = fold(acc1, iter1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            }
        }
        this.frontiter = null
        return acc1
    }

    // See also the `OneShot` specialization below.
    public open fun next(): Item? {
        while (true) {
            if (andThenOrClear(this.frontiter, Iterator::next) != null) {
                val elt = andThenOrClear(this.frontiter, Iterator::next)
                return elt
            }
            when (val matchVal = this.iter.next()) {
                null -> { return andThenOrClear(this.backiter, Iterator::next) }
                else -> {
                    val inner = matchVal
                    this.frontiter = inner.intoIter()
                }
            }
        }
    }

    public open fun sizeHint(sizeHintUFn: (U) -> Pair<Int, Int?>): Pair<Int, Int?> {
        val (flo, fhi) = (this.frontiter?.let(sizeHintUFn) ?: Pair(0, 0))
        val (blo, bhi) = (this.backiter?.let(sizeHintUFn) ?: Pair(0, 0))
        val lo = flo.saturatingAdd(blo)
        when (val matchVal = Item.size()) {
            null -> {}
            else -> {
                val fixedSize = matchVal
                val (lower, upper) = this.iter.sizeHint()
                val lower1 = lower.saturatingMul(fixedSize).saturatingAdd(lo)
                val upper1 = when (val tryResult = when (val tryResult = when (val tryResult = fhi) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }.checkedAdd(when (val tryResult = bhi) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }.checkedAdd(when (val tryResult = fixedSize.checkedMul(when (val tryResult = upper) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
                return Pair(lower1, upper1)
            }
        }
        return run {
            val matchVal1 = Triple(this.iter.sizeHint(), fhi, bhi)
            when {
                matchVal1.first.first == 0 && matchVal1.first.second != null && matchVal1.first.second == 0 && matchVal1.second != null && matchVal1.third != null -> {
                    val a = matchVal1.second
                    val b = matchVal1.third
                    Pair(lo, a.checkedAdd(b))
                }
                else -> Pair(lo, null)
            }
        }
    }

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        fun <Residual, U, Acc, R : Try<R, Acc, Residual>> flatten(fold1: (Acc, Item) -> R): (Acc, U) -> R = { acc, iter -> iter.tryFold(acc, fold1) }
        return this.iterTryFold(init, flatten(fold))
    }

    public open fun <Acc> fold(init: Acc, fold: (Acc, Item) -> Acc): Acc {
        fun <U, Acc> flatten(fold1: (Acc, Item) -> Acc): (Acc, U) -> Acc = { acc, iter -> iter.fold(acc, fold1) }
        return this.iterFold(init, flatten(fold))
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        fun <U> advance(n1: Int, iter: U): ControlFlow<Unit, Int> =
            run {
                val matchVal = iter.advanceBy(n1)
                when (matchVal) { is Result.Ok -> {
                    ControlFlow.Break()
                }; is Result.Err -> {
                    val remaining = matchVal.error
                    ControlFlow.Continue(remaining.get())
                } }
            }
        return when (val matchVal = this.iterTryFold(n, ::advance)) {
            is ControlFlow.Continue -> {
                val (remaining) = matchVal
                ((NonZero.new<NonZeroUsizeInner, Int>(remaining))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
            }
            else -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }
    }

    public open fun count(): Int {
        fun <U> count(acc: Int, iter: U): Int = acc + iter.count()
        return this.iterFold(0, ::count)
    }

    public open fun last(): Item? {
        fun <U> last(last: Item?, iter: U): Item? = (iter.last() ?: last)
        return this.iterFold(null, ::last)
    }

    // See also the `OneShot` specialization below.
    public open fun nextBack(): Item? {
        while (true) {
            if (andThenOrClear(this.backiter, { b -> b.nextBack() }) != null) {
                val elt = andThenOrClear(this.backiter, { b -> b.nextBack() })
                return elt
            }
            when (val matchVal = this.iter.nextBack()) {
                null -> { return andThenOrClear(this.frontiter, { f -> f.nextBack() }) }
                else -> {
                    val inner = matchVal
                    this.backiter = inner.intoIter()
                }
            }
        }
    }

    public open fun <Residual, Acc, R> tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        fun <Residual, U, Acc, R : Try<R, Acc, Residual>> flatten(fold1: (Acc, Item) -> R): (Acc, U) -> R = { acc, iter -> iter.tryRfold(acc, fold1) }
        return this.iterTryRfold(init, flatten(fold))
    }

    public open fun <Acc> rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc {
        fun <U, Acc> flatten(fold1: (Acc, Item) -> Acc): (Acc, U) -> Acc = { acc, iter -> iter.rfold(acc, fold1) }
        return this.iterRfold(init, flatten(fold))
    }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        fun <U> advance(n1: Int, iter: U): ControlFlow<Unit, Int> =
            run {
                val matchVal = iter.advanceBackBy(n1)
                when (matchVal) { is Result.Ok -> {
                    ControlFlow.Break()
                }; is Result.Err -> {
                    val remaining = matchVal.error
                    ControlFlow.Continue(remaining.get())
                } }
            }
        return when (val matchVal = this.iterTryRfold(n, ::advance)) {
            is ControlFlow.Continue -> {
                val (remaining) = matchVal
                ((NonZero.new<NonZeroUsizeInner, Int>(remaining))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
            }
            else -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }
    }

    // Specialization: When the inner iterator `U` never returns more than one item, the `frontiter` and
    // `backiter` states are a waste, because they'll always have already consumed their item. So in
    // this impl, we completely ignore them and just focus on `self.iter`, and we only call the inner
    // `U.next()` one time.
    //
    // It's mostly fine if we accidentally mix this with the more generic impls, e.g. by forgetting to
    // specialize one of the methods. If the other impl did set the front or back, we wouldn't see it
    // here, but it would be empty anyway; and if the other impl looked for a front or back that we
    // didn't bother setting, it would just see `None` (or a previous empty) and move on.
    //
    // An exception to that is `advanceBy(0)` and `advanceBackBy(0)`, where the generic impls may set
    // `frontiter` or `backiter` without consuming the item, so we **must** override those.
    public open fun nextIterator(): Item? {
        while (true) {
            val matchVal = this.iter.next()
            if (matchVal == null) break
            val inner = matchVal
            if (inner.intoIter().next() != null) {
                val item = inner.intoIter().next()
                return item
            }
        }
        return null
    }

    public open fun sizeHintIterator(): Pair<Int, Int?> {
        val (lower, upper) = this.iter.sizeHint()
        return when (val matchVal = Item.size()) {
            0 -> Pair(0, 0)
            1 -> Pair(lower, upper)
            else -> Pair(0, upper)
        }
    }

    public open fun <Residual, Acc, R> tryFoldIterator(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryFold(init, tryFlattenOne(fold))

    public open fun <Acc> foldIterator(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.iter.fold(init, flattenOne(fold))

    public open fun advanceByIterator(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(n)) {
            null -> {
                // Just advance the outer iterator
                this.iter.advanceBy(0)
            }
            else -> {
                val n1 = matchVal
                this.iter.tryFold(n1, ::advanceByOne).mapOr(Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit), { Result.Err(it) })
            }
        }

    public open fun countIterator(): Int = this.iter.mapNotNull(::intoItem).count()

    public open fun lastIterator(): Item? = this.iter.mapNotNull(::intoItem).last()

    // Note: We don't actually care about `U: DoubleEndedIterator`, since forward and backward are the
    // same for a one-shot iterator, but we have to keep that to match the default specialization.
    public open fun nextBackDoubleEndedIterator(): Item? {
        while (true) {
            val matchVal = this.iter.nextBack()
            if (matchVal == null) break
            val inner = matchVal
            if (inner.intoIter().next() != null) {
                val item = inner.intoIter().next()
                return item
            }
        }
        return null
    }

    public open fun <Residual, Acc, R> tryRfoldDoubleEndedIterator(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryRfold(init, tryFlattenOne(fold))

    public open fun <Acc> rfoldDoubleEndedIterator(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.iter.rfold(init, flattenOne(fold))

    public open fun advanceBackByDoubleEndedIterator(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(n)) {
            null -> {
                // Just advance the outer iterator
                this.iter.advanceBackBy(0)
            }
            else -> {
                val n1 = matchVal
                this.iter.tryRfold(n1, ::advanceByOne).mapOr(Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit), { Result.Err(it) })
            }
        }

    public companion object {
        /**
         * Adapts an iterator by flattening it, for use in `flatten()` and `flatMap()`.
         */
        public fun <I, U> new(iter: I): FlattenCompat<I, U> = FlattenCompat(iter = iter.fuse(), frontiter = null, backiter = null)
    }
}

public interface ConstSizeIntoIterator {
    public fun size(): Int?
}

public fun <T> constSizeIntoIteratorSizeT(): Int? = null

public fun <T> constSizeIntoIteratorSizeTArrayn(n: Int): Int? = n

public fun <T, U> andThenOrClear(opt: T?, f: (T) -> U?): U? {
    var opt1 = opt
    val x = f((opt1 ?: return null))
    if (x == null) {
        opt1 = null
    }
    return x
}

/**
 * Specialization trait for iterator types that never return more than one item.
 *
 * Note that we still have to deal with the possibility that the iterator was
 * already exhausted before it came into our control.
 */
public interface OneShot

// These all have exactly one item, if not already consumed.

// These are always empty, which is fine to optimize too.

// These adapters never increase the number of items.
// (There are more possible, but for now this matches BoundedSize above.)

public fun <I> intoItem(inner: I): Item? = inner.iterator().next()

public fun <I, Acc> flattenOne(fold: (Acc, Item) -> Acc): (Acc, I) -> Acc =
    { acc, inner -> when (val matchVal = inner.intoIter().next()) {
        null -> acc
        else -> {
            val item = matchVal
            fold(acc, item)
        }
    } }

public fun <Residual, I, Acc, R : Try<R, Acc, Residual>> tryFlattenOne(fold: (Acc, Item) -> R): (Acc, I) -> R =
    { acc, inner -> when (val matchVal = inner.intoIter().next()) {
        null -> acc
        else -> {
            val item = matchVal
            fold(acc, item)
        }
    } }

public fun <I> advanceByOne(n: NonZero<Int, NonZeroUsizeInner>, inner: I): NonZero<Int, NonZeroUsizeInner>? =
    when (val matchVal = inner.iterator().next()) {
        null -> n
        else -> NonZero.new<NonZeroUsizeInner, Int>(n.get() - 1)
    }
