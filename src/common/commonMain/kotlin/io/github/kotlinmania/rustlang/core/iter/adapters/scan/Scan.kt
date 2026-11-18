// port-lint: source iter/adapters/scan.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.scan

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
 * An iterator to maintain state while iterating another iterator.
 *
 * This `class` is created by the [scan] method on [Iterator]. See its
 * documentation for more.
 *
 * [scan]: Iterator.scan
 * [Iterator]: trait.Iterator.html
 */
public open class Scan<I, St, F>(
    public var iter: I,
    public var f: F,
    public var state: St,
) {
    public open fun clone(): Scan<I, St, F> = Scan(iter, f, state)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Scan").field("iter", this.iter).field("state", this.state).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, St, F> new(iter: I, state: St, f: F): Scan<I, St, F> = Scan(iter = iter, state = state, f = f)
    }
}

public fun <Item, B, I, St> Scan<I, St, F>.next(): B? where I : Iterator<Item> {
    val a = (this.iter.next() ?: return null)
    return (this.f)(this.state, a)
}

public fun <Item, B, I, St> Scan<I, St, F>.sizeHint(): Pair<Int, Int?> where I : Iterator<Item> {
    val (_, upper) = this.iter.sizeHint()
    return Pair(0, upper)
    // can't know a lower bound, due to the scan function
}

public fun <Item, Residual, B, I, St, Acc, R> Scan<I, St, F>.tryFold(init: Acc, fold: (Acc, B) -> R): R where I : Iterator<Item>, R : Try<R, Acc, Residual> {
    fun <Residual, T, St, B, Acc, R : Try<R, Acc, Residual>> scan(state: St, f: (St, T) -> B?, fold1: (Acc, B) -> R): (Acc, T) -> ControlFlow<R, Acc> =
        { acc, x -> when (val matchVal = f(state, x)) {
            null -> ControlFlow.Break(acc)
            else -> {
                val x1 = matchVal
                ControlFlow.fromTry(fold1(acc, x1))
            }
        } }
    val state = this.state
    val f = this.f
    return this.iter.tryFold(init, scan(state, f, fold)).intoTry()
}

public fun <Item, B, I, St, Aaa> Scan<I, St, F>.fold(init: Aaa, fold: (Aaa, B) -> Aaa): Aaa where I : Iterator<Item> = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

public fun <Source, St, F, I> Scan<I, St, F>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
