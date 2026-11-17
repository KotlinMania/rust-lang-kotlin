// port-lint: source task/poll.rs
package io.github.kotlinmania.rustlang.core.task.poll

import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Indicates whether a value is available or if the current task has been
 * scheduled to receive a wakeup instead.
 *
 * This is returned by [`Future.poll`](core.future.Future.poll).
 */
public sealed class Poll<T> {
    /**
     * Represents that a value is immediately ready.
     */
    public data class Ready<T>(public var value: T) : Poll<T>()
    /**
     * Represents that a value is not ready yet.
     *
     * When a function returns `Pending`, the function *must* also
     * ensure that the current task is scheduled to be awoken when
     * progress can be made.
     */
    public class Pending<T>() : Poll<T>()

    public fun rustEquals(other: Poll<T>): Boolean = this == other

    /**
     * Maps a `Poll<T>` to `Poll<U>` by applying a function to a contained value.
     *
     * # Examples
     *
     * Converts a <code>Poll<[String]></code> into a <code>Poll<[Int]></code>, consuming
     * the original:
     *
     * [String]: ../../std/string/struct.String.html "String"
     * ```kotlin
     * val pollSomeString = Poll.Ready("Hello, World!")
     * // `Poll.map` takes self *by value*, consuming `pollSomeString`
     * val pollSomeLen = pollSomeString.map({ s -> s.len() })
     * check(pollSomeLen == Poll.Ready(13))
     * ```
     */
    public open fun <U> map(f: (T) -> U): Poll<U> =
        when (this) {
            is Poll.Ready<*> -> {
                val (t) = this as Poll.Ready<T>
                Poll.Ready(f(t))
            }
            Poll.Pending -> Poll.Pending
        }

    /**
     * Returns `true` if the poll is a [Poll.Ready] value.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Poll<UInt> = Poll.Ready(2)
     * check(x.isReady() == true)
     * val x1: Poll<UInt> = Poll.Pending
     * check(x1.isReady() == false)
     * ```
     */
    public open fun isReady(): Boolean =
        when (this) {
            is Poll.Ready<*> -> true
            else -> false
        }

    /**
     * Returns `true` if the poll is a [Pending] value.
     *
     * [Pending]: Poll.Pending
     *
     * # Examples
     *
     * ```kotlin
     * val x: Poll<UInt> = Poll.Ready(2)
     * check(x.isPending() == false)
     * val x1: Poll<UInt> = Poll.Pending
     * check(x1.isPending() == true)
     * ```
     */
    public open fun isPending(): Boolean = !this.isReady()

    public companion object {
        /**
         * Moves the value into a [Poll.Ready] to make a `Poll<T>`.
         *
         * # Example
         *
         * ```kotlin
         * check(Poll.from(true) == Poll.Ready(true))
         * ```
         */
        public fun <T> from(t: T): Poll<T> = Poll.Ready(t)
    }
}

/**
 * Maps a `Poll<Result<T, E>>` to `Poll<Result<U, E>>` by applying a
 * function to a contained `Poll.Ready(Ok)` value, leaving all other
 * variants untouched.
 *
 * This function can be used to compose the results of two functions.
 *
 * # Examples
 *
 * ```kotlin
 * val res: Poll<Result<UByte>> = Poll.Ready("12".parse())
 * val squared = res.mapOk({ n -> n * n })
 * check(squared == Poll.Ready(Result.success(144)))
 * ```
 */
public fun <T, E, U> Poll<Result<T, E>>.mapOk(f: (T) -> U): Poll<Result<U, E>> =
    when {
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Ok<*, *> -> {
            val t = ((this as Poll.Ready<T>).value as Result.Ok<*, *>).okValue()
            Poll.Ready(Result.Ok(f(t)))
        }
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Err<*, *> -> {
            val e = ((this as Poll.Ready<T>).value as Result.Err<*, *>).errValue()
            Poll.Ready(Result.Err(e))
        }
        else -> Poll.Pending
    }

/**
 * Maps a `Poll.Ready<Result<T, E>>` to `Poll.Ready<Result<T, U>>` by
 * applying a function to a contained `Poll.Ready(Err)` value, leaving all other
 * variants untouched.
 *
 * This function can be used to pass through a successful result while handling
 * an error.
 *
 * # Examples
 *
 * ```kotlin
 * val res: Poll<Result<UByte>> = Poll.Ready("oops".parse())
 * val res1 = res.mapErr({ 0u.toUByte() })
 * check(res1 == Poll.Ready(Result.failure(0)))
 * ```
 */
public fun <T, E, U> Poll<Result<T, E>>.mapErr(f: (E) -> U): Poll<Result<T, U>> =
    when {
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Ok<*, *> -> {
            val t = ((this as Poll.Ready<T>).value as Result.Ok<*, *>).okValue()
            Poll.Ready(Result.Ok(t))
        }
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Err<*, *> -> {
            val e = ((this as Poll.Ready<T>).value as Result.Err<*, *>).errValue()
            Poll.Ready(Result.Err(f(e)))
        }
        else -> Poll.Pending
    }

public fun <T, E> tryFromOutputPollResultTE(c: Poll<T>): Poll<Result<T, E>> = c.map({ Result.Ok(it) })

public fun <T, E> Poll<Result<T, E>>.branch(): ControlFlow<Result<Nothing, E>, Poll<T>> =
    when {
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Ok<*, *> -> {
            val x = ((this as Poll.Ready<T>).value as Result.Ok<*, *>).okValue()
            ControlFlow.Continue(Poll.Ready(x))
        }
        this is Poll.Ready<*> && (this as Poll.Ready<T>).value is Result.Err<*, *> -> {
            val e = ((this as Poll.Ready<T>).value as Result.Err<*, *>).errValue()
            ControlFlow.Break(Result.Err(e))
        }
        else -> ControlFlow.Continue(Poll.Pending)
    }

public fun <T, E, F> fromResidualPollResultTF(x: Result<Nothing, E>): Poll<Result<T, F>> =
    when (x) {
        is Result.Err<*, *> -> {
            val e = x.errValue()
            Poll.Ready(Result.Err(From.from(e)))
        }
    }

public fun <T, E> tryFromOutputPollOptionResultTE(c: Poll<T?>): Poll<Result<T, E>?> = c.map({ x -> x.map({ Result.Ok(it) }) })

public fun <T, E, F> fromResidualPollOptionResultTF(x: Result<Nothing, E>): Poll<Result<T, F>?> =
    when (x) {
        is Result.Err<*, *> -> {
            val e = x.errValue()
            Poll.Ready(Result.Err(From.from(e)))
        }
    }
