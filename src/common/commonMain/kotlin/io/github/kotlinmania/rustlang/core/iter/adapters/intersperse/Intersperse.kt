// port-lint: source iter/adapters/intersperse.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.intersperse

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen

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
 * An iterator adapter that places a separator between all elements.
 *
 * This `class` is created by [Iterator.intersperse]. See its documentation
 * for more information.
 */
public open class Intersperse<I, Item>(
    public var started: Boolean,
    public var separator: Item,
    public var nextItem: Item?,
    public var iter: I,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Intersperse.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Intersperse<I, Item> = Intersperse(started, separator, nextItem, iter)

    public open fun next(): Item? =
        if (this.started) {
            when (val matchVal = this.nextItem.also { this.nextItem = null }) {
                null -> {
                    val nextItem = this.iter.next()
                    if (nextItem != null) {
                        this.nextItem = nextItem
                        this.separator.clone()
                    } else {
                        null
                    }
                }
                else -> {
                    val v = matchVal
                    v
                }
            }
        } else {
            val item = this.iter.next()
            this.started = item != null
            item
        }

    public open fun sizeHint(): Pair<Int, Int?> = intersperseSizeHint(this.iter, this.started, this.nextItem != null)

    public open fun <B> fold(init: B, f: (B, Item) -> B): B {
        val separator = this.separator
        return intersperseFold(this.iter, init, f, { separator.clone() }, this.started, this.nextItem)
    }

    public companion object {
        public fun <I> new(iter: I, separator: Item): Intersperse<I, Item> = Intersperse(started = false, separator = separator, nextItem = null, iter = iter)
    }
}

/**
 * An iterator adapter that places a separator between all elements.
 *
 * This `class` is created by [Iterator.intersperseWith]. See its
 * documentation for more information.
 */
public open class IntersperseWith<I, G, Item>(
    public var started: Boolean,
    public var separator: G,
    public var nextItem: Item?,
    public var iter: I,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "IntersperseWith").field("started", this.started).field("separator", this.separator).field("iter", this.iter).field("next_item", this.nextItem).finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): IntersperseWith<I, G, Item> = IntersperseWith(started = this.started, separator = this.separator.clone(), iter = this.iter.clone(), nextItem = this.nextItem)

    public companion object {
        public fun <I> new(iter: I, separator: () -> Item): IntersperseWith<I, G, Item> = IntersperseWith(started = false, separator = separator, nextItem = null, iter = iter)
    }
}

public fun <I> IntersperseWith<I, G, Item>.next(): Item? =
    if (this.started) {
        when (val matchVal = this.nextItem.also { this.nextItem = null }) {
            null -> {
                val nextItem = this.iter.next()
                if (nextItem != null) {
                    this.nextItem = nextItem
                    (this.separator)()
                } else {
                    null
                }
            }
            else -> {
                val v = matchVal
                v
            }
        }
    } else {
        val item = this.iter.next()
        this.started = item != null
        item
    }

public fun <I> IntersperseWith<I, G, Item>.sizeHint(): Pair<Int, Int?> = intersperseSizeHint(this.iter, this.started, this.nextItem != null)

public fun <I, B> IntersperseWith<I, G, Item>.fold(init: B, f: (B, Item) -> B): B = intersperseFold(this.iter, init, f, this.separator, this.started, this.nextItem)

public fun <I> intersperseSizeHint(iter: I, started: Boolean, nextIsSome: Boolean): Pair<Int, Int?> {
    val (lo, hi) = iter.sizeHint()
    return Pair(lo.saturatingSub((if (!started) 1 else 0)).saturatingAdd(nextIsSome.toInt()).saturatingAdd(lo), hi.andThen({ hi1 ->
        hi1.saturatingSub((if (!started) 1 else 0)).saturatingAdd(nextIsSome.toInt()).checkedAdd(hi1)
    }))
}

public fun <I, B> intersperseFold(iter: I, init: B, f: (B, I.Item) -> B, separator: () -> I.Item, started: Boolean, nextItem: Item?): B {
    var accum = init
    val first =
        if (started) {
            nextItem.also { nextItem = null }
        } else {
            val n = iter.next()
            // skip invoking fold() for empty iterators
            if (n == null) {
                return accum
            }
            n
        }
    if (first != null) {
        val x = first
        accum = f(accum, x)
    }
    return iter.fold(accum, { accum1, x1 ->
        accum1 = f(accum1, separator())
        accum1 = f(accum1, x1)
        accum1
    })
}
