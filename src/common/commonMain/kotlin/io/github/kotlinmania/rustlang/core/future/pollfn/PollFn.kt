// port-lint: source future/poll_fn.rs
package io.github.kotlinmania.rustlang.core.future.pollfn

import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

public class DebugStruct(
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
 * Creates a future that wraps a function returning [Poll].
 *
 * Polling the future delegates to the wrapped function. If the returned future is pinned, then the
 * captured environment of the wrapped function is also pinned in-place, so as long as the closure
 * does not move out of its captures it can soundly create pinned references to them.
 *
 * # Examples
 *
 * ```kotlin
 * import core.future.pollFn
 * import std.task.Context
 * import std.task.Poll
 *
 * fun readLine(cx: Context): Poll<String> {
 *     return Poll.Ready("Hello, World!".into())
 * }
 * val readFuture = pollFn(::readLine)
 * check(readFuture == "Hello, World!".toOwned())
 * ```
 *
 * ## Capturing a pinned state
 *
 * Example of a closure wrapping inner futures:
 *
 * ```kotlin
 * import core.future.Future
 * import core.task.Poll
 *
 * / **
 *  * Resolves to the first future that completes. In the event of a tie, `a` wins.
 *  * /
 * fun <T> naiveSelect(a: Future<T>, b: Future<T>): Future<T> {
 *     var (a1, b1) = Pair(Holder.pin(a), Holder.pin(b))
 *     return future.pollFn({ cx ->
 *         when (val matchVal1 = a1.asMut().poll(cx)) {
 *             is Poll.Ready -> {
 *                 val (r1) = matchVal1
 *                 Poll.Ready(r1)
 *             }
 *             else -> {
 *                 when (val matchVal = b1.asMut().poll(cx)) {
 *                     is Poll.Ready -> {
 *                         val (r) = matchVal
 *                         Poll.Ready(r)
 *                     }
 *                     else -> {
 *                         Poll.Pending
 *                     }
 *                 }
 *             }
 *         }
 *     })
 * }
 * val a = 42
 * val b = future.pending()
 * val v = naiveSelect(a, b)
 * check(v == 42)
 * val a1 = future.pending()
 * val b1 = 27
 * val v1 = naiveSelect(a1, b1)
 * check(v1 == 27)
 * val a2 = 42
 * val b2 = 27
 * val v2 = naiveSelect(a2, b2)
 * check(v2 == 42)
 * // biased towards `a` in case of tie!
 * ```
 *
 * This time without [Box.pin]ning:
 *
 * [Box.pin]: ../../std/boxed/struct.Holder.html#method.pin
 *
 * ```kotlin
 * import core.future.Future
 * import core.pin.pin
 * import core.task.Poll
 *
 * / **
 *  * Resolves to the first future that completes. In the event of a tie, `a` wins.
 *  * /
 * fun <T> naiveSelect(a: Future<T>, b: Future<T>): Future<T> {
 *     return run {
 *         var (a1, b1) = Pair(pin(a), pin(b))
 *         future.pollFn({ cx ->
 *             when (val matchVal1 = a1.asMut().poll(cx)) {
 *                 is Poll.Ready -> {
 *                     val (r1) = matchVal1
 *                     Poll.Ready(r1)
 *                 }
 *                 else -> {
 *                     when (val matchVal = b1.asMut().poll(cx)) {
 *                         is Poll.Ready -> {
 *                             val (r) = matchVal
 *                             Poll.Ready(r)
 *                         }
 *                         else -> {
 *                             Poll.Pending
 *                         }
 *                     }
 *                 }
 *             }
 *         })
 *     }
 * }
 * val a = 42
 * val b = future.pending()
 * val v = naiveSelect(a, b)
 * check(v == 42)
 * ```
 *
 *   - Notice how, by virtue of being in an `async` context, we have been able to make the [pin]
 *     macro work, thereby avoiding any need for the `unchecked`
 *     <code>[Pin.newUnchecked](fut)</code> constructor.
 */
public fun <T> pollFn(f: (Context) -> Poll<T>): PollFn<(Context) -> Poll<T>> = PollFn(f = f)

/**
 * A Future that wraps a function returning [Poll].
 *
 * This `class` is created by [pollFn]. See its
 * documentation for more.
 */
public open class PollFn<F>(
    public var f: F,
) : Future<T> {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "PollFn").finish()

    public override fun poll(cx: Context): Poll<T> {
        // SAFETY: We are not moving out of the pinned field.
        return (this.getUncheckedMut().f)(cx)
    }

    public companion object {
        public fun <F> typeLayout(typeLayoutF: TypeLayout<F>): TypeLayout<PollFn<F>> =
            object : TypeLayout<PollFn<F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutF.sizeBytes, alignBytes)
                override fun readNative(addr: Long): PollFn<F> =
                    PollFn(
                        (typeLayoutF).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PollFn<F>) {
                    (typeLayoutF).writeNative(addr, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PollFn<F> =
                    PollFn(
                        (typeLayoutF).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PollFn<F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.f)
                }
            }
    }
}
