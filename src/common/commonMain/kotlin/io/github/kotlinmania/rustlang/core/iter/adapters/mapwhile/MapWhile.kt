// port-lint: source iter/adapters/map_while.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.mapwhile

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

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
        public fun <I, P> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutP: TypeLayout<P>): TypeLayout<MapWhile<I, P>> =
            object : TypeLayout<MapWhile<I, P>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutP.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutP.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutP.sizeBytes), alignBytes)
                override fun readNative(addr: Long): MapWhile<I, P> =
                    MapWhile(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutP).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: MapWhile<I, P>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutP).writeNative(addr + off1, value.predicate)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MapWhile<I, P> =
                    MapWhile(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutP).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MapWhile<I, P>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutP).writeBytes(buf, offset + off1.toInt(), value.predicate)
                }
            }

        public fun <I, P> new(iter: I, predicate: P): MapWhile<I, P> = MapWhile(iter = iter, predicate = predicate)
    }
}

public fun <B, I> MapWhile<I, P>.next(): B? {
    val x = (this.iter.next() ?: return null)
    return (this.predicate)(x)
}

public fun <B, I> MapWhile<I, P>.sizeHint(): Pair<Int, Int?> {
    val (_, upper) = this.iter.sizeHint()
    return Pair(0, upper)
    // can't know a lower bound, due to the predicate
}

public fun <Residual, B, I, Acc, R> MapWhile<I, P>.tryFold(init: Acc, fold: (Acc, B) -> R): R where R : Try<R, Acc, Residual> {
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

public fun <B, I, Aaa> MapWhile<I, P>.fold(init: Aaa, fold: (Aaa, B) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

public fun <Source, I, P> MapWhile<I, P>.asInner(): Source where I : SourceIter<Source> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
