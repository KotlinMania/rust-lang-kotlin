// port-lint: source future/ready.rs
package io.github.kotlinmania.rustlang.core.future.ready

import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

/**
 * A future that is immediately ready with a value.
 *
 * This `class` is created by [ready]. See its
 * documentation for more.
 */
public open class Ready<T>(
    public var value: T?,
) : Future<Any?, T> {
    public var field0: T?
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Ready<T> = Ready(value)

    public override fun poll(cx: Context): Poll<T> = Poll.Ready((this.value.also { this.value = null } ?: error("`Ready` should not be polled after completion")))

    /**
     * Consumes the `Ready`, returning the wrapped value.
     *
     * # Panics
     *
     * Will panic if this [Ready] was already polled to completion.
     *
     * # Examples
     *
     * ```kotlin
     * import std.future
     *
     * val a = future.ready(1)
     * check(a.intoInner() == 1)
     * ```
     */
    public open fun intoInner(): T = (this.value ?: error("`into_inner()` should not be called on `Ready` after completion"))
}

/**
 * Creates a future that is immediately ready with a value.
 *
 * Futures created through this function are functionally similar to those
 * created through `async {}`. The main difference is that futures created
 * through this function are named and implement `Unpin`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.future
 *
 * val a = future.ready(1)
 * check(a == 1)
 * ```
 */
public fun <T> ready(t: T): Ready<T> = Ready(t)
