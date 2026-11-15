// port-lint: source iter/sources/successors.rs
package io.github.kotlinmania.rustlang.core.iter.sources.successors

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
 * Creates an iterator which, starting from an initial item,
 * computes each successive item from the preceding one.
 *
 * This iterator stores an optional item (`Option<T>`) and a successor closure (`implementation FnMut(T) -> Option<T>`).
 * Its `next` method returns the stored optional item and
 * if it is `Some(val)` calls the stored closure on `val` to compute and store its successor.
 * The iterator will apply the closure successively to the stored option's value until the option is `None`.
 * This also means that once the stored option is `None` it will remain `None`,
 * as the closure will not be called again, so the created iterator is a [FusedIterator].
 * The iterator's items will be the initial item and all of its successors as calculated by the successor closure.
 *
 * ```kotlin
 * import std.iter.successors
 *
 * val powersOf10 = successors(1u.toUShort(), { n -> n.checkedMul(10) })
 * check(powersOf10.collect<Vec>() == listOf(1, 10, 100, 1000, 10000))
 * ```
 */
public fun <T> successors(first: T?, succ: (T) -> T?): Successors<T, (T) -> T?> {
    // If this function returned `implementation Iterator<Item = T>`
    // it could be based on `fromFn` and not need a dedicated type.
    // However having a named `Successors<T, F>` type allows it to be `Clone` when `T` and `F` are.
    return Successors(next = first, succ = succ)
}

/**
 * An iterator which, starting from an initial item,
 * computes each successive item from the preceding one.
 *
 * This `class` is created by the [iter.successors] function.
 * See its documentation for more.
 *
 * [iter.successors]: successors
 */
public open class Successors<T, F>(
    public var next: T?,
    public var succ: F,
) : Iterable<T> {
    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Successors.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): Successors<T, F> = Successors(next, succ)

    public open fun next(): T? {
        val item = (this.next.also { this.next = null } ?: return null)
        this.next = (this.succ)(item)
        return item
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.next != null) {
            Pair(1, null)
        } else {
            Pair(0, 0)
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Successors").field("next", this.next).finish()
}
