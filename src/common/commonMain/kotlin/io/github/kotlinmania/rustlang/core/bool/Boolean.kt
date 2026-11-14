// port-lint: source bool.rs
package io.github.kotlinmania.rustlang.core.bool

import io.github.kotlinmania.rustlang.core.intrinsics.disjointBitor
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition
import io.github.kotlinmania.rustlang.core.ubchecks.checkLanguageUb

// impl bool {}

/**
 * Returns `Some(t)` if the `Boolean` is [`trueVal`](../std/keyword.true.html),
 * or `None` otherwise.
 *
 * Arguments passed to `thenSome` are eagerly evaluated; if you are
 * passing the result of a function call, it is recommended to use
 * [then], which is lazily evaluated.
 *
 * [then]: bool.then
 *
 * # Examples
 *
 * ```kotlin
 * check(false.thenSome(0) == null)
 * check(true.thenSome(0) == 0)
 * ```
 *
 * ```kotlin
 * var a = 0
 * var functionWithSideEffects = {
 *     a += 1
 * }
 * true.thenSome(functionWithSideEffects())
 * false.thenSome(functionWithSideEffects())
 * // `a` is incremented twice because the value passed to `thenSome` is
 * // evaluated eagerly.
 * check(a == 2)
 * ```
 */
public fun <T> Boolean.thenSome(t: T): T? =
    if (this) {
        t
    } else {
        null
    }

/**
 * Returns `Some(f())` if the `Boolean` is [`trueVal`](../std/keyword.true.html),
 * or `None` otherwise.
 *
 * # Examples
 *
 * ```kotlin
 * check(false.then({ 0 }) == null)
 * check(true.then({ 0 }) == 0)
 * ```
 *
 * ```kotlin
 * var a = 0
 * true.then({
 *     a += 1
 * })
 * false.then({
 *     a += 1
 * })
 * // `a` is incremented once because the closure is evaluated lazily by
 * // `then`.
 * check(a == 1)
 * ```
 */
public fun <T> Boolean.then(f: () -> T): T? =
    if (this) {
        f()
    } else {
        null
    }

/**
 * Returns `Ok(())` if the `Boolean` is [`trueVal`](../std/keyword.true.html),
 * or `Err(err)` otherwise.
 *
 * Arguments passed to `okOr` are eagerly evaluated; if you are
 * passing the result of a function call, it is recommended to use
 * [okOrElse], which is lazily evaluated.
 *
 * [okOrElse]: bool.okOrElse
 *
 * # Examples
 *
 * ```kotlin
 * check(false.okOr(0) == Result.failure(0))
 * check(true.okOr(0) == Unit)
 * ```
 *
 * ```kotlin
 * var a = 0
 * var functionWithSideEffects = {
 *     a += 1
 * }
 * check(true.okOr(functionWithSideEffects()).isOk())
 * check(false.okOr(functionWithSideEffects()).isErr())
 * // `a` is incremented twice because the value passed to `okOr` is
 * // evaluated eagerly.
 * check(a == 2)
 * ```
 */
public fun <E> Boolean.okOr(err: E): Result<Unit, E> =
    if (this) {
        Result.Ok(Unit)
    } else {
        Result.Err(err)
    }

/**
 * Returns `Ok(())` if the `Boolean` is [`trueVal`](../std/keyword.true.html),
 * or `Err(f())` otherwise.
 *
 * # Examples
 *
 * ```kotlin
 * check(false.okOrElse({ 0 }) == Result.failure(0))
 * check(true.okOrElse({ 0 }) == Unit)
 * ```
 *
 * ```kotlin
 * var a = 0
 * check(true.okOrElse({
 *     a += 1
 * }).isOk())
 * check(false.okOrElse({
 *     a += 1
 * }).isErr())
 * // `a` is incremented once because the closure is evaluated lazily by
 * // `okOrElse`.
 * check(a == 1)
 * ```
 */
public fun <E> Boolean.okOrElse(f: () -> E): Result<Unit, E> =
    if (this) {
        Result.Ok(Unit)
    } else {
        Result.Err(f())
    }

/**
 * Toggles `self` in-place.
 *
 * - If `self` is [trueVal], sets `self` to [falseVal].
 * - If `self` is [falseVal], sets `self` to [trueVal].
 *
 * Equivalent to `value = !value`.
 *
 * # Examples
 *
 * ```kotlin
 * var boolean = false
 * boolean.toggle()
 * check(boolean == true)
 * boolean.toggle()
 * check(boolean == false)
 * ```
 *
 * [trueVal]: ../std/keyword.true.html
 * [falseVal]: ../std/keyword.false.html
 */
public fun Boolean.toggle() {
    this = !this
}

/**
 * Disjoint, bitwise or. Computes `self | rhs`, assuming at most one, true value.
 *
 * Practically, this requires that `self | rhs` and `self ^ rhs` both yield the
 * same result, allowing for any of the two to be emitted in codegen -- depending
 * on whichever is cheapest.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(disjointBitor)
 * public fun doctestEntry() {
 *     check(false.uncheckedDisjointBitor(true) == true)
 *     check(false.uncheckedDisjointBitor(false) == false)
 * }
 * ```
 *
 * # Safety
 *
 * This results in undefined behaviour if both `self` and `rhs` are true.
 */
public fun Boolean.uncheckedDisjointBitor(rhs: Boolean): Boolean {
    assertUnsafePrecondition(checkLanguageUb, "bool::unchecked_disjoint_bitor cannot bitor two truths", lhs, this, rhs, rhs, lhs, rhs)
    // SAFETY: Same precondition.
    return disjointBitor(this, rhs)
}
