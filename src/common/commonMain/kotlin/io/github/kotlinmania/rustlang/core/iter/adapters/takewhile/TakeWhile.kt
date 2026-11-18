// port-lint: source iter/adapters/take_while.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.takewhile

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
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
 * An iterator that only accepts elements while `predicate` returns `true`.
 *
 * This `class` is created by the [takeWhile] method on [Iterator]. See its
 * documentation for more.
 *
 * [takeWhile]: Iterator.takeWhile
 * [Iterator]: trait.Iterator.html
 */
public open class TakeWhile<I, P>(
    public var iter: I,
    public var flag: Boolean,
    public var predicate: P,
) {
    public open fun clone(): TakeWhile<I, P> = TakeWhile(iter, flag, predicate)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "TakeWhile").field("iter", this.iter).field("flag", this.flag).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <I, P> new(iter: I, predicate: P): TakeWhile<I, P> = TakeWhile(iter = iter, flag = false, predicate = predicate)
    }
}

public fun <Item, I : Iterator<Item>> TakeWhile<I, P>.next(): Item? {
    return if (this.flag) {
        null
    } else {
        val x = (this.iter.next() ?: return null)
        if ((this.predicate)(x)) {
            x
        } else {
            this.flag = true
            null
        }
    }
}

public fun <Item, I : Iterator<Item>> TakeWhile<I, P>.sizeHint(): Pair<Int, Int?> =
    if (this.flag) {
        Pair(0, 0)
    } else {
        val (_, upper) = this.iter.sizeHint()
        Pair(0, upper)
        // can't know a lower bound, due to the predicate
    }

public fun <Item, Residual, I : Iterator<Item>, Acc, R> TakeWhile<I, P>.tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
    fun <Residual, T, Acc, R : Try<R, Acc, Residual>> check(flag: RustMutableReference<Boolean>, p: (T) -> Boolean, fold1: (Acc, T) -> R): (Acc, T) -> ControlFlow<R, Acc> {
        val flag1 = flag
        return { acc, x ->
            if (p(x)) {
                ControlFlow.fromTry(fold1(acc, x))
            } else {
                (flag1).write(true)
                ControlFlow.Break(acc)
            }
        }
    }
    return if (this.flag) {
        init
    } else {
        val flag = RustMutableReference(allocateNativeCell(this.flag, PrimitiveLayouts.BOOL), PrimitiveLayouts.BOOL)
        val p = this.predicate
        this.iter.tryFold(init, check(flag, p, fold)).intoTry()
    }
}

public fun <Item, I : Iterator<Item>, Aaa> TakeWhile<I, P>.fold(init: Aaa, fold: (Aaa, Item) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

public fun <Source, P, I> TakeWhile<I, P>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
