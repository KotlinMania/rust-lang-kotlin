// port-lint: source future/pending.rs
package io.github.kotlinmania.rustlang.core.future.pending

import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

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
 * Creates a future which never resolves, representing a computation that never
 * finishes.
 *
 * This `class` is created by [pending]. See its
 * documentation for more.
 */
public open class Pending<T>(
    public var data: Unit,
) : Future<T> {
    public override fun poll(arg0: Context): Poll<T> = Poll.Pending

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Pending").finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): Pending<T> = pending()
}

/**
 * Creates a future which never resolves, representing a computation that never
 * finishes.
 *
 * # Examples
 *
 * ```kotlin
 * import std.future
 *
 * val future = future.pending()
 * val unused = future
 * error("unreachable")
 * ```
 */
public fun <T> pending(): Pending<T> = Pending(data = Unit)
