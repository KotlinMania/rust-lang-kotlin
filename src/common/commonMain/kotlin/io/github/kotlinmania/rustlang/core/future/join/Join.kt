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
public sealed class MaybeDone<F : Future<Any?>> : Future<Unit> {
    public data class Future<F : Future<Any?>>(
        public var value: F,
    ) : MaybeDone<F>()
    public data class Done<F : Future<Any?>>(
        public var value: Output,
    ) : MaybeDone<F>()
    public class Taken<F : Future<Any?>> : MaybeDone<F>()

    public open fun takeOutput(): Output? =
        when (this) {
            is MaybeDone.Done ->
                when (val matchVal = replace(this, MaybeDone.Taken)) {
                    is MaybeDone.Done -> {
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
            is MaybeDone.Future -> {
                val (f) = matchVal
                val value = ready(Pin.newUnchecked(f).poll(cx))
                this.set(MaybeDone.Done(value))
            }
            is MaybeDone.Done -> {}
            MaybeDone.Taken -> error("unreachable")
        }
        return Poll.Ready()
    }
}
