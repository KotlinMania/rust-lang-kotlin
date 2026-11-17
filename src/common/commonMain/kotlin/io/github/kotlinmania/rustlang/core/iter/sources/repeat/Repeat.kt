// port-lint: source iter/sources/repeat.rs
package io.github.kotlinmania.rustlang.core.iter.sources.repeat

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Creates a new iterator that endlessly repeats a single element.
 *
 * The `repeat()` function repeats a single value over and over again.
 *
 * Infinite iterators like `repeat()` are often used with adapters like
 * [Iterator.take], in order to make them finite.
 *
 * If you know the number of repetitions in advance, consider using [repeatN]
 * instead, as it is more efficient and conveys the intent more clearly.
 *
 * Use [str.repeat] instead of this function if you just want to repeat
 * a char/string `n` times.
 *
 * If the element type of the iterator you need does not implement `Clone`,
 * or if you do not want to keep the repeated element in memory, you can
 * instead use the [repeatWith] function.
 *
 * [str.repeat]: ../../std/primitive.str.html#method.repeat
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.iter
 *
 * // the number four 4ever:
 * var fours = iter.repeat(4)
 * check(4 == fours.next())
 * check(4 == fours.next())
 * check(4 == fours.next())
 * check(4 == fours.next())
 * check(4 == fours.next())
 * // yup, still four
 * check(4 == fours.next())
 * ```
 *
 * Going finite with [Iterator.take]:
 *
 * ```kotlin
 * import std.iter
 *
 * // that last example was too many fours. Let's only have four fours.
 * var fourFours = List(4) { 4 }
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * // ... and now we're done
 * check(null == fourFours.next())
 * ```
 */
public fun <T> repeat(elt: T): Repeat<T> = Repeat(element = elt)

/**
 * An iterator that repeats an element endlessly.
 *
 * This `class` is created by the [repeat] function. See its documentation for more.
 */
public open class Repeat<A>(
    public var element: A,
) : TrustedLen, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@Repeat.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Repeat<A> = Repeat(element)

    public override fun next(): A? = this.element.clone()

    public override fun sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        // Advancing an infinite iterator of a single element is a no-op.
        return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
    }

    public override fun nth(n: Int): A? = this.element.clone()

    /**
     * Consumes the iterator and panics.
     *
     * # Panics
     *
     * This method always panics because `Repeat` is infinite.
     */
    public override fun last(): A? {
        error("iterator is infinite")
    }

    /**
     * Consumes the iterator and panics.
     *
     * # Panics
     *
     * This method always panics because `Repeat` is infinite.
     */
    public override fun count(): Int {
        error("iterator is infinite")
    }

    public open fun nextBack(): A? = this.element.clone()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        // Advancing an infinite iterator of a single element is a no-op.
        return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
    }

    public open fun nthBack(n: Int): A? = this.element.clone()

    public companion object {
        public fun <A> typeLayout(typeLayoutA: TypeLayout<A>): TypeLayout<Repeat<A>> =
            object : TypeLayout<Repeat<A>> {
                override val alignBytes: Long = listOf(1L, typeLayoutA.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutA.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Repeat<A> =
                    Repeat(
                        (typeLayoutA).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Repeat<A>) {
                    (typeLayoutA).writeNative(addr, value.element)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Repeat<A> =
                    Repeat(
                        (typeLayoutA).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Repeat<A>) {
                    (typeLayoutA).writeBytes(buf, offset, value.element)
                }
            }
    }
}
