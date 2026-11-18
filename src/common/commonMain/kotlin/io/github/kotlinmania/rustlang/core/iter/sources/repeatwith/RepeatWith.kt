// port-lint: source iter/sources/repeat_with.rs
package io.github.kotlinmania.rustlang.core.iter.sources.repeatwith

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugStruct(
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
 * Creates a new iterator that repeats elements of type `A` endlessly by
 * applying the provided closure, the repeater, `F: FnMut() -> A`.
 *
 * The `repeatWith()` function calls the repeater over and over again.
 *
 * Infinite iterators like `repeatWith()` are often used with adapters like
 * [Iterator.take], in order to make them finite.
 *
 * If the element type of the iterator you need implements [Clone], and
 * it is OK to keep the source element in memory, you should instead use
 * the [repeat] function.
 *
 * An iterator produced by `repeatWith()` is not a [DoubleEndedIterator].
 * If you need `repeatWith()` to return a [DoubleEndedIterator],
 * please open a GitHub issue explaining your use case.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.iter
 *
 * // let's assume we have some value of a type that is not `Clone`
 * // or which we don't want to have in memory just yet because it is expensive:
 * open class Expensive
 * // a particular value forever:
 * var things = iter.repeatWith({ Expensive() })
 * check(Expensive() == things.next())
 * check(Expensive() == things.next())
 * check(Expensive() == things.next())
 * check(Expensive() == things.next())
 * check(Expensive() == things.next())
 * ```
 *
 * Using mutation and going finite:
 *
 * ```kotlin
 * import std.iter
 *
 * // From the zeroth to the third power of two:
 * var curr = 1
 * var pow2 = iter.repeatWith({
 *     val tmp = curr
 *     curr *= 2
 *     tmp
 * }).take(4)
 * check(1 == pow2.next())
 * check(2 == pow2.next())
 * check(4 == pow2.next())
 * check(8 == pow2.next())
 * // ... and now we're done
 * check(null == pow2.next())
 * ```
 */
public fun <A> repeatWith(repeater: () -> A): RepeatWith<() -> A> = RepeatWith(repeater = repeater)

/**
 * An iterator that repeats elements of type `A` endlessly by
 * applying the provided closure `F: FnMut() -> A`.
 *
 * This `class` is created by the [repeatWith] function.
 * See its documentation for more.
 */
public open class RepeatWith<F>(
    public var repeater: F,
) {
    public open fun clone(): RepeatWith<F> = RepeatWith(repeater)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "RepeatWith").finishNonExhaustive(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun <F> typeLayout(typeLayoutF: TypeLayout<F>): TypeLayout<RepeatWith<F>> =
            object : TypeLayout<RepeatWith<F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutF.sizeBytes, alignBytes)
                override fun readNative(addr: Long): RepeatWith<F> =
                    RepeatWith(
                        (typeLayoutF).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RepeatWith<F>) {
                    (typeLayoutF).writeNative(addr, value.repeater)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RepeatWith<F> =
                    RepeatWith(
                        (typeLayoutF).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RepeatWith<F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.repeater)
                }
            }
    }
}

public fun <A> RepeatWith<F>.next(): A? = (this.repeater)()

public fun <A> RepeatWith<F>.sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)

public fun <Residual, A, Acc, R> RepeatWith<F>.tryFold(init: Acc, fold: (Acc, A) -> R): R where R : Try<R, Acc, Residual> {
    var init1 = init
    // This override isn't strictly needed, but avoids the need to optimize
    // away the `next`-always-returns-`Some` and emphasizes that the `?`
    // is the only way to exit the loop.
    while (true) {
        val item = (this.repeater)()
        init1 = when (val tryResult = fold(init1, item)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
}
