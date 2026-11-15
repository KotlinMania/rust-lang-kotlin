// port-lint: source ops/function.rs
package io.github.kotlinmania.rustlang.core.ops.function

import io.github.kotlinmania.rustlang.marker.Tuple
import io.github.kotlinmania.rustlang.ptr.nonnull.NonNull

// so that regex can rely that `String: !FnMut`

/**
 * The version of the call operator that takes an immutable receiver.
 *
 * Instances of `Fn` can be called repeatedly without mutating state.
 *
 * *This trait (`Fn`) is not to be confused with [function pointers]
 * (`fun`).*
 *
 * `Fn` is implemented automatically by closures which only take immutable
 * references to captured variables or don't capture anything at all, as well
 * as (safe) [function pointers] (with some caveats, see their documentation
 * for more details). Additionally, for any type `F` that implements `Fn`, `F`
 * implements `Fn`, too.
 *
 * Since both [FnMut] and [FnOnce] are supertraits of `Fn`, any
 * instance of `Fn` can be used as a parameter where a [FnMut] or [FnOnce]
 * is expected.
 *
 * Use `Fn` as a bound when you want to accept a parameter of function-like
 * type and need to call it repeatedly and without mutating state (e.g., when
 * calling it concurrently). If you do not need such strict requirements, use
 * [FnMut] or [FnOnce] as bounds.
 *
 * See the [chapter on closures in *The Rust Programming Language*][book] for
 * some more information on this topic.
 *
 * Also of note is the special syntax for `Fn` traits (e.g.
 * `Fn(Int, Boolean) -> Int`). Those interested in the technical details of
 * this can refer to [the relevant section in the *Rustonomicon*][nomicon].
 *
 * [book]: ../../book/ch13-01-closures.html
 * [function pointers]: fn
 * [nomicon]: ../../nomicon/hrtb.html
 *
 * # Examples
 *
 * ## Calling a closure
 *
 * ```kotlin
 * val square = { x -> x * x }
 * check(square(5) == 25)
 * ```
 *
 * ## Using a `Fn` parameter
 *
 * ```kotlin
 * fun callWithOne(func: (Int) -> Int): Int {
 *     return func(1)
 * }
 * val double = { x -> x * 2 }
 * check(callWithOne(double) == 2)
 * ```
 */
public interface Fn<Args : Tuple> : FnMut<Args> {
    /**
     * Performs the call operation.
     */
    public fun call(args: Args): Output
}

// so that regex can rely that `String: !FnMut`

/**
 * The version of the call operator that takes a mutable receiver.
 *
 * Instances of `FnMut` can be called repeatedly and may mutate state.
 *
 * `FnMut` is implemented automatically by closures which take mutable
 * references to captured variables, as well as all types that implement
 * [Fn], e.g., (safe) [function pointers] (since `FnMut` is a supertrait of
 * [Fn]). Additionally, for any type `F` that implements `FnMut`, `F`
 * implements `FnMut`, too.
 *
 * Since [FnOnce] is a supertrait of `FnMut`, any instance of `FnMut` can be
 * used where a [FnOnce] is expected, and since [Fn] is a subtrait of
 * `FnMut`, any instance of [Fn] can be used where `FnMut` is expected.
 *
 * Use `FnMut` as a bound when you want to accept a parameter of function-like
 * type and need to call it repeatedly, while allowing it to mutate state.
 * If you don't want the parameter to mutate state, use [Fn] as a
 * bound; if you don't need to call it repeatedly, use [FnOnce].
 *
 * See the [chapter on closures in *The Rust Programming Language*][book] for
 * some more information on this topic.
 *
 * Also of note is the special syntax for `Fn` traits (e.g.
 * `Fn(Int, Boolean) -> Int`). Those interested in the technical details of
 * this can refer to [the relevant section in the *Rustonomicon*][nomicon].
 *
 * [book]: ../../book/ch13-01-closures.html
 * [function pointers]: fn
 * [nomicon]: ../../nomicon/hrtb.html
 *
 * # Examples
 *
 * ## Calling a mutably capturing closure
 *
 * ```kotlin
 * var x = 5
 * run {
 *     var squareX = { x *= x }
 *     squareX()
 * }
 * check(x == 25)
 * ```
 *
 * ## Using a `FnMut` parameter
 *
 * ```kotlin
 * fun doTwice(func: () -> Unit) {
 *     func()
 *     func()
 * }
 * var x: Int = 1
 * run {
 *     val addTwoToX = { x += 2 }
 *     doTwice(addTwoToX)
 * }
 * check(x == 5)
 * ```
 */
public interface FnMut<Args : Tuple> : FnOnce<Args, Any?> {
    /**
     * Performs the call operation.
     */
    public fun callMut(args: Args): Output
}

// so that regex can rely that `String: !FnMut`

/**
 * The version of the call operator that takes a by-value receiver.
 *
 * Instances of `FnOnce` can be called, but might not be callable multiple
 * times. Because of this, if the only thing known about a type is that it
 * implements `FnOnce`, it can only be called once.
 *
 * `FnOnce` is implemented automatically by closures that might consume captured
 * variables, as well as all types that implement [FnMut], e.g., (safe)
 * [function pointers] (since `FnOnce` is a supertrait of [FnMut]).
 *
 * Since both [Fn] and [FnMut] are subtraits of `FnOnce`, any instance of
 * [Fn] or [FnMut] can be used where a `FnOnce` is expected.
 *
 * Use `FnOnce` as a bound when you want to accept a parameter of function-like
 * type and only need to call it once. If you need to call the parameter
 * repeatedly, use [FnMut] as a bound; if you also need it to not mutate
 * state, use [Fn].
 *
 * See the [chapter on closures in *The Rust Programming Language*][book] for
 * some more information on this topic.
 *
 * Also of note is the special syntax for `Fn` traits (e.g.
 * `Fn(Int, Boolean) -> Int`). Those interested in the technical details of
 * this can refer to [the relevant section in the *Rustonomicon*][nomicon].
 *
 * [book]: ../../book/ch13-01-closures.html
 * [function pointers]: fn
 * [nomicon]: ../../nomicon/hrtb.html
 *
 * # Examples
 *
 * ## Using a `FnOnce` parameter
 *
 * ```kotlin
 * fun consumeWithRelish(func: () -> String) {
 *     // `func` consumes its captured variables, so it cannot be run more
 *     // than once.
 *     println("Consumed: ${func()}")
 *     println("Delicious!")
 *     // Attempting to invoke `func()` again will throw a `use of moved
 *     // value`error for`func`.
 * }
 * val x = "x"
 * val consumeAndReturnX = { x }
 * consumeWithRelish(consumeAndReturnX)
 * // `consumeAndReturnX` can no longer be invoked at this point
 * ```
 */
public interface FnOnce<Args : Tuple, out Output> {
    /**
     * Performs the call operation.
     */
    public fun callOnce(args: Args): Output
}

public fun <A : Tuple, F> F.callMut(args: A): Output where F : Fn<A> = this.call(args)

public fun <A : Tuple, F> F.callOnce(args: A): Output where F : Fn<A> = this.call(args)

public fun <A : Tuple, F> F.callOnceA(args: A): Output where F : FnMut<A> = this.callMut(args)

/**
 * A common trait implemented by all function pointers and function definition types
 */
public interface FnAddr<T> {
    /**
     * Returns the address of the function pointer.
     */
    public fun addr(): Int = this.asPtr().addr().get()

    /**
     * Returns the function pointer as a [NonNull.Code].
     */
    public fun asPtr(): NonNull
}

/**
 * A common trait implemented by all function pointers.
 */
public interface FnPtr<T> : FnAddr<T> {
    /**
     * Constructs a function pointer from a `NonNull` pointer.
     *
     * # Safety
     *
     * The function pointer must have been obtained
     * from an [FnAddr.asPtr] call from a function
     * pointer type that is ABI compatible.
     */
    public fun fromPtr(ptr: NonNull): T
}
