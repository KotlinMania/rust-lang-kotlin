package io.github.kotlinmania.rustlang.core.iter.adapters

import io.github.kotlinmania.rustlang.core.iter.InPlaceIterable
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit

public typealias Source<I> = I.Source

/**
 * An iterator adapter that produces output as long as the underlying
 * iterator produces values where `Try.branch` says to `ControlFlow.Continue`.
 *
 * If a `ControlFlow.Break` is encountered, the iterator stops and the
 * residual is stored.
 */
internal open class GenericShunt<I, R>(
    public var iter: I,
    public var residual: R?,
) : SourceIter<Any?, Source>, InPlaceIterable, Iterable<Output> {
    override fun iterator(): Iterator<Output> = object : AbstractIterator<Output>() {
        override fun computeNext() {
            when (val v = this@GenericShunt.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Output? = this.tryForEach(ControlFlow.Break).breakValue()

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.residual != null) {
            Pair(0, 0)
        } else {
            val (_, upper) = this.iter.sizeHint()
            Pair(0, upper)
        }

    public open fun <B, T> tryFold(init: B, f: (B, Output) -> T): T where T : Try<T, B, Any?> =
        this.iter.tryFold(init, { acc, x -> when (val matchVal = x.branch()) {
            is ControlFlow.Continue -> {
                val (x1) = matchVal
                ControlFlow.fromTry(f(acc, x1))
            }
            is ControlFlow.Break -> {
                val (r) = matchVal
                this.residual = r
                ControlFlow.Break(acc)
            }
        } }).intoTry()

    public open fun <Aaa> fold(init: Aaa, fold: (Aaa, Output) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        // SAFETY: GenericShunt.next calls `I.tryForEach`, which has to advance `iter`
        // in order to return `Some(_)`. Since `iter` has type `I: InPlaceIterable` it's
        // guaranteed that at least one item will be moved out from the underlying source.
        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
    }
}
