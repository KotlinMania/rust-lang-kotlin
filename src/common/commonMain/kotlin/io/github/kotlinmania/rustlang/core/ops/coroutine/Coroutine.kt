// port-lint: source ops/coroutine.rs
package io.github.kotlinmania.rustlang.core.ops.coroutine

import io.github.kotlinmania.rustlang.pin.Pin

/**
 * The result of a coroutine resumption.
 *
 * This enum is returned from the `Coroutine.resume` method and indicates the
 * possible return values of a coroutine. Currently this corresponds to either
 * a suspension point (`Yielded`) or a termination point (`Complete`).
 */
public sealed class CoroutineState<Y, R> {
    /**
     * The coroutine suspended with a value.
     *
     * This state indicates that a coroutine has been suspended, and typically
     * corresponds to a `yield` statement. The value provided in this variant
     * corresponds to the expression passed to `yield` and allows coroutines to
     * provide a value each time they yield.
     */
    public data class Yielded<Y, R>(public var value: Y) : CoroutineState<Y, R>()
    /**
     * The coroutine completed with a return value.
     *
     * This state indicates that a coroutine has finished execution with the
     * provided value. Once a coroutine has returned `Complete` it is
     * considered a programmer error to call `resume` again.
     */
    public data class Complete<Y, R>(public var value: R) : CoroutineState<Y, R>()

    public fun rustEquals(other: CoroutineState<Y, R>): Boolean = this == other
}

/**
 * The trait implemented by builtin coroutine types.
 *
 * Coroutines are currently an
 * experimental language feature in Rust. Added in [RFC 2033] coroutines are
 * currently intended to primarily provide a building block for async/await
 * syntax but will likely extend to also providing an ergonomic definition for
 * iterators and other primitives.
 *
 * The syntax and semantics for coroutines is unstable and will require a
 * further RFC for stabilization. At this time, though, the syntax is
 * closure-like:
 *
 * ```kotlin
 * import std.ops.Coroutine
 * import std.ops.CoroutineState
 * import std.pin.Pin
 *
 * @feature(coroutines)
 * @feature(coroutineTrait)
 * @feature(stmtExprAttributes)
 * public fun doctestEntry() {
 *     fun main() {
 *         var coroutine = {
 *             yield(1)
 *             "foo"
 *         }
 *         run {
 *             val matchVal = Pin.new(coroutine).resume()
 *             when {
 *                 matchVal is CoroutineState.Yielded && matchVal.value == 1 -> {}
 *                 else -> error("unexpected return from resume")
 *             }
 *         }
 *         run {
 *             val matchVal1 = Pin.new(coroutine).resume()
 *             when {
 *                 matchVal1 is CoroutineState.Complete && matchVal1.value == "foo" -> {}
 *                 else -> error("unexpected return from resume")
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * More documentation of coroutines can be found in the [unstable book].
 *
 * [RFC 2033]: https://github.com/rust-lang/rfcs/pull/2033
 * [unstable book]: ../../unstable-book/language-features/coroutines.html
 */
public interface Coroutine<R, Yield, Return> {
    /**
     * Resumes the execution of this coroutine.
     *
     * This function will resume execution of the coroutine or start execution
     * if it hasn't already. This call will return back into the coroutine's
     * last suspension point, resuming execution from the latest `yield`. The
     * coroutine will continue executing until it either yields or returns, at
     * which point this function will return.
     *
     * # Return value
     *
     * The `CoroutineState` enum returned from this function indicates what
     * state the coroutine is in upon returning. If the `Yielded` variant is
     * returned then the coroutine has reached a suspension point and a value
     * has been yielded out. Coroutines in this state are available for
     * resumption at a later point.
     *
     * If `Complete` is returned then the coroutine has completely finished
     * with the value provided. It is invalid for the coroutine to be resumed
     * again.
     *
     * # Panics
     *
     * This function may panic if it is called after the `Complete` variant has
     * been returned previously. While coroutine literals in the language are
     * guaranteed to panic on resuming after `Complete`, this is not guaranteed
     * for all implementations of the `Coroutine` trait.
     */
    public fun resume(arg: R): CoroutineState<Yield, Return>
}

public fun <G : Coroutine<R, Any?, Any?>, R> Pin<G>.resume(arg: R, resumeGFn: (G, R) -> CoroutineState<Yield, Return>): CoroutineState<Yield, Return> = resumeGFn(this.asMut(), arg)
