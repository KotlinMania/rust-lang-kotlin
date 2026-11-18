// port-lint: source iter/adapters/inspect.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.inspect

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
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
 * An iterator that calls a function with a reference to each element before
 * yielding it.
 *
 * This `class` is created by the [inspect] method on [Iterator]. See its
 * documentation for more.
 *
 * [inspect]: Iterator.inspect
 * [Iterator]: trait.Iterator.html
 */
public open class Inspect<I, F>(
    public var iter: I,
    public var f: F,
) {
    public open fun clone(): Inspect<I, F> = Inspect(iter, f)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Inspect").field("iter", this.iter).finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun doInspect(elt: Item?): Item? {
        if (elt != null) {
            val a = elt
            (this.f)(a)
        }
        return elt
    }

    public companion object {
        public fun <I, F> new(iter: I, f: F): Inspect<I, F> = Inspect(iter = iter, f = f)
    }
}

public fun <T, Acc> inspectFold(f: (T) -> Unit, fold: (Acc, T) -> Acc): (Acc, T) -> Acc =
    { acc, item ->
        f(item)
        fold(acc, item)
    }

public fun <T, Acc, R> inspectTryFold(f: (T) -> Unit, fold: (Acc, T) -> R): (Acc, T) -> R =
    { acc, item ->
        f(item)
        fold(acc, item)
    }

public fun <Item, I : Iterator<Item>> Inspect<I, F>.next(): Item? {
    val next = this.iter.next()
    return this.doInspect(next)
}

public fun <Item, I : Iterator<Item>> Inspect<I, F>.sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

public fun <Item, Residual, I : Iterator<Item>, Acc, R> Inspect<I, F>.tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryFold(init, inspectTryFold(this.f, fold))

public fun <Item, I : Iterator<Item>, Acc> Inspect<I, F>.fold(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.iter.fold(init, inspectFold(this.f, fold))

public fun <Item, I : Iterator<Item>> Inspect<I, F>.nextBack(): Item? {
    val next = this.iter.nextBack()
    return this.doInspect(next)
}

public fun <Item, Residual, I : Iterator<Item>, Acc, R> Inspect<I, F>.tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.iter.tryRfold(init, inspectTryFold(this.f, fold))

public fun <Item, I : Iterator<Item>, Acc> Inspect<I, F>.rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc = this.iter.rfold(init, inspectFold(this.f, fold))

public fun <Item, I : Iterator<Item>> Inspect<I, F>.len(): Int = this.iter.len()

public fun <Item, I : Iterator<Item>> Inspect<I, F>.isEmpty(): Boolean = this.iter.isEmpty()

public fun <Source, I, F> Inspect<I, F>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
