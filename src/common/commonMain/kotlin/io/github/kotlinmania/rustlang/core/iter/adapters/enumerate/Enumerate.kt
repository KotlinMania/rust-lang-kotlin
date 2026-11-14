// port-lint: source iter/adapters/enumerate.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.enumerate

import io.github.kotlinmania.rustlang.core.iter.InPlaceIterable
import io.github.kotlinmania.rustlang.core.iter.TrustedFused
import io.github.kotlinmania.rustlang.core.iter.TrustedLen
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator that yields the current count and the element during iteration.
 *
 * This `class` is created by the [enumerate] method on [Iterator]. See its
 * documentation for more.
 *
 * [enumerate]: Iterator.enumerate
 * [Iterator]: trait.Iterator.html
 */
public open class Enumerate<I>(
    public var iter: I,
    public var count: Int,
) : TrustedRandomAccess, TrustedRandomAccessNoCoerce, TrustedFused, TrustedLen<Enumerate<I>>, SourceIter<Any?, Source>, InPlaceIterable, Iterable<Pair<Int, Item>> {
    override fun iterator(): kotlin.collections.Iterator<Pair<Int, Item>> = object : AbstractIterator<Pair<Int, Item>>() {
        override fun computeNext() {
            when (val v = this@Enumerate.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Enumerate<I> = Enumerate(iter, count)

    /**
     * Retrieve the current position of the iterator.
     *
     * If the iterator has not advanced, the position returned will be 0.
     *
     * The position may also exceed the bounds of the iterator to allow for calculating
     * the displacement of the iterator from following calls to [Iterator.next].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(nextIndex)
     * public fun doctestEntry() {
     *     val arr = listOf('a', 'b')
     *     var iter = arr.iter().mapIndexed { index, item -> Pair(index, item) }
     *     check(iter.nextIndex() == 0)
     *     check(iter.next() == Pair(0, 'a'))
     *     check(iter.nextIndex() == 1)
     *     check(iter.nextIndex() == 1)
     *     check(iter.next() == Pair(1, 'b'))
     *     check(iter.nextIndex() == 2)
     *     check(iter.next() == null)
     *     check(iter.nextIndex() == 2)
     * }
     * ```
     */
    public open fun nextIndex(): Int = this.count

    /**
     * # Overflow Behavior
     *
     * The method does no guarding against overflows, so enumerating more than
     * `Int.MAX` elements either produces the wrong result or panics. If
     * overflow checks are enabled, a panic is guaranteed.
     *
     * # Panics
     *
     * Might panic if the index of the element overflows a `Int`.
     */
    public override fun next(): Pair<Int, Item>? {
        val a = (this.iter.next() ?: return null)
        val i = this.count
        this.count += 1
        return Pair(i, a)
    }

    public override fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public override fun nth(n: Int): Pair<Int, Item>? {
        val a = (this.iter.nth(n) ?: return null)
        val i = this.count + n
        this.count = i + 1
        return Pair(i, a)
    }

    public override fun count(): Int = this.iter.count()

    public override fun <Acc, R> tryFold(init: Acc, fold: (Acc, Pair<Int, Item>) -> R): R where R : Try<R, Acc, Any?> {
        fun <T, Acc, R> enumerate(count: RustMutableReference<Int>, fold1: (Acc, Pair<Int, T>) -> R): (Acc, T) -> R {
            val count1 = count
            return { acc, item ->
                val acc1 = fold1(acc, Pair((count1).read(), item))
                (count1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
                acc1
            }
        }
        return this.iter.tryFold(init, Enumerate(this.count, fold))
    }

    public override fun <Acc> fold(init: Acc, fold: (Acc, Pair<Int, Item>) -> Acc): Acc {
        fun <T, Acc> enumerate(count: Int, fold1: (Acc, Pair<Int, T>) -> Acc): (Acc, T) -> Acc {
            var count1 = count
            return { acc, item ->
                val acc1 = fold1(acc, Pair(count1, item))
                count1 += 1
                acc1
            }
        }
        return this.iter.fold(init, Enumerate(this.count, fold))
    }

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int>> {
        val remaining = this.iter.advanceBy(n)
        val advanced =
            when (remaining) { is Result.Ok<*> -> {
                n
            }; is Result.Err<*> -> {
                val rem = remaining.error
                n - rem.get()
            } }
        this.count += advanced
        return remaining
    }

    public override fun iteratorGetUnchecked(idx: Int): Item {
        // SAFETY: the caller must uphold the contract for
        // `Iterator.__iterator_get_unchecked`.
        val value = tryGetUnchecked(this.iter, idx)
        return Pair(this.count + idx, value)
    }

    public open fun nextBack(): Pair<Int, Item>? {
        val a = (this.iter.nextBack() ?: return null)
        val len = this.iter.len()
        // Can safely add, `ExactSizeIterator` promises that the number of
        // elements fits into a `Int`.
        return Pair(this.count + len, a)
    }

    public open fun nthBack(n: Int): Pair<Int, Item>? {
        val a = (this.iter.nthBack(n) ?: return null)
        val len = this.iter.len()
        // Can safely add, `ExactSizeIterator` promises that the number of
        // elements fits into a `Int`.
        return Pair(this.count + len, a)
    }

    public open fun <Acc, R> tryRfold(init: Acc, fold: (Acc, Pair<Int, Item>) -> R): R where R : Try<R, Acc, Any?> {
        // Can safely add and subtract the count, as `ExactSizeIterator` promises
        // that the number of elements fits into a `Int`.
        fun <T, Acc, R> enumerate(count: Int, fold1: (Acc, Pair<Int, T>) -> R): (Acc, T) -> R {
            var count1 = count
            return { acc, item ->
                count1 -= 1
                fold1(acc, Pair(count1, item))
            }
        }
        val count = this.count + this.iter.len()
        return this.iter.tryRfold(init, Enumerate(count, fold))
    }

    public open fun <Acc> rfold(init: Acc, fold: (Acc, Pair<Int, Item>) -> Acc): Acc {
        // Can safely add and subtract the count, as `ExactSizeIterator` promises
        // that the number of elements fits into a `Int`.
        fun <T, Acc> enumerate(count: Int, fold1: (Acc, Pair<Int, T>) -> Acc): (Acc, T) -> Acc {
            var count1 = count
            return { acc, item ->
                count1 -= 1
                fold1(acc, Pair(count1, item))
            }
        }
        val count = this.count + this.iter.len()
        return this.iter.rfold(init, Enumerate(count, fold))
    }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> {
        // we do not need to update the count since that only tallies the number of items
        // consumed from the front. consuming items from the back can never reduce that.
        return this.iter.advanceBackBy(n)
    }

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public override fun asInner(): Source {
        // SAFETY: unchecked function forwarding to unchecked function with the same requirements
        return this.iter.asInner()
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: io.github.kotlinmania.rustlang.core.mem.TypeLayout<I>): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Enumerate<I>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Enumerate<I>> {
                override val alignBytes: Long = listOf(8L, typeLayoutI.alignBytes).max()
                private val off1: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes(typeLayoutI.sizeBytes, 8L)
                override val sizeBytes: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes((off1 + 8L), alignBytes)
                override fun readNative(addr: Long): Enumerate<I> =
                    Enumerate(
                        (typeLayoutI).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Enumerate<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + off1, value.count)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Enumerate<I> =
                    Enumerate(
                        (typeLayoutI).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Enumerate<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off1.toInt(), value.count)
                }
            }

        internal fun <I> new(iter: I): Enumerate<I> = Enumerate(iter = iter, count = 0)

        public val MAY_HAVE_SIDE_EFFECT: Boolean = I.MAY_HAVE_SIDE_EFFECT

        public val EXPAND_BY: NonZero<Int>? = I.EXPAND_BY

        public val MERGE_BY: NonZero<Int>? = I.MERGE_BY

        /**
         * Creates an `Enumerate` iterator from the default value of `I`
         * ```kotlin
         * val iter: Enumerate<ArrayDeque<UByte>> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <I> default(defaultIFn: () -> I): Enumerate<I> = Enumerate.new(defaultIFn())
    }
}
