// port-lint: source sync/mpmc/error.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc

import io.github.kotlinmania.rustlang.sync.mpsc.SendError

/**
 * An error returned from the [sendTimeout] method.
 *
 * The error contains the message being sent so it can be recovered.
 */
public sealed class SendTimeoutError<T> {
    /**
     * The message could not be sent because the channel is full and the operation timed out.
     *
     * If this is a zero-capacity channel, then the error indicates that there was no receiver
     * available to receive the message and the operation timed out.
     */
    public data class Timeout<T>(public var value: T) : SendTimeoutError<T>()
    /**
     * The message could not be sent because the channel is disconnected.
     */
    public data class Disconnected<T>(public var value: T) : SendTimeoutError<T>()

    public fun rustEquals(other: SendTimeoutError<T>): Boolean = this == other

    public open fun fmt(f: StringBuilder) {
        "SendTimeoutError(..)".fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        when (this) {
            is SendTimeoutError.Timeout -> "timed out waiting on send operation".fmt(f)
            is SendTimeoutError.Disconnected -> "sending on a disconnected channel".fmt(f)
        }
    }

    public companion object {
        public fun <T> from(err: SendError<T>): SendTimeoutError<T> =
            when (err) {
                is SendError -> {
                    val (e) = err
                    SendTimeoutError.Disconnected(e)
                }
            }
    }
}
