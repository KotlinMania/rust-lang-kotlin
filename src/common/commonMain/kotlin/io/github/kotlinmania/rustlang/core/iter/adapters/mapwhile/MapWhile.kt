// port-lint: source iter/adapters/map_while.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.mapwhile

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

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
 * An iterator that only accepts elements while `predicate` returns `Some(_)`.
 *
 * This `class` is created by the [mapWhile] method on [Iterator]. See its
 * documentation for more.
 *
 * [mapWhile]: Iterator.mapWhile
 * [Iterator]: trait.Iterator.html
 */
public open class MapWhile<I, P>(
    public var iter: I,
    public var predicate: P,
) {
    public open fun clone(): MapWhile<I, P> = MapWhile(iter, predicate)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "MapWhile").field("iter", this.iter).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, P> new(iter: I, predicate: P): MapWhile<I, P> = MapWhile(iter = iter, predicate = predicate)
    }
}

public fun <Item, B, I : Iterator<Item>> MapWhile<I, P>.next(): B? {
    val x = (this.iter.next() ?: return null)
    return (this.predicate)(x)
}

public fun <Item, B, I : Iterator<Item>> MapWhile<I, P>.sizeHint(): Pair<Int, Int?> {
    val (_, upper) = this.iter.sizeHint()
    return Pair(0, upper)
    // can't know a lower bound, due to the predicate
}

public fun <Item, Residual, B, I : Iterator<Item>, Acc, R> MapWhile<I, P>.tryFold(init: Acc, fold: (Acc, B) -> R): R where R : Try<R, Acc, Residual> {
    val iter = this.iter
    val predicate = this.predicate
    return iter.tryFold(init, { acc, x -> when (val matchVal = predicate(x)) {
        null -> ControlFlow.Break(acc)
        else -> {
            val item = matchVal
            ControlFlow.fromTry(fold(acc, item))
        }
    } }).intoTry()
}

public fun <Item, B, I : Iterator<Item>, Aaa> MapWhile<I, P>.fold(init: Aaa, fold: (Aaa, B) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

public fun <Source, I, P> MapWhile<I, P>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
