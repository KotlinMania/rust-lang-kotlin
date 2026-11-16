// port-lint: source iter/adapters/map.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.map

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.iter.traits.marker.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
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
 * This will print `('a', 1), ('b', 2), ('c', 3)`.
 *
 * Now consider this twist where we add a call to `rev`. This version will
 * print `('c', 1), ('b', 2), ('a', 3)`. Note that the letters are reversed,
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
) : TrustedFused, TrustedLen<Map<I, F>>, TrustedRandomAccess, TrustedRandomAccessNoCoerce, SourceIter<Source>, InPlaceIterable, Iterable<B> {
    override fun iterator(): Iterator<B> = object : AbstractIterator<B>() {
        override fun computeNext() {
            when (val v = this@Map.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Map<I, F> = Map(iter, f)

    public open fun intoInner(): I = this.iter

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Map").field("iter", this.iter).finish()

    public override fun next(): B? = this.iter.next()?.let(this.f)

    public override fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public override fun <Acc, R> tryFold(init: Acc, g: (Acc, B) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryFold(init, mapTryFold(this.f, g))

    public override fun <Acc> fold(init: Acc, g: (Acc, B) -> Acc): Acc = this.iter.fold(init, mapFold(this.f, g))

    public override fun iteratorGetUnchecked(idx: Int): B {
        // SAFETY: the caller must uphold the contract for
        // `Iterator.__iterator_get_unchecked`.
        return (this.f)(tryGetUnchecked(this.iter, idx))
    }

    public open fun nextBack(): B? = this.iter.nextBack()?.let(this.f)

    public open fun <Acc, R> tryRfold(init: Acc, g: (Acc, B) -> R): R where R : Try<R, Acc, Any?> = this.iter.tryRfold(init, mapTryFold(this.f, g))

    public open fun <Acc> rfold(init: Acc, g: (Acc, B) -> Acc): Acc = this.iter.rfold(init, mapFold(this.f, g))

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I, F> typeLayout(typeLayoutI: TypeLayout<I>, typeLayoutF: TypeLayout<F>): TypeLayout<Map<I, F>> =
            object : TypeLayout<Map<I, F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutI.alignBytes, typeLayoutF.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, typeLayoutF.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutF.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Map<I, F> =
                    Map(
                        (typeLayoutI).readNative(addr),
                        (typeLayoutF).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Map<I, F>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (typeLayoutF).writeNative(addr + off1, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Map<I, F> =
                    Map(
                        (typeLayoutI).readBytes(buf, offset),
                        (typeLayoutF).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Map<I, F>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (typeLayoutF).writeBytes(buf, offset + off1.toInt(), value.f)
                }
            }

        public fun <I, F> new(iter: I, f: F): Map<I, F> = Map(iter = iter, f = f)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = true

        public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = I.MERGE_BY
    }
}

public fun <T, B, Acc> mapFold(f: (T) -> B, g: (Acc, B) -> Acc): (Acc, T) -> Acc = { acc, elt -> g(acc, f(elt)) }

public fun <T, B, Acc, R> mapTryFold(f: (T) -> B, g: (Acc, B) -> R): (Acc, T) -> R = { acc, elt -> g(acc, f(elt)) }
