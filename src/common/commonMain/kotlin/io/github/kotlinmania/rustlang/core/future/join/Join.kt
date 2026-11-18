// port-lint: source future/join.rs
package io.github.kotlinmania.rustlang.core.future.join

import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.future.ready.ready
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

/**
 * Future used by `join` that stores it's output to
 * be later taken and doesn't panic when polled after ready.
 *
 * This type is public in a private module for use by the macro.
 */
public sealed class MaybeDone<F : io.github.kotlinmania.rustlang.core.future.future.Future<Any?>, Output> : io.github.kotlinmania.rustlang.core.future.future.Future<Unit> {
    public data class Future<F : io.github.kotlinmania.rustlang.core.future.future.Future<Any?>, Output>(public var value: F) : MaybeDone<F, Output>()
    public data class Done<F : io.github.kotlinmania.rustlang.core.future.future.Future<Any?>, Output>(public var value: Output) : MaybeDone<F, Output>()
    public class Taken<F : io.github.kotlinmania.rustlang.core.future.future.Future<Any?>, Output>() : MaybeDone<F, Output>()

    public open fun takeOutput(): Output? =
        when (this) {
            is MaybeDone.Done<*, *> -> when (val matchVal = replace(this, MaybeDone.Taken)) {
                is MaybeDone.Done<*, *> -> {
                    val (value) = matchVal
                    value
                }
                else -> error("unreachable")
            }
            else -> null
        }

    public override fun poll(cx: Context): Poll<Unit> {
        // SAFETY: pinning in structural for `f`
        when (val matchVal = this.asMut().getUncheckedMut()) {
            is MaybeDone.Future<*, *> -> {
                val (f) = matchVal
                val value = ready(Pin.newUnchecked(f).poll(cx))
                this.set(MaybeDone.Done(value))
            }
            is MaybeDone.Done<*, *> -> {}
            MaybeDone.Taken -> error("unreachable")
        }
        return Poll.Ready(Unit)
    }
}
