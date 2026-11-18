// port-lint: source iter/sources/empty.rs
package io.github.kotlinmania.rustlang.core.iter.sources.empty

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
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

/**
 * Creates an iterator that yields nothing.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.iter
 *
 * // this could have been an iterator over i32, but alas, it's just not.
 * var nope = iter.empty<Int>()
 * check(null == nope.next())
 * ```
 */
public fun <T> empty(): Empty<T> = Empty(Unit)

/**
 * An iterator that yields nothing.
 *
 * This `class` is created by the [empty] function. See its documentation for more.
 */
public open class Empty<T>(
    public var value: Unit,
) : TrustedLen, Iterable<T> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Empty.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Empty").finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun next(): T? = null

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, 0)

    public open fun nextBack(): T? = null

    public open fun len(): Int = 0

    // not @derive because that adds a Clone bound on T,
    // which isn't necessary.
    public open fun clone(): Empty<T> = Empty(Unit)

    public companion object {
        // not @derive because that adds a Default bound on T,
        // which isn't necessary.
        public fun <T> default(): Empty<T> = Empty(Unit)
    }
}
