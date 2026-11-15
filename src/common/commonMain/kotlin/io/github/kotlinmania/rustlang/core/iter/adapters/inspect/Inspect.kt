// port-lint: source iter/adapters/inspect.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.inspect

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.ops.trytrait.Try

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
) : TrustedFused, SourceIter<Source>, InPlaceIterable, Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Inspect.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Inspect<I, F> = Inspect(iter, f)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Inspect").field("iter", this.iter).finish()

    public open fun doInspect(elt: Item?): Item? {
        if (elt != null) {
            val a = elt
            (this.f)(a)
        }
        return elt
    }

    public open fun next(): Item? {
        val next = this.iter.next()
        return this.doInspect(next)
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <Acc, R> tryFold(init: Acc, fold: (Acc, I.Item) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryFold(init, inspectTryFold(this.f, fold))

    public open fun <Acc> fold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc = this.iter.fold(init, inspectFold(this.f, fold))

    public open fun nextBack(): Item? {
        val next = this.iter.nextBack()
        return this.doInspect(next)
    }

    public open fun <Acc, R> tryRfold(init: Acc, fold: (Acc, I.Item) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryRfold(init, inspectTryFold(this.f, fold))

    public open fun <Acc> rfold(init: Acc, fold: (Acc, I.Item) -> Acc): Acc = this.iter.rfold(init, inspectFold(this.f, fold))

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I, F> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutF: TypeLayout<F>): TypeLayout<Inspect<I, F>> =
            object : TypeLayout<Inspect<I, F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutF.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutF.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutF.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Inspect<I, F> =
                    Inspect(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutF).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Inspect<I, F>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutF).writeNative(addr + off1, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Inspect<I, F> =
                    Inspect(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutF).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Inspect<I, F>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutF).writeBytes(buf, offset + off1.toInt(), value.f)
                }
            }

        public fun <I, F> new(iter: I, f: F): Inspect<I, F> = Inspect(iter = iter, f = f)

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY
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
