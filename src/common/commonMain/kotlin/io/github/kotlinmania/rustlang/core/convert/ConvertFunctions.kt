package io.github.kotlinmania.rustlang.core.convert

import io.github.kotlinmania.rustlang.core.result.Result

/**
 * The identity function.
 *
 * Two things are important to note about this function:
 *
 * - It is not always equivalent to a closure like `| x | x`, since the
 *   closure may coerce `x` into a different type.
 *
 * - It moves the input `x` passed to the function.
 *
 * While it might seem strange to have a function that just returns back the
 * input, there are some interesting uses.
 *
 * # Examples
 *
 * Using `identity` to do nothing in a sequence of other, interesting,
 * functions:
 *
 * ```kotlin
 * fun manipulation(x: UInt): UInt {
 *     // Let's pretend that adding one is an interesting function.
 *     return x + 1u
 * }
 * val arr = listOf(::identity, manipulation)
 * ```
 *
 * Using `identity` as a "do nothing" base case in a conditional:
 *
 * ```kotlin
 * val doStuff = if (condition) {
 *     manipulation
 * } else {
 *     ::identity
 * }
 * // Do more interesting stuff...
 * val results = doStuff(42)
 * ```
 *
 * Using `identity` to keep the `Some` variants of an iterator of `Option<T>`:
 *
 * ```kotlin
 * val iter = listOf(1, null, 3).intoIter()
 * val filtered = iter.mapNotNull(::identity).collect<Vec>()
 * check(mutableListOf(1, 3) == filtered)
 * ```
 */
public fun <T> identity(x: T): T = x

// Note (#45742): replace the above impl for with the following more general one:
// // AsMut lifts over DerefMut
// impl<D: ?Sized + Deref<Target: AsMut<U>>, U: ?Sized> AsMut<U> for D {
// fn asMut(self) -> U {
// self.derefMut().asMut()
// }
// }
// From implies Into

/**
 * Calls `U.from(self)`.
 *
 * That is, this conversion is whatever the implementation of
 * <code>[From]&lt;T&gt; for U</code> chooses to do.
 */
public fun <T, U> T.into(fromUFn: (T) -> U): U where U : From<T, U> = fromUFn(this)

// From (and thus Into) is reflexive

/**
 * Returns the argument unchanged.
 */
public fun <T> fromT(t: T): T = t

// TryFrom implies TryInto
public fun <T, U> T.tryInto(tryFromUFn: (T) -> Result<U, Error>): Result<U, Error> where U : TryFrom<T, U, Any?> = tryFromUFn(this)

// Infallible conversions are semantically equivalent to fallible conversions
// with an uninhabited error type.
public fun <T, U> tryFromT(value: U, intoUFn: (U) -> T): Result<T, kotlin.Nothing> where U : Into<T> = Result.Ok(intoUFn(value))

// ///////////////////////////////////////////////////////////////////////////
// CONCRETE IMPLS
// ///////////////////////////////////////////////////////////////////////////
public fun <T> List<T>.asRef(): List<T> = this

public fun <T> List<T>.asMut(): MutableList<T> = this

public fun String.asRef(): String = this

public fun String.asMut(): String = this

public fun <T> kotlin.Nothing.asRef(): T = this

public fun <T> kotlin.Nothing.asMut(): T = this
