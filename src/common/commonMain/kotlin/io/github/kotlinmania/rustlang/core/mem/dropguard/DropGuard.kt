// port-lint: source mem/drop_guard.rs
package io.github.kotlinmania.rustlang.core.mem.dropguard

import io.github.kotlinmania.rustlang.core.io.util.Take
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Wrap a value and run a closure when dropped.
 *
 * This is useful for quickly creating destructors inline.
 *
 * # Examples
 *
 * ```kotlin
 * import std.mem.DropGuard
 *
 * run {
 *     // Create a new guard around a string that will
 *     // print its value when dropped.
 *     val s = "Chashu likes tuna"
 *     var s1 = DropGuard.new(s, { s1 -> println("$s") })
 *     // Modify the string contained in the guard.
 *     s1.pushStr("!!!")
 *     // The guard will be dropped here, printing:
 *     // "Chashu likes tuna!!!"
 * }
 * ```
 */
public open class DropGuard<T, F, Output>(
    public var inner: T,
    public var f: F,
) {
    public open fun deref(): T = this.inner

    public open fun derefMut(): T = this.inner

    public open fun drop() {
        // SAFETY: `DropGuard` is in the process of being dropped.
        val inner = ManuallyDrop.Take(this.inner)
        // SAFETY: `DropGuard` is in the process of being dropped.
        val f = ManuallyDrop.Take(this.f)
        f(inner)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this).toString()); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        /**
         * Create a new instance of `DropGuard`.
         *
         * # Example
         *
         * ```kotlin
         * import std.mem.DropGuard
         *
         * val value = "Chashu likes tuna"
         * val guard = DropGuard.new(value, { s -> println("$s") })
         * ```
         */
        public fun <T> new(inner: T, f: (T) -> Unit): DropGuard<T, F, Output> = DropGuard(inner = inner, f = f)

        /**
         * Consumes the `DropGuard`, returning the wrapped value.
         *
         * This will not execute the closure. It is typically preferred to call
         * this function instead of `mem.forget` because it will return the stored
         * value and drop variables captured by the closure instead of leaking their
         * owned resources.
         *
         * # Example
         *
         * ```kotlin
         * import std.mem.DropGuard
         *
         * val value = "Nori likes chicken"
         * val guard = DropGuard.new(value, { s -> println("$s") })
         * check(DropGuard.dismiss(guard) == "Nori likes chicken")
         * ```
         */
        public fun <T> dismiss(guard: DropGuard<T, F, Output>): T {
            // First we ensure that dropping the guard will not trigger
            // its destructor
            val guard1 = guard
            // Next we manually read the stored value from the guard.
            //
            // SAFETY: this is safe because we've taken ownership of the guard.
            val value = ManuallyDrop.Take(guard1.inner)
            // Finally we drop the stored closure. We do this *after* having read
            // the value, so that even if the closure's `drop` function panics,
            // unwinding still tries to drop the value.
            //
            // SAFETY: this is safe because we've taken ownership of the guard.
            ManuallyDrop.Drop(guard1.f)
            return value
        }
    }
}
