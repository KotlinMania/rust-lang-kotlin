// port-lint: source future/into_future.rs
package io.github.kotlinmania.rustlang.core.future.intofuture

import io.github.kotlinmania.rustlang.core.future.future.Future

public typealias Output<F> = F.Output

/**
 * Conversion into a `Future`.
 *
 * By implementing `IntoFuture` for a type, you define how it will be
 * converted to a future.
 *
 * # `.await` desugaring
 *
 * The `.await` keyword desugars into a call to `IntoFuture.intoFuture`
 * first before polling the future to completion. `IntoFuture` is implemented
 * for all `T: Future` which means the `intoFuture` method will be available
 * on all futures.
 *
 * ```kotlin
 * import std.future.IntoFuture
 *
 * val v = "meow"
 * var fut = v.intoFuture()
 * check("meow" == fut)
 * ```
 *
 * # Async builders
 *
 * When implementing futures manually there will often be a choice between
 * implementing `Future` or `IntoFuture` for a type. Implementing `Future` is a
 * good choice in most cases. But implementing `IntoFuture` is most useful when
 * implementing "async builder" types, which allow their values to be modified
 * multiple times before being `.await`ed.
 *
 * ```kotlin
 * import std.future.Ready
 * import std.future.ready
 *
 * public typealias Output = UShort
 *
 * public typealias IntoFuture = Ready<Output>
 *
 * public fun doctestEntry() {
 *     / **
 *      * Eventually multiply two numbers
 *      * /
 *     open class Multiply(public var num: UShort, public var factor: UShort) : IntoFuture {
 *         / **
 *          * Constructs a new instance of `Multiply`.
 *          * /
 *         public open fun new(num: UShort, factor: UShort): Multiply {
 *             return Multiply(num = num, factor = factor)
 *         }
 *
 *         / **
 *          * Set the number to multiply by the factor.
 *          * /
 *         public open fun number(num: UShort): Multiply {
 *             this.num = num
 *             return this
 *         }
 *
 *         / **
 *          * Set the factor to multiply the number with.
 *          * /
 *         public open fun factor(factor: UShort): Multiply {
 *             this.factor = factor
 *             return this
 *         }
 *
 *         public open fun intoFuture(): Ready<UShort> {
 *             return ready(this.num * this.factor)
 *         }
 *     }
 *     // NOTE: Rust does not yet have an `async fun main` function, that functionality
 *     // currently only exists in the ecosystem.
 *     fun run() {
 *         val num = Multiply.new(0, 0).number(2).factor(2)
 *         // convert to future and .await
 *         check(num == 4)
 *     }
 * }
 * ```
 *
 * # Usage in trait bounds
 *
 * Using `IntoFuture` in trait bounds allows a function to be generic over both
 * `Future` and `IntoFuture`. This is convenient for users of the function, so
 * when they are using it they don't have to make an extra call to
 * `IntoFuture.intoFuture` to obtain an instance of `Future`:
 *
 * ```kotlin
 * import std.future.IntoFuture
 *
 * / **
 *  * Converts the output of a future to a string.
 *  * /
 * fun <Output, IntoFuture, Fut> futToString(fut: Fut): String where Fut : IntoFuture<Any?, Any?> {
 *     return "$fut"
 * }
 * ```
 */
public interface IntoFuture<Output, out IntoFuture> {
    /**
     * Creates a future from a value.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import std.future.IntoFuture
     *
     * val v = "meow"
     * var fut = v.intoFuture()
     * check("meow" == fut)
     * ```
     */
    public fun intoFuture(): IntoFuture
}

public fun <Output, F : Future<Any?>> F.intoFuture(): F = this
