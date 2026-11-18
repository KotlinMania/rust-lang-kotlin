// port-lint: source vec/peek_mut.rs
package io.github.kotlinmania.rustlang.alloc.vec.peekmut

import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Structure wrapping a mutable reference to the last item in a
 * `List`.
 *
 * This `class` is created by the [peekMut] method on [Vec]. See
 * its documentation for more.
 *
 * [peekMut]: Vec.peekMut
 */
public open class PeekMut<T, A : Allocator>(
    public var vec: MutableList<T>,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "PeekMut").field(this.deref()).finish(); Result.Ok<Unit, Error>(Unit) }

    public open fun deref(): T {
        val idx = this.vec.size - 1
        // SAFETY: PeekMut is only constructed if the vec is non-empty
        return this.vec.getUnchecked(idx)
    }

    public open fun derefMut(): T {
        val idx = this.vec.size - 1
        // SAFETY: PeekMut is only constructed if the vec is non-empty
        return this.vec.getUncheckedMut(idx)
    }

    public companion object {
        public fun <T, A : Allocator> new(vec: MutableList<T>): PeekMut<T, A>? =
            if (vec.isEmpty()) {
                null
            } else {
                PeekMut(vec = vec)
            }

        /**
         * Removes the peeked value from the vector and returns it.
         */
        public fun <T, A : Allocator> pop(thisRef: PeekMut<T, A>): T {
            // SAFETY: PeekMut is only constructed if the vec is non-empty
            return thisRef.vec.pop().unwrapUnchecked()
        }
    }
}
