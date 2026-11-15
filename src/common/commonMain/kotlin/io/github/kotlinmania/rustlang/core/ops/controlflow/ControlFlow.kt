// port-lint: source ops/control_flow.rs
package io.github.kotlinmania.rustlang.core.ops.controlflow

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.ops.trytrait.FromResidual
import io.github.kotlinmania.rustlang.ops.trytrait.Try

public typealias TryType<B, C> = ControlFlow<B, C>

// ControlFlow should not implement PartialOrd or Ord, per RFC 3058:
// https://rust-lang.github.io/rfcs/3058-try-trait-v2.html#traits-for-controlflow

/**
 * Used to tell an operation whether it should exit early or go on as usual.
 *
 * This is used when exposing things (like graph traversals or visitors) where
 * you want the user to be able to choose whether to exit early.
 * Having the enum makes it clearer -- no more wondering "wait, what did `false`
 * mean again?" -- and allows including a value.
 *
 * Similar to [Option] and [Result], this enum can be used with the `?` operator
 * to return immediately if the [Break] variant is present or otherwise continue normally
 * with the value inside the [Continue] variant.
 *
 * # Examples
 *
 * Early-exiting from [Iterator.tryForEach]:
 * ```kotlin
 * import std.ops.ControlFlow
 *
 * val r = (2 until 100).tryForEach(closure@{ x ->
 *     if (403 % x == 0) {
 *         return@closure ControlFlow.Break(x)
 *     }
 *     ControlFlow.Continue()
 * })
 * check(r == ControlFlow.Break(13))
 * ```
 *
 * A basic tree traversal:
 * ```kotlin
 * import std.ops.ControlFlow
 *
 * open class TreeNode<T>(
 *     public var value: T,
 *     public var left: TreeNode<T>?,
 *     public var right: TreeNode<T>?,
 * ) {
 *     public open fun <B> traverseInorder(f: (T) -> ControlFlow<B>): ControlFlow<B> {
 *         when (val matchVal = this.left) {
 *             null -> {}
 *             else -> {
 *                 val left = matchVal
 *                 left.traverseInorder(f).getOrElse { return Result.failure(it) }
 *             }
 *         }
 *         f(this.value).getOrElse { return Result.failure(it) }
 *         when (val matchVal1 = this.right) {
 *             null -> {}
 *             else -> {
 *                 val right = matchVal1
 *                 right.traverseInorder(f).getOrElse { return Result.failure(it) }
 *             }
 *         }
 *         return ControlFlow.Continue()
 *     }
 *
 *     public open fun leaf(value: T): TreeNode<T>? {
 *         return TreeNode(value = value, left = null, right = null)
 *     }
 * }
 * val node = TreeNode(value = 0, left = TreeNode.leaf(1), right = TreeNode(value = -1, left = TreeNode.leaf(5), right = TreeNode.leaf(2)))
 * var sum = 0
 * val res = node.traverseInorder({ value ->
 *     if (value < 0) {
 *         ControlFlow.Break(value)
 *     } else {
 *         sum += value
 *         ControlFlow.Continue()
 *     }
 * })
 * check(res == ControlFlow.Break(-1))
 * check(sum == 6)
 * ```
 *
 * [Break]: ControlFlow.Break
 * [Continue]: ControlFlow.Continue
 */
public sealed class ControlFlow<B, C> : Try<ControlFlow<B, C>, C, ControlFlow<B, Nothing>>, FromResidual<ControlFlow<B, Nothing>, ControlFlow<B, C>> {
    /**
     * Move on to the next phase of the operation as normal.
     */
    public data class Continue<B, C>(public var value: C) : ControlFlow<B, C>()
    /**
     * Exit the operation without running subsequent phases.
     */
    public data class Break<B, C>(public var value: B) : ControlFlow<B, C>()

    public fun rustEquals(other: ControlFlow<B, C>): Boolean = this == other

    public override fun branch(): ControlFlow<ControlFlow<B, Nothing>, C> =
        when (this) {
            is ControlFlow.Continue -> {
                val (c) = this
                ControlFlow.Continue(c)
            }
            is ControlFlow.Break -> {
                val (b) = this
                ControlFlow.Break(ControlFlow.Break(b))
            }
        }

    /**
     * Returns `true` if this is a `Break` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * check(ControlFlow.Break("Stop right there!").isBreak())
     * check(!ControlFlow.Continue(3).isBreak())
     * ```
     */
    public open fun isBreak(): Boolean =
        when (this) {
            is ControlFlow.Break -> true
            else -> false
        }

    /**
     * Returns `true` if this is a `Continue` variant.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * check(!ControlFlow.Break("Stop right there!").isContinue())
     * check(ControlFlow.Continue(3).isContinue())
     * ```
     */
    public open fun isContinue(): Boolean =
        when (this) {
            is ControlFlow.Continue -> true
            else -> false
        }

    /**
     * Converts the `ControlFlow` into an `Option` which is `Some` if the
     * `ControlFlow` was `Break` and `None` otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * check(ControlFlow.Break("Stop right there!").breakValue() == "Stop right there!")
     * check(ControlFlow.Continue(3).breakValue() == null)
     * ```
     */
    public open fun breakValue(): B? =
        when (this) {
            is ControlFlow.Continue -> null
            is ControlFlow.Break -> {
                val (x) = this
                x
            }
        }

    /**
     * Converts the `ControlFlow` into a `Result` which is `Ok` if the
     * `ControlFlow` was `Break` and `Err` if otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * open class TreeNode<T>(
     *     public var value: T,
     *     public var left: TreeNode<T>?,
     *     public var right: TreeNode<T>?,
     * ) {
     *     public open fun find(predicate: (T) -> Boolean): Result<T> {
     *         var f = { t: T ->
     *             if (predicate(t)) {
     *                 ControlFlow.Break(t)
     *             } else {
     *                 ControlFlow.Continue()
     *             }
     *         }
     *         return this.traverseInorder(f).breakOk()
     *     }
     *
     *     public open fun <B> traverseInorder(f: (T) -> ControlFlow<B>): ControlFlow<B> {
     *         when (val matchVal = this.left) {
     *             null -> {}
     *             else -> {
     *                 val left = matchVal
     *                 left.traverseInorder(f).getOrElse { return Result.failure(it) }
     *             }
     *         }
     *         f(this.value).getOrElse { return Result.failure(it) }
     *         when (val matchVal1 = this.right) {
     *             null -> {}
     *             else -> {
     *                 val right = matchVal1
     *                 right.traverseInorder(f).getOrElse { return Result.failure(it) }
     *             }
     *         }
     *         return ControlFlow.Continue()
     *     }
     *
     *     public open fun leaf(value: T): TreeNode<T>? {
     *         return TreeNode(value = value, left = null, right = null)
     *     }
     * }
     * val node = TreeNode(value = 0, left = TreeNode.leaf(1), right = TreeNode(value = -1, left = TreeNode.leaf(5), right = TreeNode.leaf(2)))
     * val res = node.indexOf({ value: Int -> value > 3 }).takeIf { it >= 0 }
     * check(res == Result.success(5))
     * ```
     */
    public open fun breakOk(): Result<B, C> =
        when (this) {
            is ControlFlow.Continue -> {
                val (c) = this
                Result.Err(c)
            }
            is ControlFlow.Break -> {
                val (b) = this
                Result.Ok(b)
            }
        }

    /**
     * Maps `ControlFlow<B, C>` to `ControlFlow<T, C>` by applying a function
     * to the break value in case it exists.
     */
    public open fun <T> mapBreak(f: (B) -> T): ControlFlow<T, C> =
        when (this) {
            is ControlFlow.Continue -> {
                val (x) = this
                ControlFlow.Continue(x)
            }
            is ControlFlow.Break -> {
                val (x1) = this
                ControlFlow.Break(f(x1))
            }
        }

    /**
     * Converts the `ControlFlow` into an `Option` which is `Some` if the
     * `ControlFlow` was `Continue` and `None` otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * check(ControlFlow.Break("Stop right there!").continueValue() == null)
     * check(ControlFlow.Continue(3).continueValue() == 3)
     * ```
     */
    public open fun continueValue(): C? =
        when (this) {
            is ControlFlow.Continue -> {
                val (x) = this
                x
            }
            is ControlFlow.Break -> null
        }

    /**
     * Converts the `ControlFlow` into a `Result` which is `Ok` if the
     * `ControlFlow` was `Continue` and `Err` if otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * open class TreeNode<T>(
     *     public var value: T,
     *     public var left: TreeNode<T>?,
     *     public var right: TreeNode<T>?,
     * ) {
     *     public open fun <B> validate(f: (T) -> ControlFlow<B>): Result<Unit> {
     *         return this.traverseInorder(f).continueOk()
     *     }
     *
     *     public open fun <B> traverseInorder(f: (T) -> ControlFlow<B>): ControlFlow<B> {
     *         when (val matchVal = this.left) {
     *             null -> {}
     *             else -> {
     *                 val left = matchVal
     *                 left.traverseInorder(f).getOrElse { return Result.failure(it) }
     *             }
     *         }
     *         f(this.value).getOrElse { return Result.failure(it) }
     *         when (val matchVal1 = this.right) {
     *             null -> {}
     *             else -> {
     *                 val right = matchVal1
     *                 right.traverseInorder(f).getOrElse { return Result.failure(it) }
     *             }
     *         }
     *         return ControlFlow.Continue()
     *     }
     *
     *     public open fun leaf(value: T): TreeNode<T>? {
     *         return TreeNode(value = value, left = null, right = null)
     *     }
     * }
     * val node = TreeNode(value = 0, left = TreeNode.leaf(1), right = TreeNode(value = -1, left = TreeNode.leaf(5), right = TreeNode.leaf(2)))
     * val res = node.validate(closure@{ value ->
     *     if (value < 0) {
     *         return@closure ControlFlow.Break("negative value detected")
     *     }
     *     if (value > 4) {
     *         return@closure ControlFlow.Break("too big value detected")
     *     }
     *     ControlFlow.Continue()
     * })
     * check(res == Result.failure("too big value detected"))
     * ```
     */
    public open fun continueOk(): Result<C, B> =
        when (this) {
            is ControlFlow.Continue -> {
                val (c) = this
                Result.Ok(c)
            }
            is ControlFlow.Break -> {
                val (b) = this
                Result.Err(b)
            }
        }

    /**
     * Maps `ControlFlow<B, C>` to `ControlFlow<B, T>` by applying a function
     * to the continue value in case it exists.
     */
    public open fun <T> mapContinue(f: (C) -> T): ControlFlow<B, T> =
        when (this) {
            is ControlFlow.Continue -> {
                val (x) = this
                ControlFlow.Continue(f(x))
            }
            is ControlFlow.Break -> {
                val (x1) = this
                ControlFlow.Break(x1)
            }
        }

    public companion object {
        public fun <B, C> fromOutput(output: C): ControlFlow<B, C> = ControlFlow.Continue(output)

        public fun <B, C> fromResidual(residual: ControlFlow<B, Nothing>): ControlFlow<B, C> =
            when (residual) {
                is ControlFlow.Break -> {
                    val (b) = residual
                    ControlFlow.Break(b)
                }
            }
    }
}

/**
 * Extracts the value `T` that is wrapped by `ControlFlow<T, T>`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.ControlFlow
 *
 * @feature(controlFlowIntoValue)
 * public fun doctestEntry() {
 *     check(ControlFlow.Break(1024).intoValue() == 1024)
 *     check(ControlFlow.Continue(512).intoValue() == 512)
 * }
 * ```
 */
public fun <T> ControlFlow<T, T>.intoValue(): T =
    when (this) {
        is ControlFlow.Continue -> {
            val (x) = this
            x
        }
        is ControlFlow.Break -> {
            val (x1) = this
            x1
        }
    }

// These are used only as part of implementing the iterator adapters.
// They have mediocre names and non-obvious semantics, so aren't
// currently on a path to potential stabilization.

/**
 * Creates a `ControlFlow` from any type implementing `Try`.
 */
public fun <Output, Residual, R : Try<R, Any?, Any?>> ControlFlow.Companion.fromTry(r: R, branchRFn: (R) -> ControlFlow<Residual, Output>, fromResidualRFn: (R) -> R): ControlFlow<R, R.Output> where R : Try<R, Any?, Any?> =
    when (val matchVal = branchRFn(r)) {
        is ControlFlow.Continue -> {
            val (v) = matchVal
            ControlFlow.Continue(v)
        }
        is ControlFlow.Break -> {
            val (v1) = matchVal
            ControlFlow.Break(fromResidualRFn(v1))
        }
    }

/**
 * Converts a `ControlFlow` into any type implementing `Try`.
 */
public fun <Output, Residual, R : Try<R, Any?, Any?>> ControlFlow<R, R.Output>.intoTry(fromOutputRFn: (Output) -> R): R where R : Try<R, Any?, Any?> =
    when (this) {
        is ControlFlow.Continue -> {
            val (v) = this
            fromOutputRFn(v)
        }
        is ControlFlow.Break -> {
            val (v1) = this
            v1
        }
    }
