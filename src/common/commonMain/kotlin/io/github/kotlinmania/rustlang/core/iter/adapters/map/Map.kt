// port-lint: source iter/adapters/map.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.map

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

private class DebugStruct(
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

public typealias Source<I> = I.Source

/**
 * An iterator that maps the values of `iter` with `f`.
 *
 * This `class` is created by the [map] method on [Iterator]. See its
 * documentation for more.
 *
 * [map]: Iterator.map
 * [Iterator]: trait.Iterator.html
 *
 * # Notes about side effects
 *
 * The [map] iterator implements [DoubleEndedIterator], meaning that
 * you can also [map] backwards:
 *
 * ```kotlin
 * val v: List<Int> = listOf(1, 2, 3).intoIter().map({ x -> x + 1 }).rev().toMutableList()
 * check(v == listOf(4, 3, 2))
 * ```
 *
 * [DoubleEndedIterator]: trait.DoubleEndedIterator.html
 *
 * But if your closure has state, iterating backwards may act in a way you do
 * not expect. Let's go through an example. First, in the forward direction:
 *
 * ```kotlin
 * var c = 0
 * for (pair in listOf('a', 'b', 'c').intoIter().map({ letter ->
 *     c += 1
 *     Pair(letter, c)
 * })) {
 *     println("$pair")
 * }
 * ```
 *
 * This will print `(`a`, 1), (`b`, 2), (`c`, 3)`.
 *
 * Now consider this twist where we add a call to `rev`. This version will
 * print `(`c`, 1), (`b`, 2), (`a`, 3)`. Note that the letters are reversed,
 * but the values of the counter still go in order. This is because `map()` is
 * still being called lazily on each item, but we are popping items off the
 * back of the vector now, instead of shifting them from the front.
 *
 * ```kotlin
 * var c = 0
 * for (pair in listOf('a', 'b', 'c').intoIter().map({ letter ->
 *     c += 1
 *     Pair(letter, c)
 * }).rev()) {
 *     println("$pair")
 * }
 * ```
 */
public open class Map<I, F>(
    // Used for `SplitWhitespace` and `SplitAsciiWhitespace` `asStr` methods
    public var iter: I,
    public var f: F,
) {
    public open fun clone(): Map<I, F> = Map(iter, f)

    public open fun intoInner(): I = this.iter

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Map").field("iter", this.iter).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, F> new(iter: I, f: F): Map<I, F> = Map(iter = iter, f = f)
    }
}

public fun <T, B, Acc> mapFold(f: (T) -> B, g: (Acc, B) -> Acc): (Acc, T) -> Acc = { acc, elt -> g(acc, f(elt)) }

public fun <T, B, Acc, R> mapTryFold(f: (T) -> B, g: (Acc, B) -> R): (Acc, T) -> R = { acc, elt -> g(acc, f(elt)) }

public fun <Item, B, I : Iterator<Item>> Map<I, F>.next(): B? = this.iter.next()?.let(this.f)

public fun <Item, B, I : Iterator<Item>> Map<I, F>.sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

public fun <Item, Residual, B, I : Iterator<Item>, Acc, R> Map<I, F>.tryFold(init: Acc, g: (Acc, B) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryFold(init, mapTryFold(this.f, g))

public fun <Item, B, I : Iterator<Item>, Acc> Map<I, F>.fold(init: Acc, g: (Acc, B) -> Acc): Acc = this.iter.fold(init, mapFold(this.f, g))

public fun <Item, B, I : Iterator<Item>> Map<I, F>.iteratorGetUnchecked(idx: Int): B {
    // SAFETY: the caller must uphold the contract for
    // `Iterator.__iterator_get_unchecked`.
    return (this.f)(tryGetUnchecked(this.iter, idx))
}

public fun <Item, B, I : Iterator<Item>> Map<I, F>.nextBack(): B? = this.iter.nextBack()?.let(this.f)

public fun <Item, Residual, B, I : Iterator<Item>, Acc, R> Map<I, F>.tryRfold(init: Acc, g: (Acc, B) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryRfold(init, mapTryFold(this.f, g))

public fun <Item, B, I : Iterator<Item>, Acc> Map<I, F>.rfold(init: Acc, g: (Acc, B) -> Acc): Acc = this.iter.rfold(init, mapFold(this.f, g))

public fun <Item, B, I : Iterator<Item>> Map<I, F>.len(): Int = this.iter.len()

public fun <Item, B, I : Iterator<Item>> Map<I, F>.isEmpty(): Boolean = this.iter.isEmpty()

public val MAY_HAVE_SIDE_EFFECT: Boolean = true

public fun <Source, I, F> Map<I, F>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
