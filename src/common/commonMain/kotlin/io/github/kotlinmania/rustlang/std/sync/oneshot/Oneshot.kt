// port-lint: source sync/oneshot.rs
package io.github.kotlinmania.rustlang.std.sync.oneshot

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.mpmc.syncChannel
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvError
import io.github.kotlinmania.rustlang.std.sync.mpsc.SendError
import io.github.kotlinmania.rustlang.std.time.Instant

// A single-producer, single-consumer (oneshot) channel.
//
// This is an experimental module, so the API will likely change.

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

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Creates a new oneshot channel, returning the sender/receiver halves.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.oneshot
 * import std.thread
 *
 * @feature(oneshotChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = oneshot.channel()
 *     // Spawn off an expensive computation.
 *     thread.spawn({
 *         sender.send(expensiveComputation()).unwrap()
 *     })
 *     doOtherWork()
 *     // Let's see what that answer was...
 *     println("${receiver.recv().unwrap()}")
 *     // `receiver` is consumed by `recv`, so we cannot use it anymore.
 * }
 * ```
 */
public fun <T> channel(): Pair<Sender<T>, Receiver<T>> {
    // Using a `syncChannel` with capacity 1 means that the internal implementation will use the
    // `Array`-flavored channel implementation.
    val (sender, receiver) = syncChannel(1)
    return Pair(Sender(inner = sender), Receiver(inner = receiver))
}

// ///////////////////////////////////////////////////////////////////////////////////////////////
// Sender
// ///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The sending half of a oneshot channel.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.oneshot
 * import std.thread
 *
 * @feature(oneshotChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = oneshot.channel()
 *     thread.spawn({
 *         sender.send("Hello from thread!").unwrap()
 *     })
 *     check(receiver.recv().unwrap() == "Hello from thread!")
 * }
 * ```
 *
 * `Sender` cannot be sent between threads if it is sending non-`Send` types.
 *
 * ```compile_fail
 * @file:feature(oneshotChannel)
 * use std.sync.oneshot;
 * use std.thread;
 * use std.ptr;
 *
 * let (sender, receiver) = oneshot.channel();
 *
 * struct NotSend(*mut ());
 * thread.spawn(move || {
 *     sender.send(NotSend(ptr.nullMut()));
 * });
 *
 * let reply = receiver.tryRecv().unwrap();
 * ```
 */
public open class Sender<T>(
    /**
     * The `oneshot` channel is simply a wrapper around a `mpmc` channel.
     */
    public var inner: io.github.kotlinmania.rustlang.std.sync.mpmc.Sender<T>,
) {
    // SAFETY: Sending a value requires ownership of the sender. Shared references only inspect
    // channel state, so they cannot send a value of a non-`Send` type from another thread.

    /**
     * Attempts to send a value through this channel. This can only fail if the corresponding
     * [Receiver.T] has been dropped.
     *
     * This method is non-blocking (wait-free).
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.oneshot
     * import std.thread
     *
     * @feature(oneshotChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = oneshot.channel()
     *     thread.spawn({
     *         // Perform some computation.
     *         val result = 2 + 2
     *         tx.send(result).unwrap()
     *     })
     *     check(rx.recv().unwrap() == 4)
     * }
     * ```
     */
    public open fun send(t: T): Result<Unit, SendError<T>> = this.inner.send(t)

    /**
     * Returns `true` if the corresponding [Receiver] has been dropped.
     *
     * If this method returns `true`, [`send`](Self.send) is guaranteed to return an error.
     *
     * On the receiving side, [Receiver.isReady] checks whether a value is available without
     * consuming the receiver.
     *
     * A return value of `false` does not guarantee that a later call to [`send`](Self.send) will
     * succeed. The receiver may be dropped immediately after this method returns.
     */
    public open fun isClosed(): Boolean = this.inner.isDisconnected()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Sender").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

// ///////////////////////////////////////////////////////////////////////////////////////////////
// Receiver
// ///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The receiving half of a oneshot channel.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.oneshot
 * import std.thread
 * import std.time.Duration
 *
 * @feature(oneshotChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = oneshot.channel()
 *     thread.spawn({
 *         thread.sleep(Duration.fromMillis(100))
 *         sender.send("Hello after delay!").unwrap()
 *     })
 *     println("Waiting for message...")
 *     println("${receiver.recv().unwrap()}")
 * }
 * ```
 *
 * `Receiver` cannot be sent between threads if it is receiving non-`Send` types.
 *
 * ```compile_fail
 * # @file:feature(oneshotChannel)
 * # use std.sync.oneshot;
 * # use std.thread;
 * # use std.ptr;
 * #
 * let (sender, receiver) = oneshot.channel();
 *
 * struct NotSend(*mut ());
 * sender.send(NotSend(ptr.nullMut()));
 *
 * thread.spawn(move || {
 *     let reply = receiver.tryRecv().unwrap();
 * });
 * ```
 */
public open class Receiver<T>(
    /**
     * The `oneshot` channel is simply a wrapper around a `mpmc` channel.
     */
    public var inner: io.github.kotlinmania.rustlang.std.sync.mpmc.Receiver<T>,
) {
    // SAFETY: Receiving a value requires ownership of the receiver. Shared references only inspect
    // channel state, so they cannot access a value of a non-`Send` type from another thread.

    /**
     * Receives the value from the sending end, blocking the calling thread until it gets it.
     *
     * Can only fail if the corresponding [Sender.T] has been dropped.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.oneshot
     * import std.thread
     * import std.time.Duration
     *
     * @feature(oneshotChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = oneshot.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(500))
     *         tx.send("Done!").unwrap()
     *     })
     *     // This will block until the message arrives.
     *     println("${rx.recv().unwrap()}")
     * }
     * ```
     */
    public open fun recv(): Result<T, RecvError> = this.inner.recv()

    // Fallible methods.

    /**
     * Returns `Ok(true)` if the sender has sent a value over the channel.
     *
     * Returns `Ok(false)` if no value has been sent, but the corresponding [Sender.T] still
     * exists (has not been dropped yet).
     *
     * Returns a [RecvError] if the corresponding [Sender.T] has been dropped before a value
     * has been sent.
     *
     * If this method returns `Ok(true)`, then any of the `recv` methods are guaranteed to return
     * the value successfully without blocking.
     *
     * # Warnings
     *
     * A return value of `Ok(false)` does not guarantee that a later receive operation will block.
     * The sender may send a value or be dropped immediately after this method returns.
     *
     * Polling this method in a loop just to wait for a value wastes CPU time. Use
     * [`recv`](Self.recv) or [`recvTimeout`](Self.recvTimeout) instead.
     */
    public open fun isReady(): Result<Boolean, RecvError> {
        // Sending consumes and drops the sender after writing the value, so the channel is ready
        // only when it is both disconnected and nonempty.
        if (!this.inner.isDisconnected()) {
            return Result.Ok<Boolean, RecvError>(false)
        }
        return if (this.inner.isEmpty()) {
            Result.Err<Boolean, RecvError>(RecvError)
        } else {
            Result.Ok<Boolean, RecvError>(true)
        }
    }

    /**
     * Attempts to return a pending value on this receiver without blocking.
     *
     * To check whether a value is available without consuming the receiver, use
     * [`isReady`](Self.isReady).
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.oneshot
     * import std.thread
     * import std.time.Duration
     *
     * @feature(oneshotChannel)
     * public fun doctestEntry() {
     *     var (sender, receiver) = oneshot.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(100))
     *         sender.send(42).unwrap()
     *     })
     *     // Keep trying until we get the message, doing other work in the process.
     *     while (true) {
     *         run {
     *             val matchVal = receiver.tryRecv()
     *             when {
     *                 matchVal is Ok -> {
     *                     val (value) = matchVal
     *                     check(value == 42)
     *                     break
     *                 }
     *                 matchVal is Err && matchVal.value is oneshot.TryRecvError.Empty -> {
     *                     val (rx) = matchVal.value as oneshot.TryRecvError.Empty
     *                     // Retake ownership of the receiver.
     *                     receiver = rx
     *                     doOtherWork()
     *                 }
     *                 else -> error("Sender disconnected")
     *             }
     *         }
     *     }
     * }
     * ```
     */
    public open fun tryRecv(): Result<T, TryRecvError<T>> =
        this.inner.tryRecv().mapErr({ err -> when (err) {
            is io.github.kotlinmania.rustlang.std.sync.mpmc.TryRecvError.Empty<*> -> TryRecvError.Empty(this)
            is io.github.kotlinmania.rustlang.std.sync.mpmc.TryRecvError.Disconnected<*> -> TryRecvError.Disconnected()
        } })

    /**
     * Attempts to wait for a value on this receiver, returning an error if the corresponding
     * [Sender] half of this channel has been dropped, or if it waits more than `timeout`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.oneshot
     * import std.thread
     * import std.time.Duration
     *
     * @feature(oneshotChannel)
     * public fun doctestEntry() {
     *     val (sender, receiver) = oneshot.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(500))
     *         sender.send("Success!").unwrap()
     *     })
     *     // Wait up to 1 second for the message
     *     run {
     *         val matchVal = receiver.recvTimeout(Duration.fromSecs(1))
     *         when {
     *             matchVal is Ok -> {
     *                 val (msg) = matchVal
     *                 println("Received: $msg")
     *             }
     *             matchVal is Err && matchVal.value is oneshot.RecvTimeoutError.Timeout -> println("Timed out!")
     *             else -> println("Sender dropped!")
     *         }
     *     }
     * }
     * ```
     */
    public open fun recvTimeout(timeout: Duration): Result<T, RecvTimeoutError<T>> =
        this.inner.recvTimeout(timeout).mapErr({ err -> when (err) {
            is io.github.kotlinmania.rustlang.std.sync.mpmc.RecvTimeoutError.Timeout<*> -> RecvTimeoutError.Timeout(this)
            is io.github.kotlinmania.rustlang.std.sync.mpmc.RecvTimeoutError.Disconnected<*> -> RecvTimeoutError.Disconnected()
        } })

    /**
     * Attempts to wait for a value on this receiver, returning an error if the corresponding
     * [Sender] half of this channel has been dropped, or if `deadline` is reached.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.oneshot
     * import std.thread
     * import std.time.Duration
     * import std.time.Instant
     *
     * @feature(oneshotChannel)
     * public fun doctestEntry() {
     *     val (sender, receiver) = oneshot.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(100))
     *         sender.send("Just in time!").unwrap()
     *     })
     *     val deadline = Instant.now() + Duration.fromMillis(500)
     *     run {
     *         val matchVal = receiver.recvDeadline(deadline)
     *         when {
     *             matchVal is Ok -> {
     *                 val (msg) = matchVal
     *                 println("Received: $msg")
     *             }
     *             matchVal is Err && matchVal.value is oneshot.RecvTimeoutError.Timeout -> println("Missed deadline!")
     *             else -> println("Sender dropped!")
     *         }
     *     }
     * }
     * ```
     */
    public open fun recvDeadline(deadline: Instant): Result<T, RecvTimeoutError<T>> =
        this.inner.recvDeadline(deadline).mapErr({ err -> when (err) {
            is io.github.kotlinmania.rustlang.std.sync.mpmc.RecvTimeoutError.Timeout<*> -> RecvTimeoutError.Timeout(this)
            is io.github.kotlinmania.rustlang.std.sync.mpmc.RecvTimeoutError.Disconnected<*> -> RecvTimeoutError.Disconnected()
        } })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Receiver").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

// ///////////////////////////////////////////////////////////////////////////////////////////////
// Receiver Errors
// ///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * An error returned from the [`tryRecv`](Receiver.tryRecv) method.
 *
 * See the documentation for [tryRecv] for more information on how to use this error.
 *
 * [tryRecv]: Receiver.tryRecv
 */
public sealed class TryRecvError<T> {
    /**
     * The [Sender] has not sent a message yet, but it might in the future (as it has not yet
     * disconnected). This variant contains the [Receiver] that [`tryRecv`](Receiver.tryRecv)
     * took ownership over.
     */
    public data class Empty<T>(public var value: Receiver<T>) : TryRecvError<T>()
    /**
     * The corresponding [Sender] half of this channel has become disconnected, and there will
     * never be any more data sent over the channel.
     */
    public class Disconnected<T>() : TryRecvError<T>()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "TryRecvError").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TryRecvError.Empty<*> -> "receiving on an empty oneshot channel".fmt(f)
            is TryRecvError.Disconnected<*> -> "receiving on a closed oneshot channel".fmt(f)
        }

    public companion object {
        /**
         * Converts a `RecvError` into a `TryRecvError`.
         *
         * This conversion always returns `TryRecvError.Disconnected`.
         *
         * No data is allocated on the heap.
         */
        public fun <T> from(err: RecvError): TryRecvError<T> =
            when (err) {
                RecvError -> TryRecvError.Disconnected()
            }
    }
}

/**
 * An error returned from the [`recvTimeout`](Receiver.recvTimeout) or
 * [`recvDeadline`](Receiver.recvDeadline) methods.
 *
 * # Examples
 *
 * Usage of this error is similar to [TryRecvError].
 *
 * ```kotlin
 * import std.sync.oneshot.RecvTimeoutError
 * import std.thread
 * import std.time.Duration
 *
 * @feature(oneshotChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = oneshot.channel()
 *     val sendFailure = thread.spawn({
 *         // Simulate a long computation that takes longer than our timeout.
 *         thread.sleep(Duration.fromMillis(250))
 *         // This will likely fail to send because we drop the receiver in the main thread.
 *         sender.send("Goodbye!".toString()).unwrap()
 *     })
 *     // Try to receive the message with a short timeout.
 *     run {
 *         val matchVal = receiver.recvTimeout(Duration.fromMillis(10))
 *         when {
 *             matchVal is Ok -> {
 *                 val (msg) = matchVal
 *                 println("Received: $msg")
 *             }
 *             matchVal is Err && matchVal.value is RecvTimeoutError.Timeout -> {
 *                 val (rx) = matchVal.value as RecvTimeoutError.Timeout
 *                 println("Timed out waiting for message!")
 *                 // Note that you can reuse the receiver without dropping it.
 *                 (rx).let { }
 *             }
 *             else -> println("Sender dropped!")
 *         }
 *     }
 *     sendFailure.join().unwrapErr()
 * }
 * ```
 */
public sealed class RecvTimeoutError<T> {
    /**
     * The [Sender] has not sent a message yet, but it might in the future (as it has not yet
     * disconnected). This variant contains the [Receiver] that either
     * [`recvTimeout`](Receiver.recvTimeout) or [`recvDeadline`](Receiver.recvDeadline) took
     * ownership over.
     */
    public data class Timeout<T>(public var value: Receiver<T>) : RecvTimeoutError<T>()
    /**
     * The corresponding [Sender] half of this channel has become disconnected, and there will
     * never be any more data sent over the channel.
     */
    public class Disconnected<T>() : RecvTimeoutError<T>()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "RecvTimeoutError").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is RecvTimeoutError.Timeout<*> -> "timed out waiting on oneshot channel".fmt(f)
            is RecvTimeoutError.Disconnected<*> -> "receiving on a closed oneshot channel".fmt(f)
        }

    public companion object {
        /**
         * Converts a `RecvError` into a `RecvTimeoutError`.
         *
         * This conversion always returns `RecvTimeoutError.Disconnected`.
         *
         * No data is allocated on the heap.
         */
        public fun <T> from(err: RecvError): RecvTimeoutError<T> =
            when (err) {
                RecvError -> RecvTimeoutError.Disconnected()
            }
    }
}
