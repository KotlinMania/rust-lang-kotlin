// port-lint: source iter/sources/repeat_n.rs
package io.github.kotlinmania.rustlang.core.iter.sources.repeatn

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
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

/**
 * Creates a new iterator that repeats a single element a given number of times.
 *
 * The `repeatN()` function repeats a single value exactly `n` times.
 *
 * This is very similar to using [repeat] with [Iterator.take],
 * but `repeatN()` can return the original value, rather than always cloning.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.iter
 *
 * // four of the number four:
 * var fourFours = iter.repeatN(4, 4)
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * check(4 == fourFours.next())
 * // no more fours
 * check(null == fourFours.next())
 * ```
 *
 * For non-`Copy` types,
 *
 * ```kotlin
 * import std.iter
 *
 * val v: List<Int> = ArrayList(123)
 * var it = iter.repeatN(v, 5)
 * for (i in 0 until 4) {
 *     // It starts by cloning things
 *     val cloned = it.next()!!
 *     check(cloned.len() == 0)
 *     check(cloned.capacity() == 0)
 * }
 * // ... but the last item is the original one
 * val last = it.next()!!
 * check(last.len() == 0)
 * check(last.capacity() == 123)
 * // ... and now we're done
 * check(null == it.next())
 * ```
 */
public fun <T> repeatN(element: T, count: Int): RepeatN<T> = RepeatN(inner = RepeatNInner.new(element, count))

public open class RepeatNInner<T> internal constructor(
    countCtorArg: NonZero<Int, NonZeroUsizeInner>,
    public var element: T,
) {
    public var count: NonZero<Int, NonZeroUsizeInner> = countCtorArg
        internal set

    public open fun clone(): RepeatNInner<T> = RepeatNInner(count, element)

    public companion object {
        public operator fun <T> invoke(
            count: NonZero<Int, NonZeroUsizeInner>,
            element: T,
        ): RepeatNInner<T> = RepeatNInner<T>(
            countCtorArg = count,
            element = element,
        )

        public fun <T> new(element: T, count: Int): RepeatNInner<T>? {
            val count1 = (NonZero.new<NonZeroI32Inner, Int>(count) ?: return null)
            return RepeatNInner(element = element, count = count1)
        }
    }
}

/**
 * An iterator that repeats an element an exact number of times.
 *
 * This `class` is created by the [repeatN] function.
 * See its documentation for more.
 */
public open class RepeatN<A>(
    public var inner: RepeatNInner<A>?,
) : TrustedLen<Any>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@RepeatN.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): RepeatN<A> = RepeatN(inner)

    /**
     * If we haven't already dropped the element, return it in an option.
     */
    public open fun takeElement(): A? = (this.inner.also { this.inner = null })?.let({ inner -> inner.element })

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val (count, element) =
            when (val matchVal = this.inner) {
                null -> Pair(0, null)
                else -> {
                    val inner = matchVal
                    Pair(inner.count.get(), inner.element)
                }
            }
        return run { DebugStruct(f, "RepeatN").field("count", count).field("element", element).finish(); Result.Ok<Unit, Unit>(Unit) }
    }

    public open fun next(): A? {
        val inner = (this.inner ?: return null)
        val count = inner.count.get()
        when (val matchVal = NonZero.new(count - 1)) {
            null -> {}
            else -> {
                val decremented = matchVal
                // Order of these is important for optimization
                val tmp = inner.element.clone()
                inner.count = decremented
                return tmp
            }
        }
        return this.takeElement()
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.len()
        return Pair(len, len)
    }

    public open fun advanceBy(skip: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val inner = this.inner ?: return ((NonZero.new<NonZeroUsizeInner, Int>(skip))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
        val len = inner!!.count.get()
        when (val matchVal = len.checkedSub(skip)?.let(NonZero::new)) {
            null -> {}
            else -> {
                val newLen = matchVal
                inner.count = newLen
                return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
            }
        }
        this.inner = null
        return ((NonZero.new<NonZeroUsizeInner, Int>(skip - len))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun last(): A? = this.takeElement()

    public open fun count(): Int = this.len()

    public open fun len(): Int = (this.inner?.let({ inner -> inner.count.get() }) ?: 0)

    public open fun nextBack(): A? = this.next()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.advanceBy(n)

    public open fun nthBack(n: Int): A? = this.nth(n)

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, A) -> R): R where R : Try<R, B, Residual> = this.tryFold(init, f)

    public open fun <B> rfold(init: B, f: (B, A) -> B): B = this.fold(init, f)

    public companion object {
        /**
         * Creates an empty iterator, like [`repeatN`][repeatN]
         * but without needing any such value at hand.
         */
        public fun <A> default(): RepeatN<A> = RepeatN(inner = null)
    }
}
